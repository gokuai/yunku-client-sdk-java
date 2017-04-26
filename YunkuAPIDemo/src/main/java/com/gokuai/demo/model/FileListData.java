package com.gokuai.demo.model;

import com.gokuai.base.ReturnResult;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * 文件列表信息
 * Created by Brandon on 2017/4/21.
 */
public class FileListData extends BaseData {

    private ArrayList<FileData> list;


    public static FileListData create(String returnString) {
        FileListData mountListData = new FileListData();

        ReturnResult returnResult = ReturnResult.create(returnString);
        mountListData.setCode(returnResult.getStatusCode());

        if (returnResult.getStatusCode() == HttpURLConnection.HTTP_OK) {
            JSONObject json = new JSONObject(returnResult.getResult());
            JSONArray jsonArray = json.optJSONArray("list");

            ArrayList<FileData> list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                FileData mountData = FileData.create(jsonArray.optJSONObject(i));
                list.add(mountData);
            }
            mountListData.setList(list);

        }
        return mountListData;
    }

    public ArrayList<FileData> getList() {
        return list;
    }

    public void setList(ArrayList<FileData> list) {
        this.list = list;
    }

}
