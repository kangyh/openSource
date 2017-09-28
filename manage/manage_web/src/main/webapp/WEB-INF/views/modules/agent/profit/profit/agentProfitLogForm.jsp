<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分润申请管理</title>
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
	<%--<ul class="nav nav-tabs">--%>
		<%--<li><a href="${ctx}/profit/profit/agentProfitLog/">分润申请列表</a></li>--%>
		<%--<li class="active"><a href="${ctx}/profit/profit/agentProfitLog/form?id=${agentProfitLog.id}">分润申请<shiro:hasPermission name="profit:profit:agentProfitLog:edit">${not empty agentProfitLog.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="profit:profit:agentProfitLog:edit">查看</shiro:lacksPermission></a></li>--%>
	<%--</ul><br/>--%>
	<form:form id="inputForm" modelAttribute="agentProfitLog" action="${ctx}/profit/profit/agentProfitLog/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<div class="control-left">
			<label class="control-label">分润结算ID<%--（agent_profit表的id）--%>：</label>
			<div class="controls">
				<form:input path="agentProfitId" htmlEscape="false" maxlength="20" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
			</div>

	<div class="control-right">
			<label class="control-label">申请时间：</label>
			<div class="controls">
				<input name="applyTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${agentProfitLog.applyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
	</div>
		</div>
		<div class="control-group">
			<div class="control-left">
			<label class="control-label">申请人：</label>
			<div class="controls">
				<form:input path="agentUserId" htmlEscape="false" maxlength="10" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
			</div>
			<div class="control-right">
		<div class="control-group">
			<label class="control-label">发票信息：</label>
			<div class="controls">
				<form:input path="invoices" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
			<label class="control-label">快递信息：</label>
			<div class="controls">
				<form:input path="express" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
			</div>

			<div class="control-right">
			<label class="control-label">申请备注：</label>
			<div class="controls">
				<form:input path="applyRemark" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		</div>

		<div class="control-group">
			<div class="control-left">
			<label class="control-label">状态<%--（INICHK初审 RECHEK复审 REJECT审核拒绝 PROFIT分润中 FINISH已完成）--%>：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge required">
					<form:option value="" label="全部"/>
					<form:options items="${agentProfitStatusList}" itemLabel="value" itemValue="name" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
			</div>

			<div class="control-right">
			<label class="control-label">备注（可填拒绝理由）：</label>
			<div class="controls">
				<form:input path="remark" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		</div>
		<div class="control-group">
			<label class="control-label">分润金额：</label>
			<div class="controls">
				<form:input path="profitAmount" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="profit:profit:agentProfitLog:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>