<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订阅支付明细管理</title>
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
		<li class="active"><a href="${ctx}/payment/subscribeCollectionRecordDetail/">订阅支付明细列表</a></li>
		<%--<shiro:hasPermission name="payment:subscribeCollectionRecordDetail:edit"><li><a href="${ctx}/payment/subscribeCollectionRecordDetail/form">订阅支付明细添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="subscribeCollectionRecordDetail" action="${ctx}/payment/subscribeCollectionRecordDetail/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>订阅编号：</label>
				<form:input path="subscribeId" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>明细编号：</label>
				<form:input path="collectDetailId" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>商户订单号：</label>
				<form:input path="merchantOrderNo" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>商户编码：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>商户用户编码：</label>
				<form:input path="merchantUserId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${subscribeCollectionRecordDetail.beginCreateTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${subscribeCollectionRecordDetail.endCreateTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>订阅编号</th>
				<th>明细编号</th>
				<th>商户订单号</th>
				<th>商户编码</th>
				<th>商户用户编码</th>
				<th>代收金额</th>
				<%--<th>代收成功金额</th>--%>
				<th>手续费</th>
				<th>支付状态</th>
				<th>创建时间</th>
				<th>请求来源</th>
				<%--<shiro:hasPermission name="payment:subscribeCollectionRecordDetail:edit"><th>操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="subscribeCollectionRecordDetail">
			<tr>
				<td>
						${subscribeCollectionRecordDetail.subscribeId}
				</td>
				<td><a href="${ctx}/payment/subscribeCollectionRecordDetail/form?id=${subscribeCollectionRecordDetail.collectDetailId}">
						${subscribeCollectionRecordDetail.collectDetailId}
				</a></td>
				<td>
						${subscribeCollectionRecordDetail.merchantOrderNo}
				</td>
				<td>
					${subscribeCollectionRecordDetail.merchantId}
				</td>
				<td>
					${subscribeCollectionRecordDetail.merchantUserId}
				</td>
				<td>
					<fmt:formatNumber value="${subscribeCollectionRecordDetail.collectAmount}" pattern="￥#,##0.00" />
				</td>
				<td>
					<fmt:formatNumber value="${subscribeCollectionRecordDetail.feeAmount}" pattern="￥#,##0.00" />
				</td>
				<td>
						${fns:getDictLabel(subscribeCollectionRecordDetail.status, 'SubscribeDetailStatus', '')}
				</td>
				<td>
					<fmt:formatDate value="${subscribeCollectionRecordDetail.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(subscribeCollectionRecordDetail.transferFrom, 'SubscribeDetailSrouce', '')}
				</td>
				<%--<td>--%>
					<%--${subscribeCollectionRecordDetail.successAmount}--%>
				<%--</td>--%>

				<%--<shiro:hasPermission name="payment:subscribeCollectionRecordDetail:edit"><td>--%>
    				<%--<a href="${ctx}/payment/subscribeCollectionRecordDetail/form?id=${subscribeCollectionRecordDetail.id}">修改</a>--%>
					<%--<a href="${ctx}/payment/subscribeCollectionRecordDetail/delete?id=${subscribeCollectionRecordDetail.id}" onclick="return confirmx('确认要删除该订阅支付明细吗？', this.href)">删除</a>--%>
				<%--</td></shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>