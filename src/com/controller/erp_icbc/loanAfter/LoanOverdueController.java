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
 * �ͻ����ڿ�����
 * 
 * @author ��ʮ����
 * 2019-3-21
 */
@Controller
@RequestMapping("/loan")
public class LoanOverdueController {
	@Autowired
	private LoanOverdueService loanOverdueService;
	
	//��ѯ�ͻ���������
	@RequestMapping("/selectOverdue.do")
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
		List<PageData> pdList=loanOverdueService.selectOverdueList(pd);
		newpdList = limitutil.fy(pdList,pagesize, pagenow);
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
	
	//�޸Ŀͻ�����״̬-�ֶ����������
	@RequestMapping("/updateOverdueStatus.do")
	public String updateOverdueStatus(
			String qn,
			String cn,
			String type,
			String dn,	
			int pagesize,
			int pagenow,
			int id,
			HttpServletRequest request){
		//�޸Ŀͻ�����״̬-�ֶ����������
		PageData updateStatus = new PageData();
		updateStatus.put("id",id);
		updateStatus.put("type_id","2"); // ������(type_id=2)
		int a = loanOverdueService.updateOverdueStatus(updateStatus); // a == 1,�޸ĳɹ�
		//��Ӳ�����¼ start
		PageData pdOne = loanOverdueService.selectOverdueOne(updateStatus);//��loan_overdue_list���������� ��ȡ��Ҫ�޸�ĳһ�ͻ�
		PageData pdsession= (PageData)request.getSession().getAttribute("pd");//��ȡ��ǰ��������Ϣ
		PageData addResult =  AddResult.addResult(pdsession,pdOne);//���������Ϣ
		int b = loanOverdueService.addOperationResult(addResult);//��Ӽ�¼
		//��Ӳ�����¼ end
		request.setAttribute("dn", dn);
		request.setAttribute("cn", cn);
		request.setAttribute("qn", qn);
		request.setAttribute("type", type);	
		request.setAttribute("pagesize",pagesize);
		request.setAttribute("pagenow",pagenow);
		return "redirect:/loan/selectOverdue.do?type=khyqmd&dn=loan_overdue&qn=list&pagesize="+pagesize+"&pagenow="+pagenow;
		//return select(qn, cn, type, dn, pagesize, pagenow, "", request);
	}
}
