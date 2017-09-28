<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/modules/sys/validation.jsp"%>
<html>
<head>
	<title>merchant管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/validateNum.js"></script>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function search(){
			var flag = validatePreventInject($("#merchantCompanyName").val(),"商户公司名称输入不合法!");
			var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})$/;
			var email = $("#merchantLoginName").val();
			if(null != email && email != ""){
				if(!reg.test(email)){
					$("#messageBox").text("商户账号输入不合法!");
					return false;
				}
			}
			return flag;
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/merchant/merchantProductRate/">产品配置列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="merchantProductRate" action="${ctx}/merchant/merchantProductRate/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户编码：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="10" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}
					else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label>商户公司名称：</label>
				<form:input path="merchantCompanyName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>商户账号：</label>
				<form:input path="merchantLoginName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form">
			<li><label>状态：</label>
				<form:select id="status" path="status" class="input-medium" style="width:178px;">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getEnumList('commonStatus')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>签约方式：</label>
				<form:select id="contractType" path="contractType" class="input-medium" style="width:178px;">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getEnumList('contractType')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>审核状态：</label>
				<form:select id="rateAudit" path="rateAudit" class="input-medium" style="width:178px;">
					<form:option value="" label="全部"/>
					<c:forEach items="${rateAudit}" var="rateAuditFor">
						<form:option value="${rateAuditFor.value}" label="${rateAuditFor.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" onclick="return search()" value="查询"/></li>
			<li class="clearfix"></li>
			<div id="messageBox" style="font-size: 15px; color: red;"></div>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户编码</th>
				<th>商户公司名称</th>
				<th>商户账号</th>
				<th>业务类型</th>
				<th>产品名称</th>
				<th>合同有效时间</th>
				<th>签约方式</th>
				<th>费率审核状态</th>
				<th>状态</th>
				<shiro:hasPermission name="merchant:merchantRateNew:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchantProductRate">
			<tr>
				<td>${merchantProductRate.merchantId}</td>
				<td>${merchantProductRate.merchantCompanyName}</td>
				<td>${merchantProductRate.merchantLoginName}</td>
				<td>${merchantProductRate.businessType}</td>
				<td>${merchantProductRate.productName}</td>
				<td><fmt:formatDate value="${merchantProductRate.ruleBeginTime}" pattern="yyyy-MM-dd"/>
					<c:if test="${!empty merchantProductRate.ruleBeginTime}">到</c:if>
				<fmt:formatDate value="${merchantProductRate.ruleEndTime}" pattern="yyyy-MM-dd"/></td>
				<td>${merchantProductRate.contractType}</td>
				<td <c:choose>
					<c:when test="${merchantProductRate.rateAudit=='审核通过'}">
						style="background:#6cf683"
					</c:when>
					<c:when test="${merchantProductRate.rateAudit=='审核不通过'}">
						style="background:red"
					</c:when>
				</c:choose>>${merchantProductRate.rateAudit}</td>
				<td  <c:choose>
					<c:when test="${merchantProductRate.status=='启用'}">
						style="background:#6cf683"
					</c:when>
					<c:when test="${merchantProductRate.status=='禁用'}">
						style="background:red"
					</c:when>
				</c:choose>>${merchantProductRate.status}</td>
				<shiro:hasPermission name="merchant:merchantRateNew:edit"><td>
				    <a style="cursor:pointer;" class="checkPass"
                       value-url="${ctx}/merchant/merchantProductRate/form?id=${merchantProductRate.id}" >修改</a>
    				<%-- <c:if test="${merchantProductRate.status == '启用'}">
    					<a href="${ctx}/merchant/merchantProductRate/status?id=${merchantProductRate.id}&status=DISABL">禁用</a>
    				</c:if>   
    				<c:if test="${merchantProductRate.status == '禁用'}">
    					<a href="${ctx}/merchant/merchantProductRate/status?id=${merchantProductRate.id}&status=ENABLE">启用</a>
    				</c:if> --%>
    				<c:if test="${merchantProductRate.productCode != 'CP04'}">
	    				<a href="${ctx}/merchant/merchantRateNew?rateId=${merchantProductRate.id}">费率详情</a>
    				</c:if>
    				<a href="${ctx}/merchant/merchantProductRate/detail?id=${merchantProductRate.id}">详情</a>
					<a href="${ctx}/merchant/merchantProductRate/delete?id=${merchantProductRate.id}" onclick="return confirmx('确认要删除该费率吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>