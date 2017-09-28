<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>user管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			var s;
			var d;
			var set;
			var setValue;
		    if($("#status").val()=="正常"){
		    	 $("#changeStatus").val("禁用账号"); 
		    	s="禁用账号";
		    	d="正在禁用...";
		    	set="确认禁用";
		    	setValue="FREZED";
			}else if($("#status").val()=="冻结"){
				 $("#changeStatus").val("启用账号"); 
		    	s="启用账号";
		    	d="正在启用..."; 
		    	set="确认启用";
		    	setValue="NORMAL";
			}  
			$("#reSetLoginKey").click(function(){
				var a = $("#loginName").val();
				parent.showUser();
				parent.sendEmail(a,"重置登录密码","发送重置【登录密码】链接邮箱：");
			}); 
			$("#reSetpayKey").click(function(){
				var a = $("#loginName").val();
				parent.showUser();
				parent.sendEmail(a,"重置支付密码","发送重置【支付密码】链接邮箱：");
			}); 
			$("#changeStatus").click(function(){
				var a = $("#loginName").val();
				var f = $("#merchantId").val();
				parent.showStatus();
				parent.changeStatus(a,s,d,setValue,set,f);
			}); 
			$("#unbundlingPhone").click(function(){
				var a = $("#loginName").val();
				var b = $("#merchantId").val();
				parent.showUnbundling();
				parent.setLoginName(a,b);
			}); 
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/merchant/merchantUser/">商家账户详情列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="merchantUser" class="breadcrumb form-search">
		<ul class="ul-form">
		<shiro:hasPermission name="merchant:merchantUser:rc">
			<li class="btns"><input id="changeStatus" class="btn btn-primary" type="button" value="禁用账号"/></li>
			<li class="btns"><input id="unbundlingPhone" class="btn btn-primary" type="button" value="重置绑定手机号" /></li>
			<li class="btns"><input id="reSetLoginKey" class="btn btn-primary" type="button" value="重置登录密码" /></li>
			<li class="btns"><input id="reSetpayKey" class="btn btn-primary" type="button" value="重置支付密码" /></li>
			<li class="clearfix"></li>
		</shiro:hasPermission>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<input id="loginName" type="hidden" value="${merchantUser.loginName}"/>
	<input id="status" type="hidden" value="${merchantUser.status}"/>
	<input id="merchantId" type="hidden" value="${merchantUser.id}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		 <tr>
			  <th align="left">商户账号</th>
			  <td align="right">${merchantUser.loginName}</td>
			  <th align="left">商户编码</th>
			  <td align="right">${merchantUser.id}</td>
		</tr>
		<tr>
			  <th align="left">创建时间</th>
			  <td align="right"><fmt:formatDate value="${merchantUser.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			  <th align="left">账号状态</th>
			  <td align="right" id="status_one">${merchantUser.status}</td>
		</tr>
		<tr>
			  <th align="left">绑定手机</th>
			  <td align="right">${merchantUser.mobile}</td>
			  <th align="left">认证状态</th>
			  <td align="right">${merchantUser.infoAuthStatus}</td>
		</tr>
		<tr>
			  <th align="left">商户公司名称</th>
			  <td align="right">${merchantUser.companyName}</td>
			  <th align="left">法人姓名</th>
			  <td align="right">${merchantUser.legalRepresentative}</td>
		</tr>
		<tr>
			  <th align="left">最后登录IP</th>
			  <td align="right">${merchantUser.lastLoginIp}</td>
			  <th align="left">法人身份证号码</th>
			  <td align="right">${merchantUser.legalIdcard}</td>
		</tr>
	</table>
	<div class="form-actions">
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</body>
</html>