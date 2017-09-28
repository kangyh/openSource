<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>账户管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/prom/promAccountInfo/">账户管理列表</a></li>
		<li class="active"><a href="${ctx}/prom/promAccountInfo/form?id=${promAccountInfo.id}">账户管理<shiro:hasPermission name="prom:promAccountInfo:edit">${not empty promAccountInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="prom:promAccountInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="promAccountInfo" action="${ctx}/prom/promAccountInfo/save" method="post" class="form-horizontal">
		<form:hidden path="accountId"/>
		<form:hidden path="merchantId"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">银行账号：</label>
			<div class="controls">
				<form:input path="bankNo" htmlEscape="false" maxlength="25" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行开户名：</label>
			<div class="controls">
				<form:input path="openName" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开户所在地：</label>
			<div class="controls">
				<form:input path="openAdress" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开户银行全称：</label>
			<div class="controls">
				<form:input path="openAllName" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开户支行全称：</label>
			<div class="controls">
				<form:input path="openBranchName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联行号：</label>
			<div class="controls">
				<form:input path="bankNum" htmlEscape="false" maxlength="30" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="prom:promAccountInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>