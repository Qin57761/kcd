package com.mapper1.settle;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.model1.icbc.erp.PageData;

public interface settleMapper {

	/**
	 * ��ѯ�б�ҳ�������ݲ�ģ����ѯ
	 * @param param
	 * @param pd
	 * @return
	 */
	List<PageData> selectsettle(@Param(value="param") String param,@Param("pagenow")int pagenow,@Param("pagesize")int pagesize,@Param("fsid")int fsid,@Param("fs_id")int fs_id);
	
	/**
	 * ��ѯ������
	 */
	int count();

	/**
	 * ��ѯ�ͻ�������Ϣ
	 */
	Map<String, Object> selectgrxx(Integer icbc_id);
	/**
	 * ��ѯ������Ϣ
	 */
	Map<String, Object> selectclxx(Integer icbc_id);
	/**
	 * ��ѯ�����
	 */
	Map<String, Object> selectdkfa(Integer icbc_id);
	/**
	 * ��ѯ�ͻ���������
	 */
	
	List<Map> selectschedule(Integer icbc_id);

	

	//����δ����
	int addTc1(Map<String, Object> detailMap);

	
	/**
	 * ��ѯ��¼��
	 */
	List<Map> selectinput(@Param("icbc_id") Integer icbc_id);


	//���ı���״̬
	int updateTcStatus(Integer icbc_id);

	
	
}
