<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>同申报批次号详情管理</title>
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
		<li><a href="${ctx}/cbms/cbmsOrderform/">同申报批次号详情列表</a></li>
		<li class="active"><a href="${ctx}/cbms/cbmsOrderform/form?id=${cbmsOrderform.id}">同申报批次号详情<shiro:hasPermission name="cbms:cbmsOrderform:edit">${not empty cbmsOrderform.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cbms:cbmsOrderform:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cbmsOrderform" action="${ctx}/cbms/cbmsOrderform/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">订到导入表主键：</label>
			<div class="controls">
				<form:input path="orderId" htmlEscape="false" maxlength="20" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">导入批次号：</label>
			<div class="controls">
				<form:input path="importBatchNumber" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">报送海关批次号：</label>
			<div class="controls">
				<form:input path="sentCustomsNumber" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">海关流水号：</label>
			<div class="controls">
				<form:input path="customSn" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户ID：</label>
			<div class="controls">
				<form:input path="merchantNo" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电商企业编号：</label>
			<div class="controls">
				<form:input path="busEnterpriseNo" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电商企业名称：</label>
			<div class="controls">
				<form:input path="busEnterpriseName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电商订单号：</label>
			<div class="controls">
				<form:input path="orderFormNo" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">企业报送类型：</label>
			<div class="controls">
				<form:input path="transCode" htmlEscape="false" maxlength="10" class="input-xlarge "/>
				<span class="help-inline"><font color="red">1、新增 2、变更 3、删除。 默认为：1</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">模式代码：</label>
			<div class="controls">
				<form:input path="modeCode" htmlEscape="false" maxlength="10" class="input-xlarge "/>
				<span class="help-inline"><font color="red">1、一般模式 2、保税模式。 默认为：1</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">检验检疫机构代码：</label>
			<div class="controls">
				<form:input path="inspectionQuarantineCode" htmlEscape="false" maxlength="10" class="input-xlarge "/>
				<span class="help-inline"><font color="red">参考检验检疫机构代码</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">海关编码：</label>
			<div class="controls">
				<form:select path="customCode" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品名称：</label>
			<div class="controls">
				<form:input path="commodityName" htmlEscape="false" maxlength="1000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品数量：</label>
			<div class="controls">
				<form:input path="commodityCount" htmlEscape="false" maxlength="11" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品单位：</label>
			<div class="controls">
				<form:input path="commodityUnit" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品及物流信息：</label>
			<div class="controls">
				<form:input path="goodMsg" htmlEscape="false" maxlength="640" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否为保税区货物：</label>
			<div class="controls">
				<form:input path="isRef" htmlEscape="false" maxlength="11" class="input-xlarge "/>
				<span class="help-inline"><font color="red">0、否 1、是</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人民币单价：</label>
			<div class="controls">
				<form:input path="rmbPrices" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">外币单价：</label>
			<div class="controls">
				<form:input path="foreignPrices" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单人民币金额：</label>
			<div class="controls">
				<form:input path="rmbAmount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单外币金额：</label>
			<div class="controls">
				<form:input path="foreignAmount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人民币支付金额：</label>
			<div class="controls">
				<form:input path="rmbPayAmount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续费收取金额：</label>
			<div class="controls">
				<form:input path="chargeAmount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付用途：</label>
			<div class="controls">
				<form:input path="payPurpose" htmlEscape="false" maxlength="5" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付税款：</label>
			<div class="controls">
				<form:input path="payTaxes" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付运费：</label>
			<div class="controls">
				<form:input path="freight" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">原始支付企业名称：</label>
			<div class="controls">
				<form:input path="orgPayEnterpriseName" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">原始支付流水号：</label>
			<div class="controls">
				<form:input path="orgPayTransno" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">汇元银通支付支付流水号：</label>
			<div class="controls">
				<form:input path="hyPayTransno" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付交易状态 </label>
			<div class="controls">
				<form:input path="payStatus" htmlEscape="false" maxlength="10" class="input-xlarge "/>
				<span class="help-inline"><font color="red">D、代扣(款项由消费者账户转至 第三方支付企业账户) S、实扣(款项由消费者账户转至 收款方账户) C、取消(退款)：</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">付款人名称：</label>
			<div class="controls">
				<form:input path="payName" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				<form:input path="sex" htmlEscape="false" maxlength="4" class="input-xlarge "/>
				<span class="help-inline"><font color="red">B、男 G、女</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年龄：</label>
			<div class="controls">
				<form:input path="age" htmlEscape="false" maxlength="11" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">付款时间：</label>
			<div class="controls">
				<input name="payTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${cbmsOrderform.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">付款人证件类型：</label>
			<div class="controls">
				<form:input path="payErcertificateType" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">付款人证件号：</label>
			<div class="controls">
				<form:input path="payErcertificateNo" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">付款币种ID：</label>
			<div class="controls">
				<form:input path="currencyId" htmlEscape="false" maxlength="11" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">卡种：</label>
			<div class="controls">
				<form:input path="bankCardType" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">付款人银行账号：</label>
			<div class="controls">
				<form:input path="payEraccountNo" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">付款人手机号：</label>
			<div class="controls">
				<form:input path="payerPhone" htmlEscape="false" maxlength="30" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单文件的文件名：</label>
			<div class="controls">
				<form:input path="fileName" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认时间：</label>
			<div class="controls">
				<input name="confirmTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${cbmsOrderform.confirmTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成交时间：</label>
			<div class="controls">
				<input name="tadingTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${cbmsOrderform.tadingTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实名认证时间：</label>
			<div class="controls">
				<input name="authenticationTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${cbmsOrderform.authenticationTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">申报时间：</label>
			<div class="controls">
				<input name="declarationTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${cbmsOrderform.declarationTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品分类：</label>
			<div class="controls">
				<form:input path="commodityClassify" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收货人名称：</label>
			<div class="controls">
				<form:input path="consigneeName" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收货人电话：</label>
			<div class="controls">
				<form:input path="consigneePhone" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收货人地址：</label>
			<div class="controls">
				<form:input path="consigneeAddress" htmlEscape="false" maxlength="1000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">运送方式：</label>
			<div class="controls">
				<form:input path="deliveryType" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">运单号：</label>
			<div class="controls">
				<form:input path="wayBillno" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">物流公司：</label>
			<div class="controls">
				<form:input path="logisticsCompany" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">录入时间：</label>
			<div class="controls">
				<input name="createdTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${cbmsOrderform.createdTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">修改时间：</label>
			<div class="controls">
				<input name="updateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${cbmsOrderform.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务状态：</label>
			<div class="controls">
				<form:input path="serviceStatus" htmlEscape="false" maxlength="4" class="input-xlarge "/>
				<span class="help-inline"><font color="red">1、暂存 2、申报 。默认为2。</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订阅状态：</label>
			<div class="controls">
				<form:input path="subscriptionStatus" htmlEscape="false" maxlength="10" class="input-xlarge "/>
				<span class="help-inline"><font color="red">用户订阅单证业务状态的信息 ALL-订阅数据和回执 DATA-只订阅数据 RET- 只订阅回执 默认：ALL</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订阅方传输模式：</label>
			<div class="controls">
				<form:input path="subTransMode" htmlEscape="false" maxlength="10" class="input-xlarge "/>
				<span class="help-inline"><font color="red">默认为DXP；指中国电子口岸数据交换平台</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:input path="note" htmlEscape="false" maxlength="1000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附言：</label>
			<div class="controls">
				<form:input path="postScript" htmlEscape="false" maxlength="1000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标识：</label>
			<div class="controls">
				<form:input path="identification" htmlEscape="false" maxlength="2" class="input-xlarge "/>
				<span class="help-inline"><font color="red">0、新增申报 1、修改申报</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单状态：</label>
			<div class="controls">
				<form:input path="orderStatus" htmlEscape="false" maxlength="4" class="input-xlarge "/>
				<span class="help-inline"><font color="red">1、确认，待审核 2、审核通过、待报关 3、已报关</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注A：</label>
			<div class="controls">
				<form:input path="notea" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注B：</label>
			<div class="controls">
				<form:input path="noteb" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注c：</label>
			<div class="controls">
				<form:input path="notec" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="cbms:cbmsOrderform:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>