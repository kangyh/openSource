<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商户侧清结算管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="#">通道差错审核状态处理页面</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="settleDifferChannel" action="${ctx}/accounting/settleDifferChannels/save/${settleDifferChannel.differChanbillId}" method="post" class="form-horizontal">

	<div class="control-group">
		<label class="control-label">通道合作方：</label>
		<div class="controls">
			<input  value="${settleDifferChannel.channelCode}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">支付通道类型：</label>
		<div class="controls">
			<input value="${settleDifferChannel.channelType}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	
	<div class="control-group">
		<label class="control-label">差错批次号：</label>
		<div class="controls">
			<input value="${settleDifferChannel.errorBath}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">差错日期：</label>
		<div class="controls">
			<fmt:formatDate value="${settleDifferChannel.errorDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
		</div>
	</div>
	
	<div class="control-group">
		<label class="control-label">交易订单号：</label>
		 <div class="controls">
			<input value="${settleDifferChannel.transNo}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div>
	</div> 
	<div class="control-group">
		<label class="control-label">支付单号：</label>
		 <div class="controls">
			<input value="${settleDifferChannel.paymentId}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div>
	</div> 
	<div class="control-group">
		<label class="control-label">币种：</label>
		 <div class="controls">
			<input value="${settleDifferChannel.currency}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div>
	</div> 
	<div class="control-group">
		<label class="control-label">实际支付金额：</label>
		<div class="controls">
			<fmt:formatNumber value="${settleDifferChannel.successAmount}" pattern="￥0.0000" />
		</div>
	</div>

	<div class="control-group">
		<label class="control-label">交易成本：</label>
		<div class="controls">
			<fmt:formatNumber value="${settleDifferChannel.cost}" pattern="￥0.0000" />
		</div>
	</div>
	
	<div class="control-group">
		<label class="control-label">账单类型：</label>
		<div class="controls">
			<input path="billType" id="billType" value="${settleDifferChannel.billType}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">记账状态：</label>
		<div class="controls">
			<input path="errorStatus" id="errorStatus" value="${settleDifferChannel.errorStatus}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	
	<div class="control-group">
		<label class="control-label">审核状态：</label>
		<div class="controls">
	    	<form:select id="checkStatus" path="checkStatus" name="checkStatus" class="input-xlarge required">
			    <c:forEach items="${checkStatus}" var="checkStatusControl">
					<form:option value="${checkStatusControl.value}" label="${checkStatusControl.name}"/>
				</c:forEach>
		    </form:select>
		</div>
	</div>
	
	<div class="control-group">
		<label class="control-label">审核备注：</label>
		<div class="controls">
			 <form:textarea path="checkMessage" htmlEscape="false"  maxlength="20" class="required" placeholder="最多输入20个字符"/>
				<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	
		
		<input id="refererid" value="${referer}" type="hidden" name="referer"/>
		<input value="${pageNo}" type="hidden" name="pageNo"/>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保存并提交"/>
			<input id="btnCancel" class="btn" type="button" value="取 消" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>