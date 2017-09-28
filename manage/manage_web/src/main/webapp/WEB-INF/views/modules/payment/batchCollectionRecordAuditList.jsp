<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>代收审核管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//验证搜索条件
		var validateFrom = {
			validate: function(form){
				form.submit();
			}
		}

		//搜索
		function onSubmit(){
			validateFrom.validate($("#wrOrderSearchForm"));
		}

		//清空
		function onClear(){
			$("#wrOrderSearchForm").find("input[type='text']").val("");
		}

		//导出
		function onExport(){

		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/batchCollectionRecordAudit/">代收审核管理列表</a></li>
		<shiro:hasPermission name="payment:batchCollectionRecord:edit"><li><a href="${ctx}/payment/batchCollectionRecordAudit/form">委托收款管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="wrOrderSearchForm" modelAttribute="batchCollectionRecord" action="${ctx}/payment/batchCollectionRecordAudit/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="status" name="status" type="hidden" value="R1"/>
		<ul class="ul-form">
			<li><label>批次号：</label>
				<form:input path="batchId" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>商户名称：</label>
				<form:input path="merchantLoginName" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>商户编号：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>商户账号：</label>
				<form:input path="accountName" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" onclick="onExport()" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>批次号</th>
				<th>商户编码</th>
				<th>商户账号</th>
				<th>商户公司名称</th>
				<th>提交总金额</th>
				<th>成功总金额</th>
				<th>提交总笔数</th>
				<th>成功总笔数</th>
				<th>手续费金额</th>
				<th>处理状态</th>
				<th>申请时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="batchCollectionRecord">
			<tr>
				<td>
					${batchCollectionRecord.batchId}
				</td>
				<td>
					${batchCollectionRecord.accountId}
				</td>
				<td>
					${batchCollectionRecord.merchantLoginName}
				</td>
				<td>
					${batchCollectionRecord.merchantCompany}
				</td>
				<td>
					<fmt:formatNumber value="${batchCollectionRecord.totalAmount}" pattern="￥#,##0.00" />
				</td>
				<td>
				</td><fmt:formatNumber value="${batchCollectionRecord.successTotalAmount}" pattern="￥#,##0.00" />
				<td>
					${batchCollectionRecord.totalNum}
				</td>
				<td>
					${batchCollectionRecord.successTotalNum}
				</td>
				<td>
					<%--<c:choose>--%>
						<%--<c:when test="${not empty batchCollectionRecord.feeRatio}">--%>
							<%--<fmt:formatNumber value="${batchCollectionRecord.feeRatio}" pattern="￥.00" />--%>
						<%--</c:when>--%>
						<%--<c:otherwise>--%>
							<%-----%>
						<%--</c:otherwise>--%>
					<%--</c:choose>--%>
				</td>
				<td>
					${fns:getDictLabel(batchCollectionRecord.status, 'BatchCollectionStatus', '')}
				</td>
				<td>
					<fmt:formatDate value="${batchCollectionRecord.createDate}" pattern="yyyy-MM--dd HH:mm:ss"/>
				</td>
				<td>
					<a href="${ctx}/payment/batchCollectionRecordAudit/toAuditDetailList?id=${batchCollectionRecord.batchId}">审核</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>