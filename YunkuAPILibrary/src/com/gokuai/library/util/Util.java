package com.gokuai.library.util;

import org.apache.http.util.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.*;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    private static final String TAG = "Util";
    /**
     * base64加密替换
     *
     * @param str
     * @return
     */
    public static String encodeBase64Replace(String str) {
        return Base64.encodeBytes(str.getBytes()).replace('=', '!')
                .replace('+', '-').replace('/', '|');
    }


    /**
     * base64解密替换
     *
     * @param str
     * @return
     */
    public static String decodeBase64Replace(String str) {
        try {
            return Base64.decode(
                    str.replace('!', '=').replace('-', '+').replace('|', '/')
                            .getBytes()).toString();
        } catch (IOException e) {
            return "";
        }
    }

    public static String getFileSha1(String path) {
        String filehash = "";
        path = URLDecoder.decode(path.replace("file://", ""));
        File file = new File(path);
        if (file.exists()) {
            FileInputStream in = null;
            MessageDigest messagedigest;
            try {
                try {
                    in = new FileInputStream(file);
                    messagedigest = MessageDigest.getInstance("SHA-1");

                    byte[] buffer = new byte[1024 * 1024 * 10];
                    int len = 0;

                    while ((len = in.read(buffer)) > 0) {
                        messagedigest.update(buffer, 0, len);
                    }

                    filehash = toHexString(messagedigest.digest());
                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return filehash;
    }

    /**
     * 获取文件的filehash
     */
    public static String getFileSha1(InputStream in) {
        String filehash = "";
        MessageDigest messagedigest;
        try {
            messagedigest = MessageDigest.getInstance("SHA-1");

            try {
                byte[] buffer = new byte[1024 * 1024 * 10];
                int len = 0;

                while ((len = in.read(buffer)) > 0) {
                    messagedigest.update(buffer, 0, len);
                }
                //FIXME
                filehash = toHexString(messagedigest.digest());
            } catch (OutOfMemoryError e) {
                DebugFlag.logInfo(TAG,"getFileSha1： out of memory");
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filehash;
    }

    private static String toHexString(byte b[]) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
            sb.append(hexChar[b[i] & 0xf]);
        }
        return sb.toString();
    }

    private static char hexChar[] = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


    /**
     * 将流转化为文件
     *
     * @param inputStream
     */
    public static boolean convertStreamToFile(InputStream inputStream, String filePath) {
        OutputStream outputStream = null;
        try {
            // write the inputStream to a FileOutputStream
            outputStream =
                    new FileOutputStream(new File(filePath));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }


        } catch (IOException e) {

            e.printStackTrace();
            return false;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    // outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return true;
    }

    /**
     * MD5加密
     *
     * @param str
     * @return MD5加密后的32位
     */
    public static String convert2MD532(String str) {
        String md5Str = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();
            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            md5Str = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return md5Str;
    }

    /**
     * ArrayList 转 string
     *
     * @param arraylist
     * @param conv
     * @return
     */
    public static String arrayListToString(ArrayList<String> arraylist,
                                           String conv) {
        String strReturn = "";
        int size = arraylist.size();
        if (size > 0) {
            for (int i = 0; i < size - 1; i++) {
                strReturn += arraylist.get(i) + conv;
            }
            strReturn += arraylist.get(size - 1);
        }
        return strReturn;
    }

    /**
     * String[] 转 string
     *
     * @param strArray
     * @param conv
     * @return
     */
    public static String strArrayToString(String[] strArray, String conv) {
        String strReturn = "";
        int length = strArray.length;
        if (length > 0) {
            for (int i = 0; i < length - 1; i++) {
                strReturn += strArray[i] + conv;
            }
            strReturn += strArray[length - 1];
        }
        return strReturn;
    }


    /**
     * @param data 待加密的数据
     * @param key  加密使用的key
     * @return 编码后的String
     */
    public static String getHmacSha1(String data, String key) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(),
                    "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] text = mac.doFinal(data.getBytes());
            String result = Base64.encodeBytes(text);
            return result.trim();
        } catch (Exception e) {
            return "";
        }
    }

    public static String getHmacSha1(byte[] data, String key) {
        try {
            SecretKeySpec signingKey=new SecretKeySpec(key.getBytes(),"HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] text = mac.doFinal(data);
            String result = Base64.encodeBytes(text);
            return result.trim();

        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 得到路径分隔符在文件路径中最后出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
     *
     * @param fileName 文件路径
     * @return 路径分隔符在路径中最后出现的位置，没有出现时返回-1。
     */
    public static int getPathLastIndex(String fileName) {
        int point = fileName.lastIndexOf('/');
        if (point == -1) {
            point = fileName.lastIndexOf('\\');
        }
        return point;
    }

    /**
     * 得到路径分隔符在文件路径中指定位置前最后出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
     *
     * @param fileName  文件路径
     * @param fromIndex 开始查找的位置
     * @return 路径分隔符在路径中指定位置前最后出现的位置，没有出现时返回-1。
     */
    public static int getPathLastIndex(String fileName, int fromIndex) {
        int point = fileName.lastIndexOf('/', fromIndex);
        if (point == -1) {
            point = fileName.lastIndexOf('\\', fromIndex);
        }
        return point;
    }

    /**
     * 得到文件名中的父路径部分。 对两种路径分隔符都有效。 不存在时返回""。
     * 如果文件名是以路径分隔符结尾的则不考虑该分隔符，例如"/path/"返回""。
     *
     * @param fileName 文件名
     * @return 父路径，不存在或者已经是父目录时返回""
     */
    public static String getPathPart(String fileName) {
        int point = getPathLastIndex(fileName);
        int length = fileName.length();
        if (point == -1) {
            return "";
        } else if (point == length - 1) {
            int secondPoint = getPathLastIndex(fileName, point - 1);
            if (secondPoint == -1) {
                return "";
            } else {
                return fileName.substring(0, secondPoint);
            }
        } else {
            return fileName.substring(0, point);
        }
    }

    /**
     * 得到文件路径中的文件名
     *
     * @param filePath 文件路径名
     * @return 文件名
     */
    public static String getNameFromPath(String filePath) {
        int point = getPathLastIndex(filePath);
        int length = filePath.length();
        if (point == -1) {
            return filePath;
        } else {
            return filePath.substring(point, length);
        }
    }

    /**
     * @param folderPath
     * @return
     */
    public static String getTargetFolderName(String folderPath) {
        if (folderPath.equals("")) return "";

        folderPath = folderPath.substring(0, folderPath.length() - 1);
        return getNameFromPath(folderPath);
    }


    public final static String TIMEFORMAT_YMD = "yyyy/MM/dd";
    public final static String TIMEFORMAT = "yyyy/MM/dd HH:mm:ss";
    public final static String TIMEFORMAT_WITHOUT_SECONDS = "yyyy/M/d HH:mm";
    public final static String TIMEFORMAT_HS = "HH:mm";
    private final static SimpleDateFormat TIMEFORMATRFC822 = new SimpleDateFormat(
            "EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'Z", Locale.US);

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(TIMEFORMAT_YMD);
        return sdf.format(new Date());
    }

    /**
     * 格式化时间
     *
     * @param secondes
     * @return
     */
    public static String formateTime(long secondes) {
        long milliseconds = secondes * 1000;
        SimpleDateFormat formatter = new SimpleDateFormat(
                TIMEFORMAT_WITHOUT_SECONDS);
        Date date = new Date(milliseconds);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String time = formatter.format(date);
        return time;
    }

    /**
     * 格式化时间
     *
     * @param milliseconds
     * @return
     */
    public static String formateTime(long milliseconds, String timeFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(timeFormat);
        Date date = new Date(milliseconds);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String time = formatter.format(date);
        return time;
    }

    private static final Object[][] TIMEAGOMAP = new Object[][]{{1, "秒"},
            {60, "分钟"}, {3600, "小时"}, {86400, "天"}, {604800, "周"},
            {2592000, "个月"}, {31536000, "年"}};

    /**
     * 距离现在过去的时间
     *
     * @param dateline
     * @return
     */
    public static String timeago(int dateline) {
        int now = (int) (System.currentTimeMillis() / 1000);
        int diff = dateline - now;
        if (diff == 0) {
            return "刚刚";
        }
        int abs_diff = Math.abs(diff);
        String suffix = "";
        String return_str = "";
        if (diff < 0) {
            suffix = "前";
        } else {
            suffix = "后";
        }

        int index = 0;
        Object[] lastObj = null;
        for (Object[] obj : TIMEAGOMAP) {
            int key = (Integer) obj[0];
            if (abs_diff < key) {
                int num = (int) Math.floor(abs_diff / (Integer) lastObj[0]);
                return_str += String.format("%1$s" + lastObj[1], num);
                return_str += suffix;
                break;
            }
            index++;
            if (index == TIMEAGOMAP.length) {
                return_str = formateTime((long) dateline * 1000, TIMEFORMAT_YMD);
                break;
            }
            lastObj = obj;
        }
        return return_str;
    }

//    /**
//     * 获取当前RFC822时间
//     *
//     * @return
//     */
//    public static String getDateAsRFC822String() {
//        Date date = new Date();
//        String time = TIMEFORMATRFC822.format(date);
//        return time;
//    }
//


    /**
     * 重命名文件或者文件夹
     *
     * @param oldPath
     * @param newName
     */
    public static void renameFile(String oldPath, String newName) {
        File file = new File(oldPath);
        if (!file.exists()) {
            return;
        }
        String newPath = newName;

        if (file.getParent() != null) {
            newPath = file.getParent() + File.separator + newName;
        }
        file.renameTo(new File(newPath));
    }

    public static void copyToClipboard(String result) {
        //复制到粘贴
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        StringSelection stringSel = new StringSelection(result);
        clipboard.setContents(stringSel, null);
    }


    /**
     * 删除文件或者文件夹
     *
     * @param path
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            if (file.isFile()) {
                file.delete();
            }
            return;
        }
        String[] tempList = file.list();
        String childFilePath = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                childFilePath = path + tempList[i];
            } else {
                childFilePath = path + File.separator + tempList[i];
            }
            File temp = new File(childFilePath);
            if (temp.isFile()) {
                temp.delete();
            } else if (temp.isDirectory()) {
                deleteFile(childFilePath);
            }
        }
        file.delete();
    }


    /*
     * Helper function to build the downloading text.
     */
    public static String getDownloadingText(long totalBytes, long currentBytes) {
        if (totalBytes <= 0) {
            return "";
        }
        long progress = currentBytes * 100 / totalBytes;
        StringBuilder sb = new StringBuilder();
        sb.append(progress);
        sb.append('%');
        return sb.toString();
    }


    /**
     * 0时区时间
     *
     * @return
     */
    public static long getUnixDateline() {
        Calendar ca = Calendar.getInstance(Locale.US);
        return ca.getTimeInMillis() / 1000;
    }

    public static boolean isHttpUrl(String s) {
        String regEx = "(http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?";
        String regEx2 = "[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?";

        Pattern p = Pattern.compile(regEx);
        Pattern p2 = Pattern.compile(regEx2);
        Matcher m = p.matcher(s);
        Matcher m2 = p2.matcher(s);
        return m.matches() || m2.matches();
    }

    public static String convertToByteString(byte[] objectGUID) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < objectGUID.length; i++) {
            String transformed = prefixZeros((int) objectGUID[i] & 0xFF);
            result.append("\\");
            result.append(transformed);
        }
        return result.toString();
    }

    private static String prefixZeros(int value) {
        if (value <= 0xF) {
            StringBuilder sb = new StringBuilder("0");
            sb.append(Integer.toHexString(value));
            return sb.toString();
        } else {
            return Integer.toHexString(value);
        }
    }

    public static byte[] getContent(String filePath) throws IOException {
        File file = new File(filePath);

        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            System.out.println("file too big...");
            return null;
        }

        FileInputStream fi = new FileInputStream(file);

        byte[] buffer = new byte[(int) fileSize];

        int offset = 0;

        int numRead = 0;

        while (offset < buffer.length

                && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {

            offset += numRead;

        }

        // 确保所有数据均被读取

        if (offset != buffer.length) {

            throw new IOException("Could not completely read file "
                    + file.getName());

        }

        fi.close();

        return buffer;
    }


    public static InputStream String2InputStream(String str) {
        InputStream stream = new ByteArrayInputStream(str.getBytes());
        return stream;
    }


    public static boolean isContainSpecail(String folderName) {
        String strPattern = "^[\u4e00-\u9fa5_a-zA-Z0-9\\s]+$";

        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(folderName);
        return m.matches();
    }

    /**
     * 将InputStream转换成某种字符编码的String
     *
     * @param in
     * @param encoding
     * @return
     * @throws Exception
     */
    public static String inputstream2String(InputStream in, String encoding) throws Exception {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[4096];
        int count = -1;
        while ((count = in.read(data, 0, 4096)) != -1)
            outStream.write(data, 0, count);

        data = null;
        return new String(outStream.toByteArray(), encoding);
    }

    public static void stringToFile(String str, String filename) {
        try {
            BufferedReader in = new BufferedReader(new StringReader(str));

            PrintWriter out = new PrintWriter(new FileWriter(filename));

            String s;

            while ((s = in.readLine()) != null) {
                out.println(s);
            }

            out.close();

        } catch (IOException e4) {
            e4.printStackTrace();
        }

    }

    public static byte[] input2byte(InputStream inStream)
            throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }

    /**
     * 对象转数组
     *
     * @param obj
     * @return
     */
    public static byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }


    /**
     * 数组转对象
     *
     * @param bytes
     * @return
     */
    public static Object toObject(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    public static byte[] shortToByteArray(short s) {
        byte[] shortBuf = new byte[2];
        for (int i = 0; i < 2; i++) {
            int offset = i * 8;
            shortBuf[i] = (byte) ((s >>> offset) & 0xff);
        }
        return shortBuf;
    }

    /**
     * 将32位整数转换成长度为4的byte数组
     *
     * @param s int
     * @return byte[]
     */
    public static byte[] intToByteArray(int s) {
        byte[] targets = new byte[4];
        for (int i = 0; i < 4; i++) {
            int offset = i * 8;
            targets[i] = (byte) ((s >>> offset) & 0xff);
        }
        return targets;
    }


    public static String intArrayToString(int[] strArray, String conv) {
        String strReturn = "";
        int length = strArray.length;
        if (length > 0) {
            for (int i = 0; i < length - 1; i++) {
                strReturn += strArray[i] + conv;
            }
            strReturn += strArray[length - 1];
        }
        return strReturn;
    }


    /**
     * 获取url中query的参数
     *
     * @param query
     * @return
     */
    public static Map<String, String> getQueryMap(String query) {
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (String param : params) {
            String name = param.split("=")[0];
            String value = param.split("=")[1];
            map.put(name, value);
        }
        return map;
    }

    public static String getUrlFromHashMapParams(String url, HashMap<String, String> params) {
        String paramsString = getParamsStringFromHashMapParams(params);
        if (!TextUtils.isEmpty(paramsString)) {
            url += "?" + paramsString;
        }
        return url;
    }

    public static String getParamsStringFromHashMapParams(HashMap<String, String> params) {
        String paramsString = "";
        if (params != null && params.size() > 0) {
            Set<String> keys = params.keySet();

            for (String key : keys) {
                paramsString += key + "=" + URLEncoder.encodeUTF8(params.get(key) + "") + "&";
            }
            if (paramsString.endsWith("&")) {
                paramsString = paramsString.substring(0, paramsString.length() - 1);
            }
        }
        return paramsString;
    }


    /**
     * 判断是否有网络
     * @return
     */
    public static boolean isNetworkAvailableEx() {
//        return isNetworkAvailable(CustomApplication.getInstance());

        //FIXME 这里可以加网络判断执行的方法
        return true;
    }

    /**
     * 生成6个随机字符
     *
     * @return
     */
    public static String getSixRandomChars() {
        String result = "";

        for (int i = 0; i < 6; ++i) {
            int intVal = (int) (Math.random() * 26 + 97);
            result = result + (char) intVal;
        }

        return result;
    }



}
