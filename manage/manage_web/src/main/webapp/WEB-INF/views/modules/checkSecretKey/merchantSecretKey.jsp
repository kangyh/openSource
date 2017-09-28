<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>秘钥详情页</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="javascript:void(0);">秘钥详情</a></li>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>商户编码</th>
			<th>商户公司名称</th>
			<th>商户账号</th>
			<th>产品名称</th>
			<th>合同有效时间</th>
			<th>状态</th>
			<th>秘钥</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchantProductRate">
			<tr>
				<td>${merchantProductRate.merchantId}</td>
				<td>${merchantProductRate.merchantCompanyName}</td>
				<td>${merchantProductRate.merchantLoginName}</td>
				<td>${merchantProductRate.productName}</td>
				<td><fmt:formatDate value="${merchantProductRate.ruleBeginTime}" pattern="yyyy-MM-dd"/>
					<c:if test="${!empty merchantProductRate.ruleBeginTime}">到</c:if>
					<fmt:formatDate value="${merchantProductRate.ruleEndTime}" pattern="yyyy-MM-dd"/></td>
				<td>${merchantProductRate.status}</td>
				<td>${merchantProductRate.autographKey}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<input class="btn" id="btnCancel" onclick="history.go(-1)" type="button" value="返 回">
</body>
</html>