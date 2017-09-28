<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>有效合约法务审核</title>
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
        //搜索框 清空
        function onClear(){
            $("#searchForm").find("input[type='text']").val("");
            $(".select2-chosen:eq(0)").text($("#legalAuditStatus option[selected]").text());
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/hgms/hgmsValidContractLegalAudit/">合约法务列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="hgmsValidContract" action="${ctx}/hgms/hgmsValidContractLegalAudit" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>合约编码：</label>
				<form:input path="contractId" htmlEscape="false" maxlength="10" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}
					else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label>商户编码：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="10" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}
					else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label>上级商户编码：</label>
				<form:input path="superiorId" htmlEscape="false" maxlength="10" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}
					else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label>合同名称：</label>
				<form:input path="contractName" htmlEscape="false" maxlength="256" class="input-medium"/>
			</li>
			<li><label>有效期：</label>
				<input id="validityBeginTime" name="validityBeginTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${hgmsValidContract.validityBeginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,maxDate:'#F{$dp.$D(\'validityEndTime\')}'});"/> -
				<input id="validityEndTime" name="validityEndTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${hgmsValidContract.validityEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,minDate:'#F{$dp.$D(\'validityBeginTime\')}'});"/>
			</li>
			<li><label>状态：</label>
				<form:select path="legalAuditStatus" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getEnumList('routeStatus')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
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
			<th>合约编码</th>
			<th>商户编码</th>
			<th>公司名称</th>
			<th>上级商户编码</th>
			<th>上级商户名称</th>
			<th>合同名称</th>
			<th>业务名称</th>
			<th>服务项名称</th>
			<th>有效期开始时间</th>
			<th>有效期结束时间</th>
			<th>合约状态</th>
			<th>审核状态</th>
			<th>创建时间</th>
			<shiro:hasPermission name="hgms:hgmsValidContract:edit"><th>操作</th></shiro:hasPermission>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hgmsValidContract">
			<tr>
				<td>
						${hgmsValidContract.contractId}
				</td>
				<td>
						${hgmsValidContract.merchantId}
				</td>
				<td>
						${hgmsValidContract.companyName}
				</td>
				<td>
						${hgmsValidContract.superiorId}
				</td>
				<td>
						${hgmsValidContract.superiorName}
				</td>
				<td>
						${hgmsValidContract.contractName}
				</td>
				<td>
						${hgmsValidContract.businessName}
				</td>
				<td>
						${hgmsValidContract.serviceName}
				</td>
				<td>
					<fmt:formatDate value="${hgmsValidContract.validityBeginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${hgmsValidContract.validityEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
						${hgmsValidContract.contractStatus}
				</td>
				<td>
						${hgmsValidContract.legalAuditStatus}
				</td>
				<td>
					<fmt:formatDate value="${hgmsValidContract.enteringTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="hgms:hgmsValidContractLegalAudit:edit"><td>
					<c:if test="${hgmsValidContract.legalAuditStatus == '待审核'}">
						<a href="${ctx}/hgms/hgmsValidContractLegalAudit/form?id=${hgmsValidContract.contractId}">审核</a>
					</c:if>
					<c:if test="${hgmsValidContract.legalAuditStatus == '审核不通过'}">
						<a href="${ctx}/hgms/hgmsValidContractLegalAudit/edit?id=${hgmsValidContract.contractId}">修改</a>
					</c:if>
					<c:if test="${hgmsValidContract.legalAuditStatus == '审核通过'}">
						<a href="${ctx}/hgms/hgmsValidContractLegalAudit/detail?id=${hgmsValidContract.contractId}">查看</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>