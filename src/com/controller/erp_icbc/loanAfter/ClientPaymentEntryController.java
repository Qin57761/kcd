package com.controller.erp_icbc.loanAfter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.controller.Excel.UploadExcelController;
import com.model1.icbc.erp.PageData;
import com.service1.loan.AboutExcelService;
import com.service1.loan.AboutExcelService;
import com.util.limitutil;
/**
 * �ͻ��������-�ͻ�����¼�������
 * 
 * @author ��ʮ����
 * 2019-3-16
 */
@Controller
@RequestMapping("/loan")
public class ClientPaymentEntryController {
	private static Logger log = LogManager.getLogger(UploadExcelController.class.getName());
	@Autowired
	private AboutExcelService AboutExcelService;
	
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
