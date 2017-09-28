<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商户订单管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
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
		<li class="active"><a href="${ctx}/payment/paymentRecord/">商户订单列表</a></li>
		<shiro:hasPermission name="payment:paymentRecord:edit"><li><a href="${ctx}/payment/paymentRecord/form">交易管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="paymentRecord" action="${ctx}/payment/paymentRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户订单号：</label>
				<form:input path="merchantOrderNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>交易订单号：</label>
				<form:input path="paymentId" htmlEscape="false" maxlength="26" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')} else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label>商户公司名称：</label>
				<form:input path="merchantCompany" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商户编码：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="20" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')} else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label>交易状态：</label>
				<form:select id="paymentStatus" path="status" class="input-xlarge">
					<form:option value="" label="全部"/>
					<c:forEach items="${fns:getDictList('PaymentRecordStatus')}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.label}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>交易类型：</label>
				<form:select id="transType" path="transType" class="input-xlarge">
					<form:option value="R0" label="全部"/>
					<c:forEach items="${fns:getDictList('TransType')}" var="wStatus">
						<%--充值,退款,转账，消费   【 暂时隐藏转账 || wStatus.value eq 'BATPAY'】 --%>
						<c:if test="${wStatus.value eq 'CHARGE' || wStatus.value eq 'REFUND' || wStatus.value eq 'PAYMNT'}">
							<form:option value="${wStatus.value}" label="${wStatus.label}"/>
						</c:if>
					</c:forEach>
				</form:select>
			</li>
			<li><label>支付类型：</label>
				<form:select id="payType" path="payType" class="input-xlarge">
					<form:option value="" label="全部"/>
					<c:forEach items="${fns:getDictList('PaymentType')}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.label}"/>
					</c:forEach>
				</form:select>
			</li> 
			<li><label>升降排序：</label>
				<form:select id="sortOrder" path="sortOrder" class="input-medium">
					<c:forEach items="${fns:getDictList('SortStatus')}" var="sStatus">
						<form:option value="${sStatus.value}" label="${sStatus.label}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>创建时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${paymentRecord.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${paymentRecord.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<!-- <li class="btns"><input id="btnExport" class="btn btn-primary" type="button" onclick="onExport()" value="导出"/></li> -->
			<li class="clearfix"></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>

	<table id="contentTable" class="table table-striped   table-bordered table-condensed">
		<thead>
		<thead>
		<tr>
			<th>商户编码</th>
			<th>商户公司名称</th>
			<th>交易订单号</th>
			<th>交易类型</th>
			<th>支付类型</th>
			<th>商户订单号</th>
			<th>订单金额</th>
			<th>交易状态</th>
			<th>创建时间</th>
			<!--  <th>维系员工</th>-->
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="paymentRecord">
			<tr >
				<td>${paymentRecord.merchantId}</td>
				<td>${paymentRecord.merchantCompany}</td>
				<td>
					<a href="${ctx}/payment/paymentRecord/detail?paymentId=${paymentRecord.paymentId}&transNo=${paymentRecord.transNo}&payMentType=${paymentRecord.transType}">
						${paymentRecord.paymentId}
					</a>
				</td>
				<td>
					${fns:getDictLabel(paymentRecord.transType, 'TransType', '')}
				</td>
				<td>
					<c:if test="${paymentRecord.payType=='QUICKP' }">
						快捷
					</c:if>
					<c:if test="${paymentRecord.payType=='GATEWY' }">
						网银
					</c:if>
					<c:if test="${paymentRecord.payType=='BATCOL' }">
						代收
					</c:if>
					<c:if test="${paymentRecord.payType=='REFUND' }">
						退款
					</c:if>
					<c:if test="${paymentRecord.payType=='BATPAY' }">
						代付
					</c:if>
				</td>
				<td>
					<c:if test="${paymentRecord.transType=='PAYMNT' }">
						${paymentRecord.merchantOrderNo}
					</c:if>
				</td>
				<td>
					<fmt:formatNumber value="${paymentRecord.payAmount}" pattern="￥#,##0.00" />
				</td>
				<td<c:if test="${paymentRecord.status=='SUCCES'}">
			style="background:${success_background}" 
			</c:if>>${fns:getDictLabel(paymentRecord.status, 'PaymentRecordStatus', '')}</td>
				<td><fmt:formatDate value="${paymentRecord.payTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<!--  <td>-</td>-->
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>