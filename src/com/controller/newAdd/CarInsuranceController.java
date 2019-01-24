package com.controller.newAdd;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.http.newAdd.CarInsuranceHttp;
import com.model.jbapi.jbzxapiuser;
import com.model.newAdd.queryBx;
import com.model1.carbrand;
import com.service.newAdd.queryBxService;
import com.service.zx.jbzxapiuserService;
import com.service1.car.brandService;
import com.util.Page;
import com.util.creditutil;
import com.util.jsonutil;
import com.util.pagedate;
/**
 * �������ձ���
 * @author HZJ
 * 2018��4��19��
 * http://localhost:8080/kcd/CarInsurance.do?appKey=6602e77a-c286-a0b9-13a6-287f438b3336&VIN=LFV3A24G0D3030240&platenumber=��A632P6
 *  */
@Controller
public class CarInsuranceController {
	@Autowired
	private jbzxapiuserService jbzxapiuserservice;
	@Autowired
	private queryBxService queryBxService;
	@Autowired
	private brandService brandService;
	// ���� �ӿ�
	@RequestMapping(value="CarInsurance.do",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String CarInsurance(
		@RequestParam("appKey") String appKey,
		@RequestParam("VIN") String VIN,
		String platenumber,   		
		HttpServletRequest request) throws IOException{
		//�õ��û�����
		jbzxapiuser ja= jbzxapiuserservice.findapiuserbyappkey(appKey);
		String str = null;
		str= CarInsuranceHttp.CarInsuranceUri(VIN, platenumber,appKey);
		System.err.println(str+"------");
	    JSONObject jb=JSONObject.parseObject(str);
	    System.err.println(appKey);
		JSONObject reslult=new JSONObject(); //�Զ��巵����Ϣ �ǽӿڷ���
		if(ja!=null){//�������� 
			//�˻����
			BigDecimal balance=new  BigDecimal(ja.getApi_money());//�˻���� 
			BigDecimal zero=new  BigDecimal("0");//��
			BigDecimal remain=balance.subtract(new BigDecimal(35));//���� ��ȥά�ޱ���ʣ�µĽ��
			boolean apitype=true; // api���Ͳ�����1��ʱ����߽���ȥ35 >= 0 ��ʱ��Ϊtrue
			boolean deductionlogo=true;//�۷ѱ�ʶ
			if(ja.getApi_type()!=1){//�ж��û�������
				deductionlogo=false; //���۷�
			}else{ 
				//�۷�
				//�ж����
				if(remain.compareTo(zero)==-1){
					//��Ҳ��㹻
					apitype=false; 
				}
			}
			if(apitype){
				//��װ��
				int id = queryBxService.getNewId("assess_querybx","kcdapitest");  // ���ȵõ�����ID
				queryBx bx=new queryBx();
				bx.setAppkey(appKey);
				bx.setDt_add(creditutil.time());
				bx.setDt_edit(creditutil.time());
				bx.setC_carvin(VIN);
				String sqlGems_code = String.format("BXkcd%07d",id);
				bx.setGems_code(sqlGems_code);  // ����
				bx.setApi_result(str);  //   ̩��API��ѯ��������������Ϣ  
				bx.setApi_msg(jb.getString("resultMsg")); //   API������ʾ��Ϣ  
				bx.setApi_code(jb.getString("resultCode"));//  api���ش���  
				if(jb.getString("resultCode").equals("0")){ // resultCode 0 �������API�ɹ�  
					// resultCode 0 ���� API�ɹ��Ϸ���   -- ��������ɹ� resultCodeΪ0��ʱ��ſۿ�
					if(deductionlogo){
						//���·���
						ja.setApi_money(remain.toString());
						jbzxapiuserservice.upmoney(ja);
					}
					JSONObject resultData=jb.getJSONObject("resultData");
					JSONObject result=resultData.getJSONObject("result");
					JSONArray carClaimRecords=result.getJSONArray("carClaimRecords");
					List list=jsonutil.toList(carClaimRecords);					
					Map map=(Map) list.get(0);
					String car_name=map.get("vehicleModel").toString().substring(0, 2);
					carbrand carbrand=brandService.findbrandbyname(car_name);
					if(carbrand.getLogo()!=null&&!carbrand.getLogo().equals("")){
					jb.put("logo","http://a.kcway.net/"+carbrand.getLogo());	
					}
					// 3 �����ѯ���
					bx.setBc_status(3);   
//					reslult.put("resultCode","0");
//					reslult.put("resultMsg","��ѯ�ɹ�");
//			      	reslult.put("success",true);
			      	reslult.put("result",jb);  // ��ѯ�ɹ����ȫ�����ݸ��Է�
				}else{
					// 2 �������ڲ�ѯ
					bx.setBc_status(2); 
					reslult.put("resultCode",jb.getString("resultCode"));
					reslult.put("resultMsg",jb.getString("resultMsg"));
			      	reslult.put("success",false);
				}
				queryBxService.addBX(bx);
				return reslult.toString();
			}else{
				reslult.put("resultCode","302");
		      	reslult.put("resultMsg","�˻�����,���ֵ");
		      	reslult.put("success",false);
		      	return  reslult.toString();
			}
		}else{
	      	reslult.put("resultCode","303");
	      	reslult.put("resultMsg","�û����Ʋ�����");
	      	reslult.put("success",false);
	      	return  reslult.toString();
		}
	}
	
	// ���� չʾ����
	@RequestMapping(value="showAllBX.do",produces = "text/html;charset=UTF-8")
	public String CarInsurance(HttpServletRequest request,HttpServletResponse response){
		//��ҳ��ѯ
		String size=request.getParameter("size");
	    String pagenow=request.getParameter("pagenow");
	    int s;
	    if(size!=null){
	    	s=Integer.parseInt(size);
	    }else{
	    	s=10;
	    }	
	    int totalCount;
	    Page page;
  		//���ڱ����ѯ��������
	    List list = new ArrayList();
	    totalCount =queryBxService.BxCounts();
	    if (pagenow!=null) {				
			//System.out.println(0);
			page = new Page(totalCount, Integer.parseInt(pagenow),s);
			list = queryBxService.showBxAndKey(page.getStartPos(),page.getPageSize());	
			
		} else {
			//System.out.println(1);
			page = new Page(totalCount, 1,s);					
			list = queryBxService.showBxAndKey(page.getStartPos(),page.getPageSize());			   
			pagenow = "1";
		}				
		int q=totalCount%s;
    	int w=0;
    	if(q==0){
    		w=totalCount/s;
    	}else{
    		w=totalCount/s+1;
    	}    		
    	request.setAttribute("w",w);
    	request.setAttribute("pagenow",pagenow);
    	request.setAttribute("size",s);
		request.setAttribute("totalCount",totalCount);
		request.setAttribute("list", list);
		return "CarBx";
	}
}
