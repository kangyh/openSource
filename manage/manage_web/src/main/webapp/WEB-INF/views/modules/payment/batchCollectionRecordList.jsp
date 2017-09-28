<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单据管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script language="JavaScript" src="${ctxStatic}/common/AjaxExportExcel.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			new AjaxExportExcel('frameId',"btnExport","a,b,c","${ctx}/payment/batchCollectionRecord/exportExcel");
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
			validateFrom.validate($("#wrOrderSearchForm"));
		}

		//清空
		function onClear(){
			$("#wrOrderSearchForm").find("input[type='text']").val("");
			$("#wrOrderSearchForm").find("option").removeAttr("selected");
			$("#wrOrderSearchForm").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen").text($("#wrOrderSearchForm option[selected]").text());
		}

		//导出
		function onExport(){

		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/batchCollectionRecord/">单据管理列表</a></li>
		<shiro:hasPermission name="payment:batchCollectionRecord:edit"><li><a href="${ctx}/payment/batchCollectionRecord/form">委托收款管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="wrOrderSearchForm" modelAttribute="batchCollectionRecord" action="${ctx}/payment/batchCollectionRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>申请时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${batchCollectionRecord.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${batchCollectionRecord.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>批次号：</label>
				<form:input  path="batchId" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>商户名称：</label>
				<form:input path="merchantLoginName" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>商户编号：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>商户账号：</label>
				<form:input path="accountName" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<c:forEach items="${fns:getDictList('CollectionRecordStatus')}" var="bStatus">
						<form:option value="${bStatus.value}" label="${bStatus.label}"/>
					</c:forEach>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" onclick="" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>批次号</th>
				<th>商户编码</th>
				<th>商户账号</th>
				<th>商户公司名称</th>
				<th>提交总金额</th>
				<th>成功总金额</th>
				<th>提交总笔数</th>
				<th>成功总笔数</th>
				<th>手续费金额</th>
				<th>处理状态</th>
				<th>申请时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="batchCollectionRecord">
			<tr>
				<td>
					${batchCollectionRecord.batchId}
				</td>
				<td>
					${batchCollectionRecord.accountId}
				</td>
				<td>
					${batchCollectionRecord.merchantLoginName}
				</td>
				<td>
					${batchCollectionRecord.merchantCompany}
				</td>
				<td>
					<fmt:formatNumber value="${batchCollectionRecord.totalAmount}" pattern="￥#,##0.00" />
				</td>
				<td>
					${batchCollectionRecord.successTotalAmount}
				</td>
				<td>
					${batchCollectionRecord.totalNum}
				</td>
				<td>
					${batchCollectionRecord.successTotalNum}
				</td>
				<td>
					<%--<c:choose>--%>
						<%--<c:when test="${not empty batchCollectionRecord.feeRatio}">--%>
							<%--<fmt:formatNumber value="${batchCollectionRecord.feeRatio}" pattern="￥.00" />--%>
						<%--</c:when>--%>
						<%--<c:otherwise>--%>
							<%-----%>
						<%--</c:otherwise>--%>
					<%--</c:choose>--%>
				</td>
				<td>
					${fns:getDictLabel(batchCollectionRecord.status, 'CollectionRecordStatus', '')}
				</td>
				<td>
					<fmt:formatDate value="${batchCollectionRecord.createDate}" pattern="yyyy-MM--dd HH:mm:ss"/>
				</td>
				<td>
					<a href="${ctx}/payment/batchCollectionRecord/form?id=${batchCollectionRecord.batchId}">详情</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>