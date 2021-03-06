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
import com.service1.sxx.TrailerManagementService;
import com.service1.sxx.VehicleMortgageService;
import com.util.limitutil;

@Controller
@RequestMapping("/trailerManagementController")
public class TrailerManagementController {
	
	@Autowired
	private TrailerManagementService trailerManagementService;

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
		
		//获取登陆信息
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
		
		if("tcglnot".equals(type)){
			System.out.println("未处理");
			//获取车辆抵押专员审批后的原始数据 --待处理
			List<Map<String, Object>> list1 = trailerManagementService.ToBeProcessed(33);
			//循环原始数据
			for (int i = 0; i < list1.size(); i++) {
				//获取到每条数据中的result_1_value值	并把这个值转为map			
				Map<String, String> maps = (Map)JSON.parse( (String)list1.get(i).get("result_1_value") );
				//判断当前这条数据的result_1_value值中是否包含ds_status属性
				if(null != maps){
					if(null != maps.get("ds_status")){
						//判断当前这条数据的result_1_value值中的ds_status值是否为1  如果为1就代表专员审核通过，然后就获取到当前这条数据的icbcid值存到list中
						if("1".equals(maps.get("ds_status"))){
							icbcid.add( (int)list1.get(i).get("icbc_id") );
						}
					}
				}
			}
			//根据筛选过的icbcid值获取最终页面上需要的数据
			list2 = trailerManagementService.FindDataByIcbcid(icbcid, param);
			newpdList = limitutil.fy(list2, pagesize, pagenow);
			
	
		} else {
			System.out.println("已处理");
			//获取车辆抵押专员审批后的原始数据 --已处理
			List<Map<String, Object>> list1 = trailerManagementService.ToBeProcessed(76);
			for (int i = 0; i < list1.size(); i++) {
				Map<String, String> maps = (Map)JSON.parse( (String)list1.get(i).get("result_1_value") );
				if(null != maps.get("76_cyqk")){
					if("通过".equals(maps.get("76_cyqk"))){
						icbcid.add( (int)list1.get(i).get("icbc_id") );
					}
				}
			}
			
			list2 = trailerManagementService.FindDataByIcbcid(icbcid, param);
			newpdList = limitutil.fy(list2, pagesize, pagenow);
			
		}
		
		int q = list2.size()%pagesize;
		int totalpage = 0;//总页数
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
