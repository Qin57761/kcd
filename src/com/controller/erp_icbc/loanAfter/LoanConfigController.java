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
 * �ͻ���������
 * 
 * @author ��ʮ����
 * 2019-3-27
 */
@Controller
@RequestMapping("/loan")
public class LoanConfigController {
	@Autowired
	private LoanOverdueService loanOverdueService;
	
	//��ѯ�ͻ���������
	@RequestMapping("/loanConfig.do")
	@ResponseBody
	public void loanConfig(
			String overdue_one,
			String overdue_two,
			String overdue_three,
			String overdue_to_phone,	
			String overdue_money,
			HttpServletRequest request){
		//��ȡ��ǰ��������Ϣ
		PageData pdsession= (PageData)request.getSession().getAttribute("pd");
		pdsession.get("icbc_erp_fsid");
		PageData pd=new PageData();
		
		
	}
	
}
