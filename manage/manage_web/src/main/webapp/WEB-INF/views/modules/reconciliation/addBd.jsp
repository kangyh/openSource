<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>差错管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					
					var handleMessage=$('#handleMessage').val();
					if(handleMessage!=""){
					$('#handleMessageid').val(handleMessage);
					}
					var requestAmount=$("#requestAmount").val();
					var successAmount=$("#successAmount").val();
					if(requestAmount != successAmount){
						$("#successAmountId").text("金额不一致，不能进行补单");
						return false;
					}
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
		<li><a href="${ctx}/reconciliation/SettleDifferRrecord?cache=1">对账差错记录列表</a></li>
		<li class="active"><a href="#">处理补单页面</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="settleDifferRecord" action="${ctx}/reconciliation/SettleDifferRrecord/differences/bd/${settleDifferRecord.differId}" method="post" class="form-horizontal">
		<sys:message content="${message}"/>	
	
	<div class="control-group">
		<label class="control-label">通道合作方：</label>
		<div class="controls">
			<input name="channelName" value="${settleDifferRecord.channelName}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">支付通道类型：</label>
		<div class="controls">
			<input value="${settleDifferRecord.channelType}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">差错日期：</label>
		 <div class="controls">
			<fmt:formatDate value="${settleDifferRecord.errorDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
	</div> 
	<div class="control-group">
		<label class="control-label">差错类型：</label>
		<div class="controls">
			<input path="differType"  value="${settleDifferRecord.differType}" readOnly="true"style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">交易订单号：</label>
		<div class="controls">
			<input path="transNo" id="transNo" value="${settleDifferRecord.transNo}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">支付单号：</label>
		<div class="controls">
			<input path="paymentId" id="paymentId" value="${settleDifferRecord.paymentId}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">我方金额：</label>
		<div class="controls">
			<fmt:formatNumber value="${settleDifferRecord.requestAmount}" pattern="￥0.0000" />
		</div>
	</div>
	
	<c:choose>
		<c:when test="${not empty settleDifferRecord.successAmount}">
			<div class="control-group">
				<label class="control-label">对方金额：</label>
				<div class="controls">
					<fmt:formatNumber value="${settleDifferRecord.successAmount}" pattern="￥0.0000" />
				</div>
			</div>
		</c:when>
		<c:otherwise>
		-- 
		</c:otherwise>
	</c:choose>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" onclick="return confirmation()" value="保存并提交"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="取 消" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>