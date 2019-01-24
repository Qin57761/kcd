package com.controller.newAdd;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import com.service.newAdd.BmsUserService;
import com.util.creditutil;
import com.util.jsonutil;
import com.util.newAdd.jsonToOther;
@Controller
public class BmsUserController {
	@Autowired
	private BmsUserService bmsUserService;
	HttpServletRequest request;
	HttpServletResponse response;
	
	// �޸��û�����Ȩ�ޣ�
	@RequestMapping(value="/updateUserDeptno.do",produces="text/html;charset=UTF-8")
	public String updateUserDeptno(HttpServletRequest request,HttpServletResponse response,
			int deptno,
			int uid){
		System.err.println(deptno+"--uid:"+uid);
		bmsUserService.updateUserDeptno(deptno, uid); // ͨ�� uid Ψһ�������޸��û�deptno(Ȩ�ޱ��)
		//return "wangye0119/administrator";
		return selectAllUser(request,response);
	}
	
	//ģ����ѯ
	@RequestMapping(value="/selectUserLike.do",produces="text/html;charset=UTF-8")
	public String selectUserLike(
			HttpServletRequest request
			,HttpServletResponse response
			){
		String uname = "";
		try {
			uname = new String(request.getParameter("uname").getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ģ����ѯ��"+uname+"-----");
		ArrayList list = bmsUserService.selectUserLike("%"+uname+"%");
		int userSum = list.size(); // ����ѯ���������
		request.setAttribute("userSum", userSum);   // ����ѯ���������
		request.setAttribute("list", list); // ����ѯ�����ȫ����Ϣ
		return "wangye0119/administrator";
	}
	
	//ɾ���û�
	@RequestMapping(value="/deleteUserByUid.do",produces="text/html;charset=UTF-8")
	public String deleteUserByUid(HttpServletRequest request,HttpServletResponse response,int uid){
		bmsUserService.deleteUserByUid(uid); // ͨ�� uid Ψһ������ɾ��ע��һ���û�
		return selectAllUser(request, response);
	}
	
	//�޸��û�  �Ƿ����
	@RequestMapping(value="/updateUserStatus.do",produces="text/html;charset=UTF-8")
	public String updateUserStatus(HttpServletRequest request,HttpServletResponse response,
			int status,
			int uid){
		if(status == 1){  // 1����      0������         ����û����ã����޸�Ϊ������  
			int a = bmsUserService.updateUserStatus(0,uid);
		}else if(status == 0){
			bmsUserService.updateUserStatus(1,uid);
		}
		return selectAllUser(request, response);
	}

	//����û�ʱ��ѯ�Ƿ��ظ�
	@RequestMapping(value="/addUserBySelectRepeat.do",produces="text/html;charset=UTF-8")
	public void addUserBySelectRepeat(HttpServletRequest request,HttpServletResponse response){
		String username = request.getParameter("username");
		BmsUser addUser = bmsUserService.addUserBySelect(username);
		response.setCharacterEncoding("UTF-8");
		if(addUser != null){
			try { //���˺��Ѵ��ڣ�������ע��
				response.getWriter().println(1);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{ // ���˺ſ���ע��!
			try {
				response.getWriter().println(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//���ϵͳ�û�							 
	@RequestMapping(value="/addBMSUser.do",method=RequestMethod.POST,produces="multipart/form-data;charset=UTF-8")
	public String addBMSUser(HttpServletRequest request,HttpServletResponse response,
			String userName,
			String newpassword2,
			String trueName,
			String userTel,
			int adminRole
			){
//		try {
//			request.setCharacterEncoding("UTF-8");
//			response.setCharacterEncoding("UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		BmsUser bu = new BmsUser();
		bu.setUsername(userName);
		bu.setPassword(newpassword2);
		bu.setUname(trueName);
		bu.setRegTime(creditutil.time().toString());
		bu.setUpdateTime(creditutil.time().toString());
		bu.setUserPhone(userTel);
		bu.setDeptno(adminRole);
		bu.setStatus(1);  // 1����    0δ����
		int a = bmsUserService.addBMSUser(bu);
		if(a>0){
			System.out.println("���ϵͳ�û��ɹ�!");
		}else{
			System.out.println("���ϵͳ�û�ʧ��!");
		}
		return selectAllUser(request,response);
	}
	
	//�����ֱ��ѯ
	@RequestMapping(value="/selectOtherUser.do",produces="text/html;charset=UTF-8")
	public String selectOtherUser(HttpServletRequest request,HttpServletResponse response,int deptno){
		ArrayList listOtherUser = bmsUserService.selectOtherUser(deptno);
		ArrayList listt = bmsUserService.selectAllUser();
		int userSum = listt.size(); // �û�����
		int userSupervisorCounts = 0;  // ��������
		int userManagerCounts = 0; // ҵ��������
		int userSalesmanCounts = 0;    // ҵ��Ա����
		List<Map<String,Object>> list = jsonToOther.stringToListMap(jsonutil.toJSONString(listt));
		for(int i=0;i<list.size();i++){
			Map map = list.get(i);
			double deptnoo = (double) map.get("deptno");
			if(deptnoo == 10){
				userSupervisorCounts++;
				userSupervisorCounts = userSupervisorCounts;
			}else if(deptnoo == 20){
				userManagerCounts++;
				userManagerCounts = userManagerCounts;
			}else if(deptnoo == 30){
				userSalesmanCounts++;
				userSalesmanCounts = userSalesmanCounts;
			}
		}
		request.setAttribute("userSum", userSum);
		request.setAttribute("userSupervisorCounts", userSupervisorCounts);
		request.setAttribute("userManagerCounts", userManagerCounts);
		request.setAttribute("userSalesmanCounts", userSalesmanCounts);
		request.setAttribute("list", listOtherUser); // �����û�ȫ����Ϣ
		return "wangye0119/administrator";
	}
	
	//��ѯ��ϵͳ���е��û�-�Լ�-��ͬ�����û��ĸ���
	@RequestMapping(value="/selectAllUser.do",produces="text/html;charset=UTF-8")
	public String selectAllUser(HttpServletRequest request,HttpServletResponse response){
		ArrayList listUser = bmsUserService.selectAllUser();
		int userSum = listUser.size(); // �û�����
		int userSupervisorCounts = 0;  // ��������
		int userManagerCounts = 0; // ҵ��������
		int userSalesmanCounts = 0;    // ҵ��Ա����
//		System.err.println(listUser); // [{uid=1, username=18637815946, ucid=1, s
//		System.err.println(jsonutil.toJSONString(listUser)); // [{"uid":1,"username":"18637815946","ucid":1,
//		System.err.println(jsonToOther.stringToListMap(jsonutil.toJSONString(listUser))); //[{uid=1.0, username=18637815946, ucid=1.0, status=1.0, upid=1.0
		List<Map<String,Object>> list = jsonToOther.stringToListMap(jsonutil.toJSONString(listUser));
		for(int i=0;i<list.size();i++){
			Map map = list.get(i);
			double deptno = (double) map.get("deptno");
			if(deptno == 10){
				userSupervisorCounts++;
				userSupervisorCounts = userSupervisorCounts;
			}else if(deptno == 20){
				userManagerCounts++;
				userManagerCounts = userManagerCounts;
			}else if(deptno == 30){
				userSalesmanCounts++;
				userSalesmanCounts = userSalesmanCounts;
			}
		}
		request.setAttribute("list", listUser); // �û�ȫ����Ϣ
		request.setAttribute("userSum", userSum);
		request.setAttribute("userSupervisorCounts", userSupervisorCounts);
		request.setAttribute("userManagerCounts", userManagerCounts);
		request.setAttribute("userSalesmanCounts", userSalesmanCounts);
		return "wangye0119/administrator";
	}
	
	//��½
	@RequestMapping(value="/bmsLogin.do",produces="text/html;charset=UTF-8")
	public String bmsLogin(HttpServletRequest request,HttpServletResponse response,
			String username,
			String password
			){
		System.err.println(username+"-----"+password);
		Map bmsAdminMap = null;
		bmsAdminMap = bmsUserService.checkUser(username, password);
		BmsUser buAdmin= jsonutil.toBean(bmsAdminMap,BmsUser.class);
		if(bmsAdminMap!=null){
			int status = buAdmin.getStatus();
			System.err.println("---status---"+status);
			if(status==1){
				System.out.println("��½�ɹ�");
				request.getSession().setAttribute("buAdmin", buAdmin);  
				request.getSession().setAttribute("bms_AdminMap",bmsAdminMap);         	
				request.getSession().setAttribute("bms_username",bmsAdminMap.get("username"));  // �˺�
				request.getSession().setAttribute("bms_password",bmsAdminMap.get("password")); // ����
				request.getSession().setAttribute("bms_uname",bmsAdminMap.get("uname"));      // ����
				request.getSession().setAttribute("bms_id",bmsAdminMap.get("uid"));			 // id
				request.getSession().setAttribute("bms_deptno",bmsAdminMap.get("deptno"));  // ���ű��
				request.getSession().setAttribute("bms_ucid",bmsAdminMap.get("ucid"));     // ucid �û��͹�˾����
				int deptno = buAdmin.getDeptno();
				if(deptno == 40){ //��������Ա
					return "wangye0119/index";
				}
				if(deptno == 10){  // ����
					return "wangye0119/index1"; 
				}
				if(deptno == 20){ // ҵ����
					return "wangye0119/index2";
				}
				if(deptno == 30){ // ҵ��Ա
					return "wangye0119/index3";
				}
			}else{
				System.out.println("��½ʧ��!");
				return "wangye0119/login";
			}
		}else{  // ������
			System.out.println("��½ʧ��!");
			return "wangye0119/login";
		}
		return "wangye0119/login";
	}      
	//�˳�
	@RequestMapping(value="/bmsLoginOut.do",produces="text/html;charset=UTF-8")
	public String bmsLoginOut(HttpSession session,HttpServletRequest request,String username,String password){
		 //���Session 
		 session.invalidate();
		 return "wangye0119/login";					
	}
}


