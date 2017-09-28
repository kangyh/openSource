<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商户日结手续费管理</title>
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
        function detailQuary(n){
            $("#searchForm"+n).submit();
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/hgms/hgmsMerchantFeeManagementObj/dayFeeList">商户日结手续费查询</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="hgmsMerchantFeeManagementObj" action="${ctx}/hgms/hgmsMerchantFeeManagementObj/dayFeeList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户名称：</label>
				<form:input path="sourceMerchantName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>日期：</label>

				<input name="createTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${hgmsMerchantFeeManagementObj.createTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="deleteselect()"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
		<%--	<th>商户编号</th>--%>
			<th>商户名称</th>
			<th>日期</th>
			<th>手续费</th>

			<shiro:hasPermission name="hgms:hgmsBatchCollectionRecordHistory:edit"><th>明细</th></shiro:hasPermission>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hgmsMerchantFeeManagementObj" varStatus="status">
			<tr>
				<%--<td>
						${hgmsMerchantFeeManagementObj.sourcemerchantid}
				</td>--%>
				<td>
						${hgmsMerchantFeeManagementObj.sourceMerchantName}
				</td>
				<td>
					<fmt:formatDate value="${hgmsMerchantFeeManagementObj.createTime}" pattern="yyyy-MM-dd"/>
				</td>

				<td>
							<fmt:formatNumber value="${hgmsMerchantFeeManagementObj.feeAount}" pattern="￥0.00" />
				</td>

				<shiro:hasPermission name="hgms:hgmsMerchantFeeManagementObj:view"><td>
					<form:form id="searchForm${ status.index + 1}" modelAttribute="hgmsMerchantFeeManagementObj" action="${ctx}/hgms/hgmsMerchantFeeManagementObj/dayDetailList" method="post" >
						<input  name="sourceMerchantName" type="hidden" value="${hgmsMerchantFeeManagementObj.sourceMerchantName}"/>
						<input name="beginCreateTime" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${hgmsMerchantFeeManagementObj.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							<%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"--%>/>
						<input name="endCreateTime" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${hgmsMerchantFeeManagementObj.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							<%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"--%>/>
						<input name="createTime" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${hgmsMerchantFeeManagementObj.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							<%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"--%>/>
						<a href = "JavaScript:void(0)" onclick = "detailQuary(${ status.index + 1})">详情</a>
					</form:form>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>