<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>对账管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<style>
        #main {
            margin: 50px;
        }
    </style>
	<script type="text/javascript">
		$(document).ready(function() {
			var checkStatus = $("#checkStatus option[selected]").val();
			if(checkStatus=='Y'){
				$('#checkStatusY').css('display','block');
			}else{
				$('#checkStatusY').css('display','none');
			}
		});
		//验证搜索条件
		var validateFrom = {
			validate: function(form){
				
				//对账批次号验证
				var checkBathNo = $("#checkBathNo").val();
				var pattern = new RegExp("[`~!#$^@&*()=|{}':;',\\[\\]<>/?~！#￥……&*（）——|{}【】‘；：”“'。，、？]") 
				if(pattern.test(checkBathNo)){
					$("#messageBox").text("对账批次号输入不合法，请重新输入");
					return false;
				}
				
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
			$("#pageNo").val(1);
			validateFrom.validate($("#searchForm"));
		}
		//清空
		function onClear(){
			$("#formBtn").submit();
		}
		
		//上传文件
		function onDownLoad(){
			$("#formBtn2").submit();
		}

		//文件导出
		function onExport(){
			
	        
	        var actionURL = $("#searchForm").attr("action");
	        $("#searchForm").attr("action",$("#searchForm").attr("action")+"export");
			validateFrom.validate($("#searchForm"));
			$("#searchForm").attr("action",actionURL);
		}

		function Sel2change(field){
			$("#channelCode").val(field);
		}
		
		function checkBathNo(id){
			$.post("${ctx}/reconciliation/SettleCheckBath/checkBathNoTwo/"+id,function(msg){
				if(msg !='0'){
					window.location.href='${ctx}/reconciliation/SettleCheckBath/checkBathNo/'+id;
				}else{
					parent.showThree();
					parent.changeThreeName("下载文件不存在");
				}
			},"text");
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/reconciliation/SettleCheckBath/">对账批次查询列表</a></li>
	</ul>
	<!-- 清除用的表单 -->
	<form action="${ctx}/reconciliation/SettleCheckBath/" method="post" id="formBtn"></form>
	<!-- 清除用的表单 -->
	<form action="${ctx}/reconciliation/SettleCheckBath/uploadFile" method="post" id="formBtn2"></form>
	
	<form:form id="searchForm" modelAttribute="settleChannelLog" action="${ctx}/reconciliation/SettleCheckBath/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			
		<ul class="ul-form">
			<li><label class="control-label">通道合作方：</label>
			    <form:select id="channelCode" path="channelCode" class="input-xlarge" >
					<form:option value="" label="-通道合作方-"/>
					<c:forEach items="${checklist}" var="SettleRuleControl">
						<form:option value="${SettleRuleControl.channelCode}" label="${SettleRuleControl.channelName}"/>
					</c:forEach>
				</form:select>
			    <!-- <input type="hidden" name="channelCode" id="channelCode" class="input-xlarge required">  -->
			</li>
			
			<li><label>支付通道类型：</label>
				<form:select id="channelType" path="channelType" class="input-xlarge">
					<form:option value="" label="-支付通道类型-"/>
						<c:forEach items="${checkTypeList}" var="SettleRuleControl">
							<form:option value="${SettleRuleControl.value}" label="${SettleRuleControl.name}"/>
						</c:forEach>
				</form:select> 
			</li>
			
			<li><label>对账状态：</label>
				<form:select id="checkStatus" path="checkStatus" class="input-xlarge">
					<form:option value="" label="全部"/>
						<c:forEach items="${checkStatus}" var="checkStatusControl">
							<form:option value="${checkStatusControl.value}" label="${checkStatusControl.name}"/>
						</c:forEach>
				</form:select>
			</li>
			
			</ul>
			<ul class="ul-form">
			<li><label>对账批次号：</label>
					<form:input path="checkBathNo" id="checkBathNo" htmlEscape="false" maxlength="20" class="input-medium" placeholder="请输入查询条件" style="width:256px;"/>
			</li> 
			
			<li><label>通道对账日期：</label>
				<input id="beginOperEndTime" name="beginOperEndTime" type="text" readonly="readonly" maxlength="20" style="width:115px;" class="input-medium Wdate"
					value="<fmt:formatDate value="${settleChannelLog.beginOperEndTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input id="endOperEndTime" name="endOperEndTime" type="text" readonly="readonly" maxlength="20" style="width:115px;" class="input-medium Wdate"
					value="<fmt:formatDate value="${settleChannelLog.endOperEndTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清除"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" onclick="onExport()" value="导出"/></li>
			<shiro:hasPermission name="settle:settleChannelLog:edit"><li class="btns"><input class="btn btn-primary" type="button" onclick="onDownLoad()" value="上传对账文件"/></li></shiro:hasPermission>
			
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>通道合作方</th>
			<th>支付通道类型</th>
			<th>通道对账日期</th>
			<th>对账批次号</th>
			
			<th>入款总笔数</th>
			<th>入款总金额</th>
			<th>出款总笔数</th>
			<th>出款总金额</th>
			<th>差异总笔数</th>
			<th>差异总金额</th>
			
			<th>总笔数</th>
			<th>总金额</th>

			<th>规则类型</th>
			<th>对账状态</th>
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="settleChannelLog">
			<tr>
				
				<td>${settleChannelLog.channelName}</td>
				<td>${settleChannelLog.channelTypeName}</td>
				<td><fmt:formatDate value="${settleChannelLog.operEndTime}" type="both" pattern="yyyy-MM-dd"/></td>
				
				<td><a onclick="checkBathNo('${settleChannelLog.checkBathNo}')" style="cursor: pointer;">${settleChannelLog.checkBathNo}</a></td>
				
				<td>${settleChannelLog.inRecordNum}</td>
				<td>
					<fmt:formatNumber value="${settleChannelLog.inTotalAmount}" pattern="￥0.0000" />
				</td>
				<td>${settleChannelLog.outRecordNum}</td>
				<td>
					<fmt:formatNumber value="${settleChannelLog.outTotalAmount}" pattern="￥0.0000" />
				</td>
				
				<td>${settleChannelLog.errorRecordNum}</td>
				<td>${settleChannelLog.errorTotalAmount}</td>
				
				<td>${settleChannelLog.recordNum}</td>
				<td>
					<fmt:formatNumber value="${settleChannelLog.totalAmount}" pattern="￥0.0000" />
				</td>
				<%-- <td>${settleChannelLog.checkFileFrom}</td>
				<td>${settleChannelLog.checkFileWhere}</td> --%>
				<%-- <!-- 平账总笔数 = 出款总笔数 + 入款总记录数 -->
				<td>${settleChannelLog.outRecordNum+settleChannelLog.inRecordNum}</td>
				<td>
					<!-- 平账总金额 = 出款总金额 + 入款总金额 -->
					<fmt:formatNumber value="${settleChannelLog.inTotalAmount+settleChannelLog.outTotalAmount}" pattern="￥0.0000" />
				</td> --%>

				<td>${settleChannelLog.ruleType}</td>
				<td <c:choose>
			     		<c:when test="${settleChannelLog.checkStatus=='已对账'}">
			     		 style="background:#6cf683"
			     		</c:when>
			     		<c:when test="${settleChannelLog.checkStatus=='未开始'}">
			     		 style="background:#707c9b"
			     		</c:when>
			     		<c:when test="${settleChannelLog.checkStatus=='对账中'}">
			     		 style="background:#ffb300"
			     		</c:when>
			     		<c:when test="${settleChannelLog.checkStatus=='未开始中'}">
			     		 style="background:#ffb300"
			     		</c:when>
			     	</c:choose>>
						${settleChannelLog.checkStatus}
				</td>
				<td><a href="${ctx}/reconciliation/SettleCheckBath/show/${settleChannelLog.logId}" style="cursor: pointer;">详情</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>