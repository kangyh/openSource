<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>调拨管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					if(checkAccount() && checkAmount()){
						if(confirmWindow()){
							loading('正在提交，请稍等...');
							form.submit();
						}
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
			
			//出账下拉框事件
			$("select[name='reserveOutAccountId']").on("change", function(){
				 $.ajax({
		               type: "POST",
		               url: "${ctx}/allocate/allocateRecord/getAmountByAccountId",
		               data: {"accountId":$(this).val()},
		               dataType: 'json',
		               success: function(data){
		            	   $("#reserveOutAccountIdLabel").text(data);
		               },
		               error: function(){
		                    console.log("请求失败!");
		               }
		        }); 
			});
			
			//入账下拉框事件
			$("select[name='reserveInAccountId']").on("change", function(){
				$.ajax({
		               type: "POST",
		               url: "${ctx}/allocate/allocateRecord/getAmountByAccountId",
		               data: {"accountId":$(this).val()},
		               dataType: 'json',
		               success: function(data){
		            	   $("#reserveInAccountIdLabel").text(data);
		               },
		               error: function(){
		                    console.log("请求失败!");
		               }
		        }); 
			});
			
		});
		
		
		//校验出账/入账的账户是否为同一个
		function checkAccount(){
			var reserveOutAccountVal = $("select[name='reserveOutAccountId']").val();
			var reserveInAccountVal = $("select[name='reserveInAccountId']").val();
			if(reserveOutAccountVal == reserveInAccountVal){
				alert("出账的备付金账户和入账的备付金账户是同一个，请检查！");
				return false;
			}
			return true;
			
		}
		
		//校验调拨金额必须大于0
		function checkAmount(){
			var amount = $("#amount").val();
			if(amount <= 0){
				alert("调拨金额必须大于0");
				return false;
			}
			return true;
		}
		
		//弹出确认提示框
		function confirmWindow(){
			var amount = $("#amount").val();
			if(confirm("您本次资金头寸调拨金额为："+amount+"元,您确认调拨吗？")){
		    	return true;
			}
			return false;
			 
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/allocate/allocateRecord/">调拨列表</a></li>
		<li class="active"><a href="${ctx}/allocate/allocateRecord/form?id=${allocateRecord.id}">调拨<shiro:hasPermission name="allocate:allocateRecord:edit">${not empty allocateRecord.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="allocate:allocateRecord:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="allocateRecord" action="${ctx}/allocate/allocateRecord/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">出账的备付金账户：</label>
			<div class="controls">
				<form:select path="reserveOutAccountId" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options name="accountId" items="${merchantAccountList}" itemLabel="accountName" itemValue="accountId" htmlEscape="false" onclick="changeAccount(this)"/>
				</form:select>
				<span class="help-inline">
					<font color="red">*</font> 
					<label id="reserveOutAccountIdLabel" style="color:#CD2626;font-size: 14px;"></label>
				</span>
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">入账的备付金账户：</label>
			<div class="controls">
				<form:select path="reserveInAccountId" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${merchantAccountList}" itemLabel="accountName" itemValue="accountId" htmlEscape="false"/>
				</form:select>
				<span class="help-inline">
					<font color="red">*</font>
					<label id="reserveInAccountIdLabel" style="color:#CD2626;font-size: 14px;"></label>
				</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">调拨金额：</label>
			<div class="controls">
				<form:input id="amount" path="amount" htmlEscape="false" class="input-xlarge required number" onkeyup="parent.amountCheck(this);"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">原因：</label>
			<div class="controls">
				<form:textarea path="reason" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="allocate:allocateRecord:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>