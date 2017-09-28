<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>同申报批次号详情管理</title>
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
        //搜索
        function onSubmit(){
            validateFrom.validate($("#searchForm"));
        }

        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }

        //重置
        function onClear(){
            $("#searchForm").find("input[type='text']").val("");
            //默认申报海关
            $("#code").find("option").removeAttr("selected");
            $("#code").find("option:eq(0)").attr("selected", "selected");
            $(".select2-chosen:eq(0)").text($("#code option[selected]").text());
            //海关报送状态
            $("#status").find("option").removeAttr("selected");
            $("#status").find("option:eq(0)").attr("selected", "selected");
            $(".select2-chosen:eq(0)").text($("#status option[selected]").text());
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cbms/cbmsCustomorderSum/">通关申报订单列表</a></li>
		<%--<li class="active"><a href="${ctx}/cbms/cbmsOrderform/">同申报批次号详情列表</a></li>--%>
		<%--<shiro:hasPermission name="cbms:cbmsOrderform:edit"><li><a href="${ctx}/cbms/cbmsOrderform/form">同申报批次号详情添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="cbmsOrderform" action="${ctx}/cbms/cbmsOrderform/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="declarationBatchHumber"  name="declarationBatchHumber" type="hidden" value="${cbmsCustomorderSum.declarationBatchHumber}" />
		<input id="customCode" name="customCode" type="hidden" value="${cbmsCustomorderSum.customCode}" />
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
                <th>订单号</th>
				<th>导入批次号</th>
				<th>申报批次号</th>
				<th>海关编码</th>
				<th>支付交易编码</th>
				<th>电子订单编号</th>
				<th>电商企业名称</th>
				<th>支付人姓名</th>
				<th>付款人证件号</th>
				<th>支付人电话</th>
				<th>支付金额</th>
				<th>付款时间</th>
				<th>业务状态</th>
				<th>交易状态</th>
				<th>标识</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cbmsOrderform">
			<tr>
                <td>
					<c:out value="${cbmsOrderform.orderId}"/>
                </td>
				<td>
					<c:out value="${cbmsOrderform.importBatchNumber}"/>
				</a></td>
				<td>
					<c:out value="${cbmsOrderform.sentCustomsNumber}"/>
				</td>
				<td>
					<c:out value="${cbmsOrderform.customCode}"/>
					<%--${fns:getDictLabel(cbmsOrderform.customCode, '', '')}--%>
				</td>
				<td>
					<c:out value="${cbmsOrderform.orgPayTransno}"/>
				</td>
				<td>
					<c:out value="${cbmsOrderform.orderFormNo}"/>
				</td>
				<td>
					<c:out value="${cbmsOrderform.busEnterpriseName}"/>
				</td>
				<td>
					<c:out value="${cbmsOrderform.payName}"/>
				</td>
				<td>
					<c:out value="${cbmsOrderform.payErcertificateNo}"/>
				</td>
				<td>
					<c:out value="${cbmsOrderform.payerPhone}"/>
				</td>
				<td>
					<c:out value="${cbmsOrderform.rmbPayAmount}"/>
				</td>
				<td>
					<fmt:formatDate value="${cbmsOrderform.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
			<c:out value="${cbmsOrderform.customStatus}"/>
					<%--${fns:getDictLabel(cbmsOrderform.customStatus, '', '')}--%>
				</td>
				<td>
					<c:out value="${cbmsOrderform.transStatus}"/>
				</td>
				<td>
					<c:out value="${cbmsOrderform.identification}"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	<div class="pagination">${page}</div>
</body>
</html>