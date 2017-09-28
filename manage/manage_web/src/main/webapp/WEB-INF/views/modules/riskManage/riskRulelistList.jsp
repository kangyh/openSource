<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>风控规则列表管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
	
	//验证搜索条件
	var validateFrom = {
		validate: function(form){
			
			var bgTime = $("#beginOperEndTime").val();
			var endTime = $("#endOperEndTime").val();
			var ruleId = $("#ruleId").val();
			if(ruleId !="" && isNaN(ruleId)){
				$("#messageBox").text("规则Id必须为数字!");
				return ;
			}
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
		<li class="active"><a href="${ctx}/risk/riskRulelist/">风控规则列表</a></li>
		<shiro:hasPermission name="risk:riskRulelist:edit"><li><a href="${ctx}/risk/riskRulelist/form">风控规则列表添加</a></li></shiro:hasPermission>
	</ul>
	
	<form action="${ctx}/risk/riskRulelist/" method="post" id="formBtn"></form> 
	
	<form:form id="searchForm" modelAttribute="riskRulelist" action="${ctx}/risk/riskRulelist/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>规则ID：</label>
				<form:input id="ruleId" path="ruleId"   htmlEscape="false" maxlength="12" class="input-medium required"/>
			</li>
			<li><label>风控措施：</label>
				<form:select id="riskControlAction" path="riskControlAction" class="input-xlarge">
               		<form:option value="" label="请选择"/>
					<c:forEach items="${actionList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select id="status" path="status" class="input-xlarge">
               		<form:option value="" label="请选择"/>
					<c:forEach items="${statusList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>监控对象：</label>
                <form:select id="monitorObject" path="monitorObject" class="input-xlarge">
                    <form:option value="" label="请选择"/>
                    <c:forEach items="${monitorList}" var="wStatus">
                        <form:option value="${wStatus.value}" label="${wStatus.name}"/>
                    </c:forEach>
                </form:select>
			</li>
		</ul>
		<ul class="ul-form">
			<li><label>业务类型：</label>
				<form:input  path="buziType"  htmlEscape="false" maxlength="12" class="input-medium required"/>
			</li>
			<li><label>关联风控项：</label>
				<form:input  path="otherItem"  htmlEscape="false" maxlength="12" class="input-medium required"/>
			</li>
			<li><label>规则类型：</label>
				<form:input  path="ruleType"  htmlEscape="false" maxlength="12" class="input-medium required"/>
			</li>
		   <li><label>创建日期：</label>
				<input id="beginOperEndTime" name="beginOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${riskRulelist.beginOperEndTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input id="endOperEndTime" name="endOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${riskRulelist.endOperEndTime}" pattern="yyyy-MM-dd"/>"
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
				<th>规则类型</th>
				<th>监控对象</th>
				<th>业务类型</th>
				<th>规则ID</th>
				<th>规则描述</th>
				<th>风控措施</th>
				<th>风控因素</th>
				<th>关联风控项</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<th>状态</th>
				<th>操作人</th>
				<shiro:hasPermission name="risk:riskRulelist:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="riskRulelist">
			<tr>
				<td>${riskRulelist.ruleType}</td>
				<td>${riskRulelist.monitorObject}</td>
				<td>${riskRulelist.buziType}</td>
				<td>${riskRulelist.ruleId}</td>
				<td>${riskRulelist.ruleDescription}</td>
				<td>${riskRulelist.riskControlAction}</td>
				<td>${riskRulelist.riskControlFact}</td>
				<td>${riskRulelist.otherItem}</td>
				<td>
					<fmt:formatDate value="${riskRulelist.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${riskRulelist.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>${riskRulelist.status}</td>
				<td>${riskRulelist.operator}</td>
				<shiro:hasPermission name="risk:riskRulelist:edit"><td>
    				<a href="${ctx}/risk/riskRulelist/form?id=${riskRulelist.rulelistId}">修改</a>
					<%-- <a href="${ctx}/risk/riskRulelist/delete?id=${riskRulelist.rulelistId}" onclick="return confirmx('确认要删除该风控规则列表吗？', this.href)">删除</a> --%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>