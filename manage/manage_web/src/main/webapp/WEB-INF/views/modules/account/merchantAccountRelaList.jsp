<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>账务关联账户管理</title>
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
		function add(){
			window.location.href = "${ctx}/account/merchantAccountRela/add";
		}

		//动态口令
		$(document).ready(function() {
			$(".checkPass").on("click",function(){
				var url = $(this).attr("value-url");
				parent.showDynamicPa();
				parent.enterDynamicPa(url);
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/account/merchantAccountRela/">账务关联账户列表</a></li>
		<shiro:hasPermission name="account:merchantAccountRela:edit"><li><a style="cursor:pointer;" class="checkPass" value-url="${ctx}/account/merchantAccountRela/form">账务关联账户添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="merchantAccountRela" action="${ctx}/account/merchantAccountRela/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<%--<ul class="ul-form">--%>
			<%--<li><label>交易类型：</label>--%>
				<%--<form:select path="transType" class="input-medium">--%>
					<%--<form:option value="" label=""/>--%>
					<%--<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
				<%--</form:select>--%>
			<%--</li>--%>
			<%--<li><label>记账标识：</label>--%>
				<%--<form:select path="transSubType" class="input-medium">--%>
					<%--<form:option value="" label=""/>--%>
					<%--<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
				<%--</form:select>--%>
			<%--</li>--%>
			<%--<li><label>账户类型：</label>--%>
				<%--<form:select path="accountType" class="input-medium">--%>
					<%--<form:option value="" label=""/>--%>
					<%--<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
				<%--</form:select>--%>
			<%--</li>--%>
			<%--<li><label>余额方向：</label>--%>
				<%--<form:select path="balanceDirection" class="input-medium">--%>
					<%--<form:option value="" label=""/>--%>
					<%--<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
				<%--</form:select>--%>
			<%--</li>--%>
			<%--<li><label>余额变动类型：</label>--%>
				<%--<form:select path="balanceChangeType" class="input-medium">--%>
					<%--<form:option value="" label=""/>--%>
					<%--<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
				<%--</form:select>--%>
			<%--</li>--%>
			<%--<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>--%>
				<%--<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="add()" value="新增"/></li>--%>
			<%--<li class="clearfix"></li>--%>
		<%--</ul>--%>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>交易阶段</th>
				<th>关联ID</th>
				<th>交易类型</th>
				<th>记账标识</th>
				<th>账户类型</th>
				<th>余额方向</th>
				<th>余额变动类型</th>
				<th>启用状态</th>
				<th>记账顺序</th>
				<%--<shiro:hasPermission name="account:merchantAccountRela:edit"><th>操作</th></shiro:hasPermission>--%>
				<th>操作</th>
			</tr>
		</thead>
		<c:set var="M_LENGTH" value="0"></c:set>
		<c:forEach items="${merchantAccountRelaMap}" var="map">
			<c:set var="M_LENGTH" value="${M_LENGTH+1}"></c:set>
			<c:set var="I_NUMBER" value="0"></c:set>
			<c:set var="TR_STYLE" value="background-color: #ffffff;"></c:set>
			<c:set var="KEY_STR" value="${fn:split(map.key, '_')}"></c:set>
				<c:forEach items="${map.value}" var="v">
					<c:if test="${M_LENGTH%2 eq 0}">
						<c:set var="TR_STYLE" value="background-color: #f1bc80;"></c:set>
					</c:if>
					<tr>
					<c:set var="I_NUMBER" value="${I_NUMBER + 1}"></c:set>
					<c:if test="${I_NUMBER eq 1}">
						<td rowspan="${fn:length(map.value)+1}">
							<c:if test="${fn:length(KEY_STR) eq 2}">
								${fns:getDictLabel(KEY_STR[0], 'TransType', '')} - ${fns:getDictLabel(KEY_STR[1], 'AccountMark', '')}
							</c:if>
						</td>
					</c:if>
					<td>${v.relaId}</td>
					<td>${fns:getDictLabel(v.transType, 'TransType', '-')}</td>
					<td>${v.transSubType}</td>
					<td>${fns:getDictLabel(v.accountType, 'InternalAccountType', '')}</td>
					<td>
						<c:if test="${'C' eq v.balanceDirection}">贷</c:if>
						<c:if test="${'D' eq v.balanceDirection}">借</c:if>
					</td>
					<td>${fns:getDictLabel(v.balanceChangeType, 'BalanceChangeType', '')}</td>
					<td>
						<c:if test="${v.isEnable eq 'Y'}">启用</c:if>
						<c:if test="${v.isEnable eq 'N'}">禁用</c:if>
					</td>
					<td>${v.sequence}</td>
					<shiro:hasPermission name="account:merchantAccountRela:edit">
						<td>
							<a style="cursor:pointer;" class="checkPass" value-url="${ctx}/account/merchantAccountRela/form?id=${v.relaId}">修改</a>
							<a style="cursor:pointer;" class="checkPass" value-url="${ctx}/account/merchantAccountRela/delete?id=${v.relaId}">删除</a>
						</td>
					</shiro:hasPermission>
					</tr>
				</c:forEach>
			<c:set var="TD_COLSPAN_NUMBER" value="10"></c:set>
			<shiro:hasPermission name="account:merchantAccountRela:edit">
				<c:set var="TD_COLSPAN_NUMBER" value="11"></c:set>
			</shiro:hasPermission>
			<%--<tr><td colspan="${TD_COLSPAN_NUMBER}" align="left">--%>
				<%--<a href="${ctx}/account/merchantAccountRela/add?id=${v.relaId}&transType=${KEY_STR[0]}&transSubType=${KEY_STR[1]}">新增</a>--%>
				<%--<td></tr>--%>
			<tr><td colspan="${TD_COLSPAN_NUMBER}">&nbsp;<td></tr>
		</c:forEach>
		</tbody>
	</table>
	<%--<div class="pagination">${page}</div>--%>
</body>
</html>