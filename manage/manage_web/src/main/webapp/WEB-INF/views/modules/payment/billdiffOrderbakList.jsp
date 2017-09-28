<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单据备份管理</title>
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
		<li class="active"><a href="${ctx}/payment/billdiffOrderbak/">单据备份列表</a></li>
	</ul>
	
	
	<form:form id="searchForm" modelAttribute="billdiffOrderbak" action="${ctx}/payment/billdiffOrderbak/" method="post" class="breadcrumb form-search">
		
		<ul class="ul-form">
			<li><label>订单号：</label>
				<form:input path="qtrans_no" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="修改订单"/></li>
			<li class="clearfix"></li>
		</ul>
		
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>paymentid：</label>
				<form:input path="paymentId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>交易id：</label>
				<form:input path="transNo" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>自曾id</th>
				<th>paymentid</th>
				<th>交易id</th>
				<th>表名</th>
				<th>备份时间</th>
				<th>备份内容json格式</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="billdiffOrderbak">
			<tr>
				<td>
					${billdiffOrderbak.bakId}
				</td>
				<td>
					${billdiffOrderbak.paymentId}
				</td>
				
				<td>
					${billdiffOrderbak.transNo}
				</td>
				<td>
					${billdiffOrderbak.tableName}
				</td>
				<td>
					<fmt:formatDate value="${billdiffOrderbak.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${billdiffOrderbak.content}
				</td>

			
			</tr>
			</c:forEach>
		
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
