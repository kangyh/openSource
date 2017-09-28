<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>风控管理</title>
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
		<li class="active"><a href="${ctx}/risk/logs">操作日志</a></li>
	</ul>
	<form action="${ctx}/risk/logs" method="post" id="formBtn"></form>
	
	<form:form id="searchForm" modelAttribute="riskLogs" action="${ctx}/risk/logs" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			 
		<ul class="ul-form">
			
			<li><label>操作类型:</label>
				<form:input path="operationType"  htmlEscape="false" maxlength="40" class="input-medium" style="width:125px;" placeholder="请输入操作类型"/>
			</li> 
			<li><label>用户名:</label>
				<form:input path="userId"  htmlEscape="false" maxlength="40" class="input-medium" style="width:125px;" placeholder="请输入用户"/>
			</li> 
			<li><label>操作时间：</label>
					<input id="beginOperEndTime" name="beginOperEndTime" type="text" readonly="readonly" style="width:150px;" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${riskLogs.beginOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endOperEndTime" name="endOperEndTime" type="text" readonly="readonly" maxlength="20" style="width:150px;" class="input-medium Wdate"
					value="<fmt:formatDate value="${riskLogs.endOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清除"/></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
		
	</form:form>

	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>操作类型(增删改查)</th>
			<th>用户名</th>
			<th>访问ip</th>
			<th>操作者</th>
			<th>操作时间</th>
			<th>操作内容</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="riskLogs">
			<tr>
				<td>${riskLogs.operationType}</td>
				<td>${riskLogs.userId}</td>
				<td>${riskLogs.ip}</td>
				<td>${riskLogs.updateUser}</td>
				<td>
					<fmt:formatDate value="${riskLogs.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>${riskLogs.remark}</td>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>