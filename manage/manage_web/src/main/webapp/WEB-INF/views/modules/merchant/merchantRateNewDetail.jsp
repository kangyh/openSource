<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品配置管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#chargeCollectionTypeDiv").find("input[value='RETIME']").attr("disabled",'disabled');
			$("#chargeCollectionTypeDiv").find("input[value='SETTLE']").attr("disabled",'disabled');
			if($("#productCode").val() === ""){
	            a();            
	        }else{
	            getProductType($("#productCode").val());
	            choose($("#productType").val(),$("#productName").val());
				productX($("#productCode").val());
	        }
			if($("#msg").val() != ""){
				parent.showThree();
				parent.changeThreeName($("#msg").val());
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
		
		function choose(type,name){
			console.log("type"+type);
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
	            e();
	            break;
	        case "REFUND"://退款
	            f();
	            break;
	        case "RENAME"://实名认证
	            g();
	            break;
	        case "SHARES"://平级分润
	            i();
	            break;
	        default://支付
	            //B2B支付,微信
	            if(name.indexOf("B2B") >= 0 || name.indexOf("微信") >= 0){
	                h();
	                break;
	            }
	            //其他支付
	            a();
	            break;
	        }
	    }

		//是否T+X方法
		function productX(code){
			var productX = $("#productX").val();
			var isTX = $("#isTX").val();
			var isSelected = "";
			if(isTX == "X"){
				isSelected = "selected='selected'";
			}
			if(productX.indexOf(code) >= 0){
				$("#settleType").append("<option value='X' "+isSelected+" class='input-xlarge required'>X</option>");
				$("#s2id_settleType").find(".select2-chosen").text($("#settleTypeDiv").find("option:selected").text());
			}else{
				$("#settleType option[value=X]").remove();
			}
		}
		
		function all(){
			$("#ruleBeginTimeDiv").show();
			$("#ruleEndTimeDiv").show();
			$("#chargeSourceDiv").show();
			$("#chargeDeductTypeDiv").show();
			$("#chargeCollectionTypeDiv").show();
			$("#isRefundableDiv").show();
			$("#statusDiv").show();
			$("#ipDomainDiv").show();
			$("#ipDomain").val("");
			$("#ruleBeginTime").val("");
			$("#ruleEndTime").val("");
			$("#settleTypeDiv").show();
            $("#settlementToDiv").show();
            $("#notifyUrlDiv").show();
            $("#backUrlDiv").show();
            $("#ipDomainDiv").show();
            $("#autographTypeDiv").show();
			$("#chargeSource").val("");
			$("#chargeDeductType").val("");
			$("#chargeCollectionType").val("");
			$("#isRefundable").val("");
			$("#status").val("");
			$("input[type='radio']").removeAttr('checked');
		}
		
		function a(){
			$("#chargeSourceDiv").hide();
			$("#isRefundableDiv").hide();
			$("#chargeSourceDiv").hide();
	        $("#chargeDeductTypeDiv").hide();
	        $("#chargeCollectionTypeDiv").hide();
	        $("#autographTypeDiv").hide();
		}
		function b(){
			$("#chargeSourceDiv").hide();
			$("#isRefundableDiv").hide();
			$("#chargeDeductTypeDiv").find("input[value='EXDEDU']").attr("checked",'checked');
			chargeDeductTypeChange("EXDEDU");
			$("#chargeDeductTypeDiv").hide();
			$("#chargeCollectionTypeDiv").hide();
			$("#autographTypeDiv").hide();
		}
		function c(){
			$("#chargeSourceDiv").hide();
			$("#isRefundableDiv").hide();
			$("#chargeDeductTypeDiv").find("input[value='EXDEDU']").attr("checked",'checked');
			chargeDeductTypeChange("EXDEDU");
			$("#chargeDeductTypeDiv").hide();
			$("#chargeCollectionTypeDiv").hide();
			$("#settleTypeDiv").hide();
            $("#settlementToDiv").hide();
            $("#autographTypeDiv").hide();
		}
		function d(){
			$("#ruleBeginTimeDiv").hide();
			$("#ruleEndTimeDiv").hide();
			$("#chargeSourceDiv").hide();
			$("#isRefundableDiv").hide();
			$("#chargeDeductTypeDiv").find("input[value='INDEDU']").attr("checked",'checked');
			chargeDeductTypeChange("INDEDU");
			$("#chargeDeductTypeDiv").hide();
			$("#chargeCollectionTypeDiv").hide();
			$("#settleTypeDiv").hide();
            $("#settlementToDiv").hide();
            $("#notifyUrlDiv").hide();
            $("#backUrlDiv").hide();
            $("#doMainDiv").hide();
            $("#autographTypeDiv").hide();
            $("#ruleBeginTimeDiv").hide();
            $("#ruleEndTimeDiv").hide();
            $("#ipDomainDiv").hide();
		}
		function e(){
			$("#chargeSourceDiv").hide();
			$("#isRefundableDiv").hide();
			$("#chargeDeductTypeDiv").find("input[value='EXDEDU']").attr("checked",'checked');
			chargeDeductTypeChange("EXDEDU");
			$("#chargeDeductTypeDiv").hide();
			$("#chargeCollectionTypeDiv").hide();
			$("#settleTypeDiv").hide();
            $("#settlementToDiv").hide();
            $("#notifyUrlDiv").hide();
            $("#backUrlDiv").hide();
            $("#ipDomainDiv").hide();
            $("#autographTypeDiv").hide();
            $("#ruleBeginTimeDiv").hide();
            $("#ruleEndTimeDiv").hide();
            $("#ipDomainDiv").hide();
		}
		function f(){
			$("#bankCardTypeDiv").hide();
			$("#chargeSourceDiv").hide();
			$("#chargeDeductTypeDiv").hide();
			$("#chargeCollectionTypeDiv").hide();
			$("#settleTypeDiv").hide();
            $("#settlementToDiv").hide();
            $("#notifyUrlDiv").hide();
            $("#backUrlDiv").hide();
            $("#ipDomainDiv").hide();
            $("#autographTypeDiv").hide();
            $("#ruleBeginTimeDiv").hide();
            $("#ruleEndTimeDiv").hide();
            $("#ipDomainDiv").hide();
		}
		function g(){
			$("#chargeTypeDiv").hide();
			$("#isRefundableDiv").hide();
			$("#chargeSourceDiv").hide();
			$("#chargeDeductTypeDiv").find("input[value='EXDEDU']").attr("checked",'checked');
			chargeDeductTypeChange("EXDEDU");
			$("#chargeDeductTypeDiv").hide();
			$("#chargeCollectionTypeDiv").hide();
			$("#settleTypeDiv").hide();
            $("#settlementToDiv").hide();
            $("#autographTypeDiv").hide();
		}
		function h(){
			$("#bankCardTypeDiv").hide();
			$("#chargeSourceDiv").hide();
			$("#isRefundableDiv").hide();
			$("#chargeSourceDiv").hide();
			$("#chargeDeductTypeDiv").hide();
            $("#chargeCollectionTypeDiv").hide();
			$("#autographTypeDiv").hide();
		}
		function i(){
			$("#chargeSourceDiv").hide();
			$("#isRefundableDiv").hide();
			$("#chargeDeductTypeDiv").find("input[value='EXDEDU']").attr("checked",'checked');
			chargeDeductTypeChange("EXDEDU");
			$("#chargeDeductTypeDiv").hide();
			$("#chargeCollectionTypeDiv").hide();
			$("#settleTypeDiv").hide();
            $("#settlementToDiv").hide();
            $("#autographTypeDiv").hide();
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
	        case "SHARES"://平级分润
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
					//$("#chargeSourceDiv").show();
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
	<style>
		.radio_inout label{
			width:60px;
			display:inline-block;
			text-align:left;
			
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/merchant/merchantProductRate?cache=1">产品配置列表</a></li>
		<li class="active"><a href="">产品配置查看</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="merchantProductRate" action="${ctx}/merchant/merchantProductRate/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" name="returnUrl" value="edit"/>
		<input type="hidden" id="isTX" value="${merchantProductRate.settleType}"/>
		<input type="hidden" id="productX" value="${productX}"/>
		<form:hidden path="merchantLoginName"/>
		<sys:message content="${message}"/>	
		<input type="hidden" id="productType"/>
		<input type="hidden" id="msg" value = "${msg}" />	
		<div class="control-group">
			<label class="control-label">商户编码：</label>
			<div class="controls">
				<input name="merchantId" value="${merchantProductRate.merchantId}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户公司名称：</label>
			<div class="controls">
				<input name="merchantCompanyName" value="${merchantProductRate.merchantCompanyName}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务类型：</label>
			<div class="controls">
				<input value="${merchantProductRate.businessType}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				<%-- <form:select path="businessType" class="input-xlarge ">
					<form:options items="${fns:getEnumList('rateBusinessType')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select> --%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品名称：</label>
			<div class="controls">
				<input id="productName" value="${merchantProductRate.productName}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				<%-- <form:select id="productCode" path="productCode" class="input-xlarge" onchange="productChange(this.options[this.options.selectedIndex]);">
					<form:option value="" label=""/>
					<form:options items="${fns:getProductList()}" itemLabel="name" itemValue="code" htmlEscape="false"/>
				</form:select> --%>
				<form:hidden path="productCode" id="productCode"/>
			</div>
		</div>
		
		<!-- 结算周期  -->
        <div class="control-group" id="settleTypeDiv">
            <label class="control-label">结算类型：</label>
            <div class="controls">
                T+${merchantProductRate.settleType}
            </div>
        </div>
        <div class="control-group" id="settlementToDiv">
            <label class="control-label">结算至：</label>
            <div class="controls">
				<input value="${merchantProductRate.settlementTo}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        
        <!-- 技术签约 -->
        <div class="control-group" id="notifyUrlDiv">
            <label class="control-label">异步通知地址：</label>
            <div class="controls">
                <input  value="${merchantProductRate.notifyUrl}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-group" id="backUrlDiv">
            <label class="control-label">同步返回地址：</label>
            <div class="controls">
                <input value="${merchantProductRate.backUrl}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-group" id="ipDomainDiv">
            <label class="control-label">ip/域名：</label>
            <div class="controls">
                <input value="${merchantProductRate.ipDomain}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-group" id="autographTypeDiv">
            <label class="control-label">签名方式：</label>
            <div class="controls">
                <input value="${merchantProductRate.autographType}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        
        <!-- 费率 -->
		<div class="control-group" id="chargeDeductTypeDiv">
			<label class="control-label">手续费扣除方式：</label>
			<div class="controls radio_inout">
                <input value="${merchantProductRate.chargeDeductType}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div>
		<div class="control-group" id="chargeSourceDiv">
			<label class="control-label">手续费来源：</label>
			<div class="controls radio_inout">
                <input value="${merchantProductRate.chargeSource}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div>
		<div class="control-group" id="chargeCollectionTypeDiv">
			<label class="control-label ">手续费收取方式：</label>
			<div class="controls radio_inout">
                <input value="${merchantProductRate.chargeCollectionType}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div>
		<div class="control-group" id="isRefundableDiv">
			<label class="control-label">退款时是否退还手续费：</label>
			<div class="controls radio_inout">
                <input value="${merchantProductRate.isRefundable}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div>
		<div class="control-group" id="statusDiv">
			<label class="control-label">规则状态：</label>
			<div class="controls radio_inout">
                <input value="${merchantProductRate.status}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div>
		<div class="control-group" id="ruleBeginTimeDiv">
			<label class="control-label">规则开始时间：</label>
			<div class="controls">
				<input id="ruleBeginTime" name="ruleBeginTime" type="text" readonly="readonly" style="border:0px;background-color:#fff;padding-top: 3px;"
					value="<fmt:formatDate value="${merchantProductRate.ruleBeginTime}" pattern="yyyy-MM-dd"/>" />
			</div>
		</div>
		<div class="control-group" id="ruleEndTimeDiv">
			<label class="control-label">规则结束时间：</label>
			<div class="controls">
				<input id="ruleEndTime" name="ruleEndTime" type="text" readonly="readonly" style="border:0px;background-color:#fff;padding-top: 3px;"
					value="<fmt:formatDate value="${merchantProductRate.ruleEndTime}" pattern="yyyy-MM-dd"/>" />
			</div>
		</div>

		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>