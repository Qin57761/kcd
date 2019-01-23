package com.controller.erp_icbc;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import javax.swing.JFileChooser;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.model1.icbc.erp.PageData;
import com.service1.erp_icbc.erp_loanBeforeService;
import com.util.limitutil;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Controller
public class erp_loanBeforeController {
	@Autowired
	private erp_loanBeforeService erp_loanBeforeService;
	
	
	//�������->��ǰҵ�����->�ſ��¼-���б�ҳ
	@RequestMapping(value="erp/lengding_list.do",produces = "text/html;charset=UTF-8") 
	public String lengding_list(
			String selectMsg, //��ѯ�ֶ�
			String assess_cp, //��ѯ�ֶ�-�ſ�״̬
			Integer pagesize,
			Integer pagenow,
			String dn,
			String qn,
			String cn,
			String type,
			HttpServletRequest request) throws UnsupportedEncodingException{
		System.err.println(selectMsg);
		//SQL��ѯ����ԭʼ����
		List<PageData> pdDate = new ArrayList<>();  
		//ִ��sql���
		PageData pd = new PageData();
		//��ѯ����
		pd.put("dn","selectListLendingResult");
		PageData pdLoginSession= (PageData)request.getSession().getAttribute("pd");
		pd.put("fsid",pdLoginSession.get("icbc_erp_fsid"));
		//��ѯ�ֶ�
		if(null!=selectMsg){
			pd.put("selectMsg",new String(selectMsg.getBytes("ISO-8859-1"),"UTF-8"));
			request.setAttribute("selectMsg",new String(selectMsg.getBytes("ISO-8859-1"),"UTF-8"));
		}
		if(assess_cp!=null){
			pd.put("assess_cp",assess_cp);
			request.setAttribute("assess_cp",assess_cp); //��ѯ����״̬�ֶ�
		}
		pdDate = erp_loanBeforeService.findList(pd);
		//��ѯ�ſ��� start
		PageData getLengingMoney = new PageData();
		PageData pdMoney = new PageData();
		for(int i=0;i<pdDate.size();i++){
			//��ȡÿ���ͻ���Ϣ
			getLengingMoney = pdDate.get(i);
			pdDate.remove(i);
			int icbc_id = (int) getLengingMoney.get("icbc_id");
			//��ѯÿ���ͻ��ķſ���ſ�����
			pdMoney.put("dn","selectOneLendingMoeny");
			pdMoney.put("icbc_id",icbc_id);
			PageData getMoney = erp_loanBeforeService.findById(pdMoney);
			if(getMoney != null){
				//����ֶε� newpdList����ǰ̨������
				if(getMoney.get("fk_money")!=null){
					getLengingMoney.put("fk_money",getMoney.get("fk_money"));
				}
				if(getMoney.get("fk_date")!=null){
					getLengingMoney.put("fk_date",getMoney.get("fk_date"));
				}
			}
			pdDate.add(getLengingMoney);
		}
		//��ѯ�ſ��� end
		//��ҳ
		int ps = 0;
		int pn = 0;
		if(null!=pagesize && !pagesize.equals("")){
			ps = pagesize;
		}else{
			ps = 10;
		}
		if(null!=pagenow && !pagenow.equals("")){
			pn = pagenow;
		}else{
			pn = 1;
		}
		//�����ҳ��ʵ��չʾ������
		List<PageData> newpdList = new ArrayList<>(); 
		newpdList = limitutil.fy(pdDate,ps,pn);	
		int q = pdDate.size()%ps;
		int totalpage=0;//��ҳ��
		if(q == 0){
			totalpage = pdDate.size()/ps;	    		
		}else{
			totalpage = pdDate.size()/ps+1;
		} 
		//����ǰ̨����
		//request.setAttribute("pdDate",pdDate);
		request.setAttribute("dn",dn);
		request.setAttribute("qn",qn);
		request.setAttribute("cn",cn);
		request.setAttribute("type",type);
		//��ҳ start 
		request.setAttribute("totalpage",totalpage);
		request.setAttribute("totalsize",pdDate.size());
		request.setAttribute("pdDate",newpdList);
		request.setAttribute("pagesize",ps);
		request.setAttribute("pagenow",pn);
		//��ҳ end
		return "kjs_icbc/index";
	}
	
	
	
	//�������->��ǰҵ�����->���ʼ�¼-���б�ҳ
	@SuppressWarnings("unchecked")
	@RequestMapping(value="erp/matEndowentResult_list.do",produces = "text/html;charset=UTF-8") 
	public String matEndowentResult_list(
			String selectMsg, //��ѯ�ֶ�-ҵ���Ż�ͻ����������֤��
			String assess_cp, //��ѯ�ֶ�-����״̬
			Integer pagesize,
			Integer pagenow,
			String dn,
			String qn,
			String cn,
			String type,
			HttpServletRequest request) throws UnsupportedEncodingException{
		System.err.println(selectMsg);
		//SQL��ѯ����ԭʼ����
		List<PageData> pdDate=new ArrayList<>(); 
		//ִ��sql���
		PageData pd = new PageData();
		//��ѯ����
		pd.put("dn","selectListMatEndowmentResult");
		PageData pdLoginSession= (PageData)request.getSession().getAttribute("pd");
		pd.put("fsid",pdLoginSession.get("icbc_erp_fsid"));
		//��ѯ�ֶ�  �����ѯ�����͸�ֵ����pd SQL���в�ѯ,����,selectMsgΪ��
		if(selectMsg!=null){
			pd.put("selectMsg",new String(selectMsg.getBytes("ISO-8859-1"),"UTF-8"));
			request.setAttribute("selectMsg",new String(selectMsg.getBytes("ISO-8859-1"),"UTF-8")); //��ѯ����״̬�ֶ�
		}
		if(assess_cp!=null){
			pd.put("assess_cp",assess_cp);
			request.setAttribute("assess_cp",assess_cp); //��ѯ����״̬�ֶ�
		}
		pdDate = erp_loanBeforeService.findList(pd);
		//��ѯ���ʽ�� start
		PageData getMatEndownmentMoney = new PageData();
		PageData pdMoney = new PageData();
		for(int i=0;i<pdDate.size();i++){
			//��ȡÿ���ͻ���Ϣ
			getMatEndownmentMoney = pdDate.get(i);
			pdDate.remove(i);
			int icbc_id = (int) getMatEndownmentMoney.get("icbc_id");
			//��ѯÿ���ͻ��ķſ���ſ�����
			pdMoney.put("dn","selectOneMatEndwomentMoeny");
			pdMoney.put("icbc_id",icbc_id);
			PageData getValues = erp_loanBeforeService.findById(pdMoney);
			if(getValues!=null){
				Map<String,Object> result_1_value = JSON.parseObject(getValues.get("result_1_value").toString());  // String ת JSONObject 
				if(result_1_value!=null){
					//����ֶε� newpdList����ǰ̨������
					if(result_1_value.get("rz_dzje")!=null){
						getMatEndownmentMoney.put("rz_dzje",result_1_value.get("rz_dzje"));
					}
					if(result_1_value.get("rz_date")!=null){
						getMatEndownmentMoney.put("rz_date",result_1_value.get("rz_date"));
					}
					if(result_1_value.get("rz_bank")!=null){
						getMatEndownmentMoney.put("rz_bank",result_1_value.get("rz_bank"));
					}
				}
			}
			pdDate.add(getMatEndownmentMoney);
		}
		//��ѯ���ʽ�� end
		//��ҳ
		int ps=0;
		int pn=0;
		if(pagesize!=null&&!pagesize.equals("")){
			ps=pagesize;
		}else{
			ps=10;
		}
		if(pagenow!=null&&!pagenow.equals("")){
			pn=pagenow;
		}else{
			pn=1;
		}
		//�����ҳ��ʵ��չʾ������
		List<PageData> newpdList=new ArrayList<>(); 
		newpdList=limitutil.fy(pdDate,ps,pn);		
		int q=pdDate.size()%ps;
		int totalpage=0;//��ҳ��
		if(q==0){
			totalpage=pdDate.size()/ps;	    		
		}else{
			totalpage=pdDate.size()/ps+1;
		} 
		
		//����ǰ̨����
		//request.setAttribute("pdDate",pdDate);
		request.setAttribute("dn",dn);
		request.setAttribute("qn",qn);
		request.setAttribute("cn",cn);
		request.setAttribute("type",type);
		//��ҳ start 
		request.setAttribute("totalpage",totalpage);
		request.setAttribute("totalsize",pdDate.size());
		request.setAttribute("pdDate",newpdList);
		request.setAttribute("pagesize",ps);
		request.setAttribute("pagenow",pn);
		//��ҳ end
		return "kjs_icbc/index";
	}
	
	//�������->��ǰҵ�����->ҵ�񸶿�����-���б�ҳ
	@RequestMapping(value="erp/loanBefore_list.do",produces = "text/html;charset=UTF-8") 
	public String loanBefore_list(
			String selectMsg, //��ѯ�ֶ�
			Integer pagesize,
			Integer pagenow,
			String dn,
			String qn,
			String cn,
			String type,
			HttpServletRequest request) throws UnsupportedEncodingException{
		System.err.println(selectMsg);
		//SQL��ѯ����ԭʼ����
		List<PageData> pdDate=new ArrayList<>(); 
		//ִ��sql���
		PageData pd = new PageData();
		//��ѯ����
		pd.put("dn","selectList");
		PageData pdLoginSession= (PageData)request.getSession().getAttribute("pd");
		System.err.println(pdLoginSession+"-999999");
		pd.put("fsid",pdLoginSession.get("icbc_erp_fsid"));
		//��ѯ�ֶ�  �����ѯ�����͸�ֵ����pd SQL���в�ѯ,����,selectMsgΪ��
		if(selectMsg!=null){
			pd.put("selectMsg",new String(selectMsg.getBytes("ISO-8859-1"),"UTF-8"));
			request.setAttribute("selectMsg",new String(selectMsg.getBytes("ISO-8859-1"),"UTF-8"));
		}
		pdDate = erp_loanBeforeService.findList(pd);
		//��ҳ
		int ps=0;
		int pn=0;
		if(pagesize!=null&&!pagesize.equals("")){
			ps=pagesize;
		}else{
			ps=10;
		}
		if(pagenow!=null&&!pagenow.equals("")){
			pn=pagenow;
		}else{
			pn=1;
		}
		//�����ҳ��ʵ��չʾ������
		List<PageData> newpdList=new ArrayList<>(); 
		newpdList=limitutil.fy(pdDate,ps,pn);		
		int q=pdDate.size()%ps;
		int totalpage=0;//��ҳ��
		if(q==0){
			totalpage=pdDate.size()/ps;	    		
		}else{
			totalpage=pdDate.size()/ps+1;
		} 
		System.err.println("list--"+newpdList);
		//����ǰ̨����
		//request.setAttribute("pdDate",pdDate);
		request.setAttribute("dn",dn);
		request.setAttribute("qn",qn);
		request.setAttribute("cn",cn);
		request.setAttribute("type",type);
		//��ҳ start 
		request.setAttribute("totalpage",totalpage);
		request.setAttribute("totalsize",pdDate.size());
		request.setAttribute("pdDate",newpdList);
		request.setAttribute("pagesize",ps);
		request.setAttribute("pagenow",pn);
		//��ҳ end
		return "kjs_icbc/index";
	}
	//�������->��ǰҵ�����->ҵ�񸶿�����-������ҳ
	@RequestMapping(value="erp/loanBefore_form.do",produces = "text/html;charset=UTF-8") 
	public ModelAndView loanBefore_form(
			String date,
			int icbc_id,
			String dn,
			String qn,
			String cn,
			String type,
			HttpServletRequest request){
		//ִ��sql���
		PageData pd = new PageData();
		pd.put("dn","selectOneBusiness");
		pd.put("icbc_id",icbc_id);
		//��ѯ����
		PageData pdDate = erp_loanBeforeService.findById(pd);
		pdDate.put("date",date);
		//��ȡ��½�ŵ���Ϣ start
		PageData pdLoginSession= (PageData)request.getSession().getAttribute("pd");
		pdLoginSession.get("icbc_erp_fsid");//��˾ID
		Config cf = new Config();
		cf.get(request,"aa");
		//{sex=0, gems_imgurl=, deltag=0, modal_tag=1, tel=18637815946, fs_type=2, icbc_erp_tag=1, dt_edit=2018-09-21 10:11:50.0, icbc_erp_fsid=1708, gems_ssbm=0, id=4826, username=18637815946, ssm_id=0, qx_type=2, erp_tag=1, agpid=0, purview_map=glzx,gzrw,wdrw,wdcy,wdqd,qbrw,zhgl,gsgl,rygl,zhqx,gsgladd,gsgldelete,gsglupdate,zhqxadd,zhqxdelete,zhqxupdate,rygladd,rygldelete,ryglupdate,wlghd,zx,qcpg,kk,ssmq,qcdk,dhgl,yhhkxq,yhhklr,dclcjyq,dclzjyq,dclgjyq,dcltc,dclgs,dhx,yhx,cwgl,khdkmx,zxsp,clgc,zxcx,cxjg_3,zxtr,zxyhyj_6,trsh_7,clpg,pgjsh_11,yhds,dsjg_15,kksq,sfhcjg_19,fkkkjg_20,spmq,jgfk_24,kqyywsp,zgsh_27,zjlsh_29,qcdksh,zysh_33,zgsh_35,jlsh_37,zjsh_39,nstr,jgzjl_42,trshyj_43,trzg_44,trjl_45,zjfp,qrsqdz_48,zjfp_49,cz_50,sslr_51,cwqrdz_52,yhdksq,jgjscl_57,jtsjqr_58,yhsjqr_59,yhspjg_60,yhfkjg_61,skqr_62,bcclqr_63,bccl_64,gsgd,jtzzgd_67,zzgd_68,shybcl_69,xzrk_70,dygd,gzjl_73,dycljsjg_74,jgsjqr_75,dyqkjl_76,dycljh_77,shsjqr_78,dyclzyh_79,yhsjqr_80,lryhcyqk_81,ywxxxg,ywglb_84,xtyw_85,ywxxxgsq_96,tdtf,shytdsh_88,tdsjxz_89,shjltdsh_90,jghkjf_91,gsqrdz_92,cljh_93,jgsjqr_94,khgl,zdrxx,ghrxx,qtlxr,srxx,jtxx,fcxx,dkgl,clxx,zzsc,sfrz,sjhm,mzmd,grfxxx,bjgcxxx,fqzbg,zxbg,yycl,yycl11,yycl2,yycl3,yycl4,yycl5,yycl6,yycl7,yycl8,yycl9,yycl10,sfmx,clhsqk,rwcl,financing,financing_101,financing_102,financing_103,, gemsid=4550, bc_title=����Ȩ��, showtag=1, name=�����, mid_edit=4797, issupplier=0, mid_add=4797, fsname=�����, logindt=2018-12-13 14:30:57.0, dt_add=2018-08-23 21:47:55.0, cp=1, gems_id=4550, email=, jgid=141fe1da9e8005810e3, userpass=8f426e98572b28d0e8383c20e8f0bea2, fs_id=1708, upac_id=0, isadmin=1, loginip=218.82.140.129, erp_agpid=6}-9999999999999999999
		//��ȡ��½�ŵ���Ϣ end
		//����ǰ̨����
		request.setAttribute("reqName",pdLoginSession.get("name"));
		request.setAttribute("icbc_id",icbc_id);
		request.setAttribute("pd",pdDate);
		request.setAttribute("dn",dn);
		request.setAttribute("qn",qn);
		request.setAttribute("cn",cn);
		request.setAttribute("type",type);
		return new ModelAndView("kjs_icbc/index");
	}
	
	//ҵ�����븶� ������ӡ
	@RequestMapping(value="erp/businessPaymentRequestForm.do",produces="text/html;charset=UTF-8")
	public void businessPaymentRequestForm(
			String code,
			String name,
			String businessType,
			String reqName,
			String dept,//?
			String date,
			String moneyName,
			String moneyNumber,
			String bank,
			String money,
			String bigMoney,
			String moneyUse,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Map<String,Object> dataMap = new HashMap<String,Object>();
		if(businessType.equals("1"))businessType = "�³�";
		if(businessType.equals("2"))businessType = "���ֳ�";
		if(moneyUse.equals("1"))moneyUse = "���մ�����������";
		if(moneyUse.equals("2"))moneyUse = "���մ��������";
		dataMap.put("code",code); // ҵ����
		dataMap.put("name",new String(name.getBytes("ISO-8859-1"),"UTF-8")); // �ͻ�����
		dataMap.put("businessType",businessType); // ҵ������  1�³� 2���ֳ�
		dataMap.put("reqName",new String(reqName.getBytes("ISO-8859-1"),"UTF-8")); // ������
		//���� start
		Map<String,Object> deptMap = new HashMap<String,Object>();
		deptMap.put("1","��Ӫ��");
		deptMap.put("2","����");
		deptMap.put("3","��ز�");
		deptMap.put("4","������");
		//���� end
		dataMap.put("dept",deptMap.get(dept)); // ��	��?
		dataMap.put("date",date); //  ��������
		dataMap.put("moneyName",new String(moneyName.getBytes("ISO-8859-1"),"UTF-8")); // �տ��˻�
		dataMap.put("moneyNumber",new String(moneyNumber.getBytes("ISO-8859-1"),"UTF-8")); // �տ��˺�
		dataMap.put("bank",new String(bank.getBytes("ISO-8859-1"),"UTF-8")); // �տ����
		dataMap.put("money",money); // ���
		dataMap.put("bigMoney",new String(bigMoney.getBytes("ISO-8859-1"),"UTF-8")); // ��д���
		dataMap.put("moneyUse",moneyUse); // ������; 1���մ�����������   2���մ��������
		dataMap.put("operating", ""); // ��Ӫ�������� ?
		dataMap.put("riskControl", ""); //��ز������� ?
		dataMap.put("financial", ""); //���񲿸����� ?
		dataMap.put("manager", ""); // �ܾ��� ?
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		//�ļ�·�� E:\kuaiJinSuo___DuoYing\.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps\kcd\kjs_icbc
		String ff = request.getSession().getServletContext().getRealPath("/kjs_icbc"); //��ȡģ�������ļ���
		configuration.setDirectoryForTemplateLoading(new File(ff));
		// ����ĵ�·��������
		//File outFile = new File("H://BusinessPaymentRequestFormA.doc");
		//��utf-8�ı����ȡftl�ļ�
		Template t =  configuration.getTemplate("BusinessPaymentRequestFormA.xml","utf-8");
		response.setContentType("multipart/form-data"); //���������ļ����� application/octet-stream  
        response.setCharacterEncoding("UTF-8"); //���ñ����ʽ
        String fileName = new String("ҵ�񸶿����뵥(�������)".getBytes("GB2312"),"ISO-8859-1")+".doc"; //java.net.URLEncoder.encode("ҵ�񸶿����뵥(�������)","UTF-8")+".doc"
        response.setHeader("Content-Disposition","attachment;filename="+fileName); //�����ļ���    
        Writer out = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(response.getOutputStream()),"utf-8"),1024);
		t.process(dataMap,out);
		out.close();
	}
	
	//��ӡ������
	public static void main(String[] args){
         JFileChooser fileChooser = new JFileChooser(); // ������ӡ��ҵ
         int state = fileChooser.showOpenDialog(null);
         if (state == fileChooser.APPROVE_OPTION) {
             File file = fileChooser.getSelectedFile(); // ��ȡѡ����ļ�
             // ������ӡ�������Լ�
             HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
             // ���ô�ӡ��ʽ����Ϊδȷ�����ͣ�����ѡ��autosense
             DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
             // �������еĿ��õĴ�ӡ����
             PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
             // ��λĬ�ϵĴ�ӡ����
             PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
             // ��ʾ��ӡ�Ի���
             PrintService service = ServiceUI.printDialog(null, 200, 200,printService, defaultService, flavor, pras);
             if (service != null) {
                 try {
                     DocPrintJob job = service.createPrintJob(); // ������ӡ��ҵ
                     FileInputStream fis = new FileInputStream(file); // �������ӡ���ļ���
                     DocAttributeSet das = new HashDocAttributeSet();
                     Doc doc = new SimpleDoc(fis,flavor,das);
                     job.print(doc,pras);
                 } catch (Exception e){
                	 e.printStackTrace();
                 }
             }
         }
     }
}
