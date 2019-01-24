package com.util.sxx.financialExcel.controller;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.set.SynchronizedSet;
import org.apache.log4j.chainsaw.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.mapper1.sxx.financialExcel.FinancialExcelMapper;
import com.service1.sxx.FinancialExcelService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/financialExcelController")
public class FinancialExcelController {
	
	@Autowired
	private FinancialExcelService financialExcelService;
	
	String jsonicbc = "";
	//�����ֶ��б�
	String[] QueryField = {"�����ύʱ��", "�������", "�ſ�����", "��Ʒ����", "������������", "����", "ҵ������ʡ��", "ҵ�����ڳ���", "������", "�ͻ�����", "��ϵ�绰"
								, "���֤����", "��������", "����Ʒ���ͺ�", "���Ƴ���", "�������ڱ���", "���ڷ���ѷ��ڱ���", "���ڱ���ϼ�", "��������/��", "ִ����������"
								, "��ͬ��Ϣ�ϼ�", "�ͻ��»�����", "�г�����"};
	
	/**
	 * ���д���
	 * @param pagesize
	 * @param pagenow
	 * @param dn
	 * @param qn
	 * @param cn
	 * @param type
	 * @param request
	 * @return
	 */
	@RequestMapping("/bankLoan")
	public String BankLoan(
			Integer pagesize,
			Integer pagenow,
			String dn,
			String qn,
			String cn,
			String type,
			HttpServletRequest request
			){
		//��������
		List<String> list1 = financialExcelService.FindInstitutionsNameList();
		//��������
		List<String> list2 = financialExcelService.FindBankNameList();
	
		request.setAttribute("Institutions", list1);
		request.setAttribute("Bank", list2);
		request.setAttribute("QueryField", QueryField);
		
		request.setAttribute("dn",dn);  
		request.setAttribute("qn",qn);
		request.setAttribute("cn",cn);
		request.setAttribute("type",type);
		
		return "kjs_icbc/index";
	}
	
	/**
	 * ʵ�ֲ������ģ�����д�����б�����ʾ������Ϣ�Ĺ���
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/findicbcMapbyid")
	public String FindicbcMapbyid(
			Integer id,
			String dn,
			String qn,
			String cn,
			String type,
			HttpServletRequest request){
		 Map<String, Object> map = financialExcelService.GetLoanInformationbyid(id);
		
		jsonicbc = JSON.toJSONString(map);
		
		//�õ���Ҫ���δ��������
		//1.���з��ڱ���
		float a = 0;
		BigDecimal bd = null;
		if(null!=map.get("kk_loan_amount_s") && null!=map.get("kk_loan_amount")){
			a = (float)map.get("kk_loan_amount")*10000 + (int)map.get("kk_loan_amount_s");
		}
		//2.��ͬ��Ϣ�ϼ�
		if(null!=map.get("kk_loan_rate")){
			float b = ((float)map.get("kk_loan_rate")/100 + 1);
			BigDecimal b1 = new BigDecimal(String.valueOf(b));
			BigDecimal b2 = new BigDecimal(String.valueOf(a));
			bd = b1.multiply(b2);
		}
		//3.�ͻ��»�����
		BigDecimal c = new BigDecimal("0");
		if ((int)map.get("kk_loan_ajqx")!=0 && null!=map.get("kk_loan_ajqx")) {
			BigDecimal c1 = new BigDecimal(String.valueOf(map.get("kk_loan_ajqx")));
			c = bd.divide(c1 , 5, RoundingMode.HALF_UP);
		}
		
		map.put("Principal", a);
		map.put("PrincipalAndInterest", bd);
		map.put("Monthlypayments", c);
		 
		request.setAttribute("icbc_map", map);
		request.setAttribute("icbc_json", jsonicbc);
		
		request.setAttribute("dn",dn);
		request.setAttribute("qn",qn);
		request.setAttribute("cn",cn);
		request.setAttribute("type",type);
		return "kjs_icbc/index";
	}
	
	/**
	 * ʵ�����д���ҳ�浼�����ݵ�excel�Ĺ���
	 * @return map	�����Ľ��
	 * @throws Exception
	 */
	@RequestMapping("/financialExportExcel")
	@ResponseBody
	public Map<String, String> FinancialExportExcel(String Institutions, String Bank, String arr) throws Exception{	
		Map<String, String> map = new HashMap<>();
		System.out.println("���뵼������");
		//����״̬
		String status = "�����ɹ�";
		System.out.println("������������������:"+Institutions+"***"+Bank);//������������������
//		System.out.println(arr.length());			//����û�û��ѡ�񵼳��ֶεĻ� arr��ֵΪ"[]" ����Ϊ2
		
		try {
			//���û�û��ѡ�񵼳����ֶ�ʱ,��ѯ�������ݲ�����
			if(arr.length() == 2){
				//��ȡ����
				List<Map> list1 = financialExcelService.ExportBuyCarInstallmentExcel(Institutions, Bank);
				List<Map> list2 = financialExcelService.FindBuyCarInstallmentExcelByStatus();
				List<Map> list3 = financialExcelService.FindFirstPaymentDate();
				//�������Ҳ�������
				ExportOperationsExcel.CreateExcel(list1, list2, list3);
			  //���û�ѡ������Ҫ�������ֶ�ʱ, ֻ�����û�ѡ����ֶ�
			} else {
				//����Ҫ�����ֶε�json�ַ���תΪlist
				JSONArray json = JSONArray.fromObject(arr);
				List<String> jsonlist = json.toList(json);
				
				List<String> fieldName = new ArrayList<>();  //����������ݿ��ֶ���
				List<Integer> lev2MenuName = new ArrayList<>();  //��������û�ѡ��������˵����ϼ�Ҳ���Ƕ����˵��ڼ����е�λ��
				List<Integer> lev3MenuName = new ArrayList<>();  //��������û��û�ѡ��������˵��ڼ����е�λ��
				
				for (int i = 0; i < jsonlist.size(); i++) {
					fieldName.add(jsonlist.get(i).substring(1, jsonlist.get(i).length()-2));  //ȡ�����ݿ��ֶ���
					lev2MenuName.add(Integer.parseInt( jsonlist.get(i).substring(0, 1) ));  //��ȡ���û�ѡ��������˵����ϼ�Ҳ���Ƕ����˵��ڼ����е�λ��
					lev3MenuName.add( Integer.parseInt(jsonlist.get(i).substring(jsonlist.get(i).length()-2, jsonlist.get(i).length())) );  //��ȡ���û�ѡ��������˵��ڼ����е�λ��
				}
		
				List<Map> list = financialExcelService.ExportBuyCarInstallmentExcel(Institutions, Bank);
				
				ExportOperationsExcel.CreateExcel(fieldName, lev2MenuName, lev3MenuName, list);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			status = "��һ����������ʹ�ô��ļ��������޷�����";
		} catch (Exception e) {
			e.printStackTrace();
			status = "ϵͳ����,����ʧ��";
		}
		
		map.put("status", status);
		return map;
	}
	

}
