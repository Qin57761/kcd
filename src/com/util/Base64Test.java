package com.util; 

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import javax.imageio.ImageIO;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;
public class Base64Test   
{  
	//���ڶ�ȡ��Ҫһ��ʱ�䣬���Բ��ܵ������ֽ����������������Ҫ�ж��Ƿ����
    public static byte[] readInputStream(InputStream inStream) throws Exception{  
    	//��Ŷ�ȡ�����е��ֽ�����
        ByteArrayOutputStream outStream = new ByteArrayOutputStream(); 
        byte[] buffer = new byte[1024]; 
        int len = 0; 
        while( (len=inStream.read(buffer)) != -1 ){ 
            outStream.write(buffer, 0, len); 
        } 
        inStream.close(); 
        return outStream.toByteArray(); 
    }  
	

	/**
	 * @Description: ����ͼƬ��ַת��Ϊbase64�����ַ���
	 * @Author: 
	 * @CreateTime: 
	 * @return
	 */
	public static String getImageStr(String imgFile) {
	    InputStream inputStream = null;
	    byte[] data = null;
	    try {
	        inputStream = new FileInputStream(imgFile);
	        data = new byte[inputStream.available()];
	        inputStream.read(data);
	        inputStream.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    // ����
	    BASE64Encoder encoder = new BASE64Encoder();
	    return encoder.encode(data);
	}

    //ͼƬת����base64�ַ���  
    public static String GetImageStr(String imgFile)  
    {//��ͼƬ�ļ�ת��Ϊ�ֽ������ַ��������������Base64���봦��  
       // String imgFile = "C:/Users/Administrator/Desktop/1/275-3-1280x800.jpg";//�������ͼƬ  
        InputStream in = null;  
        byte[] data = null;  
        //��ȡͼƬ�ֽ�����  
        try   
        {  
            in = new FileInputStream(imgFile);          
            data = new byte[in.available()];  
            in.read(data);  
            in.close();  
        }   
        catch (IOException e)   
        {  
            e.printStackTrace();  
        }  
        //���ֽ�����Base64����  
        BASE64Encoder encoder = new BASE64Encoder();  
       
        return encoder.encode(data);//����Base64��������ֽ������ַ���  
    }  
      
    //base64�ַ���ת����ͼƬ  
    public static String GenerateImage(String imgStr,String path)  
    
    
    {    	
    	//���ֽ������ַ�������Base64���벢����ͼƬ  
        if (imgStr == null){ //ͼ������Ϊ��  
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();  
        try   
        {  
            //Base64����  
            byte[] b = decoder.decodeBuffer(imgStr);  
            for(int i=0;i<b.length;++i)  
            {  
                if(b[i]<0)  
                {//�����쳣����  
                    b[i]+=256;  
                }  
            }  
            //����jpegͼƬ  
            pathutil putil=new pathutil();
            
            UUID randomUUID = UUID.randomUUID();
            String uuidName = randomUUID.toString().replaceAll("-","")+".jpg";
            String imgFilePath =uuidName;//�����ɵ�ͼƬ 
            String imgpath=path+pathutil.getPath().toString()+"img"+pathutil.getPath().toString()+creditutil.timefile().toString();
            File file=new File(imgpath);
            if(!file.exists()){
            	file.mkdirs();
   			 }
            OutputStream out = new FileOutputStream(imgpath+pathutil.getPath()+imgFilePath);        
            out.write(b);
            System.out.println(b);
            out.flush();  
            out.close();           
            return imgFilePath;  
        }   
        catch (Exception e)   
        {  
            return e.toString();  
        }  
    }  
    /** 
     * ������ͼƬ����Base64λ���� 
     *  
     * @param imgUrl 
     *            ͼƬ��url·������http://.....xx.jpg 
     * @return 
     */  
    public static String encodeImgageToBase64(URL imageUrl) {// ��ͼƬ�ļ�ת��Ϊ�ֽ������ַ��������������Base64���봦��  
        ByteArrayOutputStream outputStream = null;  
        try {  
            BufferedImage bufferedImage = ImageIO.read(imageUrl);  
            outputStream = new ByteArrayOutputStream();  
            ImageIO.write(bufferedImage, "jpg", outputStream);  
        } catch (MalformedURLException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        // ���ֽ�����Base64����  
        BASE64Encoder encoder = new BASE64Encoder();  
        return encoder.encode(outputStream.toByteArray());// ����Base64��������ֽ������ַ���  
    }
    /** 
     * ������ͼƬ����Base64λ���� 
     *  
     * @param imgUrl 
     *            ͼƬ��url·������http://.....xx.jpg 
     * @return 
     */  
    public static String encodeImgageToBase64(File imageFile) {// ��ͼƬ�ļ�ת��Ϊ�ֽ������ַ��������������Base64���봦��  
        ByteArrayOutputStream outputStream = null;  
        try {  
            BufferedImage bufferedImage = ImageIO.read(imageFile);  
            outputStream = new ByteArrayOutputStream();  
            ImageIO.write(bufferedImage, "jpg", outputStream);  
        } catch (MalformedURLException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        // ���ֽ�����Base64����  
        BASE64Encoder encoder = new BASE64Encoder();  
        return encoder.encode(outputStream.toByteArray());// ����Base64��������ֽ������ַ���  
    }  
  /**
   * 
   * 
   * 
   */
    
    public static void contentToTxt(String filePath, String content) {  
        String str = new String(); //ԭ��txt����  
        String s1 = new String();//���ݸ���  
        try {  
            File f = new File(filePath);  
            if (f.exists()) {  
                System.out.print("�ļ�����");  
            } else {  
                System.out.print("�ļ�������");  
                f.createNewFile();// �������򴴽�  
            }  
            BufferedReader input = new BufferedReader(new FileReader(f));  
  
            while ((str = input.readLine()) != null) {  
                s1 += str + "\n";  
            }  
            System.out.println(s1);  
            input.close();  
            s1 += content;  
  
            BufferedWriter output = new BufferedWriter(new FileWriter(f));  
            output.write(s1);  
            output.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
  
        }  
    }  
    /** 
     * ��Base64λ�����ͼƬ���н��룬�����浽ָ��Ŀ¼ 
     *  
     * @param base64 
     *            base64�����ͼƬ��Ϣ 
     * @return 
     */  
    public static void decodeBase64ToImage(String base64, String path,  
            String imgName) {  
        BASE64Decoder decoder = new BASE64Decoder();  
        try {  
        	byte[] decoderBytes =decoder.decodeBuffer(base64);
        	FileOutputStream write = new FileOutputStream(new File(path+"/"+imgName));
    		//�����ļ�������
    		//BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
    		//�����ļ������
    		//BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(write);    	
            //System.out.println(decoderBytes[1]);
            write.write(decoderBytes,0,decoderBytes.length);
            write.flush();
            //System.out.println(decoderBytes);
            write.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
    
    


    public static void main(String[] args)  
    {  
        String strImg = getImageStr("C:/Users/Administrator/Desktop/1/1.jpg"); 

      // System.out.println(strImg);  
//       String[]  s = [strImg];
//       readInputStream(strImg);
       //contentToTxt("C:/Users/Administrator/Desktop/1/base2.txt",strImg);
        
//        GenerateImage(strImg,"C:/Users/Administrator/Desktop/2");
//    	MultipartFile file;
//		file = (MultipartFile) new File("C:/Users/Administrator/Desktop/1/1.jpg");
		//String strImg = encodeImgageToBase64(file);
		 //System.out.println(strImg);  
//		 GenerateImage(strImg,"C:/Users/Administrator/Desktop/2");				 		 
	//decodeBase64ToImage(strImg,"F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/kcd/image/upload/img/"+creditutil.timefile(),"3.jpg");
      
    }  


} 
