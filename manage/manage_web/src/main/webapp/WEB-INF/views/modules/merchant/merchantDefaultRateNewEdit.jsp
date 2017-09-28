<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>merchant管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function() {
		//$("#name").focus();
		$("#chargeCollectionTypeDiv").find("input[value='RETIME']").attr("disabled",'disabled');
		$("#chargeCollectionTypeDiv").find("input[value='SETTLE']").attr("disabled",'disabled');
		$("#bankCardTypeDiv").find("input[value='SAVING']").attr("disabled",'disabled');
		$("#bankCardTypeDiv").find("input[value='CREDIT']").attr("disabled",'disabled');
		if($("#productCode").val() === ""){
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
		chargeDeductTypeChange($("#chargeDeductTypeDiv").find("input[checked='checked']").val());
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
	
	function pass(){
		if(parseFloat($("#maxFee").val()) < parseFloat($("#minFee").val())){
			parent.showThree();
			parent.changeThreeName("手续费最大值必须大于等于手续费最小值");
			return false;
		}else{
            if(!confirm("请确认提交此操作?")){
                return false;
            }
		}
	}
	
	function chargeTypeChange(option){
		var code = option.value;
		switch(code){
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
	
	
	function choose(type,name){
        switch(type){
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
            if(name.indexOf("B2B") >= 0 || name.indexOf("微信") >= 0 || name.indexOf("快捷支付2") >= 0){
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
		//$("#ruleBeginTimeDiv").show();
		//$("#ruleEndTimeDiv").show();
		$("#chargeTypeDiv").show();
		$("#chargeRatioDiv").show();
		$("#chargeFeeDiv").show();
		$("#chargeSourceDiv").show();
		$("#chargeDeductTypeDiv").show();
		$("#chargeCollectionTypeDiv").show();
		$("#isRefundableDiv").show();
		$("#statusDiv").show();
		$("#bankNoDiv").show();
		$("#bankCardType").val("");
		//$("#ruleBeginTime").val("");
		//$("#ruleEndTime").val("");
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
		$("#bankNoDiv").hide();
		$("#chargeDeductTypeDiv").find("input[value='EXDEDU']").attr("checked",'checked');
		chargeDeductTypeChange("EXDEDU");
		$("#chargeDeductTypeDiv").hide();
		$("#chargeCollectionTypeDiv").hide();
	}
	function d(){
		$("#bankCardTypeDiv").hide();
//		$("#ruleBeginTimeDiv").hide();
//		$("#ruleEndTimeDiv").hide();
		$("#chargeFeeDiv").hide();
		$("#chargeRatioDiv").hide();
		$("#maxFeeDiv").hide();
		$("#minFeeDiv").hide();
		$("#chargeSourceDiv").hide();
		$("#isRefundableDiv").hide();
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
		$("#bankNoDiv").hide();
	}
	function g(){
		$("#bankCardTypeDiv").hide();
		//$("#chargeTypeDiv").hide();
		$("#isRefundableDiv").hide();
		$("#chargeRatioDiv").hide();
		$("#maxFeeDiv").hide();
		$("#minFeeDiv").hide();
		$("#chargeSourceDiv").hide();
		//$("#chargeDeductTypeDiv").hide();
		//$("#chargeCollectionTypeDiv").hide();
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
		$("#bankNoDiv").hide();
		$("#chargeDeductTypeDiv").find("input[value='EXDEDU']").attr("checked",'checked');
		chargeDeductTypeChange("EXDEDU");
		$("#chargeDeductTypeDiv").hide();
		$("#chargeCollectionTypeDiv").hide();
	}

	function dptpay() {
		$("#bankCardTypeDiv").hide();
		$("#chargeFeeDiv").hide();
		$("#chargeRatioDiv").hide();
		$("#maxFeeDiv").hide();
		$("#minFeeDiv").hide();
		$("#isRefundableDiv").hide();
		$("#chargeSourceDiv").hide();
		$("#bankNoDiv").hide();
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
		$("#chargeCollectionTypeDiv").hide();
	}


	function bankNoChange(option){
		var name = option.text;
		$("#bankName").val(name);
	}
	function chargeDeductTypeChange(type){
		var code = $("#productType").val();
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
				$("#chargeCollectionTypeDiv").find("input[value='RETIME']").attr("disabled",'disabled');
			}else if(type == "EXDEDU"){
				$("#chargeCollectionTypeDiv").find("input[value='RETIME']").attr("checked",'checked');
				$("#chargeCollectionTypeDiv").find("input[value='SETTLE']").attr("disabled",'disabled');
			}
			break;
		default:
			if(type == "INDEDU"){
				$("#chargeSourceDiv").show();
				$("#chargeCollectionTypeDiv").find("input[value='SETTLE']").attr("checked",'checked');
				$("#chargeCollectionTypeDiv").find("input[value='RETIME']").attr("disabled",'disabled');
			}else if(type == "EXDEDU"){
				$("#chargeSourceDiv").hide();
				$("#chargeCollectionTypeDiv").find("input[value='RETIME']").attr("checked",'checked');
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
		<li><a href="${ctx}/merchant/merchantDefaultRateNew?cache=1">费率配置列表</a></li>
		<li class="active"><a href="">费率配置<shiro:hasPermission name="merchant:merchantDefaultRateNew:edit">${not empty merchantRateNew.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="merchant:merchantDefaultRateNew:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="merchantRateNew" action="${ctx}/merchant/merchantDefaultRateNew/save" method="post" class="form-horizontal">
		<form:hidden id="id" path="id"/>
		<form:hidden path="merchantLoginName"/>
		<sys:message content="${message}"/>
		<input type="hidden" id="productType"/>
		<input type="hidden" id="msg" value = "${msg}" />
		<form:hidden path="merchantId"  value="1"/>
		<form:hidden path="merchantCompanyName" value="汇元网" />
		<div class="control-group">
			<label class="control-label">业务类型：</label>
			<div class="controls">
				<input value="${merchantRateNew.businessType}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				<%-- <form:select path="businessType" class="input-xlarge ">
					<form:options items="${fns:getEnumList('rateBusinessType')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select> --%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品名称：</label>
			<div class="controls">
				<input id="productName" name="productName" value="${merchantRateNew.productName}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				<%-- <form:select path="productCode" class="input-xlarge" onchange="productChange(this.options[this.options.selectedIndex]);">
					<form:option value="" label=""/>
					<form:options items="${fns:getProductList()}" itemLabel="name" itemValue="code" htmlEscape="false"/>
				</form:select> --%>
				<form:hidden path="productCode" id="productCode"/>
			</div>
		</div>
		<div class="control-group" id="bankCardTypeDiv">
			<label class="control-label">银行卡类型：</label>
			<div class="controls">
				<form:radiobuttons id="bankCardType" path="bankCardType" items="${fns:getEnumList('rateBankcardType')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group" id="chargeDeductTypeDiv">
			<label class="control-label">手续费扣除方式：</label>
			<div class="controls">
				<form:radiobuttons id="chargeDeductType" path="chargeDeductType" items="${fns:getEnumList('chargeDeductType')}" itemLabel="name" itemValue="value" htmlEscape="false" class="" onchange="chargeDeductTypeChange(this.value)"/>
			</div>
		</div>
		<div class="control-group" id="chargeSourceDiv">
			<label class="control-label">手续费来源：</label>
			<div class="controls">
				<form:radiobuttons id="chargeSource" path="chargeSource" items="${fns:getEnumList('chargeSource')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group" id="chargeCollectionTypeDiv">
			<label class="control-label">手续费收取方式：</label>
			<div class="controls">
				<form:radiobuttons id="chargeCollectionType" path="chargeCollectionType" items="${fns:getEnumList('chargeCollectionType')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
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
<%-- 		<div class="control-group" id="ruleBeginTimeDiv">
			<label class="control-label">规则开始时间：</label>
			<div class="controls">
				<input id="ruleBeginTime" name="ruleBeginTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${merchantRateNew.ruleBeginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group" id="ruleEndTimeDiv">
			<label class="control-label">规则结束时间：</label>
			<div class="controls">
				<input id="ruleEndTime" name="ruleEndTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${merchantRateNew.ruleEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div> --%>
		<div class="control-group" id="bankNoDiv">
			<label class="control-label">银行名称：</label>
			<div class="controls">
				<form:select id="bankNo" path="bankNo" class="input-xlarge" onchange="bankNoChange(this.options[this.options.selectedIndex]);">
					<form:option value="" label=""/>
					<form:options items="${fns:getBankInfoList()}" itemLabel="bankName" itemValue="bankNo" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<form:hidden path="bankName" id="bankName"/>
		<div class="control-group" id="chargeTypeDiv">
			<label class="control-label">计费类型：</label>
			<div class="controls">
				<form:select id="chargeType" path="chargeType" class="input-xlarge" onchange="chargeTypeChange(this.options[this.options.selectedIndex]);">
					<form:option value="" label=""/>
					<form:options items="${fns:getEnumList('costType')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group" id="chargeRatioDiv">
			<label class="control-label">手续费费用(%)：</label>
			<div class="controls">
				<form:input id="chargeRatio" path="chargeRatio" htmlEscape="false" maxlength="100" class="input-xlarge" onkeyup="parent.amountCheck(this);"/>
			</div>
		</div>
		<div class="control-group" id="chargeFeeDiv">
			<label class="control-label">手续费费用(元)：</label>
			<div class="controls">
				<form:input id="chargeFee" path="chargeFee" htmlEscape="false" maxlength="100" class="input-xlarge" onkeyup="parent.amountCheck(this);"/>
			</div>
		</div>
		<div class="control-group" id="maxFeeDiv">
			<label class="control-label">手续费最大值(元)：</label>
			<div class="controls">
				<form:input id="maxFee" path="maxFee" htmlEscape="false" maxlength="100" class="input-xlarge" onkeyup="parent.amountCheck(this);"/>
			</div>
		</div>
		<div class="control-group" id="minFeeDiv">
			<label class="control-label">手续费最小值(元)：</label>
			<div class="controls">
				<form:input id="minFee" path="minFee" htmlEscape="false" maxlength="100" class="input-xlarge" onkeyup="parent.amountCheck(this);"/>
			</div>
		</div>
		
		
		<div class="form-actions">
			<shiro:hasPermission name="merchant:merchantDefaultRateNew:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="return pass();"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>