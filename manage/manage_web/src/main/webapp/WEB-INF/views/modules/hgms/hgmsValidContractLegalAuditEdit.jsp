<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>有效合约管理</title>
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
            //根据业务类型获取服务项方法
            getServAccBusi($("#businessId").val());
            //根据businessId获取service
            $("#code_business").on("change", "select", function () {
                var businessId = $("#businessName option:selected").attr("title");
                $("#businessId").val(businessId);
                $('#serviceName').empty();
                $('#serviceId').val('');
                $('#serviceName').parent().find(".select2-chosen").html("");
                getServAccBusi(businessId);
            });
            //根据businessId获取service
            $("#code_service").on("change", "select", function () {
                $("#serviceId").val($("#serviceName option:selected").attr("title"));
            });
            $("#s2id_serviceName").find(".select2-chosen").text($("#name_service").val());
		});
        //根据业务类型获取服务项方法
        function getServAccBusi(businessId){
            var el  = $('#serviceName');
            $.ajax({
                async: false,
                type: "GET",
                url: "${ctx}/hgms/hgmsValidContract/getService",
                data: {"businessId":businessId},
                dataType: 'json',
                success: function(data){
                    $.each(data , function(k , v) {
                        var option  = '<option title="'+v.serviceId+'" value="'+v.serviceName+'">'+v.serviceName+'</option>';
                        el.append(option);
                    })
                },
                error: function(){
                    console.log("请求失败!");
                }
            });
        }


	</script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/hgms/hgmsValidContractLegalAudit/">合约法务列表</a></li>
    <li class="active"><a href="${ctx}/hgms/hgmsValidContractLegalAudit/edit?id=${hgmsValidContract.id}">合约法务修改</a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="hgmsValidContract" action="${ctx}/hgms/hgmsValidContractLegalAudit/update" method="post" class="form-horizontal"  enctype="multipart/form-data">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">合约编码：</label>
        <div class="controls">
            <form:input path="contractId" class="input-xlarge" readonly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">商户编号：</label>
        <div class="controls">
            <form:input path="merchantId" class="input-xlarge" readonly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">公司名称：</label>
        <div class="controls">
            <form:input path="companyName" class="input-xlarge" htmlEscape="false" readonly="true" style="border:0px;background-color:#fff;padding-top: 3px;" />
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">业务名称：</label>
        <div class="controls" id="code_business">
            <form:select id="businessName" path="businessName" class="input-xlarge required">
                <c:forEach items="${businessList}" var="business">
                    <form:option value="${business.businessName}" label="${business.businessName}" title="${business.businessId}"/>
                </c:forEach>
            </form:select>
        </div>
        <form:hidden id="businessId" path="businessId"/>
    </div>
    <div class="control-group">
        <label class="control-label">服务项名称：</label>
        <div class="controls" id="code_service">
            <form:select id="serviceName" path="serviceName" class="input-xlarge required">
            </form:select>
        </div>
        <input id="name_service" type="hidden" value="${hgmsValidContract.serviceName}"/>
        <form:hidden id="serviceId" path="serviceId"/>
    </div>
    <div class="control-group">
        <label class="control-label">有效期开始：</label>
        <div class="controls">
            <input name="validityBeginTime" type="text" readonly="true" maxlength="20" class="input-medium Wdate "
                   value="<fmt:formatDate value="${hgmsValidContract.validityBeginTime}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'#F{$dp.$D(\'validityEndTime\')}'});"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">有效期结束：</label>
        <div class="controls">
            <input name="validityEndTime" type="text" readonly="true" maxlength="20" class="input-medium Wdate "
                   value="<fmt:formatDate value="${hgmsValidContract.validityEndTime}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'#F{$dp.$D(\'validityBeginTime\')}'});"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">合同名称：</label>
        <div class="controls">
            <form:input path="contractName" class="input-xlarge required" htmlEscape="false" />
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">合同文件：</label>
        <div class="controls">
            <input type="file" name="contractFileAddressFile" class="input-xlarge" htmlEscape="false" />
            <c:if test="${not empty hgmsValidContract.contractFileAddress}">
                <a class="media" href="${hgmsValidContract.contractFileAddress}" target="_blank">合同文件</a>
            </c:if>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="hgms:hgmsValidContractLegalAudit:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>