<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>差异管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li><a href="${ctx}/payment/wallet/differenceBillList">钱包差异列表</a></li>
	<li class="active"><a href="#">处理页面</a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="walletRecord" action="${ctx}/payment/wallet/editDifBill" method="post" enctype="multipart/form-data" class="form-horizontal">
	<div class="control-group">
		<label class="control-label">交易订单号：</label>
		<div class="controls">
			<input value="${walletRecord.transNo}" name='transNo'readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">支付方式：</label>
		<div class="controls">
			<input value="${walletRecord.description}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">状态：</label>
		<div class="controls">
			<input value="${fns:getDictLabel(walletRecord.status, 'PaymentRecordStatus', '')}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
			<input value="${walletRecord.status}" name='status' type="hidden"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">金额：</label>
		<div class="controls">
			<input value="${walletRecord.successAmount}" name='successAmount' readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">时间：</label>
		<div class="controls">
			<input value="<fmt:formatDate value="${walletRecord.successTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" name='successTimes' readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>

	<div class="form-actions">
		<input id="btnSubmit" class="btn btn-primary" type="submit" onclick="return confirmation()" value="保存并提交"/>
		<input id="btnCancel" class="btn" type="button" value="取 消" onclick="history.go(-1)"/>
	</div>
</form:form>
</body>
</html>