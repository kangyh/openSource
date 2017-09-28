<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/modules/sys/validation.jsp"%>
<html>
<head>
	<title>清结算管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
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
		}

		//清空
		function onClear(){
			$("#searchForm").find("input[type='text']").val("");

			$("#messageBox").text("");
			flg = true;

			//通道业务类型
			$("#channelType").find("option").removeAttr("selected");
			$("#channelType").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#channelType option[selected]").text());

			//通道名称
			$("#channelName").find("option").removeAttr("selected");
			$("#channelName").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(1)").text($("#channelName option[selected]").text());

			//结算状态
			$("#settleStatus").find("option").removeAttr("selected");
			$("#settleStatus").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(2)").text($("#settleStatus option[selected]").text());
		}

		//文件导出
		function onExport(){
            var flag = true;
            $("form :input").not(":button, :submit, :reset, :hidden").each(function (element) {
                if(this.value != ""){
                    var actionURL = $("#searchForm").attr("action");
                    $("#searchForm").attr("action",$("#searchForm").attr("action")+"export");
                    validateFrom.validate($("#searchForm"));
                    $("#searchForm").attr("action",actionURL);
                    flag = false;
                    return
                }
            });
            if(flag){
                alertx("请选择导出条件!");
            }

			/*var actionURL = $("#searchForm").attr("action");
	        $("#searchForm").attr("action",$("#searchForm").attr("action")+"export");
			validateFrom.validate($("#searchForm"));
			$("#searchForm").attr("action",actionURL);*/
		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/settle/settleChannelRecordQuery/">通道结算单据列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="settleChannelRecord" action="${ctx}/settle/settleChannelRecordQuery/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>通道合作方：</label>
				<form:select id="channelName" path="channelName" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${cList}" var="wStatus">
						<form:option value="${wStatus.channelName}" label="${wStatus.channelName}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>支付通道类型：</label>
				 <form:select id="channelType" path="channelType" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${channelType}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select> 
			</li>
			<li><label>结算状态：</label>
				<form:select id="settleStatus" path="settleStatus" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${settleStatus}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form">
			<li><label>结算日期：</label>
				<input id="beginSettleTime" name="beginSettleTime" type="text" readonly="readonly" style="width:115px;" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${settleChannelRecord.beginSettleTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input id="endSettleTime" name="endSettleTime" type="text" readonly="readonly" style="width:115px;" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${settleChannelRecord.endSettleTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>结算单号：</label>
				<form:input path="settleBath" onchange="checkValue(this.value)" htmlEscape="false" style="width:256px;" maxlength="17" class="input-medium required"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" onclick="onExport()" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>交易总笔数</th>
			<th>交易总金额</th>
			<th>交易总成本</th>
		</tr>
		</thead>
		<tbody>
		<tr>
			<td>${settleSumNum.sumPayNum} 笔</td>
			<td><fmt:formatNumber value="${settleSumNum.sumTotalAmount}" pattern="￥0.0000" /></td>
			<td><fmt:formatNumber value="${settleSumNum.sumCostAmount}" pattern="￥0.0000" /></td>
		</tr>
		</tbody>
	</table>

	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>通道合作方</th>
			<th>支付通道类型</th>
			<th>交易总笔数</th>
			<th>交易总金额</th>
			<th>结算日期</th>
			<th>结算单号</th>
			<th>交易总成本</th>
			<th>结算状态</th>
			<th>查看明细</th>
			<shiro:hasPermission name="settle:settleChannelRecord:edit"><th style="width: 150px">结算处理</th></shiro:hasPermission>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="settleChannelRecord">
			<tr>
				<td>${settleChannelRecord.channelName}</td>
				<td>${settleChannelRecord.channelType}</td>
				<td>${settleChannelRecord.payNum}</td>
				<td>
					<fmt:formatNumber value="${settleChannelRecord.totalAmount}" pattern="￥0.0000" />
				</td>
				<td><fmt:formatDate value="${settleChannelRecord.settleTime}" type="both" pattern="yyyy-MM-dd"/></td>
				<td>${settleChannelRecord.settleBath}</td>
				<td>
					<fmt:formatNumber value="${settleChannelRecord.costAmount}" pattern="￥0.0000" />
				</td>
				<td <c:choose>
			     		<c:when test="${settleChannelRecord.settleStatus=='已结算'}">
			     		 style="background:#6cf683" 	
			     		</c:when>
			     		<c:when test="${settleChannelRecord.settleStatus=='未结算'}">
			     		 style="background:#707c9b" 
			     		</c:when>
			     		<c:when test="${settleChannelRecord.settleStatus=='结算中'}">
			     		 style="background:#ffb300" 
			     		</c:when>
			     		<c:when test="${settleChannelRecord.settleStatus=='不结算'}">
			     		 style="background: #00ffff;"
			     		</c:when>
			     		<c:when test="${settleChannelRecord.settleStatus=='处理中'}">
			     		 style="background:red"
			     		</c:when>
			     	</c:choose>>${settleChannelRecord.settleStatus}</td>
				<td>
					<a href="${ctx}/settle/settleChannelRecordQuery/toDetail?settleBath=${settleChannelRecord.settleBath}">查看明细</a>
				</td>
				<shiro:hasPermission name="settle:settleChannelRecord:edit"><td>
					<c:choose>
						<c:when test="${not empty settleChannelRecord.flag}">
							<c:if test="${settleChannelRecord.settleStatus == '处理中'}">
								<a href="${ctx}/settle/settleChannelRecordQuery/settle/1/${settleChannelRecord.settleBath}" onclick="return confirmx('<h4 style=\'color: red;\'>是否取消结算,该记录再次结算过</h4>', this.href)" style="color: red">取消结算</a>
								|   <a href="${ctx}/settle/settleChannelRecordQuery/settle/2/${settleChannelRecord.settleBath}" onclick="return confirmx('<h4>确认要二次发起？</h4>', this.href)" style="color: red">二次发起</a>
							</c:if>
							<c:if test="${settleChannelRecord.settleStatus != '处理中'}">
								<a href="${ctx}/settle/settleChannelRecordQuery/settle/1/${settleChannelRecord.settleBath}" onclick="return confirmx('确认要取消结算？', this.href)">取消结算</a>
								|   <a href="${ctx}/settle/settleChannelRecordQuery/settle/2/${settleChannelRecord.settleBath}" onclick="return confirmx('确认要再次结算？', this.href)">再次结算</a>
							</c:if>
						</c:when>
						<c:otherwise>
							--
						</c:otherwise>
					</c:choose>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>