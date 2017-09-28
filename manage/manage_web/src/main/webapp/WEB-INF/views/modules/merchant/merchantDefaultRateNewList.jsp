<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/modules/sys/validation.jsp"%>
<html>
<head>
	<title>merchant管理</title>
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
		<li class="active"><a href="${ctx}/merchant/merchantDefaultRateNew/">费率配置列表</a></li>
		<shiro:hasPermission name="merchant:merchantDefaultRateNew:edit"><li><a style="cursor:pointer;" class="checkPass" value-url="${ctx}/merchant/merchantDefaultRateNew/form">费率配置添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="merchantRateNew" action="${ctx}/merchant/merchantDefaultRateNew/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>产品名称：</label>
				<form:select path="productCode" class="input-xlarge" >
					<form:option value="" label="全部"/>
					<form:options items="${fns:getProductList()}" itemLabel="name" itemValue="code" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>创建时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${merchantRateNew.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,maxDate:'#F{$dp.$D(\'endCreateTime\')}'});"/> -
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${merchantRateNew.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,minDate:'#F{$dp.$D(\'beginCreateTime\')}'});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>产品名称</th>
				<th>银行卡类型</th>
				<th>计费类型</th>
				<th>手续费费用</th>
				<th>手续费最大值</th>
				<th>手续费最小值</th>	
				<th>状态</th>
				<th>创建时间</th>
				<shiro:hasPermission name="merchant:merchantDefaultRateNew:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchantRateNew">
			<tr>
				<td>${merchantRateNew.productName}</td>
				<td>${merchantRateNew.bankCardType}</td>
				<td>${merchantRateNew.chargeType}</td>
				<td>
					<c:if test="${merchantRateNew.chargeType == '按比例'}">${merchantRateNew.chargeRatio}%</c:if>
					<c:if test="${merchantRateNew.chargeType == '按笔数'}">${merchantRateNew.chargeFee}元</c:if>
				</td>
				<td>${merchantRateNew.maxFee}</td>
				<td>${merchantRateNew.minFee}</td>
				<td>${merchantRateNew.status}</td>
				<td><fmt:formatDate value="${merchantRateNew.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<shiro:hasPermission name="merchant:merchantDefaultRateNew:edit"><td>
    				<!-- <a href="${ctx}/merchant/merchantDefaultRateNew/detail?id=${merchantRateNew.id}">详情</a>-->
    				<a style="cursor:pointer;" class="checkPass" value-url="${ctx}/merchant/merchantDefaultRateNew/form?id=${merchantRateNew.id}">修改</a>
					<%-- <a href="${ctx}/merchant/merchantDefaultRateNew/delete?id=${merchantRateNew.id}" onclick="return confirmx('确认要删除该费率吗？', this.href)">删除</a> --%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>