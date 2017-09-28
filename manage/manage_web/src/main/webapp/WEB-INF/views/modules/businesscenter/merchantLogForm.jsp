<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>账户明细查询管理</title>
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
	<style>
	.control-group{
	 	width:40%;
	 	float:left;
	 }
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/business/merchantLog/getMerchantLog">交易账务列表</a></li>
		<li class="active"><a href="javascript:void(0)">交易账务详情查看</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="merchantLog" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">账号：</label>
			<div class="controls">
				${merchantLog.accountId }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">账务流水号：</label>
			<div class="controls">
				${merchantLog.logId }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">账户名称：</label>
			<div class="controls">
				${merchantLog.accountName }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户账号：</label>
			<div class="controls">
				${merchantLog.merchantId }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户名称：</label>
			<div class="controls">
				<c:choose>
					<c:when test="${not empty merchantLog.merchantName}">
						${merchantLog.merchantName }
					</c:when>
					<c:otherwise>-</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">账户类型：</label>
			<div class="controls">
				${fns:getDictLabel(merchantLog.merchantType, 'MerchantAccountType', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易订单号：</label>
			<div class="controls">
				${merchantLog.transNo }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行流水号：</label>
			<div class="controls">
				<c:choose>
					<c:when test="${not empty merchantLog.paymentId}">
						${merchantLog.paymentId }
					</c:when>
					<c:otherwise>-</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易总笔数：</label>
			<div class="controls">
				${merchantLog.payNum }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">资金变动：</label>
			<div class="controls">
				<fmt:formatNumber value="${merchantLog.balanceAmountChanges }" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">账户余额：</label>
			<div class="controls">
				<fmt:formatNumber value="${merchantLog.balanceAmount }" pattern="￥#,##0.00" />
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">冻结总金额：</label>
			<div class="controls">
			<fmt:formatNumber value="${merchantLog.balanceFreezedAmount }" pattern="￥#,##0.00" />
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">可用总金额：</label>
			<div class="controls">
			<fmt:formatNumber value="${merchantLog.balanceAvailableAmount }" pattern="￥#,##0.00" />
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">可提现总金额：</label>
			<div class="controls">
			<fmt:formatNumber value="${merchantLog.balanceAvailableWithdrawAmount }" pattern="￥#,##0.00" />
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收入金额：</label>
			<div class="controls">
			<fmt:formatNumber value="${merchantLog.totalInAmount }" pattern="￥#,##0.00" />
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支出金额：</label>
			<div class="controls">
			<fmt:formatNumber value="${merchantLog.totalOutAmount }" pattern="￥#,##0.00" />
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
			<fmt:formatDate value="${merchantLog.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">资金方向:</label>
			<div class="controls">
				<c:if test="${merchantLog.balanceDirection eq 'D'}">借</c:if>
				<c:if test="${merchantLog.balanceDirection eq 'C'}">贷</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易类型：</label>
			<div class="controls">
				${fns:getDictLabel(merchantLog.type, 'TransType', '')}
			</div>
		</div>
		<div style="clear:both"></div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>