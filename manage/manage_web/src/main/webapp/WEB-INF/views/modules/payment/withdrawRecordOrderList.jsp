<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>提现订单管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
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
				var merchantCompany = $("#merchantCompany").val();
				var merchantLoginName = $("#merchantLoginName").val();
				
				if(bgTime=="" && endTime!="" || bgTime!="" && endTime=="" ){
					$("#messageBox").text("请正确设置查询时间范围!");
					return ;
				}
				
				var merchantId = $("#merchantId").val();
				if(!isEmpty(merchantId)&&!/\d+/.test(merchantId)){
					alert("商户编码输入不合法，只能输入数字");
					return ;
				}
				if(!isEmpty(merchantCompany)&&!/^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(merchantCompany)){
					alert("商户公司名称输入不合法，请重新输入");
					return ;
				}
				if(!isEmpty(merchantLoginName)&&!/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/.test(merchantLoginName)){
					alert("商户账号输入不合法，请重新输入");
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
		
		function isEmpty(value){
	 		if(value==undefined||value==""||value.trim()==""){
	 			return true;
	 		}
	 		return false;
	 	}

		//搜索
		function onSubmit(){
			validateFrom.validate($("#searchForm"));
		}
		//清空
		function onClear(){
			$("#searchForm").find("input[type='text']").val("");
			//默认支付状态
			$("#withDrawStatus").find("option").removeAttr("selected");
			$("#withDrawStatus").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#withDrawStatus option[selected]").text());
            $(".select2-chosen:eq(1)").text($("#withDrawStatus option[selected]").text());
			//默认排序方式
			$("#sortOrder").find("option").removeAttr("selected");
			$("#sortOrder").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(2)").text($("#sortOrder option[selected]").text());
		}

		
		//导出
		function exportExcel(){
			if(checkDate()){
				var merchantId = $("#merchantId").val();
				var merchantCompany = $("#merchantCompany").val();
				var withdrawId = $("#withdrawId").val();
				var paymentId = $("#paymentId").val();
				var merchantLoginName = $("#merchantLoginName").val();
				var beginCreateTime = $("#beginCreateTime").val();
				var endCreateTime = $("#endCreateTime").val();
				var withDrawStatus = $("#withDrawStatus").val();
				var host = "${ctx}/payment/withdrawRecordOrderList/exportExcel";
				var url = host+"?withdrawId="+withdrawId+"&merchantId="+merchantId+"&merchantCompany="+merchantCompany +
						"&merchantLoginName="+merchantLoginName+"&beginCreateTime="+beginCreateTime+"&endCreateTime="+endCreateTime+
						"&paymentId="+paymentId+"&withDrawStatus="+withDrawStatus;
				$('<form method="post" action="' + url + '"></form>').appendTo('body').submit().remove();
			}
		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/withdrawRecordOrderList/">提现订单管理列表</a></li>
		<shiro:hasPermission name="payment:withdrawRecord:edit"><li><a href="${ctx}/payment/withdrawRecordOrderList/form">提现管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="withdrawRecord" action="${ctx}/payment/withdrawRecordOrderList/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${orderPage.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${orderPage.pageSize}"/>
		<ul class="ul-form">
		
		<!-- 新增查询条件 -->
	        <li><label>商户编码：</label>
                <form:input path="merchantId" id="merchantId" htmlEscape="false" maxlength="19" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')} else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
            </li>
            <li><label>商户公司名称：</label>
                <form:input path="merchantCompany" id="merchantCompany" htmlEscape="false" maxlength="100" class="input-medium"/>
            </li>
			<li><label>交易订单号：</label>
				<form:input path="withdrawId" id="withdrawId" htmlEscape="false" maxlength="20" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')} else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label>支付单号：</label>
				<form:input path="paymentId" id="paymentId" htmlEscape="false" maxlength="20" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')} else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label>商户账号：</label>
				<form:input path="merchantLoginName" id="merchantLoginName" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>
			<li><label>提现状态：</label>
				<form:select id="withDrawStatus" path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<c:forEach items="${fns:getDictList('WithdrawStatus')}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.label}"/>
					</c:forEach>
				</form:select>
			</li>
			<li>
				<label>申请时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${withdrawRecord.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> -
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${withdrawRecord.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
				<form:select id="statisticsDate" path="statisticsDate" class="input-medium" style="width:100px;" onchange="changeDateSection(this.options[this.options.selectedIndex].text);">
					<form:option value="" label="全部"/>
					<c:forEach items="${fns:getDictList('statisticsDate')}" var="item">
						<form:option value="${item.value}" label="${item.label}"/>
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
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" onclick="onSubmit()"  type="button" value="查询"/></li>
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
            <th>提现总笔数</th>
            <th>成功总笔数</th>
            <th>失败总笔数</th>
            <th>提现总金额</th>
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
    
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户编码</th>
				<th>商户公司名称</th>
				<th>商户账号</th>
				<th>交易订单号</th>
				<th>提现金额</th>
				<th>手续费</th>
				<th>支付单号</th>
				<th>提现银行卡号</th>
				<th>提现状态</th>
				<th>提现模式</th>
				<th>申请时间</th>
				<%-- <shiro:lacksPermission name="payment:withdrawRecord:edit"><th>操作</th></shiro:lacksPermission> --%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${orderPage.list}" var="withdrawRecord">
			<tr >
				<td>${withdrawRecord.merchantId}</td>
				<td>
					${withdrawRecord.merchantCompany}
				</td>
				<td>
					${withdrawRecord.merchantLoginName}
				</td>
				<td><a href="${ctx}/payment/withdrawRecord/form?id=${withdrawRecord.withdrawId}">
					${withdrawRecord.withdrawId}
				</a></td>
				<td>
			
					<fmt:formatNumber value="${withdrawRecord.withdrawAmount}" pattern="￥#,##0.00" />
					
				</td>
				<td>
		
					 <fmt:formatNumber value="${withdrawRecord.feeAmount}" pattern="￥#,##0.00" />
				
                </td>
				<td>
                    ${withdrawRecord.paymentId }
                </td>
				<td>
					${withdrawRecord.bankcardNo }
				<%--  	<c:if test="${not empty withdrawRecord.bankcardNo}">
						${fns:decrypt(withdrawRecord.bankcardNo,'1231231adasfsadff')}
					</c:if>  --%>
				</td>

				<td <c:if test="${withdrawRecord.status=='SUCCES'}">
			style="background:${success_background}" 
			</c:if>>
						${fns:getDictLabel(withdrawRecord.status, 'WithdrawStatus', '')}
				</td>
				<td>
					<c:set var="intoaccountTime">
						<fmt:formatDate value="${withdrawRecord.intoaccountTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
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
				</td>
				<td>
					<fmt:formatDate value="${withdrawRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
		<%-- 		<shiro:lacksPermission name="payment:withdrawRecord:edit">
					<td>
						<a href="${ctx}/payment/withdrawRecordOrderList/form?id=${withdrawRecord.withdrawId}">查看</a>
					</td>
				</shiro:lacksPermission> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${orderPage}</div>
	<script type="text/javascript">
		$(function(){
			var merchantId = $("#merchantId").val();
			var merchantCompany = $("#merchantCompany").val();
			var withdrawId = $("#withdrawId").val();
			var paymentId = $("#paymentId").val();
			var merchantLoginName = $("#merchantLoginName").val();
			var beginCreateTime = $("#beginCreateTime").val();
			var endCreateTime = $("#endCreateTime").val();
			var withDrawStatus = $("#withDrawStatus").val();
			$.ajax({
				type : "POST",
				url : "${ctx}/payment/withdrawRecordOrderList/getStatisticsList",
				data : {
					"merchantId" : merchantId,
					"merchantCompany" : merchantCompany,
					"withdrawId" : withdrawId,
					"paymentId" : paymentId,
					"merchantLoginName" : merchantLoginName,
					"beginCreateTime" : beginCreateTime,
					"endCreateTime" : endCreateTime,
					"withDrawStatus" : withDrawStatus
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
