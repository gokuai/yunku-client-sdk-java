package com.gokuai.demo.model;

import com.gokuai.base.ReturnResult;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 库信息
 * Created by Brandon on 2017/4/20.
 */
public class LibraryData {
    int id;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static LibraryData create(JSONObject jsonObject) {
        LibraryData data = new LibraryData();
        data.id = jsonObject.optInt("mount_id");
        data.name = jsonObject.optString("org_name");
        return data;
    }

    public static List<LibraryData> createList(ReturnResult result) {
        List<LibraryData> list = new ArrayList<>();
        JSONObject json = new JSONObject(result.getBody());
        JSONArray jsonList = json.optJSONArray("list");
        for (int i = 0; i < jsonList.length(); i++) {
            LibraryData data = LibraryData.create(jsonList.optJSONObject(i));
            list.add(data);
        }
        return list;
    }
}
