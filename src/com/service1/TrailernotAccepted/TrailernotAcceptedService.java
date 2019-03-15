package com.service1.TrailernotAccepted;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.model1.icbc.erp.PageData;

public interface TrailernotAcceptedService {

	/**
	 * ��ѯ�б�ҳ�������ݲ�ģ����ѯ
	 * @param param
	 * @param pd
	 * @return
	 */
	List<PageData> selectTrailernotAccepted(@Param(value="param") String param,@Param("pagenow")int pagenow,@Param("pagesize")int pagesize,int fsid,int fs_id);
	/**
	 * ��ѯ������
	 */
	int count();
	/**
	 * ��ѯ�б�ҳ�������ݲ�ģ����ѯ
	 * @param param
	 * @param pd
	 * @return
	 */
	List<PageData> selectTrailernotAccepted1( String param,int pagenow,int pagesize,int fsid,int fs_id);
	/**
	 * ��ѯ������
	 */
	int count1();
	/**
	 * ��ѯ�б�ҳ�������ݲ�ģ����ѯ
	 * @param param
	 * @param pd
	 * @return
	 */
	List<PageData> selectTrailernotAccepted2( String param,int pagenow,int pagesize,int fsid,int fs_id);
	/**
	 * ��ѯ������
	 */
	int count2();
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
	/**
	 * ��ѯ�ͻ�������Ϣ
	 */
	Map<String, Object> selectgrxx1(Integer icbc_id);
	/**
	 * ��ѯ������Ϣ
	 */
	Map<String, Object> selectclxx1(Integer icbc_id);
	/**
	 * ��ѯ�����
	 */
	Map<String, Object> selectdkfa1(Integer icbc_id);
	/**
	 * ��ѯ�ͻ���������
	 */
	
	List<Map> selectschedule1(Integer icbc_id);
	/**
	 * ��ѯ�ͻ�������Ϣ
	 */
	Map<String, Object> selectgrxx2(Integer icbc_id);
	/**
	 * ��ѯ������Ϣ
	 */
	Map<String, Object> selectclxx2(Integer icbc_id);
	/**
	 * ��ѯ�����
	 */
	Map<String, Object> selectdkfa2(Integer icbc_id);
	/**
	 * ��ѯ�ͻ���������
	 */
	
	List<Map> selectschedule2(Integer icbc_id);
	/**
	 * ���¼�뵽����
	 * @param map
	 * @return
	 */
	//�ϳ�δ����
	int addTc1(Map<String, Object> detailMap);
	int addTc2(Map<String, Object> detailMap);
	int addTc3(Map<String, Object> detailMap);
	/**
	 * ��ѯ��¼��
	 */
	List<Map> selectinput(@Param("icbc_id") Integer icbc_id);
	/**
	 * ��ѯ��¼��
	 */
	List<Map> selectinput1(@Param("icbc_id") Integer icbc_id);
	/**
	 * ��ѯ��¼��
	 */
	List<Map> selectinput2(@Param("icbc_id") Integer icbc_id);
	//���ı���״̬
	int updateTcStatus(Integer icbc_id);
	int updateTcStatus1(Integer icbc_id);
	

	/**
	 * ��ѯ��¼����Ϣ
	 */
	Map<String, Object> selectjll(int id);
	
	
}
