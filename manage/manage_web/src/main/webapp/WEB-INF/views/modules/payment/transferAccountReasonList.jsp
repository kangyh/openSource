<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>转账理由管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
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

		//搜索
		function onSubmit(){
			validateFrom.validate($("#searchForm"));
		}

		//清空
		function onClear(){
			$("#searchForm").find("input[type='text']").val("");
			//默认排序方式
			$("#sortOrder").find("option").removeAttr("selected");
			$("#sortOrder").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen").text($("#sortOrder option[selected]").text());
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/transferAccountReason/">转账理由列表</a></li>
		<shiro:hasPermission name="payment:transferAccountReason:edit"><li><a href="${ctx}/payment/transferAccountReason/form">转账理由添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="transferAccountReason" action="${ctx}/payment/batchPayRecordReasonAudit1/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户编码：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${transferAccountReason.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${transferAccountReason.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>升降排序：</label>
				<form:select id="sortOrder" path="sortOrder" class="input-medium">
					<c:forEach items="${fns:getDictList('SortStatus')}" var="sStatus">
						<form:option value="${sStatus.value}" label="${sStatus.label}"/>
					</c:forEach>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="搜索"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="clearfix"></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>流水号</th>
				<th>商户编码</th>
				<th>商户公司名称</th>
				<th>理由</th>
				<th>是否需要审核</th>
				<th>审核状态</th>
				<th>审核时间</th>
				<th>审批人</th>
				<%--<shiro:hasPermission name="payment:transferAccountReason:edit"><th>操作</th></shiro:hasPermission>--%>
				<%--<th>操作</th>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="transferAccountReason">
			<tr>
				<td>
						${transferAccountReason.transferReasonId}
				</td>
				<td>
						${transferAccountReason.merchantId}
				</td>
				<td>
						${transferAccountReason.merchantCompany}
				</td>
				<td>
						${transferAccountReason.transferReason}
				</td>
				<td>
					<c:if test="${transferAccountReason.auditNeed eq '0'}">
							是
					</c:if>
					<c:if test="${transferAccountReason.auditNeed eq '1'}">
						否
					</c:if>
				</td>
				<td>
					<c:if test="${transferAccountReason.status eq 'SUCCES'}">
						审核通过
					</c:if>
				</td>
				<td>
					<c:if test="${not empty transferAccountReason.updateTime}">
						<fmt:formatDate value="${transferAccountReason.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
					</c:if>
				</td>
				<td>${transferAccountReason.autiter}</td>
				<%--<td>
					<a href="${ctx}/payment/batchPayRecordReasonAudit/detail?id=${transferAccountReason.transferReasonId}">查看</a>
					<c:if test="${transferAccountReason.status == 'AUDING' && transferAccountReason.auditNeed != '1'}">
						<a href="${ctx}/payment/batchPayRecordReasonAudit/audrej?id=${transferAccountReason.transferReasonId}">拒绝</a>
						<a href="${ctx}/payment/batchPayRecordReasonAudit/adopt?id=${transferAccountReason.transferReasonId}">通过</a>
					</c:if>
				</td>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>