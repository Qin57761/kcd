package com.model;

import com.util.creditutil;

public class pdfover {
  private int eid;
  private String pdfurl;//pdf ·��
  private String pdftest;//pdf �ı�����
  private String pdfname;//pdf����
  private String pdftime=creditutil.time();//��� ʱ�� 
  private String pdfuptime=creditutil.time();//pdf����ʱ��
  private String uid;
public int getEid() {
	return eid;
}
public void setEid(int eid) {
	this.eid = eid;
}
public String getPdfurl() {
	return pdfurl;
}
public void setPdfurl(String pdfurl) {
	this.pdfurl = pdfurl;
}
public String getPdftest() {
	return pdftest;
}
public void setPdftest(String pdftest) {
	this.pdftest = pdftest;
}
public String getPdftime() {
	return pdftime;
}
public void setPdftime(String pdftime) {
	this.pdftime = pdftime;
}

public String getPdfname() {
	return pdfname;
}
public void setPdfname(String pdfname) {
	this.pdfname = pdfname;
}
public String getUid() {
	return uid;
}
public void setUid(String uid) {
	this.uid = uid;
}
public String getPdfuptime() {
	return pdfuptime;
}
public void setPdfuptime(String pdfuptime) {
	this.pdfuptime = pdfuptime;
}

  
}
