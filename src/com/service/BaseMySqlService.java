package com.service;

import org.springframework.transaction.annotation.Transactional;

/** 
* ����mysql Service������ 
* ʹ��isap����Դ���ع� 
* @author zhuc 
* @create 2013-3-11 ����4:27:33 
*/  
@Transactional(value = "kcdweb", rollbackFor = Exception.class)  
public abstract class BaseMySqlService {  

}
