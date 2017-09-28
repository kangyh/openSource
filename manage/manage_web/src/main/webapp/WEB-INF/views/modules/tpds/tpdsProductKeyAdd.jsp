<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商户产品密钥配置</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					var packDir=$("#packDir").val();
					if(packDir==''){
						$("#packDirId").text("服务路径不能为空");
						return false;
					}else{
						$("#packDirId").text("");
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
		
		//onchange="ChangeValue();"
		function ChangeValue(){
			var merchantNo=$("#merchantNo").val();
			$.ajax({
	            type: "GET",
	            url: "${ctx}/tpds/tpdsProductKey/changeValue",
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
	
		
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="tpdsProductKey" action="${ctx}/tpds/tpdsProductKey/save?keyId=${tpdsProductKey.keyId}" method="post" class="form-horizontal">
		<sys:message content="${message}"/>	
		
		 <div class="control-group">
			<label class="control-label">商户编号：</label>
			<div class="controls">
				  <form:input path="merchantNo" id="merchantNo" value="${tpdsProductKey.merchantNo}" htmlEscape="false"  maxlength="11" placeholder="请输入商户编号,不超过11个数"  class="required"/>
				<span id="merchantNoId" style="color:red;" class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> 
		<div class="control-group">
			<label class="control-label">交易类型：</label>
			<div class="controls">
				<form:select id="transType" path="transType" class="input-xlarge required" >
				<c:forEach items="${transType}" var="transTypes">
					<form:option value="${transTypes.value}" label="${transTypes.name}"/>
				</c:forEach>
			</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品名称：</label>
			<div class="controls">
				<form:select id="productName" path="productName" class="input-xlarge required" >
				<c:forEach items="${productName}" var="productNames">
					<form:option value="${productNames.value}" label="${productNames.name}"/>
				</c:forEach>
			</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">密钥：</label>
			<div class="controls">
				  <form:input path="signKey" id="signKey" value="${tpdsBindInterface.signKey}" htmlEscape="false"  maxlength="128" placeholder="请输入密钥" type="text" class="required"/>
				<span class="help-inline" id="signKeyId"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保存并提交"/>
			<input id="btnCancel" class="btn" type="button" value="取 消" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>