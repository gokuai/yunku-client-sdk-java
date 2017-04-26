package com.gokuai.demo;

import com.gokuai.base.ReturnResult;
import com.gokuai.cloud.ConfigHelper;
import com.gokuai.cloud.transinterface.YKHttpEngine;
import com.gokuai.demo.helper.ClientConfig;
import com.gokuai.demo.helper.DeserializeHelper;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Brandon on 2017/4/19.
 */
public class YunkuSsoLogin {

    static {
        new ConfigHelper()
                .client(ClientConfig.CLIENT_ID, ClientConfig.CLIENT_SECRET)
                .apiHost("yunku-api.goukuai.cn")
                .webHost("yunku.goukuai.cn")
                .logVisible(true)
                .config();
    }

    public static void main(String[] args) {

        String returnString = YKHttpEngine.getInstance().ssoLogin("[account]", "[clientId]", "[clientSecret]");
        ReturnResult returnResult = ReturnResult.create(returnString);
        if (returnResult.getStatusCode() == 200) {
            try {
                DeserializeHelper.getInstance().deserializeResult(returnString);
                JSONObject json = new JSONObject(returnResult.getResult());
                String key = json.optString("gkkey");
                DeserializeHelper.getInstance().deserializeResult(YKHttpEngine.getInstance().otherMethodToLogin(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}
