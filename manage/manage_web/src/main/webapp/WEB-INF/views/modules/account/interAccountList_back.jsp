<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>内部账户管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		//验证搜索条件
		var validateFrom = {
			validate: function(form){
				var maxYear = 1;	//默认查询一年
				var bgTime = $("#beginCreateTime").val();
				var endTime = $("#endCreateTime").val();
				if(bgTime=="" && endTime!="" || bgTime!="" && endTime=="" ){
					$("#messageBox").text("请正确设置查询时间范围!");
					return ;
				}
				if( bgTime!="" && endTime!=""){
					if(compareDate(convertDateToJoinStr(bgTime),
									convertDateToJoinStr(endTime)) > 0){
						$("#messageBox").text("起始日期不能大于结束日期!");
						return ;
					}else if(compareYear(convertDateToJoinStr(bgTime),
									convertDateToJoinStr(endTime),maxYear) < 0){
						$("#messageBox").text("查询的时间范围不能超过" + maxYear + "年!");
						return ;
					}
				}
				form.submit();
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
			validateFrom.validate($("#searchForm"));
		}

		//清空
		function onClear(){
			$("#searchForm").find("input[type='text']").val("");
		}

		//导出
		function onExport(){

		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/account/interAccount/">内部账户管理列表</a></li>
		<shiro:hasPermission name="account:merchantAccount:edit"><li><a href="${ctx}/account/interAccount/form">账户管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="merchantAccount" action="${ctx}/account/interAccountQuery/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>账号：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
<%-- 			<li><label>商户编码：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li> --%>
			<li><label>商户公司名称：</label>
				<form:input path="accountName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<%--<li><label>账户类型：</label>--%>
				<%--<form:select path="type" class="input-xlarge">--%>
					<%--<form:option value="" label="全部"/>--%>
					<%--<c:forEach items="${fns:getDictList('MerchantAccountType')}" var="wStatus">--%>
						<%--<form:option value="${wStatus.value}" label="${wStatus.label}"/>--%>
					<%--</c:forEach>--%>
				<%--</form:select>--%>
			<%--</li>--%>

			<%--<li><label>创建时间：</label>--%>
				<%--<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"--%>
					<%--value="<fmt:formatDate value="${merchantAccount.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"--%>
					<%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - --%>
				<%--<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"--%>
					<%--value="<fmt:formatDate value="${merchantAccount.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"--%>
					<%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>--%>
			<%--</li>--%>
			<%--<li><label>状态：</label>--%>
				<%--<form:select path="status" class="input-xlarge">--%>
					<%--<form:option value="" label="全部"/>--%>
					<%--<c:forEach items="${fns:getDictList('MerchantStatus')}" var="wStatus">--%>
						<%--<form:option value="${wStatus.value}" label="${wStatus.label}"/>--%>
					<%--</c:forEach>--%>
				<%--</form:select>--%>
			<%--</li>--%>
			<li><label>升降排序：</label>
				<form:select id="sortOrder" path="sortOrder" class="input-medium">
					<c:forEach items="${fns:getDictList('SortStatus')}" var="sStatus">
						<form:option value="${sStatus.value}" label="${sStatus.label}"/>
					</c:forEach>
				</form:select>
			</li>

			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" onclick="onExport()" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<%--<th>商户编码</th>--%>
			<th>账号</th>
			<th>内部账户名称</th>
		<!-- 	<th>最后操作人</th> -->
			<th>账户余额</th>
			<th>可用余额</th>
			<th>可提现金额</th>
			<th>冻结余额</th>
			<th>状态</th>
			<th>创建时间</th>
			<!-- <th>操作</th> -->
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchantAccount">
			<tr>
				<td>
					<a href="${ctx}/account/interAccountQuery/form?id=${merchantAccount.accountId}">
						${merchantAccount.accountId}
					</a>
				</td>
				<td>${merchantAccount.accountName}</td>
				<td>
					<fmt:formatNumber value="${merchantAccount.balanceAmount}" pattern="￥#,##0.00" />
				
				</td>
				<td>
					<fmt:formatNumber value="${merchantAccount.balanceAvailableAmount}" pattern="￥#,##0.00" />
				</td>
				<td>
					<fmt:formatNumber value="${merchantAccount.balanceAvailableWithdrawAmount}" pattern="￥#,##0.00" />

				</td>
				<td>
					<fmt:formatNumber value="${merchantAccount.balanceFreezedAmount}" pattern="￥#,##0.00" />
				</td>
	<%-- 			<td>
					${merchantAccount.merchantId}
				</td> --%>
				<!-- <td>-</td> -->
				<td>${fns:getDictLabel(merchantAccount.status, 'MerchantStatus', '')}</td>
				<td><fmt:formatDate value="${merchantAccount.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<%-- 		<td>
					<c:if test="${'FREZED' eq merchantAccount.status}">
						<a href="${ctx}/account/accountQuery/toThaw?accountId=${merchantAccount.accountId}">账户解冻</a>
					</c:if>
					<c:if test="${'NORMAL' eq merchantAccount.status}">
						<a href="${ctx}/account/accountQuery/toFrezed?accountId=${merchantAccount.accountId}">账户冻结</a>
					</c:if>
					<c:if test="${'CLOSED' eq merchantAccount.status}">
						<a href="${ctx}/account/accountQuery/toEnable?accountId=${merchantAccount.accountId}">账户启用</a>
					</c:if>
					<c:if test="${'CLOSED' != merchantAccount.status}">
						<a href="${ctx}/account/accountQuery/toClosed?accountId=${merchantAccount.accountId}">账户关闭</a>
					</c:if>
					<a href="${ctx}/account/accountQuery/toFrezedBalanceAmount?accountId=${merchantAccount.accountId}">冻结余额</a>
					<a href="${ctx}/account/accountQuery/toWhawBalanceAmount?accountId=${merchantAccount.accountId}">解冻余额</a>
				</td> --%>
			</tr>

		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>

</body>
</html>