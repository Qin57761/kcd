package com.controller.icbc;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.model.icbc.icbc;
import com.model.icbc.icbc_kk;
import com.model.icbc.icbc_result;
import com.model1.gems;
import com.model1.icbc.icbc_dk;
import com.model1.icbc.icbc_mq;
import com.model1.icbc.icbc_mq_result;
import com.service.zx.jbzxapiuserService;
import com.service1.gemsService;
import com.service1.kjs_icbc.icbc_dkService;
import com.service1.kjs_icbc.icbc_mqService;
import com.service1.kjs_icbc.icbc_result1Service;
import com.service1.kjs_icbc.newicbcService;
import com.service1.kjs_icbc.newicbc_kkService;
import com.util.creditutil;

@Controller
public class icbc_jjController {

	 @Autowired
	 private newicbcService newicbcService;
	 @Autowired
	 private jbzxapiuserService jbzxapiuserService;
	 @Autowired
	 private newicbc_kkService newicbc_kkService;
	 @Autowired
	 private icbc_result1Service icbc_result1Service;
	 @Autowired
	 private gemsService gemsService;
	 @Autowired
	 private icbc_dkService icbc_dkService;
	 @Autowired
	 private icbc_mqService icbc_mqService;
	 @Autowired
	 private com.service1.kjs_icbc.icbc_mq_resultService icbc_mq_resultService;
	 


	 
	    @RequestMapping(value="/icbc_Entry_result.do",produces="text/html;charset=UTF-8")	
		@ResponseBody
		public String icbc_Entry_result(HttpServletRequest request){
	    	String orderid=request.getParameter("orderid");
	        String appkey=request.getParameter("appkey");
	        icbc icbc;
			if(orderid!=null&&!orderid.equals("")){
	        	icbc=newicbcService.findicbcbyorderid(orderid);        	
	            if(icbc==null||icbc.equals("")){
	            	JSONObject result=new JSONObject();
	    			result.put("result","");
	    			result.put("msg","������Ŵ���");
	    			result.put("code","212");
	    			return result.toString();
	            }	
	        }else{
	        	JSONObject result=new JSONObject();
				result.put("result","");
				result.put("msg","orderid����Ϊ��");
				result.put("code","210");
				return result.toString();
	        }
			gems gems;
	        if(appkey!=null&&!appkey.equals("")){
	        	gems=gemsService.find_api(appkey);
				if(gems!=null&&!gems.equals("")){
					if(gems.getApi_tag()!=1){
						JSONObject result=new JSONObject();
						result.put("result","");
						result.put("msg","δ��ͨAPIȨ��");
						result.put("code","211");
						return result.toString();			
					}
				}else{
					JSONObject result=new JSONObject();
					result.put("result","");
					result.put("msg","appkey����");
					result.put("code","211");
					return result.toString();
				}
			}else{
				JSONObject result=new JSONObject();
				result.put("result","");
				result.put("msg","appkey����Ϊ��");
				result.put("code","210");
				return result.toString();	
			}
	        icbc_dk icbc_dk=icbc_dkService.findjj_status(icbc.getId());
	        if(icbc_dk!=null&&!icbc_dk.equals("")){
	        	icbc_result kk_result=icbc_result1Service.kklastfindresult(Integer.parseInt(icbc_dk.getKkid()));
	        	icbc_result dk_result=icbc_result1Service.dklastfindresult(Integer.parseInt(icbc_dk.getDkid()));
	        	icbc_mq_result imr=icbc_mq_resultService.lasticbc_mq_result(Integer.parseInt(icbc_dk.getMqid()));
	        	JSONObject result=new JSONObject();
		        JSONObject result1=new JSONObject();
		        if(icbc_dk.getFk_status()>0){
		        result1.put("fk_status",icbc_dk.getFk_status());
		        result1.put("fk_date",icbc_dk.getFk_time().replace(".0", ""));	
		        }
		        result1.put("orderid",orderid);
		        if(icbc_dk.getKk_kh()!=null&&!icbc_dk.getKk_kh().equals("")){
		        result1.put("kk_kh",icbc_dk.getKk_kh());
		        }
		        if(icbc_dk.getPdfstep4_1()!=null&&!icbc_dk.getPdfstep4_1().equals("")){
		        result1.put("kk_pdffile","http://a.kcway.net/assess/"+icbc_dk.getPdfstep4_1());
			    result1.put("kk_pdfdate",icbc_dk.getPdf_time().replace(".0", ""));	
		        }
		        result1.put("kk_status",icbc_dk.getKk_status());
		        if(kk_result!=null&&!kk_result.equals("")){  
		        result1.put("kk_date",kk_result.getDt_add().replace(".0", ""));
		        result1.put("kk_remark",kk_result.getRemark());
		        }
		        result1.put("mq_status",icbc_dk.getMq_status());
		        if(imr!=null&&!imr.equals("")){
		        result1.put("mq_date",imr.getDt_add().replace(".0", ""));
		        result1.put("mq_remark",imr.getRemark());
		        }
		        result1.put("dk_status",icbc_dk.getDk_status());
		        if(dk_result!=null&&!dk_result.equals("")){
		        result1.put("dk_date",dk_result.getDt_add().replace(".0", ""));
		        result1.put("dk_remark",dk_result.getRemark());
        		}
		        result.put("result",result1);
				result.put("msg","�ɹ�");
				result.put("code","200");
				return result.toString();
	        }else{
	        	JSONObject result=new JSONObject();
		        result.put("result","");
				result.put("msg","û�н�����Ϣ");
				result.put("code","211");
				return result.toString();
	        }
	        
	    }	 
    @RequestMapping(value="/icbc_Entry.do",produces="text/html;charset=UTF-8")	
	@ResponseBody
	public String icbc_Entry(HttpServletRequest request){
    	String orderid=request.getParameter("orderid");
        String appkey=request.getParameter("appkey");
        String name=request.getParameter("name");//����
        String phoneno=request.getParameter("phoneno");//�ֻ���
        String billingprice=request.getParameter("billingprice");//��Ʊ��
        String loanprice=request.getParameter("loanprice");//������
        String coverprice=request.getParameter("coverprice");//�����
        String loanallprice=request.getParameter("loanallprice");//�����ܶ�
        String sfje=request.getParameter("sfje");//�����ܶ�
        String mortgagemodel=request.getParameter("mortgagemodel");//����ģʽ
        String mortgageterm=request.getParameter("mortgageterm");//��������
        String mortgagebank=request.getParameter("mortgagebank");//��������
        String dk_lv=request.getParameter("dk_lv");//��������
        String dz_type=request.getParameter("dz_type");//��������
        String kk_car_stateid=request.getParameter("kk_car_stateid");//����ʡ
        String kk_car_cityid=request.getParameter("kk_car_cityid");//������
        String kk_loan_stateid=request.getParameter("kk_loan_stateid");//ҵ��ʡ
        String kk_loan_cityid=request.getParameter("kk_loan_cityid");//ҵ����
        String cars_type=request.getParameter("cars_type");//ҵ����
        String oredit=request.getParameter("oredit");//�Ƿ�༭�˶���  1�� /2�� Ĭ��Ϊ�� 
        gems gems;   
        if(loanprice!=null
        		&&!loanprice.equals("")
        		&&coverprice!=null
        		&&!coverprice.equals("")	
        		&&loanallprice!=null
        		&&!loanallprice.equals("")
        		){
        	BigDecimal b1 = new BigDecimal(loanprice);   
    		BigDecimal b2 = new BigDecimal(coverprice); 
    		BigDecimal b3 = new BigDecimal(loanallprice);	   
		if(new BigDecimal(b3.doubleValue()).compareTo(new BigDecimal(b1.add(b2).doubleValue()))!=0){
		    JSONObject result=new JSONObject();
			result.put("result","");
			result.put("msg","������+����Ѳ����ڴ����ܶ�");
			result.put("code","215");
			return result.toString();
	    }     
        }
        if(appkey!=null&&!appkey.equals("")){
        	gems=gemsService.find_api(appkey);
			if(gems!=null&&!gems.equals("")){
				if(gems.getApi_tag()!=1){
					JSONObject result=new JSONObject();
					result.put("result","");
					result.put("msg","δ��ͨAPIȨ��");
					result.put("code","211");
					return result.toString();			
				}
			}else{
				JSONObject result=new JSONObject();
				result.put("result","");
				result.put("msg","appkey����");
				result.put("code","211");
				return result.toString();
			}
		}else{
			JSONObject result=new JSONObject();
			result.put("result","");
			result.put("msg","appkey����Ϊ��");
			result.put("code","210");
			return result.toString();	
		}
        icbc_kk ik=new icbc_kk();
        ik.setC_name(name);            
        ik.setC_tel(phoneno);
        ik.setMid_add(gems.getAdmin_id());
        ik.setGems_fs_id(gems.getFsid());
        ik.setGems_id(gems.getId());
        ik.setPdfstep4_1("");
        ik.setPdf_time("0000-00-00 00:00:00");
        icbc_result ir=new icbc_result();
        icbc icbc;
		if(orderid!=null&&!orderid.equals("")){
        	icbc=newicbcService.findicbcbyorderid(orderid);        	
            if(icbc==null||icbc.equals("")){
            	JSONObject result=new JSONObject();
    			result.put("result","");
    			result.put("msg","������Ŵ���");
    			result.put("code","212");
    			return result.toString();
            }	
        }else{
        	JSONObject result=new JSONObject();
			result.put("result","");
			result.put("msg","orderid����Ϊ��");
			result.put("code","210");
			return result.toString();
        }
		if(kk_car_stateid!=null&&!kk_car_stateid.equals("")){
			ik.setKk_car_stateid(Integer.parseInt(kk_car_stateid));
			ik.setKk_car_cityid(Integer.parseInt(kk_car_cityid));
		}
		if(kk_loan_stateid!=null&&!kk_loan_stateid.equals("")){
			ik.setKk_loan_stateid(Integer.parseInt(kk_loan_stateid));
			ik.setKk_loan_cityid(Integer.parseInt(kk_loan_cityid));
		}
		if(cars_type!=null&&!cars_type.equals("")){
			ik.setCars_type(Integer.parseInt(cars_type));
		}
		if(dz_type!=null&&!dz_type.equals("")){
			ik.setDz_type(Integer.parseInt(dz_type));
		}
		if(sfje!=null&&!sfje.equals("")){
			BigDecimal bd=new BigDecimal(sfje);   
	        //����С��λ������һ��������С��λ�����ڶ���������ȡ�᷽��(��������)   
	        bd=bd.setScale(2,BigDecimal.ROUND_HALF_UP); 
			ik.setSfje(bd);
		}
		if(billingprice!=null&&!billingprice.equals("")){
			BigDecimal bd=new BigDecimal(billingprice);   
	        //����С��λ������һ��������С��λ�����ڶ���������ȡ�᷽��(��������)   
	        bd=bd.setScale(2,BigDecimal.ROUND_HALF_UP); 
			ik.setKp_price(bd);	
		}
		if(loanprice!=null&&!loanprice.equals("")){
			BigDecimal bd=new BigDecimal(loanprice);   
	        //����С��λ������һ��������С��λ�����ڶ���������ȡ�᷽��(��������)   
	        bd=bd.setScale(2,BigDecimal.ROUND_HALF_UP); 
			ik.setDk_price(bd);			
			}
		if(coverprice!=null&&!coverprice.equals("")){
			BigDecimal bd=new BigDecimal(coverprice);   
	        //����С��λ������һ��������С��λ�����ڶ���������ȡ�᷽��(��������)   
	        bd=bd.setScale(2,BigDecimal.ROUND_HALF_UP); 
			ik.setJrfw_price(bd);			
		}
		if(loanallprice!=null&&!loanallprice.equals("")){
			BigDecimal bd=new BigDecimal(loanallprice);   
	        //����С��λ������һ��������С��λ�����ڶ���������ȡ�᷽��(��������)   
	        bd=bd.setScale(2,BigDecimal.ROUND_HALF_UP); 
			ik.setDk_total_price(bd);
		}
		if(mortgagemodel!=null&&!mortgagemodel.equals("")){			
			ik.setAj_type(Integer.parseInt(mortgagemodel));	
		}
		if(mortgageterm!=null&&!mortgageterm.equals("")){			
			ik.setAj_date(mortgageterm);
		}
		if(mortgagebank!=null&&!mortgagebank.equals("")){
			ik.setAj_bank(Integer.parseInt(mortgagebank));
		}
		ik.setKk_kh("");
		if(dk_lv!=null&&!dk_lv.equals("")){
			BigDecimal bd=new BigDecimal(dk_lv);   
	        //����С��λ������һ��������С��λ�����ڶ���������ȡ�᷽��(��������)   
	        bd=bd.setScale(2,BigDecimal.ROUND_HALF_UP); 
			ik.setDk_lv(bd);
		}	
		ik.setImgstep3_1("");
		ik.setImgstep3_2("");
		ik.setImgstep3_3("");
		ik.setImgstep3_4("");
		ik.setImgstep3_5("");
		ik.setImgstep3_6("");
		ik.setImgstep3_7("");
		ik.setImgstep3_7s("");
		BigDecimal bd=new BigDecimal("0");   
        //����С��λ������һ��������С��λ�����ڶ���������ȡ�᷽��(��������)   
        bd=bd.setScale(2,BigDecimal.ROUND_HALF_UP); 
		ik.setPo_ysr(bd);
		ik.setPo_xl(0);
        ik.setPo_xzdz("");
        ik.setPo_yzbm("");
        ik.setPo_gzdw("");
        ik.setPo_dwdz("");
        ik.setPo_zw("");
        ik.setJjlxr_c_name("");
        ik.setJjlxr_c_tel("");
        ik.setJjlxr_jdrgx("");
        ik.setJjlxr_xzdz("");
        ik.setZdr_hyzk(0);
        ik.setZdr_grsr(bd);
        ik.setZdr_jtsr(bd);
        ik.setZdr_xl(0);
        ik.setZdr_jzzk(0);
        ik.setZdr_xzdz("");
        ik.setZdr_yzbm("");
        ik.setZdr_gzdw("");
        ik.setZdr_dwdh("");
        ik.setZdr_dwdz("");
        ik.setZdr_dwxz(0);
        ik.setZdr_sshy(0);
        ik.setZdr_zy(0);
        ik.setZdr_zw(0);
        ik.setZdr_gznx(0);
        ik.setGjr1_dwdz("");
        ik.setGjr1_gzdw("");
        ik.setGjr1_hyzk(0);
        ik.setGjr1_wsdz("");
        ik.setGjr1_xzdz("");
        ik.setGjr1_yzbm("");
        ik.setGjr1_yzdrgx("");
        ik.setGjr2_dwdz("");
        ik.setGjr2_gzdw("");
        ik.setGjr2_hyzk(0);
        ik.setGjr2_wsdz("");
        ik.setGjr2_xzdz("");
        ik.setGjr2_yzbm("");
        ik.setGjr2_yzdrgx("");
        ik.setZdr_wsdz("");
        ik.setPo_wsdz("");
	 icbc_mq icbc_mq =new icbc_mq();	
	 icbc_mq.setBc_status(2);
     icbc_mq.setDt_edit(creditutil.time());
     icbc_mq.setImgstep8_1v("");
     icbc_mq.setImgstep8_2v("");
     icbc_mq.setImgstep8_3v("");
     icbc_mq.setImgstep8_4v("");
     icbc_mq_result icbc_mq_result=new icbc_mq_result();
     icbc_mq_result.setDt_add(creditutil.time());
     icbc_mq_result.setDt_edit(creditutil.time());
     icbc_mq_result.setMid_add(gems.getAdmin_id());
     icbc_mq_result.setMid_edit(gems.getAdmin_id());
     icbc_mq_result.setStatus(2);
	 icbc_dk icbc_dk=new icbc_dk();	 
	 icbc_dk.setDt_edit(creditutil.time());
	 icbc_dk.setMid_edit(gems.getAdmin_id());
	 icbc_dk.setGems_fs_id(gems.getFsid());
	 icbc_dk.setGems_id(gems.getId());
	 icbc_dk.setIcbc_id(icbc.getId());
	 icbc_dk.setQuery_type(2);	
	 icbc_dk.setBc_status(2);
	 icbc_dk.setImgstep4_1ss("");
	 icbc_dk.setImgstep4_2ss("");
	 icbc_dk.setImgstep4_3ss("");
	 icbc_dk.setImgstep4_4ss("");
	 icbc_dk.setImgstep4_5ss("");
	 icbc_dk.setImgstep5_1ss("");
	 icbc_dk.setImgstep6_1ss("");
	 icbc_dk.setImgstep7_1ss("");
	 icbc_kk icbc_kk2=newicbc_kkService.findicbc_kkbyorder(icbc.getId());
	 icbc_dk icbc_dk2=icbc_dkService.findicbc_cardk(icbc.getId());
	 icbc_mq icbc_mq2=icbc_mqService.findmqbyicbc(icbc.getId());
	 icbc_result ir1=new icbc_result();
	    ir1.setDt_add(creditutil.time());
        ir1.setDt_edit(creditutil.time());
        ir1.setMid_add(0);
        ir1.setMid_edit(0);               
        ir1.setStatus(2); 
		    ir.setDt_add(creditutil.time());
	        ir.setDt_edit(creditutil.time());
	        ir.setMid_add(0);
	        ir.setMid_edit(0);               
	        ir.setStatus(2); 
	        ik.setBc_status(2);
	        ik.setDt_edit(creditutil.time());
     if(oredit!=null&&
     		!oredit.equals("")&&
     		oredit.equals("1")){    
    	    if(icbc_kk2==null 
             		&&icbc_dk2==null
         			&&icbc_mq2==null
    	    		){
    	    	JSONObject result=new JSONObject();
     			result.put("result",orderid);
     			result.put("msg","�޿ɱ༭������Ϣ");
     			result.put("code","213");
     			return result.toString();
    	    }
    	   	if(icbc_kk2!=null
         		&&!icbc_kk2.equals("")         	
                ){          		
         		ik.setMid_edit(gems.getAdmin_id());
         		ik.setId(icbc_kk2.getId());
         		newicbc_kkService.upicbc_kk(ik); 
         		ir.setRemark("���±༭��Ϣ");         		
         		ir.setQryid(icbc_kk2.getId());
         		icbc_result1Service.addkk_result(ir);
         	}
         	if(icbc_dk2!=null
         			&&!icbc_dk2.equals("")
                ){ 
        		icbc_dk.setId(icbc_dk2.getId());
         		icbc_dkService.upicbc_cardk(icbc_dk);         		         		
         		ir1.setRemark("���±༭��Ϣ");
         		ir1.setQryid(icbc_dk2.getId());
         		icbc_result1Service.addcardk_result(ir1);
         	}
     	if(icbc_mq2!=null
     			&&!icbc_mq2.equals("")
            ){ 
     		icbc_mq.setMid_edit(gems.getAdmin_id());
    		icbc_mq.setId(icbc_mq2.getId());
    		icbc_mqService.upmq(icbc_mq);	
     		icbc_mq_result.setRemark("���±༭��Ϣ");
     	    icbc_mq_result.setQryid(icbc_mq2.getId());
     		icbc_mq_resultService.addmq_result(icbc_mq_result);
     	}
 		    JSONObject result=new JSONObject();
 		    JSONObject result1=new JSONObject();
			result1.put("orderid",orderid); 			
			result.put("result",result1);
			result.put("msg","�༭�ɹ�");
			result.put("code","200");
			return result.toString();
     }else{  
     	if(icbc_kk2!=null
     			&&!icbc_kk2.equals("")
     			&&icbc_dk2!=null
     			&&!icbc_dk2.equals("")
     			&&icbc_mq2!=null
     			&&!icbc_mq2.equals("")            
     			){
     			JSONObject result=new JSONObject();
         		JSONObject result1=new JSONObject();
     			result1.put("orderid",orderid);
     			result.put("result",result1);
     			result.put("msg","���н�����Ϣ");
     			result.put("code","214");
     			return result.toString();     		
     	   }
     	if(icbc_kk2==null){
    	    ik.setMid_edit(gems.getAdmin_id());
     	    ik.setIcbc_id(icbc.getId());	
     	    ik.setDt_add(creditutil.time());
     	    ik.setQuery_type(2);
			newicbc_kkService.addicbc_kk(ik);
			ir.setRemark("�ύ������Ϣ");
			ir.setQryid(ik.getId());
			icbc_kk ik1=new icbc_kk();
			ik1.setId(ik.getId());
			String order1="KKKCD"+testStringBuilder(7-String.valueOf(ik.getId()).length())+ik.getId();
			ik1.setGems_code(order1);
			newicbc_kkService.upicbc_kk(ik1);      
		    icbc_result1Service.addkk_result(ir);
     		
     	}
     	if(icbc_dk2==null){
     	    icbc_dk.setMid_add(gems.getAdmin_id());
     	    icbc_dk.setDt_add(creditutil.time());
     	    icbc_dkService.addicbc_cardk(icbc_dk);
     	    icbc_dk ic1=new icbc_dk();
            ic1.setId(icbc_dk.getId());
            String order2="DKKCD"+testStringBuilder(7-String.valueOf(icbc_dk.getId()).length())+icbc_dk.getId();
            ic1.setGems_code(order2);
            icbc_dkService.upicbc_cardk(ic1);
            ir1.setRemark("�ύ������Ϣ");
            ir1.setQryid(icbc_dk.getId());
		    icbc_result1Service.addcardk_result(ir1);
     	}
     	if(icbc_mq2==null){
        	icbc_mq.setDt_add(creditutil.time());
        	icbc_mq.setQuery_type(2);
            icbc_mq.setGems_code("MQKCD");
            icbc_mq.setGems_fs_id(gems.getFsid());
            icbc_mq.setGems_id(gems.getId());
            icbc_mq.setMid_add(gems.getAdmin_id());  
            icbc_mq.setMid_edit(gems.getAdmin_id());
            icbc_mq.setIcbc_id(icbc.getId());
            icbc_mqService.savemq(icbc_mq);
            String order3="MQKCD"+testStringBuilder(7-String.valueOf(icbc_mq.getId()).length())+icbc_mq.getId();
            icbc_mq icbc_mq3=new icbc_mq();
            icbc_mq3.setGems_code(order3);
            icbc_mq3.setId(icbc_mq.getId());
            icbc_mqService.upmq(icbc_mq3);
            icbc_mq_result.setRemark("�ύ������Ϣ");
    		icbc_mq_result.setQryid(icbc_mq.getId());
    		icbc_mq_resultService.addmq_result(icbc_mq_result);
     	}
		    JSONObject result=new JSONObject();
 		    JSONObject result1=new JSONObject();
			result1.put("orderid",orderid);
			result.put("result",result1);
			result.put("msg","�ɹ�");
			result.put("code","200");
			return result.toString();
     } 
	}
	 //������ߵķ���
    public static String testStringBuilder(int sl) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i<sl; i++) {
            sb.append(0);
        }
        return sb.toString();        
    }
}
