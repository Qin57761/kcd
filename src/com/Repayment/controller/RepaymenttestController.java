package com.Repayment.controller;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.model1.icbc.erp.PageData;
import com.service1.Repayment.RepaymentService;
import com.util.limitutil;

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
			String param,
			String qn,
			String type,
			String dn,	
			int pagesize,
			int pagenow,	
			HttpServletRequest request) throws UnsupportedEncodingException{
		requestParams(request);
		
		List<PageData> newpdList=new ArrayList<>();
		PageData pd=new PageData();
		pd.put("dn", dn);
//      
//		String param=request.getParameter("param");
//		if(StringUtils.isNotBlank(param)){
//		  param = new String(param.getBytes("ISO-8859-1"),"utf-8");
//		}
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
			double myyh=0;
			if(null != map){
				//�ܶ�
				BigDecimal aa = new BigDecimal(map.get("dk_total_price").toString());

				//��������
				BigDecimal cc=new BigDecimal( map.get("aj_date").toString());
				
				//ÿ��Ӧ��=�ܶ�/��������
				BigDecimal dd2 = aa.divide(cc, 3,BigDecimal.ROUND_DOWN);
				String a1 = dd2.toString();//ת��string
				System.out.println(a1);
				System.out.println(a1.length());//��ȡ����
				System.out.println(a1.substring(a1.indexOf(".")+3, a1.length()));
				String bb = a1.substring(a1.indexOf(".")+3, a1.length());//��ȡ����λС��
				System.out.println("----bb��"+bb);
				int c1 = Integer.parseInt(bb);//ת��int
				if(c1 > 0){//�ж��Ƿ����0  �Ǿͱ�����λС��
					BigDecimal vv = dd2.setScale(2, BigDecimal.ROUND_DOWN);
					BigDecimal zero = new BigDecimal("0.01");
					myyh = vv.add(zero).doubleValue();
				}else{
					BigDecimal vv = dd2.setScale(2, BigDecimal.ROUND_DOWN);
					myyh=vv.doubleValue();
					System.out.println("----ÿ��Ӧ��2��"+myyh);
				}
						
				map.put("myyh", myyh);
				BigDecimal icbc_pricecs = new BigDecimal(map.get("icbc_pricecs").toString());
				String s=icbc_pricecs.toString();
				//ȥ������� 0
				if(s.indexOf(".")!=-1){
					 s = s.replaceAll("0+?$", "").replaceAll("[.]$", "");  
				}
				map.put("icbc_pricecs",s);
			}
			
			
		}		
		request.setAttribute("dn", dn);
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
								,String type
								,String dn
								,HttpServletRequest request
								,String c_cardno
								,String icbc_id
								){
		
		Map<String, Object> lborrow = repaymentService.selectBorrow(icbc_id);
				
			log.info("map2->"+lborrow);
			double yy=0;
			if(null != lborrow){
				//�ܶ�
				BigDecimal mm = new BigDecimal(lborrow.get("dk_total_price").toString());
				System.out.println("-----mm:"+mm);
				//��������
				BigDecimal cc2=new BigDecimal( lborrow.get("aj_date").toString());
				System.out.println("------cc2:"+cc2);
				//ÿ��Ӧ��=��Ϣ�ϼ�/��������
				BigDecimal dd2 = mm.divide(cc2, 3,BigDecimal.ROUND_DOWN);//������λС��
				System.out.println("------dd2:"+dd2);
				String aa = dd2.toString();//ת��string
				System.out.println(aa);
				System.out.println(aa.length());//��ȡ����
				System.out.println(aa.substring(aa.indexOf(".")+3, aa.length()));
				String bb = aa.substring(aa.indexOf(".")+3, aa.length());//��ȡ����λС��
				int cc = Integer.parseInt(bb);//ת��int
				if(cc > 0){//�ж��Ƿ����0  �Ǿͱ�����λС��
					BigDecimal vv = dd2.setScale(2, BigDecimal.ROUND_DOWN);	
					BigDecimal zero = new BigDecimal("0.01");
					yy = vv.add(zero).doubleValue();
				}else{
					BigDecimal vv = dd2.setScale(2, BigDecimal.ROUND_DOWN);
					yy=vv.doubleValue();
				}
				lborrow.put("myyh", yy);
				System.out.println("-----yy:"+yy);
				BigDecimal icbc_pricecs2 = new BigDecimal(lborrow.get("icbc_pricecs").toString());
				String s=icbc_pricecs2.toString();
				//ȥ������� 0
				if(s.indexOf(".")!=-1){
					 s = s.replaceAll("0+?$", "").replaceAll("[.]$", ""); 
			}
				lborrow.put("icbc_pricecs",s);
			}
			
			
		//��ѯ����ƻ�
			List<Map> mapschedule = repaymentService.selectschedule(icbc_id);
			
			log.info("map3->"+mapschedule);
		//��ѯimport_repayment
			//��ѯ������Ϣ
			List<Map> mapafter = repaymentService.selectafter(icbc_id);
			log.info("map4->"+mapafter);
//			System.out.println("234577980---------------------"+mapafter.get(0).get("c_name_gj2 "));
			
			//��ѯ��������Ϣ
			Map<String, Object> mapzdr = repaymentService.selectzdr(icbc_id);
			log.info("map5->"+mapzdr);
		request.setAttribute("dn", dn);
		request.setAttribute("qn", qn);
		request.setAttribute("type", type);
		request.setAttribute("lborrow", lborrow);
		request.setAttribute("mapschedule", mapschedule);
		request.setAttribute("mapafter", mapafter);
		request.setAttribute("mapzdr", mapzdr);
		return "kjs_icbc/index";
		
	}
	//��ѯ�޸Ļ����¼
	public void selectImport(){
		List<Map> repayMap = repaymentService.selectimport();
		Map<String, Object> map=new HashMap<>();
		for(int i=0;i<repayMap.size();i++){
			map.put("practical_money", Math.round((double) repayMap.get(i).get("balance_card")*100)/100.00);
			map.put("overdue_money", Math.round((double) repayMap.get(i).get("overdue_amount")*100)/100.00);
			map.put("practical_date", repayMap.get(i).get("add_time"));
			map.put("cardno", repayMap.get(i).get("id_card"));
			if(Math.round((double) repayMap.get(i).get("overdue_amount")) != 0){
				map.put("overdue", 1);
			}else{
				map.put("overdue", 0);
			}
			repaymentService.updateschedule(map);
		}
		

	}
	
	
}
