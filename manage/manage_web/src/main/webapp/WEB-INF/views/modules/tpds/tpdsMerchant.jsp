<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>资金存管</title>
<meta name="decorator" content="default"/>
<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
<style>
       #main {
           margin: 50px;
       }
   </style>
<script type="text/javascript">
	//验证搜索条件
	var validateFrom = {
		validate: function(form){
			
			
			var companyName = $("#companyName").val();
			var pattern = new RegExp("[`~!#$^@&*()=|{}':;',\\[\\]<>/?~！#￥……&*（）——|{}【】‘；：”“'。，、？]") 
			if(pattern.test(companyName)){
				$("#messageBox").text("商户公司名称输入不合法，请重新输入");
				return false;
			}
			
			
			var merchantNo = $("#merchantNo").val();
			if(isNaN(merchantNo)){
				$("#messageBox").text("商户编码 请输入数字类型");
				$("#merchantNo").val("");
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
	
	function Sel2change(field){
		$("#channelName").val(field);
	}
	
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/tpds/tpdsMerchant">商户列表</a></li>
	<shiro:hasPermission name="tpds:tpdsMerchant:edit">
		<li><a href="${ctx}/tpds/tpdsMerchant/addAndUpdate">添加新商户</a></li></shiro:hasPermission>
</ul>
<form action="${ctx}/tpds/tpdsMerchant" method="post" id="formBtn"></form>

<form:form id="searchForm" modelAttribute="tpdsMerchant" action="${ctx}/tpds/tpdsMerchant" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		 
	<ul class="ul-form">
		
		<li><label>商户编码:</label>
			<form:input path="merchantNo" id="merchantNo" htmlEscape="false" maxlength="40" class="input-medium" style="width:125px;" placeholder="请输入商户编码"/>
		</li> 
		<li><label>商户公司名称:</label>
			<form:input path="companyName" id="companyName" htmlEscape="false" maxlength="40" class="input-medium" style="width:125px;" placeholder="请输入商户公司名称"/>
		</li> 
		<li><label>创建时间：</label>
				<input id="beginOperEndTime" name="beginOperEndTime" type="text" readonly="readonly" placeholder="请选择开始时间" style="width:150px;" maxlength="20" class="input-medium Wdate"
				value="<fmt:formatDate value="${tpdsMerchant.beginOperEndTime}" pattern="yyyy-MM-dd"/>"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
			<input id="endOperEndTime" name="endOperEndTime" type="text" readonly="readonly" placeholder="请选择结束时间" style="width:150px;" class="input-medium Wdate"
				value="<fmt:formatDate value="${tpdsMerchant.endOperEndTime}" pattern="yyyy-MM-dd"/>"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
		</li>
		
		<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清除"/></li>
		<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
	</ul>
	<div id="messageBox" style="font-size: 15px; color: red;"></div>
	
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead>
	<tr>
		<th>商户编码</th>
		<th>商户公司名称</th>
		<th>联系人</th>
		<th>创建时间</th>
		<th>创建人</th>
		<th>更新时间</th>
		<th>更新人</th>
		<th>操作</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.list}" var="tpdsMerchant">
		<tr>
			<td>${tpdsMerchant.merchantNo}</td>
			<td>${tpdsMerchant.companyName}</td>
			<td>${tpdsMerchant.contactor}</td>
			<td><fmt:formatDate value="${tpdsMerchant.createTime}" pattern="yyyy-MM-dd"/></td>
			<td>${tpdsMerchant.createAuthor}</td>
			<td><fmt:formatDate value="${tpdsMerchant.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>${tpdsMerchant.updateAuthor}</td>
			<td>
   				<a href="${ctx}/tpds/tpdsMerchant/addAndUpdate?read=1&merchantId=${tpdsMerchant.merchantId}">查看</a>
   			  | <a href="${ctx}/tpds/tpdsMerchant/addAndUpdate?merchantId=${tpdsMerchant.merchantId}">修改</a>
   			  | <a href="${ctx}/tpds/tpdsMerchant/binding/${tpdsMerchant.merchantId}">绑定银行卡</a>
   			  | <a href="${ctx}/tpds/tpdsMerchant/viewList/${tpdsMerchant.merchantNo}">银行卡信息</a>
   			</td>
		<tr>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>