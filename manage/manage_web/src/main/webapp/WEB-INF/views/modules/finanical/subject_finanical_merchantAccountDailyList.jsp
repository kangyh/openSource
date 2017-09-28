<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>余额汇总-会计科目</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#contentTable").find("tr").find("td").attr("style","text-align: center;");
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
	<form:form id="searchForm" modelAttribute="merchantAccountDaily" action="${ctx}/finanical/statement/subjectList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>日期：</label>
				<input id="accountDate" name="accountDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${accountDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>					
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>

		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>

		</thead>
		<tbody>
		<tr><th colspan="10" height="60px"  style="text-align: center;color: #6F00D2;">资产类</th></tr>
		<tr>
			<th rowspan="2" style="text-align: center;color: #46A3FF;">科目</th>	
			<th rowspan="2" style="text-align: center;color: #46A3FF;">项目</th>	
			<th colspan="2" style="text-align: center;color: #46A3FF;">期初余额</th>
			<th colspan="2" style="text-align: center;color: #46A3FF;">发生额</th>					
			<th colspan="2" style="text-align: center;color: #46A3FF;">期末余额</th>
		</tr>
		<tr>
			<th style="text-align: center;color: #46A3FF;vertical-align: middle;">借方</th>
			<th style="text-align: center;color: #46A3FF;vertical-align: middle;">贷方</th>
			<th style="text-align: center;color: #46A3FF;vertical-align: middle;">借方</th>
			<th style="text-align: center;color: #46A3FF;vertical-align: middle;">贷方</th>
			<th style="text-align: center;color: #46A3FF;vertical-align: middle;">借方</th>
			<th style="text-align: center;color: #46A3FF;vertical-align: middle;">贷方</th>
		</tr>
		<c:forEach items="${result}" var="sum">
			<tr>
				<c:if test="${ sum.subjectType=='assetClass'}">
					<td>
						${sum.type}
					</td>
					<td >
						${fns:getDictLabel(sum.type, 'AccountSubject', '')}
					</td>
					<td>
						<fmt:formatNumber value="${sum.debitBeginBalances}" pattern="￥#,##0.00"/>
					</td>
					<td>
						<fmt:formatNumber value="${sum.creditBeginBalances}" pattern="￥#,##0.00"/>
					</td>
					<td>
						<fmt:formatNumber value="${sum.debitCurrentAmount}" pattern="￥#,##0.00"/>
					</td>				
					<td>
						<fmt:formatNumber value="${sum.creditCurrentAmount}" pattern="￥#,##0.00"/>
					</td>
					<td>
						<fmt:formatNumber value="${sum.debitEndingBalances}" pattern="￥#,##0.00"/>
					</td>
					<td>
						<fmt:formatNumber value="${sum.creditEndingBalances}" pattern="￥#,##0.00"/>
					</td>
				</c:if>
			</tr>
		</c:forEach>
		<tr><th colspan="10" height="60px"  style="text-align: center;color: #6F00D2;">负债类</th></tr>
		<tr>
			<th rowspan="2" style="text-align: center;color: #46A3FF;">科目</th>
			<th rowspan="2" style="text-align: center;color: #46A3FF;">项目</th>
			<th colspan="2" style="text-align: center;color: #46A3FF;">期初余额</th>
			<th colspan="2" style="text-align: center;color: #46A3FF;">发生额</th>
			<th colspan="2" style="text-align: center;color: #46A3FF;">期末余额</th>
		</tr>
		<tr>
			<th style="text-align: center;color: #46A3FF;vertical-align: middle;">借方</th>
			<th style="text-align: center;color: #46A3FF;vertical-align: middle;">贷方</th>
			<th style="text-align: center;color: #46A3FF;vertical-align: middle;">借方</th>
			<th style="text-align: center;color: #46A3FF;vertical-align: middle;">贷方</th>
			<th style="text-align: center;color: #46A3FF;vertical-align: middle;">借方</th>
			<th style="text-align: center;color: #46A3FF;vertical-align: middle;">贷方</th>
		</tr>
		<c:forEach items="${result}" var="sum">
			<tr>
				<c:if test="${ sum.subjectType=='debtClass'}">
					<td>
						${sum.type}
					</td>
					<td>
						${fns:getDictLabel(sum.type, 'AccountSubject', '')}
					</td>
					<td>
						<fmt:formatNumber value="${sum.debitBeginBalances}" pattern="￥#,##0.00"/>
					</td>
					<td>
						<fmt:formatNumber value="${sum.creditBeginBalances}" pattern="￥#,##0.00"/>
					</td>
					<td>
						<fmt:formatNumber value="${sum.debitCurrentAmount}" pattern="￥#,##0.00"/>
					</td>
					<td>
						<fmt:formatNumber value="${sum.creditCurrentAmount}" pattern="￥#,##0.00"/>
					</td>
					<td>
						<fmt:formatNumber value="${sum.debitEndingBalances}" pattern="￥#,##0.00"/>
					</td>
					<td>
						<fmt:formatNumber value="${sum.creditEndingBalances}" pattern="￥#,##0.00"/>
					</td>
				</c:if>
			</tr>
		</c:forEach>
		<tr><th colspan="10" height="60px"  style="text-align: center;color: #6F00D2;">共同类</th></tr>
		<tr>
			<th rowspan="2" style="text-align: center;color: #46A3FF;">科目</th>
			<th rowspan="2" style="text-align: center;color: #46A3FF;">项目</th>
			<th colspan="2" style="text-align: center;color: #46A3FF;">期初余额</th>
			<th colspan="2" style="text-align: center;color: #46A3FF;">发生额</th>
			<th colspan="2" style="text-align: center;color: #46A3FF;">期末余额</th>
		</tr>
		<tr>
			<th style="text-align: center;color: #46A3FF;vertical-align: middle;">借方</th>
			<th style="text-align: center;color: #46A3FF;vertical-align: middle;">贷方</th>
			<th style="text-align: center;color: #46A3FF;vertical-align: middle;">借方</th>
			<th style="text-align: center;color: #46A3FF;vertical-align: middle;">贷方</th>
			<th style="text-align: center;color: #46A3FF;vertical-align: middle;">借方</th>
			<th style="text-align: center;color: #46A3FF;vertical-align: middle;">贷方</th>
		</tr>		
		<c:forEach items="${result}" var="sum">
			<tr>
				<c:if test="${ sum.subjectType=='commonClass'}">
					<td>
						${sum.type}
					</td>
					<td>
						${fns:getDictLabel(sum.type, 'AccountSubject', '')}
					</td>
					<td>
						<fmt:formatNumber value="${sum.debitBeginBalances}" pattern="￥#,##0.00"/>
					</td>
					<td>
						<fmt:formatNumber value="${sum.creditBeginBalances}" pattern="￥#,##0.00"/>
					</td>
					<td>
						<fmt:formatNumber value="${sum.debitCurrentAmount}" pattern="￥#,##0.00"/>
					</td>
					<td>
						<fmt:formatNumber value="${sum.creditCurrentAmount}" pattern="￥#,##0.00"/>
					</td>
					<td>
						<fmt:formatNumber value="${sum.debitEndingBalances}" pattern="￥#,##0.00"/>
					</td>
					<td>
						<fmt:formatNumber value="${sum.creditEndingBalances}" pattern="￥#,##0.00"/>
					</td>
				</c:if>
			</tr>
		</c:forEach>				
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>