package com.mapper;

import java.util.List;
import java.util.Map;

import com.model.gskh;


public interface gskhMapper {

	//��˾����
	public void addgskh(gskh gskh);
	//��ѯ��˾��Ϣ
	public Map sltgskh(String name,String ncode);
	//��ѯ��˾����
	public List<gskh> fgsname();
	//
	public Map fgsname1(String name);
	//
	public Map fgsbyid(int id);
	//
	public void upgskhkd(gskh gskh);
}
