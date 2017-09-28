<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订阅支付明细管理</title>
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
		<li><a href="${ctx}/payment/subscribeCollectionRecordDetail/">订阅支付明细列表</a></li>
		<li class="active"><a href="${ctx}/payment/subscribeCollectionRecordDetail/form?id=${subscribeCollectionRecordDetail.id}">订阅支付明细<shiro:hasPermission name="payment:subscribeCollectionRecordDetail:edit">${not empty subscribeCollectionRecordDetail.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="payment:subscribeCollectionRecordDetail:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="subscribeCollectionRecordDetail" action="${ctx}/payment/subscribeCollectionRecordDetail/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">订阅明细编号：</label>
			<div class="controls">
				${subscribeCollectionRecordDetail.collectDetailId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订阅编号：</label>
			<div class="controls">
					${subscribeCollectionRecordDetail.subscribeId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户编码：</label>
			<div class="controls">
					${subscribeCollectionRecordDetail.merchantId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户用户编码：</label>
			<div class="controls">
					${subscribeCollectionRecordDetail.merchantUserId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">卡号：</label>
			<div class="controls">
					${subscribeCollectionRecordDetail.bankcardNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号：</label>
			<div class="controls">
					${subscribeCollectionRecordDetail.bankcardOwnerMobile}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
					${subscribeCollectionRecordDetail.bankcardOwnerName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">持卡人身份证：</label>
			<div class="controls">
					${subscribeCollectionRecordDetail.bankcardOwnerIdcard}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行名称：</label>
			<div class="controls">
					${subscribeCollectionRecordDetail.bankName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行编号：</label>
			<div class="controls">
					${subscribeCollectionRecordDetail.bankId}
			</div>
		</div>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">授权码：</label>--%>
			<%--<div class="controls">--%>
					<%--${subscribeCollectionRecordDetail.authCode}--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="control-group">
			<label class="control-label">商户订单号：</label>
			<div class="controls">
					${subscribeCollectionRecordDetail.merchantOrderNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">代收金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${subscribeCollectionRecordDetail.collectAmount}" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续费：</label>
			<div class="controls">
				<fmt:formatNumber value="${subscribeCollectionRecordDetail.feeAmount}" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<fmt:formatDate value="${subscribeCollectionRecordDetail.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="form-actions">
			<%--<shiro:hasPermission name="payment:subscribeCollectionRecordDetail:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>--%>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>