<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订阅支付申请管理</title>
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
		<li class="active"><a href="${ctx}/payment/subscribeCollectionRecord/">订阅支付申请列表</a></li>
		<%--<shiro:hasPermission name="payment:subscribeCollectionRecord:edit"><li><a href="${ctx}/payment/subscribeCollectionRecord/form">订阅支付申请添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="subscribeCollectionRecord" action="${ctx}/payment/subscribeCollectionRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>订阅编号：</label>
				<form:input path="subscribeId" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>商户订单号：</label>
				<form:input path="merchantOrderNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商户编号：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<%--<li><label>授权编码：</label>--%>
				<%--<form:input path="authCode" htmlEscape="false" maxlength="256" class="input-medium"/>--%>
			<%--</li>--%>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>订阅编号</th>
				<th>商户订单号</th>
				<th>商户编号</th>
				<th>申请总额</th>
				<th>成功金额</th>
				<th>成功笔数</th>
				<th>扣款频率</th>
				<th>单次扣款金额</th>
				<th>已扣款金额</th>
				<th>开始扣款时间</th>
				<th>最后扣款时间</th>
				<th>前次扣款时间</th>
				<th>下次扣款时间</th>
				<th>申请状态</th>
				<%--<th>授权编码</th>--%>
				<%--<th>请求来源</th>--%>
				<th>创建时间</th>
				<th>修改时间</th>
				<%--<shiro:hasPermission name="payment:subscribeCollectionRecord:edit"><th>操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="subscribeCollectionRecord">
			<tr>
				<td>
					<%--<a href="${ctx}/payment/subscribeCollectionRecord/form?id=${subscribeCollectionRecord.id}">--%>
					${subscribeCollectionRecord.subscribeId}
				<%--</a>--%>
				</td>
				<td>
					${subscribeCollectionRecord.merchantOrderNo}
				</td>
				<td>
					${subscribeCollectionRecord.merchantId}
				</td>
				<td>
					<fmt:formatNumber value="${subscribeCollectionRecord.totalAmount}" pattern="￥#,##0.00" />
				</td>

				<td>
					<fmt:formatNumber value="${subscribeCollectionRecord.successTotalAmount}" pattern="￥#,##0.00" />
				</td>
				<td>
					${subscribeCollectionRecord.successTotalNum}
				</td>
				<td>
					${fns:getDictLabel(subscribeCollectionRecord.withholdRate, 'SubscribeRateStatus', '')}
				</td>
				<td>
					<fmt:formatNumber value="${subscribeCollectionRecord.withholdAmount}" pattern="￥#,##0.00" />
				</td>
				<td>
					<fmt:formatNumber value="${subscribeCollectionRecord.withholdTotalAmount}" pattern="￥#,##0.00" />
				</td>
				<td>
					<fmt:formatDate value="${subscribeCollectionRecord.withholdBeginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${subscribeCollectionRecord.withholdLastTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${subscribeCollectionRecord.withholdPreTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${subscribeCollectionRecord.withholdLatterTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td <c:if test="${'NORMAL' eq subscribeCollectionRecord.subscribeStatus}">style="background:#6cf683;" </c:if>
					<c:if test="${'INVALI' eq subscribeCollectionRecord.subscribeStatus}">style="background:#ff0305" </c:if>
					<c:if test="${'RELIEV' eq subscribeCollectionRecord.subscribeStatus}">style="background:#CCCCCC" </c:if>
					<c:if test="${'FINISH' eq subscribeCollectionRecord.subscribeStatus}">style="background:#B2DD32" </c:if>
						>
					${fns:getDictLabel(subscribeCollectionRecord.subscribeStatus, 'SubscribeStatus', '')}
				</td>
				<%--<td>--%>
					<%--${subscribeCollectionRecord.authCode}--%>
				<%--</td>--%>
				<%--<td>--%>
						<%--${subscribeCollectionRecord.transferFrom}--%>
				<%--</td>--%>
				<td>
					<fmt:formatDate value="${subscribeCollectionRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${subscribeCollectionRecord.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<%--<shiro:hasPermission name="payment:subscribeCollectionRecord:edit"><td>--%>
    				<%--<a href="${ctx}/payment/subscribeCollectionRecord/form?id=${subscribeCollectionRecord.id}">暂停</a>--%>
				<%--</td></shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>