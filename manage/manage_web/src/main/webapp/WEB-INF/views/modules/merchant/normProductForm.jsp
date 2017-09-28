<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>标准产品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			if($("#productCode1").val() === ""){
				a();
			}else{
				getProductType($("#productCode").val());
				choose($("#productType").val(),$("#productName").val());
			}
			if($("#msg").val() != ""){
				parent.showThree();
				parent.changeThreeName($("#msg").val());
			}
			if($("#chargeType").val() === "RATIOD"){
				$("#chargeRatioDiv").show();
				$("#maxFeeDiv").show();
				$("#minFeeDiv").show();
				$("#chargeFeeDiv").hide();
			}else if($("#chargeType").val() === "COUNTD"){
				$("#chargeRatioDiv").hide();
				$("#maxFeeDiv").hide();
				$("#minFeeDiv").hide();
				$("#chargeFeeDiv").show();
			}
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
			if($("#id").val() == ""){
				$("#chargeFeeDiv").hide();
				$("#chargeRatioDiv").hide();
				$("#maxFeeDiv").hide();
				$("#minFeeDiv").hide();
			}else{
				if($("#chargeType").val() === "RATIOD"){
					$("#chargeRatioDiv").show();
					$("#maxFeeDiv").show();
					$("#minFeeDiv").show();
					$("#chargeFeeDiv").hide();
				}else if($("#chargeType").val() === "COUNTD"){
					$("#chargeRatioDiv").hide();
					$("#maxFeeDiv").hide();
					$("#minFeeDiv").hide();
					$("#chargeFeeDiv").show();
				}
			}
		});

		function chargeTypeChange(code){
			switch(code.value){
				case "RATIOD":
					aa();
					break;
				case "COUNTD":
					bb();
					break;
			}
		}

		function aa(){
			$("#chargeRatioDiv").show();
			$("#maxFeeDiv").show();
			$("#minFeeDiv").show();
			$("#chargeFeeDiv").hide();
			$("#chargeFee").val("");
			$("#maxFee").val("");
			$("#minFee").val("");
		}

		function bb(){
			$("#chargeRatioDiv").hide();
			$("#maxFeeDiv").hide();
			$("#minFeeDiv").hide();
			$("#chargeFeeDiv").show();
			$("#chargeRatio").val("");
			$("#maxFee").val("");
			$("#minFee").val("");
		}

		function productChange(option){
			var code = option.value;
			var name = option.text;
			var type = $(option).data('type');
			$("#productName").val(name);
			all();
			choose(type,name);
		}

		function choose(type,name){
			console.log(type,name);
			switch(type) {
				case "BATCOL"://代收
					b();
					break;
				case "BATPAY"://转账
					c();
					break;
				case "CHARGE"://充值
					d();
					break;
				case "WZDRAW"://提现
				case "DPTWZD"://存管提现
					e();
					break;
				case "REFUND"://退款
					f();
					break;
				case "RENAME"://实名认证
				case "EPRISE"://企业认证
				case "AUTHEN"://鉴权认证
					g();
					break;
				case "SHARES"://平级分润
					i();
					break;
				case "CACNCE"://通关申报
					costom();
					break;
				case "DPTPAY"://存管支付
					dptpay();
					break;
				default://支付
					//B2B支付,微信
					if (name.indexOf("B2B") >= 0 || name.indexOf("微信") >= 0 || name.indexOf("快捷支付2") >= 0) {
						h();
						break;
					}
					//其他支付
					a();
					break;
			}
		}

		function all(){
			$("#bankCardTypeDiv").show();
			$("#chargeTypeDiv").show();
			$("#chargeRatioDiv").show();
			$("#chargeFeeDiv").show();
			$("#chargeSourceDiv").show();
			$("#chargeDeductTypeDiv").show();
			$("#chargeCollectionTypeDiv").show();
			$("#isRefundableDiv").show();
			$("#settleTypeDiv").show();
			$("#settlementToDiv").show();
			$("#statusDiv").show();
			$("#bankNoDiv").show();
			$("#bankCardType").val("");
			$("#chargeType").val("");
			$("#chargeRatio").val("");
			$("#chargeFee").val("");
			$("#chargeSource").val("");
			$("#chargeDeductType").val("");
			$("#chargeCollectionType").val("");
			$("#isRefundable").val("");
			$("#status").val("");
			$("#maxFee").val("");
			$("#minFee").val("");
			$("input[type='radio']").removeAttr('checked');
			$("#s2id_chargeType").find(".select2-chosen").text("");
			$("#statusDiv").find("input[value='ENABLE']").attr("checked",'checked');
		}

		function a(){
			$("#chargeFeeDiv").hide();
			$("#chargeRatioDiv").hide();
			$("#maxFeeDiv").hide();
			$("#minFeeDiv").hide();
			$("#isRefundableDiv").hide();
			$("#chargeSourceDiv").hide();
			$("#bankNoDiv").hide();
		}
		function b(){
			$("#bankCardTypeDiv").hide();
			$("#chargeFeeDiv").hide();
			$("#chargeRatioDiv").hide();
			$("#maxFeeDiv").hide();
			$("#minFeeDiv").hide();
			$("#chargeSourceDiv").hide();
			$("#isRefundableDiv").hide();
			$("#bankNoDiv").hide();
			$("#chargeDeductTypeDiv").find("input[value='EXDEDU']").attr("checked",'checked');
			chargeDeductTypeChange("EXDEDU");
			$("#chargeDeductTypeDiv").hide();
			$("#chargeCollectionTypeDiv").hide();
		}
		function c(){
			$("#bankCardTypeDiv").hide();
			$("#chargeFeeDiv").hide();
			$("#chargeRatioDiv").hide();
			$("#maxFeeDiv").hide();
			$("#minFeeDiv").hide();
			$("#chargeSourceDiv").hide();
			$("#isRefundableDiv").hide();
			$("#settleTypeDiv").hide();
			$("#settlementToDiv").hide();
			$("#bankNoDiv").hide();
			$("#chargeDeductTypeDiv").find("input[value='EXDEDU']").attr("checked",'checked');
			chargeDeductTypeChange("EXDEDU");
			$("#chargeDeductTypeDiv").hide();
			$("#chargeCollectionTypeDiv").hide();
		}
		function d(){
			$("#bankCardTypeDiv").hide();
			$("#chargeFeeDiv").hide();
			$("#chargeRatioDiv").hide();
			$("#maxFeeDiv").hide();
			$("#minFeeDiv").hide();
			$("#chargeSourceDiv").hide();
			$("#isRefundableDiv").hide();
			$("#settleTypeDiv").hide();
			$("#settlementToDiv").hide();
			$("#bankNoDiv").hide();
			$("#chargeDeductTypeDiv").find("input[value='INDEDU']").attr("checked",'checked');
			chargeDeductTypeChange("INDEDU");
			$("#chargeDeductTypeDiv").hide();
			$("#chargeCollectionTypeDiv").hide();

		}
		function e(){
			$("#bankCardTypeDiv").hide();
			$("#chargeFeeDiv").hide();
			$("#chargeRatioDiv").hide();
			$("#maxFeeDiv").hide();
			$("#minFeeDiv").hide();
			$("#chargeSourceDiv").hide();
			$("#isRefundableDiv").hide();
			$("#settleTypeDiv").hide();
			$("#settlementToDiv").hide();
			$("#bankNoDiv").hide();
			$("#chargeDeductTypeDiv").find("input[value='EXDEDU']").attr("checked",'checked');
			chargeDeductTypeChange("EXDEDU");
			$("#chargeDeductTypeDiv").hide();
			$("#chargeCollectionTypeDiv").hide();
		}
		function f(){
			$("#bankCardTypeDiv").hide();
			$("#chargeTypeDiv").hide();
			$("#chargeRatioDiv").hide();
			$("#maxFeeDiv").hide();
			$("#minFeeDiv").hide();
			$("#chargeFeeDiv").hide();
			$("#chargeSourceDiv").hide();
			$("#chargeDeductTypeDiv").hide();
			$("#chargeCollectionTypeDiv").hide();
			$("#settleTypeDiv").hide();
			$("#settlementToDiv").hide();
			$("#bankNoDiv").hide();
		}
		function g(){
			$("#bankCardTypeDiv").hide();
			$("#isRefundableDiv").hide();
			$("#chargeRatioDiv").hide();
			$("#maxFeeDiv").hide();
			$("#minFeeDiv").hide();
			$("#chargeSourceDiv").hide();
			$("#settleTypeDiv").hide();
			$("#settlementToDiv").hide();
			$("#bankNoDiv").hide();
			$("#chargeType").find("option[value='COUNTD']").attr("selected",true);
			$("#chargeDeductTypeDiv").find("input[value='EXDEDU']").attr("checked",'checked');
			chargeDeductTypeChange("EXDEDU");
			$("#chargeDeductTypeDiv").hide();
			$("#chargeCollectionTypeDiv").hide();
		}
		function h(){
			$("#bankCardTypeDiv").hide();
			$("#chargeFeeDiv").hide();
			$("#chargeRatioDiv").hide();
			$("#maxFeeDiv").hide();
			$("#minFeeDiv").hide();
			$("#isRefundableDiv").hide();
			$("#chargeSourceDiv").hide();
			$("#bankNoDiv").hide();
		}
		function i(){
			$("#bankCardTypeDiv").hide();
			$("#chargeFeeDiv").hide();
			$("#chargeRatioDiv").hide();
			$("#maxFeeDiv").hide();
			$("#minFeeDiv").hide();
			$("#chargeSourceDiv").hide();
			$("#isRefundableDiv").hide();
			$("#settleTypeDiv").hide();
			$("#settlementToDiv").hide();
			$("#bankNoDiv").hide();
			$("#chargeDeductTypeDiv").find("input[value='EXDEDU']").attr("checked",'checked');
			chargeDeductTypeChange("EXDEDU");
			$("#chargeDeductTypeDiv").hide();
			$("#chargeCollectionTypeDiv").hide();
		}
		function costom(){
			$("#bankCardTypeDiv").hide();
			$("#chargeFeeDiv").hide();
			$("#chargeRatioDiv").hide();
			$("#maxFeeDiv").hide();
			$("#minFeeDiv").hide();
			$("#chargeSourceDiv").hide();
			$("#isRefundableDiv").hide();
			$("#bankNoDiv").hide();
			$("#chargeDeductTypeDiv").find("input[value='EXDEDU']").attr("checked",'checked');
			chargeDeductTypeChange("EXDEDU");
			$("#chargeDeductTypeDiv").hide();
			$("#chargeCollectionTypeDiv").hide();
		}

		//存管支付
		function dptpay(){
			$("#bankCardTypeDiv").hide();
			$("#chargeFeeDiv").hide();
			$("#chargeRatioDiv").hide();
			$("#maxFeeDiv").hide();
			$("#minFeeDiv").hide();
			$("#isRefundableDiv").hide();
			$("#chargeSourceDiv").hide();
			$("#bankNoDiv").hide();
		}

		function bankNoChange(option){
			var name = option.text;
			$("#bankName").val(name);
		}
		function chargeDeductTypeChange(type){
			var code = $("#productCode option:selected").data('type');
			switch(code){
				case "BATCOL"://代收
				case "BATPAY"://转账
				case "CHARGE"://充值
				case "WZDRAW"://提现
				case "REFUND"://退款
				case "RENAME"://实名认证
				case "EPRISE"://企业认证
				case "AUTHEN"://鉴权认证
				case "SHARES"://平级分润
				case "CACNCE"://通关申报
				case "DPTWZD"://存管提现
					if(type == "INDEDU"){
						$("#chargeCollectionTypeDiv").find("input[value='SETTLE']").attr("checked",'checked');
						$("#chargeCollectionTypeDiv").find("input[value='SETTLE']").removeAttr("disabled");
						$("#chargeCollectionTypeDiv").find("input[value='RETIME']").attr("disabled",'disabled');
					}else if(type == "EXDEDU"){
						$("#chargeCollectionTypeDiv").find("input[value='RETIME']").attr("checked",'checked');
						$("#chargeCollectionTypeDiv").find("input[value='RETIME']").removeAttr("disabled");
						$("#chargeCollectionTypeDiv").find("input[value='SETTLE']").attr("disabled",'disabled');
					}
					break;
				default:
					if(type == "INDEDU"){
						$("#chargeSourceDiv").show();
						$("#chargeCollectionTypeDiv").find("input[value='SETTLE']").attr("checked",'checked');
						$("#chargeCollectionTypeDiv").find("input[value='SETTLE']").removeAttr("disabled");
						$("#chargeCollectionTypeDiv").find("input[value='RETIME']").attr("disabled",'disabled');
					}else if(type == "EXDEDU"){
						$("#chargeSourceDiv").hide();
						$("#chargeCollectionTypeDiv").find("input[value='RETIME']").attr("checked",'checked');
						$("#chargeCollectionTypeDiv").find("input[value='RETIME']").removeAttr("disabled");
						$("#chargeCollectionTypeDiv").find("input[value='SETTLE']").attr("disabled",'disabled');
					}
					break;
			}
		}

		//根据产品code获取交易类型(type)
		function getProductType(code){
			$.ajax({
				async: false,
				type: "GET",
				url: "${ctx}/merchant/product/getTypeByCode",
				data: {"code":code},
				dataType: 'json',
				success: function(data){
					$("#productType").val(data.trxType);
					$("#productName").val(data.name);
				},
				error: function(){
					console.log("请求失败!");
				}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/merchant/normProduct?cache=1">标准产品列表</a></li>
		<li class="active"><a href="${ctx}/merchant/normProduct/form?id=${normProduct.id}">标准产品<shiro:hasPermission name="merchant:normProduct:edit">${not empty normProduct.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="merchant:normProduct:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="normProduct" action="${ctx}/merchant/normProduct/save" method="post" class="form-horizontal">
		<form:hidden path="id" id="id"/>
		<sys:message content="${message}"/>
		<input type="hidden" id="productType"/>
		<input type="hidden" id="productName"/>
		<input type="hidden" value="${msg}" id="msg" />
		<div class="control-group">
			<label class="control-label">产品名称：</label>
			<div class="controls">
				<form:select id="productCode" path="productCode" class="input-xlarge required" onchange="productChange(this.options[this.options.selectedIndex]);">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getProductList()}" itemLabel="name" itemValue="code" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开通费：</label>
			<div class="controls">
				<form:input path="openUpFee" htmlEscape="false" class="input-xlarge " onkeyup="parent.amountCheck(this);"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">技术对接费：</label>
			<div class="controls">
				<form:input path="technologyFee" htmlEscape="false" class="input-xlarge " onkeyup="parent.amountCheck(this);"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">保证金：</label>
			<div class="controls">
				<form:input path="cashDeposit" htmlEscape="false" class="input-xlarge " onkeyup="parent.amountCheck(this);"/>
			</div>
		</div>
		<div class="control-group" id="bankCardTypeDiv">
			<label class="control-label">银行卡类型：</label>
			<div class="controls">
				<form:radiobuttons id="bankCardType" path="bankCardType" items="${fns:getEnumList('rateBankcardType')}" itemLabel="name" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<!-- 结算周期  -->
		<div class="control-group" id="settleTypeDiv">
			<label class="control-label">结算类型：</label>
			<div class="controls">
				T+<select id="settleType" name="settleType" style="width:50px">
				<option value="0" <c:if test="${merchantProductRate.settleType=='0'}">selected="selected"</c:if>  class="input-xlarge required">0</option>
				<option value="1" <c:if test="${merchantProductRate.settleType=='1'}">selected="selected"</c:if>  class="input-xlarge required">1</option>
			</select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="settlementToDiv">
			<label class="control-label">结算至：</label>
			<div class="controls">
				<form:select id="settlementTo" path="settlementTo" class="input-xlarge required">
					<form:options items="${fns:getEnumList('settlementTo')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="chargeDeductTypeDiv">
			<label class="control-label">手续费扣除方式：</label>
			<div class="controls">
				<form:radiobuttons id="chargeDeductType" path="chargeDeductType" items="${fns:getEnumList('chargeDeductType')}" itemLabel="name" itemValue="value" htmlEscape="false" class="required" onchange="chargeDeductTypeChange(this.value)"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="chargeSourceDiv">
			<label class="control-label">手续费来源：</label>
			<div class="controls">
				<form:radiobuttons id="chargeSource" path="chargeSource" items="${fns:getEnumList('chargeSource')}" itemLabel="name" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="chargeCollectionTypeDiv">
			<label class="control-label">手续费收取方式：</label>
			<div class="controls">
				<form:radiobuttons id="chargeCollectionType" path="chargeCollectionType" items="${fns:getEnumList('chargeCollectionType')}" itemLabel="name" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="isRefundableDiv">
			<label class="control-label">退款时是否退还手续费：</label>
			<div class="controls">
				<form:radiobuttons id="isRefundable" path="isRefundable" items="${fns:getEnumList('yesOrNo')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group" id="statusDiv">
			<label class="control-label">规则状态：</label>
			<div class="controls">
				<form:radiobuttons id="status" path="status" items="${fns:getEnumList('commonStatus')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group" id="chargeTypeDiv">
			<label class="control-label">计费类型：</label>
			<div class="controls">
				<form:select id="chargeType" path="chargeType" class="input-xlarge required" onchange="chargeTypeChange(this.options[this.options.selectedIndex]);">
					<form:option value="" label=""/>
					<form:options items="${fns:getEnumList('costType')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="chargeRatioDiv">
			<label class="control-label">手续费费用(%)：</label>
			<div class="controls">
				<form:input id="chargeRatio" path="chargeRatio" htmlEscape="false" maxlength="100" class="input-xlarge required" onkeyup="parent.amountCheck(this);"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="chargeFeeDiv">
			<label class="control-label">手续费费用(元)：</label>
			<div class="controls">
				<form:input id="chargeFee" path="chargeFee" htmlEscape="false" maxlength="100" class="input-xlarge required" onkeyup="parent.amountCheck(this);"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="maxFeeDiv">
			<label class="control-label">手续费最大值(元)：</label>
			<div class="controls">
				<form:input id="maxFee" path="maxFee" htmlEscape="false" maxlength="100" class="input-xlarge required" onkeyup="parent.amountCheck(this);"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="minFeeDiv">
			<label class="control-label">手续费最小值(元)：</label>
			<div class="controls">
				<form:input id="minFee" path="minFee" htmlEscape="false" maxlength="100" class="input-xlarge required" onkeyup="parent.amountCheck(this);"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="merchant:normProduct:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" onclick="return confirmation()" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>