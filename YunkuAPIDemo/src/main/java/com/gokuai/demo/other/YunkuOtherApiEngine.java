package com.gokuai.demo.other;

import com.gokuai.base.RequestMethod;
import com.gokuai.base.ReturnResult;
import com.gokuai.cloud.YKConfig;
import com.gokuai.cloud.transinterface.YKHttpEngineExtraBase;

import java.util.HashMap;

/**
 * 构建额外的 API 接口
 * Created by Brandon on 2017/4/19.
 */
public class YunkuOtherApiEngine extends YKHttpEngineExtraBase {

    protected YunkuOtherApiEngine(String clientId, String clientSecret) {
        super(clientId, clientSecret);
    }

    private static volatile YunkuOtherApiEngine instance = null;


    public static YunkuOtherApiEngine getInstance() {
        if (instance == null) {
            synchronized (YunkuOtherApiEngine.class) {
                if (instance == null) {
                    instance = new YunkuOtherApiEngine(YKConfig.CLIENT_ID, YKConfig.CLIENT_SECRET);
                }
            }
        }
        return instance;
    }

    public static void releaseEngine() {
        if (instance != null) {
            instance.token = null;
            instance.refreshToken = null;
            instance = null;
        }

    }




    public ReturnResult otherApiRequest(String param1, String params2) {
        HashMap<String, String> params = new HashMap<>();
        params.put("param1 key", param1);
        params.put("param2 key", params2);
        return callApi(RequestMethod.POST, "/API_URL", params);
    }


}
