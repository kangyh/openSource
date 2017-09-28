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
					$(this).find("td").attr("style","color: #00A600;");					
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
	<form:form id="searchForm" modelAttribute="merchantAccountDaily" action="${ctx}/finanical/statement/sumList" method="post" class="breadcrumb form-search">
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
			<li><label>账号ID：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>商户ID：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="20" class="input-medium"/>
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
				<th rowspan="2" style="text-align: center;color: #46A3FF;vertical-align: middle;">商户ID</th>
				<th rowspan="2" style="text-align: center;color: #46A3FF;vertical-align: middle;">商户名称</th>
				<th rowspan="2" style="text-align: center;color: #46A3FF;vertical-align: middle;">余额账号</th>
				<th rowspan="2" style="text-align: center;color: #46A3FF;vertical-align: middle;">期初余额</th>
				<th colspan="2" style="text-align: center;color: #46A3FF;vertical-align: middle;">发生额</th>
				<th rowspan="2" style="text-align: center;color: #46A3FF;vertical-align: middle;">期末余额</th>
				<th rowspan="2" style="text-align: center;color: #46A3FF;vertical-align: middle;">待结算账号</th>
				<th rowspan="2" style="text-align: center;color: #46A3FF;vertical-align: middle;">期初余额</th>
				<th colspan="2" style="text-align: center;color: #46A3FF;vertical-align: middle;">发生额</th>
				<th rowspan="2" style="text-align: center;color: #46A3FF;vertical-align: middle;">期末余额</th>				
				<th rowspan="2" style="text-align: center;color: #46A3FF;vertical-align: middle;">手动结算结算账号</th>
				<th rowspan="2" style="text-align: center;color: #46A3FF;vertical-align: middle;">期初余额</th>
				<th colspan="2" style="text-align: center;color: #46A3FF;vertical-align: middle;">发生额</th>
				<th rowspan="2" style="text-align: center;color: #46A3FF;vertical-align: middle;">期末余额</th>	
			</tr>
			<tr>
				<th style="text-align: center;color: #46A3FF;vertical-align: middle;">借方</th>
				<th style="text-align: center;color: #46A3FF;vertical-align: middle;">贷方</th>			
				<th style="text-align: center;color: #46A3FF;vertical-align: middle;">借方</th>
				<th style="text-align: center;color: #46A3FF;vertical-align: middle;">贷方</th>	
				<th style="text-align: center;color: #46A3FF;vertical-align: middle;">借方</th>
				<th style="text-align: center;color: #46A3FF;vertical-align: middle;">贷方</th>					
			</tr>			
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchantAccountDaily" varStatus="status">
			<tr index="${status.index}">
				<td >
					${merchantAccountDaily.accountDate}
				</td>			
				<td >
					${merchantAccountDaily.merchantId}
				</td>
				<td >
					${merchantAccountDaily.merchantCompany}
				</td>
				<td >
					${merchantAccountDaily.accountId}
				</td>
				<td  >
					<fmt:formatNumber value="${merchantAccountDaily.creditBeginningBalances}" pattern="￥#,##0.00"/>
				</td >								
				<td >
					<fmt:formatNumber value="${merchantAccountDaily.debitCurrentAmount}" pattern="￥#,##0.00"/>
				</td>
				<td >
					<fmt:formatNumber value="${merchantAccountDaily.creditCurrentAmount}" pattern="￥#,##0.00"/>
				</td>

				<td >
					<fmt:formatNumber value="${merchantAccountDaily.creditEndingBalances}" pattern="￥#,##0.00"/>
				</td>
				
				
				<td >
					${merchantAccountDaily.daijiesuanAccountId}
				</td>
				<td >
					<fmt:formatNumber value="${merchantAccountDaily.daijiesuanBeginningBalances}" pattern="￥#,##0.00"/>
				</td>								
				<td >
					<fmt:formatNumber value="${merchantAccountDaily.daijiesuanDebitCurrentAmount}" pattern="￥#,##0.00"/>
				</td>
				<td >
					<fmt:formatNumber value="${merchantAccountDaily.daijiesuanCreditCurrentAmount}" pattern="￥#,##0.00"/>
				</td>

				<td >
					<fmt:formatNumber value="${merchantAccountDaily.daijiesuanEndingBalances}" pattern="￥#,##0.00"/>
				</td>
				<td >
					${merchantAccountDaily.hmDaijiesuanAccountId}
				</td>
				<td >
					<fmt:formatNumber value="${merchantAccountDaily.hmDaijiesuanBeginningBalances}" pattern="￥#,##0.00"/>
				</td>								
				<td >
					<fmt:formatNumber value="${merchantAccountDaily.hmDaijiesuanDebitCurrentAmount}" pattern="￥#,##0.00"/>
				</td>
				<td >
					<fmt:formatNumber value="${merchantAccountDaily.hmDaijiesuanCreditCurrentAmount}" pattern="￥#,##0.00"/>
				</td>

				<td >					
						<fmt:formatNumber value="${merchantAccountDaily.hmDaijiesuanEndingBalances}" pattern="￥#,##0.00"/>
				</td>								
				
				
				<shiro:hasPermission name="trial:merchantAccountDaily:edit"><td>
    				<a href="${ctx}/trial/merchantAccountDaily/form?id=${merchantAccountDaily.id}">修改</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>