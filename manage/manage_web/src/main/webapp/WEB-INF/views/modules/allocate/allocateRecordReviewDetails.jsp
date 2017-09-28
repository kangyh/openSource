<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>调拨详情</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/allocate/allocateRecordReview/">调拨列表</a></li>
		<li class="active"><a href="${ctx}/allocate/allocateRecordReview/details?allocateId=${allocateRecord.allocateId}">调拨详情</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="allocateRecord" method="post" class="form-horizontal">
        <div class="control-group">
			<label class="control-label">出账的备付金账户：</label>
			<div class="controls">
				${allocateRecord.reserveInAccountName} [${allocateRecord.reserveInAccountId}]
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">入账的备付金账户：</label>
			<div class="controls">
				${allocateRecord.reserveOutAccountName} [${allocateRecord.reserveOutAccountId}]
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户Id：</label>
			<div class="controls">
				${allocateRecord.merchantName} [${allocateRecord.merchantId}]
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><b>调拨金额：</b></label>
			<div class="controls">
				<fmt:formatNumber type="number" value="${allocateRecord.amount}" pattern="￥0.00" maxFractionDigits="2"/> 
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><b>原因：</b></label>
			<div class="controls">
				${allocateRecord.reason}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><b>操作人：</b></label>
			<div class="controls">
				${allocateRecord.creator}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><b>审核人：</b></label>
			<div class="controls">
				${allocateRecord.auditor}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><b>创建时间：</b></label>
			<div class="controls">
				<fmt:formatDate value="${allocateRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><b>审核时间：</b></label>
			<div class="controls">
				<fmt:formatDate value="${allocateRecord.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><b>备注：</b></label>
			<div class="controls">
				${allocateRecord.remark}
			</div>
		</div>
	</form:form>
	<div class="form-actions">
	   <input type="button" class="btn" value="返回" onclick="javascript:window.location='${ctx}/allocate/allocateRecordReview/list';"/>					
	</div>
</body>
</html>