<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>通知列表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
        function deleteselect(){
            $("#searchForm").find("input[type='text']").val("");
        }
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
	<style>

		.scs{

			width:200px;font-size:12px;

			border:0px solid #ddd;

			overflow:hidden;

			text-overflow:ellipsis;

			white-space:nowrap;}

	</style>


</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cbms/cbmsNofityRecord/">通知列表</a></li>
		<%--<shiro:hasPermission name="cbms:cbmsOrderSum:edit"><li><a href="${ctx}/cbms/cbmsOrderSum/form">导入文件查询添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="cbmsNofityRecord" action="${ctx}/cbms/cbmsNofityRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户编码：</label>
				<form:input  path="merchantId" htmlEscape="false" maxlength="64" class="input-medium" onkeyup="value=value.replace(/[^\d]/g,'')"/>
			</li>
			<li><label>导入批次号：</label>
				<form:input  path="importBatchNo" htmlEscape="false" maxlength="64" class="input-medium" onKeyUp="value=value.replace(/[^\d\a-z\A-Z]/g,'')"/>
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
				<th>商户编号</th>
				<th>导入批次号</th>
				<th>通知时间</th>
				<th>返回结果</th>
				<th>通知内容</th>
				<th>通知次数</th>
				<th>创建时间</th>
				<th>通知地址</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="notifyRecord">
			<tr>
				<td>
					<c:out value="${notifyRecord.merchantId}"/>
				</td>
				<td>
					<c:out value="${notifyRecord.importBatchNo}"/>
				</td>
				<td>
					<fmt:formatDate value="${notifyRecord.notifyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<div class="scs" title="<c:out value="${notifyRecord.notifyResponse}"/>" style=""> <c:out value="${notifyRecord.notifyResponse}"/></div>
				</td>
				<td>
					<div class="scs" title="<c:out value="${notifyRecord.notifyRequestParams}"/>" style=""> <c:out value="${notifyRecord.notifyRequestParams}"/></div>
				</td>
				<td>
					<c:out value="${notifyRecord.notifyNumber}"/>
				</td>
				<td>
					<fmt:formatDate value="${notifyRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<c:out value="${notifyRecord.notifyUrl}"/>
				</td>
				<td>
					<c:out value="${notifyRecord.status}"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>