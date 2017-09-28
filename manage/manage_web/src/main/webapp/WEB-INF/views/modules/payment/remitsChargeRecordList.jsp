<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>汇款充值审核</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnSubmit").on("click",function(){
				var merchantId = $("#merchantId").val();
				var chargeId = $("#chargeId").val();
				var merchantCompany = $("#merchantCompany").val();
				var merchantLoginName = $("#merchantLoginName").val();

				if(!isEmpty(merchantId)&&!/\d+/.test(merchantId)){
					alert("商户编码输入不合法，只能输入数字");
					return ;
				}
				if(!isEmpty(chargeId)&&!/\d+/.test(chargeId)){
					alert("交易订单号输入不合法，只能输入数字");
					return ;
				}
				if(!isEmpty(merchantCompany)&&!/^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(merchantCompany)){
					alert("商户公司名称输入不合法，请重新输入");
					return ;
				}
				if(!isEmpty(merchantLoginName)&&!/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/.test(merchantLoginName)){
					alert("商户账号输入不合法，请重新输入");
					return ;
				}
				
				if(checkDate()){
					$("#searchForm").submit();
				}
				
			});
			
			
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
		
		function isEmpty(value){
	 		if(value==undefined||value==""||value.trim()==""){
	 			return true;
	 		}
	 		return false;
	 	}
		//清空
		function onClear(){
			$("#searchForm").find("input[type='text']").val("");
 			//默认支付状态
			$("#status").find("option").removeAttr("selected");
			$("#status").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#status option[selected]").text());
			//默认排序方式
			$("#sortOrder").find("option").removeAttr("selected");
			$("#sortOrder").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(1)").text($("#sortOrder option[selected]").text());
			
		}
		
		//导出
		function exportExcel(){
			if(checkDate()){
				var chargeId = $("#chargeId").val();
				var merchantId = $("#merchantId").val();
				var merchantCompany = $("#merchantCompany").val();
				var merchantLoginName = $("#merchantLoginName").val();
				var beginCreateTime = $("#beginCreateTime").val();
				var endCreateTime = $("#endCreateTime").val();
				var host = "${ctx}/payment/remitsChargeRecord/exportExcel";
				var url = host+"?chargeId="+chargeId+"&merchantId="+merchantId+"&merchantCompany="+merchantCompany +
						"&merchantLoginName="+merchantLoginName+"&beginCreateTime="+beginCreateTime+"&endCreateTime="+endCreateTime;
				$('<form method="post" action="' + url + '"></form>').appendTo('body').submit().remove();
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/remitsChargeRecord/">充值管理列表</a></li>
		<shiro:hasPermission name="payment:chargeRecord:edit"><li><a href="${ctx}/payment/remitsChargeRecord/form">充值管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="chargeRecord" action="${ctx}/payment/remitsChargeRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>交易订单号：</label>
				<form:input path="chargeId" id="chargeId" htmlEscape="false" maxlength="20" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')} else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label>商户编码：</label>
				<form:input path="merchantId" id="merchantId" htmlEscape="false" maxlength="19" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')} else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label>商户公司名称：</label>
				<form:input path="merchantCompany" id="merchantCompany" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>商户账号：</label>
				<form:input path="merchantLoginName" id="merchantLoginName" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>
		<%-- 	<li><label>账户ID：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>账户名称：</label>
				<form:input path="accountName" htmlEscape="false" maxlength="256" class="input-medium"/>
			</li> --%>
			<li><label>创建时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${chargeRecord.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${chargeRecord.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="btns"><input class="btn btn-warning" type="button" onclick="exportExcel()" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table class="table table-striped   table-bordered table-condensed">
		<thead>
			<tr>
				<th>充值总金额</th>
				<th>成功总金额</th>
				<th>失败总金额</th>
				<th>成功手续费</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td id="chargeTotalAmount">￥0.00</td>
				<td id="successTotalAmount">￥0.00</td>
				<td id="failedTotalAmount">￥0.00</td>
				<td id="feeTotalAmount">￥0.00</td>
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
				<th>创建时间</th>
				<th>充值金额</th>
				<th>手续费</th>
				<th>实际支付金额</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="chargeRecord">
			<tr >
				<td>
					${chargeRecord.merchantId}
				</td>
				<td>
					${chargeRecord.merchantCompany}
				</td>
				<td>
					${chargeRecord.merchantLoginName}
				</td>
				<td>
					<a href="${ctx}/payment/remitsChargeRecord/form?id=${chargeRecord.id}">	${chargeRecord.id}</a>
				</td>
				<td>
					<fmt:formatDate value="${chargeRecord.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatNumber value="${chargeRecord.chargeAmount}" pattern="￥#,##0.00" />
				</td>
				<td>
					<fmt:formatNumber value="${chargeRecord.feeAmount}" pattern="￥#,##0.00" />
				</td>
				<td>
					<fmt:formatNumber value="${chargeRecord.realAmount}" pattern="￥#,##0.00" />
				</td>
				<td <c:if test="${chargeRecord.status=='SUCCES'}">style="background:${success_background}" </c:if>>
					${fns:getDictLabel(chargeRecord.status, 'ChargeStatus', '')}
				</td>
    			<td>
    				<c:if test="${chargeRecord.status=='PREAUD'}">
    				<a style="cursor:pointer;" class="checkPass" value-url="${ctx}/payment/remitsChargeRecord/toReview?id=${chargeRecord.id}">审核</a>
    				</c:if>
    			</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script type="text/javascript">
		$(function(){
			var chargeId = $("#chargeId").val();
			var merchantId = $("#merchantId").val();
			var merchantCompany = $("#merchantCompany").val();
			var merchantLoginName = $("#merchantLoginName").val();
			var beginCreateTime = $("#beginCreateTime").val();
			var endCreateTime = $("#endCreateTime").val();
			$.ajax({
				type : "POST",
				url : "${ctx}/payment/remitsChargeRecord/getStatisticsList",
				data : {
					"chargeId" : chargeId,
					"merchantId" : merchantId,
					"merchantCompany" : merchantCompany,
					"merchantLoginName" : merchantLoginName,
					"beginCreateTime" : beginCreateTime,
					"endCreateTime" : endCreateTime
				},
				dataType : "json",
				success : function(data){
					$("#chargeTotalAmount").text(data.chargeTotalAmount);
					$("#successTotalAmount").text(data.successTotalAmount);
					$("#failedTotalAmount").text(data.failedTotalAmount);
					$("#feeTotalAmount").text(data.feeTotalAmount);
				}
			});
			
		});
	</script>
</body>
</html>