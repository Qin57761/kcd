package com.controller.erp_icbc.YunXin.seats;
/**
 * @Description:TODO
 * @author:LiWang
 * @time:2018��8��18��
 */
public class PoolCache1 {
	//װ��ͬʹ�����͵���ϯ
	public static volatile Seats Seats; 
	//ˢ�¼��ʱ�� 
	private static Long cleanIntervalSecond =90L;//ѭ��һ��
	/**
	 ��ʼ������
	 */
	public static void initContainer(final Seats seats){
		Seats=seats;
		new Thread(new Runnable(){
			@Override
			public void run(){
				while (true){
					try{
						Thread.sleep(cleanIntervalSecond*1000);//1����=0.001�� 
					} catch (InterruptedException e){
						e.printStackTrace();
					}
					seats.relieveOccupy();
				}
			}
		}).start();
	} 
}
