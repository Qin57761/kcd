package com.controller.erp_icbc;

import java.io.File;
import java.security.MessageDigest;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;

/*
 * ���ù�����,���ֳ��õķ���������
 * ������2018-11-23
 * */
public class Tools<K, V> {
	static final public char sp = 5;// �ָ����

	/**
	 * =================================�ַ����ȳ��ô�����==============================
	 * === map��jsong�ַ����Ĵ�����Ҫ���ð���Ͱ͵Ŀ�Դ���fastjson��pom.xml�������´��� <dependency>
	 * <groupId>com.alibaba</groupId> <artifactId>fastjson</artifactId>
	 * <version>1.2.4</version> </dependency>
	 *
	 * @param mpStr
	 * @return
	 */
	public static String jsonEnCode(Map<String, String> mpStr) {
		return JSON.toJSONString(mpStr);
	}

	public static Map<String, String> jsonDeCode(String mpStr) {
		Map<String, String> mp = new HashMap<>();
		Map maps = (Map) JSON.parse(mpStr);
		for (Object map : maps.entrySet()) {
			mp.put(((Map.Entry) map).getKey().toString(), ((Map.Entry) map)
					.getValue().toString());
		}
		return mp;
	}

	public static String dirDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("/yyyy/MM/dd/");
		String dateString = formatter.format(new Date());
		return dateString;
	}

	public static String md5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
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
	 * @param
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
		if (s == null || s.equals("") || s.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ɾ��map�е�ĳ��key
	 */
	public static Map<String, String> deleteKeyOfMap(
			Map<String, String> paramsMap, String ID) {
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
			return (int) ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest().getSession()
					.getAttribute("tt_mid");
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
			return (boolean) ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest().getSession()
					.getAttribute("tt_isadmin");
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean in_array_list(String str, List<String> arr) {
		return arr.contains(str);
	}

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
		if (mpNoFilters.get(str) == "1") {
			return false;
		}
		String inj_str = "";
		// ����Ķ����������Լ����
		String[] inj_stra = inj_str.split("\\|");
		for (int i = 0; i < inj_stra.length; i++) {
			if (str.indexOf(inj_stra[i]) >= 0) {
				System.out.println(str + "_ע��__" + i);
				return false;
			}
		}
		return false;
	}

	public static Map<String, String> getpostmap(HttpServletRequest request) {
		Map<String, String> result = new HashMap<>();
		Enumeration<String> ennum = request.getParameterNames();
		while (ennum.hasMoreElements()) {
			String paramName = (String) ennum.nextElement();
			if (sql_inj(paramName) == true) {// ���˲�����
				continue;
			}
			String[] values = request.getParameterValues(paramName);
			String value = "";
			for (int i = 0; i < values.length; i++) {
				value += values[i] + sp;
			}
			if (myisnull(value) == false) {
				value = value.substring(0, value.length() - 1);// ȥ��char(5);
			}
			if (sql_inj(value) == true) {// ���˲���ֵ
				System.out.println("mysqlע�룺" + value);
			} else {
				result.put(paramName, value);
			}
		}
		System.out.println("�������" + JSON.toJSONString(result));
		return result;
	}

	public static boolean inarray_s(String[] arr, String targetValue) {
		return Arrays.asList(arr).contains(targetValue);
	}

	/* ����ɾ��ָ���ֶκ��url*,useAge urlKill("cn|id|type"); */
	public static String urlKill(String s) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String result = "";
		Map<String, String> mpUrl = getpostmap(request);
		s = s.toLowerCase();
		String[] ss = s.split("\\|");
		for (Iterator<Map.Entry<String, String>> it = mpUrl.entrySet()
				.iterator(); it.hasNext();) {
			Map.Entry<String, String> item = it.next();
			if (inarray_s(ss, item.getKey().toLowerCase())) {
				it.remove();
			}
			// ... todo with item
		}
		for (String key : mpUrl.keySet()) {
			result = result + "&" + key + "=" + mpUrl.get(key);
		}
		if (myisnull(result) == false) {
			result = "?" + result.substring(1);
		}
		return request.getRequestURI() + result;
	}

	/**
	 * =================================�ļ�io��ش�����,���๦�ܲο�FileTools.java========
	 * ========================= FileExists������ļ�/Ŀ¼�Ƿ����
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
	 * ��ʽ��һ���ļ�·���ַ���������·���ָ���б�˺ͷ�б��windows��\\,linux��/�����⣬��ʽ������Ŀǰ����Ļ�������ȷ�ķָ�����
	 * ͬʱȥ���ظ��ķָ���
	 *
	 * @param strFilePath
	 * @return
	 */
	public static String formatFilePath(String strFilePath) {
		strFilePath = strFilePath.replace("\\\\", "\\");
		strFilePath = strFilePath.replace("\\\\", "\\");
		strFilePath = strFilePath.replace("//", "/");
		strFilePath = strFilePath.replace("//", "/");
		return strFilePath.replace('/', File.separatorChar).replace('\\',
				File.separatorChar);
	}

	/**
	 * ��ȡһ����·���ַ������ļ���
	 *
	 * @param strFullFilePath
	 * @return
	 */
	public static String extractFileName(String strFullFilePath) {
		return strFullFilePath.substring(formatFilePath(strFullFilePath)
				.lastIndexOf(File.separator) + 1);
	}

	/**
	 * ��ȡһ����·������ȥ���ļ�����ģ�����/����\\
	 *
	 * @param strFullFilePath
	 * @return
	 */
	public static String extractFilePath(String strFullFilePath) {
		return strFullFilePath.substring(0, strFullFilePath.length()
				- extractFileName(strFullFilePath).length());
	}

	/**
	 * ����ǰ·��ĩβ���Ϸָ���\\����/
	 *
	 * @param strFullFilePath
	 * @return
	 */
	public static String addSpc(String strFullFilePath) {
		if (myisnull(strFullFilePath) == false
				&& !strFullFilePath.substring(strFullFilePath.length() - 1)
						.equals(File.separator)) {
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
				&& strFullFilePath.substring(strFullFilePath.length() - 1)
						.equals(File.separator)) {
			while (strFullFilePath.substring(strFullFilePath.length() - 1)
					.equals(File.separator)) {
				strFullFilePath = strFullFilePath.substring(0,
						strFullFilePath.length() - 1);
			}
			return strFullFilePath;
		} else {
			return strFullFilePath;
		}
	}
}
