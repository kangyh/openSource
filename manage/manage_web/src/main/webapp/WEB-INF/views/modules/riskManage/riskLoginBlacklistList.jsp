<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商户注册黑名单管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
	//验证搜索条件
	var validateFrom = {
		validate: function(form){
			
			var bgTime = $("#beginOperEndTime").val();
			var endTime = $("#endOperEndTime").val();
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
			
			$("#searchForm").submit();
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
		<li class="active"><a href="${ctx}/risk/riskLoginBlacklist/">商户注册黑名单列表</a></li>
		<shiro:hasPermission name="risk:riskLoginBlacklist:edit"><li><a href="${ctx}/risk/riskLoginBlacklist/form">商户注册黑名单添加</a></li></shiro:hasPermission>
	</ul>
	
	<form action="${ctx}/risk/riskLoginBlacklist/" method="post" id="formBtn"></form> 
	
	<form:form id="searchForm" modelAttribute="riskLoginBlacklist" action="${ctx}/risk/riskLoginBlacklist/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>公司名称：</label>
				<form:input path="companyName"  htmlEscape="false" maxlength="50" class="input-medium required"/>
			</li>
			<li><label>营业执照编号：</label>
				<form:input path="buziCode"  htmlEscape="false" maxlength="50" class="input-medium required"/>
			</li>
		</ul>
		<ul class="ul-form">
			<li><label>法人姓名：</label>
				<form:input path="ownerName"  htmlEscape="false" maxlength="50" class="input-medium required"/>
			</li>
			<li><label>法人身份证号：</label>
				<form:input path="ownerId"  htmlEscape="false" maxlength="50" class="input-medium required"/>
			</li>
		      <li><label>创建日期：</label>
				<input id="beginOperEndTime" name="beginOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${riskLoginBlacklist.beginOperEndTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input id="endOperEndTime" name="endOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${riskLoginBlacklist.endOperEndTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			 </li> 
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
		</ul>
	</form:form>
	<div id="messageBox" style="font-size: 15px; color: red;"></div>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>公司名称</th>
				<th>营业执照编号</th>
				<th>法人姓名</th>
				<th>法人身份证号</th>
				<th>创建时间</th>
				<shiro:hasPermission name="risk:riskLoginBlacklist:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="riskLoginBlacklist">
			<tr>
				<td>${riskLoginBlacklist.companyName}</td>
				<td>${riskLoginBlacklist.buziCode}</td>
				<td>${riskLoginBlacklist.ownerName}</td>
				<td>${riskLoginBlacklist.ownerId}</td>
				<td>
					<fmt:formatDate value="${riskLoginBlacklist.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="risk:riskLoginBlacklist:edit"><td>
    				<a href="${ctx}/risk/riskLoginBlacklist/form?id=${riskLoginBlacklist.blackId}">修改</a>
					<a href="${ctx}/risk/riskLoginBlacklist/delete?id=${riskLoginBlacklist.blackId}" onclick="return confirmx('确认要删除该记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>