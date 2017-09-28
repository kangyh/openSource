<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>费率操作日志管理</title>
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
		<li><a href="${ctx}/merchant/merchantRateLog/">费率操作日志列表</a></li>
		<li class="active"><a href="${ctx}/merchant/merchantRateLog/form?id=${merchantRateLog.id}">费率操作日志<shiro:hasPermission name="merchant:merchantRateLog:edit">${not empty merchantRateLog.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="merchant:merchantRateLog:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="merchantRateLog" action="${ctx}/merchant/merchantRateLog/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">商户产品表ID：</label>
			<div class="controls">
			    ${merchantRateLog.rateId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户费率详情表ID：</label>
			<div class="controls">
			    ${merchantRateLog.bankRateId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户ID：</label>
			<div class="controls">
			    ${merchantRateLog.merchantId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务类型：</label>
			<div class="controls">
			    ${merchantRateLog.businessType}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品代码：</label>
			<div class="controls">
			    ${merchantRateLog.productCode}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品名称：</label>
			<div class="controls">
			    ${merchantRateLog.productName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规则开始时间：</label>
			<div class="controls">
			     <fmt:formatDate value="${merchantRateLog.ruleBeginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规则结束时间：</label>
			<div class="controls">
				<fmt:formatDate value="${merchantRateLog.ruleEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续费来源：</label>
			<div class="controls">
			     ${merchantRateLog.chargeSource}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续费扣除方式：</label>
			<div class="controls">
			     ${merchantRateLog.chargeDeductType}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续费收取方式：</label>
			<div class="controls">
			     ${merchantRateLog.chargeCollectionType}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">退款时是否退还手续费：</label>
			<div class="controls">
			     ${merchantRateLog.isRefundable}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
			     ${merchantRateLog.status}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结算类型：</label>
			<div class="controls">
			     T+${merchantRateLog.settleType}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结算至：</label>
			<div class="controls">
			     ${merchantRateLog.settlementTo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">异步通知地址：</label>
			<div class="controls">
			     ${merchantRateLog.notifyUrl}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同步返回地址：</label>
			<div class="controls">
			     ${merchantRateLog.backUrl}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">ip/域名：</label>
			<div class="controls">
			     ${merchantRateLog.ipDomain}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">签名key：</label>
			<div class="controls">
			     ${merchantRateLog.autographKey}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">签名方式：</label>
			<div class="controls">
			     ${merchantRateLog.autographType}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行id：</label>
			<div class="controls">
			     ${merchantRateLog.bankNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行名：</label>
			<div class="controls">
			     ${merchantRateLog.bankName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行卡类型：</label>
			<div class="controls">
			     ${merchantRateLog.bankCardType}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">计费类型：</label>
			<div class="controls">
			     ${merchantRateLog.chargeType}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续费费用(%)比例：</label>
			<div class="controls">
			     ${merchantRateLog.chargeRatio}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续费费用(元)固定：</label>
			<div class="controls">
			     ${merchantRateLog.chargeFee}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续费比例最大金额：</label>
			<div class="controls">
			     ${merchantRateLog.maxFee}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续费比例最小金额：</label>
			<div class="controls">
			     ${merchantRateLog.minFee}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作时间：</label>
			<div class="controls">
			     <fmt:formatDate value="${merchantRateLog.operationTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作人：</label>
			<div class="controls">
			     ${merchantRateLog.operationUser}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作类型：</label>
			<div class="controls">
			     ${merchantRateLog.operationType}
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>