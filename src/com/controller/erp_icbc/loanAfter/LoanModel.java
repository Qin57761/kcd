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
		 
		 loanTypeModel.put("3",  "�ϳ�");
		 loanTypeModel.put("31", "�ϳ�δ����");
		 loanTypeModel.put("32", "�ϳ��Ѵ���");
		 loanTypeModel.put("33", "�ϳ����");
		 loanTypeModel.put("34", "ʧ��");
		 
		 loanTypeModel.put("4", "����");
		 loanTypeModel.put("41", "δ����");
		 loanTypeModel.put("42", "������");
		 
		 loanTypeModel.put("5", "����");
		 loanTypeModel.put("51", "δ����");
		 loanTypeModel.put("52", "����(�������)");
		 loanTypeModel.put("53", "ӯ��(�������)");
		 
		 loanTypeModel.put("6", "����");
		 loanTypeModel.put("61", "��������");
		 loanTypeModel.put("62", "��ǰ����");
		 loanTypeModel.put("63", "ǿ�ƽ���");
		 loanTypeModel.put("64", "�������");
		 
		 loanTypeModel.put("7", "����");
		 loanTypeModel.put("71", "δ����");
		 loanTypeModel.put("72", "�Ѻ���");
		 
		 return loanTypeModel;	
	}
	
	public static void main(String[] args) {
		String a = LoanModel.LoanTypeModel().get("1");
		System.out.println(a);
	}

}
