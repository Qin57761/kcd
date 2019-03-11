package com.controller.matEndiwmentResult;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.controller.businessPayApplication.BusinessPayApplicationController;
import com.model1.icbc.erp.PageData;
import com.service1.businessPayApplication.BusinessPayService;
import com.service1.matEndiwmentResult.MatEndiwmentResultService;
import com.util.limitutil;

@Controller
@RequestMapping("/matEndiwmentResultControoller")
public class MatEndiwmentResultControoller {
	
	private static Logger log = LogManager.getLogger(BusinessPayApplicationController.class.getName());
	@Autowired
	private MatEndiwmentResultService matEndiwmentResultService;
	
	@RequestMapping("/select")
	public String select(String qn,
			String param,
			String type,
			String dn,	
			Integer pagesize,
			Integer pagenow,
			String id_card,
			HttpServletRequest request) throws UnsupportedEncodingException{
//			List<PageData> newpdList=new ArrayList<>();
			
//			String param=request.getParameter("param");
//			if(StringUtils.isNotBlank(param)){
//			  param = new String(param.getBytes("ISO-8859-1"),"utf-8");
//			}
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
			List<PageData> buList=matEndiwmentResultService.selectMat(param,(pn-1)*ps,ps);
				
			for(PageData pd : buList){
			
			//�жϸ������֤�Ų�ѯʱ���ݲ�Ϊ��
			if(pd.get("id_card") != null && !pd.get("id_card").equals("")){
				//�������֤�Ų�ѯimport_repayment�����Ϣ
				List<Map> imlist= matEndiwmentResultService.selectdetail(pd.getString("id_card"));
//				�жϼ��ϲ�Ϊnull
				if(imlist.size()>0){
					//����δ��������	
					BigDecimal yqwhtotal = new BigDecimal("0");			
					//����������Ԫ��
					for(Map map:imlist){
					double overdue_amount = (double) map.get("overdue_amount");
					BigDecimal yqwhtotal1=new BigDecimal(String.valueOf(overdue_amount));
					if(null != yqwhtotal1){
						//�õ����ڽ��
						yqwhtotal=yqwhtotal.add(yqwhtotal1);//���ڽ�����
					}									
					}			
					pd.put("yqwhtotal", yqwhtotal);
					
					}
				
				//��ȡ��ǰǷ��
				//��Ϣ�ϼ�=����ϼ�*��1+���ʣ�
				//������
				if(null != pd.get("dk_total_price")){
					BigDecimal aa = new BigDecimal(pd.get("dk_total_price").toString());
					//����
					double lv = (double)pd.get("dk_lv")/100 + 1;
					BigDecimal bb = new BigDecimal(String.valueOf(lv));
					//����*���� �õ���Ϣ�ϼ�
					BigDecimal ee = aa.multiply(bb);//�˷�
					log.info("��Ϣ�ϼ�->"+ee);
					//��������
					BigDecimal cc=new BigDecimal( pd.get("aj_date").toString());
					//ÿ��Ӧ��=��Ϣ�ϼ�/��������
					if(cc.compareTo(BigDecimal.ZERO) != 0){
						
						BigDecimal myyh = ee.divide(cc, 2, RoundingMode.HALF_UP);
						//ÿ��Ӧ��*�ѻ�����=�ѻ�
						BigDecimal b3=new BigDecimal(pd.get("jd_count").toString());
						//�õ��ѻ�Ǯ��
						BigDecimal yh = myyh.multiply(b3);
						//�����ܶ�-�ѻ�Ǯ��
						BigDecimal dqqktotal = aa.subtract(yh);//�õ���ǰǷ��
						
						pd.put("dqqktotal", dqqktotal);
					}
				}	
			}		
		}
			int totalsize=matEndiwmentResultService.count();
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
		request.setAttribute("totalsize",totalsize);
		request.setAttribute("pagesize",ps);
		request.setAttribute("pagenow",pn);
		request.setAttribute("newpdList", buList);
		return "kjs_icbc/index";
		
	}
	
	@RequestMapping("/selectDetail")
	public String selectDetail(String qn,
			String type,
			String dn,	
			String id_card,
			String periods,
			HttpServletRequest request){
		
		List<Map> matMap = matEndiwmentResultService.selectDetail(id_card,periods);
				
		request.setAttribute("dn", dn);
		request.setAttribute("qn", qn);
		request.setAttribute("type", type);	
		request.setAttribute("matMap",matMap);
		return "kjs_icbc/index";
	}
	
	@RequestMapping("/addAgree")
	@Transactional
	public String addAgree(String c_cardno,
			String periods,
			String qn,
			String type,
			String dn,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		
		//��ȡ��ǰ����
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");	
		String localTime = df.format(LocalDateTime.now());
		System.out.println(localTime);	
		//�޸�״̬���Ѵ�������
		matEndiwmentResultService.updateflag(c_cardno, periods,localTime);		
		System.out.println("&&&&&&&&&&&&&"+c_cardno+"������������������������"+periods);
		//��ѯ����
		List<Map> agreeList = matEndiwmentResultService.selectDetail(c_cardno, periods);
		System.out.println("++++++++++++++++++++"+agreeList);
		//���ǰ�Ĳ�ѯ
		Map<String, Object> cardnoMap = matEndiwmentResultService.selectid_card(c_cardno);
		System.out.println("---------- -------"+cardnoMap);
		List<Map> afreeMap = matEndiwmentResultService.selectAfree(c_cardno);
		if(afreeMap.size() == 0){
			matEndiwmentResultService.addMat(cardnoMap);
		}
		//�������
		matEndiwmentResultService.addhk(agreeList.get(0));
		//�޸ĵ�����ǰ��ѯ����
		String count = matEndiwmentResultService.selectcount(c_cardno);
		System.out.println("count____________:"+count);
		//�޸�����
		matEndiwmentResultService.updatecount(c_cardno, count);
		matEndiwmentResultService.updatecount2(c_cardno, count);
		//�޸�import_repayment����״̬ ���֤�ţ�����
		matEndiwmentResultService.updatestate(c_cardno, periods,localTime);
		request.setAttribute("dn", dn);
		request.setAttribute("qn", qn);
		request.setAttribute("type", type);
		return "kjs_icbc/index";
	}
	
}
