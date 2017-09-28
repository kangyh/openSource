<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商户管理</title>
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
        }
        $(function(){
            $("#messageBox").show();
        });
	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li class="active"><a href="${ctx}/cbms/merchant/merchant/">跨境商户列表</a></li>
	<shiro:hasPermission name="cbms:cbmsMerchantSuppliersetting:edit">
		<li><a href="${ctx}/cbms/cbmsMerchantSuppliersetting/form">跨境商户添加</a></li>
	</shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="cbmsSuppliersetting" action="${ctx}/cbms/merchant/merchant/" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<ul class="ul-form">
		<li><label>商户编码：</label>
			<form:input path="merchantNo" htmlEscape="false" maxlength="10" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}
					else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
		</li>
		<li><label style="width:100px">商户公司名称：</label>
			<form:input path="companyName" htmlEscape="false" maxlength="50" class="input-medium"
						onkeyup="value=value.replace(/[^\u4E00-\u9FA5|\uFF08|\uFF09\a-zA-Z]/g,'')"
						onFocus="if(value==defaultValue){value='';this.style.color='#000'}"
						onBlur="if(!value){value=defaultValue;this.style.color='#999'}"
						style="color:#999999"/>
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
		<th>商户账号</th>
		<th>商户公司名称</th>
		<th>维系员工</th>
		<th>联系人</th>
		<th>状态</th>
		<th>创建时间</th>
		<th>自动审核开关状态</th>
		<th>操作</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.list}" var="merchant">
		<tr>
			<td>${merchant.merchantNo}</td>
			<td>${merchant.email}</td>
			<td>${merchant.companyName}</td>
			<td>${merchant.inchargerId}</td>
			<td>${merchant.contactName}</td>
			<td>${merchant.status}</td>
			<td><fmt:formatDate value="${merchant.createdTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>
				<c:if test="${merchant.automaticAudit == '1'}">启用</c:if>
				<c:if test="${merchant.automaticAudit == '2' || merchant.automaticAudit ==null}">禁用</c:if>
			</td>
			<td>
				<a href="${ctx}/cbms/cbmsMerchantSuppliersetting/detail?id=${merchant.merchantNo}">查看</a>
				<shiro:hasPermission name="merchant:merchant:edit">
					<c:if test="${merchant.status == '正常'}">
						<a href="${ctx}/cbms/merchant/merchant/status?id=${merchant.merchantNo}&status=FREZED">禁用</a>
					</c:if>
					<c:if test="${merchant.status == '冻结'}">
						<a href="${ctx}/cbms/merchant/merchant/status?id=${merchant.merchantNo}&status=NORMAL">启用</a>
					</c:if>
					<c:if test="${merchant.automaticAudit == '1'}">
						<a href="${ctx}/cbms/merchant/merchant/updateAutomaticAudit?id=${merchant.merchantNo}&automaticAudit=2">禁用自动审核</a>
					</c:if>
					<c:if test="${merchant.automaticAudit == '2' || merchant.automaticAudit ==null}">
						<a href="${ctx}/cbms/merchant/merchant/updateAutomaticAudit?id=${merchant.merchantNo}&automaticAudit=1">启动自动审核</a>
					</c:if>

				</shiro:hasPermission>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>