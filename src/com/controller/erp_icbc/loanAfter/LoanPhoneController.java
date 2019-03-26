package com.controller.erp_icbc.loanAfter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
public class LoanPhoneController {
	@Autowired
	private LoanOverdueService loanOverdueService;
	@Autowired
	private ClientPaymentService clientPaymentService;
	
	//��ѯ�ͻ��������
	@RequestMapping("/selectPhoneList.do")
	public String select(
			String qn,
			String cn,
			String type,
			String dn,	
			int pagesize,
			int pagenow,
			String param,
			int type_id,
			int type_status,
			HttpServletRequest request){
		//��ȡ��ǰ��������Ϣ
		PageData pdsession= (PageData)request.getSession().getAttribute("pd");
		List<PageData> newpdList=new ArrayList<>();
		PageData pd=new PageData();
		pd.put("param",param);
		System.err.println(pdsession.get("icbc_erp_fsid")+"--99999999");
		pd.put("gems_fs_id",pdsession.get("icbc_erp_fsid"));
		pd.put("type_id",type_id); // ��ѯ���������  1���ڣ�2��ߣ�3�ϳ���4���ϣ�5������6����
		pd.put("type_status",type_status); // ��ѯ����������µ�С״̬  �����src/com/controller/erp_icbc/loanAfter/AddResult.java
		List<PageData> pdList=loanOverdueService.selectPhoneList(pd);
		newpdList = limitutil.fy(pdList,pagesize,pagenow);
		int q=pdList.size()%pagesize;
		int totalpage=0;//��ҳ��
		if(q==0){
			totalpage=pdList.size()/pagesize;	    		
		}else{
			totalpage=pdList.size()/pagesize+1;
		} 
		
		request.setAttribute("dn", dn);
		request.setAttribute("cn", cn);
		request.setAttribute("qn", qn);
		request.setAttribute("type", type);	
		request.setAttribute("totalpage",totalpage);
		request.setAttribute("totalsize",pdList.size());
		request.setAttribute("pagesize",pagesize);
		request.setAttribute("pagenow",pagenow);
		request.setAttribute("newpdList", newpdList);
		return "kjs_icbc/index";
	}
	
	//�����߿ͻ���������
	@RequestMapping("/selectPhoneForm.do")
	public String selectPhoneForm(
			String qn,
			String cn,
			String type,
			String dn,	
			String param,
			int id,
			HttpServletRequest request){
		//��ȡ��ǰ��������Ϣ
		PageData pdsession= (PageData)request.getSession().getAttribute("pd");
		List<PageData> newpdList=new ArrayList<>();
		PageData pd=new PageData();
		pd.put("id",id);
		//��ѯ �ͻ���Ϣ+������Ϣ+�����
		PageData CCL = loanOverdueService.selectPhoneClientCarLoanInfo(pd);
		//��ѯ����ƻ�
		List<PageData> paySchedule = clientPaymentService.selectPaySchedule(CCL.get("icbc_id").toString());
		//��ѯ������¼
		List<PageData> results = loanOverdueService.selectResults(pd);
		//��ѯ������������  ������Ӳ�����¼ʱʹ�� type_id��type_status����Ϣ
		PageData pdOne = loanOverdueService.selectOverdueOne(pd);
		request.setAttribute("dn", dn);
		request.setAttribute("cn", cn);
		request.setAttribute("qn", qn);
		request.setAttribute("type",type);	
		request.setAttribute("CCL",CCL);
		request.setAttribute("paySchedule",paySchedule);
		request.setAttribute("results",results);
		request.setAttribute("pdOne",pdOne);
		return "kjs_icbc/index";
	}
	
	
	//�����ϳ�������������
	@RequestMapping("/updatePhoneStatusToCarOrLitigation.do")
	@ResponseBody
	public String updateOverdueStatus(
			String result_msg,
			int type_id,
			int type_status,
			int icbc_id,
			int lolId,
			HttpServletRequest request){
		PageData pdsession= (PageData)request.getSession().getAttribute("pd");//��ȡ��ǰ��������Ϣ
		//�޸Ŀͻ�����״̬-�ֶ����������
		PageData updateStatus = new PageData();
		updateStatus.put("id",lolId);
		updateStatus.put("type_id",type_id); // �����ϳ�
		updateStatus.put("type_status",type_status); // �����ϳ�δ����
		updateStatus.put("mid_edit",pdsession.get("id")); // �޸Ĳ�����
		System.err.println(pdsession.get("id")+"-----------");
		int a = loanOverdueService.updateOverdueStatus(updateStatus); // a == 1,�޸ĳɹ�
		String reuslt = "failure";
		if(a>0){
			reuslt = "successful!";
		}
		//��Ӳ�����¼ start
		PageData addResult=new PageData();
		addResult.put("qryid",lolId);
		addResult.put("mid_add",pdsession.get("id"));
		addResult.put("mid_edit",pdsession.get("id"));
		addResult.put("icbc_id",icbc_id);
		addResult.put("type_id",type_id);
		addResult.put("type_status",type_status);
		addResult.put("result_msg",result_msg);
		addResult.put("remark",result_msg);
		int b = loanOverdueService.addOperationResult(addResult);//��Ӽ�¼
		//��Ӳ�����¼ end
		return reuslt;
	}
}
