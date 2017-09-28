<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>试算平衡-账户维度管理</title>
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
		
		function updateStatus(id,tag){
			
			$.ajax({
				url:"${ctx}/trial/trialBalanceAccount/save",
				data:{"id":id},
				success:function(date){
					var html='<span style="color: green;">已处理</span>';
					
					$(tag).parent().attr("style","color: green;");
					$(tag).parent().text("已处理");
				}
			})
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/trial/trialBalanceAccount/">试算平衡-账户维度列表</a></li>
		<shiro:hasPermission name="trial:trialBalanceAccount:edit"><li><a href="${ctx}/trial/trialBalanceAccount/form">试算平衡-账户维度添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="trialBalanceAccount" action="${ctx}/trial/trialBalanceAccount/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>ID：</label>
				<form:input path="id" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>账户id：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>流水Id(当前)：</label>
				<form:input path="logId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>流水Id(下一条)：</label>
				<form:input path="nextLogId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th rowspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">ID</th>
			<th rowspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">账户id</th>
			<th rowspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">账户名称</th>
			<th colspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">当前</th>
			<th colspan="5" style="text-align: center;vertical-align: middle;color: #46A3FF;">下一条</th>
			<th rowspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">应记录余额</th>
			<th rowspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">账户科目名称</th>
			<th rowspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">创建时间</th>
			<th rowspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">更新时间</th>
			<th rowspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">处理状态</th>
		</tr>
			<tr>
				<th style="text-align: center;vertical-align: middle;color: #46A3FF;">流水Id</th>
				<th style="text-align: center;vertical-align: middle;color: #46A3FF;">余额</th>
				<th style="text-align: center;vertical-align: middle;color: #46A3FF;">流水Id</th>
				<th style="text-align: center;vertical-align: middle;color: #46A3FF;">发生额</th>
				<th style="text-align: center;vertical-align: middle;color: #46A3FF;">余额</th>
				<th style="text-align: center;vertical-align: middle;color: #46A3FF;">资金方向</th>
				<th style="text-align: center;vertical-align: middle;color: #46A3FF;">交易发生时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="trialBalanceAccount">
			<tr>
				<td style="text-align: center;"><a href="${ctx}/trial/trialBalanceAccount/form?id=${trialBalanceAccount.id}">
					${trialBalanceAccount.id}
				</a></td>
				<td style="text-align: center;">
					${trialBalanceAccount.accountId}
				</td>
				<td style="text-align: center;">
					${trialBalanceAccount.accountName}
				</td>
				<td style="text-align: center;">
					${trialBalanceAccount.logId}
				</td>
				<td style="text-align: center;">
					<fmt:formatNumber value="${trialBalanceAccount.balanceAmount}" pattern="￥#,##0.00" />
				</td>
				<td style="text-align: center;">
					${trialBalanceAccount.nextLogId}
				</td>
				<td style="text-align: center;">
					<fmt:formatNumber value="${trialBalanceAccount.nextAroseAmount}" pattern="￥#,##0.00" />
				</td>
				<td style="text-align: center;">
					<fmt:formatNumber value="${trialBalanceAccount.nextBalanceAmount}" pattern="￥#,##0.00" />
				</td>
				<td style="text-align: center;">
					<c:if test="${trialBalanceAccount.nextBalanceDirection eq 'D'}">借</c:if>
					<c:if test="${trialBalanceAccount.nextBalanceDirection eq 'C'}">贷</c:if>
				</td>
				<td style="text-align: center;">
					<fmt:formatDate value="${trialBalanceAccount.nextAroseTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td style="text-align: center;">
					<fmt:formatNumber value="${trialBalanceAccount.mustBalanceAmount}" pattern="￥#,##0.00" />
				</td>
				<td style="text-align: center;">
					${trialBalanceAccount.accountTitle}
				</td>
				<td style="text-align: center;">
					<fmt:formatDate value="${trialBalanceAccount.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td style="text-align: center;">
					<fmt:formatDate value="${trialBalanceAccount.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td style="text-align: center;">
					<c:if test="${trialBalanceAccount.status == 'PREHAN'}">
						<span><input type="button" style="color:red;" onclick="updateStatus(${trialBalanceAccount.id},this)" value="未处理"></span>
					</c:if>
					<c:if test="${trialBalanceAccount.status != 'PREHAN'}">
						<span style="color: green;">已处理</span>
					</c:if>					
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>