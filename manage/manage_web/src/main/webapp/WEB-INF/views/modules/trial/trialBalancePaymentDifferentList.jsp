<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>交易和账务数据日终校验管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnReset").click(function(){
				var beginAccountDate=$("#beginAccountDate").val();
				var endAccountDate=$("#endAccountDate").val();
				if(!beginAccountDate || !endAccountDate){
					alert("请选择重新生成数据的时间区间！");
					return;
				}
				if(confirm("确定要重新生成数据吗？")){
		        	$("#btnReset").attr("disabled",true);
		        	window.location.href="${ctx}/trial/trialBalancePaymentDifferent/reset?beginAccountDate="+beginAccountDate+"&endAccountDate="+endAccountDate;
				}
			});
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
		<li class="active"><a href="${ctx}/trial/trialBalancePaymentDifferent/">交易和账务数据日终校验列表</a></li>
		<shiro:hasPermission name="trial:trialBalancePaymentDifferent:edit"><li><a href="${ctx}/trial/trialBalancePaymentDifferent/form">交易和账务数据日终校验添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="trialBalancePaymentDifferent" action="${ctx}/trial/trialBalancePaymentDifferent/" method="post" class="breadcrumb form-search">
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
			<li><label>交易类型：</label>
				<form:select id="transType" path="transType" class="input-xlarge">
					<form:option value="R0" label="全部"/>
					<c:forEach items="${fns:getDictList('TransType')}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.label}"/>
					</c:forEach>
				</form:select>
			</li>					
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnReset" class="btn btn-warning" type="button" value="重新生成"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>交易类型</th>
				<th>交易总金额</th>
				<th>贷方金额</th>
				<th>借方金额</th>
				<th>贷方手续费</th>
				<th>借方手续费</th>
				<th>会计日期</th>
				<th>处理状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="trialBalancePaymentDifferent">
			<tr>
				<td>
					${trialBalancePaymentDifferent.transType}
				</td>
				<td>
					<fmt:formatNumber value="${trialBalancePaymentDifferent.paymentAmount}" pattern="￥#,##0.00" />
				</td>
				<td>
					<fmt:formatNumber value="${trialBalancePaymentDifferent.creditBalanceAmount}" pattern="￥#,##0.00" />
				</td>
				<td>
					<fmt:formatNumber value="${trialBalancePaymentDifferent.debitBalanceAmount}" pattern="￥#,##0.00" />
				</td>
				<td>
					<fmt:formatNumber value="${trialBalancePaymentDifferent.creditBalanceFeeAmount}" pattern="￥#,##0.00" />
				</td>
				<td>
					<fmt:formatNumber value="${trialBalancePaymentDifferent.debitBalanceFeeAmount}" pattern="￥#,##0.00" />
				</td>
				<td>
					${trialBalancePaymentDifferent.accountDate}
				</td>
				
				<c:if test="${trialBalancePaymentDifferent.status eq 'SUCCES'}">
					<td style="background-color: #53FF53;">
					校验一致
					</td>
				</c:if>
				<c:if test="${trialBalancePaymentDifferent.status eq 'FAILED'}">
					<td style="background-color: #FF0000;">
					校验一不致
					</td>
				</c:if>
				
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>