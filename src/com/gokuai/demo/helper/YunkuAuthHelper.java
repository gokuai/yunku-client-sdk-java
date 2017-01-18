package com.gokuai.demo.helper;

import com.gokuai.cloud.YKConfig;
import com.gokuai.cloud.transinterface.YKHttpEngine;

/**
 * Created by Brandon on 2016/10/12.
 * <p>
 * 账号认证Demo示例
 */
public class YunkuAuthHelper {

    public static final String USERNAME = "";
    public static final String PASSWORD = "";


    private YunkuAuthHelper() {

    }

    private static class SingletonHolder {
        private static final YunkuAuthHelper INSTANCE = new YunkuAuthHelper();
    }

    public static YunkuAuthHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }


    /**
     * 账号密码登录
     */
    public void loginByAccount() {

        //账号密码登录
        String returnString = YKHttpEngine.getInstance().loginSync(USERNAME, PASSWORD);

        DeserializeHelper.getInstance().deserializeResult(returnString);

    }

    /**
     * 单点登录调用
     */
    public void loginByExchangeToken(String exchangeToken) {
        String returnString = YKHttpEngine.getInstance().exchangeToken(exchangeToken);
        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

}
