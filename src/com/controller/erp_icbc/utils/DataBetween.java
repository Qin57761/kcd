package com.controller.erp_icbc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/** ����������ڵļ������ �����������ڵ�����
 * @Description:TODO
 * @author:LiWang
 * @time:2018��8��2��
 */
public class DataBetween {
	/**dayDiff("2018-02-15","2018-02-16","yyyy-MM-dd")
	 * @param date1
	 * @param date2
	 * @param format
	 * @return
	 * @Description: TODO
	 * @param name
	 * @return 
	 */
	public static long dayDiff(String date1, String date2,String format) {
		SimpleDateFormat formater = new SimpleDateFormat(format);
		long diff=0l;
		try {
			long d1 = formater.parse(date1).getTime();
			long d2 = formater.parse(date2).getTime();
			//absȡ����ֵ���������Ϊ�Ǹ������򷵻ظò������������Ϊ�������򷵻ظò������෴��������������£�
			diff=(Math.abs(d1-d2) / (1000 * 60 * 60 * 24));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return diff;
	}
}
