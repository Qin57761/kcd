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
			HttpServletRequest request){
		//��ȡ��ǰ��������Ϣ
		PageData pdsession= (PageData)request.getSession().getAttribute("pd");
		List<PageData> newpdList=new ArrayList<>();
		PageData pd=new PageData();
		pd.put("param",param);
		System.err.println(pdsession.get("icbc_erp_fsid")+"--99999999");
		pd.put("gems_fs_id",pdsession.get("icbc_erp_fsid"));
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
		
		request.setAttribute("dn", dn);
		request.setAttribute("cn", cn);
		request.setAttribute("qn", qn);
		request.setAttribute("type", type);	
		request.setAttribute("CCL", CCL);
		return "kjs_icbc/index";
	}
	
}
