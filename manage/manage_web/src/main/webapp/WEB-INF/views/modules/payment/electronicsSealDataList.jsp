<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>签宝账户印章模板管理</title>
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
		<li class="active"><a href="${ctx}/payment/electronicsSealData/">签宝账户印章模板列表</a></li>
		<%--<shiro:hasPermission name="payment:electronicsSealData:edit"><li><a href="${ctx}/payment/electronicsSealData/form">签宝账户印章模板添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="electronicsSealData" action="${ctx}/payment/electronicsSealData/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户账号：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>商户名称：</label>
				<form:input path="merchantName" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			<li><label>签宝账号：</label>
				<form:input path="sealAccountId" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<%--<li><label>印章样式：</label>--%>
				<%--<form:input path="sealType" htmlEscape="false" maxlength="32" class="input-medium"/>--%>
			<%--</li>--%>
			<%--<li><label>印章数据：</label>--%>
				<%--<form:input path="sealData" htmlEscape="false" class="input-medium"/>--%>
			<%--</li>--%>
			<%--<li><label>创建时间：</label>--%>
				<%--<input name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"--%>
					<%--value="<fmt:formatDate value="${electronicsSealData.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"--%>
					<%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - --%>
				<%--<input name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"--%>
					<%--value="<fmt:formatDate value="${electronicsSealData.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"--%>
					<%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>--%>
			<%--</li>--%>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户账号</th>
				<th>商户名称</th>
				<th>签宝账号</th>
				<th>印章样式</th>
				<th>印章数据</th>
				<th>创建时间</th>
				<th>备注</th>
				<shiro:hasPermission name="payment:electronicsSealData:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="electronicsSealData">
			<tr>
				<td>
					<%--<a href="${ctx}/payment/electronicsSealData/form?id=${electronicsSealData.id}">--%>
					${electronicsSealData.merchantId}
				<%--</a>--%>
				</td>
				<td>
					${electronicsSealData.merchantName}
				</td>
				<td>
					${electronicsSealData.sealAccountId}
				</td>
				<td>
					${electronicsSealData.sealType}
				</td>
				<td>
					<c:choose>
						<c:when test="${not empty electronicsSealData.sealData}">
							${fn:substring(electronicsSealData.sealData, 0, 50)}...
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<fmt:formatDate value="${electronicsSealData.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${electronicsSealData.remark}
				</td>
				<shiro:hasPermission name="payment:electronicsSealData:edit"><td>
    				<a href="${ctx}/payment/electronicsSealData/form?id=${electronicsSealData.id}">修改</a>
					<a href="${ctx}/payment/electronicsSealData/delete?id=${electronicsSealData.id}" onclick="return confirmx('确认要删除该签宝账户印章模板吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>