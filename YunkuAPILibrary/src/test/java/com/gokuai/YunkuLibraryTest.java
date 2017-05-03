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

    // FIXME: YunkuLibrary

    public static final String CLIENT_ID = "";
    public static final String CLIENT_SECRET = "";

    public static final String USERNAME = "";
    public static final String PASSWORD = "";

    static {
        new ConfigHelper().client(CLIENT_ID,CLIENT_SECRET).config();
        YKHttpEngine.getInstance().loginSync(USERNAME, PASSWORD);
    }

    @Test
    public void getStoragePoint() throws Exception {
        YKHttpEngine.getInstance().getStoragePoint(new HttpEngine.DataListener() {
            @Override
            public void onReceivedData(int apiId, Object object, int errorId) {
                if (apiId == YKHttpEngine.API_ID_LOGIN) {
                    ReturnResult r = ReturnResult.create(object.toString());
                    Assert.assertEquals(200, r.getStatusCode());
                }
            }
        },0);
    }

    @Test
    public void getDefaultLibLogos() throws Exception {
        String s = YKHttpEngine.getInstance().getDefaultLibLogos();
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void getLibInfoByMountId() throws Exception {
        String s = YKHttpEngine.getInstance().getLibInfoByMountId(1221861);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void getLibInfoByOrgId() throws Exception {
        String s = YKHttpEngine.getInstance().getLibInfoByOrgId(1221858);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void searchLibraryMembers() throws Exception {
        String s = YKHttpEngine.getInstance().searchLibraryMembers(1221858, 0, "", 0, false, false);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void getMemberRelative() throws Exception {
        String s = YKHttpEngine.getInstance().getMemberRelative(1221858, 0, false,false);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void getGroupRelative() throws Exception {
        String s = YKHttpEngine.getInstance().getGroupRelative(1221858, false);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Ignore("addLibraryGroup is ignored")
    @Test
    public void addLibraryGroup() throws Exception {
        String s = YKHttpEngine.getInstance().addLibraryGroup(71715, 1258751, 4448, 3208);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Ignore("updateLibraryGroup is ignored")
    @Test
    public void updateLibraryGroup() throws Exception {
        String s = YKHttpEngine.getInstance().updateLibraryGroup(705, 371523, 78724, 7880);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Ignore("updateLibraryGroup is ignored")
    @Test
    public void delLibraryGroup() throws Exception {
        String s = YKHttpEngine.getInstance().delLibraryGroup(705, 371523, 78724);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Ignore("updateLibraryGroup is ignored")
    @Test
    public void addLibraryMember() throws Exception {
        String s = YKHttpEngine.getInstance().addLibraryMember(371523, "2608", 3208);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    // FIXME : 注意事项 以下接口目前只支持企业库  返回的是新增成功的成员，已存在的不会返回

    @Ignore("changeLibraryMemberRole is ignored")
    @Test
    public void changeLibraryMemberRole() throws Exception {
        String s = YKHttpEngine.getInstance().changeLibraryMemberRole(0, "", 0);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Ignore("delLibraryMember is ignored")
    @Test
    public void delLibraryMember() throws Exception {
        String s = YKHttpEngine.getInstance().delLibraryMember(0, "");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Ignore("createLibrary is ignored")
    @Test
    public void createLibrary() throws Exception {
        String s = YKHttpEngine.getInstance().createLibrary("",0, "", "", "", -1);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Ignore("changeCLoudLogo is ignored")
    @Test
    public void changeCLoudLogo() throws Exception {
        YKHttpEngine.getInstance().changeCLoudLogo(new HttpEngine.DataListener() {
            @Override
            public void onReceivedData(int apiId, Object object, int errorId) {
                if (apiId == YKHttpEngine.API_ID_LOGIN) {
                    ReturnResult r = ReturnResult.create(object.toString());
                    Assert.assertEquals(200, r.getStatusCode());
                }
            }
        },0, "");
    }

    @Ignore("changeOrgName is ignored")
    @Test
    public void changeOrgName() throws Exception {
        YKHttpEngine.getInstance().changeOrgName(new HttpEngine.DataListener() {
            @Override
            public void onReceivedData(int apiId, Object object, int errorId) {
                if (apiId == YKHttpEngine.API_ID_LOGIN) {
                    ReturnResult r = ReturnResult.create(object.toString());
                    Assert.assertEquals(200, r.getStatusCode());
                }
            }
        },0, "");
    }

    @Ignore("changeCLoudDesc is ignored")
    @Test
    public void changeCLoudDesc() throws Exception {
        YKHttpEngine.getInstance().changeCLoudDesc(new HttpEngine.DataListener() {
            @Override
            public void onReceivedData(int apiId, Object object, int errorId) {
                if (apiId == YKHttpEngine.API_ID_LOGIN) {
                    ReturnResult r = ReturnResult.create(object.toString());
                    Assert.assertEquals(200, r.getStatusCode());
                }
            }
        },0, "");
    }

    @Ignore("changeCLoudSpace is ignored")
    @Test
    public void changeCLoudSpace() throws Exception {
        YKHttpEngine.getInstance().changeCLoudSpace(new HttpEngine.DataListener() {
            @Override
            public void onReceivedData(int apiId, Object object, int errorId) {
                if (apiId == YKHttpEngine.API_ID_LOGIN) {
                    ReturnResult r = ReturnResult.create(object.toString());
                    Assert.assertEquals(200, r.getStatusCode());
                }
            }
        },0, -1);
    }

    @Ignore("deleteLib is ignored")
    @Test
    public void deleteLib() throws Exception {
        YKHttpEngine.getInstance().deleteLib(new HttpEngine.DataListener() {
            @Override
            public void onReceivedData(int apiId, Object object, int errorId) {
                if (apiId == YKHttpEngine.API_ID_LOGIN) {
                    ReturnResult r = ReturnResult.create(object.toString());
                    Assert.assertEquals(200, r.getStatusCode());
                }
            }
        },0);
    }



    @Ignore("quitLib is ignored")
    @Test
    public void quitLib() throws Exception {
        YKHttpEngine.getInstance().quitLib(new HttpEngine.DataListener() {
            @Override
            public void onReceivedData(int apiId, Object object, int errorId) {
                if (apiId == YKHttpEngine.API_ID_LOGIN) {
                    ReturnResult r = ReturnResult.create(object.toString());
                    Assert.assertEquals(200, r.getStatusCode());
                }
            }
        },0);
    }
}
