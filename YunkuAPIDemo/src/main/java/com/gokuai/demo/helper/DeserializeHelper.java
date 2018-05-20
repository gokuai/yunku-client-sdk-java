package com.gokuai.demo.helper;

import com.gokuai.base.ReturnResult;
import com.gokuai.library.data.BaseData;

/**
 * Created by Brandon on 2016/10/12.
 */
public class DeserializeHelper {

    private DeserializeHelper() {

    }

    private static class SingletonHolder {
        private static final DeserializeHelper INSTANCE = new DeserializeHelper();
    }

    public static DeserializeHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 返回操作信息
     *
     * @param result
     */
    public void deserializeResult(ReturnResult result) {
        if (result.isOK()) {
            //成功的结果
            System.out.println("result code: " + Integer.toString(result.getCode()));
            System.out.println("result body: " + result.getBody());
        } else {
            if (result.getException() != null) {
                //出现网络或IO错误
                result.getException().printStackTrace();
            } else {
                System.out.println("http response code: " + result.getCode() + ", body: " + result.getBody());

                //解析result中的内容
                BaseData data = BaseData.create(result.getBody());
                if (data != null) {
                    //如果可解析，则返回错误信息和错误号
                    System.out.println(data.getErrorCode() + ":" + data.getErrorMsg());
                }
            }
        }
    }
}
