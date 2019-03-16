package com.controller.TrailernotAccepted;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.controller.Excel.UploadExcelController;
import com.model1.icbc.erp.PageData;
import com.service1.TrailernotAccepted.TrailernotAcceptedService;
import com.service1.sxx.VehicleMortgageService;
import com.util.limitutil;
import com.util.Excel.CommonUtil;

@Controller
@RequestMapping("/trailernotAcceptedController")
public class TrailernotAcceptedController {
	private static Logger log = LogManager.getLogger(TrailernotAcceptedController.class.getName());
	@Autowired
	private TrailernotAcceptedService trailernotAcceptedService;

	/**
	 * ��ѯ�б�ҳ�������ݲ�ģ����ѯ
	 * @param pagesize
	 * @param pagenow
	 * @param dn
	 * @param qn
	 * @param cn
	 * @param type
	 * @param param
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/select")
	public String selectTrailernotAccepted(
			Integer pagesize,
			Integer pagenow,
			String dn,
			String qn,
			String cn,
			String type,
			String param,
			HttpServletRequest request
			)throws UnsupportedEncodingException{
		
		//��ȡ��ǰ��������Ϣ
		PageData pdsession= (PageData)request.getSession().getAttribute("pd");
		System.out.println("--------+:"+pdsession);
		int fsid = Integer.parseInt(pdsession.get("fs_id").toString());
		int fs_id = Integer.parseInt(pdsession.get("fs_id").toString());

			
		int ps = 0;
		int pn = 0;
		if (pagesize != null && !pagesize.equals("")) {
			ps = pagesize;
		} else {
			ps = 10;
		}
		if (pagenow != null && !pagenow.equals("")) {
			pn = pagenow;
		} else {
			pn = 1;
		}
		List<PageData> newpdList=trailernotAcceptedService.selectTrailernotAccepted(param,(pn-1)*ps,ps,fsid,fs_id);
		
		
		
		int totalsize=trailernotAcceptedService.count();
//		System.out.println("***************count:"+totalsize);
		int q=totalsize%ps;
		int totalpage=0;//��ҳ��
		if(q==0){
			totalpage=totalsize/ps;	    		
		}else{
			totalpage=totalsize/ps+1;
		} 

		
		request.setAttribute("dn", dn);
		request.setAttribute("qn", qn);
		request.setAttribute("type", type);
		request.setAttribute("totalpage",totalpage);
		request.setAttribute("pagesize",ps);
		request.setAttribute("pagenow",pn);
		request.setAttribute("totalsize",totalsize);
		request.setAttribute("newpdList", newpdList);
		log.info("���"+newpdList);
		
		return "kjs_icbc/index";
	}
	/**
	 * ��ѯ�б�ҳ�������ݲ�ģ����ѯ
	 * @param pagesize
	 * @param pagenow
	 * @param dn
	 * @param qn
	 * @param cn
	 * @param type
	 * @param param
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/select1")
	public String selectTrailernotAccepted1(
			Integer pagesize,
			Integer pagenow,
			String dn,
			String qn,
			String cn,
			String type,
			String param,
			HttpServletRequest request
			)throws UnsupportedEncodingException{
	
		//��ȡ��ǰ��������Ϣ
		PageData pdsession= (PageData)request.getSession().getAttribute("pd");
		System.out.println("--------+:"+pdsession);
		int fsid = Integer.parseInt(pdsession.get("fs_id").toString());
		int fs_id = Integer.parseInt(pdsession.get("fs_id").toString());

	
		int ps = 0;
		int pn = 0;
		if (pagesize != null && !pagesize.equals("")) {
			ps = pagesize;
		} else {
			ps = 10;
		}
		if (pagenow != null && !pagenow.equals("")) {
			pn = pagenow;
		} else {
			pn = 1;
		}
		List<PageData> newpdList=trailernotAcceptedService.selectTrailernotAccepted1(param,(pn-1)*ps,ps,fsid,fs_id);
		int totalsize=trailernotAcceptedService.count1();
//		System.out.println("***************count:"+totalsize);
		int q=totalsize%ps;
		int totalpage=0;//��ҳ��
		if(q==0){
			totalpage=totalsize/ps;	    		
		}else{
			totalpage=totalsize/ps+1;
		} 

		
		request.setAttribute("dn", dn);
		request.setAttribute("qn", qn);
		request.setAttribute("type", type);
		request.setAttribute("totalpage",totalpage);
		request.setAttribute("pagesize",ps);
		request.setAttribute("pagenow",pn);
		request.setAttribute("totalsize",totalsize);
		request.setAttribute("newpdList", newpdList);
		log.info("���"+newpdList);
		
		return "kjs_icbc/index";
	}
	/**
	 * ��ѯ�б�ҳ�������ݲ�ģ����ѯ
	 * @param pagesize
	 * @param pagenow
	 * @param dn
	 * @param qn
	 * @param cn
	 * @param type
	 * @param param
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/select2")
	public String selectTrailernotAccepted2(
			Integer pagesize,
			Integer pagenow,
			String dn,
			String qn,
			String cn,
			String type,
			String param,
			HttpServletRequest request
			)throws UnsupportedEncodingException{
		
		//��ȡ��ǰ��������Ϣ
		PageData pdsession= (PageData)request.getSession().getAttribute("pd");
		System.out.println("--------+:"+pdsession);
		int fsid = Integer.parseInt(pdsession.get("fs_id").toString());
		int fs_id = Integer.parseInt(pdsession.get("fs_id").toString());

		
		
		int ps = 0;
		int pn = 0;
		if (pagesize != null && !pagesize.equals("")) {
			ps = pagesize;
		} else {
			ps = 10;
		}
		if (pagenow != null && !pagenow.equals("")) {
			pn = pagenow;
		} else {
			pn = 1;
		}
		List<PageData> newpdList=trailernotAcceptedService.selectTrailernotAccepted2(param,(pn-1)*ps,ps,fsid,fs_id);
		int totalsize=trailernotAcceptedService.count2();
//		System.out.println("***************count:"+totalsize);
		int q=totalsize%ps;
		int totalpage=0;//��ҳ��
		if(q==0){
			totalpage=totalsize/ps;	    		
		}else{
			totalpage=totalsize/ps+1;
		} 

		
		request.setAttribute("dn", dn);
		request.setAttribute("qn", qn);
		request.setAttribute("type", type);
		request.setAttribute("totalpage",totalpage);
		request.setAttribute("pagesize",ps);
		request.setAttribute("pagenow",pn);
		request.setAttribute("totalsize",totalsize);
		request.setAttribute("newpdList", newpdList);
		log.info("���"+newpdList);
		
		return "kjs_icbc/index";
	}
	/**
	 * ��ѯ����
	 * @param c_cardno  ���֤��
	 * @return
	 */
	@RequestMapping("/selectdetail")
	public String selectdetail(
			String qn,
			String type,
			String dn,
			String c_cardno,  
			Integer icbc_id,
			HttpServletRequest request
			)throws ParseException{
		//��ȡ��½��Ϣ
		PageData pdLoginSession = (PageData)request.getSession().getAttribute("pd");
		Map<String, Object> grxxMap= trailernotAcceptedService.selectgrxx(icbc_id);
		Map<String, Object> clxxMap= trailernotAcceptedService.selectclxx(icbc_id);
		Map<String, Object> dkfaMap= trailernotAcceptedService.selectdkfa(icbc_id);

			System.out.println("dkfaMap:"+dkfaMap);
			//�ж�  �������֤�Ų�ѯʱ���ݲ�Ϊ��
			if(dkfaMap.get("icbc_id") != null && !dkfaMap.get("icbc_id").equals("")){
			//��ȡ��ǰǷ��
			//��Ϣ�ϼ�=����ϼ�*��1+���ʣ�
			//������
		if(null != dkfaMap.get("dk_total_price")){
			BigDecimal aa=new BigDecimal(dkfaMap.get("dk_total_price").toString());
			//����
			double lv=(double)dkfaMap.get("dk_lv")/100+1;
			BigDecimal bb=new BigDecimal(String.valueOf(lv));
			//����*����  �õ���Ϣ�ϼ�
			BigDecimal bxhj=aa.multiply(bb);//�˷�
//			log.info("��Ϣ�ϼ�------"+bxhj);
			dkfaMap.put("bxhj", bxhj);
		}
			}

		//��ѯ�ͻ���������
		List<Map> scheduleMap = trailernotAcceptedService.selectschedule(icbc_id);
			
		//��ѯ��¼������
		List<Map> inputMap = trailernotAcceptedService.selectinput(icbc_id);
		System.out.println("**/*/*/*/*/*/*/"+icbc_id);
		System.out.println("============"+grxxMap);
//		System.out.println("**********"+detailList);
		request.setAttribute("dn",dn);
		request.setAttribute("qn", qn);
		request.setAttribute("type", type); 
		request.setAttribute("grxxMap", grxxMap);
		request.setAttribute("clxxMap", clxxMap);
		request.setAttribute("dkfaMap", dkfaMap);
		request.setAttribute("scheduleMap", scheduleMap);
		request.setAttribute("inputMap", inputMap);
		return "kjs_icbc/index";
		   
	}
	/**
	 * ��ѯ����
	 * @param c_cardno  ���֤��
	 * @return
	 */
	@RequestMapping("/selectdetail1")
	public String selectdetail1(
			String qn,
			String type,
			String dn,
			String c_cardno,  
			Integer icbc_id,
			HttpServletRequest request
			)throws ParseException{
		//��ȡ��½��Ϣ
		PageData pdLoginSession = (PageData)request.getSession().getAttribute("pd");
		Map<String, Object> grxxMap= trailernotAcceptedService.selectgrxx1(icbc_id);
		Map<String, Object> clxxMap= trailernotAcceptedService.selectclxx1(icbc_id);
		Map<String, Object> dkfaMap= trailernotAcceptedService.selectdkfa(icbc_id);
		System.out.println("dkfaMap:"+dkfaMap);
		//�ж�  �������֤�Ų�ѯʱ���ݲ�Ϊ��
		if(dkfaMap.get("icbc_id") != null && !dkfaMap.get("icbc_id").equals("")){
		//��ȡ��ǰǷ��
		//��Ϣ�ϼ�=����ϼ�*��1+���ʣ�
		//������
	if(null != dkfaMap.get("dk_total_price")){
		BigDecimal aa=new BigDecimal(dkfaMap.get("dk_total_price").toString());
		//����
		double lv=(double)dkfaMap.get("dk_lv")/100+1;
		BigDecimal bb=new BigDecimal(String.valueOf(lv));
		//����*����  �õ���Ϣ�ϼ�
		BigDecimal bxhj=aa.multiply(bb);//�˷�
//		log.info("��Ϣ�ϼ�------"+bxhj);
		dkfaMap.put("bxhj", bxhj);
	}
		}
		//��ѯ�ͻ���������
		List<Map> scheduleMap = trailernotAcceptedService.selectschedule1(icbc_id);
			
		//��ѯ��¼������
		List<Map> inputMap = trailernotAcceptedService.selectinput1(icbc_id);
		System.out.println("**/*/*/*/*/*/*/"+icbc_id);
		System.out.println("============"+grxxMap);
//		System.out.println("**********"+detailList);
		request.setAttribute("dn",dn);
		request.setAttribute("qn", qn);
		request.setAttribute("type", type); 
		request.setAttribute("grxxMap", grxxMap);
		request.setAttribute("clxxMap", clxxMap);
		request.setAttribute("dkfaMap", dkfaMap);
		request.setAttribute("scheduleMap", scheduleMap);
		request.setAttribute("inputMap", inputMap);
		return "kjs_icbc/index";
		   
	}
	/**
	 * ��ѯ����
	 * @param c_cardno  ���֤��
	 * @return
	 */
	@RequestMapping("/selectdetail2")
	public String selectdetail2(
			String qn,
			String type,
			String dn,
			String c_cardno,  
			Integer icbc_id,
			HttpServletRequest request
			)throws ParseException{
		//��ȡ��½��Ϣ
		PageData pdLoginSession = (PageData)request.getSession().getAttribute("pd");
		Map<String, Object> grxxMap= trailernotAcceptedService.selectgrxx2(icbc_id);
		Map<String, Object> clxxMap= trailernotAcceptedService.selectclxx2(icbc_id);
		Map<String, Object> dkfaMap= trailernotAcceptedService.selectdkfa(icbc_id);
		System.out.println("dkfaMap:"+dkfaMap);
		//�ж�  �������֤�Ų�ѯʱ���ݲ�Ϊ��
		if(dkfaMap.get("icbc_id") != null && !dkfaMap.get("icbc_id").equals("")){
		//��ȡ��ǰǷ��
		//��Ϣ�ϼ�=����ϼ�*��1+���ʣ�
		//������
	if(null != dkfaMap.get("dk_total_price")){
		BigDecimal aa=new BigDecimal(dkfaMap.get("dk_total_price").toString());
		//����
		double lv=(double)dkfaMap.get("dk_lv")/100+1;
		BigDecimal bb=new BigDecimal(String.valueOf(lv));
		//����*����  �õ���Ϣ�ϼ�
		BigDecimal bxhj=aa.multiply(bb);//�˷�
//		log.info("��Ϣ�ϼ�------"+bxhj);
		dkfaMap.put("bxhj", bxhj);
	}
		}
		//��ѯ�ͻ���������
		List<Map> scheduleMap = trailernotAcceptedService.selectschedule2(icbc_id);
			
		//��ѯ��¼������
		List<Map> inputMap = trailernotAcceptedService.selectinput2(icbc_id);
		System.out.println("**/*/*/*/*/*/*/"+icbc_id);
		System.out.println("============"+grxxMap);
//		System.out.println("**********"+detailList);
		request.setAttribute("dn",dn);
		request.setAttribute("qn", qn);
		request.setAttribute("type", type); 
		request.setAttribute("grxxMap", grxxMap);
		request.setAttribute("clxxMap", clxxMap);
		request.setAttribute("dkfaMap", dkfaMap);
		request.setAttribute("scheduleMap", scheduleMap);
		request.setAttribute("inputMap", inputMap);
		return "kjs_icbc/index";
		   
	}
	
	
	/**
	 * ������ݵ�����
	 * @param map
	 * @return
	 */
	@RequestMapping("/add")
	@Transactional
	public String add(
			String c_cardno,
			String qn,
			String cn,
			String type,
			String dn,
			String value,
			HttpServletRequest request,
			Integer icbc_id,	
			HttpServletResponse response) throws IOException,ParseException{
		
		//��ȡ��½��Ϣ
		PageData pdLoginSession = (PageData)request.getSession().getAttribute("pd");

		
		//���¼�����ݵ�����
		Map<String, Object> map=new HashMap<>();
		map.put("value", value);
		//��ȡ�ͻ����������֤��
		Map<String,Object> naMap = trailernotAcceptedService.selectgrxx(icbc_id);
		map.put("operator", pdLoginSession.get("name"));
		map.put("icbc_id", icbc_id);
		map.put("oc_name", naMap.get("c_name"));
		map.put("id_card", naMap.get("c_cardno"));
		trailernotAcceptedService.addTc1(map);
		trailernotAcceptedService.updateTcStatus(icbc_id);
				
		request.setAttribute("dn",dn);
		request.setAttribute("cn", cn);
		request.setAttribute("qn", qn);
		request.setAttribute("type", type);
		return "kjs_icbc/index";
	}
	/**
	 * ������ݵ�����
	 * @param map
	 * @return
	 */
	@RequestMapping("/add1")
	@Transactional
	public String add1(
			String c_cardno,
			String qn,
			String cn,
			String type,
			String dn,
			String value,
			HttpServletRequest request,
			Integer icbc_id,	
			@RequestParam(value = "add_video", required = false) MultipartFile file,
			HttpServletResponse response) throws IOException,ParseException{

		System.out.println(file);
		//��ȡ��½��Ϣ
		PageData pdLoginSession = (PageData)request.getSession().getAttribute("pd");		
		
		//���¼�����ݵ�����
		Map<String, Object> map=new HashMap<>();
		map.put("value", request.getParameter("value"));
		map.put("add_time",request.getParameter("add_time"));
		map.put("add_address",request.getParameter("add_address"));
		
		String headImg=null;//�����ļ���
		if(file != null && !file.isEmpty()){
			headImg = file.getOriginalFilename();
			System.out.println("�ļ���"+headImg);
			//�����ϴ�Ŀ¼���ļ����󣬲��������Զ�����
			Date date=new Date();
			String filePath="/KCDIMG/assess/upload/"+new SimpleDateFormat("yyyy/MM/dd/").format(date);
			String path=request.getSession().getServletContext().getRealPath(filePath);
		    File fileDir = new File(path);  
		    if (!fileDir.exists()) { //��������� �򴴽�   
		        fileDir.mkdirs();  
		    }  
			
			File imgFile=new File(path,headImg);
			System.out.println("===================="+imgFile);
			map.put("add_video", headImg);
			//�����ļ�
			try {
				file.transferTo(imgFile);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
		//��ȡ�ͻ����������֤��
		Map<String,Object> naMap = trailernotAcceptedService.selectgrxx1(icbc_id);
		map.put("operator", pdLoginSession.get("name"));
		map.put("icbc_id", icbc_id);
		map.put("oc_name", naMap.get("c_name"));
		map.put("id_card", naMap.get("c_cardno"));
		trailernotAcceptedService.addTc2(map);
		trailernotAcceptedService.updateTcStatus1(icbc_id);

		
		request.setAttribute("dn",dn);
		request.setAttribute("cn", cn);
		request.setAttribute("qn", qn);
		request.setAttribute("type", type);
		return "kjs_icbc/index";
	}
	/**
	 * ������ݵ�����
	 * @param map
	 * @return
	 */
	@RequestMapping("/add2")
	@Transactional
	public String add2(
			String c_cardno,
			String qn,
			String cn,
			String type,
			String dn,
			String value,
			HttpServletRequest request,
			Integer icbc_id,	
			HttpServletResponse response) throws IOException,ParseException{

		//��ȡ��½��Ϣ
		PageData pdLoginSession = (PageData)request.getSession().getAttribute("pd");
		//���¼�����ݵ�����
		Map<String, Object> map=new HashMap<>();
		map.put("value", value);
		//��ȡ�ͻ����������֤��
		Map<String,Object> naMap = trailernotAcceptedService.selectgrxx2(icbc_id);
		map.put("operator", pdLoginSession.get("name"));
		map.put("icbc_id", icbc_id);
		map.put("oc_name", naMap.get("c_name"));
		map.put("id_card", naMap.get("c_cardno"));
		trailernotAcceptedService.addTc3(map);
//		trailernotAcceptedService.updateTcStatus(icbc_id);

		
		request.setAttribute("dn",dn);
		request.setAttribute("cn", cn);
		request.setAttribute("qn", qn);
		request.setAttribute("type", type);
		return "kjs_icbc/index";
	}

	private void requestParams(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}
	
	//��ѯ��¼������
	@RequestMapping("/selectjll")
	@ResponseBody
	public Map selectjll(int id,HttpServletRequest request){
		System.out.println("-----------id:"+id);
		Map<String, Object> value = trailernotAcceptedService.selectjll(id);	
		System.out.println("-------value:"+value);
		return value;
	}
	
	
}
