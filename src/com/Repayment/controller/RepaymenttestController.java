package com.Repayment.controller;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.model1.icbc.erp.PageData;
import com.service1.Repayment.RepaymentService;
import com.util.limitutil;
import com.util.pagedate;

@Controller
@RequestMapping("/repaymentController")
public class RepaymenttestController {
	
	private static Logger log = LogManager.getLogger(RepaymenttestController.class.getName());
	@Autowired
	private RepaymentService repaymentService;

	/**
	 * ��ѯ����ҳ
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/select")
	public String select(
			String qn,
			String cn,
			String type,
			String dn,	
			int pagesize,
			int pagenow,	
			HttpServletRequest request) throws UnsupportedEncodingException{
		requestParams(request);
		
		List<PageData> newpdList=new ArrayList<>();
		PageData pd=new PageData();
		pd.put("dn", dn);
      
		String param=request.getParameter("param");
		if(StringUtils.isNotBlank(param)){
		  param = new String(param.getBytes("ISO-8859-1"),"utf-8");
		}
		List<PageData> pdList=repaymentService.selectRepayment(param, pd);
		newpdList = limitutil.fy(pdList, pagesize, pagenow);
		int q=pdList.size()%pagesize;
		int totalpage=0;//��ҳ��
		if(q==0){
			totalpage=pdList.size()/pagesize;	    		
		}else{
			totalpage=pdList.size()/pagesize+1;
		} 
		
		
		for(int i = 0;i<pdList.size();i++){			
			Map map = pdList.get(i);
			log.info("map->"+map);
			if(null != map){
				//��Ϣ�ϼ�=����ϼ�*��1+���ʣ�
				BigDecimal aa = new BigDecimal(map.get("dk_total_price").toString());
				double lv = (double)map.get("dk_lv")/100 + 1;
				//����
				BigDecimal bb = new BigDecimal(String.valueOf(lv));
				BigDecimal ee = aa.multiply(bb);//�˷�
					
				log.info("��Ϣ�ϼ�->"+ee);
				//��������
				BigDecimal cc=new BigDecimal( map.get("aj_date").toString());
				//ÿ��Ӧ��=��Ϣ�ϼ�/��������
				BigDecimal dd = ee.divide(cc, 2, RoundingMode.HALF_UP);
				map.put("myyh", dd);
				BigDecimal icbc_pricecs = new BigDecimal(map.get("icbc_pricecs").toString()).multiply(new BigDecimal("10000"));
				String s=icbc_pricecs.toString();
				//ȥ������� 0
				if(s.indexOf(".")!=-1){
					 s = s.replaceAll("0+?$", "").replaceAll("[.]$", "");  
				}
				map.put("icbc_pricecs",s);
			}
			
			
		}		
		request.setAttribute("dn", dn);
		request.setAttribute("cn", cn);
		request.setAttribute("qn", qn);
		request.setAttribute("type", type);	
		request.setAttribute("totalpage",totalpage);
		request.setAttribute("totalsize",pdList.size());
		request.setAttribute("pagesize",pagesize);
		request.setAttribute("pagenow",pagenow);
		request.setAttribute("newpdList", newpdList);
		return "kjs_icbc/index";
	}
	//�򵥴�ӡ�����������
	public static void requestParams(HttpServletRequest request){
		Enumeration enu=request.getParameterNames();  
		while(enu.hasMoreElements()){  
			String paraName=(String)enu.nextElement();  
			log.info("������:"+paraName+",����ֵ��"+request.getParameter(paraName));
		}
	}
	
	//��ѯ��������Ϣ��
	@RequestMapping("/selectBorrow")
	public String selectBorrow(String qn
								,String cn
								,String type
								,String dn
								,HttpServletRequest request
								,Integer id
								,String c_cardno
								){
		
		System.out.println("id_card========================================="+c_cardno);
		
		Map<String, Object> lborrow = repaymentService.selectBorrow(id);
				
			log.info("map2->"+lborrow);
			if(null != lborrow){
				//��Ϣ�ϼ�=����ϼ�*��1+����
				BigDecimal mm = new BigDecimal(lborrow.get("dk_total_price").toString());
				double lv2 = (double)lborrow.get("dk_lv")/100 + 1;
				//����
				BigDecimal bb2 = new BigDecimal(String.valueOf(lv2));
				BigDecimal ee2 = mm.multiply(bb2);//�˷�
					
				log.info("��Ϣ�ϼ�->"+ee2);
				//��������
				BigDecimal cc2=new BigDecimal( lborrow.get("aj_date").toString());
				//ÿ��Ӧ��=��Ϣ�ϼ�/��������
				BigDecimal dd2 = ee2.divide(cc2, 2, RoundingMode.HALF_UP);
				lborrow.put("myyh", dd2);
				BigDecimal icbc_pricecs2 = new BigDecimal(lborrow.get("icbc_pricecs").toString()).multiply(new BigDecimal("10000"));
				String s=icbc_pricecs2.toString();
				//ȥ������� 0
				if(s.indexOf(".")!=-1){
					 s = s.replaceAll("0+?$", "").replaceAll("[.]$", ""); 
			}
				lborrow.put("icbc_pricecs",s);
			}
			
			
		//��ѯ����ƻ�
			Map<String, Object> mapschedule = repaymentService.selectschedule(c_cardno);
			
			log.info("map3->"+mapschedule);
			
			//ʵ�����
			BigDecimal b1 = null;
			BigDecimal b2 = null;
			BigDecimal b3 = null;
			if(null != mapschedule){
				 b1=new BigDecimal(mapschedule.get("balance_card").toString());
				System.out.println("456789"+b1);
				//���ڽ��
				 b2=new BigDecimal(mapschedule.get("overdue_amount").toString());
//					Ӧ�����=ʵ�����+���ڽ��
				 b3=b1.add(b2);
				 mapschedule.put("b3", b3);
			}
			

			
			
			
			//��ѯ������Ϣ
			List<Map> mapafter = repaymentService.selectafter(id);
			log.info("map4->"+mapafter);
//			System.out.println("234577980---------------------"+mapafter.get(0).get("c_name_gj2 "));
			
			//��ѯ��������Ϣ
			Map<String, Object> mapzdr = repaymentService.selectzdr(id);
			log.info("map5->"+mapzdr);
		request.setAttribute("dn", dn);
		request.setAttribute("cn", cn);
		request.setAttribute("qn", qn);
		request.setAttribute("type", type);
		request.setAttribute("lborrow", lborrow);
		request.setAttribute("mapschedule", mapschedule);
		request.setAttribute("mapafter", mapafter);
		request.setAttribute("mapzdr", mapzdr);
		return "kjs_icbc/index";
		
	}
	
	
	
}
