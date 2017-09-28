<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>银行订单管理</title>
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
				
				
				var days = dateDiffBank(bgTime.replace(/ \d+(:\d+){2}/,''), endTime.replace(/ \d+(:\d+){2}/,''));
				if(days > 185){
					$("#messageBox").text("查询的时间范围不能超过6个月!");
					return ;
				}
				
				var bankSerialNo = $("#bankSerialNo").val();
				var bankSerialNoResult = validateNum(bankSerialNo,"银行流水号请输入数字!");
				var paymentId = $("#paymentId").val();
				var paymentIdResult = validateNum(paymentId,"支付单号请输入数字!");
				var merchantLoginName = $("#merchantLoginName").val();
				var merchantLoginNameResult = validatePreventInject(merchantLoginName,"商户账号输入不合法!");
				var merchantId = $("#merchantId").val();
				var result = validateNum(merchantId,"商户编码请输入数字!");
				var transNo = $("#transNo").val();
				var transNoResult = validateNum(transNo,"交易订单号请输入数字!");
				if(!bankSerialNoResult || !paymentIdResult || !merchantLoginNameResult || !result || !transNoResult){
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
			//默认扣款状态
			$("#transType").find("option").removeAttr("selected");
			$("#transType").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#transType option[selected]").text());
			//默认扣款状态
			$("#paymentStatus").find("option").removeAttr("selected");
			$("#paymentStatus").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(1)").text($("#paymentStatus option[selected]").text());
			
			$("#checkStatus").find("option").removeAttr("selected");
			$("#checkStatus").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(2)").text($("#checkStatus option[selected]").text());
            $(".select2-chosen:eq(3)").text($("#checkStatus option[selected]").text());
            $(".select2-chosen:eq(5)").text($("#checkStatus option[selected]").text());
            $(".select2-chosen:eq(6)").text($("#checkStatus option[selected]").text());
			//默认排序方式
			$("#sortOrder").find("option").removeAttr("selected");
			$("#sortOrder").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(4)").text($("#sortOrder option[selected]").text());
		}

		//导出
		function onExport(){
            if(checkDate()){
                var bankSerialNo = $("#bankSerialNo").val();
                var paymentId = $("#paymentId").val();
                var transNo = $("#transNo").val();
                var merchantLoginName = $("#merchantLoginName").val();
                var merchantId = $("#merchantId").val();
                var transType = $("#transType").val();
                var status = $("#paymentStatus").val();
                var checkStatus = $("#checkStatus").val();
                var bankName = $("#bankName").val();
                var bankcardType = $("#bankcardType").val();
                var sortOrder = $("#sortOrder").val();
                var beginCreateTime = $("#beginCreateTime").val();
                var endCreateTime = $("#endCreateTime").val();
                var groupType = $("input[name='groupType']:checked").val();
                var channelPartner = $("#channelPartner").val();
                var host = "${ctx}/payment/paymentFundRecord/exportExcel";
                var url = host+"?bankSerialNo="+bankSerialNo+"&paymentId="+paymentId+"&transNo="+transNo + "&merchantLoginName="+merchantLoginName +
                    "&merchantId="+merchantId+"&transType="+transType+"&status="+status+"&checkStatus="+checkStatus+"&bankName="+bankName+"&bankcardType="+bankcardType+
                    "&sortOrder="+sortOrder+"&beginCreateTime="+beginCreateTime+"&endCreateTime="+endCreateTime+"&groupType="+groupType+"&channelPartner="+channelPartner;
                $('<form method="post" action="' + url + '"></form>').appendTo('body').submit().remove();
            }
		}
		
		
		
		function dateDiffBank(date1, date2){       
	        var type1 = typeof date1; 
	        var type2 = typeof date2;       
	        if(type1 == 'string')       
	            date1 = stringToTimeBank(date1);       
	        else if(date1.getTime)       
	            date1 = date1.getTime();       
	        if(type2 == 'string')       
	            date2 = stringToTimeBank(date2);       
	        else if(date2.getTime)       
	            date2 = date2.getTime();   
	        return (date2 - date1) / 1000 / 60 / 60 / 24;//除1000是毫秒，不加是秒   
		}
		
		function stringToTimeBank(string){       
	        var f = string.split(' ', 2);       
	        var d = (f[0] ? f[0] : '').split('-', 3);       
	        var t = (f[1] ? f[1] : '').split(':', 3);       
	        return (new Date(       
	        parseInt(d[0], 10) || null,       
	        (parseInt(d[1], 10) || 1)-1,       
	        parseInt(d[2], 10) || null,       
	        parseInt(t[0], 10) || null,      
	        parseInt(t[1], 10) || null,       
	        parseInt(t[2], 10) || null)).getTime();   
	    }  
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/paymentFundRecord/">银行订单列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="paymentRecord" action="${ctx}/payment/paymentFundRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>银行流水号：</label>
				<form:input path="bankSerialNo" htmlEscape="false" maxlength="26" class="input-medium"/>
			</li>
			<li><label>支付单号：</label>
				<form:input path="paymentId" htmlEscape="false" maxlength="26" class="input-medium"/>
			</li>
			<li><label>交易订单号：</label>
				<form:input path="transNo" htmlEscape="false" maxlength="26" class="input-medium"/>
			</li>
			<li><label>商户账号：</label>
				<form:input path="merchantLoginName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商户编码：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<!-- 新增交易类型 -->
			<li><label>交易类型：</label>
				<form:select id="transType" path="transType" class="input-medium">
					<form:option value="R1" label="全部"/>
					<c:forEach items="${fns:getDictList('TransType')}" var="wStatus">
						<%--充值,退款,转账，消费，代收，提现,实名认证,打款认证  --%>
						<c:if test="${wStatus.value eq 'CHARGE' || wStatus.value eq 'REFUND' || wStatus.value eq 'PAYMNT'|| wStatus.value eq 'BATPAY'|| wStatus.value eq 'WZDRAW'|| wStatus.value eq 'WECHAT' || wStatus.value eq 'PMONEY' || wStatus.value eq 'RENAME'}">
							<form:option value="${wStatus.value}" label="${wStatus.label}"/>
						</c:if>
					</c:forEach>
				</form:select>
			</li>
			<li><label>扣款状态：</label>
				<form:select id="paymentStatus" path="status" class="input-medium">
					<form:option value="R1" label="全部"/>
					<c:forEach items="${fns:getDictList('PaymentRecordStatus')}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.label}"/>
					</c:forEach>
				</form:select>
			</li>
	 		<li><label>核对状态：</label>
				<form:select id="checkStatus" path="checkStatus" class="input-medium">
					<form:option value="R1" label="全部"/>
					<c:forEach items="${fns:getDictList('CheckStatus')}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.label}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>银行名称：</label>
				<form:input path="bankName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>银行卡类型：</label>
                <form:select id="bankcardType" path="bankcardType" class="input-medium">
                    <form:option value="" label="全部"/>
                    <c:forEach items="${fns:getDictList('BankcardType')}" var="bankcardType">
                        <form:option value="${bankcardType.value}" label="${bankcardType.label}"/>
                    </c:forEach>
                </form:select>
			</li>
			<li><label>升降排序：</label>
				<form:select id="sortOrder" path="sortOrder" class="input-medium">
					<c:forEach items="${fns:getDictList('SortStatus')}" var="sStatus">
						<form:option value="${sStatus.value}" label="${sStatus.label}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${paymentRecord.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${paymentRecord.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<form:select id="statisticsDate" path="statisticsDate" class="input-medium" style="width:100px;" onchange="changeDateSection(this.options[this.options.selectedIndex].text);">
					<form:option value="" label="当天"/>
					<c:forEach items="${fns:getDictList('statisticsDate')}" var="item">
						<form:option value="${item.value}" label="${item.label}"/>
					</c:forEach>
				</form:select>
				<input name="groupType"  type="radio" value="1" style="margin-left: 10px;"  <c:if test="${groupType == 1}">checked="checked"</c:if> >按提交时间
				<input name="groupType" type="radio" value="2" style="margin-right: 10px;" <c:if test="${groupType == 2}">checked="checked"</c:if>>按扣款时间
			</li>
			<li><label>通道合作方：</label>
				<form:select id="channelPartner" path="channelPartner" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getEnumList('channelPartner')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
				</form:select>
			</li>

			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-warning" type="button" onclick="onExport()" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	<table class="table table-striped   table-bordered table-condensed">
		<thead>
		<tr>
			<th>银行订单总金额</th>
			<th>待支付总金额</th>
			<th>处理中总金额</th>
			<th>支付失败总金额</th>
			<th>支付超时总金额</th>
			<th>支付成功总金额</th>
		</tr>
		</thead>
		<tbody>
		<tr>
			<td id="paymentTotalAmount">￥0.00</td>
			<td id="prepayTotalAmount">￥0.00</td>
			<td id="payingTotalAmount">￥0.00</td>
			<td id="failedTotalAmount">￥0.00</td>
			<td id="timoutTotalAmount">￥0.00</td>
			<td id="succesTotalAmount">￥0.00</td>
		</tr>
		</tbody>
	</table>
	<table id="contentTable" class="table  table-striped   table-bordered table-condensed">
		<thead>
		<thead>
		<tr>
			<th>商户编码</th>
			<th>商户公司名称</th>
			<th>商户账号</th>
			<th>支付单号</th>
	<!-- 		<th>支付通道名称</th>
			<th>支付通道类型</th> -->
			<th>通道合作方</th>
			<th>银行名称</th>
			<th>银行卡类型</th>
			<th>银行流水号</th>
			<th>交易类型 </th>
			<th>扣款时间</th>
			<th>订单金额</th>
			<th>实际支付金额</th>
			<th>扣款状态</th>
			<th>交易订单号</th>
			<th>商户订单号</th>
			<th>核对状态</th>
			<!-- <th>平台来源</th> -->
			<th>提交时间</th>
			<!--  <th>核对人</th>-->
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="paymentRecord">
			<tr>
				<td>${paymentRecord.merchantId}</td>
				<td>${paymentRecord.merchantCompany}</td>
				<td>${paymentRecord.merchantLoginName}</td>
				<td><a href="${ctx}/payment/paymentFundRecord/form?id=${paymentRecord.paymentId}">
					${paymentRecord.paymentId}
				</a></td>
				<td>${fns:getDictLabel(paymentRecord.channelPartner, 'ChannelPartner', paymentRecord.channelPartner)}</td>
				<td>${paymentRecord.bankName}</td>
				<td>${fns:getDictLabel(paymentRecord.bankcardType, 'BankcardType', paymentRecord.bankcardType)}</td>
	<%-- 			<td>${paymentRecord.channelName}</td>
				<td>${fns:getDictLabel(paymentRecord.payType, 'PaymentType', '')}</td> --%>
				<td>${paymentRecord.bankSerialNo}</td>
				<td>${fns:getDictLabel(paymentRecord.transType, 'TransType', '')}</td>
				<td><fmt:formatDate value="${paymentRecord.successTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<fmt:formatNumber value="${paymentRecord.payAmount}" pattern="￥#,##0.00" />
				</td>
				<td>
					<fmt:formatNumber value="${paymentRecord.successAmount}" pattern="￥#,##0.00" />
				</td>
				<td <c:if test="${paymentRecord.status=='SUCCES'}">
			            style="background:${success_background}"
			        </c:if>>${fns:getDictLabel(paymentRecord.status, 'PaymentRecordStatus', '')}</td>
				<td>${paymentRecord.transNo}</td>				
				<td>${paymentRecord.merchantOrderNo}</td>
				<td>${fns:getDictLabel(paymentRecord.checkStatus, 'CheckStatus', '')}</td> 
				<td><fmt:formatDate value="${paymentRecord.payTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<!--  <td>-</td>-->
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script type="text/javascript">
        $(function(){
            var bankSerialNo = $("#bankSerialNo").val();
            var paymentId = $("#paymentId").val();
            var transNo = $("#transNo").val();
            var merchantLoginName = $("#merchantLoginName").val();
            var merchantId = $("#merchantId").val();
            var transType = $("#transType").val();
            var status = $("#paymentStatus").val();
            var checkStatus = $("#checkStatus").val();
            var bankName = $("#bankName").val();
            var bankcardType = $("#bankcardType").val();
            var sortOrder = $("#sortOrder").val();
            var beginCreateTime = $("#beginCreateTime").val();
            var endCreateTime = $("#endCreateTime").val();
            var groupType = $("input[name='groupType']:checked").val();
            var channelPartner = $("#channelPartner").val();
            $.ajax({
                type : "POST",
                url : "${ctx}/payment/paymentFundRecord/getStatisticsList",
                data : {
                    "bankSerialNo" : bankSerialNo,
                    "paymentId" : paymentId,
                    "transNo" : transNo,
                    "merchantLoginName" : merchantLoginName,
                    "merchantId" : merchantId,
                    "transType" : transType,
                    "status" : status,
                    "checkStatus" : checkStatus,
                    "bankName" : bankName,
                    "bankcardType" : bankcardType,
                    "sortOrder" : sortOrder,
                    "beginCreateTime" : beginCreateTime,
                    "endCreateTime" : endCreateTime,
                    "groupType" : groupType,
                    "channelPartner" : channelPartner
                },
                dataType : "json",
                success : function(data){
                    $("#paymentTotalAmount").text(data.paymentTotalAmount);
                    $("#prepayTotalAmount").text(data.prepayTotalAmount);
                    $("#payingTotalAmount").text(data.payingTotalAmount);
                    $("#failedTotalAmount").text(data.failedTotalAmount);
                    $("#timoutTotalAmount").text(data.timoutTotalAmount);
                    $("#succesTotalAmount").text(data.succesTotalAmount);
                }
            });

        });
	</script>
</body>
</html>