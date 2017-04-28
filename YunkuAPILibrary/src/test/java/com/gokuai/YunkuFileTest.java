package com.gokuai;

import com.gokuai.base.HttpEngine;
import com.gokuai.base.ReturnResult;
import com.gokuai.cloud.ConfigHelper;
import com.gokuai.cloud.transinterface.YKHttpEngine;
import com.gokuai.library.net.UploadCallBack;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * Created by qp on 2017/4/27.
 */
public class YunkuFileTest {

    // FIXME: YunkuFile

    public static final String CLIENT_ID = "";
    public static final String CLIENT_SECRET = "";

    public static final String USERNAME = "";
    public static final String PASSWORD = "";

    public static String TEST_FILE_PATH = "YunkuAPILibrary/testData/test.jpg";

    static {
        new ConfigHelper().client(CLIENT_ID,CLIENT_SECRET).config();
        YKHttpEngine.getInstance().loginSync(USERNAME, PASSWORD);
    }

    @Test
    public void lock() throws Exception {
        String s = YKHttpEngine.getInstance().lock("v2.png", 1221861, 0);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void getFileUpdates() throws Exception {
        String s = YKHttpEngine.getInstance().getFileUpdates(1221861, 0, 0);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void getFileLink() throws Exception {
        String s = YKHttpEngine.getInstance().getFileLink("v2.png", 1221861, "", "",
                "","","");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void getFavoriteInfo() throws Exception {
        String s = YKHttpEngine.getInstance().getFavoriteInfo(0);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void addFavorities() throws Exception {
        String s = YKHttpEngine.getInstance().addFavorities(1221861,"v2.png",0);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void delFavorities() throws Exception {
        String s = YKHttpEngine.getInstance().delFavorities(1221861, "v2.png", 0);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void clearFavorities() throws Exception {
        String s = YKHttpEngine.getInstance().clearFavorities();
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void changeFavoritesName() throws Exception {
        String s = YKHttpEngine.getInstance().changeFavoritesName(0, "test");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void getFavoritesName() throws Exception {
        String s = YKHttpEngine.getInstance().getFavoritesName();
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void getFileRecentModified() throws Exception {
        String s = YKHttpEngine.getInstance().getFileRecentModified();
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void getFileLocked() throws Exception {
        String s = YKHttpEngine.getInstance().getFileLocked();
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void getPermissionOfList() throws Exception {
        String s = YKHttpEngine.getInstance().getPermissionOfList(1221861, "v2.png");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void getFileListSync() throws Exception {
        String s = YKHttpEngine.getInstance().getFileListSync(1221861, "v2.png", 0, 0);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void getFileListByHashs() throws Exception {
        String s = YKHttpEngine.getInstance().getFileListByHashs(1221861, 0, 0, new String[]{"8ac70c619d8051c9fe9ec474b691740413851f63\n"});
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Ignore("uploadFile is ignored")
    @Test
    public void uploadFile() throws Exception {
        String s = YKHttpEngine.getInstance().uploadFile(1221861,"test.jpg","qp", TEST_FILE_PATH,false);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void batchDeleteFileAsync() throws Exception {
        ArrayList<String> fullPaths = new ArrayList<>();
        fullPaths.add("test.jpg");
        String s = YKHttpEngine.getInstance().batchDeleteFileAsync(1221861,fullPaths);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Ignore("fileBatchCopy is ignored")
    @Test
    public void fileBatchCopy() throws Exception {
        ArrayList<String> fullPaths = new ArrayList<>();
        fullPaths.add("test.jpg");
        String s = YKHttpEngine.getInstance().fileBatchCopy(1221861,fullPaths,1221862,"test.jpg");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void fileBatchMove() throws Exception {
        ArrayList<String> fullPaths = new ArrayList<>();
        fullPaths.add("test.jpg");
        String s = YKHttpEngine.getInstance().fileBatchMove(1221861,fullPaths,1221861,"test.jpg");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void getDownloadFileUrlByFileHash() throws Exception {
        String s = YKHttpEngine.getInstance().getDownloadFileUrlByFileHash(1221861,"913b52ae320f26b08a2b0108f1ca053f71cde781");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void getUrlByFileHash() throws Exception {
        YKHttpEngine.getInstance().getUrlByFileHash(1221861,"913b52ae320f26b08a2b0108f1ca053f71cde781", "", false, new HttpEngine.DataListener() {
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
    public void setFilePermission() throws Exception {
        String s = YKHttpEngine.getInstance().setFilePermission(1221861,"v2.png", "",false);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void getMemberFolderPermission() throws Exception {
        String s = YKHttpEngine.getInstance().getMemberFolderPermission(1221861,"v2.png", 0,0);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void getGroupFolderPermission() throws Exception {
        String s = YKHttpEngine.getInstance().getGroupFolderPermission(1221861,"v2.png", 0,0);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void getFileInfoSync() throws Exception {
        String s = YKHttpEngine.getInstance().getFileInfoSync("v2.png",1221861, "");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void getFileInfoByHash() throws Exception {
        String s = YKHttpEngine.getInstance().getFileInfoByHash("8ac70c619d8051c9fe9ec474b691740413851f63",1221861);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void getFolderAttribute() throws Exception {
        String s = YKHttpEngine.getInstance().getFolderAttribute(1221861, "Folder Test", "");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void getDownloadFileUrlByPath() throws Exception {
        String s = YKHttpEngine.getInstance().getDownloadFileUrlByPath(1221861,"v2.png","");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void fileExistByHash() throws Exception {
        String s = YKHttpEngine.getInstance().fileExistByHash("8ac70c619d8051c9fe9ec474b691740413851f63",1221861);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void fileExistByFullPath() throws Exception {
        String s = YKHttpEngine.getInstance().fileExistByFullPath("v2.png",1221861);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void createFolder() throws Exception {
        String s = YKHttpEngine.getInstance().createFolder(1221861,"test for YunkuFileTest");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Ignore("addFile is ignored")
    @Test
    public void addFile() throws Exception {
        String s = YKHttpEngine.getInstance().addFile(1221861,"v3.png","913b52ae320f26b08a2b0108f1ca053f71cde781",7376);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void getHistory() throws Exception {
        String s = YKHttpEngine.getInstance().getHistory(1221861,"v2.png");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void revert() throws Exception {
        String s = YKHttpEngine.getInstance().revert("v2.png",1221861,"");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void fileSearch() throws Exception {
        String s = YKHttpEngine.getInstance().fileSearch("v2",1221861);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void addFileRemark() throws Exception {
        String s = YKHttpEngine.getInstance().addFileRemark(1221861,"v2.png","test1");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void getFileRemarkList() throws Exception {
        String s = YKHttpEngine.getInstance().getFileRemarkList(1221861,"v2.png",0,0);
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void createOffline() throws Exception {
        YKHttpEngine.getInstance().getUrlByFileHash(1221861,"9187d349dfeaa17059f9606b7864614b2df5af31", "", false, new HttpEngine.DataListener() {
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
    public void setFileKeyWord() throws Exception {
        String s = YKHttpEngine.getInstance().setFileKeyWord(1221861,"v2.png","test2");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Ignore("uploadByBlock is ignored")
    @Test
    public void uploadByBlock() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Assert.assertEquals(true,new File(TEST_FILE_PATH).exists());
        YKHttpEngine.getInstance().uploadByBlock(1221861, "v3.png", TEST_FILE_PATH, new UploadCallBack() {
            @Override
            public void onSuccess(long threadId, String fileHash) {
                latch.countDown();
                Assert.assertEquals(1,threadId);
                System.out.println("success:" + threadId);
            }

            @Override
            public void onFail(long threadId, String errorMsg) {
                Assert.fail();
                System.out.println("fail:" + threadId + " errorMsg:" + errorMsg);
            }

            @Override
            public void onProgress(long threadId, float percent) {
                System.out.println("onProgress:" + threadId + " onProgress:" + percent * 100);
            }
        });
        latch.await();
    }

    @Ignore("fileSave is ignored")
    @Test
    public void fileSave() throws Exception {
        String s = YKHttpEngine.getInstance().fileSave(0, "", "",0,0,"");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void renameFile() throws Exception {
        String s = YKHttpEngine.getInstance().renameFile( "v2.png",1221861, "v2.png");
        ReturnResult r = ReturnResult.create(s);
        Assert.assertEquals(200, r.getStatusCode());
    }
}
