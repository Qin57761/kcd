package com.service1.businessPayApplication;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.model1.icbc.erp.PageData;

public interface BusinessPayService {
		
	/**
	 * ��ѯȫ������ by  zp 2019-2-15
	 * @return 
	 */
   List<PageData> selectBusinessPay(String param);
   
   /**
    * ���ǰ�Ĳ�ѯ
    */
   Map<String, Object> selectCardno(@Param("c_cardno") String c_cardno);
   
   /**
    * ��ѯ���
    */
   List<Map> selectdetail(@Param("id_card") String id_card,@Param("periods") String periods);
   
   
   /**
    * ��ӵ�һҳ����
    */
   int addBusin(Map<String, Object> map);
   
   /**
    * ��ӻ���ƻ�
    */
   int addhk(Map<String, Object> map);
   
   /**
    * ��ѯ�û���Ϣ��
    */
   Map<String, Object> selectconfirm(String id_card);
   
   /**
    * �޸�״̬
    */
   int updateflag(@Param("id_card") String id_card,@Param("periods") String periods,@Param("date") String date,@Param("day") String day);
}
