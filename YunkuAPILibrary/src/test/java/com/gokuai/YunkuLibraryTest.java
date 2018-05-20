package com.gokuai;

import com.gokuai.base.HttpEngine;
import com.gokuai.base.ReturnResult;
import com.gokuai.cloud.ConfigHelper;
import com.gokuai.cloud.transinterface.YKHttpEngine;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by qp on 2017/4/27.
 */
public class YunkuLibraryTest {

    public static final String CLIENT_ID = "";
    public static final String CLIENT_SECRET = "";

    public static final String USERNAME = "";
    public static final String PASSWORD = "";

    static {
        new ConfigHelper(CLIENT_ID,CLIENT_SECRET).config();
        YKHttpEngine.getInstance().login(USERNAME, PASSWORD);
    }

    @Test
    public void getLibInfoByMountId() throws Exception {
        ReturnResult r = YKHttpEngine.getInstance().getLibraryInfoByMountId(1221861);
        Assert.assertEquals(200, r.getCode());
    }

    @Test
    public void getLibInfoByOrgId() throws Exception {
        ReturnResult r = YKHttpEngine.getInstance().getLibraryInfoByOrgId(1221858);
        Assert.assertEquals(200, r.getCode());
    }

    @Ignore("deleteLib is ignored")
    @Test
    public void deleteLib() throws Exception {
        ReturnResult r = YKHttpEngine.getInstance().deleteLibrary(0);
        Assert.assertEquals(200, r.getCode());
    }



    @Ignore("quitLib is ignored")
    @Test
    public void quitLib() throws Exception {
        ReturnResult r = YKHttpEngine.getInstance().quitLibrary(1);
        Assert.assertEquals(200, r.getCode());
    }
}
