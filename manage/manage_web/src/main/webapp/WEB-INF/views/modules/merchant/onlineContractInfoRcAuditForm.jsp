<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>线上签约管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			if($("#msg").val() != ""){
				parent.showThree();
				parent.changeThreeName($("#msg").val());
			}
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
			$("#rcAuditStatus").val("SUCCES");
		}
		function reject(){
			if($("#objection").val() == ""){
				parent.showThree();
				parent.changeThreeName("拒绝理由不能为空");
				return false;
			}
			$("#rcAuditStatus").val("AUDREJ");
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/merchant/onlineContractInfoRc?cache=1">产品签约列表</a></li>
		<li class="active"><a href="">产品签约审核</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="onlineContractInfo" action="${ctx}/merchant/onlineContractInfoRc/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<input type="hidden" id="msg" value="${msg}"/>
		<div class="control-group">
			<label class="control-label">商户编码：</label>
			<div class="controls">
					${onlineContractInfo.userId}
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">商户公司名称：</label>
			<div class="controls">
					${onlineContractInfo.merchantCompanyName}
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<fmt:formatDate value="${onlineContractInfo.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">合同有效时间：</label>
			<div class="controls">
				<fmt:formatDate value="${onlineContractInfo.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>——
				<fmt:formatDate value="${onlineContractInfo.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>


		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
					${onlineContractInfo.legalAuditStatus}
			</div>
		</div>

		<label class="control-label">本次签约产品：</label>
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
			<tr>
				<th>产品名称</th>
				<th>业务类型</th>
				<th>异步通知地址</th>
				<th>同步返回地址</th>
				<th>ip/域名</th>
				<th>其他资料</th>
				<th>操作</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${list}" var="normProduct">
				<tr>
					<td>${normProduct.productName}</td>
					<td>${normProduct.businessType}</td>
					<td>${normProduct.notifyUrl}</td>
					<td>${normProduct.backUrl}</td>
					<td>${normProduct.ipDomain}</td>
					<td>${normProduct.ipDomain}</td>
					<td>
						<a>下载资料</a>
						<a target="_Blank" href="${normProduct.originalFilePath}">查看合同</a>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>

		<div class="control-group">
			<label class="control-label">拒绝理由：</label>
			<div class="controls">
				<form:input id="objection" path="objection" htmlEscape="false" maxlength="50" class="input-xlarge"/>
			</div>
		</div>
		<input type="hidden"  id="rcAuditStatus" name="rcAuditStatus"/>
		<div class="form-actions">
			<shiro:hasPermission name="merchant:product:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" onclick="return pass();" value="通 过"/>&nbsp;</shiro:hasPermission>
			<shiro:hasPermission name="merchant:product:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" onclick="return reject();" value="拒 绝"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>