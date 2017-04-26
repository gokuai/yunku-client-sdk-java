package com.gokuai.cloud.transinterface;

import com.gokuai.base.RequestMethod;

import java.util.HashMap;

/**
 * Created by Brandon on 2017/4/19.
 */
public class YKHttpEngineExtraBase extends YKHttpEngine {

    protected YKHttpEngineExtraBase(String clientId, String clientSecret) {
        super(clientId, clientSecret);
    }

    /**
     *
     * @param method
     * @param url
     * @param params
     * @return
     */
    protected String callApi(RequestMethod method, String url, HashMap<String, String> params) {
        params.put("sign", generateSign(params));
        String apiUrl = URL_API + url;
        return new RequestHelper().setUrl(apiUrl).setParams(params).setMethod(method).executeSync();
    }
}
