<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品添加页</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		
	</script>
</head>
<body>
	<form:form id="loadForm"  action="${ctx}/prom/promProduct/upload" method="POST">
		<div class="controls">
			<!-- <input type="text" name="file" id="uploadId" style="width:1500px;height:1000px;"/> -->
			<!-- <label id="type_val_info" for="typeId" class="error"></label> -->
			<textarea rows="1" cols="1" name="file" id="uploadId" style="width:90%;height:500px"></textarea>
		</div>
					
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保存并提交"/>
			<input id="btnCancel" class="btn" type="button" value="取 消" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>