package com.controller.newAdd;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.model.newAdd.BmsCpyclient;
import com.model.newAdd.BmsUser;
import com.service.newAdd.BmsClientService;
import com.service.newAdd.BmsUserService;
import com.util.creditutil;

@Controller
public class BmsClientController {
	@Autowired
	private BmsClientService bmsClientService;
	@Autowired
	private BmsUserService bmsUserService;
	HttpServletRequest request;
	HttpServletResponse response;
	
	
	//�޸�ĳ�ͻ�������Ա����
	@RequestMapping(value="/updateClientToUserUp.do",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
	public String updateClientToUserUp(HttpServletRequest request,HttpServletResponse response,
			int tid,
			String ucid){
		int a = bmsClientService.updateClientToUserUp(ucid, tid);
		/*
		if(a>0){
			System.out.println("�޸ĳɹ�!");
		}else{
			System.out.println("�޸�ʧ��!");
		}
		*/
		return selectAllClient(request, response);
	}
	
	
	//ҵ�����ҵ��Ա--ģ����ѯ
	@RequestMapping(value="/selectClientLikeManager.do",produces="text/html;charset=UTF-8")
	public String selectClientLikeManager(HttpServletRequest request,HttpServletResponse response,
			String tname){
		String ucid =  request.getSession().getAttribute("bms_id").toString();
		ArrayList list = bmsClientService.selectClientLikeManager(ucid,"%"+tname+"%");
		int clientSum = list.size();
		request.setAttribute("list", list); // ����ѯ�����ȫ����Ϣ
		request.setAttribute("clientSum", clientSum); // ȫ�����û�����
		return "wangye0119/user_list";
	}
	
	//����Ȩ��--ģ����ѯ
	@RequestMapping(value="/selectClientLike.do",produces="text/html;charset=UTF-8")
	public String selectClientLike(HttpServletRequest request,HttpServletResponse response){
		String tname ="";
		try {
			tname = new String(request.getParameter("tname").getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList list = bmsClientService.selectClientLike("%"+tname+"%");
		int clientSum = list.size();
		request.setAttribute("list", list); // ����ѯ�����ȫ����Ϣ
		request.setAttribute("clientSum", clientSum); // ȫ�����û�����
		return "wangye0119/user_list";
	}
	
	//��ӿͻ� ����
	@RequestMapping(value="/addBMSClient.do",method=RequestMethod.POST,produces="multipart/form-data;charset=UTF-8")
	public String addBMSUser(HttpServletRequest request,HttpServletResponse response,
			String tname,
			int sex,
			String clientPhone,
			String cname,
			String clientAddress,
			String ta,
			int status
			){
		BmsCpyclient bc = new BmsCpyclient();
		bc.setTname(tname); 
		bc.setSex(sex);
		bc.setCname(cname);
		bc.setCaddTime(creditutil.time().toString());
		bc.setCupdateTime(creditutil.time().toString());
		bc.setClientPhone(clientPhone);
		bc.setClientAddress(clientAddress);
		bc.setTa(ta);
		bc.setStatus(status); // 1����    0δ����
		String uid = request.getSession().getAttribute("bms_id").toString();
		bc.setUcid(uid);  // ��½�û�������û�����
		int a = bmsClientService.addBMSClient(bc);
		/*
		if(a>0){
			System.out.println("���ϵͳ�û��ɹ�!");
		}else{
			System.out.println("���ϵͳ�û�ʧ��!");
		}
		*/
		return selectAllClient(request,response);
	}
	
	
	//�޸��û�  �Ƿ����
	@RequestMapping(value="/updateClientStatus.do",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
	public String updateClientStatus(HttpServletRequest request,HttpServletResponse response,
			int status,
			int tid){
		if(status == 1){  // 1����      0������         ����û����ã����޸�Ϊ������  
			bmsClientService.updateClientStatus(0, tid);
		}else if(status == 0){
			bmsClientService.updateClientStatus(1,tid);
		}
		return selectAllClient(request, response);
	}
	
	//ɾ���û�
	@RequestMapping(value="/deleteClientByTid.do",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
	public String deleteUserByUid(HttpServletRequest request,HttpServletResponse response,int tid){
		bmsClientService.deleteClientByTid(tid); // ͨ�� Tid Ψһ������ɾ��ע��һ���û�
		return selectAllClient(request, response);
	}
	
	
	//�޸ĵ����û��ĸ������
	@RequestMapping(value="/updateOneClientByTid.do",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
	public String updateOneClientByTid(HttpServletRequest request,HttpServletResponse response,
			String ta,
			String tb,
			String tc,
			String td,
			int tid
			){
		BmsCpyclient oneClient = new BmsCpyclient();
		bmsClientService.updateClientInfo(ta, tb, tc, td, tid);
		return selectOneClientByTid(request,response,tid);
	}
	
	
	//��ѯ�����û��ĸ������
	@RequestMapping(value="/selectOneClientByTid.do",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
	public String selectOneClientByTid(HttpServletRequest request,HttpServletResponse response,int tid){
		BmsCpyclient oneClient = new BmsCpyclient();
		oneClient =	bmsClientService.selectOneClientByTid(tid); // ͨ�� Tid Ψһ������ѯ
		request.setAttribute("oneClient", oneClient);
		return "wangye0119/member-show";
	}
	
	//��ѯ���пͻ�
	@RequestMapping(value="/selectAllClient.do",produces="text/html;charset=UTF-8")
	public String selectAllClient(HttpServletRequest request,HttpServletResponse response){
		int deptno = (int) request.getSession().getAttribute("bms_deptno");
		/*
		 * �ж�Ȩ��
		 * ����Ȩ����ʾ��ͬ�ķ��ؽ��
		 * */
		// ҵ�����ҵ��Ա
		if(deptno == 20 || deptno == 30){
			String ucid = request.getSession().getAttribute("bms_id").toString();
			ArrayList listClient = bmsClientService.selectLoginUserClient(ucid);
			int clientSum = listClient.size();
			request.setAttribute("clientSum", clientSum); // ȫ�����û�����
			request.setAttribute("list", listClient); // �û�ȫ����Ϣ
		}else if(deptno == 10 || deptno == 40){
			// ����
			ArrayList allUser = bmsUserService.selectAllUser();
			ArrayList listClient = bmsClientService.selectAllClient();
			int clientSum = listClient.size();
			request.setAttribute("allUser", allUser); // ����Աȫ����Ϣ
			request.setAttribute("clientSum", clientSum); // ȫ�����û�����
			request.setAttribute("list", listClient); // �ͻ�ȫ����Ϣ
		}
		return "wangye0119/user_list";
	}
}
                                                                                                                                                                                                                  