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

        //清空
        function onClear(){
        	$("form :input").not(":button, :submit, :reset, :hidden").val("").removeAttr("checked").remove("selected");
        	$("#searchForm").submit();
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/merchant/merchantProductRate/rateAudit">费率详情列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="merchantProductRate" action="${ctx}/merchant/merchantProductRate/rateAudit" method="post" class="breadcrumb form-search">
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
			<li><label>状态：</label>
				<form:select id="status" path="status" class="input-xlarge" style="width:180px;">
					<form:option value="" label="-选择状态-"/>
					<form:options items="${fns:getEnumList('commonStatus')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
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
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清除"/></li>
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
				<th>费率状态</th>
				<th>状态</th>
				<shiro:hasPermission name="merchant:merchantProductRate:edit"><th>审核操作</th></shiro:hasPermission>
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
					<c:if test="${not empty merchantProductRate.ruleBeginTime}">到</c:if>
				<fmt:formatDate value="${merchantProductRate.ruleEndTime}" pattern="yyyy-MM-dd"/></td>
				<td <c:choose>
					<c:when test="${merchantProductRate.status=='启用'}">
						style="background:#6cf683"
					</c:when>
					<c:when test="${merchantProductRate.status=='禁用'}">
						style="background:#707c9b"
					</c:when>
				</c:choose>>
						${merchantProductRate.status}
				</td>
				<td <c:choose>
					<c:when test="${merchantProductRate.rateAudit=='审核通过'}">
						style="background:#6cf683"
					</c:when>
					<c:when test="${merchantProductRate.rateAudit=='审核不通过'}">
						style="background:red"
					</c:when>
					<c:when test="${merchantProductRate.rateAudit=='未审核'}">
						style="background:#707c9b"
					</c:when>
				</c:choose>>
						${merchantProductRate.rateAudit}
				</td>
				<shiro:hasPermission name="merchant:merchantProductRate:edit"><td>
					<c:if test="${merchantProductRate.rateAudit=='未审核'}">
						<a style="cursor:pointer;" class="checkPass" value-url="${ctx}/merchant/merchantProductRate/rateAudit/jump/${merchantProductRate.id}" >费率审核</a>
					</c:if>
					<c:if test="${merchantProductRate.rateAudit!='未审核'}">
						<a href="${ctx}/merchant/merchantProductRate/rateAudit/jump/${merchantProductRate.id}?rateAuditValue=S">查看</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>