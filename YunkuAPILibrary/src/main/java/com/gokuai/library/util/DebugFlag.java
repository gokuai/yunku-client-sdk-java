package com.gokuai.library.util;

public class DebugFlag {

    private static final String LOG_TAG_PREFIX = "YKLog->";

    private static final boolean FLAG_INFO = true;//test

    public static boolean LOG_VISIBLE = false;

    public static void logInfo(String tag, String msg) {
        if (FLAG_INFO && LOG_VISIBLE) {
            System.out.println(LOG_TAG_PREFIX + tag + " " + msg);
        }
    }
}
