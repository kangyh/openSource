<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品管理</title>
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
		function pass(){
			$("#auditStatus").val("SUCCES");
		}
		function reject(){
			if($("#objection").val() == ""){
				parent.showThree();
				parent.changeThreeName("拒绝理由不能为空");
				return false;
			}
			$("#auditStatus").val("AUDREJ");
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/merchant/product?cache=1">产品列表</a></li>
		<%--<li class="active"><a href="${ctx}/merchant/product/form?id=${product.id}">产品<shiro:hasPermission name="merchant:product:edit">${not empty product.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="merchant:product:edit">查看</shiro:lacksPermission></a></li>--%>
		<li class="active"><a href="${ctx}/merchant/product/audit?id=${product.id}">产品审核</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="product" action="${ctx}/merchant/product/auditStatus" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">产品编码：</label>
			<div class="controls">
				${product.code}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品名称：</label>
			<div class="controls">
				${product.name}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				${product.remark}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建人：</label>
			<div class="controls">
				${product.createName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<fmt:formatDate value="${product.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">拒绝理由：</label>
			<div class="controls">
				<form:input id="objection" path="objection" htmlEscape="false" maxlength="50" class="input-xlarge"/>
			</div>
		</div>
		
		<input type="hidden"  id="auditStatus" name="auditStatus" />
		
		<div class="form-actions">
			<shiro:hasPermission name="merchant:product:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" onclick="return pass();" value="通 过"/>&nbsp;</shiro:hasPermission>
			<shiro:hasPermission name="merchant:product:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" onclick="return reject();" value="拒 绝"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>