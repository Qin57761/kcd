package com.mapper1.lendingResult;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.model1.icbc.erp.PageData;

public interface lendingResultMapper {
	
	/**
	 * ��ѯ�б�ҳ�������ݲ�ģ����ѯ
	 * @param param
	 * @param pd
	 * @return
	 */
	List<PageData> selectlendingResult(@Param(value="param") String param,@Param("pd")PageData pd);
	/**
	 * ���ǰ�Ĳ�ѯ
	 * @param c_cardno
	 * @return
	 */
	Map<String, Object> selectCardno(@Param("c_cardno") String c_cardno);
	/**
	 * ��ѯ����
	 * @param id_card  ���֤��
	 * @param periods  �ڼ���
	 * @return
	 */
	List<Map> selectdetail(@Param("id_card") String id_card,@Param("periods") String periods);
	/**
	 * ��ѯ�û���Ϣ�Ƿ��ڱ�������ͬ����
	 * @param id_card
	 * @return
	 */
	Map<String, Object> selectconfirm(String id_card);
	/**
	 * �޸�״̬
	 * @param id_card
	 * @param periods
	 * @return
	 */
	int updateflag(@Param("id_card") String id_card,@Param("periods") String periods,@Param("date") String date,@Param("myyh") String myyh);
	/**
	 * ������ݵ�����
	 * @param map
	 * @return
	 */
	int addlendingResult(Map<String, Object> map);
	/**
	 * ��ӻ���ƻ���
	 * @param map
	 * @return
	 */
	int adddetail(Map<String, Object> map);
	/**
	 * �޸�partner_details����״̬
	 */
	int updatedetail(@Param("id_card") String id_card,@Param("periods") String periods,@Param("date1") String date1);
}
