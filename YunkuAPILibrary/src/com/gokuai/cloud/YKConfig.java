package com.gokuai.cloud;

import com.gokuai.library.Config;

import java.net.MalformedURLException;
import java.net.URL;


public class YKConfig extends Config {

    public enum Site {
        PUBLISH,//正式发布站点
        PUBLISH_LINE_B, //内测发布站点
        TEST,//测试站点
    }

    public final static Site SITE = Site.PUBLISH;//地址
    public final static boolean LOG_VISIBLE = true;//日志是否可见
    public static boolean HTTPS = false;//https或http
    public static String ENT_DOMAIN = "crpower";


    //==============这些参数会在HostConfigHelper中替换=====

    public static String CLIENT_ID;
    public static String CLIENT_SECRET;

    public static String URL_API_HOST;
    public static String URL_HOST;
    public static String URL_UPDATE;

    public static String URL_OTHER_LOGIN;
    public static String URL_OTHER_LOGIN_SINA;
    public static String URL_OTHER_LOGIN_QQ;
    public static String URL_OTHER_LOGIN_MD;
    public static String URL_OTHER_LOGIN_SHJD;
    public static String URL_OTHER_LOGIN_XDF;
    public static String URL_ENT;
    public static String URL_REGISTER_ENT;

    public static String URL_OSS_WEBSITE;
    public static String URL_AGREEMENT;
    public static String URL_AGREEMENT_EN;

    public static String URL_USER_AVATAR_FORMAT_BY_NAME;
    public static String URL_USER_AVATAR_FORMAT_BY_MEMBERID;
    public static String URL_USER_AVATAR_FORMAT;

    public static String URL_USER_FEEDBACK_HELP;
    public static String URL_USER_FEEDBACK_BBS;
    public static String URL_USER_FEEDBACK_SUGGESTION;

    public static String FILE_THUMBNAIL_FORMAT;

    public static String SCHEME_PROTOCOL;
    //====================================================

    public final static String[] EXCEPT_INNER_SERVER = {"gokuai.com", "goukuai.cn", "aliyuncs.com"};


    /**
     * @param urlString
     * @return
     */
    public static boolean isInnerServerSite(String urlString) {
        try {
            URL url = new URL(urlString);
            for (String suffix : EXCEPT_INNER_SERVER) {
                if (url.getHost().endsWith(suffix)) {
                    return false;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return true;
    }


}
