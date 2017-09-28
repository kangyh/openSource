<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>批量代付汇总表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            $("input").attr("onkeyup","value=value.replace(/[^0-9\u4E00-\u9FA5\a-z\A-Z\.\-]/g,'')")
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/hgms/hgmsBatchPayRecord/">当日代付记录汇总查询列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="hgmsBatchPayRecord" action="${ctx}/hgms/hgmsBatchPayRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>订单批次号：</label>
				<form:input path="batchId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商户名称：</label>
				<form:input path="merchantCompany" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>交易创建时间：</label>
				<input name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${hgmsBatchPayRecord.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${hgmsBatchPayRecord.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>文件名：</label>
				<form:input path="batchFileName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="deleteselect()"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>订单批次号</th>
				<th>汇付宝批次号</th>
				<th>商户名称</th>
				<th>交易时间</th>
				<th>文件名</th>
				<th>总笔数</th>
				<th>总金额</th>
				<th>总手续费</th>
				<th>成功笔数</th>
				<th>成功总额</th>
				<th>失败笔数</th>
				<th>失败金额</th>
				<shiro:hasPermission name="hgms:hgmsBatchPayRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hgmsBatchPayRecord">
			<tr>
				<td>
					${hgmsBatchPayRecord.batchId}
				</td>
				<td>
						${hgmsBatchPayRecord.extend1}
				</td>
				<td>
					${hgmsBatchPayRecord.merchantCompany}
				</td>
				<td>
					<fmt:formatDate value="${hgmsBatchPayRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
						${hgmsBatchPayRecord.batchFileName}
				</td>
				<td>
						${hgmsBatchPayRecord.totalNum}
				</td>
				<td>
						<fmt:formatNumber value="${hgmsBatchPayRecord.totalAmount}" pattern="￥0.00" />
				</td>
				<td>
					<fmt:formatNumber value="${hgmsBatchPayRecord.feeTotalAmount}" pattern="￥0.00" />
				</td>

				<td>
					${hgmsBatchPayRecord.successTotalNum}
				</td>
				<td>
						<fmt:formatNumber value="${hgmsBatchPayRecord.successTotalAmount}" pattern="￥0.00" />
				</td>
				<td>
					${hgmsBatchPayRecord.failedTotalNum}
				</td>
				<td>
						<fmt:formatNumber value="${hgmsBatchPayRecord.failedTotalAmount}" pattern="￥0.00" />
				</td>


				<shiro:hasPermission name="hgms:hgmsBatchPayRecordDetail:view"><td>
    				<a href="${ctx}/hgms/hgmsBatchPayRecordDetail/?batchId=${hgmsBatchPayRecord.batchId}">详情</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>