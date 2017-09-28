<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单表管理</title>
	<meta name="decorator" content="default"/>

	<link rel="stylesheet" type="text/css" href="${ctxStatic}/bootstrap/select/assets/jquery.multiselect.css"/>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/bootstrap/select/assets/jquery.multiselect.filter.css"/>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/bootstrap/select/assets/style.css"/>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/bootstrap/select/assets/prettify.css"/>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/bootstrap/select/assets/jquery-ui.css"/>

	<script type="text/javascript" src="${ctxStatic}/bootstrap/select/assets/jquery-ui.js"></script>
	<script type="text/javascript" src="${ctxStatic}/bootstrap/select/src/jquery.multiselect.js"></script>
	<script type="text/javascript" src="${ctxStatic}/bootstrap/select/src/jquery.multiselect.filter.js"></script>
	<script type="text/javascript" src="${ctxStatic}/bootstrap/select/assets/prettify.js"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			$("#orderSettleDiv").find("input[value='1']").attr("checked",'checked');
			$("#statusDiv").find("input[value='ENABLE']").attr("checked",'checked');
			$("#accountTypeDiv").find("input[value='COMMON']").attr("checked",'checked');
			$("#businessTypeDiv").find("input[value='OWNBAK']").attr("checked",'checked');
			$("#accountTypeDiv").hide();
			$("#businessTypeDiv").hide();
//			$("#merchantNoDiv").hide();
			$("#bankName").val($("#bankNo").children('option:selected').html());
			$("#costT").val($("#costType").children('option:selected').html());
			if ($("#costT").val() == "按比例"){
				$("#costCount").hide();
				$("#costRate").show();
			}else{
				$("#costCount").show();
				$("#costRate").hide();
			}
			$("#inputForm").validate({
				submitHandler: function(form){

                    var bankNo = $("#bankNo").val();
                    if(bankNo == "" || typeof bankNo=='undefined'|| bankNo==null){
                        alertx("请选择银行名称!");
                        return false;
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

		function Sel2change(a){
			if(a == "按比例"){
			   $("#cost1").html("‰");
			   $("#costCount").hide();
			   $("#costRate").show();
			}else{
			   $("#cost1").html("元");
			   $("#costCount").show();
			   $("#costRate").hide();
				$(".error_rate").html('<font color="red">*</font>');
			}
		}
		function Sel3change(a){
			if(a == "周期"){
			   $("#settlePeriod").show();
			}else{
			   $("#settlePeriod").hide();	
			}		   
		}
		function chargeDeductTypeChange(type){
			if(type == "INDEDU"){
				$("#chargeReturnDiv").show();
			}else{
				$("#chargeReturnDiv").hide();
			}
		}
		function channelTypeChange(type){
//			$("#merchantNumber").val("");
//			$("#merchantNoDiv").hide();
			if(type == "BATCHP"){
				$("#accountTypeDiv").find("input[value='COMMON']").attr("checked",false);
				$("#accountTypeDiv").find("span:eq(2)").hide();
				$("#businessTypeDiv").find("input[value='OWNBAK']").attr("checked",false);
				/*$("#cardTypeDiv").find("input[value='SAVING']").attr("disabled",false);
				$("#cardTypeDiv").find("input[value='CREDIT']").attr("disabled",false);*/
				$("#accountTypeDiv").show();
				$("#businessTypeDiv").show();
			}else{
				/*if(type == "B2CEBK"||type == "WECHAT"||type == "B2BEBK"||type == "ALIPAY"||type == "OFFACC"||type == "WAPPAY"||type == "QRCODE"){
					$("#merchantNoDiv").show();
				}*/
				$("#accountTypeDiv").find("input[value='COMMON']").attr("checked",'checked');
				$("#businessTypeDiv").find("input[value='OWNBAK']").attr("checked",'checked');
				/*$("#cardTypeDiv").find("input[value='SAVING']").attr("disabled",false);
				$("#cardTypeDiv").find("input[value='CREDIT']").attr("disabled",false);*/
				$("#accountTypeDiv").hide();
				$("#businessTypeDiv").hide();
				$("#cardTypeDiv").show();
			}
		}
		function accountTypeChange(type){
			if(type=="PUBLIC"){
				$("#cardTypeDiv").hide();
				$("#cardTypeDiv").find("input[value='SAVING']").attr("checked",'checked');
				/*$("#cardTypeDiv").find("input[value='UNKNOW']").attr("checked",'checked');
				$("#cardTypeDiv").find("input[value='SAVING']").attr("disabled",true);
				$("#cardTypeDiv").find("input[value='CREDIT']").attr("disabled",true);
				/!*$("#cardTypeCode").val("UNKNOW");*!/*/
			}else{
				$("#cardTypeDiv").show();
				/*$("#cardTypeDiv").find("input[value='SAVING']").attr("disabled",false);
				$("#cardTypeDiv").find("input[value='CREDIT']").attr("disabled",false);*/
			}
		}
		function pass () {
			if($("#costType").children('option:selected').html()=="按比例"){
				var rate= $("#costRate").val();
				if(parseFloat(rate)>parseFloat(1000.0000)){
					$(".error_rate").html('<font color="red">按比例成本最大值为1000.0000</font>');
					return false;
				}else{
					$(".error_rate").html('<font color="red">*</font>');
				}
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/route/payChannel/">支付通道列表</a></li>
		<li class="active"><a href="${ctx}/route/payChannel/form?id=${payChannels.id}">支付通道<shiro:hasPermission name="route:payChannel:edit">${not empty payChannel.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="route:payChannel:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="payChannel" action="${ctx}/route/payChannel/save?bankNoFind=&channelPartnerCodeFind=&channelTypeCodeFind=&cardTypeCodeFind=&selectStatusFind=&pageNo=1" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">银行名称：</label>
			<div class="controls">			
			   <%-- <form:select id="bankNo" multiple = "multiple" path="bankNo" name="bankNo"  class="input-xlarge required" cssStyle="width: 310px">
				   <form:options items="${fns:getBankInfoList()}" itemLabel="bankName" itemValue="bankNo" htmlEscape="false" />
			    </form:select>
			    <span class="help-inline"><font color="red">*</font> </span>--%>
				   <select multiple="multiple" name="bankNo" id="bankNo" class="input-xlarge required" style="width:312px">
					   <c:forEach items="${fns:getBankInfoList()}" var="bankInfoFor">
						   <option value="${bankInfoFor.bankNo}">${bankInfoFor.bankName}</option>
					   </c:forEach>
				   </select>
				   <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">通道合作方：</label>
			<div class="controls">
			    <form:radiobuttons path="channelPartnerCode" items="${fns:getEnumList('channelPartner')}" itemLabel="name" itemValue="value" htmlEscape="false"  class="input-xlarge required" /> 
			    <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属主体：</label>
			<div class="controls">
				<form:input path="merchantSubject" name="merchantSubject" style="width:300px"  htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付通道类型：</label>
			<div class="controls">
			    <form:radiobuttons id="channelTypeCode" path="channelTypeCode" items="${fns:getEnumList('channelType')}" itemLabel="name" itemValue="value" htmlEscape="false"  class="input-xlarge required" onchange="channelTypeChange(this.value)"/>
			    <span class="help-inline"><font color="red">*</font> </span>    
			</div>
		</div>
		<div class="control-group" id="merchantNoDiv">
			<label class="control-label">通道侧商户号：</label>
			<div class="controls">
				<form:input path="merchantNumber" id="merchantNumber"   style="width:300px" maxlength="50" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group" id="accountTypeDiv">
			<label class="control-label">对公对私标识：</label>
			<div class="controls">
			    <form:radiobuttons id="accountType" path="accountType" items="${fns:getEnumList('accountType')}" itemLabel="name" itemValue="value" htmlEscape="false"  class="input-xlarge required" onchange="accountTypeChange(this.value)"/>
			    <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="cardTypeDiv">
			<label class="control-label" >银行卡类型：</label>
			<div class="controls">
				<form:radiobuttons  path="cardTypeCode" id="cardTypeCode" items="${fns:getEnumList('rateBankcardType')}" itemLabel="name" itemValue="value" htmlEscape="false"  class="input-xlarge required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group"  id="businessTypeDiv">
			<label class="control-label">付款类型：</label>
			<div class="controls">
			    <form:radiobuttons id="businessType" path="businessType" items="${fns:getEnumList('businessType')}" itemLabel="name" itemValue="value" htmlEscape="false"  class="input-xlarge required" />
			    <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续费扣除方式：</label>
			<div class="controls">
				<form:radiobuttons path="chargeDeductType" items="${fns:getEnumList('chargeDeductType')}" itemLabel="name" itemValue="value" htmlEscape="false"  class="input-xlarge required"  onchange="chargeDeductTypeChange(this.value)"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="chargeReturnDiv">
			<label class="control-label">是否退还手续费：</label>
			<div class="controls">
				<span><input id="chargeReturnTag1" path="chargeReturnTag" name="chargeReturnTag" type="radio" value="1" <c:if test="${payChannel.chargeReturnTag=='1'}">checked="checked"</c:if>  class="input-xlarge required" /><label for="chargeReturnTag1">是</label></span>
				<span><input id="chargeReturnTag2" path="chargeReturnTag" name="chargeReturnTag" type="radio" value="0" <c:if test="${payChannel.chargeReturnTag=='0'}">checked="checked"</c:if>  class="input-xlarge required" /><label for="chargeReturnTag2">否</label></span>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="orderSettleDiv">
			<label class="control-label">通道结算周期：</label>
			<div class="controls">
				<span><input path="orderSettlePeriod" id="orderSettlePeriod1" name="orderSettlePeriod" type="radio" value="1" <c:if test="${payChannel.orderSettlePeriod=='1'}">checked="checked"</c:if>  class="input-xlarge required" /><label for="orderSettlePeriod1">T+1</label></span>
				<span><input path="orderSettlePeriod" id="orderSettlePeriod2" name="orderSettlePeriod" type="radio" value="0" <c:if test="${payChannel.orderSettlePeriod=='0'}">checked="checked"</c:if>  class="input-xlarge required" /><label for="orderSettlePeriod2">T+0</label></span>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">有效开始时间：</label>
			<div class="controls">
				<span><input id="effectStartDate" name="effectStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required "
					value="<fmt:formatDate value="${payChannel.effectStartDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,maxDate:'#F{$dp.$D(\'effectEndDate\')}'});"/></span>
			    <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">有效结束时间：</label>
			<div class="controls">
				<input id="effectEndDate" name="effectEndDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${payChannel.effectEndDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,minDate:'#F{$dp.$D(\'effectStartDate\')}'});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成本类型：</label>
			<div class="controls">
				<form:select path="costType" style="width:150px"  class="input-xlarge required"  onchange="Sel2change(this.options[this.options.selectedIndex].text);">
					<form:options items="${fns:getEnumList('costType')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
				</form:select>
				<input type="hidden" name="costT" id="costT" class="input-xlarge required" >
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成本：</label>
			<div class="controls">
			    <form:input id="costCount" path="costCount" style="width:150px" htmlEscape="false"  onkeyup="parent.amountCheck(this);" onpaste="return false"  class="input-xlarge required "/>
				<form:input id="costRate" path="costRate" style="width:150px" htmlEscape="false"  onkeyup="parent.amountCheck(this);" onpaste="return false"  class="input-xlarge required " maxlength="18"/>
				<span id="cost1" ><c:if test="${empty payChannel.costType||payChannel.costType=='RATIOD'}">‰</c:if>
				<c:if test="${payChannel.costType=='COUNTD'}">元</c:if></span>
				<span class="help-inline error_rate " ><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续费结算类型：</label>
			<div class="controls">
				<form:select path="settleType" style="width:150px"  class="input-xlarge required"  onchange="Sel3change(this.options[this.options.selectedIndex].text);">
					<form:options items="${fns:getEnumList('settleType')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
				</form:select>
			    <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>         
		<div id="settlePeriod" class="control-group">
			<label class="control-label">手续费结算周期：</label>
			<div class="controls">
			    <form:radiobuttons path="settlePeriod" items="${fns:getEnumList('settlePeriod')}" itemLabel="name" itemValue="value" htmlEscape="false"  class="input-xlarge required" /> 
			    <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" style="display:none">
			<label class="control-label">合约时间：</label>
			<div class="controls">
				<input name="contractDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required "
					value="<fmt:formatDate value="${payChannel.contractDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">优先级别：</label>
			<div class="controls">
			     <select  path="sort" name="sort" style="width:100px" class="input-xlarge" >
			         <option value="1" <c:if test="${payChannel.sort=='1'}">selected="selected"</c:if>  maxlength="6"  class="input-xlarge">默认</option>
			         <option value="" <c:if test="${empty payChannel.sort}">selected="selected"</c:if>  maxlength="6"  class="input-xlarge">无</option>
				 </select>		
	             <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通道限额&nbsp;&nbsp;&nbsp;</label>
		</div>
		<div class="control-group">
			<label class="control-label">单笔限额：</label>
			<div class="controls">
				<form:input path="perlimitAmount" name="perlimitAmount" style="width:150px" onkeyup="parent.amountCheck(this);" onpaste="return false" htmlEscape="false"  class="input-xlarge required"/>
				<span id="cost2">元</span>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单日限额：</label>
			<div class="controls">
				<form:input path="daylimitAmount" name="daylimitAmount" style="width:150px" onkeyup="parent.amountCheck(this);" onpaste="return false"  htmlEscape="false" class="input-xlarge required"/>
				<span id="cost3">元</span>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单月限额：</label>
			<div class="controls">
				<form:input path="monlimitAmount" name="monlimitAmount" style="width:150px" onkeyup="parent.amountCheck(this);" onpaste="return false"  htmlEscape="false"  class="input-xlarge"/>
				<span id="cost4">元</span>
			</div>
		</div>
		<div class="control-group" id="statusDiv">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:radiobuttons path="status"  items="${fns:getEnumList('commonStatus')}" itemLabel="name" itemValue="value" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="route:payChannel:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="return pass()"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<script type="text/javascript">
        $("#bankNo").multiselect().multiselectfilter();
	</script>
</body>
</html>