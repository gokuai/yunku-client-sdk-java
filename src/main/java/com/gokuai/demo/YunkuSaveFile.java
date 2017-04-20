package com.gokuai.demo;

import com.gokuai.cloud.ConfigHelper;
import com.gokuai.cloud.transinterface.YKHttpEngine;
import com.gokuai.demo.helper.ClientConfig;
import com.gokuai.demo.helper.DeserializeHelper;
import com.gokuai.library.data.ReturnResult;
import com.gokuai.library.util.DebugFlag;
import org.json.JSONException;
import org.json.JSONObject;

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

        loginBySso("anpei", "024bcd3979f7cccbb8be41cd301b8b90","8bc4984196dc44b7ecc2428be2c79882");

        getMountList();

        getFileList(1221861, "", 0, 500);

        getFileListByHashs(1221861, 0, 500, new String[]{"913b52ae320f26b08a2b0108f1ca053f71cde781"});

        addFile(41792,"qp.JPG","9187d349dfeaa17059f9606b7864614b2df5af31",7376,1492697296,"");
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
    private static void getMountList() {

        DebugFlag.logInfo(TAG, "======getMountList\n");

        String returnString = YKHttpEngine.getInstance().getMountsInfo();

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 获取文件列表
     */
    private static void getFileList(final int mountId, final String fullPath, int start, int size) {

        DebugFlag.logInfo(TAG, "====== getFileList\n");

        String returnString = YKHttpEngine.getInstance().getFileListSync(mountId, fullPath, start, size);

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 根据指定文件hash获取列表
     */
    private static void getFileListByHashs(int mountId, int start, int size, String[] hashs) {

        DebugFlag.logInfo(TAG, "====== getFileListByHashs\n");

        String returnString = YKHttpEngine.getInstance()
                .getFileListByHashs(mountId, start, size, hashs);

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 文件上传
     */
    private static void addFile(int mountId, String fullpath, String filehash, long filesize, long createDateline, String dialogId) {

        DebugFlag.logInfo(TAG, "====== addFile\n");

        String returnString = YKHttpEngine.getInstance()
                .addFile(mountId, fullpath, filehash, filesize, createDateline, dialogId);

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }
}

