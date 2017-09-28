<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>报表条件设置管理</title>
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
		});

		function checkNameValue(valId,nameId) {
            var name = $("#"+valId).find("option:selected").text();
            $("#"+nameId).val(name);
        }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/bossreport/reportQueryConditions/">报表条件配置列表</a></li>
		<li class="active"><a href="${ctx}/bossreport/reportQueryConditions/form?reportId=${reportQueryConditions.id}"><shiro:hasPermission name="bossreport:reportQueryConditions:edit">${not empty reportQueryConditions.reportId?'修改':'添加'}</shiro:hasPermission>报表条件配置<shiro:lacksPermission name="bossreport:reportQueryConditions:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="reportQueryConditions" action="${ctx}/bossreport/reportQueryConditions/save" method="post" class="form-horizontal">
		<form:hidden path="reportId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">支付类型：</label>
			<div class="controls">
				<form:select id="payType" path="payType" class="required input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${payTypeList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行支付提供者：</label>
			<div class="controls">
				<form:input path="bankProvider" htmlEscape="false" maxlength="50" class="required input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行支付提供者名称：</label>
			<div class="controls">
				<form:input path="bankProviderName" htmlEscape="false" maxlength="50" class="required input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行ID：</label>
			<div class="controls">
				<form:input path="bankId" htmlEscape="false" maxlength="10" class="required input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行名称：</label>
			<div class="controls">
				<form:input path="bankName" htmlEscape="false" maxlength="50" class="required input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行名称(复)：</label>
			<div class="controls">
				<form:input path="bankNameRe" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">java支付类型：</label>
			<div class="controls">
				<form:select id="payTypeJava" path="payTypeJava" onchange="checkNameValue('payTypeJava','payTypeNameJava')" class="required input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${payType}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</div>
			<input id="payTypeNameJava" name="payTypeNameJava" type="hidden"/>
		</div>
		<div class="control-group">
			<label class="control-label">java银行名称：</label>
			<div class="controls">
				<form:select id="bankIdJava" path="bankIdJava" onchange="checkNameValue('bankIdJava','bankNameJava')" class="required input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${bankList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</div>
			<input id="bankNameJava" name="bankNameJava" type="hidden"/>
		</div>
		<div class="control-group">
			<label class="control-label">java通道合作方：</label>
			<div class="controls">
				<form:select id="channelPartnerJava" path="channelPartnerJava" onchange="checkNameValue('channelPartnerJava','channelPartnerNameJava')" class="required input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${channelList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</div>
			<input id="channelPartnerNameJava" name="channelPartnerNameJava" type="hidden"/>
		</div>
		<div class="control-group">
			<label class="control-label">公司ID|结算主体：</label>
			<div class="controls">
				<form:input path="companyId" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司名称|结算主体：</label>
			<div class="controls">
				<form:input path="companyName" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select id="status" path="status" class="required input-xlarge">
					<form:option value="" label="请选择"/>
					<form:option value="Y" label="生效"/>
					<form:option value="N" label="无效"/>
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="bossreport:reportQueryConditions:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>