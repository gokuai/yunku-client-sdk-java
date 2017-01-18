package com.gokuai.cloud;


import com.gokuai.library.util.DebugFlag;
import org.apache.http.util.TextUtils;

/**
 * Created by Brandon on 15/9/14.
 * <p>
 * <p>
 * 配置云库地址和client、secret
 */
public class ConfigHelper {

    private final static String HTTPS = "https://";
    public final static String HTTP = "http://";

    /**
     * 初始化网络请求的host
     */
    public static void init() {

        //https
        YKConfig.SCHEME_PROTOCOL = YKConfig.HTTPS ? HTTPS : HTTP;

        // FIXME: 联系够快开发人员获取需要的CLIENT_ID，CLIENT_SECRET 参数
        switch (YKConfig.SITE) {
            case PUBLISH:
                YKConfig.URL_API_HOST = "yk3-api.gokuai.com";
                YKConfig.URL_HOST = "yk3.gokuai.com";
                YKConfig.URL_UPDATE = YKConfig.SCHEME_PROTOCOL + "app3.gokuai.com";
                YKConfig.CLIENT_ID = "";
                YKConfig.CLIENT_SECRET = "";
                break;
            case PUBLISH_LINE_B:
                YKConfig.URL_API_HOST = "yk3b-api.goukuai.cn";
                YKConfig.URL_HOST = "yk3b.goukuai.cn";
                YKConfig.URL_UPDATE = YKConfig.SCHEME_PROTOCOL + "app3.gokuai.com";
                YKConfig.CLIENT_ID = "";
                YKConfig.CLIENT_SECRET = "";
                break;
            case TEST:
                YKConfig.URL_API_HOST = "yk3-api.goukuai.cn";
                YKConfig.URL_HOST = "yk3.goukuai.cn";
                YKConfig.URL_UPDATE = YKConfig.SCHEME_PROTOCOL + "app3.goukuai.cn";
                YKConfig.CLIENT_ID = "";
                YKConfig.CLIENT_SECRET = "";
                break;
        }

        if (TextUtils.isEmpty(YKConfig.CLIENT_ID) || TextUtils.isEmpty(YKConfig.CLIENT_SECRET)) {
            throw new IllegalArgumentException("CLIENT_ID CLIENT_SECRET can not be empty!!");
        }

        String url = YKConfig.SCHEME_PROTOCOL + YKConfig.URL_HOST;
        YKConfig.URL_OTHER_LOGIN = url + "/account/other_login";
        YKConfig.URL_ENT = url + "/account/sso_login";
        YKConfig.URL_REGISTER_ENT = url + "/ent/regist";

        YKConfig.URL_OTHER_LOGIN_SINA = url + "/account/oauth?oauth=sina";
        YKConfig.URL_OTHER_LOGIN_QQ = url + "/account/oauth?oauth=qq";
        YKConfig.URL_OTHER_LOGIN_MD = url + "/account/oauth?oauth=mingdao";
        YKConfig.URL_OTHER_LOGIN_SHJD = url + "/autologin/shsmu";
        YKConfig.URL_OTHER_LOGIN_XDF = url + "/autologin/xdf";

        YKConfig.URL_OSS_WEBSITE = url + "/account/sso?url=%s&token=%s&t=%s&n=%s&s=%s";

        YKConfig.URL_USER_AVATAR_FORMAT_BY_NAME = url + "/index/avatar?name=%s";
        YKConfig.URL_USER_AVATAR_FORMAT = url + "/index/avatar?id=%d&org_id=%d&name=%s";
        YKConfig.URL_USER_AVATAR_FORMAT_BY_MEMBERID = url + "/index/avatar?id=%d";

        YKConfig.FILE_THUMBNAIL_FORMAT = url + "/index/thumb?hash=%s&filehash=%s&type=%s&mount_id=%s";

        YKConfig.URL_USER_FEEDBACK_HELP = url + "/help";
        YKConfig.URL_USER_FEEDBACK_SUGGESTION = url + "/account/bbs_feedback";
        YKConfig.URL_USER_FEEDBACK_BBS = url + "/account/bbs?id=";

        DebugFlag.LOG_VISIBLE = YKConfig.LOG_VISIBLE;

    }


}
