package com.controller.dyapi;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.http.duoying.syncjkrxxHttp;
import com.model1.bank;
import com.model1.mgcert;
import com.model1.ylds;
import com.model1.ylqy;
import com.model1.zjf.zjfhistory;
import com.service1.duoying.bankService;
import com.service1.duoying.mgcertService;
import com.service1.duoying.yldsService;
import com.service1.duoying.ylqyService;
import com.service1.zjf.zjfhistoryService;
import com.util.creditutil;
import com.util.duoying.syncutil;

@Controller
public class tofkController {
	@Autowired
	private mgcertService mgcertservice;
	
	@Autowired
	private ylqyService ylqyservice;
	
	@Autowired
	private bankService bankservice;
	
	@Autowired
	private yldsService yldsservice;
	
	@Autowired
	private zjfhistoryService zjfhistoryservice;
    //日期加多少天
		public static Date addDate(Date date,long day) throws ParseException {							
			 long time = date.getTime(); //得到指定日期的毫秒数
			 day = day*24*60*60*1000; //要加上的天数转换成毫秒数
			 time+=day; //相加得到新的毫秒数
			 return new Date(time); // 将毫秒数转换成日期
			}
		  //日期转换 21312312 格式
		public static String todata(String time) throws ParseException {						
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMddHHmmss");// 日期格式
			Date date =df.parse(time); // 指定日期
			//df1.format(date);
		    return df1.format(date).toString(); 
		    
		}
    /**
     * 还款计划
     * 
     * @return
     * @throws ParseException 
     */
    private static Object addRepaymentPlan(
    		double sq,
			String time,
			double lx,
			int qs,
			double wq,
			double bj,
			double qdmqlx,
			double qdwqje,
			double dsmqje,
			int a) throws ParseException {
        List<JSONObject> list = new ArrayList<>();	        
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
        double yhfy=0;
        double yhxj=0;
        double qdlx=0;
        // SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMdd");// 日期格式		
        DecimalFormat jd = new DecimalFormat("#0.00");  
    	
        for (int i = 0; i < qs; i++) {
            JSONObject one = new JSONObject();
            one.put("period",i+1);// 期数	  
            //System.out.println("time:"+time);
    	    Date date = df.parse(time);
    		Date d= addDate(date,30);
    		//System.out.println(df.format(d));
    		//time= todata(df.format(d).toString()); 		
            one.put("repaymentDate",todata(df.format(d).toString()));// 是 应还日期
        	double ssfy=0;
            if(a==0){            	
            	if(i+1==qs){
            	one.put("repaymentPrincipal",bj);// 否 应还本金	
            	ssfy=qdwqje-lx-bj;	
            	wq=qdwqje;
            	}else{	
            	one.put("repaymentPrincipal","0");// 否 应还本金		
            	}            	
            }
            if(a==1){
            	String mqbj=jd.format(bj/qs);
            	System.out.println("每期本金："+mqbj);
            if(bj%qs>0){ 
               one.put("repaymentPrincipal",jd.format(bj/qs));// 否 应还本金       	                   
            }else{
               one.put("repaymentPrincipal",jd.format(bj/qs));// 否 应还本金 
               System.out.println("每期本金："+bj/qs);
            } 
              if(i+1==qs){
             wq=bj*0.0068+bj/qs+qdmqlx; 
             ssfy=qdmqlx;
             }             
            }            
            if(i+1==1){            	
            	 one.put("periodType",0);// 还款计划
            	 one.put("repaymentCost",jd.format(qdmqlx));// 应还费用
            	 yhxj=yhxj+sq+qdmqlx;            	
            	 one.put("repaymentTotal",jd.format(sq+qdmqlx));// 应还小计  
            	 one.put("progress",jd.format(yhxj));
            }else if(i+1==qs){
            	 one.put("periodType",1);// 还款计划            
            	 one.put("repaymentCost",jd.format(ssfy));// 应还费用
            	 one.put("repaymentTotal",jd.format(wq));// 应还小计
            	 yhxj=yhxj+wq;            	   
            	 one.put("progress",jd.format(yhxj));
            }else{
            	 one.put("periodType",2);// 还款计划
            	 one.put("repaymentCost",jd.format(qdmqlx));// 应还费用
            	 yhxj=yhxj+sq+qdmqlx;
            	 one.put("repaymentTotal",jd.format(sq+qdmqlx));// 应还小计  
            	 one.put("progress",jd.format(yhxj));           
            }
            //yhfy=Double.parseDouble(jd.format(lx+sq));
            one.put("repaymentInterest",jd.format(lx));// 否 应还利息        
            one.put("remark ","还款计划");// 备注
            time=df.format(d).toString();	           
            list.add(one);
        }
        return list;
    }

	
	/**
	 * 3.11查询放款许可
     * 
	 * @return
	 */
	@RequestMapping(value="kjs_fkresult.do",produces = "text/html;charset=UTF-8")
	@ResponseBody
	private String kjs_fkresult(	
			String kjs_type,
			String id
			){
		List<JSONObject> disbursementInfo=new ArrayList<JSONObject>();
		List<mgcert> ml=new ArrayList<mgcert>();
		if(kjs_type.equals("8")){
		   ml=mgcertservice.carlist(6);
		}
        if(kjs_type.equals("9")){
    	   ml=mgcertservice.certlist(6);	
		}
        if(kjs_type.equals("10")){
    	   ml=mgcertservice.newcarlist(6);
        }				
	     com.alibaba.fastjson.JSONObject data=new com.alibaba.fastjson.JSONObject();
	     com.alibaba.fastjson.JSONObject obj=new com.alibaba.fastjson.JSONObject();
	     String s = null;
	     DecimalFormat jd = new DecimalFormat("#0.00");
	     String res="";
		if(ml!=null&&ml.size()>0){			
		for(mgcert m : ml){	
			    JSONObject jb=new JSONObject();
				if(kjs_type.equals("8")){
	            jb.put("loanBaseId",m.getGems_code()+"|mgcar|"+m.getSpcount());//借款信息外部唯一ID  , gems_code
				}
			    if(kjs_type.equals("9")){
		        jb.put("loanBaseId",m.getGems_code()+"|mgcert|"+m.getSpcount());//借款信息外部唯一ID  , gems_code	
				}
			    if(kjs_type.equals("10")){
		        jb.put("loanBaseId",m.getGems_code()+"|mgnewcar|"+m.getSpcount());//借款信息外部唯一ID  , gems_code
			    }
//              支付方式
//              0:第三方支付平台,
//              1:现金,2:银行转账,
//              3:应收票据,4:债权转让。
              jb.put("disbursementPayType",2); 
              double ffje=Double.parseDouble(m.getC_mgprice_result())*10000; 
              jb.put("disbursementAmount",ffje);//发放金额
              if(m.getDt_fk()!=null&&!m.getDt_fk().equals("")){
          	   jb.put("disbursementDate",creditutil.datatotime1(m.getDt_fk()));//发放日期
              }
              ylqy y =ylqyservice.ylqymap(m.getC_name());    
              if(y!=null){                	
              	JSONObject Bank=new JSONObject();
                  bank b = bankservice.bankmap(y.getBANK_CODE());
                  if(b!=null){
                  	 Bank.put("bankName",b.getName());	
                   } 
                       Bank.put("bankBranchName",y.getBANK_NAME());
                       Bank.put("accountNo",y.getACCOUNT_NO());
                       Bank.put("remark", "开户行");                        
                       jb.put("disbursementBank",Bank);//发放银行账户
                       jb.put("repaymentBank",Bank);//还款银行账号              
			    int qs=m.getC_mgdays()/30;
			    //0 先息后本 1 等额本息
		        int a = m.getC_mgtype();
		       // DecimalFormat jd = new DecimalFormat("#.00");  
				double bj =Double.parseDouble(m.getC_mgprice_result())*10000;
				bj = Double.parseDouble(jd.format(bj));
				//System.out.println();
				//loanInfo.put("repaymentDay",s);// 还款日	
				SimpleDateFormat dftime = new SimpleDateFormat("yyyy-MM-dd"); // 日期格式
				double mq = 0;//每期还款金额 mq_money
				double sq = 0;//首期还款金额 sq_money
				double wq = 0;//尾期还款金额 wq_money
				double lx = 0;
				//0 先息后本 1 等额本息
				 List<ylds>  dl= yldsservice.findyldsbyid(y.getACCOUNT_NO());
		          double dszje=0;//渠道总利息
		          double ze=0;//总额
		          double dsmqje=0;//
		          double qdmqlx=0;//渠道每期利息		
		          double qdfy=0;
		          double lv=0;
		          double qdwqje=0;//先息后本 渠道尾期还款金额
				if(a==1){				//0.0068		        
			    if(dl!=null&&dl.size()>0 ){
			        	 double amount=0;
			        	 //double amount1=0;
			        	 for(int i=0;i<dl.size();i++){
			        		 ylds ylds=dl.get(i); 			        		 
			        		 amount=amount+ylds.getAMOUNT();			        		 
			        	 }
			        	  ylds ylds1=dl.get(2); 
			        	// System.out.println("应还总额:"+ze);
			        	 dszje=amount/100;
			        	 dsmqje=(double)ylds1.getAMOUNT()/100;
			        	 System.out.println("代收每期金额："+dsmqje);
			    }	
				mq=bj*0.0068+bj/qs;
		        sq=bj*0.0068+bj/qs;
		        System.out.println("a=1时："+sq);
		        lx=bj*0.0068;
		        qdmqlx=dsmqje-lx-bj/qs;
		        System.out.println("渠道每期利息:"+qdmqlx);
		        wq=bj*0.0068+bj/qs+qdmqlx;// 尾期还款金额 wq_money	
		        qdfy=dszje-lx*qs-bj;
		        lv=0.0068;//利率  等额本息
		        }
		        if(a==0){               //0.01		       
		        	 if(dl!=null&&dl.size()>1){
			        	 double amount=0;
			        	 //double amount1=0;
			        	 for(int i=0;i<dl.size();i++){
			        		 ylds ylds=dl.get(i); 			        		 
			        		 amount=amount+ylds.getAMOUNT();			        		 
			        	 }
			        	 ylds ylds=dl.get(1); 
			        	 ylds ylds1=dl.get(0);
			        	 qdwqje=ylds1.getAMOUNT()/100;
			        	 ze=bj+lx*qs;
			        	 System.out.println("应还总额:"+ze);
			        	 dszje=amount/100;			        	 
			        	 dsmqje=(double)ylds.getAMOUNT()/100;		        	 
			           }	
		        mq=bj*0.01;
		 	    sq=bj*0.01;
		 	    System.out.println("a=0时："+sq);
		 	    lx=bj*0.01;//利息		         
		 	    qdmqlx=dsmqje-lx;
		 	    wq=qdwqje;// 尾期还款金额 wq_money	
		 	    qdfy=dszje-lx*qs-bj;
		 	    lv=0.01;//利率  先息后本
		 	    
		        }
		        if(m.getDt_fk()!=null){
		        	 jb.put("baseDay",creditutil.datatotime1(m.getDt_fk()));//指定基准日	
		        }
		          //System.out.println("银行卡号："+y.getACCOUNT_NO());		         
		          //System.out.println("总额:"+qdzlx);
		          jb.put("repaymentCost",jd.format(qdfy));//应还费用
	              jb.put("everyRepaymentAmount",jd.format(dsmqje));//每期还款金额
	              jb.put("firstRepaymentAmount",jd.format(dsmqje));//首期还款金额
	              jb.put("lastRepaymentAmount",jd.format(wq));//尾期还款金额
	              jb.put("pricesp","");//补息差              
	              jb.put("repaymentPrincipal",bj);//应还本金
	              jb.put("repaymentInterest",jd.format(lx*qs));//应还利息	              
	              jb.put("repaymentTotal",jd.format(dszje));//应还总额
	              jb.put("disbursementRate",lv);//发放利率
		         //还款计划
		         if(m.getDt_fk()!=null){
		        	try {
						jb.put("repaymentPlan",addRepaymentPlan(
                         sq,
                        m.getDt_fk(),
                        lx,qs,wq,bj,qdmqlx,qdwqje,dsmqje,a));
					} catch (ParseException e) {												
				        System.out.println("还款计划error:--"+e);
				        continue;
					}	 
		        } 
          	    }   
                disbursementInfo.add(jb);
			  }		
			 data.put("disbursementInfo",disbursementInfo);
	    	 obj=syncutil.createHead(data);
	    	 obj.put("data", data);
		        zjfhistory zh=new zjfhistory();
		        zh.setAddtime(creditutil.time());
		        zh.setUptime(creditutil.time());
		        zh.setSynpath("http://abs.51duoying.com:8082/ws/services/rest/loan/doApproval");
		        zh.setSynrecording(obj.toString());
		        zh.setCountnum(disbursementInfo.size());
		        zh.setZjfname("多盈放款");
		        zh.setPath_type(8);
	    	 try {
		    	 s=syncjkrxxHttp.dyhttp("http://abs.51duoying.com:8082/ws/services/rest/loan/doDisbursement", obj.toString());  	 			     	
		         zh.setReturnmsg(s);
		         zh.setSuccessnum(1);  
				 zjfhistoryservice.addsynhistory(zh);
				 JSONObject result=JSONObject.parseObject(s);
			    	if(result.getString("code").equals("200")){			    		
			    		JSONObject ret=JSONObject.parseObject(result.getString("ret"));
			            String[] successIds=ret.getString("successIds").replace(" ","").replace("[","").replace("]","").split(",");
			    	  if(successIds[0]!=null&&!successIds[0].equals("")){
			    		 // System.out.println("多盈终审查询(成功订单)");
			    		 for(int i=0;i<successIds.length;i++){
			    			 mgcert c=new mgcert();
			    			 if(kjs_type.equals("8")){
				    			 mgcert mc=mgcertservice.findcar(successIds[i].substring(1,successIds[i].indexOf("|")));		    			 				    				
				    				 c.setZjf_type("7");
				    				 //c.setSpcount(0);
				    				 c.setId(mc.getId());
				    				 mgcertservice.upmgcar(c);
				    			//System.out.println("审核成功订单id:"+successIds[i].substring(1,successIds[i].indexOf("|")));
			    			 }
			    			 if(kjs_type.equals("9")){
				    			 mgcert mc=mgcertservice.findcert(successIds[i].substring(1,successIds[i].indexOf("|")));		    			 				    			 
				    				 c.setZjf_type("7");
				    				 //c.setSpcount(0);
				    				 c.setId(mc.getId());
				    				 mgcertservice.upmgcert(c);
			    			 }
			    			 if(kjs_type.equals("10")){
				    			 mgcert mc=mgcertservice.findnewcar(successIds[i].substring(1,successIds[i].indexOf("|")));		    			 				    							    				 
				    				 c.setZjf_type("7");
				    				 //c.setSpcount(0);
				    				 c.setId(mc.getId());
				    				 mgcertservice.upmgnewcar(c);
			    			 }	
			    			 
			    		 }
			    	  }
			    		 }
				 return s.toString();
			 } catch (Exception e) {
				JSONObject result=new JSONObject();
				 result.put("code","201"); 
				 result.put("ret","");
				 result.put("msg","同步失败-"+e);
			     zh.setReturnmsg(result.toString());
			     zjfhistoryservice.addsynhistory(zh); 
			     return result.toString();
			 }
          }else{	
  			JSONObject jb=new JSONObject();
  			jb.put("code","202");
  			jb.put("ret","");
  			jb.put("msg","无记录");
  		    return jb.toString();
       }			
	}
}
