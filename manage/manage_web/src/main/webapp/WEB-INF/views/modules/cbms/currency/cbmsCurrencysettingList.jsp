<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>币种信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        function deleteselect(){
            $("#searchForm").find("input[type='text']").val("");
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cbms/cbmsCurrencysetting/">币种信息列表</a></li>

	</ul>
	<form:form id="searchForm" modelAttribute="cbmsCurrencysetting" action="${ctx}/cbms/cbmsCurrencysetting/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>币种编码：</label>
				<form:input path="currencyNo" htmlEscape="false" maxlength="20" class="input-medium" onkeyup="value=value.replace(/[^\a-z\A-Z]/g,'')"/>
			</li>
			<li><label>币种名称：</label>
				<form:input path="currencyName" htmlEscape="false" maxlength="64" class="input-medium" onkeyup="value=value.replace(/[^\u4E00-\u9FA5\a-z\A-Z]/g,'')"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="清空" onclick="deleteselect()"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>币种编码</th>
				<th>币种名称</th>
				<th>状态</th>

			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cbmsCurrencysetting" varStatus="status">
			<tr>
				<td><%--<a href="${ctx}/cbms/cbmsCurrencysetting/form?id=${cbmsCurrencysetting.id}">
					${cbmsCurrencysetting.currencyId}
				</a>--%>${ status.index + 1}
				</td>
				<td>
					${cbmsCurrencysetting.currencyNo}
				</td>
				<td>
					${cbmsCurrencysetting.currencyName}
				</td>
				<td>
					${cbmsCurrencysetting.status}
				</td>

			</tr>
		</c:forEach>
		</tbody>
	</table>
	<br/>
	<br/>
	<br/>

		<div class="pagination">${page}</div>
</body>
</html>