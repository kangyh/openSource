<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>服务器日志管理</title>
<meta name="decorator" content="default"/>
<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
<style>
    #main {
        margin: 50px;
    }
</style>
<script type="text/javascript">
$(document).ready(function() {
	$("#inputForm").validate({
		submitHandler: function(form){
			
			var groupName=$('#groupName').val();
			var mark=$('#mark').val();
			
			var pattern = new RegExp("[`~!#$^@&*()=|{}':;',\\[\\]<>/?~！#￥……&*（）——|{}【】‘；：”“'。，、？]") 
			if(pattern.test(groupName)){
				$("#groupNameId").text("对账批次号输入不合法，请重新输入");
				return false;
			}else{
				$("#groupNameId").text("");
			}
			
			if(pattern.test(mark)){
				$("#markId").text("对账批次号输入不合法，请重新输入");
				return false;
			}else{
				$("#markId").text("");
			}
			
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
	<li class="active"><a>组信息${not empty monitorGroup.groupName?'修改':'添加' }</a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="monitorGroup" action="${ctx}/modules/monitorGroup/saveEntity" method="post" class="form-horizontal">
	
	<div class="control-group">
		<label class="control-label">分组名称：</label>
		<div class="controls">
			<form:input path="groupName" id="groupName"  value="${monitorGroup.groupName}" htmlEscape="false"  maxlength="10" class="required" placeholder="请输入分组名称,不超过10个数" style="width:200px;"/>
			<span class="help-inline"><font color="red">*</font> </span>
			<label id="groupNameId" for="typeId" class="info"></label>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">分组备注：</label>
		<div class="controls">
			<form:input path="mark" id="mark"  value="${monitorGroup.mark}" htmlEscape="false"  maxlength="10" class="required" placeholder="请输入分组备注,不超过10个数" style="width:200px;"/>
			<span class="help-inline"><font color="red">*</font> </span>
			<label id="markId" for="typeId" class="info"></label>
		</div>
	</div>
	
	<div class="form-actions">
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="提交申请"/>
		<input id="btnCancel" class="btn" type="button" value="返回" onclick="history.go(-1)" style="margin-left:50px"/>
		<input type="hidden" name="groupId" value="${monitorGroup.groupId}">
	</div>
</form:form>
</body>
</html>