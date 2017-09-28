<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>交易管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/payment/paymentRecord/">交易管理列表</a></li>
		<li class="active">提现详情查看</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="withdrawRecord" action="${ctx}/payment/paymentRecord/save" method="post" class="form-horizontal">
		<%--<form:hidden path="id"/>--%>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">提现ID：</label>
			<div class="controls">
					${withdrawRecord.withdrawId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户编码：</label>
			<div class="controls">
					${withdrawRecord.merchantLoginName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户账号：</label>
			<div class="controls">
				${withdrawRecord.merchantLoginName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提现状态：</label>
			<div class="controls">
					${fns:getDictLabel(withdrawRecord.status, 'WithdrawStatus', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提现金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${withdrawRecord.withdrawAmount}" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提现银行卡号：</label>
			<div class="controls">
			${fns:decrypt(withdrawRecord.bankcardNo,'1231231adasfsadff')}
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>