<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>黑白名单明细上传</title>
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
	
	function checkDelete(blackItemId,blackId) {
		if( !confirm("确认要删除这条数据？") ){
	    	return false;
	    }
		loading('正在提交，请稍等...');
		$.ajax({
			url:"${ctx}/risk/riskBlackorwhite/view/deleteItem",
			type:"post",
			cache:false,
			timeout:10000,
			data:{
				"blackItemId":blackItemId,
				"blackId":blackId
			},
			success:function(msg){
				closeLoading();
				alertx(msg, closed);
				if( msg.indexOf("成功")>-1 ){
					$("#searchForm").submit();
				}
				return ;
			},
			error:function(){
				closeLoading();
				alertx("删除失败", closed);
				return ;
			}
		});
	}
	function returnQuery( ) {
		$("#searchForm2").submit();
	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="###">
				<c:forEach items="${enumMap.riskBwType}" var="wStatus">
					<c:if test="${rbwInfo.type eq wStatus.value}">
						${wStatus.name}
					</c:if>
				</c:forEach>-
				<c:forEach items="${enumMap.riskBwCategory}" var="wStatus">
					<c:if test="${rbwInfo.category eq wStatus.value}">
						${wStatus.name}
					</c:if>
				</c:forEach>-
				${rbwInfo.name}明细-上传</a></li> 
	</ul>
	<form:form id="searchForm" modelAttribute="pcacPersonReport" action="${ctx}/risk/riskBlackorwhite/view/readyUploadFile/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="blackItemValues" name="blackItemValues" type="hidden" value="${blackItemValues}"/>
		<input  name="blackId" type="hidden" value="${rbwInfo.blackId}"/>
		<input  name="status" type="hidden" value="ENABLE"/>
	</form:form>
	<sys:message content="${message}"/>
		
	
	<div style="font-size: 2em;font-family: serif;font: bold;padding-bottom: 20px;padding-top: 20px;">黑白名单明细信息:</div>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
                <th>元素值</th>
				<th>操作</th>
				
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bwItem" varStatus="index">
			<tr>
                <td>
					${index.index+1}
                </td>
				<td>
					${bwItem.blackItemValue}
				</td>
				<td>
					<a href="javascript:void(0)" onclick="checkDelete('${bwItem.blackItemId}','${bwItem.blackId}')">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
		<form id="loadForm" action="${ctx}/risk/riskBlackorwhite/view/loadModel" method="post" enctype="multipart/form-data" class="form-horizontal">
			<div class="control-group" style="margin-top:30px">
				<div class="control-left">
				<label class="control-label">黑白名单明细信息上传：</label>
					<div class="controls">
						<input type="file" name="file" id="uploadId"/>
						<span class="info"><a href="${ctxStatic}/risk/BlackWhiteItem.xls">点击下载模板</a></span>
						<!-- <span class="info">(近30天的交易，不超过1000笔)</span> -->
						<label id="type_val_info" for="typeId" class="error"></label>
						<input type="button" class="btn btn-primary"   onclick="load()" value="上传">
						<input type="button" class="btn btn-primary"   onclick="returnQuery()" value="返回">
						<input id="blackId" name="blackId" type="hidden" value="${rbwInfo.blackId}"/>
					</div>
				</div>
			</div>
		</form>
		<form:form id="searchForm2" modelAttribute="riskBlackorwhiteItem" action="${ctx}/risk/riskBlackorwhite/view/itemlist" method="post" class="breadcrumb form-search">
			<input id="pageNo" name="pageNo" type="hidden" value="1"/>
			<input id="pageSize" name="pageSize" type="hidden" value="10"/>
			<input id="searchFlag" name="searchFlag" type="hidden" value="1"/>
			<input type="hidden" name="blackId"  value="${riskBlackorwhiteItem.blackId }"/>
			<input type="hidden" name="status"  value="${riskBlackorwhiteItem.status }"/>
			<input type="hidden" name="blackItemValue"  value="${riskBlackorwhiteItem.blackItemValue }"/>
			<input type="hidden" id="beginCreTime" name="beginCreTime" value="<fmt:formatDate value="${riskBlackorwhiteItem.beginCreTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			<input type="hidden" id="endCreTime" name="endCreTime" value="<fmt:formatDate value="${riskBlackorwhiteItem.endCreTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			<input type="hidden" id="beginUpdTime" name="beginUpdTime" value="<fmt:formatDate value="${riskBlackorwhiteItem.beginUpdTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			<input type="hidden" id="endUpdTime" name="endUpdTime" value="<fmt:formatDate value="${riskBlackorwhiteItem.endUpdTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		</form:form>
</body>
</html>