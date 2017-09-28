<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>协议代扣审批管理</title>
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
		<li><a href="${ctx}/payment/bankcardAuth/">限额设置</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="bankcardAuth" action="${ctx}/payment/bankcardAuth/limitAmount" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">商户名称：</label>
			<div class="controls">
					${bankcardAuth.merchantCompany}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">持卡人姓名：</label>
			<div class="controls">
					${bankcardAuth.bankcardOwnerName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">汇付宝协议号：</label>
			<div class="controls">
					${bankcardAuth.authId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">协议最大累计金额：</label>
			<div class="controls">
				<form:input path="protocolMaxAmount" htmlEscape="false" class="input-xlarge" />
			</div>
		</div>

        <input type="hidden" id="authId" name="authId" value="${bankcardAuth.authId}" />

		<div class="form-actions">
			<shiro:hasPermission name="payment:bankcardAuth:edit">
                <input class="btn btn-primary" type="submit" value="保 存" />&nbsp;
            </shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>