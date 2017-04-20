package com.gokuai.demo.model;

import org.json.JSONObject;

/**
 * 库信息
 * Created by Brandon on 2017/4/20.
 */
public class MountData {
    private String name;
    private int mountId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMountId() {
        return mountId;
    }

    public void setMountId(int mountId) {
        this.mountId = mountId;
    }

    public static MountData create(JSONObject jsonObject) {
        MountData mountData = new MountData();
        mountData.mountId = jsonObject.optInt("mount_id");
        mountData.name = jsonObject.optString("name");
        return mountData;

    }
}
