package com.controller.htpdf;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.controller.htpdf.DocumentHandler2.WordDisposition;
/**���ݹ���
 * @Description:TODO
 * @author:LiWang
 * @time:2018��6��6��
 */
public class DataConversion{

	public static void dataDisposeToMap(Map data,WordDisposition wd){
			String totalamount="";
			String kpj="";//��Ʊ��
			data.put("jigou","������������Ϻ������޹�˾");
			data.put("a","����������������");//ί����Ȩ����
			data.put("b", "���������������޹�˾");
			data.put("papers_type","���֤");//֤������
			data.put("jxs","����");//������
			data.put("zh", "����");
			data.put("shul","Ҽ̨");
			try {
				String s =subZeroAndDot(data.get("f_rate").toString());//��������
				data.put("f_rate",s+"%");//��������
				totalamount=data.get("totalamount").toString();//�����ܶ�
				kpj=data.get("kpj").toString();//��Ʊ��
				data.put("kpj",subZeroAndDot(kpj));
				data.put("f_service_charge",// ���������ѽ��=���ڸ����ܶ�*���������ѷ���
						subZeroAndDot(DoubleUtil.ru(DoubleUtil.mul(totalamount, DoubleUtil.div(s, "100", 4).toString()).toString(),"1",2)));
				data.put("m_repayment",// ÿ�ڻ����=�����ڸ�����+���������ѣ�/���ڸ�������
						subZeroAndDot(DoubleUtil.ru(DoubleUtil.add(totalamount,data.get("f_service_charge").toString()),data.get("date").toString(),2)));
				data.put("totalamount", subZeroAndDot(totalamount));//�����ܶ�  ��������ܶ�С���������0
				data.put("loanamount",subZeroAndDot(data.get("loanamount").toString()));
				data.put("first_payment_ratio",subZeroAndDot(data.get("first_payment_ratio").toString()));//�״θ�����
				data.put("dmonty",NumberUtil.Test2(Double.parseDouble(data.get("loanamount").toString())));//�������д
				//shouyue(data.get("m_repayment").toString(),data.get("date").toString())�״θ�����
				data.put("dsmoney",NumberUtil.Test2(Double.parseDouble(data.get("first_payment_ratio").toString())));//�״θ������д
				data.put("dk",NumberUtil.Test2(Double.parseDouble(kpj)));//��Ʊ��
				data.put("dt",NumberUtil.Test2(Double.parseDouble(totalamount)));
				String ss=subZeroAndDot(DoubleUtil.mul(DoubleUtil.div(data.get("first_payment_ratio").toString(),data.get("kpj").toString(),4),"100"));
				data.put("down_payment_for",ss+"%");//�׸�����
				data.put("dbl",DoubleUtil.sub("100",ss)+"%");
				data.put("servicefee",subZeroAndDot(data.get("servicefee").toString()));//���ڷ���Ѵ���
				data.put("dserv",NumberUtil.Test2(Double.parseDouble(data.get("servicefee").toString())));//��д���ڷ����
			}catch (Exception e) {
				wd.code=0;
	    		wd.message="���������ݴ����쳣!";
	    		return; 
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
				wd.code=0;
	    		wd.message="�������ݴ���(���֤)�쳣!";
	    		return; 
			}
			try {
				data.put("xl",geteducation(data.get("xl").toString()));//ѧ��
				data.put("sr",subZeroAndDot(data.get("sr").toString()));//����
				data.put("zw", getduty(data.get("zw").toString()));//ְ��
				data.put("hf",getmarriage(data.get("hf").toString()));//���
				data.put("wsr",DoubleUtil.div2Scale(DoubleUtil.mul(data.get("sr").toString(),"12"),"10000"));//���� ����Ϊ��λ
			} catch (Exception e) {
				wd.code=0;
	    		wd.message="���˻�����Ϣ�����쳣!";
	    		return;
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
				wd.code=0;
	    		wd.message="������Ϣ�쳣!";
	    		return; 
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
				wd.code=0;
	    		wd.message="������һ���ݴ���(���֤)�쳣!";
	    		return; 
			}
			try {
				if(!data.get("gid2").toString().equals("")){
					map=getCarInfo(data.get("gid2").toString());
					data.put("gsex2",map.get("sex"));
					data.put("gage2",map.get("age"));
				}
			} catch (Exception e) {
				wd.code=0;
	    		wd.message="�����˶����ݴ���(���֤)�쳣!";
	    		return; 
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
				wd.code=0;
	    		wd.message="��ż������ݴ����쳣!";
	    		return; 
			}
			try {
				String[] tts =data.get("tt").toString().split("");
				for(int i=0;i<tts.length;i++){
					if(tts[i].length()>0){
						data.put("tt","http://a.kcway.net/assess/"+tts[i]);
						break;
					}
				}
			} catch (Exception e) {
				wd.code=0;
	    		wd.message="���֤������Ƭ�����쳣!";
	    		return; 
			}
			/*System.out.println(JSON.toJSON(data));*/
	}
	public static String getcolor(String c){
		String ss="��";
		try {
			 switch (Integer.parseInt(c)){
			 	case 1:
					ss="��";
					break;
				case 2:
					ss="��";
					break;
				case 3:
					 ss="��";
					 break;
				case 4:
					 ss="��";
					 break;
				case 5:
					ss="��";
					break;
				case 6:
					 ss="��";
					 break;
				case 7:
					ss="��";
					break;
				case 8:
					 ss="��";
					 break;
				case 9:
					 ss="��";
					 break;
				case 10:
					ss="��";
					break;
				case 11:
					 ss="��";
					 break;
				case 12:
					ss="��";
					break;
				case 13:
					 ss="��";
					 break;
				case 14:
					 ss="��";
					 break;
				case 15:
					ss="��";
					break;
				case 16:
					 ss="����";
					 break;
				case 17:
					ss="��";
					break;
				case 18:
					 ss="����";
					 break;
				case 19:
					 ss="����";
					 break;
				case 20:
					ss="��ɽ";
					break;
				case 0:
			 		ss="����ɫ";
			 		break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		 return ss;
	}
	
	/*ְ��
	 * @param s
	 * @return
	 * @Description: TODO
	 * @param name
	 * @return 
	 */
	private static String getduty(String s){
		String ss="ְԱ";
		try {
			 switch (Integer.parseInt(s)){
				case 0:
					 ss="��ҵ������";
					 break;
				case 1:
					ss="�ܾ���";
					break;
				case 2:
					 ss="���ž���";
					 break;
				default :
					ss="ְԱ";
					break; 
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		 return ss;
	}
	/**ѧ�����
	 * @param s
	 * @return
	 * @Description: TODO
	 * @param name
	 * @return 
	 */
	private static String geteducation(String s){
		String ss="����";
		try {
			 switch (Integer.parseInt(s)){
				case 1:
					 ss="Сѧ";
					 break;
				case 3:
					ss="����";
					break;
				case 4:
					 ss="��ר";
					 break;
				case 5:
					 ss="����";
					 break;
				case 6:
					ss="˶ʿ";
					break;
				case 7:
					 ss="��ʿ������";
					 break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		 return ss;
	}
	 /**�������
	 * @param s
	 * @return
	 * @Description: TODO
	 * @param name
	 * @return 
	 */
	private static String getmarriage(String s){
		 String ss="δ��";
		 try {
			 switch (Integer.parseInt(s)){
				case 1:
					 ss="�ѻ�";
					 break;
				case 2:
					ss="����";
					break;
				case 3:
					 ss="ɥż";
					 break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		 return ss;
	 }
	 
	/**�����˹�ϵ
	 * @param s
	 * @return
	 * @Description: TODO
	 * @param name
	 * @return 
	 */
	private static String getMutualborrowingR(String s){
		String ss="����";//Ĭ��
		try {
			switch (Integer.parseInt(s)){
				case 0:
					 ss="����";
					 break;
				case 1:
					 ss="����";
					 break;
				case 2:
					ss="ĸ��";
					break;
				case 7:
					 ss="��Ů";
					 break;
				case 8:
					 ss="ĸŮ";
					 break;
				case 3:
					ss="�ֵ�";
					break;
				case 6:
					 ss="����";
					 break;
				case 9:
					ss="����";
					break;
				case 10:
					ss="���";
					break;
				case 4:
					ss="ͬ��";
					break;
				case 5:
					ss="����";
					break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return ss;
	}
    /**��������
     */
    private static Map getCarInfo(String CardCode){ 
    	Map<String, Object> map = new HashMap<String, Object>();
   	 	String year="";
   	 	StringBuilder sb=new StringBuilder();
   	 	String sex;
   		year = CardCode.substring(6,10);// �õ����
   		if (Integer.parseInt(CardCode.substring(16).substring(0, 1)) % 2 == 0) {// �ж��Ա�
            sex = "Ů";
        } else {
            sex = "��";
        }  	
       Date date = new Date();// �õ���ǰ��ϵͳʱ��  
       SimpleDateFormat format = new SimpleDateFormat("yyyy"); 
       String fyear = format.format(date);
       int age = 0;  
       age=Integer.parseInt(fyear) - Integer.parseInt(year);
       map.put("sex", sex);
       map.put("age", age);
       map.put("birth", sb.append(year).append("��").append(Integer.parseInt(CardCode.substring(10,12))).append("��").append(Integer.parseInt(CardCode.substring(12,14))).append("��").toString());
       return map;  
   } 
	/**���¸��������
	 * @param s ÿ�»���
	 * @param s1 ����
	 */
	private static String shouyue(String s,String s1){
		int sum=0;
		String[] ss=s.split("\\.");
		if(s.indexOf(".")!=-1 && Integer.parseInt(ss[1])>0){//����С�� ����С�����ֲ�Ϊ0
			String z=DoubleUtil.mul("0."+ss[1],s1);//С�����ֳ��Է�������
			String[] ss1=z.split("\\.");
			if(z.indexOf(".")!=-1){//�� ΪС��
				if(Integer.parseInt(ss1[1])>0){
					sum=(Integer.parseInt(ss1[0])+Integer.parseInt(ss[0])+Integer.parseInt("1"));
				}else{
					sum=(Integer.parseInt(ss1[0])+Integer.parseInt(ss[0]));
				}
			}else{//��Ϊ����
				sum=(Integer.parseInt(z)+Integer.parseInt(ss[0]));
			}
		}else{//������С�� ���緵��
			 return s;
		}
		return sum+"";
	}
	/** 
     * ʹ��java������ʽȥ�������.��0 
     * @param s 
     * @return  
     */  
    public static String subZeroAndDot(String s){  
        if(s.indexOf(".") > 0){  
            s = s.replaceAll("0+?$", "").replaceAll("[.]$", "");  
        }  
        return s;  
    }
    /**��ס���
	 * @param s
	 * @return
	 * @Description: TODO
	 * @param name
	 * @return 
	 */
	private static String getreside(String s){
		String ss="����ס��";
		try {
			 switch (Integer.parseInt(s)){
				case 1:
					 ss="�ⷿ";
					 break;
				case 2:
					ss="���ڸ����";
					break;
				case 3:
					 ss="��������";
					 break;
				case 4:
					 ss="��λ����";
					 break;
				case 5:
					ss="����";
					break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		 return ss;
	}
	
	/**��λ����
	 * @return
	 * @Description: TODO
	 * @param name
	 * @return 
	 */
	private static String getworkunit(String s){
		String ss="����";
		try {
			 switch (Integer.parseInt(s)){
				case 0:
					 ss="����";
					 break;
				case 1:
					ss="���徭��";
					break;
				case 2:
					 ss="˽Ӫ";
					 break;
				case 3:
					 ss="��Ӫ";
					 break;
				case 4:
					ss="�ɷݺ���";
					break;
				case 5:
					 ss="�����ɷ���";
					 break;
				case 6:
					 ss="����";
					 break;
				case 7:
					 ss="����";
					 break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		 return ss;
	}
	
	/**������ҵ
	 * @return
	 * @Description: TODO
	 * @param name
	 * @return 
	 */
	private static String getindustry(String s){
		String ss="����";
		try {
			 switch (Integer.parseInt(s)){
				case 0:
					 ss="ũ������";
					 break;
				case 1:
					ss="�ʵ�ͨѶ";
					break;
				case 2:
					 ss="���ز�";
					 break;
				case 3:
					 ss="�ƽ�����";
					 break;
				case 4:
					ss="��ҵ";
					break;
				case 5:
					 ss="����";
					 break;
				case 6:
					ss="֤ȯ";
					break;
				case 7:
					 ss="����";
					 break;
				case 8:
					 ss="��ҵ";
					 break;
				case 9:
					ss="��������";
					break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		 return ss;
	}
	/**ְҵ
	 * @param s
	 * @return
	 * @Description: TODO
	 * @param name
	 * @return 
	 */
	private static String getProfessional(String s){
		String ss="����";
		try {
			 switch (Integer.parseInt(s)){
				case 0:
					 ss="����Ա";
					 break;
				case 1:
					ss="��ҵ��λԱ��";
					break;
				case 2:
					 ss="����";
					 break;
				case 3:
					 ss="ũ��";
					 break;
				case 4:
					ss="����";
					break;
				case 5:
					 ss="ְԱ";
					 break;
				case 6:
					ss="˽��ҵ��";
					break;
				case 7:
					ss="ѧ��";
					break;
				case 8:
					 ss="����ְҵ";
					 break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		 return ss;
	}
}
