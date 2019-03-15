package com.controller.Electricity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.model1.icbc.erp.PageData;
import com.service1.Electricity.ElectricityService;
import com.util.limitutil;

@Controller
@RequestMapping("/electricityController")
public class ElectricityController {
	
	@Autowired
	private ElectricityService electricityService;
	
	@RequestMapping("/select")
	public String select(
			String param,
			String qn,
			String type,
			String dn,	
			Integer pagesize,
			Integer pagenow,
			String id_card,
			HttpServletRequest request){
		
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
				List<PageData> selList = electricityService.select(param,(pn-1)*ps,ps,fsid,fs_id);		
				for(PageData pd : selList){			
				//�жϸ������֤�Ų�ѯʱ���ݲ�Ϊ��
				if(pd.get("c_cardno") != null && !pd.get("c_cardno").equals("")){
					//����δ��������	
					BigDecimal yqwhtotal = new BigDecimal("0");
					double overdue_amount = (double) pd.get("overdue_amount");
					System.out.println("overdue_amount:"+overdue_amount);
					BigDecimal yqwhtotal1=new BigDecimal(String.valueOf(overdue_amount));
					System.out.println("=================:"+yqwhtotal1);
					if(null != yqwhtotal1){
						//�õ����ڽ��
						yqwhtotal=yqwhtotal.add(yqwhtotal1);//���ڽ�����						
					}
					System.out.println("+++++++++++++++++++++"+yqwhtotal);
					pd.put("yqwhtotal", yqwhtotal);
			}	
		}
				int totalsize=electricityService.count();
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
				request.setAttribute("selList", selList);
				return "kjs_icbc/index";
	}
	
	@RequestMapping("/hgrt")
	public String hgrt(String qn,
			String type,
			String dn,	
			String icbc_id,	
			HttpServletRequest request){
			
		//��ѯ��¼������
		List<Map> inputMap = electricityService.selectinput(icbc_id);
		//��ѯ�ͻ�������Ϣ
		Map<String, Object> grxxMap = electricityService.selectgrxx(icbc_id);
		//��ѯ������Ϣ
		Map<String, Object> clxxMap = electricityService.selectclxx(icbc_id);
		//��ѯ�ͻ�����
		List<Map> scheduleMap = electricityService.selectschedule(icbc_id);
		//��ѯ��������
		Map<String, Object> dkxxMap=electricityService.selectdkxx(icbc_id);
		
		request.setAttribute("grxxMap", grxxMap);
		request.setAttribute("clxxMap", clxxMap);
		request.setAttribute("dkxxMap", dkxxMap);
		request.setAttribute("scheduleMap", scheduleMap);	
		request.setAttribute("dn", dn);
		request.setAttribute("qn", qn);
		request.setAttribute("type", type);
		request.setAttribute("newpdList", inputMap);
		return "kjs_icbc/index";
	}
	
	//���¼�����
	@RequestMapping("/dcsubmit")
	public String dcsubmit(
			String value,
			String icbc_id,
			HttpServletRequest request){
		System.out.println(value+","+icbc_id+",999999999999");
//		��½
		Map<String, Object> map=new HashMap<>();
		map.put("value", value);
		//��ȡ�ͻ����������֤��
		Map<String,Object> naMap = electricityService.selectgrxx(icbc_id);
		//��ȡ��ǰ��������Ϣ
		PageData pdsession= (PageData)request.getSession().getAttribute("pd");
		map.put("operator", pdsession.get("name"));
		map.put("icbc_id", icbc_id);
		map.put("oc_name", naMap.get("c_name"));
		map.put("id_card", naMap.get("c_cardno"));
		
		electricityService.addInput(map);
		System.out.println("--------------��ӳɹ�:"+map);
		return "kjs_icbc/index";
	}
	
	//��������ϳ�
	@RequestMapping("/applica")
	public String applica(
			String qn,
			String type,
			String dn,
			String icbc_id,
			HttpServletRequest request){
		Map<String, Object> grxxMap = electricityService.selectgrxx(icbc_id);
		System.out.println("-------:"+grxxMap);
		electricityService.applica(grxxMap);
		electricityService.updatestate(icbc_id);
		
		
		request.setAttribute("dn", dn);
		request.setAttribute("qn", qn);
		request.setAttribute("type", type);
		return "kjs_icbc/index";
	}
	
	//�����������
		@RequestMapping("/applicass")
		public String applicass(
				String qn,
				String type,
				String dn,
				String icbc_id,
				HttpServletRequest request){
			Map<String, Object> grxxMap = electricityService.selectgrxx(icbc_id);
			System.out.println("-------:"+grxxMap);
			electricityService.applicass(grxxMap);
			electricityService.updateSSstate(icbc_id);
			request.setAttribute("dn", dn);
			request.setAttribute("qn", qn);
			request.setAttribute("type", type);
			return "kjs_icbc/index";
		}
		
		//��ѯ��¼������
		@RequestMapping("/selectjll")
		@ResponseBody
		public Map selectjll(int id,HttpServletRequest request){
			System.out.println("-----------id:"+id);
			Map<String, Object> value = electricityService.selectjll(id);
		
			return value;
		}
		
}
