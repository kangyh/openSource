<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商户侧清算管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<style>
        #main {
            margin: 50px;
        }
    </style>
	<script type="text/javascript">
		$(document).ready(function() {
			var checkStatus = $("#checkStatus option[selected]").val();
			if(checkStatus=='Y'){
				$('#checkStatusY').css('display','block');
			}else{
				$('#checkStatusY').css('display','none');
			}
		});
		//验证搜索条件
		var validateFrom = {
			validate: function(form){
				
				var paymentIdid = $("#profitBathid").val();
				var shareDetailIdid = $("#shareDetailIdid").val();
				if(isNaN(paymentIdid)){
					$("#messageBox").text("分润批次号请输入数字类型");
					return false;
				}
				if(isNaN(shareDetailIdid)){
					$("#messageBox").text("分润明细ID请输入数字类型");
					return false;
				}
				
				var transNoid = $("#transNoid").val();
				if(isNaN(transNoid)){
					$("#messageBox").text("交易订单号请输入数字类型");
					return false;
				}
				
				var bgTime = $("#beginOperEndTime").val();
				var endTime = $("#endOperEndTime").val();
				if(bgTime=="" && endTime!="" || bgTime!="" && endTime=="" ){
					$("#messageBox").text("请正确设置查询时间范围!");
					return ;
				}
				if( bgTime!="" && endTime!=""){
					if(compareDate(convertDateToJoinStr(bgTime),
									convertDateToJoinStr(endTime)) > 0){
						$("#messageBox").text("起始日期不能大于结束日期!");
						return ;
					}
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
			$("#pageNo").val(1);
			validateFrom.validate($("#searchForm"));
		}
		//清空
		function onClear(){
			$("#formBtn").submit();
		}
		
		//文件导出
		function onExport(){
			var actionURL = $("#searchForm").attr("action");
	        $("#searchForm").attr("action",$("#searchForm").attr("action")+"/export");
			validateFrom.validate($("#searchForm"));
			$("#searchForm").attr("action",actionURL);
		}
		
		function Sel2change(field){
			$("#channelName").val(field);
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/accounting/clearingProfitRecord">分润明细列表</a></li>
	</ul>
	<form action="${ctx}/accounting/clearingProfitRecord" method="post" id="formBtn"></form>
	<form:form id="searchForm" modelAttribute="clearingProfitRecord" action="${ctx}/accounting/clearingProfitRecord" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			 
		<ul class="ul-form">
			<li><label>分润状态:</label>
				<form:select id="profitStatus" path="profitStatus" class="input-xlarge">
					<form:option value="" label="-选择结算状态-"/>
						<c:forEach items="${billingProfitStatus}" var="billingProfitStatusControl">
							<form:option value="${billingProfitStatusControl.value}" label="${billingProfitStatusControl.name}"/>
						</c:forEach>
				</form:select> 
			</li>
			
			
			<li><label>手续费扣除方式:</label>
				<form:select id="feeWay" path="feeWay" class="input-xlarge">
					<form:option value="" label="-选择手续费扣除方式-"/>
					<c:forEach items="${chargeDeductType}" var="chargeDeductTypeControl">
							<form:option value="${chargeDeductTypeControl.value}" label="${chargeDeductTypeControl.name}"/>
						</c:forEach>
				</form:select>
			</li> 
			</ul>
			
			<ul class="ul-form">
			<li><label>交易订单号:</label>
				<form:input path="transNo" id="transNoid" htmlEscape="false" maxlength="40" class="input-medium" style="width:256px;" placeholder="请输入20位数字"/>
			</li> 
			
			<li><label>分润明细ID:</label>
				<form:input path="shareDetailId" id="shareDetailIdid" htmlEscape="false" maxlength="40" class="input-medium" style="width:256px;" placeholder="请输入7位数字"/>
			</li> 
			</ul>
			<ul class="ul-form">
			<li><label>分润批次号:</label>
					<form:input path="profitBath" id="profitBathid" htmlEscape="false" maxlength="40" class="input-medium" style="width:256px;" placeholder="请输入17位数字"/>
			</li> 
			<li><label>分润时间：</label>
					<input id="beginOperEndTime" name="beginOperEndTime" type="text" readonly="readonly" style="width:150px;" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${clearingProfitRecord.beginOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endOperEndTime" name="endOperEndTime" type="text" readonly="readonly" maxlength="20" style="width:150px;" class="input-medium Wdate"
					value="<fmt:formatDate value="${clearingProfitRecord.endOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清除"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" onclick="onExport()" value="导出"/></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
		
	</form:form>

	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>商户编码</th>
			<th>交易订单号</th>
			<th>分润金额</th>
			<th>分润明细ID</th>
			<th>交易类型</th>
			
			<th>应结算金额</th>
			<th>分润手续费</th>
			
			<th>手续费扣除方式</th>
			<th>分润批次号</th>
			
			<th>分润时间</th>
			<th>分润状态</th>
			
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="clearingProfitRecord">
			<tr>
				<td>${clearingProfitRecord.merchantId}</td>
				<td>${clearingProfitRecord.transNo}</td>
				
				<td>
					<fmt:formatNumber value="${clearingProfitRecord.profitAmount}" pattern="￥#,##0.00" />
				</td>
				<td>${clearingProfitRecord.shareDetailId}</td>
				<td>${clearingProfitRecord.transType}</td>
				
				
				<td>
					<fmt:formatNumber value="${clearingProfitRecord.profitAmountPlan}" pattern="￥#,##0.00" />
				</td>
				
				<td>
					<fmt:formatNumber value="${clearingProfitRecord.profitFee}" pattern="￥#,##0.00" />
				</td>
				
				<td>${clearingProfitRecord.feeWay}</td>
				<td>${clearingProfitRecord.profitBath}</td>
				
				<td><fmt:formatDate value="${clearingProfitRecord.profitTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td <c:choose>
			     		<c:when test="${clearingProfitRecord.profitStatus=='已分润'}">
			     		 style="background:#6cf683" 	
			     		</c:when>
			     		<c:when test="${clearingProfitRecord.profitStatus=='未分润'}">
			     		 style="background:#707c9b" 
			     		</c:when>
			     	</c:choose>>${clearingProfitRecord.profitStatus}</td>
			     
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>