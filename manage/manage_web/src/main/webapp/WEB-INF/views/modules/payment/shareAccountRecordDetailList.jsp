<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分润明细管理</title>
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
		<li class="active"><a href="${ctx}/share/shareAccountRecordDetail/">分润明细列表</a></li>
		</ul>

	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>分润明细id</th>
				<th>分润号</th>
				<th>出款商户编码</th>
				<th>入款商户编码</th>
				<th>入款商户账号</th>
				<th>入款商户公司名称</th>
				<th>分润金额</th>
				<th>分润状态</th>
				<th>分润发起时间</th>
				<th>最后修改时间</th>
				
				<shiro:hasPermission name="share:shareAccountRecordDetail:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="shareAccountRecordDetail">
			<tr>
				<td>
					${shareAccountRecordDetail.shareDetailId}
				</td>
				<td>
					${shareAccountRecordDetail.shareId}
				</td>
				<td>
					${shareAccountRecordDetail.masterMerchantId}
				</td>
				<td>
					${shareAccountRecordDetail.slaverMerchantId}
				</td>
				<td>
					${shareAccountRecordDetail.slaverMerchantLoginName}
				</td>
				<td>
					${shareAccountRecordDetail.slaverMerchantCompany}
				</td>
				<td>
					${shareAccountRecordDetail.shareAmount}
				</td>
				<td>
					${fns:getDictLabel(shareAccountRecordDetail.status, 'ShareStatus', '')}
				</td>
				<td>
					<fmt:formatDate value="${shareAccountRecordDetail.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${shareAccountRecordDetail.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				
				
				
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">
			<div class="form-actions">
				<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div></div>
</body>
</html>