<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>交易管理管理</title>
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
		<li><a href="${ctx}/payment/gatewayRecord/">交易管理列表</a></li>
		<li class="active"><a href="${ctx}/payment/gatewayRecord/form?id=${gatewayRecord.id}">交易管理<shiro:hasPermission name="payment:gatewayRecord:edit">${not empty gatewayRecord.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="payment:gatewayRecord:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="gatewayRecord" action="${ctx}/payment/gatewayRecord/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">交易号：</label>
			<div class="controls">
				${gatewayRecord.gatewayId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户ID：</label>
			<div class="controls">
				${gatewayRecord.merchantId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户登录账号：</label>
			<div class="controls">
				${gatewayRecord.merchantLoginName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户公司：</label>
			<div class="controls">
				${gatewayRecord.merchantCompany}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户用户ID：</label>
			<div class="controls">
				${gatewayRecord.merchantUserId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户用户账号：</label>
			<div class="controls">
				${gatewayRecord.merchantUserName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户订单号：</label>
			<div class="controls">
				${gatewayRecord.merchantOrderNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">版本号：</label>
			<div class="controls">
				${gatewayRecord.version}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商家请求时间 ：</label>
			<div class="controls">
				<fmt:formatDate value="${gatewayRecord.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户请求IP：</label>
			<div class="controls">
				${gatewayRecord.requestIp}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户用户请求IP：</label>
			<div class="controls">
				${gatewayRecord.requestUserIp}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户通知地址：</label>
			<div class="controls">
				${gatewayRecord.notifyUrl}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户回调地址：</label>
			<div class="controls">
				${gatewayRecord.callbackUrl}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">来源方式：</label>
			<div class="controls">
				${fns:getDictLabel(gatewayRecord.requestType, 'RequestFrom', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易时间：</label>
			<div class="controls">
				<fmt:formatDate value="${gatewayRecord.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${gatewayRecord.requestAmount}" pattern="￥.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成功时间：</label>
			<div class="controls">
				<fmt:formatDate value="${gatewayRecord.successTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成功交易金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${gatewayRecord.successAmount}" pattern="￥.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易类型：</label>
			<div class="controls">
				${fns:getDictLabel(gatewayRecord.type, 'TransType', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易状态：</label>
			<div class="controls">
				${fns:getDictLabel(gatewayRecord.status, 'GatewayRecordStatus', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易说明：</label>
			<div class="controls">
				${gatewayRecord.note}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续费扣除方式：</label>
			<div class="controls">
				${gatewayRecord.feeType}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易手续费率：</label>
			<div class="controls">
				${gatewayRecord.feeRatio}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续费金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${gatewayRecord.feeAmount}" pattern="￥.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">风控结果：</label>
			<div class="controls">
				${gatewayRecord.riskcontrolType}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">风控原因：</label>
			<div class="controls">
				${gatewayRecord.riskcontrolReason}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">授权码：</label>
			<div class="controls">
				${gatewayRecord.authorizationCode}
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>