<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//alert($("#id").val());			
			if($("#id").val() == ""){
			   $("#bankNo").attr("disabled",false);
			   $("#bankName").attr("disabled",false);
			}else{
				$("#bankNo").attr("disabled",true);
				$("#bankName").attr("disabled",true);
			}
			if($("#msg").val() != ""){
				parent.showThree();
				parent.changeThreeName($("#msg").val());
			}
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

		function pass () {
			var bank= $("#bankNo").val();
			var reg = /^\d{3}$/;
			if(!reg.test(bank)){
				$(".error_bank").html('<font color="red">输入有误,请输入3位数字</font>');
				return false;
			}else{
				$(".error_bank").html('<font color="red">输入正确</font>');
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/route/bankInfo/">银行列表</a></li>
		<li class="active"><a href="${ctx}/route/bankInfo/form?id=${bankInfo.id}">银行信息<shiro:hasPermission name="route:bankInfo:edit">${not empty bankInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="route:bankInfo:edit">添加</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="bankInfo" action="${ctx}/route/bankInfo/save?bankNoFind=${bankNoFind}&bankNameFind=${bankNameFind}&selectStatusFind=${selectStatusFind}&pageNo=${pageNo}" method="post" class="form-horizontal">
	 <form:hidden path="id"/>
		<sys:message content="${message}"/>
		<input type="hidden" id="msg" value = "${msg}" />
		<div class="control-group">
			<label class="control-label">银行代码：</label>
			<div class="controls">
				<form:input path="bankNo" name="bankNo" id="bankNo"  onkeyup="this.value=this.value.replace(/[^\d]/g,'')"  onpaste="return false" htmlEscape="false"  maxlength="3" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<span class="help-inline error_bank"><font color="red">请输入3位数字</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行名称：</label>
			<div class="controls">
			<form:input path="bankName" name="bankName"  onpaste="return false" htmlEscape="false" maxlength="30" class="input-xlarge required"/>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行简称：</label>
			<div class="controls">
				<form:input path="bankCode" name="bankCode"  onkeyup="this.value=this.value.replace(/[^A-Z]/g,'')"  onpaste="return false"   htmlEscape="false" maxlength="10" class="input-xlarge"/>
				<span class="help-inline"><font color="red">请输入大写字母</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="route:bankInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="return pass()"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="javascript:window.location='${ctx}/route/bankInfo/list?bankNo=${bankNoFind}&bankName=${bankNameFind}&status=${selectStatusFind}&pageNo=${pageNo}';"/>
		</div>
	</form:form>
</body>
</html>