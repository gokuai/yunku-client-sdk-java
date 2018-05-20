package com.gokuai.library.net;

import com.gokuai.cloud.data.FileInfo;
import com.gokuai.cloud.data.YunkuException;

/**
 * Created by Brandon on 14/12/16.
 */
public interface UploadCallback {
    void onSuccess(String fullpath, FileInfo result);

    void onFail(String fullpath, YunkuException e);

    void onProgress(String fullpath, float percent);
}
