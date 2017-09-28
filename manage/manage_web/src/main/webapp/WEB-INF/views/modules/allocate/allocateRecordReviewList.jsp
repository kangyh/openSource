<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>调拨成功管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".checkPass").on("click",function(){
				var url = $(this).attr("value-url");
				$.ajax({
					type : "POST",
					url : "${ctx}/allocate/allocateRecordReview/isAdminUser",
					dataType : "json",
					async : false,
					success : function(data){
						if(data){
							alert("超级管理员不允许操作此功能");
							return false;
						}else{
							parent.showDynamicPa();
							parent.enterDynamicPa(url);
							return true;
						}
					}
				});			
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		//搜索
		function onSubmit(){
			if(flg){
				$("#pageNo").val(1);
				validateFrom.validate($("#searchForm"));
			}
		}
		
		//导出Excel
		function exportExcel(){
			var transNo = $("input[name='transNo']").val();
			var status = $("select[name='status']").val();
			var host = "${ctx}/allocate/allocateRecordReview/exportExcel";
			var url = host+"?transNo="+transNo+"&status="+status;
			$('<form method="post" action="' + url + '"></form>').appendTo('body').submit().remove();
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/allocate/allocateRecordReview/">调拨列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="allocateRecord" action="${ctx}/allocate/allocateRecordReview/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>调拨流水号：</label>
				<form:input path="transNo" onchange="checkValue(this.value)" htmlEscape="false" maxlength="26" class="input-medium required"/>
			</li>
			<li><label>调拨状态：</label>
				<form:select path="status" class="input-xlarge ">
                    <form:option value="" label="全部"/>
                    <form:option value="AUDING" label="待审核"/>
                    <form:option value="ADOPT" label="审核通过"/>
                    <form:option value="AUDREJ" label="审核拒绝"/>
                </form:select>
			</li>
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" onclick="onSubmit()" value="查询"/>
				<input class="btn btn-primary" type="button" onclick="exportExcel()" value="导出Excel"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>调拨流水号</th>
				<th>出账的备付金账户</th>
				<th>入账的备付金账户</th>
				<th>调拨金额</th>
				<th>原因</th>
				<th>审核状态</th>
				<th>操作人</th>
				<th>创建时间</th>
				<th>审核时间</th>
				<th>备注</th>
				<shiro:hasPermission name="allocate:allocateRecordReview:view"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="allocateRecord">
			<tr>
				<td><a href="${ctx}/allocate/allocateRecordReview/details?allocateId=${allocateRecord.allocateId}">
					${allocateRecord.transNo}
				</a></td>
				<td>
					${allocateRecord.reserveOutAccountName}
				</td>
				<td>
					${allocateRecord.reserveInAccountName}
				</td>
				<td>
					<fmt:formatNumber type="number" value="${allocateRecord.amount}" pattern="￥0.00" maxFractionDigits="2"/> 
				</td>
				<td>
					${allocateRecord.reason}
				</td>
				<td>
					<c:if test="${allocateRecord.status == 'AUDING'}">
						待审核
					</c:if>
					<c:if test="${allocateRecord.status == 'AUDREJ'}">
						审核拒绝
					</c:if>
					<c:if test="${allocateRecord.status == 'ADOPT'}">
						审核通过
					</c:if>
				</td>
				<td>
					${allocateRecord.creator}
				</td>
				<td>
					<fmt:formatDate value="${allocateRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${allocateRecord.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${allocateRecord.remark}
				</td>
				<shiro:hasPermission name="allocate:allocateRecordReview:view">
					<td>
	    				<c:if test="${allocateRecord.status == 'AUDING'}">
		    				<a href="${ctx}/allocate/allocateRecordReview/details?allocateId=${allocateRecord.allocateId}">查看详情</a>
	    					<a style="cursor:pointer;" class="checkPass" value-url="${ctx}/allocate/allocateRecordReview/toReviewPage?allocateId=${allocateRecord.allocateId}&auditStatus=true">审核</a>
	    				</c:if>
	    				<c:if test="${allocateRecord.status == 'AUDREJ'}">
		    				<a href="${ctx}/allocate/allocateRecordReview/details?allocateId=${allocateRecord.allocateId}">查看详情</a>
	    				</c:if>
	    				<c:if test="${allocateRecord.status == 'ADOPT'}">
		    				<a href="${ctx}/allocate/allocateRecordReview/details?allocateId=${allocateRecord.allocateId}">查看详情</a>
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