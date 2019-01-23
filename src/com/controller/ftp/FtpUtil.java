package com.controller.ftp;


import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.omg.CORBA.portable.ApplicationException;

import com.model.icbc.icbc_kk;
import com.model1.icbc.icbc_dk;
import com.model1.icbc.icbc_mq;
import com.util.jsonutil;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;



public class FtpUtil {

    /**
     * ��ȡFTPClient����
     *
     * @param ftpHost     FTP����������
     * @param ftpPassword FTP ��¼����
     * @param ftpUserName FTP��¼�û���
     * @param ftpPort     FTP�˿� Ĭ��Ϊ21
     * @return
     */
    public static FTPClient getFTPClient(String ftpHost, String ftpUserName,
                                         String ftpPassword, int ftpPort) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(ftpHost, ftpPort);// ����FTP������
            ftpClient.login(ftpUserName, ftpPassword);// ��½FTP������
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                System.out.println("δ���ӵ�FTP���û������������");
                ftpClient.disconnect();
            } else {
                System.out.println("FTP���ӳɹ���");
            }
        } catch (SocketException e) {
            e.printStackTrace();
            System.out.println("FTP��IP��ַ���ܴ�������ȷ���á�");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("FTP�Ķ˿ڴ���,����ȷ���á�");
        }
        return ftpClient;
    }

    /*
     * ��FTP�����������ļ�
     *
     * @param ftpHost FTP IP��ַ
     * @param ftpUserName FTP �û���
     * @param ftpPassword FTP�û�������
     * @param ftpPort FTP�˿�
     * @param ftpPath FTP���������ļ�����·�� ��ʽ�� ftptest/aa
     * @param localPath ���ص����ص�λ�� ��ʽ��H:/download
     * @param fileName �ļ�����
     */
    public static void downloadFtpFile(String ftpHost, String ftpUserName,
                                       String ftpPassword, int ftpPort, String ftpPath, String localPath,
                                       String fileName) {

        FTPClient ftpClient = null;

        try {
            ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            ftpClient.setControlEncoding("UTF-8"); // ����֧��
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(ftpPath);

            File localFile = new File(localPath + File.separatorChar + fileName);
            OutputStream os = new FileOutputStream(localFile);
            ftpClient.retrieveFile(fileName, os);
            os.close();
            ftpClient.logout();

        } catch (FileNotFoundException e) {
            System.out.println("û���ҵ�" + ftpPath + "�ļ�");
            e.printStackTrace();
        } catch (SocketException e) {
            System.out.println("����FTPʧ��.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("�ļ���ȡ����");
            e.printStackTrace();
        }

    }

    /**
     * Description: ��FTP�������ϴ��ļ�
     * @param ftpHost FTP������hostname
     * @param ftpUserName �˺�
     * @param ftpPassword ����
     * @param ftpPort �˿�
     * @param ftpPath  FTP���������ļ�����·�� ��ʽ�� ftptest/aa
     * @param fileName ftp�ļ�����
     * @param input �ļ���
     * @return �ɹ�����true�����򷵻�false
     */
    /** �����ַ����� */
    private static String LOCAL_CHARSET = "GBK";
     
    // FTPЭ�����棬�涨�ļ�������Ϊiso-8859-1
    private static String SERVER_CHARSET = "ISO-8859-1";
    
    public static boolean uploadFile(String ftpHost, String ftpUserName,
                                     String ftpPassword, int ftpPort, String ftpPath,
                                     String fileName,InputStream input) {
        boolean success = false;
        FTPClient ftpClient = null;
        try {
            int reply;
            ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return success;
            }
            ftpClient.setControlEncoding("UTF-8"); // ����֧��
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(ftpPath);
            ftpClient.storeFile(fileName,input);
            input.close();
            ftpClient.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }
    public static List<String> zipToFile(String sourceFile,String toFolder) throws Exception {  
        String toDisk = toFolder;//���ս�ѹ��Ĵ��·��  
        ZipFile zfile = new ZipFile(sourceFile);//���Ӵ���ѹ�ļ�  
        System.out.println("Ҫ��ѹ���ļ���:" + zfile.getName());  
        Enumeration zList = zfile.entries();//�õ�zip���������Ԫ��  
        ZipEntry ze = null;  
        byte[] buf = new byte[1024];  
        List<String> list=new ArrayList<String>();
        while (zList.hasMoreElements()) {  
            ze = (ZipEntry) zList.nextElement();  
            if(ze.isDirectory()){  
             //   System.out.println("��zip�ļ�����ļ���:" + ze.getName());  
                continue;  
            }  
            //System.out.println("zip������ļ�: " + ze.getName() + "\t" + "��СΪ:" 
           //         + ze.getSize() + "KB");  
            //��ZipEntryΪ�����õ�һ��InputStream����д��OutputStream��  
            OutputStream outputStream = new BufferedOutputStream(  
                    new FileOutputStream(getRealFileName(toDisk, ze.getName())));  
            InputStream inputStream = new BufferedInputStream(zfile  
                    .getInputStream(ze));  
            int readLen = 0;  
            while ((readLen = inputStream.read(buf, 0, 1024)) != -1) {  
                outputStream.write(buf, 0, readLen);  
            }  
            inputStream.close();  
            outputStream.close();  
           // System.out.println("�Ѿ���ѹ��:" + ze.getName());
            list.add(ze.getName());
        }  
        zfile.close();
		return list;  
    }  
    private static File getRealFileName(String zippath, String absFileName){  
    	 
        String[] dirs = absFileName.split("/", absFileName.length());  
        File ret = new File(zippath);// �����ļ�����  
        if (dirs.length > 1) {  
            for (int i = 0; i < dirs.length - 1; i++) {  
                ret = new File(ret, dirs[i]);  
            }  
        }  
        if (!ret.exists()) {// ����ļ��Ƿ����  
            ret.mkdirs();// �����˳���·����ָ����Ŀ¼  
        }  
        ret = new File(ret, dirs[dirs.length - 1]);// ���� ret ����·������ child ·�����ַ�������һ���� File ʵ��  
        return ret;  
    } 
//    /**
//    * �� VO ����������Ϊ null ��תΪ ""
//    *
//    * @throws ApplicationException
//     * @throws IllegalAccessException 
//     * @throws IllegalArgumentException 
//    */
//    public static Object nullConverNullString(Object obj) throws ApplicationException, IllegalArgumentException, IllegalAccessException {
//    if (obj != null) {
//    Class classz = obj.getClass();
//    // ��ȡ���иö��������ֵ
//    Field fields[] = classz.getDeclaredFields();
//    // ��������ֵ��ȡ����������Ϊ null ֵ��
//    for (Field field : fields) {
//    field.setAccessible(true);  
//    try {
//    if (field.get(obj) == null) {
//    	Type t = field.getGenericType(); 
//     if(!t.toString().equals("class java.math.BigDecimal")){
//    	 Method mtd = classz.getMethod("set"
// 			    + change(field.getName()),
// 			    new Class[] { String.class });// ȡ��������ķ�������
// 			    mtd.invoke(obj, new Object[] {""});// ִ����Ӧ��ֵ����
// 
//     }
//    }
//
//    } catch (Exception e) {
//    e.printStackTrace();    
//    }
//    }
//    }
//	return obj;
//    }
//
//    /**
//    * @param src
//    *            Դ�ַ���
//    * @return �ַ�������src�ĵ�һ����ĸת��Ϊ��д��srcΪ��ʱ����null
//    */
//    public static String change(String src) {
//    if (src != null) {
//    StringBuffer sb = new StringBuffer(src);
//    sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
//    return sb.toString();
//    } else {
//    return null;
//    }
//    } 
    public static void main(String[] args) {
    //  21��FTP�˿�
    //  80��HTTP�˿ڣ�
    //  443��HTTPS�˿�
    //  3306��MYSQL
    	
    	//javaftp  /   11189a82bf73182d
        String ftpHost = "125.77.23.30";
        String ftpUserName = "javaftp";
        String ftpPassword = "11189a82bf73182d";
        int ftpPort = 21;
        String ftpPath = "/KCDIMG/assess/upload/";
        String localPath = "C:/Users/Administrator/Desktop/contract(3)/contract/";
        String fileName = "kcdICBCAPI00000413.zip";
        //�ϴ��ļ�
        
        //��ȡϵͳĬ�ϱ���    
        System.out.println(System.getProperty("file.encoding"));     
        //���ñ���  
        System.getProperties().put("file.encoding", "UTF-8");  
        //��ȡϵͳĬ�ϱ���    
        System.out.println(System.getProperty("file.encoding"));   
        
        String path=System.getProperty("user.dir")+"/WebContent/htpdf";
        File[] files=new File(localPath).listFiles();
        System.out.println(files.length);
        for(int i=0;i<files.length;i++){
        	System.out.println(files[i].getName());
        	String  fileName1=files[i].getName() ;
        	try{
                FileInputStream in=new FileInputStream(new File(localPath+files[i].getName()));
                boolean test = FtpUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath,fileName1,in);
                System.out.println(test);            
            } catch (FileNotFoundException e){
                e.printStackTrace();
                System.out.println(e);
            } 
        }
        
       
        //��ѹ�ļ�
//        try {
//        	icbc_kk icbc_kk=new icbc_kk();
//        	icbc_dk icbc_dk=new icbc_dk();
//        	icbc_mq icbc_mq=new icbc_mq();
//			List<String> list=zipToFile(localPath,"C:/Users/Administrator/Desktop");
//			System.out.println(list.size());
//			String kkimg7s="";
//			String dkimg1="";
//			String dkimg2="";
//			String dkimg3="";
//			String dkimg4="";
//			String dkimg5="";
//			String dkimg6="";
//			String dkimg7="";
//			String dkimg8="";
//			for(int i=0;i<list.size();i++){
//				String name=list.get(i);
//				String filename=name.substring(name.indexOf("/")+1, name.indexOf("."));
//				String filetype=filename.substring(0, 2);
//				String fileno=filename.substring(2, 3);
//				if(filetype.equals("kk")){
//				if(Integer.parseInt(fileno)==1){
//				    icbc_kk.setImgstep3_1("upload/"+name);	
//				}else if(Integer.parseInt(fileno)==2){
//					icbc_kk.setImgstep3_2("upload/"+name);	
//				}else if(Integer.parseInt(fileno)==3){
//					icbc_kk.setImgstep3_3("upload/"+name);	
//				}else if(Integer.parseInt(fileno)==4){
//					icbc_kk.setImgstep3_4("upload/"+name);	
//				}else if(Integer.parseInt(fileno)==5){
//					icbc_kk.setImgstep3_5("upload/"+name);	
//				}else if(Integer.parseInt(fileno)==6){
//					icbc_kk.setImgstep3_6("upload/"+name);	
//				}else if(Integer.parseInt(fileno)==7){
//					icbc_kk.setImgstep3_7("upload/"+name);	
//				}else if(Integer.parseInt(fileno)==8){
//					kkimg7s=kkimg7s+""+"upload/"+name;
//				}
//				}
//				if(filetype.equals("dk")){
//					if(Integer.parseInt(fileno)==1){
//						dkimg1=dkimg1+""+"upload/"+name;
//					}else if(Integer.parseInt(fileno)==2){
//						dkimg2=dkimg2+""+"upload/"+name;	
//					}else if(Integer.parseInt(fileno)==3){
//						dkimg3=dkimg3+""+"upload/"+name;	
//					}else if(Integer.parseInt(fileno)==4){
//						dkimg4=dkimg4+""+"upload/"+name;	
//					}else if(Integer.parseInt(fileno)==5){
//						dkimg5=dkimg5+""+"upload/"+name;	
//					}else if(Integer.parseInt(fileno)==6){
//						dkimg6=dkimg6+""+"upload/"+name;	
//					}else if(Integer.parseInt(fileno)==7){
//						dkimg7=dkimg7+""+"upload/"+name;	
//					}else if(Integer.parseInt(fileno)==8){
//						dkimg8=dkimg8+""+"upload/"+name;
//					}	
//				}
//				if(filetype.equals("mq")){
//					if(Integer.parseInt(fileno)==1){
//						icbc_mq.setImgstep8_1v("upload/"+name);
//					}else if(Integer.parseInt(fileno)==2){
//						icbc_mq.setImgstep8_2v("upload/"+name);	
//					}else if(Integer.parseInt(fileno)==3){
//						icbc_mq.setImgstep8_3v("upload/"+name);	
//					}else if(Integer.parseInt(fileno)==4){
//						icbc_mq.setImgstep8_4v("upload/"+name);	
//					}
//				}
//			}
//			icbc_kk.setImgstep3_7s(kkimg7s);
//			icbc_dk.setImgstep4_1ss(dkimg1);
//			icbc_dk.setImgstep4_2ss(dkimg2);
//			icbc_dk.setImgstep4_3ss(dkimg3);
//			icbc_dk.setImgstep4_4ss(dkimg4);
//			icbc_dk.setImgstep4_5ss(dkimg5);
//			icbc_dk.setImgstep5_1ss(dkimg6);
//			icbc_dk.setImgstep6_1ss(dkimg7);
//			icbc_dk.setImgstep7_1ss(dkimg8);
//            System.out.println("�_��");
//			System.out.println(jsonutil.toJSONString(icbc_kk));
//			System.out.println("�J��");
//			System.out.println(jsonutil.toJSONString(icbc_dk));
//			System.out.println("�溞");
//			System.out.println(jsonutil.toJSONString(icbc_mq));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        //�ϴ�һ���ļ�
//        for(int i=0;i<100;i++){
//        	 try {
//				Thread.sleep(1);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} 
//        	 System.out.println("lz"+System.currentTimeMillis());
//        }
       


        //��FTP������������һ���ļ�������һ���ַ���д�뵽���ļ���
//        try {
//            InputStream input = new ByteArrayInputStream("test ftp jyf".getBytes("GBK"));
//            boolean flag = FtpUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, fileName,input);;
//            System.out.println(flag);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        //����һ���ļ�
       // FtpUtil.downloadFtpFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, localPath, fileName);
    }
}