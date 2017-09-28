<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品配置管理</title>
	<meta name="decorator" content="default"/>
    <script src="${ctxStatic}/merchant/rate.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			/*ip域名*/
			$(".wenhao").on("mouseover",function(){
			    $(".modify_tip").show();
			    var src = $(this).find("img").attr("src");
			    var src1 = src.replace("wenhao","wenhao1");
			    $(this).find("img").attr("src",src1);
			});
		    $(".wenhao").on("mouseout",function(){
		        $(".modify_tip").hide();
	            var src = $(this).find("img").attr("src");
	            var src1 = src.replace("wenhao1","wenhao");
		        $(this).find("img").attr("src",src1);
		    });
			//$("#name").focus();

			if($("#productCode1").val() === ""){
	            a();
	        }else{
	            getProductType($("#productCode1").val());
	            choose($("#productType").val(),$("#productName").val());
                productX($("#productCode1").val());
				if($("#chargeTypeSaving").val() === "RATIOD"){
					savingRatiod();
				}else if($("#chargeTypeSaving").val() === "COUNTD"){
					savingCountd();
				}
				if($("#chargeTypeCredit").val() === "RATIOD"){
                    creditRatiod();
                }else if($("#chargeTypeCredit").val() === "COUNTD"){
                    creditCountd();
                }
			}
			if($("#msg").val() != ""){
				parent.showThree();
				parent.changeThreeName($("#msg").val());
			}
			chargeDeductTypeChange($("#chargeDeductTypeDiv").find("input[checked='checked']").val());
			$("#inputForm").validate({
				submitHandler: function(form){
                    var code = $("#productCode").val();
                    var merchantId = $("#merchantId").val();
                    var savingType = $("#chargeTypeSaving").val();
                    var savingFee = $("#chargeFeeSaving").val();
                    if(savingType == 'RATIOD'){
                        savingFee = $("#chargeRatioSaving").val();
                    }
                    var creditType = $("#chargeTypeCredit").val();
                    var creditFee = $("#chargeFeeCredit").val();
                    if(creditType == 'RATIOD'){
                        creditFee = $("#chargeRatioCredit").val();
                    }
                    $.ajax({
                        type: "GET",
                        async : false,
                        url: "${ctx}/merchant/merchantRateNew/cost",
                        data: {'productCode':code,'merchantId':merchantId,
                            'savingType':savingType,'savingFee':savingFee,
                            'creditType':creditType,'creditFee':creditFee},
                        dataType: 'json',
                        success: function(data){
                            if(data.flag == "true" || data.flag == "trueCredit"){
                                var bankType = "储蓄卡";
                                var unit = "元";
                                if(savingType == "RATIOD"){
                                    unit = "‰";
                                }
                                if(data.flag == "trueCredit"){
                                    unit = "元";
                                    bankType = "信用卡";
                                    if(savingType == "RATIOD"){
                                        unit = "‰";
                                    }
                                }
                                top.$.jBox.confirm("产品"+bankType+"费率小于通道成本最大值("+data.cost+unit+")，是否确定配置？",'系统提示',function(v){
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
			//产品根据业务类型联动方法
			getProduct($("#businessType").val());
	        showLocation();
	        function showLocation() {
	            var title = '<option value="">请选择</option>';
	            $("#productCode").select2();
	            $('#businessType').change(function(){
	                $('#productCode').empty();
	                $('#productCode').parent().find(".select2-chosen").html("");
	                $('#productCode').append(title);
	                getProduct($("#businessType").val());
	            });
	        }
	        if($('#productCode1').val() != '' && $('#productCode1').val() != null){
                $("#productCode").find("option[value='"+$('#productCode1').val()+"']").attr("selected",true);
                $("#productCode").parent().find(".select2-chosen").text($("#productCode").find("option[value='"+$('#productCode1').val()+"']").text());
	        }
		});

	</script>
<style>
.radio_inout label{
	width:60px;
	display:inline-block;
	text-align:left;
}
.radio_inout1 span:nth-of-type(2){
	margin-left:542px;
}	
.modify_tip{
    position: absolute;
    top: -12px;
    left: 408px;
    background: #FFFFFF;
    border: 1px solid #D8D8D8;
    box-shadow: 0px 1px 5px 0px rgba(0,0,0,0.18);
    display: none;
}
.modify_tip p{
    font-size: 14px;
    margin:5px 15px;
    font-size: 12px;
    color: #999999;
}
.modify_tip p span{
    width: 6px;
    height: 6px;
    display: inline-block;
    border-radius: 50%;
    background: #D8D8D8;
    position: relative;
    right: 7px;
    bottom: 2px;
}

.wenhao img{
    width:20px;
    cursor:pointer;
}
.cop_s{
    width:420px;
}
</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/merchant/merchant/">商户列表</a></li>
		<li class="active"><a href="">产品配置<shiro:hasPermission name="merchant:merchantRateNew:edit">${not empty merchantRateNewVo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="merchant:merchantRateNew:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="merchantRateNewVo" action="${ctx}/merchant/merchantRateNew/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="merchantLoginName"/>
		<input type="hidden" id="productType"/>
        <input type="hidden" id="productCode1" value="${merchantRateNewVo.productCode}"/>
        <input type="hidden" id="isTX" value="${merchantRateNewVo.settleType}"/>
        <input type="hidden" id="productX" value="${productX}"/>
		<sys:message content="${message}"/>	
		<input type="hidden" id="msg" value = "${msg}" />	
		<div class="control-group">
			<label class="control-label">商户编码：</label>
			<div class="controls">
				<input id="merchantId" name="merchantId" value="${merchantRateNewVo.merchantId}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户公司名称：</label>
			<div class="controls">
				<input name="merchantCompanyName" value="${merchantRateNewVo.merchantCompanyName}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务类型：</label>
			<div class="controls">
				<form:select path="businessType" class="input-xlarge ">
					<form:options items="${fns:getEnumList('rateBusinessType')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品名称：</label>
			<div class="controls">
				<form:select id="productCode" path="productCode" class="input-xlarge required" onchange="productChange(this.options[this.options.selectedIndex]);">
					<form:option value="" label=""/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<form:hidden path="productName" id="productName"/>
			
		<!-- 结算周期  -->
		<div class="control-group" id="settleTypeDiv">
            <label class="control-label">结算类型：</label>
            <div class="controls">
                T+<select id="settleType" path="settleType" name="settleType" style="width:50px">
                    <option value="1" <c:if test="${merchantRateNewVo.settleType=='1'}">selected="selected"</c:if>  class="input-xlarge required">1</option>
                    <option value="0" <c:if test="${merchantRateNewVo.settleType=='0'}">selected="selected"</c:if>  class="input-xlarge required">0</option>
                </select>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group" id="settlementToDiv">
            <label class="control-label">结算至：</label>
            <div class="controls">
                <form:select id="settlementTo" path="settlementTo" class="input-xlarge required">
                    <form:option value="">请选择</form:option>
                    <form:options items="${fns:getEnumList('settlementTo')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
                </form:select>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        
        <!-- 技术签约 -->
        <div class="control-group" id="notifyUrlDiv">
            <label class="control-label">异步通知地址：</label>
            <div class="controls">
                <form:input id="notifyUrl" path="notifyUrl" htmlEscape="false" maxlength="512" class="input-xlarge"/>
            </div>
        </div>
        <div class="control-group" id="backUrlDiv">
            <label class="control-label">同步返回地址：</label>
            <div class="controls">
                <form:input id="backUrl" path="backUrl" htmlEscape="false" maxlength="512" class="input-xlarge"/>
            </div>
        </div>
        <div class="control-group" id="ipDomainDiv">
            <label class="control-label">ip/域名：</label>
            <div class="controls" style="position:relative">
                <form:input id="ipDomain" path="ipDomain" htmlEscape="false" maxlength="512" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font></span>
                <span class="wenhao"><img src="${ctxStatic}/images/wenhao.png"/></span>
                <div class="modify_tip">
                     <p><span></span>每个ip/域名之间只能用英文逗号进行分隔，不支持其他分隔符号方式</p>
                     <p><span></span>如以.*结尾，只需写到.*之前。例如192.168.*只需写成192.168</p>
                </div> 
            </div>
        </div>
        <div class="control-group" id="autographTypeDiv">
            <label class="control-label">签名方式：</label>
            <div class="controls">
                <form:radiobuttons id="autographType" path="autographType" items="${fns:getEnumList('merchantAutographType')}" itemLabel="name" itemValue="value" htmlEscape="false" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        
        <!-- 费率 -->
		<div class="control-group" id="chargeDeductTypeDiv">
			<label class="control-label">手续费扣除方式：</label>
			<div class="controls radio_inout">
				<form:radiobuttons id="chargeDeductType" path="chargeDeductType" items="${fns:getEnumList('chargeDeductType')}" itemLabel="name" itemValue="value" htmlEscape="false" onchange="chargeDeductTypeChange(this.value)" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="chargeSourceDiv">
			<label class="control-label">手续费来源：</label>
			<div class="controls radio_inout">
				<form:radiobuttons id="chargeSource" path="chargeSource" items="${fns:getEnumList('chargeSource')}" itemLabel="name" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="chargeCollectionTypeDiv">
			<label class="control-label ">手续费收取方式：</label>
			<div class="controls radio_inout">
				<form:radiobuttons id="chargeCollectionType" path="chargeCollectionType" items="${fns:getEnumList('chargeCollectionType')}" itemLabel="name" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="isRefundableDiv">
			<label class="control-label">退款时是否退还手续费：</label>
			<div class="controls radio_inout">
				<form:radiobuttons id="isRefundable" path="isRefundable" items="${fns:getEnumList('yesOrNo')}" itemLabel="name" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="statusDiv">
			<label class="control-label">规则状态：</label>
			<div class="controls radio_inout">
				<form:radiobuttons id="status" path="status" items="${fns:getEnumList('commonStatus')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group" id="ruleBeginTimeDiv">
			<label class="control-label">规则开始时间：</label>
			<div class="controls">
				<input id="ruleBeginTime" name="ruleBeginTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${merchantRateNewVo.ruleBeginTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:new Date(),maxDate:'#F{$dp.$D(\'ruleEndTime\')}'});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="ruleEndTimeDiv">
			<label class="control-label">规则结束时间：</label>
			<div class="controls">
				<input id="ruleEndTime" name="ruleEndTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${merchantRateNewVo.ruleEndTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'#F{$dp.$D(\'ruleBeginTime\')}'});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="bankCardTypeDiv">
            <label class="control-label">银行卡类型：</label>
            <div class="controls radio_inout1">
                <form:checkboxes id="bankCardType" path="bankCardType" items="${fns:getEnumList('rateBankcardType')}" itemLabel="name" itemValue="value" htmlEscape="false" class="required" onclick="bankCardTypeChange(this)"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group" >
            <div class="control-left" id="chargeTypeSavingDiv">
                <label class="control-label">计费类型：</label>
                <div class="controls">
                    <form:select id="chargeTypeSaving" path="chargeTypeSaving" class="input-medium"
                        onchange="chargeTypeSavingChange(this.options[this.options.selectedIndex].value);">
                        <form:option value="" label="请选择" />
                        <form:options items="${fns:getEnumList('costType')}"
                            itemLabel="name" itemValue="value" htmlEscape="false" />
                    </form:select>
                </div>
            </div>
            <div class="control-right" id="chargeTypeCreditDiv" style="margin-left:258px;">
                <label class="control-label">计费类型：</label>
                <div class="controls">
                    <form:select id="chargeTypeCredit" path="chargeTypeCredit" class="input-medium"
                        onchange="chargeTypeCreditChange(this.options[this.options.selectedIndex].value);">
                        <form:option value="" label="请选择" />
                        <form:options items="${fns:getEnumList('costType')}"
                            itemLabel="name" itemValue="value" htmlEscape="false" />
                    </form:select>
                </div>
            </div>
        </div>
        <div class="control-group" style="width:1200px;margin-bottom: 0px;padding-bottom:0px;border:none">
            <div class="control-left" style="width:600px;">
                <div id="chargeRatioSavingDiv">
                <label class="control-label">手续费费用(%)：</label>
                <div class="controls cop_s">
                    <form:input class="input-mini" path="chargeMinSavingRatiod"/>~<form:input class="input-mini" path="chargeMaxSavingRatiod"/>
                    <form:input id="chargeRatioSaving" path="chargeRatioSaving" htmlEscape="false" maxlength="100" class="input-medium required" onkeyup="parent.amountCheck(this);"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
                </div>
            </div>
            <div class="control-right" style="float:right">
                <div id="chargeRatioCreditDiv">
                <label class="control-label">手续费费用(%)：</label>
                <div class="controls cop_s">
                    <form:input class="input-mini" path="chargeMinCreditRatiod"/>~<form:input class="input-mini" path="chargeMaxCreditRatiod"/>
                    <form:input id="chargeRatioCredit" path="chargeRatioCredit" htmlEscape="false" maxlength="100" class="input-medium required" onkeyup="parent.amountCheck(this);"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
                </div>
            </div>
        </div>
        <div class="control-group" style="width:1200px;margin-bottom: 0px;padding-bottom:0px;border:none">
            <div class="control-left" style="width:600px;">
                <div id="chargeFeeSavingDiv">
                <label class="control-label">手续费费用(元)：</label>
                <div class="controls cop_s">
                    <form:input class="input-mini" path="chargeMinSavingCountd"/>~<form:input class="input-mini" path="chargeMaxSavingCountd"/>
                    <form:input id="chargeFeeSaving" path="chargeFeeSaving" htmlEscape="false" maxlength="100" class="input-medium required" onkeyup="parent.amountCheck(this);"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
                </div>
            </div>
            <div class="control-right" style="float:right">
                <div id="chargeFeeCreditDiv">
                <label class="control-label">手续费费用(元)：</label>
                <div class="controls cop_s">
                    <form:input class="input-mini" path="chargeMinCreditCountd"/>~<form:input class="input-mini" path="chargeMaxCreditCountd"/>
                    <form:input id="chargeFeeCredit" path="chargeFeeCredit" htmlEscape="false" maxlength="100" class="input-medium required" onkeyup="parent.amountCheck(this);"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
                </div>
            </div>
        </div>
        <div class="control-group" style="width:1200px;margin-bottom: 0px;padding-bottom:0px;border:none">
            <div class="control-left" style="width:600px;">
                <div id="chargeRatioSavingDiv2">
                    <label class="control-label">手续费费用(%)：</label>
                    <div class="controls cop_s">
                        <form:input class="input-mini" path="chargeMinSavingRatiod2"/>~<form:input class="input-mini" path="chargeMaxSavingRatiod2"/>
                        <form:input id="chargeRatioSaving2" path="chargeRatioSaving2" htmlEscape="false" maxlength="100" class="input-medium" onkeyup="parent.amountCheck(this);"/>
                    </div>
                </div>
            </div>
            <div class="control-right" style="float:right">
                <div id="chargeRatioCreditDiv2">
                    <label class="control-label">手续费费用(%)：</label>
                    <div class="controls cop_s">
                        <form:input class="input-mini" path="chargeMinCreditRatiod2"/>~<form:input class="input-mini" path="chargeMaxCreditRatiod2"/>
                        <form:input id="chargeRatioCredit2" path="chargeRatioCredit2" htmlEscape="false" maxlength="100" class="input-medium" onkeyup="parent.amountCheck(this);"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="control-group" style="width:1200px;margin-bottom: 0px;padding-bottom:0px;border:none">
            <div class="control-left" style="width:600px;">
                <div id="chargeFeeSavingDiv2">
                    <label class="control-label">手续费费用(元)：</label>
                    <div class="controls cop_s">
                        <form:input class="input-mini" path="chargeMinSavingCountd2"/>~<form:input class="input-mini" path="chargeMaxSavingCountd2"/>
                        <form:input id="chargeFeeSaving2" path="chargeFeeSaving2" htmlEscape="false" maxlength="100" class="input-medium" onkeyup="parent.amountCheck(this);"/>
                    </div>
                </div>
            </div>
            <div class="control-right" style="float:right">
                <div id="chargeFeeCreditDiv2">
                    <label class="control-label">手续费费用(元)：</label>
                    <div class="controls cop_s">
                        <form:input class="input-mini" path="chargeMinCreditCountd2"/>~<form:input class="input-mini" path="chargeMaxCreditCountd2"/>
                        <form:input id="chargeFeeCredit2" path="chargeFeeCredit2" htmlEscape="false" maxlength="100" class="input-medium" onkeyup="parent.amountCheck(this);"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="control-group" style="width:1200px;margin-bottom: 0px;padding-bottom:0px;border:none">
            <div class="control-left" style="width:600px;">
                <div id="chargeRatioSavingDiv3">
                    <label class="control-label">手续费费用(%)：</label>
                    <div class="controls cop_s">
                        <form:input class="input-mini" path="chargeMinSavingRatiod3"/>~<form:input class="input-mini" path="chargeMaxSavingRatiod3"/>
                        <form:input id="chargeRatioSaving3" path="chargeRatioSaving3" htmlEscape="false" maxlength="100" class="input-medium" onkeyup="parent.amountCheck(this);"/>
                    </div>
                </div>
            </div>
            <div class="control-right" style="float:right">
                <div id="chargeRatioCreditDiv3">
                    <label class="control-label">手续费费用(%)：</label>
                    <div class="controls cop_s">
                        <form:input class="input-mini" path="chargeMinCreditRatiod3"/>~<form:input class="input-mini" path="chargeMaxCreditRatiod3"/>
                        <form:input id="chargeRatioCredit3" path="chargeRatioCredit3" htmlEscape="false" maxlength="100" class="input-medium" onkeyup="parent.amountCheck(this);"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="control-group" style="width:1200px;margin-bottom: 0px;padding-bottom:0px;border:none">
            <div class="control-left" style="width:600px;">
                <div id="chargeFeeSavingDiv3">
                    <label class="control-label">手续费费用(元)：</label>
                    <div class="controls cop_s">
                        <form:input class="input-mini" path="chargeMinSavingCountd3"/>~<form:input class="input-mini" path="chargeMaxSavingCountd3"/>
                        <form:input id="chargeFeeSaving3" path="chargeFeeSaving3" htmlEscape="false" maxlength="100" class="input-medium" onkeyup="parent.amountCheck(this);"/>
                    </div>
                </div>
            </div>
            <div class="control-right" style="float:right">
                <div id="chargeFeeCreditDiv3">
                    <label class="control-label">手续费费用(元)：</label>
                    <div class="controls cop_s">
                        <form:input class="input-mini" path="chargeMinCreditCountd3"/>~<form:input class="input-mini" path="chargeMaxCreditCountd3"/>
                        <form:input id="chargeFeeCredit3" path="chargeFeeCredit3" htmlEscape="false" maxlength="100" class="input-medium" onkeyup="parent.amountCheck(this);"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="control-group" style="width:1200px;margin-bottom: 0px;padding-bottom:0px;border:none">
            <div class="control-left" style="width:600px;" >
                <div id="maxFeeSavingDiv">
                <label class="control-label">手续费最大值(元)：</label>
                <div class="controls cop_s">
                    <form:input id="maxFeeSaving" path="maxFeeSaving" htmlEscape="false" maxlength="100" class="input-medium required" onkeyup="parent.amountCheck(this);"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
                </div>
            </div>
            <div class="control-right" style="float:right">
                <div id="maxFeeCreditDiv">
                <label class="control-label">手续费最大值(元)：</label>
                <div class="controls cop_s">
                    <form:input id="maxFeeCredit" path="maxFeeCredit" htmlEscape="false" maxlength="100" class="input-medium required" onkeyup="parent.amountCheck(this);"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
                </div>
            </div>
        </div>
        <div class="control-group" style="width:1200px;margin-bottom: 0px;padding-bottom:0px;border:none">
            <div class="control-left" style="width:600px;">
                <div id="minFeeSavingDiv">
                <label class="control-label">手续费最小值(元)：</label>
                <div class="controls cop_s">
                    <form:input id="minFeeSaving" path="minFeeSaving" htmlEscape="false" maxlength="100" class="input-medium required" onkeyup="parent.amountCheck(this);"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
                </div>
            </div>
            <div class="control-right" style="float:right">
                <div id="minFeeCreditDiv">
                <label class="control-label">手续费最小值(元)：</label>
                <div class="controls cop_s">
                    <form:input id="minFeeCredit" path="minFeeCredit" htmlEscape="false" maxlength="100" class="input-medium required" onkeyup="parent.amountCheck(this);"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
                </div>
            </div>
        </div>
		<div class="form-actions">
			<shiro:hasPermission name="merchant:merchantRateNew:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="return pass();"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>