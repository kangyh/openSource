<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>量化派白条支付记录管理</title>
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
            $("#searchForm").find("option").removeAttr("selected");
            $("#searchForm").find("option:eq(0)").attr("selected","selected");
            $(".select2-chosen").text($("#searchForm option[selected]").text());
        }


        //导出
        function exportExcel(){
            if(checkDate()){
                var quantId = $("#quantId").val();
                var merchantId = $("#merchantId").val();
                var extData = $("#extData").val();
                var phoneno = $("#phoneno").val();
                var beginOrderAmount = $("#beginOrderAmount").val();
                var endOrderAmount = $("#endOrderAmount").val();
                var term = $("#term").val();
                var payMode = $("#payMode").val();
				var beginCreateTime = $("#beginCreateTime").val();
                var endCreateTime = $("#endCreateTime").val();
                var status = $("#status").val();
                var groupType = $("input[name='groupType']:checked").val();
                var host = "${ctx}/payment/quantGroupPayRecord/exportExcel";
                var url = host+"?quantId="+quantId+"&merchantId="+merchantId+"&extData="+extData+"&phoneno="+phoneno +
                    "&beginOrderAmount="+beginOrderAmount+"&endOrderAmount="+endOrderAmount+"&term="+term+
                    "&payMode="+payMode+"&beginCreateTime="+beginCreateTime+"&endCreateTime="+endCreateTime+"&status="+status+"&groupType="+groupType;
                $('<form method="post" action="' + url + '"></form>').appendTo('body').submit().remove();
            }
        }


        function checkDate(){
            var beginCreateTime = $("#beginCreateTime").val();
            var endCreateTime = $("#endCreateTime").val();
            if(beginCreateTime != '' && endCreateTime == ''){
                alert("请录入结束时间");
                return false;
            }

            if(beginCreateTime == '' && endCreateTime != ''){
                alert("请录入开始时间");
                return false;
            }

            var nowDate = getNowFormatDate();
            if(beginCreateTime > nowDate){
                alert("开始时间不能大于当前时间");
                return false;
            }

            if(endCreateTime > nowDate){
                alert("结束时间不能大于当前时间");
                return false;
            }

            if(beginCreateTime > endCreateTime){
                alert("开始时间不能大于结束时间");
                return false;
            }

            return true;

        }


        //获取当前时间
        function getNowFormatDate() {
            var date = new Date();
            var seperator1 = "-";
            var seperator2 = ":";
            var month = date.getMonth() + 1;
            var strDate = date.getDate();
            var hours = date.getHours();
            var minutes = date.getMinutes();
            var seconds = date.getSeconds();
            if (month >= 1 && month <= 9) {
                month = "0" + month;
            }
            if (strDate >= 0 && strDate <= 9) {
                strDate = "0" + strDate;
            }
            if (hours >= 1 && hours <= 9) {
                hours = "0" + hours;
            }
            if (minutes >= 0 && minutes <= 9) {
                minutes = "0" + minutes;
            }
            if (seconds >= 0 && seconds <= 9) {
                seconds = "0" + seconds;
            }
            var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
                + " " + hours + seperator2 + minutes
                + seperator2 + seconds;
            return currentdate;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/quantGroupPayRecord/">量化派白条支付记录列表</a></li>
		<shiro:hasPermission name="payment:quantGroupPayRecord:edit"><li><a href="${ctx}/payment/quantGroupPayRecord/form">量化派白条支付记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="quantGroupPayRecord" action="${ctx}/payment/quantGroupPayRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>分期支付ID：</label>
				<form:input path="quantId" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>商户ID：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>分期通道类型</label>
				<form:select path="extData" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getEnumList('installmentType')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>用户手机号：</label>
				<form:input path="phoneno" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>订单金额：</label>
				<form:input path="beginOrderAmount" htmlEscape="false" class="input-medium"/> -
				<form:input path="endOrderAmount" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>付款分期</label>
				<form:select path="term" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('InstallmentType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>支付方式</label>
				<form:select path="payMode" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('PayMode')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>创建时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${quantGroupPayRecord.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${quantGroupPayRecord.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<input name="groupType"  type="radio" value="1" style="margin-left: 10px;"  <c:if test="${groupType == 1}">checked="checked"</c:if> >按创建时间
				<input name="groupType" type="radio" value="2" style="margin-right: 10px;" <c:if test="${groupType == 2}">checked="checked"</c:if>>按修改时间
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('PayRecordStatus')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="btns"><input class="btn btn-warning" type="button" onclick="exportExcel()" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>分期支付ID</th>
				<th>支付单号</th>
				<th>商户ID</th>
				<th>分期通道类型</th>
				<th>用户手机号</th>
				<th>商户用户IP</th>
				<th>商品名称</th>
				<th>订单所属分店</th>
				<th>订单金额</th>
				<th>付款分期</th>
				<th>支付方式</th>
				<th>创建时间</th>
				<th>修改时间</th>
				<th>状态</th>
				<shiro:hasPermission name="payment:quantGroupPayRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="quantGroupPayRecord">
			<tr>
				<td><%--<a href="${ctx}/payment/quantGroupPayRecord/form?id=${quantGroupPayRecord.id}">--%>
					${quantGroupPayRecord.quantId}
				<%--</a>--%></td>
				<td>
					${quantGroupPayRecord.paymentId}
				</td>
				<td>
					${quantGroupPayRecord.merchantId}
				</td>
				<td>
					${quantGroupPayRecord.extData}
				</td>
				<td>
					<c:if test="${quantGroupPayRecord.phoneno!=null&&quantGroupPayRecord.phoneno!=''}">
						${fn:substring(quantGroupPayRecord.phoneno, 0, 3)}****${fn:substring(quantGroupPayRecord.phoneno, 7, 11)}
					</c:if>
				</td>
				<td>
					${quantGroupPayRecord.userIp}
				</td>
				<td>
					${quantGroupPayRecord.orderName}
				</td>
				<td>
					${quantGroupPayRecord.orderMerchant}
				</td>
				<td>
					${quantGroupPayRecord.orderAmount}
				</td>
				<td>
					${fns:getDictLabel(quantGroupPayRecord.term, 'InstallmentType', '')}
				</td>
				<td>
					${fns:getDictLabel(quantGroupPayRecord.payMode, 'PayMode', '')}
				</td>
				<td>
					<fmt:formatDate value="${quantGroupPayRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${quantGroupPayRecord.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td <c:if test="${quantGroupPayRecord.status=='SUCCES'}">style="background:${success_background}"</c:if>>
					${fns:getDictLabel(quantGroupPayRecord.status, 'PayRecordStatus', '')}
				</td>
				<shiro:hasPermission name="payment:quantGroupPayRecord:edit"><td>
    				<a href="${ctx}/payment/quantGroupPayRecord/form?id=${quantGroupPayRecord.id}">修改</a>
					<a href="${ctx}/payment/quantGroupPayRecord/delete?id=${quantGroupPayRecord.id}" onclick="return confirmx('确认要删除该量化派白条支付记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>