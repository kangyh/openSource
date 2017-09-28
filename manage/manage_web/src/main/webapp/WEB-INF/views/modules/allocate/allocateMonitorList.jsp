<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>监听管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#addMonitor").on("click", function(){
				var url = $("#addMonitor").attr("value-url");
				$.ajax({
					type : "POST",
					url : "${ctx}/allocate/allocateRecord/isAdminUser",
					dataType : "json",
					async : false,
					success : function(data){
						if(data){
							alert("超级管理员不允许操作此功能");
							return false;
						}else{
							parent.showDynamicPa();
							parent.enterDynamicPa(url);
							return true;
						}
					}
				});		
				
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/allocate/allocateMonitor/">监听阈值列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="allocateMonitor" action="${ctx}/allocate/allocateMonitor/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form"> 
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				<c:if test="${nums == 0}">
					<a id="addMonitor" class="btn btn-warning" value-url="${ctx}/allocate/allocateMonitor/form">新增监听阈值</a>
				</c:if>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>主键</th>
				<th>阈值</th>
				<th>备付金账户</th>
				<th>邮箱</th>
				<th>手机</th>
				<th>是否发送邮件</th>
				<th>是否发送短信</th>
				<th>操作人</th>
				<th>修改人</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<shiro:hasPermission name="allocate:allocateMonitor:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="allocateMonitor">
			<tr>
				<td><a href="${ctx}/allocate/allocateMonitor/details?monitorId=${allocateMonitor.monitorId}">
					${allocateMonitor.monitorId}
				</a></td>
				<td>
					<fmt:formatNumber type="number" value="${allocateMonitor.thresholdValue}" pattern="￥#,###.00" maxFractionDigits="2"/> 
				</td>
				<td>
					${allocateMonitor.monitorAccount}
				</td>
				<td>
					${allocateMonitor.email}
				</td>
				<td>
					${allocateMonitor.phoneNum}
				</td>
				<td>
					<c:if test="${allocateMonitor.emailStatus == 'Y'}">
						是
					</c:if>
					<c:if test="${allocateMonitor.emailStatus == 'N'}">
						否
					</c:if>
				</td>
				<td>
					<c:if test="${allocateMonitor.smsStatus == 'Y'}">
						是
					</c:if>
					<c:if test="${allocateMonitor.smsStatus == 'N'}">
						否
					</c:if>
				</td>
				<td>
					${allocateMonitor.creator}
				</td>
				<td>
					${allocateMonitor.modifier}
				</td>
				<td>
					<fmt:formatDate value="${allocateMonitor.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${allocateMonitor.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="allocate:allocateMonitor:edit"><td>
    				<a href="${ctx}/allocate/allocateMonitor/toUpdatePage?monitorId=${allocateMonitor.monitorId}">修改</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>