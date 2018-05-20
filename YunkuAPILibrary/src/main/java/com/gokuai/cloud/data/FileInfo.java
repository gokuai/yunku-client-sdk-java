package com.gokuai.cloud.data;

/**
 * Created by qp on 2017/5/2.
 */
public class FileInfo {
    public int mountId;
    public long fileSize;
    public String fileHash;
    public String fullpath;
    public String filename;
    public String hash;
    public String uploadServer;

    public FileInfo() {

    }

    public FileInfo(long fileSize, String fileHash) {
        this.fileSize = fileSize;
        this.fileHash = fileHash;
    }
}
