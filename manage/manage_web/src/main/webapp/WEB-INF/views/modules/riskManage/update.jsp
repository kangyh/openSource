<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>风控管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					
					var handleMessage=$('#handleMessage').val();
					if(handleMessage!=""){
					$('#handleMessageid').val(handleMessage);
					}
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
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
		<li><a href="#">解冻处理页面</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="riskOrderFreeze" action="${ctx}/riskManage/riskOrderFreezeQuery/xg/${riskOrderFreeze.freezeId}" method="post" class="form-horizontal">

	<div class="control-group">
		<label class="control-label">商户编码：</label>
		<div class="controls">
			<input path="merchantCode"name="merchantCode" id="merchantCode" value="${riskOrderFreeze.merchantCode}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">商户账号：</label>
		<div class="controls">
			<input path="merchantName" name="merchantName" id="merchantName" value="${riskOrderFreeze.merchantName}"  style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">冻结单号：</label>
		<div class="controls">
			<input path="freezeNo" name="freezeNo" id="freezeNo" value="${riskOrderFreeze.freezeNo}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">创建时间：</label>
		 <div class="controls">
			<fmt:formatDate value="${riskOrderFreeze.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
	</div> 
	<div class="control-group">
		<label class="control-label">交易类型：</label>
		<div class="controls">
			<input path="transType" name="transType" id="transType"  value="${riskOrderFreeze.transType}"  readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">交易订单号：</label>
		<div class="controls">
			<form:input path="transNo" name="transNo" id="transNo" value="${riskOrderFreeze.transNo}"  readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	
	<div class="control-group">
		<label class="control-label">冻结金额：</label>
		<div class="controls">
			<form:input path="transAmount" name="transAmount"  value="${riskOrderFreeze.transAmount}" pattern="￥0.0000" class="required" placeholder="金额请输入数字"/>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">交易状态：</label>
		<div class="controls">
			<input path="transStatus" name="transStatus" id="transStatus" value="${riskOrderFreeze.transStatus}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">操作人：</label>
		<div class="controls">
			<input path="createAuthor" name="createAuthor" id="createAuthor" value="${riskOrderFreeze.createAuthor}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">冻结状态：</label>
		<div class="controls">
			<input path="status" name="status" id="status" value="${riskOrderFreeze.status}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>

	
	<div class="control-group">
		<label class="control-label">冻结理由：</label>
		<div class="controls">
	    	<form:select id="freezeRemark" path="freezeRemark" name="freezeRemark" class="input-xlarge required">
			    <c:forEach items="${riskFreezeRemark}" var="riskFreezeRemarkControl">
					<form:option value="${riskFreezeRemarkControl.value}" label="${riskFreezeRemarkControl.name}"/>
				</c:forEach>
		    </form:select>
		</div>
	</div>
	
		<input id="refererid" value="${referer}" type="hidden" name="referer"/>
		<input value="${pageNo}" type="hidden" name="pageNo"/>
		<div class="form-actions">
			<shiro:hasPermission name="merchant:merchantRate:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保存并提交"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="取 消" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>