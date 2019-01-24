<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    

    <title>根据员工等级查询员工</title>
  
    <link rel="shortcut icon" href="favicon.ico"> <link href="css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
    <link href="css/font-awesome.min.css?v=4.4.0" rel="stylesheet">

    <link href="css/animate.min.css" rel="stylesheet">
    <link href="css/style.min.css?v=4.0.0" rel="stylesheet"><base target="_blank">

</head>

<body class="gray-bg"   >
    <div class="row wrapper border-bottom white-bg page-heading">
      
      <!-- ********************************************************** -->
         
             <div class="ibox-content">
                        <form method="get" class="form-horizontal">
                    
                         <h1>根据员工等级查询员工</h1>
                      
                         <div class="hr-line-dashed"></div>   
                          
                       <div class="form-group">
                                <label class="col-sm-3 control-label">公司名称：</label>

                                <div class="col-sm-8">
                                    <input type="text" placeholder="请输入公司名称" class="form-control">
                                </div>
                            </div>
                            
                           <div class="form-group">
                                <label class="col-sm-3 control-label">账户等级：</label>

                                <div class="col-sm-8">
                                    <label class="checkbox-inline">
                                        <input type="checkbox" value="option1" id="inlineCheckbox1">1级</label>
                                    <label class="checkbox-inline">
                                        <input type="checkbox" value="option2" id="inlineCheckbox2">2级</label>
                                    <label class="checkbox-inline">
                                        <input type="checkbox" value="option3" id="inlineCheckbox3">3级</label>
                                </div>
                            </div>
                            
                            
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-3">
                                    <button class="btn btn-primary" type="submit">搜索</button> 
                                    <button class="btn btn-white" type="submit">返回</button>
                                </div>
                            </div>
                        </form>
                    </div>
         
         
         <!-- ********************************************************** -->

    </div>
   
</body>

</html>