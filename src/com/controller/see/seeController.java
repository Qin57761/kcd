package com.controller.see;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class seeController {

	/**
     * @return
     * ��ʾ��������ÿ5��������������������Ϣ
     */
    @RequestMapping(value="pushsee.do",produces="text/event-stream;charset=UTF-8")
    @ResponseBody
    public  String pushsee(){
        Random r=new Random();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("111111111");
        return "data:Testing 1,2,3"+r.nextInt()+"\n\n";
    }
	
    
}
