<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>汇聚财定时任务管理</title>
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
        //搜索框 清空
        function onClear(){
            $("#searchForm").find("input[type='text']").val("");
            $("#taskType").find("option:eq(0)").attr("selected", "selected");
            $(".select2-chosen:eq(0)").text($("#taskType option[selected]").text());
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/hgms/hgmsOrderTaskRecord/">汇聚财定时任务列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="hgmsOrderTaskRecord" action="${ctx}/hgms/hgmsOrderTaskRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>编号：</label>
				<form:input path="id" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>理论执行时间：</label>
				<input id="checkBeginDate" name="checkBeginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${hgmsOrderTaskRecord.checkBeginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,maxDate:'#F{$dp.$D(\'checkEndDate\')}'});"/> -
				<input id="checkEndDate" name="checkEndDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${hgmsOrderTaskRecord.checkEndDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,minDate:'#F{$dp.$D(\'checkBeginDate\')}'});"/>
			</li>
			<li><label>任务类型：</label>
                <form:select path="taskType" class="input-medium">
                    <form:option value="" label="全部"/>
                    <form:options items="${hgmsOrderTaskType}" itemLabel="name" itemValue="value" htmlEscape="false"/>
                </form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
            <li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>编号</th>
				<th>任务名称</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<th>成功时间</th>
				<th>理论开始时间</th>
				<th>理论结束时间</th>
				<th>任务类型</th>
				<th>订单数量</th>
				<th>状态</th>
				<th>操作人</th>
				<th>错误记录</th>
				<shiro:hasPermission name="hgms:hgmsOrderTaskRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hgmsOrderTaskRecord">
			<tr>
				<td>
					${hgmsOrderTaskRecord.id}
				</td>
				<td>
					${hgmsOrderTaskRecord.taskName}
				</td>
				<td>
					<fmt:formatDate value="${hgmsOrderTaskRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${hgmsOrderTaskRecord.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${hgmsOrderTaskRecord.successTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${hgmsOrderTaskRecord.checkBeginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${hgmsOrderTaskRecord.checkEndDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${hgmsOrderTaskRecord.taskType}
				</td>
				<td>
					${hgmsOrderTaskRecord.orderAmount}
				</td>
				<td>
					${hgmsOrderTaskRecord.status}
				</td>
				<td>
					${hgmsOrderTaskRecord.operator}
				</td>
				<td>
					${hgmsOrderTaskRecord.errorRecord}
				</td>
                <shiro:hasPermission name="hgms:hgmsOrderTaskRecord:edit"><td>
                    <c:if test="${hgmsOrderTaskRecord.status == '失败' || hgmsOrderTaskRecord.status == '待处理'}" >
                        <c:if test="${hgmsOrderTaskRecord.taskType == '执行订单'}" >
                            <a class="btn btn-primary" href="${ctx}/hgms/hgmsOrderTaskRecord/executeOrder?id=${hgmsOrderTaskRecord.id}" onclick = "document.getElementById('lightsucc').style.display='block'">执行订单</a>
                        </c:if>
                        <c:if test="${hgmsOrderTaskRecord.taskType == '生成订单'}" >
                            <a class="btn btn-primary" href="${ctx}/hgms/hgmsOrderTaskRecord/createOrder?id=${hgmsOrderTaskRecord.id}" onclick = "document.getElementById('lightsucc').style.display='block'">生成订单</a>
                        </c:if>
                    </c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
    <div class="pagination">${page}</div>
    <div id="lightsucc" class="white_content" >
        <font size="4px" color="white">后台正在执行任务，请耐心等待</font>
    </div>
</body>
</html>