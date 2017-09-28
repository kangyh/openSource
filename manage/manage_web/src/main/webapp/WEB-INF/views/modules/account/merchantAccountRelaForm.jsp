<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>账务关联账户管理</title>
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
		<li><a href="${ctx}/account/merchantAccountRela/">账务关联账户列表</a></li>
		<li class="active"><a href="${ctx}/account/merchantAccountRela/form?id=${merchantAccountRela.relaId}">账务关联账户<shiro:hasPermission name="account:merchantAccountRela:edit">${not empty merchantAccountRela.relaId?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="account:merchantAccountRela:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="merchantAccountRela" action="${ctx}/account/merchantAccountRela/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<c:set var="M_DISABLED" value="${not empty dispathName}"></c:set>
		<div class="control-group">
			<label class="control-label">交易类型：</label>
			<div class="controls">
				<form:select path="transType" class="input-xlarge " disabled="${M_DISABLED}" >
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('TransType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">记账标识：</label>
			<div class="controls">
				<form:input path="transSubType" htmlEscape="false" maxlength="6" class="input-xlarge required" disabled="${M_DISABLED}"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">账户类型：</label>
			<div class="controls">
				<form:select path="accountType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('InternalAccountType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">余额方向：</label>
			<div class="controls">
				<form:select path="balanceDirection" class="input-xlarge ">
					<%--<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
					<form:option value="C" label="贷"/>
					<form:option value="D" label="借"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">余额变动类型：</label>
			<div class="controls">
				<form:select path="balanceChangeType" class="input-xlarge ">
					<%--<form:option value="" label=""/>--%>
					<form:options items="${fns:getDictList('BalanceChangeType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">启用状态：</label>
			<div class="controls">
				<form:select path="isEnable" class="input-xlarge ">
					<form:option value="Y" label="启用"/>
					<form:option value="N" label="禁用"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">记账顺序：</label>
			<div class="controls">
				<form:input path="sequence" htmlEscape="false" maxlength="3" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="account:merchantAccountRela:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>