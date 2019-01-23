package com.service1.ManagementCenter;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapper1.ManagementCenter.kj_icbc_resultMapper;

@Service
@Transactional(value = "kcway2", rollbackFor = Exception.class) 
public class kj_icbc_resultServiceImpl implements kj_icbc_resultService{
	@Resource
	private kj_icbc_resultMapper kj_icbc_resultmapper;
	// ��ѯ��Ѻ��������������0-15�죬15-30�죬30-45�죬45-60�죬60�����ϵĽ��з����ѯ
	@Override
	public List<HashMap> SelectResult() {	
		return kj_icbc_resultmapper.selectresult();
	}
}
