<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>代理商信息管理管理</title>
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
	<%--<ul class="nav nav-tabs">--%>
		<%--<li class="active"><a href="${ctx}/agent/agent/agentInfo/">代理商列表</a></li>--%>
		<%--<shiro:hasPermission name="agent:agent:agentInfo:edit"><li><a href="${ctx}/agent/agent/agentInfo/form">代理商添加</a></li></shiro:hasPermission>--%>
	<%--</ul>--%>
	<form:form id="searchForm" modelAttribute="agentInfo" action="${ctx}/agent/agent/agentInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>代理商编号：</label>
				<form:input path="agentCode" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li><label>代理商名称：</label>
				<form:input path="agentName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>负责人姓名：</label>
				<form:input path="headName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>负责人手机号：</label>
				<form:input path="headPhone" type="tel" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>法人姓名：</label>
				<form:input path="legalName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>法人手机号：</label>
				<form:input path="legalPhone" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>使用状态：</label>
				<form:select path="agentStatus" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${agentStatusList}" itemLabel="value" itemValue="name" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>创建时间：</label>
				<input name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${agentInfo.beginCreateTime}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${agentInfo.endCreateTime}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>代理商编号</th>
				<th>代理商名称</th>
				<th>代理商等级</th>
				<th>类型</th>
				<th>加入时间</th>
				<th>状态</th>
				<th>负责人姓名</th>
				<th>负责人手机号</th>
				<th>法人姓名</th>
				<th>法人手机号</th>
				<th>操作</th>
				<%--<th>操作</th>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="agentInfo">
			<tr>
				<td><%--<a href="${ctx}/agent/agent/agentInfo/form?id=${agentInfo.id}">--%>
					${agentInfo.agentCode}
				<%--</a>--%></td>
				<td>
					${agentInfo.agentName}
				</td>
				<td>
					${agentInfo.agentLevel}
				</td>
				<td>
					${agentInfo.agentType}
				</td>
				<td>
					<fmt:formatDate value="${agentInfo.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
						${agentInfo.agentStatus}
				</td>
				<td>
					${agentInfo.headName}
				</td>
				<td>
					${agentInfo.headPhone}
				</td>
				<td>
					${agentInfo.legalName}
				</td>
				<td>
					${agentInfo.legalPhone}
				</td>
				<td>
				<a href="${ctx}/agent/agent/agentInfo/detail?id=${agentInfo.id}">详情</a>
				<%--<shiro:hasPermission name="agent:agent:agentInfo:edit">--%>
					<c:if test="${(agentInfo.agentStatus == '草稿' || agentInfo.agentStatus == '审核拒绝') && agentInfo.agentLevel == 1 }">
						<a href="${ctx}/agent/agent/agentInfo/form?id=${agentInfo.id}&agentName=${agentInfo.agentName}">修改</a>
					</c:if>
					<c:if test="${agentInfo.agentStatus == '工作'}">
						<c:if test="${agentInfo.agentLevel == 1}">
							<a href="${ctx}/agent/rate/agentRate/list?agentId=${agentInfo.id}&agentName=${agentInfo.agentName}&agentCode=${agentInfo.agentCode}">配置费率</a>
						</c:if>
							<%--<a href="${ctx}/agent/agent/agentInfo/form?id=${agentInfo.id}">修改</a>--%>
						<a href="${ctx}/agent/agent/agentInfo/lock?id=${agentInfo.id}">锁定</a>
						<%--<a href="${ctx}/agent/agent/agentInfo/form?id=${agentInfo.id}">注销</a>--%>
					</c:if>
					<c:if test="${agentInfo.agentStatus == '锁定'}">
						<a href="${ctx}/agent/agent/agentInfo/unlock?id=${agentInfo.id}">解锁</a>
						<%--<a href="${ctx}/agent/agent/agentInfo/form?id=${agentInfo.id}">注销</a>--%>
					</c:if>
					<c:if test="${agentInfo.agentStatus == '修改待审'}">
						<a href="${ctx}/agent/agent/agentInfo/form?id=${agentInfo.id}">修改</a>
						<a href="${ctx}/agent/agent/agentInfo/lock?id=${agentInfo.id}">锁定</a>
						<%--<a href="${ctx}/agent/agent/agentInfo/form?id=${agentInfo.id}">注销</a>--%>
					</c:if>
						<%--<a style="cursor:pointer;" class="checkPass" value-url="${ctx}/merchant/merchantRateNew/form?merchantId=${merchant.userId}&merchantCompanyName=${merchant.companyName}&merchantLoginName=${merchant.loginName}" >产品配置</a>--%>

						<%--     				<a href="${ctx}/merchant/settleCycleManage/form?merchantId=${merchant.userId}&merchantCompanyName=${merchant.companyName}&merchantLoginName=${merchant.loginName}">结算配置</a>
                                            <a href="${ctx}/merchant/merchantAutographInfo/form?merchantId=${merchant.userId}&merchantCompanyName=${merchant.companyName}&merchantLoginName=${merchant.loginName}">签约配置</a> --%>
						<%-- <a href="${ctx}/merchant/merchant/delete?id=${merchant.userId}" onclick="return confirmx('确认要删除该merchant吗？', this.href)">删除</a> --%>
				<%--</shiro:hasPermission>--%>
				</td>
<%--				<shiro:hasPermission name="agent:agent:agentInfo:edit"><td>
    				<a href="${ctx}/agent/agent/agentInfo/form?id=${agentInfo.id}">修改</a>
					<a href="${ctx}/agent/agent/agentInfo/delete?id=${agentInfo.id}" onclick="return confirmx('确认要删除该代理商信息管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>