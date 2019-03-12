package com.service1.Repayment;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;


import com.model1.icbc.erp.PageData;

public interface RepaymentService {
	/**
	 * ��ѯȫ������ by  zp 2019-1-22
	 * @return 
	 */
   List<PageData> selectRepayment( String param,PageData pd);
   /**
    * ������
    */
   Map<String, Object> selectBorrow(String icbc_id);
   
   /**
    * ����ƻ�
    */
   List<Map> selectschedule(String icbc_id);
   
   /**
    * ����ƻ�
    */
   List<Map> selectimport();
   
   /**
    * ��ѯ������Ϣ
    */
   List<Map> selectafter(String icbc_id);
   
   /**
    * ��ѯ��������Ϣ
    */
   Map<String, Object> selectzdr(String icbc_id);
   
   /**
    * ��ѯ����
    */
   int selectrepay(int icbc_id);
   
   /**
    * ��ӷ��� ���뻹��ƻ�
    */

  int addrepay(int icbc_id,String fk_money,int aj_date);
	// �޸Ļ����¼ 
  int updateschedule(Map<String, Object> map);
}
