package com.controller.erp_icbc;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.controller.erp_icbc.base.BaseController;
import com.controller.erp_icbc.result.Result;
import com.controller.erp_icbc.utils.EmptyUtil;
import com.model1.icbc.erp.HK;
import com.service1.erp_icbc.dh_hkService;
/**������������
 * @Description:TODO
 * @author:LiWang
 * @time:2018��7��28��
 */
@Controller
@RequestMapping("erp/")
public class DaiHou_Controller extends BaseController{
	@Autowired
	private  dh_hkService daihou; 

	/** ��ѯ�û���ÿ�ڻ�������
	 * @param �û���Ψһ��ʶ
	 */
	@RequestMapping(value="srbi.do")
	@ResponseBody
	public Result selectRepaymentById(String id){
		Result result=new Result();
		if(!EmptyUtil.isEmpty(id)){ 
			List<Map<String, String>> shkb = daihou.shkb(id);
			if(shkb.size()==0){
				result.setMessage("����û�л�����Ϣ");
				result.setSuccess(false);
			}else{
				result.setSuccess(true);
				result.setData(shkb);
			}
		}
		result.setSuccess(false);
		result.setMessage("�����������");
		return result;
	} 
	/**
	 ������������ģ��
	 */
	@RequestMapping(value="lrt.do")
	public ResponseEntity loadRepaymentTemplate(HttpServletResponse response,HttpServletRequest request){
		return this.download(new File(this.readPath(request,"repaymentExcel/A.txt")));
	}
	
	/*�ϴ�ģ��*/
	@RequestMapping(value="urt.do")
	public Result uploadRepaymentTemplate(){
		Result result=new Result();
		return result;
	}
	
	/**��ӻ�����Ϣ
	 */
	@RequestMapping(value="arepayment.do")
	@ResponseBody
	public int addRepayment(HttpServletRequest request){
		int code=1;
		try {
			HK hk=new HK();
			hk.setHk_date(request.getParameter("da").toString());
			hk.setHk_periods(request.getParameter("periods").toString());
			hk.setHk_money(request.getParameter("money").toString());
			hk.setKk_id(request.getParameter("ordercode").toString());
			hk.setU_id(Integer.parseInt(this.getUserId(request)));
			code=daihou.addhk(hk);
		} catch (Exception e) {
			code=0;
		}
		return code;
	}

	/** ҳ����ת
	 * @param type ��Ȩ���õ�
	 * @param dn   �����İ�� ���� hk (������)
	 * @return 
	 */
	@RequestMapping(value="demo.do",produces = "text/html;charset=UTF-8")  
	public ModelAndView index(
			String  type, 
			String  dn,   
			String  qn,
 			HttpServletRequest request
 			) throws UnsupportedEncodingException{
		//Ĭ�Ϸ����û��������
		if(EmptyUtil.isEmpty(type) || EmptyUtil.isEmpty(dn) || EmptyUtil.isEmpty(qn)){
			type="hk";
			dn="hk";
			qn="list";
		}
		request.setAttribute("dn",dn);
		request.setAttribute("qn",qn);
		request.setAttribute("pd",type); 
		return new ModelAndView("kjs_icbc/index");
	}
}
