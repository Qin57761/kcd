package com.controller.erp_icbc.YunXin.seats;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.controller.erp_icbc.YunXin.DataUtil;
/** ��������ģʽ �Ƚ��ȳ�
 * @author:LiWang
 * @time:2019
 */
public class PoolCache1 {
	 private static Logger log = LogManager.getLogger(PoolCache1.class);
	 //����
	 public static HashMap<String,LinkedList> container=new HashMap<String,LinkedList>();
	 //æµ
	 private static  Map<String,ScanPool1> busy=new HashMap<String, ScanPool1>();
	//ˢ�¼��ʱ�� ��λ��
	private static int cleanIntervalSecond =20000;//���ˢ��ʱ�� Ĭ��90��=90000
	public PoolCache1(){};
	static void initContainer(){
		new Thread(new Runnable(){
			@Override
			public void run(){
				while (true){
					try{
						Thread.sleep(cleanIntervalSecond);//1����=0.001�� 
					} catch (InterruptedException e){ //����κ��߳��ж��˵�ǰ�̡߳����׳����쳣ʱ����ǰ�̵߳��ж�״̬ �����
						e.printStackTrace();
					}
					timeoutDetection();
				}
			}
		}).start();
	} 
	public static ScanPool1 isMarkToBusy(String mark){
		log.info("æµ��"+JSON.toJSONString(busy));
		return busy.get(mark);
	}
	public static ScanPool1 add(ScanPool1 scanPool){
		String key=scanPool.getBankId();
		LinkedList seats=container.get(key);
		Long createTime=System.currentTimeMillis();
		if(seats!=null){//�ж��Ƿ���������е���ϯ
			//���ش��б����״γ��ֵ�ָ��Ԫ�ص�������������б��в�������Ԫ�أ��򷵻� -1��
	    	int index=seats.indexOf(scanPool);
	        if (index!=-1) {//����
	        	//�ҵ����Ԫ��
	        	ScanPool1 scanPool2=(ScanPool1) seats.get(index);
	        	//ռ�õĿ�ʼʱ�������һ�ε�½��ʱ��
				String previousTime=DataUtil.millisecondTodate(scanPool2.getCreateTime());
				scanPool2.setPreviousTime(previousTime);
				//ռ�õĽ���ʱ�� ���ߴ˴ε�½�Ŀ�ʼʱ��
				String laterTime=DataUtil.millisecondTodate(createTime);
				scanPool2.setLaterTime(laterTime);
	        	scanPool2.setCreateTime(createTime);
	        	log.info("����һ����Ƶ��->"+scanPool2);
	        	return scanPool2;
	        }
		}else{
			seats=new LinkedList();
			container.put(key, seats);
		}
		//���һ�θ��µ�ʱ��
		String laterTime=DataUtil.millisecondTodate(createTime);
		scanPool.setLaterTime(laterTime);
		scanPool.setCreateTime(System.currentTimeMillis());//��ӵ�½��ʼʱ��
		//���߶���������β������Ԫ�أ���ͬ��ʱ�򣬵��������н��޵�ʱ��add�����������׳��쳣���㴦����offer����������ֱ�ӷ���false
        if(!seats.offer(scanPool)){
        	scanPool=null;
        }
        log.info("����һ����Ƶ��->"+scanPool);
		return scanPool;
	}
	
	public static Object deleteActive(ScanPool1 sp){
		LinkedList seats=container.get(sp.getBankId());
		boolean b=false;
		if(seats!=null){
			b=seats.remove(sp);//�Ӵ��б����Ƴ��״γ��ֵ�ָ��Ԫ�أ�������ڣ�������б�������Ԫ�أ��������ġ���ȷ�еؽ����Ƴ��������� (o==null ? get(i)==null : o.equals(get(i))) ��������� i ��Ԫ�أ��������������Ԫ�أ���������б��Ѱ���ָ��Ԫ�أ����ߴ��б����ڵ��ö��������ģ����򷵻� true�� 
			log.info("Web���յ�Becall(��Ծ)��ɾ����Ծ���->"+b);
		}
		HashMap map=new HashMap();
		map.put("active",b);
		map.put("SP", sp);
		return map;
	}
	
	public static Map outLogin(ScanPool1 sp){
		log.info("�ͻ����˳�start"+sp);
		LinkedList seats=container.get(sp.getBankId());
		boolean b=false;
		if(seats!=null){
			b=seats.remove(sp);//�Ӵ��б����Ƴ��״γ��ֵ�ָ��Ԫ�أ�������ڣ�������б�������Ԫ�أ��������ġ���ȷ�еؽ����Ƴ��������� (o==null ? get(i)==null : o.equals(get(i))) ��������� i ��Ԫ�أ��������������Ԫ�أ���������б��Ѱ���ָ��Ԫ�أ����ߴ��б����ڵ��ö��������ģ����򷵻� true�� 
			log.info("�ͻ����˳�end(��Ծ)�����->"+b);
		}
		ScanPool1 scanPool1=busy.remove(sp.getMark());//�� key �����ľ�ֵ����� key û���κ�ӳ���ϵ���򷵻� null�������� null �����ܱ�ʾ��ӳ��֮ǰ�� null �� key ��������
		log.info("�ͻ����˳�end(æµ)�����->"+scanPool1);
		
		//���ظ��ͻ��˵���Ϣ
		HashMap map=new HashMap();
		map.put("SP", sp.toString());
		//trueΪ��Ծ�˳�
		map.put("active",b);
		//trueΪæµ�˳�
		map.put("busy",scanPool1==null?false:true);
		return map;
	}
	//�ͻ���ˢ��
	public static ScanPool1 refresh(String key,String mark){
		log.info("�ͻ���ˢ��start");
		LinkedList seats=container.get(key);
		if(seats!=null){
			SP sp=new SP();
			sp.setMark(mark);
			int index=seats.indexOf(sp);
			if(index!=-1){
				ScanPool1 scanPool1=(ScanPool1) seats.get(index);
				//֮ǰ��Ծ��ʼ��ʱ��
				String previousTime=DataUtil.millisecondTodate(scanPool1.getCreateTime());
				
				Long createTime=System.currentTimeMillis();
				scanPool1.setCreateTime(createTime);
				
				//֮���Ծ��ʼ��ʱ��
				String laterTime=DataUtil.millisecondTodate(createTime);
				scanPool1.setPreviousTime(previousTime);
				scanPool1.setLaterTime(laterTime);
				
				log.info("�ͻ���ˢ��end->"+scanPool1);
				return scanPool1;
			}
		}
		return null;
	}

	//ɾ�����ߵ� ����æµ��
	public static ScanPool1 aReduceBusy(String key){
		LinkedList seats=container.get(key);
		if(seats!=null){
			ScanPool1 scanPool1= (ScanPool1) seats.poll();// ��ȡ���Ƴ����б��ͷ����һ��Ԫ�أ�
			if(scanPool1!=null){
				//֮ǰ��Ծ��ʼ��ʱ��
				String previousTime=DataUtil.millisecondTodate(scanPool1.getCreateTime());
				Long createTime=System.currentTimeMillis();
				scanPool1.setCreateTime(createTime);//���߿�ʼʱ��
				//֮��æµ��ʼ��ʱ��
				String laterTime=DataUtil.millisecondTodate(createTime);
				scanPool1.setPreviousTime(previousTime);
				scanPool1.setLaterTime(laterTime);
				
				busy.put(scanPool1.getMark(), scanPool1);//���æµ�е�
				return scanPool1;
			}
		}
		return null;
	}
	public static ScanPool1 defaultSeat(){
		if(container.isEmpty()){
			return null;
		}else{
			for (Map.Entry entry : container.entrySet()) { 
				String key=(String) entry.getKey();
				ScanPool1 scanPool1=aReduceBusy(key);
				if(scanPool1!=null){
					return scanPool1;
				}
			}
			return null;
		}
	}
	public static ScanPool1 deleteBusy(String mark){
		ScanPool1 scanPool1=busy.remove(mark);//ɾ��æµ�е�
		if(scanPool1!=null){
			//֮ǰ��Ծ��ʼ��ʱ��
			scanPool1=add(scanPool1);
			return scanPool1;
		}else{
			return null;
		}
	}
	public static void timeoutDetection(){
		for (Map.Entry entry : container.entrySet()) { 
			LinkedList seats=(LinkedList) entry.getValue();
			String key=(String) entry.getKey();
			int seatsSize=seats.size();
			 if(seatsSize>0){
				 for(int i=0;i<seatsSize;i++){
					 	ScanPool1 scanpool1=(ScanPool1) seats.get(i);
					 	if(System.currentTimeMillis()-scanpool1.getCreateTime()>scanpool1.getOnlinetime()){
							boolean b=seats.remove(scanpool1);
							//�Ӵ��б����Ƴ��״γ��ֵ�ָ��Ԫ�أ�������ڣ�������б�������Ԫ�أ��������ġ���ȷ�еؽ����Ƴ��������� (o==null ? get(i)==null : o.equals(get(i))) ��������� i ��Ԫ�أ��������������Ԫ�أ���
							//������б��Ѱ���ָ��Ԫ�أ����ߴ��б����ڵ��ö��������ģ����򷵻� true�� 
							log.info("��ʱ����:ɾ��һ����ʱ���ߵ�(1)->"+scanpool1+",ɾ�����->"+b);
						}
				 }
			 }else{
				 log.info("��մ�����->"+key);
				 container.remove(key);
			 }
		}
		for(ScanPool1 scanpool1:busy.values()){
			if(System.currentTimeMillis()-scanpool1.getCreateTime()>scanpool1.getValidtime()){
				ScanPool1 remove1=busy.remove(scanpool1.getMark());//�� key �����ľ�ֵ����� key û���κ�ӳ���ϵ���򷵻� null�������� null �����ܱ�ʾ��ӳ��֮ǰ�� null �� key ��������		
				log.info("��ʱ����:ɾ��һ��æµ��->"+scanpool1);
				//add(remove1);
			}
		}
	}
}
