package com.util.duoying;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import com.alibaba.fastjson.JSONObject;

public class attachmentutil {

	
    // ����
    public static JSONObject addAttachment(String fileName,String fileType,String url) {
       // List<JSONObject> attachment = new ArrayList<JSONObject>();
        JSONObject xxz = new JSONObject();	
        xxz.put("fileName",fileName);
        xxz.put("fileUrl","http://a.kcway.net/assess/"+url);
        xxz.put("fileType",fileType);
        xxz.put("fileString","");
        xxz.put("remark", "����");	       
        return xxz;
    }	
    /**
     * ������Ϣ ,����
     * 
     * @return
     */
    public static JSONObject addBank() {
        //List<JSONObject> banks = new ArrayList<>();
        JSONObject oneBank = new JSONObject();
        oneBank.put("bankName", "��������");// �������� bank
        oneBank.put("bankBranchName", "֧��");// String ����/���������� name
        oneBank.put("accountNo", "abc");// String �����˺� cardunm
        //banks.add(oneBank);
        return oneBank;
    }
    
    
}
