package com.controller.erp_icbc.utils;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
public class PageInfo {
	  private final static int PAGESIZE = 10; //Ĭ����ʾ�ļ�¼�� 
	    private int total; // �ܼ�¼ 
	    private List rows; //��ʾ�ļ�¼  
	    @JsonIgnore
	    private int from;//��ʼ�ļ�¼
	    @JsonIgnore
	    private int size;//�����ļ�¼
	    @JsonIgnore
	    private int nowpage; // ��ǰҳ 
	    @JsonIgnore
	    private int pagesize; // ÿҳ��ʾ�ļ�¼�� 
	    @JsonIgnore
	    private Map<String, Object> condition; //��ѯ����
	    @JsonIgnore
	    private String sort = "seq";// �����ֶ�
	    @JsonIgnore
	    private String order = "asc";// asc��desc mybatis Order �ؼ���
	    public PageInfo() {}
	    //���췽��
	    public PageInfo(int nowpage, int pagesize) {
	        //���㵱ǰҳ  
	        if (nowpage < 0) {
	            this.nowpage = 1;
	        } else {
	            //��ǰҳ
	            this.nowpage = nowpage;
	        }
	        //��¼ÿҳ��ʾ�ļ�¼��  
	        if (pagesize < 0) {
	            this.pagesize = PAGESIZE;
	        } else {
	            this.pagesize = pagesize;
	        }
	        //���㿪ʼ�ļ�¼�ͽ����ļ�¼  
	        this.from = (this.nowpage - 1) * this.pagesize;
	        this.size = this.pagesize;
	    }

	    // ���췽��
	    public PageInfo(int nowpage, int pagesize, String sort, String order) {
	        this(nowpage, pagesize) ;
	        // �����ֶΣ������Ƿ���
	        this.sort = sort;
	        this.order = order;
	    }

	    public int getTotal() {
	        return total;
	    }

	    public void setTotal(int total) {
	        this.total = total;
	    }

	    public List getRows() {
	        return rows;
	    }

	    public void setRows(List rows) {
	        this.rows = rows;
	    }

	    public int getFrom() {
	        return from;
	    }

	    public void setFrom(int from) {
	        this.from = from;
	    }

	    public int getSize() {
	        return size;
	    }

	    public void setSize(int size) {
	        this.size = size;
	    }

	    public int getNowpage() {
	        return nowpage;
	    }

	    public void setNowpage(int nowpage) {
	        this.nowpage = nowpage;
	    }

	    public int getPagesize() {
	        return pagesize;
	    }

	    public void setPagesize(int pagesize) {
	        this.pagesize = pagesize;
	    }

	    public Map<String, Object> getCondition() {
	        return condition;
	    }

	    public void setCondition(Map<String, Object> condition) {
	        this.condition = condition;
	    }

	    public String getSort() {
	        return sort;
	    }

	    public void setSort(String sort) {
	        this.sort = sort;
	    }

	    public String getOrder() {
	        return order;
	    }

	    public void setOrder(String order) {
	        this.order = order;
	    }

}
