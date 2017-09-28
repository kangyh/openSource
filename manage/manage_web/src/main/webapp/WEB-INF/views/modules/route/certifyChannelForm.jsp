<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>实名认证通道管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#div").hide();
			if($("#productCode").val() != "CP15"){
				$(".pcontrols input[value='HEEPAY']").parent().hide();
			}
			if($("#productCode").val() != "CP26"){
				$(".pcontrols input[value='JIXIN']").parent().hide();
			}
			if($("#productCode").val() != "CP50"){
				$(".pcontrols input[value='QAREDQ']").parent().hide();
			}else{
				$(".pcontrols input[value='LAKALA']").parent().hide();
			}
			if($("#div input[type=radio]:checked").val()){
				$("#div").show();
				$("#div input[type=radio]").attr("disabled",false);
			}
			/*通道合作方选择功能禁用 */
		    if($("#id").val() == ""){
				$(".pcontrols input[type=radio]").attr("disabled",false); 
			}else{
			    $(".pcontrols input[type=radio]").attr("disabled",true); 
			}
			if ($("#settleType").val() == "RETIME"){
				$("#settlePeriod").hide();
			}
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					$(".pcontrols input[type=radio]").attr("disabled",false);
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
			if(a == "周期"){
			   $("#settlePeriod").show();
			}else{
			   $("#settlePeriod").hide();	
			}		   
		}

		function channelPartnerChange(a){
			if(a == "LAKALA" || a == "QAREDQ" || a == "JIXIN"){
				$("#div").show();
			}
		}

		function channelTypeChange(radio){
			var name = $("input[name='channelTypeCode']:checked").next("label").text();
			$("#channelTypeName").val(name);
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/route/certifyChannel/details?id=${productId}">产品信息</a></li>
		<li class="active"><a>认证通道管理<shiro:hasPermission name="route:certifyChannel:edit">${not empty certifyChannel.id?'修改':'添加'}</shiro:hasPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="certifyChannel" action="${ctx}/route/certifyChannel/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
	    <input type=hidden name="productId" id="productId" class="input-xlarge required" value="${productId}">
        <form:hidden path="productCode" value="${productCode}"/>
		<div class="control-group">
			<label class="control-label">产品名称：</label>
			<div class="controls">${productName}</div>
			<%--<div class="controls">实名认证</div>--%>
		</div>
		<div  class="control-group">
			<label class="control-label">通道合作方：</label>
			<div class="pcontrols">&nbsp;&nbsp;&nbsp;
				<form:radiobuttons path="channelPartnerCode" items="${fns:getEnumList('certifyChannelPartner')}" itemLabel="name" itemValue="value" htmlEscape="false"  class="input-xlarge required"
				onchange="channelPartnerChange(this.value);"/>
			    <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<c:choose>
			<c:when test="${productCode=='CP25'}">
				<div  class="control-group" id="div" >
					<label class="control-label">通道类型：</label>
					<div class="pcontrols">&nbsp;&nbsp;&nbsp;
						<form:radiobuttons path="channelTypeCode" items="${fns:getDictList('CompanyLAKALA')}" itemLabel="label" itemValue="value"
										   htmlEscape="false"  class="input-xlarge required" onchange="channelTypeChange(this);"/>
						<span class="help-inline"><font color="red">*</font> </span>
						<form:hidden path="channelTypeName" value="实名认证"/>
					</div>
				</div>
			</c:when>
			<c:when test="${productCode=='CP26'}">
				<div  class="control-group" id="div" >
					<label class="control-label">通道类型：</label>
					<div class="pcontrols">&nbsp;&nbsp;&nbsp;
						<form:radiobuttons path="channelTypeCode" items="${fns:getDictList('PersonLAKALA')}" itemLabel="label" itemValue="value"
										   htmlEscape="false"  class="input-xlarge required" onchange="channelTypeChange(this);"/>
						<span class="help-inline"><font color="red">*</font> </span>
						<form:hidden path="channelTypeName" value="实名认证"/>
					</div>
				</div>
			</c:when>
            <c:when test="${productCode=='CP50'}">
                <div  class="control-group" id="div" >
                    <label class="control-label">通道类型：</label>
                    <div class="pcontrols">&nbsp;&nbsp;&nbsp;
                        <form:radiobuttons path="channelTypeCode" items="${fns:getDictList('PhoneQAREDQ')}" itemLabel="label" itemValue="value"
                                           htmlEscape="false"  class="input-xlarge required" onchange="channelTypeChange(this);"/>
                        <span class="help-inline"><font color="red">*</font> </span>
                        <form:hidden path="channelTypeName" value="实名认证"/>
                    </div>
                </div>
            </c:when>
			<c:when test="${productCode=='CP58'}">
				<div  class="control-group" id="div" >
					<label class="control-label">通道类型：</label>
					<div class="pcontrols">&nbsp;&nbsp;&nbsp;
						<form:radiobuttons path="channelTypeCode" items="${fns:getDictList('PhoneLAKALA')}" itemLabel="label" itemValue="value"
										   htmlEscape="false"  class="input-xlarge required" onchange="channelTypeChange(this);"/>
						<span class="help-inline"><font color="red">*</font> </span>
						<form:hidden path="channelTypeName" value="实名认证"/>
					</div>
				</div>
			</c:when>
			<c:otherwise>

			</c:otherwise>
		</c:choose>
		<div class="control-group">
			<label class="control-label">成本：</label>
			<div class="controls">
				<form:input path="cost" htmlEscape="false" onkeyup="parent.amountCheck(this);" onpaste="return false" class="input-xlarge required" /><span>元</span>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div  class="control-group">
			<label class="control-label">手续费结算类型：</label>
			<div class="controls">
				<form:select id="settleType" path="settleType" style="width:150px"  class="input-xlarge required"  onchange="Sel1change(this.options[this.options.selectedIndex].text);">
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
		<div class="control-group">
			<label class="control-label">优先级别：</label>
			<div class="controls">
			     <select  path="sort" name="sort" style="width:100px" class="input-xlarge" >
			         <option value="1" <c:if test="${certifyChannel.sort=='1'}">selected="selected"</c:if>  maxlength="6"  class="input-xlarge">默认</option>
			         <option value=""<c:if test="${empty certifyChannel.sort}">selected="selected"</c:if>  maxlength="6"  class="input-xlarge">无</option>
				 </select>		
	             <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="route:certifyChannel:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" onclick="return confirmation()" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>