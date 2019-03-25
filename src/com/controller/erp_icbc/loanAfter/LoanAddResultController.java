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
	
	//��ӵ�߼�¼
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
		int b = loanOverdueService.addOperationResult(addResult);//��Ӽ�¼
		String reuslt = "�ύʧ��";
		if(b>0){
			reuslt = "�ύ�ɹ�!";
		}
		//�ϳ��޸�״̬   //δ�����Ѵ���
		if(type_id==3 && type_status==31){  
			update_wcl_to_ycl(request,lolId,"3","32"); ////�޸��ϳ�״̬-δ����״̬�޸ĳ�������״̬
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
//			String coolTime,
//			String coolAddress,
			String coolStatus,
//			@RequestParam("coolVideo") MultipartFile coolVideo,
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
		//addResult.put("remark", "upload/" + relatDir1 + oriName);
		addResult.put("result_value",addResult.toString());
		int b = loanOverdueService.addOperationResult(addResult);//��Ӽ�¼
		String reuslt = "�ύʧ��";
		if(b>0){
			reuslt = "�ύ�ɹ�!";
		}
		//�ϳ��������ڴ�״̬ʱ,�޸��ϳ�״̬Ϊ�ϳ����(33)��ʧ��(34)
		update_wcl_to_ycl(request,lolId,"3",coolStatus);
		return reuslt;
	}
	
	
	public void update_wcl_to_ycl(HttpServletRequest request,int id,String type_id,String type_status){
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
