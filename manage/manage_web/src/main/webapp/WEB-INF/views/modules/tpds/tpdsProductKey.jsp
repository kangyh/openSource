<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>商户产品密钥配置</title>
<meta name="decorator" content="default"/>
<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
<script type="text/javascript">

//动态口令验证
$(document).ready(function() {
	$(".checkPass").on("click",function(){
		var url = $(this).attr("value-url");
		parent.showDynamicPa();
		parent.enterDynamicPa(url);
	})
});

	//验证搜索条件
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
	
	function Sel2change(field){
		$("#channelName").val(field);
	}
	
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/tpds/tpdsProductKey">商户产品密钥配置列表</a></li>
		<shiro:hasPermission name="tpds:tpdsProductKey:edit">
		<li><a href="${ctx}/tpds/tpdsProductKey/addEntity">添加商户产品密钥配置</a></li></shiro:hasPermission>
	</ul>
	<form action="${ctx}/tpds/tpdsProductKey" method="post" id="formBtn"></form> 
	
	<form:form id="searchForm" modelAttribute="tpdsProductKey" action="${ctx}/tpds/tpdsProductKey" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>产品名称：</label>
				<form:select id="productCode" path="productCode" class="input-xlarge" style="width:200px;">
					<form:option value="" label="全部"/>
					<c:forEach items="${productType}" var="productTypes">
						<form:option value="${productTypes.value}" label="${productTypes.name}"/>
					</c:forEach>
				</form:select>
			</li> 
			<li><label>交易类型：</label>
				<form:select id="transType" path="transType" class="input-xlarge" style="width:200px;">
					<form:option value="" label="全部"/>
					<c:forEach items="${transType}" var="transTypes">
						<form:option value="${transTypes.value}" label="${transTypes.name}"/>
					</c:forEach>
				</form:select>
			</li> 
			</ul>  
			<ul class="ul-form">
			<li><label>创建时间：</label>
				<input id="beginOperEndTime" name="beginOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${tpdsProductKey.beginOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endOperEndTime" name="endOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${tpdsProductKey.endOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
	        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			</ul> 
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>商户编码</th>
			<th>交易类型</th>
			<th>产品名称</th>
			<th>产品编码</th>
			<th>密钥</th>
			<th>创建时间</th>
			<th>创建人</th>
			<th>修改时间</th>
			<th>修改人</th>
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tpdsProductKey" varStatus="i">
				<tr>
					<td>${tpdsProductKey.merchantNo}</td>
					<td>${tpdsProductKey.transType}</td>
					<td>${tpdsProductKey.productName}</td>
					<td>${tpdsProductKey.productCode}</td>
					<td>${tpdsProductKey.signKey}</td>
					<td><fmt:formatDate value="${tpdsProductKey.createTime}" type="both" pattern="yyyy-MM-dd hh:MM:ss"/></td>
					<td>${tpdsProductKey.createOperator}</td>
					<td><fmt:formatDate value="${tpdsProductKey.updateTime}" type="both" pattern="yyyy-MM-dd hh:MM:ss"/></td>
					<td>${tpdsProductKey.updateOperator}</td>
					<td>
				     <a href="${ctx}/tpds/tpdsProductKey/updateEntity?keyId=${tpdsProductKey.keyId}&pageNo=${page.pageNo}">修改</a>
				   </td>
				</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>