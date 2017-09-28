<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>差异处理平台管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//全选反选
			$("#checkboxId").bind("click", function(){
				if ($(this).attr("checked")) {
					$(":checkbox").attr("checked", true);
				} else {
					$(":checkbox").attr("checked", false);
				}
			});

			//批量发送
			$("#btnSave").bind("click", function(){
				var paymentIds = "";
				$(":checkbox[name='checkName']:checked").each(function(index, item){
					var tablerow = $(this).parent().parent();
					var paymentId = tablerow.find("[name='paymentId']").val();
					paymentIds = paymentIds + paymentId + ",";
				});
                if(paymentIds == "") {
                    alert("未选中任何单据");
                    return;
                }
                supple(paymentIds);
			});
		});

		function supple(paymentIds) {
			$.ajax({
				type:"post",
				url:"${ctx}/differ/differRecord/batchSupple",
				dataType:'text',
				data : {"paymentIds" : paymentIds},
				success: function(data) {
					alert(data);
					location.reload();
				},
				error:function(data){
					alert(data);
				}

			});
		}

        //查询
        function queryBankResult(paymentId){
            var data = {
                paymentId:paymentId
            };
            $.ajax({
                type:"post",
                url:"${ctx}/payment/exceptionRecord/queryRefundBankResult",
                data:data,
                success: function(data) {
                    var result = data.result;
                    parent.showThree();
                    parent.changeThreeName(result);
                },
                error:function(){
                    parent.showThreeEp();
                    parent.changeThreeNameEp("系统错误，请稍后再试");
                }
            });
        }

		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/differ/differRecord/">差异处理列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="differRecord" action="${ctx}/differ/differRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>支付单号：</label>
				<form:input path="paymentId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>交易订单号：</label>
				<form:input path="transNo" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>商户号：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li><label>商户公司名：</label>
				<form:input path="merchantCompany" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>银行流水号：</label>
				<form:input path="bankSerialNo" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>交易类型：</label>
				<form:select path="transType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('TransType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<%--<li><label>支付类型：</label>
				<form:select path="payType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('payType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>--%>
			<li><label>产品编码：</label>
				<form:select path="productCode" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('ProductCodeType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>订单状态：</label>
				<form:select path="transStatus" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('PaymentRecordStatus')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>订单提交时间：</label>
				<input name="beginTransPayTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${differRecord.beginTransPayTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endTransPayTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${differRecord.endTransPayTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>差异类型：</label>
				<form:select path="differType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="RJCK" label="入金长款"/>
					<form:option value="CJCK" label="出金长款"/>
					<form:option value="JECY" label="金额差异"/>
					<form:option value="DJDS" label="单据丢失"/>
				</form:select>
			</li>
			<li><label>差异处理状态：</label>
				<form:select path="differStatus" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="PENDIN" label="待处理"/>
					<form:option value="FINISH" label="处理完成"/>
				</form:select>
			</li>
			<li><label>操作来源：</label>
				<form:select path="operateSource" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="DIFFER" label="差异处理平台"/>
					<form:option value="COMPLA" label="投诉处理平台"/>
				</form:select>
			</li>
			<li><label>处理时间：</label>
				<input name="beginOperateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${differRecord.beginOperateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endOperateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${differRecord.endOperateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnSave" class="btn btn-warning" type="button" value="批量补单"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th rowspan="2"><input type="checkbox" id="checkboxId"/></th>
				<th>支付单号</th>
				<th>交易订单号</th>
				<th>商户号</th>
				<th>商户登录名</th>
				<th>商户公司名</th>
				<th>交易金额</th>
				<th>订单状态</th>
				<th>交易类型</th>
				<%--<th>支付类型</th>--%>
				<th>产品类型</th>
				<%--<th>订单创建时间</th>--%>
				<th>订单提交时间</th>
				<%--<th>订单成功时间</th>--%>
				<th>通道编码</th>
				<th>银行流水号</th>
				<th>差异类型</th>
				<th>差异处理状态</th>
				<th>操作人</th>
				<th>操作来源</th>
				<th>处理方式</th>
				<th>处理时间</th>
				<shiro:hasPermission name="differ:differRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="differRecord" varStatus="status">
			<tr>
				<%--<td><a href="${ctx}/differ/differRecord/form?id=${differRecord.id}">
					${differRecord.differId}
				</a></td>--%>
				<td><c:if test="${differRecord.differStatus != 'FINISH' && (differRecord.differType == 'RJCK' || differRecord.differType == 'CJCK')}">
                    <input type="checkbox" name="checkName" value="${status.index}" />
                </c:if></td>
				<td><a href="${ctx}/differ/differRecord/form?differId=${differRecord.differId}">
					${differRecord.paymentId}
				</td>
				<td>
					${differRecord.transNo}
				</td>
				<td>
					${differRecord.merchantId}
				</td>
				<td>
					${differRecord.merchantLoginName}
				</td>
				<td>
					${differRecord.merchantCompany}
				</td>
				<td>
					<fmt:formatNumber value="${differRecord.payAmount}" pattern="￥#,##0.00" />
				</td>
				<td>
					${fns:getDictLabel(differRecord.transStatus, 'PaymentRecordStatus', differRecord.transStatus)}
				</td>
				<td>
					${fns:getDictLabel(differRecord.transType, 'TransType', differRecord.transType)}
				</td>
				<%--<td>
					${fns:getDictLabel(differRecord.payType, 'payType', differRecord.payType)}
				</td>--%>
				<td>
					${fns:getDictLabel(differRecord.productCode, 'ProductCodeType', differRecord.productCode)}
				</td>
				<%--<td>
					<fmt:formatDate value="${differRecord.transCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>--%>
				<td>
					<fmt:formatDate value="${differRecord.transPayTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<%--<td>
					<fmt:formatDate value="${differRecord.transSuccessTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>--%>
				<td>
					${differRecord.channelCode}
				</td>
				<td>
					${differRecord.bankSerialNo}
				</td>
				<td>
					${fns:getDictLabel(differRecord.differType, 'DifferConsts', differRecord.differType)}
				</td>
				<td <c:if test="${differRecord.differStatus=='FINISH'}">style="background:${success_background}"</c:if>>
					${fns:getDictLabel(differRecord.differStatus, 'DifferConsts', differRecord.differStatus)}
				</td>
				<td>
					${differRecord.operator}
				</td>
				<td>
					${fns:getDictLabel(differRecord.operateSource, 'DifferConsts', differRecord.operateSource)}
				</td>
				<td>
					${fns:getDictLabel(differRecord.operateType, 'DifferConsts', differRecord.operateType)}
				</td>
				<td>
					<fmt:formatDate value="${differRecord.operateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="differ:differRecord:edit">
					<td><c:if test="${differRecord.differStatus != 'FINISH' && (differRecord.differType == 'RJCK' || differRecord.differType == 'CJDK')}">
                        <input type="hidden" name="paymentId" value="${differRecord.paymentId}" />
    					<%--<a href="${ctx}/differ/differRecord/supple?paymentId=${differRecord.paymentId}">补单</a>--%>
    					<a onclick="supple(${differRecord.paymentId})">补单</a>
					</c:if><a onclick="queryBankResult(${differRecord.paymentId})">查通道</a></td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>