package com.service;

import java.util.List;
import java.util.Map;

import com.model.pdfover;

public interface pdfoverService {
	//��� pdf����
    public void addpdf(pdfover po);
    
    public List<pdfover> findbyname(String pdfname);
    public void uppdf(pdfover po);
  //����uid ��ѯ����   
    public Map findbyid(String uid);
  //����uid ��ѯ����   
    public Map pdflist(String uid);
    
    public Map findpdfurl(String uid);
}
