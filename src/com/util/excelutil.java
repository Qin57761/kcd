package com.util;
import java.io.FileOutputStream;  
import java.util.ArrayList;  
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;  
import org.apache.poi.hssf.usermodel.HSSFCellStyle;  
import org.apache.poi.hssf.usermodel.HSSFRow;  
import org.apache.poi.hssf.usermodel.HSSFSheet;  
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import com.model.yw;  
  
public class excelutil  
{  
	 public static void toexcel(List list){		 		  
	        // ��һ��������һ��webbook����Ӧһ��Excel�ļ�  
	        HSSFWorkbook wb = new HSSFWorkbook();  
	        // �ڶ�������webbook�����һ��sheet,��ӦExcel�ļ��е�sheet  
	        HSSFSheet sheet = wb.createSheet("ҵ���");  
	        // ����������sheet����ӱ�ͷ��0��,ע���ϰ汾poi��Excel����������������short  
	        HSSFRow row = sheet.createRow(0);  
	        // ���Ĳ���������Ԫ�񣬲�����ֵ��ͷ ���ñ�ͷ����  
	        HSSFCellStyle style = wb.createCellStyle();  
	        style.setAlignment(CellStyle.ALIGN_CENTER); // ����һ�����и�ʽ  
	  
	        HSSFCell cell = row.createCell((short) 0);        
	        cell.setCellValue("���");  
	        cell.setCellStyle(style);  
	       
	        cell = row.createCell((short) 1);         
	        cell.setCellValue("����ѯ������");  
	        cell.setCellStyle(style);  
	       
	        cell = row.createCell((short) 2);         
	        cell.setCellValue("������");  
	        cell.setCellStyle(style);  
	       
	        cell = row.createCell((short) 3);        
	        cell.setCellValue("�����˵ȼ�");  
	        cell.setCellStyle(style);  
	       
	        cell = row.createCell((short) 4);          
	        cell.setCellValue("�����������ŵ�");  
	        cell.setCellStyle(style);  
	       
	        cell = row.createCell((short) 5);          
	        cell.setCellValue("����״̬");  
	        cell.setCellStyle(style);  
	       
	        cell = row.createCell((short) 6);          
	        cell.setCellValue("�������");  
	        cell.setCellStyle(style);  
	       
	        cell = row.createCell((short) 7);        
	        cell.setCellValue("�������");  
	        cell.setCellStyle(style);  
	       
	        cell = row.createCell((short) 8);        
	        cell.setCellValue("����ʱ��");  
	        cell.setCellStyle(style);  

	      
	        // ���岽��д��ʵ������ ʵ��Ӧ������Щ���ݴ����ݿ�õ���  	         
	        yw yw=new yw();
	        for (int i = 0; i < list.size(); i++)  
	        {   
	        	row = sheet.createRow(i + 1);  
	            yw =(yw) list.get(i);  
	            // ���Ĳ���������Ԫ�񣬲�����ֵ  
	            row.createCell((short) 0).setCellValue(yw.getId()); 
	            row.createCell((short) 1).setCellValue(yw.getSname());  
	            row.createCell((short) 2).setCellValue(yw.getCzr());  
	            row.createCell((short) 3).setCellValue(yw.getCzrdj());
	            row.createCell((short) 4).setCellValue(yw.getCzrssmd());  
	            row.createCell((short) 5).setCellValue(yw.getCzzt());  
	            row.createCell((short) 6).setCellValue(yw.getOrderid());             
	            row.createCell((short) 7).setCellValue(yw.getSxkd());  
	            row.createCell((short) 8).setCellValue(yw.getCztime());                       
	        }  
	        // �����������ļ��浽ָ��λ��  
	        try  
	        {  
	            FileOutputStream fout = new FileOutputStream("C:/Users/Administrator/Desktop/1/yw.xls");  
	            wb.write(fout);  
	            fout.close();  
	        }  
	        catch (Exception e)  
	        {  
	            e.printStackTrace();  
	        }  
	    
		 
	 }
      
    /** 
     * @���ܣ��ֹ�����һ���򵥸�ʽ��Excel 
     */  
    private static List<yw> getStudent() throws Exception  
    {  
        List list = new ArrayList();         
      
        yw y1=new yw();
        y1.setId(1);
        y1.setCzr("1");
        y1.setCzrdj("1");
        y1.setCzrssmd("1");
        y1.setCztime(creditutil.time());
        y1.setCzzt("1");
        y1.setOrderid("1");
        y1.setSname("1");
        y1.setSxkd("1");
      
        yw y2=new yw();
        y2.setId(1);
        y2.setCzr("2");
        y2.setCzrdj("2");
        y2.setCzrssmd("2");
        y2.setCztime(creditutil.time());
        y2.setCzzt("2");
        y2.setOrderid("2");
        y2.setSname("2");
        y2.setSxkd("2");
       
        yw y3=new yw();
        y3.setId(3);
        y3.setCzr("3");
        y3.setCzrdj("3");
        y3.setCzrssmd("3");
        y3.setCztime(creditutil.time());
        y3.setCzzt("3");
        y3.setOrderid("3");
        y3.setSname("3");
        y3.setSxkd("3");
        
        list.add(y1);
        list.add(y2);
        list.add(y3);
        
        return list;  
    }  
  
    public static void main(String[] args) throws Exception  
    {
    	List list = new ArrayList();         
        
        yw y1=new yw();
        y1.setId(1);
        y1.setCzr("1");
        y1.setCzrdj("1");
        y1.setCzrssmd("1");
        y1.setCztime(creditutil.time());
        y1.setCzzt("1");
        y1.setOrderid("1");
        y1.setSname("1");
        y1.setSxkd("1");
      
        yw y2=new yw();
        y2.setId(1);
        y2.setCzr("2");
        y2.setCzrdj("2");
        y2.setCzrssmd("2");
        y2.setCztime(creditutil.time());
        y2.setCzzt("2");
        y2.setOrderid("2");
        y2.setSname("2");
        y2.setSxkd("2");
       
        yw y3=new yw();
        y3.setId(3);
        y3.setCzr("3");
        y3.setCzrdj("3");
        y3.setCzrssmd("3");
        y3.setCztime(creditutil.time());
        y3.setCzzt("3");
        y3.setOrderid("3");
        y3.setSname("3");
        y3.setSxkd("3");
        
        list.add(y1);
        list.add(y2);
        list.add(y3);
    	
        toexcel(list);
    	
    	
    }  
}  
