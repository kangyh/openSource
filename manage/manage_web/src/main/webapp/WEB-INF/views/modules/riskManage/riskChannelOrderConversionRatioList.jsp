<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>通道订单成功率管理</title>
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
		<li class="active"><a href="${ctx}/risk/riskChannelOrderConversionRatio/">通道订单成功率列表</a></li>
		<shiro:hasPermission name="risk:riskChannelOrderConversionRatio:edit"><li><a href="${ctx}/risk/riskChannelOrderConversionRatio/form">通道订单成功率添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="riskChannelOrderConversionRatio" action="${ctx}/risk/riskChannelOrderConversionRatio/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>通道编码：</label>
				<form:input path="channelPartnerCode" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>
			<li><label>支付类型编码：</label>
				<form:input path="channelTypeCode" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>
			<li><label>主机名：</label>
				<form:input path="host" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<form:input name="beginStatisticTime" path="beginStatisticTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<form:input name="endStatisticTime" path="endStatisticTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
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
				<th>通道合作方编码</th>
				<th>通道合作方名称</th>
				<th>成功订单数</th>
				<th>通道总订单</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>创建时间</th>
				<th>成功率</th>
				<th>支付类型编码</th>
				<th>支付类型名称</th>
				<th>主机名</th>
				<shiro:hasPermission name="risk:riskChannelOrderConversionRatio:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="riskChannelOrderConversionRatio">
			<tr>
				<td>
					${riskChannelOrderConversionRatio.channelPartnerCode}
				</td>
				<td>
					${riskChannelOrderConversionRatio.channelPartnerName}
				</td>
				<td>
					${riskChannelOrderConversionRatio.channelSuccessOrder}
				</td>
				<td>
					${riskChannelOrderConversionRatio.channelTotalOrder}
				</td>
				<td>
					<fmt:formatDate value="${riskChannelOrderConversionRatio.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${riskChannelOrderConversionRatio.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${riskChannelOrderConversionRatio.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${riskChannelOrderConversionRatio.sucessRatio}
				</td>
				<td>
					${riskChannelOrderConversionRatio.channelTypeCode}
				</td>
				<td>
					${riskChannelOrderConversionRatio.channelTypeName}
				</td>
				<td>
					${riskChannelOrderConversionRatio.host}
				</td>
				<shiro:hasPermission name="risk:riskChannelOrderConversionRatio:edit"><td>
    				<a href="${ctx}/risk/riskChannelOrderConversionRatio/form?id=${riskChannelOrderConversionRatio.id}">修改</a>
					<a href="${ctx}/risk/riskChannelOrderConversionRatio/delete?id=${riskChannelOrderConversionRatio.id}" onclick="return confirmx('确认要删除该风控通道转化率吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>