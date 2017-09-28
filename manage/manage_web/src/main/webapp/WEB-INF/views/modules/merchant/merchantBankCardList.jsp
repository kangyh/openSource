<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>merchant管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".getBankNo").click(function(){
				var _this = $(this);
				var id = $(this).parent().parent().find("input").val();
				$.post("${ctx}/merchant/merchantBankCard/getBankNo",{"id":id},function(data){
					_this.parent().parent().find("td").eq(2).text(data);
				},"text");
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
		<li class="active"><a href="">银行卡列表</a></li>
		<shiro:hasPermission name="merchant:merchantBankCard:edit"><li><a href="${ctx}/merchant/merchantBankCard/form">银行卡添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="merchantBankCard" action="${ctx}/merchant/merchantBankCard/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<form:hidden path="merchantId"/>
		<ul class="ul-form">
		<!-- 	<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li> -->
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户账号</th>
				<th>银行名称</th>
				<th>银行账号</th>
				<th>开户银行名称</th>
				<th>开户名称</th>
				<shiro:hasPermission name="merchant:merchantBankCard:get">
				<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchantBankCard">
			<tr>
				<input type="hidden" value="${merchantBankCard.id}" />
				<td>${merchantBankCard.merchantId}</td>
				<td>${merchantBankCard.bankName}</td>
				<td>${merchantBankCard.bankNo}</td>
				<td>${merchantBankCard.openBankName}</td>
				<td>${merchantBankCard.openName}</td>
				<shiro:hasPermission name="merchant:merchantBankCard:get">
				<td>
					<a class="getBankNo" href="javascript:void(0);">查看</a>
				</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<div class="form-actions">
		 <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</body>
</html>