package com.gokuai.library.net;

import com.gokuai.base.LogPrint;
import com.gokuai.base.NetConnection;
import com.gokuai.base.RequestMethod;
import com.gokuai.base.ReturnResult;
import com.gokuai.base.utils.URLEncoder;
import com.gokuai.base.utils.Util;
import com.gokuai.cloud.data.FileInfo;
import com.gokuai.cloud.transinterface.YKHttpEngine;
import com.gokuai.cloud.transinterface.YKUtil;
import com.gokuai.library.data.FileOperationData;
import okhttp3.*;
import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.zip.CRC32;

public class UploadRunnable implements Runnable {
    private static final String LOG_TAG = "UploadRunnable ";

    private static final String URL_UPLOAD_INIT = "/upload_init";
    private static final String URL_UPLOAD_PART = "/upload_part";
    private static final String URL_UPLOAD_ABORT = "/upload_abort";
    private static final String URL_UPLOAD_FINISH = "/upload_finish";
    private static final String URL_UPLOAD_REQ = "/upload_req";

    private final int mRangSize;// 上传分块大小

    private String mServer = "";// 上传服务器地址
    private String mSession = "";// 上传session

    private String mLocalFullPath;
    private String mFullPath;
    private int mMountId;
    private long mDateline;
    private static long threadSeqNumber;

    private OkHttpClient mUploadHttpClient;

    private UploadCallBack mCallBack;
    private long mRId;
    private InputStream mInputStream;
    private YKHttpEngine mYkHttpEngine;

    public UploadRunnable(String localFullPath, int mountId, String fullPath,
                          long dateline, YKHttpEngine ykHttpEngine, UploadCallBack callBack, int rangSize) {

        this.mLocalFullPath = localFullPath;
        this.mFullPath = fullPath;
        this.mMountId = mountId;
        this.mDateline = dateline;
        this.mCallBack = callBack;
        this.mRId = nextThreadID();
        this.mYkHttpEngine = ykHttpEngine;
        this.mRangSize = rangSize;
    }

    public UploadRunnable(InputStream inputStream, int mountId, String fullPath,
                          long dateline, YKHttpEngine ykHttpEngine, UploadCallBack callBack, int rangSize) {

        this.mInputStream = inputStream;
        this.mFullPath = fullPath;
        this.mMountId = mountId;
        this.mDateline = dateline;
        this.mCallBack = callBack;
        this.mRId = nextThreadID();
        this.mYkHttpEngine = ykHttpEngine;
        this.mRangSize = rangSize;
    }

    private static synchronized long nextThreadID() {
        return ++threadSeqNumber;
    }

    @Override
    public void run() {

        ReturnResult result;
        String fullpath = mFullPath;
        InputStream in = null;
        BufferedInputStream bis = null;
        try {

            String filehash = "";
            long filesize = 0;
            if (!Util.isEmpty(mLocalFullPath)) {
                File file = new File(mLocalFullPath);
                if (!file.exists()) {
                    LogPrint.error(LOG_TAG, "'" + mLocalFullPath + "'  file not exist!");
                    return;
                }

                filehash = Util.getFileSha1(mLocalFullPath);
                filesize = file.length();
            }
            String filename = Util.getNameFromPath(fullpath).replace("/", "");

            if (mInputStream != null) {
                mInputStream = Util.cloneInputStream(mInputStream);
                FileInfo fileInfo = YKUtil.getFileSha1(mInputStream, false);
                filehash = fileInfo.fileHash;
                filesize = fileInfo.fileSize;
            }

            ReturnResult returnResult = ReturnResult.create(mYkHttpEngine.addFile(mMountId, fullpath, filehash, filesize));

            FileOperationData data = FileOperationData.create(returnResult.getResult(), returnResult.getStatusCode());

            if (data != null) {
                if (data.getCode() == HttpURLConnection.HTTP_OK) {
                    if (data.getState() != FileOperationData.STATE_NOUPLOAD) {
                        // 服务器上没有，上传文件
                        mServer = data.getServer();

                        if (Util.isEmpty(mServer)) {
                            throw new Exception(" The server is empty ");
                        } else {
                            LogPrint.info(LOG_TAG, "The server is " + mServer);
                        }

                        // upload_init
                        int initErrorCode = upload_init(data.getHash(), filename, fullpath, filehash, filesize);

                        if (initErrorCode == HttpURLConnection.HTTP_OK) {
                            // upload_part
                            if (mInputStream != null) {
                                in = mInputStream;
                            } else {
                                in = new FileInputStream(mLocalFullPath);
                            }

                            if (in == null) {
                                throw new Exception(" error file InputStream ");
                            }

                            bis = new BufferedInputStream(in);

                            int code = 0;
                            long range_index = 0;
                            long range_end = 0;
                            long datalength = -1;
                            long crc32 = 0;
                            String range = "";
                            byte[] buffer = new byte[mRangSize];
                            CRC32 crc = new CRC32();
                            bis.mark(mRangSize);
                            while ((datalength = bis.read(buffer)) != -1 && !isStop) {

                                range_end = mRangSize * (range_index + 1) - 1;
                                if (range_end >= filesize) {
                                    range_end = filesize - 1;
                                    int length_end = (int) (filesize - mRangSize * range_index);
                                    byte[] buffer_end = new byte[length_end];
                                    System.arraycopy(buffer, 0, buffer_end, 0, length_end);
                                    crc.update(buffer_end);
                                    crc32 = crc.getValue();
                                } else {
                                    crc.update(buffer);
                                    crc32 = crc.getValue();
                                }
                                crc.reset();
                                long currentLength = mRangSize * range_index;
                                range = currentLength + "-" + range_end;

                                mCallBack.onProgress(mRId, (float) currentLength / (float) filesize);
                                result = upload_part(range, buffer, (int) datalength, crc32);
                                code = result.getStatusCode();
                                if (code == HttpURLConnection.HTTP_OK) {
                                    // 200
                                    bis.mark(mRangSize);
                                    range_index++;
                                    System.gc();
                                } else if (code == HttpURLConnection.HTTP_ACCEPTED) {
                                    // 202-上传的文件已完成, 可以直接调finish接口
                                    break;
                                } else if (code >= HttpURLConnection.HTTP_INTERNAL_ERROR) {
                                    // >=500-服务器错误
                                    upload_server(filesize, filehash, fullpath);
                                    continue;
                                } else if (code == HttpURLConnection.HTTP_UNAUTHORIZED) {
                                    // 401-session验证不通过
                                    upload_init(data.getHash(), filename, fullpath,
                                            filehash, filesize);
                                    continue;
                                } else if (code == HttpURLConnection.HTTP_CONFLICT) {
                                    // 409-上传块序号错误, http内容中给出服务器期望的块序号
                                    JSONObject json = new JSONObject(result.getResult());
                                    long part_range_start = Long.parseLong(json.optString("expect"));
                                    range_index = part_range_start / mRangSize;
                                    bis.reset();
                                    bis.skip(part_range_start);
                                    continue;
                                } else {

                                    throw new Exception();
                                }
                            }
                        }

                        int finishErrorCode;
                        // upload_check
                        do {
                            //大文件数据没有存储完毕，需要等一会再检查一遍
                            finishErrorCode = upload_check();

                            if (finishErrorCode == HttpURLConnection.HTTP_OK) {
                                break;
                            }

                            Thread.sleep(3000);

                        } while (finishErrorCode == HttpURLConnection.HTTP_ACCEPTED);
                    }

                    // upload_sussec
                    if (mCallBack != null) {
                        mCallBack.onProgress(mRId, 1);
                        mCallBack.onSuccess(mRId, returnResult.getResult());
                    }
                } else {
                    // 上传失败
                    throw new Exception(LOG_TAG + data.getErrnoMsg().trim());
                }
            } else {
                // 上传失败
                throw new Exception();
            }

        } catch (Exception ex) {
            LogPrint.warn(LOG_TAG, ex.getMessage());

            if (!Util.isEmpty(mServer)) {
                upload_abort();
            }
            if (mCallBack != null) {
                mCallBack.onFail(mRId, ex.getMessage());
            }
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                LogPrint.warn(LOG_TAG, "runnable with io exception:msg" + e.getMessage());

            }

            System.gc();

        }


    }

    /**
     * 上传检测
     *
     * @throws Exception
     */

    private int upload_check() throws Exception {
        String returnString = upload_finish();
        ReturnResult result = ReturnResult.create(returnString);
        if (result.getStatusCode() == HttpURLConnection.HTTP_OK
                || result.getStatusCode() == HttpURLConnection.HTTP_ACCEPTED) {
            return result.getStatusCode();
        } else {
            throw new Exception();
        }

    }

    /**
     * 上传服务器
     *
     * @param filesize
     * @param filehash
     * @param fullPath
     */
    private void upload_server(long filesize, String filehash, String fullPath) {
        ReturnResult returnResult = ReturnResult.create(mYkHttpEngine.addFile(mMountId, fullPath, filehash, filesize));
        FileOperationData data = FileOperationData.create(returnResult.getResult(), returnResult.getStatusCode());
        if (data != null) {
            mServer = data.getServer();
        }
    }

    /**
     * 初始化上传
     *
     * @param hash
     * @param filename
     * @param filehash
     * @param filesize
     * @throws Exception
     */
    private int upload_init(String hash, String filename, String fullpath,
                            String filehash, long filesize) throws Exception {
        String url = mServer + URL_UPLOAD_INIT;
        final HashMap<String, String> headParams = new HashMap<>();
        headParams.put("x-gk-upload-pathhash", hash);
        headParams.put("x-gk-upload-filename", URLEncoder.encodeUTF8(filename));
        headParams.put("x-gk-upload-filehash", filehash);
        headParams.put("x-gk-upload-filesize", String.valueOf(filesize));
        headParams.put("x-gk-upload-mountid", String.valueOf(mMountId));
        headParams.put("x-gk-token", mYkHttpEngine.getToken());
        String returnString = NetConnection.sendRequest(url, RequestMethod.POST, null, headParams);
        ReturnResult returnResult = ReturnResult.create(returnString);
        if (returnResult.getStatusCode() == HttpURLConnection.HTTP_OK) {
            JSONObject json = new JSONObject(returnResult.getResult());
            mSession = json.optString("session");
            return returnResult.getStatusCode();
        } else if (returnResult.getStatusCode() == HttpURLConnection.HTTP_ACCEPTED) {
            return returnResult.getStatusCode();
        } else if (returnResult.getStatusCode() >= HttpURLConnection.HTTP_INTERNAL_ERROR) {
            upload_server(filesize, fullpath, filehash);
            return upload_init(hash, filename, fullpath, filehash, filesize);
        } else {
            throw new Exception();
        }

    }

    /**
     * 分块上传
     *
     * @param range
     * @param crc32
     * @return
     */
    private ReturnResult upload_part(String range, byte[] content, int dataLength, long crc32) {
        ReturnResult returnResult = new ReturnResult();
        String url = mServer + URL_UPLOAD_PART;
        try {

            Headers.Builder headerBuilder = new Headers.Builder();
            headerBuilder.add("Connection", "Keep-Alive");
            headerBuilder.add("x-gk-upload-session", mSession);
            headerBuilder.add("x-gk-upload-range", range);
            headerBuilder.add("x-gk-upload-crc", String.valueOf(crc32));

            Request.Builder requestBuilder = new Request.Builder();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream")
                    , content, 0, dataLength);
            Request request = requestBuilder
                    .url(url)
                    .put(requestBody)
                    .headers(headerBuilder.build())
                    .build();

            Response response = getUploadHttpClient().newCall(request).execute();

            returnResult.setResult(response.body().string());
            returnResult.setStatusCode(response.code());
            response.body().close();
        } catch (Exception e) {
            LogPrint.warn(LOG_TAG, "upload_part(): Exception is: " + e.toString());
        }
        return returnResult;
    }

    private OkHttpClient getUploadHttpClient() {
        if (mUploadHttpClient == null) {
            mUploadHttpClient = NetConnection.getOkHttpClient();
        }
        return mUploadHttpClient;
    }


    /**
     * 上传完成
     *
     * @return
     */
    private String upload_finish() {
        String url = mServer + URL_UPLOAD_FINISH;
        final HashMap<String, String> headParams = new HashMap<>();
        headParams.put("x-gk-upload-session", mSession);
        return NetConnection.sendRequest(url, RequestMethod.POST, null, headParams);
    }

    /**
     * 获得还需上传的块
     */
    private String upload_req() {
        String url = mServer + URL_UPLOAD_REQ;
        final HashMap<String, String> headParams = new HashMap<>();
        headParams.put("x-gk-upload-session", mSession);
        return NetConnection.sendRequest(url, RequestMethod.GET, null, headParams);
    }


    /**
     * 上传取消
     */
    public void upload_abort() {
        String url = mServer + URL_UPLOAD_ABORT;
        final HashMap<String, String> headParams = new HashMap<>();
        headParams.put("x-gk-upload-session", mSession);
        NetConnection.sendRequest(url, RequestMethod.POST, null, headParams);
    }


    private boolean isStop;

    public void stop() {
        isStop = true;
    }


}
