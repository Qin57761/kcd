package com.controller.intercept;

import java.io.IOException;  
import java.util.Enumeration;  
import javax.servlet.FilterChain;  
import javax.servlet.RequestDispatcher;  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import org.apache.commons.lang.StringUtils;  
import org.springframework.web.filter.CharacterEncodingFilter;  
/* 
 * InvalidCharacterFilter������request�����еķǷ��ַ�����ֹ�ű����� 
 * InvalidCharacterFilter�̳���Spring��ܵ�CharacterEncodingFilter����������Ȼ������Ҳ�����Լ�ʵ������һ�������� 
 */  
public class InvalidCharacterFilter extends CharacterEncodingFilter{  
    // ��Ҫ���˵ķǷ��ַ�     
    private static String[] invalidCharacter = new String[]{  
    	"script","select","insert","document","window","function",  
        "delete","update","prompt","alert","create","alter",  
        "drop","iframe","link","where","replace","function","onabort",  
        "onactivate","onafterprint","onafterupdate","onbeforeactivate",  
        "onbeforecopy","onbeforecut","onbeforedeactivateonfocus",  
        "onkeydown","onkeypress","onkeyup","onload",  
        "expression","applet","layer","ilayeditfocus","onbeforepaste",  
        "onbeforeprint","onbeforeunload","onbeforeupdate",  
        "onblur","onbounce","oncellchange","oncontextmenu",  
        "oncontrolselect","oncopy","oncut","ondataavailable",  
        "ondatasetchanged","ondatasetcomplete","ondeactivate",  
        "ondrag","ondrop","onerror","onfilterchange","onfinish","onhelp",  
        "onlayoutcomplete","onlosecapture","onmouse","ote",  
        "onpropertychange","onreadystatechange","onreset","onresize",  
        "onresizeend","onresizestart","onrow","onscroll",  
        "onselect","onstaronsubmit","onunload","IMgsrc","infarction"  
    };  
  
    @Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)  
            throws ServletException, IOException{     
        String parameterName = null;  
        String parameterValue = null;  
        // ��ȡ����Ĳ���  
        @SuppressWarnings("unchecked")  
        Enumeration<String> allParameter = request.getParameterNames();  
        while(allParameter.hasMoreElements()){  
            parameterName = allParameter.nextElement();  
            parameterValue = request.getParameter(parameterName);  
            if(null != parameterValue){  
                for(String str : invalidCharacter){  
                    if (StringUtils.containsIgnoreCase(parameterValue, str)){  
                        request.setAttribute("errorMessage", "�Ƿ��ַ���" + str);  
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.jsp");  
                        requestDispatcher.forward(request, response);  
                        return;  
                    }  
                }  
            }  
        }  
        super.doFilterInternal(request, response, filterChain);  
    }  
}  
