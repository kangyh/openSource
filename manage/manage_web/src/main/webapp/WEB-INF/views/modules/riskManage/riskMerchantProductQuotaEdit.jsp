<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>限额管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//修改时，不允许修改商户号，产品名称，银行卡类型，账户类型
			if(null != $("#merProId").val() && undefined != $("#merProId").val() && "" != $("#merProId").val()){
				$("#merchantId").attr("readOnly","true");
				$("#bankcardType").attr("readOnly","true");
				$("#accountType").attr("readOnly","true");
				$("#add_show_info").css("display","none");
				
				//产品名称
				getProductInfo($("#merchant_code_show_id").val());
			}
			
			//从商户配置跳转添加商户产品限额
			if((null != $("#merchant_code_show_id").val() && "" != $("#merchant_code_show_id").val() 
					&& undefined != $("#merchant_code_show_id").val()) && (null == $("#merProId").val() 
					|| "" == $("#merProId").val() || undefined == $("#merProId").val())){
				getProductInfo($("#merchant_code_show_id").val());
			}
			
			
			//修改时，账户类型是对私的，显示银行卡类型
			if($("#accountType").find("option:selected").val()=='PRIVAT'){
				$("#bank_card_type_id").css("display","block");
			}
			
			$("#inputForm").validate({
				submitHandler: function(form){
					
					//产品
					var productCode = $("#productCode").find("option:selected").val();
					if(productCode != "" && productCode != null && productCode != undefined){
						$("#productCode_end").val(productCode);
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
		//根据商户编码获取该商户的所有产品信息
		function getProductInfo(val){
			var reg = /^[0-9]*$/;
			if (!reg.test(val)) {
				$("#merchant_id_info").text("输入的商户编码错误，请重新输入！");
				$("#merchant_id_info").show();
				return;
		    }else{
		    	$("#merchantId_info").text("");
		    }
			
			//移除之前添加的元素
			$("#s2id_productCode").remove();
			$("#product_select_id select").remove();
			$("#required_id").remove();
			$("#productCode_id_info").remove();
			$("#productCode_end").remove();
			$("#productName").remove();
			
			$.post($("#url_id").attr("href")+"product",{
					merchantId:$("#merchantId").val()
				},function(data){
					var message = "";
					var selectProductCode = $("#product_code_show_id").val();
					var productDiv = "";
					//添加页面和修改时判断产品的下拉选框是否为只读
					if(selectProductCode != "" && selectProductCode != null && selectProductCode != undefined && 
							null != $("#merProId").val() && undefined != $("#merProId").val() && "" != $("#merProId").val()){
						//只读
						productDiv += "<select id=\"productCode\" disabled=\"disabled\" onchange=\"getProductQuota(this.value)\" style=\"width:284px;\">";
					}else{
						//可选择
						productDiv += "<select id=\"productCode\" onchange=\"getProductQuota(this.value)\" style=\"width:284px;\">";
					}
					productDiv += '<option class=\"productClass\" value=\"\">请选择</option>';
					
					if(data != null && data != "" && data != undefined){
						data = eval(data);
						for(var i=0;i<data.length;i++){
							//修改时，默认选中之前选择的值
							if(selectProductCode == data[i].code){
								//之前的默认值
								productDiv += '<option class=\"productClass\" selected=\"selected\" select value="'+data[i].code+'">'+data[i].name+'</option>';
							}else{
								//添加时
								productDiv += '<option class=\"productClass\" value="'+data[i].code+'">'+data[i].name+'</option>';  
							}
						}						
					}else{
						message = "没有该商户！";
					}
					productDiv += "</select>";
					productDiv += "<span id=\"required_id\" class=\"help-inline\"><font color=\"red\">*</font> </span>";
					//提示是否存在该商户
					if(message != null && message != ""){
						productDiv += "<label id=\"productCode_id_info\" for=\"productCode\" class=\"error\">"+message+"</label>";
					}else{
						productDiv += "<label id=\"productCode_id_info\" for=\"productCode\" class=\"error\"></label>";
					}
					productDiv += "<input id=\"productCode_end\" type=\"hidden\" name=\"productCode\"/>";
					productDiv += "<input id=\"productName\" type=\"hidden\" name=\"productName\"/>";
					$("#product_select_id").append(productDiv);
				},"text"
			);
			
			$("#productCode").addClass("select2-container input-xlarge");
		}
		//根据产品编码获取产品限额表获取限额信息
		function getProductQuota(val){
			$.post($("#url_id").attr("href")+"quota",{
				productCode:$("#productCode").val(),
				accountType:$("#accountType").find("option:selected").val(),
				bankcardType:$("#bankcardType").find("option:selected").val() 
			},function(data){
				if(data != null && data != "" && data != undefined){
					$("#perItem").val(data.perItem);
					$("#perDay").val(data.perDay);
					$("#perMonth").val(data.perMonth);
				}else{
					$("#perItem").val("");
					$("#perDay").val("");
					$("#perMonth").val("");
				}
				
			},"text");
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
		<li><a id="url_id" href="${ctx}/riskManage/riskMerchantProductQuotaQuery/">商户产品限额列表</a></li>
		<li class="active"><a>商户产品限额<shiro:hasPermission name="riskManage:riskMerchantProductQuota:edit">${not empty riskMerchantProductQuota.merProId?'修改':'添加'}</shiro:hasPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="riskMerchantProductQuota" action="${ctx}/riskManage/riskMerchantProductQuotaQuery/save" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<form:hidden path="merProId"/>
		<input type="hidden" id="merchant_code_show_id" value="${riskMerchantProductQuota.merchantId}"/>
		<input type="hidden" id="product_code_show_id" value="${riskMerchantProductQuota.productCode}"/>
		<input type="hidden" id="merchantPageNum" name="merchantPageNum" value="${merchantPageNum}"/>
		<input type="hidden" id="referer" name="referer" value="${referer}"/>
		<div class="control-group" id="add_show_info">
			<div class="controls">
				<div style="font-size: 2em;font-family: serif;font: bold;padding-bottom: 20px;color: red;">请先输入商户编码,公私标记,银行卡类型！</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户编码:</label>
			<div class="controls">
				<form:input path="merchantId" id="merchantId" htmlEscape="false" onchange="getProductInfo(this.value)" maxlength="6" class="required input-xlarge"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<label id="merchant_id_info" class="error"></label>
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
				<span class="help-inline"><font color="red">*</font> </span>
				<label id="accountType_id_info" class="error"></label>
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
				<label id="bankcardType_id_info" class="error"></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品名称:</label>
			<div class="controls" id="product_select_id">
				<select id="productCode" name="productCode" class="input-xlarge">
               		<option value="" label="请选择"/>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单笔限额:</label>
			<div class="controls">
				<form:input path="perItem" id="perItem" htmlEscape="false" maxlength="20" onkeyup="amountCheck(this.value,'perItem');" class="required input-xlarge"/><span>元</span>
				<span class="help-inline"><font color="red">*</font> </span>
				<label id="perItem_info" class="error"></label>
				<span class="info">填0时，不限额</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单日限额:</label>
			<div class="controls">
				<form:input path="perDay" id="perDay" htmlEscape="false" maxlength="20" onkeyup="amountCheck(this.value,'perDay');" class="required input-xlarge"/><span>元</span>
				<span class="help-inline"><font color="red">*</font> </span>
				<label id="perDay_info"  class="error"></label>
				<span class="info">填0时，不限额</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单月限额:</label>
			<div class="controls">
				<form:input path="perMonth" id="perMonth" htmlEscape="false" maxlength="20" onkeyup="amountCheck(this.value,'perMonth');" class="required input-xlarge"/><span>元</span>
				<span class="help-inline"><font color="red">*</font> </span>
				<label id="perMonth_info" class="error"></label>
				<span class="info">填0时，不限额</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<input type="radio" id="merchant_status_ENABLE_id" name="status" <c:if test="${empty riskMerchantProductQuota.merProId}">checked</c:if> value="ENABLE"  <c:if test="${riskMerchantProductQuota.status eq 'ENABLE'}">checked</c:if>/><label for="merchant_status_ENABLE_id"> 启用</label> 
				<input type="radio" id="merchant_status_DISABL_id" name="status" value="DISABL" <c:if test="${riskMerchantProductQuota.status eq 'DISABL'}">checked</c:if>/><label for="merchant_status_DISABL_id"> 禁用</label> 
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="riskManage:riskMerchantProductQuota:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" onclick="return confirmation()" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>