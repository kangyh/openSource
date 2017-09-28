<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>临时代收汇总管理</title>
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
		<li class="active"><a href="${ctx}/hgms/hgmsTemporaryCollectionRecord/">临时代收汇总列表</a></li>
		<li><a href="${ctx}/hgms/hgmsTemporaryCollectionRecordDetail/">临时代收明细列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="hgmsTemporaryCollectionRecord" action="${ctx}/hgms/hgmsTemporaryCollectionRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>订单批次号：</label>
				<form:input path="batchId" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>商户公司：</label>
				<form:input path="merchantCompany" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
            <li><label>订单来源-：</label>
                <form:select path="recordSource" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${hgmsOrderSource}" itemLabel="name" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>订单批次号</th>
				<th>商户名称</th>
				<th>交易时间</th>
				<th>总笔数</th>
				<th>总金额</th>
				<th>总手续费</th>
				<th>订单来源</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hgmsTemporaryCollectionRecord">
			<tr>
				<td>
					${hgmsTemporaryCollectionRecord.batchId}
				</td>
				<td>
					${hgmsTemporaryCollectionRecord.merchantCompany}
				</td>
				<td>
					<fmt:formatDate value="${hgmsTemporaryCollectionRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${hgmsTemporaryCollectionRecord.totalNum}
				</td>
				<td>
					<fmt:formatNumber value="${hgmsTemporaryCollectionRecord.totalAmount}" pattern="￥0.00" />
				</td>
				<td>
					<fmt:formatNumber value="${hgmsTemporaryCollectionRecord.feeTotalAmount}" pattern="￥0.00" />
				</td>
				<td>
                    <c:if test="${hgmsTemporaryCollectionRecord.recordSource == 'AUTOTM'}">
                        自动生成
                    </c:if>
                    <c:if test="${hgmsTemporaryCollectionRecord.recordSource == 'LAUNMA'}">
                        手动执行
                    </c:if>
				</td>
				<shiro:hasPermission name="hgms:hgmsTemporaryCollectionRecordDetail:view"><td>
					<a href="${ctx}/hgms/hgmsTemporaryCollectionRecordDetail/?transBatchNo=${hgmsTemporaryCollectionRecord.batchId}">详情</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>