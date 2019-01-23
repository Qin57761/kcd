package com.service1.ManagementCenter;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapper1.ManagementCenter.kj_icbcMapper;


@Service
@Transactional(value = "kcway2", rollbackFor = Exception.class) 
public class kj_icbcServiceImpl implements kj_icbcService{
	@Resource
	 private kj_icbcMapper kj_icbcmapper;
	
	//ÿ�±�������
	@Override
	public List<HashMap> SelectBill(){
		return kj_icbcmapper.selectbill();
	}
	//ÿ�·ſ��,�ܽ��
	@Override
	public List<HashMap> SelectLoan(){
		return kj_icbcmapper.selectloan();
	}
	//ÿ���ѷſ�δ��Ѻ����,�ܽ��
	@Override
	public List<HashMap> SelectFk(){
		return kj_icbcmapper.selectfk();
	}
	//ÿ���ܶ�����ʡ����
	@Override
	public List<HashMap> SelectStates(){
		return kj_icbcmapper.selectstates();
	}
	//ÿ���ܶ���������������
	@Override
	public List<HashMap> SelectGems(){
		return kj_icbcmapper.selectgems();
	}
	//ÿ���ܶ�����ʡ����
	@Override
	public List<HashMap> SelectLoanStates(){
		return kj_icbcmapper.selectloanstates();
	}
	//ÿ���ܶ���������������
	@Override
	public List<HashMap> SelectLoanGems(){
		return kj_icbcmapper.selectloangems();
	}	
	//����ͼ����
	@Override
	public List<HashMap> SelectChart() {
		return kj_icbcmapper.selectchart();
	}
	//��������������ͼ
	@Override
	public List<HashMap> SelectMoneyDistribute(){
		return kj_icbcmapper.selectmoneydistribute();
	}
	//����ͨ���ʲ�ѯ
	@Override
	public List<HashMap> SelectCredit(){
		return kj_icbcmapper.selectcredit();	
	}
	//�ͻ������ѯͳ��
	@Override
	public List<HashMap> SelectClientAge() {
		return kj_icbcmapper.selectclientage();
	}
	//�³������ѯͳ��
	@Override
	public List<HashMap> SelectCarsAge(){
		return kj_icbcmapper.selectcarsage();
	}
	//���������ֲ�ͼ
	@Override
	public List<HashMap> SelectAdvanceFund(){
		return kj_icbcmapper.selectadvancefund();
	}
	//��Ѻ���ϻ������
	@Override
	public List<HashMap> SelectRecycle(){
		return kj_icbcmapper.selectrecycle();
	}
	//���ֳ�ÿ�·ſ�����ܽ��
	public List<HashMap> SelectOldCars(){
		return kj_icbcmapper.selectoldcars();
	}
	//�³�ÿ�·ſ�����ܽ��
	public List<HashMap> SelectNewCars(){
		return kj_icbcmapper.selectnewcars();
	}
}
