<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		/*$(document).ready(function() {
			/!*$("#btnClear").on("click",function(){
				//console.log("11");
				$("#channelPartnerCode").val("");
				$("#channelTypeCode").val("");
				$("#cardTypeCode").val("");
				$("#status").val("");
				$("#bankNo").val("");
				$("#searchForm").submit();
			});*!/
		});		*/
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="payChannel" action="${ctx}/route/payChannel/queryList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
	        <li><label>通道合作方：</label>
		    	<form:select  path="channelPartnerCode" name="channelPartnerCode" style="width:100px" >
	    	        <form:options items="${fns:getEnumList('channelPartner')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
			   </form:select>
	        </li>
	        <li><label>支付通道类型：</label>
	            <form:select  path="channelTypeCode" name="channelTypeCode"  style="width:100px">
					<form:options items="${fns:getEnumList('channelType')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
				</form:select>
	        </li> 
	        <li><label>银行卡类型：</label>
	            <form:select  path="cardTypeCode" name="cardTypeCode"  style="width:100px">
					<form:options items="${fns:getEnumList('rateBankcardType')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
			    </form:select>
	        </li>
			<li><label>状态：</label>
				<form:select id="status" path="status" class="input-medium" style="width:70px">
					<form:options items="${fns:getEnumList('commonStatus')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
		        <th>序号</th>
				<th style="display:none">通道合作方组Id</th>
				<th>通道合作方</th>
				<th>支付通道类型</th>
				<th>银行卡类型</th>
				<th>手续费扣除方式</th>
				<th>是否退还手续费</th>
				<th>通道结算周期</th>
				<th>成本类型</th>
				<th>成本</th>
				<th>手续费结算类型</th>
				<th>手续费结算周期</th>
				<th>状态</th>
				<%--<th>修改时间</th>
				<th>修改人</th>--%>
				<shiro:hasPermission name="route:payChannel:edit"><th style="width:150px">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="payChannel" varStatus="i" >
			<tr>
			    <td>${i.count}</td>
				<td style="display:none">${payChannel.groupId}</td>
				<td>${payChannel.channelPartnerName}</td>
				<td>${payChannel.channelTypeName}</td>
				<td>${payChannel.cardTypeName}</td>
				<td>${payChannel.chargeDeductType}</td>
				<td>${payChannel.chargeReturnTag}</td>
				<td>${payChannel.orderSettlePeriod}</td>
				<%--<td><fmt:formatDate value="${payChannel.effectStartDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><fmt:formatDate value="${payChannel.effectEndDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>--%>
				<td>${payChannel.costType}</td>
				<td><c:if test="${payChannel.costType=='按笔数'}">${payChannel.costCount}元</c:if><c:if test="${payChannel.costType=='按比例'}">${payChannel.costRate}‰</c:if></td>
				<td>${payChannel.settleType}</td>
				<td>${payChannel.settlePeriod}</td>
				<td>${payChannel.status}</td>
				<%--<td><fmt:formatDate value="${payChannel.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${payChannel.updateName}</td>--%>
				<shiro:hasPermission name="route:payChannel:edit"><td>
				    <c:if test="${payChannel.status == '启用'}">
    					<a href="${ctx}/route/payChannel/batchStatus?status=DISABL&groupId=${payChannel.groupId}&channelPartnerCode=${payChannelFind.channelPartnerCode}&channelTypeCode=${payChannelFind.channelTypeCode}&cardTypeCode=${payChannelFind.cardTypeCode}&selectStatus=${payChannelFind.status}" onclick="return confirmx('确认要禁用吗？', this.href)">禁用</a>
    				</c:if>
    				<c:if test="${payChannel.status == '禁用'}">
    					<a href="${ctx}/route/payChannel/batchStatus?status=ENABLE&groupId=${payChannel.groupId}&channelPartnerCode=${payChannelFind.channelPartnerCode}&channelTypeCode=${payChannelFind.channelTypeCode}&cardTypeCode=${payChannelFind.cardTypeCode}&selectStatus=${payChannelFind.status}" onclick="return confirmx('确认要启用吗？', this.href)">启用</a>
    				</c:if>
    				<%--<a href="${ctx}/route/payChannel/batchUpdate?groupId=${payChannel.groupId}">修改</a>--%>
					<a href="${ctx}/route/payChannel/batchUpdate?groupId=${payChannel.groupId}&channelPartnerCode=${payChannelFind.channelPartnerCode}&channelTypeCode=${payChannelFind.channelTypeCode}&cardTypeCode=${payChannelFind.cardTypeCode}&status=${payChannelFind.status}">修改</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<div class="form-actions">
		<input type="button" class="btn" value="返回" onclick="javascript:window.location='${ctx}/route/payChannel/list';"/>
	</div>
</body>
</html>