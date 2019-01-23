package com.controller.icbc;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.model.icbc.icbc;
import com.model.icbc.icbc_kk;
import com.model.icbc.icbc_result;
import com.model.icbc.icbc_zx;
import com.model.jbapi.jbzxapiuser;
import com.model1.gems;
import com.service.icbc.icbc_kkService;
import com.service.zx.jbzxapiuserService;
import com.service1.gemsService;
import com.service1.kjs_icbc.icbc_result1Service;
import com.service1.kjs_icbc.newicbcService;
import com.service1.kjs_icbc.newicbc_kkService;
import com.util.creditutil;

@Controller
public class kkController {
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
	 
	 
	    @RequestMapping(value="/icbcKaik_result.do",produces="text/html;charset=UTF-8")	
		@ResponseBody
		public String icbcKaik_result( 
				HttpServletRequest request,
	            HttpServletResponse response
	            ){
	    	String orderid=request.getParameter("orderid");
	        String appkey=request.getParameter("appkey");
	        gems gems=gemsService.find_api(appkey);
	        icbc icbc=newicbcService.findicbcbyorderid(orderid);
	        if(gems!=null&&!gems.equals("")){        	        
		        if(icbc!=null&&!icbc.equals("")){
		        icbc_kk iKk=newicbc_kkService.findicbc_kkbyorder(icbc.getId());
		        if(iKk!=null&&!iKk.equals("")){
		        	icbc_result icbc_result=icbc_result1Service.kklastfindresult(iKk.getId());
		        	JSONObject result=new JSONObject();
	        		JSONObject result1=new JSONObject();
	        		if(icbc_result!=null&&!icbc_result.equals("")){
	        		result1.put("remark",icbc_result.getRemark());
	        		}
	        		result1.put("status",iKk.getBc_status());
	        		result1.put("kk_kh",iKk.getKk_kh());
	        		result1.put("orderid",orderid);
	        		if(iKk.getPdfstep4_1()!=null&&!iKk.getPdfstep4_1().equals("")){
	        		result1.put("pdffile","http://a.kcway.net/assess/"+iKk.getPdfstep4_1());	
	        		}else{
	        			result1.put("pdffile","");	
	        		}    		
	        		result1.put("kk_orderid",iKk.getGems_code());
	        		result.put("result",result1);		    					    			
	        		result.put("msg","�ɹ�");
	    			result.put("code","200");
	    			return result.toString();	  	
		        }else{
	        		JSONObject result=new JSONObject();
	    			result.put("result",orderid);
	    			result.put("msg","�޿�����Ϣ");
	    			result.put("code","213");
	    			return result.toString();
	        	}
		        }else{
		        	JSONObject result=new JSONObject();
					result.put("result","");
					result.put("msg","������Ŵ���");
					result.put("code","212");
					return result.toString();
		        }
		        }else{
		        	JSONObject result=new JSONObject();
					result.put("result","");
					result.put("msg","appkey����");
					result.put("code","211");
					return result.toString();	
				}
	   }
	 
	 
	/**
	 * ��������
	 * @param visainterview
	 * @param creditcard1
	 * @param creditcard2
	 * @param kkcardfront
	 * @param kkcardopposite
	 * @param sibit
	 * @param doitric
	 * @param addsuppily
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/icbcKaik.do",produces="text/html;charset=UTF-8")	
	@ResponseBody
	public String icbcKaik(		     
            @RequestParam("visainterview") MultipartFile visainterview,//��ǩ��
            @RequestParam("creditcard1") MultipartFile creditcard1,//���ÿ������1
            @RequestParam("creditcard2") MultipartFile creditcard2,//���ÿ������2
            @RequestParam("kkcardfront") MultipartFile kkcardfront,//���֤����
            @RequestParam("kkcardopposite") MultipartFile kkcardopposite,//���֤����
            @RequestParam("sibit") MultipartFile sibit,//ר����ڸ���ҵ����Ϣ�� 
            @RequestParam("doitric") MultipartFile doitric,//����˰�վ������֤�����ļ�
            @RequestParam("addsuppily") MultipartFile[] addsuppily,//�����������
            HttpServletRequest request,
            HttpServletResponse response
            ) throws IOException{
		String orderid=request.getParameter("orderid");
        String appkey=request.getParameter("appkey");
        String name=request.getParameter("name");//����
        String phoneno=request.getParameter("phoneno");//�ֻ���
        String billingprice=request.getParameter("billingprice");//��Ʊ��
        String loanprice=request.getParameter("loanprice");//������
        String coverprice=request.getParameter("coverprice");//�����
        String loanallprice=request.getParameter("loanallprice");//�����ܶ�
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
        Date date = new Date(); 
        String filePath = "/KCDIMG/assess/upload/"+new SimpleDateFormat("yyyy/MM/dd/").format(date);
        String imgpath="upload/"+new SimpleDateFormat("yyyy/MM/dd/").format(date);
        File f = new File(imgpath);
        if(!f.exists()){  
            f.mkdirs();   
        }
        // ��Ӧ�ͻ���  
        response.setContentType("text/html;UTF-8"); 
        //System.out.println(cardfront.getOriginalFilename());
        if (visainterview.isEmpty()
        		||creditcard1.isEmpty()
        		||creditcard2.isEmpty()
        		||kkcardfront.isEmpty()
                ||kkcardopposite.isEmpty()
                ||doitric.isEmpty()
                ||sibit.isEmpty()
        		) {
        	JSONObject result=new JSONObject();
			result.put("result","");
			result.put("msg","��Ƭ����Ϊ��");
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
		//��ǩ��
        if (!visainterview.isEmpty()&&visainterview.getSize()>0) {  
    	    String filename = visainterview.getOriginalFilename();
    	    String prefix=filename.substring(filename.lastIndexOf(".")+1);
    	    UUID uuid = UUID.randomUUID();
    	    String uuidname=uuid.toString().replaceAll("-","")+"."+prefix;        	           
    		String path = filePath+uuidname;
            byte[] file36Byte = visainterview.getBytes();
            FileUtils.writeByteArrayToFile(new File(path),file36Byte);
            ik.setImgstep3_1(imgpath+uuidname);
        }
        //���ÿ������1
        if (!creditcard1.isEmpty()&&creditcard1.getSize()>0) {  
    	    String filename = creditcard1.getOriginalFilename();
    	    String prefix=filename.substring(filename.lastIndexOf(".")+1);
    	    UUID uuid = UUID.randomUUID();
    	    String uuidname=uuid.toString().replaceAll("-","")+"."+prefix;        	           
    		String path = filePath+uuidname;
    		byte[] file36Byte = creditcard1.getBytes();
            FileUtils.writeByteArrayToFile(new File(path),file36Byte);
            ik.setImgstep3_2(imgpath+uuidname);
        }
        //���ÿ������2
        if (!creditcard2.isEmpty()&&creditcard2.getSize()>0) {  
    	    String filename = creditcard2.getOriginalFilename();
    	    String prefix=filename.substring(filename.lastIndexOf(".")+1);
    	    UUID uuid = UUID.randomUUID();
    	    String uuidname=uuid.toString().replaceAll("-","")+"."+prefix;        	           
    		String path = filePath+uuidname;
            byte[] file36Byte = creditcard2.getBytes();
            FileUtils.writeByteArrayToFile(new File(path),file36Byte);
            ik.setImgstep3_3(imgpath+uuidname);
        }
        //���֤����
        if (!kkcardfront.isEmpty()&&kkcardfront.getSize()>0) {  
    	    String filename = kkcardfront.getOriginalFilename();
    	    String prefix=filename.substring(filename.lastIndexOf(".")+1);
    	    UUID uuid = UUID.randomUUID();
    	    String uuidname=uuid.toString().replaceAll("-","")+"."+prefix;        	           
    		String path = filePath+uuidname;
            byte[] file36Byte = kkcardfront.getBytes();
            FileUtils.writeByteArrayToFile(new File(path),file36Byte);
            ik.setImgstep3_4(imgpath+uuidname);
        }        
        //���֤����
        if (!kkcardopposite.isEmpty()&&kkcardopposite.getSize()>0) {  
    	    String filename = kkcardopposite.getOriginalFilename();
    	    String prefix=filename.substring(filename.lastIndexOf(".")+1);
    	    UUID uuid = UUID.randomUUID();
    	    String uuidname=uuid.toString().replaceAll("-","")+"."+prefix;        	           
    		String path = filePath+uuidname;
            byte[] file36Byte = kkcardopposite.getBytes();
            FileUtils.writeByteArrayToFile(new File(path),file36Byte);
            ik.setImgstep3_5(imgpath+uuidname);
        }
        //�������Ϣ��
        if (!sibit.isEmpty()&&sibit.getSize()>0) {  
    	    String filename = sibit.getOriginalFilename();
    	    String prefix=filename.substring(filename.lastIndexOf(".")+1);
    	    UUID uuid = UUID.randomUUID();
    	    String uuidname=uuid.toString().replaceAll("-","")+"."+prefix;        	           
    		String path = filePath+uuidname;
            byte[] file36Byte = sibit.getBytes();
            FileUtils.writeByteArrayToFile(new File(path),file36Byte);
            ik.setImgstep3_6(imgpath+uuidname);
        }
        //����˰�վ������֤�����ļ�
        if (!doitric.isEmpty()&&doitric.getSize()>0) {  
    	    String filename = doitric.getOriginalFilename();
    	    String prefix=filename.substring(filename.lastIndexOf(".")+1);
    	    UUID uuid = UUID.randomUUID();
    	    String uuidname=uuid.toString().replaceAll("-","")+"."+prefix;        	           
    		String path = filePath+uuidname;
            byte[] file36Byte = doitric.getBytes();
            FileUtils.writeByteArrayToFile(new File(path),file36Byte);
            ik.setImgstep3_7(imgpath+uuidname);
        }
        //�����������
        if (addsuppily!=null&&addsuppily.length>0) {          	
        	String imgs ="";
        	for(int i=0;i<addsuppily.length;i++){
        		MultipartFile file=addsuppily[i];
        		if(file.getSize()>0){
        		String filename = file.getOriginalFilename();
        	    String prefix=filename.substring(filename.lastIndexOf(".")+1);
        	    UUID uuid = UUID.randomUUID();
        	    String uuidname=uuid.toString().replaceAll("-","")+"."+prefix;        	           
        		String path = filePath+uuidname;
                byte[] file36Byte = file.getBytes();
                FileUtils.writeByteArrayToFile(new File(path),file36Byte);
                // System.out.println(imgpath+"addsuppily/"+creditutil.timefile()+"/"+UUIDTool.getUUID()+i+"."+prefix);
                imgs=imgs+""+imgpath+uuidname;  
        	} } 
        	ik.setImgstep3_7s(imgs);
        }
//        icbc1.setBc_status(2); 
//        icbc1.setDt_edit(creditutil.time());
//        icbcservice.upicbc(icbc1);
        icbc_kk icbc_kkmap = newicbc_kkService.findicbc_kkbyorder(icbc.getId());
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
        	if(icbc_kkmap!=null&&!icbc_kkmap.equals("")){        		
        		ik.setMid_edit(gems.getAdmin_id());
        		ik.setId(icbc_kkmap.getId());
        		newicbc_kkService.upicbc_kk(ik); 
        		ir.setRemark("�༭������Ϣ");
        		ir.setQryid(icbc_kkmap.getId());
        		icbc_result1Service.addkk_result(ir);
        		JSONObject result=new JSONObject();
        		JSONObject result1=new JSONObject();
    			result1.put("orderid",orderid);
    			result1.put("kk_orderid",icbc_kkmap.getGems_code());
    			result.put("result",result1);
    			result.put("msg","�༭�ɹ�");
    			result.put("code","200");
    			return result.toString();
        	}else {
        		JSONObject result=new JSONObject();
    			result.put("result",orderid);
    			result.put("msg","�޿ɱ༭������Ϣ");
    			result.put("code","213");
    			return result.toString();
			}
        }else{        	
        	if(icbc_kkmap!=null&&!icbc_kkmap.equals("")){
        		JSONObject result=new JSONObject();
        		JSONObject result1=new JSONObject();
    			result1.put("orderid",orderid);
    			result1.put("kk_orderid",icbc_kkmap.getGems_code());
    			result.put("result",result1);
    			result.put("msg","���п�����Ϣ");
    			result.put("code","214");
    			return result.toString();
        	}
        	ik.setMid_edit(gems.getAdmin_id());
        	ik.setIcbc_id(icbc.getId());	
        	ik.setDt_add(creditutil.time());
        	ik.setQuery_type(2);
			newicbc_kkService.addicbc_kk(ik);
			ir.setRemark("�ύ����������Ϣ");
			ir.setQryid(ik.getId());
			icbc_kk ik1=new icbc_kk();
			ik1.setId(ik.getId());
			String order="KKKCD"+testStringBuilder(7-String.valueOf(ik.getId()).length())+ik.getId();
			ik1.setGems_code(order);
			newicbc_kkService.upicbc_kk(ik1);      
		    icbc_result1Service.addkk_result(ir);
		    JSONObject result=new JSONObject();
    		JSONObject result1=new JSONObject();
			result1.put("orderid",orderid);
			result1.put("kk_orderid",order);
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
