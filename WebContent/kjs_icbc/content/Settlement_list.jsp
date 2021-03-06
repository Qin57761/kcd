<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="content-wrapper" style="min-height: 943px;">
		<section class="content-header">
			<h1>
			结清处理<small>
			共1个
			</small>
			</h1>
		</section>
		<!-- Main content -->
		<section class="content">
			<div class="admin-tools">
				<div class="row">
					<div class="col-sm-10">
						<div class="btn-group">		
							<form class="form-horizontal" action="${pageContext.request.contextPath}/litigationManagementController/mortgageRecord.do?type=${requestScope.type }&dn=VehicleMortgage&qn=list&cn=w1" enctype="multipart/form-data" method="post">
								<input type="text" placeholder="请输入客户姓名或身份证号" name="param" class="form-control ng-pristine ng-untouched ng-valid ng-not-empty" style="width:500px;">
								<button class="btn btn-info search-btn" type="submit" style="background-color:#00acd6;">查询</button> 
								
							</form>	
							
							<!-- 文件上传 -->
							<script type="text/javascript">
								$(function(){
									$("#newmj").click(function(){
										alert("aaaa");
										$.ajax({
											url:"${pageContext.request.contextPath}/readExcel",
											type:"post",
											success:function(result){
												alert(result);
												var json=sval('('+result+')');
											}
										})
									})
								})
									
									
							</script>

							<script type="text/javascript">
							    function a3(){
									var s=$("#page_limit_select").val();
									var querytype=$("#querytype").val();		
									window.location.href ="${pageContext.request.contextPath}/litigationManagementController/mortgageRecord.do?type=${requestScope.type }&dn=VehicleMortgage&qn=list&pagesize="+s+"&pagenow=1";
							    };
							    
							    function nul(){
							    	alert("暂无此条数据")
							    }
							   
						    </script>
		
						</div>
					</div>		
					<div class="col-sm-2">
					   <div class="btn-group">											
					   <ul class="pagination no-margin">					     				       
						<li>
							<select id="page_limit_select" onchange="a3()" class="form-control">
							    <option value="${requestScope.pagesize}">当前每页${requestScope.pagesize}条</option>
								<option value="10">每页10条</option>
								<option value="20">每页20条</option>
								<option value="30">每页30条</option>
								<option value="40">每页40条</option>
								<option value="50">每页50条</option>
								<option value="60">每页60条</option>
								<option value="70">每页70条</option>
								<option value="80">每页80条</option>
								<option value="90">每页90条</option>
								<option value="100">每页100条</option>
							</select>
						</li>
						<li>
							<%
							int pagenow1 = Integer.parseInt(request.getAttribute("pagenow").toString());
						    int totalpage1 = Integer.parseInt(request.getAttribute("totalpage").toString());
						    if(pagenow1>1&&pagenow1<=totalpage1){
							%>
							<a href="${pageContext.request.contextPath }/litigationManagementController/mortgageRecord.do?type=${requestScope.type }&dn=VehicleMortgage&qn=list&pagesize=${requestScope.pagesize}&pagenow=${requestScope.pagenow-1}" class="btn btn-default">«</a>						
							<%				    	
					         }						
							 if(pagenow1>=1&&pagenow1<totalpage1){
							%>
							<a href="${pageContext.request.contextPath }/litigationManagementController/mortgageRecord.do?type=${requestScope.type }&dn=VehicleMortgage&qn=list&pagesize=${requestScope.pagesize}&pagenow=${requestScope.pagenow+1}" class="btn btn-default">»</a>
	                        <%
	                        }
	                        %>
                        </li>
						</ul>
					   </div>	
			</div>
		</div>
		<div id="main_list" class="admin-content box">
			<!-- 数据载入中 请在搜索，筛选，载入的时候显示 放在.box里 -->
			<div class="overlay" style="display:none;">
				<i class="fa fa-refresh fa-spin"></i>
			</div>
			<!-- 数据载入中结束 -->
			<table class="table table-bordered table-hover">
				<tbody>	
					<tr>
						<th style="width:3%" class="text-center hidden-xs"><input class="check_all" type="checkbox"></th>
						<th class="text-center hidden-xs">订单编号</th>
						<th class="text-center">客户姓名</th>
						<th class="text-center">身份证号码</th>
						<th class="text-center">贷款银行</th>
						<th class="text-center">车辆类型</th>
						<th class="text-center">业务员</th>
						<th class="text-center">业务团队</th>
						<th class="text-center">车辆号牌</th>
						<th class="text-center">申请单状态</th>
						<th class="text-center">操作</th>
					</tr>
					
					<c:forEach var="item" items="${untreated}">
					
						<tr>
						<td class="text-center hidden-xs">
						<input name="delid"  type="checkbox">
						</td>
						<td class="text-center hidden-xs">
							${item.gems_code }
						</td>
						<td class="text-center">
							<span class="s-font-blue">
								${item.c_name }
							</span>
						</td>
						<td class="text-center">
							<span class="s-font-blue">
								${item.c_cardno }
							</span>
						</td>
						<td class="text-center">
							${item.y_name }
						</td>
						<td class="text-center">
							<c:if test="${item.cars_type == '1'}">
								新车
							</c:if>
							<c:if test="${item.cars_type == '2'}">
								二手车
							</c:if>
						</td>
						<td class="text-center">
							${pd.name }
						</td>
						<td class="text-center">
							${item.a_name }
						</td>
						<td class="text-center">
							<c:if test="${empty pd.c_carno}">
								无此条数据
							</c:if>
							<c:if test="${!empty pd.c_carno}">
								${pd.c_carno }
							</c:if>
						</td>
						<td class="text-center">
							<c:if test="${type == 'not'}">
								待处理
							</c:if>
							<c:if test="${type == 'already'}">
								已处理
							</c:if>
						</td>
						<td class="text-center">
							<span id="" class="label label-danger">${pd.opname }正在操作</span>
							<a href="">
								<i class="fa fa-search-plus"></i>
							</a>
							<a href="">
								<i class="fa fa-hand-paper-o"></i>
							</a>
						</td>
						
					</tr>
					
					</c:forEach>
					
				</tbody>
			</table>
		</div>
		
		<div class="foot-page">
			<c:if test="${requestScope.totalpage ge '1' }">
				<ul class="pagination no-margin">
				       <c:if test="${requestScope.pagenow ne '1' }">
				        <li><a href="${pageContext.request.contextPath }/litigationManagementController/mortgageRecord.do?type=${requestScope.type }&dn=VehicleMortgage&qn=list&cn=${requestScope.cn }&selectMsg=${requestScope.selectMsg}&pagesize=${requestScope.pagesize}&pagenow=${requestScope.pagenow-1}&fsid=${requestScope.fsid }" aria-label="Next"><span aria-hidden="true">«</span></a></li>
				       </c:if>
					   <%
				       int pagenow=Integer.parseInt(request.getAttribute("pagenow").toString());
				       int totalpage=Integer.parseInt(request.getAttribute("totalpage").toString());
				       int i=5; 
				       int h=1;
				    	 if(totalpage>=5){
				    		  if((pagenow-1)%4==0){
				    			 h=pagenow;
				    			 i=pagenow+4;
				    		  }else{
				    			 h=4*(pagenow-1-((pagenow-1)%4))/4+1;
				    			 i=h+4;
				    		  }				    		  
				    	  }else{
				    		i=totalpage;
				    	  } 
				       for(int j=h;j<i+1;j++){				    	   				    	   
				       if(j==pagenow){
				       %>
					   <li id="l<%=j %>" class="active">
					   <a id="a<%=j %>" href="${pageContext.request.contextPath }/litigationManagementController/mortgageRecord.do?type=${requestScope.type }&dn=VehicleMortgage&qn=list&cn=${requestScope.cn }&selectMsg=${requestScope.selectMsg}&pagesize=${requestScope.pagesize}&pagenow=<%=j %>&fsid=${requestScope.fsid }">
					   <%=j %>
					   </a>
					   </li>
					   <%
				       }else{
					   %>
	                   <li id="l<%=j %>" >
					   <a id="a<%=j %>" href="${pageContext.request.contextPath }/litigationManagementController/mortgageRecord.do?type=${requestScope.type }&dn=VehicleMortgage&qn=list&cn=${requestScope.cn }&selectMsg=${requestScope.selectMsg}&pagesize=${requestScope.pagesize}&pagenow=<%=j %>&fsid=${requestScope.fsid }">
					   <%=j %>					   
					   </a>
					   </li>					   
		               <%
				       }
				       if(j>=totalpage){
					    	  j=i+1; 
					   }
				       }				
		               %>
					   <c:if test="${requestScope.pagenow lt requestScope.totalpage}">
			               <c:if test="${requestScope.totalpage gt 5}">
						  <li><a href="${pageContext.request.contextPath }/litigationManagementController/mortgageRecord.do?type=${requestScope.type }&dn=VehicleMortgage&qn=list&cn=${requestScope.cn }&selectMsg=${requestScope.selectMsg}&pagesize=${requestScope.pagesize}&pagenow=${requestScope.pagenow+1}&fsid=${requestScope.fsid }" aria-label="Next"><span aria-hidden="true">»</span></a></li>  
						   </c:if>
					   </c:if>
					     				
					   </ul>
			</c:if>		   
				<div class="page-num">共${requestScope.totalsize}个 分${requestScope.totalpage}页显示</div>
		</div>
		
		</div>
	</section>
	<!-- /.content -->
	
</div>


