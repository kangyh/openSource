<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>账户转账管理</title>
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

		var validateFrom = {

		}
		function onRevoke(){
			$("#auditRemark").removeClass("required");
			$("#status").val("REVOKE");
			$("#inputForm").submit();
		}
		function onSubmit(){
			$("#auditRemark").removeClass("required");
			$("#status").val("ADOPT");
			$("#inputForm").submit();
		}
		function onRefuse(){
			if(!$("#auditRemark").hasClass("required")){
				$("#auditRemark").addClass("required");
			}
			$("#status").val("REFUSE");
			$("#inputForm").submit();
		}
	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li><a href="${ctx}/account/merchantTransfer/">商户转账列表</a></li>
	<li class="active"><a href="javascript:void(0)">商户转账详情查看</a></li>
</ul><br/>
<form:form id="inputForm" action="${ctx}/account/merchantTransfer/adjust" method="post" class="form-horizontal breadcrumb form-search">
	<%--<form:hidden path="transferId"/>--%>
	<sys:message content="${message}"/>
	<div class="control-group">
		<label class="control-label">交易号：</label>
		<div class="controls">
				${merchantTransfer.transferId}
			<input type="hidden" name="transferId" value="${merchantTransfer.transferId}">
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">申请来源：</label>
		<div class="controls">
				${fns:getDictLabel(merchantTransfer.opResource, 'AllowSystemType', '')}
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">转出方商户编码：</label>
		<div class="controls">
				${merchantTransfer.dMerchantId}
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">转出方账户：</label>
		<div class="controls">
				${merchantTransfer.dAccountId}
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">转出方企业名称：</label>
		<div class="controls">
				${merchantTransfer.dAccountName}
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">转入方商户编码：</label>
		<div class="controls">
				${merchantTransfer.cMerchantId}
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">转入方账户：</label>
		<div class="controls">
				${merchantTransfer.cAccountId}
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">转入方企业名称：</label>
		<div class="controls">
				${merchantTransfer.cAccountName}
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">转账金额：</label>
		<div class="controls">
			<fmt:formatNumber value="${merchantTransfer.transferAmount}" pattern="￥#,##0.00" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">发起人：</label>
		<div class="controls">
				${merchantTransfer.creator}
					<input type="hidden" name="creator" value="${merchantTransfer.creator}">
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">发起时间：</label>
		<div class="controls">
			<fmt:formatDate value="${merchantTransfer.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
		</div>
	</div>
	<c:if test="${'AUDING' ne merchantTransfer.status}">
		<div class="control-group">
			<label class="control-label">审核人：</label>
			<div class="controls">
					${merchantTransfer.auditor}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审核时间：</label>
			<div class="controls">
				<fmt:formatDate value="${merchantTransfer.auditTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
	</c:if>
	<div class="control-group">
		<label class="control-label">审核状态：</label>
		<div class="controls">
			<c:if test="${'AUDING' eq merchantTransfer.status}">未审核</c:if>
			<c:if test="${'REVOKE' eq merchantTransfer.status}">已撤销</c:if>
			<c:if test="${'REFUSE' eq merchantTransfer.status}">审核拒绝</c:if>
			<c:if test="${'ADOPT' eq merchantTransfer.status}">审核通过</c:if>
		</div>
	</div>
	<c:if test="${'REFUSE' eq merchantTransfer.status}">
		<div class="control-group">
			<label class="control-label">拒绝理由：</label>
			<div class="controls">
					${merchantTransfer.auditRemark}
			</div>
		</div>
	</c:if>
	<c:if test="${not empty adjust}">
		<div class="control-group">
			<label class="control-label">拒绝原因：</label>
			<div class="controls">
				<textarea name="auditRemark" id="auditRemark" htmlEscape="false" class="input-xlarge required"></textarea>
			</div>
		</div>
		<div class="control-group">
			<div class="controls">
				<input name="status" type="hidden" id="status" value="">
				<ul class="ul-form">
					<c:if test="${merchantTransfer.status ne 'REVOKE' && merchantTransfer.status ne 'ADOPT'}">
						<li class="btns"><input id="btnSubmit1" class="btn btn-primary" type="button" onclick="onRevoke()" value="撤销申请"/></li>
					</c:if>
					<c:if test="${merchantTransfer.status eq 'AUDING'}">
						<li class="btns"><input id="btnSubmit2" class="btn btn-primary" type="button" onclick="onSubmit()" value="审核通过"/></li>
						<li class="btns"><input id="btnSubmit3" class="btn btn-primary" type="button" onclick="onRefuse()" value="审核拒接"/></li>
					</c:if>
				</ul>
			</div>

		</div>
	</c:if>
	<div class="form-actions">
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</form:form>
</body>
</html>