package com.controller.intercept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.model1.icbc.erp.PageData;

import javax.servlet.http.HttpSession; 
/** 
 * ��¼��֤�������� 
 */
public class LoginInterceptor implements HandlerInterceptor{ 
  
 /** 
  * Handlerִ�����֮������������ 
  */
 @Override
public void afterCompletion(HttpServletRequest request, 
   HttpServletResponse response, Object handler, Exception exc) 
   throws Exception { 
    
 } 
  
 /** 
  * Handlerִ��֮��ModelAndView����֮ǰ����������� 
  */
 @Override
public void postHandle(HttpServletRequest request, HttpServletResponse response, 
   Object handler, ModelAndView modelAndView) throws Exception { 
 } 
  
 /** 
  * Handlerִ��֮ǰ����������� 
  */
 @Override
public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
   Object handler) throws Exception { 
  //��ȡ�����URL 
  String url = request.getRequestURI(); 
  System.out.println("�����url:"+url);
  System.out.println("*****:"+url.indexOf("erp"));
  //URL:login.jsp�ǹ�����;���demo�ǳ���login.jsp�ǿ��Թ������ʵģ�������URL���������ؿ��� 
  if(url.indexOf("ulogin.do")>=0||url.indexOf("erp/erp_login.do")>=0){ 
   return true; 
  } 
  //��ȡSession 
  HttpSession session = request.getSession(); 
  String username = (String)session.getAttribute("username");  
  PageData pd =(PageData) session.getAttribute("pd"); 
  if(username != null||pd!=null){ 
   return true; 
  }
 
  if(url.indexOf("erp")>0){
	//�����������ģ���ת����¼���� 
	  request.getRequestDispatcher("/kjs_icbc/login.jsp").forward(request, response);   
  }else{
	//�����������ģ���ת����¼���� 
	  request.getRequestDispatcher("/cskjs_wzb/login.jsp").forward(request, response);   
  }
  
    
  
  return false; 
 } 
  
} 
