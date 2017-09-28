<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会聚财转账查询管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnSubmit").on("click",function(){
				var merchantId = $("#merchantId").val();
				var certificationId = $("#certificationId").val();
				var merchantCompany=$("#merchantCompany").val();
				
				if(!isEmpty(merchantId)&&!/\d+/.test(merchantId)){
					alert("商户编码输入不合法，只能输入数字");
					return ;
				}
				if(!isEmpty(certificationId)&&!/\d+/.test(certificationId)){
					alert("交易订单号输入不合法，只能输入数字");
					return ;
				}
				if(!isEmpty(merchantCompany)&&!/^[a-zA-Z0-9\u4e00-\u9fa5()（）]+$/.test(merchantCompany)){
					alert("商户公司名称输入不合法，请重新输入");
					return ;
				}
				
				
				$("#searchForm").submit();
			});
			
			//控制输入框只能输入数字
			$("#merchantId,#certificationId").keyup(function(){
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
			//默认排序方式
			$("#productCode").find("option").removeAttr("selected");
			$("#productCode").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(1)").text($("#productCode option[selected]").text());
			
 			//默认支付状态
			$("#status").find("option").removeAttr("selected");
			$("#status").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#status option[selected]").text());
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/innerTransferRecord/">会聚财转账查询列表</a></li>
		<shiro:hasPermission name="payment:innerTransferRecord:edit"><li><a href="${ctx}/payment/innerTransferRecord/form">会聚财转账查询添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="innerTransferRecord" action="${ctx}/payment/innerTransferRecord/" method="post" class="breadcrumb form-search">
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
			<li><label>转账类型：</label>
				<form:select path="productCode" class="input-medium">
					<form:option value="R1" label="全部"/>
					<form:options items="${fns:getDictList('HJCTransType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>转账状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="R1" label="全部"/>
					<form:options items="${fns:getDictList('PaymentRecordStatus')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户订单号</th>
				<th>转出账户</th>
				<th>转出账户名称</th>
				<th>转出商户号</th>
				<th>转出商户公司</th>
				<th>转账类型</th>
				<th>转账金额</th>
				<th>手续费金额</th>
				<th>手续费类型</th>
				<th>转入账户</th>
				<th>转入账户名称</th>
				<th>转入商户号</th>
				<th>转入商户公司</th>
				<th>转账时间</th>
				<th>转账状态</th>
				<shiro:hasPermission name="payment:innerTransferRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="innerTransferRecord">
			<tr>
				<td><a href="${ctx}/payment/innerTransferRecord/form?id=${innerTransferRecord.id}">
					${innerTransferRecord.merchantOrderNo}
				</a></td>
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
					${fns:getDictLabel(innerTransferRecord.productCode, 'ProductCodeType', '')}
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
					<fmt:formatDate value="${innerTransferRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(innerTransferRecord.status, 'PaymentRecordStatus', '')}
				</td>
				<shiro:hasPermission name="payment:innerTransferRecord:edit"><td>
    				<a href="${ctx}/payment/innerTransferRecord/form?id=${innerTransferRecord.id}">修改</a>
					<a href="${ctx}/payment/innerTransferRecord/delete?id=${innerTransferRecord.id}" onclick="return confirmx('确认要删除该会聚财转账查询吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>