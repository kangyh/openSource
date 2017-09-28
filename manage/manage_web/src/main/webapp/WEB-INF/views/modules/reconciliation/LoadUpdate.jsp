<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>对账参数管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){

				   var fileId = document.getElementById("fileId");
				   if(fileId.value == "" || fileId.value == null){
				       alertx("请选择上传文件");
				       return false;
				   }
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

		function Sel2change(field){
			$("#channelName").val(field);
		}
		
		function checkValue(obj){
		var bankValue = obj.value;
		if(bankValue=='DIRCON'){
			$('#bank_id').css('display','block');
		}else{
			$('#bank_id').css('display','none');
		}
		$("#channelName").val(obj.options[obj.options.selectedIndex].text);
		
	}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/reconciliation/SettleCheckBath/uploadFile">上传文件</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="settleChannelLog" action="${ctx}/reconciliation/SettleCheckBath/upload" method="POST" enctype="multipart/form-data" class="form-horizontal">
		<sys:message content="${message}"/>	
		
		<div class="control-group">
			<label class="control-label">通道合作方：</label>
			<div class="controls">
			<form:select id="channelCode" path="channelCode"  class="input-xlarge required">
				<form:option value="" label="-通道合作方-"/>
				<c:forEach items="${checklist}" var="SettleRuleControl">
					<form:option value="${SettleRuleControl.channelCode}" label="${SettleRuleControl.channelName}"/>
				</c:forEach>
			</form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">支付通道类型：</label>
			<div class="controls">
			<!-- 隐藏域用来显示提示警告 -->
			<input id="channelTypeid" class="required" type="hidden"/>
				<form:select id="channelType" path="channelType" class="input-xlarge required">
					<form:option value="" label="-支付通道类型-"/>
					<c:forEach items="${channelTypeList}" var="typeList">
						<form:option value="${typeList.loadChannelType}" label="${typeList.channelType}"/>
					</c:forEach>
				</form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">选择上传文件：</label>
		<div class="controls">
		    	<p><input id = "fileId" type="file" name="file"/></p>
		</div> 
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保存并提交"/>
			<input id="btnCancel" class="btn" type="button" value="取 消" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>