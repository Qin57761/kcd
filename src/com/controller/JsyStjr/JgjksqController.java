package com.controller.JsyStjr;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.model1.mgcert;
import com.model1.ylqy;
import com.service1.duoying.bankService;
import com.service1.duoying.carmodelService;
import com.service1.duoying.mgcertService;
import com.service1.duoying.ylqyService;
import com.service1.jsy.jsyylqyService;
import com.util.jsy.BysfFind;

@Controller
public class JgjksqController {

	@Autowired
	private mgcertService mgcertservice;
	
	@Autowired
	private bankService bankservice;
	
	@Autowired
	private jsyylqyService jsyylqyservice;
	
	@Autowired
	private carmodelService carmodelservice;
	
//	������	    apply_amount	String	����	���������
//	�������ID	    apply_id	String	����	�̼��Զ���������Ψһ��ʶ
//	��Ʒ����	    product_name	String	����	�������Ĳ�Ʒ����
//	�������ޣ��죩	apply_limit_d	String	��ѡ	���������ޣ��£�Ϊ��ʱ��������ֵ
//	�������ޣ��£�	apply_limit_m	String	��ѡ	���������ޣ��죩Ϊ��ʱ��������ֵ
//	ʡ��	        province	String	����	���������ʡ������
//	���������	    debtor_type	String	����	��������ͣ�01�������û���02����ҵ�û�
//	���������	    debtor_name	String	��ѡ	�����������Ϊ01ʱ������
//	������ֻ���	debtor_tel	String	��ѡ	�����������Ϊ01ʱ������
//	��������֤��	debtor_idcard	String	��ѡ	�����������Ϊ01ʱ������
//	������Լ���	pros	String����	��ѡ	������Լ��ϣ������ڵ�key���ݲ�Ʒ����
//	������Ϣ����	credit_info	String����	��ѡ	�����������Ϣ���ϣ������ڵ�key���ݲ�Ʒ����
//	�������ϼ���	files	String����	��ѡ	����˸������ϼ��ϣ������ڵ�key���ݲ�Ʒ����
//	������Ϣ����	org_loan_info	String����	��ѡ	����˴�����Ϣ���ϣ������ڵ�key���ݲ�Ʒ����
//	��ͬ��Ϣ����	contracts	String����	��ѡ	��ͬ��Ϣ���ϣ������ڵ�key���ݲ�Ʒ����

	@RequestMapping(value="/jgjk.do",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String jgjk(){
         List<mgcert> ml=mgcertservice.csmgcert();
         if(ml!=null){
        	 for(mgcert m : ml){
        		    JSONObject  jb=new JSONObject();		
        			jb.put("apply_amount",m.getC_mgprice());//���������
        			jb.put("apply_id",m.getGems_code());//�̼��Զ���������Ψһ��ʶ	
        			jb.put("product_name","");//�������Ĳ�Ʒ����    Ѻ����Ѻ֤��Ѻ�³�
        			jb.put("apply_limit_d", "");//���������ޣ��£�Ϊ��ʱ��������ֵ
        			jb.put("apply_limit_m",m.getC_mgdays());//���������ޣ��죩Ϊ��ʱ��������ֵ
        			if(m.getC_cardno()!=null&&!m.getC_cardno().equals("")){
        				String szsf=BysfFind.getProvinceByIdCard(m.getC_cardno());//����ʡ��
            			jb.put("province",szsf);//���������ʡ������				
        			}
        			
        			jb.put("debtor_type", "01");//��������ͣ�01�������û���02����ҵ�û�
        			jb.put("debtor_name",m.getC_name());//�����������Ϊ01ʱ������
        		    ylqy y= jsyylqyservice.findylqybycardid(m.getC_cardno());
        		    if(y!=null&&!y.equals("")){
        		    	jb.put("debtor_tel",y.getTEL());//�����������Ϊ01ʱ������	
        		    }
        			jb.put("debtor_idcard",m.getC_cardno());//�����������Ϊ01ʱ������
        			
        		     JSONObject  pros=new JSONObject();
        		     JSONObject  credit_info=new JSONObject();
        		     JSONObject  files=new JSONObject();
        		     JSONObject  org_loan_info=new JSONObject();
        		     JSONObject  contracts=new JSONObject();
        		     pros.put("","");//����˼���
        		     pros.put("","");//������Ա�
        		     pros.put("","");//�����ѧ��
        		     pros.put("","");//���������ʡ
        		     pros.put("","");//������ŵ�����
        		     pros.put("","");//����˳�פ/������/ֱϽ�м���
        		     pros.put("","");//����˳�פ��ַ
        		     pros.put("","");//����������
        		     pros.put("","");//��������ϵ��ʽ
        		     pros.put("","");//�����˹�ϵ
        		     pros.put("","");//������λ����
        		     pros.put("","");//��ż���֤
        		     pros.put("","");//��ż��ϵ��ʽ
        		     pros.put("","");//�����������
        		     pros.put("","");//���������۸�
        		     pros.put("","");//��Ʒ����
        		     pros.put("","");//Ʒ�Ƴ���
        		     pros.put("","");//��������
        		     pros.put("","");//VIN��
        		     pros.put("","");//��������
        		     pros.put("","");//��ʽ
        			 jb.put("pros",pros);//������Լ���
        			 jb.put("credit_info",credit_info);//������Լ��ϣ������ڵ�key���ݲ�Ʒ����
        			 jb.put("files",files);//������Լ��ϣ������ڵ�key���ݲ�Ʒ����
        			 jb.put("org_loan_info",org_loan_info);//����˴�����Ϣ���ϣ������ڵ�key���ݲ�Ʒ����
        			 jb.put("contracts",contracts);//��ͬ��Ϣ���ϣ������ڵ�key���ݲ�Ʒ���� 
        	         
        	 
        	 
        	 
        	 }
         }
		
		
		
		
		
		
		
		
		
		
		
		return null;
		
		
		
		
		
		
		
	}
	
	
	
}
