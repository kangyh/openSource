<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>已签约合同管理</title>
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/contract/sealContractRecord/">已签约合同列表</a></li>
		<%--<shiro:hasPermission name="contract:sealContractRecord:edit"><li><a href="${ctx}/contract/sealContractRecord/form">已签约合同添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="sealContractRecord" action="${ctx}/contract/sealContractRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<%--<li><label>签约甲方id：</label>--%>
				<%--<form:input path="sealAsideId" htmlEscape="false" maxlength="20" class="input-medium"/>--%>
			<%--</li>--%>
			<li><label>签约甲方：</label>
				<form:input path="sealAsideName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>签约乙方：</label>
				<form:input path="sealBsideName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>合同编码：</label>
				<form:input path="sealContractId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>合同名称：</label>
				<form:input path="sealContractName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>所属项目：</label>
				<form:select id="sysResource" path="sysResource" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="heepay" label="汇付宝"/>
					<form:option value="java" label="汇收银_java"/>
					<form:option value="net" label="汇收银_net"/>
				</form:select>
			</li>
			<%--<li><label>签约产品：</label>--%>
				<%--<form:input path="sealProduct" htmlEscape="false" maxlength="32" class="input-medium"/>--%>
			<%--</li>--%>
			<%--<li><label>签约时间：</label>--%>
				<%--<input name="sealTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"--%>
					<%--value="<fmt:formatDate value="${sealContractRecord.sealTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"--%>
					<%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>--%>
			<%--</li>--%>
				<li><label>签约时间：</label>
					<input name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${sealContractRecord.sealTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
					<input name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${sealContractRecord.sealTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>编号</th>
				<th>合同编码</th>
				<th>合同名称</th>
				<%--<th>签章序列号</th>--%>
				<th>签约甲方id</th>
				<th>签约甲方</th>
				<th>签约乙方</th>
				<th>所属项目</th>
				<%--<th>签约产品</th>--%>
				<th>合同有效期</th>
				<th>合同文件名</th>
				<%--<th>合同URL</th>--%>
				<th>签约时间</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<%--<th>备注</th>--%>
				<shiro:hasPermission name="contract:sealContractRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sealContractRecord">
			<tr>
				<td>
					<%--<a href="${ctx}/contract/sealContractRecord/form?id=${sealContractRecord.id}">--%>
					${sealContractRecord.id}
				<%--</a>--%>
				</td>
				<td>
					${sealContractRecord.sealContractId}
				</td>
				<td>
					${sealContractRecord.sealContractName}
				</td>
				<%--<td>--%>
					<%--${sealContractRecord.electronicsSealId}--%>
				<%--</td>--%>
				<td>
					${sealContractRecord.sealAsideId}
				</td>
				<td>
					${sealContractRecord.sealAsideName}
				</td>
				<td>
					${sealContractRecord.sealBsideName}
				</td>
				<td>
					${sealContractRecord.sysResource}
				</td>
				<%--<td>--%>
					<%--${sealContractRecord.sealProduct}--%>
				<%--</td>--%>
				<td>
					${sealContractRecord.contractEffectiveTime}
				</td>
				<td>
					${sealContractRecord.contractFileName}
				</td>
				<%--<td>--%>
					<%--${sealContractRecord.contractUrl}--%>
				<%--</td>--%>
				<td>
					<fmt:formatDate value="${sealContractRecord.sealTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${sealContractRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${sealContractRecord.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<%--<td>--%>
					<%--${sealContractRecord.remark}--%>
				<%--</td>--%>
				<shiro:hasPermission name="contract:sealContractRecord:edit"><td>
					<a href="${ctx}/contract/sealContractRecord/look?id=${sealContractRecord.id}">查看</a>
    				<%--<a href="${ctx}/contract/sealContractRecord/form?id=${sealContractRecord.id}">修改</a>--%>
					<a href="${ctx}/contract/sealContractRecord/delete?id=${sealContractRecord.id}" onclick="return confirmx('确认要删除该已签约合同吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>