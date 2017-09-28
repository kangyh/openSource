<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>导入文件查询管理</title>
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
		function selectedstate(){
            var select = document.getElementById("selectid");
                           select.options[0].selected = true;
        }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cbms/cbmsOrderSum/">导入文件查询列表</a></li>
		<%--<shiro:hasPermission name="cbms:cbmsOrderSum:edit"><li><a href="${ctx}/cbms/cbmsOrderSum/form">导入文件查询添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="cbmsOrderSum" action="${ctx}/cbms/cbmsOrderSum/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户编码：</label>
				<form:input  path="merchantNo" htmlEscape="false" maxlength="64" class="input-medium" onkeyup="if(/\D/.test(this.value)){alert('只能输入数字');this.value='';}"/>
			</li>
			<li><label>导入批次号：</label>
				<form:input  path="importBatchNumber" htmlEscape="false" maxlength="64" class="input-medium" onKeyUp="value=value.replace(/[^\d\a-z\A-Z]/g,'')"/>
			</li>
			<li><label>导入文件名：</label>
				<form:input  path="fileName" htmlEscape="false" maxlength="64" class="input-medium" onkeyup="value=value.replace(/[^\d\u4E00-\u9FA5\a-z\A-Z\.\-]/g,'')"/>
			</li>
			<%--<li><label>状态：</label>
				<select   id="selectid" name="status">
							<option  value="" >请选择</option>
							<option  value="1">成功</option>
							<option value="0" >失败</option>
				</select>
			</li>--%>
			<li><label>导入日期：</label>
				<input name="beginImportTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${cbmsOrderSum.beginImportTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endImportTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${cbmsOrderSum.endImportTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" onclick="deleteselect();selectedstate()" value="清空"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户编码</th>
				<th>商户公司名称</th>
				<th>导入批次号</th>
				<th>支付流水号</th>
				<th>导入文件名</th>
				<th>导入日期</th>
				<th>总申报条数</th>
				<th>总申报金额</th>
				<th>状态</th>
			<%--	<shiro:hasPermission name="cbms:cbmsOrderSum:edit"><th>操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cbmsOrderSum">
			<tr>
				<td>
					<c:out value="${cbmsOrderSum.merchantNo}"/>
				</td>
				<td>
					<c:out value="${cbmsOrderSum.cbmsCompanyName}"/>
				</td>
				<td>
					<c:out value="${cbmsOrderSum.importBatchNumber}"/>
				</td>
				<td>
					<font color="red">	<c:out value="${cbmsOrderSum.comments}"/></font>
				</td>
				<td>
						<c:out value="${cbmsOrderSum.fileName}"/>
				</td>
				<td>
					<fmt:formatDate value="${cbmsOrderSum.importTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
			<c:out value="${cbmsOrderSum.declarationNumber}"/>
				</td>
				<td>
					<c:out value="${cbmsOrderSum.declarationMoney}"/>
				</td>
				<td>
			<c:out value="${cbmsOrderSum.status}"/>
				</td>

			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>