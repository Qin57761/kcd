/*
 * @Description: file content
 * @Author: tt
 * @Date: 2018-12-12 18:06:31
 * @LastEditTime: 2019-01-21 15:08:02
 * @LastEditors: tt
 */
package com.controller.erp_icbc.gps;

/**
 * HTTP����ش�������,Get��POST�����ַ�ʽʵ��
 * �Ƽ���httpClientGet��httpClientPost����������¿���������������Get��Post
 * �������ڣ�2018-11-29
 * �����£�2018-12-12
 */
import java.io.*;
import java.net.*;
import java.util.*;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class HttpTools {
    private static Logger log = LoggerFactory.getLogger(HttpTools.class);

    /**
     * ����xml,���ص�һ��Ԫ�ؼ�ֵ�ԡ������һ��Ԫ�����ӽڵ㣬��˽ڵ��ֵ���ӽڵ��xml���ݡ�
     */
    public static Map doXMLParse(String strxml) throws Exception {
        if (null == strxml || "".equals(strxml)) {
            return null;
        }
        /* ============= !!!!ע�⣬�޸���΢�Źٷ�������©����������2018-10-16 =========== */
        try {
            Map<String, String> data = new HashMap<String, String>();
            // TODO ���������
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            documentBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            documentBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            documentBuilderFactory.setXIncludeAware(false);
            documentBuilderFactory.setExpandEntityReferences(false);
            InputStream stream = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
            org.w3c.dom.Document doc = documentBuilderFactory.newDocumentBuilder().parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            try {
                stream.close();
            } catch (Exception ex) {
                // do nothing
            }
            return data;
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * ��ȥ�����еĿ�ֵ��ǩ������
     * 
     * @param sArray ǩ��������
     * @return ȥ����ֵ��ǩ�����������ǩ��������
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<String, String>();
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                    || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }

    /**
     * ��������ȡIP��ַ
     * 
     * @param request
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "nuknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "nuknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "nuknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * ��������ȡIP+[IP������ַ]
     *
     * @param request
     */
    public static String getIpBelongAddress(HttpServletRequest request) {
        String ip = getIpAddress(request);
        String belongIp = getIPbelongAddress(ip);
        return ip + belongIp;
    }

    /**
     * ��������ȡIP������ַ
     *
     * @param ip
     */
    public static String getIPbelongAddress(String ip) {
        String ipAddress = "[]";
        try {
            // �Ա��ṩ�ķ����ַ
            String context = call("http://ip.taobao.com/service/getIpInfo.php?ip=" + ip);
            JSONObject fromObject = JSONObject.fromObject(context);
            String code = fromObject.getString("code");
            if (code.equals("0")) {
                JSONObject jsonObject = fromObject.getJSONObject("data");
                ipAddress = (String) jsonObject.get("country");
                jsonObject.get("city");
            }
        } catch (Exception e) {
            log.error("��ȡIP������ַ����", e);
            e.printStackTrace();
        }
        return ipAddress;
    }

    /**
     * ��������ȡIp������ַ
     *
     * @param urlStr
     */
    public static String call(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setConnectTimeout(3000);
            httpCon.setDoInput(true);
            httpCon.setRequestMethod("GET");
            int code = httpCon.getResponseCode();
            if (code == 200) {
                return streamConvertToSting(httpCon.getInputStream());
            }
        } catch (Exception e) {
            log.error("��ȡIP������ַ����", e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ��������InputStreamת����String
     *
     * @param is
     */
    public static String streamConvertToSting(InputStream is) {
        String tempStr = "";
        try {
            if (is == null)
                return null;
            ByteArrayOutputStream arrayOut = new ByteArrayOutputStream();
            byte[] by = new byte[1024];
            int len = 0;
            while ((len = is.read(by)) != -1) {
                arrayOut.write(by, 0, len);
            }
            tempStr = new String(arrayOut.toByteArray());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return tempStr;
    }

    /**
     * ����������Ԫ�����򣬲����ա�����=����ֵ����ģʽ�á�&���ַ�ƴ�ӳ��ַ���
     *
     * @param params ��Ҫ���򲢲����ַ�ƴ�ӵĲ�����
     * @return ƴ�Ӻ��ַ���
     */
    public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1) {// ƴ��ʱ�����������һ��&�ַ�
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }

    /**
     * utf-8����
     */
    static class Utf8ResponseHandler implements ResponseHandler<String> {
        public String handleResponse(final HttpResponse response) throws HttpResponseException, IOException {
            final StatusLine statusLine = response.getStatusLine();
            final HttpEntity entity = response.getEntity();
            if (statusLine.getStatusCode() >= 300) {
                EntityUtils.consume(entity);
                throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
            }
            return entity == null ? null : EntityUtils.toString(entity, "UTF-8");
        }
    }

    /**
     * ��ȡ�û���Ϣ���� --HTTPS����
     *
     * @param requestUrl    �����ַ
     * @param requestMethod ����ʽ��GET��POST��
     * @param outputStr     �ύ������
     * @return JSONObject(ͨ��JSONObject.get ( key)�ķ�ʽ��ȡjson���������ֵ)
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
            // ����SSLContext���󣬲�ʹ������ָ�������ι�������ʼ��
            /*
             * TrustManager[] tm = {new SSLTools()}; SSLContext sslContext =
             * SSLContext.getInstance("SSL", "ttss"); sslContext.init(null, tm, new
             * java.security.SecureRandom()); // ������SSLContext�����еõ�SSLSocketFactory����
             * SSLSocketFactory ssf = sslContext.getSocketFactory();
             */
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            // conn.setSSLSocketFactory(ssf);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // ��������ʽ��GET/POST��
            conn.setRequestMethod(requestMethod);
            // ��outputStr��Ϊnullʱ�������д����
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // ע������ʽ
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // ����������ȡ��������
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // �ͷ���Դ
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("���ӳ�ʱ��{}", ce);
        } catch (Exception e) {
            log.error("https�����쳣��{}", e);
        }
        return jsonObject;
    }

    /**
     * @Description:ʹ��HttpURLConnection����post����
     */
    public static String sendPost(String urlParam, Map<String, Object> params, String charset) {
        StringBuffer resultBuffer = null;
        // �����������
        StringBuffer sbParams = new StringBuffer();
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> e : params.entrySet()) {
                sbParams.append(e.getKey());
                sbParams.append("=");
                sbParams.append(e.getValue());
                sbParams.append("&");
            }
        }
        HttpURLConnection con = null;
        OutputStreamWriter osw = null;
        BufferedReader br = null;
        // ��������
        try {
            URL url = new URL(urlParam);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            if (sbParams != null && sbParams.length() > 0) {
                osw = new OutputStreamWriter(con.getOutputStream(), charset);
                osw.write(sbParams.substring(0, sbParams.length() - 1));
                osw.flush();
            }
            // ��ȡ��������
            resultBuffer = new StringBuffer();
            int contentLength = Integer.parseInt(con.getHeaderField("Content-Length"));
            if (contentLength > 0) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
                String temp;
                while ((temp = br.readLine()) != null) {
                    resultBuffer.append(temp);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException e) {
                    osw = null;
                    throw new RuntimeException(e);
                } finally {
                    if (con != null) {
                        con.disconnect();
                        con = null;
                    }
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    br = null;
                    throw new RuntimeException(e);
                } finally {
                    if (con != null) {
                        con.disconnect();
                        con = null;
                    }
                }
            }
        }
        return resultBuffer.toString();
    }

    /**
     * @Description:ʹ��URLConnection����post
     */
    public static String sendPost2(String urlParam, Map<String, Object> params, String charset) {
        StringBuffer resultBuffer = null;
        // �����������
        StringBuffer sbParams = new StringBuffer();
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> e : params.entrySet()) {
                sbParams.append(e.getKey());
                sbParams.append("=");
                sbParams.append(e.getValue());
                sbParams.append("&");
            }
        }
        URLConnection con = null;
        OutputStreamWriter osw = null;
        BufferedReader br = null;
        try {
            URL realUrl = new URL(urlParam);
            // �򿪺�URL֮�������
            con = realUrl.openConnection();
            // ����ͨ�õ���������
            con.setRequestProperty("accept", "*/*");
            con.setRequestProperty("connection", "Keep-Alive");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // ����POST�������������������
            con.setDoOutput(true);
            con.setDoInput(true);
            // ��ȡURLConnection�����Ӧ�������
            osw = new OutputStreamWriter(con.getOutputStream(), charset);
            if (sbParams != null && sbParams.length() > 0) {
                // �����������
                osw.write(sbParams.substring(0, sbParams.length() - 1));
                // flush������Ļ���
                osw.flush();
            }
            // ����BufferedReader����������ȡURL����Ӧ
            resultBuffer = new StringBuffer();
            int contentLength = Integer.parseInt(con.getHeaderField("Content-Length"));
            if (contentLength > 0) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
                String temp;
                while ((temp = br.readLine()) != null) {
                    resultBuffer.append(temp);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException e) {
                    osw = null;
                    throw new RuntimeException(e);
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    br = null;
                    throw new RuntimeException(e);
                }
            }
        }
        return resultBuffer.toString();
    }

    /**
     * @Description:����get���󱣴������ļ�
     */
    public static void sendGetAndSaveFile(String urlParam, Map<String, Object> params, String fileSavePath) {
        // �����������
        StringBuffer sbParams = new StringBuffer();
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                sbParams.append(entry.getKey());
                sbParams.append("=");
                sbParams.append(entry.getValue());
                sbParams.append("&");
            }
        }
        HttpURLConnection con = null;
        BufferedReader br = null;
        FileOutputStream os = null;
        try {
            URL url = null;
            if (sbParams != null && sbParams.length() > 0) {
                url = new URL(urlParam + "?" + sbParams.substring(0, sbParams.length() - 1));
            } else {
                url = new URL(urlParam);
            }
            con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.connect();
            InputStream is = con.getInputStream();
            os = new FileOutputStream(fileSavePath);
            byte buf[] = new byte[1024];
            int count = 0;
            while ((count = is.read(buf)) != -1) {
                os.write(buf, 0, count);
            }
            os.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    os = null;
                    throw new RuntimeException(e);
                } finally {
                    if (con != null) {
                        con.disconnect();
                        con = null;
                    }
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    br = null;
                    throw new RuntimeException(e);
                } finally {
                    if (con != null) {
                        con.disconnect();
                        con = null;
                    }
                }
            }
        }
    }
    /**
     * @Description:ʹ��HttpURLConnection����get����
     */
    public static String sendGet(String urlParam, Map<String, Object> params, String charset) {
        StringBuffer resultBuffer = null;
        // �����������
        StringBuffer sbParams = new StringBuffer();
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                sbParams.append(entry.getKey());
                sbParams.append("=");
                sbParams.append(entry.getValue());
                sbParams.append("&");
            }
        }
        HttpURLConnection con = null;
        BufferedReader br = null;
        try {
            URL url = null;
            if (sbParams != null && sbParams.length() > 0) {
                url = new URL(urlParam + "?" + sbParams.substring(0, sbParams.length() - 1));
            } else {
                url = new URL(urlParam);
            }
            con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.connect();
            resultBuffer = new StringBuffer();
            br = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
            String temp;
            while ((temp = br.readLine()) != null) {
                resultBuffer.append(temp);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    br = null;
                    throw new RuntimeException(e);
                } finally {
                    if (con != null) {
                        con.disconnect();
                        con = null;
                    }
                }
            }
        }
        return resultBuffer.toString();
    }

    /**
     * @Description:ʹ��URLConnection����get����
     */
    public static String sendGet2(String urlParam, Map<String, Object> params, String charset) {
        StringBuffer resultBuffer = null;
        // �����������
        StringBuffer sbParams = new StringBuffer();
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                sbParams.append(entry.getKey());
                sbParams.append("=");
                sbParams.append(entry.getValue());
                sbParams.append("&");
            }
        }
        BufferedReader br = null;
        try {
            URL url = null;
            if (sbParams != null && sbParams.length() > 0) {
                url = new URL(urlParam + "?" + sbParams.substring(0, sbParams.length() - 1));
            } else {
                url = new URL(urlParam);
            }
            URLConnection con = url.openConnection();
            // ������������
            con.setRequestProperty("accept", "*/*");
            con.setRequestProperty("connection", "Keep-Alive");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // ��������
            con.connect();
            resultBuffer = new StringBuffer();
            br = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
            String temp;
            while ((temp = br.readLine()) != null) {
                resultBuffer.append(temp);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    br = null;
                    throw new RuntimeException(e);
                }
            }
        }
        return resultBuffer.toString();
    }

    public static Map<String, String> httpClientPostmss(String urlParam, Map<String, Object> params, String charset,
            Map<String, String> headers) {
        String s = httpClientPost(urlParam, params, charset, headers);
        Map<String, String> result = Tools.msoToMss(JSONObject.fromObject(s));
        return result;
    }

     /**
     * @Description:ʹ��HttpClient����post����,params�����&����urlEncode
     */
    public static String httpClientPost_String(String urlParam, String params, String charset,
            Map<String, String> headers) {
        StringBuffer resultBuffer = null;
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(urlParam);
        // �����������
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        BufferedReader br = null;
        try {
                System.out.println("param:"+params);
                StringEntity entity = new StringEntity(params, charset);
                httpPost.setEntity(entity);
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entryHeader : headers.entrySet()) {
                    httpPost.addHeader(entryHeader.getKey(), entryHeader.getValue());
                }
            }
            HttpResponse response = client.execute(httpPost);
            // ��ȡ��������Ӧ����
            resultBuffer = new StringBuffer();
            br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String temp;
            while ((temp = br.readLine()) != null) {
                resultBuffer.append(temp);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    br = null;
                    throw new RuntimeException(e);
                }
            }
        }
        return resultBuffer.toString();
    }
    /**
     * @Description:ʹ��HttpClient����post����,params�����&����urlEncode
     */
    public static String httpClientPost(String urlParam, Map<String, Object> params, String charset,
            Map<String, String> headers) {
        StringBuffer resultBuffer = null;
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(urlParam);
        // �����������
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> elem = iterator.next();
            list.add(new BasicNameValuePair(elem.getKey(), String.valueOf(elem.getValue())));
        }
        BufferedReader br = null;
        try {
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entryHeader : headers.entrySet()) {
                    httpPost.addHeader(entryHeader.getKey(), entryHeader.getValue());
                }
            }
            HttpResponse response = client.execute(httpPost);
            // ��ȡ��������Ӧ����
            resultBuffer = new StringBuffer();
            br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String temp;
            while ((temp = br.readLine()) != null) {
                resultBuffer.append(temp);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    br = null;
                    throw new RuntimeException(e);
                }
            }
        }
        return resultBuffer.toString();
    }

    /**
     * @Description:ʹ��HttpClient����get����
     */
    public static String httpClientGet(String urlParam, Map<String, Object> params, String charset,
            Map<String, String> headers) {
        StringBuffer resultBuffer = null;
        HttpClient client = new DefaultHttpClient();
        BufferedReader br = null;
        // �����������
        StringBuffer sbParams = new StringBuffer();
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                sbParams.append(entry.getKey());
                sbParams.append("=");
                try {
                    sbParams.append(URLEncoder.encode(String.valueOf(entry.getValue()), charset));// todo,������Զ�urlencode
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                sbParams.append("&");
            }
        }
        if (sbParams != null && sbParams.length() > 0) {
            urlParam = urlParam + "?" + sbParams.substring(0, sbParams.length() - 1);
        }
        HttpGet httpGet = new HttpGet(urlParam);
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entryHeader : headers.entrySet()) {
                httpGet.addHeader(entryHeader.getKey(), entryHeader.getValue());
            }
        }
        try {
            HttpResponse response = client.execute(httpGet);
            // ��ȡ��������Ӧ����
            br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String temp;
            resultBuffer = new StringBuffer();
            while ((temp = br.readLine()) != null) {
                resultBuffer.append(temp);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    br = null;
                    throw new RuntimeException(e);
                }
            }
        }
        return resultBuffer.toString();
    }

    /**
     * @Description:ʹ��socket����post����
     */
    public static String sendSocketPost(String urlParam, Map<String, Object> params, String charset) {
        String result = "";
        // �����������
        StringBuffer sbParams = new StringBuffer();
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                sbParams.append(entry.getKey());
                sbParams.append("=");
                sbParams.append(entry.getValue());
                sbParams.append("&");
            }
        }
        Socket socket = null;
        OutputStreamWriter osw = null;
        InputStream is = null;
        try {
            URL url = new URL(urlParam);
            String host = url.getHost();
            int port = url.getPort();
            if (-1 == port) {
                port = 80;
            }
            String path = url.getPath();
            socket = new Socket(host, port);
            StringBuffer sb = new StringBuffer();
            sb.append("POST " + path + " HTTP/1.1\r\n");
            sb.append("Host: " + host + "\r\n");
            sb.append("Connection: Keep-Alive\r\n");
            sb.append("Content-Type: application/x-www-form-urlencoded; charset=utf-8 \r\n");
            sb.append("Content-Length: ").append(sb.toString().getBytes().length).append("\r\n");
            // ����һ���س����У���ʾ��Ϣͷд�꣬��Ȼ������������ȴ�
            sb.append("\r\n");
            if (sbParams != null && sbParams.length() > 0) {
                sb.append(sbParams.substring(0, sbParams.length() - 1));
            }
            osw = new OutputStreamWriter(socket.getOutputStream());
            osw.write(sb.toString());
            osw.flush();
            is = socket.getInputStream();
            String line = null;
            // ��������Ӧ�����ݳ���
            int contentLength = 0;
            // ��ȡhttp��Ӧͷ����Ϣ
            do {
                line = readLine(is, 0, charset);
                if (line.startsWith("Content-Length")) {
                    // �õ���Ӧ�����ݳ���
                    contentLength = Integer.parseInt(line.split(":")[1].trim());
                }
                // ���������һ�������Ļس����У����ʾ����ͷ����
            } while (!line.equals("\r\n"));
            // ��ȡ����Ӧ�����ݣ�������Ҫ�����ݣ�
            result = readLine(is, contentLength, charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException e) {
                    osw = null;
                    throw new RuntimeException(e);
                } finally {
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            socket = null;
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    is = null;
                    throw new RuntimeException(e);
                } finally {
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            socket = null;
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * @Description:ʹ��socket����get����
     */
    public static String sendSocketGet(String urlParam, Map<String, Object> params, String charset) {
        String result = "";
        // �����������
        StringBuffer sbParams = new StringBuffer();
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                sbParams.append(entry.getKey());
                sbParams.append("=");
                sbParams.append(entry.getValue());
                sbParams.append("&");
            }
        }
        Socket socket = null;
        OutputStreamWriter osw = null;
        InputStream is = null;
        try {
            URL url = new URL(urlParam);
            String host = url.getHost();
            int port = url.getPort();
            if (-1 == port) {
                port = 80;
            }
            String path = url.getPath();
            socket = new Socket(host, port);
            StringBuffer sb = new StringBuffer();
            sb.append("GET " + path + " HTTP/1.1\r\n");
            sb.append("Host: " + host + "\r\n");
            sb.append("Connection: Keep-Alive\r\n");
            sb.append("Content-Type: application/x-www-form-urlencoded; charset=utf-8 \r\n");
            sb.append("Content-Length: ").append(sb.toString().getBytes().length).append("\r\n");
            // ����һ���س����У���ʾ��Ϣͷд�꣬��Ȼ������������ȴ�
            sb.append("\r\n");
            if (sbParams != null && sbParams.length() > 0) {
                sb.append(sbParams.substring(0, sbParams.length() - 1));
            }
            osw = new OutputStreamWriter(socket.getOutputStream());
            osw.write(sb.toString());
            osw.flush();
            is = socket.getInputStream();
            String line = null;
            // ��������Ӧ�����ݳ���
            int contentLength = 0;
            // ��ȡhttp��Ӧͷ����Ϣ
            do {
                line = readLine(is, 0, charset);
                if (line.startsWith("Content-Length")) {
                    // �õ���Ӧ�����ݳ���
                    contentLength = Integer.parseInt(line.split(":")[1].trim());
                }
                // ���������һ�������Ļس����У����ʾ����ͷ����
            } while (!line.equals("\r\n"));
            // ��ȡ����Ӧ�����ݣ�������Ҫ�����ݣ�
            result = readLine(is, contentLength, charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException e) {
                    osw = null;
                    throw new RuntimeException(e);
                } finally {
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            socket = null;
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    is = null;
                    throw new RuntimeException(e);
                } finally {
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            socket = null;
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * @Description:��ȡһ�����ݣ�contentLe���ݳ���Ϊ0ʱ����ȡ��Ӧͷ��Ϣ����Ϊ0ʱ������
     */
    private static String readLine(InputStream is, int contentLength, String charset) throws IOException {
        List<Byte> lineByte = new ArrayList<Byte>();
        byte tempByte;
        int cumsum = 0;
        if (contentLength != 0) {
            do {
                tempByte = (byte) is.read();
                lineByte.add(Byte.valueOf(tempByte));
                cumsum++;
            } while (cumsum < contentLength);// cumsum����contentLength��ʾ�Ѷ���
        } else {
            do {
                tempByte = (byte) is.read();
                lineByte.add(Byte.valueOf(tempByte));
            } while (tempByte != 10);// ���з���ascii��ֵΪ10
        }
        byte[] resutlBytes = new byte[lineByte.size()];
        for (int i = 0; i < lineByte.size(); i++) {
            resutlBytes[i] = (lineByte.get(i)).byteValue();
        }
        return new String(resutlBytes, charset);
    }

    /**
     * ��ָ��URL����GET����������,��������urlencode
     * 
     * @param url   ���������URL
     * @param param ����������������Ӧ���� name1=value1&name2=value2 ����ʽ��
     * @return URL ������Զ����Դ����Ӧ���
     */
    public static String httpClientGet(String url, String param, boolean urlEncode) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            if (Tools.myisnull(param) == false) {
                if (urlEncode) {
                    param = URLEncoder.encode(param, "UTF-8");// ����urlencode
                }
                urlNameString = url + "?" + param;
            }
            URL realUrl = new URL(urlNameString);// URLEncoder.encode(param, "UTF-8")
            // �򿪺�URL֮�������
            URLConnection connection = realUrl.openConnection();
            // ����ͨ�õ���������
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // ����ʵ�ʵ�����
            connection.connect();
            // ��ȡ������Ӧͷ�ֶ�
            Map<String, List<String>> map = connection.getHeaderFields();
            // �������е���Ӧͷ�ֶ�
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // ���� BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("����GET��������쳣��" + e);
            e.printStackTrace();
        }
        // ʹ��finally�����ر�������
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * ��ָ�� URL ����POST����������
     *
     * @param url   ��������� URL
     * @param param ����������������Ӧ���� name1=value1&name2=value2 ����ʽ��
     * @return ������Զ����Դ����Ӧ���
     */
    public static String httpClientPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // �򿪺�URL֮�������
            URLConnection conn = realUrl.openConnection();
            // ����ͨ�õ���������
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // ����POST�������������������
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // ��ȡURLConnection�����Ӧ�������
            out = new PrintWriter(conn.getOutputStream());
            // �����������
            out.print(param);
            // flush������Ļ���
            out.flush();
            // ����BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("���� POST ��������쳣��" + e);
            e.printStackTrace();
        }
        // ʹ��finally�����ر��������������
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
