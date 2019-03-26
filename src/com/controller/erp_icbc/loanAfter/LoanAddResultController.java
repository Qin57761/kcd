package com.controller.erp_icbc.loanAfter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.model1.icbc.erp.PageData;
import com.service1.Repayment.OverdueService;
import com.service1.loan.ClientPaymentService;
import com.service1.loan.LoanOverdueService;
import com.util.limitutil;

/**
 * �ͻ���߿�����
 * 
 * @author ��ʮ����
 * 2019-3-22
 */
@Controller
@RequestMapping("/loan")
public class LoanAddResultController {
	@Autowired
	private LoanOverdueService loanOverdueService;
	@Autowired
	private ClientPaymentService clientPaymentService;
	
	//��ӵ�߼�¼+�ϳ�δ����
	@RequestMapping("/addPhoneResult.do")
	@ResponseBody
	public String addPhoneResult(
			int type_id,
			int type_status,
			String result_msg,
			int icbc_id,
			int lolId,
			HttpServletRequest request){
		//��ȡ��ǰ��������Ϣ
		PageData pdsession= (PageData)request.getSession().getAttribute("pd");
		PageData addResult=new PageData();
		addResult.put("qryid",lolId);
		addResult.put("mid_add",pdsession.get("id"));
		addResult.put("mid_edit",pdsession.get("id"));
		addResult.put("icbc_id",icbc_id);
		addResult.put("type_id",type_id);
		addResult.put("type_status",type_status);
		addResult.put("result_msg",result_msg);
		addResult.put("result_value",addResult.toString());
		int b = loanOverdueService.addOperationResult(addResult);//��Ӽ�¼
		String reuslt = "failure";
		if(b>0){
			reuslt = "successful!";
		}
		//�ϳ��޸�״̬   //δ�����Ѵ���
		if(type_id==3 && type_status==31){  
			update_wcl_to_ycl(request,lolId,type_id,32); ////�޸��ϳ�״̬-δ����״̬�޸ĳ�������״̬
		}
		//�ϳ��޸�״̬   //ʧ�ܵ�����-δ������7-71��
		if(type_id==3 && type_status==34){  
			update_wcl_to_ycl(request,lolId,7,71); ////�޸��ϳ�״̬-ʧ�ܵ�����-δ������7-71��
		}
		//�����޸�״̬   //δ������7-71����������7-72��
		if(type_id==7 && type_status==71){  
			update_wcl_to_ycl(request,lolId,7,72); 
		}
		//�����޸�״̬   //δ���ϣ�4-41�������ϣ�4-42��
		if(type_id==4 && type_status==41){  
			update_wcl_to_ycl(request,lolId,4,42); 
		}
		//�����޸�״̬   //���ϣ�4-42�������ϣ�7-71��
		if(type_id==4 && type_status==42){  
			update_wcl_to_ycl(request,lolId,7,71); 
		}
		return reuslt;
	}
	
	
	//����ϳ��������¼
	@RequestMapping("/addCarYslResult.do")
	@ResponseBody
	public String addCarYslResult(
			int type_id,
			int type_status,
			String result_msg,
			int icbc_id,
			int lolId,
			int coolStatus,  //�ϳ���������״̬
			String coolTime, //�ϳ�ʱ��
			String coolAddress, //�ϳ���ַ
			String coolVideo, //�ϳ���Ƶ
			HttpServletRequest request) throws IllegalStateException, IOException{
//		System.err.println(type_id+"-999999999999999");
//		System.err.println(type_status+"-999999999999999");
//		System.err.println(result_msg+"-999999999999999");
//		System.err.println(icbc_id+"-999999999999999");
//		System.err.println(coolTime+"-999999999999999");
//		System.err.println(coolAddress+"-999999999999999");
//		System.err.println(coolStatus+"-999999999999999");
//		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("coolVideo");
		/*CommonsMultipartFile file = (CommonsMultipartFile) coolVideo;
		String relatDir1 = new SimpleDateFormat("yyyy/MM/dd/").format(new Date());
		// �ļ��в������򴴽�
		File fdir = new File("/KCDIMG/assess/upload/" + relatDir1);
		if (!fdir.exists()) {
			fdir.mkdirs();
		}
		String oriName = file.getOriginalFilename();
		File tempFile = new File(fdir.getPath() + "/" + oriName);
		file.transferTo(tempFile);*/
		//��ȡ��ǰ��������Ϣ
		PageData pdsession= (PageData)request.getSession().getAttribute("pd");
		PageData addResult=new PageData();
		addResult.put("qryid",lolId);
		addResult.put("mid_add",pdsession.get("id"));
		addResult.put("mid_edit",pdsession.get("id"));
		addResult.put("icbc_id",icbc_id);
		addResult.put("type_id",type_id);
		addResult.put("type_status",type_status);
		addResult.put("result_msg",result_msg);
		addResult.put("remark", coolVideo);
		//�ϳ����
		addResult.put("coolStatus", coolStatus);
		addResult.put("coolTime", coolTime);
		addResult.put("coolAddress", coolAddress);
		addResult.put("coolVideo", coolVideo);
		addResult.put("result_value",addResult.toString());
		int b = loanOverdueService.addOperationResult(addResult);//��Ӽ�¼
		String reuslt = "failure";
		if(b>0){
			reuslt = "successful!";
		}
		//�ϳ��������ڴ�״̬ʱ,�޸��ϳ�״̬Ϊ�ϳ����(33)��ʧ��(34)
		update_wcl_to_ycl(request,lolId,type_id,coolStatus);
		return reuslt;
	}
	
	//�ϳ�����ύ��¼
	@RequestMapping("/addCarWcResult.do")
	@ResponseBody
	public String addCarWcResult(
			int type_id,
			int type_status,
			String result_msg,
			int icbc_id,
			int lolId,
			int coolStatus,
			HttpServletRequest request){
		//��ȡ��ǰ��������Ϣ
		PageData pdsession= (PageData)request.getSession().getAttribute("pd");
		PageData addResult=new PageData();
		addResult.put("qryid",lolId);
		addResult.put("mid_add",pdsession.get("id"));
		addResult.put("mid_edit",pdsession.get("id"));
		addResult.put("icbc_id",icbc_id);
		addResult.put("type_id",type_id);
		addResult.put("type_status",type_status);
		addResult.put("result_msg",result_msg);
		//��Ӽ�¼
		addResult.put("coolStatus",coolStatus);
		addResult.put("result_value",addResult.toString());
		int b = loanOverdueService.addOperationResult(addResult);//��Ӽ�¼
		String reuslt = "failure";
		if(b>0){
			reuslt = "successful!";
		}
//		int UpTypeId = (type_status==51?5:6);
		int UpTypeId = 0;
		switch (coolStatus) { //ǰ̨ҳ��ѡ����ϳ����-���ý��
		case 51: //����-δ����
			UpTypeId=5;
			break;
		case 61: //��������
		case 62: //��ǰ����
		case 63: //����-ǿ�ƽ���
		case 64: //�������
			UpTypeId=6;
			break;
		case 71: 
			UpTypeId=7;
			break;
		case 41: 
			UpTypeId=4;
			break;
		case 53:
			UpTypeId=5;
			break;
		default:
			break;
		}
		System.err.println(UpTypeId+"---UpTypeId-999999999");
		update_wcl_to_ycl(request,lolId,UpTypeId,coolStatus); //�޸��ϳ�״̬-�ϳ�����޸ĳ�������5-51������ǿ�ƽ���״̬��6-63�� ���ɲο�com.controller.erp_icbc.loanAfter�� 
		return reuslt;
	}
	
	
	
	public void update_wcl_to_ycl(HttpServletRequest request,int id,int type_id,int type_status){
		PageData pdsession= (PageData)request.getSession().getAttribute("pd");//��ȡ��ǰ��������Ϣ
		PageData updateStatus = new PageData();
		updateStatus.put("id",id);
		updateStatus.put("type_id",type_id); // �ϳ�(type_id=3)
		updateStatus.put("type_status",type_status); // �����ϳ��Ѵ���32
		updateStatus.put("mid_edit",pdsession.get("id")); // �޸Ĳ�����
		System.err.println(pdsession.get("id")+"-----------");
		int a = loanOverdueService.updateOverdueStatus(updateStatus); // a == 1,�޸ĳɹ�
	}
}
