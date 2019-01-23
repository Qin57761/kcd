package com.service1.ManagementCenter;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapper1.ManagementCenter.kjs_icbc_cardkMapper;

@Service
@Transactional(value = "kcway2", rollbackFor = Exception.class) 
public class kjs_icbc_cardkServiceImpl implements kjs_icbc_cardkService{
	@Resource
	 private kjs_icbc_cardkMapper kjs_icbc_cardkmapper;
	
	 //ÿ�����������ܶ�����
	@Override
	public int CountSelect(){
		return kjs_icbc_cardkmapper.countselect();
	}
	 //ÿ�����������������
	@Override
	public int CountPass(){
		return kjs_icbc_cardkmapper.countpass();
	}
	//ÿ�����������ʡ��ȫ��������������
	@Override
	public List<HashMap> SelectCarPassComm(){
		return kjs_icbc_cardkmapper.selectcarpasscomm();
	}
	//ÿ�����������ʡ��ȫ��������������
	@Override
	public List<HashMap> SelectCarPassGems(){
		return kjs_icbc_cardkmapper.selectcarpassgems();
	}
	//������������ͼ
	@Override
	public List<HashMap> SelectChart(){
		return kjs_icbc_cardkmapper.selectchart();
	}
	//�����ſ�ֲ�����ͼ
	@Override
	public List<HashMap> SelectCarFk(){
		return kjs_icbc_cardkmapper.selectcarfk();
	}
}
