package com.controller.htpdf;
import java.io.File;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
/** ʵ����һ
 * @author LiWang
 */
public class DocumentHandler3 extends DocumentHandlerParent{
	public DocumentHandler3(String templateDirectory, HttpServletRequest request, Map map) {
		super(templateDirectory, request, map);
		// TODO Auto-generated constructor stub
	}
	private static Logger log = LogManager.getLogger(DocumentHandler3.class.getName());
	@SuppressWarnings("unchecked")
	public String fillTemplate () throws Exception{
		
		String pgj=map.get("pgj").toString();
		map.put("pgj1",pgj);//������ԭֵ 
		map.put("dpgj",NumberUtil.Test2(Double.parseDouble(pgj)));//������ ����Ԫ
		String hf=map.get("hf").toString();
		if(hf.equals("0")){//δ��
			map.put("hf1", "true");
		}else if(hf.equals("2")){//����
			map.put("hf2", "true");
		}else{//ɥż
			map.put("hf3", "true");
		}
		DataConversion.dataDisposeToMap(map);
		
		//ʣ����ڷ����  ���ڷ����-�����ܶ�*0.01 1%
		map.put("sy_servicefee",DataConversionParent.subZeroAndDot(DoubleUtil.sub(map.get("servicefee").toString(),DoubleUtil.mul(map.get("totalamount").toString(),"0.01"))));
		//�������:��Ϣ�ϼ�/������*0.1 10%
		map.put("dkcs",DataConversionParent.subZeroAndDot(DoubleUtil.div(map.get("allReimbursement").toString(),DoubleUtil.mul(pgj, "0.1"),4)));
		map.put("Dpgj",NumberUtil.Test2(Double.parseDouble(map.get("pgj").toString())));//������ ��ԪΪ��λ
		
		//���ݴ���
		map.put("jxs","�㽭�α������ʵ������޹�˾");//������
		map.put("jxs1", "�㽭�α������ʵ���");
		//Ʒ���ͺŴ���
		//ծȨ��
		map.put("zqr","�й��������йɷ����޹�˾���ݳ�վ֧��");
		map.put("zh", "��վ");
		map.put("zh1","��վ֧��");
		map.put("zh2","���к��ݳ�վ֧��");
		//ծ����
		if(""==map.get("pname") || map.get("pname").toString().equals("null")){
			map.put("zwr",map.get("name").toString());
		}else{//������ż�����
			map.put("zwr",new StringBuilder(map.get("name").toString()).append("          ").append(map.get("pname").toString()));
		}
		//���������� ��λ��
		map.put("zdrnsr",DataConversionParent.subZeroAndDot(DoubleUtil.div(DoubleUtil.mul(map.get("sr").toString(),"12"),"10000")));
		
		//��ż������ ��λ��
		if(map.get("posr").toString()=="" || map.get("posr").toString().equals("null")){
			map.put("ponsr","0");
		}else{
			map.put("ponsr",DataConversionParent.subZeroAndDot(DoubleUtil.div(DoubleUtil.mul(map.get("posr").toString(),"12"),"10000")));
		}
		//ĵ�������ڸ�����ǻ�������ѯ�� ��ͬ�����˼������ż ������һ��
		if(map.get("pIDnumber").toString()!="" && !map.get("pIDnumber").toString().equals("null")){
			map.put("gthkrjk_name", map.get("pname").toString());//Ϊ��ż����
			map.put("gthkrjk_IDnumber", map.get("pIDnumber").toString());//���֤��
			
			map.put("gthkrjk_gx", "��ż");//��ϵ
			map.put("gthkrjk_dwmc", map.get("pdw").toString());//��λ����
			map.put("gthkrjk_dwdz", map.get("pdwdz").toString());//������λ
			map.put("gthkrjk_zw", map.get("pzw").toString());//ְλ
			map.put("gthkrjk_ysr", map.get("posr").toString());//������
			map.put("gthkrjk_tel", map.get("pdh").toString());//�绰
		}else if(map.get("gid").toString()!="" && !map.get("gid").toString().equals("null")){//������һ
			map.put("gthkrjk_name", map.get("gthk").toString());//Ϊ��ż����
			map.put("gthkrjk_IDnumber", map.get("gid").toString());
			
			map.put("gthkrjk_gx", map.get("ggx").toString());
			map.put("gthkrjk_dwmc", map.get("ggzdw").toString());
			map.put("gthkrjk_dwdz", map.get("gdwdz").toString());
		
			map.put("gthkrjk_tel", map.get("gtel").toString());
		}
		//�������д�ֽ�  ������Ǫ½�۾�ʰ�ⷡ������
		NumberUtil.numberSubchinese(map.get("loanamount").toString(),map,"dkje");
		//�����ܶ� ��д�ֽ�
		NumberUtil.numberSubchinese(map.get("totalamount").toString(),map,"dkze");
		//���ڷ���� ��д�ֽ�
		NumberUtil.numberSubchinese(map.get("servicefee").toString(),map,"jrfuf");
		//������һѧ��
		//gjr1xl
		//�����˶�ѧ��
		//gjr2xl
		//������һ����
		//gjr1sr
		//�����˶�����
		//gjr2sr
		//������һְ��
		//gjr1zw
		//�����˶�ְ��
		//gjr2zw
		//������һ������ ��λ��
		//gjr1nsr
		//�����˶������� ��λ��
		//gjr2nsr
		//��ͥ�����뵥λ�� ��������������+��ż�����룩*12
		map.put("jtnsr",DataConversionParent.subZeroAndDot(DoubleUtil.add(map.get("zdrnsr").toString(), map.get("ponsr").toString())));
		//������һ��ͥ������
//		gjr1jtnsr
		//�����˶���ͥ������
//		gjr2jtnsr
		//https://www.cnblogs.com/yaya-yaya/p/6096539.html
		//isEmpty() �� isBlank() �� null �� ���ַ���("")���ж���ͬ��Ψһ������ǶԿհ��ַ�����ո��Ʊ�������жϡ���Կհ��ַ�" "��isEmpty()����false��isBlank()����true.
		//��������
		StringBuilder fdbr=new StringBuilder();
		if(!StringUtils.isBlank(map.get("gthk").toString()) && StringUtils.isBlank(map.get("gthk2").toString())){
			map.put("fdbr", map.get("gthk").toString());
		}else if(StringUtils.isBlank(map.get("gthk").toString()) && !StringUtils.isBlank(map.get("gthk2").toString())){
			map.put("fdbr", map.get("gthk2").toString());
		}else{
			map.put("fdbr",new StringBuilder(map.get("gthk").toString()).append("          ").append(map.get("gthk2").toString()));
		}
		int index=getLetterFirstIndex(map.get("p_x").toString());
		//Ʒ���ͺ����Ĳ���
		map.put("p_x_1", map.get("p_x").toString().substring(0,index));
		//Ʒ���ͺ�Ӣ�Ĳ���
		map.put("p_x_2", map.get("p_x").toString().substring(index));
		//����������
		map.put("date_year",DoubleUtil.div(map.get("date").toString(),"12",0));
	
		log.info("���ݼ�->"+map);
		
		File[] files=new File(pdftemplatepath).listFiles();
		boolean b=map.get("hf").toString().equals("�ѻ�");
		for(File f:files){
			String s=f.getName();
			
			if(		(s.equals("6.����״�������߽���.pdf") && !b) //ֻ���ѻ�����������
					|| (s.equals("7.����֤���������ˣ�.pdf") && b) //������ѻ���ȥ����������
					|| (s.equals("8.����֤����������һ�� .pdf") && ( map.get("gid").equals("") || map.get("ghf").toString().equals("�ѻ�"))) //�й���һ��������δ������������
					//�������� ����н��  ��ͬ�����ŵ
					|| ((s.indexOf("10.")!=-1 || s.indexOf("14.")!=-1 || s.indexOf("18.")!=-1) && map.get("pIDnumber").equals("")
					|| ((s.indexOf("11.")!=-1 || s.indexOf("15.")!=-1 || s.indexOf("19.")!=-1) && map.get("gid").equals("")) 
					|| ((s.indexOf("12.")!=-1 || s.indexOf("16.")!=-1 || s.indexOf("20.")!=-1) && map.get("gid2").equals(""))
					
					)){
				log.info("��ȥ�ĺ�ͬ->"+s);
				continue;
			}
			
			if(s.indexOf("NO")!=-1){
				
				copy(f.getName());
			}else{
				pdf(s);
			}
		}
		return endAssembly();
	}
		
}

