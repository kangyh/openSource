<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>科目管理</title>
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
	
		//选择科目级别触发的时间
		function changeSubjectLevel(obj) {
			var level = $(obj).val();
		    if(level > 1){
		    	$("#parentSubjectDiv").show();
		    	$.ajax({
		               type: "POST",
		               url: "${ctx}/subject/subject/getParentSubjects",
		               data: {"subjectLevel":level},
		               dataType: 'json',
		               success: function(data){
            		       $("#parentSubjectId").empty();
		            	   $.each(data,function(i,row){
		                        $("#parentSubjectId").append('<option value="'+row['subjectId']+'">'+row['subjectName']+'</option>');
		                    });
		               },
		               error: function(){
		                    console.log("请求失败!");
		               }
		        }); 
		    }else{
		    	$("#parentSubjectDiv").hide();
		    }
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/subject/subject/">科目列表</a></li>
		<li class="active"><a href="${ctx}/subject/subject/updateSubject?subjectId=${subject.subjectId}">科目<shiro:hasPermission name="subject:subject:edit">${not empty subject.subjectId?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="subject:subject:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="subject" action="${ctx}/subject/subject/updateSubject" method="post" class="form-horizontal">
		<form:hidden path="subjectId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">科目号：</label>
			<div class="controls">
				<form:input path="subjectCode" htmlEscape="false" maxlength="8" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">科目名称：</label>
			<div class="controls">
				<form:input path="subjectName" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">科目级别：</label>
			<div class="controls">
				<form:select path="subjectLevel" class="input-xlarge required" onchange="changeSubjectLevel(this)">
                    <form:option value="" label=""/>
                    <form:option value="1" label="一级科目"/>
                    <form:option value="2" label="二级科目"/>
                    <form:option value="3" label="三级科目"/>
                </form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="parentSubjectDiv" <c:if test="${subject.subjectLevel==1 }">style="display: none;"</c:if>>
			<label class="control-label">上级科目：</label>
			<div class="controls">
				<select class="input-xlarge required" name="parentSubjectId" id="parentSubjectId">
					<c:forEach items="${parentSubjectList}" var="parentSubject">
						<c:if test="${parentId == parentSubject.subjectId }">
							<option value="${parentSubject.subjectId }" selected="selected">${parentSubject.subjectName }</option>
						</c:if>
						<c:if test="${parentId != parentSubject.subjectId }">
							<option value="${parentSubject.subjectId }" selected="selected">${parentSubject.subjectName }</option>
						</c:if>
					</c:forEach>
				</select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否为最底层科目：</label>
			<div class="controls">
				<form:select path="isLast" class="input-xlarge required">
                    <form:option value="" label=""/>
                    <form:option value="Y" label="是"/>
                    <form:option value="N" label="否"/>
                </form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类别：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getEnumList('subjectType')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">余额方向：</label>
			<div class="controls">
				<form:select path="balanceDirection" class="input-xlarge required">
                    <form:option value="" label=""/>
                    <form:option value="D" label="借方"/>
                    <form:option value="C" label="贷方"/>
                </form:select>
                <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="subject:subject:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="修 改"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>