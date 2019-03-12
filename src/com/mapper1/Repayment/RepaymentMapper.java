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
   Map<String, Object> selectBorrow(@Param("icbc_id")String icbc_id);
   
   /**
    * ����ƻ�
    */
   List<Map> selectschedule(@Param("icbc_id")String icbc_id);
     
   /**
    * ��ѯ������Ϣ
    */
   List<Map> selectafter (@Param("icbc_id")String icbc_id);
   
   /**
    * ��ѯ������������Ϣ ģ̬��
    */
   Map<String, Object> selectzdr(@Param("icbc_id")String icbc_id);

   /**
    * ��ѯ����
    */
   int selectrepay(int icbc_id);
   
   /**
    * ����ƻ�
    */
   List<Map> selectimport();
   
   /**
    * ��ӷ��� ���뻹��ƻ�
    */
  int addrepay(Map<String, Object> map);
  //��service�����
  Map<String, Object> selectID(@Param("id")int id);
	// �޸Ļ����¼ 
  int updateschedule(Map<String, Object> map);
  
}
