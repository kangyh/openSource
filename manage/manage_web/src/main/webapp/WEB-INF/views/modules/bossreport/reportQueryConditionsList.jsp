<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>报表条件设置管理</title>
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

            $("#payTypeJava").find("option").removeAttr("selected");
            $("#payTypeJava").find("option:eq(0)").attr("selected","selected");
            $(".select2-chosen:eq(0)").text($("#payTypeJava option[selected]").text());

            $("#bankIdJava").find("option").removeAttr("selected");
            $("#bankIdJava").find("option:eq(0)").attr("selected","selected");
            $(".select2-chosen:eq(1)").text($("#bankIdJava option[selected]").text());

            $("#channelPartnerJava").find("option").removeAttr("selected");
            $("#channelPartnerJava").find("option:eq(0)").attr("selected","selected");
            $(".select2-chosen:eq(2)").text($("#channelPartnerJava option[selected]").text());
        }

        function checkValue(obj){
            if(validatePreventInject(obj)){
                $("#messageBox").text("");
            }else{
                $("#messageBox").text("输入字符违法，请重新输入!");
            }
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/bossreport/reportQueryConditions/">报表条件配置列表</a></li>
		<shiro:hasPermission name="bossreport:reportQueryConditions:edit"><li><a href="${ctx}/bossreport/reportQueryConditions/form">添加报表条件配置</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="reportQueryConditions" action="${ctx}/bossreport/reportQueryConditions/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>支付类型：</label>
				<form:select id="payType" path="payType" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${payTypeList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label style="width: auto">银行支付提供者：</label>
				<form:input path="bankProvider" htmlEscape="false" onkeydown="checkValue(this.value)" maxlength="10" cssStyle="width: 255px;" class="input-medium"/>
			</li>
			<li><label>银行ID：</label>
				<form:input path="bankId" htmlEscape="false" onkeydown="checkValue(this.value)" maxlength="10" cssStyle="width: 255px;" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form">
			<li><label>java支付类型：</label>
				<form:select id="payTypeJava" path="payTypeJava" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${payType}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>java银行名称：</label>
				<form:select id="bankIdJava" path="bankIdJava" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${bankList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label style="width: auto">java通道合作方：</label>
				<form:select id="channelPartnerJava" path="channelPartnerJava" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${channelList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
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
				<th>支付类型</th>
				<th>银行支付提供者</th>
				<th>银行ID</th>
				<th>银行名称</th>
				<th>银行名称(复)</th>
				<th>java支付类型</th>
				<th>java银行名称</th>
				<th>java通道合作方</th>
				<th>公司ID|结算主体</th>
				<th>公司名称|结算主体</th>
				<th>创建时间</th>
				<th>创建者</th>
				<th>更新时间</th>
				<th>更新者</th>
				<th>状态</th>
				<shiro:hasPermission name="bossreport:reportQueryConditions:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="reportQueryConditions">
			<tr>
				<td>${reportQueryConditions.payTypeName}</td>
				<td>${reportQueryConditions.bankProviderName}</td>
				<td>${reportQueryConditions.bankId}</td>
				<td>${reportQueryConditions.bankName}</td>
				<td>${reportQueryConditions.bankNameRe}</td>
				<td>${reportQueryConditions.payTypeNameJava}</td>
				<td>${reportQueryConditions.bankNameJava}</td>
				<td>${reportQueryConditions.channelPartnerNameJava}</td>
				<td>${reportQueryConditions.companyId}</td>
				<td>${reportQueryConditions.companyName}</td>
				<td>
					<fmt:formatDate value="${reportQueryConditions.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</td>
				<td>${reportQueryConditions.creator}</td>
				<td>
					<fmt:formatDate value="${reportQueryConditions.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</td>
				<td>${reportQueryConditions.updateAuthor}</td>
				<td>${reportQueryConditions.status}</td>
				<shiro:hasPermission name="bossreport:reportQueryConditions:edit"><td>
    				<a href="${ctx}/bossreport/reportQueryConditions/form?reportId=${reportQueryConditions.reportId}">修改</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>