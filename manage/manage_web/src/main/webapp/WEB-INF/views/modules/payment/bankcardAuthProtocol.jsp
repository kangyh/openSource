<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>协议代扣审批管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
            $("#authPass").on("click", function () {
                $("#authRes").val("SUCCES");
                $("#inputForm").submit();
            });
            $("#authReject").on("click", function () {
                $("#authRes").val("REJECT");
                $("#inputForm").submit();
            });

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
		<li><a href="${ctx}/payment/bankcardAuth/">协议审核</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="bankcardAuth" action="${ctx}/payment/bankcardAuth/auth" method="post" class="form-horizontal">
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
			<label class="control-label">银行卡号：</label>
			<div class="controls">
					${fns:hiddenBankcardNo(bankcardAuth.bankcardNo)}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号：</label>
			<div class="controls">
					${fns:hiddenMobileNo(bankcardAuth.bankcardOwnerMobile)}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号：</label>
			<div class="controls">
					${fns:hiddenIdCardNo(bankcardAuth.bankcardOwnerIdcard)}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">协议有效期：</label>
			<div class="controls">
					${bankcardAuth.protocolExpireDate}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">协议扫描件：</label>
			<div class="controls">
				<a href="${imgPath}" target="_blank">查看附件</a>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审批意见：</label>
			<div class="controls">
				<form:input path="ext1" htmlEscape="false" maxlength="128" class="input-xlarge "/>
			</div>
		</div>

        <input type="hidden" id="authId" name="authId" value="${bankcardAuth.authId}"/>
        <input type="hidden" id="authRes" name="authRes" />

		<div class="form-actions">
			<shiro:hasPermission name="payment:bankcardAuth:edit">
                <input id="authPass" class="btn btn-primary" type="button" value="审核通过"/>&nbsp;
            </shiro:hasPermission>
			<shiro:hasPermission name="payment:bankcardAuth:edit">
                <input id="authReject" class="btn btn-primary" type="button" value="审核拒绝"/>&nbsp;
            </shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>