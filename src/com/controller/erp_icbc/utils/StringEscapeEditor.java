package com.controller.erp_icbc.utils;
import java.beans.PropertyEditorSupport;
import org.springframework.web.util.HtmlUtils;
/**
 * String ���͵��ӱܱ༭��
 * PropertyEditor�����Ա༭���Ľӿڣ����涨�˽��ⲿ����ֵת��Ϊ�ڲ�JavaBean����ֵ��ת���ӿڷ���
 * @author Administrator
 * @author:LiWang
 * @time:2018��8��2��
 */
public class StringEscapeEditor extends PropertyEditorSupport {
    public StringEscapeEditor() {}

    /* �����Զ�����һ���ַ�����ʾ���Ա��ⲿ�����Ա༭�����Կ��ӻ��ķ�ʽ��ʾ��
     * ȱʡ����null����ʾ�����Բ������ַ�����ʾ��
     * @see java.beans.PropertyEditorSupport#getAsText()
     */
    @Override
    public String getAsText() {
        Object value = getValue();//�������Եĵ�ǰֵ���������ͱ���װ�ɶ�Ӧ�İ�װ��ʵ��
        return value != null ? value.toString() : "";
    }
    /* ��һ���ַ���ȥ�������Ե��ڲ�ֵ������ַ���һ����ⲿ���Ա༭������
     * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null) {
            setValue(null);//�������Ե�ֵ�����������԰�װ�ഫ�루�Զ�װ�䣩
        } else {
        	/*Spring HtmlUtils��HTML����ת�壬�ɽ�HTML��ǩ����ת��
        	String s = HtmlUtils.htmlEscape("<div>hello world</div><p>&nbsp;</p>");  
        	ת����&lt;div&gt;hello world&lt;/div&gt;&lt;p&gt;&amp;nbsp;&lt;/p&gt;  
        	String s2 = HtmlUtils.htmlUnescape(s);    
        	ת����<div>hello world</div><p>&nbsp;</p> */ 
            setValue(HtmlUtils.htmlEscape(text));
        }
    }

}
