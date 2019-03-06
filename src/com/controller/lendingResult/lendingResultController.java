package com.controller.lendingResult;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.fontbox.util.BoundingBox;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.controller.Excel.UploadExcelController;
import com.controller.erp_icbc.base.BaseController;
import com.model1.icbc.erp.PageData;
import com.service1.lendingResult.lendingResultService;
import com.util.limitutil;

@Controller
@RequestMapping("/lendingResultController")
public class lendingResultController extends BaseController{
	private static Logger log = LogManager.getLogger(lendingResultController.class.getName());
	@Autowired
	private lendingResultService lendingResultService;
	
	/**
	 * ��ѯ�������ݲ���ҳ   ģ����ѯ
	 * @return
	 */
	@RequestMapping("/select")	
	public String select(String qn,String cn,String type,String dn,
			int pagesize,
			int pagenow,
			String c_cardno,
			String periods,
			HttpServletRequest request
			) throws UnsupportedEncodingException{
		
		List<PageData> newpdList=new ArrayList<>();
		PageData pd=new PageData();
		pd.put("dn", dn);
		String param=request.getParameter("param");
		List<PageData> l1=lendingResultService.selectlendingResult(param, pd);
		for(PageData pd1 : l1){
			//�ж�  �������֤�Ų�ѯʱ���ݲ�Ϊ��
			if(pd1.get("c_cardno") != null && !pd1.get("c_cardno").equals("")){
				
				//��ȡ��ǰǷ��
				//��Ϣ�ϼ�=����ϼ�*��1+���ʣ�
				//������
				if(null != pd1.get("dk_total_price")){
					BigDecimal aa=new BigDecimal(pd1.get("dk_total_price").toString());
					//����
					double lv=(double)pd1.get("dk_lv")/100+1;
					BigDecimal bb=new BigDecimal(String.valueOf(lv));
					//����*����  �õ���Ϣ�ϼ�
					BigDecimal ee=aa.multiply(bb);//�˷�
//					log.info("��Ϣ�ϼ�------"+ee);
					//��������
					BigDecimal cc=new BigDecimal(pd1.get("aj_date").toString());
					//ÿ��Ӧ��=��Ϣ�ϼ�/��������
					if(cc.compareTo(BigDecimal.ZERO)!= 0){
						BigDecimal myyh=ee.divide(cc,2,RoundingMode.HALF_UP);
						//ÿ��Ӧ��*�ѻ�����=�ѻ�
						BigDecimal b3=new BigDecimal(pd1.get("jd_count").toString());
						//�õ��ѻ�Ǯ��
						BigDecimal yh=myyh.multiply(b3);
						//�����ܶ�-�ѻ�Ǯ��
						BigDecimal dqqktotal=aa.subtract(yh);//�õ���ǰǷ��
						pd1.put("dqqktotal", dqqktotal);
						
					}
				}
				
			}
		}
		newpdList=limitutil.fy(l1, pagesize, pagenow);
//		System.out.println("*************"+newpdList);
		int q=l1.size()%pagesize;
		
		int totalpage=0;//��ҳ��
		if (q==0) {
			totalpage=l1.size()/pagesize;
		}else{
			totalpage=l1.size()/pagesize+1;
		}
		request.setAttribute("dn", dn);
		request.setAttribute("cn", cn);
		request.setAttribute("qn", qn);
		request.setAttribute("type", type);
		request.setAttribute("totalpage",totalpage);
		request.setAttribute("pagenow",pagenow);
		request.setAttribute("pagesize", pagesize);
		request.setAttribute("totalsize",l1.size());
		request.setAttribute("newpdList", newpdList);
//		System.out.println("���"+l1);
		return "kjs_icbc/index";
		
	}
	/**
	 * ��ѯ����
	 * @param id_card  ���֤��
	 * @param periods  �ڼ���
	 * @return
	 */
	@RequestMapping("/selectdetail")
	public String selectdetail(
			String qn,
			String cn,
			String type,
			String dn,
			String c_cardno,
			String periods,  
			HttpServletRequest request
			)throws ParseException{
		List<Map> detailList=lendingResultService.selectdetail(c_cardno, periods);
		
		//��ȡ��ǰʱ��
		DateTimeFormatter df=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String localTime=df.format(LocalDateTime.now());
		System.out.println("��ǰʱ�䣺"+localTime);
		for(Map map : detailList){
			System.out.println("detail:"+detailList);
			//�ж�  �������֤�Ų�ѯʱ���ݲ�Ϊ��
			if(map.get("id_card") != null && !map.get("id_card").equals("")){
			//��ȡ��ǰǷ��
			//��Ϣ�ϼ�=����ϼ�*��1+���ʣ�
			//������
		if(null != map.get("dk_total_price")){
			BigDecimal aa=new BigDecimal(map.get("dk_total_price").toString());
			//����
			double lv=(double)map.get("dk_lv")/100+1;
			BigDecimal bb=new BigDecimal(String.valueOf(lv));
			//����*����  �õ���Ϣ�ϼ�
			BigDecimal ee=aa.multiply(bb);//�˷�
			log.info("��Ϣ�ϼ�------"+ee);
			//��������
			log.info("------------"+map.get("aj_date").toString());
			BigDecimal cc=new BigDecimal(map.get("aj_date").toString());
			//ÿ��Ӧ��=��Ϣ�ϼ�/��������
			if(cc.compareTo(BigDecimal.ZERO)!= 0){
				BigDecimal myyh=ee.divide(cc,2,RoundingMode.HALF_UP);
				log.info(myyh+"*/*/*/*/*/*/*/*/*/*");
				map.put("myyh", myyh);
				
			}
		}
			}
		}
		
		System.out.println("**********"+detailList);
		request.setAttribute("dn",dn);
		request.setAttribute("cn", cn);
		request.setAttribute("qn", qn);
		request.setAttribute("type", type); 
		request.setAttribute("detailList", detailList);
		return "kjs_icbc/index";
		   
	}
	/**
	 * ������ݵ�����
	 * @param map
	 * @return
	 */
	@RequestMapping("/addlendingResult")
	@Transactional
	public String addlendingResult(
			String c_cardno,
			String periods,
			String qn,
			String cn,
			String type,
			String dn,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException,ParseException{
		
		//��ȡ��ǰʱ��
		DateTimeFormatter df=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String localTime=df.format(LocalDateTime.now());
		System.out.println("��ǰʱ�䣺"+localTime);
		
		//��ѯ����ҳ
		List<Map> detailMap=lendingResultService.selectdetail(c_cardno, periods);
		BigDecimal myyh=null;
		for(Map map : detailMap){
			System.out.println("detail:"+detailMap);
			//�ж�  �������֤�Ų�ѯʱ���ݲ�Ϊ��
			if(map.get("id_card") != null && !map.get("id_card").equals("")){
			//��ȡ��ǰǷ��
			//��Ϣ�ϼ�=����ϼ�*��1+���ʣ�
			//������
		if(null != map.get("dk_total_price")){
			BigDecimal aa=new BigDecimal(map.get("dk_total_price").toString());
			//����
			double lv=(double)map.get("dk_lv")/100+1;
			BigDecimal bb=new BigDecimal(String.valueOf(lv));
			//����*����  �õ���Ϣ�ϼ�
			BigDecimal ee=aa.multiply(bb);//�˷�
			log.info("��Ϣ�ϼ�------"+ee);
			//��������
			log.info("------------"+map.get("aj_date").toString());
			BigDecimal cc=new BigDecimal(map.get("aj_date").toString());
			//ÿ��Ӧ��=��Ϣ�ϼ�/��������
			if(cc.compareTo(BigDecimal.ZERO)!= 0){
				myyh=ee.divide(cc,2,RoundingMode.HALF_UP);
				log.info(myyh+"*/*/*/*/*/*/*/*/*/*");
				map.put("myyh", myyh);
				String myyh1 = myyh.toString();
				//�޸�״̬  �Ѵ�������
				int flag=lendingResultService.updateflag(c_cardno, periods,localTime,myyh1);
			}
		}
			}
			
		}
		
		
		//���ǰ��ѯ����Ϣ
		Map<String, Object> listMap=lendingResultService.selectCardno(c_cardno);
		System.out.println("-------------"+listMap);
//		//��ѯ����ҳ
//		List<Map> detailMap1=lendingResultService.selectdetail(c_cardno, periods);
		//��ѯ�û���Ϣ�Ƿ��ڱ����ظ�
		Map<String, Object> confirmMap=lendingResultService.selectconfirm(c_cardno);
		if(confirmMap == null){
			//�ж����ݿ��Ƿ��Ѿ�������ͬ����    ����б�ҳ����
			int i=lendingResultService.addlendingResult(listMap);
		}		
		//�������ҳ����
		lendingResultService.adddetail(detailMap.get(0));
		//�޸�partner_details����״̬�������������
		lendingResultService.updatedetail(c_cardno, periods, localTime);
		System.out.println("============"+detailMap);
		request.setAttribute("dn",dn);
		request.setAttribute("cn", cn);
		request.setAttribute("qn", qn);
		request.setAttribute("type", type);
		return "kjs_icbc/index";
	}

	
	
	private void requestParam(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}
}
