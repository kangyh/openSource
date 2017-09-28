<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单详情页</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		
		});
	</script>
</head>
<body>
	<ul class="ul-form">
		<input id="btnExport" class="btn btn-primary" type="button" onclick="window.history.go(-1);" value="返回"/></li>
	</ul>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>生效时间</th>
				<th>公司名称</th>
				<th>投保人</th>
				<th>投保地址</th>
				<th>处理时间</th>
				<th>处理人</th>
				<th>是否分润</th>
				<th>分润时间</th>
			</tr> 
		</thead>
		<tbody>
			<tr>
			<td><fmt:formatDate value="${promOrder.effectTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>${promOrder.companyName}</td>
			<td>${promOrder.cocerPeople}</td>
			<td>${promOrder.coverAddress}</td>
			<td><fmt:formatDate value="${promOrder.dealTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>${promOrder.dealPeople}</td>
			<td>${promOrder.isProfit}</td>
			<td>${promOrder.profitTime}</td>
			</tr> 
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>