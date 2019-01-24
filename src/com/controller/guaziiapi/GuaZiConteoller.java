package com.controller.guaziiapi;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.model.guazi.GuaziRecords;
import com.model.jbapi.jbzxapiuser;
import com.service.guazi.GuaZiService;
import com.service.zx.jbzxapiuserService;
import com.util.DeductFeeAmountTool;
import com.util.EncryptUtils;
import com.util.creditutil;
/**
 * @author LiWang
 * 2018��5��14��
 */
@Controller
public class GuaZiConteoller {
	private final static  String appkey="kuaijiarenzheng_on";
	private final static String appsecret="5029b62ee218549a921d86b7dd06daf3";
	@Autowired
	private GuaZiService gzs;
	@Autowired
	private jbzxapiuserService jbzxapiuserservice;
	@RequestMapping(value="/getRecords.do",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String Records(String appKey,String vin){
		JSONObject reslult=new JSONObject();//�Զ��巵����Ϣ �ǽӿڷ���
		jbzxapiuser ja= jbzxapiuserservice.findapiuserbyappkey(appKey);
		String messages=null;
		int s=0;
		if(ja!=null){//�������� 
			if(vin==null){
			  	reslult.put("code","3");
		      	reslult.put("message","vin��Ϊ��");
		      	return  reslult.toString();
			}
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
				Map<String, Object> map = new HashMap<>();
				map.put("appkey","kuaijiarenzheng_on");
				map.put("expires",System.currentTimeMillis());
				map.put("nonce",UUID.randomUUID());
				map.put("vin",vin);  
				// LJ8F2D5D5FB023128 // LBETLBFCXFY046265 // LFBGE6063EJG25302
				String signaturn = EncryptUtils.generateSignature(map,"5029b62ee218549a921d86b7dd06daf3");
				try {
					messages=GuaZiWeiBaoUrl(map.get("appkey").toString(), map.get("expires").toString(), map.get("nonce").toString(),signaturn, map.get("vin").toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JSONObject jsonobject=(JSONObject) JSONObject.parse(messages); 
		        if(jsonobject.get("code").toString().equals("0")){//����ɹ�
		        	String status=jsonobject.getJSONObject("data").get("status").toString();
		        	System.out.println("status:"+status);
		        	if(status.equals("2")){
		        		s=2;
		        		if(deductionlogo){
							//���·���
							ja.setApi_money(remain.toString());
							jbzxapiuserservice.upmoney(ja);
						}
		        	}else if(status.equals("1")){//׼����
		        		s=1;
		        	}
		        }
			}else{
				reslult.put("code","2");
		      	reslult.put("message","�˻�����,���ֵ");
		      	return  reslult.toString();
			}
		}else{
	      	reslult.put("code","1");
	      	reslult.put("message","�û����Ʋ�����");
	      	return  reslult.toString();
		}
		//���۳ɹ�����ʧ�ܣ���������
		GuaziRecords gr=new GuaziRecords();
		String time=creditutil.time(); 
		gr.setGzAddtime(time);
		gr.setGzUptime(time);
		gr.setGzApiuserId(ja.getId());
		gr.setGzMessage(messages);
		gr.setGzStart(s);
		gr.setGzVin(vin);
		gzs.insert(gr);
		return messages;
	}
	public static String GuaZiWeiBaoUrl(
			String appkey,
    		String expires,
    		String nonce,
    		String signature,
    		String vin) throws IOException{
		String url = "http://opendata.guazi.com/oapi/car/data/maintenance/getRecords";
        CloseableHttpClient client = HttpClients.createDefault();
        //�������
        HttpUriRequest requestt = RequestBuilder.get()
                .setUri(url)
                .addParameter("appkey", appkey)
                .addParameter("expires", expires)
                .addParameter("nonce", nonce) 
                .addParameter("signature", signature) 
                .addParameter("vin", vin)
                .build();
        CloseableHttpResponse response = client.execute(requestt);
        // ���ص���API���
        return EntityUtils.toString(response.getEntity());
    }
}
