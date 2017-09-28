<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商户出入金月汇总管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnInitialize").click(function(){
				$.ajax({
					url:"${ctx}/finanical/statement/merchantAccountMonthInitialize",
					success:function(date){
						if(date=="ok"){
							alert("数据生成完毕！");
							$("#searchForm").submit();
						}else{
							alert("数据生成失败！");
						}
					},
					error:function(error){
						alert(error);
					}
				});
			})	
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
	<form:form id="searchForm" modelAttribute="merchantAccountMonthDaily" action="${ctx}/finanical/statement/merchantAccountMonth" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">

			<li><label>商户账号ID：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>商户ID：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			
			<li><label>月份：</label>
				<input name="accountMonth" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${accountMonth}" pattern="yyyy-MM"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
			</li>

			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnInitialize" class="btn btn-primary" type="button" value="生成数据"/></li>
			<li class="clearfix"></li>

		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户账号ID</th>
				<th>商户ID</th>
				<th>商户名称</th>
				<th>出金</th>
				<th>入金</th>
				<th>期初余额</th>
				<th>期末余额</th>
				<th>月份</th>
				<shiro:hasPermission name="trial:merchantAccountMonthDaily:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchantAccountMonthDaily">
			<tr>
				<td>
					${merchantAccountMonthDaily.accountId}
				</td>
				<td>
					${merchantAccountMonthDaily.merchantId}
				</td>
				<td>
					${merchantAccountMonthDaily.merchantCompany}
				</td>
				<td>
					${merchantAccountMonthDaily.outMoney}
				</td>
				<td>
					${merchantAccountMonthDaily.inMoney}
				</td>
				<td>
					${merchantAccountMonthDaily.beginningBalances}
				</td>
				<td>
					${merchantAccountMonthDaily.endingBalances}
				</td>
				<td>
					${merchantAccountMonthDaily.accountMonth}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>