<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品配置管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/merchant/rate.js"></script>
	<script type="text/javascript">
        var saveValue = "";
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
                    var values = $("#inputForm").serialize();
                    //判断页面的值是否做过修改
                    if(values == saveValue){
                        alertx("<span style='color: red'>页面未做任何修改,不予保存</span>");
                    }else{
                        loading('正在提交，请稍等...');
                        form.submit();
                    }
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
		<li class="active"><a href="">产品配置<shiro:hasPermission name="merchant:merchantRateNew:edit">${not empty merchantProductRate.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="merchant:merchantRateNew:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="merchantProductRate" action="${ctx}/merchant/merchantProductRate/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" name="returnUrl" value="edit"/>
		<input type="hidden" id="isTX" value="${merchantProductRate.settleType}"/>
		<input type="hidden" id="productX" value="${productX}"/>
		<form:hidden path="rateAudit" value="S"/>
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
            <div class="controls">
                <form:input id="ipDomain" path="ipDomain" htmlEscape="false" maxlength="512" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
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
				<form:radiobuttons id="chargeDeductType" path="chargeDeductType" items="${fns:getEnumList('chargeDeductType')}" itemLabel="name" itemValue="value" htmlEscape="false" onchange="chargeDeductTypeChange(this.value)"/>
			</div>
		</div>
		<div class="control-group" id="chargeSourceDiv">
			<label class="control-label">手续费来源：</label>
			<div class="controls radio_inout">
				<form:radiobuttons id="chargeSource" path="chargeSource" items="${fns:getEnumList('chargeSource')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group" id="chargeCollectionTypeDiv">
			<label class="control-label ">手续费收取方式：</label>
			<div class="controls radio_inout">
				<form:radiobuttons id="chargeCollectionType" path="chargeCollectionType" items="${fns:getEnumList('chargeCollectionType')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group" id="isRefundableDiv">
			<label class="control-label">退款时是否退还手续费：</label>
			<div class="controls radio_inout">
				<form:radiobuttons id="isRefundable" path="isRefundable" items="${fns:getEnumList('yesOrNo')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
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
					value="<fmt:formatDate value="${merchantProductRate.ruleBeginTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:new Date(),maxDate:'#F{$dp.$D(\'ruleEndTime\')}'});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="ruleEndTimeDiv">
			<label class="control-label">规则结束时间：</label>
			<div class="controls">
				<input id="ruleEndTime" name="ruleEndTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${merchantProductRate.ruleEndTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'#F{$dp.$D(\'ruleBeginTime\')}'});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="merchant:merchantRateNew:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>