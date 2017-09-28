<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人钱包转账管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnSubmit").on("click",function(){
				var merchantId = $("#merchantId").val();
				var fromMerchantId = $("#fromMerchantId").val();
				var toMerchantId=$("#toMerchantId").val();
				
				if(!isEmpty(merchantId)&&!/\d+/.test(merchantId)){
                    alertx("商户编码输入不合法，只能输入数字");
					return false;
				}
				if(!isEmpty(fromMerchantId)&&!/\d+/.test(fromMerchantId)){
                    alertx("转出商户号输入不合法，只能输入数字");
					return false;
				}
                if(!isEmpty(toMerchantId)&&!/\d+/.test(toMerchantId)){
                    alertx("转入商户号输入不合法，只能输入数字");
                    return false;
                }
				$("#searchForm").submit();
			});
			
			//控制输入框只能输入数字
			$("#merchantId,#fromMerchantId,#toMerchantId").keyup(function(){
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
		<li class="active"><a href="${ctx}/payment/wallet/transferList">个人钱包转账列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="innerTransferRecord" action="${ctx}/payment/wallet/transferList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户订单号：</label>
				<form:input path="merchantOrderNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>转出商户号：</label>
				<form:input path="fromMerchantId" htmlEscape="false" maxlength="18" class="input-medium"/>
			</li>
			<li><label>转入商户号：</label>
				<form:input path="toMerchantId" htmlEscape="false" maxlength="18" class="input-medium"/>
			</li>
			<li><label>主商户号：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="18" class="input-medium"/>
			</li>
			<li><label>转账状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="R1" label="全部"/>
					<form:options items="${fns:getDictList('InnerTransferRecordStatus')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>商户订单号</th>
				<th>转出账户</th>
				<th>转出账户名称</th>
				<th>转出商户号</th>
				<th>转出商户公司</th>
				<th>转账金额</th>
				<th>手续费金额</th>
				<th>手续费类型</th>
				<th>转入账户</th>
				<th>转入账户名称</th>
				<th>转入商户号</th>
				<th>转入商户公司</th>
				<th>主商户号</th>
				<th>转账时间</th>
				<th>转账状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="innerTransferRecord">
			<tr>
				<td>
					${innerTransferRecord.merchantOrderNo}
				</td>
				<td>
					${innerTransferRecord.fromAccountId}
				</td>
				<td>
					${innerTransferRecord.fromAccountName}
				</td>
				<td>
					${innerTransferRecord.fromMerchantId}
				</td>
				<td>
					${innerTransferRecord.fromMerchantCompany}
				</td>
				<td>
					${innerTransferRecord.transferAmount}
				</td>
				<td>
					${innerTransferRecord.feeAmount}
				</td>
				<td>
					${fns:getDictLabel(innerTransferRecord.feeType, 'FeeDeductType', '')}
				</td>
				<td>
					${innerTransferRecord.toAccountId}
				</td>
				<td>
					${innerTransferRecord.toAccountName}
				</td>
				<td>
					${innerTransferRecord.toMerchantId}
				</td>
				<td>
					${innerTransferRecord.toMerchantCompany}
				</td>
				<td>
					${innerTransferRecord.merchantId}
				</td>
				<td>
					<fmt:formatDate value="${innerTransferRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td <c:if test="${innerTransferRecord.status=='SUCCES'}">
					style="background:${success_background}"</c:if>>
					${fns:getDictLabel(innerTransferRecord.status, 'InnerTransferRecordStatus', '')}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>