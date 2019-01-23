package com.controller.guaziiapi;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.model.CBS.CbsSuccessfulPurchaseQueryReportExample;
import com.model.CBS.CbsSuccessfulPurchaseQueryReportExample.Criteria;
import com.model.jbapi.jbzxapiuser;
import com.service.guazi.GuaZiService;
import com.util.Page;
@Controller
public class GuaZiController {
	@Autowired
	private GuaZiService gzs;
	//��ѯ ��˾������ѯ������Ϣ(��������)
	@RequestMapping(value="/sgi.do")
	public ModelAndView query(HttpServletRequest request) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		Map<String,String> mapcondition=new HashMap();
		String spageNow=request.getParameter("pageNow");//��ǰҳ��
		String spageSize=request.getParameter("pageSize");//ÿҳ��ʾ������
		String conditionOne=request.getParameter("conditionOne");//���봴��ʱ������
		String conditionTwo=request.getParameter("conditionTwo");
		if(conditionOne!=null && !conditionOne.equals("")){//�������� 2018/03/05 - 2018/03/05
			int index=conditionOne.indexOf("-");
			mapcondition.put("timeFirst",conditionOne.substring(0,index-1));//2018/02/04  2018/02/06
			mapcondition.put("timeLast",conditionOne.substring(index+2));
		}
		//String conditionTwo=request.getParameter("conditionTwo");//����appkey/��˾����
		String conditionThree=request.getParameter("conditionThree");//����vin��
		String conditionFour=request.getParameter("conditionFour");//����״̬
		mapcondition.put("conditionOne",conditionOne);
		if(conditionTwo!=null){
			conditionTwo=new String(conditionTwo.getBytes("iso-8859-1"),"utf-8");
		}
		mapcondition.put("conditionTwo",conditionTwo);
		mapcondition.put("conditionThree",conditionThree);
		mapcondition.put("conditionFour",conditionFour);
		int ipageNow=1;//Ĭ��
		int ipageSize=10;//Ĭ��
		if(spageNow!=null && !spageNow.equals("") ){ 
			ipageNow=Integer.valueOf(spageNow);
		}
		if(spageSize!=null && !spageSize.equals("")){
			ipageSize=Integer.valueOf(spageSize);
		}
		Page page=new Page();
		page.setMap(mapcondition);//���ò�ѯ����
		page.setPageNow(ipageNow);//���õ�ǰҳ��
		page.setPageSize(ipageSize);//����ÿҳ��ʾ������
		int totalCount=gzs.OneToArrCountSelective(page);
		page.setTotalCount(totalCount);//����������
		List<jbzxapiuser> list=gzs.OneToArr(page);//��ѯ�����е�����
		page.setListdata(list);
		ModelAndView modelandview=new ModelAndView("guazi");
		modelandview.addObject("page", page);
		return modelandview;
	}
	//�����ť��ȡjson����
	@RequestMapping(value="/gbyid.do",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String reportDetailsJson(String gid){
			String s = gzs.selectbyid(gid);
			return s;
	}
}
