package com.controller.erp_icbc.YunXin.seats;
import java.util.LinkedList;
import com.alibaba.fastjson.JSON;
/** ��ֹ���� ��ֹ�ظ� �Ƚ��ȳ� 
 * @Description:TODO
 * @author:LiWang
 * @time:2018��8��18��
 */
public class AntiDuplicateLinkedBlockingQueue{
    public final LinkedList<ScanPool1> list = new LinkedList();
    //���
    public synchronized ScanPool1 offer(ScanPool1 scanpool) {
    	scanpool.setCreateTime(System.currentTimeMillis());//���µ�½��ʼʱ��
        if (list.contains(scanpool)) {//������ֱ�ӷ���
        	//System.out.println("���º�"+scanpool.getMark());
        	return scanpool;
        }
        if(list.offer(scanpool)){//����������ӵ���β 
        	//System.out.println("���һ����Ծ�ģ�"+JSON.toJSONString(list));
        	  return scanpool;//��ӳɹ� ����
        }
        return null;//���ʧ�� ���Ҳ�����
    }
    //��ȡ���һ�� 
    public synchronized ScanPool1 take() {
        return list.poll();
    }
    //ɾ��һ��
    public synchronized void delete(SP sp) {
    	//System.out.println("ɾ��һ����ʱ���ߵ�"+sp.getMark());
    	boolean remove = list.remove(sp);
    }
    public void delete(int validtime){
    	for(ScanPool1 scanpool:this.list){
    		//System.out.println("��ǰʱ�䣺"+System.currentTimeMillis()+" ���ʼ"+scanpool.getCreateTime()+" ��Чʱ��"+validtime);
			if(System.currentTimeMillis()-scanpool.getCreateTime()>validtime*1000){
				delete(scanpool);
			}
		}
    }
    /*���»�Ծʱ���������ȶ����޸�ʱ�䣬�������ܳ��ַ��ص���������������value�����¸��µ�Ϊ������; ���Ը��µ�ͬʱ����ɾ�������ӣ�Ҳ��ᵼ������ƫ��
	�������Ҳ���ͬ������ ���������ʱ�����Ӱ��
	*/
    public void refreshTime(String mark){
    	try {
    		SP sp=new SP();
        	sp.setMark(mark);
        	//�����ͨԒ�Ќ�get����
        	list.get(list.indexOf(sp)).setCreateTime(System.currentTimeMillis());//����ʱ��
        	//System.out.println("���£�"+mark);
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
}

