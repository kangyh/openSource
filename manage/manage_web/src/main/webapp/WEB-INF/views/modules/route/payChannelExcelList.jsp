<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {

		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/route/payChannel/">支付通道列表</a></li>
		<li class="active"><a>导入通道成功列表</a></li>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
		        <th>序号</th>
				<th style="display:none">支付通道代码</th>
				<th>支付通道名称</th>
				<th>银行名称</th>
				<th>通道合作方</th>
				<th>支付通道类型</th>
				<th>银行卡类型</th>
				<th>有效开始时间</th>
				<th>有效结束时间</th>
				<th>成本类型</th>
				<th>成本</th>
				<th>优先级别</th>
				<th>手续费扣除方式</th>
				<th>是否退还手续费</th>
				<th>通道结算周期</th>
				<th>手续费结算类型</th>
				<th>手续费结算周期</th>
				<th>单笔限额</th>
				<th>单日限额</th>
				<th>单月限额</th>
				<th>对公对私标识</th>
				<th>付款类型</th>
				<th>所属主体</th>
				<th>通道侧商户号</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="payChannel" varStatus="i" >
			<tr>
			    <td>${i.count}</td>
				<td  style="display:none">${payChannel.channelCode}</td>
				<td>${payChannel.channelName}</td>
				<td>${payChannel.bankName}</td>
				<td>${payChannel.channelPartnerName}</td>
				<td>${payChannel.channelTypeName}</td>
				<td>${payChannel.cardTypeName}</td>
				<td><fmt:formatDate value="${payChannel.effectStartDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><fmt:formatDate value="${payChannel.effectEndDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${payChannel.costType}</td>
				<td><c:if test="${payChannel.costType=='按笔数'}">${payChannel.costCount}元</c:if><c:if test="${payChannel.costType=='按比例'}">${payChannel.costRate}‰</c:if></td>
				<td>${payChannel.sort}</td>
				<td>${payChannel.chargeDeductType}</td>
				<td>${payChannel.chargeReturnTag}</td>
				<td>${payChannel.orderSettlePeriod}</td>
				<td>${payChannel.settleType}</td>
				<td>${payChannel.settleType}</td>
				<td>${payChannel.perlimitAmount}</td>
				<td>${payChannel.daylimitAmount}</td>
				<td>${payChannel.monlimitAmount}</td>
				<td>${payChannel.accountType}</td>
				<td>${payChannel.businessType}</td>
				<td>${payChannel.merchantSubject}</td>
				<td>${payChannel.merchantNumber}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>