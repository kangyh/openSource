<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>账户转账管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});

		//根据账户id获取商户信息
		function queryMerchantAccount(dir){
			var queryType = $("#"+dir+"queryType").val();
			var queryCondition = $("#"+dir+"queryCondition").val();
			if(queryCondition.trim() == ""){
				return;
			}
			var type = "2";
			$.ajax({
				type : "POST",
				url : "${ctx}/account/accountQuery/getMerchantByType",
				data : {
					"queryType":queryType,
					"queryCondition" : queryCondition,
					"merchantType" : type
				},
				dataType : "json",
				success : function(data){
					$("td[id^='"+dir+"']").text("-");
					if(data == null){
						alert("没有对应的商户信息，请检查编码或邮箱！");
					}else{
						$("#"+dir+"accountId").text(data.accountId);
						$("#"+dir+"accountName").text(data.accountName);
						$("#"+dir+"balanceAmount").text(data.balanceAmount);
						$("#"+dir+"balanceAvailableAmount").text(data.balanceAvailableAmount);
						$("#"+dir+"balanceAvailableWithdrawAmount").text(data.balanceAvailableWithdrawAmount);
						if("NORMAL"==data.status){
							$("#"+dir+"status").text("正常");
						}
						if("FREZED"==data.status){
							$("#"+dir+"status").text("冻结");
						}
						if("CLOSED"==data.status){
							$("#"+dir+"status").text("关闭");
						}
					}
				}
			});
		}

		function onSubmit(){
			var  DaccountId = $("#DaccountId").text();
			var  CaccountId = $("#CaccountId").text();
			var DbalanceAvailableAmount = $("#DbalanceAvailableAmount").text();
			var transferAmount = $("#transferAmount").val();
			if((DaccountId.trim() == CaccountId.trim()) && DaccountId!="-" && CaccountId!="-"){
				alert("转入方和转出方不能是同一个!");
			}else if(DbalanceAvailableAmount!="-" && parseFloat(DbalanceAvailableAmount) < parseFloat(transferAmount)){
				alert("转出方账户可用金额不足!");
			}else{
				$("#inputForm").submit();
			}
		}
	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li><a href="${ctx}/account/merchantTransfer/">商户转账列表</a></li>
	<%--<li class="active"><a href="${ctx}/account/adjustMerchantAccount/form?id=${adjustMerchantAccount.id}">商户调账申请<shiro:hasPermission name="account:adjustMerchantAccount:edit">${not empty adjustMerchantAccount.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="account:adjustMerchantAccount:edit">查看</shiro:lacksPermission></a></li>--%>
</ul><br/>
<form:form id="inputForm" action="${ctx}/account/merchantTransfer/saveApply" method="post" class="form-horizontal" enctype="multipart/form-data">
	<%--<form:hidden path="id"/>--%>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed" style="width: 100%">
		<thead>
		</thead>
		<tbody id="tbody">
		<tr>
			<td colspan="2">转出商户信息</td>
			<td colspan="2">转入商户信息</td>
		</tr>
		<tr>
			<td>商户查询:</td>
			<td>按
				<select id="DqueryType" name="DqueryType">
					<option name="" value="1">编码</option>
					<option name="" value="2">邮箱</option>
				</select>
				<input name="DqueryCondition" id="DqueryCondition" htmlEscape="false" class="input-xlarge required" style="width:160px"/>&nbsp;<input class="btn btn-primary" type="button" onclick="queryMerchantAccount('D')"  value="查询"/>
			</td>
			<td>商户查询:</td>
			<td>按
				<select id="CqueryType" name="CqueryType">
					<option name="" value="1">编码</option>
					<option name="" value="2">邮箱</option>
				</select>
				<input name="CqueryCondition" id="CqueryCondition" htmlEscape="false" class="input-xlarge required" style="width:160px"/>&nbsp;<input class="btn btn-primary" type="button" onclick="queryMerchantAccount('C')"  value="查询"/>
			</td>
		</tr>
		<tr>
			<td>商户账户:</td><td id="DaccountId">-</td>
			<td>商户账户:</td><td id="CaccountId">-</td>
		</tr>
		<tr>
			<td>商户名称:</td><td id="DaccountName">-</td>
			<td>商户名称:</td><td id="CaccountName">-</td>
		</tr>
		<tr>
			<td>总金额:</td><td id="DbalanceAmount">-</td>
			<td>总金额:</td><td id="CbalanceAmount">-</td>
		</tr>
		<tr>
			<td>可用金额:</td><td id="DbalanceAvailableAmount">-</td>
			<td>可用金额:</td><td id="CbalanceAvailableAmount">-</td>
		</tr>
		<tr>
			<td>可提现金额:</td><td id="DbalanceAvailableWithdrawAmount">-</td>
			<td>可提现金额:</td><td id="CbalanceAvailableWithdrawAmount">-</td>
		</tr>
		<tr>
			<td>账户状态:</td><td id="Dstatus">-</td>
			<td>账户状态:</td><td id="Cstatus">-</td>
		</tr>
		<tr>
			<td colspan="1">转账金额:</td>
			<td colspan="3"><input name="transferAmount" id="transferAmount" onkeyup="parent.amountCheck(this);" htmlEscape="false" class="input-xlarge required number" />&nbsp;元</td>
		</tr>
		<tr style="display: none">
			<td colspan="1">更新转入方可提现金额</td>
			<td colspan="3"><input type="checkbox" checked name="updWithDraw" htmlEscape="false" class="input-xlarge" /></td>
		</tr>
		<tr>
			<td colspan="1">转账原因:</td>
			<td colspan="3">
				<textarea name="transferReason" htmlEscape="false" class="input-xlarge required" style="width: 80%"></textarea>
			</td>
		</tr>
		<tr style="display: none">
			<td colspan="1">上传附件:</td>
			<td colspan="3">
				<input type="file" id="file" name="file" />
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<input style="float: left;margin-left: 150px;" id="" class="btn btn-primary" type="button" onclick="onSubmit()" value="申请调账"/>
			</td>
		</tr>
		</tbody>
		<tfoot>
		</tfoot>
	</table>
	<div class="form-actions">
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</form:form>
</body>
</html>