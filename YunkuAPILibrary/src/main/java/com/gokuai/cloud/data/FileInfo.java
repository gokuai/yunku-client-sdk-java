package com.gokuai.cloud.data;

/**
 * Created by qp on 2017/5/2.
 */
public class FileInfo {
    public long fileSize;
    public String fileHash;

    public FileInfo(long fileSize, String fileHash) {
        this.fileSize = fileSize;
        this.fileHash = fileHash;
    }
}
