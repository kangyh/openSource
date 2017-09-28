<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>监控告警</title>
<meta name="decorator" content="default"/>
<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
<script type="text/javascript">
	
	//验证搜索条件
	var validateFrom = {
		validate: function(form){
			
		    var groupName = $("#groupName").val();
			var pattern = new RegExp("[`~!#$^@&*()=|{}':;',\\[\\]<>/?~！#￥……&*（）——|{}【】‘；：”“'。，、？]") 
			if(pattern.test(groupName)){
				$("#messageBox").text("名称输入不合法，请重新输入");
				return false;
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
		<li class="active"><a href="${ctx}/monitors/infoGroup">组信息列表</a></li>
		<shiro:hasPermission name="monitors:infoGroup:edit">
		<li><a href="${ctx}/monitors/infoGroup/addAndUpdate">添加组信息</a></li></shiro:hasPermission>
	</ul>
	<form action="${ctx}/monitors/infoGroup" method="post" id="formBtn"></form> 
	
	<form:form id="searchForm" modelAttribute="infoGroup" action="${ctx}/monitors/infoGroup" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			
			<li><label>组名称：</label>
				<form:input path="groupName" id="groupName"  htmlEscape="false" maxlength="10"  class="input-medium required" placeholder="请输入组名称"/>
			</li>
			<li><label>创建时间：</label>
				<input id="beginOperEndTime" name="beginOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${infoGroup.beginOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endOperEndTime" name="endOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${infoGroup.endOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
			<th>组编码</th>
			<th>组名称</th>
			<th>创建人</th>
			<th>修改人</th>
			<th>创建时间</th>
			<th>修改时间</th>
			<th>发送类型</th>
			<th>状态</th>
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="infoGroup" varStatus="i">
				<tr>
					<td>${infoGroup.groupCode}</td>
					<td>${infoGroup.groupName}</td>
					<td>${infoGroup.createUser}</td>
					<td>${infoGroup.updateUser}</td>
				    
					<td><fmt:formatDate value="${infoGroup.createTime}" type="both" pattern="yyyy-MM-dd HH:MM:ss"/></td>
					<td><fmt:formatDate value="${infoGroup.updateTime}" type="both" pattern="yyyy-MM-dd HH:MM:ss"/></td>
					<td>${infoGroup.remark}</td>
					<td  <c:choose>
			     		<c:when test="${infoGroup.status=='启用'}">
			     		 style="background:#6cf683"
			     		</c:when>
			     		<c:when test="${infoGroup.status=='禁用'}">
			     		 style="background:#707c9b"
			     		</c:when>
			     	</c:choose>>${infoGroup.status}</td>
					<td>
					 <%--  <a href="${ctx}/monitors/infoGroup/delete/${infoGroup.groupId}">删除</a>
					|  --%>
					<shiro:hasPermission name="monitors:infoGroup:edit"><a href="${ctx}/monitors/infoGroup/addAndUpdate?groupId=${infoGroup.groupId}">更新</a></shiro:hasPermission>
				   </td>
				</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>