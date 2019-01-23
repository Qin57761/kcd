package com.controller.newAdd;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.model.apply;
import com.model1.mgcar;
import com.model1.mgcar_result;
import com.model1.mgcert;
import com.model1.mgcert_result;
import com.model1.ylds;
import com.model1.ylqy;
import com.service1.newAdd.TestKJSmgCarResultService;
import com.service1.newAdd.TestKJSmgcarService;
import com.service1.newAdd.TestKJSmgcertService;
import com.util.Page;

@Controller
public class TestKJSmgcarController {
	@Autowired
	private TestKJSmgcarService testKJSmgcarService;
	@Autowired
	private TestKJSmgCarResultService testKJSmgCarResultService;
	
	//����
	@RequestMapping(value="CaoZuoCar.do",produces="text/html;charset=UTF-8")
	public String CaoZuo(HttpServletRequest request,HttpServletResponse response,
		int type,
		int amid,
		String khName,
		String khCardId,
		String fsname,
		String gemsname) throws UnsupportedEncodingException{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String khNamee = new String(request.getParameter("khName").getBytes("iso-8859-1"),"utf-8");
		String khCardIdd = new String(request.getParameter("khCardId").getBytes("iso-8859-1"),"utf-8");
		String fsnamee = new String(request.getParameter("fsname").getBytes("iso-8859-1"),"utf-8");
		String gemsnamee = new String(request.getParameter("gemsname").getBytes("iso-8859-1"),"utf-8");
		
		String showImg = "cache/fillw_400h_400";
		mgcar mgcar = new mgcar();
		mgcar = testKJSmgcarService.TestKJSselectCaoZuo(amid);
		List<mgcar_result>  mgresult = new ArrayList();
		mgresult = testKJSmgCarResultService.TestKJSselectCaoZuoResultCar(amid);
		request.setAttribute("mgcar", mgcar);
		request.setAttribute("mgresult", mgresult);
		request.setAttribute("khName", khNamee);
		request.setAttribute("khCardId", khCardIdd);
		request.setAttribute("fsname", fsnamee);
		request.setAttribute("gemsname", gemsnamee);
		request.setAttribute("type", type); // 8 Ѻ��     9Ѻ֤
		request.setAttribute("showImg", showImg);
		return "testKJScaoZuo";
	}
	
	//������ת Two
	@RequestMapping(value="goToTwoCar.do",produces="text/html;charset=UTF-8")
	public String goToTwo(HttpServletRequest request,HttpServletResponse response,
			int type,
			int mgcertId,
			String khName,
			String khCardId,
			String status) throws UnsupportedEncodingException{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String khNamee = new String(request.getParameter("khName").getBytes("iso-8859-1"),"utf-8");
		String khCardIdd = new String(request.getParameter("khCardId").getBytes("iso-8859-1"),"utf-8");
		
		System.err.println(type+"---"+mgcertId);
		if(type == 2){
			List<ylds> yd = new ArrayList<ylds>();
			yd = testKJSmgcarService.TestKJSselectModelTwo(mgcertId); // ���ռ�¼
			ylds ydd = new ylds();
			if(yd!=null && yd.size()>0){
				ydd = yd.get(0);
			}
			//�½��ȶϢ��Ϣ
			List<ylqy> ylqy = new ArrayList<ylqy>();
			ylqy =  testKJSmgcarService.TestKJSselectModelTwoByMomeny(ydd.getBank_id());
			request.setAttribute("ylqy",ylqy);
			request.setAttribute("yd",yd);
			request.setAttribute("ydd",ydd);
			request.setAttribute("khName", khNamee);
			request.setAttribute("khCardId", khCardIdd);
			request.setAttribute("status", status); // �ж� ���� ״̬   2����   3ͨ��   5�ܾ�
			return "yl_modalTwo_Test";
		}else if(type == 3){
			List<ylds> yd = new ArrayList<ylds>();
			yd = testKJSmgcarService.TestKJSselectModelTwo(mgcertId); // ���ռ�¼
			ylds ydd = new ylds();
			if(yd!=null && yd.size()>0){
				ydd = yd.get(0);
			}
			//�½��ȶϢ��Ϣ
			List<ylqy> ylqy = new ArrayList<ylqy>();
			ylqy =  testKJSmgcarService.TestKJSselectModelTwoByMomeny(ydd.getBank_id());
			request.setAttribute("yd",yd);
			request.setAttribute("ydd",ydd);
			request.setAttribute("ylqy",ylqy);
			request.setAttribute("khName", khNamee);
			request.setAttribute("khCardId", khCardIdd);
			request.setAttribute("status", status); // �ж� ���� ״̬   2����   3ͨ��   5�ܾ�
			return "yl_modalThree_Test";
		}else if(type == 4){
			List<ylds> yd = new ArrayList<ylds>();
			yd = testKJSmgcarService.TestKJSselectModelTwo(mgcertId); // ���ռ�¼
			ylds ydd = new ylds();
			if(yd!=null && yd.size()>0){
				ydd = yd.get(0);
			}
			//�½��ȶϢ��Ϣ
			List<ylqy> ylqy = new ArrayList<ylqy>();
			ylqy =  testKJSmgcarService.TestKJSselectModelTwoByMomeny(ydd.getBank_id());
			request.setAttribute("yd",yd);
			request.setAttribute("ydd",ydd);
			request.setAttribute("ylqy",ylqy);
			request.setAttribute("khName", khNamee);
			request.setAttribute("khCardId", khCardIdd);
			request.setAttribute("status", status); // �ж� ���� ״̬   2����   3ͨ��   5�ܾ�
			return "yl_modalFour_Test";
		}
		return null;
	}
	
	//������ת One
	@RequestMapping(value="goToOneCar.do",produces="text/html;charset=UTF-8")
	public String goTo(HttpServletRequest request,HttpServletResponse response,
		String idcard,
		String cname) throws UnsupportedEncodingException{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String cnamee = new String(request.getParameter("cname").getBytes("iso-8859-1"),"utf-8");
		ylqy qy = new ylqy();  //513821198402169031
		qy = testKJSmgcarService.TestKJSselectModelOne("513821198402169031");
		request.setAttribute("cname", cnamee);
		request.setAttribute("idcard", idcard);
		request.setAttribute("qy",qy);
		return "yl_modalOne_Test";
	}
	
	//��ѯ��Ϣ
	@RequestMapping(value="TKJSselectAllCar.do",produces="text/html;charset=UTF-8")
	public String TKJSselectAll(HttpServletRequest request){
	  //����״̬��ʾ����
	  int status = Integer.parseInt(request.getParameter("status").replace("?v=4.0", ""));
	  ArrayList alist = new ArrayList();
	  String size=request.getParameter("size");
  	  String pagenow=request.getParameter("pagenow");
  	  int s;
  	  if(size!=null){
  		  s=Integer.parseInt(size);
  	  }else{
  		  s=10;
  	  }	    	 
  	  int totalCount = 1000;
	  Page page;
	  if(status == 2){
		totalCount = 98;
	  }else if(status == 3){
		totalCount = 13568;
	  }else if(status == 5){
		totalCount = 56;
	  }
	  	System.out.println("������"+totalCount+"��ǰҳ����"+pagenow);
		if (pagenow!=null) {				
			//System.out.println(0);
			page = new Page(totalCount, Integer.parseInt(pagenow),s);
			alist=this.testKJSmgcarService.TestKJSselectAll(status,page.getStartPos(),page.getPageSize());	
			///////����չʾʱ��///////////
				if(status == 2 || status == 3){
					if(pagenow.equals("1")){
						for(int i=0;i<10;i++){
							int[] time = {36,37,37,40,40,40,41,41,42,42};
							  Map map = (Map) alist.get(i);
							  Calendar now=Calendar.getInstance();
							  now.add(Calendar.MINUTE,-time[i]);
							  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							  String dateStr=sdf.format(now.getTimeInMillis());
							  map.put("dt_edit",dateStr);
						}
					}else if(pagenow.equals("2")){
						for(int i=0;i<10;i++){
							int[] time = {34,36,38,39,39,40,51,51,54,59};
							  Map map = (Map) alist.get(i);
							  Calendar now=Calendar.getInstance();
							  now.add(Calendar.DATE,-1);
							  now.add(Calendar.MINUTE,-time[i]);
							  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							  String dateStr=sdf.format(now.getTimeInMillis());
							  map.put("dt_edit",dateStr);
						}
					}
					
				}
			//////////////////////
		} else {
			//System.out.println(1);
			page = new Page(totalCount, 1,s);					
			alist=this.testKJSmgcarService.TestKJSselectAll(status,page.getStartPos(),page.getPageSize());	
			pagenow = "1";
			///////����չʾʱ��///////////
			if(status == 2 || status == 3 & pagenow.equals("1")){
				for(int i=0;i<10;i++){
					int[] time = {36,37,37,40,40,40,41,41,42,42};
					  Map map = (Map) alist.get(i);
					  Calendar now=Calendar.getInstance();
					  now.add(Calendar.MINUTE,-time[i]);
					  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					  String dateStr=sdf.format(now.getTimeInMillis());
					  map.put("dt_edit",dateStr);
				}
			}
			////////////////////
		}				
		int q=totalCount%s;
		int w=0;
		if(q==0){
			w=totalCount/s;
			
		}else{
			w=totalCount/s+1;
		}    		    
		request.setAttribute("status", status);
		request.setAttribute("w",w);
		request.setAttribute("pagenow",pagenow);
		request.setAttribute("size",s);
		request.setAttribute("totalCount",totalCount);
		request.setAttribute("alist", alist);
		return "testKJSmgcar";
	}
}
