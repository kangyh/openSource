<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>联行号管理管理</title>
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
		<li><a href="${ctx}/route/lineBankNumber/">联行号列表</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="lineBankNumber" action="${ctx}/route/lineBankNumber/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">银行名称：</label>
			<div class="controls">
				${lineBankNumber.bankName }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行代码：</label>
			<div class="controls">
				${lineBankNumber.bankNo }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">省份名称：</label>
			<div class="controls">
				${lineBankNumber.provinceName }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">省份代码：</label>
			<div class="controls">
				${lineBankNumber.provinceCode }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">城市名称：</label>
			<div class="controls">
				${lineBankNumber.cityName }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">城市代码：</label>
			<div class="controls">
				${lineBankNumber.cityCode }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开户银行名称：</label>
			<div class="controls">
				${lineBankNumber.openBankName }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开户银行代码：</label>
			<div class="controls">
				${lineBankNumber.openBankCode }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联行号：</label>
			<div class="controls">
				${lineBankNumber.associateLineNumber }
			</div>
		</div>

		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>