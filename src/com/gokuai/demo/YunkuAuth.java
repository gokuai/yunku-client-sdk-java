package com.gokuai.demo;

import com.gokuai.cloud.ConfigHelper;
import com.gokuai.cloud.transinterface.YKHttpEngine;
import com.gokuai.demo.helper.DeserializeHelper;
import com.gokuai.library.HttpEngine;
import com.gokuai.library.data.ReturnResult;
import com.gokuai.library.util.DebugFlag;

/**
 * Created by Brandon on 2016/10/12.
 * <p>
 * 授权认证 Demo示例
 */
public class YunkuAuth {

    private static final String TAG = "YunkuAuth";

    static {
        ConfigHelper.init();
    }

    public static void main(String[] args) {

        loginByAccount();

        loginByAccountAsync();

        refreshToken();

    }

    /**
     * 账号密码登录
     */
    private static void loginByAccount() {

        DebugFlag.logInfo(TAG, "====== loginByAccount\n");
        //账号密码登录
        String returnString = YKHttpEngine.getInstance().loginSync("test", "test");
        DeserializeHelper.getInstance().deserializeResult(returnString);

    }

    /**
     * 账号密码登录(异步)
     */
    private static void loginByAccountAsync() {

        DebugFlag.logInfo(TAG, "====== loginByAccountAsync\n");

        YKHttpEngine.getInstance().loginAsync("test", "test", new HttpEngine.DataListener() {
            @Override
            public void onReceivedData(int apiId, Object object, int errorId) {
                if (apiId == YKHttpEngine.API_ID_LOGIN) {
                    ReturnResult returnResult = ReturnResult.create(object.toString());
                    if (returnResult != null) {
                        DebugFlag.logInfo(TAG, "onReceivedData => code" + returnResult.getStatusCode()
                                + "，result：" + returnResult.getResult());
                    }
                }

            }
        });
    }


    /**
     * 刷新token
     */
    private static void refreshToken() {

        DebugFlag.logInfo(TAG, "====== refreshToken\n");

        boolean success = YKHttpEngine.getInstance().refreshToken();

        System.out.println("refreshtoken:success" + success);

    }


}
