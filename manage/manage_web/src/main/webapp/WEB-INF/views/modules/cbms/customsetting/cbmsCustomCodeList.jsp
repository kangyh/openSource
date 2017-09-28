<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>海关代码信息管理</title>
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
            $("#pchinaName").find("option").removeAttr("selected");
            $("#pchinaName").find("option:eq(0)").attr("selected", "selected");
            $(".select2-chosen:eq(0)").text($("#pchinaName option[selected]").text());

        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cbms/cbmsCustomCode/">海关代码信息列表</a></li>

	</ul>
	<form:form id="searchForm" modelAttribute="cbmsCustomCodeSuper" action="${ctx}/cbms/cbmsCustomCode/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>海关名称：</label>
				<form:select path="pchinaName" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options id="pchinaName" items="${customCodesList}" itemLabel="chinaName" itemValue="chinaName" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>关区名称：</label>
				<form:input path="chinaName" htmlEscape="false" maxlength="100" class="input-medium" onkeyup="value=value.replace(/[^\u4E00-\u9FA5\a-z\A-Z\.]/g,'')"/>
			</li>
			<li><label>关区代码：</label>
				<form:input path="customCode" htmlEscape="false" maxlength="100" class="input-medium" onkeyup="value=value.replace(/[^\d]/g,'')"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="清空" onclick="deleteselect()"/></li>
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
				<th>关区代码</th>
				<th>关区名称</th>
				<th>创建时间</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cbmsCustomCodeSuper" varStatus="status">
			<tr>
				<td>
						${ status.index + 1}
				</td>
				<td>
						${cbmsCustomCodeSuper.pcustomCode}
				</td>
				<td>
					${cbmsCustomCodeSuper.pchinaName}
				</td>
				<td>
					${cbmsCustomCodeSuper.customCode}
				</td>
				<td>
						${cbmsCustomCodeSuper.chinaName}
				</td>
				<td>
					<fmt:formatDate value="${cbmsCustomCodeSuper.createdTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
						${cbmsCustomCodeSuper.status}
				</td>
				<%--<shiro:hasPermission name="cbms:cbmsCustomCode:edit"><td>
    				<a href="${ctx}/cbms/cbmsCustomCode/form?id=${cbmsCustomCode.id}">修改</a>
					<a href="${ctx}/cbms/cbmsCustomCode/delete?id=${cbmsCustomCode.id}" onclick="return confirmx('确认要删除该海关代码信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>