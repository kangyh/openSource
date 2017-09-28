<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/modules/sys/validation.jsp"%>
<html>
<head>
	<title>银行卡bin管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnClear").on("click",function(){
				//console.log("11");
				$("#cardType").val("");
				$("#bankcardNote").val("");
				$("#cardName").val("");
				$("#status").val("");
				$("#searchForm").submit();
			});
			$("#update").attr("disabled",true);
			setInterval(setButtonGray,10000);
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function setButtonGray(){	         
        	$("#update").attr("disabled",false);
        }
        function updateCache(){       	
        	 $("#update").attr("disabled",true);
        	 window.location.href="${ctx}/route/bankCardBin/updateCache"
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/route/bankCardBin/">银行卡bin列表</a></li>
		<shiro:hasPermission name="route:bankCardBin:edit"><li><a href="${ctx}/route/bankCardBin/form">银行卡bin添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="bankCardBin" action="${ctx}/route/bankCardBin/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>发卡行标识：</label>
				<form:input path="bankcardNote" htmlEscape="false" maxlength="12" class="input-medium"/>
			</li>
			<li><label>卡名称：</label>
				<form:input path="cardName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>卡类型：</label>
				<form:select  path="cardType" name="cardType" style="width:100px" >
					<option selected value="">全部</option>
					<form:options items="${fns:getEnumList('rateBankcardType')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
					<%--<option selected value="">所有</option>
						<option value="SAVING" <c:if test="${bankCardBin.cardType=='SAVING'}">selected="selected"</c:if>  maxlength="6"  class="input-xlarge required" >借记卡</option>
						<option value="CREDIT" <c:if test="${bankCardBin.cardType=='CREDIT'}">selected="selected"</c:if>  maxlength="6"  class="input-xlarge required" >贷记卡</option>
					<form:options items="${fns:getEnumList('costType')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>--%>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select id="status" path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getEnumList('commonStatus')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="submit" value="清空" /></li>
			<li class="btns"><input id="update" type="button" class="btn btn-primary" value="更新缓存" onclick="updateCache();"/>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			    <th>序号</th>
			    <th>发卡行名称</th>
				<th>发卡行标识</th>
				<th>发卡行标识长度</th>
				<th>卡号长度</th>
				<th>卡名称</th>
				<th>卡类型</th>
				<th>状态</th>
				<th>修改人</th>
				<shiro:hasPermission name="route:bankCardBin:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bankCardBin" varStatus="i">
			<tr>
			     <td>${i.count}</td>
				<td>${bankCardBin.bankcardName}</td>
				<td>${bankCardBin.bankcardNote}</td>
				<td>${bankCardBin.bankcardNoteLength}</td>
				<td>${bankCardBin.bankcardLength}</td>
				<td>${bankCardBin.cardName}</td>
				<td>${bankCardBin.cardType}</td>
				<td>${bankCardBin.status}</td>
				<td>${bankCardBin.updateName}</td>
				<shiro:hasPermission name="route:bankCardBin:edit"><td>
					<c:if test="${bankCardBin.status == '启用'}">
						<a href="${ctx}/route/bankCardBin/status?id=${bankCardBin.id}&status=DISABL" onclick="return confirmx('确认要禁用吗？', this.href)">禁用</a>
					</c:if>
					<c:if test="${bankCardBin.status == '禁用'}">
						<a href="${ctx}/route/bankCardBin/status?id=${bankCardBin.id}&status=ENABLE" onclick="return confirmx('确认要启用吗？', this.href)">启用</a>
					</c:if>
				    <a href="${ctx}/route/bankCardBin/details?id=${bankCardBin.id}">详情</a>
					<a style="cursor:pointer;" class="checkPass" value-url="${ctx}/route/bankCardBin/form?id=${bankCardBin.id}">修改</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>