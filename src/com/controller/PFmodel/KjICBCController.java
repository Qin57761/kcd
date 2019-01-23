package com.controller.PFmodel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * ������ 
 * ������: ���� �����ݽ���
 * ������: ����
 */
@Controller
@RequestMapping("/kj")
public class KjICBCController{	
	/**�������ݽ���
	 * @param customer
	 * @param s1
	 * @Description: TODO
	 * @param name
	 * @return 
	 */
	public void icbc(Customer customer,String s1){
		 if(s1!=null && !s1.equals("")){
			int[] arr =new int[2];
			arr[0]=s1.indexOf("����");
			if(arr[0]==-1){//�����ڸ��˵Ľڵ�
				return;
			}
			arr[1]=s1.indexOf("��ż");//��ż�Ľڵ�  ���ﻹ�п��ܳ������������˵Ľڵ� ������Ŀǰû�п��������г��������˵ģ�����ȷ���ڵ�Ĺؼ��� 
			int end=aji(arr,arr[0]);
			if(end==arr[0]){
				end=s1.length();
			}
			String[] s3=s1.substring(arr[0],end).replaceAll("[\\��\\,\\Ԫ\\��\\��]",";").replaceAll("[\\��]",":").split(";");//ͳһ������
			String sss="";
			for(int j=0;j<s3.length;j++){
					sss=s3[j];
					if(sss.indexOf("�ѻ�")!=-1){
						customer.setIs_marital_status("1");
					}
					int i0=sss.indexOf("δ����������");
					if(i0!=-1){
						String ss0_=to(sss,8,i0);
						customer.setUncleared_number(add(customer.getUncleared_number(),ss0_));
						continue;
					}
					int i1=sss.indexOf("���ÿ�����:����");
					if(i1!=-1){
						customer.setCount_credit(to(sss,9,i1));
						continue;
					}
					int i2=sss.indexOf("�����ܶ�");
					if(i2!=-1){
						customer.setMax_credit(to(sss,5,i2));
						continue;
					}
					int i6=sss.indexOf("����������ڽ��");
					if(i6!=-1){
						String ss6_=to(sss,9,i6);
						customer.setHighest_overdue(max(customer.getHighest_overdue(),ss6_));
						continue;
					}
					int i7=sss.indexOf("δ����������");
					if(i7!=-1){
						String ss7_=to(sss,8,i7);
						customer.setUncleared_monty(add(customer.getUncleared_monty(),ss7_));
						continue;
					}				
				}
		}	
	}
	/**���һ�����������д���ĳ���������е���Сֵ ���������ݽڵ�Ĵ���
	 * @param arr int����
	 * @param i Ҫ�Ƚϵ�ֵ
	 * @return
	 * @Description: TODO
	 * @param name
	 * @return 
	 */
	public static int aji(int[] arr,int i){
		int i1=i;//��һ��ֵ
		int i2;//���ֵ
		for(int j=0;j<arr.length;j++){
			if(arr[j]>i){//���ڻ���ֵ
				i2=arr[j];//��maxֵ
				if(i1>i2 || i==i1){//��һ�ν��� i=i1<i2  ���԰�i2��ֵ��i1���Ժ�i<i1 �ڶ��ν��� �����i1>i2>i�������԰�i2��ֵ��i1�����򲻱�
					i1=i2;
				}
			}
		}
		return i1;
	}
	//:��
	public static String to(String s,int i,int ii){
		String s1="";
		if(s.substring(ii).indexOf(":")==-1){
			i-=1;
		}
		s1=s.substring(ii+i);
		if(s1.equals("")){
			s1="0";
		}
		return s1;
	}
	//���
	public static String add(String s1,String s2){
		int i2=0;
		try {
			i2=Integer.parseInt(s2);
		} catch (NumberFormatException e) {
		}
		return Integer.parseInt(s1)+i2+"";
	}
	//������������� 
	public static String max(String s1,String s2){
		int i2=0;
		try {
			i2=Integer.parseInt(s2);
		} catch (NumberFormatException e) {
		}
		if(i2>Integer.parseInt(s1)){
			return i2+"";
		}else{
			return s1;
		}
	}
	  //�����������ֶν���
	  public String  bigdate(Customer customer,String ss0){ 
		  if(ss0!=null && !ss0.equals("")){
	    	  JSONArray  ja1=JSONObject.fromObject(ss0).getJSONArray("risk_items");//������еķ�����
	    	  if(ja1!=null){////ɨ������ķ����Ϊ��
	    		  for(int i=0;i<ja1.size();i++){//�������еķ�����
	    			  JSONObject jo=ja1.getJSONObject(i);//������������
	    			  JSONObject jo2;
	    			  try {
	    				  jo2=jo.getJSONObject("item_detail");//�������
	    				  String s=jo.getString("item_id");
	    				  if(s.equals("3393158")){
	        				  customer.setSeven_days(jo2.getString("platform_count"));  
	        				 	continue;
	         			  }else if(s.equals("3393160")){
	        				   customer.setOne_month(jo2.getString("platform_count"));
	        				   	continue;
	         			  }else if(s.equals("3393162")){
	           				   customer.setThree_month(jo2.getString("platform_count"));
	           				   continue;
	         			  }else if(s.equals("3393164")){
	    	   				   customer.setSix_month(jo2.getString("platform_count"));
	    	   				   continue;
	        			  }else if(s.equals("3393166")){ 
	    	   				  customer.setTwelve_month(jo2.getString("platform_count"));
	    	   				  continue;
	        			  }else if(s.equals("3392960")){ // ��Ժִ�� ʧ����
			   				   JSONArray ja2=(JSONArray) jo2.get("namelist_hit_details");//�������������б� ���ع�ע����������������ģ�����������������е�һ�ֻ���
			   				   for(int j=0;j<ja2.size();j++){
			   					JSONArray ja3=ja2.getJSONObject(j).getJSONArray("court_details");//��Ժ�����б�
			   					for(int z=0;z<ja3.size();z++){//����
			   						JSONObject jo3=(JSONObject)ja3.get(z);//��Ժ����
			   						String s0=jo3.get("fraud_type").toString();//��թ����  "��Ժʧ��"��"��Ժִ��"��"��Ժ�᰸"�е�һ��
			   						String s1=jo3.getString("situation").toString();//���
			   						if(s0.equals("��Ժʧ��")){//����ʧ�Ź�
			   							customer.setIs_credit("1");
			   							continue;
			   						}
			   						if(s0.equals("��Ժִ��") || s0.equals("��Ժ�᰸")){
			   							customer.setCourt_execution("1");
			   							continue;
			   						}
			   					  }
			   				    }
			    			 }	
	    				  	 continue;
						} catch (Exception e) {
							// TODO: handle exception
							continue;
						}	
		    		 }
		    	}
		  }
		  return Demo_TaiRA.fun(customer);
    	}
	public static void main(String[] args){
		String s="���˷���ɸ����:ͨ��;;������Ϣ::�ѻ������ܶ�115000Ԫ���ö��43519Ԫ��������ʹ��33367Ԫ����2010.2.8--2030.2.8151000Ԫ�»�913Ԫ�������104377Ԫ��141�����Ѵ���2017.7.9--2020.2.24240000Ԫ�»�Ӧ1123Ԫ����ʵ��5170Ԫ";
		/*
		���ݶ���id��ѯ������ ���ؽ��
		RiskServicePreloan r=new RiskServicePreloan();
		String s1=r.query("ER20180703105348488FC4DC").toJSONString();*/
		String s1="{'final_score':27,'credit_score':503,'risk_items':[{'risk_level':'low','item_detail':{'frequency_detail_list':[{'detail':'3�������֤������ͥ��ַ����0'},{'data':['13883911275','138��������1275'],'detail':'3����_���֤_�ֻ�����_��������_ȫ�֣�2'}],'type':'frequency_detail'},'item_id':3393094,'item_name':'3���������֤�������������Ϣ','group':'�ͻ���Ϊ���'},{'risk_level':'low','item_detail':{'frequency_detail_list':[{'detail':'7����_���֤_���ִ���_��Ӧ�ã�3'}],'type':'frequency_detail'},'item_id':3393120,'item_name':'7�����豸�����֤���ֻ��������������','group':'�ͻ���Ϊ���'},{'risk_level':'high','item_detail':{'platform_detail_dimension':[{'count':1,'detail':['С����˾:1'],'dimension':'������ֻ�����'},{'count':2,'detail':['С����˾:1','������������:1'],'dimension':'��������֤����'}],'platform_detail':['С����˾:1','������������:1'],'platform_count':2,'type':'platform_detail'},'item_id':3393158,'item_name':'7�����������ڶ��ƽ̨������','group':'��ƽ̨���������'},{'risk_level':'medium','item_detail':{'platform_detail_dimension':[{'count':1,'detail':['С����˾:1'],'dimension':'������ֻ�����'},{'count':2,'detail':['С����˾:1','������������:1'],'dimension':'��������֤����'}],'platform_detail':['С����˾:1','������������:1'],'platform_count':2,'type':'platform_detail'},'item_id':3393160,'item_name':'1�������������ڶ��ƽ̨������','group':'��ƽ̨���������'},{'risk_level':'medium','item_detail':{'platform_detail_dimension':[{'count':1,'detail':['С����˾:1'],'dimension':'������ֻ�����'},{'count':2,'detail':['С����˾:1','������������:1'],'dimension':'��������֤����'}],'platform_detail':['С����˾:1','������������:1'],'platform_count':2,'type':'platform_detail'},'item_id':3393162,'item_name':'3�������������ڶ��ƽ̨������','group':'��ƽ̨���������'},{'risk_level':'medium','item_detail':{'platform_detail_dimension':[{'count':1,'detail':['С����˾:1'],'dimension':'������ֻ�����'},{'count':2,'detail':['С����˾:1','������������:1'],'dimension':'��������֤����'}],'platform_detail':['С����˾:1','������������:1'],'platform_count':2,'type':'platform_detail'},'item_id':3393164,'item_name':'6�������������ڶ��ƽ̨������','group':'��ƽ̨���������'},{'risk_level':'low','item_detail':{'platform_detail_dimension':[{'count':1,'detail':['С����˾:1'],'dimension':'������ֻ�����'},{'count':2,'detail':['С����˾:1','������������:1'],'dimension':'��������֤����'}],'platform_detail':['С����˾:1','������������:1'],'platform_count':2,'type':'platform_detail'},'item_id':3393166,'item_name':'12�������������ڶ��ƽ̨������','group':'��ƽ̨���������'},{'risk_level':'low','item_detail':{'platform_detail_dimension':[{'count':1,'detail':['С����˾:1'],'dimension':'������ֻ�����'},{'count':2,'detail':['С����˾:1','������������:1'],'dimension':'��������֤����'}],'platform_detail':['С����˾:1','������������:1'],'platform_count':2,'type':'platform_detail'},'item_id':3393168,'item_name':'18�������������ڶ��ƽ̨������','group':'��ƽ̨���������'},{'risk_level':'low','item_detail':{'platform_detail_dimension':[{'count':1,'detail':['С����˾:1'],'dimension':'������ֻ�����'},{'count':2,'detail':['С����˾:1','������������:1'],'dimension':'��������֤����'}],'platform_detail':['С����˾:1','������������:1'],'platform_count':2,'type':'platform_detail'},'item_id':3393170,'item_name':'24�������������ڶ��ƽ̨������','group':'��ƽ̨���������'},{'risk_level':'low','item_detail':{'platform_detail_dimension':[{'count':1,'detail':['С����˾:1'],'dimension':'������ֻ�����'},{'count':2,'detail':['С����˾:1','������������:1'],'dimension':'��������֤����'}],'platform_detail':['С����˾:1','������������:1'],'platform_count':2,'type':'platform_detail'},'item_id':3393172,'item_name':'��60���������������ڶ��ƽ̨������','group':'��ƽ̨���������'},{'risk_level':'medium','item_detail':{'frequency_detail_list':[{'detail':'1Сʱ��_�ֻ�����_���ִ���_��Ӧ�ã�3'}],'type':'frequency_detail'},'item_id':3393064,'item_name':'1Сʱ�����֤���ֻ�������������ڵ���3','group':'�ͻ���Ϊ���'}],'address_detect':{'mobile_address':'������','id_card_address':'�Ĵ�ʡ����������'},'final_decision':'Review','report_time':1530586428000,'success':true,'report_id':'ER20180703105348488FC4DC','apply_time':1530586428000,'application_id':'1807031053486878DC19ABCF52C14C86'}";
		KjICBCController kc=new KjICBCController();
		String defaultvalue="0";
		Customer c=new Customer();
		kc.icbc(c, s);
		try {
			defaultvalue=JSONObject.fromObject(kc.bigdate(c,s1)).toString();
		} catch (Exception e) {
			//jsonת���쳣 ���������һЩ...
		}
		System.out.println("������"+defaultvalue);
	} 
}