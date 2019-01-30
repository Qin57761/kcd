package com.mapper1.Repayment;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.model1.icbc.erp.PageData;
import com.util.duoying.mapbeanutil;
public interface RepaymentMapper {
	/**
	 * ��ѯȫ������ by  zp 2019-1-22
	 * @return 
	 */
   List<PageData> selectRepayment(@Param("param") String param,@Param("pd")PageData pd);
   
   /**
    * ������
    */
   Map<String, Object> selectBorrow(@Param("id")Integer id);
   
   /**
    * ����ƻ�
    */
   Map<String, Object> selectschedule(@Param("id_card")String id_card);
   
   /**
    * ��ѯ������Ϣ
    */
   List<Map> selectafter (@Param("c_cardno")String c_cardno);
   
   /**
    * ��ѯ��������Ϣ
    */
   Map<String, Object> selectzdr(String c_cardno);

}
