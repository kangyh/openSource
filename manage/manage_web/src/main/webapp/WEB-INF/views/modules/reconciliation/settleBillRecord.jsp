<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>对账管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<style>
        #main {
            margin: 50px;
        }
    </style>
	<script type="text/javascript">
		//验证搜索条件
		var validateFrom = {
			validate: function(form){
				
				var checkBathNo = $("#checkBathNo").val();
				var pattern = new RegExp("[`~!#$^@&*()=|{}':;',\\[\\]<>/?~！#￥……&*（）——|{}【】‘；：”“'。，、？]") 
				if(pattern.test(checkBathNo)){
					$("#messageBox").text("对账批次号输入不合法，请重新输入");
					return false;
				}
				
				var paymentId = $("#paymentId").val();
				if(isNaN(paymentId)){
					$("#messageBox").text("支付单号请输入数字类型");
					return false;
				}
				
				var channleNo = $("#channleNo").val();
				if(pattern.test(channleNo)){
                    $("#messageBox").text("上游流水号输入不合法，请重新输入");
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

		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/reconciliation/settleBillRecord/">账单明细列表</a></li>
	</ul>
	<form action="${ctx}/reconciliation/settleBillRecord" method="post" id="formBtn"></form>
	<form:form id="searchForm" modelAttribute="settleBillRecord" action="${ctx}/reconciliation/settleBillRecord" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">

			<ul class="ul-form">
			<li><label>通道编码：</label>
				<form:select id="channelCode" path="channelCode" style="width:225px;" class="input-xlarge">
					<form:option value="" label="-选择通道编码-"/>
					<c:forEach items="${channelCodeEnum}" var="channelCode">
						<form:option value="${channelCode.value}" label="${channelCode.name}"/>
					</c:forEach>
				</form:select>
			</li>

				<li><label>通道业务类型：</label>
					<form:select id="channelType" path="channelType" style="width:225px;" class="input-xlarge">
						<form:option value="" label="-选择通道业务类型-"/>
						<c:forEach items="${channelType}" var="type">
							<form:option value="${type.value}" label="${type.name}"/>
						</c:forEach>
					</form:select>
				</li>

				<li><label>支付订单号：</label>
					<form:input path="paymentId" id="paymentId" value="${settleDifferRrecord.paymentId}" htmlEscape="false" maxlength="40" class="input-medium" placeholder="请输入支付订单号" style="width:210px;"/>
			</li>
			</ul>
			<ul class="ul-form">
			<li><label>上游流水号：</label>
					<form:input path="channleNo" id="channleNo" value="${settleDifferRrecord.channleNo}" htmlEscape="false" maxlength="40" class="input-medium" placeholder="请输入上游流水号" style="width:210px;"/>
			</li>
			<li><label>对账批次号：</label>
					<form:input path="checkBathNo" id="checkBathNo" value="${settleDifferRrecord.checkBathNo}" htmlEscape="false" maxlength="40" class="input-medium" placeholder="对账批次号" style="width:210px;"/>
			</li>
			<li><label>摘要：</label>
					<form:input path="resultPass" id="remark" value="${settleDifferRrecord.resultPass}" htmlEscape="false" maxlength="80" class="input-medium" placeholder="请输入摘要" style="width:210px;"/>
			</li>
			</ul>
			<ul class="ul-form">
			
			<li><label>入库时间：</label>
					<input id="beginOperEndTime" name="beginOperEndTime" type="text" readonly="readonly" maxlength="20"  style="width:95px;cursor: pointer" class="input-medium Wdate"
					value="<fmt:formatDate value="${settleBillRecord.beginOperEndTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input id="endOperEndTime" name="endOperEndTime" type="text" readonly="readonly" maxlength="20" style="width:95px;cursor: pointer" class="input-medium Wdate"
					value="<fmt:formatDate value="${settleBillRecord.endOperEndTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清除"/></li>
		</ul>
		<br>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
		
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>通道编码</th>
			<th>通道业务类型</th>
			<th>入库时间</th>
			<th>支付订单号</th>
			<th>上游流水号</th>

			<th>支付资金</th>
			<th>手续费</th>
			
			<th>对账批次</th>
			<th>优惠金额</th>
			<th>服务费</th>
			<th>摘要</th>
			<th>账单状态</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="settleDifferRrecord">
			<tr>
				<td>${settleDifferRrecord.channelCode}</td>
				<td>${settleDifferRrecord.channelType}</td>
				<td><fmt:formatDate value="${settleDifferRrecord.saveTime}" type="both" pattern="yyyy-MM-dd"/></td>

				<td>${settleDifferRrecord.paymentId}</td>
				<td>${settleDifferRrecord.channleNo}</td>
				<td>
					<fmt:formatNumber value="${settleDifferRrecord.successAmount}" pattern="￥0.0000" />
				</td>
				<td>
					<fmt:formatNumber value="${settleDifferRrecord.fee}" pattern="￥0.0000" />
				</td>
				<td>${settleDifferRrecord.checkBathNo}</td>
				<td>
					<fmt:formatNumber value="${settleDifferRrecord.promotionAmount}" pattern="￥0.0000" />
				</td>
				<td>
					<fmt:formatNumber value="${settleDifferRrecord.feeService}" pattern="￥0.0000" />
				</td>
				<td>${settleDifferRrecord.remark}</td>
				<td <c:choose>
					<c:when test="${settleDifferRrecord.billStatus=='处理完'}">
						style="background:#6cf683"
					</c:when>
					<c:when test="${settleDifferRrecord.billStatus=='未处理'}">
						style="background:#707c9b"
					</c:when>
				</c:choose>>
						${settleDifferRrecord.billStatus}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
</body>
</html>