<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>调账-交易维度</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".checkPass").on("click",function(){
				var url = $(this).attr("value-url");
				$.ajax({
					type : "POST",
					url : "${ctx}/allocate/allocateRecordReview/isAdminUser",
					dataType : "json",
					async : false,
					success : function(data){
						if(data){
							alert("超级管理员不允许操作此功能");
							return false;
						}else{
							parent.showDynamicPa();
							parent.enterDynamicPa(url);
							return true;
						}
					}
				});			
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/adjust/adjustTradeAccountQuery/">调账-交易维度列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="trialBalanceTrans" action="${ctx}/adjust/adjustTradeAccountQuery/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>交易号：</label>
				<form:input path="transNo" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select id="status" path="status" class="input-xlarge ">
                   <form:option value="" label="全部"/>
                   <form:option value="PREHAN" label="未处理"/>
                   <form:option value="INHAND" label="处理中"/>
                   <form:option value="FINISH" label="已处理"/>
                   <form:option value="REJECT" label="审核拒绝"/>
               </form:select>
			</li>		
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>交易号</th>
				<th>交易类型</th>
				<th>借方发生额</th>
				<th>贷方发生额</th>
				<th>差额</th>
				<th>会计日期</th>
				<th>状态</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="trialBalanceTrans">
			<tr>
				<td>
					${trialBalanceTrans.transNo}
				</td>
				<td>
					<c:if test="${trialBalanceTrans.type == 'CHARGE'}">充值</c:if>
					<c:if test="${trialBalanceTrans.type == 'WZDRAW'}">提现</c:if>
					<c:if test="${trialBalanceTrans.type == 'REFUND'}">退款</c:if>
					<c:if test="${trialBalanceTrans.type == 'PAYMNT'}">支付</c:if>
					<c:if test="${trialBalanceTrans.type == 'TRAFER'}">内转</c:if>
					<c:if test="${trialBalanceTrans.type == 'BATPAY'}">转账</c:if>
					<c:if test="${trialBalanceTrans.type == 'BATCOL'}">代收</c:if>
					<c:if test="${trialBalanceTrans.type == 'SHARES'}">分润</c:if>
					<c:if test="${trialBalanceTrans.type == 'PMONEY'}">打款认证</c:if>
					<c:if test="${trialBalanceTrans.type == 'WECHAT'}">微信支付</c:if>
					<c:if test="${trialBalanceTrans.type == 'RENAME'}">实名认证</c:if>
				</td>
				<td>
					${trialBalanceTrans.debitCurrentAmount}
				</td>
				<td>
					${trialBalanceTrans.creditCurrentAmount}
				</td>
				<td>
					${trialBalanceTrans.diffAmount}
				</td>
				<td>
					${trialBalanceTrans.accountDate}
				</td>
				<td>
					<c:if test="${trialBalanceTrans.status == 'PREHAN'}"><font style="color:#636363;">未处理</font></c:if>
					<c:if test="${trialBalanceTrans.status == 'INHAND'}"><font style="color:#8B4513;">处理中</font></c:if>
					<c:if test="${trialBalanceTrans.status == 'FINISH'}"><font style="color:#008800;">已处理</font></c:if>
					<c:if test="${trialBalanceTrans.status == 'REJECT'}"><font style="color:#E63F00;">审核拒绝</font></c:if>
				</td>
				<td>
					<fmt:formatDate value="${trialBalanceTrans.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${trialBalanceTrans.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<c:if test="${trialBalanceTrans.status == 'PREHAN' || trialBalanceTrans.status == 'REJECT'}">
						<a style="cursor:pointer;" class="checkPass" value-url="${ctx}/adjust/adjustTradeAccount/form?trialBalanceTransId=${trialBalanceTrans.id}">处理</a>
					</c:if>
				</td>				
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>