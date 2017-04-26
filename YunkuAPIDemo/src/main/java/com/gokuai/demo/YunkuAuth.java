package com.gokuai.demo;

import com.gokuai.base.HttpEngine;
import com.gokuai.base.LogPrint;
import com.gokuai.base.ReturnResult;
import com.gokuai.cloud.ConfigHelper;
import com.gokuai.cloud.transinterface.YKHttpEngine;
import com.gokuai.demo.helper.ClientConfig;
import com.gokuai.demo.helper.DeserializeHelper;

/**
 * Created by Brandon on 2016/10/12.
 * <p>
 * 授权认证 Demo示例
 */
public class YunkuAuth {

    private static final String TAG = "YunkuAuth";

    static {
        new ConfigHelper().client(ClientConfig.CLIENT_ID,ClientConfig.CLIENT_SECRET).config();
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

        LogPrint.info(TAG, "====== loginByAccount\n");
        //账号密码登录
        String returnString = YKHttpEngine.getInstance().loginSync("test", "test");
        DeserializeHelper.getInstance().deserializeResult(returnString);

    }

    /**
     * 账号密码登录(异步)
     */
    private static void loginByAccountAsync() {

        LogPrint.info(TAG, "====== loginByAccountAsync\n");

        YKHttpEngine.getInstance().loginAsync("test", "test", new HttpEngine.DataListener() {
            @Override
            public void onReceivedData(int apiId, Object object, int errorId) {
                if (apiId == YKHttpEngine.API_ID_LOGIN) {
                    ReturnResult returnResult = ReturnResult.create(object.toString());
                    if (returnResult != null) {
                        LogPrint.info(TAG, "onReceivedData => code" + returnResult.getStatusCode()
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

        LogPrint.info(TAG, "====== refreshToken\n");

        boolean success = YKHttpEngine.getInstance().refreshToken();

        System.out.println("refreshtoken:success" + success);

    }


}
