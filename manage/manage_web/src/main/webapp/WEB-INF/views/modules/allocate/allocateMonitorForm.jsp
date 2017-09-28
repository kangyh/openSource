<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>监听管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				
				submitHandler: function(form){
					//校验邮箱和手机号格式是否正确
					if(checkMonitorAccount()&& checkEmail() && checkStatus()){
						loading('正在提交，请稍等...');
						form.submit();
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
		
		
		//校验邮箱格式是否正确
		function checkEmail(){
			var emailReg=/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/; 
			var emails = $("#email").val().split(",");
			for(var i=0;i<emails.length;i++){
				if(!emailReg.test(emails[i])){
					alert("您输入的邮箱["+emails[i]+"]不符合格式，请检查！");
					return false;
				}
			}
			return true;
		}
		
		
		//校验手机号码是否正确
		function checkPhoneNum(){
			var phoneReg=/^1(3|4|5|7|8)\d{9}$/;
			var phoneNums = $("#phoneNum").val().split(",");
			if(phoneNums != null){
				for(var i=0;i<phoneNums.length;i++){
					if(!phoneReg.test(phoneNums[i])){
						alert("您输入的手机号码["+phoneNums[i]+"]不符合格式，请检查！");
						return false;
					}
				}
			}
			return true;
		}
		
		function checkMonitorAccount(){
			var monitorAccounts = $("#monitorAccount").val().split(",");
			if(monitorAccounts == null){
				alert("请输入备付金账户");
				return false;
			}
			return true;
		}
		
		
		//校验是否发送短信邮件提醒
		function checkStatus(){
			var emailStatus = $("input[name='emailStatus']:checked").val();
			if(emailStatus == undefined){
				alert("您选择是否需要发送邮件提醒!");
				return false;
			}
			return true;
		}
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/allocate/allocateMonitor/">监听阈值列表</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="allocateMonitor" action="${ctx}/allocate/allocateMonitor/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">阈值：</label>
			<div class="controls">
				<form:input path="thresholdValue" htmlEscape="false" class="input-xlarge required number" onkeyup="parent.amountCheck(this);"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备付金账户：</label>
			<div class="controls">
				<form:textarea id="monitorAccount" path="monitorAccount" htmlEscape="false" rows="4" maxlength="2000" class="input-xxlarge required"/>
				<span class="help-inline">
					<font color="red">*</font>
					<label>多个备付金账户以逗号分开</label>
				</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱：</label>
			<div class="controls">
				<form:textarea id="email" path="email" htmlEscape="false" rows="4" maxlength="2000" class="input-xxlarge required"/>
				<span class="help-inline">
					<font color="red">*</font>
					<label>多个邮箱以逗号分开</label>
				</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机：</label>
			<div class="controls">
				<form:textarea id="phoneNum" path="phoneNum" htmlEscape="false" rows="4" maxlength="2000" class="input-xxlarge"/>
				<span class="help-inline">
					<label>多个手机号以逗号分开</label>
				</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否发送邮件：</label>
			<div class="controls">
				<form:radiobuttons path="emailStatus" items="${fns:getEnumList('yesOrNo')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否发送短信：</label>
			<div class="controls">
				<form:radiobuttons path="smsStatus" items="${fns:getEnumList('yesOrNo')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="allocate:allocateMonitor:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>