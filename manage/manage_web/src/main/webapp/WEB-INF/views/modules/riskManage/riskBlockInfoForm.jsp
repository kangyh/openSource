<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>阻断风险操作表管理</title>
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
		
		//验证规则Id
		function CheckRuleId(){
			var ruleId = $("#ruleId").val();
			if(ruleId !="" && isNaN(ruleId)){
				$("#messageBox").text("规则Id必须为数字!");
				$("#ruleId").val("");
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/risk/riskBlockInfo/">阻断风险操作表列表</a></li>
		<li class="active"><a href="${ctx}/risk/riskBlockInfo/form?id=${riskBlockInfo.id}">阻断风险操作表<shiro:hasPermission name="risk:riskBlockInfo:edit">${not empty riskBlockInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="risk:risk:riskBlockInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="riskBlockInfo" action="${ctx}/risk/riskBlockInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">阻断类型：</label>
			<div class="controls">
				<form:select id="blockType" path="blockType" class="input-xlarge">
               		<form:option value="" label="请选择"/>
					<c:forEach items="${actionList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规则ID：</label>
			<div class="controls">
				<form:input path="ruleId" id="ruleId" onchange="CheckRuleId()" htmlEscape="false" maxlength="100" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">送上报文信息：</label>
			<div class="controls">
				<form:input path="fileds" htmlEscape="false" maxlength="2000" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">监控对象：</label>
			<div class="controls">
				<form:select id="monitorObject" path="monitorObject" class="input-xlarge">
               		<form:option value="" label="请选择"/>
					<c:forEach items="${monitorObjectList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务类型：</label>
			<div class="controls">
				<form:select id="buziType" path="buziType" class="input-xlarge">
               		<form:option value="" label="请选择"/>
					<c:forEach items="${regLoginTypeList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="risk:riskBlockInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
</body>
</html>