<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>风控商户黑名单管理</title>
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
		<li><a href="${ctx}/risk/riskLoginBlacklist/">风控商户黑名单列表</a></li>
		<li class="active"><a href="${ctx}/risk/riskLoginBlacklist/form?id=${riskLoginBlacklist.id}">风控商户黑名单列表<shiro:hasPermission name="risk:riskLoginBlacklist:edit">${not empty riskLoginBlacklist.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="risk:riskLoginBlacklist:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="riskLoginBlacklist" action="${ctx}/risk/riskLoginBlacklist/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<c:if test="${empty riskLoginBlacklist.id}">	
		<div class="control-group">
			<label class="control-label">公司名称：</label>
			<div class="controls">
				<form:input path="companyName" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">营业执照编号：</label>
			<div class="controls">
				<form:input path="buziCode" htmlEscape="false" maxlength="30" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">法人姓名：</label>
			<div class="controls">
				<form:input path="ownerName" htmlEscape="false" maxlength="20" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">法人身份证号：</label>
			<div class="controls">
				<form:input path="ownerId" htmlEscape="false" maxlength="25" class="required"/>
			</div>
		</div>
		</c:if>
		
		<c:if test="${not empty riskLoginBlacklist.id}">
		<div class="control-group">
			<label class="control-label">公司名称：</label>
			<div class="controls">
				<form:input path="companyName" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">营业执照编号：</label>
			<div class="controls">
				<form:input path="buziCode" htmlEscape="false" maxlength="30" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">法人姓名：</label>
			<div class="controls">
				<form:input path="ownerName" htmlEscape="false" maxlength="20" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">法人身份证号：</label>
			<div class="controls">
				<form:input path="ownerId" htmlEscape="false" maxlength="25" class="required"/>
			</div>
		</div>
		</c:if>
		<div class="form-actions">
			<shiro:hasPermission name="risk:riskLoginBlacklist:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
</body>
</html>