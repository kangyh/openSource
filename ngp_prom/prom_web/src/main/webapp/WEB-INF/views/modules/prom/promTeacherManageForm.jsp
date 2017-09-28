<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>上下级关系管理</title>
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
		
		function checkCode() {
            $("#btnSubmit").attr("disabled",false);
            $("#merchantCode_info").text("");
            var merchantCode = $("#merchantCode").find("option:selected").val();
            $.post($("#url_id").attr("href")+"checkCode",{
                merchantCode:merchantCode,
                teId:$("#teId").val()
            },function(data){
                if(data != null && data != "" && data != undefined){
                    if(data == "M") {
                        $("#merchantCode_info").text("该商户存在上下级关系，请选择其他商户！").show();
                        $("#btnSubmit").attr("disabled","disabled");
                        return;
                    }else {
                        $("#merchantCode_info").text("").hide();
                        $("#btnSubmit").attr("disabled",false);
                        return;
                    }
                }
            },"text");
            $("#merchantName").val($("#merchantCode").find("option:selected").text());
        }
		
		function findLevel() {
            $("#btnSubmit").attr("disabled",false);
            $("#super_merchantCode_info").text("");
		    var merchantCode = $("#merchantCode").find("option:selected").val();
		    var superMerchantCode = $("#superMerchantCode").find("option:selected").val();
		    if(merchantCode == superMerchantCode){
                $("#super_merchantCode_info").text("上级不能选择自己，请选择其他商户！").show();
                $("#btnSubmit").attr("disabled","disabled");
                return;
            }
            $.post($("#url_id").attr("href")+"checkLevel",{
                superMerchantCode:superMerchantCode,
                merchantCode:merchantCode
            },function(data){
                if(data != null && data != "" && data != undefined){
                    if(data == "D") {
                        $("#super_merchantCode_info").text("不能互为上下级关系，请选择其他商户！").show();
                        $("#btnSubmit").attr("disabled","disabled");
                        return;
                    }else if(data == "F"){
                        $("#super_merchantCode_info").text("该商户已满3级，请选择其他商户！").show();
                        $("#btnSubmit").attr("disabled","disabled");
                        return;
                    }else if(data == "X"){
                        $("#super_merchantCode_info").text("存在互为上下级，请选择其他商户！").show();
                        $("#btnSubmit").attr("disabled","disabled");
                        return;
                    }else {
                        $("#super_merchantCode_info").text("").hide();
                        $("#btnSubmit").attr("disabled",false);
                        return;
                    }
                }
            },"text");
            $("#superMerchantName").val($("#superMerchantCode").find("option:selected").text());
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a id="url_id" href="${ctx}/prom/promTeacherManage/">上下级关系列表</a></li>
		<li class="active"><a href="${ctx}/prom/promTeacherManage/form?teId=${promTeacherManage.id}">上下级关系<shiro:hasPermission name="prom:promTeacherManage:edit">${not empty promTeacherManage.teId?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="prom:promTeacherManage:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
    <form:form id="inputForm" modelAttribute="promTeacherManage" action="${ctx}/prom/promTeacherManage/save" method="post" class="form-horizontal">
        <form:hidden path="teId" id="teId"/>
        <form:hidden path="merchantName" id="merchantName" />
        <form:hidden path="superMerchantName" id="superMerchantName" />
        <sys:message content="${message}"/>
        <div class="control-group">
            <label class="control-label">商户编码：</label>
            <div class="controls">
                <form:select id="merchantCode" path="merchantCode" onchange="checkCode()" class="input-xlarge required">
                    <form:option value="" label="--请选择--"/>
                    <c:forEach items="${merList}" var="wStatus">
                        <form:option value="${wStatus.merchantId}" label="${wStatus.merchantId}-${wStatus.merchantName}"/>
                    </c:forEach>
                </form:select>
                <span class="help-inline"><font color="red">*</font> </span>
                <label id="merchantCode_info" class="error"></label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">上级（商户编码）：</label>
            <div class="controls">
                <form:select id="superMerchantCode" path="superMerchantCode" onchange="findLevel()" class="input-xlarge">
                    <form:option value="" label="--请选择--"/>
                    <c:forEach items="${merList}" var="wStatus">
                        <form:option value="${wStatus.merchantId}" label="${wStatus.merchantId}-${wStatus.merchantName}"/>
                    </c:forEach>
                </form:select>
                <label id="super_merchantCode_info" class="error"></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分润比例模板号：</label>
			<div class="controls">
                <form:select id="templetId" path="templetId" class="input-xlarge required">
                    <form:option value="" label="--请选择--"/>
                    <c:forEach items="${profitList}" var="wStatus">
                        <form:option value="${wStatus.templetId}" label="${wStatus.templetId}-${wStatus.templetName}"/>
                    </c:forEach>
                </form:select>
                <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">生效日期：</label>
			<div class="controls">
                <input id="effectTime" name="effectTime" type="text" readonly="readonly" maxlength="20" class="input-xlarge Wdate required"
                       value="<fmt:formatDate value="${promTeacherManage.effectTime}" pattern="yyyy-MM-dd"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:new Date(),maxDate:'#F{$dp.$D(\'failureTime\')}'});"/>
                <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">失效日期：</label>
			<div class="controls">
                <input id="failureTime" name="failureTime" type="text" readonly="readonly" maxlength="20" class="input-xlarge Wdate required"
                       value="<fmt:formatDate value="${promTeacherManage.failureTime}" pattern="yyyy-MM-dd"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'#F{$dp.$D(\'effectTime\')}'});"/>
                <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">状态：</label>
            <div class="controls">
                <form:select id="status" path="status" class="input-xlarge required">
                    <c:forEach items="${status}" var="wStatus">
                        <form:option value="${wStatus.value}" label="${wStatus.name}"/>
                    </c:forEach>
                </form:select>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
		<div class="form-actions">
			<shiro:hasPermission name="prom:promTeacherManage:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>