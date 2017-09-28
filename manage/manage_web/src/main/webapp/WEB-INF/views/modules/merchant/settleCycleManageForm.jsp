<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>结算周期管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
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
			if($("#msg").val() != ""){
				parent.showThree();
				parent.changeThreeName($("#msg").val());
			}
		});
		function productChange(option){
			var name = option.text;
			$("#productName").val(name);
			if($("#id").val() === ""){
				$("#statusDiv").find("input[value='ENABLE']").attr("checked",'checked');
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/merchant/settleCycleManage/">结算周期列表</a></li>
		<li class="active"><a href="">结算周期<shiro:hasPermission name="merchant:settleCycleManage:edit">${not empty settleCycleManage.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="merchant:settleCycleManage:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="settleCycleManage" action="${ctx}/merchant/settleCycleManage/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="merchantLoginName"/>
		<sys:message content="${message}"/>		
		<input type="hidden" id="msg" value = "${msg}" />
		<div class="control-group">
			<label class="control-label">商户编码：</label>
			<div class="controls">
				<input name="merchantId" value="${settleCycleManage.merchantId}" readonly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户公司名称：</label>
			<div class="controls">
				<input name="merchantCompanyName" value="${settleCycleManage.merchantCompanyName}" readonly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品名称：</label>
			<div class="controls">
			<c:choose>
				<c:when test="${empty settleCycleManage.id}">
					<form:select id="productCode" path="productCode" class="input-xlarge required" onchange="productChange(this.options[this.options.selectedIndex]);">
						<form:option value="" label=""/>
						<form:options items="${fns:getProductSettleList()}" itemLabel="name" itemValue="code" htmlEscape="false"/>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
					<form:hidden path="productName" id="productName"/>
				</c:when>
				<c:otherwise>
					<input name="productName" value="${settleCycleManage.productName}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
					<form:hidden path="productCode" id="productCode"/>
				</c:otherwise>
			</c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结算类型：</label>
			<div class="controls">
				T+<select  path="settleType" name="settleType" style="width:50px">
					<option value="0" <c:if test="${settleCycleManage.settleType=='0'}">selected="selected"</c:if>  class="input-xlarge required">0</option>
					<option value="1" <c:if test="${settleCycleManage.settleType=='1'}">selected="selected"</c:if>  class="input-xlarge required">1</option>
				</select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">最小结算金额：</label>
			<div class="controls">
				<form:input path="minSettlementAmount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div> --%>
		<div class="control-group" style="display:none;">
			<label class="control-label">有效开始时间：</label>
			<div class="controls">
				<input id="effectiveStartTime" name="effectiveStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${settleCycleManage.effectiveStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,maxDate:'#F{$dp.$D(\'effectiveEndTime\')}'});"/>
			</div>
		</div>
		<div class="control-group" style="display:none;">
			<label class="control-label">有效截止时间：</label>
			<div class="controls">
				<input id="effectiveEndTime" name="effectiveEndTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${settleCycleManage.effectiveEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,minDate:'#F{$dp.$D(\'effectiveStartTime\')}'});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结算至：</label>
			<div class="controls">
				<form:select path="settlementTo" class="input-xlarge required">
					<form:options items="${fns:getEnumList('settlementTo')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="statusDiv">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:radiobuttons path="status" items="${fns:getEnumList('commonStatus')}" itemLabel="name" itemValue="value" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="merchant:settleCycleManage:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>