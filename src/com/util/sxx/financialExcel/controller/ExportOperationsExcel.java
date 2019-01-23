package com.util.sxx.financialExcel.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import freemarker.template.utility.StringUtil;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import net.sf.json.JSONObject;

public class ExportOperationsExcel {
	
//		//��2�Ķ�����������
//		static String[] Lev2HeaderName2 = {"���", "������������", "����", "����ͨ����", "����������ͨ����", "ʵ������ת����", "����ָ��", "���������ų�������", "�����׼", "Ӧ������"
//				, "������", "Ӧ�������ܼ�", "��ע������ԭ��"};
//		//��3-4�Ķ�����������
//		static String[] Lev2HeaderName3 = {"���", "������������", "����", "������", "�ͻ�����", "��ϵ�绰", "��ͬ��Ϣ�ϼ�", "���˱�׼"};
//		//��5-7�Ķ�����������
//		static String[] Lev2HeaderName5 = {"���", "������������", "����", "������", "�ͻ�����", "��ϵ�绰", "��ͬ��Ϣ�ϼ�", "ʣ���ͬ��Ϣ", "���˱�׼", "��Ѻ��֤��"};
//		//��8�Ķ�����������
//		static String[] Lev2HeaderName8 = {"���", "������������", "����", "���зſ������", "���зſ�����", "��Ӧִ�н�������", "�������"};
//		//��9�Ķ�����������
//		static String[] Lev2HeaderName9 = {"���", "������������", "����", "������", "�ͻ�����", "��ϵ�绰", "֧���������ڱ���ʱ��", "֧���������ڱ�����", "�Ƿ����ύ�ᳵ����"
//				, "��ع������ڱ���ʱ��", "�����������г���5����ͣ��ѯ���ţ�", "��Ϣ��׼/1-2��", "��Ϣ��׼/��3����", "���յ����Ϣ1", "�Ƿ��Ѽĳ�����", "ֽ�ʲ��ϼĳ�ʱ��", "��������"
//				, "��Ϣ��׼/1-2��", "��Ϣ��׼/��3����", "���յ����Ϣ2", "�����������ʱ��", "���������Ƿ��ѽ������", "����������������ʱ��", "������ⳬ������", "��Ϣ��׼/1-2��"
//				, "��Ϣ��׼/��3����", "���յ����Ϣ3", "�Ƿ��˵�", "�������������˵�ʱ��", "���������Ƿ��Ѵ�ص��", "�յ�����������ص��ʱ��", "��������", "��Ϣ��׼/1-2��"
//				, "��Ϣ��׼/��3����", "���յ����Ϣ4", "���յ����Ϣ�ϼ�"};
		
		//��ȡWritableWorkbook
			public static WritableWorkbook GetFile(String adress){
				File file = new File(adress);
				 WritableWorkbook wk = null;
				try {
					wk = Workbook.createWorkbook(file);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println(111);
					e.printStackTrace();
				}
				return wk;
			}
		
				
		/**
		 * ���������������ñ��ʽ�ķ���			-----���ɷǲ�ѯ�ֶεı���Լ���������
		 * @param list1		����ʱ������Ŀͻ�������Ϣ
		 * @param list2 	���еĴ󲿷�ʱ������
		 * @param list3		���ڻ�����
		 * @return
		 * @throws Exception
		 */
		public static void CreateExcel(List<Map> list1, List<Map> list2, List<Map> list3) throws Exception{
			
		    WritableWorkbook wk = ExportOperationsExcel.GetFile("D://��Ӫ�������ݿ�_2018-12-20_���ݰ�.xlsx");
		    
		    //1.������
		    WritableSheet sheet1 = wk.createSheet("��1-ҵ��̨��",0);
//		    WritableSheet sheet2 = wk.createSheet("��2-����ת���ʿ���̨��",0);
//		    WritableSheet sheet3 = wk.createSheet("��3-�ܲ��ĳ�����10���δ���׶���������Ԥ��",0);
//		    WritableSheet sheet4 = wk.createSheet("��4-���зſ�20���δ���׶���������Ԥ��",0);
//		    WritableSheet sheet5 = wk.createSheet("��5-�ܲ��ĳ�����15���δ���׶�������ȡ10%��Ѻ��֤��",0);
//		    WritableSheet sheet6 = wk.createSheet("��6-���зſ�30���δ���׶�������ȡ10%��Ѻ��֤��",0);
//		    WritableSheet sheet7 = wk.createSheet("��7-���зſ�45���δ���׶�������ȡ100%��Ѻ��֤��",0);
//		    WritableSheet sheet8 = wk.createSheet("��8-����̨��",0);
//		    WritableSheet sheet9 = wk.createSheet("��9-�����Ͽ���̨��",0);
	
		    //Ӧ�ø���ĸ�ʽ
		    ExportOperationsExcel.SetSheet1Layout(sheet1);
//		    ExportOperationsExcel.SetSheet2Layout(sheet2, 12, "��2-201X��X������ת���ʿ��˱�", Lev2HeaderName2);
//		    ExportOperationsExcel.SetSheet2Layout(sheet3, 7, "�ܲ��ĳ�����10���δ���׶���������Ԥ��", Lev2HeaderName3);
//		    ExportOperationsExcel.SetSheet2Layout(sheet4, 7, "���зſ�20���δ���׶���������Ԥ��", Lev2HeaderName3);
//		    ExportOperationsExcel.SetSheet2Layout(sheet5, 9, "�ܲ��ĳ�����15���δ���׶�������ȡ10%��Ѻ��֤��", Lev2HeaderName5);
//		    ExportOperationsExcel.SetSheet2Layout(sheet6, 9, "���зſ�30���δ���׶�������ȡ10%��Ѻ��֤��", Lev2HeaderName5);
//		    ExportOperationsExcel.SetSheet2Layout(sheet7, 9, "���зſ�45���δ���׶�������ȡ100%��Ѻ��֤��", Lev2HeaderName5);
//		    ExportOperationsExcel.SetSheet2Layout(sheet8, 6, "��8-201������¡���ҵ����̨��", Lev2HeaderName8);
//		    ExportOperationsExcel.SetSheet2Layout(sheet9, 35, "�����Ͽ���̨��", Lev2HeaderName9);

		    //������������
		    ExportOperationsExcel.InsertData(list1, list2, list3, sheet1);
		    
		    
			//������Ĺ����������֮ǰָ���Ľ�����(�����ǿͻ��������)
			wk.write();
			//�������ʱ���رն����ͷ�ռ�õ��ڴ�ռ�   
			wk.close();
			System.out.println("д��ɹ�"); 
		}
		
		/**
		 * ���������������ñ��ʽ�ķ���			-----���ɲ�ѯ�ֶεı���Լ���������
		 * @param fieldName				-----����������ݿ��ֶ���
		 * @param lev2MenuName			-----��ȡ���û�ѡ��������˵����ϼ�Ҳ���Ƕ����˵��ڼ����е�λ��
		 * @param lev3MenuName			-----��ȡ���û�ѡ��������˵��ڼ����е�λ��
		 * @throws Exception
		 */
		public static void CreateExcel(List<String> fieldName, List<Integer> lev2MenuName, List<Integer> lev3MenuName, List<Map> data) throws Exception{
			
		    WritableWorkbook wk = ExportOperationsExcel.GetFile("D://��Ӫ�������ݿ�_2018-12-20_���ݰ�.xlsx");
		    
		    //1.������
		    WritableSheet sheet1 = wk.createSheet("��1-ҵ��̨��",0);
	
		    //Ӧ�ø���ĸ�ʽ
		    ExportOperationsExcel.SetSheet1Layout(sheet1, fieldName, lev2MenuName, lev3MenuName, data);
		    
//		    //������������
//		    ExportOperationsExcel.InsertData(list2, sheet1);
		    
			//������Ĺ����������֮ǰָ���Ľ�����(�����ǿͻ��������)
			wk.write();
			//�������ʱ���رն����ͷ�ռ�õ��ڴ�ռ�   
			wk.close();
			System.out.println("д��ɹ�"); 
		}
		
		//ȡ��list2��list3��ÿ�����ݵ�idcbc_idֵ�������ж�list2��list3���Ƿ���ڵ�ǰ�������һ���û���ʱ������
		private static List<Integer> GetIcbcidList(List<Map> list){
			List<Integer> icbcid = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				icbcid.add((Integer) list.get(i).get("icbc_id"));
			}
			return icbcid;
		}
		
		/**
		 * ����в�������			-----��ǲ�ѯ�ֶεı���������
		 * @param list
		 * @throws WriteException 
		 * @throws RowsExceededException 
		 */
		private static void InsertData(List<Map> list1, List<Map> list2, List<Map> list3, WritableSheet sheet) throws RowsExceededException, WriteException{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd�� HH:mm:ss");
			//ȡ��list2��ÿ�����ݵ�idcbc_idֵ�������ж�list2���Ƿ���ڵ�ǰ�������һ���û���ʱ������
			List<Integer> icbcid = ExportOperationsExcel.GetIcbcidList(list2);
			
			//ѭ��������
			for (int i = 0; i < list1.size(); i++) {
				//ȡ��list1��ÿһ���������ڵ�map
				Map map = list1.get(i);
				
				float a = 0;	//���ڱ���ϼ�
				float b = 0;	//��ͬ��Ϣ�ϼ�
				/** 1. ��ӱ�����Ҫ�ĳ��˸���ʱ�����������*/
				//����excel����Ӧ�����ݿ��ֶ���������keyȡ������ 		----���еġ�1������ֻΪռһ��
				String[] keys = {"dt_add", "gems_code", "y_name", "loan_tpid", "a_name", "loan_level", "s_name", "s_c_name", "1", "c_name", "c_tel", "c_cardno"
						, "cars_type", "cars_name", "cse_name", "cm_name", "sp_name", "kk_loan_amount", "kk_loan_amount_s", "kk_loan_ajqx", "kk_loan_rate", "fk_status"};
					 
					 sheet.addCell(new Label(0, i+3, String.valueOf(i+1)));						//���
					 sheet.addCell(new Label(1, i+3, sdf.format(map.get(keys[0]))));			//�����ύʱ��
					 //ѭ����Ӷ�������е�����������
					 for (int j = 0; j < 12; j++) {
						 if(null!=map.get(keys[j+1])){
							 //����ӵ���Ʒ���Ƶ���һ�е�ʱ��  ���ݲ�Ʒid�ֶ������Ʒ����
							 if(j == 2){
								 if((int)map.get("loan_tpid") == 1){
									 sheet.addCell(new Label(4, i+3, "������"));
								 }
								 continue;
							 }
							 //����ӵ�������һ�е�ʱ���ֶ���Ӽ�����Ϣ
							 if(j == 4){
								/* if((int)map.get("loan_level") == 1){
									 sheet.addCell(new Label(j+2, i+3, "<=10W"));
								 } else if((int)map.get("loan_level") == 2){
									 sheet.addCell(new Label(j+2, i+3, "10W����"));
								 }*/
								 continue;
							 }
							 //����ӵ�����������һ�е�ʱ���жϳ���������Ϣ
							 if(j == 11){
								 if((int)map.get("cars_type") == 1){
									 sheet.addCell(new Label(j+2, i+3, "�³�"));
								 } else if((int)map.get("cars_type") == 2){ 
									 sheet.addCell(new Label(j+2, i+3, "���ֳ�"));
								 }
								 break;
							 }
							 if(j == 7){
								 continue;
							 }	 
						 
							 sheet.addCell(new Label(j+2, i+3, map.get(keys[j+1]).toString()));		//��������е�����������     ----���������в������⼸�����е�"������"��  �����������ߴ�ѭ��
						 }
					}
					 if (null!=map.get(keys[13]) && null!=map.get(keys[14]) && null!=map.get(keys[15])) {
						 String carsData = map.get(keys[13]).toString() + map.get(keys[14]).toString() + map.get(keys[15]).toString();
						 sheet.addCell(new Label(14, i+3, carsData ));			//����Ʒ���ͺ�
					 }
					 if(null!=map.get(keys[16])){
						 sheet.addCell(new Label(15, i+3, map.get(keys[16]).toString() ));			//���Ƴ���
					 }
					 if(null!=map.get(keys[17])){
						 sheet.addCell(new Label(16, i+3, String.valueOf((float)map.get(keys[17])*10000)) );			//�������ڱ���
					 }
					 if(null!=map.get(keys[18])){
						 sheet.addCell(new Label(17, i+3, map.get(keys[18]).toString() ));			//���ڷ���ѷ��ڱ���
					 }
					 if(null!=map.get(keys[17]) && null!=map.get(keys[18])){
						 a = (float)map.get(keys[17])*10000 + (int)map.get(keys[18]);
						 sheet.addCell( new Label(18, i+3, String.valueOf(a)) );					//���ڱ���ϼ�
					 }
					 if(null!=map.get(keys[19])){
						 sheet.addCell(new Label(19, i+3, map.get(keys[19]).toString() ));			//��������/��
					 }
					 if(null!=map.get(keys[20])){
						 sheet.addCell(new Label(20, i+3, map.get(keys[20]).toString() ));			//ִ����������
						//��ͬ��Ϣ�ϼ�
						 b = ((float)map.get(keys[20])/100 + 1);
					 }
					 BigDecimal b1 = new BigDecimal(String.valueOf(b));
					 BigDecimal b2 = new BigDecimal(String.valueOf(a));
					 BigDecimal bd = b1.multiply(b2);
					 sheet.addCell(new Label(21, i+3, String.valueOf(bd) ));					//��ͬ��Ϣ�ϼ�
					 //�ͻ��»�����
					 BigDecimal c = new BigDecimal("0");
					 if (0!=(int)map.get(keys[19]) && StringUtils.isEmpty(map.get(keys[19]))) {
						BigDecimal c1 = new BigDecimal(String.valueOf(map.get(keys[19])));
						c = bd.divide(c1 , 5, RoundingMode.HALF_UP);
					 }
					 sheet.addCell( new Label(21, i+3, String.valueOf(c)) );					//�ͻ��»�����
					 if(null!=map.get(keys[21])){
						 sheet.addCell(new Label(24, i+3, map.get(keys[21]).toString() ));			//�Ƿ����
					 }
					 
					 /** 2. ��ӱ�����Ҫ�ĸ���ʱ������*/
					 //�ж�list2���Ƿ���ڵ�ǰ�ͻ�����Ϣ
					 if ( icbcid.contains(map.get("id")) ) {
						 //����ȡ�øÿͻ��������г��ֵĴ���
						 int count = 0;
							for (int j = 0; j < icbcid.size(); j++) {
//								System.out.println(icbcid.get(j) == (int)map.get("id"));
								if(icbcid.get(j) == (int)map.get("id")){
									count = count+1;
								}
							}
						 //���ݸÿͻ���list2�г��ֵĴ�����ѭ������
						 for (int j = 0; j < count; j++) {
							 //ȡ�õ�ǰ�ͻ�ÿ�γ��ֵ�statusֵ
							 switch((int)list2.get(icbcid.indexOf(map.get("id"))+j).get("status")){ 
							 case 57:
								 //��������
								 sheet.addCell(new Label(31, i+3, list2.get(icbcid.indexOf(map.get("id"))+j).get("dt_add").toString() ));
								 break;
								 
							 case 61:
								 sheet.addCell(new Label(37, i+3, list2.get(icbcid.indexOf(map.get("id"))+j).get("dt_add").toString() ));
								 break;
			
							 case 62:
								 sheet.addCell(new Label(36, i+3, list2.get(icbcid.indexOf(map.get("id"))+j).get("dt_add").toString() ));
								 break;
								 
							 case 97:
								 sheet.addCell(new Label(35, i+3, list2.get(icbcid.indexOf(map.get("id"))+j).get("dt_add").toString() ));
								 break;
							 
							 case 74:
								 sheet.addCell(new Label(39, i+3, list2.get(icbcid.indexOf(map.get("id"))+j).get("dt_add").toString() ));
								 break;
								 
							 case 80:
								 sheet.addCell(new Label(41, i+3, list2.get(icbcid.indexOf(map.get("id"))+j).get("dt_add").toString() ));
								 break;
								 
							 case 77:
								 sheet.addCell(new Label(40, i+3, list2.get(icbcid.indexOf(map.get("id"))+j).get("dt_add").toString() ));
								 break;
							 }
							
						 }
				
					 }
					 	 
					 /** 3. �������ڻ�����*/
					//ȡ��list2��ÿ�����ݵ�idcbc_idֵ�������ж�list2���Ƿ���ڵ�ǰ�������һ���û���ʱ������
					List<Integer> icbcid2 = ExportOperationsExcel.GetIcbcidList(list3);
					//�ж�list3���Ƿ���ڵ�ǰ�ͻ�����Ϣ
					if ( icbcid2.contains(map.get("id")) ) {
						//���ݿͻ�id��icbcid2�����е�λ��ȡ���ÿͻ���list3�����е�json���� ����תΪmap����
						JSONObject jb = JSONObject.fromObject(list3.get( icbcid2.indexOf(map.get("id")) ).get("result_1_value"));
						Map<String, Object> data = (Map<String, Object>)jb;
						
						sheet.addCell(new Label(38, i+3, data.get("61_sqhkr").toString() ));
					}
					
	        } 
		}
		
		
		//����һ�����������ʽ
		public static WritableCellFormat SetLev1HeaderFontFormat() throws WriteException{
			//����WritableCellFormat���󣬽��ö���Ӧ���ڵ�Ԫ��Ӷ����õ�Ԫ�����ʽ
			WritableCellFormat OnetitleFormat = new WritableCellFormat();
			//���������ʽ    �������α�ʾ���塢�ֺ�12�����塢��б�塢�����»��ߡ�����ɫ
			OnetitleFormat.setFont(new WritableFont(WritableFont.createFont("΢���ź�"), 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK));
			//�����ı���ֱ���ж���
			OnetitleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			//�����Զ�����
			OnetitleFormat.setWrap(true);
			//���ñ߿�
			OnetitleFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
			
			return OnetitleFormat;
		}
		
		
		/**
		 * ���ñ�1�ĸ�ʽ			-----���÷ǲ�ѯ�ֶα�ĸ�ʽ
		 * @param sheet
		 * @throws WriteException 
		 * @throws RowsExceededException 
		 */
		public static void SetSheet1Layout(WritableSheet sheet) throws RowsExceededException, WriteException{
			
			//�ϲ���Ԫ��
		    sheet.mergeCells(0, 0, 55, 0);
		    
		    int[] cs = {1, 5, 10, 13, 16, 24, 29, 37, 39, 42, 45, 49};
		    int[] rs = {4, 8, 12, 15, 23, 28, 36, 38, 41, 44, 48, 55};
		    for(int i=0;i<cs.length;i++){
		    	sheet.mergeCells(cs[i], 1, rs[i], 1);
		    }
		    
		    //����һ�����������ʽ
			ExportOperationsExcel.SetLev1HeaderFontFormat();
			//����һ��������и�
			sheet.setRowView(0, 500);
			//���һ������
			Label lab_1 = new Label(0, 0, "��������ҵ����Ӫ�������ݱ�1-ҵ��̨��", ExportOperationsExcel.SetLev1HeaderFontFormat());
			sheet.addCell(lab_1);
		 
			//���ö���������и�
			sheet.setRowView(1, 550);
			//���ö�������
			WritableCellFormat TwoTitleFormat = new WritableCellFormat();
			TwoTitleFormat.setFont(new WritableFont(WritableFont.createFont("΢���ź�"),10,WritableFont.BOLD));
			TwoTitleFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
			//�����ı�ˮƽ���ж���
			TwoTitleFormat.setAlignment(Alignment.CENTRE);
			//�����ı���ֱ���ж���
			TwoTitleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			
			String[]  Lev2HeaderFontName = {"����������Ϣ", "����������Ϣ", "����", "�ͻ���Ϣ", "������Ϣ", "������Ϣ", "���ʿ��ո���Ϣ", "�ͻ�����ͬ���Ϲ�����Ϣ", "���зſ���Ϣ", "��Ѻ���Ϲ鵵��Ϣ"
											, "�ϼ���������", "���ϼ�����������Ϣ", "֧����������������Ϣ"};
			int[] Lev2HerderFontNameBegin = {1, 5, 9, 10, 13, 16, 24, 29, 37, 39, 42, 45, 49};
			
			for (int i = 0; i < Lev2HeaderFontName.length; i++) {
				Label lable = new Label(Lev2HerderFontNameBegin[i], 1, Lev2HeaderFontName[i], TwoTitleFormat);
				sheet.addCell(lable);
			}
			
			//��������������и�
			sheet.setRowView(2, 700);
			//������������ĸ�ʽ
			WritableCellFormat ThreeTitleFormat = new WritableCellFormat();
			ThreeTitleFormat.setFont(new WritableFont(WritableFont.createFont("΢���ź�"), 8, WritableFont.NO_BOLD));
			ThreeTitleFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
			//�����ı�ˮƽ���ж���
			ThreeTitleFormat.setAlignment(Alignment.CENTRE);
			//�����ı���ֱ���ж���
			ThreeTitleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			//�������������
			String[] Lev3HeaderFontName = {"���", "�����ύʱ��", "�������", "�ſ�����", "��Ʒ����", "������������", "����", "ҵ������ʡ��", "ҵ�����ڳ���", "������", "�ͻ�����"
											, "��ϵ�绰", "���֤����", "��������", "����Ʒ���ͺ�", "���Ƴ���", "�������ڱ���", "���ڷ���ѷ��ڱ���", "���ڱ���ϼ�", "��������/��"
											, "ִ����������", "��ͬ��Ϣ�ϼ�", "�ͻ��»�����", "�г�����", "�Ƿ����", "�յ��������ڱ���ʱ��", "�յ��������ڱ�����", "֧���������ڱ���ʱ��"
											, "֧���������ڱ�����", "�ᳵ�����ύ����ʱ��", "δ�����ᳵ��ص��ʱ��", "ֽ�������ʼ�ʱ��", "�����������ʱ��", "������������", "����������������ʱ��"
											, "�������������˵�ʱ��", "�յ�����������ص��ʱ��", "���зſ�ʱ��", "���ڻ�����", "�ܲ��ʼĳ������Ѻ����ʱ��", "���������ĳ��Ѱ��׵�Ѻ����ʱ��"
											, "�������׵�Ѻ����ʱ��", "���ϼ������������", "��������", "�ϼ����������ܼ�", "�յ��������ڱ���ʱ��", "�յ��������ڱ�����", "�յ����ڷ����ʱ��"
											, "�յ����ڷ���ѽ��", "֧���������ڱ���ʱ��", "֧���������ڱ�����", "�������", "��������", "ҵ��֤�����", "ҵ��֤����", "���մ������ڷ���ѽ��"};
			
			for (int i = 0; i < Lev3HeaderFontName.length; i++) {
				Label lable = new Label(i, 2, Lev3HeaderFontName[i], ThreeTitleFormat);
				sheet.addCell(lable);
				//���������Զ������п� 
				sheet.setColumnView(i, Lev3HeaderFontName[i].length()+10);
			}
			
		}
		
		/**
		 * ���ñ�1�ĸ�ʽ			-----�����û�ѡ�����Ҫ�������ֶζ�̬���ɱ�ĸ�ʽ
		 * @param sheet
		 * @param fieldName		-----�û�ѡ����Ҫ�����ֶε����ݿ��ֶ���
		 * @param lev2MenuName	-----�û�ѡ�񵼳��ֶζ�Ӧ�����ж����˵��������е�λ��
		 * @param lev3MenuName	-----�û�ѡ�񵼳��ֶζ�Ӧ�����������˵��������е�λ��
		 * @param data			-----��������
		 * @throws RowsExceededException
		 * @throws WriteException
		 */
		public static void SetSheet1Layout(WritableSheet sheet, List<String> fieldName, List<Integer> lev2MenuName, List<Integer> lev3MenuName, List<Map> data) throws RowsExceededException, WriteException{
			
			//�ϲ�һ��������ռ�ĵ�Ԫ��
			sheet.mergeCells(0, 0, fieldName.size(), 0);
			//����һ�����������ʽ
			ExportOperationsExcel.SetLev1HeaderFontFormat();
			//����һ��������и�
			sheet.setRowView(0, 500);
			//���һ������
			Label lab_1 = new Label(0, 0, "��������ҵ����Ӫ�������ݱ�1-ҵ��̨��", ExportOperationsExcel.SetLev1HeaderFontFormat());
			sheet.addCell(lab_1);
			
			
			//���ö���������и�
			sheet.setRowView(1, 550);
			//���ö�������
			WritableCellFormat TwoTitleFormat = new WritableCellFormat();
			TwoTitleFormat.setFont(new WritableFont(WritableFont.createFont("΢���ź�"),10,WritableFont.BOLD));
			TwoTitleFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
			//�����ı�ˮƽ���ж���
			TwoTitleFormat.setAlignment(Alignment.CENTRE);
			//�����ı���ֱ���ж���
			TwoTitleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
	
			String[] Lev2HeaderFontName = {"����������Ϣ", "����������Ϣ", "����", "�ͻ���Ϣ", "������Ϣ", "������Ϣ"};
			//�����ֶ��б�  / ���������б�
			String[] Lev3HeaderFontName = {"�����ύʱ��", "�������", "�ſ�����", "��Ʒ����", "������������", "����", "ҵ������ʡ��", "ҵ�����ڳ���", "������", "�ͻ�����", "��ϵ�绰"
										, "���֤����", "��������", "����Ʒ���ͺ�", "���Ƴ���", "�������ڱ���", "���ڷ���ѷ��ڱ���", "���ڱ���ϼ�", "��������/��", "ִ����������"
										, "��ͬ��Ϣ�ϼ�", "�ͻ��»�����", "�г�����"};
			
			int counts = 0;
			//ѭ����������ĸ���  
			for (int i = 1; i <= Lev2HeaderFontName.length; i++) {
				//�ж���Ҫ�����Ķ�������  -----�������1��˵����Ҫ�������������еĵ�һ��Ҳ����"����������Ϣ"
				if(lev2MenuName.contains(i)){
					//���ݶ��������Ӧ��������title�г��ֵĴ��� ȡ���û���ÿ������������ѡ����������������
					int count = Collections.frequency(lev2MenuName, i);
					//����û�ѡ���ÿ�����������������������������1 ��������������Ҫ�ϲ���Ԫ��
					if(count > 1){
						sheet.mergeCells(counts+1, 1, count+counts, 1);		//�ϲ���Ԫ��
						sheet.setColumnView(i, Lev2HeaderFontName[i-1].length()+10);		//���ø��������Զ������п�
						sheet.addCell( new Label(counts+1, 1, Lev2HeaderFontName[i-1], TwoTitleFormat) );		//��ӱ���
					} else if(count == 1){
						sheet.setColumnView(i, Lev2HeaderFontName[i-1].length()+10);
						sheet.addCell( new Label(counts+1, 1, Lev2HeaderFontName[i-1], TwoTitleFormat) );
					}
					//���ڼ���ÿ�����������µĵ�һ�����������ڱ��е�����
					counts += count;
				}
			}
			
			
			//��������������и�
			sheet.setRowView(2, 700);
			//������������ĸ�ʽ
			WritableCellFormat ThreeTitleFormat = new WritableCellFormat();
			ThreeTitleFormat.setFont(new WritableFont(WritableFont.createFont("΢���ź�"), 8, WritableFont.NO_BOLD));
			ThreeTitleFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
			//�����ı�ˮƽ���ж���
			ThreeTitleFormat.setAlignment(Alignment.CENTRE);
			//�����ı���ֱ���ж���
			ThreeTitleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			//�������������
			sheet.addCell( new Label(0, 2, "���", ThreeTitleFormat) );
			for (int i = 0; i < lev3MenuName.size(); i++) {
				sheet.addCell( new Label(i+1, 2, Lev3HeaderFontName[lev3MenuName.get(i)], ThreeTitleFormat) );
				//���������Զ������п�
				sheet.setColumnView(i+1, Lev3HeaderFontName[lev3MenuName.get(i)].length()+10);
			}
			
			//ѭ�����ÿһ��
			for (int i = 0; i < data.size(); i++) {
				sheet.addCell( new Label(0, i+3, String.valueOf(i+1)) );			//���
				Map map = data.get(i);		//��ȡ��ÿһ�е�����
				float a = 0;	//���ڱ���ϼ�
				float b = 0;	//��ͬ��Ϣ�ϼ�
				BigDecimal bd = new BigDecimal("0");	//��ͬ��Ϣ�ϼ�
				//ѭ�����ÿһ��
				for (int j = 0; j < fieldName.size(); j++) {
					
					String field = fieldName.get(j);		//ȡ���û�ѡ�񵼳������ݿ��ֶ���
					if("dt_add".equals(field)){		//�����ύʱ��
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd�� HH:mm:ss");
						sheet.addCell( new Label(j+1, i+3, sdf.format(map.get(field))) );
						sheet.setColumnView(j+1, sdf.format(map.get(field)).length()+10);
					} else if("manager".equals(field)) {
						sheet.addCell( new Label(j+1, i+3, "�޴�������") );	//������
						sheet.setColumnView(j+1, "�޴�������".length()+10);
					} else if("cars_type".equals(field)) {	//��������
						if((int)map.get("cars_type") == 1){
							 sheet.addCell(new Label(j+1, i+3, "�³�"));
						 } else if((int)map.get("cars_type") == 2){
							 sheet.addCell(new Label(j+1, i+3, "���ֳ�"));
						 }
					} else if("cars_name".equals(field)) {	//����Ʒ���ͺ�
						String carsData = map.get("cars_name").toString() + map.get("cse_name").toString() + map.get("cm_name").toString();	//��ȡ�������ĳ�����Ϣ
						sheet.addCell(new Label(j+1, i+3, carsData));
						sheet.setColumnView(j+1, carsData.length()+10);
					} else if("kk_loan_amount+kk_loan_amount_s".equals(field)) {	//���ڱ���ϼ�
						a = (float)map.get("kk_loan_amount")*10000 + (int)map.get("kk_loan_amount_s");
						sheet.addCell(new Label(j+1, i+3, String.valueOf(a)));
					} else if("principalAndInterest".equals(field)) {	//��ͬ��Ϣ�ϼ�
					    b = ((float)map.get("kk_loan_rate")/100 + 1);
						BigDecimal b1 = new BigDecimal(String.valueOf(b));
						BigDecimal b2 = new BigDecimal(String.valueOf(a));
						bd = b1.multiply(b2);
						sheet.addCell(new Label(j+1, i+3, String.valueOf(bd)));
					} else if("MonPayments".equals(field)) {	//�ͻ��»�����
						BigDecimal c = new BigDecimal("0");
						if (0!=(int)map.get("kk_loan_ajqx") && StringUtils.isEmpty(map.get("kk_loan_ajqx"))) {
							BigDecimal c1 = new BigDecimal(String.valueOf(map.get("kk_loan_ajqx")));
							c = bd.divide(c1 , 5, RoundingMode.HALF_UP);
							sheet.addCell(new Label(j+1, i+3, String.valueOf(c)));
						 } else {
							sheet.addCell(new Label(j+1, i+3, "�������޴���,�޷�����"));
							sheet.setColumnView(j+1, "�������޴���,�޷�����".length()+10);
						 }
					} else if("marketrate".equals(field)) {
						sheet.addCell(new Label(j+1, i+3, "�޴�������"));
						sheet.setColumnView(j+1, "�޴�������".length()+10);
					} else {
						sheet.addCell( new Label(j+1, i+3, map.get(field).toString()) );
					}
					
				}
			}
		}
		
//		/**
//		 * ���������ĸ�ʽ
//		 * @param sheet		���������ű�
//		 * @param endNumber		һ������ϲ������һ����Ԫ�������  ��0��ʼ
//		 * @param Lev1HeaderName		һ�����������
//		 * @param Lev2HeaderName		�����������Ƶ�����
//		 * @throws RowsExceededException
//		 * @throws WriteException
//		 */
//		public static void SetSheet2Layout(WritableSheet sheet, int endNumber, String Lev1HeaderName, String[] Lev2HeaderName) throws RowsExceededException, WriteException{
//			
		
//			//�ϲ���Ԫ��
//			sheet.mergeCells(0, 0, endNumber, 0);
//			
//			//����һ�����������ʽ
//			ExportOperationsExcel.SetLev1HeaderFontFormat();
//			sheet.setRowView(0, 500);
//			//����һ������
//			Label lab_1 = new Label(0, 0, Lev1HeaderName, ExportOperationsExcel.SetLev1HeaderFontFormat());
//			sheet.addCell(lab_1);
//			
//			//���ö�������ĸ�ʽ
//			sheet.setRowView(1, 500);
//			WritableCellFormat TwoTitleFormat = new WritableCellFormat();
//			TwoTitleFormat.setFont(new WritableFont(WritableFont.createFont("΢���ź�"), 8, WritableFont.NO_BOLD));
//			TwoTitleFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
//			//�����ı�ˮƽ���ж���
//			TwoTitleFormat.setAlignment(Alignment.CENTRE);
//			//�����ı���ֱ���ж���
//			TwoTitleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
//			for (int i = 0; i < Lev2HeaderName.length; i++) {
//				Label lable = new Label(i, 1, Lev2HeaderName[i], TwoTitleFormat);
//				sheet.addCell(lable);
//				//���������Զ������п� 
//				sheet.setColumnView(i, Lev2HeaderName[i].length()+10);
//			}
//			
//		}
		

}
