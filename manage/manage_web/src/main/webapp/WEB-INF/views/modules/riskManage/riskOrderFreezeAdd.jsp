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
	<li><a href="#">商户订单规则添加列表</a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="riskOrderFreeze" action="${ctx}/riskManage/riskOrderFreezeQuery/save" method="post" class="form-horizontal">
	<sys:message content="${message}"/>
	
	<div class="control-group">
		<label class="control-label">商户编码：</label>
		<div class="controls">
			<form:input path="merchantCode" value="${riskOrderFreeze.merchantCode}" htmlEscape="false" maxlength="20" class="required"/>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	
	<div class="control-group">
		<label class="control-label">交易订单号：</label>
		<div class="controls">
			<form:input path="transNo" value="${riskOrderFreeze.transNo}" pattern="￥0.0000" htmlEscape="false" maxlength="20" class="required" placeholder="金额请输入数字"/>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	
	<div class="control-group">
		<label class="control-label">冻结金额：</label>
		<div class="controls">
			<form:input path="transAmount" value="${riskOrderFreeze.transAmount}" pattern="￥0.0000" htmlEscape="false" maxlength="10" class="required" placeholder="金额请输入数字"/>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	
	<div class="control-group">
		<label class="control-label">冻结理由：</label>
		<div class="controls">
		    <form:select id="freezeRemark" path="freezeRemark" class="input-xlarge required">
				<c:forEach items="${riskFreezeRemark}" var="riskFreezeRemarkStatus">
					<form:option value="${riskFreezeRemarkStatus.value}" label="${riskFreezeRemarkStatus.name}"/>
				</c:forEach>
			</form:select> 
		</div>
	</div>
	
	<div class="form-actions">
		<shiro:hasPermission name="merchant:merchantRate:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保存并提交"/>&nbsp;</shiro:hasPermission>
		<input id="btnCancel" class="btn" type="button" value="取 消" onclick="history.go(-1)"/>
	</div>
</form:form>
</body>
</html>