package com.gokuai.demo;

import com.gokuai.cloud.ConfigHelper;
import com.gokuai.cloud.Constants;
import com.gokuai.cloud.transinterface.YKHttpEngine;
import com.gokuai.demo.helper.ClientConfig;
import com.gokuai.demo.helper.DeserializeHelper;
import com.gokuai.demo.helper.YunkuAuthHelper;
import com.gokuai.library.net.UploadCallBack;
import com.gokuai.library.util.DebugFlag;
import com.google.gson.Gson;

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
        DebugFlag.logInfo(TAG, "======lockFile\n");

        String returnString = YKHttpEngine.getInstance().lock("1111.gknote", 251025, 0);
        DeserializeHelper.getInstance().deserializeResult(returnString);

    }

    /**
     * 文件最近更新列表
     */
    static void getFileUpdates() {
        DebugFlag.logInfo(TAG, "====== getFileUpdates\n");

        String returnString = YKHttpEngine.getInstance().getFileUpdates(251025, 0, 500);

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 设置文件描述
     */
    static void setFileKeyWord() {
        DebugFlag.logInfo(TAG, "====== setFileKeyWord\n");

        String returnString = YKHttpEngine.getInstance().setFileKeyWord(251025, "1111", "test 1");

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 获取收藏夹信息
     */
    static void getFavorInfo() {
        DebugFlag.logInfo(TAG, "====== getFavorInfo\n");

        String returnString = YKHttpEngine.getInstance().getFavoriteInfo(0);

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 添加收藏
     */
    static void addFavor() {
        DebugFlag.logInfo(TAG, "====== addFavor\n");

        String returnString = YKHttpEngine.getInstance().addFavorities(251025, "1111", 1);

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 删除收藏
     */
    static void deleteFavor() {
        DebugFlag.logInfo(TAG, "====== deleteFavor\n");

        String returnString = YKHttpEngine.getInstance().delFavorities(251025, "1111", 1);

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 清空收藏
     */
    static void clearFavor() {
        DebugFlag.logInfo(TAG, "====== clearFavor\n");

        String returnString = YKHttpEngine.getInstance().clearFavorities();

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 获取24小时内修改的文件
     */
    static void getFileRecentModified() {
        DebugFlag.logInfo(TAG, "====== getFileRecentModified\n");

        String returnString = YKHttpEngine.getInstance().getFileRecentModified();

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 获取锁定文件信息
     */
    static void getFilesLocked() {
        DebugFlag.logInfo(TAG, "====== getFilesLocked\n");

        String returnString = YKHttpEngine.getInstance().getFileLocked();

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }


    /**
     * 获取文件（夹）信息
     */
    static void getFileInfo() {
        DebugFlag.logInfo(TAG, "====== getFileInfo\n");

        String returnString = YKHttpEngine.getInstance().getFileInfoSync("工作积累/我的够快/团队的文件/开发部相关/测试部门文档/产品工作流程.doc", 394234, "");

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 获取文件列表
     */
    static void getFileList() {
        DebugFlag.logInfo(TAG, "====== getFileList\n");

        String returnString = YKHttpEngine.getInstance().getFileListSync(251025, "", 0, 500);

        DeserializeHelper.getInstance().deserializeResult(returnString);

    }

    /**
     * 根据指定文件hash获取列表
     */
    static void getFileListByHashs() {
        DebugFlag.logInfo(TAG, "====== getFileListByHashs\n");

        String returnString = YKHttpEngine.getInstance()
                .getFileListByHashs(251025, 0, 500, new String[]{"b24cae3429409c20cd20c80aa1c42e04f5aa8287"});

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 文件上传 （表单上传）
     */
    static void fileUploadMSMulti() {
        DebugFlag.logInfo(TAG, "====== fileUploadMSMulti\n");

        String returnString = YKHttpEngine.getInstance().uploadFile(46855,
                "1.xlsx", "Brandon", "/Users/Brandon/Desktop/1.xlsx", false);

        DeserializeHelper.getInstance().deserializeResult(returnString);

    }

    /**
     * 文件上传 （分块上传）
     */
    static void fileUploadByBlock() {

        YKHttpEngine.getInstance().uploadByBlock(251025, "saveto2.png",
                "/Users/Brandon/Desktop/saveto2.png", new UploadCallBack() {
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
        DebugFlag.logInfo(TAG, "====== fileDelete\n");

        ArrayList<String> fullPaths = new ArrayList<>();
        fullPaths.add("TestImg.png");

        String returnString = YKHttpEngine.getInstance().batchDeleteFileAsync(251025, fullPaths);

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 文件复制
     */
    static void fileCopy() {
        DebugFlag.logInfo(TAG, "====== fileCopy\n");

        ArrayList<String> fullPaths = new ArrayList<>();
        fullPaths.add("TestImg.png");

        String returnString = YKHttpEngine.getInstance().fileBatchCopy(251025, fullPaths, 251025, "");

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 文件移动
     */
    static void fileMove() {
        DebugFlag.logInfo(TAG, "====== fileMove\n");

        ArrayList<String> fullPaths = new ArrayList<>();
        fullPaths.add("TestImg.png");

        String returnString = YKHttpEngine.getInstance().fileBatchMove(251025, fullPaths, 251025, "");

        DeserializeHelper.getInstance().deserializeResult(returnString);

    }

    /**
     * 恢复已删除文件
     */
    static void fileRevert() {
        DebugFlag.logInfo(TAG, "====== fileRevert\n");

        String returnString = YKHttpEngine.getInstance().revert("TestImg.png", 251025, "AVe9Q19xn7vtXv-L4a_K");
        DeserializeHelper.getInstance().deserializeResult(returnString);

    }

    /**
     * 设置收藏夹名称
     */
    static void setFavorName() {
        DebugFlag.logInfo(TAG, "====== setFavorName\n");

        String returnString = YKHttpEngine.getInstance().changeFavoritesName(1, "test2");
        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 获取收藏家名称
     */
    static void getFavorName() {
        DebugFlag.logInfo(TAG, "====== getFavorName\n");

        String returnString = YKHttpEngine.getInstance().getFavoritesName();
        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 获取文件下载链接
     */
    static void getUrlByFileHash() {
        DebugFlag.logInfo(TAG, "====== getUrlByFileHash\n");

        String returnString = YKHttpEngine.getInstance().getDownloadFileUrlByFileHash(251025, "fd9e194c7e82aeb25c6b3e857e631680a19df3b2");
        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 设置文件权限
     */
    static void setFilePermission() {

        DebugFlag.logInfo(TAG, "====== setFilePermission\n");

        ArrayList<String> permissionSetting = new ArrayList<>();
        permissionSetting.add(Constants.FILE_DELETE);
        permissionSetting.add(Constants.FILE_WRITE);

        //member
        HashMap<Integer, Object> map = new HashMap<>();
        map.put(2208, permissionSetting);

        String returnString = YKHttpEngine.getInstance().setFilePermission(251025, "1111", new Gson().toJson(map), false);
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
        DebugFlag.logInfo(TAG, "====== getFileMemberPermission\n");

        String returnString = YKHttpEngine.getInstance().getMemberFolderPermission(251025, "1111", 0, 500);

        DeserializeHelper.getInstance().deserializeResult(returnString);


    }

    /**
     * 获取文件部门权限
     */
    static void getFileGroupPermission() {
        DebugFlag.logInfo(TAG, "====== getFileGroupPermission\n");

        String returnString = YKHttpEngine.getInstance().getGroupFolderPermission(251025, "1111", 0, 500);

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 获取文件夹属性
     */
    static void getFolderAttribute() {
        DebugFlag.logInfo(TAG, "====== getFolderAttribute\n");

        String returnString = YKHttpEngine.getInstance().getFolderAttribute(251025, "1111", "");

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 打开文件
     */
    static void getFileOpen() {
        DebugFlag.logInfo(TAG, "====== getFileOpen\n");

        String returnString = YKHttpEngine.getInstance().getDownloadFileUrlByPath(251025, "1111.gknote", "");

        DeserializeHelper.getInstance().deserializeResult(returnString);

    }

    /**
     * 文件是否存在
     */
    static void fileExist() {
        DebugFlag.logInfo(TAG, "====== fileExistByHash\n");

        String returnString = YKHttpEngine.getInstance().fileExistByHash("b24cae3429409c20cd20c80aa1c42e04f5aa8287", 251025);
//        String returnString = YKHttpEngine.getInstance().fileExistByFullPath("1111.gknote", 251025);

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 创建文件夹
     */
    static void createFolder() {
        DebugFlag.logInfo(TAG, "====== createFolder\n");

        String returnString = YKHttpEngine.getInstance().createFolder(251025, "Folder Test");

        DeserializeHelper.getInstance().deserializeResult(returnString);

    }


    /**
     * 获取文件历史操作列表
     */
    static void getFileHistory() {
        DebugFlag.logInfo(TAG, "====== createFolder\n");

        String returnString = YKHttpEngine.getInstance().getHistory(251025, "TestImg.png");

        DeserializeHelper.getInstance().deserializeResult(returnString);

    }

    /**
     * 文件转存
     */
    static void fileSave() {
        DebugFlag.logInfo(TAG, "====== fileSave\n");

        String returnString = YKHttpEngine.getInstance().fileSave(251025, "TestImg.png", "1111", 0, 251025, "");

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 文件搜索
     */
    static void fileSearch() {
        DebugFlag.logInfo(TAG, "====== fileSearch\n");

        String returnString = YKHttpEngine.getInstance().fileSearch("doc", 0);
        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 添加文件备注
     */
    static void addFileRemark() {
        DebugFlag.logInfo(TAG, "====== addFileRemark\n");
        String returnString = YKHttpEngine.getInstance().addFileRemark(251025, "1111", "remark test");
        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 获取文件备注
     */
    static void getFileRemark() {
        DebugFlag.logInfo(TAG, "====== getFileRemark\n");

        String returnString = YKHttpEngine.getInstance().getFileRemarkList(251025, "1111", 0, 500);
        DeserializeHelper.getInstance().deserializeResult(returnString);


    }

    /**
     * 文件重命名
     */
    static void fileRename() {
        DebugFlag.logInfo(TAG, "====== fileRename\n");

        String returnString = YKHttpEngine.getInstance().renameFile("1111", 251025, "112");
        DeserializeHelper.getInstance().deserializeResult(returnString);

    }

    /**
     * 获取文件链接
     */
    static void getFileLink() {
        DebugFlag.logInfo(TAG, "====== getFileLink\n");

//        String returnString = YKHttpEngine.getInstance().getFileLink("1111", 251025, "112");
//        DeserializeyHelpder.getInstance().deserializeResult(returnString);
    }

}
