<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>调账详情</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function uploadFile(){
			var fileName = $("#file").val();
			if(fileName == null || fileName==''){
				alert("请选择要上传的图片");
				return;
			}
			
			loading('正在上传图片，请稍等...');
			$("#uploadForm").submit();
			
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/adjust/adjustAccount/">调账列表</a></li>
	</ul><br/>
	<form:form id="uploadForm" action="${ctx}/adjust/adjustAccount/uploadFile" method="post"  class="form-horizontal" enctype="multipart/form-data">
		<input type="hidden" id="adjustId" name="adjustId" value="${adjustAccount.adjustId }" />
		<div class="control-group">
			<label class="control-label">记账流水号：</label>
			<div class="controls">
				${adjustAccount.serialNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">笔数：</label>
			<div class="controls">
				${adjustAccount.serialNum}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<c:if test="${adjustAccount.status == 'AUDING'}">
					未复核
				</c:if>
				<c:if test="${adjustAccount.status == 'REVOKE'}">
					已撤销
				</c:if>
				<c:if test="${adjustAccount.status == 'ADOPT'}">
					复核通过
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">请选择要上传的图片：</label>
			<div class="controls">
				<input type="file" id="file" name="file" />
			</div>
		</div>
	</form:form>
	<br/><br/>
	<input id="uploadBtn" type="button" class="btn btn-primary" onclick="uploadFile()" value="上传" />
</body>
</html>