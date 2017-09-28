<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>汇付宝(个人钱包)</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//验证搜索条件
		var validateFrom = {
			validate: function(form){
				form.submit();
			}
		}
		
		//搜索
		function onSubmit(){
			validateFrom.validate($("#searchForm"));
		}
		function onClear(){
			$("#searchForm").find("input[type='text']").val("");
			
			$("#transType").find("option").removeAttr("selected");
			$("#transType").find("option:eq(0)").attr("selected","selected");
			$("#s2id_transType").find(".select2-chosen").html("全部");
			$("#s2id_status").find(".select2-chosen").html("全部");
			
			$("#status").find("option").removeAttr("selected");
			$("#status").find("option:eq(0)").attr("selected","selected");
		}
	</script>
	<style>
	.input-medium{
		width:150px;
	}
	</style>
</head>
<body>
	<form:form id="searchForm" modelAttribute="paymentRecord" action="${ctx}/payment/userRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户编码：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>
			<li><label>交易订单号：</label>
				<form:input path="transNo" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>交易类型：</label>
				<form:select id="transType" path="transType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="CHARGE" label="充值"/>
					<form:option value="WZDRAW" label="提现"/>
				</form:select>
			</li>
			<li>
				<label>创建时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${withdrawRecord.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> -
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${withdrawRecord.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</li>
			<li><label>交易状态：</label>
				<form:select id="status" path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<c:forEach items="${fns:getDictList('UserTransStatus')}" var="transStatus">
						<form:option value="${transStatus.value}" label="${transStatus.label}"/>
					</c:forEach>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" onclick="onSubmit()"  type="button" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="clearfix"></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户编码</th>
				<th>用户账号</th>
			    <th>交易订单号</th>
				<th>交易类型</th>
				<th>交易金额</th>
				<th>手续费</th>
				<th>交易状态</th>
				<th>交易时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="paymentRecord">
			<tr>
				<td>
				    ${paymentRecord.merchantId}
				</td>
				<td>
                    ${paymentRecord.merchantLoginName}
                </td>
				<td>
					${paymentRecord.transNo}
				</td>
				<td>
                    ${fns:getDictLabel(paymentRecord.transType, 'TransType', '')}
                </td>
				<td>
					<fmt:formatNumber value="${paymentRecord.payAmount}" pattern="￥0.00" />
				</td>
				<td>
					<fmt:formatNumber value="${paymentRecord.feeAmount}" pattern="￥0.00" />
				</td>
				<td>
				    ${fns:getDictLabel(paymentRecord.status, 'UserTransStatus', '')}
				</td>
				<td>
					<fmt:formatDate value="${paymentRecord.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>