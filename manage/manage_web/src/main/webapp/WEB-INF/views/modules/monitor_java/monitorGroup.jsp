<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>服务器日志管理</title>
<meta name="decorator" content="default"/>
<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
<script type="text/javascript">
	
	//验证搜索条件
	var validateFrom = {
		validate: function(form){
			var bgTime = $("#beginCreateTime").val();
			var endTime = $("#endCreateTime").val();
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
	
	/* function onAdd(){
		$("#updateadd").submit();
	} */
</script>
</head>
<body>
	
	<%-- <form id="updateadd" method="get" action="${ctx}/modules/monitorGroup/updateAndAdd"></form> --%>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/modules/monitorGroup/">组信息管理列表</a></li>
		<shiro:hasPermission name="modules:monitorGroup:edit"><li><a style="cursor:pointer;" class="checkPass"  href="${ctx}/modules/monitorGroup/update">组信息添加</a></li></shiro:hasPermission>
	</ul>
	<form action="${ctx}/modules/monitorGroup" method="post" id="formBtn"></form>
	
	<form:form id="searchForm" modelAttribute="monitorGroup" action="${ctx}/modules/monitorGroup" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>创建时间：</label>
				<input id="beginCreateTime" name="beginOperEndTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${monitorGroup.beginOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endCreateTime" name="endOperEndTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${monitorGroup.endOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<!-- <li class="btns"><input id="btnAdd" class="btn btn-primary" type="button" onclick="onAdd()" value="增加"/></li> -->
			<li class="clearfix"></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>分组名称</th>
			<th>分组备注</th>
			<th>创建人</th>
			<th>创建时间</th>
			<th>更新人</th>
			<th>更新时间</th>
			<shiro:hasPermission name="modules:monitorGroup:edit"><th>操作</th></shiro:hasPermission>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="monitorGroup">
			<tr>
				<td>${monitorGroup.groupName}</td>
				<td>${monitorGroup.mark}</td>
				<td>${monitorGroup.createAuthor}</td>
				<td><fmt:formatDate value="${monitorGroup.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${monitorGroup.updateAuthor}</td>
				<td><fmt:formatDate value="${monitorGroup.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<shiro:hasPermission name="modules:monitorGroup:edit"><td><a href="${ctx}/modules/monitorGroup/delete/${monitorGroup.groupId}">删除</a>
					| <a href="${ctx}/modules/monitorGroup/update?groupId=${monitorGroup.groupId}">修改</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>