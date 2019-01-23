package com.controller.newAdd;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;
import org.apache.jasper.tagplugins.jstl.core.Out;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.model.newAdd.BmsUser;
import com.service.newAdd.BmsClientService;
import com.service.newAdd.BmsUserService;

import com.util.creditutil;
import com.util.jsonutil;
import com.util.newAdd.jsonToOther;
@Controller
public class BmsTeamController {
	@Autowired
	private BmsUserService bmsUserService;
	@Autowired
	private BmsClientService bmsClientService;
	HttpServletRequest request;
	HttpServletResponse response;
	
	
	//ɾ���û�
	@RequestMapping(value="/deleteManagerUserByUid.do",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
	public String deleteManagerUserByUid(HttpServletRequest request,HttpServletResponse response,
			int uid,
			int upid
			){
		bmsUserService.deleteUserByUid(uid); // ͨ�� uid Ψһ������ɾ��ע��һ���û�
		return selectLoginUserTeam(request,response,upid);
	}
	
	//����Ŷ��³�Ա������ʾ�ó�Ա�����еĿͻ���Ϣ
	@RequestMapping(value="/selectOtherUserClient.do",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
	public String selectOtherUserClient(HttpServletRequest request,HttpServletResponse response,String ucid){
		System.err.println(ucid+"-----------------");
		ArrayList list = bmsClientService.selectLoginUserClient(ucid); // ��ѯ��½�û��µ��Ŷ�
		request.setAttribute("list", list); // ����ѯ�����ȫ����Ϣ
		return "wangye0119/user_list";
	}
	
	//��ѯ��½�û����Ŷ���Ա
	@RequestMapping(value="/selectLoginUserTeam.do",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
	public String selectLoginUserTeam(HttpServletRequest request,HttpServletResponse response,int upid){
		//int upid = (int) request.getSession().getAttribute("bms_id");
		ArrayList list = bmsUserService.selectLoginUserTeam(upid); // ��ѯ��½�û��µ��Ŷ�
		ArrayList managerList = bmsUserService.selectOtherUser(20); //��ѯ����
		int managerSum = managerList.size();
		request.setAttribute("list", list); // ����ѯ�����ȫ����Ϣ
		request.setAttribute("managerList", managerList); // ����ѯ��ҵ����
		request.setAttribute("managerSum", managerSum); // ����ѯ��ҵ���������
		request.setAttribute("upid", upid); // ҵ�����Ŷӱ��
		return "wangye0119/Shop_list";
	}
}


