package com.gokuai.cloud;

import com.gokuai.library.util.DebugFlag;
import org.apache.http.util.TextUtils;

import java.net.Proxy;

/**
 * 配置云库地址和client、secret
 * Created by Brandon on 2017/4/19.
 */
public class ConfigHelper {


    private final static String HTTPS = "https://";
    private final static String HTTP = "http://";


    private String mClientId;
    private String mClientSecret;
    private boolean mLogVisible;
    private boolean mHttps;
    private String mEntDomain;
    private String mApiHost;
    private String mWebHost;
    private Proxy mProxy;

    /**
     * 更改日志可见行
     *
     * @param visible
     */
    public ConfigHelper logVisible(boolean visible) {
        mLogVisible = visible;
        return this;
    }

    /**
     * 更改企业域
     */
    public ConfigHelper domain(String entDomain) {
        mEntDomain = entDomain;
        return this;
    }

    /**
     * 更改https
     *
     * @param https
     */
    public ConfigHelper https(boolean https) {
        mHttps = https;
        return this;
    }

    /**
     * 更改API host
     *
     * @param apiHost
     * @return
     */
    public ConfigHelper apiHost(String apiHost) {
        mApiHost = apiHost;
        return this;
    }


    /**
     * 更改Web host
     *
     * @param webHost
     * @return
     */
    public ConfigHelper webHost(String webHost) {
        mWebHost = webHost;
        return this;
    }


    /**
     * 更改代理设置
     *
     * @param proxy
     * @return
     */
    public ConfigHelper proxy(Proxy proxy) {

        mProxy = proxy;
        return this;
    }


    public ConfigHelper client(String clientId, String clientSecret) {
        mClientId = clientId;
        mClientSecret = clientSecret;
        return this;
    }

    /**
     * 配置参数
     */
    public void config() {
        YKConfig.SCHEME_PROTOCOL = mHttps ? HTTPS : HTTP;

        // FIXME: 联系够快开发人员获取需要的CLIENT_ID，CLIENT_SECRET 参数

        if (TextUtils.isEmpty(mApiHost)) {
            YKConfig.URL_API_HOST = "yk3.gokuai.com/m-api";
        } else {
            YKConfig.URL_API_HOST = mApiHost;
        }

        if (TextUtils.isEmpty(mWebHost)) {
            YKConfig.URL_HOST = "yk3.gokuai.com";
        } else {
            YKConfig.URL_HOST = mWebHost;
        }

        YKConfig.CLIENT_ID = mClientId;
        YKConfig.CLIENT_SECRET = mClientSecret;

        if (TextUtils.isEmpty(YKConfig.CLIENT_ID) || TextUtils.isEmpty(YKConfig.CLIENT_SECRET)) {
            throw new IllegalArgumentException("CLIENT_ID CLIENT_SECRET can not be empty!!");
        }

        String url = YKConfig.SCHEME_PROTOCOL + YKConfig.URL_HOST;


        YKConfig.URL_USER_AVATAR_FORMAT_BY_NAME = url + "/index/avatar?name=%s";
        YKConfig.URL_USER_AVATAR_FORMAT = url + "/index/avatar?id=%d&org_id=%d&name=%s";
        YKConfig.URL_USER_AVATAR_FORMAT_BY_MEMBERID = url + "/index/avatar?id=%d";

        YKConfig.FILE_THUMBNAIL_FORMAT = url + "/index/thumb?hash=%s&filehash=%s&type=%s&mount_id=%s";

        YKConfig.URL_ACCOUNT_AUTO_LOGIN = url + "/account/autologin/entgrant?client_id=%s&ticket=%s&returnurl=%s&format=%s";

        DebugFlag.LOG_VISIBLE = mLogVisible;
        YKConfig.ENT_DOMAIN = mEntDomain;

        //TODO 设置代理
        if (mProxy == null){
            YKConfig.PROXY = null;
        }else {
            YKConfig.PROXY = mProxy;
        }
    }
}
