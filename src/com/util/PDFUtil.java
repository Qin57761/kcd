package com.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

	/* 2017.11.24  ---�� 2017.11.27��������
	 * ����pdf�ļ��еı��
	 * */
	public class PDFUtil {
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
    //��ȡ pdf�ļ�·�� 
    public static ArrayList getFilePathFromPDF(String pdfFilePath){
    	ArrayList filePathList = new ArrayList();
    	String FilePath = null;
    	File file = new File(pdfFilePath);
		// �ѻ�ȡ�����ļ���������������
	    File[] files = file.listFiles();
	    if(files != null){
	    	for(File f:files){
	    		//--------------------------1.��ȡ�ļ�·��
	    		FilePath = f.getAbsolutePath();
	    		//��ȡ�ļ����ͣ����ļ���׺��
		    	int start = f.getAbsolutePath().length()-3;
		    	int end  = f.getAbsolutePath().length();
		    	//�õ��ļ��ĺ�׺��
		    	String pdf = f.getAbsolutePath().substring(start, end);
		    	//�ж��Ƿ���pdf��ʽ���ļ�
		    	if(pdf.equals("pdf") || pdf.equals("PDF")){
		    		filePathList.add(FilePath);
		    	}
	    	}
	    }
		return filePathList;
    }
  //��ȡһ��pdf�ļ�·�� 
    public static String getOneFilePathFromPDF(String pdfFilePath){
    	String filePathList = null;
    	String FilePath = null;
    	File f = new File(pdfFilePath);
		//--------------------------1.��ȡ�ļ�·��
		FilePath = f.getAbsolutePath();
		//��ȡ�ļ����ͣ����ļ���׺��
    	int start = f.getAbsolutePath().length()-3;
    	int end  = f.getAbsolutePath().length();
    	//�õ��ļ��ĺ�׺��
    	String pdf = f.getAbsolutePath().substring(start, end);
    	//�ж��Ƿ���pdf��ʽ���ļ�
    	if(pdf.equals("pdf") || pdf.equals("PDF")){
    		filePathList = FilePath;
    	}
		return filePathList;
    }
  //��ȡһ��pdf�ļ��еı�ŵ�ǰ��λ
  	public static String getBeforeTwoToOneCodesFromPDF(String pdfFilePath){
  		ArrayList allCode = new ArrayList();
  		String BeforeTwocode = null;  // ���ڱ�����
  		//��ȡ��ȫ������Ϣ
  		String str = getAllInfoFromPDF(pdfFilePath);
  		//������
  		BeforeTwocode = str.substring(str.indexOf("��ţ�")+3,str.indexOf("��")+3);
  		///////////////////2017.11.27��������/////////////////////////////////////////
  		//��ȡ��ȫ���ı��
  		return BeforeTwocode;
  	}
    //��ȡһ��pdf�ļ��еı��
  	public static ArrayList getOneCodesFromPDF(String pdfFilePath){
  		ArrayList allCode = new ArrayList();
  		String code = null;  // ���ڱ�����
  		//��ȡ��ȫ������Ϣ
  		String str = getAllInfoFromPDF(pdfFilePath);
  		//������
      	code = str.substring(str.indexOf("��ţ�")+3,str.indexOf("��")+27);
  		///////////////////2017.11.27��������/////////////////////////////////////////
  		//��ȡ��ȫ���ı��
  		Set set = new HashSet(); // �ѱ�Ŵ����
  		Iterator<String> it = null;
  		//
  		String regex = "\\d{26}";  
  		Pattern pattern = Pattern.compile(regex);  
  		Matcher matcher = pattern.matcher(str);  
  		//���Ϊ�ظ��ı��
  		while (matcher.find()) { 
  			//���Ϊ���ظ��ı��
  			/*set.add(matcher.group());
  			it = set.iterator();*/
  			//���Ϊ�ظ��ı��
  			code = matcher.group();
  			allCode.add(code);
  		}  
  		//���Ϊ���ظ��ı��
  		/*while (it.hasNext()) {
  			String getCode = it.next();
  			System.out.println("���Ϊ��"+getCode);
  		}*/
  		return allCode;
  	}
    //��ȡ pdf�ļ��еı��
    public static ArrayList getCodeFromPDF(String pdfFilePath){
    	ArrayList allCode = new ArrayList();
    	ArrayList al = getFilePathFromPDF(pdfFilePath);
    	Iterator itt = al.iterator();
    	String filePath = null; // ���뱣�澫ȷ·��
    	String code = null;  // ���ڱ�����
		while(itt.hasNext()){
			filePath = (String) itt.next();
			//��ȡ��ȫ������Ϣ
			String str = getAllInfoFromPDF(filePath);
			//������
	    	code = str.substring(str.indexOf("��ţ�")+3,str.indexOf("��")+27);
			///////////////////2017.11.27��������/////////////////////////////////////////
			//��ȡ��ȫ���ı��
			Set set = new HashSet(); // �ѱ�Ŵ����
			Iterator<String> it = null;
			//
			String regex = "\\d{26}";  
			Pattern pattern = Pattern.compile(regex);  
			Matcher matcher = pattern.matcher(str);  
			//���Ϊ�ظ��ı��
			while (matcher.find()) { 
				//���Ϊ���ظ��ı��
				/*set.add(matcher.group());
				it = set.iterator();*/
				//���Ϊ�ظ��ı��
				code = matcher.group();
				allCode.add(code);
			}  
			//���Ϊ���ظ��ı��
			/*while (it.hasNext()) {
				String getCode = it.next();
				System.out.println("���Ϊ��"+getCode);
			}*/
    	}
		return allCode;
    }
  
    //��ȡ pdf�ļ��е�����
    public static ArrayList getTaoCountsFromPDF(String pdfFilePath){
    	ArrayList taoCounts = new ArrayList();
    	String counts = null;
    	//��ȡ��ȫ������Ϣ
    	ArrayList al = getFilePathFromPDF(pdfFilePath);
    	Iterator it = al.iterator();
    	while(it.hasNext()){
    		String str = getAllInfoFromPDF((String)it.next());
        	int count=0,StringStart=0;
    		while(str.indexOf("����������Ϣ�������ݿ����������Ϣ�ɼ���Ȩ��", StringStart)>=0 && StringStart<str.length()){
    			//���ַ��������Ӵ�ʱ���������ַ�������
    			count++;
    			StringStart=str.indexOf("����������Ϣ�������ݿ����������Ϣ�ɼ���Ȩ��", StringStart)+"����������Ϣ�������ݿ����������Ϣ�ɼ���Ȩ��".length();//�õ��µ�startֵ��
    		}
    		counts = String.valueOf(count);
    		taoCounts.add(counts);
    	}
		return taoCounts;
    }
    //��ȡ һ��pdf�ļ��е�����
    public static String getOneTaoCountsFromPDF(String pdfFilePath){
    	String taoCounts = null;
    	//��ȡ��ȫ������Ϣ
    	String str = getAllInfoFromPDF(pdfFilePath);
    	int count=0,StringStart=0;
		while(str.indexOf("����������Ϣ�������ݿ����������Ϣ�ɼ���Ȩ��", StringStart)>=0 && StringStart<str.length()){
			//���ַ��������Ӵ�ʱ���������ַ�������
			count++;
			StringStart=str.indexOf("����������Ϣ�������ݿ����������Ϣ�ɼ���Ȩ��", StringStart)+"����������Ϣ�������ݿ����������Ϣ�ɼ���Ȩ��".length();//�õ��µ�startֵ��
		}
		taoCounts = String.valueOf(count);
		return taoCounts;
    }
    public static void main(String[] args){
    	//F:\\kuaiJinSuo_DuoYing\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\hzj\\upload
    	// ��ȡ�ļ���·��
    	/*ArrayList al = getFilePathFromPDF("F:\\pdf�ļ�����");
    	Iterator it = al.iterator();
		while(it.hasNext()){
    		System.out.println("�ļ�·��Ϊ��"+it.next());
    	}*/
    	// ��ȡ�ļ��ı��
		/*ArrayList al21 = getCodeFromPDF("F:\\pdf�ļ�����");
    	Iterator it1 = al21.iterator();
		while(it1.hasNext()){
    		System.out.println("���Ϊ��"+it1.next());
    	}*/
    	// ��ȡ�ļ�����
		/*ArrayList al1 = getTaoCountsFromPDF("F:\\pdf�ļ�����");
		Iterator it2 = al1.iterator();
		while(it2.hasNext()){
    		System.out.println("��pdf�ļ��е�����Ϊ��"+it2.next());
    	}*/
    	//��ȡһ��pdf�ļ��еı��
    	/*ArrayList al =  getOneCodesFromPDF("F:\\pdf�ļ�����\\�쳵��200.pdf");
    	Iterator it = al.iterator();
		while(it.hasNext()){
    		System.out.println("���Ϊ��"+it.next());
    	}*/
    	//��ȡ һ��pdf�ļ��е�����
    	/*String taoCounts = getOneTaoCountsFromPDF("F:\\pdf�ļ�����\\�쳵��200.pdf");
    	System.out.println(taoCounts+"********");*/
    	String BeforeTwo = getBeforeTwoToOneCodesFromPDF("F:\\pdf�ļ�����\\�䵱200_����1.pdf");
    	System.out.println(BeforeTwo);
    }
}
