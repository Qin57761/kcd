package com.controller.newAdd;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.apache.commons.httpclient.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.model.apply;
import com.model.newAdd.pdfdownload;
import com.service.applyService;
import com.service.newAdd.PDFdownloadService;
import com.util.PDFUtil;
import com.util.Page;
import com.util.creditutil;
import com.util.newAdd.UrlFilesToZip;
import com.util.newAdd.downLoadFile;
import com.util.url.URLAvailability;
@Controller
public class PDFDownloadController {
	@Autowired
	private PDFdownloadService PDFdownloadservice;
	@Autowired
	private applyService applyService;
	//����������,ͨ������������PDF��Ȩ�飬�����鵽�����ļ���
	//������    ͨ��һ����������һ���ļ�
	@RequestMapping(value="/PDFDownloadd.do",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public void PDFDownloadd(HttpServletRequest request,HttpServletResponse response,
			String downloadCompany,String downloadNum,String downloadCzr){
		System.err.println(downloadCompany+"--"+downloadNum+"--"+downloadCzr);
		pdfdownload p = new pdfdownload();
		p.setDownloadCompany(downloadCompany);  // ���ع�˾
		p.setDownloadCzr(downloadCzr);  // ������Ա
		p.setDownloadNum(Integer.parseInt(downloadNum));  // ��������
		//p.setPDFurl(authorizeName);  //��������
		p.setDownloadTime(creditutil.time());  // ����ʱ��
		p.setUpdateTime(creditutil.time()); // ����޸�ʱ��
		try {
			downLoadFile.downLoadFromUrl(response,"authorizeName","111.jpg", "F://aa");
			//������ݵ����ݿ�
			PDFdownloadservice.addPDFdownload(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//�ļ��������   
	@RequestMapping(value="/Zip1.do")
	@ResponseBody
	public String Zip1(HttpServletRequest request,
			HttpServletResponse response,
			String downloadCompany,
			String downloadNum,
			String downloadCzr,
			String apply_address
			){
		String[] FileNameList = null;
		JSONObject result=new JSONObject();
		FileNameList = PDFdownloadservice.getFileName(1,Integer.parseInt(apply_address),0,Integer.parseInt(downloadNum));
		// ��ȫ�����ļ��Ž��ļ�������  File[]  files
		int leng = FileNameList.length;
		if(leng>=Integer.parseInt(downloadNum)){			
		for(int i=0;i<leng;i++){
			String oneFile=FileNameList[i];
			int k= URLAvailability.isConnect(oneFile);			
			if(k!=0){				
				System.out.println("true");
			}else{				
				applyService.delapply(oneFile);
				System.out.println("false");
			}
		}
		result.put("no",leng);
		result.put("code","1");
		}else{
			result.put("no",leng);
			result.put("code","0");
		}
		return result.toString();
	}

	//�ļ��������   
	@RequestMapping(value="/Zip.do",produces = "multipart/form-data;charset=UTF-8")
	public void Zip(HttpServletRequest request,
			HttpServletResponse response,
			String downloadCompany,
			String downloadNum,
			String downloadCzr,
			String apply_address
			) throws IOException{
		//System.out.println(downloadCompany+"----"+downloadNum+"----"+downloadCzr);
		String[] FileNameList = null; 
		//�����������
        String base_name = UUID.randomUUID().toString()+".zip";
		//�õ�Ҫ���ص��ļ�����
		FileNameList = PDFdownloadservice.getFileName(1,Integer.parseInt(apply_address),0,Integer.parseInt(downloadNum));
		// ��ȫ�����ļ��Ž��ļ�������  File[]  files
		int leng = FileNameList.length;
		InputStream inputStream = null;
		DataInputStream in = null;
		URL url=null;
		OutputStream os=null;
		ByteArrayOutputStream bos=null;
		try {
			   String filename = new String(base_name.getBytes("UTF-8"),"ISO8859-1");//�����ļ�������
			   bos = new ByteArrayOutputStream();
			   ZipOutputStream zos = new ZipOutputStream(bos);
			   UrlFilesToZip s = new UrlFilesToZip();
			   int idx = 1;
			   // int size=0;
			   for (String oneFile:FileNameList) {			    
			    byte[] bytes=null;			    
			    bytes = s.getImageFromURL(oneFile);				    
			    zos.putNextEntry(new ZipEntry("file"+idx+".pdf"));
			    zos.write(bytes, 0, bytes.length);
			    //size=size+bytes.length;
				zos.closeEntry();
			    idx++;	
			   }			
			   zos.close();
			   response.setContentType("application/force-download");// ����ǿ�����ز���
			   response.addHeader("Content-Disposition", "attachment;fileName="+filename);// �����ļ���			   			  
			   os = response.getOutputStream();
			   os.write(bos.toByteArray());	               			   
			   os.close();
			   bos.close();			   
				//������ؼ�¼�����ݿ�
				pdfdownload p = new pdfdownload();
				List codes = PDFdownloadservice.getFileCode(1,Integer.parseInt(apply_address),0,Integer.parseInt(downloadNum));
				String code = "";
				apply a=new apply();
				apply al=new apply();
				for(int i=0;i<codes.size();i++){
					code += (String)codes.get(i)+",";
				    if(codes.get(i)!=null&&!codes.get(i).equals("")){
				     a=applyService.findapplybycode(codes.get(i).toString());		    
				     al.setApply_id(a.getApply_id());
				     al.setAid(2);
				     applyService.upapply(al);
				    }	
				}		
				p.setPDFcode(code);
				p.setDownloadCompany(downloadCompany); //���ع�˾
				p.setDownloadCzr(downloadCzr);  //������Ա
				p.setDownloadNum(Integer.parseInt(downloadNum));  //��������
				p.setDownloadTime(creditutil.time());  //����ʱ��
				p.setUpdateTime(creditutil.time()); //����޸�ʱ��
				p.setAddtype(Integer.parseInt(apply_address));
				PDFdownloadservice.addPDFdownload(p);
				//request.setAttribute("error","���سɹ���");
		    } catch (Exception ex) {
			   ex.printStackTrace();
			   ServletOutputStream sos = null;  
               try {  
                   //response.setContentType("text/html;charset=utf-8");  
                   sos = response.getOutputStream();  
                   sos.write("<script>alert('����ʧ�ܣ��������Ա��ϵ~~');</script>".getBytes("UTF-8"));
                   sos.flush();  
               } catch (IOException e1) {  
                   e1.printStackTrace();  
               }finally{  
                   if(null != sos){  
                       try {  
                           sos.close();  
                       } catch (IOException e1) {  
                           e1.printStackTrace();  
                       }  
                   }  
               } 
		    }finally{
		    	if(os!=null){
		    		os.close();
		    	}
		    	if(bos!=null){
		    		bos.close();	
		    	}
		    	
		    }
	
	}
	
	/*//�ļ����ص���    
	@RequestMapping(value="/ZipOne.do",method=RequestMethod.POST,produces = "multipart/form-data;charset=UTF-8")
	@ResponseBody
	public String ZipOne(HttpServletRequest request,HttpServletResponse response,
			String authorizeName,String downloadCompany,String downloadNum,String downloadCzr
	    	){
		System.err.println(authorizeName+"--"+downloadCompany+"--"+downloadNum+"--"+downloadCzr);
		String[] FileNameList = null; 
		//�õ�Ҫ���ص��ļ�����
		FileNameList = PDFdownloadservice.getFileName(1,20,0,Integer.parseInt(downloadNum));
		// ��ȫ�����ļ��Ž��ļ�������  File[]  files
		int leng = FileNameList.length;
		InputStream inputStream = null;
		URL url=null;
		for(int i=0;i<FileNameList.length;i++){
			//�������ַ�����ɱ��ص�ַ
			try {
				url = new URL(FileNameList[i]);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				inputStream = connection.getInputStream();
				DataInputStream in = new DataInputStream(connection.getInputStream()); 
				////��ÿ���ļ�����һ���ļ�������///////
				File[] files = new File[FileNameList.length];
				File f = new File(FileNameList[i]);
				//////////////////
				response.setCharacterEncoding("utf-8");//���ñ���ͳһ
				response.setContentType("multipart/form-data");//����multipart
				//�����������
		        String base_name = UUID.randomUUID().toString();
		        response.setHeader("Content-disposition", "attachment;filename="+i+".pdf");
		        DataOutputStream out = new DataOutputStream(response.getOutputStream());  
				//DataOutputStream out = new DataOutputStream(new FileOutputStream("H:/"+base_name+".pdf"));  
				byte[] buffer = new byte[2048];  
				int count = 0;  
				while ((count = in.read(buffer)) > 0) {  
					out.write(buffer,0,count);  
				}  
				out.close();  
				in.close();  
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		//������ؼ�¼�����ݿ�
		pdfdownload p = new pdfdownload();
		p.setDownloadCompany(downloadCompany); //���ع�˾
		p.setDownloadCzr(downloadCzr);  //������Ա
		p.setDownloadNum(Integer.parseInt(downloadNum));  //��������
		p.setPDFurl(authorizeName);  //��������
		p.setDownloadTime(creditutil.time());  //����ʱ��
		p.setUpdateTime(creditutil.time()); //����޸�ʱ��
		try{
			PDFdownloadservice.add_PDFdownload(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "PDFDownload";
		
	}*/
	// ��ѯ"δ���ռ�"��"�ѻ��ռ�"  produces = "text/html;charset=UTF-8"
	@RequestMapping(value="/NoPDFDownloadd.do",produces = "multipart/form-data;charset=UTF-8")
	public String NoPDFDownloadd(HttpServletRequest request,HttpServletResponse response){
		//***************************************************************
		//��ҳ��ѯ
		String size=request.getParameter("size");
	    String pagenow=request.getParameter("pagenow");
	    int s;
	    if(size!=null){
	    	s=Integer.parseInt(size);
	    }else{
	    	s=10;
	    }	
	    int totalCount;
	    Page page;
    	//��ǰ̨ҳ���ȡ ״ֵ̬ 
  		int saa = Integer.parseInt(request.getParameter("sa").replace("?v=4.0", ""));
  		//���ڱ����ѯ��������
  		List<pdfdownload> list=new ArrayList<>();
	    totalCount =PDFdownloadservice.PDFCounts(saa);
	    if (pagenow!=null) {				
			//System.out.println(0);
			page = new Page(totalCount, Integer.parseInt(pagenow),s);
			list=this.PDFdownloadservice.NoPDFdownload(saa, page.getStartPos(),page.getPageSize());									
		} else {
			//System.out.println(1);
			page = new Page(totalCount, 1,s);					
			list=this.PDFdownloadservice.NoPDFdownload(saa, page.getStartPos(),page.getPageSize());				   
			pagenow="1";
		}				
		int q=totalCount%s;
    	int w=0;
    	if(q==0){
    		w=totalCount/s;
    	}else{
    		w=totalCount/s+1;
    	}    		
		//�õ� δ���ռ�   
		/*list = PDFdownloadservice.NoPDFdownload(saa,0,10);*/
    	request.setAttribute("w",w);
    	request.setAttribute("pagenow",pagenow);
    	request.setAttribute("size",s);
		request.setAttribute("totalCount",totalCount);
    	request.setAttribute("list", list);	    
		request.setAttribute("sa", saa);
		// ����ָ��ҳ��
		if(saa == 0){
			return "getNoFileSuccess";
		}else{
			return "getFileSuccess";
		}
	}
	// �޸�"δ���ռ�"��Ϊ"�ѻ��ռ�"״̬    produces = "multipart/form-data;charset=UTF-8"
	@RequestMapping(value="/updataPDFS.do",produces = "text/html;charset=UTF-8")
	public void updataPDFS(HttpServletRequest request,HttpServletResponse response){
		//��ǰ̨ҳ���ȡ ״ֵ̬ 
		int pid = Integer.parseInt(request.getParameter("pid"));
		int s = Integer.parseInt(request.getParameter("s"));
		System.err.println(pid+"*****"+s);
		pdfdownload p = new pdfdownload();
		p.setStatus(s);
		p.setPDFdownload_id(pid);
		PDFdownloadservice.updatePDFdownload(p);
		// ����ָ��ҳ��
		//return "getNoFileSuccess";
	}
	//������ѯ
	@RequestMapping(value="/selectByCompanyOrCode.do",produces = "multipart/form-data;charset=UTF-8")
	public String selectByCompanyOrCode(HttpServletRequest request,HttpServletResponse response,String sele){
		//��ǰ̨ҳ���ȡ ״ֵ̬ 
		String company = request.getParameter("sele");
		int saa = Integer.parseInt(request.getParameter("saa").replace("?v=4.0", ""));
		System.err.println(saa+"******************************"+company);
		//���ڱ����ѯ��������
		List<pdfdownload> plist = new ArrayList(); 
		//�õ� δ���ռ�   
		plist = PDFdownloadservice.selectByCompanyOrCode(saa,company);
		// �Ѳ�ѯ�������ݴ���ǰ̨ҳ��
		request.setAttribute("list", plist);	
		// ����ָ��ҳ��
		if(saa == 0){
			return "getNoFileSuccess";
		}else{
			return "getFileSuccess";
		}
	}
	
	
}
