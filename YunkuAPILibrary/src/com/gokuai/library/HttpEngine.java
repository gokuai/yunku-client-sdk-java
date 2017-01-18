package com.gokuai.library;


import com.gokuai.library.util.URLEncoder;
import com.gokuai.library.util.Util;
import org.apache.http.util.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public abstract class HttpEngine {
    private final static String LOG_TAG = "HttpEngine";

    public final static int API_ID_VERSION = 0;


    public final static int API_ID_GET_FILE_INFO = 50;
    public final static int API_ID_GET_URL_BY_HASH = 49;


    protected String token;
    protected String refreshToken;


    protected HttpEngine() {

    }

    public abstract String getToken();

    public abstract boolean refreshToken();

    public boolean isTokenAvailable() {
        return !TextUtils.isEmpty(token);
    }

    public static final int ERRORID_NETDISCONNECT = 1;
    public static final int ERRORID_CHAT_SERVICE_NOT_LOGIN = 2;

    /**
     * 从服务器获得数据后的回调
     *
     * @author Administrator
     */
    public interface DataListener {
        void onReceivedData(int apiId, Object object, int errorId);
    }


    /**
     * 根据clientsecret 签名
     *
     * @param params
     * @param secret
     * @return
     */
    public String generateSignOrderByKey(HashMap<String, String> params, String secret) {
        return generateSignOrderByKey(params, secret, new ArrayList<String>());
    }

    /**
     * 根据clientsecret 签名 ,排除不需要签名的value
     *
     * @param params
     * @param secret
     * @param ignoreKeys
     * @return
     */
    protected String generateSignOrderByKey(HashMap<String, String> params, String secret, ArrayList<String> ignoreKeys) {
        ArrayList<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys, mComparator);
        int size = params.size();
        String string_to_sign = "";

        if (size > 0) {
            for (int i = 0; i < size - 1; i++) {
                String key = keys.get(i);
                if (ignoreKeys != null && ignoreKeys.contains(key)) {
                    continue;
                }

                String value = params.get(key);
                if (value == null) {
                    continue;
                }

                string_to_sign += value + "\n";
            }
            string_to_sign += params.get(keys.get(size - 1));
        }
        return Util.getHmacSha1(string_to_sign, secret);
    }

    /**
     * 重新根据参数进行签名
     *
     * @param params
     * @param secret
     * @param needEncode
     */
    protected void reSignParams(HashMap<String, String> params, String secret,
                                boolean needEncode) {
        reSignParams(params, secret, needEncode, new ArrayList<String>());

    }

    /**
     * 重新根据参数进行签名
     *
     * @param params
     * @param secret
     * @param needEncode
     * @param ignoreKeys
     */
    protected void reSignParams(HashMap<String, String> params, String secret,
                                boolean needEncode, ArrayList<String> ignoreKeys) {
        params.remove("token");
        params.remove("sign");
        params.put("token", getToken());
        params.put("sign", generateSignOrderByKey(params, secret, ignoreKeys));

    }


    private Comparator<String> mComparator = new Comparator<String>() {
        public int compare(String p1, String p2) {
            return p1.compareTo(p2);
        }
    };


}
