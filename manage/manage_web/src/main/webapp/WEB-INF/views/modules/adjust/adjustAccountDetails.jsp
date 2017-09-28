<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>调账详情</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/adjust/adjustAccount/">调账列表</a></li>
	</ul><br/>
	<form:form  class="form-horizontal">
		<div class="control-group">
			<label class="control-label">记账流水号：</label>
			<div class="controls">
				${adjustAccount.serialNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">笔数：</label>
			<div class="controls">
				${adjustAccount.serialNum}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<c:if test="${adjustAccount.status == 'AUDING'}">
					未复核
				</c:if>
				<c:if test="${adjustAccount.status == 'REVOKE'}">
					已撤销
				</c:if>
				<c:if test="${adjustAccount.status == 'ADOPT'}">
					复核通过
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<c:forEach items="${adjustAccount.images}" var="image">
					<img src="${image }"><br/>
				</c:forEach>
			</div>
		</div>
	</form:form>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="5%">套号</th>
				<th width="8%">账户类型</th>
				<th width="15%">账号</th>
				<th width="20%">户名</th>
				<th width="10%">当前余额</th>
				<th width="8%">借贷</th>
				<th width="10%">金额</th>
				<th width="15%">备注</th>
			</tr>
		</thead>
		<tbody id="tbody">
			<c:forEach items="${list}" var="adjustAccountDetail">
				<tr>
					<td>${adjustAccountDetail.seNo }</td>
					<td>${adjustAccountDetail.accountType }</td>
					<td>${adjustAccountDetail.accountid }</td>
					<td>${adjustAccountDetail.accountName }</td>
					<td>${adjustAccountDetail.balanceAmount }</td>
					<td>${adjustAccountDetail.direction }</td>
					<td>￥${adjustAccountDetail.amount }</td>
					<td>${adjustAccountDetail.remark }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>	
	<div class="form-actions">
		<input type="button" class="btn" value="返回" onclick="javascript:window.location='${ctx}/adjust/adjustAccount/list';"/>
	</div>
</body>
</html>