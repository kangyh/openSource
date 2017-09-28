<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>海关信息设置管理</title>
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
        function deleteselect(){
            $("#searchForm").find("input[type='text']").val("");


        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cbms/cbmsCustomsetting/">海关信息设置列表</a></li>
		<shiro:hasPermission name="cbms:cbmsCustomsetting:edit"><li><a href="${ctx}/cbms/cbmsCustomsetting/form">海关信息设置添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cbmsCustomsetting" action="${ctx}/cbms/cbmsCustomsetting/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>海关编码：</label>
				<form:input path="customNo" htmlEscape="false" maxlength="20" class="input-medium" onkeyup="value=value.replace(/[^\A-Z]/g,'') "/>
			</li>
			<li><label>海关名称：</label>
				<form:input path="chinaName" htmlEscape="false" maxlength="20" class="input-medium" onkeyup="value=value.replace(/[^\u4E00-\u9FA5\a-z\A-Z\.]/g,'')"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" onclick="deleteselect()" value="清空"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>海关编码</th>
				<th>海关名称</th>
				<th>支付公司海关备案号</th>
				<th>支付公司海关备案名称</th>
				<th>支付公司海关DXPID</th>
				<th>海关联系人姓名</th>
				<th>海关联系人电话</th>
				<th>海关地址</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<th>状态</th>
				<shiro:hasPermission name="cbms:cbmsCustomsetting:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cbmsCustomsetting" varStatus="status">
			<tr>
				<td><%--<a href="${ctx}/cbms/cbmsCustomsetting/form?id=${cbmsCustomsetting.id}">
					${cbmsCustomsetting.customId}
				</a>--%>${ status.index + 1}</td>
				<td>
					<c:out value="${cbmsCustomsetting.customNo}"/>
				</td>
				<td>
					<c:out value="${cbmsCustomsetting.chinaName}"/>
				</td>
				<td>
					<c:out value="${cbmsCustomsetting.recordNumber}"/>
				</td>
				<td>
					<c:out value="${cbmsCustomsetting.companyName}"/>
				</td>
				<td>
					<c:out value="${cbmsCustomsetting.companyDxpid}"/>
				</td>
				<td>
					<c:out value="${cbmsCustomsetting.customName}"/>
				</td>
				<td>
					<c:out value="${cbmsCustomsetting.customPhone}"/>
				</td>
				<td>
					<c:out value="${cbmsCustomsetting.customAddress}"/>
				</td>
				<td>
					<fmt:formatDate value="${cbmsCustomsetting.createdTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${cbmsCustomsetting.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<c:out value="${cbmsCustomsetting.status}"/>
				</td>
				<shiro:hasPermission name="cbms:cbmsCustomsetting:edit"><td>
    				<a href="${ctx}/cbms/cbmsCustomsetting/form?id=${cbmsCustomsetting.customId}">修改</a>
					<c:if test="${cbmsCustomsetting.status =='启用' }">
						<a href="${ctx}/cbms/cbmsCustomsetting/disable?id=${cbmsCustomsetting.customId}" >禁用</a>
					</c:if>
					<c:if test="${cbmsCustomsetting.status =='禁用' }">
						<a href="${ctx}/cbms/cbmsCustomsetting/enable?id=${cbmsCustomsetting.customId}" >启用</a>
					</c:if>

				</td></shiro:hasPermission>
			</tr>
		</c:forEach>

		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>