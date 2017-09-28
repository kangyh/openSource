<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商户出入金限额管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/validateNum.js"></script>
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
            $("#messageBox").text("");
            flg = true;

            $("#searchForm").find("input[type='text']").val("");

            //产品名称
            $("#incomeDirection").find("option").removeAttr("selected");
            $("#incomeDirection").find("option:eq(0)").attr("selected","selected");
            $(".select2-chosen:eq(0)").text($("#incomeDirection option[selected]").text());
        }

        //验证是否是数字
        function checkNum(obj){
            if(validateNum(obj)){
                $("#messageBox").text("");
                flg = true;
			}else{
                $("#messageBox").text("商户编码请输入数字!");
                flg = false;
			}
        }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/riskManage/riskIncomeQuota/">商户出入金限额列表</a></li>
		<shiro:hasPermission name="riskManage:riskIncomeQuota:edit"><li><a href="${ctx}/riskManage/riskIncomeQuota/form">添加商户出入金限额</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="riskIncomeQuota" action="${ctx}/riskManage/riskIncomeQuota/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户编码：</label>
				<form:input path="merchantId" htmlEscape="false" onchange="checkNum(this.value)" maxlength="10" class="input-medium required"/>
			</li>
			<li><label>出入金类型：</label>
				<form:select id="incomeDirection" path="incomeDirection" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${ioList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="clearfix"></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户编码</th>
				<th>出入金类型</th>
				<th>日出入金限额</th>
				<th>创建时间</th>
				<th>创建者</th>
				<th>更新时间</th>
				<th>更新者</th>
				<th>状态</th>
				<shiro:hasPermission name="riskManage:riskIncomeQuota:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="riskIncomeQuota">
			<tr>
				<td>${riskIncomeQuota.merchantId}</td>
				<td>${riskIncomeQuota.incomeDirection}</td>
				<td>
					<fmt:formatNumber value="${riskIncomeQuota.dayIncomeQuotaAmount}" pattern="￥0.0000" />
				</td>
				<td>
					<fmt:formatDate value="${riskIncomeQuota.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</td>
				<td>${riskIncomeQuota.createAuthor}</td>
				<td>
					<fmt:formatDate value="${riskIncomeQuota.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</td>
				<td>${riskIncomeQuota.updateAuthor}</td>
				<td>${riskIncomeQuota.status}</td>
				<shiro:hasPermission name="riskManage:riskIncomeQuota:edit"><td>
    				<a href="${ctx}/riskManage/riskIncomeQuota/form?quotaId=${riskIncomeQuota.quotaId}">修改</a>&nbsp;&nbsp;|&nbsp;&nbsp;
					<a href="${ctx}/riskManage/riskIncomeQuota/delete?quotaId=${riskIncomeQuota.quotaId}">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>