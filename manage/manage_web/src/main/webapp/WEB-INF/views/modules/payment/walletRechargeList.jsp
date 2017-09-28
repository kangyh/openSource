<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人钱包充值管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnSubmit").on("click",function(){
				var merchantId = $("#merchantId").val();
				var transNo = $("#transNo").val();
				var walletMerchantId=$("#walletMerchantId").val();
				
				if(!isEmpty(merchantId)&&!/\d+/.test(merchantId)){
					alertx("用户ID输入不合法，只能输入数字");
					return false;
				}
				if(!isEmpty(transNo)&&!/\d+/.test(transNo)){
					alertx("交易订单号输入不合法，只能输入数字");
					return false;
				}
                if(!isEmpty(walletMerchantId)&&!/\d+/.test(walletMerchantId)){
                    alertx("商户号输入不合法，只能输入数字");
                    return false;
                }
				$("#searchForm").submit();
			});
			
			//控制输入框只能输入数字
			$("#transNo,#walletMerchantId,#merchantId,#merchantOrderNo").keyup(function(){
				 if(this.value.indexOf(".")==0){
					 this.value="0"+this.value;
				 }
				 this.value = this.value.replace(/[^\d]/g,'');
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		function isEmpty(value){
	 		if(value==undefined||value==""||value.trim()==""){
	 			return true;
	 		}
	 		return false;
	 	}
		//清空
		function onClear(){
			$("#searchForm").find("input[type='text']").val("");
 			//默认支付状态
			$("#status").find("option").removeAttr("selected");
			$("#status").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#status option[selected]").text());
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/wallet/rechargeList">个人钱包充值列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="paymentRecord" action="${ctx}/payment/wallet/rechargeList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>交易订单号：</label>
				<form:input path="transNo" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>商户订单号：</label>
				<form:input path="merchantOrderNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>用户ID：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="18" class="input-medium"/>
			</li>
			<li><label>商户号：</label>
				<form:input path="walletMerchantId" htmlEscape="false" maxlength="18" class="input-medium"/>
			</li>
			<li><label>充值状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="R1" label="全部"/>
					<form:options items="${fns:getDictList('WalletChargeStatus')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="150">交易订单号</th>
				<th>商户订单号</th>
				<th>商户号</th>
				<th>用户ID</th>
				<th>充值金额</th>
				<th>手续费</th>
				<th>手续费扣除方式</th>
				<th width="150">支付时间</th>
				<th>成功金额</th>
				<th width="150">成功时间</th>
				<th>充值状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="pay">
			<tr>
				<td>
					${pay.transNo}
				</td>
				<td>
					${pay.merchantOrderNo}
				</td>
				<td>
					${pay.walletMerchantId}
				</td>
				<td>
					${pay.merchantId}
				</td>
				<td>
					${pay.payAmount}
				</td>
				<td>
					${pay.feeAmount}
				</td>
				<td>
					${fns:getDictLabel(pay.feeType, 'FeeDeductType', '')}
				</td>
								<td>
					<fmt:formatDate value="${pay.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${pay.successAmount}
				</td>
				<td>
					<fmt:formatDate value="${pay.successTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td <c:if test="${pay.status=='SUCCES'}">
					style="background:${success_background}"</c:if>>
					${fns:getDictLabel(pay.status, 'WalletChargeStatus', '')}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>