<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>个人商户风险信息上传</title>
<meta name="decorator" content="default"/>
<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
<script type="text/javascript">
	
	
	$(document).ready(function() {
		
	});
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
    	return false;
    }
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
	<ul class="nav nav-tabs">
		<li class="active"><a>个人商户风险信息上传</a></li> 
	</ul>
	<form:form id="searchForm" modelAttribute="pcacPersonReport" action="${ctx}/pcac/pcacPersonReport/view/readyUploadFile/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="reportIds" name="reportIds" type="hidden" value="${reportIds}"/>
	</form:form>
	<sys:message content="${message}"/>
		
	
	<div style="font-size: 2em;font-family: serif;font: bold;padding-bottom: 20px;padding-top: 20px;">个人商户风险信息:</div>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
                <th>商户名称</th>
				<th>证件类型</th>
				<th>证件号码</th>
				<th>商户代码</th>
				<th>操作</th>
				
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="ppReportInfo" varStatus="index">
			<tr>
                <td>
					${index.index+1}
                </td>
				<td>
					${ppReportInfo.regName}
				</a></td>
				<c:forEach items="${enumMap.legDocType}" var="wStatus">
					<c:if test="${ppReportInfo.docType eq wStatus.value}">
						<td>${wStatus.name}</td>
					</c:if>
				</c:forEach>
				<td>
					${ppReportInfo.docCode}
				</td>
				<td>
					${ppReportInfo.cusCode}
				</td>
				<td>
					<a href="${ctx}/pcac/pcacPersonReport/view/viewDetail?personReportId=${ppReportInfo.personReportId}&type=view&fromWhere=upload&param=hidden">详情</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
		<form id="loadForm" action="${ctx}/pcac/pcacPersonReport/view/loadModel" method="post" enctype="multipart/form-data" class="form-horizontal">
			<div class="control-group" style="margin-top:30px">
				<div class="control-left">
				<label class="control-label">个人商户风险信息上传：</label>
					<div class="controls">
						<input type="file" name="file" id="uploadId"/>
						<span class="info"><a href="${ctxStatic}/pcac/pcacPersonReport.xls">点击下载模板</a></span>
						<!-- <span class="info">(近30天的交易，不超过1000笔)</span> -->
						<label id="type_val_info" for="typeId" class="error"></label>
						<input type="button" class="btn btn-primary"   onclick="load()" value="上传">
					</div>
				</div>
			</div>
		</form>
	
	<!-- <div class="form-actions">		
					<input id="btnSubmit" class="btn btn-primary" type="submit" onclick="save()" value="保存"/> 
	</div> -->
	
</body>
</html>