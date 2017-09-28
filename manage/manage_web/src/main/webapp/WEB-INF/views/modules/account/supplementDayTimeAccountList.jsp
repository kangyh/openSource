<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>补日间账务</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script language="JavaScript" src="${ctxStatic}/common/validateNum.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		//验证搜索条件
		var validateFrom = {
			validate: function(form){
				form.submit();
			}
		}

		//搜索
		function onSubmit(ta){
			$("#transNo").val($(ta).attr("title"));
			validateFrom.validate($("#searchForm"));
		}

		//清空
		function onClear(){
			$("#transNo").val("");
		}

		function onfind(){
			$("#contentTable").css("display","none");
			$("#tbodyHTML").html("");
			$.ajax({
				type : "POST",
				url : "${ctx}/account/splDayTime/findPaymentFundData",
				data : {
					"transNo" : $("#transNo").val()
				},
				dataType : "json",
				success : function(data){
					if(data.retCode=="0"){
						alert(data.retMsg);
					}else{
						$("#contentTable").css("display","");
						var thlength = $("#contentTable").find("th").length;
						var ht ="<tr>";
						ht =ht+"<td>"+data.merchantId+"</td>";
						ht =ht+"<td>"+data.merchantCompany+"</td>";
						ht =ht+"<td>"+data.paymentId+"</td>";
						ht =ht+"<td>"+data.transNo+"</td>";
						ht =ht+"<td>"+data.transType+"</td>";
						ht =ht+"<td>"+data.payAmount+"</td>";
						ht =ht+"<td>"+data.feeAmount+"</td>";
						ht =ht+"<td>"+data.feeType+"</td>";
						ht =ht+"<td>"+data.channelCode+"</td>";
						ht =ht+"<td>"+data.successTime+"</td>";
						if(thlength == 11){
							if(data.merchantLogFlag=="N"){
								ht =ht+"<td><a href='javascript:void(0)' title='"+data.transNo+"' onclick='onSubmit(this)'>补账</a></td>";
							}else{
								ht =ht+"<td></td>";
							}
						}
						ht =ht+"</tr>";
						$("#tbodyHTML").html(ht);
					}
				}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/account/splDayTime/toSplDayTime">补日间账务</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="merchantLog" action="${ctx}/account/splDayTime/splDayTimeAccount" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<li><label style="">交易号：</label>
				<input name="transNo" id="transNo" htmlEscape="false" maxlength="26" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onfind()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="clearfix"></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" style="display: none;" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>商户编码</th>
			<th>商户公司</th>
			<th>支付ID</th>
			<th>交易订单号</th>
			<th>交易类型</th>
			<th>支付金额</th>
			<th>手续费金额</th>
			<th>手续费收取方式</th>
			<th>通道代码</th>
			<th>成功时间</th>
			<shiro:hasPermission name="account:splDayTime:edit"><th>操作</th></shiro:hasPermission>
		</tr>
		</thead>
		<tbody id="tbodyHTML">

		</tbody>
	</table>
</body>
</html>