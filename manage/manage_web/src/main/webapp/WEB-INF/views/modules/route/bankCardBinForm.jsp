<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>银行卡bin管理管理</title>
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
		function CalculateLength(){
			var length = $("#bankcardNote").val();
			if(length!=""){
				$("#bankcardNoteLength").val(length.length);
			}else{
				$("#bankcardNoteLength").val("");
			}
		}

		function pass () {
			var bank= $("#bankcardCode").val();
			var reg = /^\d{3}$/;
			if(!reg.test(bank)){
				$(".error_bank").html('<font color="red">输入有误,请输入3位数字</font>');
				return false;
			}else{
				$(".error_bank").html('<font color="red">输入正确</font>');
			}
		}

		function Sel1change(a){
			$("#bankcardName").val(a);
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/route/bankCardBin?cache=1">银行卡bin列表</a></li>
		<li class="active"><a href="${ctx}/route/bankCardBin/form?id=${bankCardBin.id}">银行卡bin<shiro:hasPermission name="route:bankCardBin:edit">${not empty bankCardBin.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="route:bankCardBin:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="bankCardBin" action="${ctx}/route/bankCardBin/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">发卡行名称：</label>
			<div class="controls">
				<form:select id="bankcardCode" path="bankcardCode" name="bankcardCode"  class="input-xlarge required"  onchange="Sel1change(this.options[this.options.selectedIndex].text);">
					<option selected value="">请选择</option>
					<form:options items="${fns:getBankInfoList()}" itemLabel="bankName" itemValue="bankNo" htmlEscape="false" />
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" style="display: none">
			<label class="control-label">发卡行代码对应名称：</label>
			<div class="controls">
				<form:input id="bankcardName" path="bankcardName"  htmlEscape="false"  class="input-xlarge "  readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发卡行标识：</label>
			<div class="controls">
				<form:input id="bankcardNote" path="bankcardNote" onkeyup="this.value=this.value.replace(/[^\d]/g,'')"  onpaste="return false" htmlEscape="false" maxlength="9" class="input-xlarge required" oninput="CalculateLength()"/>
			    <span class="help-inline"><font color="red">*</font> </span>
				<span class="help-inline"><font color="red">请输入数字</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发卡行标识长度：</label>
			<div class="controls">
				<form:input id="bankcardNoteLength" path="bankcardNoteLength"  htmlEscape="false" maxlength="1" class="input-xlarge "  readonly="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">卡号长度：</label>
			<div class="controls">
				<form:input path="bankcardLength" htmlEscape="false"  onkeyup="this.value=this.value.replace(/[^\d]/g,'')"  onpaste="return false" maxlength="2" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<span class="help-inline"><font color="red">请输入数字</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">卡名称：</label>
			<div class="controls">
				<form:input path="cardName" htmlEscape="false" maxlength="40" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">卡类型：</label>
			<div class="controls">
				<form:radiobuttons path="cardType" items="${fns:getEnumList('rateBankcardType')}" itemLabel="name" itemValue="value" htmlEscape="false"  class="input-xlarge required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="route:bankCardBin:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"  onclick="return pass()"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>