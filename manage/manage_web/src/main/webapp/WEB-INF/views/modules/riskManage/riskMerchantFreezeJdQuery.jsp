a<%@ page contentType="text/html;charset=UTF-8" %>
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
</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li><a href="#">解冻申请</a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="riskMerchantFreeze" action="${ctx}/riskManage/RiskMerchantFreezeQueryJd/query/tojd/${riskMerchantFreeze.freezeId}" method="post" class="form-horizontal">

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
		<label class="control-label">冻结申请时间：</label>
		<div class="controls">
			<fmt:formatDate value="${riskMerchantFreeze.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">冻结金额：</label>
		<div class="controls">
			<fmt:formatNumber  value="${riskMerchantFreeze.transAmount}" pattern="￥0.0000" />
		</div>
	</div>
	
	<div class="control-group">
		<label class="control-label">冻结理由：</label>
		<div class="controls">
			<input value="${riskMerchantFreeze.freezeRemark}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;width:300px;"/>
		</div>
	</div>
	
	
	<div class="control-group">
		<label class="control-label">冻结申请备注1：</label>
		<div class="controls">
			<input path="remark1" id="remark1"  value="${riskMerchantFreeze.remark1}" readOnly="true" maxlength="20" style="border:0px;background-color:#fff;padding-top: 3px;width:300px;" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">冻结申请备注2：</label>
		<div class="controls">
			<input path="remark2" id="remark2"  value="${riskMerchantFreeze.remark2}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;width:300px;"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">冻结申请备注3：</label>
		<div class="controls">
			<input path="remark3" id="remark3"  value="${riskMerchantFreeze.remark3}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;width:300px;"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">冻结申请备注4：</label>
		<div class="controls">
			<input path="remark4" id="remark4"  value="${riskMerchantFreeze.remark4}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;width:300px;"/>
		</div>
	</div>
	
	<div class="control-group">
			<label class="control-label">冻结审核备注：</label>
			<div class="controls">
				  <input path="freezeMessage" value="${riskMerchantFreeze.freezeMessage}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;width:300px;"/>
			</div>
	</div> 
	
	<div class="control-group">
			<label class="control-label">解冻申请备注：</label>
			<div class="controls">
				  <form:input path="thawLog" htmlEscape="false" class="required" maxlength="20" placeholder="请输入备注信息,不超过20个数" style="width:300px;"/>
				<span class="help-inline"><font color="red">*</font> </span> 
			</div>
	</div> 
	
	<c:if test="${riskMerchantFreeze.status == '解冻审核拒绝'}">
		<div class="control-group">
				<label class="control-label">解冻审核备注：</label>
				<div class="controls">
					  <form:input path="thawMessages" value="${riskMerchantFreeze.thawMessages}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;width:300px;"/>(原申请已拒绝)
				</div>
		</div> 	
	</c:if>
		
	<input id="refererid" value="${referer}" type="hidden" name="referer"/>
	<input value="${pageNo}" type="hidden" name="pageNo"/>
		
	<div class="form-actions">
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="保存并提交"/>
		<input id="btnCancel" class="btn" type="button" value="取 消" onclick="history.go(-1)"/>
	</div>
</form:form>
</body>
</html>