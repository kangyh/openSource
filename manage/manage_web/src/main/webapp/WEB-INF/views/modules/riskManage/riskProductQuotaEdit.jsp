<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>限额管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//修改时，不允许修改下列字段
			if(null != $("#proId").val() && undefined != $("#proId").val() && "" != $("#proId").val()){
				$("#productCode").attr("readOnly","true");
				$("#bankcardType").attr("readOnly","true");
				$("#accountType").attr("readOnly","true");
			}
			//修改时，账户类型是对私的，显示银行卡类型
			if($("#accountType").find("option:selected").val()=='PRIVAT'){
				$("#bank_card_type_id").css("display","block");
			}
			
			$("#inputForm").validate({
				submitHandler: function(form){
					//产品
					var productCode = $("#productCode").find("option:selected").val();
					if(productCode != "" && productCode != null){
						$("#productName").val($("#productCode").find("option:selected").text());
					}else{
						$("#productCode_id_info").text("请选择产品名称！");
						$("#productCode_id_info").show();
						return;
					}
					
					//账户类型为对私时，显示银行卡类型
					var accountType = $("#accountType").find("option:selected").val();
					if(accountType != null && accountType != ""){
						if(accountType == 'PRIVAT'){
							var bankcardType = $("#bankcardType").find("option:selected").val();
							if(bankcardType != null && bankcardType != ""){
							}else{
								$("#bankcardType_id_info").text("请选择银行卡类型！");
								$("#bankcardType_id_info").show();
								return;
							}
						}else{
							$("#bankcardType").find("option").removeAttr("selected");
							$("#bankcardType").find("option:eq(0)").attr("selected","selected");
						}
					}else{
						$("#accountType_id_info").text("请选择账户类型！");
						$("#accountType_id_info").show();
						return;
					}
					
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
		
		//账户类型为对私时，显示银行卡类型
		function checkAccountType(val){
			if(val=='PRIVAT'){
				$("#bank_card_type_id").css("display","block");
			}else{
				$("#bank_card_type_id").css("display","none");
			}
		}
		
		//验证金额
		function amountCheck(val,type){
			var reg = /^(([1-9][0-9]*)|(([0]\.\d{1,4}|[1-9][0-9]*\.\d{1,4}))|([0]{1}))$/;
			if(!reg.test(val)){
				$("#"+type+"_info").text("输入的金额错误，请重新输入！").show();
			}else{
				$("#"+type+"_info").text("");
			}
		} 
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a id="url_id" href="${ctx}/riskManage/riskProductQuotaQuery/">产品限额列表</a></li>
		<li class="active"><a href="${ctx}/riskManage/riskProductQuotaQuery/edit">产品限额<shiro:hasPermission name="riskManage:riskProductQuota:edit">${not empty riskProductQuota.proId?'修改':'添加'}</shiro:hasPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="riskProductQuota" action="${ctx}/riskManage/riskProductQuotaQuery/save" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<form:hidden path="proId"/>
		<div class="control-group">
			<label class="control-label">产品名称:</label>
			<div class="controls">
				<form:select id="productCode" path="productCode" class="input-xlarge">
               		<form:option id="prodduct_option_id" value="" label="请选择"/>
               		<c:forEach items="${prodList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
				<label id="productCode_id_info" for="productCode" class="error"></label>
				<input id="productName" type="hidden" name="productName"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公私标记:</label>
			<div class="controls">
				<form:select id="accountType" path="accountType" onchange="checkAccountType(this.value)" class="input-xlarge">
               		<form:option value="" label="请选择"/>
					<c:forEach items="${rATList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
				<label id="accountType_id_info" for="accountType" class="error"></label>
			</div>
		</div>
		<div class="control-group" id="bank_card_type_id" style="display: none;">
			<label class="control-label">银行卡类型:</label>
			<div class="controls">
				<form:select id="bankcardType" path="bankcardType" class="input-xlarge">
               		<form:option value="" label="请选择"/>
					<c:forEach items="${rBTList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
				<label id="bankcardType_id_info" for="bankcardType" class="error"></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单笔限额:</label>
			<div class="controls">
				<form:input path="perItem" htmlEscape="false" maxlength="20" onkeyup="amountCheck(this.value,'perItem');" class="required input-xlarge"/><span>元</span>
				<span class="help-inline"><font color="red">*</font> </span>
				<label id="perItem_info" class="error"></label>
				<span class="info">填0时，不限额</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单日限额:</label>
			<div class="controls">
				<form:input path="perDay" htmlEscape="false" maxlength="20" onkeyup="amountCheck(this.value,'perDay');" class="required input-xlarge"/><span>元</span>
				<span class="help-inline"><font color="red">*</font> </span>
				<label id="perDay_info"  class="error"></label>
				<span class="info">填0时，不限额</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单月限额:</label>
			<div class="controls">
				<form:input path="perMonth" htmlEscape="false" maxlength="20" onkeyup="amountCheck(this.value,'perMonth');" class="required input-xlarge"/><span>元</span>
				<span class="help-inline"><font color="red">*</font> </span>
				<label id="perMonth_info" class="error"></label>
				<span class="info">填0时，不限额</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<input type="radio" id="prod_status_ENABLE_id" name="status" value="ENABLE" <c:if test="${empty riskProductQuota.proId}">checked</c:if> value="ENABLE"  <c:if test="${riskMerchantProductQuota.status eq 'ENABLE'}">checked</c:if> <c:if test="${riskProductQuota.status eq 'ENABLE'}">checked</c:if>/><label for="prod_status_ENABLE_id"> 启用</label> 
				<input type="radio" id="prod_status_DISABL_id" name="status" value="DISABL" <c:if test="${riskProductQuota.status eq 'DISABL'}">checked</c:if>/><label for="prod_status_DISABL_id"> 禁用</label> 
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="riskManage:riskProductQuota:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>