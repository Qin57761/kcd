package com.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.http.duoying.syncjkrxxHttp;
import com.mashape.unirest.http.JsonNode;
import com.util.creditutil;
import com.util.jsonutil;
import com.util.pathutil;
import com.util.pdfreader;
import com.util.duoying.syncutil;



public class a {
	
	public static void traverseFolder2(String path) {

        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                System.out.println("�ļ����ǿյ�!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        System.out.println("�ļ���:" + file2.getAbsolutePath());
                        traverseFolder2(file2.getAbsolutePath());
                    } else {
                        System.out.println("�ļ�:" + file2.getAbsolutePath());
                    }
                }
            }
        } else {
            System.out.println("�ļ�������!");
        }
    }
	
	
	    int aaa=3652;  
	    int getdata(CALL call){  
	            call.get_in(aaa);  
	             return 1;  
	            }  
	    
	    interface CALL {  
	        void  get_in(int abc);  
	      

         }  
	    
	    
	    public static void main(String[] args) {
	    	//a.traverseFolder2("C:/Users/Administrator/Desktop/1/json.txt");
	    	 File file = new File("C:/Users/Administrator/Desktop/1/2");
	         if (file.exists()) {
	             File[] files = file.listFiles();
	             if (files.length == 0) {
	                 System.out.println("�ļ����ǿյ�!");
	                 return;
	             } else {
	                 for (File file2 : files) {
	                     if (file2.isDirectory()) {
	                         System.out.println("�ļ���:" + file2.getAbsolutePath());
	                     
	                     } else {
	                    	 
	                    		String s1=syncutil.getJson(file2.getAbsolutePath().toString());
		                     	net.sf.json.JSONObject js=jsonutil.toJSONObject(s1.toString());    
		                     	net.sf.json.JSONObject data1=(net.sf.json.JSONObject) js.get("data");
		                     	System.out.println("sign:---"+js.get("sign"));
		                         //��������
		                     	  List<JSONObject> loanInfoList=new ArrayList<JSONObject>();
		                     	  loanInfoList=(List<JSONObject>) data1.get("loanInfoList");
		                     	    	if(null!=loanInfoList&&loanInfoList.size()>0){
		                     	    	int pointsDataLimit=10;//��������
		                     	    	Integer size = loanInfoList.size();
		                     	    	//�ж��Ƿ��б�Ҫ����
		                     	    	if(pointsDataLimit<size){
		                     	    	int part = size/pointsDataLimit;//������
		                     	    	System.out.println("���� �� "+size+"������"+" ��Ϊ ��"+part+"��");
		                     	    	//
		                     	    	for (int i = 0; i < part; i++) {
		                     	    	//1000��
		                     	         List<JSONObject> listPage = loanInfoList.subList(0, pointsDataLimit);
		                     	    	 JSONObject data =new JSONObject();
		                     	    	 data.put("loanInfoList",listPage); 
		                     	    	// System.out.println("data��"+data);
		                     		     //System.out.println("С��100������:"+loanInfoList.size());
		                     		 	 JSONObject obj=syncutil.createHead(data); 
		                     	       	 obj.put("data",data); // ���ݵĲ���  
		                                 String s=syncjkrxxHttp.dyhttp("http://abs.51duoying.com:8082/ws/services/rest/loan/addLoanInfo",obj.toString());				    		         	      
		                     	         System.out.println(s+"------"); 
		                     	    	 //�޳�
		                     	         loanInfoList.subList(0, pointsDataLimit).clear();
		                     	         }
		                     	    if(!loanInfoList.isEmpty()){
		                     	    	 JSONObject data =new JSONObject();
		                     	    	 data.put("loanInfoList", loanInfoList); 
		                     	    	// System.out.println("data��"+data);
		                     		     //System.out.println("С��100������:"+loanInfoList.size());
		                     		 	 JSONObject obj=syncutil.createHead(data); 
		                     	       	 obj.put("data",data); // ���ݵĲ���  
		                     	       	String s=syncjkrxxHttp.dyhttp("http://abs.51duoying.com:8082/ws/services/rest/loan/addLoanInfo",obj.toString());				    		         	      
		                     	       System.out.println(s); 
		                     	    }
		                     	   }else{
		                     	    	 JSONObject data =new JSONObject();
		                     	    	 data.put("loanInfoList", loanInfoList); 
		                     	    	// System.out.println("data��"+data);
		                     		     //System.out.println("С��100������:"+loanInfoList.size());
		                     		 	 JSONObject obj=syncutil.createHead(data); 
		                     	       	 obj.put("data",data); // ���ݵĲ���  
		                                //JsonNode s=syncjkrxxHttp.dyhttp("http://abs.51duoying.com:8082/ws/services/rest/loan/addLoanInfo",obj.toString());				    		         	      
		                     	       System.out.println("�µ�sign:"+obj.get("sign")); 
		                     	    }
		                     	    	}else{
		                     	  System.out.println("û������!!!");
		                     	   }
		                         
		                         
		                         
	                         //System.out.println("�ļ�:" + file2.getAbsolutePath());
	                     }
	                 }
	             }
	         } else {
	             System.out.println("�ļ�������!");
	         }   
	
	    	
	    	
	    
	    	
	    	
	    	
	    	
	    	
//	    String	pdfFile = "C:/Users/Administrator/Desktop/1/�䵱200.pdf";
//	    int j=1;
//	    for(int i=1;i<=2;i=i+2){
//	    UUID randomUUID = UUID.randomUUID();
//	    System.out.println(randomUUID.toString().replaceAll("-","").length());
//	   	String uuidName =creditutil.timefile()+randomUUID.toString().replaceAll("-","").substring(0,8);
//	   
//	   	String	newFile ="C:/Users/Administrator/Desktop/1/"+uuidName+".pdf";
//	   	pdfreader.partitionPdfFile(pdfFile,newFile,i,i+1);  
//	   	
//	    System.out.println("��"+j+"��"+uuidName);
//	    j=j+1;
//	    }

//	       //System.out.println(result.toString());
//	        net.sf.json.JSONObject j=jsonutil.toJSONObject(result.toString());
//	       System.out.println(j.get("basicInfo"));
//	    		    	
//	    	String jsonstring="{'name':'ssss','ID_card':'ssss'}";
//	   	 jsonstring="["+jsonstring+"]";
//     	 List l=jsonutil.toList(jsonstring,ykdutil.class);
//     	 for(int i=0;i<l.size();i++){
//     		ykdutil y=new ykdutil();
//     		y=(ykdutil) l.get(i);
//     		System.out.println(y.getName());
//     	 }
//	    String str="[{111111111}]";
//	    System.out.println(str.substring(1,str.length()-1));
//	    Date currentTime = new Date();
//	    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//	    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	    Date date;
//		try {
//			date = fmt.parse("2017-11-12 12:12:20");
//		    String dateString = formatter.format(date);			   
//		    System.out.println(dateString);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}


	   
//	    	String imgname="sadas.jpg";
//	    	 String path="/opt/javaimg/image/upload/img/1111/"+imgname;	 
//	    	 String imgpath;
//	    	 if(imgname.indexOf(".new.jpg") > 0 )
//	    	 {
//	    		 imgpath=path;
//	    	 }else{
//	    		 imgpath=path+".new.jpg";
//	    	 }
//		   System.out.println(imgpath);
		}
	}  
	   


	    
	    
	    
	    

