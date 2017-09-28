<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>账户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				rules: {
					remark: {
						required: true
					}
				},
				messages: {
					remark:"请添加备注。"
				},

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
		<li><a href="${ctx}/account/accountQuery/">账户管理列表</a></li>
		<li class="active"><a href="javascript:void(0)">冻结账户</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="merchantAccount" action="${ctx}/account/accountQuery/frezed" method="post" class="form-horizontal">
		<form:hidden path="accountId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">会员账户：</label>
			<div class="controls">
					${merchantAccount.accountId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会员名称：</label>
			<div class="controls">
					${merchantAccount.accountName}
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remark" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<%--<shiro:hasPermission name="account:merchantAccount:edit">--%>
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="确 定"/>&nbsp;
				<%--</shiro:hasPermission>--%>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>