<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>查询管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		
		var validateFrom = {
			validate: function(form){
				form.submit();
			}
		}
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		//清空
		function onClear(){
			$("#searchForm").find("input[type='text']").val("");
			//默认升降排序
			$("#sortOrder").find("option").removeAttr("selected");
			$("#sortOrder").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#sortOrder option[selected]").text());
			$("#status").find("option").removeAttr("selected");
			$("#status").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#status option[selected]").text());
		}
		
		//搜索
		function onSubmit(){
			validateFrom.validate($("#searchForm"));
		}
		
		//导出
		function exportExcel(){
			if(checkDate()){
				var depositWithdrawId = $("#depositWithdrawId").val();
				var paymentId = $("#paymentId").val();
				var businessSeqNo = $("#businessSeqNo").val();
				var merchantId = $("#merchantId").val();
				var accountId = $("#accountId").val();
				var beginCreateTime = $("#beginCreateTime").val();
				var endCreateTime = $("#endCreateTime").val();
				var status = $("#status").val();
				var host = "${ctx}/deposit/depositWithdrawRecord/exportExcel";
				var url = host+"?depositWithdrawId="+depositWithdrawId+"&paymentId="+paymentId+"&businessSeqNo="+businessSeqNo +
						"&merchantId="+merchantId+"&accountId="+accountId+"&beginCreateTime="+beginCreateTime+"&endCreateTime="+endCreateTime+
						"&status="+status;
				$('<form method="post" action="' + url + '"></form>').appendTo('body').submit().remove();
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/deposit/depositWithdrawRecord/">查询列表</a></li>
		<shiro:hasPermission name="deposit:depositWithdrawRecord:edit"><li><a href="${ctx}/deposit/depositWithdrawRecord/form">查询添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="depositWithdrawRecord" action="${ctx}/deposit/depositWithdrawRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>交易订单号：</label>
				<form:input path="depositWithdrawId" id="depositWithdrawId" htmlEscape="false" maxlength="256" class="input-medium"/>
			</li>
			<li><label>支付订单号：</label>
				<form:input path="paymentId" id="paymentId" htmlEscape="false" maxlength="256" class="input-medium"/>
			</li>
			<li><label>商家订单号：</label>
				<form:input path="businessSeqNo" id="businessSeqNo" htmlEscape="false" maxlength="256" class="input-medium"/>
			</li>
			<li><label>商户编码：</label>
				<form:input path="merchantId" id="merchantId" htmlEscape="false" maxlength="6" class="input-medium"/>
			</li>
			<li><label>商户账户：</label>
				<form:input path="accountId" id="accountId" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select id="status" path="status" class="input-medium ">
                   <form:option value="" label="全部"/>
                   <form:option value="PREDRA" label="待处理"/>
                   <form:option value="FAILED" label="失败"/>
                   <form:option value="SUCCES" label="成功"/>
               </form:select>
			</li>
			<li><label>创建时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${depositWithdrawRecord.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${depositWithdrawRecord.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			    <form:select id="statisticsDate" path="statisticsDate" class="input-medium" style="width:100px;" onchange="changeDateSection(this.options[this.options.selectedIndex].text);">
					<form:option value="" label="全部"/>
					<c:forEach items="${fns:getDictList('statisticsDate')}" var="item">
						<form:option value="${item.value}" label="${item.label}"/>
					</c:forEach>
				</form:select>
		    </li>
			<li><label>升降排序：</label>
				<form:select id="sortOrder" path="sortOrder" class="input-medium">
					<c:forEach items="${fns:getDictList('SortStatus')}" var="sStatus">
						<form:option value="${sStatus.value}" label="${sStatus.label}"/>
					</c:forEach>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="btns"><input class="btn btn-warning" type="button" onclick="exportExcel()" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table class="table table-striped   table-bordered table-condensed">
		<thead>
			<tr>
				<th>待处理总笔数</th>
				<th>待处理总金额</th>
				<th>失败总笔数</th>
				<th>失败总金额</th>
				<th>成功总笔数</th>
				<th>成功总金额</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td id="pendingTotalSum">0笔</td>
				<td id="pendingTotalAmount">￥0.00</td>
				<td id="failedTotalSum">0笔</td>
				<td id="failedTotalAmount">￥0.00</td>
				<td id="successTotalSum">0笔</td>
				<td id="successTotalAmount">￥0.00</td>
			</tr>
		</tbody>
	</table>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>交易订单号</th>
				<th>商家订单号</th>
				<th>支付订单号</th>
				<th>商户编码</th>
				<th>商户名称</th>
				<th>商户账户</th>
				<th>账户名称</th>
				<th>卡持有人</th>
				<th>开户行支行名称</th>
				<th>银联行号</th>
				<th>银行卡类型</th>
				<th>提现金额</th>
				<th>状态</th>
				<th>创建时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="depositWithdrawRecord">
			<tr>
				<td>
					${depositWithdrawRecord.depositWithdrawId}
				</td>
				<td>
					${depositWithdrawRecord.businessSeqNo}
				</td>
				<td>
					${depositWithdrawRecord.paymentId}
				</td>
				<td>
					${depositWithdrawRecord.merchantId}
				</td>
				<td>
					${depositWithdrawRecord.merchantLoginName}
				</td>
				<td>
					${depositWithdrawRecord.accountId}
				</td>
				<td>
					${depositWithdrawRecord.accountName}
				</td>
				<td>
					${depositWithdrawRecord.recAccName}
				</td>
				<td>
					${depositWithdrawRecord.recOpenAccDept}
				</td>
				<td>
					${depositWithdrawRecord.recBankNo}
				</td>
				<td>
					${fns:getDictLabel(depositWithdrawRecord.cardTypeCode, 'BankcardType', '')}
				</td>
				<td>
					<fmt:formatNumber value="${depositWithdrawRecord.amount}" pattern="￥#,##0.00" />
				</td>
					<td <c:if test="${depositWithdrawRecord.status=='SUCCES'}">
			            style="background:#6CF683"
			        </c:if>>
					<c:if test="${depositWithdrawRecord.status == 'PREDRA'}">
						待处理
					</c:if>
					<c:if test="${depositWithdrawRecord.status == 'SUCCES'}">
						成功
					</c:if>
					<c:if test="${depositWithdrawRecord.status == 'FAILED'}">
						失败
					</c:if>
					</td>
				<td>
					<fmt:formatDate value="${depositWithdrawRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script type="text/javascript">
		$(function(){
			var depositWithdrawId = $("#depositWithdrawId").val();
			var paymentId = $("#paymentId").val();
			var businessSeqNo = $("#businessSeqNo").val();
			var merchantId = $("#merchantId").val();
			var accountId = $("#accountId").val();
			var beginCreateTime = $("#beginCreateTime").val();
			var endCreateTime = $("#endCreateTime").val();
			var status = $("#status").val();
			$.ajax({
				type : "POST",
				url : "${ctx}/deposit/depositWithdrawRecord/getStatisticsList",
				data : {
					"depositWithdrawId" : depositWithdrawId,
					"paymentId" : paymentId,
					"businessSeqNo" : businessSeqNo,
					"merchantId" : merchantId,
					"accountId" : accountId,
					"beginCreateTime" : beginCreateTime,
					"endCreateTime" : endCreateTime,
					"status" : status
				},
				dataType : "json",
				success : function(data){
					$("#pendingTotalSum").text(data.pendingTotalSum);
					$("#pendingTotalAmount").text(data.pendingTotalAmount);
					$("#failedTotalSum").text(data.failedTotalSum);
					$("#failedTotalAmount").text(data.failedTotalAmount);
					$("#successTotalSum").text(data.successTotalSum);
					$("#successTotalAmount").text(data.successTotalAmount);
				}
			});
			
		});
	</script>
</body>
</html>