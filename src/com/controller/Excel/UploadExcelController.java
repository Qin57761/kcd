package com.controller.Excel;
import java.awt.Desktop;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


import com.controller.erp_icbc.base.BaseController;

import com.model1.icbc.erp.PageData;
import com.service1.Excel.recordService;
import com.util.limitutil;
import com.util.Excel.CommonUtil;
import com.util.Excel.RecordUtil;


import jxl.read.biff.BiffException;

@Controller
@RequestMapping("/uploadExcelController")
public class UploadExcelController extends BaseController{
	private static Logger log = LogManager.getLogger(UploadExcelController.class.getName());
	@Autowired
	private com.mapper1.Excel.uploadExcelMapper uploadExcelMapper;
	@Autowired
	private com.mapper1.Excel.recordMapper recordMapper;
	@Autowired
	private recordService recordService;  
	
	private Logger logger  = Logger.getLogger(UploadExcelController.class);
	private final  String xls = "xls";  
	private final  String xlsx = "xlsx"; 
	//�ͻ����� ���֤�� ����� ����� ���ڽ�� ����ΥԼ���� ���ΥԼ���� �������� �ڱ���� 
	private static String[] ss={"name","id_card","repayment_card","balance_card","overdue_amount","continuity","maximum","add_time","balance_on"};

//	/**
//	 * �����ݿ�����ӱ�������
//	 * @param list
//	 * @return
//	 * @throws IOException 
//	 * @throws BiffException
//	 */
//	@RequestMapping("/addExcel.do")
//	@ResponseBody
//	public String  addExcel(
//			
//			HttpServletRequest request,HttpServletResponse response,RecordUtil recordUtil) throws BiffException, IOException{  
//		    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//	        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
//	        String relatDir1=new SimpleDateFormat("yyyy/MM/dd/").format(new Date());
//	        //�ļ��в������򴴽�
//	        File fdir = new File("D:/eclipse/kcd/WebContent/upload/"+relatDir1);
//	        if (!fdir.exists()) { fdir.mkdirs(); }
//
//	        String oriName = file.getOriginalFilename();
//	        File tempFile = new File(fdir.getPath()+File.separator+oriName);
//	        log.info("�ļ������ַ->"+tempFile);
//	        file.transferTo(tempFile);
//	       
////	        Map<String,String> rowMap=new HashMap<String,String>();
////	        String[] string= new String[4];//����ss.length����ά���飬ÿ����������x������
////			// ��ù��������� 
////			Workbook workbook = Workbook.getWorkbook(tempFile); 
////			// ������й����� 
////			Sheet[] sheets = workbook.getSheets(); 
////			// ���������� 
////			if (sheets != null) { 
////				for (Sheet sheet : sheets) { 
////					// ������� 
////					int rows = sheet.getRows(); 
////					// ������� 
////					int cols = sheet.getColumns(); 
////					// ��ȡ���� 
////					for (int row = 1; row < rows; row++) { 
////						
////						for (int col = 0; col < cols; col++) {
////							Cell cell = sheet.getCell(col, row); 
////							rowMap.put(ss[col], cell.getContents());
////							//log.info("-------->"+cell.getContents());
////							
////						}
////						log.info("��ӻ�������->"+rowMap);
////						
////						int addCount=uploadExcelMapper.addExcel(rowMap);
////						
////						log.info("add->"+addCount);
////						//list.add(rowMap);
////					} 
////				} 				
////			} 
//	        
//	        
//			
//			
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ     new Date()Ϊ��ȡ��ǰϵͳʱ��
//			PageData pdsession= (PageData)request.getSession().getAttribute("pd");//��ȡsession��Ϣ
//			Map<String, String> map=new HashMap<String,String>();
//			map.put("uuid", CommonUtil.getUUID());//���к�
//			map.put("oriName", oriName);//�ļ�����
//		    map.put("dt_add", df.format(new Date()));//����ʱ��	
//			map.put("financial_products", "");
//			map.put("mid_add", pdsession.get("name").toString());//��ȡ������Ա
//			recordMapper.addRecord(map);
//			
//			
//		   
//			
//			//map����ת��ΪJSON����
//			//������ά����
//	        //JSONArray json = new JSONArray(string);
//			//��JSON���󴫵ݸ�ǰ��AJAX����
//			response.setContentType("text/html;charset=UTF-8");
//			response.setContentType("application/json");
//			PrintWriter pw = response.getWriter();
//			//pw.print(json); // �켣ͼ������ȡ��������
//			pw.flush();
//			pw.close();
//			
//			//workbook.close(); 
//			return null;
//	}
	
	
	/**
	 * ͨ���ļ�����ģ����ѯ     ��ѯȫ�����ݲ���ҳ
	 * @param qn
	 * @param cn
	 * @param type
	 * @param dn
	 * @param totalpage
	 * @param pagenow
	 * @param status
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/select")
	public String select(
			String qn,
			String cn,
			String type,
			String dn,		
			int pagesize,
			int pagenow,
			HttpServletRequest request) throws UnsupportedEncodingException{
	
		requestParams(request);
		List<PageData> newpdList=new ArrayList<>();
		PageData pd=new PageData();
		pd.put("dn", dn);
		String param=request.getParameter("param");
		List<PageData> l1 = recordService.selectRecord(param, pd);
		newpdList=limitutil.fy(l1, pagesize, pagenow);
		System.out.println("*************"+newpdList);
		int q=l1.size()%pagesize;
		int totalpage=0;//��ҳ��
		if(q==0){
			totalpage=l1.size()/pagesize;
		}else{
			totalpage=l1.size()/pagesize+1;
		}
		request.setAttribute("dn", dn);
		request.setAttribute("cn", cn);
		request.setAttribute("qn", qn);
		request.setAttribute("type", type);
		request.setAttribute("totalpage",totalpage);
		request.setAttribute("pagenow",pagenow);
		request.setAttribute("pagesize", pagesize);
		request.setAttribute("totalsize",l1.size());
		request.setAttribute("newpdList", newpdList);
		log.info("���"+l1);
		return "kjs_icbc/index";
	}
	
	/**
	 * �����ļ�
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/download.do")
	@ResponseBody
	public void download(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
		String oriName = request.getParameter("oriName");    //��ȡҪ���ص��ļ���
		if(StringUtils.isNotBlank(oriName)){
			oriName = new String(oriName.getBytes("ISO-8859-1"),"utf-8");
		}
		DataInputStream in = null;
        OutputStream out = null;
		System.out.println(oriName+"------------------------------------");
//		response.setHeader("Content-Disposition", "attachment;oriName="+oriName);  //��������յ������Դʱ�������صķ�ʽ���ѣ�������չʾ��
		
		String dt_add=request.getParameter("dt_add");//��ȡ����ʱ��
		log.info(dt_add+"**************************");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//Сд��mm��ʾ���Ƿ���  
		String dstr=dt_add;  //stringת��date����
		java.util.Date date=sdf.parse(dstr);  
		String year=String.format("%tY", date);
		String month=String .format("%tm", date);
		String day=String .format("%td", date);
		System.out.println("��: " + year);  
	    System.out.println("��: " + month);  
	    System.out.println("��: " + day);  
		String excelPath = request.getSession().getServletContext().getRealPath("/upload/" + year + "/" + month + "/" + day + "/" +oriName);//��ȡ�ļ�·��
        log.info("·��"+excelPath);
        
        try{
        	response.reset();// ��������
            
            String resultFileName = oriName;
           log.info("�ļ���********"+resultFileName);
            response.setCharacterEncoding("UTF-8");  
            //response.setHeader("Content-disposition", "attachment; oriName=" + resultFileName);// �趨����ļ�ͷ
            response.setHeader("Content-Disposition", "attachment;filename=" + resultFileName);
            response.setContentType("application/msexcel");// �����������
            //�������������ļ�·��
            in = new DataInputStream(
                    new FileInputStream(new File(excelPath)));  
            //�����
            out = response.getOutputStream();
            //����ļ�
            int bytes = 0;
            byte[] bufferOut = new byte[1024];  
            while ((bytes = in.read(bufferOut)) != -1) {  
                out.write(bufferOut, 0, bytes);  
            }
        } catch(Exception e){
            e.printStackTrace();
            response.reset();
            try {
                OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream(), "UTF-8");  
                String data = "<script language='javascript'>alert(\"\\u64cd\\u4f5c\\u5f02\\u5e38\\uff01\");</script>";
                writer.write(data); 
                writer.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }finally {
            if(null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		System.out.println("���سɹ�");
		
		
		
		} 
	
		/**
		 * ��excel�ļ�
		 * @param request
		 * @param response
		 * @return
		 * @throws ParseException
		 */
		@RequestMapping("/openExcel.do")
		public String openExcel(HttpServletRequest request, HttpServletResponse response) throws ParseException{
			String oriName = request.getParameter("oriName");    //��ȡҪ�򿪵��ļ���
			String dt_add=request.getParameter("dt_add");//��ȡ����ʱ��
			log.info(dt_add+"**************************");
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//Сд��mm��ʾ���Ƿ���  
			String dstr=dt_add;  //stringת��date����
			java.util.Date date=sdf.parse(dstr);  
			String year=String.format("%tY", date);
			String month=String .format("%tm", date);
			String day=String .format("%td", date);
			System.out.println("��: " + year);  
		    System.out.println("��: " + month);  
		    System.out.println("��: " + day);  
			String excelPath = request.getSession().getServletContext().getRealPath("/upload/" + year + "/" + month + "/" + day + "/" +oriName);//��ȡ�ļ�·��
	        log.info("·��"+excelPath);
	        
	        try {
	            File file = new File(excelPath); // �����ļ�����
	            Desktop.getDesktop().open(file); // �������ڱ���������ע��Ĺ���Ӧ�ó��򣬴��ļ��ļ�file��
	        } catch (IOException | NullPointerException e) { // �쳣����
	            System.err.println(e);
	        }

			return "redirect:/uploadExcelController/select.do?type=hklr&dn=dh_repaymentEntry&qn=list&cn=w1&pagesize=5&pagenow=1";
			
		}


	private ServletRequest getServletContext() {
		// TODO Auto-generated method stub
		return null;
	}


	

	
	
	
	private void requestParams(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}


	private static String[] insert(String[] arr, String str)
	{
	int size = arr.length;

	String[] tmp = new String[size + 1];

	System.arraycopy(arr, 0, tmp, 0, size);

	tmp[size] = str;

	return tmp;
	}
	

	public static void volidateCelll(String s,int i){
		
	}
	
	
	/** 
     * ����excel�ļ��������󷵻� 
     * @param file 
     * @throws IOException  
     */  
	@RequestMapping("/readExcel.do")
	@ResponseBody
    public Map readExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{  
        
    	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file"); 	
    	String relatDir1=new SimpleDateFormat("yyyy/MM/dd/").format(new Date());
        //�ļ��в������򴴽�
        File fdir = new File("/KCDIMG/assess/upload/"+relatDir1);
        if (!fdir.exists()) { fdir.mkdirs(); }

        String oriName = file.getOriginalFilename();
        
        File tempFile = new File(fdir.getPath()+"/"+oriName);
        log.info("�ļ������ַ->"+tempFile);
        file.transferTo(tempFile);
        
        String[] string= new String[4];//����ss.length����ά���飬ÿ����������x������
    	//����ļ�  
        checkFile(file);  
        //���Workbook����������  
        Workbook workbook = getWorkBook(file);  
        //�������ض��󣬰�ÿ���е�ֵ��Ϊһ�����飬��������Ϊһ�����Ϸ���  
        Map<String,String> rowMap=new HashMap<String,String>();
        List<String[]> list = new ArrayList<String[]>();  
        if(workbook != null){  
            for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){  
                //��õ�ǰsheet������  
                Sheet sheet = workbook.getSheetAt(sheetNum);  
                if(sheet == null){  
                    continue;  
                }  
                //��õ�ǰsheet�Ŀ�ʼ��  
                int firstRowNum  = sheet.getFirstRowNum();  
                //��õ�ǰsheet�Ľ�����  
                int lastRowNum = sheet.getLastRowNum();  
                //ѭ�����˵�һ�е�������  
                for(int rowNum = firstRowNum+1;rowNum <= lastRowNum;rowNum++){  
                    //��õ�ǰ��  
                    Row row = sheet.getRow(rowNum);  
                    if(row == null){  
                        continue;  
                    }  
                    //��õ�ǰ�еĿ�ʼ��  
                    int firstCellNum = row.getFirstCellNum();  
                    //��õ�ǰ�е�����  
                    int lastCellNum = row.getPhysicalNumberOfCells();  
                    String[] cells = new String[row.getPhysicalNumberOfCells()];  
                    //ѭ����ǰ��  
                    for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){  
                        Cell cell = row.getCell(cellNum);  
                        cells[cellNum] = getCellValue(cell);
                        rowMap.put(ss[cellNum], cells[cellNum]);
						
					
					
                    }  
                    uploadExcelMapper.addExcel(rowMap);
                    //list.add(cells);  
                }  
            }  
            //workbook.close();  
        }  
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ     new Date()Ϊ��ȡ��ǰϵͳʱ��
		PageData pdsession= (PageData)request.getSession().getAttribute("pd");//��ȡsession��Ϣ
		Map<String, String> map=new HashMap<String,String>();
		map.put("uuid", CommonUtil.getUUID());//���к�
		map.put("oriName", oriName);//�ļ�����
	    map.put("dt_add", df.format(new Date()));//����ʱ��	
		map.put("financial_products", "");
		map.put("filepath", "upload/"+relatDir1+oriName);
		map.put("mid_add", pdsession.get("name").toString());//��ȡ������Ա
		recordMapper.addRecord(map);
		//map����ת��ΪJSON����
		//������ά����
       // JSONArray json = new JSONArray(string);
		//��JSON���󴫵ݸ�ǰ��AJAX����
//		response.setContentType("text/html;charset=UTF-8");
//		response.setContentType("application/json");
//		PrintWriter pw = response.getWriter();
//		//pw.print(json); // �켣ͼ������ȡ��������
//		pw.flush();
//		pw.close();
		//workbook.close();
		Map result =new HashMap();
		result.put("msg","����ɹ�");
		result.put("code","1");
		return result;
    }  
    public void checkFile(MultipartFile file) throws IOException{  
        //�ж��ļ��Ƿ����  
        if(null == file){  
            logger.error("�ļ������ڣ�");  
            throw new FileNotFoundException("�ļ������ڣ�");  
        }  
        //����ļ���  
        String fileName = file.getOriginalFilename();  
        //�ж��ļ��Ƿ���excel�ļ�  
        if(!fileName.endsWith(xls) && !fileName.endsWith(xlsx)){  
            logger.error(fileName + "����excel�ļ�");  
            throw new IOException(fileName + "����excel�ļ�");  
        }  
    }  
    public Workbook getWorkBook(MultipartFile file) {  
        //����ļ���  
        String fileName = file.getOriginalFilename();  
        //����Workbook���������󣬱�ʾ����excel  
        Workbook workbook = null;  
        try {  
            //��ȡexcel�ļ���io��  
            InputStream is = file.getInputStream();  
            //�����ļ���׺����ͬ(xls��xlsx)��ò�ͬ��Workbookʵ�������  
            if(fileName.endsWith(xls)){  
                //2003  
                workbook = new HSSFWorkbook(is);  
            }else if(fileName.endsWith(xlsx)){  
                //2007  
                workbook = new XSSFWorkbook(is);  
            }  
        } catch (IOException e) {  
            logger.info(e.getMessage());  
        }  
        return workbook;  
    }  
    public String getCellValue(Cell cell){  
        String cellValue = "";  
        if(cell == null){  
            return cellValue;  
        }  
        //�����ֵ���String�������������1����1.0�����  
        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){  
            cell.setCellType(Cell.CELL_TYPE_STRING);  
        }  
        //�ж����ݵ�����  
        switch (cell.getCellType()){  
            case Cell.CELL_TYPE_NUMERIC: //����  
                cellValue = String.valueOf(cell.getNumericCellValue());  
                break;  
            case Cell.CELL_TYPE_STRING: //�ַ���  
                cellValue = String.valueOf(cell.getStringCellValue());  
                break;  
            case Cell.CELL_TYPE_BOOLEAN: //Boolean  
                cellValue = String.valueOf(cell.getBooleanCellValue());  
                break;  
            case Cell.CELL_TYPE_FORMULA: //��ʽ  
                cellValue = String.valueOf(cell.getCellFormula());  
                break;  
            case Cell.CELL_TYPE_BLANK: //��ֵ   
                cellValue = "";  
                break;  
            case Cell.CELL_TYPE_ERROR: //����  
                cellValue = "�Ƿ��ַ�";  
                break;  
            default:  
                cellValue = "δ֪����";  
                break;  
        }  
        return cellValue;  
    }  
}


