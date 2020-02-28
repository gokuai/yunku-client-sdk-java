package com.gokuai.cloud;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.NetConnection;
import com.gokuai.base.utils.Util;
import com.gokuai.cloud.transinterface.YKHttpEngine;
import com.gokuai.library.net.UploadManager;

import java.net.Proxy;

/**
 * 配置云库地址和client、secret
 * Created by Brandon on 2017/4/19.
 */
public class ConfigHelper {
    private String mClientId;
    private String mSecret;
    private String mEntDomain;
    private String mApiHost;
    private String mWebHost;
    private Proxy mProxy;
    private String mUserAgent;
    private String mLanguage;
    private long mConnectTimeout;
    private long mTimeout;
    private boolean mTrustSsl = false;
    private int mBlockSize;
    private int mRetry;
    private boolean mDebug;

    public ConfigHelper(String clientId, String secret) {
        mClientId = clientId;
        mSecret = secret;
    }

    /**
     * 是否开启debug模式
     *
     * @param debug
     */
    public ConfigHelper debug(boolean debug) {
        mDebug = debug;
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

    /**
     * HTTP请求的User-Agent
     *
     * @param userAgent
     * @return
     */
    public ConfigHelper userAgent(String userAgent) {
        this.mUserAgent = userAgent;
        return this;
    }

    /**
     * 信任https证书
     *
     * @param trust
     * @return
     */
    public ConfigHelper trustSsl(boolean trust) {
        this.mTrustSsl = trust;
        return this;
    }

    /**
     * 语言环境
     *
     * @param language
     * @return
     */
    public ConfigHelper language(String language) {
        this.mLanguage = language;
        return this;
    }

    /**
     * 分块上传单块大小, 单位字节
     *
     * @return
     */
    public ConfigHelper blockSize(int blockSize) {
        this.mBlockSize = blockSize;
        return this;
    }

    /**
     * 网络连接超时
     *
     * @param timeoutSeconds
     * @return
     */
    public ConfigHelper connectTimeout(long timeoutSeconds) {
        this.mConnectTimeout = timeoutSeconds;
        return this;
    }

    /**
     * 网络执行超时
     *
     * @param timeoutSeconds
     * @return
     */
    public ConfigHelper timeout(long timeoutSeconds) {
        this.mTimeout = timeoutSeconds;
        return this;
    }

    /**
     * 接口访问失败后重试几次
     *
     * @param retry
     * @return
     */
    public ConfigHelper retry(int retry) {
        if (retry > 0) {
            this.mRetry = retry;
        }
        return this;
    }

    /**
     * 配置参数
     */
    public void config() {
        if (Util.isEmpty(mWebHost)) {
            YKConfig.URL_HOST = "http://yk3.gokuai.com";
        } else {
            YKConfig.URL_HOST = mWebHost;
        }

        if (Util.isEmpty(mApiHost)) {
            YKConfig.URL_API_HOST = "http://yk3.gokuai.com/m-api";
        } else {
            YKConfig.URL_API_HOST = mApiHost;
        }

        YKConfig.CLIENT_ID = mClientId;
        YKConfig.CLIENT_SECRET = mSecret;

        String url = YKConfig.URL_HOST;
        YKConfig.URL_USER_AVATAR_FORMAT_BY_NAME = url + "/index/avatar?name=%s";
        YKConfig.URL_USER_AVATAR_FORMAT = url + "/index/avatar?id=%d&org_id=%d&name=%s";
        YKConfig.URL_USER_AVATAR_FORMAT_BY_MEMBERID = url + "/index/avatar?id=%d";
        YKConfig.FILE_THUMBNAIL_FORMAT = url + "/index/thumb?hash=%s&filehash=%s&type=%s&mount_id=%s";
        YKConfig.URL_ACCOUNT_AUTO_LOGIN = url + "/account/autologin/entgrant?client_id=%s&ticket=%s&returnurl=%s&format=%s";

        DebugConfig.DEBUG = mDebug;
        YKConfig.ENT_DOMAIN = mEntDomain;

        if (!(mProxy == null)){
            NetConnection.setProxy(mProxy);
        }
        if (!Util.isEmpty(mUserAgent)) {
            NetConnection.setUserAgent(mUserAgent);
        }

        if (mConnectTimeout > 0) {
            NetConnection.setConnectTimeout(mConnectTimeout);
        }

        if (mTimeout > 0) {
            NetConnection.setTimeout(mTimeout);
        }

        NetConnection.trustSsl(mTrustSsl);

        if (!Util.isEmpty(mLanguage)) {
            NetConnection.setAcceptLanguage(mLanguage);
        }

        if (this.mBlockSize > 0) {
            YKHttpEngine.setDefaultBlockSize(this.mBlockSize);
        }

        if (this.mRetry > 0) {
            NetConnection.setRetry(mRetry);
            UploadManager.setRetry(mRetry);
        }
    }
}
