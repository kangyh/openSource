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
		<li class="active"><a href="${ctx}/tpds/tpdsCutDay/">日切信息配置列表</a></li>
	<shiro:hasPermission name="tpds:tpdsCutDay:edit">
		<li><a href="${ctx}/tpds/tpdsCutDay/addEntity">添加日切点</a></li></shiro:hasPermission>
	</ul>
	<form action="${ctx}/tpds/tpdsCutDay/" method="post" id="formBtn"></form> 
	<form:form id="searchForm" modelAttribute="tpdsCutDay" action="${ctx}/tpds/tpdsCutDay" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
 		<ul class="ul-form">
			<li><label>日切类型：</label>
				<form:select id="cutType" path="cutType" class="input-xlarge" style="width:200px;">
					<form:option value="" label="全部"/>
					<c:forEach items="${cutDay}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li> 
			<li><label>修改时间：</label>
				<input id="beginOperEndTime" name="beginOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${tpdsCutDay.beginOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endOperEndTime" name="endOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${tpdsCutDay.endOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
	     <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
	     <li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
	     <!-- <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onAdd()" value="添加"/></li> -->
		</ul>  
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>标识</th>
			<th>日切类型</th>
			<th>日切点</th>
			<th>注册时间</th>
			<th>修改时间</th>
			<th>更新人</th>
			<th>状态</th>
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tpdsCutDay" varStatus="i">
				<tr>
					<td>${tpdsCutDay.busiNo}</td>
					<td>${tpdsCutDay.cutType}</td>
					<td>${tpdsCutDay.cutTime}</td>
				    <%-- <td><fmt:formatDate value="${tpdsCutDay.cutTime}" type="both" pattern="HH:mm:ss"/></td> --%>
				    <td><fmt:formatDate value="${tpdsCutDay.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td><fmt:formatDate value="${tpdsCutDay.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				    <td>${tpdsCutDay.updateUser}</td>
				    <td>${tpdsCutDay.status}</td>
					<td>
				        <a href="${ctx}/tpds/tpdsCutDay/updateEntity?tpdsCutDayId=${tpdsCutDay.tpdsCutDayId}">修改</a>
				   </td>
				</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>