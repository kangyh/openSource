<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>风控ip库管理</title>
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
		
		function checkIp(){
			var ip = $("#ip").val();
			$.ajax({
	            type: "GET",
	            url: "${ctx}/risk/riskIpbase/checkIp",
	            cache:false,
	            data:{
	            	"ip":ip
				},
	            success: function(data){
	                if(1!=data){
	                	$("#ipId").text("该Ip已存在");
	                	$("#ip").val("");
	                }else{
	                	$("#ipId").text("");
	                }
	            }
	        });
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/risk/riskIpbase/">风控ip库列表</a></li>
		<li class="active"><a href="${ctx}/risk/riskIpbase/form?id=${riskIpbase.id}">风控ip库<shiro:hasPermission name="risk:riskIpbase:edit">${not empty riskIpbase.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="risk:riskIpbase:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="riskIpbase" action="${ctx}/risk/riskIpbase/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<div class="control-group">
			<label class="control-label">IP：</label>
			<div class="controls">
				<form:input id="ip" onchange="checkIp()" path="ip" htmlEscape="false" maxlength="50" class="required"/>
				<span id="ipId" style="color:red;" class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">国家：</label>
			<div class="controls">
				<form:input path="country" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">省份：</label>
			<div class="controls">
				<form:input path="province" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">城市：</label>
			<div class="controls">
				<form:input path="city" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">区县：</label>
			<div class="controls">
				<form:input path="region" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">省份ID：</label>
			<div class="controls">
				<form:input path="provinceId" htmlEscape="false" maxlength="18" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:input path="remark" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="risk:riskIpbase:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>