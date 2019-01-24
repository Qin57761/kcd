<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<style>
		            	.flex-box{
		            		display: flex;
		            		flex-direction: column;
		            	}
		            	.flex-box div[class^='flex-row']{
		            		width: 100%;
		            	}
		            	.flex-box .flex-row{
		            		height: 35px;	
		            	}
		            	.flex-row-rhcen{
		            		display: flex;
		            		justify-content: flex-end;
		            		align-items: center;
		            	}
		            	em{
		            		cursor: pointer;
		            	}
		            	.flex-row-rhcen em{
		            		padding: 0 5px;
		            	}
		            	.flex-rowcen{
		            		flex: 1;
		            	}
		            	.text-primary em{
		            		display: block;
		            		font-size: 15px;
		            		line-height: 25px;
		            	}
	            		.text-primary .big-conte{
		            		background-color:#f7f7f7;
		            		height:auto!important; 
							height:100px; 
							display:none;
							min-height:100px;
		    				padding: 15px 0;
		    				margin: 15px 0;
		    				border-radius:10px;
		     			}
		     			.big-conte-row span{
		     				text-align: right;
		     				padding-right: 15px;
		     				line-height: 34px;
		     				width: 25%;
		     				float: left;
		     				
		     			}
		     			.big-conte-row input{
		     				float: left;
		     				width: 20%;
		     			}
		     			.big-conte-row{
		     				margin: 20px;
		     				height: 34px;
		     			}
		            </style>
		            <%
	request.setCharacterEncoding("utf-8");
%>
<div class="modal-body flex-box" style="height: auto; ">
<div class="flex-row flex-row-rhcen">
<em onclick="funUnfold()" class="text-muted">全部展开</em> 
<em onclick="funClose()" class="text-muted">全部收起</em> 
</div>
<div class="flex-rowcen">
			            	<ol>
<c:forEach var="erp15" items="${requestScope.erp15 }" varStatus="status">
<c:if test="${erp15.status eq '83'}">			            	
<li class="text-primary">
<em>${status.index+1 }.${erp15.taskname_name}：</em>
<div class="big-conte" style="display: none;">                    
<div style="float:left;margin-left:20px;width:260px;" >
<strong>开始时间：</strong><fmt:formatDate value="${erp15.dt_add}" pattern="yyyy-MM-dd HH:mm:ss" />
</div>
<div style="float:left;margin-left:20px;width:260px;" >
<strong>处理人：</strong>${erp15.gname1}
</div>         
</div>							  	
</li>
</c:if>

<c:if test="${erp15.status eq '96'}">	
<li class="text-primary">
<em>${status.index+1 }.${erp15.taskname_name}：</em>
<div class="big-conte" style="display: none;">  
<div style="float:left;margin-left:20px;width:260px;" class="ng-binding">
<strong>开始时间：</strong><fmt:formatDate value="${erp15.dt_add}" pattern="yyyy-MM-dd HH:mm:ss" />
</div>
<div style="float:left;margin-left:20px;width:260px;" class="ng-binding">
<strong>处理时间：</strong><fmt:formatDate value="${erp15.dt_edit}" pattern="yyyy-MM-dd HH:mm:ss" />
</div>
<div style="float:left;margin-left:20px;width:260px;" class="ng-binding">
<strong>处理人：</strong>${erp15.gname1}</div>
<strong style="margin-left:10px;"><i>处理信息：</i></strong><br>
<!-- ngIf: taskAct.pageName!='cudp'||'_cudp'.indexOf(taskAct.pageName)<=-1 -->
<!-- ngInclude: '/modules/'+taskAct.menuCode+'/'+taskAct.pageName+'.html' -->
<div class="task_margin ng-scope"  style="border:1px solid #ccc; border-radius: 10px;background-color:#F7F7F7; padding-top:10px;">
<form name="modalForm" class="form-horizontal ng-pristine ng-valid ng-scope">
   <!-- ngIf: notUseButton -->
   <div style="display:none;">
		 <textarea id="inObj_96">${erp15.result_1_value}</textarea>
	</div>
   <div class="form-group ng-scope">
		<label class="col-sm-2 control-label">业务类型</label>
		<div class="col-sm-2">
			<input disabled="disabled" id="96_ywlx" type="text" class="form-control ng-pristine ng-untouched ng-valid ng-not-empty ng-valid-required">
			<!-- <select id="96_ywlx" name="ywlx"  class="form-control form-inline hidden-xs">
				<option value="">***业务类型***</option>
				<option value="1">征信查询</option>
				<option value="2">征信通融</option>
				<option value="3">车辆评估</option>
				<option value="4">银行电审</option>
				<option value="5">开卡申请</option>
				<option value="6">视频面签</option>
				<option value="7">跨区域业务审批</option>
				<option value="8">汽车贷款</option>
				<option value="9">内审通融</option>
				<option value="10">资金分配</option>
				<option value="11">银行贷款申请</option>
				<option value="12">公司归档</option>
				<option value="13">抵押归档</option>
				<option value="14">业务信息修改</option>
				<option value="15">退单退费</option>
			</select> -->
		</div>
	</div><!-- end ngIf: notUseButton -->
	<!-- ngIf: !notUseButton -->
	<!-- ngIf: !notUseButton -->
	<!-- ngIf: task.tranCode&&!notUseButton -->
	<div class="form-group">
		<label class="col-sm-2 control-label">修改内容备注</label>
		<div class="col-sm-8">
			<textarea id="96_xgbz" class="form-control ng-pristine ng-untouched ng-valid ng-empty" type="text" ></textarea>
		</div>
	</div>
	<!-- ngIf: currentUser.orgType=='ALAN' -->
	<div class="ng-scope">
		<!-- <label class="col-sm-2 control-label">上传相关材料:</label>								
	     	<div style="overflow: hidden;margin-left: 7%;">
	     	ngRepeat: img in task.filepathlist
			</div> -->
			<div class="clear"></div> 
	<!-- ngIf: (!notUseButton&&rootData.taskDefKey == 'updateLoanField_updateLoan_operation1')||(notUseButton&&taskAct.activityId=='updateLoanField_updateLoan_operation1') -->
	<div class="form-group">
		<label class="col-sm-2 control-label">修改原因备注</label>
		<div class="col-sm-8">
			<textarea class="form-control ng-pristine ng-untouched ng-valid ng-empty" type="text">${erp15.result_1_msg}</textarea>
		</div>
	</div>
	</div><!-- end ngIf: currentUser.orgType=='ALAN' -->
	<!-- ngIf: !notUseButton -->
	<script>
		// 69 处理 json ,并给相应的对象赋值
		var objS_96 = JSON.parse($("#inObj_96").val()); //由JSON字符串转换为JSON对象
		document.getElementById("96_ywlx").value = objS_96['96_ywlx'];
		document.getElementById("96_xgbz").value = objS_96['96_xgbz'];
	</script>
</form>
</div>                                             
</div>									  	
</li>
</c:if>

<c:if test="${erp15.status eq '84'}">
<li class="text-primary">
<em>${status.index+1 }.${erp15.taskname_name }：</em>
<div class="big-conte" style="display:none;">  
<div style="float:left;margin-left:20px;width:260px;" class="ng-binding">
<strong>开始时间：</strong><fmt:formatDate value="${erp15.dt_add}" pattern="yyyy-MM-dd HH:mm:ss" />
</div>
<div style="float:left;margin-left:20px;width:260px;" class="ng-binding">
<strong>处理时间：</strong><fmt:formatDate value="${erp15.dt_edit}" pattern="yyyy-MM-dd HH:mm:ss" />
</div>
<div style="float:left;margin-left:20px;width:260px;" class="ng-binding">
<strong>处理人：</strong>${erp15.gname1}</div>
<strong style="margin-left:10px;"><i>处理信息：</i></strong><br>
<div class="task_margin ng-scope"  style="border:1px solid #ccc; border-radius: 10px;background-color:#F7F7F7; padding-top:10px;">
<form name="modalForm" class="form-horizontal ng-pristine ng-valid ng-scope">
   <!-- ngIf: notUseButton -->
   <div class="form-group ng-scope">
		<label class="col-sm-2 control-label">审核结果</label>
		<div class="col-sm-6">
			<input type="radio" value="1" ${erp15.result_1_code eq '1'?"checked='checked'":'' }  class="ng-pristine ng-untouched ng-valid ng-not-empty" >通过
	            &nbsp;&nbsp;&nbsp;&nbsp;
	        <input type="radio" value="2" ${erp15.result_1_code eq '2'?"checked='checked'":'' } class="ng-pristine ng-untouched ng-valid ng-not-empty" >不通过
		</div>
	</div><!-- end ngIf: notUseButton -->
	<!-- ngIf: !notUseButton -->
	<!-- ngIf: !notUseButton -->

	
	<!-- ngIf: task.tranCode&&!notUseButton -->
	<div class="form-group">
		<label class="col-sm-2 control-label">备注</label>
		<div class="col-sm-8">
			<textarea class="form-control ng-pristine ng-untouched ng-valid ng-not-empty" type="text">${erp15.result_1_msg}</textarea>
		</div>
	</div>
	
	<!-- ngIf: currentUser.orgType=='ALAN' -->
	<div class="ng-scope">
	
		<label class="col-sm-2 control-label">相关材料:</label>								
	     	<div style="overflow: hidden;margin-left: 7%;">
	     	<!-- ngRepeat: img in task.filepathlist -->
			</div>
			<div class="clear"></div> 
			<br>
			<br>
	<!-- ngIf: (!notUseButton&&rootData.taskDefKey == 'updateLoanField_updateLoan_operation1')||(notUseButton&&taskAct.activityId=='updateLoanField_updateLoan_operation1') -->
		<!-- <div class="form-group">
			<label class="col-sm-2 control-label">修改原因备注</label>
			<div class="col-sm-8">
				<textarea class="form-control ng-pristine ng-untouched ng-valid ng-empty" type="text"></textarea>
			</div>
		</div> -->
	</div><!-- end ngIf: currentUser.orgType=='ALAN' -->
	<!-- ngIf: !notUseButton -->
</form>
</div>
</div>   							  	
</li>
</c:if>


<c:if test="${erp15.status eq '85'}">
<li class="text-primary">
<em>${status.index+1 }.${erp15.taskname_name }：</em>
<div class="big-conte" style="display: none;">  
<div style="float:left;margin-left:20px;width:260px;" class="ng-binding">
<strong>开始时间：</strong><fmt:formatDate value="${erp15.dt_add}" pattern="yyyy-MM-dd HH:mm:ss" />
</div>
<div style="float:left;margin-left:20px;width:260px;" class="ng-binding">
<strong>处理时间：</strong><fmt:formatDate value="${erp15.dt_edit}" pattern="yyyy-MM-dd HH:mm:ss" />
</div>
<div style="float:left;margin-left:20px;width:260px;" class="ng-binding">
<strong>处理人：</strong>${erp15.gname1}</div>
<strong style="margin-left:10px;"><i>处理信息：</i></strong><br>
<!-- ngIf: taskAct.pageName!='cudp'||'_cudp'.indexOf(taskAct.pageName)<=-1 -->
<!-- ngInclude: '/modules/'+taskAct.menuCode+'/'+taskAct.pageName+'.html' -->
<div class="task_margin ng-scope"  style="border:1px solid #ccc; border-radius: 10px;background-color:#F7F7F7; padding-top:10px;">
<form name="modalForm" class="form-horizontal ng-pristine ng-valid ng-scope">
   <!-- ngIf: notUseButton -->
   <div class="form-group ng-scope">
		<label class="col-sm-2 control-label">审核结果</label>
		<div class="col-sm-6">
			<input type="radio" value="1" ${erp15.result_1_code eq '1'?"checked='checked'":'' } class="ng-pristine ng-untouched ng-valid ng-not-empty" name="810">通过
	            &nbsp;&nbsp;&nbsp;&nbsp;
	        <input type="radio" value="2" ${erp15.result_1_code eq '2'?"checked='checked'":'' }  class="ng-pristine ng-untouched ng-valid ng-not-empty" name="811">不通过
		</div>
	</div><!-- end ngIf: notUseButton -->
	<!-- ngIf: !notUseButton -->
	<!-- ngIf: !notUseButton -->

	
	<!-- ngIf: task.tranCode&&!notUseButton -->
	<div class="form-group">
		<label class="col-sm-2 control-label">备注</label>
		<div class="col-sm-8">
			<textarea class="form-control ng-pristine ng-untouched ng-valid ng-empty" type="text" >${erp15.result_1_msg}</textarea>
		</div>
	</div>
	
	<!-- ngIf: currentUser.orgType=='ALAN' --><div class="ng-scope">
	
		<label class="col-sm-2 control-label">相关材料:</label>								
	     	<div style="overflow: hidden;margin-left: 7%;">
	     	<!-- ngRepeat: img in task.filepathlist -->
			</div>
			<div class="clear"></div> 
			<br>
			<br>
	<!-- ngIf: (!notUseButton&&rootData.taskDefKey == 'updateLoanField_updateLoan_operation1')||(notUseButton&&taskAct.activityId=='updateLoanField_updateLoan_operation1') -->
	<!-- <div class="form-group ng-scope">
		<label class="col-sm-2 control-label">备注</label>
		<div class="col-sm-8">
			<textarea class="form-control ng-pristine ng-untouched ng-valid ng-not-empty" type="text"></textarea>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">修改原因备注</label>
		<div class="col-sm-8">
			<textarea class="form-control ng-pristine ng-untouched ng-valid ng-empty" type="text"></textarea>
		</div>
	</div> -->
	</div><!-- end ngIf: currentUser.orgType=='ALAN' -->
	<!-- ngIf: !notUseButton -->
</form>
</div>                                             
</div>									  	
</li>
</c:if>


		<c:choose>
			<c:when test="${erp15.status eq '86'}">
				<li class="text-primary"><em>${status.index+1 }.完成：</em>
					<div class="big-conte" style="display: none;">
						<div style="float: left; margin-left: 20px; width: 260px;">
							<strong>完成时间：</strong>
							<fmt:formatDate value="${erp15.dt_edit}"
								pattern="yyyy-MM-dd HH:mm:ss" />
						</div>
					</div></li>
			</c:when>
			<c:otherwise>
			    <c:if test="${status.last}">
				<jsp:include page="/kjs_icbc/model_son/starutil.jsp">
					<jsp:param value="${erp15.dt_edit }" name="sdate" />
					<jsp:param value="${requestScope.sh_name }" name="sname" />
					<jsp:param value="${status.index+2 }" name="snum" />
				</jsp:include>
				</c:if>
			</c:otherwise>
		</c:choose>


</c:forEach>

							</ol>
		            	</div>
		            	<script>
                       function showradio(id,value) {
		            	    	switch(value){
		            	    	  case "1":
		            	    		  $("#"+id+"2").removeAttr("checked");
		            	    		  $("#"+id+"3").removeAttr("checked");
		            	    	   break;
		            	    	  case "2":
		            	    		  $("#"+id+"3").removeAttr("checked");
		            	    		  $("#"+id+"1").removeAttr("checked");
		            	    	   break;
		            	    	  case "3":
		            	    		  $("#"+id+"1").removeAttr("checked");
		            	    		  $("#"+id+"2").removeAttr("checked");
		            	    	   break;
		            	    	  default:
		            	    	   break;
		            	    	 }
		            	    };

		            	
		            

		            		$(".text-primary em").click(function(){
		            			$(this).next(".big-conte").slideToggle();
		            		})
		            		//全部展开
		            		function funUnfold(){
		            			$(".big-conte").slideDown();
		            		}
		            		//全部关闭
		            		function funClose(){
		            			$(".big-conte").slideUp();
		            		}
		            	</script>
		            	<div class="flex-row flex-row-rhcen">
		            		<em onclick="funUnfold()" class="text-muted">全部展开</em> 
		            		<em onclick="funClose()" class="text-muted">全部收起</em> 
		            	</div>
		            </div>