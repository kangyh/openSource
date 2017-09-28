<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>行业范围新增</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			if($("#msg").val() != ""){
				parent.showThree();
				parent.changeThreeName($("#msg").val());
			}
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
		<li><a href="${ctx}/merchant/merchantIndustryBase?cache=1">行业范围维护</a></li>
		<li class="active"><a href="${ctx}/merchant/merchantIndustryBase/form?id=${merchantIndustryBase.id}">行业范围<shiro:hasPermission name="merchant:merchantIndustryBase:edit">${not empty merchantIndustryBase.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="merchant:merchantIndustryBase:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="merchantIndustryBase" action="${ctx}/merchant/merchantIndustryBase/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" id="msg" value = "${msg}" />
		<sys:message content="${message}"/>
		<form:hidden path="industryId" htmlEscape="false" maxlength="8" class="input-xlarge " value="1"/>
		<form:hidden path="industryName" htmlEscape="false" maxlength="50" class="input-xlarge " value="一般类商户"/>
		<form:hidden path="parentId" htmlEscape="false" maxlength="8" class="input-xlarge " value="1"/>
		<div class="control-group">
			<label class="control-label">行业大类：</label>
			<div class="controls">
				<form:input path="industryChildName" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户类别码：</label>
			<div class="controls">
				<form:input path="mcc" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">适用范围：</label>
			<div class="controls">
				<form:input path="industryDescribe" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="merchant:merchantIndustryBase:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>