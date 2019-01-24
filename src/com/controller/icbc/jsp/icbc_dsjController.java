package com.controller.icbc.jsp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.connector.Request;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hp.hpl.sparta.xpath.ThisNodeTest;
import com.http.RiskServicePreloan;
import com.util.jsonutil;

@Controller
public class icbc_dsjController {
	
	
	 RiskServicePreloan service = new RiskServicePreloan();
	
	@RequestMapping(value="/dsj_report_id.do",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String dsj_report_id(String name,
			String phone,
			String cardno
			){
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("name",name); // ���� ���� 352123197501281314
    //Ƥ���� 320304198404070412  ER2017122917192888A1963A
    params.put("id_number",cardno); // ���֤��
    params.put("mobile",phone); // �ֻ���    
    JSONObject result=service.apply(params);			
    return result.toJSONString();		
	}
	
	
	@RequestMapping(value="/dsj_result.do",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String dsj_result(
			String report_id
			){
	JSONObject result = service.query(report_id);			
    return result.toJSONString();		
	}
		
	@RequestMapping(value="/dsj_result_jsp.do",produces = "text/html;charset=UTF-8")
	public String dsj_result_jsp(
			String report_id,
			HttpServletRequest request
			){
	JSONObject result=service.query(report_id);
	//�ӿڵ����Ƿ�ɹ�
	request.setAttribute("success",result.get("success"));
	//�������
	request.setAttribute("reason_code",result.get("reason_code"));
	//��������
	request.setAttribute("reason_desc",result.get("reason_desc"));
	//���շ���
	request.setAttribute("final_score",result.get("final_score"));
	//���ս��
	request.setAttribute("final_decision",result.get("final_decision"));
	//�����ؽ���
	request.setAttribute("address_detect",(Map)result.get("address_detect"));
	
	if(result.get("risk_items")!=null&&!result.get("risk_items").equals("")){
		//ɨ������ķ�����
		request.setAttribute("risk_items",(List<Map>)result.get("risk_items"));
		
		List<Map> maps=(List<Map>)result.get("risk_items");
		//risk_items  ��������
		request.setAttribute("risk_items_count",maps.size());	
		System.out.println("��������:"+maps.size());
	}
	
	//credit_score  ���÷���
	request.setAttribute("credit_score",result.get("credit_score"));
	
	
	
	return "cskjs_wzb/dsjbg";		
	}
	

  
  
	
   
}
