<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>补账务明细列表</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/jquery-ztree/3.5.12/css/demo.css" rel="stylesheet" type="text/css"/>
	<link href="${ctxStatic}/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script language="JavaScript" src="${ctxStatic}/common/validateNum.js"></script>
	<script src="${ctxStatic}/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		//验证搜索条件
		var validateFrom = {
			validate: function(form){
//				var accountId = $("#accountId").val();
//				var result = validateNum(accountId,"账号请输入数字!");
//				var accountName = $("#accountName").val();
//				var accountNameResult = validatePreventInject(accountName,"账号名称输入不合法!");
//				var transNo = $("#transNo").val();
//				var transNoResult = validateNum(transNo,"交易订单号请输入数字!");
//				var logId = $("#logId").val();
//				var logIdResult = validateNum(logId,"账务流水号请输入数字!");
//				var paymentId = $("#paymentId").val();
//				var paymentIdResult = validateNum(paymentId,"支付单号请输入数字!");
//				if(!result || !accountNameResult || !transNoResult || !logIdResult || !paymentIdResult){
//					return;
//				}
				form.submit();
			}
		}
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//搜索
		function onSubmit(){
			validateFrom.validate($("#searchForm"));
		}
		//清空
		function onClear(){
			$("#searchForm").find("input[type='text']").val("");
		}
		function splDetail(){
			var iSettleId = $("#iSettleId").val();
			var iMerchantId = $("#iMerchantId").val();
			window.location.href="${ctx}/payment/merchantLog/executeSplMerchantLogDetail?iSettleId="+iSettleId+"&iMerchantId="+iMerchantId;
		}
	</script>
	<style>
	.table td{
		font-size:14px;
		font-family:"微软";
	}
	</style>
</head>
<body>
	<div id="menuContent" class="menuContent" style="display: none; position: absolute;background:#F5F5F5";>  
		<ul id="subjectTree" class="ztree" style="margin-top:0; width:220px; height: 300px;"></ul>
    </div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/merchantLog/splMerchantLogDetailList">补账务明细列表</a></li>
		<%--<shiro:hasPermission name="payment:merchantLog:edit"><li><a href="${ctx}/payment/merchantLog/form">交易账务查询添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="merchantLog" action="${ctx}/payment/merchantLog/splMerchantLogDetailList" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<%--<li><label>商户编码：</label>--%>
				<%--<form:input path="merchantId" htmlEscape="false" maxlength="20" class="input-medium"/>--%>
			<%--</li>--%>
			<li><label style="">结算单号：</label>
				<form:input path="settleId" htmlEscape="false" maxlength="26" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="clearfix"></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户编码</th>
				<th>交易类型</th>
				<th>结算单号</th>
				<th>明细笔数</th>
				<th>结算金额</th>
				<th>记账标识</th>
				<th>创建时间</th>
				<shiro:hasPermission name="payment:splMerchantLogDetail:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:if test="${not empty splmerchantLogDetail}">
			<tr>
				<td>${splmerchantLogDetail.merchantId}</td>
				<td>${fns:getDictLabel(splmerchantLogDetail.type, 'TransType', '')}</td>
				<td>${splmerchantLogDetail.settleId}</td>
				<td>${splmerchantLogDetail.payNum}</td>
				<td><fmt:formatNumber value="${splmerchantLogDetail.balanceAmountChanges}" pattern="￥#,##0.00" /></td>
				<td>
					<c:choose>
						<c:when test="${not empty splmerchantLogDetail.accountMark}">
							${fns:getDictLabel(splmerchantLogDetail.accountMark, 'AccountMark', '')}
						</c:when>
						<c:otherwise>-</c:otherwise>
					</c:choose>
				</td>
				<td><fmt:formatDate value="${splmerchantLogDetail.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<shiro:hasPermission name="payment:executeSplMerchantLogDetail:edit">
					<%--${ctx}/payment/merchantLog/splMerchantLogDetail?settleId=${splmerchantLogDetail.settleId}--%>
					<td>
						<c:if test="${settleStatus eq false}">
							<input id="iSettleId" name="iSettleId" type="hidden" value="${splmerchantLogDetail.settleId}"/>
							<input id="iMerchantId" name="iMerchantId" type="hidden" value="${splmerchantLogDetail.merchantId}"/>
							<input id="iPayNum" name="iPayNum" type="hidden" value="${splmerchantLogDetail.payNum}"/>
							<a onclick="splDetail()"  href="javascript:void(0)">补明细</a>
						</c:if>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:if>
		</tbody>
	</table>
</body>
</html>