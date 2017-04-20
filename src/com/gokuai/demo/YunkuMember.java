package com.gokuai.demo;

import com.gokuai.cloud.ConfigHelper;
import com.gokuai.cloud.transinterface.YKHttpEngine;
import com.gokuai.demo.helper.ClientConfig;
import com.gokuai.demo.helper.DeserializeHelper;
import com.gokuai.demo.helper.YunkuAuthHelper;

/**
 * Created by Brandon on 2016/10/12.
 */
public class YunkuMember {

    private static final String TAG = "YunkuMember";

    static {
        new ConfigHelper().client(ClientConfig.CLIENT_ID,ClientConfig.CLIENT_SECRET).config();
    }

    public static void main(String[] args) {

        YunkuAuthHelper.getInstance().loginByAccount();

//        getGroupList();

        getGroupMemberList();

    }

    /**
     * 获取部门列表
     */
    static void getGroupList() {
        String returnString = YKHttpEngine.getInstance().getGroupFromGroup(750, 0);
        DeserializeHelper.getInstance().deserializeResult(returnString);

    }

    /**
     * 获取部门成员列表
     */
    static void getGroupMemberList() {
        String returnString = YKHttpEngine.getInstance().getMemberFromGroup(750, 71715, 0, 0, "", 0);
        DeserializeHelper.getInstance().deserializeResult(returnString);


    }


}
