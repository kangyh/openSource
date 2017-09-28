<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商户结算列表管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script language="JavaScript" src="${ctxStatic}/common/validateNum.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		//验证搜索条件
		var validateFrom = {
			validate: function(form){
				var merchantId = $("#merchantId").val();
				var result = validateNum(merchantId,"商户编码请输入数字!");
				var transNo = $("#transNo").val();
				var transNoResult = validateNum(transNo,"交易单号输入有误!");
				if(!result || !transNoResult){
					return;
				}
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
			$("#searchForm").attr("action","${ctx}/account/merchantSettle/merchantSettle");
			validateFrom.validate($("#searchForm"));
		}

		function onClearDate(){
			$("#searchForm").attr("action","${ctx}/account/merchantSettle/clearDate");
			$("#searchForm").submit();
		}

		//清空
		function onClear(){
			$("#searchForm").find("input[type='text']").val("");
			//默认排序方式
			$("#merchantSettleStatus").find("option").removeAttr("selected");
			$("#merchantSettleStatus").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#merchantSettleStatus option[selected]").text());
//			$("#channelSettleStatus").find("option").removeAttr("selected");
//			$("#channelSettleStatus").find("option:eq(0)").attr("selected","selected");
//			$(".select2-chosen:eq(1)").text($("#channelSettleStatus option[selected]").text());
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/merchantLogSettle/">商户结算列表</a></li>
		<%--<shiro:hasPermission name="payment:merchantLog:edit"><li><a href="${ctx}/payment/merchantLogSettle/form">商户结算添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="merchantSettleBean" action="${ctx}/account/merchantSettle/merchantSettle" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户编码：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>交易订单号：</label>
				<form:input path="transNo" htmlEscape="false" maxlength="26" class="input-medium"/>
			</li>
			<li><label>商户结算状态：</label>
				<form:select id="merchantSettleStatus" path="merchantSettleStatus" class="input-medium">
					<form:option value="" label="全部"/>
					<c:forEach items="${fns:getDictList('SettleStatus')}" var="sStatus">
						<form:option value="${sStatus.value}" label="${sStatus.label}"/>
					</c:forEach>
				</form:select>
			</li>
			<%--<li><label>通道结算状态：</label>--%>
				<%--<form:select id="channelSettleStatus" path="channelSettleStatus" class="input-medium">--%>
					<%--<c:forEach items="${fns:getDictList('SettleStatus')}" var="sStatus">--%>
						<%--<form:option value="${sStatus.value}" label="${sStatus.label}"/>--%>
					<%--</c:forEach>--%>
				<%--</form:select>--%>
			<%--</li>--%>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="btns"><input id="btnClearDate" style="display:none" class="btn btn-primary" type="button" onclick="onClearDate()" value="clear"/></li>
			<li class="clearfix"></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>流水号</th>
				<th>商户结算流水号</th>
				<%--<th>通道结算流水号</th>--%>
				<th>支付号</th>
				<th>交易号</th>
				<th>商户编码</th>
				<th>商户名称</th>
				<th>交易类型</th>
				<th>商户结算批次号</th>
				<th>商户结算金额</th>
				<th>商户手续费</th>
				<th>商户结算状态</th>
				<th>商户结算日期</th>
				<%--<th>通道结算批次号</th>--%>
				<%--<th>通道结算金额</th>--%>
				<%--<th>通道手续费</th>--%>
				<%--<th>通道结算状态</th>--%>
				<%--<th>通道结算日期</th>--%>
				<th>创建时间</th>
				<th>修改时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchantSettleSolrBean">
			<tr>
				<td>${merchantSettleSolrBean.log_id}</td>
				<td>${merchantSettleSolrBean.detail_id}</td>
				<%--<td>${merchantSettleSolrBean.detail_channal_id}</td>--%>
				<td>${merchantSettleSolrBean.paymentid}</td>
				<td>${merchantSettleSolrBean.trans_no}</td>
				<td>${merchantSettleSolrBean.merchant_id}</td>
				<td>${merchantSettleSolrBean.merchant_name}</td>
				<td>${merchantSettleSolrBean.trans_type}</td>
				<td>
					<c:choose>
						<c:when test="${not empty merchantSettleSolrBean.merchant_settle_id}">
							${merchantSettleSolrBean.merchant_settle_id}
						</c:when>
						<c:otherwise>-</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${not empty merchantSettleSolrBean.merchant_settle_amount}">
							${merchantSettleSolrBean.merchant_settle_amount}
						</c:when>
						<c:otherwise>-</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${not empty merchantSettleSolrBean.merchant_fee_amount}">
							${merchantSettleSolrBean.merchant_fee_amount}
						</c:when>
						<c:otherwise>-</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${not empty merchantSettleSolrBean.merchant_settle_status}">
							${merchantSettleSolrBean.merchant_settle_status}
						</c:when>
						<c:otherwise>-</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${not empty merchantSettleSolrBean.merchant_account_date}">
							${merchantSettleSolrBean.merchant_account_date}
						</c:when>
						<c:otherwise>-</c:otherwise>
					</c:choose>
				</td>
				<%--<td>--%>
					<%--<c:choose>--%>
						<%--<c:when test="${not empty merchantSettleSolrBean.channal_settle_id}">--%>
							<%--${merchantSettleSolrBean.channal_settle_id}--%>
						<%--</c:when>--%>
						<%--<c:otherwise>-</c:otherwise>--%>
					<%--</c:choose>--%>
				<%--</td>--%>
				<%--<td>--%>
					<%--<c:choose>--%>
						<%--<c:when test="${not empty merchantSettleSolrBean.channal_settle_amount}">--%>
							<%--${merchantSettleSolrBean.channal_settle_amount}--%>
						<%--</c:when>--%>
						<%--<c:otherwise>-</c:otherwise>--%>
					<%--</c:choose>--%>
				<%--</td>--%>
				<%--<td>--%>
					<%--<c:choose>--%>
						<%--<c:when test="${not empty merchantSettleSolrBean.channal_fee_amount}">--%>
							<%--${merchantSettleSolrBean.channal_fee_amount}--%>
						<%--</c:when>--%>
						<%--<c:otherwise>-</c:otherwise>--%>
					<%--</c:choose>--%>
				<%--</td>--%>
				<%--<td>--%>
					<%--<c:choose>--%>
						<%--<c:when test="${not empty merchantSettleSolrBean.channel_settle_status}">--%>
							<%--${merchantSettleSolrBean.channel_settle_status}--%>
						<%--</c:when>--%>
						<%--<c:otherwise>-</c:otherwise>--%>
					<%--</c:choose>--%>
				<%--</td>--%>
				<%--<td>--%>
					<%--<c:choose>--%>
						<%--<c:when test="${not empty merchantSettleSolrBean.channel_account_date}">--%>
							<%--${merchantSettleSolrBean.channel_account_date}--%>
						<%--</c:when>--%>
						<%--<c:otherwise>-</c:otherwise>--%>
					<%--</c:choose>--%>
				<%--</td>--%>
				<td>${merchantSettleSolrBean.create_time}</td>
				<td>${merchantSettleSolrBean.update_time}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>