<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商户风控审核管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".checkPass").on("click",function(){
                var url = $(this).attr("value-url");
                parent.showDynamicPa();
                parent.enterDynamicPa(url);
            });
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        //搜索框 清空
        function onClear(){
            $("#searchForm").find("input[type='text']").val("");

            //默认状态
            $("#rcAuditStatus").find("option").removeAttr("selected");
            $("#rcAuditStatus").find("option:eq(0)").attr("selected", "selected");
            $(".select2-chosen:eq(0)").text($("#rcAuditStatus option[selected]").text());
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cbms/merchant/merchantRc/">商户风控列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="merchant" action="${ctx}/cbms/merchant/merchantRc/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户编码：</label>
				<form:input path="userId" htmlEscape="false" maxlength="10" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}
					else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label style="width:100px">商户公司名称：</label>
				<form:input path="companyName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>审核状态：</label>
				<form:select  path="rcAuditStatus" class="input-xlarge">
					<form:option value="" label="全部"/>
					<form:options id="rcAuditStatus" items="${fns:getEnumList('routeStatus')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>创建时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${merchant.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,maxDate:'#F{$dp.$D(\'endCreateTime\')}'});"/> -
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${merchant.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,minDate:'#F{$dp.$D(\'beginCreateTime\')}'});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户编码</th>
				<th>商户公司名称</th>
				<th>法人代表姓名</th>
				<th>企业类别</th>
				<th>创建时间</th>
				<th>审核状态</th>
				<shiro:hasPermission name="merchant:merchantRc:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchant">
			<tr>
				<td><c:out value="${merchant.userId}"/></td>
				<td><c:out value="${merchant.companyName}"/></td>
				<td><c:out value="${merchant.legalRepresentative}"/></td>
				<td><c:out value="${merchant.remark}"/></td>
				<td><fmt:formatDate value="${merchant.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${merchant.rcAuditStatus}</td>
				<shiro:hasPermission name="merchant:merchantRc:edit">
					<td>
						<a href="${ctx}/cbms/merchant/merchantRc/detail?id=${merchant.userId}">详情</a>
						<c:if test="${merchant.rcAuditStatus == '待审核'}">
							<a href="${ctx}/cbms/merchant/merchantRc/audit?id=${merchant.userId}">审核</a>
						</c:if>
						<c:if test="${merchant.rcAuditStatus == '审核不通过'}">
							<a href="${ctx}/cbms/cbmsMerchantSuppliersetting/edit?id=${merchant.userId}&goal=2">修改</a>
						</c:if>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>