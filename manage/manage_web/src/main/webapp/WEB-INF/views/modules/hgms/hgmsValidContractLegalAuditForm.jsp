<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>有效合约法务审核</title>
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
        function pass(){
            if($("#objection").val() != ""){
                parent.showThree();
                parent.changeThreeName("通过时拒绝理由应为空");
                return false;
            }
            if('${hgmsValidContract.legalAuditStatus}'=='待审核') {
                $("#legalAuditStatus").val('SUCCES');
                $("#rcAuditStatus").val('AUDING');
            }
        }
        function reject(){
            if($("#objection").val() == ""){
                parent.showThree();
                parent.changeThreeName("拒绝理由不能为空");
                return false;
            }
            if('${hgmsValidContract.legalAuditStatus}'=='待审核') {
                $("#legalAuditStatus").val('AUDREJ');
			}

        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/hgms/hgmsValidContractLegalAudit/">合约法务列表</a></li>
		<li class="active"><a href="${ctx}/hgms/hgmsValidContractLegalAudit/form?id=${hgmsValidContract.id}">合约法务审核</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="hgmsValidContract" action="${ctx}/hgms/hgmsValidContractLegalAudit/audit" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">合约编码：</label>
				<div class="controls">
					<form:input path="contractId" class="input-xlarge" readonly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">商户编号：</label>
				<div class="controls">
					<form:input path="merchantId" class="input-xlarge" readonly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">上级商户ID：</label>
				<div class="controls">
					<form:input path="superiorId" class="input-xlarge" readonly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">上级商户公司名称：</label>
				<div class="controls">
					<form:input path="superiorName" class="input-xlarge" readonly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">公司名称：</label>
				<div class="controls">
					<form:input path="companyName" class="input-xlarge" readonly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">合同名称：</label>
				<div class="controls">
					<form:input path="contractName" class="input-xlarge" readonly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">业务编码：</label>
				<div class="controls">
					<form:input path="businessId" class="input-xlarge" readonly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">业务名称：</label>
				<div class="controls">
					<form:input path="businessName" class="input-xlarge" readonly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">服务项编码：</label>
				<div class="controls">
					<form:input path="serviceId" class="input-xlarge" readonly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">服务项名称：</label>
				<div class="controls">
					<form:input path="serviceName" class="input-xlarge" readonly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">合同文件：</label>
			<div class="controls">
				<c:if test="${not empty hgmsValidContract.contractFileAddress}">
					<a class="media" href="${hgmsValidContract.contractFileAddress}" target="_blank">合同文件</a>
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">合约状态：</label>
			<div class="controls">
				<form:input path="contractStatus" class="input-xlarge" readonly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">有效期开始：</label>
				<div class="controls">
					<input name="enteringTime" type="text" readonly class="input-xlarge" value="<fmt:formatDate value="${hgmsValidContract.validityBeginTime}" pattern="yyyy-MM-dd"/>" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">有效期结束：</label>
				<div class="controls">
					<input name="enteringTime" type="text" readonly class="input-xlarge" value="<fmt:formatDate value="${hgmsValidContract.validityEndTime}" pattern="yyyy-MM-dd"/>" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">法务审核状态：</label>
			<div class="controls">
				<form:input path="legalAuditStatus" class="input-xlarge" readonly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div>
		<form:hidden path="rcAuditStatus" />
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">录入时间：</label>
				<div class="controls">
					<input name="enteringTime" type="text" class="input-xlarge" readonly value="<fmt:formatDate value="${hgmsValidContract.enteringTime}" pattern="yyyy-MM-dd"/>" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">录入人：</label>
				<div class="controls">
					<form:input path="enteringId" class="input-xlarge" readonly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">拒绝理由：</label>
			<div class="controls">
				<form:input id="objection" path="objection" htmlEscape="false" maxlength="50" class="input-xlarge"/>
			</div>
		</div>
		<div class="form-actions">
            <shiro:hasPermission name="hgms:hgmsValidContractLegalAudit:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="通 过" onclick="return pass();"/>&nbsp;</shiro:hasPermission>
            <shiro:hasPermission name="hgms:hgmsValidContractLegalAudit:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="拒 绝" onclick="return reject();"/>&nbsp;</shiro:hasPermission>
            <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>