package com.gokuai.cloud.transinterface;

import com.gokuai.base.utils.Util;

/**
 * Created by Brandon on 2014/4/28.
 */
public class YKUtil extends Util {

    /**
     * 生成6个随机字符
     *
     * @return
     */
    public static String getSixRandomChars() {
        String result = "";

        for (int i = 0; i < 6; ++i) {
            int intVal = (int) (Math.random() * 26 + 97);
            result = result + (char) intVal;
        }

        return result;
    }
}
