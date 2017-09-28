<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>推广位管理</title>
	<meta name="decorator" content="default"/>
    <script language="JavaScript" src="${ctxStatic}/jquery.base64.js"></script>
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

        //对中文进行转码
        function encodeBase64(mingwen,times){
            var code="";
            var num=1;
            if(typeof times=='undefined'||times==null||times==""){
                num=1;
            }else{
                var vt=times+"";
                num=parseInt(vt);
            }
            if(typeof mingwen=='undefined'||mingwen==null||mingwen==""){
            }else{
                $.base64.utf8encode = true;
                code=mingwen;
                for(var i=0;i<num;i++){
                    code=$.base64.btoa(code);
                }
            }
            return code;
        };

        function checkValue(obj){
            var code = encodeBase64(obj);
            $("#proUrl").val(code);
        }
    </script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/prom/promManage/">推广位列表</a></li>
		<li class="active"><a href="${ctx}/prom/promManage/form?proId=${promManage.proId}">推广位<shiro:hasPermission name="prom:promManage:edit">${not empty promManage.proId?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="prom:promManage:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="promManage" action="${ctx}/prom/promManage/save" method="post" class="form-horizontal">
		<form:hidden path="proId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">推广位ID：</label>
			<div class="controls">
				<form:input path="promotionId" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">推广位名称：</label>
			<div class="controls">
				<form:input path="proName" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品名称：</label>
			<div class="controls">
				<form:input path="productName" htmlEscape="false" maxlength="200" class="input-xlarge"/>
                <span>产品为空时，自动默认填写全部产品！</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">推广方式：</label>
			<div class="controls">
                <form:select id="proType" path="proType" class="input-xlarge required">
                    <c:forEach items="${promType}" var="wStatus">
                        <form:option value="${wStatus.value}" label="${wStatus.name}"/>
                    </c:forEach>
                </form:select>
                <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
        <div class="control-group" id="ruleBeginTimeDiv">
            <label class="control-label">生成日期：</label>
            <div class="controls">
                <input id="createDate" name="createDate" type="text" readonly="readonly" maxlength="20" class="input-xlarge Wdate required"
                       value="<fmt:formatDate value="${promManage.createDate}" pattern="yyyy-MM-dd"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:new Date()});"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">推广连接：</label>
            <div class="controls">
                <form:input path="proUrl" onchange="checkValue(this.value);" id="proUrl" htmlEscape="false" maxlength="500" class="input-xlarge required"/>
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
			<shiro:hasPermission name="prom:promManage:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>