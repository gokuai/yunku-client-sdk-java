package com.gokuai.cloud.transinterface;

import com.gokuai.base.*;
import com.gokuai.base.utils.Base64;
import com.gokuai.base.utils.URLEncoder;
import com.gokuai.base.utils.Util;
import com.gokuai.cloud.YKConfig;
import com.gokuai.cloud.data.FileInfo;
import com.gokuai.cloud.data.OauthData;
import com.gokuai.cloud.data.YunkuException;
import com.gokuai.library.net.UploadCallback;
import com.gokuai.library.net.UploadManager;
import com.gokuai.library.util.EmojiMapUtil;
import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Brandon on 14-9-12.
 * 网络接口请求
 */
public class YKHttpEngine extends HttpEngine implements IAuthRequest {

    private final static String LOG_TAG = YKHttpEngine.class.getSimpleName();
    private final static int FILE_SIZE_NONE = -1;
    private static int DEFAULT_BLOCK_SIZE = 10485760;

    protected String URL_API = YKConfig.URL_API_HOST;
    private String URL_OAUTH = URL_API + "/oauth2/token2";

    protected String token;
    protected String refreshToken;

    private final static String URL_UPDATE_APP = "/update/app";
    private final String URL_API_EXCHANGE_TOKEN = URL_OAUTH;

    // 账号相关
    private final static String URL_API_ACCOUNT_ROLES = "/1/account/roles";
    private final static String URL_API_GET_ENT_INFO = "/1/account/ent_info";
    private static final String URL_API_GET_DEVICE_LIST = "/1/account/device_list";
    private final static String URL_API_GET_ACCOUNT_INFO = "/1/account/info";
    private final static String URL_API_GET_ACCOUNT_MOUNT = "/1/account/mount";
    private final static String URL_API_GET_ACCOUNT_ENT = "/1/account/ent";
    private final static String URL_API_ORG_MEMBERS_LIST = "/1/contact/org_member_list";
    private static final String URL_API_SET_DEVICE = "/1/account/set_device";
    private static final String URL_API_CHANGE_DEVICE_STATE = "/1/account/toggle_device";
    private static final String URL_API_DEL_DEVICE = "/1/account/del_device";
    private static final String URL_API_DISABLE_NEW_DEVICE = "/1/account/disable_new_device";
    private static final String URL_API_GET_SERVER_SITE = "/1/account/servers";
    private static final String URL_API_GET_SETTING = "/1/account/setting";

    // 成员相关
    private final static String URL_API_MEMBER_LAST_VISIT = "/1/member/last_visit";

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

    public static void close() {
        if (instance != null) {
            instance.token = null;
            instance.refreshToken = null;
            instance = null;
        }
    }

    public static void setDefaultBlockSize(int size) {
        DEFAULT_BLOCK_SIZE = size;
    }

    public String getToken() {
        return this.token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }


    public boolean isTokenAvailable() {
        return !Util.isEmpty(token);
    }

    /**
     * 重新根据参数进行签名
     *
     * @param params
     */
    protected void reSignParams(HashMap<String, String> params) {
        params.put("token", this.getToken());
        params.put("sign", generateSign(params));
    }

    /**
     * 账号密码登录,会根据 com.gokuai.cloud.YKConfig.ENT_DOMAIN 自动添加前缀
     *
     * @param account
     * @param password
     * @return
     */
    public ReturnResult login(String account, String password) {
        ReturnResult result = this.loginByPassword(account, password).executeSync();
        if (result.isOK()) {
            OauthData data = OauthData.create(result.getBody());
            if (data != null) {
                token = data.getToken();
                refreshToken = data.getRefreshToken();
            }
        }
        return result;
    }

    private RequestHelper loginByPassword(String account, String password) {
        String url = URL_OAUTH;
        HashMap<String, String> params = new HashMap<>();
        params.put("grant_type", "password");
        if (!Util.isEmpty(YKConfig.ENT_DOMAIN)) {
            params.put("username", YKConfig.ENT_DOMAIN + "\\" + account);
            params.put("password", Base64.encodeBytes(password.getBytes()));
        } else {
            params.put("username", account);
            params.put("password", Util.convert2MD532(password));
        }

        params.put("client_id", YKConfig.CLIENT_ID);

        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST);
    }

    public ReturnResult exchangeToken(String exchangeToken) {
        return exchangeToken(exchangeToken, null, null, YKConfig.ENT_DOMAIN, "");
    }

    public ReturnResult exchangeToken(String username, String password) {
        return exchangeToken(null, username, password, YKConfig.ENT_DOMAIN, "");
    }

    public ReturnResult exchangeToken(String exchangeToken, String username, String password, String domain, String auth) {
        String url = URL_API_EXCHANGE_TOKEN;
        HashMap<String, String> params = new HashMap<>();
        params.put("grant_type", "exchange_token");
        if (!Util.isEmpty(exchangeToken)) {
            params.put("exchange_token", exchangeToken);
        }
        if (!Util.isEmpty(username) && !Util.isEmpty(password)) {
            params.put("username", username);
            params.put("password", Base64.encodeBytes(password.getBytes()));
        }
        params.put("domain", domain);
        params.put("auth", auth);
        params.put("client_id", YKConfig.CLIENT_ID);

        ReturnResult result = new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).executeSync();
        OauthData data = OauthData.create(result.getBody());
        if (data != null) {
            if (!data.getToken().isEmpty()) {
                this.token = data.getToken();
                this.refreshToken = data.getRefreshToken();
            }
        }
        return result;
    }

    /**
     * 重新获得token
     */
    public ReturnResult refreshToken() {
        if (Util.isEmpty(refreshToken)) {
            return null;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("grant_type", "refresh_token");
        params.put("refresh_token", refreshToken);
        params.put("client_id", YKConfig.CLIENT_ID);

        ReturnResult result = new RequestHelper().setUrl(URL_OAUTH).setMethod(RequestMethod.POST).setParams(params).executeSync();
        if (result.isOK()) {
            OauthData data = OauthData.create(result.getBody());
            if (data != null) {
                token = data.getToken();
                refreshToken = data.getRefreshToken();
                LogPrint.info(LOG_TAG, "token:" + token + "_refreshToken:" + refreshToken);
            }
        }
        return result;
    }

    /**
     *
     * @param account
     * @param clientId
     * @param secret
     * @param returnUrl
     * @param format
     * @return
     */
    public String getSsoUrl(String account, String clientId, String secret, String returnUrl, String format) {
        HashMap<String, String> map = new HashMap<>();
        map.put("account", account);
        map.put("n", YKUtil.getSixRandomChars());
        map.put("t", Long.toString(Util.getUnixDateline()));
        map.put("sign", this.generateSign(map));

        String ticket = URLEncoder.encodeUTF8(Base64.encodeBytes(new Gson().toJson(map).getBytes()));
        String url = String.format(YKConfig.URL_ACCOUNT_AUTO_LOGIN, clientId, ticket, returnUrl, format);
        return url;
    }

    /**
     * 企业一站式登录
     *
     * @param account
     * @param clientId
     * @param secret
     * @return
     */
    public ReturnResult ssoLogin(String account, String clientId, String secret) {
        String url = this.getSsoUrl(account, clientId, secret, "", "json");
        return new RequestHelper().setParams(new HashMap<String, String>())
                .setMethod(RequestMethod.POST).setUrl(url)
                .executeSync();
    }

    /**
     * gkkey登录
     *
     * @param key
     */
    public ReturnResult loginByKey(String key) {
        HashMap<String, String> params = new HashMap<>();
        params.put("gkkey", key);
        params.put("grant_type", "gkkey");
        params.put("client_id", YKConfig.CLIENT_ID);

        ReturnResult result = new RequestHelper().setParams(params)
                .setUrl(URL_API_EXCHANGE_TOKEN)
                .setMethod(RequestMethod.POST)
                .executeSync();

        if (result.isOK()) {
            OauthData data = OauthData.create(result.getBody());
            if (data != null) {
                token = data.getToken();
                refreshToken = data.getRefreshToken();
            }
        }
        return result;
    }

    /**
     * 获取企业列表
     *
     * @return
     */
    public ReturnResult getEnts() {
        String url = URL_API + URL_API_GET_ACCOUNT_ENT;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }

    /**
     * 获得用户信息
     *
     * @return
     */
    public ReturnResult getAccountInfo() {
        String url = URL_API + URL_API_GET_ACCOUNT_INFO;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }

    /**
     * 获取库列表
     *
     * @return
     */
    public ReturnResult getLibraries() {
        String url = URL_API + URL_API_GET_ACCOUNT_MOUNT;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }

    /**
     * 根据 mountId 获取库信息
     *
     * @param mountId
     * @return
     */
    public ReturnResult getLibraryInfoByMountId(int mountId) {
        return getLibraryInfo(mountId, 0);
    }

    /**
     * 根据 orgId 获取库信息
     *
     * @param orgId
     * @return
     */
    public ReturnResult getLibraryInfoByOrgId(int orgId) {
        return getLibraryInfo(0, orgId);
    }

    /**
     * 获取库信息
     *
     * @param mountId
     * @param orgId
     * @return
     */
    private ReturnResult getLibraryInfo(int mountId, int orgId) {
        String url = URL_API + URL_API_LIB_INFO;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        if (mountId > 0) {
            params.put("mount_id", Integer.toString(mountId));
        } else {
            params.put("org_id", Integer.toString(orgId));
        }
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }

    /**
     * 退出库
     *
     * @param orgId
     */
    public ReturnResult quitLibrary(int orgId) {
        String url = URL_API + URL_API_QUIT_LIB;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("org_id", Integer.toString(orgId));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST)
                .setUrl(url).setCheckAuth(true)
                .executeSync();
    }

    /**
     * 删除库
     *
     * @param orgId
     * @return
     */
    public ReturnResult deleteLibrary(int orgId) {
        String url = URL_API + URL_API_DEL_LIB;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("org_id", Integer.toString(orgId));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST)
                .setUrl(url).setCheckAuth(true)
                .executeSync();
    }

    public ReturnResult getServers(String type) {
        return getServers(type, "");
    }

    public ReturnResult getServers(String type, String storagePoint) {
        String url = URL_API + URL_API_GET_SERVER_SITE;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        if (!Util.isEmpty(storagePoint)) {
            params.put("storage_point", storagePoint);
        }
        params.put("type", type);
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).setCheckAuth(true).executeSync();
    }

    /**
     * 获取账号设置
     *
     * @return
     */
    public ReturnResult getAccountSetting() {
        String url = URL_API + URL_API_GET_SETTING;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }

    /**
     * 获取文件列表
     *
     * @param mountId
     * @param fullpath
     * @param start
     * @param size
     * @return
     */
    public ReturnResult getLibraryFiles(int mountId, String fullpath, int start, int size) {
        String url = URL_API + URL_API_FILE_LIST;
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("fullpath", fullpath);
        params.put("start", Integer.toString(start));
        params.put("size", Integer.toString(size));
        params.put("token", getToken());
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }

    /**
     * 获取指定几个文件的信息
     *
     * @param mountId
     * @param hashs
     * @return
     */
    public ReturnResult getFilesByHashs(int mountId, ArrayList<String> hashs) {
        if (hashs.isEmpty()) {
            return null;
        }
        String url = URL_API + URL_API_FILE_LIST;
        String hashsString = "";
        for (String hash : hashs) {
            hashsString += "," + hash;
        }
        hashsString = hashsString.substring(1);

        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("hashs", hashsString);
        params.put("token", getToken());
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }

    /**
     * 文件复制
     *
     * @param mountId        要复制的mount_id
     * @param fullpaths
     * @param targetMountId  制到的mount_id
     * @param targetFullpath 复制到的路径   @return
     */
    public ReturnResult filesCopy(int mountId, ArrayList<String> fullpaths, int targetMountId, String targetFullpath) {
        if (fullpaths.isEmpty()) {
            return null;
        }
        String url = URL_API + URL_API_FILE_COPY;
        String fullpathsString = "";
        for (String fullpath : fullpaths) {
            fullpathsString += "|" + fullpath;
        }
        fullpathsString = fullpathsString.substring(1);

        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("fullpaths", fullpathsString);
        params.put("target_mount_id", Integer.toString(targetMountId));
        params.put("target_fullpath", targetFullpath);
        params.put("token", getToken());

        return new RequestHelper().setParams(params).
                setMethod(RequestMethod.POST)
                .setUrl(url)
                .setCheckAuth(true).executeSync();
    }

    /**
     * 文件移动
     *
     * @param mountId        要移动的mount_id
     * @param fullpaths
     * @param targetMountId  移动到的mount_id
     * @param targetFullpath 移动到的路径   @return
     */
    public ReturnResult filesMove(int mountId, ArrayList<String> fullpaths, int targetMountId, String targetFullpath) {
        if (fullpaths.isEmpty()) {
            return null;
        }
        String url = URL_API + URL_API_FILE_MOVE;

        String fullpathsString = "";
        for (String fullpath : fullpaths) {
            fullpathsString += "|" + fullpath;
        }
        fullpathsString = fullpathsString.substring(1);

        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("fullpaths", fullpathsString);
        params.put("target_mount_id", Integer.toString(targetMountId));
        params.put("target_fullpath", targetFullpath);
        params.put("token", getToken());

        return new RequestHelper().setParams(params).
                setMethod(RequestMethod.POST)
                .setUrl(url)
                .setCheckAuth(true).executeSync();
    }

    /**
     * 搜索文件
     *
     * @param mountId
     * @param keywords
     * @return
     */
    public ReturnResult fileSearch(int mountId, String keywords) {
        String url = URL_API + URL_API_FILE_SEARCH;
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("keyword", keywords);
        params.put("token", getToken());
        return new RequestHelper().setParams(params).
                setMethod(RequestMethod.GET)
                .setUrl(url)
                .setCheckAuth(true).executeSync();
    }

    /**
     * 锁定/解锁文件
     *
     * @param mountId  文件库id
     * @param fullpath 文件路径
     * @param lock     1 lock 0 unlock
     * @return
     */
    public ReturnResult fileLock(int mountId, String fullpath, boolean lock) {
        String url = URL_API + URL_API_LOCK;
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("fullpath", fullpath);
        params.put("lock", (lock ? "lock" : "unlock"));
        params.put("token", getToken());
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).setCheckAuth(true).executeSync();
    }

    /**
     * 文件是否存在
     *
     * @param mountId
     * @param hash
     * @return
     */
    public ReturnResult fileExistsByHash(int mountId, String hash) {
        String url = URL_API + URL_API_FILE_EXIST;
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("hash", hash);
        params.put("token", getToken());
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST).setUrl(url)
                .setCheckAuth(true)
                .executeSync();
    }


    /**
     * 文件是否存在
     *
     * @param fullpath
     * @param mountId
     * @return
     */
    public ReturnResult fileExistsByFullPath(int mountId, String fullpath) {
        String url = URL_API + URL_API_FILE_EXIST;
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("fullpath", fullpath);
        params.put("token", getToken());
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST).setUrl(url)
                .setCheckAuth(true)
                .executeSync();
    }

    /**
     * 文件历史版本恢复
     *
     * @param fullpath
     * @param mountId
     * @param hid
     * @return
     */
    public ReturnResult fileRevert(String fullpath, int mountId, String hid) {
        String url = URL_API + URL_API_REVERT;
        HashMap<String, String> params = new HashMap<>();
        params.put("fullpath", fullpath);
        params.put("mount_id", Integer.toString(mountId));
        params.put("hid", hid);
        params.put("token", getToken());
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST).setUrl(url)
                .setCheckAuth(true)
                .executeSync();
    }

    /**
     * 获取文件外链
     *
     * @param mountId
     * @param fullpath
     * @param deadline
     * @param auth
     * @param password
     * @param scope
     * @param day
     * @return
     */
    public ReturnResult getFileLink(int mountId, String fullpath, String deadline,
                                    String auth, String password, String scope, String day) {
        String url = URL_API + URL_API_FILE_LINK;
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("fullpath", fullpath);
        params.put("deadline", deadline);
        params.put("auth", auth);
        params.put("password", password);
        params.put("scope", scope);
        params.put("day", day);
        params.put("token", getToken());
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST).setUrl(url)
                .setCheckAuth(true)
                .executeSync();
    }

    /**
     * 获取用户最近访问文件
     *
     * @param size
     * @return
     */
    public ReturnResult getLastVisitFile(int size) {
        String url = URL_API + URL_API_MEMBER_LAST_VISIT;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("size", Long.toString(size));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.GET).setUrl(url)
                .setCheckAuth(true)
                .executeSync();
    }

    /**
     * 文件添加备注
     *
     * @param mountId
     * @param fullpath
     * @return
     */
    public ReturnResult addFileRemark(int mountId, String fullpath, String text) {
        String url = URL_API + URL_API_ADD_FILE_REMARK;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("mount_id", Integer.toString(mountId));
        params.put("fullpath", fullpath);
        params.put("message", EmojiMapUtil.replaceUnicodeEmojis(text));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST).setUrl(url)
                .setCheckAuth(true)
                .executeSync();
    }


    /**
     * 文件备注列表
     *
     * @param mountId
     * @param fullpath
     * @param start
     * @param size
     * @return
     */
    public ReturnResult getFileRemarks(int mountId, String fullpath, int start, int size) {
        String url = URL_API + URL_API_GET_FILE_REMARK_LIST;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("mount_id", Integer.toString(mountId));
        params.put("fullpath", fullpath);
        params.put("start", Integer.toString(start));
        params.put("size", Integer.toString(size));
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.GET).setUrl(url)
                .setCheckAuth(true)
                .executeSync();
    }


    /**
     * 重命名文件、文件夹
     */
    public ReturnResult renameFile(String fullpath, int mountId, String newName) {
        String url = URL_API + URL_API_RENAME;
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("newname", newName);
        params.put("fullpath", fullpath);
        params.put("token", getToken());
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST)
                .setUrl(url).setCheckAuth(true)
                .executeSync();
    }

    /**
     * 批量删除文件文件夹
     *
     * @param mountId
     * @param fullpaths
     */
    public ReturnResult filesDelete(int mountId, ArrayList<String> fullpaths) {
        if (fullpaths.size() == 0) {
            return null;
        }
        String url = URL_API + URL_API_DELETE;
        String fullpathsString = "";
        for (String fullpath : fullpaths) {
            fullpathsString += "|" + fullpath;
        }
        fullpathsString = fullpathsString.substring(1);
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("fullpaths", fullpathsString);
        params.put("token", getToken());
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.POST)
                .setUrl(url).setCheckAuth(true)
                .executeSync();
    }

    /**
     * 获取文件历史版本
     *
     * @param mountId
     * @param fullpath
     * @return
     */
    public ReturnResult getFileHistory(int mountId, String fullpath) {
        String url = URL_API + URL_API_GET_FILE_HISTORY;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("mount_id", Integer.toString(mountId));
        params.put("fullpath", fullpath);
        return new RequestHelper().setParams(params)
                .setMethod(RequestMethod.GET).setUrl(url)
                .setCheckAuth(true)
                .executeSync();
    }

    /**
     * 获取企业成员信息
     *
     * @param entId
     * @param memberId
     * @return
     */
    public ReturnResult getMemberInfo(int entId, int memberId) {
        String url = URL_API + URL_API_CONTACT_MEMBER_INFO;
        HashMap<String, String> params = new HashMap<>();
        params.put("ent_id", Integer.toString(entId));
        params.put("_member_id", Integer.toString(memberId));
        params.put("token", getToken());
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }

    /**
     * 根据文件路径
     *
     * @param mountId
     * @param fullpath
     * @param open
     * @param hid
     * @return
     */
    public ReturnResult getFileUrlByFullpath(int mountId, String fullpath, boolean open, String hid) {
        String url = URL_API + URL_API_OPEN;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("mount_id", Integer.toString(mountId));
        params.put("fullpath", fullpath);
        params.put("hid", hid);
        params.put("log_act", "20");
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }

    /**
     * 根据文件filehash获取文件下载路径
     *
     * @param mountId
     * @param fileHash
     * @param open
     * @return
     */
    public ReturnResult getFileUrlByFileHash(int mountId, String fileHash, boolean open) {
        String url = URL_API + URL_API_GET_URL_BY_FILEHASH;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("mount_id", Integer.toString(mountId));
        params.put("filehash", fileHash);
        params.put("open", (open ? "1" : "0"));
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }

    /**
     * 获取文件夹属性
     *
     * @param mountId
     * @param fullpath
     * @param hash
     * @return
     */
    public ReturnResult getFolderAttribute(int mountId, String fullpath, String hash) {
        String url = URL_API + URL_API_GET_FILE_ATTRIBUTE;
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("fullpath", fullpath);
        params.put("hash", hash);
        params.put("token", getToken());
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }

    /**
     * 获取文件信息
     *
     * @param mountId
     * @param fullpath
     * @param hid
     * @return
     */
    public ReturnResult getFileInfoByFullpath(int mountId, String fullpath, String hid) {
        String url = URL_API + URL_API_GET_FILE_INFO;
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("fullpath", fullpath);
        params.put("hid", hid);
        params.put("token", getToken());
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }

    /**
     * 获取文件信息
     *
     * @param mountId
     * @param hash
     * @param hid
     * @return
     */
    public ReturnResult getFileInfoByHash(int mountId, String hash, String hid) {
        String url = URL_API + URL_API_GET_FILE_INFO;
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("hash", hash);
        params.put("hid", hid);
        params.put("token", getToken());
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }

    /**
     * 新建文件(支持秒传)
     *
     * @param mountId
     * @param fullpath
     * @param fileHash
     * @param fileSize
     * @return
     */
    public ReturnResult addFile(int mountId, String fullpath, String fileHash, long fileSize, boolean overwrite) {
        String url = URL_API + URL_API_CREATE_FILE;

        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("mount_id", Integer.toString(mountId));
        params.put("fullpath", fullpath);
        params.put("filehash", fileHash);
        params.put("filesize", Long.toString(fileSize));
        params.put("overwrite", overwrite ? "1" : "0");
        return new RequestHelper().setParams(params)
                .setUrl(url)
                .setMethod(RequestMethod.POST)
                .setCheckAuth(true).executeSync();
    }

    /**
     * 创建文件夹
     *
     * @param mountId
     * @param fullpath
     * @return
     */
    public ReturnResult createFolder(int mountId, String fullpath) {
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("fullpath", fullpath);
        params.put("token", getToken());
        String url = URL_API + URL_API_CREATE_FOLDER;
        return new RequestHelper().setParams(params)
                .setUrl(url)
                .setMethod(RequestMethod.POST)
                .setCheckAuth(true)
                .executeSync();
    }

    /**
     * 文件最近更新列表
     *
     * @param mountId
     * @param msdateline
     * @param size
     * @return
     */
    public ReturnResult getFileUpdates(int mountId, long msdateline, int size) {
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("dateline", Long.toString(msdateline));
        params.put("size", Long.toString(size));
        params.put("token", getToken());
        String url = URL_API + URL_API_FILE_UPDATE;
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }

    /**
     * 设置文件标签
     *
     * @param mountId
     * @param fullpath
     * @param tags
     * @return
     */
    public ReturnResult setFileTag(int mountId, String fullpath, String tags) {
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("fullpath", fullpath);
        params.put("keywords", tags);
        params.put("token", getToken());
        String url = URL_API + URL_API_FILE_KEYWORD;
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).setCheckAuth(true).executeSync();
    }

    /**
     * 上传本地文件
     *
     * @param localFile 本地文件完整路径
     * @param mountId   库空间ID
     * @param fullpath  文件在库中的完整路径
     * @param overwrite 是否覆盖已存在的文件
     * @return 文件信息
     */
    public FileInfo uploadByBlock(String localFile, int mountId, String fullpath, boolean overwrite) throws YunkuException {
        UploadManager manager = new UploadManager(this, DEFAULT_BLOCK_SIZE);
        return manager.upload(localFile, mountId, fullpath, overwrite);
    }

    /**
     * 上传文件流
     *
     * @param stream    文件流
     * @param mountId   库空间ID
     * @param fullpath  文件在库中的完整路径
     * @param overwrite 是否覆盖已存在的文件
     * @return 文件信息
     */
    public FileInfo uploadByBlock(InputStream stream, int mountId, String fullpath, boolean overwrite) throws YunkuException {
        UploadManager manager = new UploadManager(this, DEFAULT_BLOCK_SIZE);
        return manager.upload(stream, mountId, fullpath, overwrite);
    }

    /**
     * 异步方式上传本地文件
     *
     * @param localFile 本地文件完整路径
     * @param mountId   库空间ID
     * @param fullpath  文件在库中的完整路径
     * @param overwrite 是否覆盖已存在的文件
     * @param callback  回调
     * @return
     */
    public UploadManager uploadByBlockAsync(String localFile, int mountId, String fullpath, boolean overwrite, UploadCallback callback) {
        UploadManager manager = new UploadManager(this, DEFAULT_BLOCK_SIZE);
        manager.uploadAsync(localFile, mountId, fullpath, overwrite, callback);
        return manager;
    }

    /**
     * 异步方式上传文件流
     *
     * @param stream    文件流
     * @param mountId   库空间ID
     * @param fullpath  文件在库中的完整路径
     * @param overwrite 是否覆盖已存在的文件
     * @param callback  回调
     * @return
     */
    public UploadManager uploadByBlockAsync(InputStream stream, int mountId, String fullpath, boolean overwrite, UploadCallback callback) {
        UploadManager manager = new UploadManager(this, DEFAULT_BLOCK_SIZE);
        manager.uploadAsync(stream, mountId, fullpath, overwrite, callback);
        return manager;
    }

    /**
     * 文件转存
     */
    public ReturnResult fileSave(int mountId, String fileName, String fileHash, long fileSize, int targetMountId, String targetFullpath) {

        return fileSave(mountId, "", fileName, fileHash, fileSize, targetMountId, targetFullpath, null);
    }

    /**
     * 获取文件下载地址和预览地址
     *
     * @param mountId
     * @param hash
     * @param filehash
     * @return
     */
    public ReturnResult getFileUrlByHash(int mountId, String hash, String filehash) {
        String url = URL_API + URL_API_GET_FILE_URL;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("mount_id", Integer.toString(mountId));
        params.put("hash", hash);
        params.put("fullpath", filehash);
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.GET).setCheckAuth(true).executeSync();
    }

    /**
     * 文件（夹）转存
     *
     * @param mountId
     * @param fullpath
     * @param fileName
     * @param fileHash
     * @param fileSize
     * @param targetMountId
     * @param targetFullpath
     * @param dialogId
     * @return
     */
    public ReturnResult fileSave(int mountId, String fullpath, String fileName, String fileHash, long fileSize,
                                 int targetMountId, String targetFullpath, String dialogId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", getToken());
        params.put("mount_id", Integer.toString(mountId));
        params.put("filename", fileName);
        params.put("fullpath", fullpath);
        params.put("filehash", fileHash);
        params.put("filesize", Long.toString(fileSize));
        params.put("target_mount_id", Integer.toString(targetMountId));
        params.put("target_fullpath", targetFullpath);
        params.put("dialog_id", dialogId);
        String url = URL_API + URL_API_GET_FILE_SAVE;
        return new RequestHelper().setParams(params).setUrl(url).setMethod(RequestMethod.POST).setCheckAuth(true).executeSync();
    }

    //如果身份验证有问题,会自动刷token
    public ReturnResult sendRequestWithAuth(String url, RequestMethod method,
                                             HashMap<String, String> params, HashMap<String, String> headParams, String postType) {
        ReturnResult result = NetConnection.sendRequest(url, method, params, headParams, postType);
        if (result.getCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
            this.refreshToken();
            if (params == null) {
                params = new HashMap<String, String>();
            }
            this.reSignParams(params);
            result = NetConnection.sendRequest(url, method, params, headParams, postType);
        }
        return result;
    }
}
