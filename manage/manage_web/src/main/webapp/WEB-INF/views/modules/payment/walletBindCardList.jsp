<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人钱包绑卡管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnSubmit").on("click",function(){
				var merchantId = $("#merchantId").val();
				var transNo = $("#transNo").val();
				var merchantUserId=$("#merchantUserId").val();
				
				if(!isEmpty(merchantId)&&!/\d+/.test(merchantId)){
					alertx("商户号输入不合法，只能输入数字");
					return false;
				}
				if(!isEmpty(transNo)&&!/\d+/.test(transNo)){
					alertx("交易号输入不合法，只能输入数字");
					return false;
				}
                if(!isEmpty(merchantUserId)&&!/\d+/.test(merchantUserId)){
                    alertx("商户用户号输入不合法，只能输入数字");
                    return false;
                }
				$("#searchForm").submit();
			});
			
			//控制输入框只能输入数字
			$("#transNo,#merchantId,#merchantUserId").keyup(function(){
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
		<li class="active"><a href="${ctx}/payment/wallet/bindCardList">个人钱包绑卡列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="bindCardAuth" action="${ctx}/payment/wallet/bindCardList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>交易号：</label>
				<form:input path="transNo" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>商户号：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="10" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}
					else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label>商户用户号：</label>
				<form:input path="merchantUserId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>绑卡状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="R1" label="全部"/>
					<form:options items="${fns:getDictList('BankcardAuthStatus')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th width="150">汇付宝交易号</th>
				<th>商户号</th>
				<th>商户用户号</th>
				<th>持卡人姓名</th>
				<th>持卡人身份证号</th>
				<th>银行卡号</th>
				<th>银行名称</th>
				<%--<th>银行卡持卡人类型</th>--%>
				<th>银行卡预留手机号</th>
				<th>银行卡类型</th>
				<th>签约状态</th>
				<th>创建时间</th>
				<th>更新时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bc">
			<tr>
				<td>
					${bc.transNo}
				</td>
				<td>
					${bc.merchantId}
				</td>
				<td>
					${bc.merchantUserId}
				</td>
				<td>
					${bc.bankcardOwnerName}
				</td>
				<td>
					${bc.bankcardOwnerIdcard}
				</td>
				<td>
					${bc.bankcardNo}
				</td>
				<td>
					${bc.bankName}
				</td>
				<%--<td>--%>
					<%--${fns:getDictLabel(bc.bankcardOwnerType, 'BankcardOwnerType', '')}--%>
				<%--</td>--%>
				<td>
					${bc.bankcardOwnerMobile}
				</td>
				<td>
					${fns:getDictLabel(bc.bankcardType, 'BankcardType', '')}
				</td>
				<td <c:if test="${bc.status=='SUCCES'}">
					style="background:${success_background}"</c:if>>
					${fns:getDictLabel(bc.status, 'BankcardAuthStatus', '')}
				</td>
				<td>
					<fmt:formatDate value="${bc.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${bc.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>