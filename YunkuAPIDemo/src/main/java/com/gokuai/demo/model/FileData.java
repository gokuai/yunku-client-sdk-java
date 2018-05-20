package com.gokuai.demo.model;

import com.gokuai.base.ReturnResult;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件信息
 * Created by Brandon on 2017/4/21.
 */
public class FileData {
    JSONObject json;
    int mountId;
    String fullpath;
    int dir;
    String fileName;
    String hash;
    String fileHash;
    long fileSize;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFullpath() {
        return fullpath;
    }

    public void setFullpath(String fullpath) {
        this.fullpath = fullpath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    public boolean isDir() {
        return dir == 1;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public String toString() {
        return json.toString();
    }

    public static FileData create(JSONObject jsonObject) {
        FileData data = new FileData();
        data.json = jsonObject;
        data.mountId = jsonObject.optInt("mount_id");
        data.fullpath = jsonObject.optString("fullpath");
        data.dir = jsonObject.optInt("dir");
        data.fileName = jsonObject.optString("filename");
        data.hash = jsonObject.optString("hash");
        data.fileHash = jsonObject.optString("filehash");
        data.fileSize = jsonObject.optLong("filesize");
        return data;
    }

    public static List<FileData> createList(ReturnResult result) {
        List<FileData> list = new ArrayList<FileData>();
        JSONObject json = new JSONObject(result.getBody());
        JSONArray jsonArray = json.optJSONArray("list");
        for (int i = 0; i < jsonArray.length(); i++) {
            FileData data = FileData.create(jsonArray.optJSONObject(i));
            list.add(data);
        }
        return list;
    }
}
