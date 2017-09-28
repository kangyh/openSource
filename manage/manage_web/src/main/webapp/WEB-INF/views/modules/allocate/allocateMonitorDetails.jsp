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
	<form:form id="searchForm" modelAttribute="allocateMonitor" method="post" class="form-horizontal">
        <div class="control-group">
			<label class="control-label">阈值：</label>
			<div class="controls">
				<fmt:formatNumber type="number" value="${allocateMonitor.thresholdValue}" pattern="￥#,###.00" maxFractionDigits="2"/> 
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备付金账户：</label>
			<div class="controls">
				${allocateMonitor.monitorAccount}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱：</label>
			<div class="controls">
				${allocateMonitor.email}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机：</label>
			<div class="controls">
				${allocateMonitor.phoneNum}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><b>是否发送邮件：</b></label>
			<div class="controls">
				<c:if test="${allocateMonitor.emailStatus == 'Y'}">
					是
				</c:if>
				<c:if test="${allocateMonitor.emailStatus == 'N'}">
					否
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><b>是否发送短信：</b></label>
			<div class="controls">
				<c:if test="${allocateMonitor.smsStatus == 'Y'}">
					是
				</c:if>
				<c:if test="${allocateMonitor.smsStatus == 'N'}">
					否
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><b>操作人：</b></label>
			<div class="controls">
				${allocateMonitor.creator}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><b>修改人：</b></label>
			<div class="controls">
				${allocateMonitor.modifier}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><b>创建时间：</b></label>
			<div class="controls">
				<fmt:formatDate value="${allocateMonitor.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><b>更新时间：</b></label>
			<div class="controls">
				<fmt:formatDate value="${allocateMonitor.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
	</form:form>
	<div class="form-actions">
	   <input type="button" class="btn" value="返回" onclick="javascript:window.location='${ctx}/allocate/allocateMonitor/list';"/>					
	</div>
</body>
</html>