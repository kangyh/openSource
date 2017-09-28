<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>补账户管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script language="JavaScript" src="${ctxStatic}/common/validateNum.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		//验证搜索条件
		var validateFrom = {
			validate: function(form){
				form.submit();
			}
		}

		//搜索
		function onSubmit(){
			validateFrom.validate($("#searchForm"));
		}

		//清空
		function onClear(){

		}

		function onSupplementManualAccount(){
			$("#supplementManualAccountFrom").submit();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/account/accountQuery/">补账户管理列表</a></li>
	</ul>

	<shiro:hasPermission name="account:merchantAccount:splManualAccount">

	</shiro:hasPermission>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>商户编码</th>
			<th>账号</th>
			<th>商户待账户</th>
			<th>手动待结算账户</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${supplementAccountMap}" var="merchantAccount">
			<c:set var="SUP_ACCOUNT" value="${fn:split(merchantAccount.value,',')}"></c:set>
			<c:set var="SUP_ACCOUNT_LENGTH" value="${fn:length(SUP_ACCOUNT)}"></c:set>
			<tr>
				<td>${merchantAccount.key}</td>
				<c:forEach var="sup" items="${SUP_ACCOUNT}">
					<td>
						${sup}
					</td>
				</c:forEach>
				<c:if test="${SUP_ACCOUNT_LENGTH == 1}">
					<td>-</td><td>-</td>
				</c:if>
				<c:if test="${SUP_ACCOUNT_LENGTH == 2}">
					<td>-</td>
				</c:if>
			</tr>
		</c:forEach>
		<c:if test="${not empty supplementAccountMap}">
			<tr>
				<td colspan="4" align="left"><a href="${ctx}/account/accountQuery/onSupplementManualAccount">补账户</a></td>
			</tr>
		</c:if>
		</tbody>
	</table>
</body>
</html>