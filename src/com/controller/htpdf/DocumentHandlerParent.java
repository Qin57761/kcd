package com.controller.htpdf;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PRAcroForm.FieldInformation;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopyFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.util.duoying.MD5;
/**
 * @author 
 * �״򹤾� ���еĳ�ʼ���������� ����ֻ��¶��򵥵�ʵ�� �����ǰѺ�ͬ������һ���ļ����У�Ȼ���ٰ��ļ����еĺ�ͬ�ϲ�
 * �粻�������ļ���(��ʱ�ļ�)������ByteArrayOutputStream����
 */
public abstract class DocumentHandlerParent{
	private static Logger log = LogManager.getLogger(DocumentHandlerParent.class.getName());
	//����Ŀ¼ File����
	protected File file = null;
	
	//��Ŀ¼ �������ȫ�ֵ� ����ͼƬ��.pdf�������Ŀ¼���¼�
	public static String root_Directory="DIMG/assess/";
	public static String download_prefix="http://a.kcway.net/assess/";
	//public static String root_Directory="C:/Users/Administrator/Desktop/word/haha1/";
	
	//ƴ���м�Ŀ¼
	private String savepdfpath="upload/"+new SimpleDateFormat("yyyy/MM/dd/").format(new Date());
	//һ��Ŀ¼ ��.pdf�ļ� �ϼ�Ŀ¼
	private String stair_file;//��:DIMG/assess/upload\2019\01\14
	//ģ��pdf�Ĵ��Ŀ¼ ��WebContent�µ�htpdf
	private String templateDirectory;
	//����ʱģ��ȫĿ¼
	protected String pdftemplatepath;
	//����key
	private String sessionid;
	//���֤md5�����ΪĿ¼��Ψһ��ʶ
	private String md5p;
	private static BaseFont bfChinese;//��������
	//���ݼ�
	protected Map map;
	//�ļ���ʼ����
	private int progress = 0;
	
	//����main
//	public static void main(String[] args){
//		 File file=new File(root_Directory);
//		 System.out.print(file.getPath());
//	 }
	protected abstract String fillTemplate() throws Exception;
	static{
		try {
			bfChinese= BaseFont.createFont( "STSongStd-Light" ,"UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
		} catch (DocumentException | IOException e) {
			// TODO Auto-generated catch block
			log.error("�������������쳣->"+e.getMessage());
		}
	}
	public DocumentHandlerParent(String templateDirectory,HttpServletRequest request,Map map) {
		this.templateDirectory=templateDirectory;
		this.sessionid=request.getSession().getId();
		this.map=map;
        //��ȡpdfģ��Ŀ¼
		this.pdftemplatepath=readPath(request,templateDirectory);
		this.stair_file=root_Directory+savepdfpath;//���·��
		this.deleteSecondLevel();
	}
	//ǰ��ִ�� ɾ������Ŀ¼ 
	public void deleteSecondLevel(){
		//�ļ������� ���֤��-����id
    	file = new File(new StringBuilder(stair_file).append(map.get("IDnumber")).append("��").append(map.get("bankId").toString()).toString());
    	if (file.exists()){
			DeleteFile.deleteDir(file);//ɾ����ʷ��
		}
		//�����˳���·����ָ����Ŀ¼���������б��赫�����ڵĸ�Ŀ¼
    	//���ҽ����˳���·������ʾ���ļ���Ŀ¼����ʱ������ true�����򷵻� false 
		if(file.mkdirs()){
			fileAccessAuthority(file);
		}
	} 
	//https://blog.csdn.net/u014457793/article/details/24638673
	public static void fileAccessAuthority(File file){
		try {
			//https://www.aliyun.com/jiaocheng/165962.html
			//��PHP��java��Ϸ���һ̨�ļ����������,���ֵ������ǵ�java��̨������Ŀ¼php���޷������ļ���д�롣
			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime.exec("chmod 777 -R" + file.getAbsolutePath());
			/*���µ�ǰ�̵߳ȴ������б�Ҫ��һֱҪ�ȵ��ɸ� Process �����ʾ�Ľ����Ѿ���ֹ���������ֹ���ӽ��̣��˷����������ء����û����ֹ���ӽ��̣����õ��߳̽���������ֱ���˳��ӽ��̡� =
			���أ�
			���̵ĳ���ֵ�����ݹ�����0 ��ʾ������ֹ�� 
			�׳��� 
			InterruptedException - �����ǰ�߳��ڵȴ�ʱ����һ�߳��жϣ���ֹͣ�ȴ����׳� InterruptedException��*/
			boolean issuccess = proc.waitFor() == 0;
			log.info("�Ƿ������˳��߳�->"+issuccess);
		} catch (IOException e) {
			log.info("�����ļ��Ķ�Ȩ��error->"+e+",·��:"+file.getAbsolutePath());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			log.info("�˳��߳�error->"+e);
		} 
    	file.setExecutable(true); // true����ִ�в���; false���ǽ�ֹ���� 
    	file.setReadable(true); // true���������; false���ǽ�ֹ���� 
    	file.setWritable(true); // true����д����; false���ǽ�ֹ����
    	log.info("is execute allow : " + file.canExecute());
    	log.info("is read allow : " + file.canRead());
    	log.info("is write allow : " + file.canWrite());
	}
	//���ִ��
	protected String endAssembly(){
		File[] arr = file.listFiles();
	    arr=sort(arr);//����
	    //���ص��ļ���
	    md5p=MD5.sign(file.getName(),"UTF-8");
	    //���pdf�ļ�ȫ·�� 
	    StringBuilder pdfpath=new StringBuilder(stair_file).append(md5p).append(".pdf");
	   	try {
	   	  //��Ͻ��pdf
		  merge(arr,pdfpath.toString());
		  //���ظ��ͻ��˵�����·��
		  String s8=new StringBuilder(savepdfpath).append(md5p).append(".pdf").toString();
		  return s8;
	    } catch (Exception e) {
	    	DeleteFile.deleteDir(new File(pdfpath.toString()));
    		log.error("pdf�����쳣->"+e);
    		throw new RuntimeException("pdf�����쳣");
		}finally {
			//���۳ɹ�����ʧ�� ɾ��world
	    	try {
	    		ProgressSingleton.remove(sessionid);
			} catch (NullPointerException e) {
				//��Ϊnull
			}
		}
	}
	//pdf��� .pdf�ļ��� ���Ŀ¼ ���ݼ�
	protected void pdfs(String[] l1){
		 for (String sss1:l1) { 
			 pdf(sss1);
		 }
	}
	protected void pdf(String sss1){
		File f=new File(pdftemplatepath+"/"+sss1);
        PdfStamper stamp = null;
		    PdfReader reader = null;
		try {
			reader = new PdfReader(new FileInputStream(f));
		} catch (IOException e){
		   log.error("����ģ������쳣->"+e);
	       throw new RuntimeException("����ģ������쳣!");
		}
		    FileOutputStream outputstream = null;
		try {
			outputstream = new FileOutputStream(new StringBuilder(getFilePath()).append("/").append(sss1).toString());
		} catch (FileNotFoundException e) { 
		   log.error("���ģ������쳣->"+e);
	       throw new RuntimeException("���ģ������쳣!");
		}
		try {
			stamp = new PdfStamper(reader,outputstream);
		} catch (Exception e) {
		  log.error("ģ��ѹĤ�쳣!->"+e);
	          throw new RuntimeException("ģ��ѹĤ�쳣!");
		}
		    /*try {	*/
			    AcroFields form = stamp.getAcroFields();
			    stamp.setFormFlattening(true);
			    log.info("����->fileName="+sss1);
			  
			     for (Iterator i = reader.getAcroForm().getFields().iterator(); i.hasNext();) {
	  		      FieldInformation field = (FieldInformation) i.next();
	  		      String fieldName = field.getName();
	  		      Object value=map.get(fieldName);
	  		      if(fieldName.indexOf("fill")==-1 && -1==fieldName.indexOf("undefined")){//����ԭ����ͬfill_�����ͬ�г��ֵ�Ĭ�ϵ��ı���
	  		    	if(null !=value && !value.toString().equals("")){
		     		    	
		     		    	try {
		     		    		//���Ϊtt ���������к���img���ʾΪͼƬ 
		     		        	if(fieldName.equals("tt") || fieldName.indexOf("img")!=-1 ){
		     		        		log.info("�����Ƭ����:"+fieldName+",ֵ:"+value);
		     		      	        int pageNo = form.getFieldPositions(fieldName).get(0).page;
		     		      	        Rectangle signRect = form.getFieldPositions(fieldName).get(0).position;
		     		      	        float x = signRect.getLeft();
		     		      	        float y = signRect.getBottom();
		     		      	        Image image = Image.getInstance(value.toString());
		     		      	        // ��ȡ������ҳ��
		     		      	        PdfContentByte under = stamp.getOverContent(pageNo);
		     		      	        // ������Ĵ�С����ͼƬ
		     		      	        image.scaleToFit(signRect.getWidth(),signRect.getHeight());
		     		      	        // ���ͼƬ
		     		      	        image.setAbsolutePosition(x,y);
		     		      	        under.addImage(image);
		     		      	        continue;
		     		        	}
		     		        	log.info("����ֶ�����:"+fieldName+",ֵ:"+value);
		     		        		form.setFieldProperty(fieldName,"textfont",bfChinese,null);
									form.setField(fieldName, value.toString());
								} catch (Exception e) {
					  	            throw new RuntimeException("ģ������ֶ��쳣!�ֶ�����Ϊ��"+fieldName+",ֵΪ��"+value);
								}
		     		      }
	  		    	
	  		      }
	  		    }
	 		    try {
					stamp.close();
					reader.close();
				} catch (Exception e) {
					
				}
//			} catch (Exception e) {
//				log.error("pdf����ֶ��쳣"+e);
//				//throw new RuntimeException("pdf����ֶ��쳣");
//			}
	}
	//����+
	protected void addn() {
		progress += 1;
		ProgressSingleton.put(sessionid, progress);
	}
	//���pdf���·��
	private String getFilePath(){
		return file.getPath();
	}
	//pdf�Զ��忽��
	public void copys(String[] l2){
		for(String sss0:l2){
			copy(sss0);
		}
	}
	protected void copy(String sss0){
		File copyPath=new File(pdftemplatepath+"/"+sss0);
		try {
			File newPath=new File(new StringBuilder(getFilePath()).append("/").append(sss0).toString());
			log.info("����->"+copyPath.getName()+"--to--"+newPath.getName());
			CopyFile.copyFile(copyPath,newPath);
		} catch (IOException e){
		   log.error("�����쳣->"+e.getMessage());
	       throw new RuntimeException("�ļ������쳣:"+sss0); 
		}
	}
	//pdf����
	private static void merge(File[] filePathList,String mergeName) throws Exception{
		PdfCopyFields copyFields = null;
		File file=new File(mergeName);
		//���ҽ��������ھ��д˳���·����ָ�����Ƶ��ļ�ʱ�����ɷֵش���һ���µĿ��ļ�������ļ��Ƿ���ڣ�
		//���ָ�����ļ������ڲ��ɹ��ش������򷵻� true�����ָ�����ļ��Ѿ����ڣ��򷵻� false 
		if (file.createNewFile()){
			fileAccessAuthority(file);
		}
		copyFields = new PdfCopyFields(new FileOutputStream(file));
		copyFields.open();
		for (int i = 0; i < filePathList.length; i++) {
			try {
				PdfReader reader= new PdfReader(new FileInputStream(filePathList[i]));
				copyFields.addDocument(reader);//����ļ�
			} catch (Exception e) {
				new RuntimeException("pdf�����쳣->"+filePathList[i].getName());
			}
		}
		copyFields.close();
	}
	protected static int getLetterFirstIndex(String s){
		int index=0;
		for(int i=0;i<s.length();i++){
			char c=s.charAt(i);
			if('A'<c&&c<'z'){//��һ��Ӣ�ĵ�λ��
				index=s.indexOf(c);
				break;
			}
		}
		return index;
	}
	private static String readPath(HttpServletRequest request,String s){
		/*request.getSession().getServletContext() ��ȡ����Servlet���������൱��tomcat�����ˡ�
		getRealPath("/") ��ȡʵ��·������/��ָ����Ŀ��Ŀ¼�����Դ��뷵�ص�����Ŀ�������е�ʵ�ʷ������еĸ�·��*/
		return request.getSession().getServletContext().getRealPath(s);
	}
	//ѡ�������㷨
	private static File[] sort(File[] s){
        //�м�ֵ
        File temp =null;
        //��ѭ��:����Ϊ��С����,��0~����-1
        for(int j = 0; j<s.length-1;j++){
            //��Сֵ:�����һ����������С��
            String min = s[j].getName();
            //��¼��С�����±��
            int minIndex=j;
            //��ѭ��:������Ϊ����С�����ͺ������һ�������бȽ�
            for(int k=j+1;k<s.length;k++){
                //�ҵ���Сֵ
                if (Integer.parseInt(min.substring(0,min.indexOf(".")))>Integer.parseInt(s[k].getName().substring(0,s[k].getName().indexOf(".")))){
                    //�޸���С
                    min=s[k].getName();
                    minIndex=k;
                }
            }
            //���˳��ڲ�ѭ�����ҵ���ε���Сֵ
            //����λ��
            temp = s[j];
            s[j]=s[minIndex];
            s[minIndex]=temp;
        }
        return s;
	}
	/**
	 * ������� 
	 * @param file
	 * @param response
	 * @return
	 */
	public static HttpServletResponse downloadZip(File file, HttpServletResponse response) {
		if (file.exists() == false) { // ������
			//response.getOutputStream().write("<script>alert('�ļ���ɾ���������´����');</script>".getBytes("UTF-8"));
			return null;
		} else {
			try {
				// ��������ʽ�����ļ���
				InputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
				byte[] buffer = new byte[fis.available()];
				fis.read(buffer);
				fis.close();
				// ���response
				response.reset();
				OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
				response.setContentType("application/octet-stream");

				// �������������������ļ����ڴ˴���Ҫ��URLEncoder.encode�������д���
				response.setHeader("Content-Disposition",
						"attachment;filename=" + new String(file.getName().getBytes("GB2312"), "ISO8859-1"));
				toClient.write(buffer);
				toClient.flush();
				toClient.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return response;
	}
}

