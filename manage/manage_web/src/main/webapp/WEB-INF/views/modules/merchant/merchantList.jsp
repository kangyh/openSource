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
			var flag = validatePreventInject($("#companyName").val(),"商户公司名称输入不合法!");
			var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})$/;
			var email = $("#loginName").val();
			if(null != email && email != ""){
				if(!reg.test(email)){
					$("#messageBox").text("商户账号输入不合法!");
					return false;
				}
			}
			return flag;
		}
		//文件导出
		function onExport(){
			var actionURL = $("#searchForm").attr("action");
			$("#searchForm").attr("action",$("#searchForm").attr("action")+"export");
			var flag = validatePreventInject($("#companyName").val(),"商户公司名称输入不合法!");
			if(flag){
				$("#searchForm").submit();
				$("#searchForm").attr("action",actionURL);
			}
		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/merchant/merchant/">商户列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="merchant" action="${ctx}/merchant/merchant/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户编码：</label>
				<form:input path="userId" htmlEscape="false" maxlength="10" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}
					else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label style="width:100px">商户公司名称：</label>
				<form:input path="companyName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label style="width:100px">商户账号：</label>
				<form:input id="loginName" path="loginName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${merchant.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,maxDate:'#F{$dp.$D(\'endCreateTime\')}'});"/> -
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${merchant.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,minDate:'#F{$dp.$D(\'beginCreateTime\')}'});"/>
			</li>
			<li><label>来源：</label>
				<form:select path="source" class="input-medium ">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getEnumList('allowSystemType')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select path="userStatus" class="input-medium ">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getEnumList('merchantStatus')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>商户标识：</label>
				<form:select path="merchantFlag" htmlEscape="false" class="input-medium">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getEnumList('merchantFlag')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" onclick="return search()" value="查询"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" onclick="onExport()" value="导出"/></li>
			<li class="clearfix"></li>
			<div id="messageBox" style="font-size: 15px; color: red;"></div>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户编码</th>
				<th>商户账号</th>
				<th>商户公司名称</th>
				<th>维系员工</th>
				<th>联系人</th>
				<th>商户标识</th>
				<th>状态</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchant">
			<tr>
				<td>${merchant.userId}</td>
				<td>${merchant.loginName}</td>
				<td>${merchant.companyName}</td>
				<td>${fns:getUserById(merchant.inchargerId).name}</td>
				<td>${merchant.contactor}</td>
				<td>${fns:getDictLabel(merchant.merchantFlag, 'MerchantFlag', '')}</td>
				<td>${merchant.userStatus}</td>
				<td><fmt:formatDate value="${merchant.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
    				<a href="${ctx}/merchant/merchant/detail?id=${merchant.userId}">查看</a>
    				<a href="${ctx}/merchant/merchantBankCard/list?merchantId=${merchant.userId}">银行卡信息</a>
    				<shiro:hasPermission name="merchant:merchant:edit">
						<a style="cursor:pointer;" class="checkPass" value-url="${ctx}/merchant/merchant/form?id=${merchant.userId}">修改</a>
	    				<c:if test="${merchant.userStatus == '正常'}">
	    					<a href="${ctx}/merchant/merchant/status?id=${merchant.userId}&userStatus=FREZED" onclick="return confirmx('确认要禁用吗？', this.href)">禁用</a>
	    				</c:if>
	    				<c:if test="${merchant.userStatus == '冻结'}">
	    					<a href="${ctx}/merchant/merchant/status?id=${merchant.userId}&userStatus=NORMAL" onclick="return confirmx('确认要启用吗？', this.href)">启用</a>
	    				</c:if>
						<a style="cursor:pointer;" class="checkPass" value-url="${ctx}/merchant/merchantRateNew/form?merchantId=${merchant.userId}&merchantCompanyName=${merchant.companyName}&merchantLoginName=${merchant.loginName}" >产品配置</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="riskManage:riskMerchantProductQuota:edit">
    				    <a style="cursor:pointer;" class="checkPass" value-url="${ctx}/riskManage/riskMerchantProductQuotaQuery/edit?merchantId=${merchant.userId}&merchantPageNum=${page.pageNo}">限额配置</a>
    				</shiro:hasPermission>
<%--     				<a href="${ctx}/merchant/settleCycleManage/form?merchantId=${merchant.userId}&merchantCompanyName=${merchant.companyName}&merchantLoginName=${merchant.loginName}">结算配置</a>
    				<a href="${ctx}/merchant/merchantAutographInfo/form?merchantId=${merchant.userId}&merchantCompanyName=${merchant.companyName}&merchantLoginName=${merchant.loginName}">签约配置</a> --%>
					<%-- <a href="${ctx}/merchant/merchant/delete?id=${merchant.userId}" onclick="return confirmx('确认要删除该merchant吗？', this.href)">删除</a> --%>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>