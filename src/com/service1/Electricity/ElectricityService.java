package com.service1.Electricity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.model1.icbc.erp.PageData;

public interface ElectricityService {
	
	/**
	 * ��ѯ����
	 * @param param
	 * @param pagesize
	 * @param pagenow
	 * @return
	 */
	List<PageData> select( String param, int pagenow,int pagesize,int fsid,int fs_id);

	/**
	 * ��ѯ������
	 */
	int count();
	

	
	/**
	 * ������¼��������
	 */
	int addInput(Map<String, Object> map);
	
	/**
	 * ��ѯ��¼��
	 */
	List<Map> selectinput(String icbc_id);

	/**
	 * ��ѯ�ͻ�������Ϣ
	 */
	Map<String, Object> selectgrxx(String icbc_id);
	
	/**
	 * ��ѯ������Ϣ
	 */
	Map<String, Object> selectclxx(String icbc_id);
	
	/**
	 * ��ѯ��������
	 */
	Map<String, Object> selectdkxx(String icbc_id);
	
	/**
	 * ��ѯ�ͻ���������
	 */
	
	List<Map> selectschedule(String icbc_id);
	
	/**
	 * �����ϳ�������
	 */
	int applica(Map<String, Object> map);
	
	/**
	 * ��������
	 */
	int applicass(Map<String, Object> map);
	
	/**
	 * �޸����ڱ���״̬Ϊ�ϳ�
	 */
	int updatestate(String icbc_id);
	
	/**
	 * �޸����ڱ���״̬Ϊ����
	 */
	int updateSSstate(String icbc_id);
	
	/**
	 * ��ѯ��¼����Ϣ
	 */
	Map<String, Object> selectjll(int id);
}
