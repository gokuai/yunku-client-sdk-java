package com.gokuai.demo;

import com.gokuai.base.ReturnResult;
import com.gokuai.cloud.data.YunkuException;
import com.gokuai.cloud.transinterface.YKHttpEngine;
import com.gokuai.cloud.transinterface.YKUtil;
import org.json.JSONObject;

public class YunkuAuth {

    public static void main(String[] args) {
        try {
            auth();
        } catch (YunkuException e) {
            e.printStackTrace();
        }
    }

    //获取用户授权
    public static void auth() throws YunkuException {

        //注意, 必须初始化配置
        YunkuConfig.init();

        //***********************************************
        //帐号密码方式登录
        String username = "";
        String password = "";

        if (!YKUtil.isEmpty(username) && !YKUtil.isEmpty(password)) {
            loginByAccount(username, password);
            return;
        }
        //***********************************************



        //***********************************************
        //使用企业成员的帐号完成一站式登录
        String account = "michael";

        //企业管理后台开通的授权
        String entClientId = "";
        String entSecret = "";

        if (!YKUtil.isEmpty(account) && !YKUtil.isEmpty(entClientId) && !YKUtil.isEmpty(entSecret)) {
            ssoLogin(account, entClientId, entSecret);
            return;
        }
        //***********************************************


        throw new YunkuException("login params not set");
    }

    //帐号密码登录
    public static void loginByAccount(String username, String password) throws YunkuException {
        ReturnResult result = YKHttpEngine.getInstance().login(username, password);

        if (!result.isOK()) {
            throw new YunkuException("fail to login", result);
        }
    }

    //一站式登录
    public static void ssoLogin(String account, String entClientId, String entSecret) throws YunkuException {
        //sso登录, 获取key
        ReturnResult result = YKHttpEngine.getInstance().ssoLogin(account, entClientId, entSecret);

        if (!result.isOK()) {
            throw new YunkuException("fail to sso", result);
        }

        JSONObject json = new JSONObject(result.getBody());
        String key = json.optString("gkkey");

        //使用gkkey登录
        result = YKHttpEngine.getInstance().loginByKey(key);

        if (!result.isOK()) {
            throw new YunkuException("fail to login by key", result);
        }
    }
}
