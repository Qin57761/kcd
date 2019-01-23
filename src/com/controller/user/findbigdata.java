package com.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.params.HttpParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.http.RiskServicePreloan;
import com.service.mdxxService;

@Controller
public class findbigdata {
	  private static final long   WAIT_TIME    = 5 * 1000;
	
	  @Autowired
	  private mdxxService mdxxservice;
	  
	  
    @RequestMapping(value="/bigdata.do",produces="text/html;charset=UTF-8")
    @ResponseBody
	public String bigdata(HttpServletRequest request,String ckey,String name,String ID_card,String mobile){
    	JSONObject response = null;
    	RiskServicePreloan riskServicePreloan=new RiskServicePreloan();
    	Map<String, Object> params=new HashMap<String, Object>();   
        Map m=mdxxservice.mdxxmap(ckey);
    	if(m!=null&&!m.equals("")&&m.size()>0){    		    		    	
    	 params.put("name",name); // ����
         params.put("id_number",ID_card); // ���֤��
         params.put("mobile",mobile); // �ֻ���
         JSONObject jsonobject=riskServicePreloan.apply(params);
         String[] s=riskServicePreloan.analyzeJsonToArray(jsonobject,"value");
        // System.out.println(s[0]);
         if (!jsonobject.equals("")) {
             // �ȴ�һ��ʱ��󣬿ɵ���query�ӿڲ�ѯ�����
             // ʱ�佨�飺5s��ɵ���
             try {
                 Thread.sleep(WAIT_TIME);
             } catch (Exception e) {
                 //
             }
             // query�ӿڻ�ȡ���
             response = riskServicePreloan.query(s[0]);
            // System.out.println(response);
             // �������� ������
         }
    	}
		return response.toString();
		
		
		
		
		
	}


	
	
	
	
	
	
}
