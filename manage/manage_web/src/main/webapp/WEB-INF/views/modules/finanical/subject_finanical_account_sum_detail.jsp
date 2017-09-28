<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>试算平衡-汇总管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(
		function() {
			$("#inputForm").validate(
				{
					submitHandler : function(form) {
						loading('正在提交，请稍等...');
						form.submit();
					},
					errorContainer : "#messageBox",
					errorPlacement : function(error, element) {
						$("#messageBox").text("输入有误，请先更正。");
					    if (element.is(":checkbox")|| element.is(":radio") || element.parent().is(".input-append")) {
							error.appendTo(element.parent().parent());
						} else {
							error.insertAfter(element);
					}
				}
		});
	});

	function changeDate(text){
	    if(text == '全部'){
	    	var yesterday = getDateStr(-1);
	        $("#beginAccountDate").val('2016-12-01');
	        $("#endAccountDate").val(yesterday);
	    }else if(text == '昨天'){
	        var yesterday = getDateStr(-1);
	        $("#beginAccountDate").val(yesterday);
	        $("#endAccountDate").val(yesterday );
	    }else if(text == '本月'){
	        var startMonth = getMonthStartDate();
	        var endMonth = getNowFormatDate();
	        $("#beginAccountDate").val(startMonth);
	        $("#endAccountDate").val(endMonth.split(" ")[0]);
	    }else if(text == '本年'){
	        var year = getYearStr(0);
	        var endYear = getNowFormatDate();
	        $("#beginAccountDate").val(year + "-01-01");
	        $("#endAccountDate").val(endYear.split(" ")[0]);
	    }else if(text == '上月'){
	        var startMonth = getPreMonthStartDate();
	        var endMonth = getPreMonthEndDate();
	        $("#beginAccountDate").val(startMonth);
	        $("#endAccountDate").val(endMonth );
	    }else if(text == '上年'){
	        var year = getYearStr(-1);
	        $("#beginAccountDate").val(year + "-01-01");
	        $("#endAccountDate").val(year + "-12-31");
	    }
	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/finanical/statement/merchantAccountAmount">商户余额</a></li>
		<li class="active"><a
			href="${ctx}/finanical/statement/merchantAccountAmountDetail?accountDate=${accountDate}">明细(${detailParam.beginAccountDate} 至  ${detailParam.endAccountDate})
				</a></li>
	</ul>	
	<br />
	<form:form id="searchForm" modelAttribute="detailParam" action="${ctx}/finanical/statement/merchantAccountAmountDetail" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>日期：</label>
				<input id="beginAccountDate" name="beginAccountDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${beginAccountDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>-
				<input id="endAccountDate" name="endAccountDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${endAccountDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li>
			<form:select id="statisticsDate" path="statisticsDate"
				class="input-medium" style="width:100px;"
				onchange="changeDate(this.options[this.options.selectedIndex].text);">
				<form:option value="" label="全部" />
				<c:forEach items="${fns:getDictList('statisticsDate')}" var="item">
					<form:option value="${item.value}" label="${item.label}" />
				</c:forEach>
			</form:select>
			</li>
			<li>			
				<input type="hidden" name="accountDate" value="${detailParam.accountDate}"/>
				<input type="hidden" name="begin" value="${detailParam.begin}"/>
				<input type="hidden" name="end" value="${detailParam.end}"/>							
				<input type="hidden" name="d" value="${detailParam.d}"/>			
				<input type="hidden" name="c" value="${detailParam.c}"/>	
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>	
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			 <td colspan="3">
			 	<span style="color:#6F00D2;font-weight: bold;font-size: 16px;">期初余额：<fmt:formatNumber value="${detailParam.begin}" pattern="￥#,##0.00"/></span> &nbsp;&nbsp;&nbsp;&nbsp;
			 	<span style="color:#6F00D2;font-weight: bold;font-size: 16px;">借方发生额(支出)：<fmt:formatNumber value="${detailParam.d}" pattern="￥#,##0.00"/></span>&nbsp;&nbsp;&nbsp;&nbsp;
			 	<span style="color:#6F00D2;font-weight: bold;font-size: 16px;">贷方发生额(收入)：<fmt:formatNumber value="${detailParam.c}" pattern="￥#,##0.00"/></span>&nbsp;&nbsp;&nbsp;&nbsp;
			 	<span style="color:#6F00D2;font-weight: bold;font-size: 16px;">期末余额：<fmt:formatNumber value="${detailParam.end}" pattern="￥#,##0.00"/></span>		 				 				 
			 </td>
			</tr>

		</thead>
		<tbody>					
			<tr>
				<th style="text-align: center; color: #46A3FF; vertical-align: middle;">交易类型</th>
				<th style="text-align: center; color: #46A3FF; vertical-align: middle;">借方(支出)</th>
				<th style="text-align: center; color: #46A3FF; vertical-align: middle;">贷方(收入)</th>
			</tr>
			<c:forEach items="${list}" var="sum">
				<tr>
						<td style="text-align: center;">${sum.content}</td>
						<td style="text-align: center;"><fmt:formatNumber value="${sum.leftAmount}"
								pattern="￥#,##0.00" /></td>
						<td style="text-align: center;"><fmt:formatNumber value="${sum.rightAmount}"
								pattern="￥#,##0.00" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="form-actions">
		<input id="btnCancel" class="btn" type="button" value="返 回"
			onclick="history.go(-1)" />
	</div>

</body>
</html>