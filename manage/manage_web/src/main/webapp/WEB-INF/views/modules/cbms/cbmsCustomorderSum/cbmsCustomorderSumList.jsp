<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>通关申报订单管理</title>
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
        //重置
        function onClear(){
            $("#searchForm").find("input[type='text']").val("");
            //默认申报海关
            $("#customCode").find("option").removeAttr("selected");
            $("#customCode").find("option:eq(0)").attr("selected", "selected");
            $(".select2-chosen:eq(2)").text($("#customCode option[selected]").text());
            //默认状态
            $("#status").find("option").removeAttr("selected");
            $("#status").find("option:eq(0)").attr("selected", "selected");
            $(".select2-chosen:eq(1)").text($("#status option[selected]").text());
            $("#declareType").find("option").removeAttr("selected");
            $("#declareType").find("option:eq(0)").attr("selected", "selected");
            $(".select2-chosen:eq(0)").text($("#declareType option[selected]").text());
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cbms/cbmsCustomorderSum/">通关申报订单列表</a></li>
        <shiro:hasPermission name="cbms:cbmsOrderform:view"><li><a href="${ctx}/cbms/cbmsOrderform/detail">申报明细查看</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cbmsCustomorderSum" action="${ctx}/cbms/cbmsCustomorderSum/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户编码：</label>
				<form:input path="merchantNo" htmlEscape="false" maxlength="10" class="input-medium"
							onkeyup="value=value.replace(/[^\d]/g,'') "
							/>
			</li>
			<li><label>导入批次号：</label>
				<form:input path="importBatchNumber" htmlEscape="false" maxlength="64" class="input-medium"
							onKeyUp="value=value.replace(/[^\d\a-z\A-Z]/g,'')"/>
			</li>
			<li><label>申报批次号：</label>
				<form:input path="declarationBatchHumber" htmlEscape="false" maxlength="64" class="input-medium"
							onKeyUp="value=value.replace(/[^\d\a-z\A-Z]/g,'')"/>
			</li>
            <li><label>申报时间：</label>
                <input id="beginDeclarationTime" name="beginDeclarationTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                       value="<fmt:formatDate value="${cbmsCustomorderSum.beginDeclarationTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,maxDate:'#F{$dp.$D(\'endDeclarationTime\')}'});"/> -
                <input id="endDeclarationTime" name="endDeclarationTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                       value="<fmt:formatDate value="${cbmsCustomorderSum.endDeclarationTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,minDate:'#F{$dp.$D(\'beginDeclarationTime\')}'});"/>
            </li>
			<li><label>申报海关：</label>
				<form:select path="customCode" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options id="customCode" items="${customsettinglist}" itemLabel="chinaName" itemValue="customNo" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="1" label="待报送"/>
					<form:option value="2" label="报送处理中"/>
					<form:option value="3" label="已报送"/>
				</form:select>
			</li>
			<li><label>申报方式：</label>
				<form:select path="declareType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="1" label="文件上传"/>
					<form:option value="2" label="API接口"/>
				</form:select>
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
                <th>申报总笔数</th>
                <th>成功总笔数</th>
                <th>失败总笔数</th>
                <th>申报总金额</th>
                <th>成功总金额</th>
                <th>失败总金额</th>
                <th>手续费</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <c:if test="${not empty cbmsCustomorderSumSummary}">
                    <td><fmt:formatNumber value="${cbmsCustomorderSumSummary.declarationNumberCount}" /></td>
                    <td><fmt:formatNumber value="${cbmsCustomorderSumSummary.declarationNumberSuccess}" /></td>
                    <td><fmt:formatNumber value="${cbmsCustomorderSumSummary.declarationNumberFailed}" /></td>
                    <td><fmt:formatNumber value="${cbmsCustomorderSumSummary.declarationMoneyCount}" /></td>
                    <td><fmt:formatNumber value="${cbmsCustomorderSumSummary.declarationMoneySuccess}" /></td>
                    <td><fmt:formatNumber value="${cbmsCustomorderSumSummary.declarationMoneyFailed}" /></td>
                    <td><fmt:formatNumber value="${cbmsCustomorderSumSummary.feeCount}" /></td>
                </c:if>
            </tr>
        </tbody>
    </table><br><br>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户编码</th>
				<th>商户公司名称</th>
                <th>商户账号</th>
				<th>导入批次号</th>
				<th>申报批次号</th>
				<th>申报海关</th>
				<th>总笔数</th>
				<th>总金额</th>
				<th>手续费</th>
                <th>状态</th>
                <th>创建时间</th>
                <th>申报时间</th>
                <th>申报方式</th>
                <th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cbmsCustomorderSum">
			<tr>
				<td>
					<c:out value="${cbmsCustomorderSum.merchantNo}"/>
			    </td>
				<td>
					<c:out value="${cbmsCustomorderSum.cbmsCompanyName}"/>
				</td>
				<td>
					<c:out value="${cbmsCustomorderSum.loginName}"/>
				</td>
				<td>
					<c:out value="${cbmsCustomorderSum.importBatchNumber}"/>
				</td>
				<td>
					<c:out value="${cbmsCustomorderSum.declarationBatchHumber}"/>
				</td>
				<td>
					<c:out value="${cbmsCustomorderSum.customName}"/>
				</td>
				<td>
					<c:out value="${cbmsCustomorderSum.declarationNumber}"/>
				</td>
				<td>
					<c:out value="${cbmsCustomorderSum.declarationMoney}"/>
				</td>
				<td>
					<c:out value="${cbmsCustomorderSum.fee}"/>
				</td>
                <td>
					<c:out value="${cbmsCustomorderSum.status}"/>
                </td>
                <td>
                    <fmt:formatDate value="${cbmsCustomorderSum.importTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <td>
                    <fmt:formatDate value="${cbmsCustomorderSum.declarationTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
				<td>
					<c:out value="${cbmsCustomorderSum.declareType}"/>
				</td>
				<shiro:hasPermission name="cbms:cbmsCustomorderSum:edit"><td>
    				<a href="${ctx}/cbms/cbmsOrderform/?declarationBatchHumber=${cbmsCustomorderSum.declarationBatchHumber}&customCode=${cbmsCustomorderSum.customCode}">查看</a>
    				<%--<a href="${ctx}/cbms/cbmsCustomorderSum/form?id=${cbmsCustomorderSum.id}">修改</a>--%>
					<%--<a href="${ctx}/cbms/cbmsCustomorderSum/delete?id=${cbmsCustomorderSum.id}" onclick="return confirmx('确认要删除该通关申报订单吗？', this.href)">删除</a>--%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>