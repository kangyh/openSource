<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>账户管理管理</title>
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
		<li><a href="${ctx}/account/accountQuery/">账户管理列表</a></li>
		<li class="active"><a href="javascript:void(0)">账户详情查看</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="merchantAccount" action="${ctx}/account/merchantAccount/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">账号：</label>
			<div class="controls">
				${merchantAccount.accountId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户公司名称：</label>
			<div class="controls">
					${merchantAccount.accountName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">账户类型：</label>
			<div class="controls">
					${fns:getDictLabel(merchantAccount.type, 'MerchantAccountType', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">账户余额：</label>
			<div class="controls">
				<fmt:formatNumber value="${merchantAccount.balanceAmount}" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">可用金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${merchantAccount.balanceAvailableAmount}" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">冻结金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${merchantAccount.balanceFreezedAmount}" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">可提现金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${merchantAccount.balanceAvailableWithdrawAmount}" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				${fns:getDictLabel(merchantAccount.status, 'MerchantStatus', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<fmt:formatDate value="${merchantAccount.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>