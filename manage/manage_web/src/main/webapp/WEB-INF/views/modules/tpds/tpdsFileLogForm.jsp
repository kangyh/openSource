<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商户台账信息</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
	
		//动态口令验证
		$(document).ready(function() {
			$(".checkPass").on("click",function(){
				var url = $(this).attr("value-url");
				parent.showDynamicPa();
				parent.enterDynamicPa(url);
			})
		});
		//验证搜索条件
		var validateFrom = {
			validate: function(form){
				
				var merchantNo = $("#merchantNo").val();
				if(isNaN(merchantNo)){
					$("#messageBox").text("商户编码 请输入数字类型");
					$("#merchantNo").val("");
					return ;
				}
			
				form.submit();
			}
		}

		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//搜索
		function onSubmit(){
			$("#pageNo").val(1);
			validateFrom.validate($("#searchForm"));
		}
		
		//清空
		function onClear(){
			$("#formBtn").submit();
		}
		
	</script>
</head>
<body>
     <ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/tpds/tpdsFileLog/">对账文件日记列表</a></li>
	</ul>
	<form action="${ctx}/tpds/tpdsFileLog/" method="post" id="formBtn"></form> 
	<form:form id="searchForm" modelAttribute="tpdsFileLog" action="${ctx}/tpds/tpdsFileLog/" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		    <li><label>商户编号：</label>
				<form:input path="merchantNo" id="merchantNo"  htmlEscape="false" maxlength="10"  class="input-medium required" placeholder="请输入商户编号"/>
			</li>
	     <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
	     <li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
		</ul>  
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>商户编码</th>
			<th>源文件IP</th>
			<th>上传文件IP</th>
			<th>对账文件名</th>
			<th>存放目录</th>
			<th>操作人</th>
			<th>操作时间</th>
			<th>补发通知</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tpdsFileLog" varStatus="i">
				<tr>
					<td>${tpdsFileLog.merchantNo}</td>
					<td>${tpdsFileLog.checkFileFrom}</td>
					<td>${tpdsFileLog.checkFileWhere}</td>
					<td>${tpdsFileLog.fileName}</td>
					 <td>${tpdsFileLog.fileDir}</td>
				    <td>${tpdsFileLog.operPerson}</td>
				    <td><fmt:formatDate value="${tpdsFileLog.operTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				    <td><a href="${ctx}/tpds/tpdsFileLog/advice?tpdsFileLogId=${tpdsFileLog.logId}&pageNo=${page.pageNo}">通知</a></td>
				</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>