<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>充值管理管理</title>
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
		<li><a href="${ctx}/payment/chargeRecord/">充值管理列表</a></li>
		<li class="active"><a href="${ctx}/payment/chargeRecord/form?id=${chargeRecord.id}">充值管理<shiro:hasPermission name="payment:chargeRecord:edit">${not empty chargeRecord.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="payment:chargeRecord:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="chargeRecord" action="${ctx}/payment/chargeRecord/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">交易订单号：</label>
			<div class="controls">
				${chargeRecord.id}
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">商户编码：</label>
			<div class="controls">
				${chargeRecord.merchantId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户公司名称：</label>
			<div class="controls">
				${chargeRecord.merchantCompany}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户账号：</label>
			<div class="controls">
				${chargeRecord.merchantLoginName}
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">订单金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${chargeRecord.chargeAmount}" pattern="￥#,##0.00" />
			</div>
		</div>
			<div class="control-group">
			<label class="control-label">手续费：</label>
			<div class="controls">
				<fmt:formatNumber value="${chargeRecord.feeAmount}" pattern="￥#,##0.00" />
			</div>
		</div>
		</div>
			<div class="control-group">
			<label class="control-label">实际支付金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${chargeRecord.realAmount}" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">充值状态：</label>
			<div class="controls">
				${fns:getDictLabel(chargeRecord.status, 'ChargeStatus', '')}
			</div>
		</div>
<%-- 		<div class="control-group">
			<label class="control-label">银行卡ID：</label>
			<div class="controls">
				${chargeRecord.bankcardId}			
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">商户请求IP：</label>
			<div class="controls">
				${chargeRecord.requestIp}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户请求IP：</label>
			<div class="controls">
				${chargeRecord.payRequestIp}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<fmt:formatDate value="${chargeRecord.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">更新时间：</label>
			<div class="controls">
				<fmt:formatDate value="${chargeRecord.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成功时间：</label>
			<div class="controls">
				<fmt:formatDate value="${chargeRecord.successTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付ID：</label>
			<div class="controls">
				${chargeRecord.paymentId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">说明：</label>
			<div class="controls">
				${chargeRecord.remark}
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>