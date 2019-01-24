package com.controller.wzfind;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.model.jbapi.jbzxapiuser;
import com.model.wzfind.peccancy;
import com.service.wzfind.peccancyService;
import com.service.zx.jbzxapiuserService;
import com.util.creditutil;

@Controller
public class peccancyController {
	
	
	@Autowired jbzxapiuserService jbzxapiuserService;
    public static final String APPKEY = "25f54018acb1a239";// ���appkey
    public static final String URL1 = "http://api.jisuapi.com/illegal/carorg2";
    public static final String URL2 = "http://api.jisuapi.com/illegal/lstype";
    public static final String URL3 = "http://api.jisuapi.com/illegal/query";
	//��ȡ���ܾ�
    public static JsonNode carorg2(){
	        HttpResponse<JsonNode> jsonResponse = null;
	        try {
	            jsonResponse = Unirest.get(URL1)
	            		.header("method", "get")
	            		//.header("Content-Type","text/html;charset=UTF-8")
	                    //.header("ckey",cKey)
	                    //.routeParam("method", "get")
	                    .queryString("appkey",APPKEY)//
                        .asJson();
	            //Unirest.shutdown();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        //System.out.println(jsonResponse.getBody());
	        return jsonResponse.getBody();
	    }
//{"result":"","status":"101","msg":"APPKEYΪ��"}
//{"result":"","status":"101","msg":"APPKEY������"}
    //��AN228E
    public static final String carorg = "";// ���ִܾ���
    public static final String lsprefix = "��";// ����ǰ׺ utf8
    public static final String lsnum = "J5AE33";// ����
    public static final String lstype = "02";// ��������
    public static final String engineno = "506527";// ��������
    public static final String frameno = "529043";// ���ܺ�
    //public static final String iscity = "";// �Ƿ񷵻س���
    public static final String mobile = "";// �ֻ���
	public static void main(String[] args) {
		//JsonNode s=carorg2();
		JsonNode s=querywz(carorg, lsprefix, lsnum, lstype,frameno,engineno, 0, mobile);
		System.out.println(s);

	  
	}

    //��ȡ���ܾ�
    @RequestMapping(value="/obtainRTA.do",produces="text/html;charset=UTF-8")
    @ResponseBody
	public String  obtainRTA(String appkey){		
    	if(appkey==null||appkey.equals("")){
    		JSONObject result=new JSONObject();
    		result.put("result", "");
    		result.put("status", "101");
    		result.put("msg", "APPKEYΪ��");
    		return result.toJSONString();
    	}
    	jbzxapiuser jau=jbzxapiuserService.findapiuserbyappkey(appkey);
		if(jau!=null&&!jau.equals("")){
			JsonNode s=carorg2();
			return s.toString();
		}else{
			JSONObject result=new JSONObject();
    		result.put("result", "");
    		result.put("status", "101");
    		result.put("msg", "APPKEY������");
    		return result.toJSONString();
		}
		
	}
     //��ȡ��������
	 public static JsonNode lstype(){
	        HttpResponse<JsonNode> jsonResponse = null;
	        try {
	            jsonResponse = Unirest.get(URL2)
	            		.header("method", "get")
	            		//.header("Content-Type","text/html;charset=UTF-8")
	                    //.header("ckey",cKey)
	                    //.routeParam("method", "get")
	                    .queryString("appkey",APPKEY)//
                        .asJson();
	            //Unirest.shutdown();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        //System.out.println(jsonResponse.getBody());
	        return jsonResponse.getBody();
	    }
    //��ȡ��������
    @RequestMapping(value="/obtaincarType.do",produces="text/html;charset=UTF-8")
    @ResponseBody
	public String  obtaincarType(String appkey){		
    	if(appkey==null||appkey.equals("")){
    		JSONObject result=new JSONObject();
    		result.put("result", "");
    		result.put("status", "101");
    		result.put("msg", "APPKEYΪ��");
    		return result.toJSONString();
    	}
    	jbzxapiuser jau=jbzxapiuserService.findapiuserbyappkey(appkey);
		if(jau!=null&&!jau.equals("")){
			JsonNode s=lstype();
			return s.toString();
		}else{
			JSONObject result=new JSONObject();
    		result.put("result", "");
    		result.put("status", "101");
    		result.put("msg", "APPKEY������");
    		return result.toJSONString();
		}
		
	}

    /**
     * ��ѯΥ��
     * @param carorg
     * @param lsprefix
     * @param lsnum
     * @param lstype
     * @param engineno
     * @param frameno
     * @return
     */
    public static JsonNode querywz(
			 String carorg,
			 String lsprefix,
			 String lsnum,
			 String lstype,
			 String frameno,
			 String engineno,			 
			 int iscity,
			 String mobile
			 ){
	        HttpResponse<JsonNode> jsonResponse = null;
	        
	        try {
	        	//System.out.println("4-----"+lsprefix);
	            jsonResponse = Unirest.get(URL3)
	            		.header("method", "get")
	            		.header("Content-Type","text/html;charset=UTF-8")
	                    //.header("ckey",cKey)
	                    //.routeParam("method", "get")
	                    .queryString("appkey",APPKEY)//
	                    .queryString("carorg",carorg)// ���ִܾ���
	                    .queryString("lsprefix",lsprefix)// ����ǰ׺ utf8
	                    .queryString("lsnum",lsnum)// ����
	                    .queryString("lstype",lstype)// ��������
	                    .queryString("engineno",engineno)// ��������
	                    .queryString("frameno",frameno)// ���ܺ�
	                    .queryString("iscity",iscity)// ���ܺ�
	                    .queryString("mobile",mobile)// ���ܺ�
	                    .asJson();
	            //Unirest.shutdown();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        //System.out.println(jsonResponse.getBody());
	        return jsonResponse.getBody();
	    }    
    //������ߵķ���
    public static String testStringBuilder(int sl) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i<sl; i++) {
            sb.append(0);
        }
        return sb.toString();
        
    }
     @Autowired
     private peccancyService peccancyService;
    //��ѯΥ��
    @RequestMapping(value="/queryPeccancy.do",produces="text/html;charset=UTF-8")
    @ResponseBody
	public String  queryPeccancy(
			@RequestParam("appkey")String appkey,
			@RequestParam("carorg")String carorg,//���ִܾ���
			@RequestParam("lsprefix")String lsprefix,//����ǰ׺
			@RequestParam("lsnum")String lsnum,//����
			@RequestParam("lstype")String lstype,//��������
			@RequestParam("frameno")String frameno,//���ܺ�
			@RequestParam("engineno")String engineno,//��������
			@RequestParam("iscity")String iscity,
			@RequestParam("mobile")String mobile
			){
    	
			//System.out.println("1-------"+lsprefix);
		
    	if(appkey==null||appkey.equals("")){
    		JSONObject result=new JSONObject();
    		result.put("result", "");
    		result.put("status", "101");
    		result.put("msg", "APPKEYΪ��");
    		return result.toJSONString();
    	}
    	int ic=0;
    	if(iscity!=null&&!iscity.equals("")){
    		ic=Integer.parseInt(iscity);
    	}
    	jbzxapiuser jau=jbzxapiuserService.findapiuserbyappkey(appkey);
		if(jau!=null&&!jau.equals("")){
			String money=jau.getApi_money();			
			if(money!=null&&!money.equals("")){
				BigDecimal b1 = new BigDecimal(money);
				String s="0.2";
				if(
						lsprefix.equals("��")||
						lsprefix.equals("��")||
						lsprefix.equals("��")
						){
			    s="0.26";	
				}else
				if(
						lsprefix.equals("��")||
						lsprefix.equals("��")
				){
				s="0.36";
				}
				BigDecimal b2 = new BigDecimal(s);	
				if(b1.subtract(b2).doubleValue()>0){
					peccancy peccancy=new peccancy();
					peccancy.setAuthority(carorg);
					peccancy.setCarprefix(lsprefix);
					//System.out.println("2-------"+lsprefix);
					peccancy.setCarrest(lsnum);
					peccancy.setCartype(lstype);
					peccancy.setCkeyid(jau.getId());
					peccancy.setVinno(frameno);
					peccancy.setEngineno(engineno);
					peccancy.setIscity(ic);
					peccancy.setMobile(mobile);
					peccancy.setAddtime(creditutil.time());
					peccancy.setUptime(creditutil.time());
					//peccancy.setOrderno("APIJBZX"+testStringBuilder(7-String.valueOf(zj.getId()).length())+zj.getId());					
					peccancyService.addpeccancy(peccancy);
					peccancy peccancy1=new peccancy();
					peccancy1.setUptime(creditutil.time());
					peccancy1.setOrderno("APIWZCX"+testStringBuilder(7-String.valueOf(peccancy.getId()).length())+peccancy.getId());
					peccancy1.setId(peccancy.getId());
					peccancyService.uporderno(peccancy1);		
					//System.out.println("3-------"+lsprefix);
					JsonNode res=querywz(carorg,lsprefix,
							lsnum,lstype,
							frameno,engineno,
							ic,mobile);
					JSONObject jb=JSONObject.parseObject(res.toString());
					if(
							jb.get("status").equals("0")||
							jb.get("status").equals("208")||
							jb.get("status").equals("210")
							){
						peccancy peccancy2=new peccancy();
						peccancy2.setId(peccancy.getId());
						peccancy2.setResultmsg(jb.toString());
						peccancy2.setOrderstate(1);
						peccancy2.setUptime(creditutil.time());
						peccancyService.uppeccancy(peccancy2);
						if(jb.get("status").equals("0")){
							peccancyService.upporderstate(peccancy2);	
						}
						jbzxapiuser jbzxapiuser=new jbzxapiuser();
						jbzxapiuser.setApi_money(b1.subtract(b2).toString());
						jbzxapiuser.setId(jau.getId());
						jbzxapiuserService.upmoney(jbzxapiuser);
					}					
					return res.toString();
				}else{
					JSONObject result=new JSONObject();
		    		result.put("result", "");
		    		result.put("status", "109");
		    		result.put("msg", "����");
		    		return result.toJSONString();
				}
			}else{
				    JSONObject result=new JSONObject();
	    		    result.put("result", "");
	    		    result.put("status", "109");
	    		    result.put("msg", "����");
	    		    return result.toJSONString();
			}			
		}else{
			JSONObject result=new JSONObject();
    		result.put("result", "");
    		result.put("status", "101");
    		result.put("msg", "APPKEY������");
    		return result.toJSONString();
		}
		
	}
    
    
}
