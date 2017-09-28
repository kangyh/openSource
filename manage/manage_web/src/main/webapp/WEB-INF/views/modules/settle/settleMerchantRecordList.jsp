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
			
		});
		//验证搜索条件
		var validateFrom = {
			validate: function(form){
				var bgCTime = $("#beginCheckTime").val();
				var endCTime = $("#endCheckTime").val();
				var bgSTime = $("#beginSettleTime").val();
				var endSTime = $("#endSettleTime").val();
				if(bgCTime=="" && endCTime!="" || bgCTime!="" && endCTime=="" ){
					$("#messageBox").text("请正确设置查询时间范围!");
					return ;
				}
				if( bgCTime!="" && endCTime!=""){
					if(compareDate(convertDateToJoinStr(bgCTime),
									convertDateToJoinStr(endCTime)) > 0){
						$("#messageBox").text("起始日期不能大于结束日期!");
						return ;
					}
				}
				if(bgSTime=="" && endSTime!="" || bgSTime!="" && endSTime=="" ){
					$("#messageBox").text("请正确设置查询时间范围!");
					return ;
				}
				if( bgSTime!="" && endSTime!=""){
					if(compareDate(convertDateToJoinStr(bgSTime),
									convertDateToJoinStr(endSTime)) > 0){
						$("#messageBox").text("起始日期不能大于结束日期!");
						return ;
					}
				}
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
            $("form :input").not(":button, :submit, :reset, :hidden").val("").removeAttr("checked").remove("selected");
            $("#searchForm").submit();
		}

		//文件导出
		function onExport(){
            var flag = true;
            $("form :input").not(":button, :submit, :reset, :hidden").each(function (element) {
                if(this.value != ""){
                    var actionURL = $("#searchForm").attr("action");
                    $("#searchForm").attr("action",$("#searchForm").attr("action")+"export");
                    validateFrom.validate($("#searchForm"));
                    $("#searchForm").attr("action",actionURL);
                    flag = false;
                    return
                }
            });
            if(flag){
                alertx("请选择导出条件!");
            }

			/*var actionURL = $("#searchForm").attr("action");
	        $("#searchForm").attr("action",$("#searchForm").attr("action")+"export");
			validateFrom.validate($("#searchForm"));
			$("#searchForm").attr("action",actionURL);*/
		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/settle/settleMerchantRecordQuery/">商户结算单据列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="settleMerchantRecord" action="${ctx}/settle/settleMerchantRecordQuery/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>会计日期：</label>
				<input id="beginCheckTime" name="beginCheckTime" type="text" readonly="readonly" maxlength="20" style="width:115px;" class="input-medium Wdate"
					value="<fmt:formatDate value="${settleMerchantRecord.beginCheckTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input id="endCheckTime" name="endCheckTime" type="text" readonly="readonly" maxlength="20" style="width:115px;" class="input-medium Wdate"
					value="<fmt:formatDate value="${settleMerchantRecord.endCheckTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>交易类型：</label>
				<form:select id="transType" path="transType" class="input-xlarge" style="width:180px;">
					<form:option value="" label="请选择"/>
					<c:forEach items="${productType}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>商户编码：</label>
				<form:input path="merchantId" onchange="checkValue(this.value)" htmlEscape="false" maxlength="10" class="input-medium required"/>
			</li>
		</ul>
		<ul class="ul-form">
			<li><label>结算日期：</label>
				<input id="beginSettleTime" name="beginSettleTime" type="text" readonly="readonly" maxlength="20" style="width:115px;" class="input-medium Wdate"
					value="<fmt:formatDate value="${settleMerchantRecord.beginSettleTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input id="endSettleTime" name="endSettleTime" type="text" readonly="readonly" maxlength="20" style="width:115px;" class="input-medium Wdate"
					value="<fmt:formatDate value="${settleMerchantRecord.endSettleTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>结算状态：</label>
				<form:select id="settleStatus" path="settleStatus" class="input-xlarge" style="width:180px;">
					<form:option value="" label="请选择"/>
					<c:forEach items="${settleStatus}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>结算单号：</label>
				<form:input path="settleBath" onchange="checkValue(this.value)" htmlEscape="false" maxlength="17" class="input-medium required"/>
			</li>
		</ul>
		<ul class="ul-form">
			<li><label>商户标识：</label>
				<form:select path="merchantFlag" htmlEscape="false" class="input-xlarge" style="width:130px;">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getEnumList('merchantFlag')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
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
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>交易总笔数</th>
			<th>交易总金额</th>
			<th>订单应结算总金额</th>
			<th>交易总手续费</th>
		</tr>
		</thead>
		<tbody>
		<tr>
			<td>${settleMerchantSumNum.sumPayNum} 笔</td>
			<td><fmt:formatNumber value="${settleMerchantSumNum.sumTotalAmount}" pattern="￥0.0000" /></td>
			<td><fmt:formatNumber value="${settleMerchantSumNum.sumSettleAmount}" pattern="￥0.0000" /></td>
			<td><fmt:formatNumber value="${settleMerchantSumNum.sumTotalFee}" pattern="￥0.0000" /></td>
		</tr>
		</tbody>
	</table>

	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>商户编码</th>
			<th>交易类型</th>
			<th>币种</th>
			<th>交易总笔数</th>
			<th>交易总金额</th>
			<th>会计日期</th>
			<th>结算日期</th>
			<th>结算单号</th>
			<th>订单应结算总金额</th>
			<th>订单结算周期</th>
			<th>交易总手续费</th>
			<th>结算状态</th>
			<th>查看明细</th>
			<shiro:hasPermission name="settle:settleMerchantRecord:edit"><th style="width: 150px">结算处理</th></shiro:hasPermission>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="settleMerchantRecord">
			<tr>
				<td>${settleMerchantRecord.merchantId}</td>
				<td>${settleMerchantRecord.transType}</td>
				<td>${settleMerchantRecord.currency}</td>
				<td>${settleMerchantRecord.payNum}</td>
				<td>
					<fmt:formatNumber value="${settleMerchantRecord.totalAmount}" pattern="￥0.0000" />
				</td>
				<td><fmt:formatDate value="${settleMerchantRecord.checkTime}" type="both" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${settleMerchantRecord.settleTime}" type="both" pattern="yyyy-MM-dd"/></td>
				<td>${settleMerchantRecord.settleBath}</td>
				<td>
					<fmt:formatNumber value="${settleMerchantRecord.settleAmount}" pattern="￥0.0000" />
				</td>
				<td>${settleMerchantRecord.settleCyc}</td>
				<td>
					<fmt:formatNumber value="${settleMerchantRecord.totalFee}" pattern="￥0.0000" />
				</td>
				<td <c:choose>
			     		<c:when test="${settleMerchantRecord.settleStatus=='已结算'}">
			     		 style="background:#6cf683" 	
			     		</c:when>
			     		<c:when test="${settleMerchantRecord.settleStatus=='未结算'}">
			     		 style="background:#707c9b" 
			     		</c:when>
			     		<c:when test="${settleMerchantRecord.settleStatus=='结算中'}">
			     		 style="background:#ffb300" 
			     		</c:when>
						<c:when test="${settleMerchantRecord.settleStatus=='不结算'}">
							style="background: #00ffff;"
						</c:when>
						<c:when test="${settleMerchantRecord.settleStatus=='处理中'}">
							style="background:red"
						</c:when>
			     	</c:choose>>${settleMerchantRecord.settleStatus}</td>
				<td>
					<a href="${ctx}/settle/settleMerchantRecordQuery/toDetail?settleBath=${settleMerchantRecord.settleBath}">查看明细</a>
				</td>
				<shiro:hasPermission name="settle:settleMerchantRecord:edit"><td>
					<c:choose>
						<c:when test="${not empty settleMerchantRecord.flag}">
							<c:if test="${settleMerchantRecord.settleStatus == '处理中'}">
								<a href="${ctx}/settle/settleMerchantRecordQuery/settle/1/${settleMerchantRecord.settleBath}" onclick="return confirmx('<h4 style=\'color: red;\'>是否取消结算,该记录再次结算过</h4>', this.href)" style="color: red">取消结算</a>
								|   <a href="${ctx}/settle/settleMerchantRecordQuery/settle/2/${settleMerchantRecord.settleBath}" onclick="return confirmx('<h4>确认要二次发起？</h4>', this.href)" style="color: red">二次发起</a>
							</c:if>
							<c:if test="${settleMerchantRecord.settleStatus != '处理中'}">
								<a href="${ctx}/settle/settleMerchantRecordQuery/settle/1/${settleMerchantRecord.settleBath}" onclick="return confirmx('确认要取消结算？', this.href)">取消结算</a>
								|   <a href="${ctx}/settle/settleMerchantRecordQuery/settle/2/${settleMerchantRecord.settleBath}" onclick="return confirmx('确认要再次结算？', this.href)">再次结算</a>
							</c:if>
						</c:when>
						<c:otherwise>
							--
						</c:otherwise>
					</c:choose>
				</td></shiro:hasPermission>
			</tr>

		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>