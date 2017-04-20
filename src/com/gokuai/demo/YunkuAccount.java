package com.gokuai.demo;

import com.gokuai.cloud.ConfigHelper;
import com.gokuai.cloud.transinterface.YKHttpEngine;
import com.gokuai.demo.helper.DeserializeHelper;
import com.gokuai.demo.helper.YunkuAuthHelper;
import com.gokuai.library.util.DebugFlag;

/**
 * Created by Brandon on 2016/10/12.
 * <p>
 * 帐号API 获取Demo示例
 */
public class YunkuAccount {

    private static final String TAG = "YunkuAccount";

    static {
        new ConfigHelper().client("","").config();
    }

    public static void main(String[] args) {

        YunkuAuthHelper.getInstance().loginByAccount();

//        YunkuAuthHelper.getInstance().loginByExchangeToken("b31db8bf415544e7acab5d772934949e");

//        getMountList();

//        getEntList();

        getAccountInfo();


    }

    /**
     * 获取库列表
     */
    static void getMountList() {

        DebugFlag.logInfo(TAG, "======getMountList\n");

        String returnString = YKHttpEngine.getInstance().getMountsInfo();

        DeserializeHelper.getInstance().deserializeResult(returnString);

    }

    /**
     * 获取企业列表
     */
    static void getEntList() {
        DebugFlag.logInfo(TAG, "======getEntList\n");

        String returnString = YKHttpEngine.getInstance().getEntInfo();

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 获取帐号信息
     */
    static void getAccountInfo() {
        DebugFlag.logInfo(TAG, "======getAccountInfo\n");

        String returnString = YKHttpEngine.getInstance().getAccountInfo();

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }
}
