package com.gokuai.demo;

import com.gokuai.cloud.ConfigHelper;
import com.gokuai.cloud.data.YunkuException;
import com.gokuai.cloud.transinterface.YKUtil;

public class YunkuConfig {

    public static void init() throws YunkuException {

        //调用用户API必须配置够快提供的用户级访问授权
        String clientId = "";
        String secret = "";

        if (YKUtil.isEmpty(clientId) || YKUtil.isEmpty(secret)) {
            throw new YunkuException("client not set");
        }

        //根据实际需要更改默认配置
        new ConfigHelper(clientId, secret)
                .webHost("http://yk3.gokuai.com")
                .apiHost("http://yk3.gokuai.com/m-api")
                .language("zh-CN")
                .debug(false) //正式环境不要设置为true
                .config();
    }
}
