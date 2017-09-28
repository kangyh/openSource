<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>

<head>
	<title>商户月结手续费管理</title>
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
        function detailQuaryPay(){
            $("#searchFormPay").submit();
        }
        function detailQuaryCollection(){
            $("#searchFormCollection").submit();
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/hgms/hgmsMerchantFeeManagementObj/">商户手续费明细查询</a></li>
	</ul>

	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户名称</th>
				<th>月份</th>
				<th>手续费</th>
				<th>业务类型</th>

				<shiro:hasPermission name="hgms:hgmsBatchCollectionRecordHistory:edit"><th>明细</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:if test="${feeManageobjPay !=null }">
			<tr>
				<td>
						${feeManageobjPay.sourceMerchantName}
				</td>
				<td>
					<fmt:formatDate value="${feeManageobjPay.createTime}" pattern="yyyy-MM"/>
				</td>

				<td>
					<fmt:formatNumber value="${feeManageobjPay.feeAount}" pattern="￥0.00" />
				</td>
				<td>
						${feeManageobjPay.flagstr}
				</td>
				<shiro:hasPermission name="hgms:hgmsBatchPayRecordHistory:view"><td>
					<form:form id="searchFormPay" modelAttribute="hgmsBatchPayRecordHistory" action="${ctx}/hgms/hgmsBatchPayRecordHistory/" method="post" >
						<input  name="merchantCompany" type="hidden" value="${feeManageobjPay.sourceMerchantName}"/>
						<input name="beginCreateTime" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${feeManageobjPay.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						/>
						<input name="endCreateTime" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${feeManageobjPay.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						/>
						<a href = "JavaScript:void(0)" onclick = "detailQuaryPay()">详情</a>
					</form:form>
				</td></shiro:hasPermission>
			</tr>
		</c:if>
		</tbody>
		<tbody>
		<c:if test="${feeManageobjCollection !=null }">
			<tr>
				<td>
					${feeManageobjCollection.sourceMerchantName}
				</td>
				<td>
					<fmt:formatDate value="${feeManageobjCollection.createTime}" pattern="yyyy-MM"/>
				</td>

				<td>
					<fmt:formatNumber value="${feeManageobjCollection.feeAount}" pattern="￥0.00" />
				</td>

				<td>
						${feeManageobjCollection.flagstr}
				</td>
				<shiro:hasPermission name="hgms:hgmsBatchCollectionRecordHistory:view"><td>
					<form:form id="searchFormCollection" modelAttribute="hgmsBatchCollectionRecordHistory" action="${ctx}/hgms/hgmsBatchCollectionRecordHistory/" method="post" >
						<input  name="merchantCompany" type="hidden" value="${feeManageobjCollection.sourceMerchantName}"/>
						<input name="beginCreateTime" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${feeManageobjCollection.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						/>
						<input name="endCreateTime" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${feeManageobjCollection.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						/>
						<a href = "JavaScript:void(0)" onclick = "detailQuaryCollection()">详情</a>
					</form:form>
				</td></shiro:hasPermission>
			</tr>
		</c:if>
		</tbody>
	</table>
	<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	<%--<div class="pagination">${page}</div>--%>
</body>
</html>