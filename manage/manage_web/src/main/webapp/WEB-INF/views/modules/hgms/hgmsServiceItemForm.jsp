<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>服务项管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });

            $("#code_product").on("change", "select", function () {
                $("#productCode").val($("#productName option:selected").attr("title"));
            });
        })
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/hgms/hgmsServiceItem/?businessId=${hgmsServiceItem.businessId}">服务项列表</a></li>
		<li class="active"><a href="${ctx}/hgms/hgmsServiceItem/form?businessId=${hgmsServiceItem.businessId}">服务项<shiro:hasPermission name="hgms:hgmsServiceItem:edit">${not empty hgmsServiceItem.id?'修改':'添加'}</shiro:hasPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="hgmsServiceItem" action="${ctx}/hgms/hgmsServiceItem/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">服务项名称：</label>
			<div class="controls">
				<form:input path="serviceName" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
			</div>
		</div>
        <form:hidden path="businessId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
		<div class="control-group">
			<label class="control-label">产品名称：</label>
			<div class="controls" id="code_product">
                <form:select path="productName" class="input-xlarge required">
                    <form:option value="" label="-产品-" title=""/>
                    <c:forEach items="${productList}" var="product">
                        <form:option value="${product.productName}" label="${product.productName}" title="${product.productCode}"/>
                    </c:forEach>
                </form:select>
			</div>
		</div>
		<form:hidden id="productCode" path="productCode"/>
		<div class="control-group">
			<label class="control-label">规则建立方式：</label>
			<div class="controls">
                <form:select id="ruleBuildType" path="ruleBuildType" class="input-xlarge required">
                    <form:option value="" label="-选择规则建立方式-"/>
                    <c:forEach items="${hgmsRuleBuildTypes}" var="hgmsRuleBuildTypes">
                        <form:option value="${hgmsRuleBuildTypes.value}" label="${hgmsRuleBuildTypes.name}"/>
                    </c:forEach>
                </form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="hgms:hgmsServiceItem:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>