<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>技术签约管理</title>
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
		<li><a href="${ctx}/merchant/merchantAutographInfo/">技术签约列表</a></li>
		<li class="active"><a href="">技术签约<shiro:hasPermission name="merchant:merchantAutographInfo:edit">${not empty merchantAutographInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="merchant:merchantAutographInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="merchantAutographInfo" action="${ctx}/merchant/merchantAutographInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="merchantLoginName"/>
		<sys:message content="${message}"/>		
		<input type="hidden" id="msg" value = "${msg}" />
		<div class="control-group">
			<label class="control-label">商户编码：</label>
			<div class="controls">
				<input name="merchantId" value="${merchantAutographInfo.merchantId}" readOnly="true" style="border:0px;background-color:#fff;"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户公司名称：</label>
			<div class="controls">
				<input name="merchantCompanyName" value="${merchantAutographInfo.merchantCompanyName}" readOnly="true" style="border:0px;background-color:#fff;"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品名称：</label>
			<div class="controls">
			<c:choose>
				<c:when test="${empty merchantAutographInfo.id}">
					<form:select id="productCode" path="productCode" class="input-xlarge required" onchange="productChange(this.options[this.options.selectedIndex]);">
						<form:option value="" label=""/>
						<form:options items="${fns:getProductList()}" itemLabel="name" itemValue="code" htmlEscape="false"/>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
					<form:hidden path="productName" id="productName"/>
				</c:when>
				<c:otherwise>
					<input name="productName" value="${merchantAutographInfo.productName}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
					<form:hidden path="productCode" id="productCode"/>
				</c:otherwise>
			</c:choose>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">异步通知地址：</label>
			<div class="controls">
				<form:input path="notifyUrl" htmlEscape="false" maxlength="512" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同步返回地址：</label>
			<div class="controls">
				<form:input path="backUrl" htmlEscape="false" maxlength="512" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">签名方式：</label>
			<div class="controls">
				<form:radiobuttons path="autographType" items="${fns:getEnumList('merchantAutographType')}" itemLabel="name" itemValue="value" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" style="display:none;">
			<label class="control-label">允许的接口类型：</label>
			<div class="controls">
				<form:checkboxes path="allowInterfaceTypes" items="${enumList}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group" style="display:none;">
			<label class="control-label">合同开始时间：</label>
			<div class="controls">
				<input id="startTime" name="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${merchantAutographInfo.startTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'#F{$dp.$D(\'endTime\')}'});"/>
			</div>
		</div>
		<div class="control-group" style="display:none;">
			<label class="control-label">合同结束时间：</label>
			<div class="controls">
				<input id="endTime" name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${merchantAutographInfo.endTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'#F{$dp.$D(\'startTime\')}'});"/>
			</div>
		</div>
		<div class="control-group" id="statusDiv">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:radiobuttons path="status" items="${fns:getEnumList('commonStatus')}" itemLabel="name" itemValue="value" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:input path="remark" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="merchant:merchantAutographInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>