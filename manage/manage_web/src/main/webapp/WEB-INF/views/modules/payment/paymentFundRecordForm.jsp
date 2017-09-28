<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>银行订单详情</title>
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
	<style type="text/css">
		.control-group{width:40%;float:left;}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/payment/paymentFundRecord/">银行订单管理列表</a></li>
		<li class="active"><a href="javascript:void(0)">银行订单管理查看</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="paymentRecord" action="${ctx}/payment/paymentFundRecord/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">支付ID：</label>
			<div class="controls">
				${paymentRecord.paymentId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易订单号：</label>
			<div class="controls">
				${paymentRecord.transNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户ID：</label>
			<div class="controls">
				${paymentRecord.merchantId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户登录账号：</label>
			<div class="controls">
				${paymentRecord.merchantLoginName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户公司：</label>
			<div class="controls">
				${paymentRecord.merchantCompany}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品编码：</label>
			<div class="controls">
				${paymentRecord.productCode}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付类型：</label>
			<div class="controls">
				${fns:getDictLabel(paymentRecord.payType, 'payType', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易类型：</label>
			<div class="controls">
				${fns:getDictLabel(paymentRecord.transType, 'TransType', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付状态：</label>
			<div class="controls">
				${fns:getDictLabel(paymentRecord.status, 'PaymentRecordStatus', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付说明：</label>
			<div class="controls">
				${paymentRecord.description}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<fmt:formatDate value="${paymentRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">更新时间：</label>
			<div class="controls">
				<fmt:formatDate value="${paymentRecord.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${paymentRecord.payAmount}" pattern="￥0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付时间：</label>
			<div class="controls">
				<fmt:formatDate value="${paymentRecord.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成功金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${paymentRecord.successAmount}" pattern="￥0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成功时间：</label>
			<div class="controls">
				<fmt:formatDate value="${paymentRecord.successTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通知地址：</label>
			<div class="controls">
				${paymentRecord.notifyUrl}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户订单号：</label>
			<div class="controls">
				${paymentRecord.merchantOrderNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行ID：</label>
			<div class="controls">
				${paymentRecord.bankId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行名称：</label>
			<div class="controls">
				${paymentRecord.bankName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行返回地址：</label>
			<div class="controls">
				${paymentRecord.bankReturnUrl}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行卡号：</label>
			<div class="controls">
				<c:if test="${not empty paymentRecord.bankcardNo}">
					${fns:hiddenBankcardNo(paymentRecord.bankcardNo)}
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行卡类型：</label>
			<div class="controls">
				${fns:getDictLabel(paymentRecord.bankcardType, 'BankcardType', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行卡持卡人类型：</label>
			<div class="controls">
				${paymentRecord.bankcardOwnerType}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行流水号：</label>
			<div class="controls">
				${paymentRecord.bankSerialNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">授权码：</label>
			<div class="controls">
				${paymentRecord.authorizationCode}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通道代码：</label>
			<div class="controls">
				${paymentRecord.channelCode}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通道名称：</label>
			<div class="controls">
				${paymentRecord.channelName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通道参考号：</label>
			<div class="controls">
				${paymentRecord.channelRefNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">币种：</label>
			<div class="controls">
				${paymentRecord.currency}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续费收取方式：</label>
			<div class="controls">
				${fns:getDictLabel(paymentRecord.feeType, 'FeeDeductType', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续费金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${paymentRecord.feeAmount}" pattern="￥0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作人：</label>
			<div class="controls">
				${paymentRecord.operator}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">优惠金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${paymentRecord.couponAmount}" pattern="￥0.00" />
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">用户ID：</label>
			<div class="controls">
				${paymentRecord.userId}
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">结算周期：</label>
			<div class="controls">
				${paymentRecord.settleCyc}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">核对状态：</label>
			<div class="controls">
				${fns:getDictLabel(paymentRecord.checkStatus, 'CheckStatus', '')}
			</div>
		</div>
		<div style="clear:both"></div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>