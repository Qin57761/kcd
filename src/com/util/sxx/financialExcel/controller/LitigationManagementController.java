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
import com.service1.sxx.LitigationManagementService;
import com.service1.sxx.TrailerManagementService;
import com.service1.sxx.VehicleMortgageService;
import com.util.limitutil;

@Controller
@RequestMapping("/litigationManagementController")
public class LitigationManagementController {
	
	@Autowired
	private LitigationManagementService litigationManagementService;

	@RequestMapping("/mortgageRecord")
	public String MortgageRecord(
			Integer pagesize,
			Integer pagenow,
			String dn,
			String qn,
			String cn,
			String type,
			String param,
			HttpServletRequest request
			){
		
		//��ȡ��½��Ϣ
		PageData pdLoginSession = (PageData)request.getSession().getAttribute("pd");
//		System.out.println(pdLoginSession.get("name"));
		List<Integer> icbcid = new ArrayList<>();
		
		if(null == pagesize){
			pagesize = 10;
		}
		if(null == pagenow){
			pagenow = 0;
		}
		
		List<PageData> list2 = new ArrayList<>();
		List<PageData> newpdList = new ArrayList<>();
		
		if("ssglnot".equals(type)){
			System.out.println("δ����");
			//��ȡ������ѺרԱ�������ԭʼ���� --������
			List<Map<String, Object>> list1 = litigationManagementService.ToBeProcessed(33);
			//ѭ��ԭʼ����
			for (int i = 0; i < list1.size(); i++) {
				//��ȡ��ÿ�������е�result_1_valueֵ	�������ֵתΪmap			
				Map<String, String> maps = (Map)JSON.parse( (String)list1.get(i).get("result_1_value") );
				//�жϵ�ǰ�������ݵ�result_1_valueֵ���Ƿ����ds_status����
				if(null != maps){
					if(null != maps.get("ds_status")){
						//�жϵ�ǰ�������ݵ�result_1_valueֵ�е�ds_statusֵ�Ƿ�Ϊ1  ���Ϊ1�ʹ���רԱ���ͨ����Ȼ��ͻ�ȡ����ǰ�������ݵ�icbcidֵ�浽list��
						if("1".equals(maps.get("ds_status"))){
							icbcid.add( (int)list1.get(i).get("icbc_id") );
						}
					}
				}
			}
			//����ɸѡ����icbcidֵ��ȡ����ҳ������Ҫ������
			list2 = litigationManagementService.FindDataByIcbcid(icbcid, param);
			newpdList = limitutil.fy(list2, pagesize, pagenow);
			
	
		} else {
			System.out.println("�Ѵ���");
			//��ȡ������ѺרԱ�������ԭʼ���� --�Ѵ���
			List<Map<String, Object>> list1 = litigationManagementService.ToBeProcessed(76);
			for (int i = 0; i < list1.size(); i++) {
				Map<String, String> maps = (Map)JSON.parse( (String)list1.get(i).get("result_1_value") );
				if(null != maps.get("76_cyqk")){
					if("ͨ��".equals(maps.get("76_cyqk"))){
						icbcid.add( (int)list1.get(i).get("icbc_id") );
					}
				}
			}
			
			list2 = litigationManagementService.FindDataByIcbcid(icbcid, param);
			newpdList = limitutil.fy(list2, pagesize, pagenow);
			
		}
		
		int q = list2.size()%pagesize;
		int totalpage = 0;//��ҳ��
		if(q == 0){
			totalpage = list2.size()/pagesize;	    		
		}else{
			totalpage = list2.size()/pagesize+1;
		} 
		
		request.setAttribute("untreated", newpdList);
		request.setAttribute("totalpage",totalpage);
		request.setAttribute("totalsize",list2.size());
		request.setAttribute("pagesize",pagesize);
		request.setAttribute("pagenow",pagenow);
		
		request.setAttribute("dn",dn);
		request.setAttribute("qn",qn);
		request.setAttribute("cn",cn);
		request.setAttribute("type",type);
		
		return "kjs_icbc/index";
	}
	
}
