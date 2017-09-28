<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>转账管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script language="JavaScript" src="${ctxStatic}/common/validateNum.js"></script>
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
				var merchantId = $("#merchantId").val();
                var result = validateNum(merchantId);
                if(!result){
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
			//默认排序方式
            $("#statisticsDate").find("option").removeAttr("selected");
            $("#statisticsDate").find("option:eq(0)").attr("selected","selected");
            $(".select2-chosen:eq(0)").text($("#statisticsDate option[selected]").text());
			$("#sortOrder").find("option").removeAttr("selected");
			$("#sortOrder").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(1)").text($("#sortOrder option[selected]").text());
		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/batchPayRecordReasonList/">转账理由列表</a></li>
		<shiro:hasPermission name="payment:batchPayRecord:edit"><li><a href="${ctx}/payment/batchPayRecord/form">转账管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="batchPayRecord" action="${ctx}/payment/batchPayRecordReasonList/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户编码：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="64" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')} else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label>创建时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${batchPayRecord.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${batchPayRecord.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
                <form:select id="statisticsDate" path="statisticsDate" class="input-medium" style="width:100px;" onchange="changeDateSection(this.options[this.options.selectedIndex].text);">
                    <form:option value="" label="全部"/>
                    <c:forEach items="${fns:getDictList('statisticsDate')}" var="item">
                        <form:option value="${item.value}" label="${item.label}"/>
                    </c:forEach>
                </form:select>
            </li>
			<li><label>批次号：</label>
				<form:input path="batchId" htmlEscape="false" maxlength="16" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')} else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label>升降排序：</label>
				<form:select id="sortOrder" path="sortOrder" class="input-medium">
					<c:forEach items="${fns:getDictList('SortStatus')}" var="sStatus">
						<form:option value="${sStatus.value}" label="${sStatus.label}"/>
					</c:forEach>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="clearfix"></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户编码</th>
				<th>商户公司名称</th>
				<th>商户账号</th>
				<th>批次号</th>
				<th>转账总金额</th>
				<th>转账总笔数</th>
				<th>审核状态</th>
				<th>银行服务类型</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="batchPayRecord">
			<tr>
				<td>
					${batchPayRecord.merchantId}
				</td>
				<td>
					${batchPayRecord.merchantCompany}
				</td>
				<td>
					${batchPayRecord.merchantLoginName}
				</td>
				<td>
						${batchPayRecord.batchId}
				</td>
				<td>
					<fmt:formatNumber value="${batchPayRecord.totalAmount}" pattern="￥#,##0.00" />
				</td>
				<td>
						${batchPayRecord.totalNum}
				</td>
				<td>
					<c:if test="${'PROCES' eq batchPayRecord.status}">待审核</c:if>
						<%--${fns:getDictLabel(batchPayRecord.status, 'BatchPayStatus', '')}--%>
							<%--<c:if test="${'PREAUD' eq batchPayRecord.status || 'PROCES' eq batchPayRecord.status}">--%>
								<%--待审核--%>
							<%--</c:if>--%>
							<%--<c:if test="${'AUDREJ' eq batchPayRecord.status}">--%>
								<%--审核拒绝--%>
							<%--</c:if>--%>
				</td>
				<td>
                    <c:set var="intoaccountTime">
                        <fmt:formatDate value="${batchPayRecord.intoaccountTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
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
				<td>
					<fmt:formatDate value="${batchPayRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<a href="${ctx}/payment/batchPayRecordReasonAuditDetail/detailList?batchId=${batchPayRecord.batchId}">
						详情
					</a>
					<a href="${ctx}/payment/batchPayRecordReasonAuditDetail/toAudit?batchId=${batchPayRecord.batchId}">
						审核
					</a>
				</td>
				<%--</shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>