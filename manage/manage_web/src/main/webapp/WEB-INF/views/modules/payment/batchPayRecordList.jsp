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
				var merchantId = $("#merchantId").val();
				var result = validateNum(merchantId);
				if(!result||!checkDate()){
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
			//升降排序
            $("#statisticsDate").find("option").removeAttr("selected");
            $("#statisticsDate").find("option:eq(0)").attr("selected","selected");
            $(".select2-chosen:eq(0)").text($("#statisticsDate option[selected]").text());
			$("#sortOrder").find("option").removeAttr("selected");
			$("#sortOrder").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(1)").text($("#sortOrder option[selected]").text());
		}
		
		//导出
		function exportExcel(){
			if(checkDate()){
				var merchantId = $("#merchantId").val();
				var batchId = $("#batchId").val();
				var merchantBatchNo = $("#merchantBatchNo").val();
				var beginCreateTime = $("#beginCreateTime").val();
				var endCreateTime = $("#endCreateTime").val();
				var host = "${ctx}/payment/batchPayRecord/exportExcel";
				var url = host+"?merchantId="+merchantId+"&batchId="+batchId +
						"&merchantBatchNo="+merchantBatchNo+"&beginCreateTime="+beginCreateTime+"&endCreateTime="+endCreateTime;
				$('<form method="post" action="' + url + '"></form>').appendTo('body').submit().remove();
			}
		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/batchPayRecord/">转账查询列表</a></li>
		<shiro:hasPermission name="payment:batchPayRecord:edit"><li><a href="${ctx}/payment/batchPayRecord/form">批量付款管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="batchPayRecord" action="${ctx}/payment/batchPayRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户编码：</label>
				<form:input path="merchantId" id="merchantId" htmlEscape="false" maxlength="64" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')} else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label>创建时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${batchPayRecord.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${batchPayRecord.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
                <form:select id="statisticsDate" path="statisticsDate" class="input-medium" style="width:100px;" onchange="changeDateSection(this.options[this.options.selectedIndex].text);">
                    <form:option value="" label="今天"/>
                    <c:forEach items="${fns:getDictList('statisticsDate')}" var="item">
                        <form:option value="${item.value}" label="${item.label}"/>
                    </c:forEach>
                </form:select>
            </li>
			<li><label>批次号：</label>
				<form:input id="batchId" path="batchId" htmlEscape="false" maxlength="16" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')} else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label>商户批次号：</label>
				<form:input id="merchantBatchNo" path="merchantBatchNo" htmlEscape="false" maxlength="64" class="input-medium" />
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
			<li class="btns"><input class="btn btn-warning" type="button" onclick="exportExcel()" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>转账总笔数</th>
			<th>成功总笔数</th>
			<th>失败总笔数</th>
			<th>转账总金额</th>
			<th>成功总金额</th>
			<th>失败总金额</th>
			<th>手续费</th>
		</tr>
		</thead>
		<tbody>
			<td id="totalNum">0笔</td>
			<td id="successTotalNum">0笔</td>
			<td id="failTotalNum">0笔</td>
			<td id="totalAmount">￥0.00</td>
			<td id="successTotalAmount">￥0.00</td>
			<td id="failTotalAmount">￥0.00</td>
			<td id="totalFeeAmount">￥0.00</td>
		</tbody>
	</table>
	转账记录
	<table id="contentTable" class="table   table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户编码</th>
				<th>商户公司名称</th>
				<th>商户账号</th>
				<th>批次号</th>
				<th>商户批次号</th>
				<th>转账总金额</th>
				<th>转账总笔数</th>
				<th>处理状态</th>
				<th>银行服务类型</th>
				<th>创建时间</th>
				<%--<shiro:hasPermission name="payment:batchPayRecord:edit"><th>操作</th></shiro:hasPermission>--%>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="batchPayRecord">
			<tr >
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
					${batchPayRecord.merchantBatchNo}
				</td>
				<td>
		
					<fmt:formatNumber value="${batchPayRecord.totalAmount}" pattern="￥#,##0.00" />
				</td>
				<td>
					${batchPayRecord.totalNum}
				</td>
				<td <c:if test="${batchPayRecord.status=='FINISH'}">
			style="background:${success_background}" 
			</c:if>>
					${fns:getDictLabel(batchPayRecord.status, 'BatchPayStatus', '')}
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
				<%--<shiro:hasPermission name="payment:batchPayRecord:edit"><td>--%>
					<a href="${ctx}/payment/batchPayRecordDetail/detailList?batchId=${batchPayRecord.batchId}">
						详情
					</a>
				</td>
				<%--</shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script type="text/javascript">
		$(function(){
			var merchantId = $("#merchantId").val();
			var batchId = $("#batchId").val();
			var merchantBatchNo = $("#merchantBatchNo").val();
			var beginCreateTime = $("#beginCreateTime").val();
			var endCreateTime = $("#endCreateTime").val();
			$.ajax({
				type : "POST",
				url : "${ctx}/payment/batchPayRecord/getStatisticsList",
				data : {
					"merchantId" : merchantId,
					"batchId" : batchId,
					"merchantBatchNo" : merchantBatchNo,
					"beginCreateTime" : beginCreateTime,
					"endCreateTime" : endCreateTime
				},
				dataType : "json",
				success : function(data){
					$("#totalNum").text(data.totalNum);
					$("#successTotalNum").text(data.successTotalNum);
					$("#failTotalNum").text(data.failTotalNum);
					$("#totalAmount").text(data.totalAmount);
					$("#successTotalAmount").text(data.successTotalAmount);
					$("#failTotalAmount").text(data.failTotalAmount);
					$("#totalFeeAmount").text(data.totalFeeAmount);
				}
			});
			
		});
	</script>
</body>
</html>