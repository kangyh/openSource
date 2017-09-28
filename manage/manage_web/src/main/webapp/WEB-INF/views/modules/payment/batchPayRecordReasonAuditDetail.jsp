<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>转账管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/CheckboxUtil.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			<c:if test="${not empty page.list}">
			var checkboxUtil = new CheckboxUtil("contentTable","parentCheckboxId","collenctIndex");
			$("#btnPass").on("click",function(){
				var cbValues = checkboxUtil.tackSelectedSonCheckBoxesValuese();
				$("#checkboxValues").val(cbValues);
				$("#pass").val("yes");
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
			$("#btnNoPass").on("click",function(){
				var cbValues = checkboxUtil.tackSelectedSonCheckBoxesValuese();
				$("#checkboxValues").val(cbValues);
				$("#pass").val("no");
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
		//搜索
		function onSubmit(){
			validateFrom.validate($("#detailPassFrom"));
		}

		//清空
		function onClear(){
			$("#detailPassFrom").find("input[type='text']").val("");
		}

	</script>
</head>
<body>
<c:if test="${not empty page.list}">
	<form:form id="detailPassFrom" modelAttribute="batchPayRecordReasonAuditDetail" action="${ctx}/payment/batchPayRecordReasonAuditDetail/executiveAudit" method="post" class="breadcrumb form-search">
		<input id="checkboxValues" name="checkboxValues" type="hidden" value=""/>
		<input id="batchId" name="batchId" type="hidden" value="${page.list[0].batchId}"/>
		<input id="pass" name="pass" type="hidden" value=""/>
		<input id="merchantId" name="merchantId" type="hidden" value="${page.list[0].merchantId}"/>
		<ul class="ul-form">
			<li class="btns">
				<input id="btnPass" class="btn btn-primary"  type="button" value="批量审核通过"/>
			</li>
			<li class="btns">
				<input id="btnNoPass" class="btn btn-primary"  type="button" value="批量审核不通过"/>
			</li>
			<li class="clearfix"></li>
			<li style="display: none;" id="checkBoxValues"></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
</c:if>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input type="checkbox" id="parentCheckboxId" name="" value="" /> 全选</th>
				<th>交易订单号</th>
				<th>商户编码</th>
				<th>商户公司名称</th>
				<th>理由</th>
				<%--<th>是否需要审核</th>--%>
				<th>审核状态</th>
				<th>银行服务类型</th>
				<!-- <th>审核人</th> -->
				</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="batchPayRecordAuditDetail">
			<tr>
				<td>
					<c:if test="${'PREPAY' ne batchPayRecordAuditDetail.status and 'AUDREJ' ne batchPayRecordAuditDetail.status}">
						<input type="checkbox" name="collenctIndex" value="${batchPayRecordAuditDetail.batchPayId}"/>
					</c:if>
				</td>
				<td>${batchPayRecordAuditDetail.batchPayId}</td>
				<td>${batchPayRecordAuditDetail.merchantId}</td>
				<td>${batchPayRecordAuditDetail.merchantCompany}</td>
				<td>${batchPayRecordAuditDetail.payReason}</td>
				<%--<td>--%>
					<%--<c:if test="${batchPayRecordAuditDetail.auditFlag eq '0'}">是</c:if>--%>
					<%--<c:if test="${batchPayRecordAuditDetail.auditFlag eq '1'}">否</c:if>--%>
				<%--</td>--%>
				<td>${fns:getDictLabel(batchPayRecordAuditDetail.status, 'BatchPayRecordDetailStatus', '')}</td>
				<td>
                    <c:set var="intoaccountTime">
                        <fmt:formatDate value="${batchPayRecordAuditDetail.intoaccountTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </c:set>
                    <c:choose>
                    <c:when test="${fn:endsWith(intoaccountTime,'23:59:59')}">
                        T+1
                    </c:when>
                    <c:otherwise>
                        T+0
                    </c:otherwise>
                    </c:choose>
                </td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<div class="form-actions">
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</body>
</html>