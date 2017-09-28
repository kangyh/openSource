<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>余额汇总-商户账户</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">

	.table tbody tr:hover{ 
    	background:#f9f1f1;
	}
	
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#contentTable").find("tr").each(function(){
				var index=$(this).attr("index");
				
				if(index && index%2!=0){
					$(this).find("td").attr("style","text-align: center;color: #00A600;");					
				}else{
					$(this).find("td").attr("style","text-align: center;");
				}
				$(this).find("td").each(function(){
					if($(this).text().trim().length==0){
						$(this).text("-");
					}
				})
				
			})
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
	<form:form id="searchForm" modelAttribute="merchantAccountDaily" action="${ctx}/finanical/statement/merchantAccountAmount" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>日期：</label>
				<input id="beginAccountDate" name="beginAccountDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${beginAccountDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>-
				<input id="endAccountDate" name="endAccountDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${endAccountDate}" pattern="yyyy-MM-dd"/>"
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
				<th rowspan="2" style="text-align: center;color: #46A3FF;vertical-align: middle;">日期</th>
				<th rowspan="2" style="text-align: center;color: #46A3FF;vertical-align: middle;">期初余额</th>
				<th colspan="2" style="text-align: center;color: #46A3FF;vertical-align: middle;">发生额</th>
				<th rowspan="2" style="text-align: center;color: #46A3FF;vertical-align: middle;">期末余额</th>
				<th rowspan="2" style="text-align: center;color: #46A3FF;vertical-align: middle;"/>	
			</tr>
			<tr>
				<th style="text-align: center;color: #46A3FF;vertical-align: middle;">借方(支出)</th>
				<th style="text-align: center;color: #46A3FF;vertical-align: middle;">贷方(收入)</th>							
			</tr>			
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchantAccountDaily" varStatus="status">
			<tr index="${status.index}">
				<td >
					${merchantAccountDaily.accountDate}
				</td>			
				<td >
					<fmt:formatNumber value="${merchantAccountDaily.creditBeginningBalances-merchantAccountDaily.debitBeginningBalances}" pattern="￥#,##0.00"/>
				</td >								
				<td >
					<fmt:formatNumber value="${merchantAccountDaily.debitCurrentAmount}" pattern="￥#,##0.00"/>
				</td>
				<td >
					<fmt:formatNumber value="${merchantAccountDaily.creditCurrentAmount}" pattern="￥#,##0.00"/>
				</td>

				<td >
					<fmt:formatNumber value="${merchantAccountDaily.creditEndingBalances-merchantAccountDaily.debitEndingBalances}" pattern="￥#,##0.00"/>
				</td>
				<td >
					<a href="${ctx}/finanical/statement/merchantAccountAmountDetail?accountDate=${merchantAccountDaily.accountDate}&begin=${merchantAccountDaily.creditBeginningBalances-merchantAccountDaily.debitBeginningBalances}&end=${merchantAccountDaily.creditEndingBalances-merchantAccountDaily.debitEndingBalances}&d=${merchantAccountDaily.debitCurrentAmount}&c=${merchantAccountDaily.creditCurrentAmount}&statisticsDate=yesterday">
					详情
					</a>
				</td>				
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>