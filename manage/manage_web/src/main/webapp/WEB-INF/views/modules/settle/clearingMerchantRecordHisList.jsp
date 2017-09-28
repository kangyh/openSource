<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>清结算管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
		//用于验证
		var flg = true;
		
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
				form.submit();
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
			
			//业务类型
			$("#transType").find("option").removeAttr("selected");
			$("#transType").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#transType option[selected]").text());
			//结算状态
			$("#settleStatus").find("option").removeAttr("selected");
			$("#settleStatus").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(1)").text($("#settleStatus option[selected]").text());
			//对账状态
			$("#checkStatus").find("option").removeAttr("selected");
			$("#checkStatus").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(2)").text($("#checkStatus option[selected]").text());
			//对账标记
			$("#checkFlg").find("option").removeAttr("selected");
			$("#checkFlg").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(3)").text($("#checkFlg option[selected]").text());
		}
		
		//对账状态选择已对账
		function checkedStatusY(obj){
			var checkStatus = obj.value;
			if(checkStatus=='Y'){
				$('#checkStatusY').css('display','block');
			}else{
				$('#checkStatusY').css('display','none');
			}
		}

		//文件导出
		function onExport(){
			var actionURL = $("#searchForm").attr("action");
	        $("#searchForm").attr("action",$("#searchForm").attr("action")+"export");
			validateFrom.validate($("#searchForm"));
			$("#searchForm").attr("action",actionURL);
		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/settle/clearingMerchantRecordHis/">商户清算历史列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="clearingMerchantRecordHis" action="${ctx}/settle/clearingMerchantRecordHis/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>交易类型：</label>
				<form:select id="transType" path="transType" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${productType}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>结算状态：</label>
				<form:select id="settleStatus" path="settleStatus" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${settleStatus}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>对账状态：</label>
				<form:select onchange="checkedStatusY(this)" id="checkStatus" path="checkStatus" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${checkStatus}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<li id="checkStatusY" style="display: none;"><label>状态描述：</label>
				<form:select id="checkFlg" path="checkFlg" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<form:option value="QSTS" label="平账"/>
					<form:option value="QFTS" label="差异账"/>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form">
			<li><label>商户编码：</label>
				<form:input path="merchantId" onchange="checkValue(this.value)" htmlEscape="false" maxlength="10" style="width:256px;" class="input-medium required"/>
			</li>
			<li><label>交易订单号：</label>
				<form:input path="transNo" onchange="checkValue(this.value)" htmlEscape="false" maxlength="26" style="width:256px;" class="input-medium required"/>
			</li>
			<li><label>结算单号：</label>
				<form:input path="settleBath" onchange="checkValue(this.value)" htmlEscape="false" maxlength="17" style="width:256px;" class="input-medium required"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" onclick="onExport()" value="导出"/></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>商户编码</th>
			<th>交易类型</th>
			<th>币种</th>
			<th>交易订单号</th>
			<!-- <th>原交易订单号</th> -->
			<th>实际支付金额</th>
			<th>清算日期</th>
			<th>清算流水号</th>
			<th>订单应结算日期</th>
			<th>订单应结算金额</th>
			<th>结算单号</th>
			<th>手续费扣除方式</th>
			<th>订单手续费</th>
			<th>是否分润</th>
			<th>对账状态</th>
			<th>状态描述</th>
			<th>结算状态</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="clearingMerchantRecordHis">
			<tr>
				<td>${clearingMerchantRecordHis.merchantId}</td>
				<td>${clearingMerchantRecordHis.transType}</td>
				<td>${clearingMerchantRecordHis.currency}</td>
				<td>${clearingMerchantRecordHis.transNo}</td>
				<%-- <td>${clearingMerchantRecord.transNoOld}</td> --%>
				<td>
					<fmt:formatNumber value="${clearingMerchantRecordHis.requestAmount}" pattern="￥0.0000" />
				</td>
				<td><fmt:formatDate value="${clearingMerchantRecordHis.settleTime}" type="both" pattern="yyyy-MM-dd"/></td>
				<td>${clearingMerchantRecordHis.settleNo}</td>
				<td><fmt:formatDate value="${clearingMerchantRecordHis.settleTimePlan}" type="both" pattern="yyyy-MM-dd"/></td>
				<td>
					<fmt:formatNumber value="${clearingMerchantRecordHis.settleAmountPlan}" pattern="￥0.0000" />
				</td>
				<td>${clearingMerchantRecordHis.settleBath}</td>
				<td>
					<c:if test='${clearingMerchantRecordHis.feeWay == "" or clearingMerchantRecord.feeWay == null}'>
						--
					</c:if>
					<c:if test='${clearingMerchantRecordHis.feeWay != ""}'>
						${clearingMerchantRecordHis.feeWay}
					</c:if>
				</td>
				<td>
					<c:if test='${clearingMerchantRecordHis.feeWay == "外扣"}'>
						--
					</c:if>
					<c:if test='${clearingMerchantRecordHis.feeWay != "外扣" and clearingMerchantRecordHis.feeWay != null and clearingMerchantRecordHis.feeWay != ""}'>
						<fmt:formatNumber value="${clearingMerchantRecordHis.fee}" pattern="￥0.0000" />
					</c:if>
					<c:if test='${clearingMerchantRecordHis.feeWay == "" or clearingMerchantRecordHis.feeWay == null}'>
						--
					</c:if>
				</td>
				<td >${clearingMerchantRecordHis.isProfit}</td>
				<td <c:choose>
			     		<c:when test="${clearingMerchantRecordHis.checkStatus=='已对账'}">
			     		 style="background:#6cf683" 	
			     		</c:when>
			     		<c:when test="${clearingMerchantRecordHis.checkStatus=='未对账'}">
			     		 style="background:#707c9b" 
			     		</c:when>
			     		<c:when test="${clearingMerchantRecordHis.checkStatus=='对账中'}">
			     		 style="background:#ffb300" 
			     		</c:when>
			     	</c:choose>>${clearingMerchantRecordHis.checkStatus}</td>
				<td>${clearingMerchantRecordHis.checkFlg}</td>
				<td <c:choose>
			     		<c:when test="${clearingMerchantRecordHis.settleStatus=='已结算'}">
			     		 style="background:#6cf683" 	
			     		</c:when>
			     		<c:when test="${clearingMerchantRecordHis.settleStatus=='未结算'}">
			     		 style="background:#707c9b" 
			     		</c:when>
			     		<c:when test="${clearingMerchantRecordHis.settleStatus=='结算中'}">
			     		 style="background:#ffb300" 
			     		</c:when>
			     	</c:choose>>${clearingMerchantRecordHis.settleStatus}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>