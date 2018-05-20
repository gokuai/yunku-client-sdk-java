package com.gokuai.demo;

import com.gokuai.base.ReturnResult;
import com.gokuai.cloud.data.YunkuException;
import com.gokuai.cloud.transinterface.YKHttpEngine;
import com.gokuai.demo.helper.DeserializeHelper;

public class YunkuDemo {

    public static void main(String[] args) {

        try {
            YunkuAuth.auth();

            getEnts();

            getAccountInfo();

            getLibraries();

        } catch (YunkuException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取库列表
     */
    static void getLibraries() throws YunkuException {

        ReturnResult result = YKHttpEngine.getInstance().getLibraries();

        //debug
        DeserializeHelper.getInstance().deserializeResult(result);

        if (!result.isOK()) {
            throw new YunkuException("fail to get libraries", result);
        }

    }

    /**
     * 获取企业列表
     */
    static void getEnts() throws YunkuException {

        ReturnResult result = YKHttpEngine.getInstance().getEnts();

        //debug
        DeserializeHelper.getInstance().deserializeResult(result);

        if (!result.isOK()) {
            throw new YunkuException("fail to get ents", result);
        }
    }

    /**
     * 获取帐号信息
     */
    static void getAccountInfo() throws YunkuException {

        ReturnResult result = YKHttpEngine.getInstance().getAccountInfo();

        //debug
        DeserializeHelper.getInstance().deserializeResult(result);

        if (!result.isOK()) {
            throw new YunkuException("fail to get account info", result);
        }
    }
}
