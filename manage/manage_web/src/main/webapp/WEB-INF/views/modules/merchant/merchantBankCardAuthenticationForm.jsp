<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商户打款认证管理</title>
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
			if(parseFloat($("#payAmount").val()) != parseFloat($("#authenticationAmount").val())){
				parent.showThree();
				parent.changeThreeName("认证金额必须与打款金额相同");
				return false;
			}else{
                if(!confirm("请确认提交此操作?")){
                    return false;
                }
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/merchant/merchantBankCardAuthentication?cache=1">商户打款认证列表</a></li>
		<li class="active"><a href="${ctx}/merchant/merchantBankCardAuthentication/form?id=${merchantBankCardAuthentication.id}">商户打款认证<shiro:hasPermission name="merchant:merchantBankCardAuthentication:edit">${not empty merchantBankCardAuthentication.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="merchant:merchantBankCardAuthentication:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="merchantBankCardAuthentication" action="${ctx}/merchant/merchantBankCardAuthentication/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">商户编码：</label>
			<div class="controls">
				<input name="merchantId" value="${merchantBankCardAuthentication.merchantId}" readonly style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户公司名称：</label>
			<div class="controls">
				<input name="merchantCompanyName" value="${merchantBankCardAuthentication.merchantCompanyName}" readonly style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">法人代表：</label>
			<div class="controls">
				<input name="legalRepresentative" value="${merchantBankCardAuthentication.legalRepresentative}" readonly style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行卡号：</label>
			<div class="controls">
				<form:input path="bankNo" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开户银行名称：</label>
			<div class="controls">
				<span>${merchantBankCardAuthentication.openBankName}</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">打款金额：</label>
			<div class="controls">
				<input id="payAmount" name="payAmount" value="${merchantBankCardAuthentication.payAmount}" readonly style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">打款状态：</label>
			<div class="controls">
				<input value="${merchantBankCardAuthentication.payStatus}" readonly style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">认证金额：</label>
			<div class="controls">
				<form:input path="authenticationAmount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">认证状态：</label>
			<div class="controls">
				<form:select path="authenticationStatus" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('AuthenticationStatus')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">打款时间：</label>
			<div class="controls">
				<input name="createDate" readonly="readonly"  style="border:0px;background-color:#fff;padding-top: 3px;"
					value="<fmt:formatDate value="${merchantBankCardAuthentication.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">修改理由：</label>
			<div class="controls">
				<form:input path="reason" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">失败原因:</label>
			<div class="controls">
				<form:textarea path="cause" htmlEscape="false" rows="3" maxlength="200" readonly="true" class="input-xxlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="merchant:merchantBankCardAuthentication:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="return pass();"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>