<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>上报信息查询</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
	
		//动态口令验证
		$(document).ready(function() {
			$(".checkPass").on("click",function(){
				var url = $(this).attr("value-url");
				parent.showDynamicPa();
				parent.enterDynamicPa(url);
			})
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
			$("#pageNo").val(1);
			validateFrom.validate($("#searchForm"));
		}
		
		//清空
		function onClear(){
			$("#formBtn").submit();
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/pbc/verification">举报及验证信息列表</a></li>
	</ul>
	<form action="${ctx}/pbc/verification/" method="post" id="formBtn"></form> 
	<form:form id="searchForm" modelAttribute="pbcFeedBack" action="${ctx}/pbc/verification" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			
			<li><label>请求事件类型：</label>
				<form:select id="feedType" path="feedType" class="input-xlarge">
					<form:option value="" label="全部"/>
					<c:forEach items="${pETList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>业务申请编号：</label>
				<form:input path="applicationId"  htmlEscape="false" maxlength="26" style="width:256px;" class="input-medium required"/>
			</li>
		</ul> 
		
	 	<ul class="ul-form">
	 		<li><label>传输报文流水：</label>
				<form:input path="transSerialNumber"  htmlEscape="false" maxlength="100" style="width:256px;" class="input-medium required"/>
			</li>
			<li><label>上报状态：</label>
				<form:select id="status" path="status" class="input-xlarge">
					<form:option value="" label="全部"/>
					<c:forEach items="${pHSTList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
		</ul> 
		
 		<ul class="ul-form">
			<li><label>风控状态审核：</label>
				<form:select id="riskStatus" path="riskStatus" class="input-xlarge">
					<form:option value="" label="全部"/>
					<c:forEach items="${riskList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li> 
			<li><label>申请时间：</label>
				<input id="beginOperEndTime" name="beginOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${pbcFeedBack.beginOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endOperEndTime" name="endOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${pbcFeedBack.endOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
	     <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
	     <li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
		</ul>  
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>序号</th>
			<th>传输报文流水号</th>
			<th>申请时间</th>
			<th>请求事件类型</th>
			<th>申请机构编码</th>
			<th>申请机构名称</th>
			<th>参数类型</th>
			<th>传入参数</th>
			
			<th>上报状态</th>
			<th>返回码</th>
			<th>返回消息</th>
			<th>上报失败原因</th>
			<th>风控审核状态</th>
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="pbcFeedBack" varStatus="i">
				<tr>
					<td>${i.count}</td>
					<td>${pbcFeedBack.transSerialNumber}</td>
					<td><fmt:formatDate value="${pbcFeedBack.applicationTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>${pbcFeedBack.feedType}</td>
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
			     		<c:when test="${pbcFeedBack.status=='上报失败'}">
			     		 style="background:red" 
			     		</c:when>
			     	</c:choose>>${pbcFeedBack.status}</td>
					<td>${pbcFeedBack.code}</td>
					<td>${pbcFeedBack.description}</td>
					<td>${pbcFeedBack.failRemark}</td>
					<td <c:choose>
			     		<c:when test="${pbcFeedBack.riskStatus=='审核通过'}">
			     		 style="background:#6cf683" 	
			     		</c:when>
			     		<c:when test="${pbcFeedBack.riskStatus=='待处理'}">
			     		 style="background:#707c9b" 
			     		</c:when>
			     		<c:when test="${pbcFeedBack.riskStatus=='待审核'}">
			     		 style="background:#ffb300" 
			     		</c:when>
			     	</c:choose>>${pbcFeedBack.riskStatus}</td>
					
					
				    <td>
				    	<c:choose>
				    		<c:when test="${not empty status}">
				    			<c:if test="${pbcFeedBack.xml == 'ABNORMALACCOUNTS'}"><a href="${ctx}/pbc/pbcPaymentAccount/view?no=true&feedBackId=${pbcFeedBack.feedBackId}">查看</a></c:if>
								<c:if test="${pbcFeedBack.xml == 'ACCOUNTSINVOLVED'}"><a href="${ctx}/pbc/pbcPaymentAccountTwo/view/two?no=true&feedBackId=${pbcFeedBack.feedBackId}">查看</a></c:if>
								<c:if test="${pbcFeedBack.xml == 'EXCEPTIONEVENTS'}"><a href="${ctx}/pbc/pbcPaymentAccountThree/view/three?no=true&feedBackId=${pbcFeedBack.feedBackId}">查看</a></c:if>
								<c:if test="${pbcFeedBack.xml == 'SEXCEPTIONEVENT'}"><a href="${ctx}/pbc/pbcPaymentAccountFour/view/four?no=true&feedBackId=${pbcFeedBack.feedBackId}">查看</a></c:if>
				    		</c:when>
				    		<c:otherwise>
				    				<c:if test="${pbcFeedBack.xml == 'ABNORMALACCOUNTS'}"><a href="${ctx}/pbc/pbcPaymentAccount/view?no=true&feedBackId=${pbcFeedBack.feedBackId}">查看</a>
									| <a href="${ctx}/pbc/pbcPaymentAccount/view?feedBackId=${pbcFeedBack.feedBackId}">审核</a>
									| <a href="${ctx}/pbc/pbcPaymentAccount/view/delete/${pbcFeedBack.feedBackId}">删除</a></c:if>
									<c:if test="${pbcFeedBack.xml == 'ACCOUNTSINVOLVED'}"><a href="${ctx}/pbc/pbcPaymentAccountTwo/view/two?no=true&feedBackId=${pbcFeedBack.feedBackId}">查看</a>
									| <a href="${ctx}/pbc/pbcPaymentAccountTwo/view/two?feedBackId=${pbcFeedBack.feedBackId}">审核</a>
									| <a href="${ctx}/pbc/pbcPaymentAccountTwo/view/two/delete/${pbcFeedBack.feedBackId}">删除</a></c:if>
									<c:if test="${pbcFeedBack.xml == 'EXCEPTIONEVENTS'}"><a href="${ctx}/pbc/pbcPaymentAccountThree/view/three?no=true&feedBackId=${pbcFeedBack.feedBackId}">查看</a>
									| <a href="${ctx}/pbc/pbcPaymentAccountThree/view/three?feedBackId=${pbcFeedBack.feedBackId}">审核</a>
									| <a href="${ctx}/pbc/pbcPaymentAccountTwo/view/two/delete/${pbcFeedBack.feedBackId}">删除</a></c:if>
									<c:if test="${pbcFeedBack.xml == 'SEXCEPTIONEVENT'}"><a href="${ctx}/pbc/pbcPaymentAccountFour/view/four?no=true&feedBackId=${pbcFeedBack.feedBackId}">查看</a>
									| <a href="${ctx}/pbc/pbcPaymentAccountFour/view/four?feedBackId=${pbcFeedBack.feedBackId}">审核</a>
									| <a href="${ctx}/pbc/pbcPaymentAccountFour/view/four/delete/${pbcFeedBack.feedBackId}">删除</a></c:if>
				    		</c:otherwise>
				    	</c:choose>
					  <%--  <c:if test="${not empty pbcFeedBack.xml}">
						   	<c:if test="${pbcFeedBack.xml == 'ABNORMALACCOUNTS'}"><a href="${ctx}/pbc/pbcPaymentAccount/view?no=true&feedBackId=${pbcFeedBack.feedBackId}">查看</a>
							| <a href="${ctx}/pbc/pbcPaymentAccount/view?feedBackId=${pbcFeedBack.feedBackId}">审核</a></c:if>
							<c:if test="${pbcFeedBack.xml == 'ACCOUNTSINVOLVED'}"><a href="${ctx}/pbc/pbcPaymentAccountTwo/view/two?no=true&feedBackId=${pbcFeedBack.feedBackId}">查看</a>
								| <a href="${ctx}/pbc/pbcPaymentAccountTwo/view/two?feedBackId=${pbcFeedBack.feedBackId}">审核</a></c:if>
							<c:if test="${pbcFeedBack.xml == 'EXCEPTIONEVENTS'}"><a href="${ctx}/pbc/pbcPaymentAccountThree/view/three?no=true&feedBackId=${pbcFeedBack.feedBackId}">查看</a>
								| <a href="${ctx}/pbc/pbcPaymentAccountThree/view/three?feedBackId=${pbcFeedBack.feedBackId}">审核</a></c:if>
							<c:if test="${pbcFeedBack.xml == 'SEXCEPTIONEVENT'}"><a href="${ctx}/pbc/pbcPaymentAccountFour/view/four?no=true&feedBackId=${pbcFeedBack.feedBackId}">查看</a>
								| <a href="${ctx}/pbc/pbcPaymentAccountFour/view/four?feedBackId=${pbcFeedBack.feedBackId}">审核</a></c:if>
					   </c:if>
					   <c:if test="${not empty pbcFeedBack.yes}">
					   		<c:if test="${pbcFeedBack.xml == 'ABNORMALACCOUNTS'}"><a href="${ctx}/pbc/pbcPaymentAccount/view?no=true&feedBackId=${pbcFeedBack.feedBackId}">查看</a></c:if>
							<c:if test="${pbcFeedBack.xml == 'ACCOUNTSINVOLVED'}"><a href="${ctx}/pbc/pbcPaymentAccountTwo/view/two?no=true&feedBackId=${pbcFeedBack.feedBackId}">查看</a></c:if>
							<c:if test="${pbcFeedBack.xml == 'EXCEPTIONEVENTS'}"><a href="${ctx}/pbc/pbcPaymentAccountThree/view/three?no=true&feedBackId=${pbcFeedBack.feedBackId}">查看</a></c:if>
							<c:if test="${pbcFeedBack.xml == 'SEXCEPTIONEVENT'}"><a href="${ctx}/pbc/pbcPaymentAccountFour/view/four?no=true&feedBackId=${pbcFeedBack.feedBackId}">查看</a></c:if>
					   </c:if> --%>
					</td> 
				</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>