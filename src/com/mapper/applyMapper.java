package com.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.model.apply;

public interface applyMapper {
	//��ѯ������Ȩ��������
	public List<apply> fapply(int aid,int start,int num);
	public int	fapplylenth(int aid);
	public int alllenth();
	public  List<apply> allapply(int start,int num);
	//�����Ȩ��������
	public void addapply(apply apply);
	public Map fapplybyname(String name);
	public void upapply(apply apply);
	//��ѯ�쳵����Ȩ��������
	public  List<apply> KCDapply(int url_fenlei,int start,int num);
	//�����ѯ�쳵���ļ�
	public int KCDCounts(int fenlei);
	//����id��ѯ
	public apply findapplybycode(@Param("acode")String acode);
	//ɾ��
	public void delapply(@Param("applyurl")String applyurl);
	//���� �������� �� ��Ų�ѯ
	public List<apply> findbyacodeandtype(@Param("acode")String acode,@Param("apply_address")int apply_address);
}
