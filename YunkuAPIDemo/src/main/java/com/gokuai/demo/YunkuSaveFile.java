package com.gokuai.demo;

import com.gokuai.cloud.ConfigHelper;
import com.gokuai.cloud.transinterface.YKHttpEngine;
import com.gokuai.demo.helper.ClientConfig;
import com.gokuai.demo.helper.DeserializeHelper;
import com.gokuai.demo.model.FileData;
import com.gokuai.demo.model.FileListData;
import com.gokuai.demo.model.MountListData;
import com.gokuai.library.data.ReturnResult;
import com.gokuai.library.util.DebugFlag;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by qp on 2017/4/20.
 */
public class YunkuSaveFile {

    private static final String TAG = "YunkuSaveFile";

    static {
        new ConfigHelper()
                .client(ClientConfig.CLIENT_ID, ClientConfig.CLIENT_SECRET)
                .apiHost("yunku-api.goukuai.cn")
                .webHost("yunku.goukuai.cn")
                .logVisible(true)
                .config();
    }

    public static void main(String[] args) {

        //先授权登录
        loginBySso("[user account]", "[CLIENT_ID]", "[CLIENT_SECRET]");

        //从库列表中获取到 mountId
        int mountId = selectMountFromList();

        //根据库id，去获取对应的文件列表,拿到列表中一个文件数据
        FileData selectedData = getFileDataForList(mountId, "", 0, 500);


        if (selectedData == null) return;

        //接着将这个文件进行转存
        // state = 1,code =200 即成功保存
        addFile(mountId, "new path/" + selectedData.getFullPath(), selectedData.getFileHash(), selectedData.getFileSize());
    }

    /**
     * sso登录
     */
    private static void loginBySso(String account, String clientId, String clientSecret) {

        String returnString = YKHttpEngine.getInstance().ssoLogin(account, clientId, clientSecret);
        ReturnResult returnResult = ReturnResult.create(returnString);
        if (returnResult.getStatusCode() == 200) {
            try {
                DeserializeHelper.getInstance().deserializeResult(returnString);
                JSONObject json = new JSONObject(returnResult.getResult());
                String key = json.optString("gkkey");
                DeserializeHelper.getInstance().deserializeResult(YKHttpEngine.getInstance().otherMethodToLogin(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取库列表
     */
    private static int selectMountFromList() {

        DebugFlag.logInfo(TAG, "======selectMountList\n");

        String returnString = YKHttpEngine.getInstance().getMountsInfo();

        DeserializeHelper.getInstance().deserializeResult(returnString);

        int mountId = 0;
        MountListData listData = MountListData.create(returnString);
        if (listData.getCode() == HttpURLConnection.HTTP_OK) {

            System.out.println("input 0-" + (listData.getList().size() - 1));

            Scanner scanner = new Scanner(System.in);

            int number = scanner.nextInt();

            mountId = listData.getList().get(number).getMountId();

            System.out.println("You select mount:" + listData.getList().get(number).getMountId());

        }

        return mountId;
    }

    /**
     * 获取文件列表
     */
    private static FileData getFileDataForList(final int mountId, final String fullPath, int start, int size) {

        DebugFlag.logInfo(TAG, "====== getFileList\n");

        String returnString = YKHttpEngine.getInstance().getFileListSync(mountId, fullPath, start, size);

        DeserializeHelper.getInstance().deserializeResult(returnString);

        FileListData fileListData = FileListData.create(returnString);
        if (fileListData.getCode() == HttpURLConnection.HTTP_OK) {

            ArrayList<FileData> list = fileListData.getList();

            if (list.size() == 0) return null;

            System.out.println("input number 0-" + (fileListData.getList().size() - 1));

            Scanner scanner = new Scanner(System.in);
            int number = scanner.nextInt();

            FileData fileData = fileListData.getList().get(number);

            if(fileData.getDir() == 1){
                //文件夹不支持缓存
                System.out.println("Directory is not supported");
            }

            String fileFullPath = fileListData.getList().get(number).getFullPath();

            System.out.println("You select file:" + fileFullPath);
            return fileListData.getList().get(number);

        }

        return null;
    }

    /**
     * 文件上传
     */
    private static void addFile(int mountId, String targetFullPath, String filehash, long filesize) {

        DebugFlag.logInfo(TAG, "====== addFile\n");

        String returnString = YKHttpEngine.getInstance()
                .addFile(mountId, targetFullPath, filehash, filesize);

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }
}

