<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/modules/sys/validation.jsp"%>
<html>
<head>
	<title>标准产品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
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
		<li class="active"><a href="${ctx}/merchant/normProduct/">标准产品列表</a></li>
		<shiro:hasPermission name="merchant:normProduct:edit"><li><a href="${ctx}/merchant/normProduct/form">标准产品添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="normProduct" action="${ctx}/merchant/normProduct/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>标准产品编码：</label>
				<form:input path="code" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>标准产品编码</th>
				<th>产品编码</th>
				<th>产品名称</th>
				<th>业务类型</th>
				<th>交易类型</th>
				<th>银行卡类型</th>
				<th>计费类型</th>
				<th>手续费费用</th>
				<th>手续费最大值</th>
				<th>手续费最小值</th>
				<shiro:hasPermission name="merchant:normProduct:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="normProduct">
			<tr>
				<td>${normProduct.code}</td>
				<td>${normProduct.productCode}</td>
				<td>${normProduct.productName}</td>
				<td>${normProduct.businessType}</td>
				<td>${normProduct.trxType}</td>
				<td>${normProduct.bankCardType}</td>
				<td>${normProduct.chargeType}</td>
				<td>
					<c:if test="${normProduct.chargeType == '按比例'}">${normProduct.chargeRatio}%</c:if>
					<c:if test="${normProduct.chargeType == '按笔数'}">${normProduct.chargeFee}元</c:if>
				</td>
				<td>${normProduct.maxFee}</td>
				<td>${normProduct.minFee}</td>
				<shiro:hasPermission name="merchant:normProduct:edit"><td>
					<a href="${ctx}/merchant/normProduct/delete?id=${normProduct.id}" onclick="return confirmx('确认要删除该标准产品吗？', this.href)">删除</a>
				  | <a style="cursor:pointer;" class="checkPass" value-url="${ctx}/merchant/normProduct/form?id=${normProduct.id}">修改</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>