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
	<li class="active"><a href="javascript:void(0)">转账详情查看</a></li>
</ul><br/>
${batchCollectionRecordDetail}
<form:form id="inputForm" modelAttribute="batchPayRecord" action="${ctx}/payment/paymentRecord/save" method="post" class="form-horizontal">
	<%--<form:hidden path="collectId"/>--%>
	<sys:message content="${message}"/>
	<div class="control-group">
		<label class="control-label">交易订单号：</label>
		<div class="controls">
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">批次号：</label>
		<div class="controls">
				${batchPayRecord.batchId}
		</div>
	</div>

	<div class="control-group">
		<label class="control-label">商户编码：</label>
		<div class="controls">
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">商户公司名称：</label>
		<div class="controls">
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">商户账号：</label>
		<div class="controls">
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">转账总金额(元)：</label>
		<div class="controls">
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">转账成功总笔数：</label>
		<div class="controls">
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">付款通道：</label>
		<div class="controls">
		</div>
	</div>		<div class="control-group">
	<label class="control-label">会计日期：</label>
	<div class="controls">
		-
	</div>
</div>
	<div class="form-actions">
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</form:form>
</body>
</html>