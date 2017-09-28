<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>退款管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnSubmit").on("click",function(){
				var merchantId = $("#merchantId").val();
				var refundId = $("#refundId").val();
				var merchantOrderNo = $("#merchantOrderNo").val();
				var merchantLoginName = $("#merchantLoginName").val();

				if(!isEmpty(merchantId)&&!/\d+/.test(merchantId)){
					alert("商户编码输入不合法，只能输入数字");
					return ;
				}
				if(!isEmpty(refundId)&&!/\d+/.test(refundId)){
					alert("交易订单号输入不合法，只能输入数字");
					return ;
				}
				if(!isEmpty(merchantOrderNo)&&/^[!@#$%^&*()！@#￥%……&*（）]+$/.test(merchantOrderNo)){
					alert("原商户订单号输入不合法，请重新输入");
					return ;
				}
				if(!isEmpty(merchantLoginName)&&!/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/.test(merchantLoginName)){
					alert("商户账号输入不合法，请重新输入");
					return ;
				}
				if(checkDate()){
					$("#searchForm").submit();
				}
			});

		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//清空
		function onClear(){
			$("#searchForm").find("input[type='text']").val("");
			//默认支付状态
			$("#refundType").find("option").removeAttr("selected");
			$("#refundType").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#refundType option[selected]").text());
			
			$("#refundFrom").find("option").removeAttr("selected");
			$("#refundFrom").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(1)").text($("#refundFrom option[selected]").text());

			$("#RefundStatus").find("option").removeAttr("selected");
			$("#RefundStatus").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(2)").text($("#RefundStatus option[selected]").text());
            $(".select2-chosen:eq(4)").text($("#RefundStatus option[selected]").text());
			//默认排序方式
			$("#sortOrder").find("option").removeAttr("selected");
			$("#sortOrder").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(3)").text($("#sortOrder option[selected]").text());
		}
		function isEmpty(value){
			if(value==undefined||value==""||value.trim()==""){
				return true;
			}
			return false;
		}
		
		
		
		//导出
		function exportExcel(){
			if(checkDate()){
				var refundId = $("#refundId").val();
				var transNo = $("#transNo").val();
				var merchantId = $("#merchantId").val();
				var merchantOrderNo = $("#merchantOrderNo").val();
				var merchantLoginName = $("#merchantLoginName").val();
				var beginCreateTime = $("#beginCreateTime").val();
				var endCreateTime = $("#endCreateTime").val();
				var refundType = $("#refundType").val();
				var refundFrom = $("#refundFrom").val();
				var refundStatus = $("#RefundStatus").val();
				var groupType = $("input[name='groupType']:checked").val();
				var host = "${ctx}/payment/refundRecord/exportExcel";
				var url = host+"?refundId="+refundId+"&transNo="+transNo+"&merchantId="+merchantId + "&merchantOrderNo="+merchantOrderNo +
						"&merchantLoginName="+merchantLoginName+"&beginCreateTime="+beginCreateTime+"&endCreateTime="+endCreateTime+
						"&refundType="+refundType+"&refundFrom="+refundFrom+"&refundStatus="+refundStatus+"&groupType="+groupType;
				$('<form method="post" action="' + url + '"></form>').appendTo('body').submit().remove();
			}
		}

		//导出对账文件
		function exportBalanceAccountExcel(){
			if(checkDate()){
				var refundId = $("#refundId").val();
				var transNo = $("#transNo").val();
				var merchantId = $("#merchantId").val();
				var merchantOrderNo = $("#merchantOrderNo").val();
				var merchantLoginName = $("#merchantLoginName").val();
				var beginCreateTime = $("#beginCreateTime").val();
				var endCreateTime = $("#endCreateTime").val();
				var refundType = $("#refundType").val();
				var refundFrom = $("#refundFrom").val();
				var refundStatus = $("#RefundStatus").val();
				var groupType = $("input[name='groupType']:checked").val();
				var host = "${ctx}/payment/refundRecord/exportBalanceAccountExcel";
				var url = host+"?refundId="+refundId+"&transNo="+transNo+"&merchantId="+merchantId + "&merchantOrderNo="+merchantOrderNo +
						"&merchantLoginName="+merchantLoginName+"&beginCreateTime="+beginCreateTime+"&endCreateTime="+endCreateTime+
						"&refundType="+refundType+"&refundFrom="+refundFrom+"&refundStatus="+refundStatus+"&groupType="+groupType;
				$('<form method="post" action="' + url + '"></form>').appendTo('body').submit().remove();
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/refundRecord/">退款列表</a></li>
		<shiro:hasPermission name="payment:refundRecord:edit"><li><a href="${ctx}/payment/refundRecord/form">退款添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="refundRecord" action="${ctx}/payment/refundRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>交易订单号：</label>
				<form:input path="refundId" id="refundId" htmlEscape="false" maxlength="20" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')} else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label>原交易订单号：</label>
				<form:input path="transNo" id="transNo" htmlEscape="false" maxlength="26" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')} else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label>原商户订单号：</label>
				<form:input path="merchantOrderNo" id="merchantOrderNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商户编码：</label>
				<form:input path="merchantId" id="merchantId" htmlEscape="false" maxlength="20" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')} else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label>商户账号：</label>
				<form:input path="merchantLoginName" id="merchantLoginName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>退款类型：</label>
				<form:select id="refundType" path="type" class="input-medium">
					<form:option value="R1" label="全部"/>
					<c:forEach items="${fns:getDictList('RefundType')}" begin="0" end="6" var="rtype">
						<form:option value="${rtype.value}" label="${rtype.label}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>退款来源：</label>
				<form:select id="refundFrom" path="refundFrom" class="input-medium">
					<form:option value="R1" label="全部"/>
					<c:forEach items="${fns:getDictList('RefundFrom')}" begin="0" end="6" var="rtype">
						<form:option value="${rtype.value}" label="${rtype.label}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>状态 ：</label>
				<form:select id="RefundStatus" path="status" class="input-medium">
					<form:option value="R1" label="全部"/>
					<c:forEach items="${fns:getDictList('RefundStatus')}" begin="0" end="8" var="status">
						<form:option value="${status.value}" label="${status.label}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>升降排序：</label>
				<form:select id="sortOrder" path="sortOrder" class="input-medium">
					<c:forEach items="${fns:getDictList('SortStatus')}" var="sStatus">
						<form:option value="${sStatus.value}" label="${sStatus.label}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<form:select id="statisticsDate" path="statisticsDate" class="input-medium" style="width:100px;" onchange="changeDateSection(this.options[this.options.selectedIndex].text);">
					<form:option value="" label="全部"/>
					<c:forEach items="${fns:getDictList('statisticsDate')}" var="item">
						<form:option value="${item.value}" label="${item.label}"/>
					</c:forEach>
				</form:select>
				<input name="groupType"  type="radio" value="1" style="margin-left: 10px;"  <c:if test="${groupType == 1}">checked="checked"</c:if> >按创建时间
				<input name="groupType" type="radio" value="2" style="margin-right: 10px;" <c:if test="${groupType == 2}">checked="checked"</c:if>>按成功时间
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="btns"><input class="btn btn-warning" type="button" onclick="exportExcel()" value="导出"/></li>
			<li class="btns"><input class="btn btn-warning" type="button" onclick="exportBalanceAccountExcel()" value="导出对账文件"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table class="table table-striped   table-bordered table-condensed">
		<thead>
			<tr>
				<th>待审核总金额</th>
				<th>待处理总金额</th>
				<th>处理中总金额</th>
				<th>退款失败总金额</th>
				<th>审核失败总金额</th>
				<th>退款成功总金额</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td id="preautTotalAmount">￥0.00</td>
				<td id="prerfdTotalAmount">￥0.00</td>
				<td id="rfdingTotalAmount">￥0.00</td>
				<td id="failedTotalAmount">￥0.00</td>
				<td id="rejectTotalAmount">￥0.00</td>
				<td id="successTotalAmount">￥0.00</td>
			</tr>
		</tbody>
	</table>
	<table id="contentTable" class="table table-striped   table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户编码</th>
				<th>商户公司名称</th>
				<th>商户账号</th>
				<th>交易订单号</th>
				<th>原交易订单号</th>
				<th>原商户订单号</th>
				<!-- <th>账户ID</th>
				<th>账户名称</th> -->
				<th>退款金额</th>
				<th>退款来源</th>
				<th>创建时间</th>
				<th>成功时间</th>
				<!-- <th>成功交易金额</th> -->
				<th>退款类型</th>
				<th>状态 </th>
<!-- 				<th>手续费费率</th>
				<th>手续费金额</th> -->
				<shiro:hasPermission name="payment:refundRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="refundRecord">
			<tr >
				<td>
					${refundRecord.merchantId}
				</td>
				<td>
					${refundRecord.merchantCompany}
				</td>
				<td>
					${refundRecord.merchantLoginName}
				</td>
				<td><a href="${ctx}/payment/refundRecord/form?id=${refundRecord.refundId}">
					${refundRecord.refundId}
				</a></td>
				<td>
					${refundRecord.transNo}
				</td>
				<td>
					<c:choose>
						<c:when test="${refundRecord.type=='CONSUM' }">
							${refundRecord.merchantOrderNo}
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
					</c:choose>
					
				</td>
				
				
			<%-- 	<td>
					${refundRecord.accountId}
				</td>
				<td>
					${refundRecord.accountName}
				</td> --%>
				<td>
					<fmt:formatNumber value="${refundRecord.refundAmount}" pattern="￥#,##0.00" />
				</td>
				<td>
					${fns:getDictLabel(refundRecord.refundFrom, 'RefundFrom', '')}
				</td>
				<td>
					<fmt:formatDate value="${refundRecord.refundTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${refundRecord.refundSuccessTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			<%-- 	<td>
					${refundRecord.refundSuccessAmount}
				</td> --%>
				<td>
					${fns:getDictLabel(refundRecord.type, 'RefundType', '')}
				</td>
				<td <c:if test="${refundRecord.status=='SUCCES'}">
			style="background:${success_background}" 
			</c:if>>
					${fns:getDictLabel(refundRecord.status, 'RefundStatus', '')}
				</td>
			<%-- 	<td>
					${refundRecord.feeRatio}
				</td>
				<td>
					${refundRecord.feeAmount}
				</td> --%>
				<shiro:hasPermission name="payment:refundRecord:edit"><td>
    				<a href="${ctx}/payment/refundRecord/form?id=${refundRecord.id}">修改</a>
					<a href="${ctx}/payment/refundRecord/delete?id=${refundRecord.id}" onclick="return confirmx('确认要删除该退款吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
		<script type="text/javascript">
		$(function(){
			var refundId = $("#refundId").val();
			var transNo = $("#transNo").val();
			var merchantId = $("#merchantId").val();
			var merchantOrderNo = $("#merchantOrderNo").val();
			var merchantLoginName = $("#merchantLoginName").val();
			var beginCreateTime = $("#beginCreateTime").val();
			var endCreateTime = $("#endCreateTime").val();
			var refundType = $("#refundType").val();
			var refundFrom = $("#refundFrom").val();
			var refundStatus = $("#RefundStatus").val();
			var groupType = $("input[name='groupType']:checked").val();
			$.ajax({
				type : "POST",
				url : "${ctx}/payment/refundRecord/getStatisticsList",
				data : {
					"refundId" : refundId,
					"transNo" : transNo,
					"merchantId" : merchantId,
					"merchantOrderNo" : merchantOrderNo,
					"merchantLoginName" : merchantLoginName,
					"beginCreateTime" : beginCreateTime,
					"endCreateTime" : endCreateTime,
					"refundType" : refundType,
					"refundFrom" : refundFrom,
					"refundStatus" : refundStatus,
					"groupType" : groupType
				},
				dataType : "json",
				success : function(data){
					$("#preautTotalAmount").text(data.preautTotalAmount);
					$("#prerfdTotalAmount").text(data.prerfdTotalAmount);
					$("#rfdingTotalAmount").text(data.rfdingTotalAmount);
					$("#failedTotalAmount").text(data.failedTotalAmount);
					$("#rejectTotalAmount").text(data.rejectTotalAmount);
					$("#successTotalAmount").text(data.successTotalAmount);
				}
			});
			
		});
	</script>
</body>
</html>