package com.mapper1.car;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.model1.carseries;

public interface seriesMapper {
	// ��ѯȫ��
	public List<carseries> findseries();

	// ����id��ѯ
	public carseries findseriesbyid(@Param("id") int id);

	// ��ѯȫ��
	public List<carseries> findseries_v2();

	// ����id��ѯ
	public carseries findseriesbyid_v2(@Param("id") int id);
}
