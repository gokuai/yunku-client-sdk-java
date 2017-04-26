package com.gokuai.demo;

import com.gokuai.base.LogPrint;
import com.gokuai.cloud.ConfigHelper;
import com.gokuai.cloud.transinterface.YKHttpEngine;
import com.gokuai.demo.helper.ClientConfig;
import com.gokuai.demo.helper.DeserializeHelper;
import com.gokuai.demo.helper.YunkuAuthHelper;

/**
 * Created by Brandon on 2016/10/12.
 * <p>
 * 库API Demo示例
 */
public class YunkuLibrary {

    private static final String TAG = "YunkuLibrary";

    static {
        new ConfigHelper().client(ClientConfig.CLIENT_ID,ClientConfig.CLIENT_SECRET).config();
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

        LogPrint.info(TAG, "====== getLibraryLogos\n");

        String returnString = YKHttpEngine.getInstance().getDefaultLibLogos();

        DeserializeHelper.getInstance().deserializeResult(returnString);

    }

    /**
     * 获取库信息
     */
    static void getLibraryInfo() {
        LogPrint.info(TAG, "====== getLibraryInfo\n");

        String returnString = YKHttpEngine.getInstance().getLibInfoByMountId(1221861);
//        String returnString = YKHttpEngine.getInstance().getLibInfoByOrgId(251023);
        DeserializeHelper.getInstance().deserializeResult(returnString);

    }

    /**
     * 获取库成员列表
     */
    static void getLibraryMembers() {
        LogPrint.info(TAG, "====== getLibraryMembers\n");

        String returnString = YKHttpEngine.getInstance().getMemberRelative(371523, 0, false, false);
        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 获取库部门列表
     */
    static void getLibraryGroups() {
        LogPrint.info(TAG, "====== getLibraryGroups\n");

        String returnString = YKHttpEngine.getInstance().getGroupRelative(371523, true);
        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 添加库部门
     */
    static void addLibraryGroup() {
        LogPrint.info(TAG, "====== addLibraryGroup\n");

        String returnString = YKHttpEngine.getInstance().addLibraryGroup(705, 371523, 78724, 7880);

        DeserializeHelper.getInstance().deserializeResult(returnString);

    }

    /**
     * 修改库部门
     */
    static void updateLibraryGroup() {
        LogPrint.info(TAG, "====== updateLibraryGroup\n");

        String returnString = YKHttpEngine.getInstance().updateLibraryGroup(705, 371523, 78724, 7880);

        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 删除库部门
     */
    static void deleteLibraryGroup() {
        LogPrint.info(TAG, "====== deleteLibraryGroup\n");

        String returnString = YKHttpEngine.getInstance().delLibraryGroup(705, 371523, 78724);

        DeserializeHelper.getInstance().deserializeResult(returnString);

    }


    /**
     * 添加库成员
     */
    static void addLibraryMember() {
        LogPrint.info(TAG, "====== addLibraryMember\n");

        String returnString = YKHttpEngine.getInstance().addLibraryMember(371523, "2608", 3208);
        DeserializeHelper.getInstance().deserializeResult(returnString);

    }

    /**
     * 添加库成员
     */
    static void updateLibraryMember() {
        LogPrint.info(TAG, "====== updateLibraryMember\n");

        String returnString = YKHttpEngine.getInstance().changeLibraryMemberRole(371523, "2608", 3208);
        DeserializeHelper.getInstance().deserializeResult(returnString);

    }

    /**
     * 添加库成员
     */
    static void deleteLibraryMember() {
        LogPrint.info(TAG, "====== deleteLibraryMember\n");

        String returnString = YKHttpEngine.getInstance().delLibraryMember(371523, "4");
        DeserializeHelper.getInstance().deserializeResult(returnString);
    }

    /**
     * 创建库
     */
    static void createLibrary() {
        LogPrint.info(TAG, "====== createLibrary\n");

        String returnString = YKHttpEngine.getInstance().createLibrary("test", 32, "test", "", "", -1);
        DeserializeHelper.getInstance().deserializeResult(returnString);
    }


}
