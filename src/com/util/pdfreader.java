package com.util;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.lowagie.text.Document;  
import com.lowagie.text.DocumentException;  
import com.lowagie.text.pdf.PdfCopy;  
import com.lowagie.text.pdf.PdfImportedPage;  
import com.lowagie.text.pdf.PdfReader;  





public class pdfreader {

    //��ȡpdf ȫ������
    public static String readFdfbyAll(String pdffile){
        //StringBuffer result=null;
        String str=null;
        FileInputStream is = null;
        PDDocument document = null;
        try {
            is = new FileInputStream(pdffile);
            PDFParser parser = new PDFParser(new RandomAccessBuffer(is));
            parser.parse();
            document = parser.getPDDocument();
            PDFTextStripper stripper = new PDFTextStripper();
            str=stripper.getText(document);
            //result = new StringBuffer(str);
            // System.out.println("------"+str+"--------------");
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
        return str;





    }


/*
��ҳ��ȡ������ȡ��ÿһҳ��Ϣ����ҳ��Ϊkey��������Ϊvalueֵ���뵽map��
 */
    public static Map readFdfbyPage(String file) throws Exception {
        // �Ƿ�����
        boolean sort = false;
        // pdf�ļ���
        String pdfFile = file;
        // �����ı��ļ�����
        String textFile = null;
        // ���뷽ʽ
        String encoding = "UTF-8";
        // ��ʼ��ȡҳ��
        int startPage = 1;
        // ������ȡҳ��
        int endPage =5;
        // �ļ��������������ı��ļ�
        Writer output = null;
        // �ڴ��д洢��PDF Document
        PDDocument document = null;
        Map mpdf=new HashMap();
        List mp=new ArrayList();
        try {
            try {
                // ���ȵ���һ��URL��װ���ļ�������õ��쳣�ٴӱ����ļ�ϵͳ//ȥװ���ļ�
                URL url = new URL(pdfFile);
                //ע������Ѳ�����ǰ�汾�е�URL.����File��
                document = PDDocument.load(new File(pdfFile));
                // ��ȡPDF���ļ���
                String fileName = url.getFile();
                // ��ԭ��PDF�������������²�����txt�ļ�
                if (fileName.length() > 4) {
                    File outputFile = new File(fileName.substring(0, fileName
                            .length() - 4)
                            + ".txt");
                    textFile = outputFile.getName();
                }
            } catch (MalformedURLException e) {
                // �����ΪURLװ�صõ��쳣����ļ�ϵͳװ��
                //ע������Ѳ�����ǰ�汾�е�URL.����File��
                document = PDDocument.load(new File(pdfFile));
                if (pdfFile.length() > 4) {
                    textFile = pdfFile.substring(0, pdfFile.length() - 4)
                            + ".txt";
                }
            }
            // �ļ���������д���ļ���textFile
            output = new OutputStreamWriter(new FileOutputStream(textFile),
                    encoding);
            // PDFTextStripper����ȡ�ı�
            PDFTextStripper  stripper = null;
            stripper = new PDFTextStripper();
            // �����Ƿ�����
            stripper.setSortByPosition(sort);
//            // ������ʼҳ
//            stripper.setStartPage(startPage);
//            // ���ý���ҳ
//            stripper.setEndPage(endPage);



            for(int i=startPage;i<=document.getNumberOfPages();i++){
                stripper = new PDFTextStripper();
                stripper.setSortByPosition(sort);
                // ������ʼҳ
                stripper.setStartPage(i);
                // ���ý���ҳ
                stripper.setEndPage(i);
                String textT=stripper.getText(document);
//               System.out.println("��" + i+"ҳ");
//               System.out.println( "��ʼ--------------------------------------------------------------------");
//               System.out.println( textT);
//                System.out.println( "--------------------------------------------------------------------����");
                mpdf.put(i, textT);
//                System.out.println(mpdf.get(i));
            }
            // System.out.println(mpdf.size());
            // ����PDFTextStripper��writeText��ȡ������ı�
//            stripper.writeText(document, output);
//            System.out.println(stripper.getEndPage());
//            System.out.println( "*****="+stripper.getText(document));
//            System.out.println("*****22=" + stripper.getTextLineMatrix());
//            System.out.println("*****33=" + stripper.getTextMatrix());
//            System.out.println("*****44=" + stripper.getArticleStart());
//            System.out.println("*****55=" + stripper.getArticleEnd());

        } finally {
            if (output != null) {
                // �ر������
                output.close();
            }
            if (document != null) {
                // �ر�PDF Document
                document.close();
            }
        }
        return mpdf;
    }


    /*
��ҳ��ȡ������ȡ��ÿһҳ��Ϣ����ҳ��Ϊkey��������Ϊvalueֵ���뵽map��,ͬʱ
 */
    public static Map readFdfbyPageandAll(String file) throws Exception {
        // �Ƿ�����
        boolean sort = false;
        // pdf�ļ���
        String pdfFile = file;
        // �����ı��ļ�����
        String textFile = null;
        // ���뷽ʽ
        String encoding = "UTF-8";
        StringBuffer sbf=new StringBuffer();
        // ��ʼ��ȡҳ��
        int startPage = 1;
        // ������ȡҳ��
        int endPage =5;
        // �ļ��������������ı��ļ�
        Writer output = null;
        // �ڴ��д洢��PDF Document
        PDDocument document = null;
        PDFParser p=null;
        Map mpdf=new HashMap();
        try {
            try {
                // ���ȵ���һ��URL��װ���ļ�������õ��쳣�ٴӱ����ļ�ϵͳ//ȥװ���ļ�
                //URL url = new URL(pdfFile);
                //ע������Ѳ�����ǰ�汾�е�URL.����File��
                p=new PDFParser(new RandomAccessBuffer(new FileInputStream(pdfFile)));
                p.parse();
                document = p.getPDDocument();

            } catch (Exception e) {
                System.out.println("e.tostring="+e.toString());
                // �����ΪURLװ�صõ��쳣����ļ�ϵͳװ��
                //ע������Ѳ�����ǰ�汾�е�URL.����File��
                document = PDDocument.load(new File(pdfFile));
//                if (pdfFile.length() > 4) {
//                    textFile = pdfFile.substring(0, pdfFile.length() - 4)
//                            + ".txt";
//                }
            }
            // �ļ���������д���ļ���textFile
//            output = new OutputStreamWriter(new FileOutputStream(textFile),
//                    encoding);
            // PDFTextStripper����ȡ�ı�
            PDFTextStripper stripper = null;
//            stripper = new PDFTextStripper();
//            // �����Ƿ�����
//            stripper.setSortByPosition(sort);
//            // ������ʼҳ
//            stripper.setStartPage(startPage);
//            // ���ý���ҳ
//            stripper.setEndPage(endPage);


            stripper = new PDFTextStripper();
            stripper.setSortByPosition(sort);
            //System.out.println("sss="+document.getNumberOfPages());
            String textT="";
            int nullpage=0;//��ҳ���
            for(int i=startPage;i<=document.getNumberOfPages();i++){
            	 String str=null;
                // ������ʼҳ
                stripper.setStartPage(i);
                // ���ý���ҳ
                stripper.setEndPage(i);
                textT=stripper.getText(document).trim();
                if(textT.equals("")){
                    //System.out.println("666666666666666");
                    nullpage=nullpage+1;
                    if(nullpage==2){
                        return null;
                    }
                }
               //System.out.println("��" + i + "ҳ");
              // System.out.println( "��ʼ--------------------------------------------------------------------");
              // System.out.println( textT);
             // System.out.println( "--------------------------------------------------------------------����");
                str=textT;
               //mpdf.put(i,textT);
                sbf.append(textT);
               // System.out.println(mpdf.get(i));
                mpdf.put(i, str);
             }
             
            // System.out.println(mpdf.size());
            // ����PDFTextStripper��writeText��ȡ������ı�
//            stripper.writeText(document, output);
//            System.out.println(stripper.getEndPage());
//            System.out.println( "*****="+stripper.getText(document));
//            System.out.println("*****22=" + stripper.getTextLineMatrix());
//            System.out.println("*****33=" + stripper.getTextMatrix());
//            System.out.println("*****44=" + stripper.getArticleStart());
//            System.out.println("*****55=" + stripper.getArticleEnd());

        } finally {
            if (document != null) {
                // �ر�PDF Document
                document.close();
            }
        }
        return mpdf;
    }
       
      public static List<String> forcode(Map hm){
		  List<String> l=new ArrayList<String>();
    	   for(int i=1;i<=hm.size();i++){                		
      		  String str=(String)hm.get(i);        		
              String code = str.substring(str.indexOf("��")+1,str.indexOf("��")+27);
              l.add(code);
      		  //System.out.println("��"+i+"�ν���pdf�ļ�����Ϊ:"+code);
      	   }
    	  return l;   	  
      }
      /** 
       * ��ȡpdfFile�ĵ�fromҳ����endҳ�����һ���µ��ļ��� 
       * @param pdfFile 
       * @param subfileName 
       * @param from 
       * @param end 
       */  
      public static void partitionPdfFile(String pdfFile,  
              String newFile, int from, int end) {  
          Document document = null;  
          PdfCopy copy = null;          
          try {  
              PdfReader reader = new PdfReader(pdfFile);            
              int n = reader.getNumberOfPages();            
              if(end==0){  
                  end = n;  
              }  
              ArrayList<String> savepaths = new ArrayList<String>();  
              //String staticpath = pdfFile.substring(0,pdfFile.lastIndexOf("\\")+1);  
              //System.out.println("sssssss:"+staticpath);
              String savepath =newFile;  
              System.out.println("����·��:"+savepath);
              savepaths.add(savepath);  
              document = new Document(reader.getPageSize(1));  
              copy = new PdfCopy(document, new FileOutputStream(savepaths.get(0)));  
              document.open();  
              for(int j=from;j<=end;j++) {  
                  document.newPage();   
                  PdfImportedPage page=copy.getImportedPage(reader,j);  
                  copy.addPage(page);  
              }  
              document.close();  
    
          } catch (IOException e) {  
              e.printStackTrace();  
          } catch(DocumentException e) {  
              e.printStackTrace();  
          }  
      }  
     
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        pdfreader pdfReader = new pdfreader();
        try {
            // ȡ��E���µ�SpringGuide.pdf������
         //  String str=  pdfReader.readFdfbyAll("C:/Users/Administrator/Desktop/test/�Ϻ������������޲�Ʒ��ҵ�����̽���V1.12.pdf");
        	// ͨ�������ļ�·����ȡ�ļ�
    		File file = new File("C:/Users/Administrator/Desktop/2/pdf");
    		// �ѻ�ȡ�����ļ���������������
    	    File[] files = file.listFiles();
    	     
    	    for(File f:files){
    	    	 //System.out.println(f);
    	    	//��ȡ�ļ����ͣ����ļ���׺��
    	    	int start = f.getAbsolutePath().length()-3;
    	    	int end  = f.getAbsolutePath().length();
    	    	//�õ��ļ��ĺ�׺��
    	    	String pdf = f.getAbsolutePath().substring(start, end);
    	    	//System.out.println(pdf);
    	    	//�ж��Ƿ���pdf��ʽ���ļ�
    	    	if(pdf.equals("pdf") || pdf.equals("PDF")){
        	Map hm=pdfreader.readFdfbyPageandAll(f.toString());
            
        	System.out.println("---------"+hm.size());
        	for(int i=1;i<=hm.size();i++){                		
        		String str=(String)hm.get(i);        		
                String code = str.substring(str.indexOf("��")+1,str.indexOf("��")+27);
        		System.out.println("��"+i+"�ν���pdf�ļ�����Ϊ:"+code);
        	}
    	    	}
    	    	}
            //  pdfReader.readFdfbyPageandAll("C:/pdf/0107/JuChao/̫ƽ���״ι�������A�ɹ�Ʊ���й�����.pdf");
            //̫ƽ���״ι�������A�ɹ�Ʊ���й�����.pdf
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

