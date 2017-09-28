<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>交易管理管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/validateNum.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		var validateFrom = {
			validate: function(form){
				var merchantOrderNo = $("#merchantOrderNo").val();
				var merchantOrderNoResult = validatePreventInject(merchantOrderNo,"商户订单号输入不合法!");
				var merchantCompany = $("#merchantCompany").val();
				var merchantCompanyResult = validatePreventInject(merchantCompany,"商户公司名称输入不合法!");
				var merchantLoginName = $("#merchantLoginName").val();
				var merchantLoginNameResult = validatePreventInject(merchantLoginName,"商户账号输入不合法!");
				if(!merchantOrderNoResult || !merchantCompanyResult || !merchantLoginNameResult){
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
			if(checkDate()){
				validateFrom.validate($("#searchForm"));
			}
		}
		//清空
		function onClear(){
			$("#searchForm").find("input[type='text']").val("");
			//默认支付状态
			$("#status").find("option").removeAttr("selected");
			$("#status").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#status option[selected]").text());
			//手续费扣除方式
			$("#feeType").find("option").removeAttr("selected");
			$("#feeType").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(1)").text($("#feeType option[selected]").text());
            $(".select2-chosen:eq(2)").text($("#feeType option[selected]").text());
            //默认排序方式
	 		$("#sortOrder").find("option").removeAttr("selected");
			$("#sortOrder").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(3)").text($("#sortOrder option[selected]").text());
			
		}
		
		
		//导出
		function exportExcel(){
			if(checkDate()){
				var gatewayId = $("#gatewayId").val();
				var merchantId = $("#merchantId").val();
				var merchantCompany = $("#merchantCompany").val();
				var merchantOrderNo = $("#merchantOrderNo").val();
				var merchantLoginName = $("#merchantLoginName").val();
				var beginCreateTime = $("#beginCreateTime").val();
				var endCreateTime = $("#endCreateTime").val();
				var status = $("#status").val();
				var feeType = $("#feeType").val();
				var host = "${ctx}/payment/gatewayRecord/exportExcel";
				var url = host+"?gatewayId="+gatewayId+"&merchantId="+merchantId+"&merchantCompany="+merchantCompany +
						"&merchantLoginName="+merchantLoginName+"&beginCreateTime="+beginCreateTime+"&endCreateTime="+endCreateTime+
						"&merchantOrderNo="+merchantOrderNo+"&status="+status+"&feeType="+feeType;
				$('<form method="post" action="' + url + '"></form>').appendTo('body').submit().remove();
			}
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/gatewayRecord/">支付查询</a></li>
		<shiro:hasPermission name="payment:gatewayRecord:edit"><li><a href="${ctx}/payment/gatewayRecord/form">交易管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="gatewayRecord" action="${ctx}/payment/gatewayRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>交易订单号：</label>
				<form:input path="gatewayId" id="gatewayId" htmlEscape="false" maxlength="26" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')} else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label>商户订单号：</label>
				<form:input path="merchantOrderNo" id="merchantOrderNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商户编码：</label>
				<form:input path="merchantId" id="merchantId" htmlEscape="false" maxlength="64" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')} else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label>商户公司名称：</label>
				<form:input path="merchantCompany" id="merchantCompany" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商户账号：</label>
				<form:input path="merchantLoginName" id="merchantLoginName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${gatewayRecord.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${gatewayRecord.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
                <form:select id="statisticsDate" path="statisticsDate" class="input-medium" style="width:100px;" onchange="changeDateSection(this.options[this.options.selectedIndex].text);">
                    <form:option value="" label="当天"/>
                    <c:forEach items="${fns:getDictList('statisticsDate')}" var="item">
                        <form:option value="${item.value}" label="${item.label}"/>
                    </c:forEach>
                </form:select>
            </li>
	<%-- 		<li><label>订单金额：</label>
				<form:input path="requestAmount" htmlEscape="false" class="input-medium"/>
			</li> --%>
<%-- 			<li><label>成功时间：</label>
				<input name="beginSuccessTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${gatewayRecord.beginSuccessTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endSuccessTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${gatewayRecord.endSuccessTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li> --%>
		<%-- 	<li><label>交易类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('TransType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li> --%>
			<li><label>状态：</label>
				<form:select id="status" path="status" class="input-medium">
					<form:option value="R1" label="全部"/>
					<form:options items="${fns:getDictList('GatewayRecordStatus')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label style="width:110px;">手续费扣除方式：</label>
				<form:select id="feeType" path="feeType" class="input-medium">
					<form:option value="R1" label="全部"/>
					<form:options items="${fns:getDictList('FeeDeductType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>升降排序：</label>
				<form:select id="sortOrder" path="sortOrder" class="input-medium">
					<c:forEach items="${fns:getDictList('SortStatus')}" var="sStatus">
						<form:option value="${sStatus.value}" label="${sStatus.label}"/>
					</c:forEach>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="btns"><input class="btn btn-warning" type="button" onclick="exportExcel()" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	<table class="table table-striped   table-bordered table-condensed">
		<thead>
			<tr>
				<th>总笔数</th>
				<th>订单总金额</th>
				<th>实际支付总金额</th>
				<th>手续费总金额</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td id="count">0笔</td>
				<td id="totalOrderAmount">￥0.00</td>
				<td id="totalRealAmount">￥0.00</td>
				<td id="totalFeeAmount">￥0.00</td>
			</tr>
		</tbody>
	</table>
	<table id="contentTable" class="table table-striped   table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户编码</th>
				<th>商户公司名称</th>
				<th>商户账号</th>
				<th>交易订单号</th>
				<!-- <th>商户用户账号</th> -->
				<th>商户订单号</th>
				<!-- <th>来源方式</th> -->
				<th>创建时间</th>
				<th>订单金额</th>
				<!-- <th>成功时间</th> -->
				<th>手续费金额</th>
				<th>手续费扣除方式</th>
				<th>实际支付金额</th>
				<!-- <th>成功交易金额</th> -->
				<!-- <th>交易类型</th> -->
				<th>状态</th>
				<!-- <th>交易说明</th> -->
		<!-- 		<th>交易手续费方式</th>
				<th>交易手续费率</th> -->
		<!-- 		<th>风控结果</th>
				<th>风控原因</th> -->
				<!-- <th>授权码</th> -->
				<!-- <th>操作</th> -->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="gatewayRecord">
			<tr>
				<td>
					${gatewayRecord.merchantId}
				</td>
				<td>
					${gatewayRecord.merchantCompany}
				</td>
				<td>
					${gatewayRecord.merchantLoginName}
				</td>
				<td><a href="${ctx}/payment/gatewayRecord/form?id=${gatewayRecord.gatewayId}">
					${gatewayRecord.gatewayId}
				</a></td>
			<%-- 	<td>
					${gatewayRecord.merchantUserName}
				</td> --%>
				<td>
					${gatewayRecord.merchantOrderNo}
				</td>
		<%-- 		<td>
					${fns:getDictLabel(gatewayRecord.requestType, 'RequestFrom', '')}
				</td> --%>
				<td>
					<fmt:formatDate value="${gatewayRecord.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatNumber value="${gatewayRecord.requestAmount}" pattern="￥#,##0.00" />
				</td>
			<%-- 	<td>
					<fmt:formatDate value="${gatewayRecord.successTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td> --%>
				<td>
					<fmt:formatNumber value="${gatewayRecord.feeAmount}" pattern="￥#,##0.00" />
				</td>
				<td>
					${fns:getDictLabel(gatewayRecord.feeType, 'FeeDeductType', '')}
				</td>
				<td>
					<fmt:formatNumber value="${gatewayRecord.successAmount}" pattern="￥#,##0.00" />
				</td>
<%-- 				<td>
					<fmt:formatNumber value="${gatewayRecord.successAmount}" pattern="￥0.00" />
				</td> --%>
			<%-- 	<td>
					${fns:getDictLabel(gatewayRecord.type, 'TransType', '')}
				</td> --%>
				<td <c:if test="${gatewayRecord.status=='SUCCES'}">
						style="background:${success_background}" 
					</c:if>>
					${fns:getDictLabel(gatewayRecord.status, 'GatewayRecordStatus', '')}
				</td>
			<%-- 	<td>
					${gatewayRecord.note}
				</td> --%>
	<%-- 			<td>
					${gatewayRecord.feeType}
				</td>
				<td>
					${gatewayRecord.feeRatio}
				</td> --%>
		<%-- 		<td>
					${gatewayRecord.riskcontrolType}
				</td>
				<td>
					${gatewayRecord.riskcontrolReason}
				</td> --%>
			<%-- 	<td>
					${gatewayRecord.authorizationCode}
				</td> --%>
			<%-- 	<td>
					<c:choose>
						<c:when test="${gatewayRecord.status == 'PROCES'}">
							<a href="${ctx}/payment/gatewayRecord/freeze?Id=${gatewayRecord.gatewayId}" onclick="return confirmx('确认要冻结该交易管理吗？', this.href)">冻结</a>
						</c:when>
						<c:when test="${gatewayRecord.status == 'FREZED'}">
							<a href="${ctx}/payment/gatewayRecord/thaw?Id=${gatewayRecord.gatewayId}" onclick="return confirmx('确认要解冻该交易管理吗？', this.href)">解冻</a>
						</c:when>
					</c:choose>
				</td> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script type="text/javascript">
		$(function(){
			var gatewayId = $("#gatewayId").val();
			var merchantId = $("#merchantId").val();
			var merchantCompany = $("#merchantCompany").val();
			var merchantOrderNo = $("#merchantOrderNo").val();
			var merchantLoginName = $("#merchantLoginName").val();
			var beginCreateTime = $("#beginCreateTime").val();
			var endCreateTime = $("#endCreateTime").val();
			var status = $("#status").val();
			var feeType = $("#feeType").val();
			$.ajax({
				type : "POST",
				url : "${ctx}/payment/gatewayRecord/getStatisticsList",
				data : {
					"gatewayId" : gatewayId,
					"merchantId" : merchantId,
					"merchantCompany" : merchantCompany,
					"merchantOrderNo" : merchantOrderNo,
					"merchantLoginName" : merchantLoginName,
					"beginCreateTime" : beginCreateTime,
					"endCreateTime" : endCreateTime,
					"status" : status,
					"feeType" : feeType
				},
				dataType : "json",
				success : function(data){
					$("#count").text(data.count);
					$("#totalOrderAmount").text(data.totalOrderAmount);
					$("#totalRealAmount").text(data.totalRealAmount);
					$("#totalFeeAmount").text(data.totalFeeAmount);
				}
			});
			
		});
	</script>
</body>
</html>