package com.service1.Repayment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mapper1.Repayment.RepaymentMapper;
import com.model1.icbc.erp.PageData;

@Service
@Transactional(value = "kcway2", rollbackFor = Exception.class)
public class RepaymentServiceImpl implements RepaymentService{

	@Autowired
	private RepaymentMapper repaymentMapper;

	@Override
	public List<PageData> selectRepayment(String param,PageData pd) {
		// TODO Auto-generated method stub
		return repaymentMapper.selectRepayment(param,pd);
	}

	@Override
	public Map<String, Object> selectBorrow(String icbc_id) {
		// TODO Auto-generated method stub
		return repaymentMapper.selectBorrow(icbc_id);
	}

	@Override
	public List<Map> selectschedule(String icbc_id) {
		// TODO Auto-generated method stub
		return repaymentMapper.selectschedule(icbc_id);
	}

	@Override
	public List<Map> selectafter(String icbc_id) {
		// TODO Auto-generated method stub
		return repaymentMapper.selectafter(icbc_id);
	}

	@Override
	public Map<String, Object> selectzdr(String icbc_id) {
		// TODO Auto-generated method stub
		return repaymentMapper.selectzdr(icbc_id);
	}

	@Override
	public int selectrepay(int icbc_id) {
		// TODO Auto-generated method stub
		return repaymentMapper.selectrepay(icbc_id);
	}

	@Override
	public int addrepay(int icbc_id, String fk_money, int aj_date) {
		// TODO Auto-generated method stub
		int result=0;
		Map<String, Object> map = repaymentMapper.selectID(icbc_id);
		Map<String, Object> addmap = new HashMap<String, Object>();
		double myyh=0;
		if(null != map){
			//�õ��ſ���
			BigDecimal mm = new BigDecimal(fk_money);
			//��������
			BigDecimal cc2=new BigDecimal( map.get("aj_date").toString());			
			//ÿ��Ӧ�� =�����ܶ�/��������
			BigDecimal dd2 = mm.divide(cc2, 3,BigDecimal.ROUND_DOWN);//��λС��
			String aa = dd2.toString();//ת��string
			System.out.println(aa);
			
			System.out.println(aa.length());//��ȡ����
			System.out.println(aa.substring(aa.indexOf(".")+3, aa.length()));
			String bb = aa.substring(aa.indexOf(".")+3, aa.length());//��ȡ����λС��
			int cc = Integer.parseInt(bb);//ת��int
			if(cc > 0){//�ж��Ƿ����0  �Ǿͱ�����λС��
				BigDecimal vv = dd2.setScale(2, BigDecimal.ROUND_DOWN);
				BigDecimal zero = new BigDecimal("0.01");
				myyh = vv.add(zero).doubleValue();
			}
			
			System.out.println("================:"+myyh);
			
		}
		
		//�õ���ǰʱ��
		Date date = new Date();  	
		int year=Integer.parseInt(new SimpleDateFormat("yyyy").format(date));//ȡ�����ֵ
		int month=Integer.parseInt(new SimpleDateFormat("MM").format(date))+1;//ȡ�����ֵ  
		String day="10";//ÿ�»�����	
		
		addmap.put("should_money", myyh);
		
		addmap.put("icbc_id", icbc_id);
		addmap.put("name", map.get("c_name"));
		addmap.put("cardno", map.get("c_cardno"));
		String should_data=null;
		for(int i=0;i<aj_date;i++){
			if(month > 12){
				year = year+1;
				month=1;
			}
			should_data = year+"-"+month+"-"+day;
			addmap.put("should_date", should_data);
			repaymentMapper.addrepay(addmap);
			month++;
		}		
		return result;
	}

	@Override
	public List<Map> selectimport() {
		// TODO Auto-generated method stub
		
		return repaymentMapper.selectimport();
	}
	// �޸Ļ����¼ 
	
	@Override
	public int updateschedule(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return repaymentMapper.updateschedule(map);
	}
	
}
