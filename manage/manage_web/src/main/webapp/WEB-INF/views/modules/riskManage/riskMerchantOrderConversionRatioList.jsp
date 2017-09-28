<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商户订单成功率管理</title>
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
		<li class="active"><a href="${ctx}/risk/riskMerchantRatio/">商户订单成功率列表</a></li>
		<shiro:hasPermission name="risk:riskMerchantOrderConversionRatio:edit"><li><a href="${ctx}/risk/riskMerchantRatio/form">商户订单成功率添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="riskMerchantOrderConversionRatio" action="${ctx}/risk/riskMerchantRatio/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户编号：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>
			<%--<li><label>产品类型：</label>--%>
				<%--<form:input path="productCode" htmlEscape="false" maxlength="20" class="input-medium"/>--%>
			<%--</li>--%>
			<li><label>产品名称：</label>
				<form:select id="productCode" path="productCode" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${productTypeList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.content}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>主机名：</label>
				<form:input path="host" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<form:input name="beginStatisticTime" path="beginStatisticTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<form:input name="endStatisticTime" path="endStatisticTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			<%--<form:input id="beginStatisticsTime" path="beginStatisticTime" maxlength="20" class="input-mini Wdate"--%>
				   <%--value="${riskMerchantOrderConversionRatio.beginStatisticsTime}"--%>
				   <%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>--%>
				<%--&lt;%&ndash;-&ndash;%&gt;--%>
			<%--<input id="endStatisticsTime" name="endStatisticsTime" type="hidden" readonly="readonly" maxlength="20" class="input-mini Wdate"--%>
				   <%--value="<fmt:formatDate value='${riskMerchantOrderConversionRatio.endStatisticsTime}' pattern='yyyy-MM-dd'/>"--%>
				   <%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>--%>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户编号</th>
				<th>商户成功订单</th>
				<th>商户总订单</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>创建时间</th>
				<th>成功率</th>
				<th>产品编号</th>
				<th>产品名称</th>
				<th>主机名</th>
				<shiro:hasPermission name="risk:riskMerchantOrderConversionRatio:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="riskMerchantOrderConversionRatio">
			<tr>
				<td>
					${riskMerchantOrderConversionRatio.merchantId}
				</td>
				<td>
					${riskMerchantOrderConversionRatio.merchantSucessOrder}
				</td>
				<td>
					${riskMerchantOrderConversionRatio.merchantTotalOrder}
				</td>
				<td>
					<fmt:formatDate value="${riskMerchantOrderConversionRatio.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${riskMerchantOrderConversionRatio.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${riskMerchantOrderConversionRatio.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${riskMerchantOrderConversionRatio.sucessRatio}
				</td>
				<td>
					${riskMerchantOrderConversionRatio.productCode}
				</td>
				<td>
					${riskMerchantOrderConversionRatio.productName}
				</td>
				<td>
					${riskMerchantOrderConversionRatio.host}
				</td>
				<shiro:hasPermission name="risk:riskMerchantOrderConversionRatio:edit"><td>
    				<a href="${ctx}/risk/riskMerchantOrderConversionRatio/form?id=${riskMerchantOrderConversionRatio.id}">修改</a>
					<a href="${ctx}/risk/riskMerchantOrderConversionRatio/delete?id=${riskMerchantOrderConversionRatio.id}" onclick="return confirmx('确认要删除该风控商户统计表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>