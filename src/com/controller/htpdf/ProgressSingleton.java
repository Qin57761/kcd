package com.controller.htpdf;

import java.util.Hashtable;
public class ProgressSingleton {
  //Ϊ�˷�ֹ���û�������ʹ���̰߳�ȫ��Hashtable
  private static Hashtable<Object, Object> table = new Hashtable<>();
  public static void put(Object key, Object value){
      table.put(key, value);
  }
 
 public static Object get(Object key){
     return table.get(key);
 }
 
 public static Object remove(Object key){
     return table.remove(key);
 }
}
