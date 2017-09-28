<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>内部账户映射管理</title>
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
		//清空
		function onClear() {
			$("#searchForm").find("input[type='text']").val("");
			//账户类型
			$("#internalAccountTypeId").find("option").removeAttr("selected");
			$("#internalAccountTypeId").find("option:eq(0)").attr("selected", "selected");
			$(".select2-chosen:eq(0)").text($("#internalAccountTypeId option[selected]").text());
			//余额方向
			$("#internalAccountBalanceDirection").find("option").removeAttr("selected");
			$("#internalAccountBalanceDirection").find("option:eq(0)").attr("selected", "selected");
			$(".select2-chosen:eq(1)").text($("#internalAccountBalanceDirection option[selected]").text());

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
		<li class="active"><a href="${ctx}/account/internalAccountMpr/">内部账户映射列表</a></li>
		<shiro:hasPermission name="account:internalAccountMpr:edit"><li><a style="cursor:pointer;" class="checkPass" value-url="${ctx}/account/internalAccountMpr/form">内部账户映射添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="internalAccountMpr" action="${ctx}/account/internalAccountMpr/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>序列号：</label>
				<form:input path="internalAccountSerialNumber" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>账户名称：</label>
				<form:input path="internalAccountName" htmlEscape="false" maxlength="256" class="input-medium"/>
			</li>
			<li><label>账户类型：</label>
				<form:select path="internalAccountTypeId" class="input-xlarge">
					<form:option value="" label="全部"/>
					<c:forEach items="${fns:getDictList('InternalAccountType')}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.label}(${wStatus.value})"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>余额方向：</label>
				<form:select path="internalAccountBalanceDirection" class="input-xlarge">
					<form:option value="" label="全部"/>
					<form:option value="0" label="借(0)"/>
					<form:option value="1" label="贷(1)"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序列号</th>
				<th>账户名称</th>
				<th>账户类型</th>
				<th>详细类型</th>
				<th>余额方向(借:0,贷:1)</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<%--<th>描述</th>--%>
				<%--<th>备注</th>--%>
				<shiro:hasPermission name="account:internalAccountMpr:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="internalAccountMpr">
			<tr>
				<td>
					<%--<a href="${ctx}/account/internalAccountMpr/form?id=${internalAccountMpr.internalAccountSerialNumber}">--%>
							${internalAccountMpr.internalAccountSerialNumber}
					<%--</a>--%>
				</td>
				<td>${internalAccountMpr.internalAccountName}</td>
				<td>${fns:getDictLabel(internalAccountMpr.internalAccountTypeId, 'InternalAccountType', '')} (${internalAccountMpr.internalAccountTypeId})</td>
				<td>${internalAccountMpr.internalAccountDetailsTypeId}</td>
				<td>
					<c:if test="${'0' eq internalAccountMpr.internalAccountBalanceDirection}">借</c:if>
					<c:if test="${'1' eq internalAccountMpr.internalAccountBalanceDirection}">贷</c:if>
				</td>
				<td><fmt:formatDate value="${internalAccountMpr.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><fmt:formatDate value="${internalAccountMpr.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<%--<td>${internalAccountMpr.description}</td>--%>
				<%--<td>${internalAccountMpr.remark}</td>--%>
				<shiro:hasPermission name="account:internalAccountMpr:edit"><td>
    				<a style="cursor:pointer;" class="checkPass" value-url="${ctx}/account/internalAccountMpr/form?id=${internalAccountMpr.internalAccountSerialNumber}">修改</a>
					<a style="cursor:pointer;" class="checkPass" value-url="${ctx}/account/internalAccountMpr/delete?id=${internalAccountMpr.internalAccountSerialNumber}">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>