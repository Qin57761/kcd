package com.service1.lendingResult;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapper1.lendingResult.lendingResultMapper;
import com.model1.icbc.erp.PageData;

@Service
@Transactional(value="kcway2", rollbackFor=Exception.class)
public class lendingResultServiceImpl implements lendingResultService{

	@Autowired
	private lendingResultMapper lendingResultMapper;

	/**
	 * ��ѯ�б�ҳ�������ݲ�ģ����ѯ
	 * @param param
	 * @param pd
	 * @return
	 */
	@Override
	public List<PageData> selectlendingResult(String param, PageData pd) {
		// TODO Auto-generated method stub
		return lendingResultMapper.selectlendingResult(param, pd);
	}

	/**
	 * ���ǰ��ѯ
	 * @param id_card
	 * @return
	 */
	@Override
	public Map<String, Object> selectCardno(String c_cardno) {
		// TODO Auto-generated method stub
		return lendingResultMapper.selectCardno(c_cardno);
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
		return lendingResultMapper.selectdetail(id_card, periods);
	}
	/**
	 * ������ݵ�����
	 * @param map
	 * @return
	 */
	@Override
	public int addlendingResult(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return lendingResultMapper.addlendingResult(map);
	}
	/**
	 * ��ѯ�û���Ϣ�Ƿ��ڱ�������ͬ����
	 * @param id_card
	 * @return
	 */
	@Override
	public Map<String, Object> selectconfirm(String id_card) {
		// TODO Auto-generated method stub
		return lendingResultMapper.selectconfirm(id_card);
	}
	/**
	 * �޸�״̬
	 * @param id_card
	 * @param periods
	 * @return
	 */
	@Override
	public int updateflag(String id_card, String periods,String date,String myyh) {
		// TODO Auto-generated method stub
		return lendingResultMapper.updateflag(id_card, periods,date,myyh);
	}
	/**
	 * ��ӻ���ƻ���
	 * @param map
	 * @return
	 */
	@Override
	public int adddetail(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return lendingResultMapper.adddetail(map);
	}

	@Override
	public int updatedetail(String id_card, String periods, String date1) {
		// TODO Auto-generated method stub
		return lendingResultMapper.updatedetail(id_card, periods, date1);
	}
	
}
