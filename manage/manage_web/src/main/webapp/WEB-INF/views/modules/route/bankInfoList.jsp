<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/modules/sys/validation.jsp"%>
<html>
<head>
	<title>单表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnClear").on("click",function(){
				$("#bankNo").val("");
				$("#bankName").val("");
				$("#status").val("");
				$("#searchForm").submit();
			});
			$("#updateBankCache").attr("disabled",true);
			setInterval(setButtonGray,10000);
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        };       
        function setButtonGray(){        
        	$("#updateBankCache").attr("disabled",false);
        }
        function updateCache(){       	
        		 $("#updateBankCache").attr("disabled",true);
        		 window.location.href="${ctx}/route/bankInfo/updateCache"
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/route/bankInfo/">银行列表</a></li>
		<shiro:hasPermission name="route:bankInfo:edit"><li><a href="${ctx}/route/bankInfo/form">银行列表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="bankInfo" action="${ctx}/route/bankInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		    <li><label>银行代码：</label>
		    	<form:input id="bankNo"  path="bankNo" htmlEscape="false" maxlength="3" class="input-medium" />
	        </li>
	        <li><label>银行名称：</label>
	        	<form:input id="bankName" path="bankName" htmlEscape="false" maxlength="100" class="input-medium" />
	      	</li>
			<li><label>状态：</label>
				<form:select id="status" path="status"  class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getEnumList('commonStatus')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="submit" value="清空" /></li>
			<li class="btns"><input id="updateBankCache" type="button" class="btn btn-primary"  value="更新缓存" onclick="updateCache();"/>
			<li class="clearfix"></li>		
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed"  style="width:1000px">
		<thead>
			<tr>
			    <tr>
					<th>银行代码</th>
					<th>银行名称</th>
					<th>银行简称</th>
					<th>创建时间</th>
					<th>状态</th>
					<th>修改人</th>
					<shiro:hasPermission name="route:bankInfo:edit"><th>操作</th></shiro:hasPermission>
				</tr>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bankInfo">
			<tr>
			    <td><a href="${ctx}/route/bankInfo/details?id=${bankInfo.id}">${bankInfo.bankNo}</a></td>
				<td>${bankInfo.bankName}</td>
				<td>${bankInfo.bankCode}</td>
				<td><fmt:formatDate value="${bankInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${bankInfo.status}</td>
				<td>${bankInfo.updateName}</td>
				<shiro:hasPermission name="route:bankInfo:edit"><td>
			        <c:if test="${bankInfo.status == '启用'}">
    					<a href="${ctx}/route/bankInfo/status?id=${bankInfo.id}&status=DISABL&bankNo=${bankInfoFind.bankNo}&bankName=${bankInfoFind.bankName}&selectStatus=${bankInfoFind.status}&pageNo=${page.pageNo}"  onclick="return confirmx('确认要禁用吗？', this.href)">禁用</a>
    				</c:if>
    				<c:if test="${bankInfo.status == '禁用'}">
    					<a href="${ctx}/route/bankInfo/status?id=${bankInfo.id}&status=ENABLE&bankNo=${bankInfoFind.bankNo}&bankName=${bankInfoFind.bankName}&selectStatus=${bankInfoFind.status}&pageNo=${page.pageNo}" onclick="return confirmx('确认要启用吗？', this.href)">启用</a>
    				</c:if>
				    <a href="${ctx}/route/bankInfo/details?id=${bankInfo.id}">查看</a>
    				<%--<a href="${ctx}/route/bankInfo/form?id=${bankInfo.id}&bankNo=${bankInfoFind.bankNo}&bankName=${bankInfoFind.bankName}&selectStatus=${bankInfoFind.status}&pageNo=${page.pageNo}">修改</a>--%>
					<a style="cursor:pointer;" class="checkPass" value-url="${ctx}/route/bankInfo/form?id=${bankInfo.id}&bankNoFind=${bankInfoFind.bankNo}&bankNameFind=${bankInfoFind.bankName}&selectStatusFind=${bankInfoFind.status}&pageNo=${page.pageNo}">修改</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<%--<input type="hidden" id="selectStatus" value="${selectStatus}">--%>
	<%--<input type="hidden" id="bankNoHide" value="${bankNo}">
	<input type="hidden" id="bankNameHide" value="${bankName}">
	<input type="hidden" id="statusHide" value="${status}">
	<input type="hidden" id="pageNo" value="${page.pageNo}">--%>
</body>
</html>