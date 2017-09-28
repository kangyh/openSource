<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分润比例模板管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					var huiyuanScale = $("#huiyuanScale").val();
					var spreaderScale = $("#spreaderScale").val();
					var gearScale = $("#gearScale").val();
					var higherLevelScale = $("#higherLevelScale").val();
					var highestLevelScale = $("#highestLevelScale").val();
					var total = huiyuanScale*1 + spreaderScale*1 + gearScale*1 + higherLevelScale*1 + highestLevelScale*1;
					if(total == 100){
						loading('正在提交，请稍等...');
						form.submit();
					}else{
						$("#messageBox1").text("分润比例之和应等于100");
						return;
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
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/prom/promProfitTemplet/">分润比例模板列表</a></li>
		<li class="active"><a href="${ctx}/prom/promProfitTemplet/form?promId=${promProfitTemplet.promId}">分润比例模板<shiro:hasPermission name="prom:promProfitTemplet:edit">${not empty promProfitTemplet.promId?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="prom:promProfitTemplet:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="promProfitTemplet" action="${ctx}/prom/promProfitTemplet/save" method="post" class="form-horizontal">
		<form:hidden path="promId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">模板名称：</label>
			<div class="controls">
				<form:input path="templetName" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">汇元比例：</label>
			<div class="controls">
				<form:input path="huiyuanScale" id="huiyuanScale" htmlEscape="false" maxlength="10" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">推荐人比例：</label>
			<div class="controls">
				<form:input path="spreaderScale" id="spreaderScale" htmlEscape="false" maxlength="10" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">推广者比例：</label>
			<div class="controls">
				<form:input path="gearScale" id="gearScale" htmlEscape="false" maxlength="10" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上级比例：</label>
			<div class="controls">
				<form:input path="higherLevelScale" id="higherLevelScale" htmlEscape="false" maxlength="10" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上上级比例：</label>
			<div class="controls">
				<form:input path="highestLevelScale" id="highestLevelScale" htmlEscape="false" maxlength="10" class="input-xlarge required"/>
			</div>
		</div>
		<div id="messageBox1" style="font-size: 15px; color: red;"></div>
		<div class="form-actions">
			<shiro:hasPermission name="prom:promProfitTemplet:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>