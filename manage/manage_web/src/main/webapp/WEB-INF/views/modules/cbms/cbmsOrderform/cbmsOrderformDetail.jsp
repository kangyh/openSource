<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>通关申报明细管理</title>
	<meta name="decorator" content="default"/>
	<style>

		.scs{

			width:200px;font-size:12px;

			border:0px solid #ddd;

			overflow:hidden;

			text-overflow:ellipsis;

			white-space:nowrap;}

	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			var width=$("#contentTable").width();
				$("#ulTableId").attr("style","width:"+width+"px;");
				$("#searchForm").attr("style","width:"+(width-27)+"px;");
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        //清空
        function onClear(){
            $("#searchForm").find("input[type='text']").val("");
            //默认状态
            $("#customStatus").find("option").removeAttr("selected");
            $("#customStatus").find("option:eq(0)").attr("selected", "selected");
            $(".select2-chosen:eq(2)").text($("#customStatus option[selected]").text());
            $("#customCode").find("option").removeAttr("selected");
            $("#customCode").find("option:eq(0)").attr("selected", "selected");
            $(".select2-chosen:eq(1)").text($("#customCode option[selected]").text());
            $("#declareType").find("option").removeAttr("selected");
            $("#declareType").find("option:eq(0)").attr("selected", "selected");
            $(".select2-chosen:eq(0)").text($("#declareType option[selected]").text());

        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs" id="ulTableId">
		<li class="active"><a href="${ctx}/cbms/cbmsCustomorderSum/">通关申报订单列表</a></li>
		<li class="active"><a href="${ctx}/cbms/cbmsOrderform/detail">申报明细查看</a></li>
		<%--<shiro:hasPermission name="cbms:cbmsOrderform:edit"><li><a href="${ctx}/cbms/cbmsOrderform/form">通关申报明细添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="cbmsOrderform" action="${ctx}/cbms/cbmsOrderform/detail" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form" style="width:1739px;">
			<li><label>导入批次号：</label>
				<form:input path="importBatchNumber" htmlEscape="false" maxlength="64" class="input-medium" onKeyUp="value=value.replace(/[^\d\a-z\A-Z]/g,'')"/>
			</li>
			<li><label>申报批次号：</label>
				<form:input path="sentCustomsNumber" htmlEscape="false" maxlength="64" class="input-medium" onKeyUp="value=value.replace(/[^\d\a-z\A-Z]/g,'')"/>
			</li>
			<li><label>商户编码：</label>
				<form:input path="merchantNo" htmlEscape="false" maxlength="64" class="input-medium" onkeyup="value=value.replace(/[^\d]/g,'') "/>
			</li>
			<li><label>申报海关：</label>
				<form:select path="customCode" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options id="customCode" items="${customsettinglist}" itemLabel="chinaName" itemValue="customNo" htmlEscape="false"/>
				</form:select>
			</li>
            <li><label>申报时间：</label>
                <input id="beginDeclarationTime" name="beginDeclarationTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                       value="<fmt:formatDate value="${cbmsOrderform.beginDeclarationTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,maxDate:'#F{$dp.$D(\'endDeclarationTime\')}'});"/> -
                <input id="endDeclarationTime" name="endDeclarationTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                       value="<fmt:formatDate value="${cbmsOrderform.endDeclarationTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,minDate:'#F{$dp.$D(\'beginDeclarationTime\')}'});"/>
            </li>
			<li><label>海关报送状态：</label>
				<form:select path="customStatus" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options id="customStatus" items="${fns:getEnumList('orderFormStatus')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>申报方式：</label>
				<form:select path="declareType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="1" label="文件上传"/>
					<form:option value="2" label="API接口"/>
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
				<th>订单号</th>
				<th>电商订单号</th>
				<th>交易订单号</th>
				<th>海关名称</th>
				<th>商户编码</th>
				<th>导入批次号</th>
				<th>申报批次号</th>
				<th>手续费</th>
				<th>申报金额</th>
                <th>业务状态</th>
                <th>交易状态</th>
				<th>创建时间</th>
				<th>申报时间</th>
				<th>申报方式</th>
				<th>接口返回描述</th>
				<th>实名认证结果</th>
				<th>报送类型</th>
				<th>模式代码</th>
				<th>主管海关代码</th>
				<th>检验检疫机构代码</th>
				<th>标的物名称</th>
				<th>数量</th>
				<th>单位</th>
				<th>原始支付企业名称</th>
				<th>原始支付流水号</th>
				<th>支付交易状态</th>
				<th>支付用途</th>
				<th>支付税款</th>
				<th>支付运费</th>
				<th>支付货款</th>
				<th>支付人姓名</th>
				<th>证件类型</th>
				<th>证件号码</th>
				<th>支付时间</th>
				<th>支付币种</th>
				<th>支付人电话</th>
				<th>电商企业编号</th>
				<th>电商企业名称</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cbmsOrderform">
			<tr>
				<td>
					<c:out value="${cbmsOrderform.orderId}"/>
				</td>
				<td>
					<c:out value="${cbmsOrderform.orderFormNo}"/>
				</td>
				<td>
						<c:out value="${cbmsOrderform.transId}"/>
				</td>
				<td>
					<c:out value="${cbmsOrderform.customCode}"/>
				</td>
				<td>
					<c:out value="${cbmsOrderform.merchantNo}"/>
				</td>
				<td>
					<c:out value="${cbmsOrderform.importBatchNumber}"/>
				</td>
				<td>
					<c:out value="${cbmsOrderform.sentCustomsNumber}"/>
				</td>
				<td>
					<c:out value="${cbmsOrderform.chargeAmount}"/>
				</td>
				<td>
					<c:out value="${cbmsOrderform.rmbPayAmount}"/>
				</td>
				<td>
					<c:out value="${cbmsOrderform.customStatus}"/>
				</td>
				<td>
					<c:out value="${cbmsOrderform.transStatus}"/>
				</td>
				<td nowrap="nowrap">
					<fmt:formatDate value="${cbmsOrderform.createdTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td nowrap="nowrap">
					<fmt:formatDate value="${cbmsOrderform.declarationTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<c:out value="${cbmsOrderform.declareType}"/>
				</td>
				<td><c:out value="${cbmsOrderform.causesFailure}"/></td>
				<td><c:out value="${cbmsOrderform.authMsg}"/></td>
				<td><c:out value="${cbmsOrderform.transCode}"/></td>
				<td><c:out value="${cbmsOrderform.modeCode}"/></td>
				<td><c:out value="${cbmsOrderform.mainCustomCode}"/></td>
				<td><c:out value="${cbmsOrderform.inspectionQuarantineCode}"/></td>
				<td>
					<div  class="scs" title="<c:out value="${cbmsOrderform.commodityName}"/>"><c:out value="${cbmsOrderform.commodityName}"/></div></td>
				<td><c:out value="${cbmsOrderform.commodityCount}"/></td>
				<td><c:out value="${cbmsOrderform.commodityUnit}"/></td>
				<td><c:out value="${cbmsOrderform.orgPayEnterpriseName}"/></td>
				<td><c:out value="${cbmsOrderform.orgPayTransno}"/></td>
				<td><c:out value="${cbmsOrderform.payStatus}"/></td>
				<td><c:out value="${cbmsOrderform.payPurpose}"/></td>
				<td><c:out value="${cbmsOrderform.payTaxes}"/></td>
				<td><c:out value="${cbmsOrderform.freight}"/></td>
				<td><c:out value="${cbmsOrderform.rmbPrices}"/></td>
				<td><c:out value="${cbmsOrderform.payName}"/></td>
				<td><c:out value="${cbmsOrderform.payErcertificateType}"/></td>
				<td><c:out value="${cbmsOrderform.payErcertificateNo}"/></td>
				<td nowrap="nowrap"><fmt:formatDate value="${cbmsOrderform.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><c:out value="${cbmsOrderform.currencyId}"/></td>
				<td><c:out value="${cbmsOrderform.payerPhone}"/></td>
				<td><c:out value="${cbmsOrderform.busEnterpriseNo}"/></td>
				<td><c:out value="${cbmsOrderform.busEnterpriseName}"/></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>