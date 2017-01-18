package com.gokuai.demo;

import com.gokuai.cloud.ConfigHelper;
import com.gokuai.cloud.transinterface.YKHttpEngine;
import com.gokuai.demo.helper.DeserializeHelper;
import com.gokuai.demo.helper.YunkuAuthHelper;
import com.gokuai.library.util.DebugFlag;

/**
 * Created by Brandon on 2016/10/12.
 * <p>
 * 库API Demo示例
 */
public class YunkuLibrary {

    private static final String TAG = "YunkuLibrary";

    static {
        ConfigHelper.init();

    }

    public static void main(String[] args) {

        YunkuAuthHelper.getInstance().loginByAccount();

//        getLibraryLogos();
//
//        getLibraryInfo();
//
//        getLibraryMembers();
//
//        getLibraryGroups();

//        addLibraryGroup();

//        updateLibraryGroup();

//        deleteLibraryGroup();

//        addLibraryMember();

//        updateLibraryMember();

//        deleteLibraryMember();

//        createLibrary();

    }

    /**
     * 默认库图标列表
     */
    static void getLibraryLogos() {

        DebugFlag.logInfo(TAG, "====== getLibraryLogos\n");

        String returnString = YKHttpEngine.getInstance().getDefaultLibLogos();

        DeserializeHelper.getInstance().deserializeResult(returnString);

    }

    /**
     * 获取库信息
     */
    static void getLibraryInfo() {
        DebugFlag.logInfo(TAG, "====== getLibraryInfo\n");

        String returnString = YKHttpEngine.getInstance().getLibInfoByMountId(251025);
//        String returnString = YKHttpEngine.getInstance().getLibInfoByOrgId(251023);
        DeserializeHelper.getInstance().deserializeResult(returnString);

    }

    /**
     * 获取库成员列表
     */
    static void getLibraryMembers() {
        DebugFlag.logInfo(TAG, "====== getLibraryMembers\n");

        String returnString = YKHttpEngine.getInstance().getMemberRelative(371523, 0, false, false);
        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 获取库部门列表
     */
    static void getLibraryGroups() {
        DebugFlag.logInfo(TAG, "====== getLibraryGroups\n");

        String returnString = YKHttpEngine.getInstance().getGroupRelative(371523, true);
        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 添加库部门
     */
    static void addLibraryGroup() {
        DebugFlag.logInfo(TAG, "====== addLibraryGroup\n");

        String returnString = YKHttpEngine.getInstance().addLibraryGroup(705, 371523, 78724, 7880);

        DeserializeHelper.getInstance().deserializeResult(returnString);

    }

    /**
     * 修改库部门
     */
    static void updateLibraryGroup() {
        DebugFlag.logInfo(TAG, "====== updateLibraryGroup\n");

        String returnString = YKHttpEngine.getInstance().updateLibraryGroup(705, 371523, 78724, 7880);

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 删除库部门
     */
    static void deleteLibraryGroup() {
        DebugFlag.logInfo(TAG, "====== deleteLibraryGroup\n");

        String returnString = YKHttpEngine.getInstance().delLibraryGroup(705, 371523, 78724);

        DeserializeHelper.getInstance().deserializeResult(returnString);

    }


    /**
     * 添加库成员
     */
    static void addLibraryMember() {
        DebugFlag.logInfo(TAG, "====== addLibraryMember\n");

        String returnString = YKHttpEngine.getInstance().addLibraryMember(371523, "2608", 3208);
        DeserializeHelper.getInstance().deserializeResult(returnString);

    }

    /**
     * 添加库成员
     */
    static void updateLibraryMember() {
        DebugFlag.logInfo(TAG, "====== updateLibraryMember\n");

        String returnString = YKHttpEngine.getInstance().changeLibraryMemberRole(371523, "2608", 3208);
        DeserializeHelper.getInstance().deserializeResult(returnString);

    }

    /**
     * 添加库成员
     */
    static void deleteLibraryMember() {
        DebugFlag.logInfo(TAG, "====== deleteLibraryMember\n");

        String returnString = YKHttpEngine.getInstance().delLibraryMember(371523, "4");
        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 创建库
     */
    static void createLibrary() {
        DebugFlag.logInfo(TAG, "====== createLibrary\n");

        String returnString = YKHttpEngine.getInstance().createLibrary("test", 32, "test", "", "", -1);
        DeserializeHelper.getInstance().deserializeResult(returnString);
    }


}
