<%@page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>登录异常</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
</head>
<body>
	<div class="container-fluid">
		<div class="page-header"><h1>登录异常，您与上次登录的电脑信息不符，如需变更，请与管理员联系!</h1></div>
		<div><a href="${ctx}/login" class="btn">返回登录页</a></div>
	</div>
</body>
</html>
