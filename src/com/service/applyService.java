package com.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.model.apply;

public interface applyService {
	public List<apply> fapply(int aid,int start,int num);
	//�����Ȩ��������
    public void addapply(apply apply);
    public int	fapplylenth(int aid);
    public int alllenth();
	public  List<apply> allapply(int start,int num);
    //
	public Map fapplybyname(String name);
	
	public void upapply(apply apply);
	//��ѯ�쳵����Ȩ��������
	public  List<apply> KCDapply(int start,int num,int url_fenlei);
	//�����ѯ�쳵���ļ�
	public int KCDCounts(int fenlei);
	//����code��ѯ
	public apply findapplybycode(String acode);
	//ɾ��
	public void delapply(String applyurl);
	//���� �������� �� ��Ų�ѯ
	public List<apply> findbyacodeandtype(String acode,int apply_address);

}
