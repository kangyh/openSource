<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>出入金配置管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/validateNum.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
                    var flgs = validatePreventInject($("#remark").val(),"输入内容错误，请重新输入!");
                    if(!flgs){
                        $("#remark_info").text("输入有误，请先更正。");
                        $("#remark_info").show();
                        return;
                    }
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
		<li><a href="${ctx}/riskManage/settleIncomeInfo/">出入金配置列表</a></li>
		<li class="active"><a href="${ctx}/riskManage/settleIncomeInfo/form?incomeId=${settleIncomeInfo.incomeId}"><shiro:hasPermission name="riskManage:settleIncomeInfo:edit">${not empty settleIncomeInfo.incomeId?'修改':'添加'}</shiro:hasPermission>出入金配置<shiro:lacksPermission name="riskManage:settleIncomeInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="settleIncomeInfo" action="${ctx}/riskManage/settleIncomeInfo/save" method="post" class="form-horizontal">
		<form:hidden path="incomeId"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">出入金类型：</label>
			<div class="controls">
				<form:select id="incomeDirection" path="incomeDirection" class="required input-xlarge">
					<form:option value="" label="请选择"/>
					<form:option value="in" label="入金"/>
					<form:option value="out" label="出金"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务类型：</label>
			<div class="controls">
				<form:select id="businessType" path="businessType" class="required input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${bList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易类型：</label>
			<div class="controls">
				<form:select id="transType" path="transType" class="required input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${tList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品编码：</label>
			<div class="controls">
				<form:select id="productCode" path="productCode" class="input-xlarge required">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getProductList()}" itemLabel="name" itemValue="code" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否结算：</label>
			<div class="controls">
				<form:select id="settleStatus" path="settleStatus" class="required input-xlarge">
					<form:option value="" label="请选择"/>
					<form:option value="Y" label="是"/>
					<form:option value="N" label="否"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:input path="remark" id="remark" htmlEscape="false" maxlength="50" class="input-xlarge "/>
				<label id="remark_info" class="error"></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select id="status" path="status" class="required input-xlarge">
					<form:option value="" label="请选择"/>
					<form:option value="Y" label="生效"/>
					<form:option value="N" label="无效"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="riskManage:settleIncomeInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>