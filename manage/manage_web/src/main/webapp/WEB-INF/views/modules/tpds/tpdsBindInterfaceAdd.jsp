<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>监管银行接口绑定配置</title>
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
		
		function ChangeValue(){
			var merchantNo=$("#merchantNo").val();
			$.ajax({
	            type: "GET",
	            url: "${ctx}/tpds/tpdsBindInterface/changeValue",
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
	<form:form id="inputForm" modelAttribute="tpdsBindInterface" action="${ctx}/tpds/tpdsBindInterface/save?tpdsBindId=${tpdsBindInterface.tpdsBindId}" method="post" class="form-horizontal">
		<sys:message content="${message}"/>	
		
		 <div class="control-group">
			<label class="control-label">商户编号：</label>
			<div class="controls">
				  <form:input path="merchantNo" id="merchantNo" value="${tpdsBindInterface.merchantNo}" htmlEscape="false"  maxlength="11" placeholder="请输入商户编号,不超过11个数"  class="required" onchange="ChangeValue();"/>
				<span id="merchantNoId" style="color:red;" class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> 
	<%-- 	<div class="control-group">
			<label class="control-label">银行代码：</label>
			<div class="controls">
				   <form:input path="bankNo" value="${tpdsBindInterface.bankNo}" htmlEscape="false"  maxlength="32" type="text" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行简称：</label>
			<div class="controls">
				   <form:input path="bankCode" value="${tpdsBindInterface.bankCode}" htmlEscape="false"  maxlength="32" type="text" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">银行名称：</label>
			<div class="controls">
				  <form:select id="bankName" path="bankName" class="input-xlarge required"
                         onchange="SelchangeBank(this.options[this.options.selectedIndex].text);" style="width:315px;">
                <form:options items="${fns:getBankInfoList()}" itemLabel="bankName" itemValue="bankNo"
                              htmlEscape="false"/>
            </form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务路径：</label>
			<div class="controls">
				  <form:input path="packDir" id="packDir" value="${tpdsBindInterface.packDir}" htmlEscape="false"  maxlength="128" placeholder="请输入服务路径（包路径）" type="text" class="required"/>
				<span class="help-inline" id="packDirId"><font color="red">*</font> </span>
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
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保存并提交"/>
			<input id="btnCancel" class="btn" type="button" value="取 消" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>