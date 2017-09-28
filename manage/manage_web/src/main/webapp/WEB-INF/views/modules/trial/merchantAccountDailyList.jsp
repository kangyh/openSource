<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>试算平衡-账户日汇总管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
	<style type="text/css">

	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/trial/merchantAccountDaily/">试算平衡-账户日汇总列表</a></li>
		<shiro:hasPermission name="trial:merchantAccountDaily:edit"><li><a href="${ctx}/trial/merchantAccountDaily/form">试算平衡-账户日汇总添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="merchantAccountDaily" action="${ctx}/trial/merchantAccountDaily/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">

			<li><label>账号ID：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>商户ID：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>日期：</label>
				<form:input path="accountDate" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr >
			<th rowspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">会计日期</th>
			<th rowspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">商户账号ID</th>
			<th rowspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">商户ID</th>
			<th colspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">期初余额</th>
			<th colspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">发生额</th>
			<th colspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">期末余额</th>
			<th rowspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">账户科目</th>
		</tr>
		<tr>
			<th style="text-align: center;vertical-align: middle;color: #46A3FF;">借方</th>
			<th style="text-align: center;vertical-align: middle;color: #46A3FF;">贷方</th>
			<th style="text-align: center;vertical-align: middle;color: #46A3FF;">借方</th>
			<th style="text-align: center;vertical-align: middle;color: #46A3FF;">贷方</th>
			<th style="text-align: center;vertical-align: middle;color: #46A3FF;">借方</th>
			<th style="text-align: center;vertical-align: middle;color: #46A3FF;">贷方</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchantAccountDaily">
			<tr>
				<%--<td><a href="${ctx}/trial/merchantAccountDaily/form?id=${merchantAccountDaily.id}">--%>
					<%--${merchantAccountDaily.id}--%>
				<%--</a></td>--%>
				<td style="text-align: center;">
							${merchantAccountDaily.accountDate}
				</td>
				<td style="text-align: center;">
					${merchantAccountDaily.accountId}
				</td>
				<td style="text-align: center;">
					${merchantAccountDaily.merchantId}
				</td>
				<td style="text-align: center;">
					<fmt:formatNumber value="${merchantAccountDaily.debitBeginningBalances}" pattern="￥#,##0.00" />
				</td>
				<td style="text-align: center;">
					<fmt:formatNumber value="${merchantAccountDaily.creditBeginningBalances}" pattern="￥#,##0.00" />
				</td>
				<td style="text-align: center;">
					<fmt:formatNumber value="${merchantAccountDaily.debitCurrentAmount}" pattern="￥#,##0.00" />
				</td>
				<td style="text-align: center;">
					<fmt:formatNumber value="${merchantAccountDaily.creditCurrentAmount}" pattern="￥#,##0.00" />
				</td>
				<td style="text-align: center;">
					<fmt:formatNumber value="${merchantAccountDaily.debitEndingBalances}" pattern="￥#,##0.00" />
				</td>
				<td style="text-align: center;">
					<fmt:formatNumber value="${merchantAccountDaily.creditEndingBalances}" pattern="￥#,##0.00" />
				</td>
				<td style="text-align: center;">
					${merchantAccountDaily.accountTitle}
				</td>
				<!-- 
				<td style="text-align: center;">
					<fmt:formatDate value="${merchantAccountDaily.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td style="text-align: center;">
					<fmt:formatDate value="${merchantAccountDaily.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				 -->
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>