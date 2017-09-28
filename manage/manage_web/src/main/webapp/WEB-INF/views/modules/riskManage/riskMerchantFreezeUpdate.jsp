<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>风控管理</title>
<meta name="decorator" content="default"/>
<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
<style>
       #main {
           margin: 50px;
       }
   </style>
<script type="text/javascript">

	$(document).ready(function() {
		$("#inputForm").validate({
			submitHandler: function(form){
				
				var transAmountId=$('#transAmountId').val();
				
				if(isNaN(transAmountId)){
					$("#type_val_info").text("金额请输入数字");
					$("#type_val_info").removeAttr("style");
					return false;
				}else{
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
	function Sel2change(field){
		
		$("#channelName").val(field);
	}
</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li><a href="#">账户冻结修改页面</a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="riskMerchantFreeze" action="${ctx}/riskManage/RiskMerchantFreezeQuery/updateEntity" method="post" class="form-horizontal">

	<div class="control-group">
		<label class="control-label">商户编码：</label>
		<div class="controls">
			<input path="merchantCode" name="merchantCode" id="merchantCode"   value="${riskMerchantFreeze.merchantCode}" readOnly="true"style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	
	<div class="control-group">
		<label class="control-label">商户账号：</label>
		<div class="controls">
			<input value="${riskMerchantFreeze.merchantName}" readOnly="true"style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	
	<div class="control-group">
		<label class="control-label">冻结单号：</label>
		<div class="controls">
			<input value="${riskMerchantFreeze.freezeNo}" readOnly="true"style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">创建时间：</label>
		<div class="controls">
			<fmt:formatDate value="${riskMerchantFreeze.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">冻结金额：</label>
		<div class="controls">
			<form:input path="transAmount" id="transAmountId"  value="${riskMerchantFreeze.transAmount}" htmlEscape="false" maxlength="20" class="required" placeholder="金额请输入数字"/>
			<span class="help-inline"><font color="red">*</font> </span>
			<label id="type_val_info" for="typeId" class="error"></label>
		</div>
		
	</div>
	
	<div class="control-group">
		<label class="control-label">操作状态：</label>
		<div class="controls">
			<input  value="${riskMerchantFreeze.status}" readOnly="true"style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	
	<div class="control-group">
		<label class="control-label">冻结理由：</label>
		<div class="controls">
		    <form:select id="freezeRemark" path="freezeRemark" name="freezeRemark" class="input-xlarge required">
			    <c:forEach items="${riskFreezeRemark}" var="riskFreezeRemarkControl">
					<form:option value="${riskFreezeRemarkControl.value}" label="${riskFreezeRemarkControl.name}"/>
				</c:forEach>
		    </form:select>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">操作人：</label>
		<div class="controls">
			<input value="${riskMerchantFreeze.createAuthor}" readOnly="true"style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	
	<div class="control-group">
		<label class="control-label">解冻理由：</label>
		<div class="controls">
		    <form:select id="freezeRemark" path="freezeRemark" name="freezeRemark" class="input-xlarge required">
			    <c:forEach items="${riskFreezeRemark}" var="riskFreezeRemarkControl">
					<form:option value="${riskFreezeRemarkControl.value}" label="${riskFreezeRemarkControl.name}"/>
				</c:forEach>
		    </form:select>
		</div>
	</div>
	
	<input id="refererid" value="${referer}" type="hidden" name="referer"/>
	<input value="${pageNo}" type="hidden" name="pageNo"/>
	<input path="freezeId" name="freezeId" value="${riskMerchantFreeze.freezeId}" type="hidden" />
		
	<div class="form-actions">
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="保存"/>
		<input id="btnCancel" class="btn" type="button" value="取 消" onclick="history.go(-1)"/>
	</div>
</form:form>
</body>
</html>