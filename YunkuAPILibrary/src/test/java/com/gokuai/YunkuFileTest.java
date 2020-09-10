package com.gokuai;

import com.gokuai.base.HttpEngine;
import com.gokuai.base.ReturnResult;
import com.gokuai.cloud.ConfigHelper;
import com.gokuai.cloud.data.FileInfo;
import com.gokuai.cloud.data.YunkuException;
import com.gokuai.cloud.transinterface.YKHttpEngine;
import com.gokuai.library.net.UploadCallback;
import com.gokuai.library.net.UploadManager;
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
        new ConfigHelper(CLIENT_ID, CLIENT_SECRET).config();
        YKHttpEngine.getInstance().login(USERNAME, PASSWORD);
    }

    @Test
    public void lock() {
        ReturnResult result = YKHttpEngine.getInstance().fileLock(1221861, "v2.png", true);
        Assert.assertEquals(200, result.getCode());
    }

    @Test
    public void getFileUpdates() throws Exception {
        ReturnResult result = YKHttpEngine.getInstance().getFileUpdates(1221861, 0, 0);
        Assert.assertEquals(200, result.getCode());
    }

    @Test
    public void getFileLink() throws Exception {
        ReturnResult result = YKHttpEngine.getInstance().getFileLink(1221861, "v2.png", "", "",
                "", "", "");
        Assert.assertEquals(200, result.getCode());
    }

    @Test
    public void getLibraryFiles() throws Exception {
        ReturnResult result = YKHttpEngine.getInstance().getLibraryFiles(1221861, "v2.png", 0, 0);
        Assert.assertEquals(200, result.getCode());
    }

    @Test
    public void getFilesByHashs() throws Exception {
        ArrayList<String> hashs = new ArrayList<>();
        hashs.add("8ac70c619d8051c9fe9ec474b691740413851f63");
        ReturnResult result = YKHttpEngine.getInstance().getFilesByHashs(1221861, hashs);
        Assert.assertEquals(200, result.getCode());
    }

    @Test
    public void filesDelete() throws Exception {
        ArrayList<String> fullPaths = new ArrayList<>();
        fullPaths.add("test.jpg");
        ReturnResult result = YKHttpEngine.getInstance().filesDelete(1221861, fullPaths);
        Assert.assertEquals(200, result.getCode());
    }

    @Ignore("fileBatchCopy is ignored")
    @Test
    public void filesCopy() throws Exception {
        ArrayList<String> fullPaths = new ArrayList<>();
        fullPaths.add("test.jpg");
        ReturnResult result = YKHttpEngine.getInstance().filesCopy(1221861, fullPaths, 1221862, "test.jpg");
        Assert.assertEquals(200, result.getCode());
    }

    @Test
    public void filesMove() throws Exception {
        ArrayList<String> fullPaths = new ArrayList<>();
        fullPaths.add("test.jpg");
        ReturnResult result = YKHttpEngine.getInstance().filesMove(1221861, fullPaths, 1221861, "test.jpg");
        Assert.assertEquals(200, result.getCode());
    }

    @Test
    public void getFileUrlByFileHash() {
        ReturnResult result = YKHttpEngine.getInstance().getFileUrlByFileHash(1221861, "913b52ae320f26b08a2b0108f1ca053f71cde781", false);
        Assert.assertEquals(200, result.getCode());
    }

    @Test
    public void getFileInfoByFullpath() throws Exception {
        ReturnResult result = YKHttpEngine.getInstance().getFileInfoByFullpath(1221861, "v2.png", null);
        Assert.assertEquals(200, result.getCode());
    }

    @Test
    public void getFileInfoByHash() throws Exception {
        ReturnResult result = YKHttpEngine.getInstance().getFileInfoByHash(1221861, "8ac70c619d8051c9fe9ec474b691740413851f63", null);
        Assert.assertEquals(200, result.getCode());
    }

    @Test
    public void getFolderAttribute() throws Exception {
        ReturnResult result = YKHttpEngine.getInstance().getFolderAttribute(1221861, "Folder Test", null);
        Assert.assertEquals(200, result.getCode());
    }

    @Test
    public void getDownloadFileUrlByPath() throws Exception {
        ReturnResult result = YKHttpEngine.getInstance().getFileUrlByFullpath(1221861, "v2.png", false, null);
        Assert.assertEquals(200, result.getCode());
    }

    @Test
    public void fileExistByHash() throws Exception {
        ReturnResult result = YKHttpEngine.getInstance().fileExistsByHash(1221861, "8ac70c619d8051c9fe9ec474b691740413851f63");
        Assert.assertEquals(200, result.getCode());
    }

    @Test
    public void fileExistByFullPath() throws Exception {
        ReturnResult result = YKHttpEngine.getInstance().fileExistsByFullPath(1221861, "v2.png");
        Assert.assertEquals(200, result.getCode());
    }

    @Test
    public void createFolder() throws Exception {
        ReturnResult result = YKHttpEngine.getInstance().createFolder(1221861, "demo/test");
        Assert.assertEquals(200, result.getCode());
    }

    @Ignore("addFile is ignored")
    @Test
    public void addFile() throws Exception {
        ReturnResult result = YKHttpEngine.getInstance().addFile(1221861, "v3.png", "913b52ae320f26b08a2b0108f1ca053f71cde781", 7376, true);
        Assert.assertEquals(200, result.getCode());
    }

    @Test
    public void getHistory() throws Exception {
        ReturnResult result = YKHttpEngine.getInstance().getFileHistory(1221861, "v2.png");
        Assert.assertEquals(200, result.getCode());
    }

    @Test
    public void revert() throws Exception {
        ReturnResult result = YKHttpEngine.getInstance().fileRevert(1221861, "v2.png", "");
        Assert.assertEquals(200, result.getCode());
    }

    @Test
    public void fileSearch() {
        ReturnResult result = YKHttpEngine.getInstance().fileSearch(1221861, "v2");
        Assert.assertEquals(200, result.getCode());
    }

    @Test
    public void addFileRemark() {
        ReturnResult result = YKHttpEngine.getInstance().addFileRemark(1221861, "v2.png", "test1");
        Assert.assertEquals(200, result.getCode());
    }

    @Test
    public void getFileRemarkList() {
        ReturnResult result = YKHttpEngine.getInstance().getFileRemarks(1221861, "v2.png", 0, 0);
        Assert.assertEquals(200, result.getCode());
    }

    @Test
    public void setFileKeyWord() {
        ReturnResult result = YKHttpEngine.getInstance().setFileTag(1221861, "v2.png", "test2");
        Assert.assertEquals(200, result.getCode());
    }

//    @Ignore("uploadByBlock is ignored")
    @Test
    public void uploadByBlock() throws YunkuException {
        try {
            String fullpath = "testRangSize.png";
            FileInfo file = YKHttpEngine.getInstance().uploadByBlock(TEST_FILE_PATH, 1221861, fullpath, true);
            Assert.assertEquals(fullpath, file.fullpath);
        } catch (YunkuException e) {
            Assert.fail();
            e.printStackTrace();

            ReturnResult result = e.getReturnResult();
            if (result != null) {
                if (result.getException() != null) {
                    //出现网络或IO错误
                    result.getException().printStackTrace();
                } else {
                    //如果API接口返回异常, 获取最后一次API请求的结果
                    System.out.println("http response code: " + result.getCode() + ", body: " + result.getBody());
                }
            }
        }
    }

    @Test
    public void uploadByBlockAsync() {
        final CountDownLatch latch = new CountDownLatch(1);
        final String fullpath = "testRangSize.png";
        UploadManager manager = YKHttpEngine.getInstance().uploadByBlockAsync(TEST_FILE_PATH, 1221861, fullpath, true, new UploadCallback() {
            @Override
            public void onSuccess(String fullpath, FileInfo file) {
                latch.countDown();
                Assert.assertEquals(fullpath, file.fullpath);
                System.out.println("success: " + file.fullpath);
            }

            @Override
            public void onFail(String fullpath, YunkuException e) {
                Assert.fail();
                System.out.println("upload fail");
                e.printStackTrace();

                ReturnResult result = e.getReturnResult();
                if (result != null) {
                    if (result.getException() != null) {
                        //出现网络或IO错误
                        result.getException().printStackTrace();
                    } else {
                        //如果API接口返回异常, 获取最后一次API请求的结果
                        System.out.println("http response code: " + result.getCode() + ", body: " + result.getBody());
                    }
                }
            }

            @Override
            public void onProgress(String fullpath, float percent) {
                System.out.println(fullpath + ": "  + percent * 100 + "%");
            }
        });
    }

    @Ignore("fileSave is ignored")
    @Test
    public void fileSave() {
        ReturnResult result = YKHttpEngine.getInstance().fileSave(0, "", "", 0, 0, "");
        Assert.assertEquals(200, result.getCode());
    }

    @Test
    public void renameFile() {
        ReturnResult result = YKHttpEngine.getInstance().renameFile("v2.png", 1221861, "v2.png");
        Assert.assertEquals(200, result.getCode());
    }
}
