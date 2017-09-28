<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>电信诈骗风险管理</title>
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
			if(flg){
				$("#pageNo").val(1);
				validateFrom.validate($("#searchForm"));
			}
		}

		//清空
		function onClear(){
			$("#messageBox").text("");
			flg = true;
			
			$("#searchForm").find("input[type='text']").val("");
			
			//请求时间类型
			$("#requestEventType").find("option").removeAttr("selected");
			$("#requestEventType").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#requestEventType option[selected]").text()); 
			
			$("#status").find("option").removeAttr("selected");
			$("#status").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(1)").text($("#status option[selected]").text()); 
			
			$("#riskStatus").find("option").removeAttr("selected");
			$("#riskStatus").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(2)").text($("#riskStatus option[selected]").text()); 
		}
		
		//验证是否是数字
		function checkNum(obj){
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
		
		//文件导出
		function onExport(){
	        var actionURL = $("#searchForm").attr("action");
	        $("#searchForm").attr("action",$("#searchForm").attr("action")+"export");
			validateFrom.validate($("#searchForm"));
			$("#searchForm").attr("action",actionURL);
	        
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/pbc/pbcFeedBackQueryDealQuery/">反欺诈交易处理列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="pbcFeedBack" action="${ctx}/pbc/pbcFeedBackQueryDealQuery/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>申请时间：</label>
				<input id="beginOperEndTime" name="beginOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${pbcFeedBack.beginOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endOperEndTime" name="endOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${pbcFeedBack.endOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>请求事件类型：</label>
				<form:select id="requestEventType" path="requestEventType" class="input-xlarge">
               		<form:option value="" label="请选择"/>
					<c:forEach items="${rEList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>业务申请编号：</label>
				<form:input path="applicationId" onchange="checkValue(this.value)" htmlEscape="false"  style="width:256px;" class="input-medium required"/>
			</li>
		</ul>
		<ul class="ul-form">
			<li><label>报文流水号：</label>
				<form:input path="transSerialNumber" onchange="checkValue(this.value)" htmlEscape="false" style="width:350px;" class="input-medium required"/>
			</li>
			<li><label>上报状态：</label>
				<form:select id="status" path="status" class="input-xlarge">
               		<form:option value="" label="请选择"/>
					<c:forEach items="${rSsList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>风控审核状态：</label>
				<form:select id="riskStatus" path="riskStatus" class="input-xlarge">
               		<form:option value="" label="请选择"/>
					<c:forEach items="${rSList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" onclick="onExport()" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>传输报文流水号</th>
			<th>业务申请编号</th>
			<th>申请时间</th>
			<th>请求事件类型</th>
			<th>申请机构编码</th>
			<th>申请机构名称</th>
			<th>参数类型</th>
			<th>传入参数</th>
			<th>上报状态</th>
			<th>上报失败原因</th>
			<th>风控审核状态</th>
			<shiro:hasPermission name="pbc:pbcFeedBackQueryDeal:edit"><th>操作</th></shiro:hasPermission>
		</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="pbcFeedBack">
				<tr>
					<td>${pbcFeedBack.transSerialNumber}</td>
					<td>${pbcFeedBack.applicationId}</td>
					<td>
						<fmt:formatDate value="${pbcFeedBack.applicationTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>${pbcFeedBack.requestEventType}</td>
					<td>${pbcFeedBack.applicationOrgCode}</td>
					<td>${pbcFeedBack.applicationOrgName}</td>
					<td>${pbcFeedBack.paramType}</td>
					<td>${pbcFeedBack.params}</td>
					<td <c:choose>
			     		<c:when test="${pbcFeedBack.status=='上报成功'}">
			     		 style="background:#6cf683" 	
			     		</c:when>
			     		<c:when test="${pbcFeedBack.status=='未上报'}">
			     		 style="background:#707c9b" 
			     		</c:when>
			     	</c:choose>>${pbcFeedBack.status}</td>
					<td>${pbcFeedBack.failRemark}</td>
					<td <c:choose>
			     		<c:when test="${pbcFeedBack.riskStatus=='审核通过'}">
			     		 style="background:#6cf683" 	
			     		</c:when>
			     		<c:when test="${pbcFeedBack.riskStatus=='待处理'}">
			     		 style="background:#707c9b" 
			     		</c:when>
			     		<c:when test="${pbcFeedBack.riskStatus=='处理中'}">
			     		 style="background:#ffb300" 
			     		</c:when>
			     	</c:choose>>${pbcFeedBack.riskStatus}</td>
					<shiro:hasPermission name="pbc:pbcFeedBackQueryDeal:edit"><td>
						<c:if test="${pbcFeedBack.riskStatus != '待审核'}">
							<a href="${ctx}/pbc/pbcFeedBackQueryDealQuery/deal?feedBackId=${pbcFeedBack.feedBackId}&param=hidden">处理</a>
						</c:if>
					</td></shiro:hasPermission>					
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>