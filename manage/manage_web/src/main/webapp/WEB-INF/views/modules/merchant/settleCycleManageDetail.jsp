<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>结算周期管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/merchant/settleCycleManage/">结算周期列表</a></li>
		<li class="active"><a href="">结算周期<shiro:hasPermission name="merchant:settleCycleManage:edit">${not empty settleCycleManage.id?'详情':'添加'}</shiro:hasPermission><shiro:lacksPermission name="merchant:settleCycleManage:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="settleCycleManage"  class="form-horizontal">
		<div class="control-group">
			<label class="control-label">商户编码：</label>
			<div style="padding-top: 3px;">${settleCycleManage.merchantId}</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户公司名称：</label>
			<div style="padding-top: 3px;">${settleCycleManage.merchantCompanyName}</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品名称：</label>
			<div style="padding-top: 3px;">${settleCycleManage.productName}</div>
		</div>
		<div class="control-group">
			<label class="control-label">结算类型：</label>
			<div style="padding-top: 3px;">T+${settleCycleManage.settleType}</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">最小结算金额：</label>
			<div style="padding-top: 3px;">${settleCycleManage.minSettlementAmount}</div>
		</div> --%>
		<div class="control-group" style="display:none;">
			<label class="control-label">有效开始时间：</label>
			<div style="padding-top: 3px;"><fmt:formatDate value="${settleCycleManage.effectiveStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
		</div>
		<div class="control-group" style="display:none;">
			<label class="control-label">有效截止时间：</label>
			<div style="padding-top: 3px;"><fmt:formatDate value="${settleCycleManage.effectiveEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
		</div>
		<div class="control-group">
			<label class="control-label">结算至：</label>
			<div style="padding-top: 3px;">${settleCycleManage.settlementTo}</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div style="padding-top: 3px;">${settleCycleManage.status}</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>