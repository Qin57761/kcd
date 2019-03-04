package com.controller.businessPayApplication;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.joda.LocalDateParser;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itextpdf.text.log.SysoCounter;
import com.model1.icbc.erp.PageData;
import com.service1.businessPayApplication.BusinessPayService;
import com.util.limitutil;

@Controller
@RequestMapping("/businessPayApplicationController")
public class BusinessPayApplicationController {
	
	private static Logger log = LogManager.getLogger(BusinessPayApplicationController.class.getName());
	@Autowired
	private BusinessPayService businessPayService;
	
	/**
	 * @throws ParseException 
	 * ��ѯ����ҳ
	 * @throws 
	 */
	@RequestMapping("/select")
	public String select(
			String param,
			String qn,
			String type,
			String dn,	
			Integer pagesize,
			Integer pagenow,
			String c_cardno,
			String periods,
			HttpServletRequest request) throws UnsupportedEncodingException, ParseException{
		
//		List<PageData> newpdList=new ArrayList<>();
		
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
//		String param=request.getParameter("param");
//		if(StringUtils.isNotBlank(param)){
//			  param = new String(param.getBytes("ISO-8859-1"),"utf-8");
//			}
		List<PageData> buList=businessPayService.selectBusinessPay(param,(pn-1)*ps,ps);
		//System.out.println("_--------------------------param:"+param);
		for(PageData pd : buList){
			
			//�жϸ������֤�Ų�ѯʱ���ݲ�Ϊ��
			if(pd.get("c_cardno") != null && !pd.get("c_cardno").equals("")){
//				//�������֤�Ų�ѯimport_repayment�����Ϣ
				List<Map> imlist= businessPayService.selectdetail(pd.getString("c_cardno"),"");
				//�жϼ��ϲ�Ϊnull
				if(imlist.size()>0){
					//����δ��������	
					BigDecimal yqwhtotal = new BigDecimal("0");
					//����������Ԫ��
					for(Map map:imlist){
					double overdue_amount = (double) map.get("overdue_amount");
					//System.out.println("__________________"+overdue_amount);
					BigDecimal yqwhtotal1=new BigDecimal(String.valueOf(overdue_amount));
					//System.out.println("+++++++++++++++++++++"+yqwhtotal1);
					if(null != yqwhtotal1){
						//�õ����ڽ��
						yqwhtotal=yqwhtotal.add(yqwhtotal1);//���ڽ�����						
					}									
					}	
					//System.out.println("+++++++++++++++++++++"+yqwhtotal);
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
		//System.out.println("***************count:"+businessPayService.count());
		int totalsize=businessPayService.count();
		System.out.println("***************count:"+totalsize);
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
	
	/**
	 * ��ѯ����
	 * @throws ParseException 
	 */
	@RequestMapping("/selectdetail")
	public String selectdetail(
			String qn,
			String type,
			String dn,	
			String c_cardno,
			String periods,
			HttpServletRequest request) throws ParseException{
//		System.out.println("id_card------------------------"+c_cardno);
		List<Map> detailList=businessPayService.selectdetail(c_cardno,periods);
		System.out.println("*****************"+detailList);
		//�õ���ǰʱ��
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");	
		String localTime = df.format(LocalDateTime.now());
		System.out.println(localTime);	
		if(detailList.size()>0){
			for(Map mm : detailList){
				String days = null;
				System.out.println("----------------:"+mm.get("hk_time").toString());
				if(null != mm.get("hk_time").toString()){
				String hkdate=mm.get("hk_time").toString();
				System.out.println("hkdate:"+hkdate);
				int dd = localTime.compareTo(hkdate);
				
				if(dd > 0){
					System.out.println("��ǰʱ������ָ��ʱ��,�Ѿ�����ָ��ʱ��");
					//������������
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd");
					
					Date d1 = sdf.parse(localTime);
					Date d2 = sdf.parse(hkdate);
					Calendar cal = Calendar.getInstance();
					cal.setTime(d1);
					//��ǰʱ��
					long time1 = cal.getTimeInMillis(); 
					Calendar cal2 = Calendar.getInstance();
					cal.setTime(d2);
					//ָ��ʱ��
					long time2 = cal2.getTimeInMillis();
					long between_days = (time1-time2)/(10003600*24); 
					String sub = String.valueOf(between_days);
					days = sub.substring(1);
					System.out.println("����������"+days);				
				}else if(dd < 0){
					System.out.println("��ǰʱ������ָ��ʱ�䣬��û��ָ��ʱ��");
					days="��";
				}else{
					System.out.println("���");
					days=localTime;
				}
				mm.put("days", days);
				}
			}
		}
		
		request.setAttribute("detailList", detailList);
		request.setAttribute("dn", dn);
		request.setAttribute("qn", qn);
		request.setAttribute("type", type);
		return "kjs_icbc/index";
	}
	

	//��ѯ���
	@RequestMapping("/addconfirm1")
	@Transactional
	public String Addconfirm1(
			String c_cardno,
			String periods,
			String qn,
			String type,
			String dn,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException, ParseException{
					
			//�õ���ǰʱ��
			DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");	
			String localTime = df.format(LocalDateTime.now());
			System.out.println(localTime);	
			//��ѯ����
			List<Map> periodsMap = businessPayService.selectdetail(c_cardno,periods);
			String days = null;
			if(periodsMap.size()>0){
				for(Map mm : periodsMap){
					
					String hkdate=mm.get("hk_time").toString();

					int dd = localTime.compareTo(hkdate);
					if(dd > 0){
						System.out.println("��ǰʱ������ָ��ʱ��,�Ѿ�����ָ��ʱ��");
						//������������
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd");
						
						Date d1 = sdf.parse(localTime);
						Date d2 = sdf.parse(hkdate);
						Calendar cal = Calendar.getInstance();
						cal.setTime(d1);
						//��ǰʱ��
						long time1 = cal.getTimeInMillis(); 
						Calendar cal2 = Calendar.getInstance();
						cal.setTime(d2);
						//ָ��ʱ��
						long time2 = cal2.getTimeInMillis();
						long between_days = (time1-time2)/(10003600*24); 
						String sub = String.valueOf(between_days);
						days = sub.substring(1);
						System.out.println("����������"+days);	
					}else if(dd < 0){
						System.out.println("��ǰʱ������ָ��ʱ�䣬��û��ָ��ʱ��");
						days="��";
					}else{
						System.out.println("���");
						days=localTime;
					}
//					mm.put("days", days);
			
				}
			}
			
			int j = businessPayService.updateflag(c_cardno, periods,localTime,days);
			
			System.out.println("&&&&&&&&&&&&&"+c_cardno+"������������������������"+periods);
			
			Map<String, Object> CardnoMap = businessPayService.selectCardno(c_cardno);
			List<Map> uuList = businessPayService.selectdetail(c_cardno, periods);
			Map<String, Object> confirmMap = businessPayService.selectconfirm(c_cardno);
			if(null == confirmMap){
				//�ж����ݿ����Ƿ��Ѿ�����һ�������� 
				int i = businessPayService.addBusin(CardnoMap);				
			}		
			//�������
			businessPayService.addhk(uuList.get(0));	
		
			request.setAttribute("dn", dn);
			request.setAttribute("qn", qn);
			request.setAttribute("type", type);
			return "kjs_icbc/index";
		 
	}
	
	
	
}
