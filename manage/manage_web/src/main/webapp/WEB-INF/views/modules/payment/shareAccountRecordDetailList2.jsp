<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分润明细管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/validateNum.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});

		var validateFrom = {
			validate: function(form){
				var shareId = $("#shareId").val();
				var shareIdResult = validateNum(shareId,"分润号请输入数字!");
				var transNo = $("#transNo").val();
				var transNoResult = validateNum(transNo,"原交易订单号请输入数字!");
				var masterMerchantLoginName = $("#masterMerchantLoginName").val();
				var masterMerchantLoginNameResult = validatePreventInject(masterMerchantLoginName,"分润商户账号输入不合法!");
				var masterMerchantCompany = $("#masterMerchantCompany").val();
				var masterMerchantCompanyResult = validatePreventInject(masterMerchantCompany,"分润商户公司输入不合法!");
				var slaverMerchantLoginName = $("#slaverMerchantLoginName").val();
				var slaverMerchantLoginNameResult = validatePreventInject(slaverMerchantLoginName,"入款商户账号输入不合法!");
				var slaverMerchantCompany = $("#slaverMerchantCompany").val();
				var slaverMerchantCompanyResult = validatePreventInject(slaverMerchantCompany,"分润商户公司输入不合法!");
				var shareAmount = $("#shareAmount").val();
				var shareAmountResult = validateAmount(shareAmount,"分润金额输入不合法!");
				if(!shareIdResult || !transNoResult || !masterMerchantLoginNameResult || !masterMerchantCompanyResult
						|| !slaverMerchantLoginNameResult || !slaverMerchantCompanyResult || !shareAmountResult){
					return;
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
			//默认支付状态
			$("#status").find("option").removeAttr("selected");
			$("#status").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#status option[selected]").text());
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/share/shareAccountRecordDetail/">分润明细列表</a></li>
		<shiro:hasPermission name="share:shareAccountRecordDetail:edit"><li><a href="${ctx}/share/shareAccountRecordDetail/form">分润明细添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="shareAccountRecordDetail" action="${ctx}/share/shareAccountRecordDetail/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>分润号：</label>
				<form:input path="shareId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>原交易订单号：</label>
				<form:input path="transNo" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			
			<li><label>分润商户编码：</label>
				<form:input path="masterMerchantId" htmlEscape="false" maxlength="20" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')} else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label>分润商户账号：</label>
				<form:input path="masterMerchantLoginName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>分润商户公司：</label>
				<form:input path="masterMerchantCompany" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			
			<li><label>入款商户编码：</label>
				<form:input path="slaverMerchantId" htmlEscape="false" maxlength="20" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')} else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label>入款商户账号：</label>
				<form:input path="slaverMerchantLoginName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>入款商户公司：</label>
				<form:input path="slaverMerchantCompany" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			
			
			
			<li><label>分润金额：</label>
				<form:input path="shareAmount" htmlEscape="false" class="input-medium" />
			</li>
			
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<c:forEach items="${fns:getDictList('ShareStatus')}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.label}"/>
					</c:forEach>
				</form:select>
			</li>
			
			
			
			<!--  
			<li><label>原订单创建时间：</label>
				<input name="transCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${shareAccountRecordDetail.transCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			-->
			
			<li><label>分润发起时间：</label>
					<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${shareAccountRecordDetail.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${shareAccountRecordDetail.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			
			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>自增主键</th>
				<th>分润号</th>
				<th>分润商户编码</th>
				<th>分润商户账号</th>
				<th>分润商户公司</th>
				<th>入款商户编码</th>
				<th>入款商户账号</th>
				<th>入款商户公司</th>
				<th>分润金额</th>
				<th>创建日期</th>
				<th>修改日期</th>
				<th>原交易订单号</th>
				<th>原订单创建时间</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="shareAccountRecordDetail">
			<tr  >
				<td>
					${shareAccountRecordDetail.shareDetailId}
				</td>
				<td>
					${shareAccountRecordDetail.shareId}
				</td>
				<td>
					${shareAccountRecordDetail.masterMerchantId}
				</td>
				<td>
					${shareAccountRecordDetail.masterMerchantLoginName}
				</td>
				<td>
					${shareAccountRecordDetail.masterMerchantCompany}
				</td>
				
				<td>
					${shareAccountRecordDetail.slaverMerchantId}
				</td>
				<td>
					${shareAccountRecordDetail.slaverMerchantLoginName}
				</td>
				<td>
					${shareAccountRecordDetail.slaverMerchantCompany}
				</td>
				
				<td>
					${shareAccountRecordDetail.shareAmount}
				</td>
				
				<td>
					<fmt:formatDate value="${shareAccountRecordDetail.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${shareAccountRecordDetail.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				
				<td>
					${shareAccountRecordDetail.transNo}
				</td>
				<td>
					<fmt:formatDate value="${shareAccountRecordDetail.transCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td <c:if test="${shareAccountRecordDetail.status=='SUCCES'}">
			style="background:${success_background}" 
			</c:if>>
					${fns:getDictLabel(shareAccountRecordDetail.status, 'ShareStatus', '')}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>