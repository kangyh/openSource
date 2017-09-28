<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>海关信息设置管理</title>
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
	<script type="text/javascript">
        $(function(){
            document.getElementById("customcodeid").value =  $('#customNo').val();
        });
        function submitform(){
            var customcodeid=$("#customcodeid").val();

            var id=$("#id").val();
            var customNos=  $('#customNo').val();//选中的文本
           if(id!=""){
               if( customNos!=customcodeid ){
            $.ajax({
                type:"post",
                url:"${ctx}/cbms/cbmsCustomsetting/Customsettinglist",
                success:function(msg){
                    if (msg.indexOf(customNos)== -1){
                       $('#inputForm').submit();
                    }else{
                        alert("此海关被占用 ，请选取其他海关");
                    }
                }
            });
			}else{
                $('#inputForm').submit();
			}
		   }else{
               $.ajax({
                   type:"post",
                   url:"${ctx}/cbms/cbmsCustomsetting/Customsettinglist",
                   success:function(msg){
                       if (msg.indexOf(customNos)== -1){
                           $('#inputForm').submit();
                       }else{
                           alert("此海关被占用 ，请选取其他海关");
                       }
                   }
               });
		   }
        }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/cbms/cbmsCustomsetting/">海关信息设置列表</a></li>
		<li class="active"><a href="${ctx}/cbms/cbmsCustomsetting/form?id=${cbmsCustomcodeandsetting.id}">海关信息设置<shiro:hasPermission name="cbms:cbmsCustomsetting:edit">${not empty cbmsCustomsetting.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cbms:cbmsCustomsetting:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cbmsCustomsetting" action="${ctx}/cbms/cbmsCustomsetting/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
        <input type="hidden" id="id" value="${cbmsCustomCode.id}"/>
        <input type="hidden" id="customcodeid" value=""/>
		<div class="control-group">
			<label class="control-label">海关编码：</label>
			<div class="controls">
				<form:input path="customNo" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">海关名称：</label>
			<div class="controls">
				<form:input path="chinaName" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付公司海关编号：</label>
			<div class="controls">
				<form:input path="customCode" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付企业名称：</label>
			<div class="controls">
				<form:input path="companyName" htmlEscape="false" maxlength="64" class="input-xlarge  required "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付公司备案号：</label>
			<div class="controls">
				<form:input path="recordNumber" htmlEscape="false" maxlength="64" class="input-xlarge  required "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付公司DXPID：</label>
			<div class="controls">
				<form:input path="companyDxpid" htmlEscape="false" maxlength="64" class="input-xlarge  required " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">海关联系人姓名：</label>
			<div class="controls"><form:input path="customName" htmlEscape="false" maxlength="200" class="input-xlarge  required " onkeyup="value=value.replace(/[\d]/g,'') " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">海关联系人电话：</label>
			<div class="controls">
				<form:input path="customPhone" htmlEscape="false" maxlength="12" class="input-xlarge required " onkeyup="value=value.replace(/[^\d\-]/g,'') " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">海关地址：</label>
			<div class="controls">
				<form:input path="customAddress" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="cbms:cbmsCustomsetting:edit"><input  class="btn btn-primary" type="button" onclick="submitform()" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>