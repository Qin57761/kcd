package com.controller.CBSapi;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.chaboshi.util.CBS;
import com.model.CBS.CbsSuccessfulPurchaseQueryReport;
import com.model.jbapi.jbzxapiuser;
import com.service.CBS.CbsSuccessfulPurchaseQueryReportService;
import com.service.zx.jbzxapiuserService;
import com.util.DeductFeeAmountTool;
import com.util.Page;
import com.util.creditutil;
/**
 * �鲩ʿ����
 * @author LiWang
 * 2018��2��2��
 * http://localhost:8080/kcd/purchaseReport.do?appKey=8918da17a51cca06cbed868dcaeb669a&vin=/WBAFG210XBL505722&enginno=1&licenseplate=1
 */
@Controller
public class CBSController{
	private static final String USER_ID="60206";
	private static final String  KEY_SECRET="b2082f585b0fcb96f19283bb74e5f235";
	//�鲩ʿ�Ĳ���
	@Autowired
	private CbsSuccessfulPurchaseQueryReportService cspqr;
	@Autowired
	private jbzxapiuserService jbzxapiuserservice;
	//���򱨸� 
	@RequestMapping(value="/purchaseReport.do",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String  cbsPurchase(
			@RequestParam("appKey")String appKey,  //�û�����
			@RequestParam("vin") String vin,	//����Vin��
	/*		@RequestParam(value="callbackurl",required=false)String callbackurl, */
			/*�ص���ַ 
		    1.required=false��ʾ�����Ļ������������ֵΪnull��required=true���Ǳ���Ҫ�У�����400 
			2.������򱨸�ӿڵ�ʱ��û�д���ص��ĵ�ַ��˵������Ҫ�ص�������Ҫ�ӿڵ��û����ò鿴����״̬�Ľӿڲ鿴״̬
			3.�������״̬Ϊ�ѳ������ʱ��ſɵ��ò鿴��������ҳ�Ͳ鿴����json�Ľӿ�Ȼ�����,���ʧ�ܣ������ʧ��ԭ��
			*/
			@RequestParam(value="enginno",required=false) String enginno,//��������
			@RequestParam(value="licenseplate",required=false) String licenseplate //����
				) throws IOException{
		jbzxapiuser ja= jbzxapiuserservice.findapiuserbyappkey(appKey);
		String messages=null;
		JSONObject reslult=new JSONObject();//�Զ��巵����Ϣ �ǽӿڷ���
		if(ja!=null){//�������� 
			//�˻����
			BigDecimal balance=new  BigDecimal(ja.getApi_money());//�˻����
			BigDecimal zero=new  BigDecimal("0");//��
			BigDecimal remain=balance.subtract(new  BigDecimal(DeductFeeAmountTool.MAINTENANCE_ENQUIRY));//��ȥά�ޱ���ʣ�µĽ��
			boolean apitype=true;//api���Ͳ�����1��ʱ����߽���ȥ8>=0��ʱ��Ϊtrue
			boolean deductionlogo=true;//�۷ѱ�ʶ
			if(ja.getApi_type()!=1){//�ж��û�������
				deductionlogo=false; //���۷�
			}else{ //�۷�
				//�ж����
				if(remain.compareTo(zero)==-1){
					//��Ҳ��㹻
					apitype=false; 
				}
			}
			if(apitype){
				CBS cbs=CBS.getInstance(USER_ID, KEY_SECRET);
				//�ӿ�״̬  ���أ��ɹ� ������ʧ�ܷ�����Ϣ
				messages=cbs.getBuyReport(vin, enginno, licenseplate, null);
				JSONObject jsonobject=null;
				jsonobject=JSONObject.parseObject(messages);//ת��Ϊjson����
				
				Object code=null;//code��
				code=jsonobject.get("Code");
				
				int cbstart=0; //����״̬ 
				if(code.equals("0")){//��������ɹ� CodeΪ0��ʱ�����orderid
					if(deductionlogo){
						//���·���
						ja.setApi_money(remain.toString());
						jbzxapiuserservice.upmoney(ja);
					}
					CbsSuccessfulPurchaseQueryReport csfpq=new CbsSuccessfulPurchaseQueryReport();////�鲩ʿ��װ��
					csfpq.setCbsVin(vin); 
					csfpq.setCbsEnginno(enginno);
					csfpq.setCbsLicenseplate(licenseplate); 
					csfpq.setCbsApiuserId(ja.getId());
					
					String orderid=(String) jsonobject.get("orderId");//��ȡ����id 
					//����״̬
					String s=cbs.getReportStatus(orderid);//��ѯ����������Ϣ
					jsonobject=JSONObject.parseObject(s);
					code=jsonobject.get("Code");
					
					if(code.equals("1102")){//��ѯ��
						cbstart=1;
					}else if(code.equals("1104")){//�ѳ�����
						cbstart=2;
						//��ѯ�������� json��ʽ
						String reportdetails=cbs.getNewReportJson(orderid);
						csfpq.setCbsReportDetails(reportdetails);
					}
					csfpq.setCbsOrderid((String)orderid);//���涩��id
					csfpq.setCbsStart(cbstart);//���ö���״̬
					csfpq.setCbsMessage(s);//���淵�صĽ��
					String time=creditutil.time(); 
					csfpq.setCbsAddtime(time);//��Ӷ�����Ϣ
					csfpq.setCbsUptime(time); //������ʱ��
					//System.out.println(csfpq); ���������̨
					//���������ݿ�
					cspqr.insertSelective(csfpq);
				}
				return messages;
			}else{
				reslult.put("resultCode","2");
		      	reslult.put("resultMsg","�˻�����,���ֵ");
		      	reslult.put("success",false);
		      	return  reslult.toString();
			}
		}else{
	      	reslult.put("resultCode","1");
	      	reslult.put("resultMsg","�û����Ʋ�����");
	      	reslult.put("success",false);
	      	return  reslult.toString();
		}
	}
	//�鿴����״̬�ӿ�
	//http://localhost:8080/kcd/reportStatus.do?appKey=8918da17a51cca06cbed868dcaeb669a&orderId=fcc19c57aae3348e16d85ac79cb136be
	@RequestMapping(value="/reportStatus.do",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String reportStatus(
			@RequestParam("appKey")String appKey,  //�û�����
			@RequestParam("orderId")String orderId //����id
			){
			jbzxapiuser ja= jbzxapiuserservice.findapiuserbyappkey(appKey);
			if(ja!=null){
				return CBS.getInstance(USER_ID, KEY_SECRET).getReportStatus(orderId);
			}else{
				JSONObject reslult=new JSONObject();//�Զ��巵����Ϣ �ǽӿڷ���
			  	reslult.put("resultCode","1");
		      	reslult.put("resultMsg","�û����Ʋ�����");
		      	reslult.put("success",false);
		      	return reslult.toString();
			}
	}
	//���Ʒ���Ƿ�֧�ֲ�ѯ
	//http://localhost:8080/kcd/checkBrand.do?appKey=8918da17a51cca06cbed868dcaeb669a&vin=WBAFG210XBL505722
	@RequestMapping(value="/checkBrand.do",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String checkBrand(
			@RequestParam("appKey")String appKey,  //�û�����
			@RequestParam("vin")String vin //vin��
			){
			jbzxapiuser ja= jbzxapiuserservice.findapiuserbyappkey(appKey);
			if(ja!=null){
				return CBS.getInstance(USER_ID, KEY_SECRET).getCheckBrand(vin);
			}else{
				JSONObject reslult=new JSONObject();//�Զ��巵����Ϣ �ǽӿڷ���
			  	reslult.put("resultCode","1");
		      	reslult.put("resultMsg","�û����Ʋ�����");
		      	reslult.put("success",false);
		      	return reslult.toString();
			}
	}
	//��ѯ��������(url)
	//����һ��json����pcUrl���Բ鿴PC��������ҳ;mobileUrl:���Կ���wap�ı�������ҳ
	//http://localhost:8080/kcd/reportDetailsUrl.do?appKey=8918da17a51cca06cbed868dcaeb669a&orderId=3754b2334f23400ca7e7dff0d5d51331
	/*@RequestMapping(value="/reportDetailsUrl.do",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String reportDetailsUrl(
			@RequestParam("appKey")String appKey,  //�û�����
			@RequestParam("orderId")String orderId //����id
			){
			JSONObject reslult=new JSONObject();//�Զ��巵����Ϣ �ǽӿڷ���
			jbzxapiuser ja= jbzxapiuserservice.findapiuserbyappkey(appKey);
			if(ja!=null){
				Map map=CBS.getInstance(USER_ID, KEY_SECRET).getNewReportUrl(orderId);
				reslult.put("resultCode", "0");
		      	reslult.put("pcUrl",map.get("pcUrl"));
		      	reslult.put("mobileUrl",map.get("mobileUrl"));
		      	reslult.put("success", true);
		      	return reslult.toString();
			}else{
			
			  	reslult.put("resultCode","1");
		      	reslult.put("resultMsg","�û����Ʋ�����");
		      	reslult.put("success",false);
		      	return reslult.toString();
			}
	}*/
	//��ȡjson����
	@RequestMapping(value="/reportDetailsJson.do",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String reportDetailsJson(
			@RequestParam("appKey")String appKey,  //�û�����
			@RequestParam("orderId")String orderId //����id
			){
			JSONObject reslult=new JSONObject();//�Զ��巵����Ϣ �ǽӿڷ���
			jbzxapiuser ja= jbzxapiuserservice.findapiuserbyappkey(appKey);
			if(ja!=null){
				/*
				����orderid��ѯ
				CbsSuccessfulPurchaseQueryReportExample cspqre=new CbsSuccessfulPurchaseQueryReportExample();
				Criteria criteria=cspqre.createCriteria();
				criteria.andCbsOrderidEqualTo(orderId);
				List<CbsSuccessfulPurchaseQueryReport> selectByExample = cspqr.selectByExample(cspqre);*/
				String reportdetailsjson=CBS.getInstance(USER_ID, KEY_SECRET).getNewReportJson(orderId);
		      	return reportdetailsjson;
			}else{
			  	reslult.put("resultCode","1");
		      	reslult.put("resultMsg","�û����Ʋ�����");
		      	reslult.put("success",false);
		      	return reslult.toString();
			}
	}

	
}
