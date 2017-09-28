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
		<li class="active"><a href="javascript:void(0)">消费详情查看</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="gatewayRecord" action="${ctx}/payment/paymentRecord/save" method="post" class="form-horizontal">
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
					${paymentRecord.paymentId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户编码：</label>
			<div class="controls">
					${gatewayRecord.merchantId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户公司名称：</label>
			<div class="controls">
					${gatewayRecord.merchantCompany}
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">商户订单号：</label>
			<div class="controls">
					${gatewayRecord.merchantOrderNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付类型：</label>
			<div class="controls">
					${fns:getDictLabel(paymentRecord.type, 'PaymentType', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通道提供方：</label>
			<div class="controls">
				${paymentRecord.channelName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行名称：</label>
			<div class="controls">
				${paymentRecord.bankName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行流水号：</label>
			<div class="controls">
				${paymentRecord.bankSerialNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付金额：</label>
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
				<fmt:formatNumber value="${gatewayRecord.feeAmount}" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续费来源：</label>
			<div class="controls">
				-
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">操作IP：</label>
			<div class="controls">
				${gatewayRecord.requestIp}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易状态：</label>
			<div class="controls">
						${fns:getDictLabel(gatewayRecord.status, 'PaymentRecordStatus', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通知商户状态：</label>
			<div class="controls">
					-
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付完通知商户的URL：</label>
			<div class="controls">
					${gatewayRecord.notifyUrl}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付完返回到商户的URL：</label>
			<div class="controls">
					${gatewayRecord.callbackUrl}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">维系员工：</label>
			<div class="controls">
-
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建人：</label>
			<div class="controls">
				-
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建日期：</label>
			<div class="controls">
					<fmt:formatDate value="${paymentRecord.payTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会计日期：</label>
			<div class="controls">
				-
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">修改人：</label>
			<div class="controls">
				-
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">修改日期：</label>
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