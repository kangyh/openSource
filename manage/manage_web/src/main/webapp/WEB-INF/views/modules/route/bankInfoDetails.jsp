<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/route/bankInfo/">银行列表</a></li>
		<li class="active"><a href="${ctx}/route/bankInfo/details?id=${bankInfo.id}">银行信息</a></li>
	</ul><br/>
	<form:form  modelAttribute="bankInfo"  method="post" class="form-horizontal">
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">银行代码：</label>
			<div class="controls">
				${bankInfo.bankNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行名称：</label>
			<div class="controls">
				${bankInfo.bankName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行简称：</label>
			<div class="controls">
				${bankInfo.bankCode}
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<fmt:formatDate value="${bankInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				${bankInfo.status}
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>