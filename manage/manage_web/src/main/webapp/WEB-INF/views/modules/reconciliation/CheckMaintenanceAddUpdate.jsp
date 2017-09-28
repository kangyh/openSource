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
					
					var channelCode=$('#channelCode').val();
					var channelType=$('#channelType').val();
					var bankNo=$('#bankNo').val();
					var remoteAdressPath=$('#remoteAdressPath').val();
				
					if(channelCode !='' && channelType !=''){
						if(channelCode=='DIRCON'){
							if(bankNo !=''){
								$('#channelCodeid').val(channelCode);
								$('#channelTypeid').val(channelType);
								$('#bankNoid').val(bankNo);
								loading('正在提交，请稍等...');
									form.submit();
							}else{
									alert("请选择直连银行");
									return false;
							}
						}
							$('#channelCodeid').val(channelCode);
							$('#channelTypeid').val(channelType);
							loading('正在提交，请稍等...');
							form.submit();
						
					}else{
						alert("请选择通道合作方和通道类型");
						return false;
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
			$("#bankName").val(field);
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
		<li><a href="${ctx}/reconciliation/CheckMaintenance?cache=1">对账参数维护列表</a></li>
		<li class="active"><a href="#">${not empty settleChannelManager.channelManangeId?'对账参数修改列表':'对账参数添加列表'}</a></li>
	</ul><br/>

	<form:form id="inputForm" modelAttribute="settleChannelManager" action="${ctx}/reconciliation/CheckMaintenance/save?channelManangeId=${settleChannelManager.channelManangeId}" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<c:if test="${empty settleChannelManager.channelManangeId}">
			<div class="control-group">
				<label class="control-label">通道合作方：</label>
				<div class="controls">
				<form:select id="channelCode" path="channelCode" name="channelCode" class="input-xlarge required" onchange="checkValue(this);" cssStyle="width: 220px">
					<option selected value="">-选择通道合作方-</option>
					<c:forEach items="${dataEntity}" var="SettleRuleControl">
						<form:option value="${SettleRuleControl.value}" label="${SettleRuleControl.name}"/>
					</c:forEach>
				</form:select>
				<input type="hidden" name="channelName" id="channelName" class="input-xlarge required">
				</div>
			</div>

			<div style="display: none;" id="bank_id"  class=control-group>
				<label class="control-label">选择直连银行：</label>
				<div class="controls">
				<form:select id="bankNo" path="bankNo" name="bankNo" class="input-xlarge required" cssStyle="width: 220px" onchange="Sel2change(this.options[this.options.selectedIndex].text);">
					<option selected value="">-选择直连银行-</option>
					<form:options items="${fns:getBankInfoList()}" itemLabel="bankName" itemValue="bankNo" htmlEscape="false" />
				</form:select>
				<input type="hidden" name="bankName" id="bankName" class="input-xlarge required">
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">支付通道类型：</label>
				<div class="controls">
				<form:select id="channelType" path="channelType" class="input-xlarge required" cssStyle="width: 220px">
					<option selected value="">-选择支付通道类型-</option>
					<c:forEach items="${checkFlgList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">对账标识：</label>
				<div class="controls">
					<span><form:checkbox  path="checkFlg" id="checkFlg1" maxlength="12" class="required" value="FI"/><label for="checkFlg1">文件对账</label></span>
					<span><form:checkbox  path="checkFlg" id="checkFlg2" maxlength="12" class="required" value="II"/><label for="checkFlg2">接口对账</label></span>
					<input type="hidden" name="_checkFlg" value="on"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</c:if>

		<%--修改操作--%>
		<c:if test="${not empty settleChannelManager.channelManangeId}">
			<div class="control-group">
				<label class="control-label">通道合作方：</label>
				<div class="controls">
					<input value="${settleChannelManager.channelName}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">支付通道类型：</label>
				<div class="controls">
					<input  value="${settleChannelManager.channelType}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">对账标识：</label>
				<div class="controls">
					<span><form:radiobutton path="checkFlg" id="checkFlg1" maxlength="12" class="required" value="FI"/><label for="checkFlg1">文件对账</label></span>
					<span><form:radiobutton path="checkFlg" id="checkFlg2" maxlength="12" class="required" value="II"/><label for="checkFlg2">接口对账</label></span>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</c:if>

		<%--<div class="control-group">
			<label class="control-label">对账标识：</label>
			<div class="controls">
				<form:checkboxes path="checkFlg" items="${checkFlg}" maxlength="12" itemLabel="name" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">对账方式：</label>
			<div class="controls">
				<form:radiobuttons path="settleWay" items="${settleWayList}" maxlength="12" itemLabel="name" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>

		<div class="control-group">
			<label class="control-label">对账方式：</label>
			<div class="controls">
				<span><form:radiobutton path="settleWay" id="settleWay1" maxlength="12" class="required" value="auto"/><label for="settleWay1">自动对账</label></span>
				<span><form:radiobutton path="settleWay" id="settleWay2" maxlength="12" class="required" value="handle"/><label for="settleWay2">手动对账</label></span>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>


		<div class="control-group">
			<label class="control-label">远程对账文件地址：</label>
			<div class="controls">
				<form:select id="remoteAdressPath" path="remoteAdressPath"  class="size:40px">
					<c:forEach items="${adressPath}" var="adressPathStatus">
						<form:option value="${adressPathStatus.value}" label="${adressPathStatus.name}"/>
					</c:forEach>
				</form:select>
				<!-- 组合生成地址 -->
				<form:input path="remoteAdress" maxlength="32" htmlEscape="false" class="required" cssStyle="width: 150px"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		 <div class="control-group">
			<label class="control-label">对账本地地址：</label>
			<div class="controls">
				  <form:input path="localFilePath" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> 
		 <div class="control-group">
			<label class="control-label">远程服务器用户名：</label>
			<div class="controls">
				  <form:input path="remoteUserName" htmlEscape="false" maxlength="32" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> 
		 <div class="control-group">
			<label class="control-label">远程服务器密码：</label>
			<div class="controls">
				  <form:input path="remotePassword" htmlEscape="false"  maxlength="32" type="password" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		 <div class="control-group">
			<label class="control-label">端口号：</label>
			<div class="controls">
				  <form:input path="port" htmlEscape="false"  maxlength="32"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">生效标识：</label>
			<div class="controls">
				<form:select id="effectFlg" path="effectFlg"  class="input-xlarge required" cssStyle="width: 220px">
					<c:forEach items="${effectFlg}" var="effectFlgStatus">
						<form:option value="${effectFlgStatus.value}" label="${effectFlgStatus.name}"/>
					</c:forEach>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规则类型：</label>
			<div class="controls">
				<form:select id="effectFlg" path="ruleType"  class="input-xlarge required" cssStyle="width: 220px">
					<c:forEach items="${ruleType}" var="ruleTy">
						<form:option value="${ruleTy.value}" label="${ruleTy.name}"/>
					</c:forEach>
				</form:select>
			</div>
		</div>

		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保存并提交"/>
			<input id="btnCancel" class="btn" type="button" value="取 消" onclick="history.go(-1)"/>
		</div>
	</form:form>

</body>
</html>