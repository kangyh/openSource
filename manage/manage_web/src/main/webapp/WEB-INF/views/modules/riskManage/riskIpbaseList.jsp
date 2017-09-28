<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>风控ip库管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	var validateFrom = {
			validate: function(form){
				
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
		<li class="active"><a href="${ctx}/risk/riskIpbase/">风控ip库列表</a></li>
		<shiro:hasPermission name="risk:riskIpbase:edit"><li><a href="${ctx}/risk/riskIpbase/form">风控ip库添加</a></li></shiro:hasPermission>
	</ul>
	
	<form action="${ctx}/risk/riskIpbase/" method="post" id="formBtn"></form> 
	
	<form:form id="searchForm" modelAttribute="riskIpbase" action="${ctx}/risk/riskIpbase/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>IP：</label>
				<form:input  path="ip"  htmlEscape="false" maxlength="12" class="input-medium required"/>
			</li>
			<li><label>国家：</label>
				<form:input  path="country"  htmlEscape="false" maxlength="12" class="input-medium required"/>
			</li>
			<li><label>省份：</label>
				<form:input  path="province"  htmlEscape="false" maxlength="12" class="input-medium required"/>
			</li>
		</ul>
		<ul class="ul-form">
			<li><label>城市：</label>
				<form:input  path="city"  htmlEscape="false" maxlength="12" class="input-medium required"/>
			</li>
			<li><label>区县：</label>
				<form:input  path="region"  htmlEscape="false" maxlength="12" class="input-medium required"/>
			</li>
			<li><label>省份Id：</label>
				<form:input  path="provinceId"  htmlEscape="false" maxlength="12" class="input-medium required"/>
			</li>
			
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>IP</th>
				<th>国家</th>
				<th>省份</th>
				<th>城市</th>
				<th>区县</th>
				<th>省份Id</th>
				<th>备注</th>
				<shiro:hasPermission name="risk:riskIpbase:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="riskIpbase">
			<tr>
				<td>${riskIpbase.ip}</td>
				<td>${riskIpbase.country}</td>
				<td>${riskIpbase.province}</td>
				<td>${riskIpbase.city}</td>
				<td>${riskIpbase.region}</td>
				<td>${riskIpbase.provinceId}</td>
				<td>${riskIpbase.remark}</td>
				<shiro:hasPermission name="risk:riskIpbase:edit"><td>
    				<a href="${ctx}/risk/riskIpbase/form?id=${riskIpbase.ip}">修改</a>
					<a href="${ctx}/risk/riskIpbase/delete?id=${riskIpbase.ip}" onclick="return confirmx('确认要删除该风控ip库吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>