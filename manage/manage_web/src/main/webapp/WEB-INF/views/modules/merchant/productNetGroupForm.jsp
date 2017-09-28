<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>结算周期管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			if($("#msg").val() != ""){
				parent.showThree();
				parent.changeThreeName($("#msg").val());
			}
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
		});
		function productChange(option){
			var name = option.text;
			$("#productName").val(name);
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/merchant/productNetGroup/">产品组关联列表</a></li>
		<li class="active"><a href="">产品组关联<shiro:hasPermission name="merchant:productNetGroup:edit">${not empty productNetGroup.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="merchant:productNetGroup:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="productNetGroup" action="${ctx}/merchant/productNetGroup/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" id="msg" value = "${msg}" />
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">产品名称：</label>
			<div class="controls">
				<c:choose>
					<c:when test="${empty productNetGroup.id}">
						<form:select id="productCode" path="productCode" class="input-xlarge required" onchange="productChange(this.options[this.options.selectedIndex]);">
							<form:option value="" label=""/>
							<form:options items="${fns:getProductList()}" itemLabel="name" itemValue="code" htmlEscape="false"/>
						</form:select>
						<span class="help-inline"><font color="red">*</font> </span>
						<form:hidden path="productName" id="productName"/>
					</c:when>
					<c:otherwise>
						<input name="productName" value="${productNetGroup.productName}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
						<form:hidden path="productCode" id="productCode"/>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">.net组：</label>
			<div class="controls">
				<form:input path="netGroup" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="merchant:productNetGroup:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>