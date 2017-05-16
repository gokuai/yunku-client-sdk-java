package com.gokuai.library.net;

/**
 * Created by Brandon on 14/12/16.
 */
public interface UploadCallBack {
    void onSuccess(long threadId,String result);

    void onFail(long threadId, String errorMsg);

    void onProgress(long threadId, float percent);
}
