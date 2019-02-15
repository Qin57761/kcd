package com.util.sxx.financialExcel.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.util.jsonutil;

@Controller
@RequestMapping("/automobileFinance")
public class AutomobileFinance {

	/**
	 * �������ڴ�ǰ��������ӿ�
	 * @param name	����
	 * @param idCardNo	���֤��
	 * @param mobileNo	�ֻ���
	 * @return
	 */
	@RequestMapping("/pre_loanAssessmentReport")
	public String Pre_loanAssessmentReport(String name, String idCardNo, String mobileNo, HttpServletRequest req){
		
		System.out.println(name+"**********"+idCardNo+"***********"+mobileNo);
		//����������������������
		String status = "�ɹ�";
		//���������������������ַ
		String autoFinancingUrl = "http://tidata.taifinance.cn:8080/api/credit/auto-financial-preloan-eval-report?name=" + name + "&idCardNo=" + idCardNo + "&mobileNo=" + mobileNo;
	    //����������������ӿڷ���ֵ
		String result = "";
	      try {
	        HttpGet request = new HttpGet(autoFinancingUrl);
	        request.setHeader("tfapi-key", "7yScQE0SHF5TxO65lq1BoOyNeXQfv3");
	        CloseableHttpClient client = HttpClientBuilder.create().build();
	        HttpResponse response = client.execute(request);

	        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	          result = EntityUtils.toString(response.getEntity(), "utf-8");
	        }
	        
	      } catch (Exception e) {
	    	status = "�����������������ȡʧ��";
	        e.printStackTrace();
	      }
	      
	    //��ǰ����������
		String status1 = "�ɹ�";
		//��ǰ���������ַ
		String loanAssessment = "http://tidata.taifinance.cn:8080/api/preloan/submit";
		//��ǰ�����ӿڷ���ֵ
		String result1 = "";
		try {
			CloseableHttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(loanAssessment);
			post.setHeader("tfapi-key", "7yScQE0SHF5TxO65lq1BoOyNeXQfv3");
			//��װpost����Ĳ���
			List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
			
            BasicNameValuePair pair = new BasicNameValuePair("name", name);
            BasicNameValuePair pair1 = new BasicNameValuePair("idCardNo", idCardNo);
            BasicNameValuePair pair2 = new BasicNameValuePair("mobileNo", mobileNo);
            parameters.add(pair);
            parameters.add(pair1);
            parameters.add(pair2);
                
            UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(parameters, "utf-8");
            post.setEntity(encodedFormEntity);
			
	        HttpResponse response = client.execute(post);

	        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	          result1 = EntityUtils.toString(response.getEntity(), "utf-8");
	        }
	        
	      } catch (Exception e) {
	    	status1 = "��ǰ������ȡʧ��";
	        e.printStackTrace();
	      }
		
		System.out.println(result1);
		
		//��ǰ�������ݴ���
		Map<String, Object> map1 = (Map) JSON.parse(result1);
		Map<String, Object> resultData1 = new HashMap<>();
		try {
			resultData1 = (Map) JSON.parse( map1.get("resultData").toString() );	//resultData
		} catch (Exception e) {
			status1 = "��ѯ��������";
		}
		try {
			
			Map<String, Object> INFOANALYSIS = (Map) JSON.parse( resultData1.get("INFOANALYSIS").toString() ); //resultData.INFOANALYSIS
				Map<String, Object> address_detect = (Map) JSON.parse( INFOANALYSIS.get("address_detect").toString() ); //resultData.INFOANALYSIS.address_detect
				INFOANALYSIS.put("address_detect", address_detect);
				Map<String, Object> geoip_info = (Map) JSON.parse( INFOANALYSIS.get("geoip_info").toString() ); //resultData.INFOANALYSIS.geoip_info
				INFOANALYSIS.put("geoip_info", geoip_info);
				Map<String, Object> device_info = (Map) JSON.parse( INFOANALYSIS.get("device_info").toString() ); //resultData.INFOANALYSIS.device_info
				INFOANALYSIS.put("device_info", device_info);
				Map<String, Object> geotrueip_info = (Map) JSON.parse( INFOANALYSIS.get("geotrueip_info").toString() ); //resultData.INFOANALYSIS.geotrueip_info
				INFOANALYSIS.put("geotrueip_info", geotrueip_info);
				resultData1.put("INFOANALYSIS", INFOANALYSIS);
			
				Map<String, Object> ANTIFRAUD = (Map) JSON.parse( resultData1.get("ANTIFRAUD").toString() ); //resultData.ANTIFRAUD
					List<Map<String, Object>> risk_items = jsonutil.toList(ANTIFRAUD.get("risk_items")); 
					for (int i = 0; i < risk_items.size(); i++) {
						
						List<Map<String, Object>> risk_detail = jsonutil.toList( risk_items.get(i).get("risk_detail") );
						
						if(risk_detail.size()>0){
							if(risk_detail.get(0).get("type").equals("grey_list")){	//�ж�risk_detail�е�typeΪgrey_list
								List<Map<String, Object>> grey_list_details = (List<Map<String, Object>>) risk_detail.get(0).get("grey_list_details");
								risk_detail.get(0).put("grey_list_details", grey_list_details);
								risk_items.get(0).put("risk_detail", risk_detail);
							}
						
							if(risk_detail.get(0).get("type").equals("frequency_detail")){
								List<Map<String, Object>> frequency_detail_list = (List<Map<String, Object>>) risk_detail.get(0).get("frequency_detail_list");
								for (int j = 0; j < frequency_detail_list.size(); j++) {
									if(frequency_detail_list.get(j).containsKey("data")){
										List<String> data = (List<String>) frequency_detail_list.get(j).get("data");
										frequency_detail_list.get(j).put("data", data);
									}
								}
								risk_detail.get(0).put("frequency_detail_list", frequency_detail_list);
								risk_items.get(0).put("risk_detail", risk_detail);
							}
						}
						
					}
					ANTIFRAUD.put("risk_items", risk_items);
					resultData1.put("ANTIFRAUD", ANTIFRAUD);
					map1.put("resultData", resultData1);
					
		} catch (Exception e) {
			status1 = "���ݴ���ʧ��";
	        e.printStackTrace();
		}
	    
		//�������������������ݴ���
	    Map<String, Object> map = (Map) JSON.parse(result);
	    Map<String, Object> resultData = new HashMap<>();
      	try {
      		resultData = (Map) JSON.parse( map.get("resultData").toString() );	//resultData
		} catch (Exception e) {
			status = "��ѯ��������";
		}
		    
	    try {
	    	Map<String, Object> idVerification = (Map) JSON.parse( resultData.get("idVerification").toString() ); //resultData.idVerification
		    resultData.put("idVerification", idVerification);
		    
		    Map<String, Object> carrier = (Map) JSON.parse( resultData.get("carrier").toString() );	//resultData.carrier
			    Map<String, Object> verification = (Map) JSON.parse( carrier.get("verification").toString() ); //carrier.verification
			    carrier.put("verification", verification);
			    Map<String, Object> serviceDuration = (Map) JSON.parse( carrier.get("serviceDuration").toString() ); //carrier.serviceDuration
			    carrier.put("serviceDuration", serviceDuration);
			    Map<String, Object> serviceStatus = (Map) JSON.parse( carrier.get("serviceStatus").toString() ); //carrier.serviceStatus
			    carrier.put("serviceStatus", serviceStatus);
		    resultData.put("carrier", carrier);
		    
		    Map<String, Object> badJudicialRecord = (Map) JSON.parse( resultData.get("badJudicialRecord").toString() );	//resultData.badJudicialRecord
		    	//ѭ��badJudicialRecord�µ�����
		    	String[] badJudicialRecord_s = {"dishonest", "executed", "lawCase", "netLoanOverdue", "callLoan", "courtNotice", "courtAnnouncement", "lawCaseProcess"};
		    	for (int i = 0; i < badJudicialRecord.size(); i++) {
		    		Map<String, Object> badJudicialRecord_i = (Map) JSON.parse( badJudicialRecord.get(badJudicialRecord_s[i]).toString() );
			    	List<Map<String, Object>> items = jsonutil.toList(badJudicialRecord_i.get("items")); 
			    	badJudicialRecord_i.put("items", items);
			    	badJudicialRecord.put(badJudicialRecord_s[i], badJudicialRecord_i);
				}
		    resultData.put("badJudicialRecord", badJudicialRecord);	
		    	
		    Map<String, Object> riskList = (Map) JSON.parse( resultData.get("riskList").toString() );	//resultData.riskList
		    	List<Map<String, Object>> items = jsonutil.toList(riskList.get("items")); 
		    	riskList.put("items", items);
		    resultData.put("riskList", riskList);	
		    
		    Map<String, Object> loan = (Map) JSON.parse( resultData.get("loan").toString() );	//resultData.riskList 	
		    	//ѭ������loan�µ�����
		    	String[] loan_s = {"registration", "application", "approval", "rejection", "overdue"};
		    	for (int i = 0; i < loan.size(); i++) {
		    		Map<String, Object> loan_i = (Map) JSON.parse( loan.get(loan_s[i]).toString() );
		    		loan.put(loan_s[i], loan_i);
				}
		    resultData.put("loan", loan);
		    	
		    map.put("resultData", resultData);
		} catch (Exception e) {
			status = "���ݴ���ʧ��";
	        e.printStackTrace();
		}
	    

		req.setAttribute("data", map);
		req.setAttribute("data1", map1);
		req.setAttribute("status", status);
		req.setAttribute("status1", status1);
		    
		return "kjs_icbc/content/AppraisalReport";
	}
	
}
