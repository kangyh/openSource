<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>风险订单管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
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
		
		//验证是否是数字
		function checkValue(obj){
			if(isNaN(obj)){
				$("#messageBox").text("请输入数字！");
				flg = false;
				return;
			}else{
				$("#messageBox").text("");
				flg = true;
				return;
			}
		}

		//清空
		function onClear(){
			$("#messageBox").text("");
			flg = true;
			
			$("#searchForm").find("input[type='text']").val("");
			
			//商户公司名称
			$("#merchantName").find("option").removeAttr("selected");
			$("#merchantName").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#merchantName option[selected]").text());
			
			//产品名称
			$("#productCode").find("option").removeAttr("selected");
			$("#productCode").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(1)").text($("#productCode option[selected]").text()); 
			
			//交易类型
			$("#transType").find("option").removeAttr("selected");
			$("#transType").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(2)").text($("#transType option[selected]").text()); 
			
			//风险订单处理方式
			$("#orderDealwith").find("option").removeAttr("selected");
			$("#orderDealwith").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(3)").text($("#orderDealwith option[selected]").text()); 
			
			//风险订单状态
			$("#orderStatus").find("option").removeAttr("selected");
			$("#orderStatus").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(4)").text($("#orderStatus option[selected]").text()); 
			
			//规则
			$("#ruleDetailId").find("option").removeAttr("selected");
			$("#ruleDetailId").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(5)").text($("#ruleDetailId option[selected]").text()); 
		}
		
		//文件导出
		function onExport(){
	        var actionURL = $("#searchForm").attr("action");
	        $("#searchForm").attr("action",$("#searchForm").attr("action")+"export");
			validateFrom.validate($("#searchForm"));
			$("#searchForm").attr("action",actionURL);
	        
		}
		
		//如果风险订单处理方式是放行，就不显示风险订单状态
		function checkOrderStatus(val){
			if(val=='1' || val == '2'){
				$("#order_status_id").css("display","none");
			}else{
				$("#order_status_id").css("display","block");
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/riskManage/riskOrderQuery/">风险订单查询列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="riskOrder" action="${ctx}/riskManage/riskOrderQuery/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户编码：</label>
				<form:input path="merchantId" onchange="checkValue(this.value)" htmlEscape="false" maxlength="6" class="input-medium required"/>
			</li>
			<li><label>商户订单号：</label>
				<form:input path="merchantOrderNo" onchange="checkValue(this.value)" htmlEscape="false" maxlength="26" style="width: 256px;" class="input-medium required"/>
			</li>
			<li><label>商户公司名称：</label>
				<form:select id="merchantName" path="merchantName" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${mNList}" var="wStatus">
						<form:option value="${wStatus.merchantName}" label="${wStatus.merchantName}"/>
					</c:forEach>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form">
			<li><label>交易订单号：</label>
				<form:input path="transNo" onchange="checkValue(this.value)" htmlEscape="false" maxlength="26" class="input-medium required"/>
			</li>
			<li><label>产品名称：</label>
				<form:select id="productCode" path="productCode" class="input-xlarge">
               		<form:option value="" label="请选择"/>
					<c:forEach items="${prodList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>交易类型：</label>
				<form:select id="transType" path="transType" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${productType}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form">
			<li><label>风控单号：</label>
				<form:input path="riskNo" onchange="checkValue(this.value)" htmlEscape="false" maxlength="50" class="input-medium required"/>
			</li>
			<li><label>处理方式：</label>
				<form:select id="orderDealwith" path="orderDealwith" onchange="checkOrderStatus(this.value)" class="input-xlarge">
               		<form:option value="" label="请选择"/>
					<c:forEach items="${rDTList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<li id="order_status_id"><label>状态：</label>
				<form:select id="orderStatus" path="orderStatus" class="input-xlarge">
               		<form:option value="" label="请选择"/>
					<c:forEach items="${rSList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form">
			<li><label>风控规则：</label>
				<form:select id="ruleDetailId" path="ruleDetailId" class="input-xlarge">
               		<form:option value="" label="请选择"/>
               		<c:forEach items="${riskRuleList}" var="wStatus">
						<form:option value="${wStatus.ruleId}" label="${wStatus.riskName}"/>
					</c:forEach>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" onclick="onExport()" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>风控单号</th>
			<th>商户编码</th>
			<th>商户公司名称</th>
			<th>产品名称</th>
			<th>交易类型</th>
			<th>订单创建时间</th>
			<th>商户订单号</th>
			<th>交易订单号</th>
			<th>支付单号</th>
			<th>交易金额</th>
			<th>处理方式</th>
			<th>状态</th>
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="riskOrder">
				<tr>
					<td>${riskOrder.riskNo}</td>
					<td>${riskOrder.merchantId}</td>
					<td>${riskOrder.merchantName}</td>
					<td>${riskOrder.productName}</td>
					<td>${riskOrder.transType}</td>
					<td>
						<fmt:formatDate value="${riskOrder.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>${riskOrder.merchantOrderNo}</td>
					<td>${riskOrder.transNo}</td>
					<td>${riskOrder.paymentId}</td>
					<td>
						<fmt:formatNumber value="${riskOrder.transAmount}" pattern="￥0.0000" />
					</td>
					<td>${riskOrder.orderDealwith}</td>
					<td <c:choose>
			     		<c:when test="${riskOrder.orderStatus=='已阻断'}">
			     		 style="background:#6cf683"	
			     		</c:when>
			     		<c:when test="${riskOrder.orderStatus=='已人工阻断'}">
			     		 style="background:#6cf683" 
			     		</c:when>
			     		<c:when test="${riskOrder.orderStatus=='已人工放行'}">
			     		 style="background:#6cf683" 
			     		</c:when>
			     		<c:when test="${riskOrder.orderStatus=='预警'}">
			     		 style="background:#707c9b"  
			     		</c:when>
			     		<c:when test="${riskOrder.orderStatus=='人工审核'}">
			     		 style="background:#707c9b"  
			     		</c:when>
			     	</c:choose>>${riskOrder.orderStatus}</td>
					<td>
						<a href="${ctx}/riskManage/riskOrderQuery/show?riskId=${riskOrder.riskId}">详情</a>
					</td>					
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>