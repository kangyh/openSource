<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>监控告警</title>
<meta name="decorator" content="default"/>
<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
<script type="text/javascript">
	//验证搜索条件
	var validateFrom = {
		validate: function(form){
			
			var groupId = $("#groupId").val();
			if(isNaN(groupId)){
				$("#messageBox").text("请输入数字！");
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
		$("#channelName").val(field);
	}
	
	function toMsg(id){
		loading('正在发送，请稍等...');
		window.location.href = "${ctx}/monitors/infoMsg/sendMsg/"+id;
	}
	
	
	  function display(msgId){
		  closeModal('fenpeiModal');
		   $.post("${ctx}/monitors/infoMsg/displayBody/"+msgId,
		   function(data){
			   $("#showValue").text(data);
			   /* $("#showValue").text(data);
			   $("#showValue").attr("style","display: block;"); */
		   
		   }, "html");
	  }
	  function disappear(){
		 /*  $("#showValue").attr("style","display: none;"); */
	  } 
	  
	  function closeModal(modalID){
		  $('#fenpeiModal').modal('show');
		  $(".pop").show();
	    	/* $('#'+modalID).modal('hide'); */
	  }
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/monitors/infoMsg">消息管理列表</a></li>
	</ul>
	<form action="${ctx}/monitors/infoMsg" method="post" id="formBtn"></form> 
	
	<form:form id="searchForm" modelAttribute="infoMsg" action="${ctx}/monitors/infoMsg" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			
		<ul class="ul-form">
			<li><label>是否有附件：</label>
				<form:select id="isAdd" path="isAdd" class="input-xlarge" style="width:150px;" >
					<form:option value="" label="-选择附件-" />
						<c:forEach items="${isAdds}" var="isAdd">
							<form:option value="${isAdd.value}" label="${isAdd.name}"/>
						</c:forEach>
				</form:select> 
			</li>
			<li><label>发送状态：</label>
				<form:select id="sendStatus" path="sendStatus" class="input-xlarge" style="width:150px;">
					<form:option value="" label="-选择发送状态-"/>
						<c:forEach items="${sendStatus}" var="send">
							<form:option value="${send.value}" label="${send.name}"/>
						</c:forEach>
				</form:select> 
			</li>
			<li><label>组查询：</label>
				<form:select id="groupId" path="groupId" class="input-xlarge" style="width:150px;">
					<form:option value="" label="-选择组查询-"/>
						<c:forEach items="${groupValue}" var="group">
							<form:option value="${group.value}" label="${group.name}"/>
						</c:forEach>
				</form:select> 
			</li>
			
			<%-- <li><label>组ID：</label>
				<form:input path="groupId" id="groupId"  htmlEscape="false" maxlength="20"  class="input-medium required" placeholder="请输入组ID"/>
			</li> --%>
			
			<li><label>创建时间：</label>
				<input id="beginOperEndTime" name="beginOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${infoMsg.beginOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endOperEndTime" name="endOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${infoMsg.endOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
	     <li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
	     <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
		</ul>  
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>组管理名称</th>
			<th>类型</th>
			<th>消息发送者</th>
			<th>消息头</th>
			<th>消息体</th>
			<th>是否有附件</th>
			
			<th>创建时间</th>
			<th>发送时间</th>
			<th>发送状态</th>
			<th>备注</th>
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="infoMsg" varStatus="i">
				<tr>
					<td>${infoMsg.groupName}</td>
					<td>${infoMsg.type}</td>
					<td>${infoMsg.senderUser}</td>
					<td>${infoMsg.msgHead}</td>
					<td><a onclick="display(${infoMsg.msgId})" style="cursor: pointer;">点击查看消息体</a></td>
					<td>${infoMsg.isAdd}</td>
					<td><fmt:formatDate value="${infoMsg.createTime}" type="both" pattern="yyyy-MM-dd HH:MM:ss"/></td>
					<td><fmt:formatDate value="${infoMsg.sendTime}" type="both" pattern="yyyy-MM-dd HH:MM:ss"/></td>
					<td <c:choose>
			     		<c:when test="${infoMsg.sendStatus=='发送成功'}">
			     		 style="background:#6cf683"
			     		</c:when>
			     		<c:when test="${infoMsg.sendStatus=='发送失败'}">
			     		 style="background:red"
			     		</c:when>
			     		<c:when test="${infoMsg.sendStatus=='未发送'}">
			     		 style="background:#707c9b"
			     		</c:when>
			     		<c:when test="${infoMsg.sendStatus=='发送中'}">
			     		 style="background:#3399CC"
			     		</c:when>
			     	</c:choose>>${infoMsg.sendStatus}</td>
					<td>${infoMsg.remark}</td>
					<td>
						<c:if test="${infoMsg.sendStatus=='发送失败'}">
						  <a onclick="toMsg(${infoMsg.msgId})" style="cursor: pointer;">重发</a>
						</c:if>
						<c:if test="${infoMsg.sendStatus != '发送失败'}">
						  --
						</c:if>
				   </td>
				</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	<!-- 模态框（Modal）  -->
	<div class="modal fade" id="fenpeiModal" tabindex="-1" role="dialog" 
	  style="width:50%;display:none;" aria-labelledby="myModalLabel" aria-hidden="true">
	   <div class="modal-dialog">
	      <div class="modal-content">
	         <div class="modal-header">
	            <button type="button" class="close" 
	               data-dismiss="modal" aria-hidden="true" onclick="closeModal('fenpeiModal')">
	                  &times;
	            </button>
	            <h4 class="modal-title"  id="titleName">消息体详细信息</h4>
	         </div>
	         <div class="pop">
	         	<div style="height:300px;">
		         <table class="table table-striped table-bordered table-condensed">
			         <thead>
			         	<tr>
			         		<th>消息体</th>
			         	</tr>
			        
			         </thead>
		         </table>
		         <div id="showValue" style="overflow-y:auto;height:280px;"></div>
		         </div>
			</div>
	         <div class="modal-footer">
	            <button type="button" class="btn btn-default" 
	               data-dismiss="modal" onclick="closeModal('fenpeiModal')">取消
	            </button>
	         </div>
	      </div>
		</div>
	</div>
	
</body>
</html>