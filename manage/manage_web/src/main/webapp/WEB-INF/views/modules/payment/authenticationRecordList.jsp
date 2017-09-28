<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>鉴权记录管理</title>
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
		<li class="active"><a href="${ctx}/payment/authenticationRecord/">鉴权记录列表</a></li>
		<shiro:hasPermission name="payment:authenticationRecord:edit"><li><a href="${ctx}/payment/authenticationRecord/form">鉴权记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="authenticationRecord" action="${ctx}/payment/authenticationRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>鉴权记录ID：</label>
				<form:input path="authRecordId" htmlEscape="false" maxlength="26" class="input-medium"/>
			</li>
			<li><label>鉴权交易号：</label>
				<form:input path="transNo" htmlEscape="false" maxlength="26" class="input-medium"/>
			</li>
			<li><label>商户ID：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>鉴权状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('AuthenticationStatus')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>鉴权类型：</label>
				<form:select path="description" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('AuthType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>创建时间：</label>
				<input name="beginPayTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="${authenticationRecord.beginPayTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input name="endPayTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="${authenticationRecord.endPayTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>商户订单号：</label>
				<form:input path="merchantOrderNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>银行卡类型：</label>
				<form:input path="bankcardType" htmlEscape="false" maxlength="6" class="input-medium"/>
			</li>
			<li><label>银行卡卡号：</label>
				<form:input path="bankcardNo" htmlEscape="false" maxlength="256" class="input-medium"/>
			</li>
			<li><label>持卡人姓名：</label>
				<form:input path="bankcardOwner" htmlEscape="false" maxlength="256" class="input-medium"/>
			</li>
			<li><label>身份证号：</label>
				<form:input path="bankcardOwnerIdcard" htmlEscape="false" maxlength="256" class="input-medium"/>
			</li>
			<li><label>手机号：</label>
				<form:input path="bankcardOwnerMobile" htmlEscape="false" maxlength="256" class="input-medium"/>
			</li>
			<li><label>通道合作方：</label>
				<form:select id="channelCode" path="channelCode" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="KOALAB" label="考拉"/>
					<form:option value="QAREDQ" label="岂安"/>
					<form:option value="HEEPAY" label="汇付宝"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>鉴权记录ID</th>
				<th>鉴权交易号</th>
				<th>商户ID</th>
				<th>商户登录账号</th>
				<th>商户公司</th>
				<th>鉴权状态</th>
				<th>鉴权类型</th>
				<th>创建时间</th>
				<th>商户订单号</th>
				<th>银行名称</th>
				<th>银行卡类型</th>
				<th>银行卡卡号</th>
				<th>持卡人姓名</th>
				<th>身份证号</th>
				<th>手机号</th>
				<th>通道代码</th>
				<th>手续费</th>
				<shiro:hasPermission name="payment:authenticationRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="authenticationRecord">
			<tr>
				<td>
					${authenticationRecord.authRecordId}
				</td>
				<td>
					${authenticationRecord.transNo}
				</td>
				<td>
					${authenticationRecord.merchantId}
				</td>
				<td>
					${authenticationRecord.merchantLoginName}
				</td>
				<td>
					${authenticationRecord.merchantCompany}
				</td>
				<td <c:if test="${authenticationRecord.status=='SUCCES'}">style="background:${success_background}"</c:if>>
					${fns:getDictLabel(authenticationRecord.status, 'AuthenticationStatus', authenticationRecord.status)}
				</td>
				<td>
					${fns:getDictLabel(authenticationRecord.description, 'AuthType', authenticationRecord.description)}
				</td>
				<td>
					<fmt:formatDate value="${authenticationRecord.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${authenticationRecord.merchantOrderNo}
				</td>
				<td>
					${authenticationRecord.bankName}
				</td>
				<td>
					${authenticationRecord.bankcardType}
				</td>
				<td>
					${fns:hiddenBankcardNo(authenticationRecord.bankcardNo)}
				</td>
				<td>
					${authenticationRecord.bankcardOwner}
				</td>
				<td>
					${fns:hiddenIdCardNo(authenticationRecord.bankcardOwnerIdcard)}
				</td>
				<td>
					${fns:hiddenMobileNo(authenticationRecord.bankcardOwnerMobile)}
				</td>
				<td>
					${authenticationRecord.channelCode}
				</td>
				<td>
					${authenticationRecord.feeAmount}
				</td>
				<shiro:hasPermission name="payment:authenticationRecord:edit"><td>
    				<a href="${ctx}/payment/authenticationRecord/form?id=${authenticationRecord.id}">修改</a>
					<a href="${ctx}/payment/authenticationRecord/delete?id=${authenticationRecord.id}" onclick="return confirmx('确认要删除该鉴权记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>