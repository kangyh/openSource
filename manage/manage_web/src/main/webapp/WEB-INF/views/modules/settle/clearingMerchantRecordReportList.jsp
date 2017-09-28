<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>报表管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script language="JavaScript" src="${ctxStatic}/common/validateNum.js"></script>
	<script type="text/javascript">
		//用于验证
		var flg = true;
		
		$(document).ready(function() {
		});
		//验证搜索条件
		var validateFrom = {
			validate: function(form){
				var bgTime = $("#beginSettleTime").val();
				var endTime = $("#endSettleTime").val();
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
				form.submit();
			}
		}
		
		//验证是否是数字
		function checkValue(obj){
			var flgs = validatePreventInject(obj,"商户公司名称输入不合法!");
			if(!flgs){
				$("#messageBox").text("商户公司名称输入不合法，请重新输入");
				flg = false;
				return;
			}else{
				var pattern = new RegExp("[`~!#%$^@&*()=|{}':;',\\[\\]<>/?~！#￥……&*（）——|{}【】‘；：”“'。，、？.]")
				if(pattern.test(obj)){
					$("#messageBox").text("商户编码输入不合法，请重新输入");
					flg = false;
					return;
				}
				if(isNaN(obj)){
					$("#messageBox").text("请输入数字！");
					flg = false;
					return;
				}else{
					$("#messageBox").text("");
					flg = true;
					return;
				}
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
			if(flg){
				$("#pageNo").val(1);
				validateFrom.validate($("#searchForm"));
			}
			return flg;
		}

		//清空
		function onClear(){
			$("#searchForm").find("input[type='text']").val("");
			
			$("#messageBox").text("");
			flg = true;
			
			$("#payType").find("option").removeAttr("selected");
			$("#payType").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#payType option[selected]").text());
			
			$("#channelName").find("option").removeAttr("selected");
			$("#channelName").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(1)").text($("#channelName option[selected]").text());
			
			$("#bankcardType").find("option").removeAttr("selected");
			$("#bankcardType").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(2)").text($("#bankcardType option[selected]").text());

			$("#groupby").find("option").removeAttr("selected");
			$("#groupby").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(3)").text($("#groupby option[selected]").text());
		}
		
		//文件导出
		function onExport(){
			var actionURL = $("#searchForm").attr("action");
	        $("#searchForm").attr("action",$("#searchForm").attr("action")+"export");
			validateFrom.validate($("#searchForm"));
			$("#searchForm").attr("action",actionURL);
		}
		
		function checkNameValue(val){
			var flgs = validatePreventInject(val,"商户公司名称输入不合法!");
			if(!flgs){
				$("#messageBox").text("商户公司名称输入不合法，请重新输入");
				flg = false;
				return;
			}else{
				var pattern = new RegExp("[`~!#%$^@&*()=|{}':;',\\[\\]<>/?~！#￥……&*（）——|{}【】‘；：”“'。，、？.]") 
				if(pattern.test(val)){
					$("#messageBox").text("商户公司名称输入不合法，请重新输入");
					flg = false;
					return;
				}else{
					$("#messageBox").text("");
					flg = true;
					return;
				}
			}
		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/settle/clearingMerchantRecordReportQuery/">财务报表数据列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="clearingMerchantRecord" action="${ctx}/settle/clearingMerchantRecordReportQuery/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>日期：</label>
				<input id="beginSettleTime" name="beginSettleTime" type="text" readonly="readonly" maxlength="20" style="width:115px;" class="input-medium Wdate"
					value="<fmt:formatDate value="${clearingMerchantRecord.beginSettleTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input id="endSettleTime" name="endSettleTime" type="text" readonly="readonly" maxlength="20" style="width:115px;" class="input-medium Wdate"
					value="<fmt:formatDate value="${clearingMerchantRecord.endSettleTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>商户编码：</label>
				<form:input path="merchantId" onchange="checkValue(this.value)" htmlEscape="false" maxlength="10" style="width:256px;" class="input-medium required"/>
			</li>
			<li><label>商户名称：</label>
				<form:input path="merchantName" onchange="checkNameValue(this.value)" htmlEscape="false" maxlength="10" style="width:256px;" class="input-medium required"/>
			</li>
		</ul>
		<ul class="ul-form">
			<li><label>支付类型：</label>
				<form:select id="payType" path="payType" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${pList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select> 
			</li>
			<li><label>通道编码：</label>
				<form:select id="channelName" path="channelName" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${cList}" var="wStatus">
						<form:option value="${wStatus.channelName}" label="${wStatus.channelName}"/>
					</c:forEach>
				</form:select> 
			</li>
		</ul>
		<ul class="ul-form">
			<li><label>银行卡类型：</label>
				<form:select id="bankcardType" path="bankcardType" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<form:option value="SAVING" label="储蓄卡"/>
					<form:option value="CREDIT" label="信用卡"/>
				</form:select>
			</li>
			<li><label>分组类型：</label>
				<form:select id="groupby" path="groupby" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<form:option value="merchant" label="按商户"/>
					<form:option value="paytype" label="按支付类型"/>
					<form:option value="merchantAndPayType" label="按商户+支付类型"/>
					<form:option value="channel" label="按通道"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="return onSubmit();" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" onclick="onExport()" value="导出"/></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<c:if test="${clearingMerchantRecord.groupby != null and clearingMerchantRecord.groupby == 'merchant'}">
				<th>商户编码</th>
				<th>商户名称</th>
			</c:if>
			<c:if test="${clearingMerchantRecord.groupby != null and clearingMerchantRecord.groupby == 'paytype'}">
				<th>支付类型</th>
			</c:if>
			<c:if test="${clearingMerchantRecord.groupby != null and clearingMerchantRecord.groupby == 'merchantAndPayType'}">
				<th>商户编码</th>
				<th>商户名称</th>
				<th>支付类型</th>
			</c:if>
			<c:if test="${clearingMerchantRecord.groupby != null and clearingMerchantRecord.groupby == 'channel'}">
				<th>支付类型</th>
				<th>通道名称</th>
				<th>银行名称</th>
			</c:if>
			<th>交易笔数（笔）</th>
			<th>成功交易金额（元）</th>
			<th>手续费（元）</th>
			<th>商户应结算金额（元）</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="clearingMerchantRecord">
			<tr>
				<c:if test="${clearingMerchantRecord.groupby != null and clearingMerchantRecord.groupby == 'merchant'}">
					<td>${clearingMerchantRecord.merchantId}</td>
					<td>${clearingMerchantRecord.merchantName}</td>
				</c:if>
				<c:if test="${clearingMerchantRecord.groupby != null and clearingMerchantRecord.groupby == 'paytype'}">
					<td>${clearingMerchantRecord.payType}</td>
				</c:if>
				<c:if test="${clearingMerchantRecord.groupby != null and clearingMerchantRecord.groupby == 'merchantAndPayType'}">
					<td>${clearingMerchantRecord.merchantId}</td>
					<td>${clearingMerchantRecord.merchantName}</td>
					<td>${clearingMerchantRecord.payType}</td>
				</c:if>
				<c:if test="${clearingMerchantRecord.groupby != null and clearingMerchantRecord.groupby == 'channel'}">
					<td>${clearingMerchantRecord.payType}</td>
					<td>${clearingMerchantRecord.channelName}</td>
					<td>${clearingMerchantRecord.bankName}</td>
				</c:if>
				<td>${clearingMerchantRecord.countNum}</td>
				<td>
					<fmt:formatNumber value="${clearingMerchantRecord.successAmount}" pattern="￥0.0000" />
				</td>
				<td>
					<fmt:formatNumber value="${clearingMerchantRecord.feeAmount}" pattern="￥0.0000" />
				</td>
				<td>
					<fmt:formatNumber value="${clearingMerchantRecord.settleAmount}" pattern="￥0.0000" />
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>