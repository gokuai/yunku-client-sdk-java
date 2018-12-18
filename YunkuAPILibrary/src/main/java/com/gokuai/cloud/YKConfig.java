package com.gokuai.cloud;

import com.gokuai.library.Config;

import java.net.MalformedURLException;
import java.net.URL;


public class YKConfig extends Config {

    public static String ENT_DOMAIN = "";

    //==============这些参数会在HostConfigHelper中替换=====

    public static String CLIENT_ID;
    public static String CLIENT_SECRET;

    public static String URL_API_HOST;
    public static String URL_HOST;

    public static String URL_USER_AVATAR_FORMAT_BY_NAME;
    public static String URL_USER_AVATAR_FORMAT_BY_MEMBERID;
    public static String URL_USER_AVATAR_FORMAT;

    public static String FILE_THUMBNAIL_FORMAT;

    public static String URL_ACCOUNT_AUTO_LOGIN;

    //====================================================
}
