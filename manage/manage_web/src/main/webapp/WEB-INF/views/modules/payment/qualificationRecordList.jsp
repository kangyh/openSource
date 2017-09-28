<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>打款认证管理</title>
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
		//搜索
		function onSubmit(){
			var maxYear = 1;	//默认查询一年
			var bgTime = $("#beginCreateTime").val();
			var endTime = $("#endCreateTime").val();
			var merchantLoginName = $("#merchantLoginName").val();
			if(bgTime=="" && endTime!="" || bgTime!="" && endTime=="" ){
				$("#messageBox").text("请正确设置查询时间范围!");
				return ;
			}
			if( bgTime!="" && endTime!=""){
				if(compareDate(convertDateToJoinStr(bgTime),
								convertDateToJoinStr(endTime)) > 0){
					$("#messageBox").text("起始日期不能大于结束日期!");
					return ;
				}
			}
			if(""!=merchantLoginName &&!/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/.test(merchantLoginName)){
				$("#messageBox").text("商户账号输入不合法，请重新输入!");
				return ;
			}
			$("#searchForm").submit();
		}

		//清空
		function onClear(){
			$("#searchForm").find("input[type='text']").val("");
			$("#searchForm").find("option").removeAttr("selected");
			$("#searchForm").find("option:eq(0)").attr("selected","selected");
            $(".select2-chosen").text($("#searchForm option[selected]").text());
		}

        //导出
        function onExport(){
            if(checkDate()){
                var merchantId = $("#merchantId").val();
                var merchantLoginName = $("#merchantLoginName").val();
                var beginCreateTime = $("#beginCreateTime").val();
                var endCreateTime = $("#endCreateTime").val();
                var qualifyId = $("#qualifyId").val();
                var status = $("#status").val();
                var host = "${ctx}/payment/qualificationRecord/exportExcel";
                var url = host+"?merchantId="+merchantId+"&merchantLoginName="+merchantLoginName+"&beginCreateTime="+beginCreateTime + "&endCreateTime="+endCreateTime +
                    "&qualifyId="+qualifyId+"&status="+status;
                $('<form method="post" action="' + url + '"></form>').appendTo('body').submit().remove();
            }
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/qualificationRecord/">打款认证列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="qualificationRecord" action="${ctx}/payment/qualificationRecord/" method="post" class="breadcrumb form-search">
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <ul class="ul-form">
            <li><label>商户编码：</label>
                <form:input path="merchantId" htmlEscape="false" maxlength="64" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')} else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
            </li>
            <li><label>商户账号：</label>
                <form:input path="merchantLoginName" htmlEscape="false" maxlength="64" class="input-medium"/>
            </li>
            <li><label>打款时间：</label>
                <input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                    value="<fmt:formatDate value="${qualificationRecord.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                    onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
                <input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                    value="<fmt:formatDate value="${qualificationRecord.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                    onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<form:select id="statisticsDate" path="statisticsDate" class="input-medium" style="width:100px;" onchange="changeDateSection(this.options[this.options.selectedIndex].text);">
					<form:option value="" label="全部"/>
					<c:forEach items="${fns:getDictList('statisticsDate')}" var="item">
						<form:option value="${item.value}" label="${item.label}"/>
					</c:forEach>
				</form:select>
            </li>
            <li><label>交易订单号：</label>
                <form:input path="qualifyId" htmlEscape="false" maxlength="20" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')} else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
            </li>
            <li><label>状态：</label>
                <form:select path="status" class="input-medium">
                    <form:option value="" label="全部"/>
                    <c:forEach items="${fns:getDictList('QualificationRecordStatus')}" var="sStatus">
                        <form:option value="${sStatus.value}" label="${sStatus.label}"/>
                    </c:forEach>
                </form:select>
            </li>
        </ul>
        <p class="btns" style="margin-left:44px;margin-top:10px;">
	        <input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/>
	        <input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空" style="margin-left:10px;"/>
			<input id="btnExport" class="btn btn-warning" type="button" onclick="onExport()" value="导出" style="margin-left:10px;"/>
        </p>
        <div id="messageBox" style="font-size: 15px; color: red;"></div>
    </form:form>
	<sys:message content="${message}"/>
	<table class="table table-striped   table-bordered table-condensed">
		<thead>
		<tr>
			<th>打款认证总金额</th>
			<th>打款中金额</th>
			<th>打款失败总金额</th>
			<th>打款成功总金额</th>
		</tr>
		</thead>
		<tbody>
		<tr>
			<td id="qualificationTotalAmount">￥0.00</td>
			<td id="payingTotalAmount">￥0.00</td>
			<td id="failedTotalAmount">￥0.00</td>
			<td id="succesTotalAmount">￥0.00</td>
		</tr>
		</tbody>
	</table>
	<table id="contentTable" class="table table-striped   table-bordered table-condensed">
		<thead>
			<tr>
			    <th>交易订单号</th>
				<th>商户编码</th>
				<th>商户账号</th>
				<th>商户公司名称</th>
				<th>银行卡号</th>
				<th>开户行名称</th>
				<th>打款金额</th>
				<th>状态</th>
				<th>打款时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="qualification">
			<tr >
			    <td>
                    ${qualification.qualifyId}
                </td>
				<td>
					${qualification.merchantId}
				</td>
				<td>
					${qualification.merchantLoginName}
				</td>
				<td>
                    ${qualification.merchantName}
                </td>
                <td>
                    ${qualification.bankcardNo}
                </td>
                <td>
                    ${qualification.openbankName}
                </td>
				<td>
					<fmt:formatNumber value="${qualification.payAmount}" pattern="￥#,##0.00" />
				</td>
				<td <c:if test="${qualification.status=='SUCCES'}">
			style="background:${success_background}" 
			</c:if>>
                    ${fns:getDictLabel(qualification.status, 'QualificationRecordStatus', '')}
                </td>
				<td>
                    <fmt:formatDate value="${qualification.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script type="text/javascript">
        $(function(){
            var merchantId = $("#merchantId").val();
            var merchantLoginName = $("#merchantLoginName").val();
            var beginCreateTime = $("#beginCreateTime").val();
            var endCreateTime = $("#endCreateTime").val();
            var qualifyId = $("#qualifyId").val();
            var status = $("#status").val();
            $.ajax({
                type : "POST",
                url : "${ctx}/payment/qualificationRecord/getStatisticsList",
                data : {
                    "merchantId" : merchantId,
                    "merchantLoginName" : merchantLoginName,
                    "beginCreateTime" : beginCreateTime,
                    "endCreateTime" : endCreateTime,
                    "qualifyId" : qualifyId,
                    "status" : status
                },
                dataType : "json",
                success : function(data){
                    $("#qualificationTotalAmount").text(data.qualificationTotalAmount);
                    $("#payingTotalAmount").text(data.payingTotalAmount);
                    $("#failedTotalAmount").text(data.failedTotalAmount);
                    $("#succesTotalAmount").text(data.succesTotalAmount);
                }
            });

        });
	</script>
</body>
</html>