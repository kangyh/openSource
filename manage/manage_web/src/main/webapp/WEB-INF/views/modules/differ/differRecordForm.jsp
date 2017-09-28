<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>差异处理平台管理</title>
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
		<li class="active"><a href="${ctx}/differ/differRecord/form?id=${differRecord.id}">差异处理平台</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="differRecord" action="${ctx}/differ/differRecord/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">差异处理主键：</label>
			<div class="controls">
					${differRecord.differId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付单号：</label>
			<div class="controls">
					${differRecord.paymentId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易订单号：</label>
			<div class="controls">
					${differRecord.transNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户号：</label>
			<div class="controls">
					${differRecord.merchantId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户登录名：</label>
			<div class="controls">
					${differRecord.merchantLoginName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户公司名：</label>
			<div class="controls">
					${differRecord.merchantCompany}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易金额：</label>
			<div class="controls">
					<fmt:formatNumber value="${differRecord.payAmount}" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户手续费：</label>
			<div class="controls">
					<fmt:formatNumber value="${differRecord.merchantFee}" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单状态：</label>
			<div class="controls">
					${fns:getDictLabel(differRecord.transStatus, 'PaymentRecordStatus', differRecord.transStatus)}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易类型：</label>
			<div class="controls">
					${fns:getDictLabel(differRecord.transType, 'TransType', differRecord.transType)}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付类型：</label>
			<div class="controls">
					${fns:getDictLabel(differRecord.payType, 'payType', differRecord.payType)}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品编码：</label>
			<div class="controls">
					${fns:getDictLabel(differRecord.productCode, 'ProductCodeType', differRecord.productCode)}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单创建时间：</label>
			<div class="controls">
					<fmt:formatDate value="${differRecord.transCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单提交网关时间：</label>
			<div class="controls">
					<fmt:formatDate value="${differRecord.transPayTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单成功时间：</label>
			<div class="controls">
					<fmt:formatDate value="${differRecord.transSuccessTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通道编码：</label>
			<div class="controls">
					${differRecord.channelCode}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行流水号：</label>
			<div class="controls">
					${differRecord.bankSerialNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成功金额：</label>
			<div class="controls">
					<fmt:formatNumber value="${differRecord.successAmount}" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通道手续费：</label>
			<div class="controls">
					<fmt:formatNumber value="${differRecord.channelFee}" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务费(银联)：</label>
			<div class="controls">
					<fmt:formatNumber value="${differRecord.serviceFee}" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通道成功时间：</label>
			<div class="controls">
					<fmt:formatDate value="${differRecord.bankSuccessTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">差异类型：</label>
			<div class="controls">
					${fns:getDictLabel(differRecord.differType, 'DifferConsts', differRecord.differType)}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">差异处理状态：</label>
			<div class="controls">
					${fns:getDictLabel(differRecord.differStatus, 'DifferConsts', differRecord.differStatus)}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">对账批次：</label>
			<div class="controls">
					${differRecord.checkBatch}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作人：</label>
			<div class="controls">
					${differRecord.operator}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作来源：</label>
			<div class="controls">
					${fns:getDictLabel(differRecord.operateSource, 'DifferConsts', differRecord.operateSource)}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">处理方式：</label>
			<div class="controls">
					${fns:getDictLabel(differRecord.operateType, 'DifferConsts', differRecord.operateType)}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">处理时间：</label>
			<div class="controls">
				<fmt:formatDate value="${differRecord.operateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<fmt:formatDate value="${differRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">更新时间：</label>
			<div class="controls">
				<fmt:formatDate value="${differRecord.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>