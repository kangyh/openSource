<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>转账管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {

			});
	</script>
</head>
<body>
		转账明细查询
<br>
		<br>
		合计实际转账金额：<fmt:formatNumber value="${batchPayRecordDetailSummary.successTotalAmount}" pattern="￥#,##0.00" />,
		手续费总额：<fmt:formatNumber value="${batchPayRecordDetailSummary.totalFeeAmount}" pattern="￥#,##0.00" />,
		合计失败转账金额：<fmt:formatNumber value="${batchPayRecordDetailSummary.failTotalAmount}" pattern="￥#,##0.00" />
<br>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead>
	<tr>
		<th>交易订单号</th>
		<th>商户编码</th>
		<th>付款批次号</th>
		<th>银行名称</th>
		<th>省份</th>
		<th>城市</th>
		<th>开户支行名称</th>
		<th>付款通道</th>
		<th>收款方开户名</th>
		<th>银行卡号</th>
		<th>手续费</th>
		<th>转账金额</th>
		<th>备注</th>
		<th>状态</th>
		<th>银行服务类型</th>
		<th>处理时间</th>
		<!-- <th>会计日期</th> -->
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.list}" var="batchPayRecordDetail">
		<tr>
			<td>
					${batchPayRecordDetail.batchPayId}
			</td>
			<td>
					${batchPayRecordDetail.merchantId}
			</td>
			<td>
					${batchPayRecordDetail.batchId}
			</td>
			<td>
					${batchPayRecordDetail.bankName}
			</td>
			<td>
					${batchPayRecordDetail.province}
			</td>
			<td>
					${batchPayRecordDetail.city}
			</td>
			<td>
					${batchPayRecordDetail.openbankName}
			</td>
			<td>
					${batchPayRecordDetail.channelName}
			</td>
			<td>
					${batchPayRecordDetail.bankcardOwnerName}
			</td>
			<td>
				<shiro:lacksPermission name="payment:batchPayRecordDetail:bankcardNo">
					${fns:hiddenBankcardNo(batchPayRecordDetail.bankcardNo)}
				</shiro:lacksPermission>
				<shiro:hasPermission name="payment:batchPayRecordDetail:bankcardNo">
					${batchPayRecordDetail.bankcardNo}
				</shiro:hasPermission>
			</td>
			<td>
					<fmt:formatNumber value="${batchPayRecordDetail.feeAmount}" pattern="￥#,##0.00" />
			</td>
			<td>
				<fmt:formatNumber value="${batchPayRecordDetail.payAmount}" pattern="￥#,##0.00" />
			</td>
			<td>
               ${batchPayRecordDetail.payReason}
            </td>
			<td>
				${fns:getDictLabel(batchPayRecordDetail.status, 'BatchPayRecordDetailStatus', '')}
			</td>
			<td>
                <c:set var="intoaccountTime">
                    <fmt:formatDate value="${batchPayRecordDetail.intoaccountTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </c:set>
                <c:choose>
                <c:when test="${fn:endsWith(intoaccountTime,'23:59:59')}">
                    T+1
                </c:when>
                <c:otherwise>
                    T+0
                </c:otherwise>
                </c:choose>
            </td>
			<td>
				<fmt:formatDate value="${batchPayRecordDetail.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>
		<!-- 	<td>
					-
			</td> -->
		</tr>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>
<div class="form-actions">
	<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
</div>
</body>
</html>