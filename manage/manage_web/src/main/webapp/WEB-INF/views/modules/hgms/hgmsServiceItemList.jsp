<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>服务项管理</title>
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
		<li><a href="${ctx}/hgms/hgmsBusiness/">业务管理列表</a></li>
		<li class="active"><a href="${ctx}/hgms/hgmsServiceItem/?businessId=${businessId}">服务项列表</a></li>
		<shiro:hasPermission name="hgms:hgmsServiceItem:edit"><li><a href="${ctx}/hgms/hgmsServiceItem/form?businessId=${businessId}">服务项添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="hgmsServiceItem" action="${ctx}/hgms/hgmsServiceItem/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
            <form:hidden path="businessId" value="${businessId}" htmlEscape="false" maxlength="6" class="input-medium"/>
			<li><label>服务项名称：</label>
				<form:input path="serviceName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>创建人：</label>
				<form:input path="inputuserName" htmlEscape="false" maxlength="6" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${hgmsMerchantInfo.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,maxDate:'#F{$dp.$D(\'endCreateTime\')}'});"/> -
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${hgmsMerchantInfo.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,minDate:'#F{$dp.$D(\'beginCreateTime\')}'});"/>
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
				<th>服务项名称</th>
                <th>创建人</th>
                <th>创建时间</th>
				<th>业务名称</th>
				<th>产品编号</th>
				<th>产品名称</th>
				<th>规则建立</th>
				<th>状态</th>
				<shiro:hasPermission name="hgms:hgmsServiceItem:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hgmsServiceItem">
			<tr>
				<td><a href="${ctx}/hgms/hgmsServiceItem/form?id=${hgmsServiceItem.id}">
					${hgmsServiceItem.serviceId}
				</a></td>
				<td>
					${hgmsServiceItem.serviceName}
				</td>
                <td>
                    ${hgmsServiceItem.inputuserName}
                </td>
                <td>
                    <fmt:formatDate value="${hgmsServiceItem.createdTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
				<td>
					${hgmsServiceItem.businessName}
				</td>
				<td>
					${hgmsServiceItem.productCode}
				</td>
				<td>
					${hgmsServiceItem.productName}
				</td>
				<td>
					${hgmsServiceItem.ruleBuildType}
				</td>
				<td>
					<c:if test="${hgmsServiceItem.businessStatus == 'ENABLE'}">
						${hgmsServiceItem.status}
					</c:if>
					<c:if test="${hgmsServiceItem.businessStatus == 'DISABL'}">
						禁用
					</c:if>
				</td>
				<shiro:hasPermission name="hgms:hgmsServiceItem:edit"><td>
					<c:if test="${hgmsServiceItem.businessStatus == 'ENABLE'}">
						<c:if test="${hgmsServiceItem.status == '启用'}">
							<a href="${ctx}/hgms/hgmsServiceItem/status?id=${hgmsServiceItem.serviceId}&status=DISABL">禁用</a>
						</c:if>
						<c:if test="${hgmsServiceItem.status == '禁用'}">
							<a href="${ctx}/hgms/hgmsServiceItem/status?id=${hgmsServiceItem.serviceId}&status=ENABLE">启用</a>
						</c:if>
					</c:if>
    				<a href="${ctx}/hgms/hgmsServiceItem/form?id=${hgmsServiceItem.serviceId}">编辑</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>