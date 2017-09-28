<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>代收审核详情管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/CheckboxUtil.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			<c:if test="${not empty page.list}">
				var checkboxUtil = new CheckboxUtil("contentTable","parentCheckboxId","collenctIndex");
				$("#btnSubmit").on("click",function(){
					var cbValues = checkboxUtil.tackSelectedSonCheckBoxesValuese();
					$("#checkboxValues").val(cbValues);
					//验证搜索条件
					var validateFrom = {
						validate: function(form){
							if($("#checkboxValues").val()==""){
								$("#messageBox").text("请选择要审核的订单!");
							}else{
								form.submit();
							}
						}
					}
					validateFrom.validate($("#detailPassFrom"));
				});
			</c:if>
		});
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
	<li class="active"><a href="${ctx}/payment/batchCollectionRecord/">审核列表</a></li>
</ul>
<c:if test="${not empty page.list}">
<form:form id="detailPassFrom" modelAttribute="batchCollectionRecord" action="${ctx}/payment/batchCollectionRecordAudit/executiveAudit" method="post" class="breadcrumb form-search">
	<input id="checkboxValues" name="checkboxValues" type="hidden" value=""/>
	<input id="batchId" name="batchId" type="hidden" value="${page.list[0].batchId}"/>
	<input id="merchantId" name="merchantId" type="hidden" value="${page.list[0].merchantId}"/>
	<ul class="ul-form">
		<li class="btns">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="批量审核通过"/>
		</li>
		<li class="clearfix"></li>
		<li style="display: none;" id="checkBoxValues"></li>
	</ul>
	<div id="messageBox" style="font-size: 15px; color: red;"></div>
</form:form>
</c:if>
<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input type="checkbox" id="parentCheckboxId" name="" value="" /> 全选</th>
				<th>订单号</th>
				<th>商户编码</th>
				<th>商户账号</th>
				<th>商户公司名称</th>
				<th>银行户名</th>
				<th>银行卡号</th>
				<th>手机号</th>
				<th>扣款金额</th>
				<th>银行名称</th>
				<th>审核状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="batchCollectionRecordDetail">
			<tr>
				<td>
					<input type="checkbox" name="collenctIndex" value="${batchCollectionRecordDetail.collectId}" onclick=""/>
				</td>
				<td>${batchCollectionRecordDetail.collectId}</td>
				<td>${batchCollectionRecordDetail.merchantId}</td>
				<td>${batchCollectionRecordDetail.accountId}</td>
				<td>${batchCollectionRecordDetail.merchantCompany}</td>
				<td>${batchCollectionRecordDetail.bankcardOwnerName}</td>
				<td>
				${fns:decrypt(batchCollectionRecordDetail.bankcardNo,'1231231adasfsadff')}
				</td>
				<td>${batchCollectionRecordDetail.bankcardOwnerMobile}</td>
				<td><fmt:formatNumber value="${batchCollectionRecordDetail.collectAmount}" pattern="￥#,##0.00" />
				</td>
				<td>${batchCollectionRecordDetail.bankName}</td>
				<td>
					${fns:getDictLabel(batchCollectionRecordDetail.status, 'CollectionRecordDetailStatus', '')}
				</td>
			</tr>
		</c:forEach>
		<c:if test="${empty page.list}">
			没有数据
		</c:if>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<div class="form-actions">
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</body>
</html>