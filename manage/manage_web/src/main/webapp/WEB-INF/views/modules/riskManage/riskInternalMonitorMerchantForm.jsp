<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>内部监控商户配制表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
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

                    //校验
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

		function  getMerchantName() {
		    var merchantId = $("#merchantId").val();
		    if(merchantId == undefined || merchantId == ""){
                $("merchantId").focus();
                return;
			}

            $.ajax({
                async: false,
                type: "GET",
                url: "${ctx}/merchant/selectMerchantName",
                data: {'merchantId': merchantId},
                dataType: 'json',
                jsonp: 'callback',
                success: function (data) {
                    if(data == "NOFOUND"){
                        $("#merchantId").val("");
                        $("#merchantId").focus();
                        return;
					}
                    $("#merchantName").val(data);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                }
            });
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/risk/riskInternalMonitorMerchant/">内部监控商户配制表列表</a></li>
		<li class="active"><a href="${ctx}/risk/riskInternalMonitorMerchant/form?id=${riskInternalMonitorMerchant.id}">内部监控商户配制表<shiro:hasPermission name="risk:riskInternalMonitorMerchant:edit">${not empty riskInternalMonitorMerchant.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="risk:riskInternalMonitorMerchant:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="riskInternalMonitorMerchant" action="${ctx}/risk/riskInternalMonitorMerchant/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<c:if test="${empty riskLoginBlacklist.id}">		
		<div class="control-group">
			<label class="control-label">商户编码：</label>
			<div class="controls">
				<form:input path="merchantId" id="merchantId" type="number" htmlEscape="false" maxlength="20" class="input-xlarge  required" onblur="getMerchantName()"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户名称：</label>
			<div class="controls">
				<form:input path="merchantName" id="merchantName" htmlEscape="false" maxlength="100" readonly="true" class="input-xlarge  required"/>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">产品编码：</label>
			<div class="controls">
				<form:input path="productCode" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div> --%>
		<div class="control-group">
			<label  class="control-label">产品名称：</label>
			   <div class="controls"><form:select id="productCode" path="productCode" class="input-xlarge  required">
               		<form:option value="" label="请选择"/>
					<c:forEach items="${productNameList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			  </div>
		</div>
		<div class="control-group">
			<label class="control-label">频率（分钟）：</label>
			<div class="controls">
				<form:input path="frequency" htmlEscape="false" maxlength="11" value="30" readonly="true" class="input-xlarge  required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">阈值（%）：</label>
			<div class="controls">
				<form:input path="threshold" id="threshold" htmlEscape="false" maxlength="3" text="number" class="input-xlarge  required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">变化率（%）：</label>
			<div class="controls">
				<form:input path="changeRate" id="changeRate" htmlEscape="false" maxlength="3" text="number" class="input-xlarge  required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始时间：</label>
			<div class="controls">
				<form:input path="beginTime" id="beginTime" htmlEscape="false" maxlength="20" class="input-xlarge  required"/>
				<label style="color: red">时间格式：HH:MM </label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间：</label>
			<div class="controls">
				<form:input path="endTime"  id = "endTime" htmlEscape="false" maxlength="20" class="input-xlarge  required"/>
				<label style="color: red">时间格式：HH:MM </label>
			</div>
		</div>
		</c:if>
		
		
		<c:if test="${not empty riskLoginBlacklist.id}">		
		<div class="control-group">
			<label class="control-label">商户编码：</label>
			<div class="controls">
				<form:input path="merchantId" id="merchantId" type="number" htmlEscape="false" maxlength="20" class="input-xlarge  required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户名称：</label>
			<div class="controls">
				<form:input path="merchantName" id="merchantName" htmlEscape="false" maxlength="100" class="input-xlarge  required"/>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">产品编码：</label>
			<div class="controls">
				<form:input path="productCode" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div> --%>
		<div class="control-group">
			<label  class="control-label">产品名称：</label>
			   <div class="controls"><form:select id="productCode" path="productCode" class="input-xlarge  required">
               		<form:option value="" label="请选择"/>
					<c:forEach items="${productNameList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			  </div>
		</div>
		<div class="control-group">
			<label class="control-label">频率（分钟）：</label>
			<div class="controls">
				<form:input path="frequency" htmlEscape="false" maxlength="11" value="30" readonly="true" class="input-xlarge  required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">阈值（%）：</label>
			<div class="controls">
				<form:input path="threshold" id="threshold"  htmlEscape="false" maxlength="3" text="number"  class="input-xlarge  required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">变化率（%）：</label>
			<div class="controls">
				<form:input path="changeRate" id="changeRate" htmlEscape="false" maxlength="3" text="number" class="input-xlarge  required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始时间：</label>
			<div class="controls">
				<form:input path="beginTime" id="beginTime" htmlEscape="false" maxlength="20" class="input-xlarge  required"/>
				<label style="color: red">时间格式：HH:MM </label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间：</label>
			<div class="controls">
				<form:input path="endTime" id="endTime" htmlEscape="false" maxlength="20" class="input-xlarge  required"/>
				<label style="color: red">时间格式：HH:MM </label>
			</div>
		</div>
		</c:if>
		<div class="form-actions">
			<shiro:hasPermission name="risk:riskInternalMonitorMerchant:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>