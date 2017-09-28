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
		function onExport(){

		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/wechat/wechatRecord/">微信支付订单列表</a></li>
		<shiro:hasPermission name="wechat:wechatRecord:edit"><li><a href="${ctx}/wechat/wechatRecord/form">微信支付订单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wechatRecord" action="${ctx}/wechat/wechatRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>交易号：</label>
				<form:input path="wechatId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>交易时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${wechatRecord.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${wechatRecord.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>商户订单号：</label>
				<form:input path="outTradeNo" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<!-- 
			<li><label>货币类型：</label>
				<form:input path="currency" htmlEscape="false" maxlength="16" class="input-medium"/>
			</li>
			<li><label>总金额：</label>
				<form:input path="totalFee" htmlEscape="false" class="input-medium"/>
			</li>
			 -->
			<li><label>交易类型 ：</label>
				<form:select id="tradeType" path="tradeType" class="input-xlarge">
					<form:option value="" label="全部"/>
					<form:option value="JSAPI" label="JSAPI"/>
					<form:option value="NATIVE" label="NATIVE"/>
					<form:option value="APP" label="APP"/>
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
			<!-- 
			<li><label>优惠总额：</label>
				<form:input path="couponFee" htmlEscape="false" class="input-medium"/>
			</li>
			 -->
			<li><label>微信订单号：</label>
				<form:input path="transactionId" htmlEscape="false" maxlength="256" class="input-medium"/>
			</li>
			<li><label>通道订单号：</label>
				<form:input path="passTradeNo" htmlEscape="false" maxlength="256" class="input-medium"/>
			</li>
			<!-- 
			<li><
			<li><label>通道代码：</label>
				<form:input path="channelCode" htmlEscape="false" maxlength="256" class="input-medium"/>
			</li> -->
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table  table-bordered table-condensed">
		<thead>
			<tr>
				<th>交易号</th>
				<th>商户ID</th>
				<th>微信公众号</th>
				<th>交易时间</th>
				<th>商户订单号</th>
				<th>货币类型</th>
				<th>总金额</th>
				<th>交易类型 </th>
				<th>成功交易金额</th>
				<th>交易状态</th>
				<th>手续费金额</th>
				<th>优惠总额</th>
				<th>微信订单号</th>
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
					${wechatRecord.wxAppid}
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
					${wechatRecord.totalFee}
				</td>
				<td>
					${wechatRecord.tradeType}
				</td>
				<td>
					${wechatRecord.successAmount}
				</td>
				<td <c:if test="${wechatRecord.status=='SUCCES'}">
			style="background:${success_background}" 
			</c:if>>
					${fns:getDictLabel(wechatRecord.status, 'WeChatRecordStatus', '')} 
				</td>
				<td>
					${wechatRecord.feeAmount}
				</td>
				<td>
					${wechatRecord.couponFee}
				</td>
				<td>
					${wechatRecord.transactionId}
				</td>
				<td>
					${wechatRecord.passTradeNo}
				</td>
				<td>
					${wechatRecord.channelCode}
				</td>
				<td>
					${wechatRecord.settleCyc}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>