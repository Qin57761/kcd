package com.controller.erp_icbc;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
/**�ж϶����Ƿ�Ϊnull ���� �ַ�������ֵ�Ƿ�Ϊ���ַ�
 * @Description:TODO
 * @author:LiWang
 * @time:2018��7��28��
 */
public class EmptyUtil {
	 public static boolean isNull(Object obj) {
          return obj == null;
    }
  
    public static boolean isNotNull(Object obj) {
          return !isNull(obj);
    }
    /**
     * @param obj
     * @return
     * @Description: TODO
     * @param name
     * @return 
     */
    public static boolean isEmpty(Object obj) {
          if (obj == null) return true;
          else if(obj instanceof String) return obj.equals("") || obj.equals("null");
          else if (obj instanceof CharSequence) return ((CharSequence) obj).length() == 0;
          /*����� collection ������Ԫ�أ��򷵻� true*/
          else if (obj instanceof Collection) return ((Collection) obj).isEmpty();
          else if (obj instanceof Map) return ((Map) obj).isEmpty();
          else if (obj.getClass().isArray()) return Array.getLength(obj) == 0;
          return false;
     }
     public static boolean isNotEmpty(Object obj) {
          return !isEmpty(obj);
    }
}
