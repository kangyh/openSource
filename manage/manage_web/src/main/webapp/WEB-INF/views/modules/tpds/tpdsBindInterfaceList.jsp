<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商户台账信息</title>
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
		
		//添加
		function onAdd(){
			$("#add").submit();
		}
	</script>
</head>
<body>
    <ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/tpds/tpdsBindInterface/">银行绑定接口信息列表</a></li>
	<shiro:hasPermission name="tpds:tpdsBindInterface:edit">
		<li><a href="${ctx}/tpds/tpdsBindInterface/addEntity">添加银行绑定接口</a></li></shiro:hasPermission>
	</ul>
	<form action="${ctx}/tpds/tpdsBindInterface/" method="post" id="formBtn"></form> 
	<form:form id="searchForm" modelAttribute="tpdsBindInterface" action="${ctx}/tpds/tpdsBindInterface" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
		<ul class="ul-form">
			
			<li><label>商户编号：</label>
				<form:input path="merchantNo"  htmlEscape="false" maxlength="16" style="width:256px;" class="input-medium required"/>
			</li>
			<li><label>银行名称：</label>
				<form:input path="bankName"  htmlEscape="false" maxlength="64" style="width:256px;" class="input-medium required"/>
			</li>
		</ul> 
 		<ul class="ul-form">
			<li><label>修改时间：</label>
				<input id="beginOperEndTime" name="beginOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${tpdsBindInterface.beginOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endOperEndTime" name="endOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${tpdsBindInterface.endOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
	     <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
	     <li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
		</ul>  
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>商户编号</th>
			<th>银行代码</th>
			<th>银行简称</th>
			<th>银行名称</th>
			<th>注册时间</th>
			<th>修改时间</th>
			<th>更新人</th>
			<th>服务路径</th>
			<th>状态</th>
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tpdsBindInterface" varStatus="i">
				<tr>
					<td>${tpdsBindInterface.merchantNo}</td>
					<td>${tpdsBindInterface.bankNo}</td>
					<td>${tpdsBindInterface.bankCode}</td>
					<td>${tpdsBindInterface.bankName}</td>
				    <td><fmt:formatDate value="${tpdsBindInterface.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td><fmt:formatDate value="${tpdsBindInterface.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				    <td>${tpdsBindInterface.updateUser}</td>
				    <td>${tpdsBindInterface.packDir}</td>
				    <td>${tpdsBindInterface.status}</td>
					<td>
				 	<a href="${ctx}/tpds/tpdsBindInterface/updateEntity?tpdsBindId=${tpdsBindInterface.tpdsBindId}&pageNo=${page.pageNo}">修改</a>
				   </td>
				</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>