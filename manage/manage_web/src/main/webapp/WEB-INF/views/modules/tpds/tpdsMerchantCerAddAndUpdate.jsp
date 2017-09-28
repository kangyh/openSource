<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>商户台账信息</title>
<meta name="decorator" content="default"/>
<script type="text/javascript">
	
	$(document).ready(function() {
		$(".add_number").on("blur",function(){
			var this_value = $(this).val();
			var this_id=this.id;
			
			/* var pattern = new RegExp("[`~!#$^@&()=|{}':;',\\[\\]<>/?~！#￥……&（）——|{}【】‘；：”“'。，、？]") 
			if(pattern.test(this_value)){
				$("#"+this_id+"Id").text("输入不合法，请重新输入");
			}else{
				if(this_value.length<3){
					$("#"+this_id+"Id").text("输入类容太短，请重新输入");
				}else{
					$("#"+this_id+"Id").text("");
				}
			} */
		});
		
		$("#inputFormFrom").validate({
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
	
	function ChangeValue(){
		var merchantNo=$("#merchantNo").val();
		$.ajax({
            type: "GET",
            url: "${ctx}/tpds/tpdsMerchantCer/changeValue/"+merchantNo,
            type:"html",
            success: function(data){
                if(0!=data){
                	$("#merchantNoId").text("商户编号已存在请重新输入");
                	$("#merchantNo").val("");
                }else{
                	$("#merchantNoId").text("");
                }
            }
        });
	}
</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li class="active"><a>${empty tpdsMerchantCer.tpdsMerchantCerId?'商户证书密钥管理添加':'商户证书密钥管理修改'}</a></li>
</ul><br/>
<form:form id="inputFormFrom" modelAttribute="tpdsMerchantCer" action="${ctx}/tpds/tpdsMerchantCer/saveEntity" method="post" enctype="multipart/form-data" class="form-horizontal">
	<sys:message content="${message}"/>
	
		<div class="control-group">
	         <label class="control-label">商户编号：</label>
	         <div class="controls">
				<c:if test="${empty tpdsMerchantCer.tpdsMerchantCerId}">
		             <form:input path="merchantNo" id="merchantNo" value="${tpdsMerchantCer.merchantNo}" htmlEscape="false" maxlength="15"  class="required" onchange="ChangeValue()" placeholder="请输入商户编号信息,不超过15个数" style="width:300px;"/>
		             <span class="help-inline" id="merchantNoId" style="color: red"><font color="red">*</font> </span>
		       </c:if>
			   <c:if test="${not empty tpdsMerchantCer.tpdsMerchantCerId}">
					<input type="text" name="merchantNo" path="merchantNo" id="merchantNo" value="${tpdsMerchantCer.merchantNo}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
			   </c:if>
	         </div>
	     </div>
	
	<div class="control-group">
		<label class="control-label">公钥：</label>
		<div class="controls">
		<c:if test="${empty tpdsMerchantCer.tpdsMerchantCerId}">
			<form:input path="pubKey" id="pubKey"  value="${tpdsMerchantCer.pubKey}" htmlEscape="false" maxlength="100" placeholder="请输入公钥信息,不超过100个数" style="width:300px;" class="add_number required"/>
		</c:if>
		<c:if test="${not empty tpdsMerchantCer.tpdsMerchantCerId}">
			<form:input path="pubKey" id="pubKey"  type="password" value="${tpdsMerchantCer.pubKey}" htmlEscape="false" maxlength="100" placeholder="请输入公钥信息,不超过100个数" style="width:300px;" class="add_number required"/>
		</c:if>
			<span class="help-inline" id="pubKeyId" style="color: red"><font color="red">*</font> </span>
		</div>
	</div>
	
	
	<div class="control-group">
		<label class="control-label">私钥：</label>
		<div class="controls">
		<c:if test="${empty tpdsMerchantCer.tpdsMerchantCerId}">
			<form:input path="priKey" id="priKey"  value="${tpdsMerchantCer.priKey}" htmlEscape="false" maxlength="100" placeholder="请输入私钥信息,不超过100个数" style="width:300px;" class="add_number required"/>
		</c:if>
		<c:if test="${not empty tpdsMerchantCer.tpdsMerchantCerId}">
			<form:input path="priKey" id="priKey"  type="password" value="${tpdsMerchantCer.priKey}" htmlEscape="false" maxlength="100" placeholder="请输入私钥信息,不超过100个数" style="width:300px;" class="add_number required"/>
		</c:if>
			<span class="help-inline" id="priKeyId" style="color: red"><font color="red">*</font> </span>
		</div>
	</div>
	
	
	<div class="control-group">
		<label class="control-label">备注：</label>
		<div class="controls">
			<form:textarea path="note" id="note"  value="${tpdsMerchantCer.note}" htmlEscape="false" maxlength="120" placeholder="请输入备注信息,不超过120个数" style="width:300px;" class="add_number required"/>
			<span class="help-inline" id="noteId" style="color: red"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
			<label class="control-label">证书文件：</label>
			<div class="controls">
                <c:if test="${empty tpdsMerchantCer.tpdsMerchantCerId}">
                    <input type="file" id="file" name="permitsAccountsFile" htmlEscape="false" maxlength="256" class="add_number required" />
                </c:if>
                <c:if test="${not empty tpdsMerchantCer.tpdsMerchantCerId}">
                   <a href="${tpdsMerchantCer.cerPath}">点击查看</a>
                </c:if>
                <span class="help-inline" id="fileId"><font color="red">*</font> </span>
			</div>
		</div>
		
	<input id="tpdsMerchantCerId" value="${tpdsMerchantCer.tpdsMerchantCerId}" type="hidden" name="tpdsMerchantCerId"/>
	<div class="form-actions">
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="提交申请"/>
		<input id="btnCancel" class="btn" type="button" value="返回" onclick="history.go(-1)" style="margin-left:50px"/>
	</div>
</form:form>
</body>
</html>