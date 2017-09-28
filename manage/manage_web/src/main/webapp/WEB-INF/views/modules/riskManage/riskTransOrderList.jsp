<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>交易风控管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
	//验证搜索条件
	var validateFrom = {
		validate: function(form){
			
			$("#searchForm").submit();
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
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/risk/riskTransOrder/">交易风控管理列表</a></li>
	</ul>
	
	<form action="${ctx}/risk/riskTransOrder/" method="post" id="formBtn"></form> 
	
	<form:form id="searchForm" modelAttribute="riskTransOrderVo" action="${ctx}/risk/riskTransOrder/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户订单号：</label>
				<form:input path="merchantOrderNo"  htmlEscape="false" maxlength="30" class="input-medium required"/>
			</li>
			<li><label>商户编号：</label>
				<form:input path="merchantId"  htmlEscape="false" maxlength="12" class="input-medium required"/>
			</li>
			<li><label>产品类型：</label>
				<form:select id="productType" path="productType" class="input-xlarge">
               		<form:option value="" label="请选择"/>
					<c:forEach items="${productList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form">
			<li><label>交易类型：</label>
				<form:select id="transType" path="transType" class="input-xlarge">
               		<form:option value="" label="请选择"/>
					<c:forEach items="${tranTypeList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
			  <li><label>创建日期：</label>
				<input id="beginTime" name="beginTime" type="text" class="input-medium Wdate"
					value="<fmt:formatDate value="${riskTransOrderVo.beginTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input id="endTime" name="endTime" type="text" class="input-medium Wdate"
					value="<fmt:formatDate value="${riskTransOrderVo.endTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			 </li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户订单号</th>
				<th>交易单号</th>
				<th>产品名称</th>
				<th>商户编号</th>
				<th>商户公司名称</th>
				<th>交易类型</th>
				<th>交易金额</th>
				<th>通道编码</th>
				<%--<th>通道名称</th>--%>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="riskTransOrderVo">
			<tr>
				<td>${riskTransOrderVo.merchantOrderNo}</td>
				<td>${riskTransOrderVo.transNo}</td>
				<td>${riskTransOrderVo.productName}</td>
				<td>${riskTransOrderVo.merchantId}</td>
				<td>${riskTransOrderVo.merchantCompany}</td>
				<td>${riskTransOrderVo.transType}</td>
				<td>${riskTransOrderVo.transAmount}</td>
				<td>${riskTransOrderVo.channelCode}</td>
				<%--<td>${riskTransOrderVo.channelName}</td>--%>
				<td><fmt:formatDate value="${riskTransOrderVo.createDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><a href="${ctx}/risk/riskTransOrder/toDetail?orderId=${riskTransOrderVo.orderId}">详情</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>