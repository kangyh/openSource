<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>单表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			console.log($("#msg").val());
			if ($("#msg").val() != "") {
				parent.showThree();
				parent.changeThreeName($("#msg").val());
			}
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/route/download/">对账文件下载</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="downloadAccFile" action="${ctx}/route/download/down" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<input type="hidden" id="msg" value = "${msg}" />
		<div class="control-group">
			<label class="control-label">银行名称：</label>
			<div class="controls" >
				<form:select id="bankNo" path="bankNo"  class="input-medium required"  >
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('DownBank')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规则开始时间：</label>
			<div class="controls">
				<input id="downDate" name="downDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					   value="<fmt:formatDate value="${downloadAccFile.downDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'%y-%M-{%d-1}'});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="下 载"/>
		</div>
	</form:form>
</body>
</html>