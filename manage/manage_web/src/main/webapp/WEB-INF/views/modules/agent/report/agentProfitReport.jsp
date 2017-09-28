<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分润报表</title>
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

        //导出
        function exportExcel(){
			var profitDateBegin = $("#profitDateBegin").val();
			var profitDateEnd = $("#profitDateEnd").val();

			var host = "${ctx}/agent/report/exportExcel";
			var url = host+"?profitDateBegin="+profitDateBegin+"&profitDateEnd="+profitDateEnd;
			$('<form method="post" action="' + url + '"></form>').appendTo('body').submit().remove();
        }
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">--%>
		<%--<li class="active"><a href="${ctx}/agent/agent/agentInfo/">代理商列表</a></li>--%>
		<%--<shiro:hasPermission name="agent:agent:agentInfo:edit"><li><a href="${ctx}/agent/agent/agentInfo/form">代理商添加</a></li></shiro:hasPermission>--%>
	<%--</ul>--%>
	<form:form id="searchForm" modelAttribute="agentProfit" action="${ctx}/agent/report/profitSum/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>结算日期：</label>
				<input id="profitDateBegin" name="profitDateBegin" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${agentProfit.profitDateBegin}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input id="profitDateEnd" name="profitDateEnd" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${agentProfit.profitDateEnd}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input class="btn btn-warning" type="button" onclick="exportExcel()" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>结算日期</th>
				<th>交易总笔数</th>
				<th>交易总商户数</th>
				<th>交易总金额</th>
				<th>手续费总金额</th>
				<th>一级代理数</th>
				<th>总分润金额</th>
				<th>查看</th>
				<th>财务处理情况</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="agentProfit">
			<tr>
				<td><%--<a href="${ctx}/agent/agent/agentInfo/form?id=${agentInfo.id}">--%>
					${agentProfit.profitDate}
				<%--</a>--%></td>
				<td>
					${agentProfit.transTotalCount}
				</td>
				<td>
					${agentProfit.merchantNum}
				</td>
				<td>
					${agentProfit.transTotalAmount}
				</td>
				<td>
				    ${agentProfit.feeTotalAmount}
				</td>
				<td>
				    ${agentProfit.agentId}
				</td>
				<td>
					${agentProfit.profitTotalAmount}
				</td>
				<td>
					<a href="${ctx}/agent/report/detail?profitDate=${agentProfit.profitDate}">明细</a>
				</td>
				<td>
					<c:if test="${agentProfit.payStatus == 'NOTPAY'}">
						<a href="${ctx}/agent/report/pay?profitDate=${agentProfit.profitDate}" onclick="return confirmx('已将${agentProfit.profitDate}日总分润金额划拨至代理商系统账户',this.href)">未打款</a>
					</c:if>
					<c:if test="${agentProfit.payStatus == 'PAYSUC'}">
						已付款
					</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>