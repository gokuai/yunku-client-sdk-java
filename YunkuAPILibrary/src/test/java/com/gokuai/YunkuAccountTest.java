package com.gokuai;

import com.gokuai.base.ReturnResult;
import com.gokuai.cloud.ConfigHelper;
import com.gokuai.cloud.transinterface.YKHttpEngine;
import org.junit.Assert;
import org.junit.Test;

public class YunkuAccountTest {

    public static final String CLIENT_ID = "";
    public static final String CLIENT_SECRET = "";

    public static final String USERNAME = "";
    public static final String PASSWORD = "";

    static {
        new ConfigHelper(CLIENT_ID, CLIENT_SECRET).config();
        YKHttpEngine.getInstance().login(USERNAME, PASSWORD);
    }

    @Test
    public void getAccountInfo() {
        ReturnResult result = YKHttpEngine.getInstance().getAccountInfo();
        Assert.assertEquals(200, result.getCode());
    }

    @Test
    public void getEntInfo() {
        ReturnResult result = YKHttpEngine.getInstance().getEnts();
        Assert.assertEquals(200, result.getCode());
    }

    @Test
    public void getLibraries() {
        ReturnResult r = YKHttpEngine.getInstance().getLibraries();
        Assert.assertEquals(200, r.getCode());
    }

    @Test
    public void getServers() {
        ReturnResult result = YKHttpEngine.getInstance().getServers("");
        Assert.assertEquals(200, result.getCode());
    }

    @Test
    public void getAccountSetting() {
        ReturnResult result = YKHttpEngine.getInstance().getAccountSetting();
        Assert.assertEquals(200, result.getCode());
    }

    @Test
    public void getMemberInfo() {
        ReturnResult result = YKHttpEngine.getInstance().getMemberInfo(750, 71715);
        Assert.assertEquals(200, result.getCode());
    }

}
