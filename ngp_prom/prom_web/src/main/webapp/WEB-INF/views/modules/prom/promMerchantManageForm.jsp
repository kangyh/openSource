<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商户管理</title>
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
		<li><a href="${ctx}/prom/promMerchantManage/">商户列表</a></li>
		<li class="active"><a href="${ctx}/prom/promMerchantManage/form?merchantId=${promMerchantManage.merchantId}">商户<shiro:hasPermission name="prom:promMerchantManage:edit">${not empty promMerchantManage.merchantId?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="prom:promMerchantManage:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="promMerchantManage" action="${ctx}/prom/promMerchantManage/save" method="post" class="form-horizontal">
		<form:hidden path="merchantId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
            <div class="control-left">
                <label class="control-label">商户名称：</label>
                <div class="controls">
                    <form:input path="merchantName" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
            <div class="control-right">
                <div class="control-left">
                    <label class="control-label">商户性质：</label>
                    <div class="controls">
                        <form:select id="merchantType" path="merchantType" class="input-xlarge required">
                            <c:forEach items="${merchantTypeList}" var="wStatus">
                                <form:option value="${wStatus.value}" label="${wStatus.name}"/>
                            </c:forEach>
                        </form:select>
                        <span class="help-inline"><font color="red">*</font> </span>
                    </div>
                </div>
            </div>
		</div>
        <div class="control-group">
            <div class="control-left">
                <label class="control-label">商户来源：</label>
                <div class="controls">
                    <form:select id="merSource" path="merSource" class="input-xlarge required">
                        <c:forEach items="${sourceTypeList}" var="wStatus">
                            <form:option value="${wStatus.value}" label="${wStatus.name}"/>
                        </c:forEach>
                    </form:select>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
            <div class="control-right">
                <div class="control-left">
                    <label class="control-label">付款方式：</label>
                    <div class="controls">
                        <form:select id="payType" path="payType" class="input-xlarge required">
                            <c:forEach items="${payTypeList}" var="wStatus">
                                <form:option value="${wStatus.value}" label="${wStatus.name}"/>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>
            </div>
        </div>
		<div class="control-group">
            <div class="control-left">
                <label class="control-label">微信号：</label>
                <div class="controls">
                    <form:input path="wechatNo" htmlEscape="false" maxlength="50" class="input-xlarge "/>
                </div>
            </div>
            <div class="control-right">
                <div class="control-left">
                    <label class="control-label">支付宝账号：</label>
                    <div class="controls">
                        <form:input path="payAccountNo" htmlEscape="false" maxlength="50" class="input-xlarge "/>
                    </div>
                </div>
            </div>
		</div>
		<div class="control-group">
            <div class="control-left">
                <label class="control-label">联系人：</label>
                <div class="controls">
                    <form:input path="contact" htmlEscape="false" maxlength="20" class="input-xlarge "/>
                </div>
            </div>
            <div class="control-right">
                <div class="control-left">
                    <label class="control-label">联系电话：</label>
                    <div class="controls">
                        <form:input path="telPhone" htmlEscape="false" maxlength="20" class="input-xlarge "/>
                    </div>
                </div>
            </div>
		</div>
		<div class="control-group">
            <div class="control-left">
                <label class="control-label">联系邮箱：</label>
                <div class="controls">
                    <form:input path="email" htmlEscape="false" maxlength="50" class="input-xlarge "/>
                </div>
            </div>
            <div class="control-right">
                <div class="control-left">
                    <label class="control-label">地址：</label>
                    <div class="controls">
                        <form:input path="address" htmlEscape="false" maxlength="200" class="input-xlarge "/>
                    </div>
                </div>
            </div>
		</div>
		<div class="control-group">
            <div class="control-left">
                <label class="control-label">社会统一信用代码：</label>
                <div class="controls">
                    <form:input path="organizationCode" htmlEscape="false" maxlength="100" class="input-xlarge "/>
                </div>
            </div>
            <div class="control-right">
                <div class="control-left">
                    <label class="control-label">法定代表人：</label>
                    <div class="controls">
                        <form:input path="legal" htmlEscape="false" maxlength="20" class="input-xlarge "/>
                    </div>
                </div>
            </div>
		</div>
		<div class="control-group">
            <div class="control-left">
                <label class="control-label">法人身份证号：</label>
                <div class="controls">
                    <form:input path="legalIdcard" htmlEscape="false" maxlength="20" class="input-xlarge "/>
                </div>
            </div>
            <div class="control-right">
                <div class="control-left">
                    <label class="control-label">营业执照：</label>
                    <div class="controls">
                        <form:input path="businessLicenseNo" htmlEscape="false" maxlength="100" class="input-xlarge "/>
                    </div>
                </div>
            </div>
		</div>
		<div class="control-group">
            <div class="control-left">
                <label class="control-label">推荐人：</label>
                <div class="controls">
                    <form:input path="referees" htmlEscape="false" maxlength="20" class="input-xlarge "/>
                </div>
            </div>
            <div class="control-right">
                <div class="control-left">
                    <label class="control-label">商户等级：</label>
                    <div class="controls">
                        <form:select id="level" path="level" class="input-xlarge required">
                            <form:option value="1" label="1级"/>
                            <form:option value="2" label="2级"/>
                            <form:option value="3" label="3级"/>
                        </form:select>
                    </div>
                </div>
            </div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
                <form:select id="status" path="status" class="input-xlarge required">
                    <c:forEach items="${status}" var="wStatus">
                        <form:option value="${wStatus.value}" label="${wStatus.name}"/>
                    </c:forEach>
                </form:select>
                <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="prom:promMerchantManage:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>