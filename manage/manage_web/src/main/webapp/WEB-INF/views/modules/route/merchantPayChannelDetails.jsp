<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".checkPass").on("click",function(){
				var url = $(this).attr("value-url");
				parent.showDynamicPa();
				parent.enterDynamicPa(url);
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
			return false;
		};
        function productChange(option){
            var name = option.text;
            $("#productName").val(name);
           /* var orinMerchantId = $("#orinMerchantId").val();
            $("#merchantId").val(orinMerchantId);*/

        }
        function addChannel(merchantId) {
            var productCode = $("#productCode").val();
            if(productCode==""){
               alert("请选择产品");
			}else {
                var productName = $("#productName").val();
                var companyName = $("#companyName").val();
                window.location.href="${ctx}/route/merchantPayChannel/add?merchantId="+merchantId+"&companyName="+companyName+"&productCode="+productCode+"&productName="+productName
			}
        }
	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li><a href="${ctx}/route/merchantPayChannel?cache=1">商户列表</a></li>
	<li class="active"><a href="${ctx}/route/merchantPayChannel/details?id=${merchant.userId}">商户通道信息</a></li>
</ul>
<form:form id="searchForm" modelAttribute="product" method="post" class="form-horizontal">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<div class="control-group">
		<label class="control-label">商户名称：</label>
		<div class="controls">
				${merchant.companyName}
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">状态：</label>
		<div class="controls">
				${merchant.userStatus}
		</div>
	</div>
</form:form>
<form:form id="searchForm" modelAttribute="merchantPayChannel" action="${ctx}/route/merchantPayChannel/details?id=${merchant.userId}" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<ul class="ul-form">
		<li><label>产品名称：</label>
			<form:select id="productCode" path="productCode" class="input-medium" onchange="productChange(this.options[this.options.selectedIndex]);">
				<form:option value="" label=""/>
				<form:options items="${fns:getProductList()}" itemLabel="name" itemValue="code" htmlEscape="false"/>
			</form:select>
			<span class="help-inline"><font color="red">*</font> </span>
			<form:hidden path="productName" id="productName"/>
			<%--<form:hidden path="merchantId" name="merchantId"/>--%>
		</li>
		<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
		<li class="clearfix"></li>
	</ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed" style="width:1200px">
	<thead>
	<tr>
		<th>产品代码</th>
		<th>产品名称</th>
		<th>支付通道名称</th>
		<th>银行名称</th>
		<th>通道合作方</th>
		<th>支付通道类型</th>
		<th>银行卡类型</th>
		<shiro:hasPermission name="route:merchantPayChannel:edit"><th>操作</th></shiro:hasPermission>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.list}" var="merchantPayChannel" >
		<tr>
			<td>${merchantPayChannel.productCode}</td>
			<td>${merchantPayChannel.productName}</td>
			<td>${merchantPayChannel.channelCode}</td>
			<td>${merchantPayChannel.bankName}</td>
			<td>${merchantPayChannel.channelPartnerName}</td>
			<td>${merchantPayChannel.channelTypeName}</td>
			<td>${merchantPayChannel.cardTypeName}</td>
			<shiro:hasPermission name="route:merchantPayChannel:edit"><td>
				<a id="a" href="${ctx}/route/merchantPayChannel/delete?id=${merchantPayChannel.id}&merchantId=${merchant.userId}" onclick="return confirmx('确认要删除该支付通道吗？', this.href)">删除</a>
			</td></shiro:hasPermission>
		</tr>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>
<div class="form-actions">
	<input type="button" value="添加通道" class="btn btn-primary" onclick = "addChannel(${merchant.userId});"/>
	<%--<input type="button" value="添加通道" class="checkPass btn btn-primary" value-url="${ctx}/route/merchantPayChannel/add?merchantId=${merchant.userId}&companyName=${merchant.companyName}productCode=${productCode}&productName=${productName}"/>--%>
	<input type="button" class="btn" value="返回" onclick="javascript:window.location='${ctx}/route/merchantPayChannel/list?cache=1';"/>
	<input type="hidden" id="companyName" value="${merchant.companyName}">
</div>
<input type="hidden" id="orinMerchantId" value="${merchant.userId}">
</body>
</html>