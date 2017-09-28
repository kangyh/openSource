<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>银联扫码预下单管理</title>
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
		<li><a href="${ctx}/payment/preUnionpayRecord/">银联扫码预下单列表</a></li>
		<li class="active"><a href="${ctx}/payment/preUnionpayRecord/form?id=${preUnionpayRecord.id}">银联扫码预下单<shiro:hasPermission name="payment:preUnionpayRecord:edit">${not empty preUnionpayRecord.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="payment:preUnionpayRecord:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="preUnionpayRecord" action="${ctx}/payment/preUnionpayRecord/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">预下单单号：</label>
			<div class="controls">
					${preUnionpayRecord.prepayId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户号：</label>
			<div class="controls">
					${preUnionpayRecord.merchantId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户登录名：</label>
			<div class="controls">
					${preUnionpayRecord.merchantLoginName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户公司名：</label>
			<div class="controls">
					${preUnionpayRecord.merchantCompany}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户MCC码：</label>
			<div class="controls">
					${preUnionpayRecord.merchantMcc}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户预下单号：</label>
			<div class="controls">
					${preUnionpayRecord.merchantOrderNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品编码：</label>
			<div class="controls">
					${preUnionpayRecord.productCode}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易类型：</label>
			<div class="controls">
					${preUnionpayRecord.transType}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">二维码：</label>
			<div class="controls">
					${preUnionpayRecord.qrCode}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">二维码支付次数限制：</label>
			<div class="controls">
					${preUnionpayRecord.limitCount}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">二维码有效期：</label>
			<div class="controls">
					${preUnionpayRecord.qrValidTime}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">二维码截止时间：</label>
			<div class="controls">
				<fmt:formatDate value="${preUnionpayRecord.qrExpireTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">二维码单次支付金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${preUnionpayRecord.requestAmount}" pattern="￥0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成功支付总次数：</label>
			<div class="controls">
					${preUnionpayRecord.successTimes}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成功支付总金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${preUnionpayRecord.successAmount}" pattern="￥0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收款方信息：</label>
			<div class="controls">
					${preUnionpayRecord.payeeInfo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收款方附言：</label>
			<div class="controls">
					${preUnionpayRecord.payeeComments}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户请求时间：</label>
			<div class="controls">
					${preUnionpayRecord.requestDate}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户请求ip：</label>
			<div class="controls">
					${preUnionpayRecord.requestIp}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户通知地址：</label>
			<div class="controls">
					${preUnionpayRecord.notifyUrl}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户回调地址：</label>
			<div class="controls">
					${preUnionpayRecord.callbackUrl}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">来源方式：</label>
			<div class="controls">
					${preUnionpayRecord.requestType}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">版本号：</label>
			<div class="controls">
					${preUnionpayRecord.version}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<fmt:formatDate value="${preUnionpayRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">更新时间：</label>
			<div class="controls">
				<fmt:formatDate value="${preUnionpayRecord.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">扩展字段1：</label>
			<div class="controls">
					${preUnionpayRecord.ext1}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">扩展字段2：</label>
			<div class="controls">
					${preUnionpayRecord.ext2}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">扩展字段3：</label>
			<div class="controls">
					${preUnionpayRecord.ext3}
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="payment:preUnionpayRecord:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>