<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>风控规则列表管理</title>
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
		
		//验证规则Id
		function CheckRuleId(){
			var ruleId = $("#ruleId").val();
			if(ruleId !="" && isNaN(ruleId)){
				$("#messageBox").text("规则Id必须为数字!");
				$("#ruleId").val("");
			}else{
				$.ajax({
		            type: "GET",
		            url: "${ctx}/risk/riskRulelist/checkRuleId",
		            cache:false,
		            data:{
		            	"ruleId":ruleId
					},
		            success: function(data){
		                if(1!=data){
		                	$("#ruleIdId").text("该ID已存在");
		                	$("#ruleId").val("");
		                }else{
		                	$("#ruleIdId").text("");
		                }
		            }
		        });
			}
		}
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/risk/riskRulelist/">风控规则列表列表</a></li>
		<li class="active"><a href="${ctx}/risk/riskRulelist/form?id=${riskRulelist.id}">风控规则列表<shiro:hasPermission name="risk:riskRulelist:edit">${not empty riskRulelist.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="risk:riskRulelist:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="riskRulelist" action="${ctx}/risk/riskRulelist/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<c:if test="${empty riskRulelist.rulelistId}">	
		<div class="control-group">
			<label class="control-label">规则类型：</label>
			<div class="controls">
				<form:input path="ruleType" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label  class="control-label">业务类型：</label>
			   <div class="controls">
			   <form:input path="buziType" htmlEscape="false" maxlength="20" class="required"/>
			  </div>
		</div>
		<div class="control-group">
			<label class="control-label">规则ID：</label>
			<div class="controls">
				<form:input id="ruleId" path="ruleId" onchange="CheckRuleId()" htmlEscape="false"  maxlength="50" class="required"/>
				<span id="ruleIdId" style="color:red;" class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规则描述：</label>
			<div class="controls">
				<form:input path="ruleDescription" htmlEscape="false" maxlength="2000" class="required"/>
			</div>
		</div>
			<div class="control-group">
			<label class="control-label">监控对像：</label>
			<div class="controls"><form:select id="monitorObject" path="monitorObject" class="input-xlarge ">
               		<form:option value="" label="请选择"/>
					<c:forEach items="${quotaTypeList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			  </div>
		</div>
		<div class="control-group">
			<label  class="control-label">风控措施：</label>
			   <div class="controls"><form:select id="riskControlAction" path="riskControlAction" class="input-xlarge ">
               		<form:option value="" label="请选择"/>
					<c:forEach items="${actionList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			  </div>
		</div>
		
		<div class="control-group">
			<label class="control-label">风控因素：</label>
			<div class="controls">
				<form:input path="riskControlFact" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">关联风控项：</label>
			<div class="controls">
				<form:input path="otherItem" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label  class="control-label">状态：</label>
			   <div class="controls"><form:select id="status" path="status" class="input-xlarge ">
               		<form:option value="" label="请选择"/>
					<c:forEach items="${statusList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			  </div>
		</div>
		</c:if>
		<c:if test="${not empty riskRulelist.rulelistId}">
		<div class="control-group">
			<label class="control-label">规则类型：</label>
			<div class="controls">
				<form:input path="ruleType" htmlEscape="false" maxlength="50" readonly="true" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label  class="control-label">业务类型：</label>
			   <div class="controls">
			   <form:input path="buziType" htmlEscape="false" maxlength="20" readonly="true" class="required"/>
			  </div>
		</div>
		<div class="control-group">
			<label class="control-label">规则ID：</label>
			<div class="controls">
				<form:input id="ruleId" path="ruleId" onchange="CheckRuleId()" htmlEscape="false"  readonly="true" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规则描述：</label>
			<div class="controls">
				<form:input path="ruleDescription" htmlEscape="false" maxlength="2000" readonly="true" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">监控对像：</label>
			<div class="controls">
				<form:input path="monitorObject" htmlEscape="false" maxlength="20" readonly="true" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">风控措施：</label>
			<div class="controls">
				<form:input path="riskControlAction" htmlEscape="false" maxlength="20" readonly="true" class="required"/>
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">风控因素：</label>
			<div class="controls">
				<form:input path="riskControlFact" htmlEscape="false" maxlength="200" readonly="true" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">关联风控项：</label>
			<div class="controls">
				<form:input path="otherItem" htmlEscape="false" maxlength="20" readonly="true" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label  class="control-label">状态：</label>
			   <div class="controls"><form:select id="status" path="status" class="input-xlarge ">
               		<form:option value="" label="请选择"/>
					<c:forEach items="${statusList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			  </div>
		</div>
		</c:if>
		<div class="form-actions">
			<shiro:hasPermission name="risk:riskRulelist:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
</body>
</html>