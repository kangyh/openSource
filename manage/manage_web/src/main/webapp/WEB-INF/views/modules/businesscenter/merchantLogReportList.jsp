<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>账务结算报表管理</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/jquery-ztree/3.5.12/css/demo.css" rel="stylesheet" type="text/css"/>
	<link href="${ctxStatic}/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script language="JavaScript" src="${ctxStatic}/common/validateNum.js"></script>
	<script src="${ctxStatic}/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		//验证搜索条件
		var validateFrom = {
			validate: function(form){
				var maxYear = 1;	//默认查询一年
				var bgTime = $("#beginCreateTime").val();
				var endTime = $("#endCreateTime").val();
				if(bgTime=="" && endTime!="" || bgTime!="" && endTime=="" ){
					$("#messageBox").text("请正确设置查询时间范围!");
					return ;
				}
				if( bgTime!="" && endTime!=""){
					if(compareDate(convertDateToJoinStr(bgTime),
									convertDateToJoinStr(endTime)) > 0){
						$("#messageBox").text("起始日期不能大于结束日期!");
						return ;
					}else if(compareYear(convertDateToJoinStr(bgTime),
									convertDateToJoinStr(endTime),maxYear) < 0){
						$("#messageBox").text("查询的时间范围不能超过" + maxYear + "年!");
						return ;
					}
				}
				var accountId = $("#accountId").val();
				var result = validateNum(accountId,"账号请输入数字!");
				var merchantId = $("#merchantId").val();
				var merchantIdResult = validateNum(merchantId,"商户编码请输入数字");
				var accountName = $("#accountName").val();
				var accountNameResult = validatePreventInject(accountName,"账号名称输入不合法!");
				if(!result || !accountNameResult || !merchantIdResult){
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
		function sortOrderByD(){
			var sOrderVal = $("#sOrder").val();
			if(sOrderVal=="0"){
				$("#sOrder").val("1");
				onSubmit();
			}
			if(sOrderVal=="1"){
				$("#sOrder").val("0");
				onSubmit();
			}
			if(sOrderVal !="0" && sOrderVal !="1"){
				$("#sOrder").val("0");
				onSubmit();
			}
		}
		function sortOrderByC(){
			var sOrderVal = $("#sOrder").val();
			if(sOrderVal=="2"){
				$("#sOrder").val("3");
				onSubmit();
			}
			if(sOrderVal=="3"){
				$("#sOrder").val("2");
				onSubmit();
			}
			if(sOrderVal !="2" && sOrderVal !="2"){
				$("#sOrder").val("2");
				onSubmit();
			}
		}

		//搜索
		function onSubmit(){
			$("#searchForm").attr("action","${ctx}/business/merchantLogReport/getMerchantLogReport");
			validateFrom.validate($("#searchForm"));
		}
		function onDisplay(tar){
			if("2" == $(tar).val() || "" == $(tar).val()){
				$("#merchantSonTypeLiId").css("display","block");
				$("#merchantSonType").find("option").removeAttr("selected");
				$("#merchantSonType").find("option:eq(0)").attr("selected","selected");
				$(".select2-chosen:eq(1)").text($("#merchantSonType option[selected]").text());
			}else{
				$("#merchantSonTypeLiId").css("display","none");
			}
		}
		//清空
		function onClear(){
			$("#searchForm").find("input[type='text'][id!='beginCreateTime'][id!='endCreateTime']").val("");
			$("#merchantSonTypeLiId").css("display","block");
			//账户类型
			$("#merchantType").find("option").removeAttr("selected");
			$("#merchantType").find("option:eq(5)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#merchantType option[selected]").text());
			//账户类型
			$("#merchantSonType").find("option").removeAttr("selected");
			$("#merchantSonType").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(1)").text($("#merchantSonType option[selected]").text());
			//交易类型
			$("#type").find("option").removeAttr("selected");
			$("#type").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(2)").text($("#type option[selected]").text());
		}
	</script>
	<style>
	.table td{
		font-size:14px;
		font-family:"微软";
	}
	</style>
</head>
<body>
	<div id="menuContent" class="menuContent" style="display: none; position: absolute;background:#F5F5F5";>  
		<ul id="subjectTree" class="ztree" style="margin-top:0; width:220px; height: 300px;"></ul>
    </div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/business/merchantLogReport/getMerchantLogReport">账务结算报表管理</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="merchantLog" action="${ctx}/business/merchantLogReport/getMerchantLogReport" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="sOrder" name="sOrder" type="hidden" value="${sOrder}"/>
		<ul class="ul-form">
			<li><label>商户编码：</label>
				<%--1000004--%>
				<form:input path="merchantId" htmlEscape="false" maxlength="7" class="input-medium"/>
			</li>
			<li><label>公司名称：</label>
				<form:input path="accountName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>账号：</label>
				<%--6803405030011156--%>
				<form:input path="accountId" htmlEscape="false" maxlength="16" class="input-medium"/>
			</li>
			<li><label>账户名称：</label>
				<form:input path="merchantName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>结算日期：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${merchantLog.beginCreateTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${merchantLog.endCreateTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<%--<li><label>升降排序：</label>--%>
				<%--<form:select id="sortOrder" path="sortOrder" class="input-medium">--%>
					<%--<c:forEach items="${fns:getDictList('SortAmountStatus')}" var="sStatus">--%>
						<%--<form:option value="${sStatus.value}" label="${sStatus.label}"/>--%>
					<%--</c:forEach>--%>
				<%--</form:select>--%>
			<%--</li>--%>
			<li><label>账户类型：</label>
				<form:select path="merchantType" class="input-xlarge" onchange="onDisplay(this)">
					<form:option value="" label="全部"/>
					<c:forEach items="${fns:getDictList('MerchantAccountType')}" var="wStatus">
						<c:if test="${wStatus.value ne '3'}">
							<form:option value="${wStatus.value}" label="${wStatus.label}"/>
						</c:if>
					</c:forEach>
				</form:select>
			</li>
				<li id="merchantSonTypeLiId" <c:if test="${logserch.merchantType eq '2' || logserch.merchantType eq ''}">style="display: block"</c:if><c:if test="${logserch.merchantType ne '2' && logserch.merchantType ne ''}">style="display: none"</c:if>><label>商户账户类型：</label>
					<form:select path="merchantSonType" class="input-xlarge">
						<form:option value="" label="全部"/>
						<c:forEach items="${fns:getDictList('MerchantSonType')}" var="wStatus">
							<form:option value="${wStatus.value}" label="${wStatus.label}"/>
						</c:forEach>
					</form:select>
				</li>
			<%--</c:if>--%>
			<li><label>交易类型：</label>
				<form:select path="type" class="input-xlarge">
					<form:option value="" label="全部"/>
					<c:forEach items="${fns:getDictList('TransType')}" var="tp">
						<form:option value="${tp.value}" label="${tp.label}"/>
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
				<c:choose>
					<c:when test="${logserch.merchantType eq '3'}"></c:when>
					<c:otherwise>
						<th style="width: 10%;">商户编码</th>
						<th style="width: 15%;">公司名称</th>
					</c:otherwise>
				</c:choose>
				<th style="width: 15%;">账号</th>
				<th style="width: 15%;">账户名称</th>
				<th style="width: 10%;">交易类型</th>
				<th style="width: 10%; cursor:pointer;color: red;" onclick="sortOrderByD();">借<c:if test="${logserch.merchantType ne '3'}">(支出)</c:if></th>
				<th style="width: 10%; cursor:pointer;color: red;" onclick="sortOrderByC();">贷<c:if test="${logserch.merchantType ne '3'}">(收入)</c:if></th>
				<th style="width: 15%;">备注</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="accountSettleReportResult">
			<tr>
				<c:choose>
					<c:when test="${accountSettleReportResult.merchantType eq '3'}">
					</c:when>
					<c:otherwise>
						<td>${accountSettleReportResult.merchantId}</td>
						<td>${accountSettleReportResult.accountName}</td>
					</c:otherwise>
				</c:choose>
				<td>${accountSettleReportResult.accountId}</td>
				<c:choose>
					<c:when test="${accountSettleReportResult.merchantType eq '3'}">
						<td>${accountSettleReportResult.accountName}</td>
						<td></td>
						<td></td>
					</c:when>
					<c:otherwise>
						<td>${accountSettleReportResult.merchantName}</td>
					</c:otherwise>
				</c:choose>
				<td>
					<c:set var="TRANS_TYPE" value="${fns:getDictLabel(accountSettleReportResult.transType, 'TransType', '')}"></c:set>
					<c:choose>
						<c:when test="${not empty TRANS_TYPE}">${TRANS_TYPE}</c:when>
						<c:otherwise>-</c:otherwise>
					</c:choose>
				</td>
				<td style="text-align: right;">
				<c:set var="deebitAmountSum">
					<fmt:formatNumber value="${accountSettleReportResult.deebitAmountSum}" pattern="￥#,##0.00" /></td>
				</c:set>
				<c:choose>
					<c:when test="${'0' eq accountSettleReportResult.deebitAmountSum}">
						-
					</c:when>
					<c:otherwise>
						${deebitAmountSum}
					</c:otherwise>
				</c:choose>
				<td style="text-align: right;">
					<c:set var="crebitAmountSum">
						<fmt:formatNumber value="${accountSettleReportResult.crebitAmountSum}" pattern="￥#,##0.00" />
					</c:set>
				<c:choose>
					<c:when test="${'0' eq accountSettleReportResult.crebitAmountSum}">
						-
					</c:when>
					<c:otherwise>
						${crebitAmountSum}
					</c:otherwise>
				</c:choose>
				</td>
				<td>
					<c:set var="ACCOUNT_EXPLAIN" value="${fns:getDictLabel(accountSettleReportResult.accountExplain, 'AccountExplain', '')}"></c:set>
					<c:choose>
						<c:when test="${not empty ACCOUNT_EXPLAIN}">
							${ACCOUNT_EXPLAIN}
						</c:when>
						<c:otherwise>-</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td>合计:</td>
			<c:choose>
				<c:when test="${logserch.merchantType eq '3'}"></c:when>
				<c:otherwise>
					<td></td>
					<td></td>
				</c:otherwise>
			</c:choose>
			<td></td>
			<td></td>
			<td style="text-align: right;"><fmt:formatNumber value="${cdMap.D}" pattern="￥#,##0.00" /></td>
			<td style="text-align: right;"><fmt:formatNumber value="${cdMap.C}" pattern="￥#,##0.00" /></td>
			<td></td>
		</tr>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>