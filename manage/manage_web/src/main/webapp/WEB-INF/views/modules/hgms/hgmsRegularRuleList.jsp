<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>规则表管理</title>
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

            $("#ruleType").find("option").removeAttr("selected");
            $("#ruleType").find("option:eq(0)").attr("selected", "selected");
            $(".select2-chosen:eq(0)").text($("#ruleType option[selected]").text());

            $("#ruleMode").find("option").removeAttr("selected");
            $("#ruleMode").find("option:eq(0)").attr("selected", "selected");
            $(".select2-chosen:eq(1)").text($("#ruleMode option[selected]").text());

            $("#ruleForms").find("option").removeAttr("selected");
            $("#ruleForms").find("option:eq(0)").attr("selected", "selected");
            $(".select2-chosen:eq(2)").text($("#ruleForms option[selected]").text());


            $("#transWay").find("option").removeAttr("selected");
            $("#transWay").find("option:eq(0)").attr("selected", "selected");
            $(".select2-chosen:eq(3)").text($("#transWay option[selected]").text());
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/hgms/hgmsRegularRule/">规则表列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="hgmsRegularRule" action="${ctx}/hgms/hgmsRegularRule/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>规则ID：</label>
				<form:input path="ruleId" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>创建商户ID：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>任务名称：</label>
				<form:input path="ruleName" htmlEscape="false" maxlength="256" class="input-medium"/>
			</li>
			<li><label>规则类型</label>
				<form:select path="ruleType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${hgmsBusinessType}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>规则模式：</label>
				<form:select path="ruleMode" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${hgmsRuleMode}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>规则形式：</label>
				<form:select path="ruleForms" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${hgmsRuleForms}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>交易类型</label>
				<form:select path="transWay" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${hgmsTransWay}" itemLabel="name" itemValue="value" htmlEscape="false"/>
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
				<th>规则ID</th>
				<th>创建商户编码</th>
				<th>创建商户名称</th>
				<th>任务名称</th>
				<th>规则类型</th>
				<th>规则模式</th>
				<th>规则形式</th>
				<th>规则状态</th>
				<th>归集金额</th>
				<th>创建时间</th>
				<th>修改人</th>
				<th>更新时间</th>
				<th>交易类型</th>
				<th>规则模式详情</th>
				<shiro:hasPermission name="hgms:hgmsRegularRule:view"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hgmsRegularRule">
			<tr>
				<td>
					${hgmsRegularRule.ruleId}
				</td>
				<td>
					${hgmsRegularRule.merchantId}
				</td>
				<td>
					${hgmsRegularRule.companyName}
				</td>
				<td>
					${hgmsRegularRule.ruleName}
				</td>
				<td>
					${hgmsRegularRule.ruleType}
				</td>
				<td>
					${hgmsRegularRule.ruleMode}
				</td>
				<td>
					${hgmsRegularRule.ruleForms}
				</td>
				<td>
					${hgmsRegularRule.userStatus}
				</td>
				<td>
					${hgmsRegularRule.payAmount}
				</td>
				<td>
					<fmt:formatDate value="${hgmsRegularRule.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${hgmsRegularRule.updateUserId}
				</td>
				<td>
					<fmt:formatDate value="${hgmsRegularRule.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${hgmsRegularRule.transWay}
				</td>
				<td>
					${hgmsRegularRule.ruleDetail}
				</td>
                <td>
                    <shiro:hasPermission name="hgms:hgmsRegularRule:view">
                        <a href="${ctx}/hgms/hgmsRegularRule/detail?id=${hgmsRegularRule.ruleId}">查看</a>
                    </shiro:hasPermission>
                </td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>