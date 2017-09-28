<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>转账明细管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script language="JavaScript" src="${ctxStatic}/common/validateNum.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

		});
		//验证搜索条件
		var validateFrom = {
			validate: function(form){
				var merchantId = $("#merchantId").val();
                var result = validateNum(merchantId);
                if(!result||!checkDate()){
                    return;
                }
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
			$("#searchForm").find("option").removeAttr("selected");
			$("#searchForm").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen").text($("#searchForm option[selected]").text());
		}

		
		//导出
		function exportExcel(){
			if(checkDate()){
				var merchantId = $("#merchantId").val();
				var batchId = $("#batchId").val();
				var merchantBatchNo = $("#merchantBatchNo").val();
				var batchPayId = $("#batchPayId").val();
				var merchantPayNo = $("#merchantPayNo").val();
				var status = $("#status").val();
				var beginCreateTime = $("#beginCreateTime").val();
				var endCreateTime = $("#endCreateTime").val();
                var groupType = $("input[name='groupType']:checked").val();
				var host = "${ctx}/payment/batchPayRecordDetail/exportExcel";
				var url = host+"?merchantId="+merchantId+"&batchId="+batchId + "&batchPayId="+batchPayId +
						"&merchantBatchNo="+merchantBatchNo+"&beginCreateTime="+beginCreateTime+"&endCreateTime="+endCreateTime + 
						"&merchantPayNo="+merchantPayNo+"&status="+status+"&groupType="+groupType;
				$('<form method="post" action="' + url + '"></form>').appendTo('body').submit().remove();
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/batchPayRecordDetail/">转账明细查询列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="batchPayRecordDetail" action="${ctx}/payment/batchPayRecordDetail/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户编码：</label>
				<form:input path="merchantId" id="merchantId" htmlEscape="false" maxlength="64" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')} else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label>付款批次号：</label>
				<form:input path="batchId" id="batchId" htmlEscape="false" maxlength="64" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')} else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label>商户批次号：</label>
				<form:input path="merchantBatchNo" id="merchantBatchNo" htmlEscape="false" maxlength="64" class="input-medium" />
			</li>
			<li><label>交易订单号：</label>
                <form:input path="batchPayId" id="batchPayId" htmlEscape="false" maxlength="20" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')} else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
            </li>
			<li><label>商户订单号：</label>
				<form:input path="merchantPayNo" id="merchantPayNo" htmlEscape="false" maxlength="64" class="input-medium" />
			</li>
			<li><label>支付单号：</label>
				<form:input path="paymentId" id="paymentId" htmlEscape="false" maxlength="64" class="input-medium" />
			</li>
			<li><label>状态：</label>
                <form:select path="status" id="status" class="input-medium">
                    <form:option value="" label="全部"/>
                    <c:forEach items="${fns:getDictList('BatchPayRecordDetailStatus')}" var="sStatus">
                        <form:option value="${sStatus.value}" label="${sStatus.label}"/>
                    </c:forEach>
                </form:select>
            </li>
            <li><label>银行名称：</label>
                <form:input path="bankName" id="bankName" htmlEscape="false" maxlength="64" class="input-medium" />
            </li>
            <li><label>银行卡类型：</label>
                <form:select id="bankcardType" path="bankcardType" class="input-medium">
                    <form:option value="" label="全部"/>
                    <c:forEach items="${fns:getDictList('BankcardType')}" var="bankcardType">
                        <form:option value="${bankcardType.value}" label="${bankcardType.label}"/>
                    </c:forEach>
                </form:select>
            </li>
            <li><label>对公对私：</label>
                <form:select id="bankcardOwnerType" path="bankcardOwnerType" class="input-medium">
                    <form:option value="" label="全部"/>
                    <c:forEach items="${fns:getDictList('BankcardOwnerType')}" var="bankcardOwnerType">
                        <form:option value="${bankcardOwnerType.value}" label="${bankcardOwnerType.label}"/>
                    </c:forEach>
                </form:select>
            </li>
            <li><label>时间：</label>
                <input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                       value="<fmt:formatDate value="${batchPayRecordDetail.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
                <input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                       value="<fmt:formatDate value="${batchPayRecordDetail.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
                <form:select id="statisticsDate" path="statisticsDate" class="input-medium" style="width:100px;" onchange="changeDateSection(this.options[this.options.selectedIndex].text);">
                    <form:option value="" label="今天"/>
                    <c:forEach items="${fns:getDictList('statisticsDate')}" var="item">
                        <form:option value="${item.value}" label="${item.label}"/>
                    </c:forEach>
                </form:select>
				<input name="groupType"  type="radio" value="1" style="margin-left: 10px;"  <c:if test="${groupType == 1}">checked="checked"</c:if> >按创建时间
				<input name="groupType" type="radio" value="2" style="margin-right: 10px;" <c:if test="${groupType == 2}">checked="checked"</c:if>>按处理时间
			</li>
			<li class="clearfix"></li>
		</ul>
		<p class="btns" style="margin-left:44px;margin-top:10px;">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="btns"><input class="btn btn-warning" type="button" onclick="exportExcel()" value="导出"/></li>
		</p>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>转账总金额</th>
			<th>成功总金额</th>
			<th>失败总金额</th>
			<th>转账成功手续费</th>
		</tr>
		</thead>
		<tbody>
			<td id="totalAmount">￥0.00</td>
			<td id="successTotalAmount">￥0.00</td>
			<td id="failTotalAmount">￥0.00</td>
			<td id="totalFeeAmount">￥0.00</td>
		</tbody>
	</table>
	转账明细记录
	<table id="contentTable" class="table   table-bordered table-condensed">
		<thead>
			<tr>
				<th>交易订单号</th>
				<th>商户订单号</th>
				<th>支付单号</th>
		        <th>商户编码</th>
		        <th>付款批次号</th>
		        <th>商户批次号</th>
		        <th>手续费</th>
		        <th>转账金额</th>
		        <th>状态</th>
		        <th>银行名称</th>
		        <th>银行卡类型</th>
		        <th>对公对私</th>
		        <th>银行服务类型</th>
		        <th>创建时间</th>
		        <th>处理时间</th>
		        <th>付款通道</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="batchPayRecordDetail">
			<tr >
				<td>
				    <a href="${ctx}/payment/batchPayRecordDetail/form?id=${batchPayRecordDetail.batchPayId}&channelCode=${batchPayRecordDetail.channelCode}">
                    ${batchPayRecordDetail.batchPayId}
                    </a>
	            </td>
				<td>
					${batchPayRecordDetail.merchantPayNo}
				</td>
				<td>
					${batchPayRecordDetail.paymentId}
				</td>
	            <td>
                    ${batchPayRecordDetail.merchantId}
	            </td>
	            <td>
                    ${batchPayRecordDetail.batchId}
	            </td>
				<td>
					${batchPayRecordDetail.merchantBatchNo}
				</td>
	            <td>
                    <fmt:formatNumber value="${batchPayRecordDetail.feeAmount}" pattern="￥#,##0.00" />
	            </td>
	            <td>
	                <fmt:formatNumber value="${batchPayRecordDetail.payAmount}" pattern="￥#,##0.00" />
	            </td>
	            <td <c:if test="${batchPayRecordDetail.status=='SUCCES'}">
			            style="background:${success_background}"
			        </c:if>>
	                ${fns:getDictLabel(batchPayRecordDetail.status, 'BatchPayRecordDetailStatus', '')}
	            </td>
                <td>
                    ${batchPayRecordDetail.bankName}
                </td>
                <td>
                    ${fns:getDictLabel(batchPayRecordDetail.bankcardType, 'BankcardType', batchPayRecordDetail.bankcardType)}
                </td>
                <td>
                    ${fns:getDictLabel(batchPayRecordDetail.bankcardOwnerType, 'BankcardOwnerType', batchPayRecordDetail.bankcardOwnerType)}
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
                    <fmt:formatDate value="${batchPayRecordDetail.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
	            <td>
	                <fmt:formatDate value="${batchPayRecordDetail.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
	            </td>
	            <td>
                    ${batchPayRecordDetail.channelCode}
                </td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
		<script type="text/javascript">
		$(function(){
			var merchantId = $("#merchantId").val();
			var batchId = $("#batchId").val();
			var merchantBatchNo = $("#merchantBatchNo").val();
			var batchPayId = $("#batchPayId").val();
			var merchantPayNo = $("#merchantPayNo").val();
			var status = $("#status").val();
			var beginCreateTime = $("#beginCreateTime").val();
			var endCreateTime = $("#endCreateTime").val();
            var groupType = $("input[name='groupType']:checked").val();
			$.ajax({
				type : "POST",
				url : "${ctx}/payment/batchPayRecordDetail/getStatisticsList",
				data : {
					"merchantId" : merchantId,
					"batchId" : batchId,
					"merchantBatchNo" : merchantBatchNo,
					"batchPayId" : batchPayId,
					"merchantPayNo" : batchPayId,
					"status" : status,
					"beginCreateTime" : beginCreateTime,
					"endCreateTime" : endCreateTime,
					"groupType" : groupType,
                    "bankName" : $("#bankName").val(),
                    "bankcardType": $("#bankcardType").val(),
                    "bankcardOwnerType" : $("#bankcardOwnerType").val()
				},
				dataType : "json",
				success : function(data){
					$("#totalAmount").text(data.totalAmount);
					$("#successTotalAmount").text(data.successTotalAmount);
					$("#failTotalAmount").text(data.failTotalAmount);
					$("#totalFeeAmount").text(data.totalFeeAmount);
				}
			});
			
		});
	</script>
</body>
</html>