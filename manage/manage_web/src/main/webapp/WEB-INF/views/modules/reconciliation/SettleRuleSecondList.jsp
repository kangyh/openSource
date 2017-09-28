<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/modules/sys/validation.jsp"%>
<html>
<head>
	<title>对账管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<style>
        #main {
            margin: 50px;
        }
    </style>
	<script type="text/javascript">
		
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
		
		//清空
		function onClear(){
			$("#formBtn").submit();
		}
		
		//搜索
		function onSubmit(){
			$("#pageNo").val(1);
			validateFrom.validate($("#searchForm"));
		}

		function onAdd(){
			$("#updateadd").submit();
		}

	</script>
</head>
<body>
	<form id="updateadd" method="get" action="${ctx}/reconciliation/SettleRuleSecond/updateAndAdd"></form>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/reconciliation/SettleRuleSecond/">对账规则控制</a></li>
	</ul>
	<form action="${ctx}/reconciliation/SettleRuleSecond/" method="post" id="formBtn"></form>
	<form:form id="searchForm" modelAttribute="settleRuleSecond" action="${ctx}/reconciliation/SettleRuleSecond/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<!-- 业务类型  待定 -->

			<li><label class="control-label">通道合作方：</label>
			    	<form:select id="channelCode" path="channelCode" class="input-xlarge" style="width:200px;">
						<form:option value="" label="-通道合作方-"/>
						<c:forEach items="${channelCode}" var="channelCodeFor">
							<form:option value="${channelCodeFor.channelCode}" label="${channelCodeFor.channelName}"/>
						</c:forEach>
					</form:select>
			</li>
			<li><label>支付通道类型：</label>
				<form:select id="channelType" path="channelType" class="input-xlarge" style="width:200px;">
					<form:option value="" label="-支付通道类型-"/>
						<c:forEach items="${channelType}" var="channelTypeFor">
							<form:option value="${channelTypeFor.value}" label="${channelTypeFor.name}"/>
						</c:forEach>
				</form:select>
			</li>
			<li><label>生效标识：</label>
				<form:select id="status" path="status" class="input-xlarge" style="width:200px;">
					<form:option value="" label="-生效标识-"/>
					<c:forEach items="${status}" var="statusFor">
							<form:option value="${statusFor.value}" label="${statusFor.name}"/>
						</c:forEach>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清除"/></li>
			<c:if test="${not empty batchList}">
				<li class="btns"><input id="btnAdd" class="btn btn-primary" type="button" onclick="onAdd()" value="增加"/></li>
			</c:if>

		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>通道合作方</th>
			<th>支付通道类型</th>
			<th>账单类型</th>
			<th>对账方式</th>
			<th>分隔符</th>

			<th>支付单号</th>
			<th>银行流水号</th>
			<th>服务费</th>
			<th>优惠金额</th>
			<th>发卡行手续费</th>
			<th>结算金额</th>
			<th>开始行号</th>
			<th>结束标记</th>

			<th>交易状态</th>
			<th>生效标识</th>
			<th>创建者</th>
			<th>创建时间</th>
			<th>更新者</th>
			<th>更新时间</th>
			<shiro:hasPermission name="settle:settleRuleSecond:edit"><th>操作</th></shiro:hasPermission>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="settleRuleSecond">
			<tr>
				<td>${settleRuleSecond.channelName}</td>
				<td>${settleRuleSecond.channelType}</td>
				<td>${settleRuleSecond.billType}
				<td>${settleRuleSecond.settleWay}</td>
				<td>${settleRuleSecond.splitFlg}</td>

				<td>${settleRuleSecond.paymentId}</td>
				<td>${settleRuleSecond.bankSeq}</td>
				<td>${settleRuleSecond.feeService}</td>
				<td>${settleRuleSecond.feeFree}</td>
				<td>${settleRuleSecond.feeBank}</td>

				<td>${settleRuleSecond.successAmount}</td>
				<td>${settleRuleSecond.startRow}</td>
				<td>${settleRuleSecond.endFlg}</td>

				<td>${settleRuleSecond.transStatus}</td>
				<td <c:choose>
			     		<c:when test="${settleRuleSecond.status=='生效'}">
			     		 style="background:#6cf683"
			     		</c:when>
			     		<c:when test="${settleRuleSecond.status=='无效'}">
			     		 style="background:red"
			     		</c:when>
			     	</c:choose>>${settleRuleSecond.status}</td>
				<td>${settleRuleSecond.createAuthor}</td>
				<td>
					<fmt:formatDate value="${settleRuleSecond.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>${settleRuleSecond.updateAuthor}</td>
				<td>
					<fmt:formatDate value="${settleRuleSecond.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>


				<shiro:hasPermission name="settle:settleRuleSecond:edit"><td>
					<a href="${ctx}/reconciliation/SettleRuleSecond/delete/${settleRuleSecond.ruleId}" onclick="return confirmx('确认要删除该规则吗？', this.href)">删除</a>
				 | 	<a style="cursor:pointer;" class="checkPass"   value-url="${ctx}/reconciliation/SettleRuleSecond/updateAndAdd?ruleId=${settleRuleSecond.ruleId}">修改</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>