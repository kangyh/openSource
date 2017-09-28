<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>鉴权补账详情</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function uploadFile(){
			var fileName = $("#file").val();
			if(fileName == null || fileName==''){
				alert("请选择补商户账务文件");
				return;
			}
			$("#uploadForm").submit();
		}

		function uploadChannelFile(){
			var fileName = $("#channelFile").val();
			if(fileName == null || fileName==''){
				alert("请选择补通道账务文件");
				return;
			}
			$("#uploadChannelForm").submit();
		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/authSpl/toMerchantUpload/">鉴权补账</a></li>
	</ul><br/>
	<form:form id="" action="" method="post"  class="form-horizontal" enctype="multipart/form-data">
		<div class="control-group">
			<label class="control-label">鉴权补账模板：</label>
			<div class="controls">
				<a href="${ctx}/authSpl/downloadTemplate">下载</a>
			</div>
		</div>
	</form:form>
	<br/><br/>
	<form:form id="uploadForm" action="${ctx}/authSpl/uploadFile" method="post"  class="form-horizontal" enctype="multipart/form-data">
		<div class="control-group">
			<label class="control-label">补商户账务：</label>
			<div class="controls">
				<input type="file" id="file" name="file" />
				<input id="uploadBtn" type="button" class="btn btn-primary" onclick="uploadFile()" value="上传" />
			</div>
		</div>
	</form:form>

	<br/>
	<form:form id="uploadChannelForm" action="${ctx}/authSpl/uploadChannelFile" method="post"  class="form-horizontal" enctype="multipart/form-data">
		<div class="control-group">
			<label class="control-label">补通道账务：</label>
			<div class="controls">
				<input type="file" id="channelFile" name="file" />
				<input id="uploadBtn" type="button" class="btn btn-primary" onclick="uploadChannelFile()" value="上传" />
			</div>
		</div>
	</form:form>
</body>
</html>