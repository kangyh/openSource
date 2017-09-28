<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	//验证搜索条件
	$(document).ready(function() {
			
		});
	
	var validateFrom = {
		validate: function(form){
			
			var orderId = $("#orderId").val().trim();
			var warrantyId = $("#warrantyId").val().trim();
			var spreadId = $("#spreadId").val().trim();
			var orderBath = $("#orderBath").val().trim();
			if(!checkSafe(orderId)){
				
				$("#messageBox").text("订单包含非法字符!");
				return ;
			}
			if(!checkSafe(warrantyId)){
				
				$("#messageBox").text("保单包含非法字符!");
				return ;
			}
			if(!checkSafe(spreadId)){
				
				$("#messageBox").text("推广位Id包含非法字符!");
				return ;
			}
            if(!checkSafe(orderBath)){
				
				$("#messageBox").text("订单批次包含非法字符!");
				return ;
			}
			
			var bgTime = $("#beginOperEndTime").val();
			var endTime = $("#endOperEndTime").val();
			if(bgTime=="" && endTime!="" || bgTime!="" && endTime=="" ){
				$("#messageBox").text("请正确设置查询时间范围!");
				return ;
			}
			if( bgTime!="" && endTime!=""){
				if(compareDate(convertDateToJoinStr(bgTime),
								convertDateToJoinStr(endTime)) > 0){
					$("#messageBox").text("起始日期不能大于结束日期!");
					return ;
				}
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
		$("#pageNo").val(1);
		validateFrom.validate($("#searchForm"));
	}
	
	//清空
	function onClear(){
		$("#formBtn").submit();
	}
	
	//导入数据
	function onImport(){
		  var actionURL = $("#searchForm").attr("action");
	        $("#searchForm").attr("action",$("#searchForm").attr("action")+"onImport");
	        validateFrom.validate($("#searchForm"));
			$("#searchForm").attr("action",actionURL);
	}
	
	function checkSafe(a){
		if ( a.indexOf("script")>-1 || a.indexOf("\\")>-1 ){
			return false;
		}
		fibdn = new Array ("'",">","<","*","%","#","$","}","{","~","`","!","￥","/","?","&","^","(",")","=",";");
		i=fibdn.length;
		j=a.length;
		for (ii=0; ii<i; ii++) {
			for (jj=0; jj<j; jj++) {
				temp1=a.charAt(jj);
				temp2=fibdn[ii];
				if (temp1==temp2){
				return false;
				}
			}
		}
		
		return true;
	}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/prom/promOrder/">订单管理列表</a></li>
	</ul>
	
	<form action="${ctx}/prom/promOrder/" method="post" id="formBtn"></form> 
	
	<form:form id="searchForm" modelAttribute="promOrder" action="${ctx}/prom/promOrder/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>订单号：</label>
				<form:input path="orderId"  id="orderId" htmlEscape="false" maxlength="64" style="width:256px;" class="input-medium required"/>
			</li>
			<li><label>保单号：</label>
				<form:input path="warrantyId"  id="warrantyId" htmlEscape="false" maxlength="64" style="width:256px;" class="input-medium required"/>
			</li>
			<li><label>推广位ID：</label>
				<form:input path="spreadId"  id="spreadId" htmlEscape="false" maxlength="64" style="width:256px;" class="input-medium required"/>
			</li>
		</ul>
		<ul class="ul-form">
			<li><label>产品名称：</label>
				<form:input path="productName"  htmlEscape="false" maxlength="64" style="width:256px;" class="input-medium required"/>
			</li>
			<li><label>订单批次号：</label>
				<form:input path="orderBath"  id="orderBath" htmlEscape="false" maxlength="64" style="width:256px;" class="input-medium required"/>
			</li>
			<li><label>推广方式：</label>
				<form:input path="spreadWay"  htmlEscape="false" maxlength="64" style="width:256px;" class="input-medium required"/>
			</li>
		</ul> 
		<ul class="ul-form">
		    <li><label>下单时间：</label>
				<input id="beginOperEndTime" name="beginOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${promOrder.beginOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endOperEndTime" name="endOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${promOrder.endOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>推广位名称：</label>
				<form:input path="spreadName"  htmlEscape="false" maxlength="64" style="width:256px;" class="input-medium required"/>
			</li>
			 <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
	         <li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
	         <li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onImport()" value="导入数据"/></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>订单号</th>
				<th>保单号</th>
				<th>推广位ID</th>
				<th>推广位名称</th>
				<th>产品名称</th>
				<th>推广方式</th>
				<th>佣金状态</th>
				<th>订单总额</th>
				<th>下单时间</th>
				<th>推广费</th>
				<th>订单批次号</th>
				<shiro:hasPermission name="prom:promOrder:edit"><th>操作</th></shiro:hasPermission>
			</tr> 
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="promOrder">
			<tr>
			<td>${promOrder.orderId}</td>
			<td>${promOrder.warrantyId}</td>
			<td>${promOrder.spreadId}</td>
			<td>${promOrder.spreadName}</td>
			<td>${promOrder.productName}</td>
			<td>${promOrder.spreadWay}</td>
			<td>${promOrder.commissionStatus}</td>
			<td>${promOrder.orderMoney}</td>
			<td><fmt:formatDate value="${promOrder.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>${promOrder.spreadMoney}</td>
			<td>${promOrder.orderBath}</td>
				<shiro:hasPermission name="prom:promOrder:edit"><td>
    				<a href="${ctx}/prom/promOrder/detail?id=${promOrder.promId}">详情</a>
				</td></shiro:hasPermission>
			</tr> 
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>