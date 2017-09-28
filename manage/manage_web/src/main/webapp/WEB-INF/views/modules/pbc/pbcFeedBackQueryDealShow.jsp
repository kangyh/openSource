<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>电信诈骗风险管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	
	//文件导出
	function onExport(id){
		validateFrom.validate($("#searchForm"));
		$("#searchForm").attr("action",actionURL);
	    $.post($("#search_id").attr("href")+"export",{
	    	queryInfoId:id
	    },function(data){
	    	
	    },"text");
	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a id="search_id" href="${ctx}/pbc/pbcFeedBackQueryDealQuery">电信诈骗交易处理列表</a></li>
		<li class="active"><a>详情</a></li>
	</ul>
	<br />
	<form:form id="inputForm" action="" method="post" class="form-horizontal">
	<input id="hidden" name="hidden"  type="hidden" value="${hidden}"/>
		<!-- 头部开始 -->
		<div class="controls">
			<div style="font-size: 2em;font-family: serif;font: bold;padding-bottom: 20px;"><%=request.getAttribute("title") %></div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">交易类型编号：</label>
				<div class="controls">
					<input value="${pbcQueryInfo.transTypeCode}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">发送机构编号：</label>
				<div class="controls">
					<input value="${pbcQueryInfo.messageFromCode}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-right">
				<label class="control-label">传输报文流水号：</label>
				<div class="controls">
					<input value="${pbcQueryInfo.transSerialNumber}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">业务申请编号：</label>
				<div class="controls">
					<input value="${pbcQueryInfo.applicationId}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-right">
				<label class="control-label">案件编号：</label>
				<div class="controls">
					<input value="${pbcQueryInfo.caseNumber}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">案件类型：</label>
				<div class="controls" style="width: 205px;">
					<input value="${pbcQueryInfo.caseType}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">查询支付机构编号：</label>
				<div class="controls" style="width: 205px;">
					<input value="${pbcQueryInfo.onlinePayCompanyId}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">查询支付机构名称：</label>
				<div class="controls" style="width: 205px;">
					<input value="${pbcQueryInfo.onlinePayCompanyName}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<!-- 头部结束 -->
		
		
		<!-- 信息内容开始 -->
		<%-- 账户交易明细查询  --%>
		<c:if test="${pbcFeedBack.requestEventType == 'ACCOUNTRANSDETAIL'}">
			<div class="control-group">
				<div class="control-left">
					<label class="control-label">明细查询传入参数的类型：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcTransDetailQuery.dataType}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
					</div>
				</div>
				<div class="control-right">
					<label class="control-label">明细查询操作的传入参数：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcTransDetailQuery.data}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
					</div>
				</div>
			</div> 
			<div class="control-group">
				<div class="control-left">
					<label class="control-label">查询类型：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcTransDetailQuery.inquiryMode}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
					</div>
				</div>
				<div class="control-right">
					<label class="control-label">支付帐号明细查询类别：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcTransDetailQuery.paymentAccountDetailType}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
					</div>
				</div>
			</div> 
			<div class="control-group">
				<div class="control-left">
					<label class="control-label">交易流水查询起始时间：</label>
					<div class="controls" style="width: 205px;">
						<fmt:formatDate value="${pbcTransDetailQuery.transStartTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
						<%-- <input value="${pbcTransDetailQuery.transStartTime}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" /> --%>
					</div>
				</div>
				<div class="control-right">
					<label class="control-label">交易流水查询截止日期：</label>
					<div class="controls" style="width: 205px;">
						<fmt:formatDate value="${pbcTransDetailQuery.transEndTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
						<%-- <input value="${pbcTransDetailQuery.transEndTime}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" /> --%>
					</div>
				</div>
			</div>
		</c:if>
		
		<%-- 账户主体详情查询   --%>
		<c:if test="${pbcFeedBack.requestEventType == 'ACCOUNTSUBJECTDETAIL'}">
			<div class="control-group">
				<div class="control-left">
					<label class="control-label">明细查询传入参数的类型：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcQueryInfo.dataType}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
					</div>
				</div>
				<div class="control-right">
					<label class="control-label">明细查询操作的传入参数：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcQueryInfo.data}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
					</div>
				</div>
			</div> 
		</c:if>
		
		<%-- 账户动态查询  --%>
		<c:if test="${pbcFeedBack.requestEventType == 'ACCOUNTDYNAMIC'}">
			<div class="control-group">
				<div class="control-left">
					<label class="control-label">动态查询传入参数的类型：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcAccountDynamicQuery.dataType}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
					</div>
				</div>
			</div>
			<div class="control-group">
				<div class="control-left">
					<label class="control-label">支付账户类型：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcAccountDynamicQuery.subjectType}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
					</div>
				</div>
				<div class="control-right">
					<label class="control-label">支付账号：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcAccountDynamicQuery.accountNumber}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
					</div>
				</div>
			</div>
			<div class="control-group">
				<div class="control-left">
					<label class="control-label">支付账户主体的证件类型：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcAccountDynamicQuery.idType}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
					</div>
				</div>
				<div class="control-left">
					<label class="control-label">支付账户主体的证件号码：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcAccountDynamicQuery.idNumber}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
					</div>
				</div>
			</div>
			<div class="control-group">
				<div class="control-left">
					<label class="control-label">执行起始日期：</label>
					<div class="controls" style="width: 205px;">
						<fmt:formatDate value="${pbcAccountDynamicQuery.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
						<%-- <input value="${pbcAccountDynamicQuery.accountName}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" /> --%>
					</div>
				</div>
				<div class="control-left">
					<label class="control-label">执行截止日期：</label>
					<div class="controls" style="width: 205px;">
						<fmt:formatDate value="${pbcAccountDynamicQuery.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
						<%-- <input value="${pbcAccountDynamicQuery.accountName}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" /> --%>
					</div>
				</div>
			</div>
		</c:if>
		
		<%-- 账户动态查询解除   --%>
		<c:if test="${pbcFeedBack.requestEventType == 'ACCOUNTDYNAMICREMOVE'}">
			<div class="control-group">
				<div class="control-left">
					<label class="control-label">原动态查询申请编号：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcQueryInfo.originalApplicationId}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
					</div>
				</div>
			</div>
		</c:if>
		
		<%-- 关联全支付账号查询  --%>
		<c:if test="${pbcFeedBack.requestEventType == 'PAYMENYACCOUNT'}">
			<div class="control-group">
				<div class="control-left">
					<label class="control-label">明细查询传入参数的类型：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcPaymentAccountQuery.dataType}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
					</div>
				</div>
				<div class="control-right">
					<label class="control-label">明细查询操作的传入参数：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcPaymentAccountQuery.data}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
					</div>
				</div>
			</div>
			<div class="control-group">
				<div class="control-left">
					<label class="control-label">支付帐号明细查询类别：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcPaymentAccountQuery.subjectType}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
					</div>
				</div>
				<div class="control-right">
					<label class="control-label">开户主体姓名或商户名：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcPaymentAccountQuery.accountOwnerName}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
					</div>
				</div>
			</div>
			<div class="control-group">
				<div class="control-left">
					<label class="control-label">开户主体证件类型：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcPaymentAccountQuery.accountOwnerIdType}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
					</div>
				</div>
			</div>
		</c:if>
		
		<%-- 按交易流水号查询银行卡/支付帐号  --%>
		<c:if test="${pbcFeedBack.requestEventType == 'TRANSNOACCOUNT'}">
			<div class="control-group">
				<div class="control-left">
					<label class="control-label">查询传入参数的类型：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcTransSerialCardPaymentAccountQuery.dataType}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
					</div>
				</div>
				<div class="control-right">
					<label class="control-label">查询操作的传入参数：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcTransSerialCardPaymentAccountQuery.data}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
					</div>
				</div>
			</div>
			<div class="control-group">
				<div class="control-left">
					<label class="control-label">银行机构编码：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcTransSerialCardPaymentAccountQuery.bankId}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
					</div>
				</div>
				<div class="control-right">
					<label class="control-label">交易时间：</label>
					<div class="controls" style="width: 205px;">
						<fmt:formatDate value="${pbcTransSerialCardPaymentAccountQuery.transTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
						<%-- <input value="${pbcTransSerialCardPaymentAccountQuery.transTime}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" /> --%>
					</div>
				</div>
			</div>
		</c:if>
		<!-- 信息内容结束 -->
		
		
		<!-- 尾部开始 -->
		<div class="control-group">
			<div class="control-right">
				<label class="control-label">查询事由：</label>
				<div class="controls" style="width: 205px;">
					<input value="${pbcQueryInfo.reason}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">查询说明：</label>
				<div class="controls" style="width: 205px;">
					<input value="${pbcQueryInfo.remark}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">申请时间：</label>
				<div class="controls" style="width: 205px;">
					<fmt:formatDate value="${pbcQueryInfo.applicationTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
					<%-- <input value="${pbcQueryInfo.applicationTime}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" /> --%>
				</div>
			</div>
			<div class="control-left">
				<label class="control-label">申请机构编码：</label>
				<div class="controls" style="width: 205px;">
					<input value="${pbcQueryInfo.applicationOrgId}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-right">
				<label class="control-label">申请机构名称：</label>
				<div class="controls" style="width: 205px;">
					<input value="${pbcQueryInfo.applicationOrgName}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-left">
				<label class="control-label">经办人证件类型：</label>
				<div class="controls" style="width: 205px;">
					<input value="${pbcQueryInfo.operatorIdType}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-right">
				<label class="control-label">经办人证件号：</label>
				<div class="controls" style="width: 205px;">
					<input value="${pbcQueryInfo.operatorIdNumber}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-left">
				<label class="control-label">经办人姓名：</label>
				<div class="controls" style="width: 205px;">
					<input value="${pbcQueryInfo.operatorName}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-right">
				<label class="control-label">经办人电话：</label>
				<div class="controls" style="width: 205px;">
					<input value="${pbcQueryInfo.operatorPhoneNumber}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-left">
				<label class="control-label">协查人证件类型：</label>
				<div class="controls" style="width: 205px;">
					<input value="${pbcQueryInfo.investigatorIdType}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-right">
				<label class="control-label">协查人证件号：</label>
				<div class="controls" style="width: 205px;">
					<input value="${pbcQueryInfo.investigatorIdNumber}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-left">
				<label class="control-label">协查人姓名：</label>
				<div class="controls" style="width: 205px;">
					<input value="${pbcQueryInfo.investigatorName}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group" id="aaa">
			<div class="control-right">
				<a onclick="onExport(${pbcQueryInfo.queryInfoId})">查看附件</a>
			</div>
		</div>
		<!-- 尾部结束 -->
		<div class="form-actions">
			<input id="btnCancel" class="btn btn-primary" type="button" value="返 回" onclick="history.go(-1)" />
		</div>
	</form:form>
</body>
</html>