<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>保存商户出入金限额成功管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/validateNum.js"></script>
	<script type="text/javascript">

		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
                    var flg = false;
                    var merchantId = $("#merchantId").val();
                    if(validateNum(merchantId)){
                        $("#merchantId_info").text("");
                        flg = true;
                    }else{
                        $("#merchantId_info").text("商户编码请输入数字!").show();
                        flg = false;
                        return;
                    }
				    var val = $("#dayIncomeQuotaAmount").val();
                    if(!val>0){
                        $("#dayIncomeQuotaAmount_info").text("输入的金额错误，请重新输入！").show();
                        flg = false;
                        return;
                    }
                    if(validatePreventInject(val,"输入内容不合法，请重新输入！")){
                        $("#dayIncomeQuotaAmount_info").text("");
                        flg = true;
                    }else{
                        $("#dayIncomeQuotaAmount_info").text("输入内容不合法，请重新输入！");
                        flg = false;
                        return;
                    }
                    var reg = /^(([1-9][0-9]*)|(([0]\.\d{1,4}|[1-9][0-9]*\.\d{1,4}))|([0]{1}))$/;
                    if(!reg.test(val)){
                        $("#dayIncomeQuotaAmount_info").text("输入的金额错误，请重新输入！").show();
                        flg = false;
                        return;
                    }else{
                        $("#dayIncomeQuotaAmount_info").text("");
                        flg = true;
                    }
				    if(flg){
                        loading('正在提交，请稍等...');
                        form.submit();
                    }else{
                        $("#dayIncomeQuotaAmount_info").text("输入有误，请先更正。").show();
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
		});

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/riskManage/riskIncomeQuota/">商户出入金限额列表</a></li>
		<li class="active"><a href="${ctx}/riskManage/riskIncomeQuota/form?quotaId=${riskIncomeQuota.quotaId}"><shiro:hasPermission name="riskManage:riskIncomeQuota:edit">${not empty riskIncomeQuota.quotaId?'修改':'添加'}</shiro:hasPermission>商户出入金限额<shiro:lacksPermission name="riskManage:riskIncomeQuota:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="riskIncomeQuota" action="${ctx}/riskManage/riskIncomeQuota/save" method="post" class="form-horizontal">
		<form:hidden path="quotaId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">商户编码：</label>
			<div class="controls">
				<form:input path="merchantId" htmlEscape="false" id="merchantId" maxlength="10" class="required input-xlarge "/>
				<span class="help-inline"><font color="red">*</font> </span>
				<label id="merchantId_info" class="error"></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">日出入金限额：</label>
			<div class="controls">
				<form:input path="dayIncomeQuotaAmount" id="dayIncomeQuotaAmount" maxlength="22" htmlEscape="false" class="required input-xlarge "/>
				<span class="help-inline"><font color="red">*</font> </span>
				<label id="dayIncomeQuotaAmount_info" class="error"></label>
				<span class="info">填0时，不限额</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出入金类型：</label>
			<div class="controls">
				<form:select id="incomeDirection" path="incomeDirection" class="required input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${ioList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
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
			<shiro:hasPermission name="riskManage:riskIncomeQuota:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>