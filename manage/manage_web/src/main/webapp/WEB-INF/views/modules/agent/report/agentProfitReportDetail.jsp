<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分润明细报表</title>
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
            var profitDate = $("#profitDate").val();

            var host = "${ctx}/agent/report/exportExcelDetail";
            var url = host+"?profitDate="+profitDate;
            $('<form method="post" action="' + url + '"></form>').appendTo('body').submit().remove();
        }
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="agentProfit" class="breadcrumb form-search">
		<ul class="ul-form">
			<input id="profitDate" name="profitDate" type="hidden" value="${agentProfit.profitDate}"/>
			<li class="btns"><input id="return" class="btn btn-primary" type="button" onclick="window.history.go(-1);" value="返回"/></li>
			<li class="btns"><input class="btn btn-warning" type="button" onclick="exportExcel()" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>结算日期</th>
				<th>一级代理商</th>
				<th>所属商户数</th>
				<th>交易总金额</th>
				<th>手续费总额</th>
				<th>分润金额</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="agentProfit">
			<tr>
				<td><%--<a href="${ctx}/agent/agent/agentInfo/form?id=${agentInfo.id}">--%>
					${agentProfit.profitDate}
				<%--</a>--%></td>
				<td>
					${agentProfit.agentName}
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
					${agentProfit.profitTotalAmount}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>