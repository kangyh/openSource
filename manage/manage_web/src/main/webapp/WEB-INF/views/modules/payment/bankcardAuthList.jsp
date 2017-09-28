<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>协议代扣审批管理</title>
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
		<li class="active"><a href="${ctx}/payment/bankcardAuth/">协议代扣审批列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="bankcardAuth" action="${ctx}/payment/bankcardAuth/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>处理时间：</label>
				<input name="beginAuthTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${bankcardAuth.beginAuthTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endAuthTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${bankcardAuth.endAuthTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>商户ID：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>商户公司：</label>
				<form:input path="merchantCompany" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>汇付宝协议号：</label>
				<form:input path="authId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>商户协议号：</label>
				<form:input path="merchantOrderNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${bankcardAuth.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${bankcardAuth.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>审核状态：</label>
                <form:select path="status" class="input-medium">
                    <form:option value="" label="全部"/>
                    <form:option value="PRESGN" label="未审核"/>
                    <form:option value="REJECT" label="审核拒绝"/>
                    <form:option value="AUTSUC" label="审核成功"/>
                </form:select>
			</li>
			<li><label>协议状态：</label>
                <form:select path="ext2" class="input-medium">
                    <form:option value="" label="全部"/>
                    <form:options items="${fns:getDictList('CommonStatus')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
			</li>
			<li><label>银行卡号：</label>
				<form:input path="bankcardNo" htmlEscape="false" maxlength="256" class="input-medium"/>
			</li>
			<li><label>手机号：</label>
				<form:input path="bankcardOwnerMobile" htmlEscape="false" maxlength="256" class="input-medium"/>
			</li>
			<li><label>身份证号：</label>
				<form:input path="bankcardOwnerIdcard" htmlEscape="false" maxlength="256" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>创建时间</th>
				<th>处理时间</th>
                <th>商户ID</th>
                <th>商户公司</th>
                <th>商户用户ID</th>
                <th>汇付宝协议号</th>
                <th>商户协议号</th>
                <th>持卡人姓名</th>
                <th>银行卡号</th>
                <th>手机号</th>
                <th>身份证号</th>
                <th>协议状态</th>
                <th>协议有效期</th>
                <th>审核状态</th>
				<shiro:hasPermission name="payment:bankcardAuth:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bankcardAuth">
			<tr>
                <td>
                    <fmt:formatDate value="${bankcardAuth.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <td>
                    <fmt:formatDate value="${bankcardAuth.authTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <td>
                        ${bankcardAuth.merchantId}
                </td>
                <td>
                        ${bankcardAuth.merchantCompany}
                </td>
                <td>
                        ${bankcardAuth.merchantUserId}
                </td>
				<td>
					    ${bankcardAuth.authId}
				</td>
                <td>
                        ${bankcardAuth.merchantOrderNo}
                </td>
                <td>
                        ${bankcardAuth.bankcardOwnerName}
                </td>
                <td>
                        ${fns:hiddenBankcardNo(bankcardAuth.bankcardNo)}
                </td>
                <td>
                        ${fns:hiddenMobileNo(bankcardAuth.bankcardOwnerMobile)}
                </td>
                <td>
                        ${fns:hiddenIdCardNo(bankcardAuth.bankcardOwnerIdcard)}
                </td>
                <td>
                        ${fns:getDictLabel(bankcardAuth.ext2, "CommonStatus", bankcardAuth.ext2)}
                </td>
                <td>
                        ${bankcardAuth.protocolExpireDate}
                </td>
				<td>
					<c:choose>
						<c:when test="${bankcardAuth.status eq 'PRESGN'}">未审核</c:when>
						<c:when test="${bankcardAuth.status eq 'REJECT'}">审核拒绝</c:when>
						<c:otherwise>审核成功</c:otherwise>
					</c:choose>
				</td>
				<shiro:hasPermission name="payment:bankcardAuth:edit"><td>
                    <a href="${ctx}/payment/bankcardAuth/toAuth?authId=${bankcardAuth.authId}">协议审核</a>
                    <a href="${ctx}/payment/bankcardAuth/toLimitAmount?authId=${bankcardAuth.authId}">限额设置</a>
                    <c:if test="${bankcardAuth.ext2 eq 'DISABL' and bankcardAuth.status eq 'SUCCES'}">
                        <a href="${ctx}/payment/bankcardAuth/setEnable?authId=${bankcardAuth.authId}&enable=ENABLE">启用</a>
                    </c:if>
                    <c:if test="${bankcardAuth.ext2 eq 'ENABLE' and bankcardAuth.status eq 'SUCCES'}">
                        <a href="${ctx}/payment/bankcardAuth/setEnable?authId=${bankcardAuth.authId}&enable=DISABL">禁用</a>
                    </c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>