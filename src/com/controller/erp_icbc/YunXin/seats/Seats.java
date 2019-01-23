package com.controller.erp_icbc.YunXin.seats;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/** �������������
 *  �ͻ��������ͷźͷ�������ʱ�ͷ���ͬ��accid  ��ͬ��ScanPool1��active�в���׷�ӿ��£���������ظ�
 *  �ͻ��������ͷźͷ�������ʱ�ͷŲ���ͬ��accid ������Ӻ���ֻ�����һ��(��С����)
	�ͻ��˵�¼�����ж��Ƿ���ڣ����������ˢ�£��������򴴽���������£���Ϊ���pool������æµ״̬ �ᵼ���ظ����SCanpooll 
	������׾������ߵĲ��������������¶��ظ���Ҳ���²���д�ظ��ģ�����û��д��
 * @Description:TODO
 * @author:LiWang
 * @time:2018��8��18��
 */
public  class Seats {
	 //æµ
	 public  Map<String, ScanPool1>  busy=new HashMap<String, ScanPool1>();
	 //����  
	 public  AntiDuplicateLinkedBlockingQueue active=new AntiDuplicateLinkedBlockingQueue();
	 //����ռ��ʱ��  ��СΪ10����
	 private volatile int validtime;
	 //���ߵ���Чʱ��
	 public static int onlinetime;
	 public Seats(int validtime,int online) throws Exception{
		 if(validtime<=0 || online<0){
			 throw new RuntimeException("��Чʱ��������0");
		 }
		 	this.validtime=validtime;
		 	this.onlinetime=online;
	 }
	 private Seats(){};
	 /*����һ�����ߵ�  ����һ��æµ�� ����ͻ������� ����ϵͳ��ʱ����*/
	 public ScanPool1 aReduceBusy(){
		ScanPool1 ScanPool1 = active.take();//��ȡ���Ƴ��˶��е�ͷ������˶���Ϊ�գ��򷵻� null����
		if(ScanPool1!=null){
			//System.out.println("����һ�����ߵ�:"+JSON.toJSONString(ScanPool1));
			ScanPool1.setCreateTime(System.currentTimeMillis());
			busy.put(ScanPool1.getMark(),ScanPool1);//��ӱ�ռ�õĿͻ���ϯ
			return ScanPool1;
		}
		return null;
	 }
	 /*����һ��æµ�� ϵͳ��ʱ�ͺͿͻ��˵��ýӿ�*/
	 public ScanPool1 aAddActive(String mark){
		 	
			ScanPool1 ScanPool1 = busy.remove(mark);//�� key ��������ǰֵ;��� key û��ӳ���ϵ���򷵻� null 
			//System.out.println("����һ��æµ�Ŀ�ʼ"+mark+","+JSON.toJSONString(ScanPool1));
			ScanPool1 offer=null;
			if(ScanPool1!=null){
				//System.out.println("����һ��æµ��"+" "+JSON.toJSONString(ScanPool1));
				 offer = active.offer(ScanPool1);//��ָ��Ԫ�ز���˶��е�β����
			}
			return offer;
	 }
	 /*ˢ�����ߵ� �ͻ���ˢ�� */
	 public  void refresh(String mark){
		  active.refreshTime(mark);//���ָ��λ�õ�����
	 }
	 //�˳�
	 public  void outLogin(String mark){
		 SP sp=new SP();
		 sp.setMark(mark);
		 busy.remove(mark);//ɾ��æµ��
		 active.delete(sp);//ɾ�����
	 }
	 //���ڴ���
	 public  void relieveOccupy(){
		//System.out.println("��ѵ���");
		for(ScanPool1 scanpool:busy.values()){
			if(System.currentTimeMillis()-scanpool.getCreateTime()>this.validtime*1000){
				ScanPool1 remove = busy.remove(scanpool.getMark());//��ɾ��æµ��
				active.offer(remove);
				//System.out.println("æµ��ʱ��"+JSON.toJSONString(remove));
			}
		}
		this.active.delete(onlinetime);
	 }
	 
	/* public static void main(String[] args) {
		 SP sp=new SP();
		 sp.setMark("123");
		 ScanPool1 sp1=new ScanPool1();
		 sp1.setMark("123");
		 Seats seats=new Seats();
		 seats.active.add(sp1);
		 //System.out.println(seats.active.indexOf(sp));
	}*/
}
