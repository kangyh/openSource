<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分润查看管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/validateNum.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		var validateFrom = {
			validate: function(form){
				var merchantOrderNo = $("#merchantOrderNo").val();
				var merchantOrderNoResult = validateNum(merchantOrderNo,"原商户订单号请输入数字!");
				var merchantLoginName = $("#merchantLoginName").val();
				var merchantLoginNameResult = validatePreventInject(merchantLoginName,"出款商户账号输入不合法!");
				var merchantCompany = $("#merchantCompany").val();
				var merchantCompanyResult = validatePreventInject(merchantCompany,"出款商户公司输入不合法!");
				if(!merchantOrderNoResult || !merchantLoginNameResult || !merchantCompanyResult){
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
		<li class="active"><a href="${ctx}/share/shareAccountRecord/">分润查看列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="shareAccountRecord" action="${ctx}/share/shareAccountRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>分润号：</label>
				<form:input path="shareId" htmlEscape="false" maxlength="20" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')} else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label>原交易订单号：</label>
				<form:input path="transNo" htmlEscape="false" maxlength="50" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')} else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label>原单创建时间：</label>
					<input id="beginCreateTime" name="beginTransCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${shareAccountRecord.beginTransCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input id="endCreateTime" name="endTransCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${shareAccountRecord.endTransCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<c:forEach items="${fns:getDictList('ShareStatus')}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.label}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>分润发起时间：</label>
					<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${shareAccountRecord.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${shareAccountRecord.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>原商户订单号：</label>
				<form:input path="merchantOrderNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>出款商户账号：</label>
				<form:input path="merchantLoginName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>出款商户公司：</label>
				<form:input path="merchantCompany" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped  table-bordered table-condensed">
		<thead>
			<tr>
				<th>分润号</th>
				<th>商户编码</th>
				<th>商户账号</th>
				<th>商户公司名称</th>
				<th>原交易订单号</th>
				<th>原商户订单号</th>		
				<th>原订单创建时间</th>
				<th>原订单总金额</th>				
				<th>原订单完成金额</th>				
				<th>分润总金额</th>
				<th>手续费</th>
				<th>收款商户总数</th>
				<th>分润发起时间</th>
				<th>最后修改时间</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="shareAccountRecord">
			<tr >
				<td><a href="${ctx}/share/shareAccountRecord/detaillist?shareId=${shareAccountRecord.shareId}">
					${shareAccountRecord.shareId}
				</a></td>
				<td>
					${shareAccountRecord.merchantId}
				</td>
				<td>
					${shareAccountRecord.merchantLoginName}
				</td>
				<td>
					${shareAccountRecord.merchantCompany}
				</td>
				<td>
					${shareAccountRecord.transNo}
				</td>
				<td>
					${shareAccountRecord.merchantOrderNo}
				</td>
				<td>
					<fmt:formatDate value="${shareAccountRecord.transCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${shareAccountRecord.transPayAmount}
				</td>
				<td>
					${shareAccountRecord.transSuccessAmount}
				</td>
				
				<td>
					${shareAccountRecord.shareAmount}
				</td>
				<td>
					${shareAccountRecord.feeAmount}
				</td>
				<td>
					${shareAccountRecord.shareMerchantNum}
				</td>
				<td>
					<fmt:formatDate value="${shareAccountRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${shareAccountRecord.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				
				
				
				<td  <c:if test="${shareAccountRecord.status=='SUCCES'}">
			style="background:${success_background}" 
			</c:if>>
					${fns:getDictLabel(shareAccountRecord.status, 'ShareStatus', '')}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>