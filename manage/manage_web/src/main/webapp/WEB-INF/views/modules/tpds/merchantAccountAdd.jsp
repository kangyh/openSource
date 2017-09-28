<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商户台账信息</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					var systemNo=$("#systemNo").val();
					if(systemNo==''){
						$("#systemNoId").text("接入系统编码不能为空");
						return false;
					}else{
						$("#systemNoId").text("");
					} 
					
					var loginName=$("#loginName").val();
					if(loginName==''){
						$("#loginNameId").text("用户名称不能为空");
						return false;
					}else{
						$("#loginNameId").text("");
					} 
					
					var merchantNo=$("#merchantNo").val();
					if(isNaN(merchantNo)){
						$("#merchantNoId").text("请输入数字类型");
						return false;
					}else{
						$("#merchantNoId").text("");
					} 
					loading('正在提交，请稍等...');
					form.submit();
				},
			});
			
		});
		
		
		function ChangeValue(){
			var merchantNo=$("#merchantNo").val();
			$.ajax({
	            type: "GET",
	            url: "${ctx}/tpds/merchantAccount/changeValue",
	            cache:false,
	            data:{
	            	"merchantNo":merchantNo
				},
	            success: function(data){
	                if(1!=data){
	                	$("#merchantNoId").text("商户编号已存在请重新输入");
	                	$("#merchantNo").val("");
	                }else{
	                	$("#merchantNoId").text("");
	                }
	            }
	        });
		}
		
		
		function ChangeValue1(){
			var systemNo=$("#systemNo").val();
			$.ajax({
	            type: "GET",
	            url: "${ctx}/tpds/merchantAccount/changeValue1",
	            cache:false,
	            data:{
	            	"systemNo":systemNo
				},
	            success: function(data){
	                if(1!=data){
	                	$("#systemNoId").text("系统接入号已存在请重新输入");
	                	$("#systemNo").val("");
	                }else{
	                	$("#systemNoId").text("");
	                }
	            }
	        });
		}
		
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="tpdsMerchantAccount" action="${ctx}/tpds/merchantAccount/save?tpdsMerchantId=${tpdsMerchantAccount.tpdsMerchantId}" method="post" class="form-horizontal">
		<sys:message content="${message}"/>	
	
		 <div class="control-group">
			<label class="control-label">商户编号：</label>
			<div class="controls">
				  <form:input path="merchantNo" id="merchantNo" value="${tpdsMerchantAccount.merchantNo}" htmlEscape="false"  maxlength="15" placeholder="请输入商户编号,不超过15个数"  class="required" onchange="ChangeValue();"/>
				<span id="merchantNoId" style="color:red;" class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> 
		 <div class="control-group">
			<label class="control-label">商户名称：</label>
			<div class="controls">
				  <form:input path="loginName" id="loginName" value="${tpdsMerchantAccount.loginName}" htmlEscape="false"  maxlength="32" type="text" placeholder="请输入商户名称,不超过32个数" class="required"/>
				<span id="loginNameId" class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> 
		 <div class="control-group">
			<label class="control-label">接入系统编码：</label>
			<div class="controls">
				  <form:input path="systemNo" id="systemNo" value="${tpdsMerchantAccount.systemNo}" htmlEscape="false"  placeholder="请输入商户编号,不超过4个数" maxlength="4"  class="required" onchange="ChangeValue1();"/>
				<span id="systemNoId" style="color:red;" class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--  <div class="control-group">
			<label class="control-label">台账账户：</label>
			<div class="controls">
				  <form:input path="accNo" value="${tpdsMerchantAccount.accNo}" htmlEscape="false"  maxlength="32" type="text" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		 <div class="control-group">
			<label class="control-label">银行卡号：</label>
			<div class="controls">
				   <form:input path="bankCard" value="${tpdsMerchantAccount.bankCard}" htmlEscape="false"  maxlength="32" type="text" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		 <div class="control-group">
			<label class="control-label">银行卡所在银行联行号：</label>
			<div class="controls">
				  <form:input path="bankCardBranch" value="${tpdsMerchantAccount.bankCardBranch}" htmlEscape="false"  maxlength="32" type="text" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		 <div class="control-group">
			<label class="control-label">存管户：</label>
			<div class="controls">
				  <form:input path="bankAccount" value="${tpdsMerchantAccount.bankAccount}" htmlEscape="false"  maxlength="32" type="text" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		 <div class="control-group">
			<label class="control-label">存管户所在银行联行号：</label>
			<div class="controls">
				  <form:input path="bankAccountBranch" value="${tpdsMerchantAccount.bankAccountBranch}" htmlEscape="false"  maxlength="32" type="text" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select id="status" path="status" class="input-xlarge required" >
				<c:forEach items="${status}" var="statuss">
					<form:option value="${statuss.value}" label="${statuss.name}"/>
				</c:forEach>
			</form:select>
			</div>
		</div>
		
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保存并提交"/>
			<input id="btnCancel" class="btn" type="button" value="取 消" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>