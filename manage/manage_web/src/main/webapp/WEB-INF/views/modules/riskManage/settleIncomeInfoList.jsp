<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>出入金配置管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        //用于验证
        var flg = true;

		$(document).ready(function() {
			
		});

        //验证搜索条件
        var validateFrom = {
            validate: function(form){
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
            if(flg){
                $("#pageNo").val(1);
                validateFrom.validate($("#searchForm"));
            }
        }

        //清空
        function onClear(){
            $("#searchForm").find("input[type='text']").val("");

            $("#messageBox").text("");
            flg = true;

            $("#incomeDirection").find("option").removeAttr("selected");
            $("#incomeDirection").find("option:eq(0)").attr("selected","selected");
            $(".select2-chosen:eq(0)").text($("#incomeDirection option[selected]").text());

            $("#businessType").find("option").removeAttr("selected");
            $("#businessType").find("option:eq(0)").attr("selected","selected");
            $(".select2-chosen:eq(1)").text($("#businessType option[selected]").text());

            $("#transType").find("option").removeAttr("selected");
            $("#transType").find("option:eq(0)").attr("selected","selected");
            $(".select2-chosen:eq(2)").text($("#transType option[selected]").text());

            $("#productCode").find("option").removeAttr("selected");
            $("#productCode").find("option:eq(0)").attr("selected","selected");
            $(".select2-chosen:eq(3)").text($("#productCode option[selected]").text());
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/riskManage/settleIncomeInfo/">出入金配置列表</a></li>
		<shiro:hasPermission name="riskManage:settleIncomeInfo:edit"><li><a href="${ctx}/riskManage/settleIncomeInfo/form">添加出入金配置</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="settleIncomeInfo" action="${ctx}/riskManage/settleIncomeInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>出入金类型：</label>
				<form:select id="incomeDirection" path="incomeDirection" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${ioList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>业务类型：</label>
				<form:select id="businessType" path="businessType" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${bList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>交易类型：</label>
				<form:select id="transType" path="transType" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${tList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form">
			<li><label>产品编码：</label>
				<form:select id="productCode" path="productCode" class="input-xlarge required" >
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getProductList()}" itemLabel="name" itemValue="code" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>出入金类型</th>
				<th>业务类型</th>
				<th>交易类型</th>
				<th>产品编码</th>
				<th>是否结算</th>
				<th>创建时间</th>
				<th>创建者</th>
				<th>更新时间</th>
				<th>更新者</th>
				<th>备注</th>
				<th>状态</th>
				<shiro:hasPermission name="riskManage:settleIncomeInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="settleIncomeInfo">
			<tr>
				<td>${settleIncomeInfo.incomeDirection}</td>
				<td>${settleIncomeInfo.businessType}</td>
				<td>${settleIncomeInfo.transType}</td>
				<td>${settleIncomeInfo.productCode}</td>
				<td>${settleIncomeInfo.settleStatus}</td>
				<td>
					<fmt:formatDate value="${settleIncomeInfo.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</td>
				<td>${settleIncomeInfo.creator}</td>
				<td>
					<fmt:formatDate value="${settleIncomeInfo.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</td>
				<td>${settleIncomeInfo.modifier}</td>
				<td>${settleIncomeInfo.remark}</td>
				<td>${settleIncomeInfo.status}</td>
				<shiro:hasPermission name="riskManage:settleIncomeInfo:edit"><td>
    				<a href="${ctx}/riskManage/settleIncomeInfo/form?incomeId=${settleIncomeInfo.incomeId}">修改</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>