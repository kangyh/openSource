<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>转账理由管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/payment/transferAccountReason/">转账理由列表</a></li>
		<li class="active"><a href="${ctx}/payment/transferAccountReason/form?id=${transferAccountReason.id}">转账理由<shiro:hasPermission name="payment:transferAccountReason:edit">${not empty transferAccountReason.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="payment:transferAccountReason:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="transferAccountReason" action="${ctx}/payment/transferAccountReason/save" method="post" class="form-horizontal">
		<form:hidden path="transferReasonId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">流水号：</label>
			<div class="controls">
			${transferAccountReason.transferReasonId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">转账理由：</label>
			<div class="controls">
					${transferAccountReason.transferReason}
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">商户编码：</label>
			<div class="controls">
					${transferAccountReason.merchantId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<fmt:formatDate value="${transferAccountReason.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审核时间：</label>
			<div class="controls">
				<fmt:formatDate value="${transferAccountReason.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审核状态：</label>
			<div class="controls">
					${fns:getDictLabel(transferAccountReason.status, 'BatchPayReason', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">

			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="payment:transferAccountReason:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>