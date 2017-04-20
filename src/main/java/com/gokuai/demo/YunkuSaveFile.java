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

        loginBySso();

        getMountList();

        getFileList();

        getFileListByHashs();

        addFile();
    }

    /**
     * sso登录
     */
    private static void loginBySso() {

        String returnString = YKHttpEngine.getInstance().ssoLogin("[account]", "[clientId]", "[clientSecret]");
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
    private static void getFileList() {

        DebugFlag.logInfo(TAG, "====== getFileList\n");

        String returnString = YKHttpEngine.getInstance().getFileListSync(251025, "", 0, 500);

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 根据指定文件hash获取列表
     */
    private static void getFileListByHashs() {

        DebugFlag.logInfo(TAG, "====== getFileListByHashs\n");

        String returnString = YKHttpEngine.getInstance()
                .getFileListByHashs(251025, 0, 500, new String[]{"b24cae3429409c20cd20c80aa1c42e04f5aa8287"});

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 文件上传
     */
    private static void addFile() {

        DebugFlag.logInfo(TAG, "====== addFile\n");

        String returnString = YKHttpEngine.getInstance()
                .addFile(0,"","",100,111,"");

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }
}

