package com.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
	/* 2017.11.24
	 * ����pdf�ļ��еı��
	 * */
	public class getInfoFromPDF {
		private static String result = null;  // ��������pdf�ļ��е���Ϣ
	    private static FileInputStream is = null;  // ������
	    private static PDDocument document = null;   
	//��ȡpdf�ļ��ļ���ȫ����Ϣ
    public static String getAllInfoFromPDF(String pdfFilePath){  
        String result = null;  
        FileInputStream is = null;  
        PDDocument document = null;  
        try {  
            is = new FileInputStream(pdfFilePath);  
            PDFParser parser = new PDFParser(new RandomAccessBuffer(is));              
            parser.parse();  
            document = parser.getPDDocument();  
            PDFTextStripper stripper = new PDFTextStripper();  
            result = stripper.getText(document);  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (is != null) {  
                try {  
                    is.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
            if (document != null) {  
                try {  
                    document.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return result;  
    }  
	public static void main(String[] args) throws IOException {
		/*  
		 * ͨ���ݹ�õ�ĳһ·�������е�Ŀ¼����PDF�ļ�
		 */
		// ͨ�������ļ�·����ȡ�ļ�
		File file = new File("C:/Users/Administrator/Desktop/2/pdf");
		// �ѻ�ȡ�����ļ���������������
	    File[] files = file.listFiles();
	     
	    for(File f:files){
	    	 System.out.println(f);
	    	//��ȡ�ļ����ͣ����ļ���׺��
	    	int start = f.getAbsolutePath().length()-3;
	    	int end  = f.getAbsolutePath().length();
	    	//�õ��ļ��ĺ�׺��
	    	String pdf = f.getAbsolutePath().substring(start, end);
	    	System.out.println(pdf);
	    	//�ж��Ƿ���pdf��ʽ���ļ�
	    	if(pdf.equals("pdf") || pdf.equals("PDF")){
	    		// ��pdf��ʽ���ļ�
	    		//�õ�ȫ��pdf�ļ��е���Ϣ
	    		String str = getInfoFromPDF.getAllInfoFromPDF(f.getAbsolutePath());
	    		//��ȡpdf�ļ��еı��
		    	String code = str.substring(str.indexOf("��")+1,str.indexOf("��")+27);
		    	System.out.println("����pdf�ļ�����Ϊ:"+code); 
	    	}
	    }
	}
}
