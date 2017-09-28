<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商户侧清结算管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<style>
        #main {
            margin: 50px;
        }
    </style>
	<script type="text/javascript">
		$(document).ready(function() {
		});
		//验证搜索条件
		var validateFrom = {
			validate: function(form){
				var transNoid = $("#transNoId").val();
				if(isNaN(transNoid)){
					$("#messageBox").text("交易订单号请输入数字类型");
					return false;
				}
				
				var merchantCodeid = $("#merchantCodeId").val();
				if(isNaN(merchantCodeid)){
					$("#messageBox").text("商户编码请输入数字类型");
					return false;
				}
				form.submit();
			}
		}
		
		
		//搜索
		function onSubmit(){
			$("#pageNo").val(1);
			validateFrom.validate($("#searchForm"));
		}
		//清空
		function onClear(){
			$("#formBtn").submit();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/riskManage/riskOrderFreezeQuery">商户订单冻结列表</a></li>
		<shiro:hasPermission name="settleDic:settleDicItem:edit"><li><a href="${ctx}/riskManage/riskOrderFreezeQuery/add">商户订单规则添加</a></li></shiro:hasPermission>
	</ul>
	<form action="${ctx}/riskManage/riskOrderFreezeQuery/" method="post" id="formBtn"></form>
	<form:form id="searchForm" modelAttribute="riskOrderFreeze" action="${ctx}/riskManage/riskOrderFreezeQuery" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			
			<li><label>商户编码：</label>
					<form:input path="merchantCode" id="merchantCodeId" htmlEscape="false" maxlength="40" class="input-medium" placeholder="请输入6位数字"/>
			</li> 
			
			<li><label>交易订单号：</label>
					<form:input path="transNo" id="transNoId" htmlEscape="false" maxlength="40" class="input-medium" placeholder="请输入20位数字"/>
			</li> 
			
			<li><label>交易类型：</label>
				<form:select id="transType" path="transType" class="input-xlarge">
					<form:option value="" label="-选择交易类型-"/>
					<c:forEach items="${transType}" var="transTypeControl">
							<form:option value="${transTypeControl.value}" label="${transTypeControl.name}"/>
						</c:forEach>
				</form:select>
			</li> 
			
			<li><label>冻结状态：</label>
				<form:select id="status" path="status" class="input-xlarge">
					<form:option value="" label="-选择冻结状态-"/>
					<c:forEach items="${riskStatus}" var="riskStatusControl">
							<form:option value="${riskStatusControl.value}" label="${riskStatusControl.name}"/>
						</c:forEach>
				</form:select>
			</li> 
			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清除"/></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
		
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>商户编码</th>
			<th>商户账号</th>
			<th>冻结单号</th>
			<th>创建时间</th>
			<th>交易类型</th>
			<th>交易订单号</th>
			
			<th>冻结金额</th>
			<th>交易状态</th>
			
			<th>操作人</th>
			<th>冻结状态</th>
			<th>冻结理由</th>
			
			<th>操作</th>
			
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="riskOrderFreeze">
			<tr>
				<td>${riskOrderFreeze.merchantCode}</td>
				<td>${riskOrderFreeze.merchantName}</td>
				<td>${riskOrderFreeze.freezeNo}</td>
				<td><fmt:formatDate value="${riskOrderFreeze.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				
				<td>${riskOrderFreeze.transType}</td>
				<td>${riskOrderFreeze.transNo}</td>
				<td>
					<fmt:formatNumber value="${riskOrderFreeze.transAmount}" pattern="￥0.0000" />
				</td>
				<td>${riskOrderFreeze.transStatus}</td>
				
				<td>${riskOrderFreeze.createAuthor}</td>
				<td>${riskOrderFreeze.status}</td>
				<td>${riskOrderFreeze.freezeRemark}</td>
				<td>
					<c:choose>
						<c:when test="${riskOrderFreeze.status == '已冻结'}">
						   
							<a href="${ctx}/riskManage/riskOrderFreezeQuery/tojd/${riskOrderFreeze.freezeId}">解冻</a>
						</c:when>
						
						<c:when test="${riskOrderFreeze.status == '冻结失败'}">
							已取消
						</c:when>
						
						<c:otherwise>
							<a href="${ctx}/riskManage/riskOrderFreezeQuery/toxg/${riskMerchantFreeze.freezeId}">修改</a>
							
							<a href="${ctx}/riskManage/riskOrderFreezeQuery/dj/${riskMerchantFreeze.freezeId}">冻结</a>
							
							<a href="${ctx}/riskManage/riskOrderFreezeQuery/qx/${riskMerchantFreeze.freezeId}">取消</a>
						</c:otherwise>
					</c:choose> 
				</td>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>