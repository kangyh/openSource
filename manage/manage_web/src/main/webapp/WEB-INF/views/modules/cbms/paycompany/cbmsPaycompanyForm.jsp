<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>支付机构设置管理</title>
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
		<%--<li><a href="${ctx}/cbms/cbmsPaycompany/">支付机构设置列表</a></li>
		<li class="active"><a href="${ctx}/cbms/cbmsPaycompany/form?id=${cbmsPaycompany.id}">支付机构设置<shiro:hasPermission name="cbms:cbmsPaycompany:edit">${not empty cbmsPaycompany.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cbms:cbmsPaycompany:edit">查看</shiro:lacksPermission></a></li>--%>
			<li class="active"><a href="${ctx}/cbms/cbmsPaycompany/">支付机构设置${cbmsPaycompany.companyId?'添加':'修改'}</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cbmsPaycompany" action="${ctx}/cbms/cbmsPaycompany/save" method="post" class="form-horizontal">
		<form:hidden path="companyId"/>
		<sys:message content="${message}"/>
		<form:hidden path="companyName"/>
		<form:hidden path="organizationCode"/>
		<form:hidden path="inputuserName"/>
		<form:hidden path="phone"/>
		<div class="control-group">
			<label class="control-label">申报企业的单一窗口注册编号：</label>
			<div class="controls">
				<form:input path="companyNo" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付企业的海关注册登记编号：</label>
			<div class="controls">
				<form:input path="companyCode" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付企业名称：</label>
			<div class="controls">
				<form:input path="companyCustomsName" htmlEscape="false" maxlength="500" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">统一社会信用代码：</label>
			<div class="controls">
				<form:input path="creditCode" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">法人名称：</label>
			<div class="controls">
				<form:input path="legalPerson" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">法人身份证：</label>
			<div class="controls">
				<form:input  path="legalCardId" htmlEscape="false" maxlength="18" class="input-xlarge  required" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">国检备案编号：</label>
			<div class="controls">
				<form:input path="inspectionNumber" htmlEscape="false" maxlength="10"  class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">办公地址：</label>
			<div class="controls">
				<form:input path="officeAddress" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="cbms:cbmsPaycompany:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="提交"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>