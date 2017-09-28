<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商户信息对比协查信息</title>
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
				
				var bgTime = $("#beginCheckTime").val();
				var endTime = $("#endCheckTime").val();
				if(bgTime=="" && endTime!="" || bgTime!="" && endTime=="" ){
					$("#messageBox").text("请正确设置查询时间范围!");
					return ;
				}
				if( bgTime!="" && endTime!=""){
					if(compareDate(convertDateToJoinStr(bgTime),
									convertDateToJoinStr(endTime)) > 0){
						$("#messageBox").text("起始日期不能大于结束日期!");
						return ;
					}
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
		
		//文件导出
		function onExport(){
	        var actionURL = $("#searchForm").attr("action");
	        $("#searchForm").attr("action",$("#searchForm").attr("action")+"export");
			validateFrom.validate($("#searchForm"));
			$("#searchForm").attr("action",actionURL);
	        
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/pcac/merchantInvestigation/">商户信息对比协查列表</a></li>
	</ul>
	<form action="${ctx}/pcac/merchantInvestigation/" method="post" id="formBtn"></form>
	<form:form id="searchForm" modelAttribute="merchantInvestigation" action="${ctx}/pcac/merchantInvestigation/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户名称：</label>
				<form:input path="name"  htmlEscape="false" maxlength="26" style="width:256px;" class="input-medium required"/>
		    </li>
			<li><label>推送日期：</label>
					<input id="beginCheckTime" name="beginCheckTime" type="text" readonly="readonly"  class="input-medium Wdate"
					value="<fmt:formatDate value="${merchantInvestigation.beginCheckTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endCheckTime" name="endCheckTime" type="text" readonly="readonly"  class="input-medium Wdate"
					value="<fmt:formatDate value="${merchantInvestigation.endCheckTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			    <li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清除"/></li>
			    <li class="btns"><input id="btnExport" class="btn btn-primary" type="button" onclick="onExport()" value="导出"/></li>
		</ul>
<!-- 		<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" onclick="window.history.go(-1);" value="返回"/></li>
 -->		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>序号</th>
			<th>商户代码</th>
			<th>商户名称</th>
			<th>法定代表人(负责人)姓名</th>
			<th>对比</th>
			<th>商户代码</th>
			<th>商户名称</th>
			<th>法定代表人(负责人)姓名</th>
			<th>推送日期</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchantInvestigation" varStatus="i">
			<tr>
				<td>${i.count}</td>
				<td>${merchantInvestigation.code}</td>
				<td>${merchantInvestigation.name}</td>
				<td>${merchantInvestigation.legalName}</td>
				<td>--</td>
				<td>${merchantInvestigation.merchantCode}</td>
				<td>${merchantInvestigation.merchantName}</td>
				<td>${merchantInvestigation.merchantLegalName}</td>
				<td><fmt:formatDate value="${merchantInvestigation.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
</body>
</html>