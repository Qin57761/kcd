package com.mapper1.matEndiwmentResult;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.model1.icbc.erp.PageData;

public interface MatEndiwmentResultMapper {
	
	/**
	 * ��ѯ����ҳ by zp 2019-2-16
	 */
	List<PageData> selectMat(@Param("param") String param);
	
	/**
	 * ��ѯ����
	 */
	List<Map> selectDetail(@Param("id_card")String id_card,@Param("periods") String periods);
	/**
	 * �������ڽ��
	 * @param id_card
	 * @param periods
	 * @return
	 */
	 List<Map> selectdetail(@Param("id_card") String id_card);
	/**
	 * ���ǰ�Ĳ�ѯ
	 */
	 Map<String, Object> selectid_card(@Param("id_card") String id_card);
	 
	 /**
	  * ������ݵ�����
	  */
	 int addMat(Map<String, Object> map);
	   
	 /**
	  * ��ӻ���ƻ�
	  */
	 int addhk(Map<String, Object> map);
	
	 /**
	    * ��ѯ�û���Ϣ��
	    */
	   Map<String, Object> selectAfree(String id_card);
	   
	/**
	 * �޸�״̬
	 */
	int updateflag(@Param("id_card") String id_card,@Param("periods") String periods,@Param("dcdate") String dcdate);
	
	/**
	 * �޸ĵ�����ǰ��ѯ����
	 */
	String selectcount(@Param("id_card") String id_card);
	
	/**
	 * �޸�confirm_compensatory������
	 * @param map
	 * @return
	 */
	int updatecount(@Param("id_card") String id_card,@Param("count") String count);
	/**
	 * �޸�agree_compensate������
	 * @param id_card
	 * @param count
	 * @return
	 */
	int updatecount2(@Param("id_card") String id_card,@Param("count") String count);
	
	/**
	 * �޸�import_repayment����״̬
	 */
	int updatestate(@Param("id_card") String id_card,@Param("periods") String periods,@Param("dcdate") String dcdate);
}
