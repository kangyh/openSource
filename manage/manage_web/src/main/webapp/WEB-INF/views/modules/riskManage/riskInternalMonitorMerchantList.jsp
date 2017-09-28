<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>内部监控商户配制表管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
	var validateFrom = {
			validate: function(form){
				
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
		<li class="active"><a href="${ctx}/risk/riskInternalMonitorMerchant/">内部监控商户配制表列表</a></li>
		<shiro:hasPermission name="risk:riskInternalMonitorMerchant:edit"><li><a href="${ctx}/risk/riskInternalMonitorMerchant/form">内部监控商户配制表添加</a></li></shiro:hasPermission>
	</ul>
	
	<form action="${ctx}/risk/riskInternalMonitorMerchant/" method="post" id="formBtn"></form> 
	
	<form:form id="searchForm" modelAttribute="riskInternalMonitorMerchant" action="${ctx}/risk/riskInternalMonitorMerchant/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户编码：</label>
				<form:input path="merchantId"  htmlEscape="false" maxlength="50" class="input-medium required"/>
			</li>
			<li><label>商户名称：</label>
				<form:input path="merchantName"  htmlEscape="false" maxlength="50" class="input-medium required"/>
			</li>
		</ul>
		<ul class="ul-form">
			<li><label>产品名称：</label>
				<form:select id="productCode" path="productCode" class="input-xlarge">
               		<form:option value="" label="请选择"/>
					<c:forEach items="${productNameList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
		      <li><label>创建日期：</label>
				<input id="beginOperEndTime" name="beginOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${riskInternalMonitorMerchant.beginOperEndTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input id="endOperEndTime" name="endOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${riskInternalMonitorMerchant.endOperEndTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			 </li> 
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
		</ul>
	</form:form>
	<div id="messageBox" style="font-size: 15px; color: red;"></div>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户编码</th>
				<th>商户名称</th>
				<th>产品编码</th>
				<th>产品名称</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>频率(分钟)</th>
				<th>阈值(%)</th>
				<th>变化率(%)</th>
				<th>操作人</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<shiro:hasPermission name="risk:riskInternalMonitorMerchant:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="riskInternalMonitorMerchant">
			<tr>
				<td>${riskInternalMonitorMerchant.merchantId}</td>
				<td>${riskInternalMonitorMerchant.merchantName}</td>
				<td>${riskInternalMonitorMerchant.productCode}</td>
				<td>${riskInternalMonitorMerchant.productName}</td>
				<td>${riskInternalMonitorMerchant.beginTime}</td>
				<td>${riskInternalMonitorMerchant.endTime}</td>
				<td>${riskInternalMonitorMerchant.frequency}</td>
				<td>${riskInternalMonitorMerchant.threshold}</td>
				<td>${riskInternalMonitorMerchant.changeRate}</td>
				<td>${riskInternalMonitorMerchant.operator}</td>
				<td>
					<fmt:formatDate value="${riskInternalMonitorMerchant.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${riskInternalMonitorMerchant.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="risk:riskInternalMonitorMerchant:edit"><td>
    				<a href="${ctx}/risk/riskInternalMonitorMerchant/form?id=${riskInternalMonitorMerchant.internalMerchantId}">修改</a>
					<a href="${ctx}/risk/riskInternalMonitorMerchant/delete?id=${riskInternalMonitorMerchant.internalMerchantId}" onclick="return confirmx('确认要删除该内部监控商户配制表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>