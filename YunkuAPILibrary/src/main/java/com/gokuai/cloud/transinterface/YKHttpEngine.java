package com.gokuai.cloud.transinterface;

import com.gokuai.base.*;
import com.gokuai.base.utils.Base64;
import com.gokuai.base.utils.URLEncoder;
import com.gokuai.base.utils.Util;
import com.gokuai.cloud.Constants;
import com.gokuai.cloud.YKConfig;
import com.gokuai.cloud.data.OauthData;
import com.gokuai.library.data.BaseData;
import com.gokuai.library.net.UploadCallBack;
import com.gokuai.library.net.UploadRunnable;
import com.gokuai.library.util.EmojiMapUtil;
import com.gokuai.library.util.MsMultiPartFormData;
import com.google.gson.Gson;
import org.apache.http.util.TextUtils;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Brandon on 14-9-12.
 * 网络接口请求
 */
public class YKHttpEngine extends HttpEngine {

    private final static String LOG_TAG = YKHttpEngine.class.getSimpleName();
    private final static int FILE_SIZE_NONE = -1;

    protected String URL_API = YKConfig.SCHEME_PROTOCOL + YKConfig.URL_API_HOST;
    private String URL_OAUTH = URL_API + "/oauth2/token2";

    protected String token;
    protected String refreshToken;

    private final static String URL_UPDATE_APP = "/update/app";
    private final String URL_API_EXCHANGE_TOKEN = URL_OAUTH;

    // 库账号相关
    private final static String URL_API_CHECK_OAUTH = "/1/account/check_oauth";
    private final static String URL_API_ACCOUNT_ROLES = "/1/account/roles";
    private final static String URL_API_GET_ENT_INFO = "/1/account/ent_info";
    private static final String URL_API_CHANGE_PASSWORD = "/1/account/changepassword";
    private static final String URL_API_GET_DEVICE_LIST = "/1/account/device_list";
    private final static String URL_API_GET_ACCOUNT_INFO = "/1/account/info";
    private final static String URL_API_GET_ACCOUNT_MOUNT = "/1/account/mount";
    private final static String URL_API_GET_ACCOUNT_ENT = "/1/account/ent";
    private final static String URL_API_ORG_MEMBERS_LIST = "/1/contact/org_member_list";
    private static final String URL_API_SET_INFO = "/1/account/set_info";
    private static final String URL_API_SET_DEVICE = "/1/account/set_device";
    private static final String URL_API_CHANGE_DEVICE_STATE = "/1/account/toggle_device";
    private static final String URL_API_DEL_DEVICE = "/1/account/del_device";
    private static final String URL_API_DISABLE_NEW_DEVICE = "/1/account/disable_new_device";
    private static final String URL_API_GET_SERVER_SITE = "/1/account/servers";
    private static final String URL_API_GET_SETTING = "/1/account/setting";
    private static final String URL_API_REGISTER = "/1/account/regist";
    private static final String URL_API_FIND_PASSWORD = "/1/account/findpassword";


    // 库成员相关
    private final static String URL_API_MEMBER_LAST_VISIT = "/1/member/last_visit";
    private static final String URL_API_GET_SHORTCUTS = "/1/member/get_shortcuts";
    private static final String URL_API_ADD_SHORTCUT = "/1/member/add_shortcut";
    private static final String URL_API_DEL_SHORTCUT = "/1/member/del_shortcut";

    //库相关
    private static final String URL_API_LIBRARY_MEMBERS = "/1/library/members";
    private static final String URL_API_LIBRARY_GROUPS = "/1/library/groups";
    private static final String URL_API_UPDATE_LIB_INFO = "/1/library/update";
    private static final String URL_API_GET_DEFAULT_LIB_LOGOS = "/1/library/logos";
    private static final String URL_API_GET_LIB_STORAGE_POINT = "/1/library/servers";
    private static final String URL_API_CREATE_LIB = "/1/library/create";
    private static final String URL_API_UPDATE_LIB = "/1/library/update";
    private static final String URL_API_DEL_LIB = "/1/library/delete";
    private static final String URL_API_QUIT_LIB = "/1/library/quit";
    private static final String URL_API_ADD_LIB_GROUP = "/1/library/add_group";
    private static final String URL_API_DEL_LIB_GROUP = "/1/library/remove_group";
    private static final String URL_API_UPDATE_LIB_GROUP = "/1/library/update_group";
    private static final String URL_API_ADD_LIB_MEMBER = "/1/library/add_member";
    private static final String URL_API_DEL_LIB_MEMBER = "/1/library/remove_member";
    private static final String URL_API_UPDATE_LIB_MEMBER = "/1/library/update_member";
    private static final String URL_API_LIB_INFO = "/1/library/info";

    //通讯录相关
    private static final String URL_API_CONTACT_MEMBER_INFO = "/1/contact/member_info";
    private static final String URL_API_GROUP_TABLE = "/1/contact/group_table";
    private static final String URL_API_TOP_MEMBER_LIST = "/1/contact/top_member_list";
    private final static String URL_API_GROUP_LIST = "/1/contact/group_list";
    private final static String URL_API_GROUP_MEMBER_LIST = "/1/contact/group_member_list";
    private final static String URL_API_ENT_MEMBER_LIST = "/1/contact/ent_member_list";
    private static final String URL_API_UPDATE_CONTACT_GROUP = "/1/contact/update_group";
    private static final String URL_API_DEL_CONTACT_GROUP = "/1/contact/del_group";
    private static final String URL_API_ADD_CONTACT_GROUP = "/1/contact/add_group";
    private static final String URL_API_ADD_CONTACT_MEMBER = "/1/contact/add_member";
    private static final String URL_API_DEL_CONTACT_MEMBER = "/1/contact/remove_member";
    private static final String URL_API_UPDATE_CONTACT_MEMBER = "/1/contact/update_member";
    private static final String URL_API_CONTACT_MEMBER_GROUPS = "/1/contact/member_groups";
    private static final String URL_API_CONTACT_CHECK_EXIST_MEMBER = "/1/contact/check_exist_member";
    private static final String URL_API_ADD_GROUP_MEMBER = "/1/contact/add_group_member";
    private static final String URL_API_REMOVE_GROUP_MEMBER = "/1/contact/remove_group_member";
    private final static String URL_API_CONTACT_SEARCH_GROUP = "/1/contact/search_group";
    private final static String URL_API_OUT_ID_TO_MEMBER_ID = "/1/contact/out_id_to_member_id";
    private final static String URL_API_MEMBER_ID_TO_OUT_ID = "/1/contact/member_id_to_out_id";

    //库文件相关
    private static final String URL_API_COMPARE = "/1/file/compare";
    private final static String URL_API_FILE_COPY = "/1/file/copy";
    private final static String URL_API_FILE_MOVE = "/1/file/move";
    private final static String URL_API_FILE_LIST = "/1/file/ls";
    private final static String URL_API_FILE_LINK = "/1/file/create_file_link";
    private final static String URL_API_GET_MEMBER_PERMISSIONS = "/1/file/get_member_permissions";
    private final static String URL_API_GET_GROUP_PERMISSIONS = "/1/file/get_group_permissions";
    private final static String URL_API_FILE_UPDATE = "/1/file/updates";
    private final static String URL_API_FILE_KEYWORD = "/1/file/keyword";
    private final static String URL_API_FILE_RECENT_MODIFIED = "/1/file/recent_modified";
    private final static String URL_API_FILE_LOCKED = "/1/file/locked";
    private final static String URL_API_FILE_UPLOAD_SERVER = "/1/file/upload_server";
    private final static String URL_API_FILE_UPLOAD = "/1/file/upload";
    private final static String URL_API_FILE_SAVE = "/1/file/save";
    private static final String URL_API_RENAME = "/1/file/rename";
    private static final String URL_API_LOCK = "/1/file/lock";
    private static final String URL_API_GET_URL_BY_FILEHASH = "/1/file/get_url_by_filehash";
    private static final String URL_API_DELETE = "/1/file/del";
    private final static String URL_API_CREATE_OFFLINE = "/1/file/create_offline";
    private static final String URL_API_SET_FILE_PERMISSION = "/1/file/set_permission";
    private static final String URL_API_GET_FAVORITE = "/1/file/favorites";
    private static final String URL_API_ADD_FAVORITE = "/1/file/favorites_add";
    private static final String URL_API_DEL_FAVORITE = "/1/file/favorites_delete";
    private static final String URL_API_CLEAR_FAVORITE = "/1/file/favorites_clear";
    private static final String URL_API_SET_FAVORITE_NAME = "/1/file/set_favorite_name";
    private static final String URL_API_GET_FAVORITE_NAMES = "/1/file/get_favorite_names";

    private static final String URL_API_GET_FILE_SAVE = "/2/file/save";
    private static final String URL_API_GET_FILE_URL = "/2/file/url";
    private final static String URL_API_FILE_SEARCH = "/2/file/search";
    private final static String URL_API_FILE_EXIST = "/2/file/exist";
    private static final String URL_API_GET_FILE_HISTORY = "/2/file/history";
    private static final String URL_API_REVERT = "/2/file/revert";
    private static final String URL_API_OPEN = "/2/file/open";
    private static final String URL_API_GET_FILE_INFO = "/2/file/info";
    private static final String URL_API_CREATE_FOLDER = "/2/file/create_folder";
    private static final String URL_API_CREATE_FILE = "/2/file/create_file";
    private static final String URL_API_GET_FILE_ATTRIBUTE = "/2/file/attribute";
    private static final String URL_API_ADD_FILE_REMARK = "/2/file/add_remark";
    private static final String URL_API_GET_FILE_REMARK_LIST = "/2/file/remark";

    public final static int API_ID_LOGIN = 1;
    public final static int API_ID_ACCOUNT_INFO = 2;
    public final static int API_ID_EXCHANGE_TOKEN = 4;
    public final static int API_ID_FILEADD = 11;
    public final static int API_ID_FILERENAME = 12;
    public final static int API_ID_DELETE = 15;
    public final static int API_ID_FILELOCAL = 19;
    public final static int API_ID_BATCH_DELETE = 34;

    public final static int API_ID_GET_URL_BY_HASH = 49;
    public final static int API_ID_GET_FILE_INFO = 50;

    public final static int API_ID_OTHER_METHOD_LOGIN = 51;

    public final static int API_ID_QUIT_LIB = 87;
    public final static int API_ID_UPDATE_LIB_INFO = 95;

    public final static int API_ID_SETINFO = 106;
    public final static int API_ID_DELETE_LIB = 107;
    public final static int API_ID_REGISTER = 109;
    public final static int API_ID_FIND_PASSWORD = 110;
    public final static int API_ID_FILE_BATCH_COPY = 112;
    public final static int API_ID_FILE_BATCH_MOVE = 113;
    public final static int API_ID_FILE_LIST = 115;
    public final static int API_ID_FILE_SEARCH = 116;
    public final static int API_ID_FILE_HISTORY = 117;
    public final static int API_ID_FILE_LINK = 118;
    public final static int API_ID_LOCK = 120;
    public final static int API_ID_REVERT = 121;
    public final static int API_ID_FILE_EXIST = 123;
    public final static int API_ID_GET_PERMISSION_OF_LIST = 124;
    public final static int API_ID_ADD_SHORT_CUTS = 130;
    public final static int API_ID_DEL_SHORT_CUTS = 131;
    public final static int API_ID_Add_FAVORITE_FILE = 133;
    public final static int API_ID_DEL_FAVORITE_FILE = 134;
    public final static int API_ID_SET_FAVORITE_NAME = 135;

    public final static int API_ID_LIB_LOGOS = 138;
    public final static int API_ID_LIB_STORAGE_POINT = 139;
    public final static int API_ID_LIB_CREATE = 140;
    public final static int API_ID_CONTACT_MEMBER_INFO = 141;

    public final static int API_ID_UPDATE_CONTACT_GROUP = 142;
    public final static int API_ID_DEL_CONTACT_GROUP = 143;
    public final static int API_ID_ADD_CONTACT_GROUP = 144;
    public final static int API_ID_ADD_CONTACT_MEMBER = 145;
    public final static int API_ID_DEL_CONTACT_MEMBER = 146;

    public final static int API_ID_ADD_LIB_GROUP = 147;
    public final static int API_ID_ADD_LIB_MEMBER = 148;
    public final static int API_ID_DEL_LIB_MEMBER = 149;
    public final static int API_ID_DEL_LIB_GROUP = 150;
    public final static int API_ID_UPDATE_LIB_MEMBER_ROLE = 151;
    public final static int API_ID_UPDATE_LIB_GROUP = 152;

    public final static int API_ID_FILE_CREATE_OFFLINE = 200;
    public final static int API_ID_UPDATE_USER_NAME = 153;
    public final static int API_ID_SET_FILE_PERMISSION = 154;
    public final static int API_ID_CHANGE_USER_PASSWORD = 155;
    public final static int API_ID_SINGLE_ENT_INFO = 156;
    public final static int API_ID_UPDATE_USER_PHONE = 157;
    public final static int API_ID_GET_DEVICE_LIST = 158;
    public final static int API_ID_SET_DEVICE = 159;
    public final static int API_ID_CHANGE_DEVICE_STATE = 160;
    public final static int API_ID_DEL_DEVICE = 161;
    public final static int API_ID_DISABLE_NEW_DEVICE = 162;
    public final static int API_ID_UPDATE_LIB_SPACE = 163;
    public final static int API_ID_GET_LIB_INFO = 164;
    public final static int API_ID_UPDATE_CONTACT_MEMBER = 165;

    public final static int API_ID_ADD_FAVORITE_MESSAGE = 169;
    public final static int API_ID_DEL_FAVORITE_MESSAGE = 170;
    public final static int API_ID_OUT_ID_TO_MEMBER_ID = 171;
    public final static int API_ID_MEMBER_ID_TO_OUT_ID = 172;
    public final static int API_ID_GET_MEMBER_PERMISSIONS = 173;
    public final static int API_ID_GET_GROUP_PERMISSIONS = 174;
    public final static int API_ID_GET_NOTICE_READERSHIP = 175;
    public final static int API_ID_GET_LAST_VISIT_FILE = 177;
    public final static int API_ID_GET_FAVOURITE_MESSAGE_LIST = 178;
    public final static int API_ID_GET_MESSAGE_AT = 180;
    public final static int API_ID_GET_MESSAGE_FILE = 181;
    public final static int API_ID_GET_MESSAGE_URL = 182;
    public final static int API_ID_SEND_FILE = 184;
    public final static int API_ID_ADD_FILE_REMARK = 185;
    public final static int API_ID_GET_FILE_REMARK_LIST = 186;
    public final static int API_ID_GET_GROUP_FROM_GROUP = 188;
    public final static int API_ID_GET_MEMBER_FROM_GROUP = 189;
    public final static int API_ID_GET_CONTACT_MEMBER_GROUPS = 190;
    public final static int API_ID_GET_LIBRARY_MEMBERS = 191;
    public final static int API_ID_GET_LIBRARY_GROUPS = 192;
    public final static int API_ID_CONTACT_SEARCH = 193;
    public final static int API_ID_UPDATE_LIB_MEMBER_STATE = 194;
    public final static int API_ID_CONTACT_CHECK_EXIST_MEMBER = 195;

    public final static int API_ID_ADD_GROUP_MEMBER = 196;
    public final static int API_ID_REMOVE_GROUP_MEMBER = 197;
    public final static int API_ID_UPDATE_CONTACT_MEMBER_STATE = 198;
    public final static int API_ID_SEND_FILE_WITH_CONTENT = 199;

    protected YKHttpEngine(String clientId, String clientSecret) {
        super(clientId, clientSecret);
    }

    private static volatile YKHttpEngine instance = null;


    public static YKHttpEngine getInstance() {
        if (instance == null) {
            synchronized (YKHttpEngine.class) {
                if (instance == null) {
                    instance = new YKHttpEngine(YKConfig.CLIENT_ID, YKConfig.CLIENT_SECRET);
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

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }


    public boolean isTokenAvailable() {
        return !TextUtils.isEmpty(token);
    }

    private void reSignParams(HashMap<String, String> params, ArrayList<String> ignoreKeys) {
        reSignParams(params, YKConfig.CLIENT_SECRET, ignoreKeys);
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
        params.put("sign", generateSign(params, secret, ignoreKeys));

    }


    /**
     * 重新获得token
     */
    public boolean refreshToken() {
        if (TextUtils.isEmpty(refreshToken)) {
            return false;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("grant_type", "refresh_token");
        params.put("refresh_token", refreshToken);
        params.put("client_id", YKConfig.CLIENT_ID);
        params.put("sign", generateSign(params));

        String returnString = new RequestHelper().setUrl(URL_OAUTH).setMethod(RequestMethod.POST).setParams(params).executeSync();
        ReturnResult returnResult = ReturnResult.create(returnString);
        if (returnResult != null) {
            OauthData data = OauthData.create(returnResult.getResult());
            if (data != null) {
                data.setCode(returnResult.getStatusCode());
                if (data.getCode() == HttpURLConnection.HTTP_OK) {
                    token = data.getToken();
                    refreshToken = data.getRefresh_token();
                    return true;
                }

                LogPrint.info(LOG_TAG, "token:" + token + "_refreshToken:" + refreshToken);
            }

        }
        return false;
    }


    /**
     * 账号密码登录,会根据 com.gokuai.cloud.YKConfig.ENT_DOMAIN 自动添加前缀
     *
     * @param account
     * @param password
     * @return
     */
    public String loginSync(String account, String password) {

        String url = URL_OAUTH;
        final HashMap<String, String> params = new HashMap<>();
        params.put("grant_type", "password");
        if (!TextUtils.isEmpty(YKConfig.ENT_DOMAIN)) {
            params.put("username", YKConfig.ENT_DOMAIN + "\\" + account);
            params.put("password", Base64.encodeBytes(password.getBytes()));
        } else {
            params.put("username", account);
            params.put("password", Util.convert2MD532(password));
        }

        params.put("client_id", YKConfig.CLIENT_ID);
        params.put("sign", generateSign(params));

        String returnString = new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
        ReturnResult returnResult = ReturnResult.create(returnString);
        if (returnResult != null) {
            OauthData data = OauthData.create(returnResult.getResult());
            if (data != null) {
                token = data.getToken();
                refreshToken = data.getRefresh_token();
            }
        }
        return returnString;

    }

    /**
     * 异步登录方式
     *
     * @param account
     * @param password
     * @param listener
     * @return
     */
    public Thread loginAsync(final String account, final String password, final DataListener listener) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                String returnString = loginSync(account, password);
                if (listener != null) {
                    listener.onReceivedData(API_ID_LOGIN, returnString, -1);
                }
            }
        };

        return new RequestHelper().executeAsyncTask(thread, listener, API_ID_LOGIN);
    }


    /**
     * 企业一站式登录
     *
     * @param account
     * @param clientId
     * @param clientSecret
     * @return
     */

    public String ssoLogin(String account, String clientId, String clientSecret) {

        HashMap<String, String> map = new HashMap<>();
        map.put("account", account);
        map.put("n", YKUtil.getSixRandomChars());
        map.put("t", Util.getUnixDateline() + "");
        map.put("sign", this.generateSign(map, clientSecret));

        String ticket = URLEncoder.encodeUTF8(Base64.encodeBytes(new Gson().toJson(map).getBytes()));
        String url = String.format(YKConfig.URL_ACCOUNT_AUTO_LOGIN, clientId, ticket, "", "json");

        return new RequestHelper().setParams(new HashMap<String, String>())
                .setMethod(RequestMethod.POST).setUrl(url)
                .executeSync();
    }

    /**
     * 第三方登录
     *
     * @param key
     */

    public String otherMethodToLogin(String key) {
        final HashMap<String, String> params = new HashMap<>();
        params.put("gkkey", key);
        params.put("grant_type", "gkkey");
        params.put("client_id", YKConfig.CLIENT_ID);
        params.put("sign", generateSign(params));

        String returnString = new RequestHelper().setParams(params)
                .setUrl(URL_API_EXCHANGE_TOKEN)
                .setMethod(RequestMethod.POST)
                .executeSync();

        ReturnResult returnResult = ReturnResult.create(returnString);
        if (returnResult != null) {
            OauthData data = OauthData.create(returnResult.getResult());
            if (data != null) {
                token = data.getToken();
                refreshToken = data.getRefresh_token();
            }
        }
        return returnString;

    }


    /**
     * 获得用户信息
     *
     * @param listener
     */
    public Thread getAccountInfoAsync(final DataListener listener) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                String returnString = getAccountInfo();
                if (listener != null) {
                    listener.onReceivedData(API_ID_ACCOUNT_INFO, returnString, -1);
                }
            }
        };

        return new RequestHelper().executeAsyncTask(thread, listener, API_ID_ACCOUNT_INFO);
    }

    /**
     * 获取库列表
     *
     * @return
     */
    public String getMountsInfo() {
        String url = URL_API + URL_API_GET_ACCOUNT_MOUNT;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }

    /**
     * 获取企业列表
     *
     * @return
     */
    public String getEntInfo() {
        String url = URL_API + URL_API_GET_ACCOUNT_ENT;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }


    /**
     * 获取快捷方式列表
     *
     * @return
     */
    public String getShortCuts() {
        String url = URL_API + URL_API_GET_SHORTCUTS;
        final HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }


    /**
     * 获取收藏夹信息(文件)
     */
    public String getFavoriteInfo(int type) {
        String url = URL_API + URL_API_GET_FAVORITE;
        final HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("favorite_type", type + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }


    /**
     * 获得用户信息
     *
     * @return
     */
    public String getAccountInfo() {
        String url = URL_API + URL_API_GET_ACCOUNT_INFO;
        final HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }

    /**
     * 新建文件、文件夹
     *
     * @param mountId  文件柜id
     * @param fullpath 文件路径
     * @param filehash 文件hash
     * @param filesize 文件大小
     * @param listener
     */
    public Thread addFileAsync(final int mountId, final String fullpath, final String filehash,
                               final long filesize, final DataListener listener) {

        Thread task = new Thread() {
            @Override
            public void run() {
                String result = addLibraryFile(mountId, fullpath, filehash, filesize);
                if (listener != null) {
                    listener.onReceivedData(API_ID_FILEADD, result, -1);
                }
            }
        };

        return new RequestHelper().executeAsyncTask(task, listener, API_ID_FILEADD);
    }

    /**
     * 重命名文件、文件夹
     */
    public String renameFile(String fullPath, int mountId,
                             final String newName) {

        String url = URL_API + URL_API_RENAME;

        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", mountId + "");
        params.put("newname", newName);
        params.put("fullpath", fullPath);
//        params.put("machine", android.os.Build.BRAND);
        params.put("token", getToken());
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST)
                .setUrl(url).setCheckAuth(true)
                .executeSync();
    }

    /**
     * 批量删除文件文件夹
     *
     * @param mountId
     * @param fullPaths
     */

    public String batchDeleteFileAsync(final int mountId, final ArrayList<String> fullPaths) {
        String url = URL_API + URL_API_DELETE;

        String fullPathsString = "";
        for (String fullPath : fullPaths) {
            fullPathsString += "|" + fullPath;
        }

        fullPathsString = fullPathsString.substring(1);

        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("fullpaths", fullPathsString);
        params.put("mount_id", mountId + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST)
                .setUrl(url).setCheckAuth(true)
                .executeSync();
    }


    /**
     * 获取
     *
     * @param mountId
     * @param fullPath
     * @return
     */
    public String getHistory(final int mountId, final String fullPath) {

        String url = URL_API + URL_API_GET_FILE_HISTORY;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("mount_id", String.valueOf(mountId));
        params.put("fullpath", fullPath);
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.GET).setUrl(url)
                .setCheckAuth(true)
                .executeSync();
    }


    /**
     * 获取部门成员列表
     *
     * @param entId
     * @param groupId
     * @param start
     * @param order
     * @param key
     * @param showChild
     * @return
     */
    public String getMemberFromGroup(int entId, int groupId, int start, int order, String key, int showChild) {
        String url = URL_API + URL_API_GROUP_MEMBER_LIST;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("ent_id", String.valueOf(entId));
        params.put("group_id", String.valueOf(groupId));
        params.put("start", start + "");
        params.put("size", Constants.MEMBERS_LIMIT_SIZE + "");
        params.put("order", order + "");
        params.put("keyword", key);
        params.put("show_child", showChild + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();

    }


    /**
     * 获取部门中的部门
     *
     * @param entId
     * @param groupId
     * @return
     */
    public String getGroupFromGroup(int entId, int groupId) {
        String url = URL_API + URL_API_GROUP_LIST;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("ent_id", String.valueOf(entId));
        params.put("group_id", String.valueOf(groupId));
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setUrl(url)
                .setMethod(RequestMethod.GET)
                .setCheckAuth(true).executeSync();
    }


    /**
     * 获取成员所属部门列表
     *
     * @param entId
     * @param memberId
     * @param listener
     * @return
     */

    public Thread getContactMemberGroups(final int entId, final int memberId, final DataListener listener) {


        String url = URL_API + URL_API_CONTACT_MEMBER_GROUPS;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("ent_id", String.valueOf(entId));
        params.put("_member_id", String.valueOf(memberId));
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.GET)
                .setUrl(url).setCheckAuth(true)
                .executeAsync(listener, API_ID_GET_CONTACT_MEMBER_GROUPS, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return returnString;
                    }
                });
    }


    /**
     * 获取企业成员
     *
     * @param entId
     * @param start
     * @param keyWord
     * @return
     */
    public String getEntMembers(int entId, int start, String keyWord) {
        String url = URL_API + URL_API_ENT_MEMBER_LIST;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("ent_id", String.valueOf(entId));
        params.put("start", start + "");
        params.put("size", Constants.MEMBERS_LIMIT_SIZE + "");
        params.put("keyword", keyWord + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }


    /**
     * 根据id数据获取成员
     *
     * @param entId
     * @param start
     * @param memberIdsStr
     * @return
     */
    public String getEntMembersByIds(int entId, int start, String memberIdsStr) {
        String url = URL_API + URL_API_ENT_MEMBER_LIST;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("ent_id", String.valueOf(entId));
        params.put("start", start + "");
        params.put("_member_ids", memberIdsStr + "");
        params.put("size", Constants.MEMBERS_LIMIT_SIZE + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }

    /**
     * 搜索通讯录成员
     *
     * @param entId
     * @param start
     * @param keyWord
     * @return
     */
    public String searchEntMember(int entId, int start, String keyWord) {
        String url = URL_API + URL_API_ENT_MEMBER_LIST;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("ent_id", String.valueOf(entId));
        params.put("start", start + "");
        params.put("size", Constants.MEMBERS_LIMIT_SIZE + "");
        params.put("keyword", keyWord);
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }


    /**
     * 搜索联系人-部门
     *
     * @param entId
     * @param keyWord
     * @return
     */
    public String searchContactGroups(int entId, String keyWord) {
        String url = URL_API + URL_API_CONTACT_SEARCH_GROUP;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("ent_id", String.valueOf(entId));
        params.put("keyword", keyWord);
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }

    /**
     * 搜索通讯录联系人
     *
     * @param entId
     * @param start
     * @param keyWord
     * @param listener
     * @return
     */

    public Thread searchContactMembers(final int entId, final int start, final String keyWord,
                                       final int size, final DataListener listener) {

        String url = URL_API + URL_API_ENT_MEMBER_LIST;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("ent_id", String.valueOf(entId));
        params.put("start", String.valueOf(start));
        params.put("size", size + "");
        params.put("keyword", keyWord + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.GET)
                .setUrl(url).setCheckAuth(true)
                .executeAsync(listener, API_ID_CONTACT_SEARCH, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return returnString;
                    }
                });
    }


    /**
     * 搜索库成员
     *
     * @param orgId
     * @param start
     * @param keyWord
     * @param size
     * @param withInfo
     * @param withGroup
     * @return
     */
    public String searchLibraryMembers(final int orgId, final int start, final String keyWord,
                                       final int size, final boolean withInfo, final boolean withGroup) {
        String url = URL_API + URL_API_LIBRARY_MEMBERS;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("org_id", String.valueOf(orgId));
        params.put("start", String.valueOf(start));
        params.put("size", size + "");
        params.put("keyword", keyWord + "");
        params.put("with_info", (withInfo ? 1 : 0) + "");
        params.put("with_group", (withGroup ? 1 : 0) + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }

    /**
     * 获取库成员
     *
     * @param orgId
     * @param entId
     * @param start
     * @return
     */
    public String getOrgMembersList(int orgId, int entId, int start) {
        String url = URL_API + URL_API_ORG_MEMBERS_LIST;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("org_id", String.valueOf(orgId));
        params.put("ent_id", String.valueOf(entId));
        params.put("size", Constants.MEMBERS_LIMIT_SIZE + "");
        params.put("start", start + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }


    /**
     * 获取企业所有部门
     *
     * @param entId
     * @param start
     * @return
     */
    public String getGroupTable(int entId, int start) {
        String url = URL_API + URL_API_GROUP_TABLE;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("start", String.valueOf(start));
        params.put("ent_id", String.valueOf(entId));
        params.put("size", Constants.GROUPS_LIMIT_SIZE + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }


    /**
     * 获取成员关系
     *
     * @param orgId
     * @param withInfo
     * @return
     */
    public String getMemberRelative(int orgId, int start, boolean withInfo, boolean withGroup) {
        String url = URL_API + URL_API_LIBRARY_MEMBERS;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("org_id", String.valueOf(orgId));
        params.put("start", start + "");
        params.put("size", Constants.MEMBERS_LIMIT_SIZE + "");
        params.put("with_info", (withInfo ? 1 : 0) + "");
        params.put("with_group", (withGroup ? 1 : 0) + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();

    }

    /**
     * 获取部门关系
     *
     * @param orgId
     * @param withInfo
     * @param withGroup
     * @param listener
     * @return
     */

    public Thread getMemberRelative(final int orgId, final boolean withInfo,
                                    final boolean withGroup, final DataListener listener) {

        String url = URL_API + URL_API_LIBRARY_MEMBERS;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("org_id", String.valueOf(orgId));
        params.put("with_info", (withInfo ? 1 : 0) + "");
        params.put("with_group", (withGroup ? 1 : 0) + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.GET)
                .setUrl(url).setCheckAuth(true)
                .executeAsync(listener, API_ID_GET_LIBRARY_MEMBERS, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return returnString;
                    }
                });
    }


    /**
     * 获取部门关系
     *
     * @param orgId
     * @param withInfo
     * @return
     */
    public String getGroupRelative(int orgId, boolean withInfo) {
        String url = URL_API + URL_API_LIBRARY_GROUPS;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("org_id", String.valueOf(orgId));
        params.put("with_info", (withInfo ? 1 : 0) + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }

    /**
     * 获取部门关系
     *
     * @param orgId
     * @param withInfo
     * @return
     */
    public Thread getGroupRelative(final int orgId, final boolean withInfo, final DataListener listener) {
        String url = URL_API + URL_API_LIBRARY_GROUPS;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("org_id", String.valueOf(orgId));
        params.put("with_info", (withInfo ? 1 : 0) + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.GET)
                .setUrl(url).setCheckAuth(true)
                .executeAsync(listener, API_ID_GET_LIBRARY_GROUPS, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return returnString;
                    }
                });
    }


    /**
     * 退出库
     *
     * @param listener
     * @param orgId
     */
    public Thread quitLib(final DataListener listener, final int orgId) {

        String url = URL_API + URL_API_QUIT_LIB;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("org_id", String.valueOf(orgId));
        params.put("sign", generateSign(params));

        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST)
                .setUrl(url).setCheckAuth(true)
                .executeAsync(listener, API_ID_QUIT_LIB, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return returnString;
                    }
                });

    }

    /**
     * 删除库
     *
     * @param listener
     * @param orgId
     * @return
     */
    public Thread deleteLib(final DataListener listener, final int orgId) {

        String url = URL_API + URL_API_DEL_LIB;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("org_id", String.valueOf(orgId));
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST)
                .setUrl(url).setCheckAuth(true)
                .executeAsync(listener, API_ID_DELETE_LIB, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return returnString;
                    }
                });

    }


    /**
     * 获取默认库图标列表
     */

    public String getDefaultLibLogos() {
        String url = URL_API + URL_API_GET_DEFAULT_LIB_LOGOS;
        final HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }


    /**
     * 设置文件权限
     */

    public String setFilePermission(final int mountId,
                                    final String fullpath, final String permission, final boolean isGroup) {

        String url = URL_API + URL_API_SET_FILE_PERMISSION;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("mount_id", mountId + "");
        params.put("fullpath", fullpath);
        params.put("permission", permission);
        params.put("is_group", (isGroup ? 1 : 0) + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST)
                .setUrl(url).setCheckAuth(true)
                .executeSync();
    }

    /**
     * 获取成员的文件夹权限
     *
     * @param mountId
     * @param fullpath
     * @param start
     * @param size
     * @return
     */
    public String getMemberFolderPermission(final int mountId, final String fullpath, int start, int size) {

        String url = URL_API + URL_API_GET_MEMBER_PERMISSIONS;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("mount_id", mountId + "");
        params.put("fullpath", fullpath);
        params.put("size", size + "");
        params.put("start", start + "");
        params.put("sign", generateSign(params));

        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.GET)
                .setUrl(url).setCheckAuth(true)
                .executeSync();
    }


    /**
     * 获取部门文件夹权限
     *
     * @param mountId
     * @param fullpath
     * @param start
     * @param size     @return
     */
    public String getGroupFolderPermission(final int mountId, final String fullpath, int start, int size) {
        String url = URL_API + URL_API_GET_GROUP_PERMISSIONS;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("mount_id", mountId + "");
        params.put("fullpath", fullpath);
        params.put("size", size + "");
        params.put("start", start + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.GET)
                .setUrl(url).setCheckAuth(true)
                .executeSync();
    }


    /**
     * 获取存储点列表
     */

    public Thread getStoragePoint(final DataListener listener, final int endId) {

        String url = URL_API + URL_API_GET_LIB_STORAGE_POINT;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("ent_id", endId + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.GET)
                .setUrl(url).setCheckAuth(true)
                .executeAsync(listener, API_ID_LIB_STORAGE_POINT, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return returnString;
                    }
                });

    }


    /**
     * 创建库
     */

    public String createLibrary(final String name, int entId,
                                final String description, final String logo, final String storagePoint, long capacity) {

        String url = URL_API + URL_API_CREATE_LIB;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("name", name);
        params.put("ent_id", entId + "");
        params.put("description", description);
        params.put("logo", logo);
        params.put("storage_point", storagePoint);
        params.put("capacity", capacity + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.GET)
                .setUrl(url).setCheckAuth(true)
                .executeSync();

    }


    /**
     * 添加库部门
     */

    public String addLibraryGroup(final int entId, final int orgId,
                                  final int groupId, final int roleId) {

        String url = URL_API + URL_API_ADD_LIB_GROUP;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("ent_id", entId + "");
        params.put("org_id", orgId + "");
        params.put("group_id", groupId + "");
        params.put("role_id", roleId + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST)
                .setUrl(url).setCheckAuth(true)
                .executeSync();

    }

    /**
     * 修改库部门
     *
     * @param entId
     * @param orgId
     * @param groupId
     * @param roleId
     * @return
     */
    public String updateLibraryGroup(final int entId, final int orgId,
                                     final int groupId, final int roleId) {
        String url = URL_API + URL_API_UPDATE_LIB_GROUP;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("ent_id", entId + "");
        params.put("org_id", orgId + "");
        params.put("group_id", groupId + "");
        params.put("role_id", roleId + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST)
                .setUrl(url).setCheckAuth(true)
                .executeSync();
    }


    /**
     * 移除库部门
     */

    public String delLibraryGroup(final int entId, final int orgId,
                                  final int groupId) {


        String url = URL_API + URL_API_DEL_LIB_GROUP;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("ent_id", entId + "");
        params.put("org_id", orgId + "");
        params.put("group_id", groupId + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST)
                .setUrl(url).setCheckAuth(true)
                .executeSync();

    }


    /**
     * 添加库成员
     */

    public String addLibraryMember(final int orgId, final String memberids,
                                   final int roleId) {

        String url = URL_API + URL_API_ADD_LIB_MEMBER;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("org_id", orgId + "");
        params.put("_member_ids", memberids);
        params.put("role_id", roleId + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST)
                .setUrl(url).setCheckAuth(true)
                .executeSync();

    }


    /**
     * 移除库成员
     */

    public String delLibraryMember(final int orgId, final String memberids) {
        String url = URL_API + URL_API_DEL_LIB_MEMBER;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("org_id", orgId + "");
        params.put("_member_ids", memberids);
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST)
                .setUrl(url).setCheckAuth(true)
                .executeSync();

    }


    /**
     * 修改库成员角色
     *
     * @param orgId
     * @param memberids
     * @param roleId
     * @return
     */

    public String changeLibraryMemberRole(final int orgId, final String memberids, final int roleId) {

        String url = URL_API + URL_API_UPDATE_LIB_MEMBER;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("org_id", orgId + "");
        params.put("_member_ids", memberids);
        params.put("role_id", roleId + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST)
                .setUrl(url).setCheckAuth(true)
                .executeSync();

    }


    /**
     * 修改库成员状态
     *
     * @param orgId
     * @param memberids
     * @param state
     * @param listener
     * @return
     */

    public Thread changeLibraryMemberState(final int orgId, final String memberids, final int state, final DataListener listener) {

        String url = URL_API + URL_API_UPDATE_LIB_MEMBER;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("org_id", orgId + "");
        params.put("_member_ids", memberids);
        params.put("state", state + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST)
                .setUrl(url).setCheckAuth(true)
                .executeAsync(listener, API_ID_UPDATE_LIB_MEMBER_STATE, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return returnString;
                    }
                });

    }


    /**
     * 添加收藏(文件)
     */

    public String addFavorities(final int mountid, final String fullpath, final int type) {

        String url = URL_API + URL_API_ADD_FAVORITE;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("mount_id", mountid + "");
        params.put("fullpath", fullpath);
        params.put("favorite_type", type + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST)
                .setUrl(url).setCheckAuth(true)
                .executeSync();
    }


    /**
     * 删除收藏(文件)
     */

    public String delFavorities(final int mountid, final String fullpath, final int type) {

        String url = URL_API + URL_API_DEL_FAVORITE;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("mount_id", mountid + "");
        params.put("fullpath", fullpath);
        params.put("favorite_type", type + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST)
                .setUrl(url).setCheckAuth(true)
                .executeSync();

    }

    /**
     * 清空收藏(文件)
     */

    public String clearFavorities() {

        String url = URL_API + URL_API_CLEAR_FAVORITE;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST)
                .setUrl(url).setCheckAuth(true)
                .executeSync();

    }


    /**
     * 改变收藏夹名称操作
     */

    public String changeFavoritesName(final int type, final String name) {
        String url = URL_API + URL_API_SET_FAVORITE_NAME;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("favorite_type", type + "");
        params.put("favorite_name", name);
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST)
                .setUrl(url).setCheckAuth(true)
                .executeSync();
    }

    /**
     * 获取收藏夹名称
     *
     * @return
     */
    public String getFavoritesName() {
        String url = URL_API + URL_API_GET_FAVORITE_NAMES;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.GET)
                .setUrl(url).setCheckAuth(true)
                .executeSync();
    }

    /**
     * 根据 mountId 获取库信息
     *
     * @param mountId
     * @return
     */

    public String getLibInfoByMountId(int mountId) {
        return getLibInfo(mountId, 0);
    }

    /**
     * 根据 orgId 获取库信息
     *
     * @param orgId
     * @return
     */
    public String getLibInfoByOrgId(int orgId) {
        return getLibInfo(0, orgId);
    }

    /**
     * 获取库信息
     *
     * @param mountId
     * @param orgId
     * @return
     */
    private String getLibInfo(int mountId, int orgId) {

        String url = URL_API + URL_API_LIB_INFO;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        if (mountId > 0) {
            params.put("mount_id", mountId + "");
        } else {
            params.put("org_id", orgId + "");
        }
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }


    /**
     * 改变团队图标操作
     */
    public Thread changeCLoudLogo(final DataListener listener,
                                  final int orgId, final String logoUrl) {

        String url = URL_API + URL_API_UPDATE_LIB_INFO;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("org_id", orgId + "");
        params.put("logo", logoUrl);
        params.put("sign", generateSign(params));

        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.GET)
                .setUrl(url).setCheckAuth(true)
                .executeAsync(listener, API_ID_UPDATE_LIB_INFO, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return returnString;
                    }
                });
    }


    /**
     * 改变团队名称操作
     */
    public Thread changeOrgName(final DataListener listener,
                                final int orgId, final String name) {

        String url = URL_API + URL_API_UPDATE_LIB_INFO;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("org_id", orgId + "");
        params.put("name", name);
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.GET)
                .setUrl(url).setCheckAuth(true)
                .executeAsync(listener, API_ID_UPDATE_LIB_INFO, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return returnString;
                    }
                });
    }

    /**
     * 改变团队描述操作
     */
    public Thread changeCLoudDesc(final DataListener listener,
                                  final int orgId, final String desc) {

        String url = URL_API + URL_API_UPDATE_LIB_INFO;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("org_id", orgId + "");
        params.put("description", desc);
        params.put("sign", generateSign(params));

        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.GET)
                .setUrl(url).setCheckAuth(true)
                .executeAsync(listener, API_ID_UPDATE_LIB_INFO, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return returnString;
                    }
                });
    }


    /**
     * 改变团队空间大小操作
     */
    public Thread changeCLoudSpace(final DataListener listener,
                                   final int orgId, final int capacity) {

        String url = URL_API + URL_API_UPDATE_LIB_INFO;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("org_id", orgId + "");
        params.put("capacity", capacity + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.GET)
                .setUrl(url).setCheckAuth(true)
                .executeAsync(listener, API_ID_UPDATE_LIB_SPACE, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return returnString;
                    }
                });
    }


    /**
     * 更新通讯录中成员信息
     */
    public Thread updateContactMember(final int entId, final String memberIds,
                                      final String groupIds, final int enablePublishNotice,
                                      final int enableCreateOrg, final String memberPhone, final DataListener listener) {

        String url = URL_API + URL_API_UPDATE_CONTACT_MEMBER;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("ent_id", entId + "");
        params.put("_member_ids", memberIds);
        params.put("group_ids", groupIds);
        params.put("enable_publish_notice", enablePublishNotice + "");
        params.put("enable_create_org", enableCreateOrg + "");
        params.put("member_phone", memberPhone);
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST)
                .setUrl(url).setCheckAuth(true)
                .executeAsync(listener, API_ID_UPDATE_CONTACT_MEMBER, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return BaseData.create(returnString);
                    }
                });
    }


    /**
     * 修改通讯录成员的状态(禁用/正常)
     *
     * @param entId
     * @param memberIds
     * @param state
     * @param listener
     * @return
     */
    public Thread updateContactMemberState(final int entId, final String memberIds, final int state, final DataListener listener) {
        String url = URL_API + URL_API_UPDATE_CONTACT_MEMBER;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("ent_id", entId + "");
        params.put("_member_ids", memberIds);
        params.put("state", state + "");
        params.put("sign", generateSign(params));

        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST)
                .setUrl(url).setCheckAuth(true)
                .executeAsync(listener, API_ID_UPDATE_CONTACT_MEMBER_STATE, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return BaseData.create(returnString);
                    }
                });
    }


    /**
     * 添加部门成员
     *
     * @param entId
     * @param groupId
     * @param memberIds
     * @param listener
     * @return
     */
    public Thread addGroupMember(final int entId, final int groupId, final String memberIds, final DataListener listener) {
        String url = URL_API + URL_API_ADD_GROUP_MEMBER;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("ent_id", entId + "");
        params.put("group_id", groupId + "");
        params.put("_member_ids", memberIds);
        params.put("sign", generateSign(params));

        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.GET)
                .setUrl(url).setCheckAuth(true)
                .executeAsync(listener, API_ID_ADD_GROUP_MEMBER, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return BaseData.create(returnString);
                    }
                });
    }


    /**
     * 移除部门成员
     *
     * @param entId
     * @param groupId
     * @param memberIds
     * @param listener
     * @return
     */
    public Thread removeGroupMember(final int entId, final int groupId, final String memberIds, final DataListener listener) {
        String url = URL_API + URL_API_REMOVE_GROUP_MEMBER;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("ent_id", entId + "");
        params.put("group_id", groupId + "");
        params.put("_member_ids", memberIds);
        params.put("sign", generateSign(params));

        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.GET)
                .setUrl(url).setCheckAuth(true)
                .executeAsync(listener, API_ID_REMOVE_GROUP_MEMBER, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return BaseData.create(returnString);
                    }
                });
    }


    /**
     * 改变通讯录中部门名字
     */
    public Thread changeContactGroupName(final int entId, final int groupId,
                                         final String name, final DataListener listener) {

        String url = URL_API + URL_API_UPDATE_CONTACT_GROUP;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("ent_id", entId + "");
        params.put("group_id", groupId + "");
        params.put("name", name);
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST)
                .setUrl(url).setCheckAuth(true)
                .executeAsync(listener, API_ID_UPDATE_CONTACT_GROUP, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return returnString;
                    }
                });
    }

    /**
     * 删除通讯录的部门
     */
    public Thread delContactGroup(final int entId, final int groupId, final DataListener listener) {

        String url = URL_API + URL_API_DEL_CONTACT_GROUP;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("ent_id", entId + "");
        params.put("group_id", groupId + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST)
                .setUrl(url).setCheckAuth(true)
                .executeAsync(listener, API_ID_DEL_CONTACT_GROUP, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return returnString;
                    }
                });
    }


    /**
     * 添加通讯录部门
     */
    public Thread addContactGroup(final int entId, final int groupId, final String name, final DataListener listener) {

        String url = URL_API + URL_API_ADD_CONTACT_GROUP;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("ent_id", entId + "");
        params.put("group_id", groupId + "");
        params.put("name", name);
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST)
                .setUrl(url).setCheckAuth(true)
                .executeAsync(listener, API_ID_ADD_CONTACT_GROUP, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return returnString;
                    }
                });
    }


    /**
     * 添加通讯录成员
     */
    public Thread addContactMember(final int entId, final String name, final String groupId, final String email,
                                   final String phone, final String password, final int publishNotice, final int createOrg, final DataListener listener) {

        String url = URL_API + URL_API_ADD_CONTACT_MEMBER;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("ent_id", entId + "");
        params.put("member_name", name);
        params.put("group_id", groupId);
        params.put("member_email", email);
        params.put("member_phone", phone);
        params.put("member_password", password);
        params.put("enable_publish_notice", publishNotice + "");
        params.put("enable_create_org", createOrg + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST)
                .setUrl(url).setCheckAuth(true)
                .executeAsync(listener, API_ID_ADD_CONTACT_MEMBER, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return returnString;
                    }
                });
    }


    /**
     * 检测成员是否已存在
     *
     * @param entId
     * @param memberEmail
     * @param listener
     * @return
     */

    public Thread checkExistMember(final int entId, final String memberEmail, final DataListener listener) {

        String url = URL_API + URL_API_CONTACT_CHECK_EXIST_MEMBER;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("ent_id", entId + "");
        params.put("member_email", memberEmail);
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.GET)
                .setUrl(url).setCheckAuth(true)
                .executeAsync(listener, API_ID_CONTACT_CHECK_EXIST_MEMBER, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return returnString;
                    }
                });
    }


    /**
     * 删除通讯录成员
     */
    public Thread delContactMember(final int entId, final String removeMemberIds, final int transferMemberId, final DataListener listener) {


        String url = URL_API + URL_API_DEL_CONTACT_MEMBER;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("ent_id", entId + "");
        params.put("_member_ids", removeMemberIds);
        params.put("_to_member_id", transferMemberId + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST)
                .setUrl(url).setCheckAuth(true)
                .executeAsync(listener, API_ID_DEL_CONTACT_MEMBER, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return BaseData.create(returnString);
                    }
                });
    }


    /**
     * 添加快捷方式
     */

    public Thread addShortCuts(final DataListener listener,
                               final int value, final int type) {

        String url = URL_API + URL_API_ADD_SHORTCUT;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("value", value + "");
        params.put("type", type + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST)
                .setUrl(url).setCheckAuth(true)
                .executeAsync(listener, API_ID_ADD_SHORT_CUTS, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return returnString;
                    }
                });
    }


    /**
     * 删除快捷方式
     */

    public Thread delShortCuts(final DataListener listener,
                               final int value, final int type) {

        String url = URL_API + URL_API_DEL_SHORTCUT;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("value", value + "");
        params.put("type", type + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST)
                .setUrl(url).setCheckAuth(true)
                .executeAsync(listener, API_ID_DEL_SHORT_CUTS, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return returnString;
                    }
                });
    }


    public String getServerSite(String type) {
        return getServerSite(type, "");
    }

    public String getServerSite(String type, String storagePoint) {
        String url = URL_API + URL_API_GET_SERVER_SITE;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        if (!TextUtils.isEmpty(storagePoint)) {
            params.put("storage_point", storagePoint);
        }
        params.put("type", type);
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).setCheckAuth(true).executeSync();
    }


    /**
     * 获取账号设置
     *
     * @return
     */
    public String getAccountSetting() {
        String url = URL_API + URL_API_GET_SETTING;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }


    //更改用户显示名
    public Thread uploadUserName(final DataListener listener, final String name) {

        String url = URL_API + URL_API_SET_INFO;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("member_name", name);
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).
                setMethod(RequestMethod.POST)
                .setUrl(url)
                .setCheckAuth(true).executeAsync(listener, API_ID_UPDATE_USER_NAME, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return BaseData.create(returnString);
                    }
                });
    }


    /**
     * 更改用户联系电话
     *
     * @param listener
     * @param mobile
     * @return
     */
    public Thread uploadUserPhone(final DataListener listener, final String mobile) {

        String url = URL_API + URL_API_SET_INFO;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("mobile", mobile);
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).
                setMethod(RequestMethod.POST)
                .setUrl(url)
                .setCheckAuth(true).executeAsync(listener, API_ID_UPDATE_USER_PHONE, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return BaseData.create(returnString);
                    }
                });
    }


    /**
     * 更改设备名称
     *
     * @param listener
     * @param device
     * @param info
     * @return
     */
    public Thread setDeviceInfo(final DataListener listener, final String device, final String info) {

        String url = URL_API + URL_API_SET_DEVICE;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("device", device);
        params.put("info", info);
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).
                setMethod(RequestMethod.POST)
                .setUrl(url)
                .setCheckAuth(true).executeAsync(listener, API_ID_SET_DEVICE, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return BaseData.create(returnString);
                    }
                });
    }


    /**
     * 更改设备状态
     *
     * @param listener
     * @param deviceId
     * @param state
     * @return
     */
    public Thread changeDeviceState(final DataListener listener, final int deviceId, final int state) {

        String url = URL_API + URL_API_CHANGE_DEVICE_STATE;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("device_id", deviceId + "");
        params.put("state", state + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).
                setMethod(RequestMethod.POST)
                .setUrl(url)
                .setCheckAuth(true).executeAsync(listener, API_ID_CHANGE_DEVICE_STATE, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return BaseData.create(returnString);
                    }
                });
    }


    /**
     * 删除设备
     *
     * @param listener
     * @param deviceId
     * @return
     */
    public Thread delDevice(final DataListener listener, final int deviceId) {

        String url = URL_API + URL_API_DEL_DEVICE;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("device_id", deviceId + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).
                setMethod(RequestMethod.POST)
                .setUrl(url)
                .setCheckAuth(true).executeAsync(listener, API_ID_DEL_DEVICE, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return BaseData.create(returnString);
                    }
                });
    }


    /**
     * 是否禁止新设备登录
     *
     * @param listener
     * @param state
     * @return
     */
    public Thread changeNewDeviceState(final DataListener listener, final int state) {

        String url = URL_API + URL_API_DISABLE_NEW_DEVICE;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("state", state + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).
                setMethod(RequestMethod.POST)
                .setUrl(url)
                .setCheckAuth(true).executeAsync(listener, API_ID_DISABLE_NEW_DEVICE, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return BaseData.create(returnString);
                    }
                });
    }

    /**
     * 注册
     *
     * @param listener
     * @param name
     * @param email
     * @param password
     * @return
     */
    public Thread registerAsync(final DataListener listener, final String name, final String email, final String password) {

        String url = URL_API + URL_API_REGISTER;
        HashMap<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("password", password);
        params.put("client_id", YKConfig.CLIENT_ID);
        params.put("sign", generateSign(params));

        return new RequestHelper().setParams(params).
                setMethod(RequestMethod.POST)
                .setUrl(url)
                .executeAsync(listener, API_ID_REGISTER, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return BaseData.create(returnString);
                    }
                });

    }

    /**
     * 找回密码
     *
     * @param listener
     * @param email
     * @return
     */
    public Thread findPassword(final DataListener listener, final String email) {

        String url = URL_API + URL_API_FIND_PASSWORD;
        HashMap<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("client_id", YKConfig.CLIENT_ID);
        params.put("sign", generateSign(params));

        return new RequestHelper().setParams(params).
                setMethod(RequestMethod.POST)
                .setUrl(url)
                .executeAsync(listener, API_ID_FIND_PASSWORD, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return BaseData.create(returnString);
                    }
                });
    }


    /**
     * 获取对应路径的权限
     *
     * @param mountId
     * @param fullPath
     * @return
     */
    public String getPermissionOfList(int mountId, String fullPath) {
        String url = URL_API + URL_API_FILE_LIST;

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("mount_id", String.valueOf(mountId));
        parameters.put("fullpath", fullPath);
        parameters.put("token", getToken());
        parameters.put("size", FILE_SIZE_NONE + "");
        parameters.put("sign", generateSign(parameters));
        return new RequestHelper().setParams(parameters).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }

    /**
     * 获取文件列表
     *
     * @param mountId
     * @param fullPath
     * @param start
     * @param size     @return
     */
    public String getFileListSync(final int mountId, final String fullPath, int start, int size) {
        String url = URL_API + URL_API_FILE_LIST;

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("mount_id", String.valueOf(mountId));
        parameters.put("fullpath", fullPath);
        parameters.put("token", getToken());
        parameters.put("size", size + "");
        parameters.put("start", start + "");
        parameters.put("sign", generateSign(parameters));
        return new RequestHelper().setParams(parameters).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }

    /**
     * 制定hash文件
     *
     * @param mountId
     * @param start
     * @param size
     * @return
     */
    public String getFileListByHashs(int mountId, int start, int size, String[] hashs) {
        String url = URL_API + URL_API_FILE_LIST;
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("token", getToken());
        parameters.put("mount_id", mountId + "");
        parameters.put("size", size + "");
        parameters.put("start", start + "");
        parameters.put("hashs", new Gson().toJson(hashs));
        parameters.put("sign", generateSign(parameters));
        return new RequestHelper().setParams(parameters).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();

    }


    /**
     * 文件复制
     *
     * @param mountId        要复制的mount_id
     * @param fullPaths
     * @param targetMountId  制到的mount_id
     * @param targetFullPath 复制到的路径   @return
     */
    public String fileBatchCopy(final int mountId, final ArrayList<String> fullPaths,
                                final int targetMountId, final String targetFullPath) {

        String url = URL_API + URL_API_FILE_COPY;

        String fullPathsString = "";
        for (String fullPath : fullPaths) {
            fullPathsString += "|" + fullPath;
        }

        fullPathsString = fullPathsString.substring(1);

        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", String.valueOf(mountId));
        params.put("fullpaths", fullPathsString);
        params.put("target_mount_id", String.valueOf(targetMountId));
        params.put("target_fullpath", targetFullPath);
        params.put("token", getToken());
        params.put("sign", generateSign(params));

        return new RequestHelper().setParams(params).
                setMethod(RequestMethod.POST)
                .setUrl(url)
                .setCheckAuth(true).executeSync();
    }

    /**
     * 文件移动
     *
     * @param mountId        要移动的mount_id
     * @param fullPaths
     * @param targetMountId  移动到的mount_id
     * @param targetFullPath 移动到的路径   @return
     */
    public String fileBatchMove(final int mountId, final ArrayList<String> fullPaths,
                                final int targetMountId, final String targetFullPath) {

        String url = URL_API + URL_API_FILE_MOVE;

        String fullPathsString = "";
        for (String fullPath : fullPaths) {
            fullPathsString += "|" + fullPath;
        }

        fullPathsString = fullPathsString.substring(1);

        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", String.valueOf(mountId));
        params.put("fullpaths", fullPathsString);
        params.put("target_mount_id", String.valueOf(targetMountId));
        params.put("target_fullpath", targetFullPath);
        params.put("token", getToken());
        params.put("sign", generateSign(params));

        return new RequestHelper().setParams(params).
                setMethod(RequestMethod.POST)
                .setUrl(url)
                .setCheckAuth(true).executeSync();
    }

    /**
     * 搜索文件
     *
     * @param keyword
     * @param mountId
     * @return
     */
    public String fileSearch(final String keyword, final int mountId) {

        String url = URL_API + URL_API_FILE_SEARCH;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("keyword", keyword);
        params.put("mount_id", String.valueOf(mountId));
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).
                setMethod(RequestMethod.GET)
                .setUrl(url)
                .setCheckAuth(true).executeSync();
    }

    /**
     * 文件锁定／解锁
     *
     * @param fullPath
     * @param mountId
     * @param lock
     * @param listener
     * @return
     */
    public Thread lock(final String fullPath, final int mountId, final int lock, final DataListener listener) {


        Thread task = new Thread() {
            @Override
            public void run() {
                String result = lock(fullPath, mountId, lock);
                if (listener != null) {
                    listener.onReceivedData(API_ID_LOCK, result, -1);
                }
            }
        };


        return new RequestHelper().executeAsyncTask(task, listener, API_ID_LOCK);

    }

    /**
     * 锁定文件
     *
     * @param fullPath 文件路径
     * @param mountId  文件库id
     * @param lock     1 lock 0 unlock
     * @return
     */
    public String lock(final String fullPath, final int mountId, int lock) {


        String url = URL_API + URL_API_LOCK;
        HashMap<String, String> params = new HashMap<>();
        params.put("fullpath", fullPath);
        params.put("mount_id", mountId + "");
        params.put("lock", (lock == 0 ? "unlock" : "lock"));
        params.put("token", getToken());
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).setCheckAuth(true).executeSync();
    }

    /**
     * 根据文件hash获取mountId
     *
     * @param mountId
     * @param hash
     * @param listener
     * @return
     */
    public Thread getFileInfoByHash(final int mountId, final String hash, final DataListener listener) {

        Thread task = new Thread() {
            @Override
            public void run() {
                String result = getFileInfoByHash(hash, mountId);
                if (listener != null) {
                    listener.onReceivedData(API_ID_GET_FILE_INFO, result, -1);
                }
            }
        };

        return new RequestHelper().executeAsyncTask(task, listener, API_ID_GET_FILE_INFO);
    }


    /**
     * 文件是否存在
     *
     * @param hash
     * @param mountId
     * @return
     */
    public String fileExistByHash(final String hash, final int mountId) {

        String url = URL_API + URL_API_FILE_EXIST;
        HashMap<String, String> params = new HashMap<>();
        params.put("hash", hash);
        params.put("mount_id", mountId + "");
        params.put("token", getToken());
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST).setUrl(url)
                .setCheckAuth(true)
                .executeSync();
    }


    /**
     * 文件是否存在
     *
     * @param fullPath
     * @param mountId
     * @return
     */
    public String fileExistByFullPath(final String fullPath, final int mountId) {

        String url = URL_API + URL_API_FILE_EXIST;
        HashMap<String, String> params = new HashMap<>();
        params.put("fullpath", fullPath);
        params.put("mount_id", mountId + "");
        params.put("token", getToken());
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST).setUrl(url)
                .setCheckAuth(true)
                .executeSync();
    }

    /**
     * 恢复文件
     *
     * @param fullPath
     * @param mountId
     * @param hid
     * @return
     */
    public String revert(final String fullPath, final int mountId, final String hid) {


        String url = URL_API + URL_API_REVERT;
        HashMap<String, String> params = new HashMap<>();
        params.put("fullpath", fullPath);
        params.put("mount_id", mountId + "");
        params.put("hid", hid);
        params.put("token", getToken());
        params.put("sign", generateSign(params));

        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST).setUrl(url)
                .setCheckAuth(true)
                .executeSync();

    }

    public String exchangeToken(String exchangeToken) {
        return exchangeToken(exchangeToken, "", "", YKConfig.ENT_DOMAIN, "");
    }

    private String exchangeToken(String exchangeToken, String username, String password, String domain, String auth) {

        String url = URL_API_EXCHANGE_TOKEN;
        final HashMap<String, String> params = new HashMap<>();
        params.put("grant_type", "exchange_token");
        if (!TextUtils.isEmpty(exchangeToken)) {
            params.put("exchange_token", exchangeToken);
        }
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            params.put("username", username);
            params.put("password", Base64.encodeBytes(password.getBytes()));
        }
        params.put("domain", domain);
        params.put("auth", auth);
        params.put("client_id", YKConfig.CLIENT_ID);
        params.put("sign", generateSign(params));

        String returnString = new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
        ReturnResult returnResult = ReturnResult.create(returnString);
        if (returnResult != null) {
            OauthData data = OauthData.create(returnResult.getResult());
            if (data != null) {
                token = data.getToken();
                refreshToken = data.getRefresh_token();
            }
        }

        return returnString;

    }

    /**
     * 获取sso签名后的地址
     *
     * @param url
     * @param n
     * @param t
     * @return
     */
    public String getSsoUrl(String url, int n, long t) {
        String ossUrl = "";
        if (token == null) {
            refreshToken();
        }
        if (token != null) {
            final HashMap<String, String> params = new HashMap<>();
            params.put("url", url);
            params.put("n", n + "");
            params.put("t", t + "");
            params.put("token", token);
            ossUrl = String.format(YKConfig.URL_OSS_WEBSITE, URLEncoder.encodeUTF8(url), token, t + "", n, generateSign(params));
        }
        return ossUrl;
    }


    /**
     * 获取文件分享链接
     *
     * @param fullPath
     * @param mountId
     * @param deadline
     * @param auth
     * @param password
     * @param scope
     * @param day
     * @return
     */


    public String getFileLink(String fullPath, int mountId, final String deadline,
                              final String auth, final String password, final String scope, final String day) {
        String filePath = fullPath.endsWith("/") ? fullPath.substring(0, fullPath.length() - 1) : fullPath;
        String url = URL_API + URL_API_FILE_LINK;
        final HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", mountId + "");
        params.put("fullpath", filePath);
        params.put("deadline", deadline);
        params.put("auth", auth);
        params.put("password", password);
        params.put("scope", scope);
        params.put("day", day);
        params.put("token", getToken());
        params.put("sign", generateSign(params));

        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST).setUrl(url)
                .setCheckAuth(true)
                .executeSync();

    }


    /**
     * 获取用户最近访问文件
     *
     * @param listener
     * @param size
     * @return
     */

    public Thread getLastVisitFile(final DataListener listener, final int size) {

        String url = URL_API + URL_API_MEMBER_LAST_VISIT;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("size", size + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.GET).setUrl(url)
                .setCheckAuth(true)
                .executeAsync(listener, API_ID_GET_LAST_VISIT_FILE, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return returnString;
                    }
                });
    }

    /**
     * 文件添加备注
     *
     * @param mountId
     * @param fullPath
     * @return
     */

    public String addFileRemark(final int mountId, final String fullPath, final String message) {

        String url = URL_API + URL_API_ADD_FILE_REMARK;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("mount_id", mountId + "");
        params.put("fullpath", fullPath);
        params.put("message", EmojiMapUtil.replaceUnicodeEmojis(message));
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST).setUrl(url)
                .setCheckAuth(true)
                .executeSync();
    }


    /**
     * 文件备注列表
     *
     * @param mountId
     * @param fullPath
     * @param start
     * @param size
     * @return
     */

    public String getFileRemarkList(final int mountId, final String fullPath, final int start, final int size) {

        String url = URL_API + URL_API_GET_FILE_REMARK_LIST;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("mount_id", mountId + "");
        params.put("fullpath", fullPath);
        params.put("start", start + "");
        params.put("size", size + "");
        params.put("sign", generateSign(params));

        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.GET).setUrl(url)
                .setCheckAuth(true)
                .executeSync();
    }


    /**
     * 获取成员信息
     *
     * @param entId
     * @param memberId
     * @param listener
     * @return
     */
    public Thread getMemberInfo(final int entId, final int memberId, final DataListener listener) {

        Thread task = new Thread() {
            @Override
            public void run() {
                String result = getMemberInfo(entId, memberId);
                if (listener != null) {
                    listener.onReceivedData(API_ID_CONTACT_MEMBER_INFO, result, -1);
                }
            }
        };

        return new RequestHelper().executeAsyncTask(task, listener, API_ID_CONTACT_MEMBER_INFO);

    }

    /**
     * 获取成员信息
     *
     * @param entId
     * @param memberId
     * @return
     */
    public String getMemberInfo(int entId, int memberId) {
        String url = URL_API + URL_API_CONTACT_MEMBER_INFO;
        final HashMap<String, String> params = new HashMap<>();
        params.put("ent_id", entId + "");
        params.put("_member_id", memberId + "");
        params.put("token", getToken());
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }


    /**
     * 离线下载
     *
     * @param mountId
     * @param path
     * @param fileName
     * @param downloadUrl
     * @param token
     * @param listener
     * @return
     */
    public Thread createOffline(int mountId, String path, String fileName, String downloadUrl, String token, final DataListener listener) {


        String url = URL_API + URL_API_CREATE_OFFLINE;
        final HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", mountId + "");
        params.put("path", path);
        params.put("filename", fileName);
        params.put("url", downloadUrl);
        params.put("token", TextUtils.isEmpty(token) ? getToken() : token);
        params.put("sign", generateSign(params));

        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST).setUrl(url)
                .setCheckAuth(true)
                .executeAsync(listener, API_ID_FILE_CREATE_OFFLINE, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return BaseData.create(returnString);
                    }
                });

    }


//    /**
//     * 获取成员差量数据
//     *
//     * @param size
//     * @param dateline
//     * @return
//     */
//    public DataDifferentialListData getMemberDifferential(String url, int size, long dateline) {
//        final HashMap<String ,String> params = new HashMap<>();
//        params.put("dateline", dateline + "");
//        params.put("size", size + "");
//        params.put("token", getToken());
//        params.put("sign", generateSign(params));
//        String returnString  = sendRequestWithAuth(url, "GET", params, null);
//        return DataDifferentialListData.create(b);
//
//    }

    /**
     * outId 转 memberId
     *
     * @param entId
     * @param listener
     * @return
     */
    public Thread getMemberIdFromOutId(final int[] outIds, final int entId, final DataListener listener) {


        final String url = URL_API + URL_API_OUT_ID_TO_MEMBER_ID;

        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("ent_id", entId + "");
        params.put("_out_ids", Util.intArrayToString(outIds, ","));
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST).setUrl(url)
                .setCheckAuth(true)
                .executeAsync(listener, API_ID_OUT_ID_TO_MEMBER_ID, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return returnString;
                    }
                });

    }

    /**
     * memberId 转 outId 同步方法
     *
     * @return
     */
    public String getOutIdFromMemberIdSync(int[] memberIds, int entId) {
        String url = URL_API + URL_API_MEMBER_ID_TO_OUT_ID;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", TextUtils.isEmpty(token) ? getToken() : token);
        params.put("ent_id", entId + "");
        params.put("_member_ids", Util.intArrayToString(memberIds, ","));
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }


    /**
     * memberId 转  outId
     *
     * @return
     */
    public Thread getOutIdFromMemberId(final int[] memberIds, final int entId, final DataListener listener) {

        Thread task = new Thread() {
            @Override
            public void run() {
                String result = getOutIdFromMemberIdSync(memberIds, entId);
                if (listener != null) {
                    listener.onReceivedData(API_ID_MEMBER_ID_TO_OUT_ID, result, -1);
                }
            }
        };

        return new RequestHelper().executeAsyncTask(task, listener, API_ID_MEMBER_ID_TO_OUT_ID);
    }


    /**
     * 根据文件路径
     *
     * @param mountId
     * @param fullPath
     * @param hid
     * @return
     */
    public String getDownloadFileUrlByPath(final int mountId, final String fullPath, String hid) {

        String url = URL_API + URL_API_OPEN;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("mount_id", String.valueOf(mountId));
        params.put("fullpath", fullPath);
        params.put("hid", hid);
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }

    /**
     * 根据文件filehash获取文件路径
     *
     * @param mountId
     * @param filehash
     * @return
     */
    public String getDownloadFileUrlByFileHash(int mountId, String filehash) {


        String url = URL_API + URL_API_GET_URL_BY_FILEHASH;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("mount_id", String.valueOf(mountId));
        params.put("filehash", filehash);
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }

    /**
     * 根据filehash获取文件嘻嘻
     *
     * @param mountId
     * @param fileHash
     * @param net
     * @param isOpen
     * @param listener
     * @return
     */
    public Thread getUrlByFileHash(final int mountId, final String fileHash, final String net, final boolean isOpen, final DataListener listener) {

        String url = URL_API + URL_API_GET_URL_BY_FILEHASH;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("mount_id", String.valueOf(mountId));
        params.put("filehash", fileHash);
        params.put("net", net);
        params.put("open", (isOpen ? 1 : 0) + "");
        params.put("sign", generateSign(params));
        return new RequestHelper().setParams(params).
                setMethod(RequestMethod.GET)
                .setUrl(url)
                .setCheckAuth(true).executeAsync(listener, API_ID_GET_URL_BY_HASH, new RequestHelperCallBack() {
                    @Override
                    public Object getReturnData(String returnString) {
                        return returnString;
                    }
                });
    }

    /**
     * 获取单文件数据，只能在线程或后台执行
     *
     * @return
     */
    public String getFileInfoSync(final String fullPath, int mountId, String hid) {


        final HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", String.valueOf(mountId));
        params.put("fullpath", String.valueOf(fullPath));
        params.put("hid", String.valueOf(hid));
        params.put("token", getToken());
        params.put("sign", generateSign(params));
        String url = URL_API + URL_API_GET_FILE_INFO;

        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }


    /**
     * 获取文件夹属性
     *
     * @param mountId
     * @param fullPath
     * @param hash
     * @return
     */
    public String getFolderAttribute(int mountId, String fullPath, String hash) {

        final HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", String.valueOf(mountId));
        params.put("fullpath", fullPath);
        params.put("hash", hash);
        params.put("token", getToken());
        params.put("sign", generateSign(params));

        String url = URL_API + URL_API_GET_FILE_ATTRIBUTE;

        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }


    /**
     * 根据hash获取文件信息
     *
     * @param hash
     * @param mountId
     * @return
     */
    public String getFileInfoByHash(String hash, int mountId) {


        final HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", String.valueOf(mountId));
        params.put("hash", hash);
        params.put("token", getToken());
        params.put("sign", generateSign(params));


        String url = URL_API + URL_API_GET_FILE_INFO;

        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }


    /**
     * 新建文件、文件夹
     *
     * @param mountId  文件柜id
     * @param fullpath 文件路径
     * @param filehash 文件hash
     * @param filesize 文件大小
     */
    public String addFile(int mountId, String fullpath, String filehash, long filesize) {
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", String.valueOf(mountId));

        params.put("fullpath", fullpath);
        params.put("filehash", filehash);
        params.put("filesize", String.valueOf(filesize));
        params.put("token", getToken());

        ArrayList<String> ignoreKeys = new ArrayList<>();
        ignoreKeys.add("filehash");
        ignoreKeys.add("filesize");

        params.put("sign", generateSign(params, ignoreKeys));

        String url = URL_API + URL_API_CREATE_FILE;

        return new RequestHelper().setParams(params)
                .setUrl(url)
                .setMethod(RequestMethod.POST)
                .setCheckAuth(true)
                .setIgnoreKeys(ignoreKeys)
                .executeSync();

    }

    /**
     * 创建文件夹
     *
     * @param mountId
     * @param fullpath
     * @return
     */
    public String createFolder(int mountId, String fullpath) {
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", String.valueOf(mountId));
        params.put("fullpath", fullpath);
        params.put("token", getToken());
        params.put("sign", generateSign(params));
        String url = URL_API + URL_API_CREATE_FOLDER;
        return new RequestHelper().setParams(params)
                .setUrl(url)
                .setMethod(RequestMethod.POST)
                .setCheckAuth(true)
                .executeSync();
    }


    /**
     * 添加库文件
     *
     * @param mountId
     * @param fullpath
     * @param filehash
     * @param filesize
     * @return
     */
    public String addLibraryFile(int mountId, String fullpath, String filehash, long filesize) {
        return addFile(mountId, fullpath, filehash, filesize);
    }

    /**
     * 文件最近更新列表
     *
     * @param mountId
     * @param msdateline
     * @param size
     * @return
     */
    public String getFileUpdates(int mountId, long msdateline, int size) {
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", String.valueOf(mountId));
        params.put("dateline", String.valueOf(msdateline));
        params.put("size", String.valueOf(size));
        params.put("token", getToken());
        params.put("sign", generateSign(params));
        String url = URL_API + URL_API_FILE_UPDATE;

        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }

    /**
     * 设置文件描述
     *
     * @param mountId
     * @param fullPath
     * @param keywords
     * @return
     */
    public String setFileKeyWord(int mountId, String fullPath, String keywords) {
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", String.valueOf(mountId));
        params.put("fullpath", fullPath);
        params.put("keywords", keywords);
        params.put("token", getToken());
        params.put("sign", generateSign(params));
        String url = URL_API + URL_API_FILE_KEYWORD;
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).setCheckAuth(true).executeSync();
    }

    /**
     * 获取24小时内修改的文件
     */
    public String getFileRecentModified() {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("sign", generateSign(params));
        String url = URL_API + URL_API_FILE_RECENT_MODIFIED;
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }

    /**
     * 获取锁定文件信息
     *
     * @return
     */
    public String getFileLocked() {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("sign", generateSign(params));
        String url = URL_API + URL_API_FILE_LOCKED;
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();

    }

    /**
     * 获取锁定文件信息
     *
     * @return 返回上传地址
     */
    private String getUploadSever(int mountId, String fullPath) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("fullpath", fullPath + "");
        params.put("mount_id", mountId + "");
        params.put("sign", generateSign(params));
        String url = URL_API + URL_API_FILE_UPLOAD_SERVER;
        String returnString = new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET)
                .setCheckAuth(true).executeSync();

        ReturnResult returnResult = ReturnResult.create(returnString);
        if (returnResult != null) {
            if (returnResult.getStatusCode() == HttpURLConnection.HTTP_OK) {
                try {
                    JSONObject json = new JSONObject(returnResult.getResult());
                    return json.getString("server");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return "";

    }

    /**
     * 上传文件
     *
     * @param mountId
     * @param fullPath
     */
    public String uploadFile(int mountId, String fullPath, String opName, String localPath, boolean overWrite) {
        String server = getUploadSever(mountId, fullPath);
        LogPrint.info(LOG_TAG, "upload server:" + server);

        String url = server + URL_API_FILE_UPLOAD;

        LogPrint.info(LOG_TAG, "upload url:" + url);


        FileInputStream stream;
        File file = new File(localPath);
        try {
            stream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        }

        String fileName = Util.getNameFromPath(fullPath);

        try {
            HashMap<String, String> params = new HashMap<>();
            params.put("token", getToken());
            params.put("mount_id", mountId + "");
            params.put("fullpath", fullPath);
            params.put("op_name", opName);
            params.put("overwrite", (overWrite ? 1 : 0) + "");

            MsMultiPartFormData multipart = new MsMultiPartFormData(url, "UTF-8");
            multipart.addFormField("token", getToken());
            multipart.addFormField("mount_id", mountId + "");
            multipart.addFormField("fullpath", fullPath);
            multipart.addFormField("op_name", opName);
            multipart.addFormField("overwrite", (overWrite ? 1 : 0) + "");
            multipart.addFormField("filefield", "file");
            multipart.addFormField("sign", generateSign(params));

            multipart.addFilePart("file", stream, fileName);

            return multipart.finish();

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        return "";
    }

    /**
     * 分块上传
     *
     * @param mountId
     * @param fullPath
     * @param localFilePath
     * @param callBack
     * @return
     */

    public UploadRunnable uploadByBlock(int mountId, String fullPath, String localFilePath,
                                        int rangSize, UploadCallBack callBack) {
        UploadRunnable uploadRunnable = new UploadRunnable(localFilePath, mountId, fullPath, Util.getUnixDateline(), this, callBack, rangSize);
        Thread thread = new Thread(uploadRunnable);
        thread.start();
        return uploadRunnable;
    }

    /**
     * 通过文件流分块上传
     *
     * @param fullPath
     * @param localFilePath
     * @param callBack
     * @return
     */
    public UploadRunnable uploadByBlock(int mountId, String fullPath, InputStream localFilePath,
                                        int rangSize, UploadCallBack callBack) {
        UploadRunnable uploadRunnable = new UploadRunnable(localFilePath, mountId, fullPath, Util.getUnixDateline(), this, callBack, rangSize);
        Thread thread = new Thread(uploadRunnable);
        thread.start();
        return uploadRunnable;
    }


//    /**
//     * 文件转存
//     *
//     * @param mountId
//     * @param fileName
//     * @param fileHash
//     * @param fileSize
//     * @param targetMountId
//     * @param targetFullPath
//     */
//    public String fileSave(int mountId, String fileName, String fileHash, long fileSize, int targetMountId, String targetFullPath) {
//        HashMap<String, String> params = new HashMap<>();
//        params.put("token", getToken());
//        params.put("mount_id", mountId + "");
//        params.put("filename", fileName + "");
//        params.put("filehash", fileHash + "");
//        params.put("filesize", fileSize + "");
//        params.put("target_mount_id", targetMountId + "");
//        params.put("target_fullpath", targetFullPath + "");
//        params.put("sign", generateSign(params));
//        String url = URL_API + URL_API_FILE_SAVE;
//        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).setCheckAuth(true).executeSync();
//    }

    /**
     * 文件转存
     */
    public String fileSave(int mountId, String fileName, String fileHash, long fileSize, int targetMountId, String targetFullPath) {

        return fileSave(mountId, "", fileName, fileHash, fileSize, targetMountId, targetFullPath, null);
    }

    /**
     * 文件（夹）转存
     *
     * @param mountId
     * @param fullPath
     * @param fileName
     * @param fileHash
     * @param fileSize
     * @param targetMountId
     * @param targetFullPath
     * @param dialogId
     * @return
     */
    public String fileSave(int mountId, String fullPath, String fileName, String fileHash, long fileSize,
                           int targetMountId, String targetFullPath, String dialogId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("mount_id", mountId + "");
        params.put("filename", fileName);
        params.put("fullpath", fullPath);
        params.put("filehash", fileHash);
        params.put("filesize", fileSize + "");
        params.put("target_mount_id", targetMountId + "");
        params.put("target_fullpath", targetFullPath);
        params.put("dialog_id", dialogId);
        params.put("sign", generateSign(params));
        String url = URL_API + URL_API_GET_FILE_SAVE;
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).setCheckAuth(true).executeSync();
    }


    /**
     * 如果身份验证有问题,会自动刷token
     *
     * @param url
     * @param method
     * @param params
     * @param headParams
     * @param ignoreKeys
     * @return
     */
    private String sendRequestWithAuth(String url, RequestMethod method,
                                       HashMap<String, String> params, HashMap<String, String> headParams, ArrayList<String> ignoreKeys) {
        String returnString = NetConnection.sendRequest(url, method, params, headParams);
        ReturnResult returnResult = ReturnResult.create(returnString);
        if (returnResult != null) {
            if (returnResult.getStatusCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                refreshToken();
                reSignParams(params, ignoreKeys);
                returnString = NetConnection.sendRequest(url, method, params, headParams);
            }
        }
        return returnString;
    }


    /**
     * 请求协助类
     */
    protected class RequestHelper {
        RequestMethod method;
        HashMap<String, String> params;
        HashMap<String, String> headParams;
        String url;
        boolean checkAuth;

        ArrayList<String> ignoreKeys;

        RequestHelper setMethod(RequestMethod method) {
            this.method = method;
            return this;
        }

        RequestHelper setParams(HashMap<String, String> params) {
            this.params = params;
            return this;
        }

        RequestHelper setHeadParams(HashMap<String, String> headParams) {
            this.headParams = headParams;
            return this;
        }

        RequestHelper setCheckAuth(boolean checkAuth) {
            this.checkAuth = checkAuth;
            return this;
        }

        RequestHelper setUrl(String url) {
            this.url = url;
            return this;
        }

        public RequestHelper setIgnoreKeys(ArrayList<String> ignoreKeys) {
            this.ignoreKeys = ignoreKeys;
            return this;
        }

        /**
         * 同步执行
         *
         * @return
         */
        String executeSync() {
            checkNecessaryParams(url, method);

            if (!Util.isNetworkAvailableEx()) {
                return "";
            }

            if (checkAuth) {
                return sendRequestWithAuth(url, method, params, headParams, ignoreKeys);
            }
            return NetConnection.sendRequest(url, method, params, headParams);
        }

        /**
         * 异步执行
         *
         * @return
         */
        Thread executeAsync(final DataListener listener, final int apiId, final RequestHelperCallBack callBack) {

            checkNecessaryParams(url, method);

            if (listener != null) {
                if (!Util.isNetworkAvailableEx()) {
                    listener.onReceivedData(apiId, null, ERRORID_NETDISCONNECT);
                    return null;
                }
            }

            Thread thread = new Thread() {
                @Override
                public void run() {
                    String returnString;

                    if (checkAuth) {
                        returnString = sendRequestWithAuth(url, method, params, headParams, ignoreKeys);
                    } else {
                        returnString = NetConnection.sendRequest(url, method, params, headParams);
                    }

                    if (callBack != null) {
                        if (listener != null) {
                            Object object = callBack.getReturnData(returnString);
                            listener.onReceivedData(apiId, object, -1);
                        }
                    }
                }
            };
            thread.start();
            return thread;
        }

        private void checkNecessaryParams(String url, RequestMethod method) {
            if (TextUtils.isEmpty(url)) {
                throw new IllegalArgumentException("url must not be null");
            }

            if (method == null) {
                throw new IllegalArgumentException("method must not be null");
            }
        }

        Thread executeAsyncTask(Thread task, final DataListener listener, final int apiId) {
            if (listener != null) {
                if (!Util.isNetworkAvailableEx()) {
                    listener.onReceivedData(apiId, null, ERRORID_NETDISCONNECT);
                    return null;
                }
            }

            task.start();
            return task;
        }

    }

    interface RequestHelperCallBack {
        Object getReturnData(String returnString);
    }

}
