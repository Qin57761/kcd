package com.controller.kcdback;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.service.companyService;
import com.service.loginService;
import com.util.jsonutil;

@Controller
public class loginController {

	@Autowired
    private loginService loginservice;
	@Autowired
    private companyService companyservice;
	//json ������
    jsonutil ju=new jsonutil();
    
    private final Log logger = LogFactory.getLog(getClass());
	
	
	//��½��֤
    @RequestMapping(value="/login.do",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")

    public String login(HttpServletRequest request,String username,String password){
		Map  m=new HashMap();
		if(username!=null&&!username.equals("")&&password!=null&&!password.equals("")){								
    	m=loginservice.login(username, password);
    	System.out.println(jsonutil.toJSONString(m));
    	String company=(String) m.get("company");
    	if(m.size()>0){
    		
    		if(company!=null){
    			//���û�������session���Ա�����    		
        		// request.getSession().setMaxInactiveInterval(3);��λ����
                request.getSession().setAttribute("user",m);	        	    					
    		}else{
    		
    		    Map cmap=companyservice.findcompany(m.get("owencompany").toString());
    			m.putAll(cmap);
    			//���û�������session���Ա�����    		
        		// request.getSession().setMaxInactiveInterval(3);��λ����
                request.getSession().setAttribute("user",m);	
        	
    		}
    		
        }else{
        	
        	return "�˻���������󣡣���";
        	
        }   
//    	System.out.println(m.toString());
		}else{
			
			return "�˻�������Ϊ�գ�����";
			
		}
    	return "loginxx";    	    	    	    	
    }
	
}
