<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>风控管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script language="JavaScript" src="${ctxStatic}/common/validateNum.js"></script>
	<script type="text/javascript">
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
				
				var merchantLoginNameId = $("#merchantLoginNameId").val();
				
				var pattern = new RegExp("[`~!#$^&*()=|{}':;',\\[\\]<>/?~！#￥……&*（）——|{}【】‘；：”“'。，、？]") 
				if(pattern.test(merchantLoginNameId)){
					$("#messageBox").text("商户账号输入不合法，请重新输入");
					return false;
				}
				
				var transNoId = $("#transNoId").val();
				if(isNaN(transNoId)){
					$("#messageBox").text("交易订单号请输入数字类型");
					return false;
				}
				
				var merchantId = $("#merchantId").val();
				if(isNaN(merchantId)){
					$("#messageBox").text("商户编码请输入数字类型");
					return false;
				}
				
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
			$("#pageNo").val(1);
			$("#formBtn").submit();
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/riskManage/RiskMerchantFreezeOrderQuery/view">商户订单显示列表</a></li>
	</ul>
	<form action="${ctx}/riskManage/RiskMerchantFreezeOrderQuery/view" method="post" id="formBtn"></form>
	<form:form id="searchForm" modelAttribute="riskForPaymentVO" action="${ctx}/riskManage/RiskMerchantFreezeOrderQuery/view" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
			<ul class="ul-form">
			<li><label>交易类型：</label>
				<form:select id="transType" path="transType" class="input-xlarge">
					<form:option value="" label="-选择交易类型-"/>
					<c:forEach items="${transType}" var="transTypeControl">
							<form:option value="${transTypeControl.value}" label="${transTypeControl.name}"/>
						</c:forEach>
				</form:select>
			</li> 
			
			<li><label>商户编码：</label>
				<form:input path="merchantId" id="merchantId" htmlEscape="false" maxlength="20" class="input-medium" style="width:256px;"/>
			</li>
			
			</ul>
			<ul class="ul-form">
			<li><label>商户账号：</label>
				<form:input path="merchantLoginName" id="merchantLoginNameId" htmlEscape="false" maxlength="64" class="input-medium" style="width:256px;"/>
			</li>
			<li><label>交易订单号：</label>
				<form:input path="transNo" id="transNoId" htmlEscape="false" maxlength="26" class="input-medium" style="width:256px;"/>
			</li>
			</ul>
			<ul class="ul-form">
			<li><label>	支付成功时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" style="width:150px;" class="input-medium Wdate"
					value="<fmt:formatDate value="${riskForPaymentVO.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss" />"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" style="width:150px;" class="input-medium Wdate"
					value="<fmt:formatDate value="${riskForPaymentVO.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss" />"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			
			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="clearfix"></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
		
	</form:form>
	<sys:message content="${message}"/>

	<table id="contentTable" class="table  table-striped   table-bordered table-condensed">
		<thead>
		<thead>
		<tr>
			<th>商户编码</th>
			<th>商户账号</th>
			<th>支付成功时间</th>
			<th>交易类型 </th>
			<th>实际支付金额</th>
			<th>交易订单号</th>
			<th>手续费扣除方式</th>
			<th>手续费</th>
			
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="riskForPaymentVO">
			<c:if test="${riskForPaymentVO.repeat !='Y'}">
				<tr>
					<td>${riskForPaymentVO.merchantId}</td>
					<td>${riskForPaymentVO.merchantLoginName}</td>
					<td><fmt:formatDate value="${riskForPaymentVO.successTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					
					<td>${riskForPaymentVO.transType}</td>
					<td>
						<fmt:formatNumber value="${riskForPaymentVO.successAmount}" pattern="￥0.00" />
					</td>
					
					<td>${riskForPaymentVO.transNo}</td>
					<td>
					<c:choose>
						<c:when test="${empty riskForPaymentVO.feeWay}">
							--
						</c:when>
						<c:otherwise>
							${riskForPaymentVO.feeWay}				
						</c:otherwise>
					</c:choose>	
					</td>			
					<td>
						<fmt:formatNumber value="${riskForPaymentVO.fee}" pattern="￥0.00" />
					</td>				
					
					<td>
						<a style="cursor:pointer;" class="checkPass"  value-url="${ctx}/riskManage/RiskMerchantFreezeOrderQuery/view/addAmount/${riskForPaymentVO.merchantId}/${riskForPaymentVO.successAmount}/${riskForPaymentVO.transNo}/${page.pageNo}/${riskForPaymentVO.fee}/${riskForPaymentVO.feeWayVo}">冻结</a>
					</td>
				</tr>
			</c:if>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>