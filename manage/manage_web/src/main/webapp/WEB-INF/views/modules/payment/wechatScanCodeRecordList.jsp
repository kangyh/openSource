<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信支付订单管理</title>
	<meta name="decorator" content="default"/>
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
			validateFrom.validate($("#searchForm"));
		}

		//清空
		function onClear(){
			$("#searchForm").find("input[type='text']").val("");
			//支付状态
			$("#paymentStatus").find("option").removeAttr("selected");
			$("#paymentStatus").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#paymentStatus option[selected]").text());
			//交易类型
			$("#transType").find("option").removeAttr("selected");
			$("#transType").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(1)").text($("#transType option[selected]").text());
			//默认支付状态
			$("#payType").find("option").removeAttr("selected");
			$("#payType").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(2)").text($("#payType option[selected]").text());
			//默认排序方式
			$("#sortOrder").find("option").removeAttr("selected");
			$("#sortOrder").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(3)").text($("#sortOrder option[selected]").text());
		}

		//导出
		function exportExcel(){
			var wechatId = $("#wechatId").val();
			var merchantId = $("#merchantId").val();
			var outTradeNo = $("#outTradeNo").val();
			var tradeType = $("#tradeType").val();
			var beginCreateTime = $("#beginCreateTime").val();
			var endCreateTime = $("#endCreateTime").val();
			var passTradeNo = $("#passTradeNo").val();
			var status = $("#status").val();
			var groupType = $("input[name='groupType']:checked").val();
			var host = "${ctx}/wechatScanCode/wechatScanCodeRecord/exportExcel";
			var url = host+"?wechatId="+wechatId+"&merchantId="+merchantId + "&outTradeNo="+outTradeNo +
					"&tradeType="+tradeType+"&beginCreateTime="+beginCreateTime+"&endCreateTime="+endCreateTime+
					"&passTradeNo="+passTradeNo+"&status="+status+"&groupType="+groupType;
			$('<form method="post" action="' + url + '"></form>').appendTo('body').submit().remove();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/wechatScanCode/wechatScanCodeRecord/">微信支付订单列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="wechatRecord" action="${ctx}/wechatScanCode/wechatScanCodeRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>交易订单号：</label>
				<form:input path="wechatId" id="wechatId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>商户ID：</label>
				<form:input path="merchantId" id="merchantId" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>				
			<li><label>商户订单号：</label>
				<form:input path="outTradeNo" id="outTradeNo" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>交易类型 ：</label>
				<form:select id="tradeType" path="tradeType" class="input-xlarge">
					<form:option value="" label="全部"/>
					<form:option value="weixin_qr" label="微信扫码支付"/>
					<form:option value="weixin_pub" label="微信公众号支付"/>
					<form:option value="weixin_h5" label="微信H5支付"/>
					<form:option value="alipay_qr" label="支付宝扫码支付"/>
					<form:option value="alipay_wap" label="支付宝WAP支付"/>				
				</form:select>
			</li>
			<li><label>交易状态：</label>
			<form:select id="status" path="status" class="input-xlarge">
					<form:option value="" label="全部"/>
					<c:forEach items="${fns:getDictList('WeChatRecordStatus')}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.label}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>通道订单号：</label>
				<form:input path="passTradeNo" id="passTradeNo" htmlEscape="false" maxlength="256" class="input-medium"/>
			</li>
			<li><label>交易时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${wechatRecord.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${wechatRecord.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input class="btn btn-warning" type="button" onclick="exportExcel()" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table class="table table-striped   table-bordered table-condensed">
		<thead>
			<tr>
				<th>成功总笔数</th>
				<th>成功总金额</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td id="successTotalSum">0笔</td>
				<td id="successTotalAmount">￥0.00</td>
			</tr>
		</tbody>
	</table>
	<table id="contentTable" class="table  table-bordered table-condensed">
		<thead>
			<tr>
				<th>交易订单号</th>
				<th>商户编码</th>
				<th>交易时间</th>
				<th>商户订单号</th>
				<th>货币类型</th>
				<th>总金额</th>
				<th>交易类型 </th>
				<th>成功交易金额</th>
				<th>成功交易时间</th>
				<th>交易状态</th>
				<th>手续费金额</th>
				<th>通道订单号</th>
				<th>通道代码</th>
				<th>结算类型</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wechatRecord">
			<tr>
				<td>
					${wechatRecord.wechatId}
				</td>
				<td>
					${wechatRecord.merchantId}
				</td>
				<td>
					<fmt:formatDate value="${wechatRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${wechatRecord.outTradeNo}
				</td>
				<td>
					<c:if test="${wechatRecord.currency=='156'}">
					人民币
					</c:if>
				</td>
				<td>
					<fmt:formatNumber value="${wechatRecord.totalFee}" pattern="￥0.00" />
				</td>
				<td>
					<c:if test="${wechatRecord.tradeType == 'weixin_qr'}">微信扫码支付</c:if>
					<c:if test="${wechatRecord.tradeType == 'weixin_pub'}">微信公众号支付</c:if>
					<c:if test="${wechatRecord.tradeType == 'weixin_h5'}">微信H5支付</c:if>
					<c:if test="${wechatRecord.tradeType == 'alipay_qr'}">支付宝扫码支付</c:if>
					<c:if test="${wechatRecord.tradeType == 'alipay_wap'}">支付宝WAP支付</c:if>					

				</td>
				<td>
					<fmt:formatNumber value="${wechatRecord.successAmount}" pattern="￥0.00" />
				</td>
				<td>
					<fmt:formatDate value="${wechatRecord.successTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td <c:if test="${wechatRecord.status=='SUCCES'}"> style="background:${success_background}" </c:if>>
					${fns:getDictLabel(wechatRecord.status, 'WeChatRecordStatus', '')} 
				</td>
				<td>
					<fmt:formatNumber value="${wechatRecord.feeAmount}" pattern="￥0.00" />
				</td>
				<td>
					${wechatRecord.passTradeNo}
				</td>
				<td>
					${wechatRecord.channelCode}
				</td>
				<td>
					<c:if test="${wechatRecord.settleCyc == '0'}">T+0结算</c:if>
					<c:if test="${wechatRecord.settleCyc == '1'}">T+1结算</c:if>
					<c:if test="${wechatRecord.settleCyc == 'x'}">T+x结算</c:if>
					
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script type="text/javascript">
		$(function(){
			var wechatId = $("#wechatId").val();
			var merchantId = $("#merchantId").val();
			var outTradeNo = $("#outTradeNo").val();
			var tradeType = $("#tradeType").val();
			var beginCreateTime = $("#beginCreateTime").val();
			var endCreateTime = $("#endCreateTime").val();
			var passTradeNo = $("#passTradeNo").val();
			var status = $("#status").val();
			var groupType = $("input[name='groupType']:checked").val();
			$.ajax({
				type : "POST",
				url : "${ctx}/wechatScanCode/wechatScanCodeRecord/getStatisticsList",
				data : {
					"wechatId" : wechatId,
					"merchantId" : merchantId,
					"outTradeNo" : outTradeNo,
					"tradeType" : tradeType,
					"beginCreateTime" : beginCreateTime,
					"endCreateTime" : endCreateTime,
					"passTradeNo" : passTradeNo,
					"status" : status,
					"groupType" : groupType
				},
				dataType : "json",
				success : function(data){
					$("#successTotalSum").text(data.successTotalSum);
					$("#successTotalAmount").text(data.successTotalAmount);
				}
			});
			
		});
	</script>
</body>
</body>
</html>