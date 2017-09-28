<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/modules/sys/validation.jsp"%>
<html>
<head>
	<title>merchant管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/validateNum.js"></script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="javascript:void(0);">内部商户列表</a></li>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户编码</th>
				<th>商户公司名称</th>
				<th>商户账号</th>
				<th>法人代表</th>
				<th>网址</th>
				<th>公司注册地址</th>
				<th>联系人</th>
				<shiro:hasPermission name="merchant:merchantRateNew:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchant">
			<tr>
				<td>${merchant.userId}</td>
				<td>${merchant.companyName}</td>
				<td>${merchant.email}</td>
				<td>${merchant.legalRepresentative}</td>
				<td>${merchant.website}</td>
				<td>${merchant.businessAddress}</td>
				<td>${merchant.contactor}</td>
				<shiro:hasPermission name="merchant:merchantRateNew:edit"><td>
    				<a href="${ctx}/merchant/checkSecretKey/detail?merchantId=${merchant.userId}">详情</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>