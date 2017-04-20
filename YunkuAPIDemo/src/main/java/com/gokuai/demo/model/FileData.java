package com.gokuai.demo.model;

import org.json.JSONObject;

/**
 * 文件信息
 * Created by Brandon on 2017/4/21.
 */
public class FileData {

    String fullPath;
    long fileSize;
    String hash;
    String fileHash;
    int dir;
    int mountId;

    public static FileData create(JSONObject jsonObject) {
        FileData filedata = new FileData();
        filedata.fullPath = jsonObject.optString("fullpath");
        filedata.hash = jsonObject.optString("hash");
        filedata.fileSize = jsonObject.optLong("filesize");
        filedata.fileHash = jsonObject.optString("filehash");
        filedata.dir = jsonObject.optInt("dir");
        filedata.mountId = jsonObject.optInt("mount_id");
        return filedata;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
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

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }
}
