<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人钱包注册用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnSubmit").on("click",function(){
				$("#searchForm").submit();
			});
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		//清空
		function onClear(){
			$("#searchForm").find("input[type='text']").val("");
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/wallet/merchantsUserList">个人钱包用户注册列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="merchantsUser" action="${ctx}/payment/wallet/merchantsUserList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>登录名：</label>
				<form:input path="loginName" htmlEscape="false" maxlength="11" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}
					else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="realName" htmlEscape="false" maxlength="18" class="input-medium"/>
			</li>
			<li><label>商户号：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="10" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}
					else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户ID</th>
				<th>用户登录名</th>
				<th>用户类型</th>
				<th>联系手机</th>
				<th>账号状态</th>
				<th>商户ID</th>
				<th>实名认证状态</th>
				<th>银行卡鉴权状态</th>
				<th>用户级别</th>
				<th>真实姓名</th>
				<th>创建时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wallet">
			<tr>
				<td>
					${wallet.id}
				</td>
				<td>
					${wallet.loginName}
				</td>
				<td>
					${fns:getDictLabel(wallet.userType, 'UserType', '')}
				</td>
				<td>
					${wallet.phone}
				</td>
				<td>
					${wallet.status}
				</td>
				<td>
					${wallet.merchantId}
				</td>
				<td>
					${wallet.realnameAuthSign}
				</td>
				<td>
					${wallet.bankcardAuthSign}
				</td>
				<td>
					${wallet.userLevel}
				</td>
				<td>
					${wallet.realName}
				</td>
				<td>
					<fmt:formatDate value="${wallet.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>