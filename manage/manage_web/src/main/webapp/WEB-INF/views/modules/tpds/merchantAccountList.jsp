<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商户台账信息</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
	
		//动态口令验证
		$(document).ready(function() {
			$(".checkPass").on("click",function(){
				var url = $(this).attr("value-url");
				parent.showDynamicPa();
				parent.enterDynamicPa(url);
			})
		});
		//验证搜索条件
		var validateFrom = {
			validate: function(form){
				var bgTime = $("#beginOperEndTime").val();
				var endTime = $("#endOperEndTime").val();
				if(bgTime=="" && endTime!="" || bgTime!="" && endTime=="" ){
					$("#messageBox").text("请正确设置查询时间范围!");
					return ;
				}
				if( bgTime!="" && endTime!=""){
					if(compareDate(convertDateToJoinStr(bgTime),
									convertDateToJoinStr(endTime)) > 0){
						$("#messageBox").text("起始日期不能大于结束日期!");
						return ;
					}
				}
				form.submit();
			}
		}

		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//搜索
		function onSubmit(){
			$("#pageNo").val(1);
			validateFrom.validate($("#searchForm"));
		}
		
		//清空
		function onClear(){
			$("#formBtn").submit();
		}
		
	</script>
</head>
<body>
   <ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/tpds/merchantAccount/">商户台账列表</a></li>
	<shiro:hasPermission name="tpds:merchantAccount:edit">
		<li><a href="${ctx}/tpds/merchantAccount/addEntity">添加商户台账</a></li></shiro:hasPermission>
</ul>
	<form action="${ctx}/tpds/merchantAccount/" method="post" id="formBtn"></form> 
	<form:form id="searchForm" modelAttribute="tpdsMerchantAccount" action="${ctx}/tpds/merchantAccount" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			
			<li><label>商户编号：</label>
				<form:input path="merchantNo"  htmlEscape="false" maxlength="16" style="width:256px;" class="input-medium required"/>
			</li>
			<li><label>商户名称：</label>
				<form:input path="loginName"  htmlEscape="false" maxlength="64" style="width:256px;" class="input-medium required"/>
			</li>
		</ul> 
		
 		<ul class="ul-form">
			<li><label>修改时间：</label>
				<input id="beginOperEndTime" name="beginOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${tpdsMerchantAccount.beginOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endOperEndTime" name="endOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${tpdsMerchantAccount.endOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
	     <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
	     <li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
		</ul>  
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>商户编号</th>
			<th>商户名称</th>
			<th>接入系统编码</th>
			<th>台账账户</th>
			<th>证件号码</th>
			<th>银行卡号</th>
			<th>银行卡所在银行联行号</th>
			<th>存管户</th>
			<th>存管户所在银行联行号</th>
			<th>注册时间</th>
			<th>修改时间</th>
			<th>修改人</th>
			<th>状态</th>
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tpdsMerchantAccount" varStatus="i">
				<tr>
					<td>${tpdsMerchantAccount.merchantNo}</td>
					<td>${tpdsMerchantAccount.loginName}</td>
					<td>${tpdsMerchantAccount.systemNo}</td>
				    <td>${tpdsMerchantAccount.accNo}</td>
				    <td>${tpdsMerchantAccount.certNo}</td>
				    <td>${tpdsMerchantAccount.bankCard}</td>
				    <td>${tpdsMerchantAccount.bankCardBranch}</td>
				    <td>${tpdsMerchantAccount.bankAccount}</td>
				    <td>${tpdsMerchantAccount.bankAccountBranch}</td>
					<td><fmt:formatDate value="${tpdsMerchantAccount.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td><fmt:formatDate value="${tpdsMerchantAccount.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>${tpdsMerchantAccount.updateUser}</td>
					<td>${tpdsMerchantAccount.status}</td>
					<td>
				  	<a href="${ctx}/tpds/merchantAccount/updateEntity?tpdsMerchantId=${tpdsMerchantAccount.tpdsMerchantId}&pageNo=${page.pageNo}">修改</a>
				   </td>
				</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>