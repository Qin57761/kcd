package com.controller.erp_icbc.loanAfter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.Repayment.controller.RepaymenttestController;
import com.controller.Excel.UploadExcelController;
import com.model1.icbc.erp.PageData;
import com.service1.Excel.recordService;
import com.service1.loan.AboutExcelService;
import com.util.limitutil;
import com.util.Excel.CommonUtil;
import com.util.newAdd.TimeStyle;
/**
 * ����excel��������
 * 
 * @author ��ʮ����
 * 2019-3-16
 */
@Controller
@RequestMapping("/loan")
public class LoanImportExcelController {
	private static Logger log = LogManager.getLogger(UploadExcelController.class.getName());
	private Logger logger = Logger.getLogger(UploadExcelController.class);
	private final String xls = "xls";
	private final String xlsx = "xlsx";
	//excel��� �ͻ����� ���֤�� ����� ����� ���ڽ�� ����ΥԼ���� ���ΥԼ���� �������� �ڱ���� +�������� +����ʱ��
	private static String[] ss = 
		{ "name", "id_card", "repayment_card","balance_card", "overdue_amount", "continuity", "maximum","add_time", "balance_on", "practical_date", "overdue_days"};

	@Autowired
	private AboutExcelService AboutExcelService;
	/**
	 * ����excel�ļ��������󷵻�
	 * 
	 * 
	 */
	@RequestMapping(value="/readExcel.do",method=RequestMethod.POST)
	@ResponseBody
	public Map readExcel(String id_card, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
		String relatDir1 = new SimpleDateFormat("yyyy/MM/dd/").format(new Date());
		// �ļ��в������򴴽�
		File fdir = new File("/KCDIMG/assess/upload/" + relatDir1);
		if (!fdir.exists()) {
			fdir.mkdirs();
		}
		String oriName = file.getOriginalFilename();
		File tempFile = new File(fdir.getPath() + "/" + oriName);
		log.info("�ļ������ַ->" + tempFile);
		file.transferTo(tempFile);
		String[] string = new String[4];// ����ss.length����ά���飬ÿ����������x������
		// ����ļ�
		checkFile(file);
		// ���Workbook����������
		Workbook workbook = getWorkBook(file);
		// �������ض��󣬰�ÿ���е�ֵ��Ϊһ�����飬��������Ϊһ�����Ϸ���
		PageData rowMap = new PageData();
		PageData pdsession= (PageData)request.getSession().getAttribute("pd");//��ȡsession��Ϣ
		if (workbook != null) {
			for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
				// ��ѯ�м�����ͬ����
				Integer c = 0;
				// ��õ�ǰsheet������
				Sheet sheet = workbook.getSheetAt(sheetNum);
				if (sheet == null) {
					continue;
				}
				// ��õ�ǰsheet�Ŀ�ʼ��
				int firstRowNum = sheet.getFirstRowNum();
				// ��õ�ǰsheet�Ľ�����
				int lastRowNum = sheet.getLastRowNum();
				// ѭ�����˵�һ�е�������
				for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
					// ��õ�ǰ��
					Row row = sheet.getRow(rowNum);
					if (row == null) {
						continue;
					}
					// ��õ�ǰ�еĿ�ʼ��
					int firstCellNum = row.getFirstCellNum();
					// ��õ�ǰ�е�����
//					int lastCellNum = row.getPhysicalNumberOfCells(); //��ȡ���ǿյ���
					int lastCellNum = row.getLastCellNum(); //��ȡȫ������   �ǿյĺͿյ���
					System.err.println("-------��õ�ǰ�е�����-" + lastCellNum); 
//					String[] cells = new String[row.getPhysicalNumberOfCells()]; //��ȡ���ǿյ���
					String[] cells = new String[row.getLastCellNum()]; //��ȡȫ������   �ǿյĺͿյ���
					// ѭ����ǰ��
					for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
						Cell cell = row.getCell(cellNum);
						cells[cellNum] = getCellValue(cell);
						if (cellNum == 1) {
							System.out.println(cell + "***");
							System.out.println(cells[cellNum] + "***");
							// ��ѯ�м�����ͬ����
//							c = recordService.count(cells[cellNum]);
							System.out.println("-------" + cell);
						}
						rowMap.put("add_time", relatDir1);
						rowMap.put(ss[cellNum], cells[cellNum]);
					}
					System.out.println(c + "****");
					c = c + 1;
					rowMap.put("repayment_periods", c + "");
					rowMap.put("dt_add",TimeStyle.sdfYMDHMS);
					rowMap.put("dt_edit",TimeStyle.sdfYMDHMS);
					AboutExcelService.excel_to_sql(rowMap); //��excel���·�������¼�����ݿ��loan_import_excels
					//1.�����ڿͻ���ӵ����ڱ�
					double overdue_amount = Double.parseDouble(rowMap.getString("overdue_amount")) ;//��ȡexcel����е����ڽ��
					PageData icbcInfo = new PageData();
					icbcInfo.put("c_cardno", rowMap.getString("id_card"));
					PageData getIcbcInfo = new PageData();
					getIcbcInfo = AboutExcelService.icbcInfo(icbcInfo); //����excel����������֤�Ż�ȡ�û���Ϣ
					//excel������������  ֤���ͻ�����
					if(overdue_amount>0){ 
						PageData addOverdueClient = new PageData();
						addOverdueClient.put("mid_add",pdsession.getInt("id"));
						addOverdueClient.put("mid_edit",pdsession.getInt("id") );
//						addOverdueClient.put("type_id",LoanModel.LoanTypeModel().get("����")); //???
//						addOverdueClient.put("type_status",LoanModel.LoanTypeModel().get("��������") );//???
						addOverdueClient.put("type_id","1"); //???  // 1���ڣ�2��ߣ�3�ϳ���4���ϣ�5������6����
						addOverdueClient.put("type_status","11");//???
						addOverdueClient.put("icbc_id",(getIcbcInfo==null?null:getIcbcInfo.get("icbc_id")));
						addOverdueClient.put("gems_id",(getIcbcInfo==null?null:getIcbcInfo.get("gems_id")));
						addOverdueClient.put("gems_fs_id",(getIcbcInfo==null?null:getIcbcInfo.get("gems_fs_id")));
						addOverdueClient.put("imp_name",rowMap.getString("name")); //����excel���ʱ�Ŀͻ�����
						addOverdueClient.put("c_name",(getIcbcInfo==null?null:getIcbcInfo.get("c_name")));
						addOverdueClient.put("c_cardno",(getIcbcInfo==null?null:getIcbcInfo.get("c_cardno")));
						addOverdueClient.put("c_carno",(getIcbcInfo==null?null:getIcbcInfo.get("c_carno")));
						addOverdueClient.put("c_carvin",(getIcbcInfo==null?null:getIcbcInfo.get("c_carvin")));
						addOverdueClient.put("overdue_amount",overdue_amount); //���ڽ��
						addOverdueClient.put("overdue_days",rowMap.getString("overdue_days")); //��������
						AboutExcelService.addOverdueClient(addOverdueClient);
					}
					//2.�޸Ļ���ƻ������滹������
					System.err.println(rowMap.getString("practical_date")+"---practical_date");
					System.err.println(rowMap.getString("balance_card")+"---practical_money");
					System.err.println(rowMap.getString("overdue_amount")+"---overdue_amount");
					System.err.println(rowMap.getString("overdue_days")+"---overdue_days");
					System.err.println(rowMap.getString("repayment_card")+"---repayment_card");
					System.err.println(rowMap.getString("id_card")+"---id_card");
					PageData upPay = new PageData();
					upPay.put("dt_edit", TimeStyle.sdfYMDHMS);
					upPay.put("practical_date",rowMap.getString("practical_date"));
					upPay.put("practical_money",rowMap.getString("balance_card"));
					upPay.put("overdue_status",overdue_amount>0 ? 1:2);//1Ϊ����  2Ϊ����
					upPay.put("overdue_money",rowMap.getString("overdue_amount"));
					upPay.put("overdue_days",rowMap.getString("overdue_days"));
					upPay.put("c_bank_card",rowMap.getString("repayment_card"));
					upPay.put("c_cardno",rowMap.getString("id_card"));
					upPay.put("icbc_id",(getIcbcInfo==null?null:getIcbcInfo.get("icbc_id")));
					AboutExcelService.updatePaySchedule(upPay);
				}
			}
			// workbook.close();
		}

		// �㵼�뻹���ѯ�����ڿͻ�����ӵ����ݿ�
//		List<Map> listOverdue = recordService.selectOverdue(id_card);
//		for (int i = 0; i < listOverdue.size(); i++) {
//			recordService.addOverdue(listOverdue.get(i));
//		}
//		System.out.println("���ڿͻ�" + listOverdue);
		
		PageData map = new PageData();
		map.put("uuid", CommonUtil.getUUID());// ���к�
		map.put("oriName", oriName);// �ļ�����
		map.put("dt_add", new Date());// ����ʱ��
		map.put("financial_products", "");
		map.put("filepath", "upload/" + relatDir1 + oriName);
		map.put("mid_add", pdsession.get("id"));// ��ȡ������Ա
		map.put("mid_name", pdsession.get("name").toString());// ��ȡ������Ա
		map.put("gems_fs_id",pdsession.get("icbc_erp_fsid"));// ��˾ID
		map.put("gems_id", pdsession.get("gemsid"));// ��˾��ԱID
		map.put("fsname", pdsession.getString("fsname"));// ��˾����
		AboutExcelService.addExcelRecord(map); //���excel�����ļ���¼
//		repaymenttest.selectImport(); //�޸Ŀͻ�����ƻ�����Ļ����¼
		Map result = new HashMap();
		result.put("msg", "����ɹ�");
		result.put("code", "1");
		return result;
	}

	//��ѯ�޸Ļ����¼
//		public void selectImport(){
//			List<Map> repayMap = repaymentService.selectimport();
//			Map<String, Object> map=new HashMap<>();
//			for(int i=0;i<repayMap.size();i++){
//				map.put("practical_money", Math.round((double) repayMap.get(i).get("balance_card")*100)/100.00);
//				map.put("overdue_money", Math.round((double) repayMap.get(i).get("overdue_amount")*100)/100.00);
//				map.put("practical_date", repayMap.get(i).get("add_time"));
//				map.put("cardno", repayMap.get(i).get("id_card"));
//				if(Math.round((double) repayMap.get(i).get("overdue_amount")) != 0){
//					map.put("overdue", 1);
//				}else{
//					map.put("overdue", 0);
//				}
//				repaymentService.updateschedule(map);
//			}
//			
//
//		}
	
	public void checkFile(MultipartFile file) throws IOException {
		// �ж��ļ��Ƿ����
		if (null == file) {
			logger.error("�ļ������ڣ�");
			throw new FileNotFoundException("�ļ������ڣ�");
		}
		// ����ļ���
		String fileName = file.getOriginalFilename();
		// �ж��ļ��Ƿ���excel�ļ�
		if (!fileName.endsWith(xls) && !fileName.endsWith(xlsx)) {
			logger.error(fileName + "����excel�ļ�");
			throw new IOException(fileName + "����excel�ļ�");
		}
	}

	public Workbook getWorkBook(MultipartFile file) {
		// ����ļ���
		String fileName = file.getOriginalFilename();
		// ����Workbook���������󣬱�ʾ����excel
		Workbook workbook = null;
		try {
			// ��ȡexcel�ļ���io��
			InputStream is = file.getInputStream();
			// �����ļ���׺����ͬ(xls��xlsx)��ò�ͬ��Workbookʵ�������
			if (fileName.endsWith(xls)) {
				// 2003
				workbook = new HSSFWorkbook(is);
			} else if (fileName.endsWith(xlsx)) {
				// 2007
				workbook = new XSSFWorkbook(is);
			}
		} catch (IOException e) {
			logger.info(e.getMessage());
		}
		return workbook;
	}

	public String getCellValue(Cell cell) {
		String cellValue = "";
		if (cell == null) {
			return cellValue;
		}
		// �����ֵ���String�������������1����1.0�����
		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			cell.setCellType(Cell.CELL_TYPE_STRING);
		}
		// �ж����ݵ�����
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC: // ����
			cellValue = String.valueOf(cell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_STRING: // �ַ���
			cellValue = String.valueOf(cell.getStringCellValue());
			break;
		case Cell.CELL_TYPE_BOOLEAN: // Boolean
			cellValue = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA: // ��ʽ
			cellValue = String.valueOf(cell.getCellFormula());
			break;
		case Cell.CELL_TYPE_BLANK: // ��ֵ
			cellValue = "";
			break;
		case Cell.CELL_TYPE_ERROR: // ����
			cellValue = "�Ƿ��ַ�";
			break;
		default:
			cellValue = "δ֪����";
			break;
		}
		return cellValue;
	}
	
	/**
	 * 
	 */
	@RequestMapping(value = "/downloadOneFile.do")
	public void downloadFile(String fileUrl, String fileName,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pString = fileUrl.substring(fileUrl.lastIndexOf("."));
		response.setContentType("text/html;charset=UTF-8");
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		URL url = new URL(fileUrl);
		HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
		urlCon.setConnectTimeout(6000);
		urlCon.setReadTimeout(6000);
		int code = urlCon.getResponseCode();
		if (code != HttpURLConnection.HTTP_OK) {
			throw new Exception("�ļ���ȡʧ��");
		}
		try {
			response.setContentType("multipart/form-data");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename="+java.net.URLEncoder.encode(fileName,"UTF-8"));
			response.setHeader("Content-Length",String.valueOf(urlCon.getContentLength()));
			in = new BufferedInputStream(urlCon.getInputStream());
			out = new BufferedOutputStream(response.getOutputStream());
			byte[] data = new byte[1024];
			int len = 0;
			while (-1 != (len = in.read(data, 0, data.length))) {
				out.write(data, 0, len);
			}
			System.out.println("���سɹ�");
		} catch (Exception e) {
			e.printStackTrace();
			response.reset();
			try {
				OutputStreamWriter writer = new OutputStreamWriter(
						response.getOutputStream(), "UTF-8");
				String data = "<script language='javascript'>alert(\"\\u64cd\\u4f5c\\u5f02\\u5e38\\uff01\");</script>";
				writer.write(data);
				writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}
	
	
	/**
	 * ͨ���ļ�����ģ����ѯ ��ѯȫ�����ݲ���ҳ
	 */
	@RequestMapping("/selectImpRecordList.do")
	public String select(String qn, String cn, String type, String dn,
			int pagesize, int pagenow, HttpServletRequest request)throws UnsupportedEncodingException {
		//�����ѯ����
		PageData pd = new PageData();
		pd.put("param", request.getParameter("param"));
		//��ѯ����
		List<PageData> newpdList = new ArrayList<>();
		List<PageData> l1 = AboutExcelService.selectRecordList(pd);
		newpdList = limitutil.fy(l1, pagesize, pagenow); //��ҳ
		System.out.println("*************" + newpdList);
		int q = l1.size() % pagesize;
		int totalpage = 0;// ��ҳ��
		if (q == 0) {
			totalpage = l1.size() / pagesize;
		} else {
			totalpage = l1.size() / pagesize + 1;
		}
		request.setAttribute("dn", dn);
		request.setAttribute("cn", cn);
		request.setAttribute("qn", qn);
		request.setAttribute("type", type);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("pagenow", pagenow);
		request.setAttribute("pagesize", pagesize);
		request.setAttribute("totalsize", l1.size());
		request.setAttribute("newpdList", newpdList);
		request.setAttribute("param",request.getParameter("param")); //��ѯ����
		log.info("���" + l1);
		return "kjs_icbc/index";
	}
}
