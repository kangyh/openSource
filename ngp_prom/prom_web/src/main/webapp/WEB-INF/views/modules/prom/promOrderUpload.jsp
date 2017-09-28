<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>审核成功后文件上传</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		
		function load(){
			var uploadId = document.getElementById("uploadId");
			
			if(uploadId.value==null || uploadId.value==""){
				$("#type_val_info").text("文件为空");
				$("#type_val_info").removeAttr("style");
				return false;
			}
			if(uploadId !=null){
				var	fileName=uploadId.value;
				
				if(fileName !=''){
					var	index=fileName.lastIndexOf('.');
					if(index !=-1){
						var name=fileName.substr(index);
						
						if(name==".xlsx" || name==".XLSX" || name==".xls"|| name==".XLS"){ 
							var fileSize=uploadId.files[0].size; 
							var maxSize=20*1024*1024;
							if(fileSize>maxSize){
								$("#type_val_info").text("上传文件过大,不能超过20M");
								$("#type_val_info").removeAttr("style");
								return false;
							}else{
								loading('正在提交，请稍等...');
								$("#loadForm").submit();
							}
						}else{
							$("#type_val_info").text("只允许上传Excel格式的文件");
							$("#type_val_info").removeAttr("style");
							return false;
						}
					}
				}else{
					
					loading('正在提交，请稍等...');
					form.submit();
				}
			}
		}
	</script>
</head>
<body>
	<form:form id="loadForm"  action="${ctx}/prom/promOrder/upload" method="POST" enctype="multipart/form-data" class="form-horizontal">
		<div class="controls">
			<input type="file" name="file" id="uploadId"/>
			<!-- <span class="info">(近30天的交易，不超过1000笔)</span> -->
			<label id="type_val_info" for="typeId" class="error"></label>
			<!-- <input id="btnClear" class="btn btn-primary" type="button" onclick="load()" value="导入"/></li> -->
		</div>
					
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="button" onclick="load()" value="保存并提交"/>
			<input id="btnCancel" class="btn" type="button" value="取 消" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>