<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分润比例模板管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
			$(document).ready(function() {
				
			});
		
		var validateFrom = {
			validate: function(form){
				var templetId = $("#templetId").val().trim();
				var templetName = $("#templetName").val().trim();
				if(!checkSafe(templetId)){
					
					$("#messageBox").text("模板号包含非法字符!");
					return ;
				}
				if(!checkSafe(templetName)){
					
					$("#messageBox").text("模板名称包含非法字符!");
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
		<li class="active"><a href="${ctx}/prom/promProfitTemplet/">分润比例模板列表</a></li>
		<shiro:hasPermission name="prom:promProfitTemplet:edit"><li><a href="${ctx}/prom/promProfitTemplet/form">分润比例模板添加</a></li></shiro:hasPermission>
	</ul>
	
	<form action="${ctx}/prom/promProfitTemplet/" method="post" id="formBtn"></form> 
	<form:form id="searchForm" modelAttribute="promProfitTemplet" action="${ctx}/prom/promProfitTemplet/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>模板号：</label>
				<form:input path="templetId"  id="templetId" htmlEscape="false" maxlength="64" style="width:256px;" class="input-medium required"/>
			</li>
			<li><label>模板名称：</label>
				<form:input path="templetName"  id="templetName" htmlEscape="false" maxlength="64" style="width:256px;" class="input-medium required"/>
			</li>
		</ul>
		<ul class="ul-form">
		  <li><label>创建时间：</label>
				<input id="beginOperEndTime" name="beginOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${promProfitTemplet.beginOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endOperEndTime" name="endOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${promProfitTemplet.endOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
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
				<th>模板号</th>
				<th>模板名称</th>
				<th>汇元比例</th>
				<th>推广者比例</th>
				<th>推荐人比例</th>
				<th>上级比例</th>
				<th>上上级比例</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th>修改人</th>
				<th>修改时间</th>
				<shiro:hasPermission name="prom:promProfitTemplet:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="promProfitTemplet">
			<tr>
				<td>${promProfitTemplet.templetId}</td>
				<td>${promProfitTemplet.templetName}</td>
				<td>${promProfitTemplet.huiyuanScale}</td>
				<td>${promProfitTemplet.gearScale}</td>
				<td>${promProfitTemplet.spreaderScale}</td>
				<td>${promProfitTemplet.higherLevelScale}</td>
				<td>${promProfitTemplet.highestLevelScale}</td>
				<td><fmt:formatDate value="${promProfitTemplet.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${promProfitTemplet.creator}</td>
				<td><fmt:formatDate value="${promProfitTemplet.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${promProfitTemplet.updatePeople}</td>
				<shiro:hasPermission name="prom:promProfitTemplet:edit"><td>
    				<a href="${ctx}/prom/promProfitTemplet/form?promId=${promProfitTemplet.promId}">修改</a>
					<a href="${ctx}/prom/promProfitTemplet/delete?id=${promProfitTemplet.promId}" onclick="return confirmx('确认要删除该分润比例模板吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>