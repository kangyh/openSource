<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>财务报表统计（退款）</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
            var groupBy = "${where.groupType}";
            if(groupBy == "merchant"){
                $("#merchantTable").show();
                $("#channelTable").hide();
            } else if(groupBy == "channel"){
                $("#merchantTable").hide();
                $("#channelTable").show();
            }

            if($("#beginCreateTime").val() == "" && $("#endCreateTime").val() == "") {
            	pickTime();
			}

		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

		function formatDatetime(srcDate) {
			var year = srcDate.getFullYear();
			var month = srcDate.getMonth() + 1;
			var date = srcDate.getDate();
			var hour =  srcDate.getHours();
			var minute =  srcDate.getMinutes();
			var second =  srcDate.getSeconds();
			return year + "-"
					+ (month < 10 ? '0' + month : month) + "-"
					+  (date < 10 ? '0' + date : date) + " "
					+ (hour < 10 ? '0' + hour : hour) + ":"
					+ (minute < 10 ? '0' + minute : minute) + ":"
					+ (second < 10 ? '0' + second : second);
		}

        function pickTime() {
            var timeFlag = $("#timePicker").find("option:selected").val();
			var beginTime = new Date();
			beginTime.setHours(0);
			beginTime.setMinutes(0);
			beginTime.setSeconds(0);
			var endTime = new Date();
			endTime.setHours(23);
			endTime.setMinutes(59);
			endTime.setSeconds(59);
			var oneDayMillis = 1000 * 60 * 60 * 24;
			if(timeFlag == "yesterday"){
				beginTime.setDate(beginTime.getDate() - 1);
				endTime.setDate(endTime.getDate() - 1);
			} else if(timeFlag == "today") {

			} else if(timeFlag == "thisweek") {
				beginTime.setDate(beginTime.getDate() - beginTime.getDay() + 1);
				endTime.setDate(endTime.getDate() + 7 - beginTime.getDay());
			} else if(timeFlag == "lastweek") {
				beginTime.setDate(beginTime.getDate() - beginTime.getDay() - 6);
				endTime.setDate(endTime.getDate() - beginTime.getDay());
			} else if(timeFlag == "thismonth") {
				beginTime.setDate(1);
				endTime.setMonth(endTime.getMonth() + 1);
				endTime.setDate(1);
				endTime.setTime(endTime.getTime() - oneDayMillis);
			} else if(timeFlag == "lastmonth") {
				beginTime.setMonth(beginTime.getMonth() - 1);
				beginTime.setDate(1);
				endTime.setDate(1);
				endTime.setTime(endTime.getTime() - oneDayMillis);
			} else if(timeFlag == "thisyear") {
			    beginTime.setMonth(0);
                beginTime.setDate(1);
                endTime.setMonth(11);
                endTime.setDate(31);
            } else if(timeFlag == "lastyear") {
                beginTime.setFullYear(beginTime.getFullYear() - 1);
                beginTime.setMonth(0);
                beginTime.setDate(1);
                endTime.setFullYear(endTime.getFullYear() - 1);
                endTime.setMonth(11);
                endTime.setDate(31);
            }

			$("#beginCreateTime").val(formatDatetime(beginTime));
			$("#endCreateTime").val(formatDatetime(endTime));

        }

        function toExcel(){
            $("#searchForm").attr("action","${ctx}/payment/refundReport/export").submit();
		}
		function toSearch() {
            $("#searchForm").attr("action","${ctx}/payment/refundReport/").submit();
        }

		//清空
		function onClear(){
			$("#searchForm").find("input[type='text']").val("");
// 			//默认通道合作方
//			$("#channelPartner").find("option").removeAttr("selected");
//			$("#channelPartner").find("option:eq(0)").attr("selected","selected");
//			$(".select2-chosen:eq(0)").text($("#channelPartner option[selected]").text());
//			//默认排序方式
//			$("#channelType").find("option").removeAttr("selected");
//			$("#channelType").find("option:eq(0)").attr("selected","selected");
//			$(".select2-chosen:eq(1)").text($("#channelType option[selected]").text());
//			//所有银行
//			$("#bankCode").find("option").removeAttr("selected");
//			$("#bankCode").find("option:eq(0)").attr("selected","selected");
//			$(".select2-chosen:eq(2)").text($("#bankCode option[selected]").text());
//
//			$("#bankType").find("option").removeAttr("selected");
//			$("#bankType").find("option:eq(0)").attr("selected","selected");
//			$(".select2-chosen:eq(3)").text($("#bankType option[selected]").text());
			
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/refundReport/">财务报表统计（退款）</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="refundReport" action="${ctx}/payment/refundReport/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户编码：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="64" class="input-medium" value="${where.merchantId}"/>
			</li>
			<li><label>商户名称：</label>
				<form:input path="merchantCompany" htmlEscape="false" maxlength="64" class="input-medium" value="${where.merchantCompany}"/>
			</li>
			<li><label>账号类型：</label>
				<form:select id="merchantSource" path="merchantSource" name="merchantSource" class="input-medium">
					<form:option value="" label="全部" />
					<form:option value="inside" label="内部" />
					<form:option value="outsid" label="外部" />
				</form:select>
			</li>
			<li><label>通道合作方：</label>
				<form:select id="channelPartner" path="channelPartner" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getEnumList('channelPartner')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
				</form:select>
			</li>
			<li><label>银行名称：</label>
				<form:select id="bankId" path="bankId" name="bankId" class="input-medium" >
		    	    <form:option value="" label="全部"/>
				    <form:options items="${fns:getBankInfoList()}" itemLabel="bankName" itemValue="bankNo" htmlEscape="false" />
			    </form:select>
			</li>
			<li><label>原支付类型：</label>
				<form:select path="payType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('payType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>退款提交时间：</label>
                <input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                       value="${where.beginCreateTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
                <input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                       value="${where.endCreateTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li>
                <form:select id="timePicker" path="timePicker" name="timePicker" class="input-medium" onchange="pickTime()">
                    <form:option value="yesterday" label="昨天"/>
                    <form:option value="today" label="今天"/>
                    <form:option value="thisweek" label="本周"/>
                    <form:option value="lastweek" label="上周"/>
                    <form:option value="thismonth" label="本月"/>
                    <form:option value="lastmonth" label="上月"/>
                    <form:option value="thisyear" label="今年"/>
                    <form:option value="lastyear" label="去年"/>
                </form:select>
			</li>
			<%--<li><label>升降排序：</label>
				<form:select id="orderBy" path="orderBy" name="orderBy" class="input-medium">
					<form:option value="asc" label="升序" />
					<form:option value="desc" label="降序" />
				</form:select>
			</li>
			<li><label>排序类型：</label>
				<form:select id="sortBy" path="sortBy" name="sortBy" class="input-medium">
					<form:option value="refundCounts" label="退款总笔数" />
					<form:option value="refundAmount" label="退款提交总金额" />
					<form:option value="oriFeeAmount" label="退款原订单总支付手续费" />
					<form:option value="feeBackAmount" label="退支付手续费" />
				</form:select>
			</li>--%>
            <li>
                <label>分组类型：</label>
                <form:select id="groupType" path="groupType" name="groupType" class="input-medium">
                    <form:option value="merchant" label="按商户" />
                    <form:option value="channel" label="按通道" />
                </form:select>
            </li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="toSearch()" value="查询"/>
			<input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/>
			<input class="btn btn-primary" type="button" onclick="toExcel()" value="导出Excel"/>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="merchantTable" class="table table-striped table-bordered table-condensed" style="display: none">
		<thead>
			<tr>
				<th>商户ID</th>
				<th>商户账号</th>
				<th>商户名称</th>
				<th>退款总笔数（笔）</th>
				<%--<th>退款原订单总金额（元）</th>--%>
				<th>退款提交总金额（元）</th>
				<%--<th>退款原订单总支付手续费（元）</th>--%>
				<th>退支付手续费总金额（元）</th>
			</tr>
		</thead>
		<tbody>
            <c:forEach items="${page.list}" var="refundReport">
			<tr>
				<td>
					${refundReport.merchantId}
				</td>
				<td>
                    ${refundReport.merchantLoginName}
				</td>
				<td>
					${refundReport.merchantCompany}
				</td>
				<td>
                    ${refundReport.refundCounts}
				</td>
				<%--<td>
					<fmt:formatNumber value="${refundReport.totalOriPayAmount}" pattern="￥#,##0.00" />
				</td>--%>
                <td style="text-align: right">
                    <fmt:formatNumber value="${refundReport.totalRefundAmount}" pattern="￥#,##0.00" />
                </td>
                <%--<td>
                    <fmt:formatNumber value="${refundReport.totalOriFeeAmount}" pattern="￥#,##0.00" />
                </td>--%>
                <td style="text-align: right">
                    <fmt:formatNumber value="${refundReport.totalFeeBackAmount}" pattern="￥#,##0.00" />
                </td>
			</tr>
            </c:forEach>
		</tbody>
	</table>

    <table id="channelTable" class="table table-striped table-bordered table-condensed" style="display: none">
        <thead>
        <tr>
            <th>原支付类型</th>
            <th>通道合作方</th>
            <th>银行名称</th>
            <%--<th>通道编码</th>--%>
            <th>退款总笔数（笔）</th>
            <%--<th>退款原订单总金额（元）</th>--%>
            <th>退款提交总金额（元）</th>
            <%--<th>退款原订单总支付手续费（元）</th>--%>
            <th>退支付手续费总金额（元）</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.list}" var="refundReport">
            <tr>
                <td>
                    ${fns:getDictLabel(refundReport.payType, 'payType', refundReport.payType)}
                </td>
                <td>
                    ${fns:getDictLabel(refundReport.channelPartner, 'ChannelPartner', refundReport.channelPartner)}
                </td>
                <td>
                    ${fns:getBankNameByBankId(refundReport.bankId)}
                </td>
                <td>
                    ${refundReport.refundCounts}
                </td>
                <%--<td>
                    <fmt:formatNumber value="${refundReport.oriPayAmount}" pattern="￥#,##0.00" />
                </td>--%>
                <td style="text-align: right">
                    <fmt:formatNumber value="${refundReport.totalRefundAmount}" pattern="￥#,##0.00" />
                </td>
                <%--<td>
                    <fmt:formatNumber value="${refundReport.oriFeeAmount}" pattern="￥#,##0.00" />
                </td>--%>
                <td style="text-align: right">
                    <fmt:formatNumber value="${refundReport.totalFeeBackAmount}" pattern="￥#,##0.00" />
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
	<div class="pagination">${page}</div>
</body>
</html>