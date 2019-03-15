package com.controller.settle;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
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

import com.alibaba.fastjson.JSON;
import com.controller.Excel.UploadExcelController;
import com.model1.icbc.erp.PageData;


import com.util.limitutil;
import com.util.Excel.CommonUtil;

@Controller
@RequestMapping("/settleController")
public class settleController {
	private static Logger log = LogManager.getLogger(settleController.class.getName());
	@Autowired
	private com.service1.settle.settleService settleService;

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
	public String selectsettle(
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
		List<PageData> newpdList=settleService.selectsettle(param,(pn-1)*ps,ps,fsid,fs_id);
		int totalsize=settleService.count();
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
		Map<String, Object> grxxMap= settleService.selectgrxx(icbc_id);
		Map<String, Object> clxxMap= settleService.selectclxx(icbc_id);
		Map<String, Object> dkfaMap= settleService.selectdkfa(icbc_id);

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
		List<Map> scheduleMap = settleService.selectschedule(icbc_id);
			
		//��ѯ��¼������
		List<Map> inputMap = settleService.selectinput(icbc_id);
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
//		//��ѯ����ҳ
//		Map<String, Object> grxxMap=trailernotAcceptedService.selectgrxx(icbc_id);
//		Map<String, Object> clxxMap=trailernotAcceptedService.selectclxx(icbc_id);
		
		//���¼�����ݵ�����
		Map<String, Object> map=new HashMap<>();
		map.put("value", value);
		//��ȡ�ͻ����������֤��
				Map<String,Object> naMap = settleService.selectgrxx(icbc_id);
				map.put("operator", pdLoginSession.get("name"));
				map.put("icbc_id", icbc_id);
				map.put("oc_name", naMap.get("c_name"));
				map.put("id_card", naMap.get("c_cardno"));

				settleService.addTc1(map);
			
		System.out.println("qwertyuiowjk    "+map);
		
		request.setAttribute("dn",dn);
		request.setAttribute("cn", cn);
		request.setAttribute("qn", qn);
		request.setAttribute("type", type);
		return "kjs_icbc/index";
	}

	

	private void requestParams(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}
	
}
