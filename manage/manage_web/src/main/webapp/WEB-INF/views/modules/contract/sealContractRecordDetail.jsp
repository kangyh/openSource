<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>已签约合同管理</title>
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
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/contract/sealContractRecord/">已签约合同列表</a></li>
		<li class="active"><a href="${ctx}/contract/sealContractRecord/form?id=${sealContractRecord.id}">已签约合同<shiro:lacksPermission name="contract:sealContractRecord:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sealContractRecord" action="${ctx}/contract/sealContractRecord/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">合同编码：</label>
			<div class="controls">
				${sealContractRecord.sealContractId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">合同名称：</label>
			<div class="controls">
					${sealContractRecord.sealContractName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">签章序列号：</label>
			<div class="controls">
					${sealContractRecord.electronicsSealId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">签约甲方id：</label>
			<div class="controls">
					${sealContractRecord.sealAsideId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">签约甲方：</label>
			<div class="controls">
					${sealContractRecord.sealAsideName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">签约乙方：</label>
			<div class="controls">
					${sealContractRecord.sealBsideName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">平台来源：</label>
			<div class="controls">
					${sealContractRecord.sysResource}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">签约产品：</label>
			<div class="controls">
					${sealContractRecord.sealProduct}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">合同有效期：</label>
			<div class="controls">
					${sealContractRecord.contractEffectiveTime}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">合同文件名：</label>
			<div class="controls">
					${sealContractRecord.contractFileName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">合同URL：</label>
			<div class="controls">
					${sealContractRecord.contractUrl}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">签约时间：</label>
			<div class="controls">
						<fmt:formatDate value="${sealContractRecord.sealTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
						<fmt:formatDate value="${sealContractRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">更新时间：</label>
			<div class="controls">
						<fmt:formatDate value="${sealContractRecord.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
					${sealContractRecord.remark}
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>