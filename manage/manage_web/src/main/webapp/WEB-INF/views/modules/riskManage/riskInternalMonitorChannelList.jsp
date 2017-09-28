<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>内部监控通道配置管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
		
		var bankValue = $("#channelPartnerCode").val();
		if(bankValue=='DIRCON'){
			$('#bank_id').css('display','block');
		}else{
			$('#bank_id').css('display','none');
		}
		//$("#channelName").val(obj.options[obj.options.selectedIndex].text);
	});
	
	var validateFrom = {
			validate: function(form){
				
				var bgTime = $("#beginOperEndTime").val();
				var endTime = $("#endOperEndTime").val();
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
				
				$("#searchForm").submit();
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
			$("#pageNo").val(1);
			validateFrom.validate($("#searchForm"));
		}
			//清空
			function onClear(){
				$("#formBtn").submit();
			}
			
			function Sel2change(field){
				
				$("#bankName").val(field);
			}
			
			function checkValue(obj){
				var bankValue = obj.value;
				if(bankValue=='DIRCON'){
					$('#bank_id').css('display','block');
				}else{
					$('#bank_id').css('display','none');
				}
			}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/risk/riskInternalMonitorChannel/">内部监控通道配置列表</a></li>
		<shiro:hasPermission name="risk:riskInternalMonitorChannel:edit"><li><a href="${ctx}/risk/riskInternalMonitorChannel/form">内部监控通道配置添加</a></li></shiro:hasPermission>
	</ul>
	
	<form action="${ctx}/risk/riskInternalMonitorChannel/" method="post" id="formBtn"></form> 
	
	<form:form id="searchForm" modelAttribute="riskInternalMonitorChannel" action="${ctx}/risk/riskInternalMonitorChannel/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label class="control-label">通道合作方：</label>
			    	<form:select  path="channelPartnerCode" name="channelPartnerCode" id="channelPartnerCode" class="input-xlarge" onchange="checkValue(this)" >
			    	    <option selected value="">-通道合作方-</option>
			    	 
					    <c:forEach items="${dataEntity}" var="SettleRuleControl">
							<form:option value="${SettleRuleControl.value}" label="${SettleRuleControl.name}"/>
						</c:forEach>
				    </form:select>
			</li>
			<li style="display: none;" id="bank_id" class="bankId"><label>选择直连银行：</label>
				<form:select id="bankNoid" path="bankNo" name="bankNo" class="input-xlarge" onchange="Sel2change(this.options[this.options.selectedIndex].text);">
					<form:option value="" label="-选择直连银行-"/>
						<form:options items="${fns:getBankInfoList()}" itemLabel="bankName" itemValue="bankNo" htmlEscape="false" />
				</form:select>
				<input type="hidden" name="bankName" id="bankName" class="input-xlarge required">
			</li>
			
			<li><label>支付通道类型：</label>
				<form:select id="channelTypeCode" path="channelTypeCode" class="input-xlarge">
					<form:option value="" label="-支付通道类型-"/>
						<c:forEach items="${checkTypeList}" var="SettleRuleControl">
							<form:option value="${SettleRuleControl.value}" label="${SettleRuleControl.name}"/>
						</c:forEach>
				</form:select> 
			</li>
		</ul>
		<ul class="ul-form">
		      <li><label>创建日期：</label>
				<input id="beginOperEndTime" name="beginOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${riskInternalMonitorChannel.beginOperEndTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input id="endOperEndTime" name="endOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${riskInternalMonitorChannel.endOperEndTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			 </li>
            <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
            <li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
		</ul>
	</form:form>
	<div id="messageBox" style="font-size: 15px; color: red;"></div>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>通道合作方</th>
				<th>通道支付类型</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>频率(分钟)</th>
				<th>阈值(%)</th>
				<th>变化率(%)</th>
				<th>操作人</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<shiro:hasPermission name="risk:riskInternalMonitorChannel:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="riskInternalMonitorChannel">
			<tr>
				<td>${riskInternalMonitorChannel.channelPartnerName}</td>
				<td>${riskInternalMonitorChannel.channelTypeName}</td>
				<td>${riskInternalMonitorChannel.beginTime}</td>
				<td>${riskInternalMonitorChannel.endTime}</td>
				<td>${riskInternalMonitorChannel.frequency}</td>
				<td>${riskInternalMonitorChannel.threshold}</td>
				<td>${riskInternalMonitorChannel.changeRate}</td>
				<td>${riskInternalMonitorChannel.operator}</td>
				<td>
					<fmt:formatDate value="${riskInternalMonitorChannel.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${riskInternalMonitorChannel.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="risk:riskInternalMonitorChannel:edit"><td>
    				<a href="${ctx}/risk/riskInternalMonitorChannel/form?id=${riskInternalMonitorChannel.internalChannelId}">修改</a>
					 <a href="${ctx}/risk/riskInternalMonitorChannel/delete?id=${riskInternalMonitorChannel.internalChannelId}" onclick="return confirmx('确认要删除该内部监控通道配置吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>