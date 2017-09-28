<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>账务明细列表</title>
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
		function querySettle(){
			$("#searchForm").attr("action","${ctx}/payment/merchantLogDetail/");
			$("#searchForm").submit();
		}
		<shiro:hasPermission name="payment:merchantLogDetail:delete">
		function delSettle(){
			$("#searchForm").attr("action","${ctx}/payment/merchantLogDetail/merchantLogDetailDel");
			$("#searchForm").submit();
		}
		</shiro:hasPermission>
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/merchantLogDetail/">账务明细列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="merchantLogDetail" action="${ctx}/payment/merchantLogDetail/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>结算批次号：</label>
				<form:input path="settleId" htmlEscape="false" maxlength="26" class="input-medium"/>
			</li>
			<li><label>交易号：</label>
				<form:input path="transNo" htmlEscape="false" maxlength="26" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" onclick="querySettle()" class="btn btn-primary" type="button" value="查询"/></li>
			<shiro:hasPermission name="payment:merchantLogDetail:delete">
				<li class="btns"><input id="btnDelSubmit" onclick="delSettle()" class="btn btn-primary" type="button" value="删除当前批次"/></li>
			</shiro:hasPermission>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户编码</th>
				<th>交易类型</th>
				<th>结算批次号</th>
				<th>交易号</th>
				<th>订单金额</th>
				<th>结算金额</th>
				<th>手续费金额</th>
				<th>创建时间</th>
				<th>会计日期</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchantLogDetail">
			<tr>
				<td>
					${merchantLogDetail.merchantId}
				</td>
				<td>
					<c:set var="TRANS_TYPE" value="${fns:getDictLabel(merchantLogDetail.type, 'TransType', '')}"></c:set>
					<c:choose>
						<c:when test="${not empty TRANS_TYPE}">
						${TRANS_TYPE}
						</c:when>
						<c:otherwise>-</c:otherwise>
					</c:choose>
				</td>
				<td>
					${merchantLogDetail.settleId}
				</td>
				<td>
					${merchantLogDetail.transNo}
				</td>
				<td>
					<c:if test="${not empty merchantLogDetail.balanceAmount}">
						<fmt:formatNumber value="${merchantLogDetail.balanceAmount}" pattern="￥#,##0.00" />
					</c:if>
				</td>
				<td>
					<c:if test="${not empty merchantLogDetail.settlementAmount}">
						<fmt:formatNumber value="${merchantLogDetail.settlementAmount}" pattern="￥#,##0.00" />
					</c:if>
				</td>
				<td>
					<c:if test="${not empty merchantLogDetail.feeAmount}">
						<fmt:formatNumber value="${merchantLogDetail.feeAmount}" pattern="￥#,##0.00" />
					</c:if>
				</td>
				<td>
					<fmt:formatDate value="${merchantLogDetail.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${merchantLogDetail.accountDate}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>