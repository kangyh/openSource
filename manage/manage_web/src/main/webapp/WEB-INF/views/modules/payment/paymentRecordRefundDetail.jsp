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
		<li class="active"><a href="javascript:void(0)">退款详情查看</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="refundRecord" action="${ctx}/payment/paymentRecord/save" method="post" class="form-horizontal">
		<%--<form:hidden path="id"/>--%>
		<sys:message content="${message}"/>		
<%-- 		<div class="control-group">
			<label class="control-label">来源方式：</label>
			<div class="controls">
					${fns:getDictLabel(refundRecord.refundFrom, 'RefundType', '')}
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">商户账号：</label>
			<div class="controls">
				${paymentRecord.merchantId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户公司名称：</label>
			<div class="controls">
					${refundRecord.merchantCompany}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付类型：</label>
			<div class="controls">
				${fns:getDictLabel(paymentRecord.type, 'PaymentType', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易订单号：</label>
			<div class="controls">
				${refundRecord.transNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付单时间：</label>
			<div class="controls">
					<fmt:formatDate value="${paymentRecord.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">退款来源：</label>
			<div class="controls">
							${fns:getDictLabel(refundRecord.refundFrom, 'RefundFrom', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户订单号：</label>
			<div class="controls">
				${paymentRecord.merchantOrderNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单金额：</label>
			<div class="controls">
					<fmt:formatNumber value="${refundRecord.refundAmount}" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实际支付金额：</label>
			<div class="controls">
					<fmt:formatNumber value="${refundRecord.refundSuccessAmount}" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">原支付订单号：</label>
			<div class="controls">
					${paymentRecord.paymentId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通道合作方：</label>
			<div class="controls">
-
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行名称：</label>
			<div class="controls">
				${paymentRecord.bankName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行卡号：</label>
			<div class="controls">
				${fns:decrypt(paymentRecord.bankcardNo,'1231231adasfsadff')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行卡类型：</label>
			<div class="controls">
				${fns:getDictLabel(paymentRecord.bankcardType, 'BankcardType', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">退款金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${refundRecord.refundAmount}" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">退款状态：</label>
			<div class="controls">
				${fns:getDictLabel(refundRecord.type, 'RefundStatus', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通知商户状态：</label>
			<div class="controls">
				-
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">退款申请时间：</label>
			<div class="controls">
				<fmt:formatDate value="${refundRecord.refundTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">退款处理时间：</label>
			<div class="controls">
					<fmt:formatDate value="${refundRecord.refundSuccessTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				${paymentRecord.description}
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