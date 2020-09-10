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
 * 网络接口请求
 */
public class YKHttpEngine extends HttpEngine implements IAuthRequest {

    private final static String LOG_TAG = YKHttpEngine.class.getSimpleName();
    private static int DEFAULT_BLOCK_SIZE = 10485760;

    protected String token;
    protected String refreshToken;

    protected String URL_API = YKConfig.URL_API_HOST;
    private static final String URL_API_OAUTH_TOKEN = "/oauth2/token2";

    // 账号相关
    private static final String URL_API_GET_ACCOUNT_INFO = "/1/account/info";
    private static final String URL_API_GET_ACCOUNT_MOUNT = "/1/account/mount";
    private static final String URL_API_GET_ACCOUNT_ENT = "/1/account/ent";
    private static final String URL_API_GET_SERVERS = "/1/account/servers";
    private static final String URL_API_GET_SETTING = "/1/account/setting";

    // 成员相关
    private static final String URL_API_MEMBER_RECENT_FILES = "/1/member/last_visit";

    //库相关
    private static final String URL_API_DEL_LIB = "/1/library/delete";
    private static final String URL_API_LIB_INFO = "/1/library/info";

    //通讯录相关
    private static final String URL_API_CONTACT_MEMBER_INFO = "/1/contact/member_info";

    //库文件相关
    private static final String URL_API_FILE_COPY = "/1/file/copy";
    private static final String URL_API_FILE_MOVE = "/1/file/move";
    private static final String URL_API_FILE_LIST = "/1/file/ls";
    private static final String URL_API_FILE_LINK = "/1/file/create_file_link";
    private static final String URL_API_FILE_UPDATE = "/1/file/updates";
    private static final String URL_API_FILE_KEYWORD = "/1/file/keyword";
    private static final String URL_API_RENAME = "/1/file/rename";
    private static final String URL_API_LOCK = "/1/file/lock";
    private static final String URL_API_GET_URL_BY_FILEHASH = "/1/file/get_url_by_filehash";
    private static final String URL_API_DELETE = "/1/file/del";

    private static final String URL_API_GET_FILE_SAVE = "/2/file/save";
    private static final String URL_API_GET_FILE_URL = "/2/file/url";
    private static final String URL_API_FILE_SEARCH = "/2/file/search";
    private static final String URL_API_FILE_EXIST = "/2/file/exist";
    private static final String URL_API_GET_FILE_HISTORY = "/2/file/history";
    private static final String URL_API_REVERT = "/2/file/revert";
    private static final String URL_API_OPEN = "/2/file/open";
    private static final String URL_API_GET_FILE_INFO = "/2/file/info";
    private static final String URL_API_CREATE_FOLDER = "/2/file/create_folder";
    private static final String URL_API_CREATE_FILE = "/2/file/create_file";
    private static final String URL_API_GET_FILE_ATTRIBUTE = "/2/file/attribute";
    private static final String URL_API_ADD_FILE_REMARK = "/2/file/add_remark";
    private static final String URL_API_GET_FILE_REMARK_LIST = "/2/file/remark";
    private static final String URL_API_GET_FAVORITE_FILES = "/1/favorites/get_files";
    private static final String URL_API_ADD_FAVORITE_FILE = "/1/favorites/add_file";
    private static final String URL_API_DEL_FAVORITE_FILE = "/1/favorites/del_file";

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
        HashMap<String, String> params = new HashMap<>();
        params.put("client_id", YKConfig.CLIENT_ID);
        params.put("grant_type", "password");
        if (!Util.isEmpty(YKConfig.ENT_DOMAIN)) {
            params.put("username", YKConfig.ENT_DOMAIN + "\\" + account);
            params.put("password", Base64.encodeBytes(password.getBytes()));
        } else {
            params.put("username", account);
            params.put("password", Util.convert2MD532(password));
        }
        String url = URL_API + URL_API_OAUTH_TOKEN;
        return new RequestHelper().setMethod(RequestMethod.POST).setUrl(url).setParams(params);
    }

    public ReturnResult exchangeToken(String exchangeToken) {
        return exchangeToken(exchangeToken, null, null, YKConfig.ENT_DOMAIN, "");
    }

    public ReturnResult exchangeToken(String username, String password) {
        return exchangeToken(null, username, password, YKConfig.ENT_DOMAIN, "");
    }

    public ReturnResult exchangeToken(String exchangeToken, String username, String password, String domain, String auth) {
        HashMap<String, String> params = new HashMap<>();
        params.put("client_id", YKConfig.CLIENT_ID);
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
        String url = URL_API + URL_API_OAUTH_TOKEN;
        ReturnResult result = new RequestHelper().setMethod(RequestMethod.POST).setUrl(url).setParams(params).executeSync();
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
        params.put("client_id", YKConfig.CLIENT_ID);
        params.put("grant_type", "refresh_token");
        params.put("refresh_token", refreshToken);
        String url = URL_API + URL_API_OAUTH_TOKEN;
        ReturnResult result = new RequestHelper().setMethod(RequestMethod.POST).setUrl(url).setParams(params).executeSync();
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
     * @param account
     * @param entClientId
     * @param entSecret
     * @param returnUrl
     * @param format
     * @return
     */
    public String getSsoUrl(String account, String entClientId, String entSecret, String returnUrl, String format) {
        HashMap<String, String> map = new HashMap<>();
        map.put("account", account);
        map.put("n", YKUtil.getSixRandomChars());
        map.put("t", Long.toString(Util.getUnixDateline()));
        map.put("sign", this.generateSign(map, entSecret));
        String ticket = URLEncoder.encodeUTF8(Base64.encodeBytes(new Gson().toJson(map).getBytes()));
        return String.format(YKConfig.URL_ACCOUNT_AUTO_LOGIN, entClientId, ticket, returnUrl, format);
    }

    /**
     * 企业一站式登录
     *
     * @param account
     * @param entClientId
     * @param entSecret
     * @return
     */
    public ReturnResult ssoLogin(String account, String entClientId, String entSecret) {
        String url = this.getSsoUrl(account, entClientId, entSecret, "", "json");
        return new RequestHelper().setMethod(RequestMethod.POST).setUrl(url).executeSync();
    }

    /**
     * gkkey登录
     *
     * @param key
     */
    public ReturnResult loginByKey(String key) {
        HashMap<String, String> params = new HashMap<>();
        params.put("client_id", YKConfig.CLIENT_ID);
        params.put("grant_type", "gkkey");
        params.put("gkkey", key);
        String url = URL_API + URL_API_OAUTH_TOKEN;
        ReturnResult result = new RequestHelper()
                .setMethod(RequestMethod.POST)
                .setUrl(url)
                .setParams(params)
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
        return sendRequest(RequestMethod.GET, URL_API_GET_ACCOUNT_ENT, null);
    }

    /**
     * 获得用户信息
     *
     * @return
     */
    public ReturnResult getAccountInfo() {
        return sendRequest(RequestMethod.GET, URL_API_GET_ACCOUNT_INFO, null);
    }

    /**
     * 获取库列表
     *
     * @return
     */
    public ReturnResult getLibraries() {
        return sendRequest(RequestMethod.GET, URL_API_GET_ACCOUNT_MOUNT, null);
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
        HashMap<String, String> params = new HashMap<>();
        if (mountId > 0) {
            params.put("mount_id", Integer.toString(mountId));
        } else {
            params.put("org_id", Integer.toString(orgId));
        }
        return sendRequest(RequestMethod.GET, URL_API_LIB_INFO, params);
    }

    /**
     * 删除库
     *
     * @param orgId
     * @return
     */
    public ReturnResult deleteLibrary(int orgId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("org_id", Integer.toString(orgId));
        return sendRequest(RequestMethod.POST, URL_API_DEL_LIB, params);
    }

    public ReturnResult getServers(String type) {
        return getServers(type, "");
    }

    public ReturnResult getServers(String type, String storagePoint) {
        HashMap<String, String> params = new HashMap<>();
        if (!Util.isEmpty(storagePoint)) {
            params.put("storage_point", storagePoint);
        }
        params.put("type", type);
        return sendRequest(RequestMethod.GET, URL_API_GET_SERVERS, params);
    }

    /**
     * 获取账号设置
     *
     * @return
     */
    public ReturnResult getAccountSetting() {
        return sendRequest(RequestMethod.GET, URL_API_GET_SETTING, null);
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
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("fullpath", fullpath);
        params.put("start", Integer.toString(start));
        params.put("size", Integer.toString(size));
        return sendRequest(RequestMethod.GET, URL_API_FILE_LIST, params);
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
        String hashsString = "";
        for (String hash : hashs) {
            hashsString += "," + hash;
        }
        hashsString = hashsString.substring(1);
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("hashs", hashsString);
        return sendRequest(RequestMethod.GET, URL_API_FILE_LIST, params);
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
        return sendRequest(RequestMethod.POST, URL_API_FILE_COPY, params);
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
        return sendRequest(RequestMethod.POST, URL_API_FILE_MOVE, params);
    }

    /**
     * 搜索文件
     *
     * @param mountId
     * @param keywords
     * @return
     */
    public ReturnResult fileSearch(int mountId, String keywords) {
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("keyword", keywords);
        return sendRequest(RequestMethod.GET, URL_API_FILE_SEARCH, params);
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
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("fullpath", fullpath);
        params.put("lock", (lock ? "lock" : "unlock"));
        return sendRequest(RequestMethod.POST, URL_API_LOCK, params);
    }

    /**
     * 文件是否存在
     *
     * @param mountId
     * @param hash
     * @return
     */
    public ReturnResult fileExistsByHash(int mountId, String hash) {
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("hash", hash);
        return sendRequest(RequestMethod.GET, URL_API_FILE_EXIST, params);
    }


    /**
     * 文件是否存在
     *
     * @param fullpath
     * @param mountId
     * @return
     */
    public ReturnResult fileExistsByFullPath(int mountId, String fullpath) {
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("fullpath", fullpath);
        return sendRequest(RequestMethod.GET, URL_API_FILE_EXIST, params);
    }

    /**
     * 文件历史版本恢复
     *
     * @param mountId
     * @param fullpath
     * @param hid
     * @return
     */
    public ReturnResult fileRevert(int mountId, String fullpath, String hid) {
        HashMap<String, String> params = new HashMap<>();
        params.put("fullpath", fullpath);
        params.put("mount_id", Integer.toString(mountId));
        params.put("hid", hid);
        return sendRequest(RequestMethod.POST, URL_API_REVERT, params);
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
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("fullpath", fullpath);
        params.put("deadline", deadline);
        params.put("auth", auth);
        params.put("password", password);
        params.put("scope", scope);
        params.put("day", day);
        return sendRequest(RequestMethod.POST, URL_API_FILE_LINK, params);
    }

    /**
     * 获取用户最近访问文件
     *
     * @param size
     * @return
     */
    public ReturnResult getRecentFiles(int size) {
        HashMap<String, String> params = new HashMap<>();
        params.put("size", Long.toString(size));
        return sendRequest(RequestMethod.GET, URL_API_MEMBER_RECENT_FILES, params);
    }

    /**
     * 文件添加备注
     *
     * @param mountId
     * @param fullpath
     * @return
     */
    public ReturnResult addFileRemark(int mountId, String fullpath, String text) {
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("fullpath", fullpath);
        params.put("message", EmojiMapUtil.replaceUnicodeEmojis(text));
        return sendRequest(RequestMethod.POST, URL_API_ADD_FILE_REMARK, params);
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
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("fullpath", fullpath);
        params.put("start", Integer.toString(start));
        params.put("size", Integer.toString(size));
        return sendRequest(RequestMethod.GET, URL_API_GET_FILE_REMARK_LIST, params);
    }


    /**
     * 重命名文件、文件夹
     */
    public ReturnResult renameFile(String fullpath, int mountId, String newName) {
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("newname", newName);
        params.put("fullpath", fullpath);
        return sendRequest(RequestMethod.POST, URL_API_RENAME, params);
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
        String fullpathsString = "";
        for (String fullpath : fullpaths) {
            fullpathsString += "|" + fullpath;
        }
        fullpathsString = fullpathsString.substring(1);
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("fullpaths", fullpathsString);
        return sendRequest(RequestMethod.POST, URL_API_DELETE, params);
    }

    /**
     * 获取文件历史版本
     *
     * @param mountId
     * @param fullpath
     * @return
     */
    public ReturnResult getFileHistory(int mountId, String fullpath) {
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("fullpath", fullpath);
        return sendRequest(RequestMethod.GET, URL_API_GET_FILE_HISTORY, params);
    }

    /**
     * 获取企业成员信息
     *
     * @param entId
     * @param memberId
     * @return
     */
    public ReturnResult getMemberInfo(int entId, int memberId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("ent_id", Integer.toString(entId));
        params.put("_member_id", Integer.toString(memberId));
        return sendRequest(RequestMethod.GET, URL_API_CONTACT_MEMBER_INFO, params);
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
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("fullpath", fullpath);
        params.put("hid", hid);
        params.put("log_act", "20");
        return sendRequest(RequestMethod.GET, URL_API_OPEN, params);
    }

    /**
     * 根据文件filehash获取文件下载路径
     *
     * @param mountId
     * @param filehash
     * @param open
     * @return
     */
    public ReturnResult getFileUrlByFileHash(int mountId, String filehash, boolean open) {
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("filehash", filehash);
        params.put("open", (open ? "1" : "0"));
        return sendRequest(RequestMethod.GET, URL_API_GET_URL_BY_FILEHASH, params);
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
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("fullpath", fullpath);
        params.put("hash", hash);
        return sendRequest(RequestMethod.GET, URL_API_GET_FILE_ATTRIBUTE, params);
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
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("fullpath", fullpath);
        params.put("hid", hid);
        return sendRequest(RequestMethod.GET, URL_API_GET_FILE_INFO, params);
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
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("hash", hash);
        params.put("hid", hid);
        return sendRequest(RequestMethod.GET, URL_API_GET_FILE_INFO, params);
    }

    /**
     * 新建文件(支持秒传)
     *
     * @param mountId
     * @param fullpath
     * @param filehash
     * @param filesize
     * @return
     */
    public ReturnResult addFile(int mountId, String fullpath, String filehash, long filesize, boolean overwrite) {
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("fullpath", fullpath);
        params.put("filehash", filehash);
        params.put("filesize", Long.toString(filesize));
        params.put("overwrite", overwrite ? "1" : "0");
        return sendRequest(RequestMethod.POST, URL_API_CREATE_FILE, params);
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
        return sendRequest(RequestMethod.POST, URL_API_CREATE_FOLDER, params);
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
        return sendRequest(RequestMethod.GET, URL_API_FILE_UPDATE, params);
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
        return sendRequest(RequestMethod.POST, URL_API_FILE_KEYWORD, params);
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
    public ReturnResult fileSave(int mountId, String filename, String filehash, long filesize, int targetMountId, String targetFullpath) {
        return fileSave(mountId, "", filename, filehash, filesize, targetMountId, targetFullpath, null);
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
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("hash", hash);
        params.put("fullpath", filehash);
        return sendRequest(RequestMethod.GET, URL_API_GET_FILE_URL, params);
    }

    /**
     * 文件（夹）转存
     *
     * @param mountId
     * @param fullpath
     * @param filename
     * @param filehash
     * @param filesize
     * @param targetMountId
     * @param targetFullpath
     * @param dialogId
     * @return
     */
    public ReturnResult fileSave(int mountId, String fullpath, String filename, String filehash, long filesize,
                                 int targetMountId, String targetFullpath, String dialogId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("filename", filename);
        params.put("fullpath", fullpath);
        params.put("filehash", filehash);
        params.put("filesize", Long.toString(filesize));
        params.put("target_mount_id", Integer.toString(targetMountId));
        params.put("target_fullpath", targetFullpath);
        params.put("dialog_id", dialogId);
        return sendRequest(RequestMethod.POST, URL_API_GET_FILE_SAVE, params);
    }

    /**
     * 添加文件到默认收藏夹
     *
     * @param mountId
     * @param fullpath
     * @return
     */
    public ReturnResult addFavoriteFile(int mountId, String fullpath) {
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("fullpath", fullpath);
        params.put("fav_id", "-1");
        return sendRequest(RequestMethod.POST, URL_API_ADD_FAVORITE_FILE, params);
    }

    /**
     * 从默认收藏夹取消收藏
     *
     * @param mountId
     * @param fullpath
     * @return
     */
    public ReturnResult delFavoriteFile(int mountId, String fullpath) {
        HashMap<String, String> params = new HashMap<>();
        params.put("mount_id", Integer.toString(mountId));
        params.put("fullpath", fullpath);
        params.put("fav_id", "-1");
        return sendRequest(RequestMethod.POST, URL_API_DEL_FAVORITE_FILE, params);
    }

    /**
     * 获取收藏夹文件列表
     *
     * @param start
     * @param size
     * @return
     */
    public ReturnResult getFavoriteFiles(int start, int size) {
        HashMap<String, String> params = new HashMap<>();
        params.put("fav_id", "-1");
        params.put("start", Integer.toString(start));
        params.put("size", Integer.toString(size));
        return sendRequest(RequestMethod.GET, URL_API_GET_FAVORITE_FILES, params);
    }

    private String getUrl(String uri) {
        if (uri.startsWith("http")) {
            return uri;
        } else {
            if (URL_API.endsWith("/m-api") && uri.startsWith("/m-api")) {
                uri = uri.substring(6);
            }
            return URL_API + uri;
        }
    }

    public ReturnResult sendRequest(RequestMethod method, String uri, HashMap<String, String> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        if (!params.containsKey("token")) {
            params.put("token", getToken());
        }
        return new RequestHelper()
                .setCheckAuth(true)
                .setMethod(method)
                .setUrl(getUrl(uri))
                .setParams(params)
                .executeSync();
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
