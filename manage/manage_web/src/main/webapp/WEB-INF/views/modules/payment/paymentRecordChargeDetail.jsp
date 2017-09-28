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
		<li class="active"><a href="javascript:void(0)">充值详情查看</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="chargeRecord" action="${ctx}/payment/paymentRecord/save" method="post" class="form-horizontal">
		<%--<form:hidden path="id"/>--%>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">交易类型：</label>
			<div class="controls">
				${fns:getDictLabel(paymentRecord.productCode, 'ProductCodeType', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易订单号：</label>
			<div class="controls">
				${paymentRecord.transNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户名称：</label>
			<div class="controls">
				${chargeRecord.accountName}
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">商户公司：</label>
			<div class="controls">
				${chargeRecord.merchantCompany}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户账号：</label>
			<div class="controls">
					${chargeRecord.merchantId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付类型：</label>
			<div class="controls">
				<td>
				<%--${fns:getDictLabel(paymentRecord.productCode, 'ProductCodeType', '')}--%>
						${fns:getDictLabel(paymentRecord.type, 'PaymentType', '')}
				</td>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通道合作方：</label>
			<div class="controls">
				<td>${paymentRecord.channelName}</td>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行名称：</label>
			<div class="controls">
				<td>${paymentRecord.bankName}</td>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行流水号：</label>
			<div class="controls">
				<td>${paymentRecord.bankSerialNo}</td>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${paymentRecord.payAmount}" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实际支付金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${paymentRecord.successAmount}" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续费：</label>
			<div class="controls">
-
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付时间：</label>
			<div class="controls">
					<fmt:formatDate value="${paymentRecord.payTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作IP：</label>
			<div class="controls">
			${chargeRecord.requestIp}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易状态：</label>
			<div class="controls">
				${fns:getDictLabel(paymentRecord.status, 'ChargeRecordStatus', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通知商户状态：</label>
			<div class="controls">
				-
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<fmt:formatDate value="${chargeRecord.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会计日期：</label>
			<div class="controls">
-
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>