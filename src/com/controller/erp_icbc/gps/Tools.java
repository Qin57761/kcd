/*
* @Description: ���ù��ܷ������ܡ������ַ����࣬���ݿ��࣬���ڲ����࣬�ļ���
* @Author: tt
* @Date: 2018-12-12 17:55:41
 * @LastEditTime: 2019-01-22 10:59:47
 * @LastEditors: tt
*/
package com.controller.erp_icbc.gps;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.security.MessageDigest;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class Tools<K, V> {
    static final public char sp = 5;// �ָ����
    public static boolean showlog = true;

    /**
     * =================================�ַ����ȳ��ô�����=================================
     * map��jsong�ַ����Ĵ�����Ҫ���ð���Ͱ͵Ŀ�Դ���fastjson��pom.xml�������´��� <dependency>
     * <groupId>com.alibaba</groupId> <artifactId>fastjson</artifactId>
     * <version>1.2.4</version> </dependency>
     *
     * @param mpStr
     * @return
     */
    private static void mylog(String mString) {
        if (showlog == true) {
            // mylog(this.toString() + ":" + mString);
            Log log = LogFactory.getLog(Tools.class);
            log.info(mString);
        }
    }

    /**
     * Map<String,Object>��Map<String,String>��ת��
     * 
     * @param mso
     * @return
     */
    public static Map<String, String> msoToMss(Map<String, Object> mso) {
        Map<String, String> params = new HashMap<>();
        for (String key : mso.keySet()) {
            params.put(key, mso.get(key).toString());
        }
        return params;
    }

    /**
     * List<Map<String,String>>��List<Map<String,Object>>��ת��
     * 
     * @param lss
     * @return
     */
    public static List<Map<String, Object>> lssTolso(List<Map<String, String>> lss) {
        String lssJson = jsonEnCode(lss);
        List<Map<String, Object>> lmso = new ArrayList<>();
        lmso = (List<Map<String, Object>>) JSON.parse(lssJson);
        return lmso;
    }

    /**
     * OBJECT��ʽ��ת����JSONG�ַ���
     *
     * @param object
     * @return
     */
    public static String jsonEnCode(Object object) {
        return JSON.toJSONString(object, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteNullListAsEmpty);
    }

    /**
     * ����object
     *
     * @param mpStr
     * @return
     */
    public static Object jsonDeCode(String mpStr) {
        return JSON.parse(mpStr);
    }

    /**
     * ����mss,��mso��ת��mss
     *
     * @param mpStr
     * @return
     */
    public static Map<String, String> jsonDeCode_mpob(String mpStr) {
        Map<String, String> mp = new HashMap<>();
        Map<String, Object> mo = JSONObject.fromObject(mpStr);
        for (String key : mo.keySet()) {
            mp.put(key, mo.get(key).toString());
        }
        return mp;
    }

    /**
     * ת��Map��ʽ��json��ʽ�ַ���
     *
     * @param mpStr
     * @return
     */
    public static String jsonEnCode(Map<String, String> mpStr) {
        return JSON.toJSONString(mpStr);
    }

    /**
     * JSON��ʽ�ַ�����Map<String,String>��ת��
     *
     * @param mpStr
     * @return
     */
    public static Map<String, String> jsonDeCode_mp(String mpStr) {
        Map<String, String> mp = new HashMap<>();
        Map maps = (Map) JSON.parse(mpStr);
        for (Object map : maps.entrySet()) {
            mp.put(((Map.Entry) map).getKey().toString(), ((Map.Entry) map).getValue().toString());
        }
        return mp;
    }

    /**
     * ���ظ�ʽΪ2018/09/11�������ַ���·����ȡ��ǰ���ڣ�
     *
     * @return
     */
    public static String dirDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("/yyyy/MM/dd/");
        String dateString = formatter.format(new Date());
        return dateString;
    }

    /**
     * ȡMD5ֵ
     *
     * @param inStr
     * @return
     */
    public static String md5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            mylog(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * ��ȡ����ַ���
     *
     * @param length
     * @return
     */
    public static String getRandomStringByLength(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * �ַ����������Ƿ����ָ��ֵ
     *
     * @param arr
     * @param targetValue
     * @return
     */
    public static boolean arrayIndexOf(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }

    /**
     * ����ʱ���ʽ�ַ���ת��Ϊʱ�� yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    public static long time() {
        return System.currentTimeMillis() / 1000;
    }

    public static long time(String strDate, Boolean bMillis) {
        long result = myisnull(strDate) ? new Date().getTime() : Tools.strToDateLong(strDate).getTime();
        result = bMillis == false ? result / 1000 : result;
        return result;
    }

    /**
     * ����ʱ���ʽʱ��ת��Ϊ�ַ��� yyyy-MM-dd HH:mm:ss
     *
     * @param dateDate
     * @return
     */
    public static String dateToStrLong(Date dateDate) {
        if (dateDate == null) {
            dateDate = new Date();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * ����ʱ���ʽʱ��ת��Ϊ�ַ��� yyyy-MM-dd
     *
     * @param dateDate
     * @param k
     * @return
     */
    public static String dateToStr(Date dateDate) {
        if (dateDate == null) {
            dateDate = new Date();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * ����ʱ���ʽ�ַ���ת��Ϊʱ�� yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    public static boolean myisnull(String s) {
        return (s == null || s.isEmpty());
    }

    /**
     * ɾ��map�е�ĳ��key
     */
    public static Map<String, String> deleteKeyOfMap(Map<String, String> paramsMap, String ID) {
        Iterator<String> iter = paramsMap.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            if (ID.equals(key)) {
                iter.remove();
            }
        }
        return paramsMap;
    }

    /**
     * ʹ��ʱ�����޸ĵġ���ȡ��ǰ��½�û���id
     */
    public static long mid() {
        // todo ��ȡ��ǰ��½�û���id��
        try {
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                    .getSession();
            long id = (long) session.getAttribute("tt_mid");
            /*
             * String idmd5 =(String) session.getAttribute("idmd5"); Map<String, String>
             * info
             * =recinfo("select mid from sys_session where idmd5='"+idmd5+"' and outdt=0");
             * if(info.size()<=0){//����Ѿ��������˵�½���ˣ�����δ��½״̬ id = 0 ; }
             */
            return id;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * ʹ��ʱ�����޸ĵġ���ȡ��ǰ��½���û��Ƿ��Ǻ�̨����ԱȨ��
     */
    public static boolean isadmin() {
        // todo ��ȡ��ǰ��½�û��Ƿ��Ǻ�̨����Ա����Ҫ����һ����־��
        /* ֱ�Ӵ�session�л�ȡ��Ҳ���Դ����ݱ��л�ȡ��ǰ��½�û���isadmin�ֶ��Ƿ�Ϊ1���ж��Ƿ����Ա */
        try {
            return (boolean) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                    .getSession().getAttribute("tt_isadmin");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * ָ����list���Ƿ����ĳ��String
     *
     * @param str
     * @param arr
     * @return
     */
    public static boolean in_array_list(String str, List<String> arr) {
        return arr.contains(str);
    }

    /**
     * MAP��String��ת��
     *
     * @param map
     * @param fgchar
     * @return
     */
    public static String maptostring(Map<String, String> map, String fgchar) {
        if (myisnull(fgchar)) {
            fgchar = ",";
        }
        StringBuilder sb = new StringBuilder();
        Iterator<Entry<String, String>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, String> entry = (Entry<String, String>) iter.next();
            sb.append(entry.getKey());
            sb.append('=').append('"');
            sb.append(entry.getValue());
            sb.append('"');
            if (iter.hasNext()) {
                sb.append(fgchar).append(' ');
            }
        }
        return sb.toString();
    }

    /**
     * ����mysql ע��
     */
    public static boolean sql_inj(String str) {
        str = str.toLowerCase();
        Map<String, String> mpNoFilters = new HashMap<>();// ���õļ��������ų������������ٶ�
        mpNoFilters.put("form", "1");
        mpNoFilters.put("do", "1");
        mpNoFilters.put("cn", "1");
        mpNoFilters.put("id", "1");
        mpNoFilters.put("mid_add", "1");
        mpNoFilters.put("mid_edit", "1");
        mpNoFilters.put("wxmini_car_store", "1");
        mpNoFilters.put("car_stora", "1");
        mpNoFilters.put("color", "1");
        if (mpNoFilters.get(str) == "1") {
            return false;
        }
        String inj_str = "'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|+|,|alert";
        // ����Ķ����������Լ����
        char af = 5;
        String[] inj_stra = inj_str.split(String.valueOf(af));
        for (int i = 0; i < inj_stra.length; i++) {
            if (str.indexOf(inj_stra[i]) >= 0) {
                System.err.println(str + "_ע��__��ƥ����˹ؼ��֣�" + inj_stra[i]);
                return true;
            }
        }
        return false;
    }

    /**
     * @description: js�����滻��������ʽ����,ȥ��js��ǩ
     * @param {type}
     * @return:
     */
    public static String js_inj_replace(String str) {
        str = java.util.regex.Pattern.compile("<[^><]*script[^><]*>", Pattern.CASE_INSENSITIVE).matcher(str)
                .replaceAll("");
        return str;
    }

    public static Map<String, String> getpostmap(HttpServletRequest request) {
        return getpostmap(request, false);// url������post������һ����url��post�������ظ���ֵʱ��ÿ��ֵ��sp��������
    }

    /**
     * ��ȡrequest�����в�������ֵ���浽map
     *
     * @param request
     * @return
     */
    public static Map<String, String> getpostmap(HttpServletRequest request, boolean onlyPost) {
        Map<String, String> result = new HashMap<>();
        Enumeration<String> ennum = request.getParameterNames();
        Map<String, String> mpUrlFiters = null;
        if (onlyPost){
            String urlQuerysString = request.getQueryString();
            mpUrlFiters = URLRequest(urlQuerysString); //���ֻ�ռ�post���ݣ�url���治���ǣ���Ҫ���˵�url���ظ���ֵ
        }
        while (ennum.hasMoreElements()) {
            String paramName = (String) ennum.nextElement();
            if (sql_inj(paramName) == true) {// ���˲�����
                mylog("mysql������ע�룺" + paramName);
                continue;
            } else {

            }
            String[] values = request.getParameterValues(paramName);
            if (values.length > 0 && onlyPost && mpUrlFiters.containsKey(paramName)) {
                values[0] = "{****}";//��url�����ֵ�ظ������õ�һ������url�����ظ����ֶΣ�ֵ��Ч
            }
            String value = "";
            for (int i = 0; i < values.length; i++) {
                if (values[i].equals("{****}")) {// ���˵���һ��
                    continue;
                }
                value += values[i] + sp;
            }
            if (myisnull(value) == false) {
                value = value.substring(0, value.length() - 1);// ȥ��char(5);
            }
            if (sql_inj(value) == true) {// ���˲���ֵ
                mylog("mysql����ֵע�룺" + value);
            } else {
                result.put(js_inj_replace(paramName), js_inj_replace(value));
            }
        }
        return result;
    }

    /**
     * @description: ��ȡURL�����б�
     * @param {type}
     * @return:
     */
    public static Map<String, String> getUrlParam() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String urlQuerysString = request.getQueryString();
        mylog("getQueryString:" + urlQuerysString);
        Map<String, String> mpUrl = URLRequest(urlQuerysString);
        return mpUrl;
    }

    /**
     * ������url�����еļ�ֵ�� �� "cn=admin&type=demo&sdo=form&id=21"cn:admin,type:demo�ȴ���map��
     * 
     * @return mapRequest
     */
    public static Map<String, String> URLRequest(String strUrlParam) {
        Map<String, String> mapRequest = new HashMap<String, String>();

        String[] arrSplit = null;
        if (strUrlParam == null) {
            return mapRequest;
        }
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");
            // ��������ֵ
            if (arrSplitEqual.length > 1) {
                // ��ȷ����
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

            } else {
                if (arrSplitEqual[0] != "") {
                    // ֻ�в���û��ֵ��������
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    /* ����ɾ��ָ���ֶκ��url*,useAge urlKill("cn|id|type"); ����ע�������֤ */
    public static String urlKill(String s) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String urlQuerysString = request.getQueryString();
        mylog("getQueryString:" + urlQuerysString);
        String result = "";
        Map<String, String> mpUrl = URLRequest(urlQuerysString);
        s = s.toLowerCase();
        String[] ss = s.split("\\|");
        for (Iterator<Map.Entry<String, String>> it = mpUrl.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, String> item = it.next();
            if (arrayIndexOf(ss, item.getKey().toLowerCase())) {
                it.remove();
            }
            // ... todo with item
        }
        for (String key : mpUrl.keySet()) {
            if (myisnull(key)) {
                continue;
            }
            result = result + "&" + key + "=" + mpUrl.get(key);
        }
        if (!myisnull(result)) {
            result = "?" + result.substring(1, result.length());// ȥ��ǰ����������&
        } else {
            result = "?";
        }
        result = request.getRequestURI() + result;
        return result;
    }

    /**
     * @description: ��ȡ��ǰURL�Ļ�����ַ����http://kjtest.kcway.net/
     * @param {type}
     * @return:
     */
    public static String getBaseUrl() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                    .getRequest();
            return request.getScheme() + "://" + request.getServerName()
                    + (request.getServerPort() == 80 ? "" : ":" + request.getServerPort()) + "/";
        } catch (Exception E) {
            mylog(E.getMessage());
            return "";
        }
    }




   
   
  
    /**
     * =================================�ļ�io��ش�����,���๦�ܲο�FileTools.java=================================
     * FileExists������ļ�/Ŀ¼�Ƿ����
     */
    public static boolean fileExists(String strFileFullPath) {
        File file = new File(strFileFullPath);
        return file.exists();
    }

    /**
     * �Ƿ�Ŀ¼
     *
     * @param strFileFullPath
     * @return
     */
    public static boolean isDir(String strFileFullPath) {
        File file = new File(strFileFullPath);
        return file.exists() ? file.isDirectory() : false;
    }

    public static boolean createDir(String strFileFullPath) {
        if (isDir(strFileFullPath)) {
            return true;
        }
        File file = new File(strFileFullPath);
        file.mkdirs();
        return isDir(strFileFullPath);
    }

    /**
     * ɾ��һ���ļ�/Ŀ¼��Ŀ¼ʱ�����Ŀ¼�����๦�ܲο�FileTools.java
     *
     * @param strFileFullPath
     * @return
     */
    public static boolean delFile(String strFileFullPath) {
        File file = new File(strFileFullPath);
        if (file.exists()) {// �ļ�����
            return file.delete();
        } else { // todo �����ڣ�ֱ�ӷ�����
            return true;
        }
    }

    /**
     * ��ȡ�ļ���չ��������.
     *
     * @param strFullFilePath
     * @return
     */
    public static String getFileExt(String strFullFilePath) {
        return strFullFilePath.substring(strFullFilePath.lastIndexOf(".") + 1);
    }

    /**
     * ��ȡ�ļ���չ��������.
     *
     * @param strFullFilePath
     * @return
     */
    public static String extractFileExt(String strFullFilePath) {
        return getFileExt(strFullFilePath);
    }

    /**
     * ��ʽ��һ���ļ�·���ַ���������·���ָ���б�˺ͷ�б��windows��\\,linux��/�����⣬��ʽ������Ŀǰ����Ļ�������ȷ�ķָ�����ͬʱȥ���ظ��ķָ���
     * ��http�ı�����Сд
     *
     * @param strFilePath
     * @return
     */
    public static String formatFilePath(String strFilePath) {
        strFilePath = strFilePath.replace("\\\\", "\\");
        strFilePath = strFilePath.replace("\\\\", "\\");
        strFilePath = strFilePath.replace("//", "/");
        strFilePath = strFilePath.replace("//", "/");
        strFilePath = strFilePath.replace("http:/", "http://"); // ԭ��http://�����http:/�ˣ����Իָ�http��//
        strFilePath = strFilePath.replace("https:/", "https://");
        return strFilePath.replace('/', File.separatorChar).replace('\\', File.separatorChar);
    }

    /**
     * ��ȡһ����·���ַ������ļ���
     *
     * @param strFullFilePath
     * @return
     */
    public static String extractFileName(String strFullFilePath) {
        return strFullFilePath.substring(formatFilePath(strFullFilePath).lastIndexOf(File.separator) + 1);
    }

    /**
     * ��ȡһ����·������ȥ���ļ�����ģ�����/����\\
     *
     * @param strFullFilePath
     * @return
     */
    public static String extractFilePath(String strFullFilePath) {
        return strFullFilePath.substring(0, strFullFilePath.length() - extractFileName(strFullFilePath).length());
    }

    /**
     * ����ǰ·��ĩβ���Ϸָ���\\����/
     *
     * @param strFullFilePath
     * @return
     */
    public static String addSpc(String strFullFilePath) {
        if (myisnull(strFullFilePath) == false
                && !strFullFilePath.substring(strFullFilePath.length() - 1).equals(File.separator)) {
            return strFullFilePath + File.separator;
        } else {
            return strFullFilePath;
        }
    }

    /**
     * ɾ��·����ĩβ��/����\\
     *
     * @param strFullFilePath
     * @return
     */
    public static String delSpc(String strFullFilePath) {
        if (myisnull(strFullFilePath) == false
                && strFullFilePath.substring(strFullFilePath.length() - 1).equals(File.separator)) {
            while (strFullFilePath.substring(strFullFilePath.length() - 1).equals(File.separator)) {
                strFullFilePath = strFullFilePath.substring(0, strFullFilePath.length() - 1);
            }
            return strFullFilePath;
        } else {
            return strFullFilePath;
        }
    }

    /**
     * �����ļ��������ݵ�ǰ����ʱ��
     */
    public static String getTimeMd5FileName() {
        String result = String.valueOf(System.currentTimeMillis());
        return md5(result);
    }

   

    /**
     * @description: ��ʽ�����
     * @param {type}
     * @return:
     */
    public static void formatResult(Map<String, String> result, boolean success, int code, String msg,
            String next_url) {
        result.put("success", success ? "true" : "false");
        result.put("errorcode", success ? "0" : String.valueOf(code));
        result.put("msg", msg);
        result.put("next_url", next_url);
    }

    /**
     * @description: ��ʽ�������������object
     * @param {type}
     * @return:
     */
    public static void formatResultobj(Map<String, Object> result, boolean success, int code, String msg) {
        result.put("success", success ? true : false);
        result.put("errorcode", success ? 0 : code);
        result.put("msg", msg);
    }

 

    /**
     * @description: ��ȡ��Ŀ·��
     * @param {type}
     * @return:
     */
    public static String getRootPath() {
        File directory = new File("");// ����Ϊ��
        String author = directory.getAbsolutePath();// ����·��;
        return addSpc(author);
    }

 
}
