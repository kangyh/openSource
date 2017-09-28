<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>业务管理管理</title>
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
		<li class="active"><a href="${ctx}/hgms/hgmsBusiness/">业务管理列表</a></li>
		<shiro:hasPermission name="hgms:hgmsBusiness:edit"><li><a href="${ctx}/hgms/hgmsBusiness/form">业务管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="hgmsBusiness" action="${ctx}/hgms/hgmsBusiness/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>业务名称：</label>
				<form:input path="businessName" htmlEscape="false" maxlength="64" class="input-medium"/>
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
				<th>业务名称</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th>状态</th>
				<shiro:hasPermission name="hgms:hgmsBusiness:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hgmsBusiness">
			<tr>
				<td><a href="${ctx}/hgms/hgmsBusiness/form?id=${hgmsBusiness.id}">
					${hgmsBusiness.businessId}
				</a></td>
				<td>
					${hgmsBusiness.businessName}
				</td>
				<td>
					${hgmsBusiness.inputuserName}
				</td>
				<td>
					<fmt:formatDate value="${hgmsBusiness.createdTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${hgmsBusiness.status}
				</td>
				<shiro:hasPermission name="hgms:hgmsBusiness:edit"><td>
					<c:if test="${hgmsBusiness.status == '启用'}">
						<a href="${ctx}/hgms/hgmsBusiness/status?id=${hgmsBusiness.businessId}&status=DISABL">禁用</a>
					</c:if>
					<c:if test="${hgmsBusiness.status == '禁用'}">
						<a href="${ctx}/hgms/hgmsBusiness/status?id=${hgmsBusiness.businessId}&status=ENABLE">启用</a>
					</c:if>
    				<a href="${ctx}/hgms/hgmsBusiness/form?id=${hgmsBusiness.businessId}">修改</a>
					<a href="${ctx}/hgms/hgmsServiceItem/?businessId=${hgmsBusiness.businessId}">服务项明细</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>