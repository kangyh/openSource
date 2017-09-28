<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#settleT").val($("#settleType").children('option:selected').html());
			$("#costT").val($("#costType").children('option:selected').html());

			if ($("#costT").val() == "按比例"){
				$("#costCount").hide();
				$("#costRate").show();
			}else{
				$("#costCount").show();
				$("#costRate").hide();
			}
			if ($("#settleT").val() == "实时"){
			    $("#settlePeriod").hide();
			 }
			if ($("#chargeDeductT").val() == "EXDEDU"){
				$("#chargeReturnDiv").hide();
			}
//			$("#merchantNoDiv").hide();
			if ($("#channelT").val() == "批付"){
				$("#accountDiv").show();
				$("#businessDiv").show();
			}else{
				/*if($("#channelT").val() == "B2C网银"||$("#channelT").val() == "微信"||$("#channelT").val() == "支付宝"||$("#channelT").val() == "B2B网银"||$("#channelT").val() == "二维码"||$("#channelT").val() == "公众号"||$("#channelT").val() == "H5"){
					$("#merchantNoDiv").show();
				}*/
				$("#accountDiv").hide();
				$("#businessDiv").hide();
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
		});
		function Sel1change(a){
			if(a == "按比例"){
				$("#cost1").html("‰");
				$("#costCount").hide()
				$("#costRate").show();
			}else{
				$("#cost1").html("元");
				$("#costCount").show();
				$("#costRate").hide();
				$(".error_rate").html('<font color="red">*</font>');

			}
		}
		function Sel2change(a){
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
		function pass () {
            if(!confirm("请确认提交此操作?")){
                return false;
            }else{
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
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/route/payChannel/">支付通道列表</a></li>
		<li class="active"><a href="${ctx}/route/payChannel/update?id=${payChannel.id}">支付通道修改</a></li>
		<%-- <li class="active"><a href="${ctx}/route/payChannel/form?id=${payChannels.id}">支付通道<shiro:hasPermission name="route:payChannel:edit">${not empty payChannel.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="route:payChannel:edit">查看</shiro:lacksPermission></a></li> --%>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="payChannel" action="${ctx}/route/payChannel/save?bankNoFind=${bankNoFind}&channelPartnerCodeFind=${channelPartnerCodeFind}&channelTypeCodeFind=${channelTypeCodeFind}&cardTypeCodeFind=${cardTypeCodeFind}&selectStatusFind=${selectStatusFind}&pageNo=${pageNo}" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">银行名称：</label>
			<div class="controls">${payChannel.bankName}</div>
		</div>
		<div class="control-group">
			<label class="control-label">通道合作方：</label>
			<div class="controls">${payChannel.channelPartnerName}</div>
		</div>
		<div class="control-group" >
			<label class="control-label">所属主体：</label>
			<div class="controls">
				<form:input path="merchantSubject" id="merchantSubject"   style="width:300px" maxlength="50" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付通道类型：</label>
			<div class="controls">${payChannel.channelTypeName}</div>
			<input type="hidden" name="channelT" id="channelT" class="input-xlarge required" value="${payChannel.channelTypeName}">
		</div>
		<div class="control-group" id="merchantNoDiv">
			<label class="control-label">通道侧商户号：</label>
			<div class="controls">
				<form:input path="merchantNumber" id="merchantNumber"   style="width:300px" maxlength="50" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div id="cardTypeDiv" class="control-group">
			<label class="control-label">银行卡类型：</label>
			<div class="controls">${payChannel.cardTypeName}</div>
			<input type="hidden" name="cardT" id="cardT" class="input-xlarge required" value="${payChannel.cardTypeName}">
		</div>
		<div id="accountDiv" class="control-group">
			<label class="control-label">对公对私标识：</label>
			<div class="controls">${payChannel.accountType}</div>
		</div>
		<div id="businessDiv" class="control-group">
			<label class="control-label">付款类型：</label>
			<div class="controls">${payChannel.businessType}</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续费扣除方式：</label>
			<div class="controls">
				<form:radiobuttons path="chargeDeductType" items="${fns:getEnumList('chargeDeductType')}" itemLabel="name" itemValue="value" htmlEscape="false"  class="input-xlarge required"  onchange="chargeDeductTypeChange(this.value)"/>
				<input type="hidden" name="chargeDeductT" id="chargeDeductT" class="input-xlarge required" value="${payChannel.chargeDeductType}">
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="chargeReturnDiv">
			<label class="control-label">是否退还手续费：</label>
			<div class="controls">
				<td><input path="chargeReturnTag" name="chargeReturnTag" type="radio" value="1" <c:if test="${payChannel.chargeReturnTag=='1'}">checked="checked"</c:if>  class="input-xlarge required" />是</td>
				<td><input path="chargeReturnTag" name="chargeReturnTag" type="radio" value="0" <c:if test="${payChannel.chargeReturnTag=='0'}">checked="checked"</c:if>  class="input-xlarge required" />否</td>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="orderSettleDiv">
			<label class="control-label">通道结算周期：</label>
			<div class="controls">
				<span><input path="orderSettlePeriod" name="orderSettlePeriod" type="radio" value="1" <c:if test="${payChannel.orderSettlePeriod=='1'}">checked="checked"</c:if>  class="input-xlarge required" />T+1</span>
				<span><input path="orderSettlePeriod" name="orderSettlePeriod" type="radio" value="0" <c:if test="${payChannel.orderSettlePeriod=='0'}">checked="checked"</c:if>  class="input-xlarge required" />T+0</span>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">有效开始时间：</label>
			<div class="controls">
				<input name="effectStartDate" type="text"  maxlength="20" class="input-medium Wdate required "
					value="<fmt:formatDate value="${payChannel.effectStartDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">有效结束时间：</label>
			<div class="controls">
				<input name="effectEndDate" type="text"  maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${payChannel.effectEndDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成本类型：</label>
			<div class="controls">
				<form:select path="costType" style="width:150px"  class="input-xlarge required"  onchange="Sel1change(this.options[this.options.selectedIndex].text);">
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
				<form:input id="costRate" path="costRate" style="width:150px" htmlEscape="false"  onkeyup="parent.amountCheck(this);" onpaste="return false"  class="input-xlarge required " maxlength="9"/>
				<span id="cost1" ><c:if test="${empty payChannel.costType||payChannel.costType=='RATIOD'}">‰</c:if>
				<c:if test="${payChannel.costType=='COUNTD'}">元</c:if></span>
				<span class="help-inline error_rate"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续费结算类型：</label>
			<div class="controls">
				<form:select  path="settleType" style="width:150px"  class="input-xlarge required"  onchange="Sel2change(this.options[this.options.selectedIndex].text);">
					<form:options items="${fns:getEnumList('settleType')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
				</form:select>
				<input type="hidden" name="settleT" id="settleT" class="input-xlarge required" >
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
		<div class="control-group"  style="display:none">
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
	             <span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
			    <form:radiobuttons path="status"  items="${fns:getEnumList('commonStatus')}" itemLabel="name" itemValue="value" htmlEscape="false" class="input-xlarge required"/> 
			    <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通道限额&nbsp;&nbsp;&nbsp;</label>
		</div>
		<div class="control-group">
			<label class="control-label">单笔限额：</label>
			<div class="controls">
				<form:input  path="perlimitAmount" name="perlimitAmount" style="width:150px" onkeyup="parent.amountCheck(this);" onpaste="return false" htmlEscape="false" class="input-xlarge required"/>
				<span id="cost2">元</span>
				<span class="help-inline error_per"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单日限额：</label>
			<div class="controls">
				<form:input  path="daylimitAmount" name="daylimitAmount" style="width:150px" onkeyup="parent.amountCheck(this);" onpaste="return false"  htmlEscape="false"  class="input-xlarge required"/>
				<span id="cost3">元</span>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单月限额：</label>
			<div class="controls">
				<form:input id="monlimitAmount" path="monlimitAmount" name="monlimitAmount" style="width:150px" onkeyup="parent.amountCheck(this);" onpaste="return false"  htmlEscape="false"  class="input-xlarge"/>
				<span id="cost4">元</span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="route:payChannel:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="return pass()"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>