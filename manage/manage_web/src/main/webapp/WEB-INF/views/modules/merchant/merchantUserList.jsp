<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>user管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/validateNum.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnClear").on("click",function(){
				$("#id").val("");
				$("#companyName").val("");
				$("#loginName").val("");
				$("#name").val("");
				$("#status").val("");
				$("#userType").val("");
				$("#mobile").val("");
				$("#searchForm").submit(); 
			}); 
		});
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/merchant/merchantUser/">账户列表</a></li>
		<%-- <shiro:hasPermission name="merchant:merchantUser:edit"><li><a href="${ctx}/merchant/merchantUser/form">商家账户添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="merchantUser" action="${ctx}/merchant/merchantUser/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		    <li><label>商户编码：</label>
				<form:input id="id" path="id" htmlEscape="false" maxlength="10" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}
					else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label style="width:100px">商户公司名称：</label>
				<form:input id="companyName" path="companyName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label style="width:100px">商户账号：</label>
				<form:input id="loginName" path="loginName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>账号状态：</label>
				<form:select id="status" path="status" class="input-xlarge">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getEnumList('accountStatus')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<%--<li><label>账号类型：</label>
				<form:input id="userType" path="userType" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>--%>
			<li><label>绑定手机：</label>
				<form:input id="mobile" path="mobile" htmlEscape="false" maxlength="50" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}
					else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" onclick="return search()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="submit" value="清除" /></li>
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
				<th>账号状态</th>
				<th>信息认证状态</th>
				<th>绑定手机</th>
				<th>创建时间</th>
				<shiro:hasPermission name="merchant:merchantUser:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchantUser">
			<tr>
			    <td>${merchantUser.id}</td>
				<td>${merchantUser.loginName}</td>
				<td>${merchantUser.companyName}</td>
				<td>${merchantUser.status}</td>
				<td>${merchantUser.infoAuthStatus}</td>
				<td>${merchantUser.mobile}</td>
				<td><fmt:formatDate value="${merchantUser.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<shiro:hasPermission name="merchant:merchantUser:edit"><td>
				    <a href="${ctx}/merchant/merchantUser/detail?id=${merchantUser.id}">查看详情</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>