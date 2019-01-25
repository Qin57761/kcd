package com.util.sxx.financialExcel.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.model1.icbc.erp.PageData;
import com.service1.sxx.VehicleMortgageService;

@Controller
@RequestMapping("/vehicleMortgageController")
public class VehicleMortgageController {
	
	@Autowired
	private VehicleMortgageService vehicleMortgageService;

	@RequestMapping("/mortgageRecord")
	public String MortgageRecord(
			Integer pagesize,
			Integer pagenow,
			String dn,
			String qn,
			String cn,
			String type,
			HttpServletRequest request
			){
		
		//��ȡ��½��Ϣ
		PageData pdLoginSession= (PageData)request.getSession().getAttribute("pd");
//		System.out.println(pdLoginSession.get("name"));
		List<Integer> icbcid = new ArrayList<>();
		if("not".equals(type)){
			System.out.println("δ����");
			//��ȡ������ѺרԱ�������ԭʼ���� --������
			List<Map<String, Object>> list1 = vehicleMortgageService.ToBeProcessed(33);
			//ѭ��ԭʼ����
			for (int i = 0; i < list1.size(); i++) {
				//��ȡ��ÿ�������е�result_1_valueֵ	�������ֵתΪmap			
				Map<String, String> maps = (Map)JSON.parse( (String)list1.get(i).get("result_1_value") );
				//�жϵ�ǰ�������ݵ�result_1_valueֵ���Ƿ����ds_status����
				if(maps!=null&&!maps.equals("")){
				if(null != maps.get("ds_status")){
					//�жϵ�ǰ�������ݵ�result_1_valueֵ�е�ds_statusֵ�Ƿ�Ϊ1  ���Ϊ1�ʹ���רԱ���ͨ����Ȼ��ͻ�ȡ����ǰ�������ݵ�icbcidֵ�浽list��
					if("1".equals(maps.get("ds_status"))){
						icbcid.add( (int)list1.get(i).get("icbc_id") );
					}
				}}
			}
			//����ɸѡ����icbcidֵ��ȡ����ҳ������Ҫ������
			List<Map<String, Object>> list2 = vehicleMortgageService.FindDataByIcbcid(icbcid);
			request.setAttribute("untreated", list2);  
			
		} else {
			System.out.println("�Ѵ���");
			//��ȡ������ѺרԱ�������ԭʼ���� --�Ѵ���
			List<Map<String, Object>> list1 = vehicleMortgageService.ToBeProcessed(76);
			for (int i = 0; i < list1.size(); i++) {
				Map<String, String> maps = (Map)JSON.parse( (String)list1.get(i).get("result_1_value") );
				if(null != maps.get("76_cyqk")){
					if("ͨ��".equals(maps.get("76_cyqk"))){
						icbcid.add( (int)list1.get(i).get("icbc_id") );
					}
				}
			}
			
			List<Map<String, Object>> list2 = vehicleMortgageService.FindDataByIcbcid(icbcid);
			request.setAttribute("untreated", list2); 
		}
		
		request.setAttribute("dn",dn);  
		request.setAttribute("qn",qn);
		request.setAttribute("cn",cn);
		request.setAttribute("type",type);
		
		return "kjs_icbc/index";
	}
	
}
