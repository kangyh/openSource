<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>推广位绑定管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function() {
		
	});
	//验证搜索条件
	var validateFrom = {
		validate: function(form){
			
			var bindingId = $("#bindingId").val().trim();
			var merchantId = $("#merchantId").val().trim();
			var promotionId = $("#promotionId").val().trim();
			if(!checkSafe(bindingId)){
				
				$("#messageBox").text("绑定编码包含非法字符!");
				return ;
			}
			if(!checkSafe(promotionId)){
				
				$("#messageBox").text("推广位Id包含非法字符!");
				return ;
			}
			if(!checkSafe(merchantId)){
				
				$("#messageBox").text("商户编号包含非法字符!");
				return ;
			}
			
			var bgTime = $("#beginOperEndTime").val();
			var endTime = $("#endOperEndTime").val();
			var bgTime1 = $("#beginOperEndTime1").val();
			var endTime1 = $("#endOperEndTime1").val();
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
			
			if(bgTime1=="" && endTime1!="" || bgTime1!="" && endTime1=="" ){
				$("#messageBox").text("请正确设置查询时间范围!");
				return ;
			}
			if( bgTime1!="" && endTime1!=""){
				if(compareDate(convertDateToJoinStr(bgTime1),
								convertDateToJoinStr(endTime1)) > 0){
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
		<li class="active"><a href="${ctx}/prom/promBinding/">推广位绑定列表</a></li>
		<shiro:hasPermission name="prom:promBinding:edit"><li><a href="${ctx}/prom/promBinding/form">推广位绑定添加</a></li></shiro:hasPermission>
	</ul>
	<form action="${ctx}/prom/promBinding/" method="post" id="formBtn"></form> 
	<form:form id="searchForm" modelAttribute="promBinding" action="${ctx}/prom/promBinding/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			
			<li><label>绑定编码：</label>
				<form:input path="bindingId"  id="bindingId" htmlEscape="false" maxlength="64" style="width:256px;" class="input-medium required"/>
			</li>
			<li><label>商户编码：</label>
				<form:input path="merchantId"  id="merchantId" htmlEscape="false" maxlength="64" style="width:256px;" class="input-medium required"/>
			</li>
			<li><label>推广位ID：</label>
				<form:input path="promotionId"  id="promotionId" htmlEscape="false" maxlength="64" style="width:256px;" class="input-medium required"/>
			</li>
		</ul> 
		<ul class="ul-form">
		    <li><label>生效时间：</label>
				<input id="beginOperEndTime" name="beginOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${promBinding.beginOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endOperEndTime" name="endOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${promBinding.endOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>失效时间：</label>
				<input id="beginOperEndTime1" name="beginOperEndTime1" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${promBinding.beginOperEndTime1}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endOperEndTime1" name="endOperEndTime1" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${promBinding.endOperEndTime1}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>状态：</label>
				<form:select id="status" path="status" class="input-xlarge required">
					<form:option value="" label=""/>
                    <c:forEach items="${status}" var="wStatus">
                        <form:option value="${wStatus.value}" label="${wStatus.name}"/>
                    </c:forEach>
                </form:select>
			</li>
			 <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
	         <li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>绑定编码</th>
				<th>商户编码</th>
				<th>商户名称</th>
				<th>推广位ID</th>
				<th>生效时间</th>
				<th>失效时间</th>
				<th>状态</th>
				<shiro:hasPermission name="prom:promBinding:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="promBinding">
			<tr>
				<td>${promBinding.bindingId}</td>
				<td>${promBinding.merchantId}</td>
				<td>${promBinding.merchantName}</td>
				<td>${promBinding.promotionId}</td>
				<td><fmt:formatDate value="${promBinding.effectiveTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><fmt:formatDate value="${promBinding.loseTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${promBinding.status}</td>
				<shiro:hasPermission name="prom:promBinding:edit"><td>
    				<a href="${ctx}/prom/promBinding/form?id=${promBinding.promId}">修改</a>
					<a href="${ctx}/prom/promBinding/delete?id=${promBinding.promId}" onclick="return confirmx('确认要删除该推广位绑定吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>