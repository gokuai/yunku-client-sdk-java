package com.gokuai.demo.model;

import com.gokuai.library.data.ReturnResult;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 *
 * 库列表信息
 * Created by Brandon on 2017/4/20.
 */
public class MountListData extends BaseData {

    private ArrayList<MountData> list;


    public static MountListData create(String returnString) {
        MountListData mountListData = new MountListData();

        ReturnResult returnResult = ReturnResult.create(returnString);
        mountListData.setCode(returnResult.getStatusCode());

        if (returnResult.getStatusCode() == HttpURLConnection.HTTP_OK) {
            JSONObject json = new JSONObject(returnResult.getResult());
            JSONArray jsonArray = json.optJSONArray("list");

            ArrayList<MountData> list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                MountData mountData = MountData.create(jsonArray.optJSONObject(i));
                list.add(mountData);
            }
            mountListData.setList(list);

        }
        return mountListData;
    }

    public ArrayList<MountData> getList() {
        return list;
    }

    public void setList(ArrayList<MountData> list) {
        this.list = list;
    }
}
