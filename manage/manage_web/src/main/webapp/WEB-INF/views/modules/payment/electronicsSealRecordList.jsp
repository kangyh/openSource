<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>电子签章订单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
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
            //默认扣款状态
            $("#status").find("option").removeAttr("selected");
            $("#status").find("option:eq(0)").attr("selected","selected");
            $(".select2-chosen").text($("#status option[selected]").text());
        }

        //导出
        function onExport(){
            if(checkDate()){
                var electronicsSealId = $("#electronicsSealId").val();
                var merchantId = $("#merchantId").val();
                var merchantCompany = $("#merchantCompany").val();
                var status = $("#status").val();
                var beginCreateTime = $("#beginCreateTime").val();
                var endCreateTime = $("#endCreateTime").val();
                var groupType = $("input[name='groupType']:checked").val();
                var host = "${ctx}/payment/electronicsSealRecord/exportExcel";
                var url = host+"?electronicsSealId="+electronicsSealId+"&merchantId="+merchantId+"&merchantCompany="+merchantCompany + "&status="+status +
                    "&beginCreateTime="+beginCreateTime+"&endCreateTime="+endCreateTime+"&groupType="+groupType;
                $('<form method="post" action="' + url + '"></form>').appendTo('body').submit().remove();
            }
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/electronicsSealRecord/">电子签章订单列表</a></li>
		<%--<shiro:hasPermission name="payment:electronicsSealRecord:edit"><li><a href="${ctx}/payment/electronicsSealRecord/form">电子签章订单添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="electronicsSealRecord" action="${ctx}/payment/electronicsSealRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>签章序列号：</label>
				<form:input path="electronicsSealId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>商户id：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>商户公司：</label>
				<form:input path="merchantCompany" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select id="status" path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="PENDNG" label="待处理"/>
					<form:option value="SUCCES" label="成功"/>
					<form:option value="FAILED" label="失败"/>
					<form:option value="ERRORS" label="异常"/>
				</form:select>
			</li>
			<%--<li><label>创建时间：</label>--%>
				<%--<input name="createTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"--%>
					<%--value="<fmt:formatDate value="${electronicsSealRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"--%>
					<%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>--%>
			<%--</li>--%>
			<li><label>签约时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${electronicsSealRecord.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${electronicsSealRecord.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<form:select id="statisticsDate" path="statisticsDate" class="input-medium" style="width:100px;" onchange="changeDateSection(this.options[this.options.selectedIndex].text);">
					<form:option value="" label="全部"/>
					<c:forEach items="${fns:getDictList('statisticsDate')}" var="item">
						<form:option value="${item.value}" label="${item.label}"/>
					</c:forEach>
				</form:select>
				<input name="groupType"  type="radio" value="1" style="margin-left: 10px;"  <c:if test="${groupType == 1}">checked="checked"</c:if> >按创建时间
				<input name="groupType" type="radio" value="2" style="margin-right: 10px;" <c:if test="${groupType == 2}">checked="checked"</c:if>>按更新时间
			</li>
			<%--<li><label>备注：</label>--%>
				<%--<form:input path="remark" htmlEscape="false" maxlength="200" class="input-medium"/>--%>
			<%--</li>--%>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-warning" type="button" onclick="onExport()" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table class="table table-striped   table-bordered table-condensed">
		<thead>
		<tr>
			<th>电子签章订单总金额</th>
			<th>签约中总金额</th>
			<th>签约失败总金额</th>
			<th>签约成功总金额</th>
		</tr>
		</thead>
		<tbody>
		<tr>
			<td id="electronicsSealTotalAmount">￥0.00</td>
			<td id="payingTotalAmount">￥0.00</td>
			<td id="failedTotalAmount">￥0.00</td>
			<td id="successTotalAmount">￥0.00</td>
		</tr>
		</tbody>
	</table>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>编号</th>
				<th>签章序列号</th>
				<th>商户编码</th>
				<th>商户公司</th>
				<th>手续费金额</th>
				<th>平台来源</th>
				<%--<th>签约产品</th>--%>
				<th>通道编码</th>
				<th>签约类型</th>
				<th>状态</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<th>备注</th>
				<%--<shiro:hasPermission name="payment:electronicsSealRecord:edit"><th>操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="electronicsSealRecord">
			<c:set var="STATU_BACKGROUND_COLOR" value="#3CADF1;"></c:set>
			<c:if test="${'FAILED' eq electronicsSealRecord.status}">
				<c:set var="STATU_BACKGROUND_COLOR" value="#EE0000;"></c:set>
			</c:if>
			<c:if test="${'SUCCES' eq electronicsSealRecord.status}">
				<c:set var="STATU_BACKGROUND_COLOR" value="#6CF683;"></c:set>
			</c:if>
			<tr>
				<td><a href="${ctx}/payment/electronicsSealRecord/form?id=${electronicsSealRecord.id}">
					${electronicsSealRecord.id}
				</a></td>
				<td>
					${electronicsSealRecord.electronicsSealId}
				</td>
				<td>
					${electronicsSealRecord.merchantId}
				</td>
				<td>
					${electronicsSealRecord.merchantCompany}
				</td>
				<td>
					<fmt:formatNumber value="${electronicsSealRecord.feeAmount}" pattern="￥0.00" />
				</td>
				<td>
					${electronicsSealRecord.sysResource}
				</td>
				<%--<td>--%>
					<%--${electronicsSealRecord.sealProduct}--%>
				<%--</td>--%>
				<td>
					${electronicsSealRecord.channelCode}
				</td>
				<td>
					${electronicsSealRecord.sealType}
				</td>
				<td style="background:${STATU_BACKGROUND_COLOR}">
					<c:if test="${'SUCCES' eq electronicsSealRecord.status}">成功</c:if>
					<c:if test="${'FAILED' eq electronicsSealRecord.status}">失败</c:if>
					<c:if test="${'PAYING' eq electronicsSealRecord.status}">签约中</c:if>
				</td>
				<td>
					<fmt:formatDate value="${electronicsSealRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${electronicsSealRecord.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${electronicsSealRecord.remark}
				</td>
				<%--<shiro:hasPermission name="payment:electronicsSealRecord:edit"><td>--%>
    				<%--<a href="${ctx}/payment/electronicsSealRecord/form?id=${electronicsSealRecord.id}">修改</a>--%>
					<%--<a href="${ctx}/payment/electronicsSealRecord/delete?id=${electronicsSealRecord.id}" onclick="return confirmx('确认要删除该电子签章订单吗？', this.href)">删除</a>--%>
				<%--</td></shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script type="text/javascript">
        $(function(){
            var electronicsSealId = $("#electronicsSealId").val();
            var merchantId = $("#merchantId").val();
            var merchantCompany = $("#merchantCompany").val();
            var status = $("#status").val();
            var beginCreateTime = $("#beginCreateTime").val();
            var endCreateTime = $("#endCreateTime").val();
            var groupType = $("input[name='groupType']:checked").val();
            $.ajax({
                type : "POST",
                url : "${ctx}/payment/electronicsSealRecord/getStatisticsList",
                data : {
                    "electronicsSealId" : electronicsSealId,
                    "merchantId" : merchantId,
                    "merchantCompany" : merchantCompany,
                    "status" : status,
                    "beginCreateTime" : beginCreateTime,
                    "endCreateTime" : endCreateTime,
                    "groupType" : groupType
                },
                dataType : "json",
                success : function(data){
                    $("#electronicsSealTotalAmount").text(data.electronicsSealTotalAmount);
                    $("#payingTotalAmount").text(data.payingTotalAmount);
                    $("#failedTotalAmount").text(data.failedTotalAmount);
                    $("#successTotalAmount").text(data.successTotalAmount);
                }
            });

        });
	</script>
</body>
</html>