<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>merchant管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        var saveValue = "";
		$(document).ready(function() {
			//$("#name").focus();
			$("#bankCardTypeDiv").find("input[value='SAVING']").attr("disabled",'disabled');
			$("#bankCardTypeDiv").find("input[value='CREDIT']").attr("disabled",'disabled');
			$("#bankCardTypeHidden").value = $("input[name='bankCardType']:checked").val();
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
				ratiod();
			}else if($("#chargeType").val() === "COUNTD"){
				countd();
			}
			chargeDeductTypeChange($("#chargeDeductTypeDiv").find("input[checked='checked']").val());
			$("#inputForm").validate({
				submitHandler: function(form){
					var code = $("#productCode").val();
					var merchantId = $("#merchantId").val();
					var savingType = $("#chargeType").val();
					var savingFee = $("#chargeFee").val();
					if(savingType == 'RATIOD'){
						savingFee = $("#chargeRatio").val();
					}
					$.ajax({
						type: "GET",
						async : false,
						url: "${ctx}/merchant/merchantRateNew/cost",
						data: {'productCode':code,'merchantId':merchantId,
							'savingType':savingType,'savingFee':savingFee},
						dataType: 'json',
						success: function(data){
							if(data.flag == "true"){
								var unit = "元";
								if(savingType == "RATIOD"){
									unit = "‰";
								}
								top.$.jBox.confirm("产品费率小于通道成本最大值("+data.cost+unit+")，是否确定配置？",'系统提示',function(v){
									if(v=='ok'){
										loading('正在提交，请稍等...');
										form.submit();
									}else{

									}
								});
								top.$('.jbox-body .jbox-icon').css('top','55px');
							}else{
								loading('正在提交，请稍等...');
								form.submit();
							}
						}
					});

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
            saveValue = $("#inputForm").serialize();
		});
		
		//按比例
		function ratiod() {
			$(".charge_name").html("手续费费用(%)：");
			$("#chargeRatioDiv").show();
			$("#chargeRatioDiv2").show();
			$("#chargeRatioDiv3").show();
			$("#maxFeeDiv").show();
			$("#minFeeDiv").show();
			$("#chargeFeeDiv").hide();
			$("#chargeFeeDiv2").hide();
			$("#chargeFeeDiv3").hide();
		}
		//按笔数
		function countd(){
			$(".charge_name").html("手续费费用(元)：");
			$("#chargeRatioDiv").hide();
			$("#chargeRatioDiv2").hide();
			$("#chargeRatioDiv3").hide();
			$("#maxFeeDiv").hide();
			$("#minFeeDiv").hide();
			$("#chargeFeeDiv").show();
			$("#chargeFeeDiv2").show();
			$("#chargeFeeDiv3").show();
		}


		function pass(){
			if(parseFloat($("#maxFee").val()) < parseFloat($("#minFee").val())){
				parent.showThree();
				parent.changeThreeName("手续费最大值必须大于等于手续费最小值");
				return false;
			}else {
                var values = $("#inputForm").serialize();
                //判断页面的值是否做过修改
                if(values == saveValue){
                    alertx("<span style='color: red'>页面未做任何修改,不予保存</span>");
                    return false;
                }else{
                    if (!confirm("请确认提交此操作?")) {
                        return false;
                    }
				}

            }
		}
		
		function chargeTypeChange(code){
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
			ratiod();
			$("#chargeFee").val("");
			$("#chargeFee2").val("");
			$("#chargeFee3").val("");
			$("#maxFee").val("");
			$("#minFee").val("");
		}
		function bb(){
			countd();
			$("#chargeRatio").val("");
			$("#chargeRatio2").val("");
			$("#chargeRatio3").val("");
			$("#maxFee").val("");
			$("#minFee").val("");
		}

		function choose(type,name){
	        switch(type){
	        case "BATCOL"://代收
			case "BATPAY"://转账
			case "DPTPAY"://存管支付
	            b();
	            break;
	        case "CHARGE"://充值
			case "WZDRAW"://提现
			case "DPTWZD"://存管提现
			case "SHARES"://平级分润
			case "CACNCE"://通关申报
			case "TRAFER"://内转
			case "WALLET"://钱包服务
	            d();
	            break;
			case "REFUND"://退款
	        case "RENAME"://实名认证
			case "EPRISE"://企业认证
			case "AUTHEN"://鉴权认证
	            f();
	            break;
	        default://支付
	            //B2B支付,微信
	            if(name.indexOf("B2B") >= 0 || name.indexOf("快捷支付2") >= 0){
	                b();
	                break;
	            }else if(name.indexOf("微信") >= 0 || name.indexOf("支付宝") >= 0 || name.indexOf("银联") >= 0){
	            	d();
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
			$("#chargeRatioDiv2").show();
			$("#chargeFeeDiv2").show();
			$("#chargeRatioDiv3").show();
			$("#chargeFeeDiv3").show();
			$("#bankNoDiv").show();
			$("#maxFeeDiv").show();
			$("#minFeeDiv").show();
			$("#bankCardType").val("");
			$("#chargeType").val("");
			$("#chargeRatio").val("");
			$("#chargeFee").val("");
			$("#chargeRatio2").val("");
			$("#chargeFee2").val("");
			$("#chargeRatio3").val("");
			$("#chargeFee3").val("");
			$("#chargeMax").val("");
			$("#chargeMin").val("");
			$("#chargeMax2").val("");
			$("#chargeMin2").val("");
			$("#chargeMax3").val("");
			$("#chargeMin3").val("");
			$("#bankName").val("");
			$("#bankNo").val("");
			$("#maxFee").val("");
			$("#minFee").val("");
			$("input[type='radio']").removeAttr('checked');
			$("#s2id_chargeType").find(".select2-chosen").text("");
			$("#s2id_bankNo").find(".select2-chosen").text("");

		}
		
		function a(){
			$("#chargeFeeDiv").hide();
			$("#chargeRatioDiv").hide();
			$("#maxFeeDiv").hide();
			$("#minFeeDiv").hide();
		}
		function b(){
			$("#bankCardTypeDiv").hide();
			$("#chargeFeeDiv").hide();
			$("#chargeRatioDiv").hide();
			$("#maxFeeDiv").hide();
			$("#minFeeDiv").hide();
		}

		function d(){
			$("#bankCardTypeDiv").hide();
			$("#chargeFeeDiv").hide();
			$("#chargeRatioDiv").hide();
			$("#maxFeeDiv").hide();
			$("#minFeeDiv").hide();
			$("#bankNoDiv").hide();
		}
		function f(){
			$("#bankCardTypeDiv").hide();
			$("#chargeTypeDiv").hide();
			$("#chargeRatioDiv").hide();
			$("#maxFeeDiv").hide();
			$("#minFeeDiv").hide();
			$("#chargeFeeDiv").hide();
			$("#bankNoDiv").hide();
		}


		function chargeDeductTypeChange(type){
			if(type == "INDEDU"){
				$("#chargeSourceDiv").show();
				$("#chargeCollectionTypeDiv").find("input[value='SETTLE']").attr("checked",'checked');
				$("#chargeCollectionTypeDiv").find("input[value='RETIME']").attr("disabled",'disabled');
			}else{
				$("#chargeSourceDiv").hide();
				$("#chargeCollectionTypeDiv").find("input[value='RETIME']").attr("checked",'checked');
				$("#chargeCollectionTypeDiv").find("input[value='SETTLE']").attr("disabled",'disabled');
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
	<style>
		.radio_inout label{
			width:60px;
			display:inline-block;
			text-align:left;
			
		}
		.contk_l{
			float: left;
			margin-left:20px;
		}
		.form-horizontal .contk_r{
			float: left;
			margin-left: 12px;
			position: relative;
			bottom: 3px;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/merchant/merchantProductRate?cache=1">产品配置列表</a></li>
		<li class="active"><a href="${ctx}/merchant/merchantRateNew/edit?id=${merchantRateNew.id}">产品配置<shiro:hasPermission name="merchant:merchantRateNew:edit">${not empty merchantRateNew.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="merchant:merchantRateNew:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="merchantRateNew" action="${ctx}/merchant/merchantRateNew/editSave" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="merchantLoginName"/>
		<input type="hidden" name="returnUrl" value="edit"/>
		<sys:message content="${message}"/>	
		<input type="hidden" id="msg" value = "${msg}" />
		<input type="hidden" id="productType"/>	
		<div class="control-group">
			<label class="control-label">商户编码：</label>
			<div class="controls">
				<input id="merchantId" name="merchantId" value="${merchantRateNew.merchantId}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户公司名称：</label>
			<div class="controls">
				<input name="merchantCompanyName" value="${merchantRateNew.merchantCompanyName}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务类型：</label>
			<div class="controls">
				<input value="${merchantRateNew.businessType}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品名称：</label>
			<div class="controls">
				<input id="productName" name="productName" value="${merchantRateNew.productName}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				<form:hidden path="productCode" id="productCode"/>
			</div>
		</div>
			
		<div class="control-group" id="bankCardTypeDiv">
			<label class="control-label">银行卡类型：</label>
			<div class="controls radio_inout">
				<form:radiobuttons id="bankCardType" path="bankCardType" items="${fns:getEnumList('rateBankcardType')}" itemLabel="name" itemValue="value" htmlEscape="false" class="" onchange="bankCardTypeChange(this)"/>
			</div>
		</div>
		<form:hidden path="bankCardType" id="bankCardTypeHidden"/>

		<div class="control-group" id="bankNoDiv">
			<label class="control-label">银行名称：</label>
			<div class="controls">
			    <input id="bankName" name="bankName" value="${merchantRateNew.bankName}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div>
		<form:hidden path="bankNo" id="bankNo"/>
		<div class="control-group" id="chargeTypeDiv">
			<label class="control-label">计费类型：</label>
			<div class="controls">
				<form:select id="chargeType" path="chargeType" class="input-xlarge required" onchange="chargeTypeChange(this.options[this.options.selectedIndex].value);">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getEnumList('costType')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label charge_name">手续费费用(%)：</label>
			<div class="contk_l"><form:input class="input-mini" path="chargeMin"/>~<form:input class="input-mini" path="chargeMax"/></div>
			<div class="controls contk_r" id="chargeRatioDiv">
				<form:input id="chargeRatio" path="chargeRatio" htmlEscape="false" maxlength="100" class="input-xlarge required" onkeyup="parent.amountCheck(this);"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>

			<div class="controls contk_r" id="chargeFeeDiv">
				<form:input id="chargeFee" path="chargeFee" htmlEscape="false" maxlength="100" class="input-xlarge required" onkeyup="parent.amountCheck(this);"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label charge_name">手续费费用(%)：</label>
			<div class="contk_l"><form:input class="input-mini" path="chargeMin2"/>~<form:input class="input-mini" path="chargeMax2"/></div>
			<div class="controls contk_r" id="chargeRatioDiv2">
				<form:input id="chargeRatio2" path="chargeRatio2" htmlEscape="false" maxlength="100" class="input-xlarge" onkeyup="parent.amountCheck(this);"/>
			</div>

			<div class="controls contk_r" id="chargeFeeDiv2">
				<form:input id="chargeFee2" path="chargeFee2" htmlEscape="false" maxlength="100" class="input-xlarge" onkeyup="parent.amountCheck(this);"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label charge_name">手续费费用(%)：</label>
			<div class="contk_l"><form:input class="input-mini" path="chargeMin3"/>~<form:input class="input-mini" path="chargeMax3"/></div>
			<div class="controls contk_r" id="chargeRatioDiv3">
				<form:input id="chargeRatio3" path="chargeRatio3" htmlEscape="false" maxlength="100" class="input-xlarge" onkeyup="parent.amountCheck(this);"/>
			</div>

			<div class="controls contk_r" id="chargeFeeDiv3">
				<form:input id="chargeFee3" path="chargeFee3" htmlEscape="false" maxlength="100" class="input-xlarge" onkeyup="parent.amountCheck(this);"/>
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
			<shiro:hasPermission name="merchant:merchantRateNew:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="return pass();"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>