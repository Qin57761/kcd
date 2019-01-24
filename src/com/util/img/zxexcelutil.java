package com.util.img;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;

import com.model.index.zxexcel;

public class zxexcelutil {
    /** 
     * ����·������ 
     * @param trackingList 
     * @param response 
     * @throws Exception 
     * @date 20**** 
     * @author *** 
     */  

	public static void toxxexcel(List<zxexcel> zxlist,  
            HttpServletResponse response) throws Exception{  		  
        // ��һ��������һ��webbook����Ӧһ��Excel�ļ�  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // �ڶ�������webbook�����һ��sheet,��ӦExcel�ļ��е�sheet  
        HSSFSheet sheet = wb.createSheet("worksheet"); 
      
        // ����������sheet����ӱ�ͷ��0��,ע���ϰ汾poi��Excel����������������short  
        HSSFRow row = sheet.createRow(0);  
        // ���Ĳ���������Ԫ�񣬲�����ֵ��ͷ ���ñ�ͷ����  
        HSSFCellStyle style = wb.createCellStyle();
        
        style.setAlignment(CellStyle.ALIGN_CENTER); // ����һ�����и�ʽ  
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//��ֱ����
        style.setBorderBottom(CellStyle.BORDER_THIN); //�±߿�
        style.setBorderLeft(CellStyle.BORDER_THIN);//��߿�
        style.setBorderTop(CellStyle.BORDER_THIN);//�ϱ߿�
        style.setBorderRight(CellStyle.BORDER_THIN);//�ұ߿�        
        HSSFCell cell = row.createCell((short) 0);        
        cell.setCellValue("������Ȩ����");  
        cell.setCellStyle(style);  
       
        cell = row.createCell((short) 1);         
        cell.setCellValue("֤������");  
        cell.setCellStyle(style);  
       
        cell = row.createCell((short) 2);         
        cell.setCellValue("����");  
        cell.setCellStyle(style);  
       
        cell = row.createCell((short) 3);        
        cell.setCellValue("�Ա�");  
        cell.setCellStyle(style);  
       
        cell = row.createCell((short) 4);          
        cell.setCellValue("����");  
        cell.setCellStyle(style);  
       
        cell = row.createCell((short) 5);          
        cell.setCellValue("ͨѶ��ַ");  
        cell.setCellStyle(style);  
       
        cell = row.createCell((short) 6);          
        cell.setCellValue("���֤����");  
        cell.setCellStyle(style);  
       
        cell = row.createCell((short) 7);        
        cell.setCellValue("֤��ǩ������");  
        cell.setCellStyle(style);  
       
        cell = row.createCell((short) 8);        
        cell.setCellValue("֤����Ч��");  
        cell.setCellStyle(style);  

        cell = row.createCell((short) 9);        
        cell.setCellValue("��ϵ�绰");  
        cell.setCellStyle(style);  
       
        cell = row.createCell((short) 10);        
        cell.setCellValue("��ѯ��ʽ");  
        cell.setCellStyle(style);  
       
        cell = row.createCell((short) 11);        
        cell.setCellValue("������");  
        cell.setCellStyle(style);  
     
        cell = row.createCell((short) 12);        
        cell.setCellValue("ͨѶ��ַ��������");  
        cell.setCellStyle(style);  
      for(int j=0;j<13;j++){
    	  sheet.setColumnWidth(j, 256*35+184);  
      }
        
      String excelname = null;
        // ���岽��д��ʵ������ ʵ��Ӧ������Щ���ݴ����ݿ�õ���  	         
        zxexcel zc=new zxexcel();
        for (int i = 0; i < zxlist.size(); i++)  
        {   
        	System.out.println("jsonlength:"+zxlist.size());
        	row = sheet.createRow(i + 1);  
            zc =zxlist.get(i);  
            // ���Ĳ���������Ԫ�񣬲�����ֵ  
            row.createCell((short) 0).setCellValue(zc.getCode());             
            row.createCell((short) 1).setCellValue(zc.getZjlx());  
            row.createCell((short) 2).setCellValue(zc.getName());  
            row.createCell((short) 3).setCellValue(zc.getSex());
            row.createCell((short) 4).setCellValue(zc.getMz());  
            row.createCell((short) 5).setCellValue(zc.getTxaddress());  
            row.createCell((short) 6).setCellValue(zc.getIdcard());             
            row.createCell((short) 7).setCellValue(zc.getZjqfjg());  
            row.createCell((short) 8).setCellValue(zc.getZjdate());
            row.createCell((short) 9).setCellValue(zc.getLxdh());
            row.createCell((short) 10).setCellValue(zc.getCxbs());
            row.createCell((short) 11).setCellValue(zc.getCname());
            row.createCell((short) 12).setCellValue(zc.getTxcode());
            row.setRowStyle(style);
            if(zxlist.size()==1){
            	  excelname ="����_"+zc.getName()+".xls";
                  System.out.println(excelname);
              }  
            }
          
        // �����������ļ��浽ָ��λ��  
        response.setContentType("text/html;charset=UTF-8");     
        BufferedInputStream in = null;    
        BufferedOutputStream out = null;    
     
        try  
        {  
        	response.setContentType("multipart/form-data");    
            response.setCharacterEncoding("UTF-8");    
            response.setHeader("Content-Disposition", "attachment; filename="+new String(excelname.getBytes("gbk"),"iso-8859-1"));       
            //response.setHeader("Content-Length",String.valueOf()));
            out = new BufferedOutputStream(response.getOutputStream());    
            wb.write(out); 
            //System.out.println("�������");   
            out.close();  
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
    }
	 
    public static void main(String[] args) throws Exception {
		zxexcel c=new zxexcel();		
		c.setCode("213231");
		c.setZjlx("213231");
		c.setName("ͼͼ");
		c.setSex("213231");
		c.setMz("��");
		c.setTxaddress("�Ϻ��ֶ�����������");
		c.setIdcard("41152119920420533X");
		c.setZjqfjg("�Ϻ�");
		c.setZjdate(" 2013.01.04-2023.01.04");
		c.setLxdh("213231");
		c.setCxbs("0");
		c.setCname("");
		c.setTxcode("edfsfsd");
        zxexcel c1=new zxexcel();		
		c1.setCode("213231");
		c1.setZjlx("");
		c1.setCname("ͼͼ");
		c1.setSex("");
		c1.setMz("��");
		c1.setTxaddress("�Ϻ��ֶ�����������");
		c1.setIdcard("41152119920420533X");
		c1.setZjqfjg("�Ϻ�");
		c1.setZjdate(" 2013.01.04-2023.01.04");
		c1.setLxdh("1212321321");
		c1.setCxbs("0");
		c1.setCname("");
		c1.setTxcode("edfsfsd");
        List<zxexcel> zl=new ArrayList<>();
      zl.add(c);	
      zl.add(c1);
      toxxexcel(zl, null);
      
	}
 
}
