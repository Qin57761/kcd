package com.controller.erp_icbc.loanAfter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.controller.Excel.UploadExcelController;
import com.model1.icbc.erp.PageData;
import com.service1.loan.AboutExcelService;
import com.service1.loan.ClientPaymentService;
import com.service1.loan.AboutExcelService;
import com.util.limitutil;
/**
 * �ͻ��������-�ͻ�����¼�������
 * 
 * @author ��ʮ����
 * 2019-3-16
 */
@Controller
@RequestMapping("/loan")
public class ClientPaymentEntryController {
	private static Logger log = LogManager.getLogger(UploadExcelController.class.getName());
	@Autowired
	private ClientPaymentService clientPaymentService;
	
	
	//��ѯ��������Ϣ��
	@RequestMapping("/selectPayform.do")
	public String selectBorrow(String qn
								,String type
								,String dn
								,HttpServletRequest request
								,String c_cardno
								,String icbc_id
								){
		Map<String, Object> lborrow = clientPaymentService.selectPayform(icbc_id);
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
		List<PageData> mapschedule = clientPaymentService.selectPaySchedule(icbc_id);
		log.info("map3->"+mapschedule);
		//��ѯ������Ϣ
		PageData mapafter = clientPaymentService.selectLoanAfter(icbc_id);
		log.info("map4->"+mapafter);
		//��ѯ��������Ϣ
		Map<String, Object> mapzdr = clientPaymentService.selectZdr(icbc_id);
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
	
	/**
	 * ��ѯ����ҳ
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/selectPayList.do")
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
		pd.put("param",param);
		PageData pdsession= (PageData)request.getSession().getAttribute("pd");
		pd.put("gems_fs_id",pdsession.get("icbc_erp_fsid"));
		List<PageData> pdList=clientPaymentService.selectPayList(pd);
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
				//����������
//				JSONObject json = (JSONObject) JSON.parse(map.get("result_1_value").toString());  // String ת JSONObject 
//				String syhk = json.getString("61_syhk");
//				String yh = json.getString("61_yh");
//				map.put("syhk", syhk);
//				map.put("yh", yh);
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
				map.put("yh", myyh);
			}
		}		
		request.setAttribute("param", param);
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
}
