package com.gokuai.demo;

import com.gokuai.base.LogPrint;
import com.gokuai.base.ReturnResult;
import com.gokuai.cloud.ConfigHelper;
import com.gokuai.cloud.transinterface.YKHttpEngine;
import com.gokuai.demo.helper.ClientConfig;
import com.gokuai.demo.helper.DeserializeHelper;
import com.gokuai.demo.model.FileData;
import com.gokuai.demo.model.FileListData;
import com.gokuai.demo.model.MountListData;
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
    private static int targetMountId;
    private static String dialogId;

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

        //转存的是文件夹需要输入dialogId
        if (selectedData.getDir() == 1) {

            System.out.println("input dialogId");

            Scanner scanner = new Scanner(System.in);

            dialogId = scanner.nextLine();
        }

        //接着将这个文件(夹)进行转存，转存到根目录
        // state = 1,code =200 即成功保存
        fileSave(mountId, selectedData.getFullPath(), selectedData.getFileName(), selectedData.getFileHash(),
                selectedData.getFileSize(), targetMountId, "", dialogId);
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

        LogPrint.info(TAG, "======selectMountList\n");

        String returnString = YKHttpEngine.getInstance().getMountsInfo();

        DeserializeHelper.getInstance().deserializeResult(returnString);

        int mountId = 0;
        MountListData listData = MountListData.create(returnString);
        if (listData.getCode() == HttpURLConnection.HTTP_OK) {

            System.out.println("input mountId and targetMountId， Separated by a blank key， The range is 0-" + (listData.getList().size() - 1));

            Scanner scanner = new Scanner(System.in);

            int number = scanner.nextInt();

            int targetNumber = scanner.nextInt();

            mountId = listData.getList().get(number).getMountId();

            targetMountId = listData.getList().get(targetNumber).getMountId();

            System.out.println("You select mount:" + listData.getList().get(number).getMountId());

            System.out.println("You select TargetMount:" + listData.getList().get(targetNumber).getMountId());

        }

        return mountId;
    }

    /**
     * 获取文件列表
     */
    private static FileData getFileDataForList(final int mountId, final String fullPath, int start, int size) {

        LogPrint.info(TAG, "====== getFileList\n");

        String returnString = YKHttpEngine.getInstance().getFileListSync(mountId, fullPath, start, size);

        DeserializeHelper.getInstance().deserializeResult(returnString);

        FileListData fileListData = FileListData.create(returnString);
        if (fileListData.getCode() == HttpURLConnection.HTTP_OK) {

            ArrayList<FileData> list = fileListData.getList();

            if (list.size() == 0) return null;

            System.out.println("input number 0-" + (fileListData.getList().size() - 1));

            Scanner scanner = new Scanner(System.in);
            int number = scanner.nextInt();

            String fileFullPath = fileListData.getList().get(number).getFullPath();

            System.out.println("You select file:" + fileFullPath);
            return fileListData.getList().get(number);

        }

        return null;
    }

    /**
     * 转存文件（夹）
     */
    private static void fileSave(int mountId, String fullPath, String fileName, String fileHash, long fileSize, int targetMountId, String targetFullPath, String dialogId) {

        LogPrint.info(TAG, "====== fileSave\n");

        String returnString = YKHttpEngine.getInstance()
                .fileSave(mountId, fullPath, fileName, fileHash, fileSize, targetMountId, targetFullPath, dialogId);

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }
}

