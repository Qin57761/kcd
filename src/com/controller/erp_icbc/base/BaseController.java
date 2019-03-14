package com.controller.erp_icbc.base;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.controller.erp_icbc.YunXin.YunXinController;
import com.controller.erp_icbc.result.Result;
import com.controller.erp_icbc.utils.Charsets;
import com.controller.erp_icbc.utils.StringEscapeEditor;
import com.model1.icbc.erp.PageData;
/**���� controller
 * @Description:TODO
 * @author:LiWang
 * @time:2018��8��2��
 */
public abstract class BaseController {
	private static Logger log = LogManager.getLogger(BaseController.class.getName());
    public void initBinder(ServletRequestDataBinder binder) {
        /**
         * �Զ�ת���������͵��ֶθ�ʽ
         */
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
        /**
         * ��ֹXSS����
         */
        binder.registerCustomEditor(String.class, new StringEscapeEditor());
    }
    public static String getErrorInfoFromException(Exception e) {  
        try {  
            StringWriter sw = new StringWriter();  
            PrintWriter pw = new PrintWriter(sw);  
            e.printStackTrace(pw);  
            return "\r\n" + sw.toString() + "\r\n";  
        } catch (Exception e2) {  
            return "bad getErrorInfoFromException";  
        }  
    }  
    /**
     * ��ȡ��ǰ��¼�û�id
     * @return {Long}
     */
    public String getUserId(HttpServletRequest request) {
        String id=((PageData)request.getSession().getAttribute("pd")).get("id").toString();
        log.info("��ȡ��ǰ��¼�û�Ψһ��ʶ->"+id);
        return id;
    }
    public static String getTime(){
    	Date time = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String current = sdf.format(time);
        return current;
    }
    /**
     * ajaxʧ��
     * @param msg ʧ�ܵ���Ϣ
     * @return {Object}
     */
    public static Result renderError(String msg) {
        Result result = new Result();
        result.setSuccess(false);
        result.setMessage(msg);
        return result;
    }
    
    /**
     * ajax�ɹ�
     * @return {Object}
     */
    public static Result renderSuccess() {
        Result result = new Result();
        return result;
    }
    
    /**
     * ajax�ɹ�
     * @param msg ��Ϣ
     * @return {Object}
     */
    public static Result renderSuccess(String msg) {
        Result result = new Result();
        result.setMessage(msg);
        return result;
    }

    /**
     * ajax�ɹ�
     * @param obj �ɹ�ʱ�Ķ���
     * @return {Object}
     */
    public   Result renderSuccess(Object obj) {
        Result result = new Result();
        result.setData(obj);
        return result;
    }
    
	/**
	 * �ض�����ת��ת
	 * @param url Ŀ��url
	 */
	protected String redirect(String url) {
		return new StringBuilder("redirect:").append(url).toString();
	}
	/**
	 * �����ļ�
	 * @param file �ļ�
	 */
	protected ResponseEntity<Resource> download(File file) {
		String fileName = file.getName();
		return download(file, fileName);
	}
	
	/**��ȡʵ��·��
	 * @param request
	 * @param s
	 * @return
	 * @Description: TODO
	 * @param name
	 * @return 
	 */
	public static String readPath(HttpServletRequest request,String s){
		return request.getSession().getServletContext().getRealPath(s);
	}
	/**
	 * ����
	 * @param file �ļ�
	 * @param fileName ���ɵ��ļ���
	 * @return {ResponseEntity}
	 */
	protected ResponseEntity<Resource> download(File file, String fileName) {
		/*��ȡ��Դ�ļ��ַ�ʽ
		ClassPathResource   classpath�ж�ȡ
		FileSystemResource  �ļ�ϵͳ�ж�ȡ
		ServletContextResource  ��ȡTomcat�е�application�����ļ�, ���뵼��Spring3-Web.jar��*/
		Resource resource = new FileSystemResource(file);
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String header = request.getHeader("User-Agent");//��ȡ�ͻ���������Ͳ���ϵͳ��Ϣ 
		// �����ָ�� ת��Ϊ��д
		header = header == null ? "" : header.toUpperCase();
		HttpStatus status;
		/*�����ļ�:ͬʱ����chrome Firefox IE ���������д��*/
		if (header.contains("MSIE") || header.contains("TRIDENT") || header.contains("EDGE")) {
			try {
				fileName = URLEncoder.encode(fileName, "utf-8");
				fileName = fileName.replace("+", "%20"); //IE�����ļ����ո��+������
			} catch (UnsupportedEncodingException e) {
				//�����֧��ָ���ı���
				e.printStackTrace();
			}
			status = HttpStatus.OK;
		} else {
			fileName = new String(fileName.getBytes(Charsets.UTF_8), Charsets.ISO_8859_1);
			status = HttpStatus.CREATED;
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", fileName);
		return new ResponseEntity<Resource>(resource, headers, status);
	}
	/**
     * TODO �����ļ�������
     * @author nadim  
     * @date Sep 11, 2015 11:45:31 AM
     * @param fileUrl Զ�̵�ַ
     * @param fileLocal ����·��
     * @throws Exception 
     */
    public static void downloadFile(String fileUrl,File file) throws Exception {
       log.info("���ص�ַ->"+fileUrl);
       URL url = new URL(fileUrl);
       HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
//       urlCon.setConnectTimeout(6000);
//       urlCon.setReadTimeout(6000);
       int code = urlCon.getResponseCode();
       if (code != HttpURLConnection.HTTP_OK) {
           throw new Exception("�ļ���ȡʧ��"+code);
       }
       BufferedInputStream bufferedInputStream = new  BufferedInputStream(urlCon.getInputStream());
       if(!file.exists()){
     	   file.createNewFile();//�����ļ�
       }
       FileOutputStream fileOutputStream = new FileOutputStream(file);
       int count=0;
       byte[] b = new byte[100];
       while((count = bufferedInputStream.read(b)) != -1) {                
           fileOutputStream.write(b, 0,count);
       }
       bufferedInputStream.close();
       fileOutputStream.close();
  }
    public static void urlToWeb(String farUrl, HttpServletResponse response) throws Exception{
    	 URL url = new URL(farUrl);
         HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
         int code = urlCon.getResponseCode();
         if (code != HttpURLConnection.HTTP_OK) {
             throw new Exception("�ļ���ȡʧ��"+code);
         }
         BufferedInputStream bis=null;
         BufferedOutputStream bos=null;
         try{
           bis = new  BufferedInputStream(urlCon.getInputStream());
           String[] ss=farUrl.split("/");
           response.setContentType("application/x-msdownload;");
           /* �����ļ�ͷ�����һ�����������������ļ���(�������ǽ�a.ini)   */
	       response.setHeader("Content-disposition", "attachment; filename=" +ss[ss.length-1]);
	       bos = new BufferedOutputStream(response.getOutputStream());
	       byte[] buff = new byte[2048];
	       int bytesRead;
	       while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
	         bos.write(buff, 0, bytesRead);
	       	bos.flush();
		 }catch (Exception e) {
		    	 e.printStackTrace();  
		 } finally {
	         try {
	             if (bis != null) {
	               bis.close();
	             }
	             if (bos != null) {
	               bos.close();
	             }
	           } catch (Exception e){}
	     }
    }
}
