package com.gokuai.demo;

import com.gokuai.base.LogPrint;
import com.gokuai.cloud.ConfigHelper;
import com.gokuai.cloud.Constants;
import com.gokuai.cloud.transinterface.YKHttpEngine;
import com.gokuai.demo.helper.ClientConfig;
import com.gokuai.demo.helper.DeserializeHelper;
import com.gokuai.demo.helper.YunkuAuthHelper;
import com.gokuai.library.net.UploadCallBack;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Brandon on 2016/10/12.
 * <p>
 * 文件API Demo
 */
public class YunkuFile {

    private static final String TAG = "YunkuFile";

    static {
        new ConfigHelper().client(ClientConfig.CLIENT_ID,ClientConfig.CLIENT_SECRET).config();

    }

    public static void main(String[] args) {

        YunkuAuthHelper.getInstance().loginByAccount();

        //========文件API

//        lockFile();

//        getFileUpdates();

//        setFileKeyWord();


//        getFavorInfo();

//        addFavor();

//        deleteFavor();

//        clearFavor();

//        getFileRecentModified();

//        getFilesLocked();

//        getFileList();

//        getFileListByHashs();

//        fileUploadMSMulti();

//        fileUploadByBlock();

//        fileUploadByStream();

//        fileDelete();

//        fileCopy();

//        fileMove();

//        fileRevert();

//        setFavorName();

//        getFavorName();

//        getUrlByFileHash();

//        setFilePermission();

//        getFileMemberPermission();

//        getFileGroupPermission();

        getFileInfo();

//        getFolderAttribute();

//        getFileOpen();

//        fileExist();

//        createFolder();

//        getFileHistory();

//        fileSearch();

//        addFileRemark();

//        getFileRemark();

//        fileRename();
    }

    /**
     * 锁定、解锁
     */
    static void lockFile() {
        LogPrint.info(TAG, "======lockFile\n");

        String returnString = YKHttpEngine.getInstance().lock("test.jpg", 1221861, 0);
        DeserializeHelper.getInstance().deserializeResult(returnString);

    }

    /**
     * 文件最近更新列表
     */
    static void getFileUpdates() {
        LogPrint.info(TAG, "====== getFileUpdates\n");

        String returnString = YKHttpEngine.getInstance().getFileUpdates(1221861, 0, 500);

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 设置文件描述
     */
    static void setFileKeyWord() {
        LogPrint.info(TAG, "====== setFileKeyWord\n");

        String returnString = YKHttpEngine.getInstance().setFileKeyWord(1221861, "test.jpg", "test 1");

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 获取收藏夹信息
     */
    static void getFavorInfo() {
        LogPrint.info(TAG, "====== getFavorInfo\n");

        String returnString = YKHttpEngine.getInstance().getFavoriteInfo(1);

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 添加收藏
     */
    static void addFavor() {
        LogPrint.info(TAG, "====== addFavor\n");

        String returnString = YKHttpEngine.getInstance().addFavorities(1221861, "test.jpg", 1);

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 删除收藏
     */
    static void deleteFavor() {
        LogPrint.info(TAG, "====== deleteFavor\n");

        String returnString = YKHttpEngine.getInstance().delFavorities(1221861, "test.jpg", 1);

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 清空收藏
     */
    static void clearFavor() {
        LogPrint.info(TAG, "====== clearFavor\n");

        String returnString = YKHttpEngine.getInstance().clearFavorities();

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 获取24小时内修改的文件
     */
    static void getFileRecentModified() {
        LogPrint.info(TAG, "====== getFileRecentModified\n");

        String returnString = YKHttpEngine.getInstance().getFileRecentModified();

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 获取锁定文件信息
     */
    static void getFilesLocked() {
        LogPrint.info(TAG, "====== getFilesLocked\n");

        String returnString = YKHttpEngine.getInstance().getFileLocked();

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }


    /**
     * 获取文件（夹）信息
     */
    static void getFileInfo() {
        LogPrint.info(TAG, "====== getFileInfo\n");

        String returnString = YKHttpEngine.getInstance().getFileInfoSync("Folder Test", 1221861, "");

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 获取文件列表
     */
    static void getFileList() {
        LogPrint.info(TAG, "====== getFileList\n");

        String returnString = YKHttpEngine.getInstance().getFileListSync(1221861, "", 0, 500);

        DeserializeHelper.getInstance().deserializeResult(returnString);

    }

    /**
     * 根据指定文件hash获取列表
     */
    static void getFileListByHashs() {
        LogPrint.info(TAG, "====== getFileListByHashs\n");

        String returnString = YKHttpEngine.getInstance()
                .getFileListByHashs(1221861, 0, 500, new String[]{"bac8f214d6dc064a2b758aa0c1aae11f582dffd4"});

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 文件上传 （表单上传）
     */
    static void fileUploadMSMulti() {
        LogPrint.info(TAG, "====== fileUploadMSMulti\n");

        String returnString = YKHttpEngine.getInstance().uploadFile(1221861,
                "test.jpg", "Brandon", "YunkuAPIDemo/testData/test.jpg", false);

        DeserializeHelper.getInstance().deserializeResult(returnString);

    }

    /**
     * 文件上传 （分块上传）
     */
    static void fileUploadByBlock() {

        YKHttpEngine.getInstance().uploadByBlock(1221861, "fileUploadByBlock.png",
                "YunkuAPILibrary/testData/test.jpg", new UploadCallBack() {
                    @Override
                    public void onSuccess(long threadId, String fileHash) {
                        System.out.println("onSuccess threadId：" + threadId + ",fileHash：" + fileHash);
                    }

                    @Override
                    public void onFail(long threadId, String errorMsg) {
                        System.out.println("onFail threadId：" + threadId + ",errorMsg：" + errorMsg);

                    }

                    @Override
                    public void onProgress(long threadId, float percent) {
                        System.out.println("onProgress threadId：" + threadId + ",percent：" + percent);

                    }
                });

    }

    /**
     * 流文件上传 （分块上传）
     */
    static void fileUploadByStream(){

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File("YunkuAPILibrary/testData/test.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        YKHttpEngine.getInstance().uploadByBlock(1221861, "fileUploadByStream.jpg",
                inputStream, new UploadCallBack() {
                    @Override
                    public void onSuccess(long threadId, String fileHash) {
                        System.out.println("onSuccess threadId：" + threadId + ",fileHash：" + fileHash);
                    }

                    @Override
                    public void onFail(long threadId, String errorMsg) {
                        System.out.println("onFail threadId：" + threadId + ",errorMsg：" + errorMsg);

                    }

                    @Override
                    public void onProgress(long threadId, float percent) {
                        System.out.println("onProgress threadId：" + threadId + ",percent：" + percent);

                    }
                });

    }

    /**
     * 文件删除
     */
    static void fileDelete() {
        LogPrint.info(TAG, "====== fileDelete\n");

        ArrayList<String> fullPaths = new ArrayList<>();
        fullPaths.add("test.jpg");

        String returnString = YKHttpEngine.getInstance().batchDeleteFileAsync(1221861, fullPaths);

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 文件复制
     */
    static void fileCopy() {
        LogPrint.info(TAG, "====== fileCopy\n");

        ArrayList<String> fullPaths = new ArrayList<>();
        fullPaths.add("v2.png");

        String returnString = YKHttpEngine.getInstance().fileBatchCopy(1221861, fullPaths, 1221861, "");

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 文件移动
     */
    static void fileMove() {
        LogPrint.info(TAG, "====== fileMove\n");

        ArrayList<String> fullPaths = new ArrayList<>();
        fullPaths.add("v2.png");

        String returnString = YKHttpEngine.getInstance().fileBatchMove(1221861, fullPaths, 1221861, "");

        DeserializeHelper.getInstance().deserializeResult(returnString);

    }

    /**
     * 恢复已删除文件
     */
    static void fileRevert() {
        LogPrint.info(TAG, "====== fileRevert\n");

        String returnString = YKHttpEngine.getInstance().revert("v3.JPG", 1221861, "AVr6abVdUgJYxGotInU4");
        DeserializeHelper.getInstance().deserializeResult(returnString);

    }

    /**
     * 设置收藏夹名称
     */
    static void setFavorName() {
        LogPrint.info(TAG, "====== setFavorName\n");

        String returnString = YKHttpEngine.getInstance().changeFavoritesName(1, "test2");
        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 获取收藏夹名称
     */
    static void getFavorName() {
        LogPrint.info(TAG, "====== getFavorName\n");

        String returnString = YKHttpEngine.getInstance().getFavoritesName();
        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 获取文件下载链接
     */
    static void getUrlByFileHash() {
        LogPrint.info(TAG, "====== getUrlByFileHash\n");

        String returnString = YKHttpEngine.getInstance().getDownloadFileUrlByFileHash(1221861, "b5b7dd1ec6afd6d135f80f367068748d489e37a5");
        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 设置文件权限
     */
    static void setFilePermission() {

        LogPrint.info(TAG, "====== setFilePermission\n");

        ArrayList<String> permissionSetting = new ArrayList<>();
        permissionSetting.add(Constants.FILE_DELETE);
        permissionSetting.add(Constants.FILE_WRITE);

        //member
        HashMap<Integer, Object> map = new HashMap<>();
        map.put(2208, permissionSetting);

        String returnString = YKHttpEngine.getInstance().setFilePermission(1221861, "v2.png", new Gson().toJson(map), false);
        DeserializeHelper.getInstance().deserializeResult(returnString);

        //group
//        HashMap<Integer, Object> map = new HashMap<>();
//        map.put(78724, permissionSetting);
//        String returnString = YKHttpEngine.getInstance().setFilePermission(251025, "1111", new Gson().toJson(map), true);
//        DeserializeyHelpder.getInstance().deserializeResult(returnString);

    }

    /**
     * 获取文件成员权限
     */
    static void getFileMemberPermission() {
        LogPrint.info(TAG, "====== getFileMemberPermission\n");

        String returnString = YKHttpEngine.getInstance().getMemberFolderPermission(1221861, "1111", 0, 500);

        DeserializeHelper.getInstance().deserializeResult(returnString);


    }

    /**
     * 获取文件部门权限
     */
    static void getFileGroupPermission() {
        LogPrint.info(TAG, "====== getFileGroupPermission\n");

        String returnString = YKHttpEngine.getInstance().getGroupFolderPermission(1221861, "Folder Test", 0, 500);

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 获取文件夹属性
     */
    static void getFolderAttribute() {
        LogPrint.info(TAG, "====== getFolderAttribute\n");

        String returnString = YKHttpEngine.getInstance().getFolderAttribute(1221861, "Folder Test", "");

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 打开文件
     */
    static void getFileOpen() {
        LogPrint.info(TAG, "====== getFileOpen\n");

        String returnString = YKHttpEngine.getInstance().getDownloadFileUrlByPath(1221861, "v2.png", "");

        DeserializeHelper.getInstance().deserializeResult(returnString);

    }

    /**
     * 文件是否存在
     */
    static void fileExist() {
        LogPrint.info(TAG, "====== fileExistByHash\n");

        String returnString = YKHttpEngine.getInstance().fileExistByHash("279fbbe805d7dc4ef4d65ae7a8819b1f2beb3622", 1221861);
//        String returnString = YKHttpEngine.getInstance().fileExistByFullPath("1111.gknote", 251025);

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 创建文件夹
     */
    static void createFolder() {
        LogPrint.info(TAG, "====== createFolder\n");

        String returnString = YKHttpEngine.getInstance().createFolder(1221861, "Folder Test1");

        DeserializeHelper.getInstance().deserializeResult(returnString);

    }


    /**
     * 获取文件历史操作列表
     */
    static void getFileHistory() {
        LogPrint.info(TAG, "====== getFileHistory\n");

        String returnString = YKHttpEngine.getInstance().getHistory(1221861, "v2.png");

        DeserializeHelper.getInstance().deserializeResult(returnString);

    }

    /**
     * 文件转存
     */
    static void fileSave() {
        LogPrint.info(TAG, "====== fileSave\n");

        String returnString = YKHttpEngine.getInstance().fileSave(251025, "TestImg.png", "1111", 0, 251025, "");

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 文件搜索
     */
    static void fileSearch() {
        LogPrint.info(TAG, "====== fileSearch\n");

        String returnString = YKHttpEngine.getInstance().fileSearch("doc", 0);
        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 添加文件备注
     */
    static void addFileRemark() {
        LogPrint.info(TAG, "====== addFileRemark\n");
        String returnString = YKHttpEngine.getInstance().addFileRemark(1221861, "v2.png", "remark test");
        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 获取文件备注
     */
    static void getFileRemark() {
        LogPrint.info(TAG, "====== getFileRemark\n");

        String returnString = YKHttpEngine.getInstance().getFileRemarkList(1221861, "v2.png", 0, 500);
        DeserializeHelper.getInstance().deserializeResult(returnString);


    }

    /**
     * 文件重命名
     */
    static void fileRename() {
        LogPrint.info(TAG, "====== fileRename\n");

        String returnString = YKHttpEngine.getInstance().renameFile("v3", 1221861, "v3.JPG");
        DeserializeHelper.getInstance().deserializeResult(returnString);

    }

    /**
     * 获取文件链接
     */
    static void getFileLink() {
        LogPrint.info(TAG, "====== getFileLink\n");

//        String returnString = YKHttpEngine.getInstance().getFileLink("1111", 251025, "112");
//        DeserializeyHelpder.getInstance().deserializeResult(returnString);
    }

}
