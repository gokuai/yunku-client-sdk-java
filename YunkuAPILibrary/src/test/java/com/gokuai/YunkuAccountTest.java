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
public class YunkuAccountTest {

    // FIXME : YunkuAccount

    public static final String CLIENT_ID = "";
    public static final String CLIENT_SECRET = "";

    public static final String USERNAME = "";
    public static final String PASSWORD = "";

    static {
        new ConfigHelper().client(CLIENT_ID,CLIENT_SECRET).config();
        YKHttpEngine.getInstance().loginSync(USERNAME, PASSWORD);
    }

    @Test
    public void getAccountInfo() throws Exception {
        String s = YKHttpEngine.getInstance().getAccountInfo();
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void getAccountInfoAsync() throws Exception {
        YKHttpEngine.getInstance().getAccountInfoAsync(new HttpEngine.DataListener() {
            @Override
            public void onReceivedData(int apiId, Object object, int errorId) {
                if (apiId == YKHttpEngine.API_ID_LOGIN) {
                    ReturnResult r = ReturnResult.create(object.toString());
                    Assert.assertEquals(200, r.getStatusCode());
                }
            }
        });
    }

    @Ignore("registerAsync is ignored")
    @Test
    public void registerAsync() throws Exception {
        YKHttpEngine.getInstance().registerAsync(new HttpEngine.DataListener() {
            @Override
            public void onReceivedData(int apiId, Object object, int errorId) {
                if (apiId == YKHttpEngine.API_ID_LOGIN) {
                    ReturnResult r = ReturnResult.create(object.toString());
                    Assert.assertEquals(200, r.getStatusCode());
                }
            }
        }, "", "", "");
    }

    @Test
    public void findPassword() throws Exception {
        YKHttpEngine.getInstance().findPassword(new HttpEngine.DataListener() {
            @Override
            public void onReceivedData(int apiId, Object object, int errorId) {
                if (apiId == YKHttpEngine.API_ID_LOGIN) {
                    ReturnResult r = ReturnResult.create(object.toString());
                    Assert.assertEquals(200, r.getStatusCode());
                }
            }
            },"test.com");
    }

    @Test
    public void uploadUserName() throws Exception {
        YKHttpEngine.getInstance().uploadUserName(new HttpEngine.DataListener() {
            @Override
            public void onReceivedData(int apiId, Object object, int errorId) {
                if (apiId == YKHttpEngine.API_ID_LOGIN) {
                    ReturnResult r = ReturnResult.create(object.toString());
                    Assert.assertEquals(200, r.getStatusCode());
                }
            }
        },"test");
    }

    @Test
    public void uploadUserPhone() throws Exception {
        YKHttpEngine.getInstance().uploadUserPhone(new HttpEngine.DataListener() {
            @Override
            public void onReceivedData(int apiId, Object object, int errorId) {
                if (apiId == YKHttpEngine.API_ID_LOGIN) {
                    ReturnResult r = ReturnResult.create(object.toString());
                    Assert.assertEquals(200, r.getStatusCode());
                }
            }
        },"");
    }

    @Test
    public void getMountsInfo() throws Exception {
        String s = YKHttpEngine.getInstance().getMountsInfo();
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void changeDeviceState() throws Exception {
        YKHttpEngine.getInstance().changeDeviceState(new HttpEngine.DataListener() {
            @Override
            public void onReceivedData(int apiId, Object object, int errorId) {
                if (apiId == YKHttpEngine.API_ID_LOGIN) {
                    ReturnResult r = ReturnResult.create(object.toString());
                    Assert.assertEquals(200, r.getStatusCode());
                }
            }
        },0,0);
    }


    @Test
    public void setDeviceInfo() throws Exception {
        YKHttpEngine.getInstance().setDeviceInfo(new HttpEngine.DataListener() {
            @Override
            public void onReceivedData(int apiId, Object object, int errorId) {
                if (apiId == YKHttpEngine.API_ID_LOGIN) {
                    ReturnResult r = ReturnResult.create(object.toString());
                    Assert.assertEquals(200, r.getStatusCode());
                }
            }
        },"","");
    }

    @Ignore("delDevice is ignored")
    @Test
    public void delDevice() throws Exception {
        YKHttpEngine.getInstance().delDevice(new HttpEngine.DataListener() {
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
    public void changeNewDeviceState() throws Exception {
        YKHttpEngine.getInstance().changeNewDeviceState(new HttpEngine.DataListener() {
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
    public void getServerSite() throws Exception {
        String s = YKHttpEngine.getInstance().getServerSite("");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void getAccountSetting() throws Exception {
        String s = YKHttpEngine.getInstance().getAccountSetting();
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void getEntInfo() throws Exception {
        String s = YKHttpEngine.getInstance().getEntInfo();
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }
}
