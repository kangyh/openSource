<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/modules/sys/validation.jsp"%>
<html>
<head>
	<title>商家法务审核管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/validateNum.js"></script>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        function search(){
			var flag = validatePreventInject($("#companyName").val(),"商户公司名称输入不合法!");
			var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})$/;
			var email = $("#loginName").val();
			if(null != email && email != ""){
				if(!reg.test(email)){
					$("#messageBox").text("商户账号输入不合法!");
					return false;
				}
			}
			return flag;
		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/merchant/merchantLegal/">法务审核列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="merchant" action="${ctx}/merchant/merchantLegal/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户编码：</label>
				<form:input path="userId" htmlEscape="false" maxlength="10" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}
					else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label style="width:100px">商户公司名称：</label>
				<form:input path="companyName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label style="width:100px">商户账号：</label>
				<form:input id="loginName" path="loginName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>审核状态：</label>
				<form:select id="legalAuditStatus" path="legalAuditStatus" class="input-xlarge">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getEnumList('routeStatus')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>创建时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${merchant.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,maxDate:'#F{$dp.$D(\'endCreateTime\')}'});"/> -
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${merchant.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,minDate:'#F{$dp.$D(\'beginCreateTime\')}'});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" onclick="return search()" value="查询"/></li>
			<li class="clearfix"></li>
			<div id="messageBox" style="font-size: 15px; color: red;"></div>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户编码</th>
				<th>商户账号</th>
				<th>商户公司名称</th>
				<th>法人代表姓名</th>
				<th>单位类型</th>
				<th>创建时间</th>
				<th>审核状态</th>
				<shiro:hasPermission name="merchant:merchantLegal:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchant">
			<tr>
				<td>${merchant.userId}</td>
				<td>${merchant.loginName}</td>
				<td>${merchant.companyName}</td>
				<td>${merchant.legalRepresentative}</td>
				<td>${merchant.type}</td>
				<td><fmt:formatDate value="${merchant.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${merchant.legalAuditStatus}</td>
				<shiro:hasPermission name="merchant:merchantLegal:edit"><td>
    				<a href="${ctx}/merchant/merchantLegal/detail?id=${merchant.userId}">详情</a>
    				<c:if test="${merchant.legalAuditStatus == '待审核'}">
						<a style="cursor:pointer;" class="checkPass"   value-url="${ctx}/merchant/merchantLegal/form?id=${merchant.userId}">审核</a>
    				</c:if>
					<%-- <a href="${ctx}/merchant/merchantLegal/delete?id=${merchant.userId}" onclick="return confirmx('确认要删除该merchant吗？', this.href)">删除</a> --%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>