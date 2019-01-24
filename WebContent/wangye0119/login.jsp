<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="${pageContext.request.contextPath}/wangye0119/assets/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/wangye0119/assets/css/font-awesome.min.css" />
		<!--[if IE 7]>
		  <link rel="stylesheet" href="assets/css/font-awesome-ie7.min.css" />
		<![endif]-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/wangye0119/assets/css/ace.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/wangye0119/assets/css/ace-rtl.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/wangye0119/assets/css/ace-skins.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/wangye0119/css/style.css"/>
		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="assets/css/ace-ie.min.css" />
		<![endif]-->
		<script src="${pageContext.request.contextPath}/wangye0119/assets/js/ace-extra.min.js"></script>
		<!--[if lt IE 9]>
		<script src="assets/js/html5shiv.js"></script>
		<script src="assets/js/respond.min.js"></script>
		<![endif]-->
		<script src="${pageContext.request.contextPath}/wangye0119/js/jquery-1.9.1.min.js"></script>        
        <script src="${pageContext.request.contextPath}/wangye0119/assets/layer/layer.js" type="text/javascript"></script>
<title>登陆</title>

</head>

<body class="login-layout Reg_log_style" >
<div class="logintop">    
    <span>欢迎业务后台管理界面平台</span>    
    <ul>
    <li><a href="#">返回首页</a></li>
    <li><a href="#">帮助</a></li>
    <li><a href="#">关于</a></li>
    </ul>    
    </div>
    <div class="loginbody">
<div class="login-container">
	<div class="center">
	     <img src="${pageContext.request.contextPath}/wangye0119/images/kcd.png" />
							</div>
							<div class="space-6"></div>
							<div class="position-relative">
								<div id="login-box" class="login-box widget-box no-border visible">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="icon-coffee green"></i>
												管理员登陆
											</h4>
											<div class="login_icon"><img src="${pageContext.request.contextPath}/wangye0119/images/login.png" /></div>
											<form id="form1"  action="${pageContext.request.contextPath}/bmsLogin.do" method="post">
												<fieldset>
										<ul>
										   <li class="frame_style form_error">
										   		<label class="user_icon"></label>
										   		<input onkeydown="loginDown();" name="username" type="text" id="username"/><i>用户名</i>
										   </li>
										   <li class="frame_style form_error">
										   		<label class="password_icon"></label>
										   		<input onkeydown="loginDown();" name="password" type="password"   id="userpwd"/><i>密码</i>
										   </li>
										   <!-- 验证码 -->
										  <!--  <li class="frame_style form_error"><label class="Codes_icon">
											    </label><input name="yzm" type="text"   id="Codes_text"/><i>验证码</i>
											    <div class="Codes_region"></div>
										   </li> -->
  										</ul>
													<div class="space"></div>

													<div class="clearfix">
														<!-- <label class="inline">
															<input type="checkbox" class="ace">
															<span class="lbl">保存密码</span>
														</label> -->

														<button type="button" class="width-35 pull-right btn btn-sm btn-primary" id="login_btn">
															<i class="icon-key"></i>
															登陆
														</button>
													</div>

													<div class="space-4"></div>
												</fieldset>
											</form>

											<div class="social-or-login center">
												<span class="bigger-110">通知</span>
											</div>

											<div class="social-login center">
											本网站系统不再对IE8以下浏览器支持，请见谅。
											</div>
										</div><!-- /widget-main -->

										<div class="toolbar clearfix">
											
										</div>
									</div><!-- /widget-body -->
								</div><!-- /login-box -->
							</div><!-- /position-relative -->
						</div>
                        </div>
                        <div class="loginbm"><a href="">福建快车道网络科技有限公司</a> </div><strong></strong>
</body>
</html>
<script>
    function loginDown(){  
    	if (event.keyCode == 13){  
	        event.returnValue=false;  
	        event.cancel = true;  
	        $("#form1").submit();  // 提交到 controller
    	}  
	}   
/* $.ajax({
url:"${pageContext.request.contextPath}/bmsLogin.do",
type:"post",
dataType:"text",
data :{
	username:$("#username"),
	password:$("#userpwd")
},
success:function(data){
	layer.alert('登陆成功！',{
  	title: '提示框',				
  	icon:1
	});
},
error:function(){
	layer.alert('登陆失败！',{
  	title: '提示框',				
  	icon:1
	});
}
}); */
$('#login_btn').on('click', function(){
	     var num=0;
		 var str="";
     $("input[type$='text'],input[type$='password']").each(function(n){
          if($(this).val()==""){
			    layer.alert(str+=""+$(this).attr("name")+"不能为空！\r\n",{
                title: '提示框',				
				icon:0,								
          }); 
		    num++;
            return false;            
          } 
		 });
	     if(num>0){  return false;}	 	
	     else{
			  layer.alert('登陆成功！',{
	          title: '提示框',				
			   icon:1,		
			  });
	         //location.href="${pageContext.request.contextPath}/bmsLogin.do";
	         $("#form1").submit();
			 layer.close(index);	
		  }		     						
		
	});
  $(document).ready(function(){
	 $("input[type='text'],input[type='password']").blur(function(){
        var $el = $(this);
        var $parent = $el.parent();
        $parent.attr('class','frame_style').removeClass(' form_error');
        if($el.val()==''){
            $parent.attr('class','frame_style').addClass(' form_error');
        }
    });
	$("input[type='text'],input[type='password']").focus(function(){		
		var $el = $(this);
        var $parent = $el.parent();
        $parent.attr('class','frame_style').removeClass(' form_errors');
        if($el.val()==''){
            $parent.attr('class','frame_style').addClass(' form_errors');
        } else{
			 $parent.attr('class','frame_style').removeClass(' form_errors');
		}
		});
	  })
</script>