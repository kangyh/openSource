<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>报表管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script language="JavaScript" src="${ctxStatic}/common/validateNum.js"></script>
	<script type="text/javascript">
	var flg = true;
	//动态口令验证
	$(document).ready(function() {
		$(".checkPass").on("click",function(){
			var url = $(this).attr("value-url");
			parent.showDynamicPa();
			parent.enterDynamicPa(url);
		})
	});
	//验证搜索条件
	var validateFrom = {
		validate: function(form){
			var bgTime = $("#beginSettleTime").val();
			var endTime = $("#endSettleTime").val();
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
	
	//验证是否是数字
	function checkValue(obj){
		var flgs = validatePreventInject(obj,"商户公司名称输入不合法!");
		if(!flgs){
			$("#messageBox").text("商户公司名称输入不合法，请重新输入");
			flg = false;
			return;
		}else{
			var pattern = new RegExp("[`~!#%$^@&*()=|{}':;',\\[\\]<>/?~！#￥……&*（）——|{}【】‘；：”“'。，、？.]")
			if(pattern.test(obj)){
				$("#messageBox").text("商户编码输入不合法，请重新输入");
				flg = false;
				return;
			}
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
		
	}
	
	
	function checkNameValue(val){
		var flgs = validatePreventInject(val,"商户公司名称输入不合法!");
		if(!flgs){
			$("#messageBox").text("商户公司名称输入不合法，请重新输入");
			flg = false;
			return;
		}else{
			var pattern = new RegExp("[`~!#%$^@&*()=|{}':;',\\[\\]<>/?~！#￥……&*（）——|{}【】‘；：”“'。，、？.]") 
			if(pattern.test(val)){
				$("#messageBox").text("商户公司名称输入不合法，请重新输入");
				flg = false;
				return;
			}else{
				$("#messageBox").text("");
				flg = true;
				return;
			}
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
		return flg;
	}
	
	
	//清空
	function onClear(){
		$("#formBtn").submit();
	}

		//文件导出
		function onExport(){
			var actionURL = $("#searchForm").attr("action");
	        $("#searchForm").attr("action",$("#searchForm").attr("action")+"export");
			validateFrom.validate($("#searchForm"));
			$("#searchForm").attr("action",actionURL);
		}

	</script>
	<style>
	.ul-forms li{
		list-style:none;
		float:left;
		width:20%;
	}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/settle/settleMerchantRecordReportQuery/">运营报表</a></li>
	</ul>
	<form action="${ctx}/settle/settleMerchantRecordReportQuery/" method="post" id="formBtn"></form> 
	<form:form id="searchForm" modelAttribute="clearingMerchantRecord" action="${ctx}/settle/settleMerchantRecordReportQuery/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户编码：</label>
				<form:input path="merchantId"  onchange="checkValue(this.value)" htmlEscape="false" maxlength="10" class="input-medium required"/>
			</li>
			<li><label>商户公司名称：</label>
				<form:input path="merchantName"  onchange="checkNameValue(this.value)" htmlEscape="false" maxlength="50" class="input-medium required"/>
			</li>
			<li><label>统计时间：</label>
				<input id="beginSettleTime" name="beginSettleTime" type="text" readonly="readonly" maxlength="20" style="width:115px;" class="input-medium Wdate"
					value="<fmt:formatDate value="${clearingMerchantRecord.beginSettleTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input id="endSettleTime" name="endSettleTime" type="text" readonly="readonly" maxlength="20" style="width:115px;" class="input-medium Wdate"
					value="<fmt:formatDate value="${clearingMerchantRecord.endSettleTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
		</ul>
		<ul class="ul-form">
			<li><label>交易类型：</label>
				<form:select id="transType" path="transType" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${productType}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>产品名称：</label>
				<form:select id="productName" path="productName" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${product1Type}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
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
	<sys:message content="${message}"/>
	<ul class="ul-form ul-forms">
		<li style="background:#707c9b">总笔数：${totalPayNumList1}</li>
		<li style="background:#707c9b">总金额：${totalAmountList1}</li>
		<li style="background:#707c9b">结算总金额：${totalSettleAmountList1}</li>
		<li style="background:#707c9b">手续费总金额：${totalFreeAmountList1}</li>
	</ul>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>序号</th>
			<th>商户编码</th>
			<th>商户公司名称</th>
			<th>交易类型</th>
			<th>产品名称</th>
			<th>成功总笔数</th>
			<th>成功总金额</th>
			<th>已结算总金额</th>
			<th>手续费总金额</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="clearingMerchantRecord" varStatus="i">
			<tr>
				<td>${i.count}</td>    
				<td>${clearingMerchantRecord.merchantId}</td>
				<td>${clearingMerchantRecord.merchantName}</td>
				<td>${clearingMerchantRecord.transType}</td>
				<td>${clearingMerchantRecord.productName}</td>
				<td>${clearingMerchantRecord.countNum}
				</td>
				<td>
					<fmt:formatNumber value="${clearingMerchantRecord.successAmount}" pattern="￥0.0000" />
				</td>
				<td>
					<fmt:formatNumber value="${clearingMerchantRecord.settleAmount}" pattern="￥0.0000" />
				</td>
				<td>
					<fmt:formatNumber value="${clearingMerchantRecord.feeAmount}" pattern="￥0.0000" />
				</td>
			</tr>
		</c:forEach>
		</tbody>
		<tr>
				<td>合计</td>    
				<td>--</td>
				<td>--</td>
				<td>--</td>
				<td>--</td>
				<td>${totalPayNumList}
				</td>
				<td>
					${totalAmountList}
				</td>
				<td>
					${totalSettleAmountList}
				</td>
				<td>
					${totalFreeAmountList}
				</td>
			</tr>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>