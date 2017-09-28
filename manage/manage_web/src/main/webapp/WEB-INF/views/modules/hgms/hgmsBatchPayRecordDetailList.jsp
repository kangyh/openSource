<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>批量代付明细表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            $("input").attr("onkeyup","value=value.replace(/[^0-9\u4E00-\u9FA5\a-z\A-Z\.\-]/g,'')")
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        function deleteselect(){
            $("#searchForm").find("input[type='text']").val("");
            //默认状态
            $("#transWay").find("option").removeAttr("selected");
            $("#transWay").find("option:eq(0)").attr("selected", "selected");
            $(".select2-chosen:eq(2)").text($("#transWay option[selected]").text());
            //默认状态
            $("#settlementStatus").find("option").removeAttr("selected");
            $("#settlementStatus").find("option:eq(0)").attr("selected", "selected");
            $(".select2-chosen:eq(1)").text($("#settlementStatus option[selected]").text());
            //默认状态
            $("#transStatus").find("option").removeAttr("selected");
            $("#transStatus").find("option:eq(0)").attr("selected", "selected");
            $(".select2-chosen:eq(0)").text($("#transStatus option[selected]").text());
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/hgms/hgmsBatchPayRecordDetail/">当日代付记录明细查询列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="hgmsBatchPayRecordDetail" action="${ctx}/hgms/hgmsBatchPayRecordDetail/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>订单号：</label>
				<form:input path="transId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label style="width:93px;">商户名称：</label>
				<form:input path="sourceMerchantName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>子商户名称：</label>
				<form:input path="destMerchantName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>交易时间：</label>
				<input name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${hgmsBatchPayRecordDetail.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${hgmsBatchPayRecordDetail.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>订单批次号：</label>
					<input  name="transBatchNo" type="text" value="${hgmsBatchPayRecordDetail.transBatchNo}"/>
			</li>
			<li><label>交易方式：</label>
				<form:select path="transWay" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="PAYHFB" label="向汇付宝账户付款"/>
					<form:option value="PAYBNK" label="向银行卡付款"/>
				</form:select>
			</li>
			<li><label style="width: 107px">子商户开户银行：</label>
				<form:input path="openbankName" htmlEscape="false" maxlength="256" class="input-medium"/>
			</li>
			<li><label>结算状态：</label>
				<form:select path="settlementStatus" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="UNSETT" label="未结算"/>
					<form:option value="SETTED" label="已结算"/>
				</form:select>
			</li>
			<li><label style="padding-left: 15px">交易状态：</label>
				<form:select path="transStatus" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="SUCCES" label="成功"/>
					<form:option value="FAILED" label="失败"/>
					<form:option value="PROCES" label="处理中"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="deleteselect()"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>交易总笔数</th>
			<th>成功总笔数</th>
			<th>失败总笔数</th>
			<th>处理中总笔数</th>
			<th>交易总金额</th>
			<th>成功总金额</th>
			<th>失败总金额</th>
			<th>处理中总金额</th>
			<th>交易总手续费</th>
			<th>成功手续费</th>
			<th>失败手续费</th>
			<th>处理中手续费</th>
		</tr>
		</thead>
		<tbody>
		<tr>
			<c:if test="${not empty HgmsSummaryResult}">
				<td><fmt:formatNumber value="${HgmsSummaryResult.tranNumberCount}" /></td>
				<td><fmt:formatNumber value="${HgmsSummaryResult.tranNumberSuccess}" /></td>
				<td><fmt:formatNumber value="${HgmsSummaryResult.tranNumberFailed}" /></td>
				<td><fmt:formatNumber value="${HgmsSummaryResult.tranNumberMiddle}" /></td>
				<td><fmt:formatNumber value="${HgmsSummaryResult.tranMoneyCount}"  pattern="￥0.00"/></td>
				<td><fmt:formatNumber value="${HgmsSummaryResult.tranMoneySuccess}"  pattern="￥0.00"/></td>
				<td><fmt:formatNumber value="${HgmsSummaryResult.tranMoneyFailed}"  pattern="￥0.00"/></td>
				<td><fmt:formatNumber value="${HgmsSummaryResult.tranMoneyMiddle}"  pattern="￥0.00"/></td>
				<td><fmt:formatNumber value="${HgmsSummaryResult.feeCount}"  pattern="￥0.00"/></td>
				<td><fmt:formatNumber value="${HgmsSummaryResult.feeSuccess}"  pattern="￥0.00"/></td>
				<td><fmt:formatNumber value="${HgmsSummaryResult.feeFailed}"  pattern="￥0.00"/></td>
				<td><fmt:formatNumber value="${HgmsSummaryResult.feeMiddle}"  pattern="￥0.00"/></td>
			</c:if>
		</tr>
		</tbody>
	</table><br><br>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>订单批次号</th>
				<th>订单号</th>
				<th>汇付宝订单号</th>
				<th>商户名称</th>
				<th>子商户名称</th>
				<th>开户银行</th>
				<th>交易时间</th>
				<th>交易方式</th>
				<th>交易状态</th>
				<th>结算状态</th>
				<th>手续费</th>
				<th>交易金额</th>
				<th>错误描述</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hgmsBatchPayRecordDetail">
			<tr>
				<td>
						${hgmsBatchPayRecordDetail.transBatchNo}
				</td>
				<td>
					${hgmsBatchPayRecordDetail.transId}
				</td>
				<td>
						${hgmsBatchPayRecordDetail.hfbTransId}
				</td>
				<td>
					${hgmsBatchPayRecordDetail.sourceMerchantName}
				</td>
				<td>
					${hgmsBatchPayRecordDetail.destMerchantName}
				</td>
				<td>
						${hgmsBatchPayRecordDetail.openbankName}
				</td>
				<td>
					<fmt:formatDate value="${hgmsBatchPayRecordDetail.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${hgmsBatchPayRecordDetail.transWay}
				</td>
				<td>
						${hgmsBatchPayRecordDetail.transStatus}
				</td>
				<td>
					${hgmsBatchPayRecordDetail.settlementStatus}
				</td>

				<td>
						<fmt:formatNumber value="${hgmsBatchPayRecordDetail.feeAmount}" pattern="￥0.00" />
				</td>
				<td>
					<fmt:formatNumber value="${hgmsBatchPayRecordDetail.payAmount}" pattern="￥0.00" />
				</td>
				<td>
					${hgmsBatchPayRecordDetail.errorMsg}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
</body>
</html>