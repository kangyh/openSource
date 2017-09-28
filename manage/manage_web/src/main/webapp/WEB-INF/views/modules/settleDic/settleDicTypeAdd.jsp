<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>字典类型添加</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#code").focus();
			if(null != $("#typeId").val() && undefined != $("#typeId").val() && "" != $("#typeId").val()){
				$("#text").attr("readOnly","true");
			}
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
		<li><a href="${ctx}/settleDic/settleDicTypeQuery?cache=1">字典类型列表</a></li>
		<li class="active"><a>字典类型<shiro:hasPermission name="settleDic:settleDicType:edit">${not empty settleDicType.typeId?'修改':'添加'}</shiro:hasPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="settleDicType" action="${ctx}/settleDic/settleDicTypeQuery/save" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<form:hidden path="typeId"/>
		<div class="control-group">
			<label class="control-label">字典类型编码:</label>
			<div class="controls">
				<form:input path="code" htmlEscape="false" maxlength="120" class="required input-xlarge"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">字典类型名称:</label>
			<div class="controls">
				<form:input path="text" htmlEscape="false" maxlength="20" class="required input-xlarge"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态:</label>
			<div class="controls">
               <form:select id="status" path="status" class="input-xlarge">
					<c:forEach items="${statusList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select> 
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="settleDic:settleDicType:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>