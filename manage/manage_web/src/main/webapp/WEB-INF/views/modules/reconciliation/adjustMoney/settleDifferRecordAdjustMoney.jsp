<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/modules/sys/validation.jsp"%>
<html>
<head>
	<title>调账管理</title>
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
				
				var paymentIdid = $("#paymentIdid").val();
				if(isNaN(paymentIdid)){
					$("#messageBox").text("支付单号请输入数字类型");
					return false;
				}
				
				var transNoid = $("#transNoid").val();
				if(isNaN(transNoid)){
					$("#messageBox").text("交易订单号请输入数字类型");
					return false;
				}
				
				
				var bgTime = $("#beginCheckTime").val();
				var endTime = $("#endCheckTime").val();
				if(bgTime=="" && endTime!="" || bgTime!="" && endTime=="" ){
					$("#messageBox").text("请正确设置查询时间范围!");
					return false;
				}
				if( bgTime!="" && endTime!=""){
					if(compareDate(convertDateToJoinStr(bgTime),
									convertDateToJoinStr(endTime)) > 0){
						$("#messageBox").text("起始日期不能大于结束日期!");
						return false;
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/adjustMoney/settleDifferRrecord/">对账差错列表</a></li>
	</ul>
	<form action="${ctx}/adjustMoney/settleDifferRrecord/" method="post" id="formBtn"></form>
	<form:form id="searchForm" modelAttribute="settleDifferRecord" action="${ctx}/adjustMoney/settleDifferRrecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			 
			<li><label class="control-label">通道合作方：</label>
			    	<form:select id="channelCode" path="channelCode" class="input-xlarge" style="width:225px;" onchange="Sel2change(this.options[this.options.selectedIndex].text);">
						<form:option value="" label="-通道合作方-"/>
						<c:forEach items="${checklist}" var="SettleRuleControl">
							<form:option value="${SettleRuleControl.channelCode}" label="${SettleRuleControl.channelName}"/>
						</c:forEach>
					</form:select>
					<input type="hidden" name="channelName" id="channelName" class="input-xlarge required"> 
			</li>
			
			<li><label>支付通道类型：</label>
				<form:select id="channelType" path="channelType" style="width:225px;" class="input-xlarge">
					<form:option value="" label="-支付通道类型-"/>
						<c:forEach items="${checkTypeList}" var="SettleRuleControl">
							<form:option value="${SettleRuleControl.value}" label="${SettleRuleControl.name}"/>
						</c:forEach>
				</form:select> 
			
			<li><label>交易类型：</label>
				<form:select id="transType" path="transType" style="width:225px;" class="input-xlarge">
					<form:option value="" label="-选择交易类型-"/>
					<c:forEach items="${transType}" var="transTypeControl">
							<form:option value="${transTypeControl.value}" label="${transTypeControl.name}"/>
						</c:forEach>
				</form:select>
			</li> 
			</ul>
			<ul class="ul-form">

			<li><label>支付单号：</label>
				<form:input path="paymentId" id="paymentIdid" htmlEscape="false" maxlength="40" class="input-medium" placeholder="请输入7位数字" style="width:210px;"/>
			</li> 
			<li><label>交易订单号：</label>
					<form:input path="transNo" id="transNoid" htmlEscape="false" maxlength="40" class="input-medium" placeholder="请输入20位数字" style="width:210px;"/>
			</li>
			
			<li><label>差错日期：</label>
					<input id="beginCheckTime" name="beginCheckTime" type="text" readonly="readonly" maxlength="20"  style="width:95px;" class="input-medium Wdate"
					value="<fmt:formatDate value="${settleDifferRecord.beginCheckTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input id="endCheckTime" name="endCheckTime" type="text" readonly="readonly" maxlength="20" style="width:95px;" class="input-medium Wdate"
					value="<fmt:formatDate value="${settleDifferRecord.endCheckTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清除"/></li>

			<c:if test="${not empty adjust}">
				<li class="btns"><input class="btn btn-primary" type="button" onclick="window.location='${ctx}/reconciliation/SettleDifferRrecord'" value="返回"/></li>
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
			<th>交易类型</th>

			<th>差错日期</th>
			<th>差错类型</th>
			<th>挂账单号</th>
			<th>支付单号</th>
			<th>交易订单号</th>
			
			<th>我方金额</th>
			<th>对方金额</th>
			<th>分润标识</th>
			<shiro:hasPermission name="adjustMoney:settleDifferRrecord:edit"><th>操作</th></shiro:hasPermission>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="settleDifferRrecord">
			<tr>
				<td>${settleDifferRrecord.channelName}</td>
				<td>${settleDifferRrecord.channelType}</td>
				<td>${settleDifferRrecord.transType}</td>

				<td><fmt:formatDate value="${settleDifferRrecord.errorDate}" type="both" pattern="yyyy-MM-dd"/></td>
				
				<td>${settleDifferRrecord.differType}</td>
				<td>${settleDifferRrecord.settleBath}</td>
				<td><a href="${ctx}/reconciliation/SettleDifferRrecord/more/${settleDifferRrecord.differId} ">${settleDifferRrecord.paymentId}</a></td>
				<td>${settleDifferRrecord.transNo}</td>
				
				<td>
					<fmt:formatNumber value="${settleDifferRrecord.requestAmount}" pattern="￥0.0000" />
				</td>
				
				<td>
					<fmt:formatNumber value="${settleDifferRrecord.successAmount}" pattern="￥0.0000" />
					<c:choose>
						<c:when test="${settleDifferRrecord.QT=='YQT'}">
							--
						</c:when>
					</c:choose>
				</td>
				<td>${settleDifferRrecord.isProfit}</td>
				<shiro:hasPermission name="adjustMoney:settleDifferRrecord:edit"><td <c:choose>
			     		<c:when test="${settleDifferRrecord.QT=='QT'}">
			     		 style="background:rebeccapurple"
			     		</c:when>
			     	</c:choose>>
				<c:choose>
					<c:when test="${settleDifferRrecord.QT=='QT'}">
					<a id="qtAddId"  style="cursor:pointer;" class="checkPass"  value-url="${ctx}/adjustMoney/settleDifferRrecord/qt/${settleDifferRrecord.differId}">调账</a>
					</c:when>
				</c:choose>
				</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
</body>
</html>