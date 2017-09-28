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
				var bankNo=$("#bankNo").val();
				if(bankNo == ''){
					$("#bankNoId").text("请选择银行");
					return false;
				}else{
					$("#bankNoId").text("");
				} 
			
				
				
				loading('正在提交，请稍等...');
				form.submit();
			},
		});
	});
	
	function Sel2change(field){
		
		var bankNo= $("#bankNo").val();
		$.ajax({
            type: "GET",
            url: "${ctx}/tpds/tpdsBankCer/changeValue",
            cache:false,
            data:{
            	"bankNo":bankNo
			},
            success: function(data){
                if(0!=data){
                	$("#bankNoId").text("商户编号已存在请重新输入");
                	$("#bankNo").val();
                	$("#btnSubmit").attr("disabled","disabled");
                }else{
                	$("#bankNoId").text("");
                	$("#btnSubmit").removeAttr("disabled");
                }
            }
        });
	}
</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li class="active"><a>${empty tpdsBankCer.tpdsBankId?'添加银行证书密钥添加':'添加银行证书密钥修改'}</a></li>
</ul><br/>
<form:form id="inputFormFrom" modelAttribute="tpdsBankCer" action="${ctx}/tpds/tpdsBankCer/saveEntity" method="post" enctype="multipart/form-data" class="form-horizontal">
	<sys:message content="${message}"/>
	
		<div class="control-group">
	         <label class="control-label">银行编号：</label>
	         <div class="controls">
				<c:if test="${empty tpdsBankCer.tpdsBankId}">
		             <%-- <form:input path="bankNo" id="bankNo" value="${tpdsBankCer.bankNo}" htmlEscape="false" maxlength="3"  class="required" onchange="ChangeValue()" placeholder="请输入商户编号信息,不超过3个数" style="width:300px;"/> --%>
		             
		             <form:select id="bankNo" path="bankNo" name="bankNo" class="input-xlarge" style="width:315px;" onchange="Sel2change(this.options[this.options.selectedIndex].text);">
						<form:option value="" label="-请选择银行-"/>
							<form:options items="${fns:getBankInfoList()}" itemLabel="bankName" itemValue="bankNo" htmlEscape="false" />
					</form:select>
					 <input type="hidden" name="bankName" id="bankName" class="input-xlarge required">
		             <span class="help-inline" id="bankNoId" style="color: red"><font color="red">*</font> </span>
		       </c:if>
			   <c:if test="${not empty tpdsBankCer.tpdsBankId}">
					<input type="text" id="bankNo" path="bankNo" name="bankNo" value="${tpdsBankCer.bankNo}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
			   		<span class="help-inline" id="bankNoId" style="color: red"><font color="red">*</font> </span>
			   </c:if>
	         </div>
	     </div>
	
	<div class="control-group">
		<label class="control-label">公钥：</label>
		<div class="controls">
		<c:if test="${empty tpdsBankCer.tpdsBankId}">
			<form:input path="pubKey" id="pubKey"  value="${tpdsBankCer.pubKey}" htmlEscape="false" maxlength="100" placeholder="请输入公钥信息,不超过100个数" style="width:300px;" class="add_number required"/>
		</c:if>
		<c:if test="${not empty tpdsBankCer.tpdsBankId}">
			<form:input path="pubKey" id="pubKey"  type="password" value="${tpdsBankCer.pubKey}" htmlEscape="false" maxlength="100" placeholder="请输入公钥信息,不超过100个数" style="width:300px;" class="add_number required"/>
		</c:if>
			<span class="help-inline" id="pubKeyId" style="color: red"><font color="red">*</font> </span>
		</div>
	</div>
	
	
	<div class="control-group">
		<label class="control-label">私钥：</label>
		<div class="controls">
		<c:if test="${empty tpdsBankCer.tpdsBankId}">
			<form:input path="priKey" id="priKey"  value="${tpdsBankCer.priKey}" htmlEscape="false" maxlength="100" placeholder="请输入私钥信息,不超过100个数" style="width:300px;" class="add_number required"/>
		</c:if>
		<c:if test="${not empty tpdsBankCer.tpdsBankId}">
			<form:input path="priKey" id="priKey"  type="password" value="${tpdsBankCer.priKey}" htmlEscape="false" maxlength="100" placeholder="请输入私钥信息,不超过100个数" style="width:300px;" class="add_number required"/>
		</c:if>
			<span class="help-inline" id="priKeyId" style="color: red"><font color="red">*</font> </span>
		</div>
	</div>
	
	
	<div class="control-group">
		<label class="control-label">备注：</label>
		<div class="controls">
			<form:textarea path="note" id="note"  value="${tpdsBankCer.note}" htmlEscape="false" maxlength="120" placeholder="请输入备注信息,不超过120个数" style="width:300px;" class="add_number required"/>
			<span class="help-inline" id="noteId" style="color: red"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
			<label class="control-label">证书文件：</label>
			<div class="controls">
                <c:if test="${empty tpdsBankCer.tpdsBankId}">
                    <input type="file" id="file" name="permitsAccountsFile" htmlEscape="false" maxlength="256" class="add_number required" />
                </c:if>
                <c:if test="${not empty tpdsBankCer.tpdsBankId}">
                   <a href="${tpdsBankCer.cerPath}">点击查看</a>
                </c:if>
                <span class="help-inline" id="fileId"><font color="red">*</font> </span>
			</div>
		</div>
		
	<input id="tpdsBankId" value="${tpdsBankCer.tpdsBankId}" type="hidden" name="tpdsBankId"/>
	<div class="form-actions">
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="提交申请"/>
		<input id="btnCancel" class="btn" type="button" value="返回" onclick="history.go(-1)" style="margin-left:50px"/>
	</div>
</form:form>
</body>
</html>