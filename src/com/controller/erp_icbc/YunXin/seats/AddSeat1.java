package com.controller.erp_icbc.YunXin.seats;
import org.springframework.beans.factory.InitializingBean;
/**
 * ��bean��ʼ�����ʼ������
 * @Description:TODO
 * @author:LiWang
 */
public class AddSeat1 implements InitializingBean{
	@Override
	public void afterPropertiesSet() throws Exception {
		Seats seats=new Seats(900,200);//��ռ�õ���Ч��  ���ߵ�������
		PoolCache1.initContainer(seats);
	}
}
