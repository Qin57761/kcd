package com.mapper1.ManagementCenter;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.model1.ManagementCenter.kjs_icbc_cardk;



public interface kjs_icbc_cardkMapper {
	//kjs_icbc_cardk	findicbc_cardk(@Param("icbc_id")int icbc_id);
	//ÿ�����������ܶ�����
	public int countselect();
	//ÿ�����������������
	public int countpass();
	//ÿ�����������ʡ��ȫ��������������
	public List<HashMap> selectcarpasscomm();
	//ÿ�����������������ȫ��������������
	public List<HashMap> selectcarpassgems();
	//������������ͼ
	public List<HashMap> selectchart();
	//�����ſ�ֲ�����ͼ
	public List<HashMap> selectcarfk();
}
