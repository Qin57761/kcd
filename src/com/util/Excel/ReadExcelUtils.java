package com.util.Excel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ReadExcelUtils implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	 private String name; // �ͻ�����
	 private String IdCard; // ���֤��
	 private String RepaymentCard; // �����
	 private String balanceCard;//�����
	 private String OverdueAmount;//���ڽ��
	 private String continuity;//����ΥԼ����
	 private String Maximum;//���ΥԼ����
	 private String Date;//��������
	 private String balanceOn;//�ڱ����
	public ReadExcelUtils(String filePath) {
		// TODO Auto-generated constructor stub
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdCard() {
		return IdCard;
	}
	public void setIdCard(String idCard) {
		IdCard = idCard;
	}
	public String getRepaymentCard() {
		return RepaymentCard;
	}
	public void setRepaymentCard(String repaymentCard) {
		RepaymentCard = repaymentCard;
	}
	public String getBalanceCard() {
		return balanceCard;
	}
	public void setBalanceCard(String balanceCard) {
		this.balanceCard = balanceCard;
	}
	public String getOverdueAmount() {
		return OverdueAmount;
	}
	public void setOverdueAmount(String overdueAmount) {
		OverdueAmount = overdueAmount;
	}
	public String getContinuity() {
		return continuity;
	}
	public void setContinuity(String continuity) {
		this.continuity = continuity;
	}
	public String getMaximum() {
		return Maximum;
	}
	public void setMaximum(String maximum) {
		Maximum = maximum;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getBalanceOn() {
		return balanceOn;
	}
	public void setBalanceOn(String balanceOn) {
		this.balanceOn = balanceOn;
	}
	@Override 
	public String toString() {
        return "User [�ͻ�����=" + name + ", ���֤��=" + IdCard + ", �����=" + RepaymentCard + ", �����=" + balanceCard + ","
        		+ "���ڽ��=" + OverdueAmount + ",����ΥԼ����=" + continuity + ",���ΥԼ����=" + Maximum + ",��������=" + Date + ",�ڱ����=" + balanceOn + "]";
    }

	public List<Map> readExcelContent(List<String> columnsList) {
		// TODO Auto-generated method stub
		return null;
	}


}
