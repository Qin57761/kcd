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
   List<PageData> selectRepayment(@Param("param") String param,PageData pd);
   /**
    * ������
    */
   Map<String, Object> selectBorrow(Integer id);
   
   /**
    * ����ƻ�
    */
   Map<String, Object> selectschedule(@Param("id_card")String id_card);
   
   /**
    * ��ѯ������Ϣ
    */
   List<Map> selectafter(Integer c_cardno);
   
   /**
    * ��ѯ��������Ϣ
    */
   Map<String, Object> selectzdr(Integer c_cardno);
}
