package com.controller.htpdf;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
/** ʵ����һ
 * @author LiWang
 */
public class DocumentHandler2 extends DocumentHandlerParent{
	public DocumentHandler2(String templateDirectory, HttpServletRequest request, Map map) {
		super(templateDirectory, request, map);
		// TODO Auto-generated constructor stub
	}
	private static Logger log = LogManager.getLogger(DocumentHandler2.class.getName());
	private final static String[] DPDF={"1.pdf","55.pdf","9.pdf","10.pdf","11.pdf","18.pdf","20.pdf","21.pdf","25.1.pdf","25.pdf","26.pdf","5.pdf","2.pdf","27.pdf","28.pdf","29.pdf","30.pdf","31.pdf"};
	private final static String[] NPDF={"3.pdf","4.pdf","39.pdf","51.pdf"};
	//��������ֻʹ�� list ��addAll()����������
	@SuppressWarnings("unchecked")
	public Object fillTemplate () throws Exception{
			addn();//�������
    		//���ݴ���
			map.put("a","����������������");//ί����Ȩ����
			map.put("b", "���������������޹�˾");
			map.put("jxs","����");//������
			map.put("zh", "����");
			
    		DataConversion.dataDisposeToMap(map);
    		log.info("���ݼ�->"+map);
    		//��̬��������pdf
    		List<String> l1=new ArrayList<String>();
    		//����
    		List<String> l2=new  ArrayList<String>();
    		int iii=1;
    		if(Integer.parseInt(map.get("loanamount").toString())<150000){
    			l1.add("43.pdf");//��������
    			if(!map.get("gid").toString().equals("")){
    				l1.add("14.pdf");//������һ�ʹ��ַȷ����
    				l1.add("15.pdf");//������һ�ʹ��ַȷ����
    				l1.add("7.pdf");//������һ��ż��ͬ�����ŵ��
    				l2.add("47.pdf");//������һ��������
    				l2.add("53.pdf");//������һס���ʲ�֤��
        			iii++;
        		}
        		if(!map.get("gid2").toString().equals("")){
        			l1.add("16.pdf");//�����˶��ʹ��ַȷ����
        			l1.add("17.pdf");//�����˶��ʹ��ַȷ����
        			l1.add("8.pdf");//�����˶���ͬ�����ŵ��
        			l2.add("49.pdf");//�����˶���������
        			l2.add("54.pdf");//�����˶�ס���ʲ�֤��
        			iii++;
        		}
        		if(!map.get("pIDnumber").toString().equals("")){
        			l1.add("12.pdf");//�ʹ��ַȷ����
        			l1.add("13.pdf");//�ʹ��ַȷ����
        			l1.add("6.pdf");//��ͬ�����ŵ��
        			l1.add("45.pdf");//��������
        			l2.add("52.pdf");//��ż�����ʷ��ʲ�֤��
        			l1.remove("18.pdf");
        			iii++;
        			if(!map.get("gid").toString().equals("") && map.get("gid2").toString().equals("") ){
        				l1.add("19.pdf");//����˵����
        			}
        		}
    		}else{
    			l2.add("44.pdf");//����н������֤��
    			if(!map.get("gid").toString().equals("")){
    				l1.add("14.pdf");//������һ�ʹ��ַȷ����
    				l1.add("15.pdf");//������һ�ʹ��ַȷ����
    				l1.add("7.pdf");//������һ��ż��ͬ�����ŵ��
    				l2.add("48.pdf");//������һ����н������֤��
    				l2.add("53.pdf");//������һס���ʲ�֤��
        			iii++;
        		}
        		if(!map.get("gid2").toString().equals("")){
        			l1.add("16.pdf");//�����˶��ʹ��ַȷ����
        			l1.add("17.pdf");//�����˶��ʹ��ַȷ����
        			l1.add("8.pdf");//�����˶���ͬ�����ŵ��
        			l2.add("50.pdf");//�����˶�����н������֤��
        			l2.add("54.pdf");//�����˶�ס���ʲ�֤��
        			iii++;
        		}
        		if(!map.get("pIDnumber").toString().equals("")){
        			l1.add("12.pdf");//�ʹ��ַȷ����
        			l1.add("13.pdf");//�ʹ��ַȷ����
        			l1.add("6.pdf");//��ͬ�����ŵ��
        			l2.add("46.pdf");//����н������֤��
        			l2.add("52.pdf");//��ż�����ʷ��ʲ�֤��
        			l1.remove("18.pdf");
        			iii++;
        			if(!map.get("gid").toString().equals("") && map.get("gid2").toString().equals("") ){
        				l1.add("19.pdf");//����˵����
        			}
        		}
    		}
    		if(map.get("dztype").toString().equals("2")){//2���� 1������
    			l1.add("34.pdf");//����ݽ𴸰����ʹ�ã�����
    			l1.add("35.pdf");//ί�л�����Ȩ�����ʹ�� ������
    			l1.add("36.pdf");//ί�д���Э�������ʹ�� ������
    		}
    		if(iii>2){
    			l2.add("40.pdf");//���˵��
    		}
    		
			copys(l2.toArray(new String[l2.size()]))  ;
			pdfs(l1.toArray(new String[l1.size()]));
			copys(NPDF);
			pdfs(DPDF);
			addn();//�����
			Object s=endAssembly();
     	    addn();//�������
     	    return s;
		}
}

