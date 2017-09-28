<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>账户转账管理</title>
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
		<%--function onSubmit(){--%>
			<%--$("#searchForm").attr("action","${ctx}/account/merchantTransfer/");--%>
			<%--validateFrom.validate($("#searchForm"));--%>
		<%--}--%>
		function onSq(){
			window.location.href="${ctx}/account/merchantTransfer/form";
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/account/merchantTransfer/">账户转账列表</a></li>
		<%--<shiro:hasPermission name="account:merchantTransfer:edit"><li><a href="${ctx}/account/merchantTransfer/form">账户转账添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="merchantTransfer" action="${ctx}/account/merchantTransfer/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<%--<li><label>交易订单号：</label>--%>
				<%--<form:input path="transferId" htmlEscape="false" maxlength="26" class="input-medium"/>--%>
			<%--</li>--%>
			<%--<li><label>发起人：</label>--%>
				<%--<form:input path="creator" htmlEscape="false" maxlength="50" class="input-medium"/>--%>
			<%--</li>--%>
			<%--<li><label>审核人：</label>--%>
				<%--<form:input path="auditor" htmlEscape="false" maxlength="50" class="input-medium"/>--%>
			<%--</li>--%>
			<li><label>转出商户编码：</label>
				<form:input path="dMerchantId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>转出商户账号：</label>
				<form:input path="dAccountId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>转出企业名称：</label>
				<form:input path="dAccountName" htmlEscape="false" maxlength="256" class="input-medium"/>
			</li>
			<li><label>转入商户编码：</label>
				<form:input path="cMerchantId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>转入商户账号：</label>
				<form:input path="cAccountId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>转入企业名称：</label>
				<form:input path="cAccountName" htmlEscape="false" maxlength="256" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${merchantTransfer.beginCreateTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${merchantTransfer.endCreateTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<shiro:hasPermission name="account:merchantTransfer:edit">
					<li class="btns"><input id="btnSubmit2" class="btn btn-primary" onclick="onSq()" type="button" value="申请转账"/></li>
				</shiro:hasPermission>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr >
				<th rowspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">交易订单号</th>
				<th colspan="3" style="text-align: center;vertical-align: middle;color: #46A3FF;">转出方</th>
				<th colspan="3" style="text-align: center;vertical-align: middle;color: #46A3FF;">转入方</th>
				<th rowspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">转账金额</th>
				<th rowspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">发起人</th>
				<th rowspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">审核人</th>
				<th rowspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">发起时间</th>
				<th rowspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">审核时间</th>
				<th rowspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">单据状态</th>
				<th rowspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">申请来源</th>
				<shiro:hasPermission name="account:merchantTransfer:edit">
					<th rowspan="2" style="text-align: center;vertical-align: middle;color: #46A3FF;">操作</th>
				</shiro:hasPermission>
			</tr>
			<tr>
				<th  style="text-align: center;vertical-align: middle;color: #46A3FF;">商户编码</th>
				<th  style="text-align: center;vertical-align: middle;color: #46A3FF;">商户账号</th>
				<th  style="text-align: center;vertical-align: middle;color: #46A3FF;">企业名称</th>
				<th  style="text-align: center;vertical-align: middle;color: #46A3FF;">商户编码</th>
				<th  style="text-align: center;vertical-align: middle;color: #46A3FF;">商户账号</th>
				<th  style="text-align: center;vertical-align: middle;color: #46A3FF;">企业名称</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="mer">
			<tr>
				<td><a href="${ctx}/account/merchantTransfer/getByAdjustId?id=${mer.transferId}">${mer.transferId}</a></td>
				<td>${mer.dMerchantId}</td>
				<td>${mer.dAccountId}</td>
				<td>${mer.dAccountName}</td>
				<td>${mer.cMerchantId}</td>
				<td>${mer.cAccountId}</td>
				<td>${mer.cAccountName}</td>
				<td style="text-align: right;"><fmt:formatNumber value="${mer.transferAmount}" pattern="￥#,##0.00"/></td>
				<td>${mer.creator}</td>
				<td>
					<c:choose>
						<c:when test="${not empty mer.auditor}">
							${mer.auditor}
						</c:when>
						<c:otherwise>-</c:otherwise>
					</c:choose>
				</td>
				<td><fmt:formatDate value="${mer.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<c:choose>
						<c:when test="${not empty mer.auditTime}">
							<fmt:formatDate value="${mer.auditTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
						</c:when>
						<c:otherwise>-</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:if test="${'AUDING' eq mer.status}">未审核</c:if>
					<c:if test="${'REVOKE' eq mer.status}">已撤销</c:if>
					<c:if test="${'REFUSE' eq mer.status}">审核拒绝</c:if>
					<c:if test="${'ADOPT' eq mer.status}">审核通过</c:if>
				</td>
				<td>${fns:getDictLabel(mer.opResource, 'AllowSystemType', '')}</td>
				<shiro:hasPermission name="account:merchantTransfer:edit"><td>
					<c:if test="${'AUDING' eq mer.status}">
						<a href="${ctx}/account/merchantTransfer/getByAdjustId?id=${mer.transferId}&adjust=true">审核</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>