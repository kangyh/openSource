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
			var emailValue=/^\w+([-+.]\w+)*@\w+([-.]\\w+)*\.\w+([-.]\w+)*$/;
			var email = $("#email").val();
			if(email != ''){
				if(!emailValue.test(email)){
					$("#messageBox").text("邮箱输入不合法，请重新输入");
					return false;
				}
			}
			
			var phone=/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
			var mobile = $("#mobile").val();
			if(mobile != ''){
				if(!phone.test(mobile)){
					$("#messageBox").text("手机号码输入不合法，请重新输入");
					return false;
				}
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
	
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/monitors/infoMember">成员管理列表</a></li>
		<shiro:hasPermission name="monitors:infoMember:edit">
		<li><a href="${ctx}/monitors/infoMember/addAndUpdate">添加成员信息</a></li></shiro:hasPermission>
	</ul>
	<form action="${ctx}/monitors/infoMember" method="post" id="formBtn"></form> 
	
	<form:form id="searchForm" modelAttribute="infoMember" action="${ctx}/monitors/infoMember" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			
			<li><label>组查询：</label>
				<form:select id="groupId" path="groupId" class="input-xlarge" style="width:150px;">
					<form:option value="" label="-选择组查询-"/>
						<c:forEach items="${groupValue}" var="group">
							<form:option value="${group.value}" label="${group.name}"/>
						</c:forEach>
				</form:select> 
			</li>
			<li><label>手机号码：</label>
				<form:input path="mobile" id="mobile"  htmlEscape="false" maxlength="20"  class="input-medium required" placeholder="请输入手机号码"/>
			</li>
			
			<li><label>邮箱：</label>
				<form:input path="email" id="email"  htmlEscape="false" maxlength="20"  class="input-medium required" placeholder="请输入邮箱"/>
			</li>
			<li><label>创建时间：</label>
				<input id="beginOperEndTime" name="beginOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${infoMember.beginOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endOperEndTime" name="endOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${infoMember.endOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
			<th>成员名称</th>
			<th>组管理名称</th>
			<th>邮箱</th>
			<th>手机号码</th>
			<th>微信号</th>
			<th>创建人</th>
			<th>修改人</th>
			<th>创建时间</th>
			<th>修改时间</th>
			<th>备注</th>
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="infoMember" varStatus="i">
				<tr>
					<td>${infoMember.memberName}</td>
					<td>${infoMember.groupName}</td>
					<td>${infoMember.email}</td>
					<td>${infoMember.mobile}</td>
					<td>${infoMember.wechat}</td>
				    
					<td>${infoMember.createUser}</td>
					<td>${infoMember.updateUser}</td>
					<td><fmt:formatDate value="${infoMember.createTime}" type="both" pattern="yyyy-MM-dd HH:MM:ss"/></td>
					<td><fmt:formatDate value="${infoMember.updateTime}" type="both" pattern="yyyy-MM-dd HH:MM:ss"/></td>
					<td>${infoMember.remark}</td>
					<td>
					<shiro:hasPermission name="monitors:infoMember:edit">
					  <a href="${ctx}/monitors/infoMember/delete/${infoMember.memberId}">删除</a>
					| <a href="${ctx}/monitors/infoMember/addAndUpdate?memberId=${infoMember.memberId}">更新</a>
					</shiro:hasPermission>
				   </td>
				</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>