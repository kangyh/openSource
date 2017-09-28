<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>试算平衡-交易维度管理</title>
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
				url:"${ctx}/trial/trialBalanceTrans/save",
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
		<li class="active"><a href="${ctx}/trial/trialBalanceTrans/">试算平衡-交易维度列表</a></li>
		<shiro:hasPermission name="trial:trialBalanceTrans:edit"><li><a href="${ctx}/trial/trialBalanceTrans/form">试算平衡-交易维度添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="trialBalanceTrans" action="${ctx}/trial/trialBalanceTrans/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>交易号：</label>
				<form:input path="transNo" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>		
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<%--<th rowspan="2">Id</th>--%>
				<th rowspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">会计日期</th>
				<th rowspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">交易号</th>
				<th colspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">发生额</th>
				<th rowspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">交易类型</th>
				<th rowspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">创建时间</th>
				<th rowspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">更新时间</th>
				<th rowspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">处理状态</th>
			</tr>
			<tr>
				<th style="text-align: center;vertical-align: middle;color: #46A3FF;">借方</th>
				<th style="text-align: center;vertical-align: middle;color: #46A3FF;">贷方</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="trialBalanceTrans">
			<tr>
				<%--<td><a href="${ctx}/trial/trialBalanceTrans/form?id=${trialBalanceTrans.id}">--%>
					<%--${trialBalanceTrans.id}--%>
				<%--</a></td>--%>
				<td style="text-align: center;">
					${trialBalanceTrans.accountDate}
				</td>
				<td style="text-align: center;">
					${trialBalanceTrans.transNo}
				</td>
				<td style="text-align: center;">
						<fmt:formatNumber value="${trialBalanceTrans.debitCurrentAmount}" pattern="￥#,##0.00" />
				</td>
				<td style="text-align: center;">
						<fmt:formatNumber value="${trialBalanceTrans.creditCurrentAmount}" pattern="￥#,##0.00" />
				</td>
				<td style="text-align: center;">
					${trialBalanceTrans.type}
				</td>
				<td style="text-align: center;">
					<fmt:formatDate value="${trialBalanceTrans.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td style="text-align: center;">
					<fmt:formatDate value="${trialBalanceTrans.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td style="text-align: center;">
					<c:if test="${trialBalanceTrans.status == 'PREHAN'}">
						<span><input type="button" style="color:red;" onclick="updateStatus(${trialBalanceTrans.id},this)" value="未处理"></span>
					</c:if>
					<c:if test="${trialBalanceTrans.status != 'PREHAN'}">
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