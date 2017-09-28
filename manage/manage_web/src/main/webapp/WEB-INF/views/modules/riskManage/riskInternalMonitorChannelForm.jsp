<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>内部监控通道配置管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {

			var bankValue = $("#channelPartnerCode").val();
			if(bankValue=='DIRCON'){
				$('#bank_id').css('display','block');
			}else{
				$('#bank_id').css('display','none');
			}
			
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
				    var maxThresHold = $("#threshold").val();
					if(parseInt(maxThresHold) > 100){
                        $("#threshold").val("");
                        $("#threshold").focus();
                        return;
					}
					var changeRate = $("#changeRate").val();
                    if(parseInt(changeRate) + parseInt(maxThresHold) > 100){
                        $("#changeRate").val("");
                        $("#changeRate").focus();
                        return;
                    }
                    //校验时间
                    var beginTime = $("#beginTime").val();

                    if(beginTime.length > 5 || parseInt(beginTime.substr(0,2)) > 23 || parseInt(beginTime.substr(3,2)) > 59 || beginTime.substr(2,1) != ":"){
                        $("#beginTime").val("");
                        $("#beginTime").focus();
                        return;
                    }

                    var endTime = $("#endTime").val();

                    if(endTime.length > 5 || parseInt(endTime.substr(0,2)) > 23 || parseInt(endTime.substr(3,2)) > 59 || endTime.substr(2,1) != ":"){
                        $("#endTime").val("");
                        $("#endTime").focus();
                        return;
                    }

                    if(beginTime > endTime){
                        $("#endTime").val("");
                        $("#beginTime").val("");
                        $("#beginTime").focus();
                        return;
                    }

                    var bankNo = $("#bankNo").find("option:selected").val();
                    if(bankNo != null || bankNo != "" || bankNo != undefined){
                        $("#bankName_id").val($("#bankNo").find("option:selected").text());
                    }
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
		
		function Sel2change(id,field){
			$("#"+id).val(field);
		}
		
		function checkValue(obj){
			var bankValue = obj.value;
			if(bankValue=='DIRCON'){
				$('#bank_id').css('display','block');
			}else{
				$('#bank_id').css('display','none');
			}
            $("#channelPartnerName").val($("#channelPartnerCode").find("option:selected").text());
		}


	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/risk/riskInternalMonitorChannel/">内部监控通道配置列表</a></li>
		<li class="active"><a href="${ctx}/risk/riskInternalMonitorChannel/form?id=${riskInternalMonitorChannel.id}">内部监控通道配置<shiro:hasPermission name="risk:riskInternalMonitorChannel:edit">${not empty riskInternalMonitorChannel.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="risk:riskInternalMonitorChannel:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="riskInternalMonitorChannel" action="${ctx}/risk/riskInternalMonitorChannel/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
        <form:hidden path="bankName" id="bankName" />

		<sys:message content="${message}"/>
		<c:if test="${empty riskLoginBlacklist.id}">

		<div class="control-group">
				<label class="control-label">通道合作方：</label>
				<div class="controls">
				<form:select id="channelPartnerCode" path="channelPartnerCode" name="channelPartnerCode" class="input-xlarge required" onchange="checkValue(this);" cssStyle="width: 220px" >
					<option selected value="">-选择通道合作方-</option>
					<c:forEach items="${dataEntity}" var="SettleRuleControl">
						<form:option value="${SettleRuleControl.value}" label="${SettleRuleControl.name}"/>
					</c:forEach>
				</form:select>
				</div>
			</div>

			<div style="display: none;" id="bank_id"  class=control-group>
				<label class="control-label">选择直连银行：</label>
				<div class="controls">
				<form:select id="bankNo" path="bankNo" name="bankNo" class="input-xlarge required" cssStyle="width: 220px" onchange="Sel2change('bankName',this.options[this.options.selectedIndex].text);">
					<option selected value="">-选择直连银行-</option>
					<form:options items="${fns:getBankInfoList()}" itemLabel="bankName" itemValue="bankNo" htmlEscape="false" />
				</form:select>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">支付通道类型：</label>
				<div class="controls">
				<form:select id="channelTypeCode" path="channelTypeCode" class="input-xlarge required" cssStyle="width: 220px" onchange="Sel2change('channelTypeName',this.options[this.options.selectedIndex].text);">
					<option selected value="">-选择支付通道类型-</option>
					<c:forEach items="${checkTypeList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
				</div>
			</div>
					
		<div class="control-group">
			<label class="control-label">频率（分钟）：</label>
			<div class="controls">
				<form:input path="frequency" htmlEscape="false" maxlength="11" class="input-xlarge required" readonly="true" value="30"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">阈值（%）：</label>
			<div class="controls">
				<form:input path="threshold"  id="threshold" htmlEscape="false" maxlength="3" class="input-xlarge required" type="number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">变化率（%）：</label>
			<div class="controls">
				<form:input path="changeRate"  id="changeRate" htmlEscape="false" maxlength="3" class="input-xlarge required" type="number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始时间：</label>
			<div class="controls">
				<form:input path="beginTime" htmlEscape="false" maxlength="20" class="input-xlarge required" />
				<label style="color: red">时间格式：HH:MM </label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间：</label>
			<div class="controls">
				<form:input path="endTime" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<label style="color: red">时间格式：HH:MM </label>
			</div>
		</div>
		</c:if>
		
		
		<c:if test="${not empty riskLoginBlacklist.id}">			
		<div class="control-group">
				<label class="control-label">通道合作方：</label>
				<div class="controls">
				<form:select id="channelPartnerCode" path="channelPartnerCode" name="channelPartnerCode" class="input-xlarge required" onchange="checkValue(this);" cssStyle="width: 220px">
					<option selected value="">-选择通道合作方-</option>
					<c:forEach items="${dataEntity}" var="SettleRuleControl">
						<form:option value="${SettleRuleControl.value}" label="${SettleRuleControl.name}"/>
					</c:forEach>
				</form:select>
				</div>
			</div>

			<div style="display: none;" id="bank_id"  class=control-group>
				<label class="control-label">选择直连银行：</label>
				<div class="controls">
				<form:select id="bankNo" path="bankNo" name="bankNo" class="input-xlarge required" cssStyle="width: 220px" onchange="Sel2change('bankName',this.options[this.options.selectedIndex].text);">
					<option selected value="">-选择直连银行-</option>
					<form:options items="${fns:getBankInfoList()}" itemLabel="bankName" itemValue="bankNo" htmlEscape="false" />
				</form:select>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">支付通道类型：</label>
				<div class="controls">
				<form:select id="channelTypeCode" path="channelTypeCode" class="input-xlarge required" cssStyle="width: 220px" onchange="Sel2change('channelTypeName',this.options[this.options.selectedIndex].text);">
					<option selected value="">-选择支付通道类型-</option>
					<c:forEach items="${checkTypeList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
				</div>
			</div>
		<div class="control-group">
			<label class="control-label">频率（分钟）：</label>
			<div class="controls">
				<form:input path="frequency" htmlEscape="false" maxlength="11" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">阈值（%）：</label>
			<div class="controls">
				<form:input path="threshold" htmlEscape="false" maxlength="3" text="number" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">变化率（%）：</label>
			<div class="controls">
				<form:input path="changeRate"  id="changeRate" htmlEscape="false" maxlength="3" class="input-xlarge required" type="number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始时间：</label>
			<div class="controls">
				<form:input path="beginTime" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<label style="color: red">时间格式：HH:MM </label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间：</label>
			<div class="controls">
				<form:input path="endTime" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<label style="color: red">时间格式：HH:MM </label>
			</div>
		</div>
		</c:if>
		<div class="form-actions">
			<shiro:hasPermission name="risk:riskInternalMonitorChannel:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>