package com.controller.erp_icbc.loanAfter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.service1.loan.LoanOverdueService;
import com.util.creditutil;
/**
 * �Զ�����
 * 
 * ����ÿ���賿00:00�����б�ͻ�������������һ��
 * 
 * @author ��ʮ����
 * 2019-3-27
 */
@Component
public class LoanAutomaticTaskController {
	private static Logger log = LogManager.getLogger(LoanAutomaticTaskController.class.getName());
	@Autowired
	private LoanOverdueService loanOverdueService;
	 
//	@Scheduled(cron="0/5 * *  * * ? ")   //ÿ5��ִ��һ��   
	@Scheduled(cron = "0 0 1 * * ?") //ÿ���賿1��ִ��
	public void tasktest(){	
		int counts = loanOverdueService.updateOverdueDay();
		System.out.println("�Զ�ִ��:"+creditutil.time()+"---"+counts);
		log.info("�Զ�ִ��:"+creditutil.time()+"---"+counts);
	};
}
