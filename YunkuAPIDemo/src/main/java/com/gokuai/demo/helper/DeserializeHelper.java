package com.gokuai.demo.helper;

import com.gokuai.base.ReturnResult;
import com.gokuai.library.data.BaseData;
import java.net.HttpURLConnection;

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
    public void deserializeResult(String result) {

        //解析结果
        ReturnResult returnResult = ReturnResult.create(result);

        if (returnResult.getStatusCode() == HttpURLConnection.HTTP_OK) {
            //成功的结果
            System.out.println("return 200");

        } else {
            //解析result中的内容
            BaseData data = BaseData.create(returnResult.getResult());
            if (data != null) {
                //如果可解析，则返回错误信息和错误号
                System.out.println("\ndeserializeResult:" + data.getErrorCode() + ":" + data.getErrorMsg());
            }
        }
        System.out.println(returnResult.getResult());

        //复制到剪贴板
//        Util.copyToClipboard(returnResult.getResult());
    }
}
