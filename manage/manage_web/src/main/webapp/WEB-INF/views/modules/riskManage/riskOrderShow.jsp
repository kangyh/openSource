<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>风险订单管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		var quotaItem = $("#quotaItem").val();
		if(quotaItem=="1"){
			$("#perItem").css("color","red");
		}else if(quotaItem=="2"){
			$("#perDay").css("color","red");
		}else if(quotaItem=="3"){
			$("#perMonth").css("color","red");
		}
	});
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/riskManage/riskOrderQuery/">风险订单列表</a></li>
		<li class="active"><a>详情</a></li>
	</ul>
	<br />
	<form:form id="inputForm" action="" method="post" class="form-horizontal">
		<div style="font-size: 2em;font-family: serif;font: bold;padding-bottom: 20px;">命中规则:</div>
		<input type="hidden" name="quotaItem" value="${riskOrder.quotaItem}" id="quotaItem"/>
		
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>规则编码</th>
			<th>规则名称</th>
			<th>规则状态</th>
			<th>规则优先级</th>
			<th>处理方式</th>
			<th>创建时间</th>
			<th>创建人</th>
			<th>更新时间</th>
			<th>更新人</th>
			<th>原因</th>
		</tr>
		</thead>
		<tbody>
			<tr>
				<td>${riskRuleDetail.detailCode}</td>
				<td>${riskRuleDetail.detailName}</td>
				<td>${riskRuleDetail.status}</td>
				<td>${riskRuleDetail.level}级</td>
				<td>${riskRule.handleType}</td>
				<td>
					<fmt:formatDate value="${riskRuleDetail.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>${riskRuleDetail.createAuthor}</td>
				<td>
					<fmt:formatDate value="${riskRuleDetail.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>${riskRuleDetail.updateAuthor}</td>
				<td>
					<c:if test="${riskRuleDetail.detailCode eq '10001' or riskRuleDetail.detailCode eq '10002' }">
						<a href="${ctx}/riskManage/riskOrderQuery/search?flg=true&riskId=${riskOrder.riskId}">${riskOrder.quotaItem}</a>
					</c:if>
					<c:if test="${riskRuleDetail.detailCode ne '10001' and riskRuleDetail.detailCode ne '10002' }">
						${riskOrder.quotaItem}
					</c:if>
				</td>
			</tr>
		</tbody>
	</table>
		
		<div style="font-size: 2em;font-family: serif;font: bold;padding-bottom: 20px;padding-top: 20px;">风险订单详情:</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">商户编号：</label>
				<div class="controls">
					<input value="${riskOrder.merchantId}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">商户公司名称：</label>
				<div class="controls">
					<input value="${riskOrder.merchantName}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">商户订单号：</label>
				<div class="controls">
					<input value="${riskOrder.merchantOrderNo}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-right">
				<label class="control-label">产品编码：</label>
				<div class="controls">
					<input value="${riskOrder.productCode}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">产品名称：</label>
				<div class="controls">
					<input value="${riskOrder.productName}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-left">
				<label class="control-label">交易类型：</label>
				<div class="controls">
					<input value="${riskOrder.transType}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-right">
				<label class="control-label">交易订单号：</label>
				<div class="controls" style="width: 205px;">
					<input value="${riskOrder.transNo}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">支付单号：</label>
				<div class="controls">
					<input value="${riskOrder.paymentId}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-left">
				<label class="control-label">交易金额：</label>
				<div class="controls" style="width: 205px;">
					<fmt:formatNumber value="${riskOrder.transAmount}" pattern="￥0.0000" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">风险订单处理方式：</label>
				<div class="controls" style="width: 205px;">
					<input value="${riskOrder.orderDealwith}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">风险订单状态：</label>
				<div class="controls" style="width: 205px;">
					<input value="${riskOrder.orderStatus}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn btn-primary" type="button" value="返 回" onclick="history.go(-1)" />
		</div>
	</form:form>
</body>
</html>