<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>批量代收管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function onClear(){
			$("#searchForm").find("input[type='text']").val("");
			//默认排序方式
			$("#statisticsDate").find("option").removeAttr("selected");
			$("#statisticsDate").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen").text($("#statisticsDate option[selected]").text());
		}
		
		
		//导出
		function exportExcel(){
			var merchantId = $("#merchantId").val();
			var merchantUserId = $("#merchantUserId").val();
			var bankcardOwnerMobile = $("#bankcardOwnerMobile").val();
			var bankcardOwnerName = $("#bankcardOwnerName").val();
			var bankcardOwnerIdcard = $("#bankcardOwnerIdcard").val();
			var merchantBatchNo = $("#merchantBatchNo").val();
			var merchantOrderNo = $("#merchantOrderNo").val();
			var status = $("#status").val();
            var beginCreateTime = $("#beginCreateTime").val();
            var endCreateTime = $("#endCreateTime").val();
            var groupType = $("input[name='groupType']:checked").val();
			var host = "${ctx}/payment/batchCollectionRecordDetail/exportExcel";
			var url = host+"?merchantId="+merchantId+"&merchantUserId="+merchantUserId + "&bankcardOwnerMobile="+bankcardOwnerMobile+
					"&bankcardOwnerName="+bankcardOwnerName+"&bankcardOwnerIdcard="+bankcardOwnerIdcard+"&merchantBatchNo="+merchantBatchNo+
					"&merchantOrderNo="+merchantOrderNo+"&status="+status+"&beginCreateTime="+beginCreateTime+"&endCreateTime="+endCreateTime+"&groupType="+groupType;
			$('<form method="post" action="' + url + '"></form>').appendTo('body').submit().remove();
		}
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/batchCollectionRecordDetail/">批量代收列表</a></li>
		<shiro:hasPermission name="payment:batchCollectionRecordDetail:edit"><li><a href="${ctx}/payment/batchCollectionRecordDetail/form">批量代收添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="batchCollectionRecordDetail" action="${ctx}/payment/batchCollectionRecordDetail/" method="post" class="breadcrumb form-search">

		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户编号：</label>
				<form:input path="merchantId" id="merchantId" htmlEscape="false" maxlength="18" class="input-medium"/>
			</li>
			<li><label>用户编号：</label>
				<form:input path="merchantUserId" id="merchantUserId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>预留手机号：</label>
				<form:input path="bankcardOwnerMobile" id="bankcardOwnerMobile" htmlEscape="false" maxlength="256" class="input-medium"/>
			</li>
			<li><label>持卡人姓名：</label>
				<form:input path="bankcardOwnerName" id="bankcardOwnerName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>身份证号：</label>
				<form:input path="bankcardOwnerIdcard" id="bankcardOwnerIdcard" htmlEscape="false" maxlength="256" class="input-medium"/>
			</li>
			<li><label>商户批次号：</label>
				<form:input path="merchantBatchNo" id="merchantBatchNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商户订单号：</label>
				<form:input path="merchantOrderNo" id="merchantOrderNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${batchCollectionRecordDetail.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${batchCollectionRecordDetail.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<form:select id="statisticsDate" path="statisticsDate" class="input-medium" style="width:100px;" onchange="changeDateSection(this.options[this.options.selectedIndex].text);">
					<form:option value="" label="全部"/>
					<c:forEach items="${fns:getDictList('statisticsDate')}" var="item">
						<form:option value="${item.value}" label="${item.label}"/>
					</c:forEach>
				</form:select>
				<input name="groupType"  type="radio" value="1" style="margin-left: 10px;"  <c:if test="${groupType == 1}">checked="checked"</c:if> >按申请时间
				<input name="groupType" type="radio" value="2" style="margin-right: 10px;" <c:if test="${groupType == 2}">checked="checked"</c:if>>按修改时间
			</li>
			<li><label>状态：</label>
				<form:select id="status" path="status" class="input-medium">
					<form:option value="R1" label="全部"/>
					<form:options items="${fns:getDictList('PaymentRecordStatus')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>收款总金额</th>
				<th>成功手续费</th>
				<th>手续费总金额</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td id="collectTotalAmount">￥0.00</td>
				<td id="successTotalAmount">￥0.00</td>
				<td id="feeTotalAmount">￥0.00</td>
			</tr>
		</tbody>
	</table>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>代收明细ID</th>
				<th>商户编码</th>
				<th>用户编码</th>
				<th>申请时间</th>
				<th>修改时间</th>
				<th>收款金额</th>
				<th>银行卡号</th>
				<th>银行预留手机号</th>
				<th>持卡人姓名</th>
				<th>持卡人身份证号</th>
				<th>银行名称</th>
				<th>银行号</th>
				<th>商户批次号</th>
				<th>商户订单号</th>
				<th>状态</th>
				<th>成功时间</th>
				<th>成功金额</th>
				<th>手续费金额</th>
				<shiro:hasPermission name="payment:batchCollectionRecordDetail:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="batchCollectionRecordDetail">
			<tr>
				<td>
					${batchCollectionRecordDetail.collectId}
				</td>
				<td>
					${batchCollectionRecordDetail.merchantId}
				</td>
				<td>
					${batchCollectionRecordDetail.merchantUserId}
				</td>
				<td>
					<fmt:formatDate value="${batchCollectionRecordDetail.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${batchCollectionRecordDetail.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatNumber value="${batchCollectionRecordDetail.collectAmount}" pattern="￥#,##0.00" />
				</td>
				<td>
					${batchCollectionRecordDetail.bankcardNo}
				</td>
				<td>
					${batchCollectionRecordDetail.bankcardOwnerMobile}
				</td>
				<td>
					${batchCollectionRecordDetail.bankcardOwnerName}
				</td>
				<td>
					${batchCollectionRecordDetail.bankcardOwnerIdcard}
				</td>
				<td>
					${batchCollectionRecordDetail.bankName}
				</td>
				<td>
					${batchCollectionRecordDetail.bankId}
				</td>
				<td>
					${batchCollectionRecordDetail.merchantBatchNo}
				</td>
				<td>
					${batchCollectionRecordDetail.merchantOrderNo}
				</td>
				<td>
					${fns:getDictLabel(batchCollectionRecordDetail.status, 'PaymentRecordStatus', '')}
				</td>
				<td>
					<fmt:formatDate value="${batchCollectionRecordDetail.successTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatNumber value="${batchCollectionRecordDetail.successAmount}" pattern="￥#,##0.00" />
				</td>
				<td>
					<fmt:formatNumber value="${batchCollectionRecordDetail.feeAmount}" pattern="￥#,##0.00" />
				</td>
				<shiro:hasPermission name="payment:batchCollectionRecordDetail:edit"><td>
    				<a href="${ctx}/payment/batchCollectionRecordDetail/form?id=${batchCollectionRecordDetail.id}">修改</a>
					<a href="${ctx}/payment/batchCollectionRecordDetail/delete?id=${batchCollectionRecordDetail.id}" onclick="return confirmx('确认要删除该批量代收吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script type="text/javascript">
		$(function(){
			var merchantId = $("#merchantId").val();
			var merchantUserId = $("#merchantUserId").val();
			var bankcardOwnerMobile = $("#bankcardOwnerMobile").val();
			var bankcardOwnerName = $("#bankcardOwnerName").val();
			var bankcardOwnerIdcard = $("#bankcardOwnerIdcard").val();
			var merchantBatchNo = $("#merchantBatchNo").val();
			var merchantOrderNo = $("#merchantOrderNo").val();
            var beginCreateTime = $("#beginCreateTime").val();
            var endCreateTime = $("#endCreateTime").val();
            var groupType = $("input[name='groupType']:checked").val();
			var status = $("#status").val();
			$.ajax({
				type : "POST",
				url : "${ctx}/payment/batchCollectionRecordDetail/getStatisticsList",
				data : {
					"merchantId" : merchantId,
					"merchantUserId" : merchantUserId,
					"bankcardOwnerMobile" : bankcardOwnerMobile,
					"bankcardOwnerName" : bankcardOwnerName,
					"bankcardOwnerIdcard" : bankcardOwnerIdcard,
					"merchantBatchNo" : merchantBatchNo,
					"merchantOrderNo" : merchantOrderNo,
					"status" : status,
                    "beginCreateTime" : beginCreateTime,
                    "endCreateTime" : endCreateTime,
                    "groupType" : groupType
				},
				dataType : "json",
				success : function(data){
					$("#collectTotalAmount").text(data.collectTotalAmount);
					$("#successTotalAmount").text(data.successTotalAmount);
					$("#feeTotalAmount").text(data.feeTotalAmount);
				}
			});
			
		});
	</script>
</body>
</html>