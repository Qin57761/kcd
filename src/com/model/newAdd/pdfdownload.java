package com.model.newAdd;

import java.util.List;

public class pdfdownload {
	private int PDFdownload_id;   //1 ����      
	private String PDFcode;  //2 ����pdf���   
	private String downloadCompany;  //3 ���ع�˾
	private int downloadNum;  //4 ��������
	private String downloadCzr;  //5 ������Ա
	private int status; //6 ״̬  1����'�ѻ���', 0����'δ����'
	private String PDFurl;  //7 ��������
	private String downloadTime; //8 ����ʱ��
	private String updateTime; //9 �ѻ���--�޸�ʱ��
	private int addtype;	
	public String getPDFcode() {
		return PDFcode;
	}
	public void setPDFcode(String pDFcode) {
		PDFcode = pDFcode;
	}
	public String getDownloadTime() {
		return downloadTime;
	}
	public void setDownloadTime(String downloadTime) {
		this.downloadTime = downloadTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getPDFurl() {
		return PDFurl;
	}
	public void setPDFurl(String pDFurl) {
		PDFurl = pDFurl;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getPDFdownload_id() {
		return PDFdownload_id;
	}
	public void setPDFdownload_id(int pDFdownload_id) {
		PDFdownload_id = pDFdownload_id;
	}
	public String getDownloadCompany() {
		return downloadCompany;
	}
	public void setDownloadCompany(String downloadCompany) {
		this.downloadCompany = downloadCompany;
	}
	public int getDownloadNum() {
		return downloadNum;
	}
	public void setDownloadNum(int downloadNum) {
		this.downloadNum = downloadNum;
	}
	public String getDownloadCzr() {
		return downloadCzr;
	}
	public void setDownloadCzr(String downloadCzr) {
		this.downloadCzr = downloadCzr;
	}
	public int getAddtype() {
		return addtype;
	}
	public void setAddtype(int addtype) {
		this.addtype = addtype;
	}
	
}
