package com.mapper1.lendingResult;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.model1.icbc.erp.PageData;

public interface lendingResult1Mapper {
	/**
		 * ��ѯ�������ݲ�ģ����ѯ
		 * @param param
		 * @param pd
		 * @return
		 */
		List<PageData> selectlendingResult1(@Param(value="param") String param,@Param("pd")PageData pd);
		/**
		 * ���ǰ��ѯ�б�ҳ
		 * @param c_cardno
		 * @return
		 */
		Map<String, Object> selectCardno(@Param("c_cardno") String c_cardno);
		/**
		 * ��ѯ����ҳ
		 * @param id_card
		 * @param periods
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
		int updateflag(@Param("id_card") String id_card,@Param("periods") String periods,@Param("dcdate") String dcdate,@Param("myyh") String myyh);
		/**
		 * ������ݵ�����
		 * @param map
		 * @return
		 */
		int addlendingResult1(Map<String, Object> map);
		/**
		 * ��ӻ���ƻ�������
		 * @param map
		 * @return
		 */
		int adddetail1(Map<String, Object> map);
		/**
		 * �޸�import_repayment����״̬
		 */
		int updatestate(@Param("id_card") String id_card,@Param("periods") String periods,@Param("dcdate") String dcdate);
		/**
		 * �޸�partner_details����״̬
		 */
		int updatedetail1(@Param("id_card") String id_card,@Param("periods") String periods,@Param("dcdate") String dcdate);
		/**
		 * �޸�partner_details����״̬
		 */
		int updatedetail(@Param("id_card") String id_card,@Param("periods") String periods,@Param("dcdate") String dcdate);
}

