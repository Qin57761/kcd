package com.controller.icbc.jsp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.service1.car.icbc_carsService;
import com.service1.kjs_icbc.icbc_dkService;
import com.service1.kjs_icbc.icbc_mqService;
import com.service1.kjs_icbc.newicbcService;
import com.service1.kjs_icbc.newicbc_kkService;

//ɾ������
@Controller
public class delController {
   
	 @Autowired
	 private newicbc_kkService newicbc_kkService;//����
	 
	 @Autowired
	 private newicbcService newicbcService;//������ ����
	 
	 @Autowired
	 private icbc_mqService  icbc_mqService;//��ǩ
	 
	 @Autowired
	 private icbc_dkService  icbc_dkService;//����
	 
	 @Autowired
	 private  icbc_carsService icbc_carsService;//����
	
	@RequestMapping(value="/del_icbc.do",produces="text/html;charset=UTF-8")	
	@ResponseBody
	public void del_icbc(int icbc_id){
		//ɾ����������
		newicbcService.del_icbc(icbc_id);
		//ɾ��������Ϣ
		newicbc_kkService.del_icbc_kk(icbc_id);
		//ɾ����ǩ��Ϣ
		icbc_mqService.del_icbc_mq(icbc_id);
		//ɾ��������Ϣ
		icbc_dkService.del_icbc_dk(icbc_id);
		//ɾ��������Ϣ
		icbc_carsService.del_icbc_cars(icbc_id);

	}
	
}
