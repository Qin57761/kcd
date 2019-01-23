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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSON;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PRAcroForm.FieldInformation;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopyFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.service1.htpdf.IcbcApplicationService;
import com.util.duoying.MD5;
/**
 * @author LiWang
 */
public class DocumentHandler2 {
	private WordDisposition wd = null;//ʵ�����ڲ���
	private File file = null;//world������Ŀ¼
	//C:/Users/Administrator/Desktop/word/haha1/  /KCDIMG/assess/upload/
	//�������������pdf�ĵ�ַ
	private String stair_file="/KCDIMG/assess/";
	private String savepdfpath="upload/"+new SimpleDateFormat("yyyy/MM/dd/").format(new Date());
	private static String pdftemplatepath=null;//ģ��ȫ·��
	private final static String  PTCATALOG="/htpdf";//ģ��pdf�Ĵ��Ŀ¼
	private String sessionid;
	private int progress = 0;//�ļ�����
	private final static String[] DPDF={"1.pdf","55.pdf","9.pdf","10.pdf","11.pdf","18.pdf","20.pdf","21.pdf","25.1.pdf","25.pdf","26.pdf","5.pdf","2.pdf","27.pdf","28.pdf","29.pdf","30.pdf","31.pdf"};
	private final static String[] NPDF={"3.pdf","4.pdf","39.pdf","51.pdf"};
	public DocumentHandler2() {
		stair_file=stair_file+savepdfpath;//���·��
		wd = new WordDisposition();
	}
	/**�����
	 * @author:LiWang 
	 */
	public class WordDisposition {
		 int code = 1;// codeΪ0ʧ�� 1Ϊ�ɹ�
		 String message = "ѹ���������ɣ�";// ��ʾ��Ϣ
		private String loadf;
		private Map<String, String> map = new HashMap<String, String>();
		public int getCode() {return code;}
		public void setCode(int code) {this.code = code;}
		public String getMessage() {return message;}
		public void setMessage(String message) {this.message = message;}
		public String getLoadf() {return loadf;}
		public void setLoadf(String loadf) {this.loadf = loadf;}
		public Map<String, String> getMap() {return map;}
		public void setMap(Map<String, String> map) {this.map = map;}
	}
	public static String readPath(HttpServletRequest request,String s){
		return request.getSession().getServletContext().getRealPath(s);
	}
	//��������ֻʹ�� list ��addAll()����������
	@SuppressWarnings("unchecked")
	public String fillTemplatePDF1(IcbcApplicationService ias,String s,HttpServletResponse response,HttpServletRequest request){
		List<Map> l=null;//��ѯ�Ľ��
		Map map=null;//ʧ����Ϣ
		int ii=0;
		if(s!=null && !s.equals("")){
			try {
				ii=Integer.parseInt(s);
				l=(List) ias.query1(ii);
				if(l.size()<=0){
					wd.code=0;
		    		wd.message="���û���Ϣ��ѯ����";
		    		return JSON.toJSONString(wd);
		    	}
				map=l.get(0);
			} catch (Exception e) {
				//��Ϊ����
				wd.code=0;
	    		wd.message="������ʽ����!"+s;	
	    		return JSON.toJSONString(wd);
			}
		}else{
			//��Ϊ����
			wd.code=0;
    		wd.message="���������!";	
    		return JSON.toJSONString(wd);
		}
		//map
		if(map.size()<=0){
			wd.code=0;
    		wd.message="���û���Ϣ��ѯ������";
    	}else{
    		sessionid=request.getSession().getId();
    		DataConversion.dataDisposeToMap(map,wd);
    		if(wd.code==0){//���ݴ������ ֱ�ӷ���
    			 return  JSON.toJSON(wd).toString();
    		}
    		List<String> l1=new ArrayList<String>();
    		List<String> l2=new  ArrayList<String>();
    		int iii=1;
    		if(Integer.parseInt(map.get("loanamount").toString())<150000){
    			l1.add("43.pdf");//��������
    			if(!map.get("gid").toString().equals("")){
    				l1.add("14.pdf");//������һ�ʹ��ַȷ����
    				l1.add("15.pdf");//������һ�ʹ��ַȷ����
    				l1.add("7.pdf");//������һ��ż��ͬ�����ŵ��
    				l2.add("47.pdf");//������һ��������
    				l2.add("53.pdf");//������һס���ʲ�֤��
        			iii++;
        		}
        		if(!map.get("gid2").toString().equals("")){
        			l1.add("16.pdf");//�����˶��ʹ��ַȷ����
        			l1.add("17.pdf");//�����˶��ʹ��ַȷ����
        			l1.add("8.pdf");//�����˶���ͬ�����ŵ��
        			l2.add("49.pdf");//�����˶���������
        			l2.add("54.pdf");//�����˶�ס���ʲ�֤��
        			iii++;
        		}
        		if(!map.get("pIDnumber").toString().equals("")){
        			l1.add("12.pdf");//�ʹ��ַȷ����
        			l1.add("13.pdf");//�ʹ��ַȷ����
        			l1.add("6.pdf");//��ͬ�����ŵ��
        			l1.add("45.pdf");//��������
        			l2.add("52.pdf");//��ż�����ʷ��ʲ�֤��
        			l1.remove("18.pdf");
        			iii++;
        			if(!map.get("gid").toString().equals("") && map.get("gid2").toString().equals("") ){
        				l1.add("19.pdf");//����˵����
        			}
        		}
    		}else{
    			l2.add("44.pdf");//����н������֤��
    			if(!map.get("gid").toString().equals("")){
    				l1.add("14.pdf");//������һ�ʹ��ַȷ����
    				l1.add("15.pdf");//������һ�ʹ��ַȷ����
    				l1.add("7.pdf");//������һ��ż��ͬ�����ŵ��
    				l2.add("48.pdf");//������һ����н������֤��
    				l2.add("53.pdf");//������һס���ʲ�֤��
        			iii++;
        		}
        		if(!map.get("gid2").toString().equals("")){
        			l1.add("16.pdf");//�����˶��ʹ��ַȷ����
        			l1.add("17.pdf");//�����˶��ʹ��ַȷ����
        			l1.add("8.pdf");//�����˶���ͬ�����ŵ��
        			l2.add("50.pdf");//�����˶�����н������֤��
        			l2.add("54.pdf");//�����˶�ס���ʲ�֤��
        			iii++;
        		}
        		if(!map.get("pIDnumber").toString().equals("")){
        			l1.add("12.pdf");//�ʹ��ַȷ����
        			l1.add("13.pdf");//�ʹ��ַȷ����
        			l1.add("6.pdf");//��ͬ�����ŵ��
        			l2.add("46.pdf");//����н������֤��
        			l2.add("52.pdf");//��ż�����ʷ��ʲ�֤��
        			l1.remove("18.pdf");
        			iii++;
        			if(!map.get("gid").toString().equals("") && map.get("gid2").toString().equals("") ){
        				l1.add("19.pdf");//����˵����
        			}
        		}
    		}
    		if(map.get("dztype").toString().equals("2")){//2���� 1������
    			l1.add("34.pdf");//����ݽ𴸰����ʹ�ã�����
    			l1.add("35.pdf");//ί�л�����Ȩ�����ʹ�� ������
    			l1.add("36.pdf");//ί�д���Э�������ʹ�� ������
    		}
    		if(iii>2){
    			l2.add("40.pdf");//���˵��
    		}
    		
    		//���֤md5һ��
    		String md5p=MD5.sign(map.get("IDnumber"),"UTF-8");
    		StringBuilder sbs=new StringBuilder(stair_file).append(md5p).append("1");
        	file = new File(sbs.toString());
    		if (file.exists()){
            	deleteDir(file);//������ɾ��
    		}
        	file.mkdirs();//����
            BaseFont bfChinese = null;
            try {
            	//��������
				bfChinese= BaseFont.createFont( "STSongStd-Light" ,"UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
			} catch (Exception e1) {
				wd.code=0;
  	            wd.setMessage("��������쳣!");
  	            DocumentHandler2.deleteDir(file);//ɾ�������ʱpdfĿ¼��Ŀ¼�µ��ļ�
  	            return  JSON.toJSON(wd).toString();
			};
		
			if(pdftemplatepath==null){
				pdftemplatepath=readPath(request,PTCATALOG);//pdfģ��·��
			}
		
			copys(NPDF,wd,sbs);
			pdfs(DPDF,wd,sbs,map,bfChinese);
			copys(l2.toArray(new String[l2.size()]),wd,sbs);
			pdfs(l1.toArray(new String[l1.size()]),wd,sbs,map,bfChinese);
        	 if(wd.map.size()>=(l1.size()+l2.size())){//ȫ��ʧ��
         		wd.message="pdfû�����ɣ�";
         		wd.code=0;
         	}else{
         	   addn();//�������
     		   File[] arr = file.listFiles();
     		   arr=sort(arr);//����
     		  StringBuilder  pdfpath=new StringBuilder(stair_file).append(md5p).append(".pdf");
     		   	try {
     			   merge(arr,pdfpath.toString(),wd.map);
     			   String s8=new StringBuilder(savepdfpath).append(md5p).append(".pdf").toString();
     			   wd.setLoadf(s8);
     			   addn();//������
     			   ias.create1(s8,ii);
     		    } catch (Exception e) {
     				wd.message="pdf�����쳣!";
     	    		wd.code=0;
     	    		DocumentHandler2.deleteDir(new File(pdfpath.toString()));
     			}
         	}
    	}
    	//���۳ɹ�����ʧ�� ɾ��world
    	try {
    		ProgressSingleton.remove(sessionid);
		} catch (NullPointerException e) {
			// ��Ϊnull
		}
		return JSON.toJSONString(wd); 
	  }
		//����
		public static void copys(String[] l2,WordDisposition wd,StringBuilder sbs){
			File f=null;
			File newPath=null;
			for(String sss0:l2){
				f=new File(pdftemplatepath+"/"+sss0);
				try {
					newPath=new File(new StringBuilder(sbs.toString()).append("/").append(sss0).toString());
					copyFile(f,newPath);
				} catch (IOException e){
					 wd.code=0;
	  	             wd.map.put(f.getName(),"�����쳣!");
	  	             continue;
				}
			}
		}
		//pdf���
		public static void pdfs(String[] l1,WordDisposition wd,StringBuilder sbs,Map map,BaseFont bfChinese){
			File f=null;
			 for (String sss1:l1) { 
      		   	    f=new File(pdftemplatepath+"/"+sss1);
	                PdfStamper stamp = null;
	     		    PdfReader reader = null;
					try {
						reader = new PdfReader(new FileInputStream(f));
					} catch (IOException e){
						 wd.code=0;
		  	             wd.map.put(f.getName(),"����ģ������쳣!");
		  	             continue;
					}
	     		    FileOutputStream outputstream = null;
					try {
						outputstream = new FileOutputStream(new StringBuilder(sbs.toString()).append("/").append(sss1).toString());
					} catch (FileNotFoundException e) {
						 wd.code=0;
		  	             wd.map.put(f.getName(),"���ģ������쳣!");
		  	             continue;
					}
	     		    try {
						stamp = new PdfStamper(reader,outputstream);
					} catch (Exception e) {
						wd.code=0;
		  	            wd.map.put(f.getName(),"ģ��ѹĤ�쳣!");
		  	            continue;
					}
	     		    AcroFields form = stamp.getAcroFields();
	     		    stamp.setFormFlattening(true);
	     		    Collection collection = map.keySet();
	     		    Object[] keyArray = collection.toArray();
	     		    boolean fff=true;
	     		       f:for (Iterator i = reader.getAcroForm().getFields().iterator(); i.hasNext();) {
	 	     		      FieldInformation field = (FieldInformation) i.next();
	 	     		      String fieldName = field.getName();
	 	     		      b:for (int j = 0; j < keyArray.length; j++) {
	 	     		        String key = (String) keyArray[j];
	 	     		        String value = map.get(key).toString();
	 	     		        if (fieldName.equals(key)) {
	 	     		        	try {
	 	     		        		if(fff&&fieldName.equals("tt")&&!value.equals("")){
		 	     		      	        int pageNo = form.getFieldPositions(fieldName).get(0).page;
		 	     		      	        Rectangle signRect = form.getFieldPositions(fieldName).get(0).position;
		 	     		      	        float x = signRect.getLeft();
		 	     		      	        float y = signRect.getBottom();
		 	     		      	        Image image = Image.getInstance(value);
		 	     		      	        // ��ȡ������ҳ��
		 	     		      	        PdfContentByte under = stamp.getOverContent(pageNo);
		 	     		      	        // ������Ĵ�С����ͼƬ
		 	     		      	        image.scaleToFit(signRect.getWidth(),signRect.getHeight());
		 	     		      	        // ���ͼƬ
		 	     		      	        image.setAbsolutePosition(x,y);
		 	     		      	        under.addImage(image);
		 	     		      	        fff=false;
		 	     		      	        break b;
		 	     		        	}
	 	     		        		form.setFieldProperty(fieldName,"textfont",bfChinese,null);
	 								form.setField(fieldName, value);
	 								break b;
	 							} catch (Exception e) {
	 								// TODO Auto-generated catch block
	 								wd.code=0;
	 				  	            wd.map.put(f.getName(),"ģ������ֶ��쳣!�ֶ�����Ϊ��"+fieldName+",ֵΪ��"+value);
	 				  	            break f;
	 							}
	 	     		        }
	 	     		      }
	 	     		    }
		     		    try {
							stamp.close();
							reader.close();
						} catch (Exception e) {
							continue;
						}
	        }
		}
		
		public static void merge(File[] filePathList,String mergeName,Map map) throws Exception{
			PdfCopyFields copyFields = null;
			copyFields = new PdfCopyFields(new FileOutputStream(mergeName));
			copyFields.open();
			for (int i = 0; i < filePathList.length; i++) {
				PdfReader reader = null;
				try {
					reader = new PdfReader(new FileInputStream(filePathList[i]));
				} catch (Exception e) {
					map.put(filePathList[i],"���pdf�ļ������쳣");
				} 
				try {
					copyFields.addDocument(reader);//����ļ�
				} catch (Exception e) {
					map.put(filePathList[i],"��Ͻ��pdf�ļ��쳣");
				}
			}
			copyFields.close();
		}
		 /**ѡ�������㷨
		 */
		public static File[] sort(File[] s){
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
    /**����
     * @param oldPath
     * @param newPath
     * @param map
     * @Description: TODO
     * @param name
     * @return 
     */
    public static void copyDir(File oldPath, File newPath,Map map){
        File[] filePath = oldPath.listFiles();
        if (!newPath.exists()) {
        	newPath.mkdir();
        }
        for (int i = 0; i < filePath.length; i++) {
        	//�����Ŀ¼
            if ((new File(oldPath+"/"+ filePath[i])).isDirectory()) {
            	copyDir(filePath[i],new File(newPath+"/"+ filePath[i]),map);
            }
            //������ļ�
            if (new File(oldPath+"/"+ filePath[i]).isFile()) {
                try {
					copyFile(filePath[i],new File(newPath+"/"+ filePath[i]));
				} catch (IOException e) {
					map.put(filePath[i], "����ʧ��");
					continue;
				}
            }
        }
    }
	public static void copyFile(File oldFile,File file) throws IOException {
        FileInputStream in = new FileInputStream(oldFile);
        FileOutputStream out = new FileOutputStream(file);;
        byte[] buffer=new byte[1024];
        while((in.read(buffer)) != -1){
            out.write(buffer);
        } 
    }

	public void addn() {
		progress += 1;
		ProgressSingleton.put(sessionid, progress);
	}
	/**
	 * ѹ��
	 * @param srcDir ѹ���ļ���·�� 
	 * @param out    ѹ���ļ������
	 * @param KeepDirStructure  �Ƿ���ԭ����Ŀ¼�ṹ,true:����Ŀ¼�ṹ; 
	 * 							false:�����ļ��ܵ�ѹ������Ŀ¼��(ע�⣺������Ŀ¼�ṹ���ܻ����ͬ���ļ�,��ѹ��ʧ��)
	 * @throws Exception 
	 */
	public static void toZip(File sourceFile, OutputStream out, boolean KeepDirStructure)
			throws Exception{
		ZipOutputStream zos = null;
		zos = new ZipOutputStream(out);
		compress(sourceFile,zos,sourceFile.getName(),KeepDirStructure);
		if(zos != null){
			try {
				zos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private static final int  BUFFER_SIZE = 2 * 1024;
	/**
	 * �ݹ�ѹ������
	 * @param sourceFile Դ�ļ�
	 * @param zos		 zip�����
	 * @param name		 ѹ���������
	 * @param KeepDirStructure  �Ƿ���ԭ����Ŀ¼�ṹ,true:����Ŀ¼�ṹ; 
	 * 							false:�����ļ��ܵ�ѹ������Ŀ¼��(ע�⣺������Ŀ¼�ṹ���ܻ����ͬ���ļ�,��ѹ��ʧ��)
	 * @throws Exception
	 */
	private static void compress(File sourceFile, ZipOutputStream zos, String name,
			boolean KeepDirStructure) throws Exception{
		byte[] buf = new byte[BUFFER_SIZE];
		if(sourceFile.isFile()){
			// ��zip����������һ��zipʵ�壬��������nameΪzipʵ����ļ�������
			zos.putNextEntry(new ZipEntry(name));
			// copy�ļ���zip�������
			int len;
			FileInputStream in = new FileInputStream(sourceFile);
			while ((len = in.read(buf)) != -1){
				zos.write(buf, 0, len);
			}
			// Complete the entry
			zos.closeEntry();
			in.close();
		} else {
			File[] listFiles = sourceFile.listFiles();
			if(listFiles == null || listFiles.length == 0){
				// ��Ҫ����ԭ�����ļ��ṹʱ,��Ҫ�Կ��ļ��н��д���
				if(KeepDirStructure){
					// ���ļ��еĴ���
					zos.putNextEntry(new ZipEntry(name + "/"));
					// û���ļ�������Ҫ�ļ���copy
					zos.closeEntry();
				}
				
			}else {
				for (File file : listFiles) {
					// �ж��Ƿ���Ҫ����ԭ�����ļ��ṹ
					if (KeepDirStructure) {
						// ע�⣺file.getName()ǰ����Ҫ���ϸ��ļ��е����ּ�һб��,
						// ��Ȼ���ѹ�����оͲ��ܱ���ԭ�����ļ��ṹ,���������ļ����ܵ�ѹ������Ŀ¼����
						compress(file, zos, name + "/" + file.getName(),KeepDirStructure);
					} else {
						compress(file, zos, file.getName(),KeepDirStructure);
					}
					
				}
			}
		}
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
	
	/**
	 * @param dir
	 * @return
	 * @Description: TODO
	 * @param name
	 * @return 
	 */
	private static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}
}

