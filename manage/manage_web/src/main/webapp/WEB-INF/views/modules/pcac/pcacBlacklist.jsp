<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>黑名单信息</title>
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
		<li class="active"><a href="${ctx}/pcac/pcacBlacklist/">黑名单信息列表</a></li>
	</ul>
	<form action="${ctx}/pcac/pcacBlacklist/" method="post" id="formBtn"></form>
	<form:form id="searchForm" modelAttribute="pcacBlacklist" action="${ctx}/pcac/pcacBlacklist/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		<li><label class="control-label">风险信息等级：</label>
		    <form:select id="level" path="level" class="input-xlarge" >
				<form:option value="" label="-风险信息等级-"/>
				<c:forEach items="${levelList}" var="level">
					<form:option value="${level.value}" label="${level.name}"/>
				</c:forEach>
			</form:select>
		</li>
		<li><label>风险类型：</label>
			<form:select id="riskType" path="riskType" class="input-xlarge">
				<form:option value="" label="-风险类型-"/>
					<c:forEach items="${riskTypeList}" var="riskType">
						<form:option value="${riskType.value}" label="${riskType.name}"/>
					</c:forEach>
			</form:select> 
		</li>
		<li><label>有效性：</label>
			<form:select id="validStatus" path="validStatus" class="input-xlarge">
				<form:option value="" label="有效性"/>
					<c:forEach items="${validStatusList}" var="validStatu">
						<form:option value="${validStatu.value}" label="${validStatu.name}"/>
					</c:forEach>
			</form:select>
		</li>
		</ul>
			
		<ul class="ul-form">
			<li><label>商户名称：</label>
				<form:input path="regName"  htmlEscape="false" maxlength="26" style="width:256px;" class="input-medium required"/>
		    </li>
			<li><label>推送日期：</label>
					<input id="beginCheckTime" name="beginCheckTime" type="text" readonly="readonly"   class="input-medium Wdate"
					value="<fmt:formatDate value="${pcacBlacklist.beginCheckTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endCheckTime" name="endCheckTime" type="text" readonly="readonly"  class="input-medium Wdate"
					value="<fmt:formatDate value="${pcacBlacklist.endCheckTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清除"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" onclick="onExport()" value="导出"/></li>
		</ul>
<!-- 		<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" onclick="window.history.go(-1);" value="返回"/></li>
 --> 		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>序号</th>
			<th>商户名称</th>
			<th>商户简称</th>
			<th>法人证件类型</th>
			<th>推送日期</th>
			<th>法人证件号码</th>
			<th>法定代表人姓名</th>
			<th>法定代表人证件类型</th>
			<th>法定代表人（负责人）证件号码</th>
			<th>风险信息等级</th>
			<th>风险类型</th>
			<th>有效期</th>
			<th>有效性</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="pcacBlacklist" varStatus="i">
			<tr>
				<td>${i.count}</td>
				<td>${pcacBlacklist.regName}</td>
				<td>${pcacBlacklist.cusName}</td>
				<td>${pcacBlacklist.docType}</td>
				<td><fmt:formatDate value="${pcacBlacklist.createtime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${pcacBlacklist.docCode}</td>
				<td>${pcacBlacklist.docName}</td>
				<td>${pcacBlacklist.legDocType}</td>
				<td>${pcacBlacklist.legDocCode}</td>
				<td>${pcacBlacklist.level}</td>
				<td>${pcacBlacklist.riskType}</td>
				<td><fmt:formatDate value="${pcacBlacklist.validDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${pcacBlacklist.validStatus}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
</body>
</html>