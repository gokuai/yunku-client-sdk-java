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
public class YunkuContactTest {

    // FIXME : YunkuContact

    public static final String CLIENT_ID = "";
    public static final String CLIENT_SECRET = "";

    public static final String USERNAME = "";
    public static final String PASSWORD = "";

    static {
        new ConfigHelper().client(CLIENT_ID,CLIENT_SECRET).config();
        YKHttpEngine.getInstance().loginSync(USERNAME, PASSWORD);
    }

    @Test
    public void getGroupFromGroup() throws Exception {
        String s = YKHttpEngine.getInstance().getGroupFromGroup(750, 71715);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void searchContactGroups() throws Exception {
        String s = YKHttpEngine.getInstance().searchContactGroups(750, "");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void getMemberFromGroup() throws Exception {
        String s = YKHttpEngine.getInstance().getMemberFromGroup(750, 71715, 0, 0,  "", 0);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void getMemberInfo() throws Exception {
        String s = YKHttpEngine.getInstance().getMemberInfo(750, 71715);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void getContactMemberGroups() throws Exception {
        YKHttpEngine.getInstance().getContactMemberGroups(750, 71715, new HttpEngine.DataListener() {
            @Override
            public void onReceivedData(int apiId, Object object, int errorId) {
                if (apiId == YKHttpEngine.API_ID_LOGIN) {
                    ReturnResult r = ReturnResult.create(object.toString());
                    Assert.assertEquals(200, r.getStatusCode());
                }
            }
        });
    }

    @Test
    public void addContactGroup() throws Exception {
        YKHttpEngine.getInstance().addContactGroup(750, 71715, "", new HttpEngine.DataListener() {
            @Override
            public void onReceivedData(int apiId, Object object, int errorId) {
                if (apiId == YKHttpEngine.API_ID_LOGIN) {
                    ReturnResult r = ReturnResult.create(object.toString());
                    Assert.assertEquals(200, r.getStatusCode());
                }
            }
        });
    }

    @Test
    public void changeContactGroupName() throws Exception {
        YKHttpEngine.getInstance().changeContactGroupName(750, 71715, "", new HttpEngine.DataListener() {
            @Override
            public void onReceivedData(int apiId, Object object, int errorId) {
                if (apiId == YKHttpEngine.API_ID_LOGIN) {
                    ReturnResult r = ReturnResult.create(object.toString());
                    Assert.assertEquals(200, r.getStatusCode());
                }
            }
        });
    }

    @Ignore("delContactGroup is ignored")
    @Test
    public void delContactGroup() throws Exception {
        YKHttpEngine.getInstance().delContactGroup(750, 71715, new HttpEngine.DataListener() {
            @Override
            public void onReceivedData(int apiId, Object object, int errorId) {
                if (apiId == YKHttpEngine.API_ID_LOGIN) {
                    ReturnResult r = ReturnResult.create(object.toString());
                    Assert.assertEquals(200, r.getStatusCode());
                }
            }
        });
    }

    @Test
    public void addContactMember() throws Exception {
        YKHttpEngine.getInstance().addContactMember(750,"","71715","","",
                "",0, 0, new HttpEngine.DataListener() {
            @Override
            public void onReceivedData(int apiId, Object object, int errorId) {
                if (apiId == YKHttpEngine.API_ID_LOGIN) {
                    ReturnResult r = ReturnResult.create(object.toString());
                    Assert.assertEquals(200, r.getStatusCode());
                }
            }
        });
    }

    @Test
    public void updateContactMember() throws Exception {
        YKHttpEngine.getInstance().updateContactMember(750,"","71715",0,
                0, "", new HttpEngine.DataListener() {
                    @Override
                    public void onReceivedData(int apiId, Object object, int errorId) {
                        if (apiId == YKHttpEngine.API_ID_LOGIN) {
                            ReturnResult r = ReturnResult.create(object.toString());
                            Assert.assertEquals(200, r.getStatusCode());
                        }
                    }
                });
    }

    @Test
    public void updateContactMemberState() throws Exception {
        YKHttpEngine.getInstance().updateContactMemberState(750,"71715",0, new HttpEngine.DataListener() {
                    @Override
                    public void onReceivedData(int apiId, Object object, int errorId) {
                        if (apiId == YKHttpEngine.API_ID_LOGIN) {
                            ReturnResult r = ReturnResult.create(object.toString());
                            Assert.assertEquals(200, r.getStatusCode());
                        }
                    }
                });
    }

    @Ignore("delContactMember is ignored")
    @Test
    public void delContactMember() throws Exception {
        YKHttpEngine.getInstance().delContactMember(0,"",0, new HttpEngine.DataListener() {
            @Override
            public void onReceivedData(int apiId, Object object, int errorId) {
                if (apiId == YKHttpEngine.API_ID_LOGIN) {
                    ReturnResult r = ReturnResult.create(object.toString());
                    Assert.assertEquals(200, r.getStatusCode());
                }
            }
        });
    }

    @Test
    public void getOrgMembersList() throws Exception {
        String s = YKHttpEngine.getInstance().getOrgMembersList(71715, 750, 0);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void getGroupTable() throws Exception {
        String s = YKHttpEngine.getInstance().getGroupTable(750, 0);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void getEntMembers() throws Exception {
        String s = YKHttpEngine.getInstance().getEntMembers(750, 0, "");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void getEntMembersByIds() throws Exception {
        String s = YKHttpEngine.getInstance().getEntMembersByIds(750, 0,"");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void searchEntMember() throws Exception {
        String s = YKHttpEngine.getInstance().searchEntMember(750, 0,"");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void searchContactMembers() throws Exception {
        YKHttpEngine.getInstance().searchContactMembers(750,0, "",0, new HttpEngine.DataListener() {
            @Override
            public void onReceivedData(int apiId, Object object, int errorId) {
                if (apiId == YKHttpEngine.API_ID_LOGIN) {
                    ReturnResult r = ReturnResult.create(object.toString());
                    Assert.assertEquals(200, r.getStatusCode());
                }
            }
        });
    }

    @Test
    public void getMemberIdFromOutId() throws Exception {
        YKHttpEngine.getInstance().getMemberIdFromOutId(new int[]{0},750, new HttpEngine.DataListener() {
            @Override
            public void onReceivedData(int apiId, Object object, int errorId) {
                if (apiId == YKHttpEngine.API_ID_LOGIN) {
                    ReturnResult r = ReturnResult.create(object.toString());
                    Assert.assertEquals(200, r.getStatusCode());
                }
            }
        });
    }

    @Test
    public void checkExistMember() throws Exception {
        YKHttpEngine.getInstance().checkExistMember(750,"", new HttpEngine.DataListener() {
            @Override
            public void onReceivedData(int apiId, Object object, int errorId) {
                if (apiId == YKHttpEngine.API_ID_LOGIN) {
                    ReturnResult r = ReturnResult.create(object.toString());
                    Assert.assertEquals(200, r.getStatusCode());
                }
            }
        });
    }

    @Test
    public void addGroupMember() throws Exception {
        YKHttpEngine.getInstance().addGroupMember(750,71715, "", new HttpEngine.DataListener() {
            @Override
            public void onReceivedData(int apiId, Object object, int errorId) {
                if (apiId == YKHttpEngine.API_ID_LOGIN) {
                    ReturnResult r = ReturnResult.create(object.toString());
                    Assert.assertEquals(200, r.getStatusCode());
                }
            }
        });
    }

    @Ignore("removeGroupMember is ignored")
    @Test
    public void removeGroupMember() throws Exception {
        YKHttpEngine.getInstance().removeGroupMember(750,71715, "", new HttpEngine.DataListener() {
            @Override
            public void onReceivedData(int apiId, Object object, int errorId) {
                if (apiId == YKHttpEngine.API_ID_LOGIN) {
                    ReturnResult r = ReturnResult.create(object.toString());
                    Assert.assertEquals(200, r.getStatusCode());
                }
            }
        });
    }

    @Test
    public void getOutIdFromMemberIdSync() throws Exception {
        String s = YKHttpEngine.getInstance().getOutIdFromMemberIdSync(new int[]{0}, 750);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());

    }
}
