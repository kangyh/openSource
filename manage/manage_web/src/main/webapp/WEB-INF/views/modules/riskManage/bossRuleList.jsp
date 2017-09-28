<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>报表规则配置管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
	//验证搜索条件
	var validateFrom = {
		validate: function(form){
			
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
		<li class="active"><a href="${ctx}/risk/bossRule/">报表规则配置列表</a></li>
		<shiro:hasPermission name="risk:bossRule:edit"><li><a href="${ctx}/risk/bossRule/add">报表规则配置添加</a></li></shiro:hasPermission>
	</ul>
	
	<form action="${ctx}/risk/bossRule/" method="post" id="formBtn"></form> 
	
	<form:form id="searchForm" modelAttribute="bossRule" action="${ctx}/risk/bossRule/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>开始日期：</label>
				<input id="beginOperEndTime" name="beginOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${bossRule.beginOperEndTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input id="endOperEndTime" name="endOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${bossRule.endOperEndTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			 <li><label>结束日期：</label>
				<input id="beginOperEndTime1" name="beginOperEndTime1" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${bossRule.beginOperEndTime1}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input id="endOperEndTime1" name="endOperEndTime1" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${bossRule.endOperEndTime1}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li> 
	     <li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
	     <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
		</ul> 
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			<th>开始日期</th>
			<th>结束日期</th>
			<th>创建者</th>
			<th>创建时间</th>
			<th>更新者</th>
			<th>更新时间</th>
			<th>任务开始时间</th>
			<th>任务结束时间</th>
			<th>耗时</th>
			<th>任务状态</th>
			<th>备注</th>
			<shiro:hasPermission name="risk:bossRule:edit"><th>操作</th></shiro:hasPermission>
		    </tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bossRule">
			<tr>
				<td>
					<fmt:formatDate value="${bossRule.startDate}" type="both" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${bossRule.endDate}" type="both" pattern="yyyy-MM-dd"/>
				</td>
				<td>${bossRule.creatAuthor}</td>
				<td>
					<fmt:formatDate value="${bossRule.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>${bossRule.updateAuthor}</td>
				<td>
					<fmt:formatDate value="${bossRule.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${bossRule.jobStartTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${bossRule.jobEndTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>${bossRule.takeTime}</td>
				<td>${bossRule.jobStatus}</td>
				<td>${bossRule.remark}</td>
				<shiro:hasPermission name="risk:bossRule:edit"><td>
					<c:choose>
						<c:when test="${bossRule.jobStatus == '未执行'}">
							<a href="${ctx}/risk/bossRule/form?id=${bossRule.ruleId}">修改</a>
						</c:when>
						<c:otherwise>
							--
						</c:otherwise>
					</c:choose>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>