package com.gokuai.library;


import com.gokuai.library.util.Util;
import org.apache.http.util.TextUtils;

import java.util.*;

public abstract class HttpEngine {

    private final static String LOG_TAG = "HttpEngine";


    public final static int API_ID_GET_FILE_INFO = 50;
    public final static int API_ID_GET_URL_BY_HASH = 49;


    protected String token;
    protected String refreshToken;


    protected HttpEngine() {

    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }


    public boolean isTokenAvailable() {
        return !TextUtils.isEmpty(token);
    }

    public static final int ERRORID_NETDISCONNECT = 1;

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

        //移除对应为null的参数
        Iterator it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getValue() == null ||
                    (ignoreKeys != null
                            && ignoreKeys.contains(pair.getKey().toString()))) {
                keys.remove(pair.getKey().toString());
                it.remove();
            }
        }

        if (size > 0) {
            for (int i = 0; i < size - 1; i++) {
                String key = keys.get(i);
                String value = params.get(key);
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
     */
    protected void reSignParams(HashMap<String, String> params, String secret) {
        reSignParams(params, secret, new ArrayList<String>());

    }

    /**
     * 重新根据参数进行签名
     *
     * @param params
     * @param secret
     * @param ignoreKeys
     */
    protected void reSignParams(HashMap<String, String> params, String secret,
                                ArrayList<String> ignoreKeys) {
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
