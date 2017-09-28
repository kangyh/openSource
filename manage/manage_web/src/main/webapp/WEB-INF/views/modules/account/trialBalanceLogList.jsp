<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>试算平衡管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/account/trialBalanceLog/">试算平衡列表</a></li>
		<shiro:hasPermission name="account:trialBalanceLog:edit"><li><a href="${ctx}/account/trialBalanceLog/form">试算平衡添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="trialBalanceLog" action="${ctx}/account/trialBalanceLog/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>Id</th>
				<th>所属账户Id</th>
				<th>所属商户Id</th>
				<th>对比方：资金流水Id</th>
				<th>对比方：资金变动</th>
				<th>对比方：账户余额</th>
				<th>对比方：资金方向</th>
				<th>对比方：订单号</th>
				<th>对比方：支付号</th>
				<th>被对比方：资金流水Id</th>
				<th>被对比方：资金变动</th>
				<th>被对比方：账户余额</th>
				<th>被对比方：资金方向:C入 D出</th>
				<th>对比方：订单号</th>
				<th>对比方：支付号</th>
				<th>错误类别</th>
				<th>状态</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<shiro:hasPermission name="account:trialBalanceLog:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="trialBalanceLog">
			<tr>
				<td><a href="${ctx}/account/trialBalanceLog/form?id=${trialBalanceLog.id}">
					${trialBalanceLog.id}
				</a></td>
				<td>
					${trialBalanceLog.accountId}
				</td>
				<td>
					${trialBalanceLog.merchantId}
				</td>
				<td>
					${trialBalanceLog.merchantLogIdForm}
				</td>
				<td>
					${trialBalanceLog.balanceAmountChangesForm}
				</td>
				<td>
					${trialBalanceLog.balanceAmountForm}
				</td>
				<td>
					${trialBalanceLog.balanceDirectionForm}
				</td>
				<td>
					${trialBalanceLog.transNoForm}
				</td>
				<td>
					${trialBalanceLog.paymentIdForm}
				</td>
				<td>
					${trialBalanceLog.merchantLogIdTo}
				</td>
				<td>
					${trialBalanceLog.balanceAmountChangesTo}
				</td>
				<td>
					${trialBalanceLog.balanceAmountTo}
				</td>
				<td>
					${trialBalanceLog.balanceDirectionTo}
				</td>
				<td>
					${trialBalanceLog.transNoTo}
				</td>
				<td>
					${trialBalanceLog.paymentIdTo}
				</td>
				<td>
					${trialBalanceLog.category}
				</td>
				<td>
					${trialBalanceLog.status}
				</td>
				<td>
					<fmt:formatDate value="${trialBalanceLog.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${trialBalanceLog.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="account:trialBalanceLog:edit"><td>
    				<a href="${ctx}/account/trialBalanceLog/form?id=${trialBalanceLog.id}">修改</a>
					<a href="${ctx}/account/trialBalanceLog/delete?id=${trialBalanceLog.id}" onclick="return confirmx('确认要删除该试算平衡吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>