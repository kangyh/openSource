<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>单表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/route/cache/">cache缓存删除</a></li>
	</ul><br/>
	<p>cache缓存删除</p>
	<form:form id="inputForm" action="${ctx}/route/cache/delete" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">key：</label>
			<div class="controls" >
				<input name="key" value="${key}" htmlEscape="false"  class="input-xlarge required" />
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>

		<div class="form-actions">
			<input class="btn btn-primary" type="submit" value="删 除"/>
		</div>
	</form:form>

	<p>用户session缓存删除</p>
	<form:form id="inputForm" action="${ctx}/route/cache/deleteSession" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">sesskey：</label>
			<div class="controls" >
				<input name="sesskey" value="${sesskey}" htmlEscape="false"  class="input-xlarge required" />
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>

		<div class="form-actions">
			<input class="btn btn-primary" type="submit" value="删 除"/>
		</div>
	</form:form>


</body>
</html>