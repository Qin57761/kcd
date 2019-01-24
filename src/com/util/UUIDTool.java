package com.util;
import java.util.UUID;  

public class UUIDTool {  
	
	
	public static String getAccountIdByUUId() {
		 int machineId = 1;//���֧��1-9����Ⱥ��������
		 int hashCodeV = UUID.randomUUID().toString().hashCode();
		 if(hashCodeV < 0) {//�п����Ǹ���
		 hashCodeV = - hashCodeV;
		 }
		 return machineId + String.format("%015d", hashCodeV);
		}
  
    public UUIDTool() {  
    }  
    /**  
     * �Զ�����32λ��UUid����Ӧ���ݿ������id���в����á�  
     * @return  
     */  
    public static String getUUID() {  
        /*UUID uuid = UUID.randomUUID();  
        String str = uuid.toString();  
        // ȥ��"-"����  
        String temp = str.substring(0, 8) + str.substring(9, 13)  
                + str.substring(14, 18) + str.substring(19, 23)  
                + str.substring(24);  
        return temp;*/  
          
        return UUID.randomUUID().toString().replace("-", "");  
    }  
      
  
    public static void main(String[] args) {  
//      String[] ss = getUUID(10);  
        for (int i = 0; i < 10; i++) {  
            System.out.println("ss[" + i + "]=====" +getAccountIdByUUId());  
        }  
    }  
}  