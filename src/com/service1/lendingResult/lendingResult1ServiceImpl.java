package com.service1.lendingResult;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.model1.icbc.erp.PageData;

@Service
@Transactional(value="kcway2", rollbackFor=Exception.class)
public class lendingResult1ServiceImpl implements lendingResult1Service{

	@Autowired
	private com.mapper1.lendingResult.lendingResult1Mapper lendingResult1Mapper;

	/**
	 * ��ѯ�б�ҳ�������ݲ�ģ����ѯ
	 * @param param
	 * @param pd
	 * @return
	 */
	@Override
	public List<PageData> selectlendingResult1(String param, PageData pd) {
		// TODO Auto-generated method stub
		return lendingResult1Mapper.selectlendingResult1(param, pd);
	}
	/**
	 * ���ǰ��ѯ
	 * @param id_card
	 * @return
	 */
	@Override
	public Map<String, Object> selectCardno(String c_cardno) {
		// TODO Auto-generated method stub
		return lendingResult1Mapper.selectCardno(c_cardno);
	}
	/**
	 * ��ѯ����
	 * @param id_card  ���֤��
	 * @param periods  �ڼ���
	 * @return
	 */
	@Override
	public List<Map> selectdetail(String id_card, String periods) {
		// TODO Auto-generated method stub
		return lendingResult1Mapper.selectdetail(id_card, periods);
	}
	/**
	 * ������ݵ�����
	 * @param map
	 * @return
	 */
	@Override
	public int addlendingResult1(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return lendingResult1Mapper.addlendingResult1(map);
	}
	/**
	 * ��ѯ�û���Ϣ�Ƿ��ڱ�������ͬ����
	 * @param id_card
	 * @return
	 */
	@Override
	public Map<String, Object> selectconfirm(String id_card) {
		// TODO Auto-generated method stub
		return lendingResult1Mapper.selectconfirm(id_card);
	}
	/**
	 * �޸�״̬
	 * @param id_card
	 * @param periods
	 * @return
	 */
	@Override
	public int updateflag(String id_card, String periods,String dcdate,String myyh) {
		// TODO Auto-generated method stub
		return lendingResult1Mapper.updateflag(id_card, periods,dcdate,myyh);
	}
	/**
	 * ��ӻ���ƻ���
	 * @param map
	 * @return
	 */
	@Override
	public int adddetail1(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return lendingResult1Mapper.adddetail1(map);
	}
	/**
	 * �޸�import_repayment����״̬
	 */
	@Override
	public int updatestate(String id_card, String periods, String dcdate) {
		// TODO Auto-generated method stub
		return lendingResult1Mapper.updatestate(id_card, periods, dcdate);
	}
	/**
	 * �޸�partner_details����״̬
	 */
	@Override
	public int updatedetail1(String id_card, String periods, String dcdate) {
		// TODO Auto-generated method stub
		return lendingResult1Mapper.updatedetail1(id_card, periods, dcdate);
	}
	/**
	 * �޸�partner_details����״̬
	 */
	@Override
	public int updatedetail(String id_card, String periods, String dcdate) {
		// TODO Auto-generated method stub
		return lendingResult1Mapper.updatedetail(id_card, periods, dcdate);
	}

}
