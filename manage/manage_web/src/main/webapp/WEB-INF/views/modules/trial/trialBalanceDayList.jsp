<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>余额平衡</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			$("#resetSubmit").click(function(){
				var resetDate=$("#resetDate").val();
				if(!resetDate){
					alert("请选择重新生成数据的开始时间！");
					return;
				}
				if(confirm("确定要重新生成数据吗？")){
		        	$("#resetSubmit").attr("disabled",true);
		        	window.location.href="${ctx}/trial/trialBalanceDay/reset?accountDate="+resetDate;
				}
			});
			$("#contentTable").find("tr").each(function(){
				var debitCurrentAmount=$(this).attr("debitCurrentAmount");
				var creditCurrentAmount=$(this).attr("creditCurrentAmount");				
				if(debitCurrentAmount!= creditCurrentAmount){
					$(this).attr("style","color: red;");
				}
			
			})
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        function tosubject(accountDate){

		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/trial/trialBalanceDay/">余额平衡</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="trialBalanceDay" action="${ctx}/trial/trialBalanceDay/" method="post" class="breadcrumb form-search">
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
			<li><label>日期：</label>
				<input id="resetDate" name="resetDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${resetDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="resetSubmit" class="btn btn-primary" type="button" value="重新生成"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th rowspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">会计日期</th>
				<th colspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">期初余额</th>
				<th colspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">发生额</th>
				<th colspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">期末余额</th>
				<th rowspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;"></th>
			</tr>
			<tr>
				<th style="text-align: center;vertical-align: middle;color: #46A3FF;">借方</th>
				<th style="text-align: center;vertical-align: middle;color: #46A3FF;">贷方</th>
				<th style="text-align: center;vertical-align: middle;color: #46A3FF;">借方</th>
				<th style="text-align: center;vertical-align: middle;color: #46A3FF;">贷方</th>
				<th style="text-align: center;vertical-align: middle;color: #46A3FF;">借方</th>
				<th style="text-align: center;vertical-align: middle;color: #46A3FF;">贷方</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="trialBalanceDay">
			<tr  debitCurrentAmount="${trialBalanceDay.debitCurrentAmount}" creditCurrentAmount="${trialBalanceDay.creditCurrentAmount}" <c:if test="${trialBalanceDay.isErrorData}">style="color:#CD2626;"</c:if>>
				<td style="text-align: center;">
					${trialBalanceDay.accountDate}
				</td>
				<td style="text-align: center;">
					<fmt:formatNumber value="${trialBalanceDay.debitBeginingBalances}" pattern="￥#,##0.00" />
				</td>
				<td style="text-align: center;">
					<fmt:formatNumber value="${trialBalanceDay.creditBeginingBalances}" pattern="￥#,##0.00" />
				</td>
				<td style="text-align: center;">
						<fmt:formatNumber value="${trialBalanceDay.debitCurrentAmount}" pattern="￥#,##0.00" />
				</td>
				<td style="text-align: center;">
						<fmt:formatNumber value="${trialBalanceDay.creditCurrentAmount}" pattern="￥#,##0.00" />
				</td>
				<td style="text-align: center;">
						<fmt:formatNumber value="${trialBalanceDay.debitEndingBalances}" pattern="￥#,##0.00" />
				</td>
				<td style="text-align: center;">
						<fmt:formatNumber value="${trialBalanceDay.creditEndingBalances}" pattern="￥#,##0.00" />
				</td>
				<td style="text-align: center;">
					<a href="${ctx}/trial/trialBalanceDay/form?accountDate=${trialBalanceDay.accountDate}">
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