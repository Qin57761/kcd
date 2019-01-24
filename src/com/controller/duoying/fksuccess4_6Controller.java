package com.controller.duoying;

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

import com.http.duoying.syncjkrxxHttp;
import com.model1.bank;
import com.model1.mgcert;
import com.model1.ylds;
import com.model1.ylqy;
import com.service1.duoying.bankService;
import com.service1.duoying.mgcarService;
import com.service1.duoying.mgcertService;
import com.service1.duoying.yldsService;
import com.service1.duoying.ylqyService;
import com.util.creditutil;
import com.util.duoying.bllwxs;
import com.util.duoying.syncutil;

import net.sf.json.JSONObject;

@Controller
public class fksuccess4_6Controller {
	@Autowired
	private mgcertService mgcertservice;
	
	@Autowired
	private mgcarService mgcarservice;
	
	@Autowired
	private ylqyService ylqyservice;
	
	@Autowired
	private bankService bankservice;
	
	@Autowired
	private yldsService yldsservice;
	
      /**
       * 4.6	�ſ���ɽӿ�
       *�ӿ�˵��:֪ͨ��ӯ�ſ����
       * @return
       */
	@RequestMapping(value="fksuccess.do",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String fksuccess(){
		List<JSONObject> disbursementInfo=new ArrayList<JSONObject>();		
		List<mgcert> ml=mgcertservice.Apijkxxmgcert();		
	     com.alibaba.fastjson.JSONObject data=new com.alibaba.fastjson.JSONObject();
	     com.alibaba.fastjson.JSONObject obj=new com.alibaba.fastjson.JSONObject();
	     String s = null;
	     DecimalFormat jd = new DecimalFormat("#0.00");
	     String res="";
		if(ml!=null&&ml.size()>0){			
		for(mgcert m : ml){	
			    JSONObject jb=new JSONObject();
                jb.put("loanBaseId",m.getGems_code()+"|mgcert");//�����Ϣ�ⲿΨһID  , gems_code
//              ֧����ʽ
//              0:������֧��ƽ̨,
//              1:�ֽ�,2:����ת��,
//              3:Ӧ��Ʊ��,4:ծȨת�á�
              jb.put("disbursementPayType",2); 
            double ffje=Double.parseDouble(m.getC_mgprice_result())*10000; 
              jb.put("disbursementAmount",ffje);//���Ž��
             if(m.getDt_fk()!=null&&!m.getDt_fk().equals("")){
          	   jb.put("disbursementDate",creditutil.datatotime1(m.getDt_fk()));//��������
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
                       Bank.put("remark", "������");                        
                       jb.put("disbursementBank",Bank);//���������˻�
                       jb.put("repaymentBank",Bank);//���������˺�              
			    int qs=m.getC_mgdays()/30;
			    //0 ��Ϣ�� 1 �ȶϢ
		        int a = m.getC_mgtype();
		       // DecimalFormat jd = new DecimalFormat("#.00");  
				double bj =Double.parseDouble(m.getC_mgprice_result())*10000;
				bj = Double.parseDouble(jd.format(bj));
				//System.out.println();
				//loanInfo.put("repaymentDay",s);// ������	
				SimpleDateFormat dftime = new SimpleDateFormat("yyyy-MM-dd"); // ���ڸ�ʽ
				double mq = 0;//ÿ�ڻ����� mq_money
				double sq = 0;//���ڻ����� sq_money
				double wq = 0;//β�ڻ����� wq_money
				double lx = 0;
				//0 ��Ϣ�� 1 �ȶϢ
				 List<ylds>  dl= yldsservice.findyldsbyid(y.getACCOUNT_NO());
		          double dszje=0;//��������Ϣ
		          double ze=0;//�ܶ�
		          double dsmqje=0;//
		          double qdmqlx=0;//����ÿ����Ϣ		
		          double qdfy=0;
		          double lv=0;
		          double qdwqje=0;//��Ϣ�� ����β�ڻ�����
				if(a==1){				//0.0068		        
			    if(dl!=null&&dl.size()>0 ){
			        	 double amount=0;
			        	 //double amount1=0;
			        	 for(int i=0;i<dl.size();i++){
			        		 ylds ylds=dl.get(i); 			        		 
			        		 amount=amount+ylds.getAMOUNT();			        		 
			        	 }
			        	  ylds ylds1=dl.get(2); 
			        	// System.out.println("Ӧ���ܶ�:"+ze);
			        	 dszje=amount/100;
			        	 dsmqje=(double)ylds1.getAMOUNT()/100;
			        	 System.out.println("����ÿ�ڽ�"+dsmqje);
			    }	
				mq=bj*0.0068+bj/qs;
		        sq=bj*0.0068+bj/qs;
		        System.out.println("a=1ʱ��"+sq);
		        lx=bj*0.0068;
		        qdmqlx=dsmqje-lx-bj/qs;
		        System.out.println("����ÿ����Ϣ:"+qdmqlx);
		        wq=bj*0.0068+bj/qs+qdmqlx;// β�ڻ����� wq_money	
		        qdfy=dszje-lx*qs-bj;
		        lv=0.0068;//����  �ȶϢ
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
			        	 System.out.println("Ӧ���ܶ�:"+ze);
			        	 dszje=amount/100;			        	 
			        	 dsmqje=(double)ylds.getAMOUNT()/100;		        	 
			           }	
		        mq=bj*0.01;
		 	    sq=bj*0.01;
		 	    System.out.println("a=0ʱ��"+sq);
		 	    lx=bj*0.01;//��Ϣ		         
		 	    qdmqlx=dsmqje-lx;
		 	    wq=qdwqje;// β�ڻ����� wq_money	
		 	    qdfy=dszje-lx*qs-bj;
		 	    lv=0.01;//����  ��Ϣ��
		 	    
		        }
		        if(m.getDt_fk()!=null){
		        	 jb.put("baseDay",creditutil.datatotime1(m.getDt_fk()));//ָ����׼��	
		        }
		          //System.out.println("���п��ţ�"+y.getACCOUNT_NO());		         
		          //System.out.println("�ܶ�:"+qdzlx);
		          jb.put("repaymentCost",jd.format(qdfy));//Ӧ������
	              jb.put("everyRepaymentAmount",jd.format(dsmqje));//ÿ�ڻ�����
	              jb.put("firstRepaymentAmount",jd.format(dsmqje));//���ڻ�����
	              jb.put("lastRepaymentAmount",jd.format(wq));//β�ڻ�����
	              jb.put("pricesp","");//��Ϣ��              
	              jb.put("repaymentPrincipal",bj);//Ӧ������
	              jb.put("repaymentInterest",jd.format(lx*qs));//Ӧ����Ϣ	              
	              jb.put("repaymentTotal",jd.format(dszje));//Ӧ���ܶ�
	              jb.put("disbursementRate",lv);//��������
		         //����ƻ�
		         if(m.getDt_fk()!=null){
		        	try {
						jb.put("repaymentPlan",addRepaymentPlan(
                         sq,
                        m.getDt_fk(),
                        lx,qs,wq,bj,qdmqlx,qdwqje,dsmqje,a));
					} catch (ParseException e) {												
				        System.out.println("����ƻ�error:--"+e);
				        continue;
					}	 
		        } 
          	}   
                disbursementInfo.add(jb);
			  }		
        if(null!=disbursementInfo&&disbursementInfo.size()>0){
	    	int pointsDataLimit =200;//��������
	    	Integer size = disbursementInfo.size();
	    	//�ж��Ƿ��б�Ҫ����
	    	if(pointsDataLimit<size){
	    	int part = size/pointsDataLimit;//������
	    	System.out.println("���� �� "+size+"������"+" ��Ϊ ��"+part+"��");
	    	//
	    	for (int i = 0; i < part; i++) {
	    		
	    	//100��
	        List<JSONObject> listPage = disbursementInfo.subList(0,pointsDataLimit);
			 data.put("disbursementInfo",listPage);
	    	 obj=syncutil.createHead(data);
	    	 obj.put("data", data);
	         s=syncjkrxxHttp.dyhttp("http://abs.51duoying.com:8082/ws/services/rest/loan/doDisbursement", obj.toString());  	 			     	
		     res=res+s.toString();
		     disbursementInfo.subList(0,pointsDataLimit).clear();  
	        // return data.toString();		
	      }
	     if(!disbursementInfo.isEmpty()){
			 data.put("disbursementInfo",disbursementInfo);
	    	 obj=syncutil.createHead(data);
	    	 obj.put("data", data);
	    	 s=syncjkrxxHttp.dyhttp("http://abs.51duoying.com:8082/ws/services/rest/loan/doDisbursement", obj.toString());  	 			     	
			 res=res+s.toString();
	     }
	   }else{
			 data.put("disbursementInfo",disbursementInfo);
	    	 obj=syncutil.createHead(data);
	    	 obj.put("data", data);
	    	 s=syncjkrxxHttp.dyhttp("http://abs.51duoying.com:8082/ws/services/rest/loan/doDisbursement", obj.toString());  	 			     	
		     res=s.toString();
	   }
	   }else{
	     System.out.println("û������!!!");
	   }
       }
		return res.toString();
	
		
		
	}
    //���ڼӶ�����
		public static Date addDate(Date date,long day) throws ParseException {							
			 long time = date.getTime(); //�õ�ָ�����ڵĺ�����
			 day = day*24*60*60*1000; //Ҫ���ϵ�����ת���ɺ�����
			 time+=day; //��ӵõ��µĺ�����
			 return new Date(time); // ��������ת��������
			}
		  //����ת�� 21312312 ��ʽ
		public static String todata(String time) throws ParseException {						
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMddHHmmss");// ���ڸ�ʽ
			Date date =df.parse(time); // ָ������
			//df1.format(date);
		    return df1.format(date).toString(); 
		    
		}
    /**
     * ����ƻ�
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
        // SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMdd");// ���ڸ�ʽ		
        DecimalFormat jd = new DecimalFormat("#0.00");  
    	
        for (int i = 0; i < qs; i++) {
            JSONObject one = new JSONObject();
            one.put("period",i+1);// ����	  
            //System.out.println("time:"+time);
    	    Date date = df.parse(time);
    		Date d= addDate(date,30);
    		//System.out.println(df.format(d));
    		//time= todata(df.format(d).toString()); 		
            one.put("repaymentDate",todata(df.format(d).toString()));// �� Ӧ������
        	double ssfy=0;
            if(a==0){            	
            	if(i+1==qs){
            	one.put("repaymentPrincipal",bj);// �� Ӧ������	
            	ssfy=qdwqje-lx-bj;	
            	wq=qdwqje;
            	}else{	
            	one.put("repaymentPrincipal","0");// �� Ӧ������		
            	}            	
            }
            if(a==1){
            	String mqbj=jd.format(bj/qs);
            	System.out.println("ÿ�ڱ���"+mqbj);
            if(bj%qs>0){ 
               one.put("repaymentPrincipal",jd.format(bj/qs));// �� Ӧ������       	                   
            }else{
               one.put("repaymentPrincipal",jd.format(bj/qs));// �� Ӧ������ 
               System.out.println("ÿ�ڱ���"+bj/qs);
            } 
              if(i+1==qs){
             wq=bj*0.0068+bj/qs+qdmqlx; 
             ssfy=qdmqlx;
             }             
            }            
            if(i+1==1){            	
            	 one.put("periodType",0);// ����ƻ�
            	 one.put("repaymentCost",jd.format(qdmqlx));// Ӧ������
            	 yhxj=yhxj+sq+qdmqlx;            	
            	 one.put("repaymentTotal",jd.format(sq+qdmqlx));// Ӧ��С��  
            	 one.put("progress",jd.format(yhxj));
            }else if(i+1==qs){
            	 one.put("periodType",1);// ����ƻ�            
            	 one.put("repaymentCost",jd.format(ssfy));// Ӧ������
            	 one.put("repaymentTotal",jd.format(wq));// Ӧ��С��
            	 yhxj=yhxj+wq;            	   
            	 one.put("progress",jd.format(yhxj));
            }else{
            	 one.put("periodType",2);// ����ƻ�
            	 one.put("repaymentCost",jd.format(qdmqlx));// Ӧ������
            	 yhxj=yhxj+sq+qdmqlx;
            	 one.put("repaymentTotal",jd.format(sq+qdmqlx));// Ӧ��С��  
            	 one.put("progress",jd.format(yhxj));           
            }
            //yhfy=Double.parseDouble(jd.format(lx+sq));
            one.put("repaymentInterest",jd.format(lx));// �� Ӧ����Ϣ        
            one.put("remark ","����ƻ�");// ��ע
            time=df.format(d).toString();	           
            list.add(one);
        }
        return list;
    }

  public static void main(String[] args) {
//		DecimalFormat jd = new DecimalFormat("#0.00");
//	     double  i=4.1265; 
//	     double  i1=4.56789;	   
//	     String s=String.valueOf(i);
//	     System.out.println(10%2);
//	     System.out.println(s.substring(".".length()+1,".".length()+3));
//	     if(Integer.parseInt(s.substring(s.length()-2,s.length()-1))>4){
//	    	 String  d= jd.format(i);
//	    	 System.out.println(">5:"+d); 		     
//	     }else{
//	    	 i=Double.parseDouble(s.substring(0, ".".length()+3))+0.01;		    	 
//	    	 System.out.println("<5:"+i);  
//	     }
	   double s=2246.70*24-40000-40000*0.0068*24;
	   
	    System.out.println((double)6528+40000+7392.8); 	    
	     
        }
    
    
}
