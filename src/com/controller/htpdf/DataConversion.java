package com.controller.htpdf;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
/**���ݹ���
 * @Description:TODO
 * @author:LiWang
 * @time:2018��6��6��
 */
public class DataConversion extends DataConversionParent{
	private static Logger log = LogManager.getLogger(DataConversion.class.getName());
	public static void dataDisposeToMap(Map data){
			data.put("jigou","������������Ϻ������޹�˾");
			data.put("papers_type","���֤");//֤������
			data.put("shul","Ҽ̨");
			String totalamount="";
			String kpj="";//��Ʊ��
			try {
				String s =subZeroAndDot(data.get("f_rate").toString());//��������
				data.put("f_rate",s+"%");//��������
				totalamount=data.get("totalamount").toString();//�����ܶ�
				kpj=data.get("kpj").toString();//��Ʊ��
				data.put("kpj",subZeroAndDot(kpj));
				data.put("f_service_charge",// ���������ѽ��=���ڸ����ܶ�*���������ѷ���
						subZeroAndDot(DoubleUtil.ru(DoubleUtil.mul(totalamount, DoubleUtil.div(s, "100", 4).toString()).toString(),"1",2)));
				String allReimbursement=DoubleUtil.add(totalamount,data.get("f_service_charge").toString());//��Ϣ�ϼ�=�����ܶ�+���������ѽ��
				data.put("allReimbursement",allReimbursement);
				data.put("m_repayment",// ÿ�ڻ����=�����ڸ�����+���������ѣ�/���ڸ�������
						subZeroAndDot(DoubleUtil.ru(allReimbursement,data.get("date").toString(),2)));
				data.put("totalamount", subZeroAndDot(totalamount));//�����ܶ�  ��������ܶ�С���������0
				
				data.put("loanamount",subZeroAndDot(data.get("loanamount").toString()));//������
				data.put("first_payment_ratio",subZeroAndDot(data.get("first_payment_ratio").toString()));//�״θ�����
				data.put("dmonty",NumberUtil.Test2(Double.parseDouble(data.get("loanamount").toString())));//�������д
				data.put("dsmoney",NumberUtil.Test2(Double.parseDouble(data.get("first_payment_ratio").toString())));//�״θ������д
				data.put("dk",NumberUtil.Test2(Double.parseDouble(kpj)));//��Ʊ��
				data.put("dt",NumberUtil.Test2(Double.parseDouble(totalamount)));
				String ss=subZeroAndDot(DoubleUtil.mul(DoubleUtil.div(data.get("first_payment_ratio").toString(),data.get("kpj").toString(),4),"100"));
				data.put("down_payment_for",ss+"%");//�׸�����
				data.put("down_payment_for1",ss);//�׸����� �����ٷֺ�
				data.put("dbl",DoubleUtil.sub("100",ss)+"%");
				data.put("servicefee",subZeroAndDot(data.get("servicefee").toString()));//���ڷ���Ѵ���
				data.put("dserv",NumberUtil.Test2(Double.parseDouble(data.get("servicefee").toString())));//��д���ڷ����
			}catch (Exception e) {
	    		throw new RuntimeException("���������ݴ����쳣!");
			}
			String IDnumber=null;
			Map map=null;
			try {
				IDnumber=data.get("IDnumber").toString();//���֤��
				data.put("dkyh", "��������֧��");
				data.put("year",IDnumber.substring(6,10));//��
				data.put("month",IDnumber.substring(10,12));//��
				data.put("day",IDnumber.substring(12,14));//��
				//�������֤��ȡ�������Ա�������Ϣ
				map=getCarInfo(IDnumber);
				data.put("sex",map.get("sex"));
				data.put("age",map.get("age"));
				data.put("cb1","true");
				data.put("cb6","true");
				data.put("pgx","��ż");//��ż��ϵ ��ż
				data.put("zcsrq",map.get("birth"));
				if(data.get("sex").toString().equals("��")){
					data.put("sexn","true");
				}else{
					data.put("sexv","true");
				}
			}catch (Exception e) { 
	    		throw new RuntimeException("�������ݴ���(���֤)�쳣!");
			}
			try {
				data.put("xl",geteducation(data.get("xl").toString()));//ѧ��
				data.put("sr",subZeroAndDot(data.get("sr").toString()));//����
				data.put("zw", getduty(data.get("zw").toString()));//ְ��
				data.put("hf",getmarriage(data.get("hf").toString()));//���
				data.put("wsr",DoubleUtil.div2Scale(DoubleUtil.mul(data.get("sr").toString(),"12"),"10000"));//���� ����Ϊ��λ
			} catch (Exception e) {
	    		throw new RuntimeException("���˻�����Ϣ�����쳣!");
			}
			//��������
			try {
				if(data.get("carstype").toString().equals("1")){//�³�
					data.put("x1", "true");
				}else{
					data.put("x2", "true");
				}
				data.put("ys",getcolor(data.get("ys").toString()));//��ɫ
				data.put("pgj",subZeroAndDot(DoubleUtil.div(data.get("pgj").toString(),"10000",2)));//������ ��ԪΪ��λ
				data.put("c",subZeroAndDot(DoubleUtil.mul(DoubleUtil.div2Scale(totalamount,kpj),"100"))+"%");// ���ڱ���/�����۸����
			} catch (Exception e2) {
				throw new RuntimeException("������Ϣ�쳣!");
			}
			try {
				if(!data.get("gid").toString().equals("")){//�����˴���
					map=getCarInfo(data.get("gid").toString());
					data.put("gsex",map.get("sex"));
					data.put("gage",map.get("age"));
					data.put("ghf",getmarriage(data.get("ghf").toString()));//�������Ƿ��ѻ�
				}else{
					data.put("ggx", "");
					data.put("ghf", "");
				}
			} catch (Exception e) {
	    		throw new RuntimeException("������һ���ݴ���(���֤)�쳣!");
			}
			try {
				if(!data.get("gid2").toString().equals("")){
					map=getCarInfo(data.get("gid2").toString());
					data.put("gsex2",map.get("sex"));
					data.put("gage2",map.get("age"));
				}
			} catch (Exception e) {
	    		throw new RuntimeException("�����˶����ݴ���(���֤)�쳣!");
			}
			try {
				if(!data.get("pIDnumber").toString().equals("")){//��ż����
					String pIDnumber=data.get("pIDnumber").toString();
					data.put("pyear",pIDnumber.substring(6,10));
					data.put("pmonth",pIDnumber.substring(10,12));
					data.put("pday",pIDnumber.substring(12,14));
					//�������֤��ȡ��ż�Ա�������Ϣ	
					map=getCarInfo(pIDnumber);
					data.put("psex",map.get("sex"));
					data.put("page",map.get("age"));
					data.put("posr",subZeroAndDot(data.get("posr").toString()));//��ż����
					data.put("pxl",geteducation(data.get("pxl").toString()));//��żѧ��
					data.put("pcsrq",map.get("birth"));
					data.put("jtsr",DoubleUtil.div2Scale(DoubleUtil.mul(DoubleUtil.add(data.get("posr").toString(),data.get("sr").toString()),"12"),"10000"));
					data.put("pwsr",DoubleUtil.div2Scale(DoubleUtil.mul(data.get("posr").toString(),"12"),"10000"));
				}else{
					data.put("posr","");
					data.put("pxl", "");
					data.put("jtsr",DoubleUtil.div2Scale(DoubleUtil.mul(data.get("sr").toString(),"12"),"10000"));
				}
			} catch (Exception e) {
	    		throw new RuntimeException("��ż������ݴ����쳣!");
			}
			//ͼƬ����
			try {
				int count=0;
				/*����ʾ����
				C:/Users/Administrator/Desktop/word/6.jpgC:/Users/Administrator/Desktop/word/6.jpgupload/2018/12/05/19c43d2433d75441c2dd7be45a604ff8.jpgupload/2018/12/05/250126c780fd93b29e175db5ec00db8d.jpg
				*/
				String[] tts =data.get("tt").toString().split("");
				for(int i=0;i<tts.length;i++){
					if(tts[i].length()>0){
						count++;
						if(count==1){
							data.put("tt",DocumentHandlerParent.download_prefix+tts[i]);
						}else if(count==2){
							data.put("reverseIdCard_img",DocumentHandlerParent.root_Directory+tts[i]);
							break;
						}
					}
				}
			} catch (Exception e) {
				log.error("���֤��Ƭ�����쳣!->"+e);
				throw new RuntimeException("���֤��Ƭ�����쳣!");
			}
	}
}
