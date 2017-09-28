<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>银联扫码支付管理</title>
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
		<li><a href="${ctx}/payment/unionpayRecord/">银联扫码支付列表</a></li>
		<li class="active"><a href="${ctx}/payment/unionpayRecord/form?id=${unionpayRecord.unionpayId}">银联扫码支付<shiro:hasPermission name="payment:unionpayRecord:edit">${not empty unionpayRecord.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="payment:unionpayRecord:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="unionpayRecord" action="${ctx}/payment/unionpayRecord/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">交易号：</label>
			<div class="controls">
				${unionpayRecord.unionpayId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">预下单号：</label>
			<div class="controls">
				${unionpayRecord.prepayId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付单号：</label>
			<div class="controls">
				${unionpayRecord.paymentId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户ID：</label>
			<div class="controls">
				${unionpayRecord.merchantId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户登录账号：</label>
			<div class="controls">
				${unionpayRecord.merchantLoginName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户公司：</label>
			<div class="controls">
				${unionpayRecord.merchantCompany}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户订单号：</label>
			<div class="controls">
				${unionpayRecord.merchantOrderNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易类型：</label>
			<div class="controls">
				${fns:getDictLabel(unionpayRecord.type, 'UnionpayType', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易状态：</label>
			<div class="controls">
				${fns:getDictLabel(unionpayRecord.status, 'UnionpayStatus', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">失败原因：</label>
			<div class="controls">
				${unionpayRecord.failReason}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续费金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${unionpayRecord.feeAmount}" pattern="￥0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易时间：</label>
			<div class="controls">
				<fmt:formatDate value="${unionpayRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${unionpayRecord.requestAmount}" pattern="￥0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">更新时间：</label>
			<div class="controls">
				<fmt:formatDate value="${unionpayRecord.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成功时间：</label>
			<div class="controls">
				<fmt:formatDate value="${unionpayRecord.successTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成功交易金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${unionpayRecord.successAmount}" pattern="￥0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品代码：</label>
			<div class="controls">
				${unionpayRecord.productCode}
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">终端号：</label>
			<div class="controls">
				${unionpayRecord.termId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">C2B码：</label>
			<div class="controls">
				${unionpayRecord.qrNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">二维码：</label>
			<div class="controls">
				${unionpayRecord.qrCode}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">付款凭证号：</label>
			<div class="controls">
				${unionpayRecord.voucherNum}
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">付款方信息：</label>
			<div class="controls">
				<form:input path="payerInfo" htmlEscape="false" maxlength="1024" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">付款方附言：</label>
			<div class="controls">
				${unionpayRecord.payerComments}
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">收款方信息：</label>
			<div class="controls">
				<form:input path="payeeInfo" htmlEscape="false" maxlength="1024" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">收款方附言：</label>
			<div class="controls">
				${unionpayRecord.payeeComments}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发票信息：</label>
			<div class="controls">
				${unionpayRecord.invoiceInfo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商家请求时间：</label>
			<div class="controls">
				${unionpayRecord.requestDate}
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">商户通知地址：</label>
			<div class="controls">
				${unionpayRecord.notifyUrl}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">来源方式：</label>
			<div class="controls">
				${unionpayRecord.requestType}
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="payment:unionpayRecord:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>