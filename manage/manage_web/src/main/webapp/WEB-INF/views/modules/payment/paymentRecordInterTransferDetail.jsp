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
	<li class="active">内转详情查看</li>
</ul><br/>
${batchCollectionRecordDetail}
<form:form id="inputForm" modelAttribute="innerTransferRecord" action="${ctx}/payment/paymentRecord/save" method="post" class="form-horizontal">
	<%--<form:hidden path="collectId"/>--%>
	<sys:message content="${message}"/>
	<div class="control-group">
		<label class="control-label">转账ID：</label>
		<div class="controls">
			${innerTransferRecord.innerTransferId}
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">商户交易号：</label>
		<div class="controls">
				${innerTransferRecord.merchantOrderNo}
		</div>
	</div>

	<div class="control-group">
		<label class="control-label">转出账户编码：</label>
		<div class="controls">
				${innerTransferRecord.outAccountId}
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">转出账户名称：</label>
		<div class="controls">
				${innerTransferRecord.outAccountName}
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">转出商户编码：</label>
		<div class="controls">
				${innerTransferRecord.outMerchantId}
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">转入账户ID：</label>
		<div class="controls">
				${innerTransferRecord.inAccountId}
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">转入账户名称：</label>
		<div class="controls">
				${innerTransferRecord.inAccountName}
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">转入商户编码：</label>
		<div class="controls">
				${innerTransferRecord.inMerchantId}
		</div>
	</div>
	<div class="control-group">
	<label class="control-label">转账状态：</label>
	<div class="controls">
			${innerTransferRecord.status}
	</div>
	<div class="control-group">
	<label class="control-label">创建时间：</label>
	<div class="controls">
		<fmt:formatDate value="${innerTransferRecord.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
	</div>
</div>
	<div class="form-actions">
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</form:form>
</body>
</html>