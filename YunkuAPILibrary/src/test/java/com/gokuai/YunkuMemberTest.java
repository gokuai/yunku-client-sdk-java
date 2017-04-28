package com.gokuai;

import com.gokuai.base.HttpEngine;
import com.gokuai.base.ReturnResult;
import com.gokuai.cloud.ConfigHelper;
import com.gokuai.cloud.transinterface.YKHttpEngine;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by qp on 2017/4/27.
 */
public class YunkuMemberTest {

    // FIXME: YunkuMember

    public static final String CLIENT_ID = "";
    public static final String CLIENT_SECRET = "";

    public static final String USERNAME = "";
    public static final String PASSWORD = "";

    static {
        new ConfigHelper().client(CLIENT_ID,CLIENT_SECRET).config();
        YKHttpEngine.getInstance().loginSync(USERNAME, PASSWORD);
    }

    @Test
    public void getLastVisitFile() throws Exception {
        YKHttpEngine.getInstance().getLastVisitFile(new HttpEngine.DataListener() {
            @Override
            public void onReceivedData(int apiId, Object object, int errorId) {
                if (apiId == YKHttpEngine.API_ID_LOGIN) {
                    ReturnResult r = ReturnResult.create(object.toString());
                    Assert.assertEquals(200, r.getStatusCode());
                }
            }
        }, 0);
    }

    @Test
    public void addShortCuts() throws Exception {
        YKHttpEngine.getInstance().addShortCuts(new HttpEngine.DataListener() {
            @Override
            public void onReceivedData(int apiId, Object object, int errorId) {
                if (apiId == YKHttpEngine.API_ID_LOGIN) {
                    ReturnResult r = ReturnResult.create(object.toString());
                    Assert.assertEquals(200, r.getStatusCode());
                }
            }
        }, 0, 0);
    }

    @Test
    public void getShortCuts() throws Exception {
        String s = YKHttpEngine.getInstance().getShortCuts();
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void delShortCuts() throws Exception {
        YKHttpEngine.getInstance().delShortCuts(new HttpEngine.DataListener() {
            @Override
            public void onReceivedData(int apiId, Object object, int errorId) {
                if (apiId == YKHttpEngine.API_ID_LOGIN) {
                    ReturnResult r = ReturnResult.create(object.toString());
                    Assert.assertEquals(200, r.getStatusCode());
                }
            }
        }, 0, 0);
    }
}
