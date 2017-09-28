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
				 <form:input path="merchantNo" id="merchantNo" value="${tpdsMerchantAccount.merchantNo}"   readOnly="true"  style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div> 
		 <div class="control-group">
			<label class="control-label">商户名称：</label>
			<div class="controls">
				  <form:input path="loginName" value="${tpdsMerchantAccount.loginName}"  readOnly="true"  style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div> 
		 <div class="control-group">
			<label class="control-label">接入系统编码：</label>
			<div class="controls">
				  <form:input path="systemNo" id="systemNo" value="${tpdsMerchantAccount.systemNo}" readOnly="true"  style="border:0px;background-color:#fff;padding-top: 3px;"/>
<!-- 				<span id="systemNoId" style="color:red;" class="help-inline"><font color="red">*</font> </span>
 -->			</div>
		</div>
		 <div class="control-group">
			<label class="control-label">台账账户：</label>
			<div class="controls">
				 <form:input path="accNo" value="${tpdsMerchantAccount.accNo}"   readOnly="true"  style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div>
		 <div class="control-group">
			<label class="control-label">银行卡号：</label>
			<div class="controls">
				  <form:input path="bankCard" value="${tpdsMerchantAccount.bankCard}"   readOnly="true"  style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div>
		 <div class="control-group">
			<label class="control-label">银行卡所在银行联行号：</label>
			<div class="controls">
				 <form:input path="bankCardBranch" value="${tpdsMerchantAccount.bankCardBranch}"   readOnly="true"  style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div>
		 <div class="control-group">
			<label class="control-label">存管户：</label>
			<div class="controls">
				 <form:input path="bankAccount" value="${tpdsMerchantAccount.bankAccount}"   readOnly="true"  style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div>
		 <div class="control-group">
			<label class="control-label">存管户所在银行联行号：</label>
			<div class="controls">
				 <form:input path="bankAccountBranch" value="${tpdsMerchantAccount.bankAccountBranch}"  readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div>
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
		<input id="tpdsMerchantId" value="${tpdsMerchantAccount.tpdsMerchantId}" type="hidden" name="tpdsMerchantId"/>
		
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保存并提交"/>
			<input id="btnCancel" class="btn" type="button" value="取 消" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>