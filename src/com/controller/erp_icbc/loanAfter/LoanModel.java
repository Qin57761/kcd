package com.controller.erp_icbc.loanAfter;

import java.util.HashMap;
import java.util.Map;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class LoanModel{
	public static Map<Object,String> LoanTypeModel(){
		 Map<Object,String> loanTypeModel=new HashMap<>();
		 //����ģ��
		 loanTypeModel.put("1", "����");
		 loanTypeModel.put("11", "��������");
		 loanTypeModel.put("12", "�м�����");
		 loanTypeModel.put("13", "�߼�����");
		 loanTypeModel.put("2", "���");
		 loanTypeModel.put("3", "�ϳ�");
		 loanTypeModel.put("4", "����");
		 loanTypeModel.put("5", "����");
		 loanTypeModel.put("6", "����");
		 return loanTypeModel;	
	}
	
	public static void main(String[] args) {
		String a = LoanModel.LoanTypeModel().get("1");
		System.out.println(a);
	}

}
