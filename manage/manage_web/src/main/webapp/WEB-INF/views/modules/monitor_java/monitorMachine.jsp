<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>服务器日志管理</title>
<meta name="decorator" content="default"/>
<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
<script type="text/javascript">
	
	$(document).ready(function() {
	});
	//验证搜索条件
	var validateFrom = {
		validate: function(form){
			
			var outerIp = $("#outerIp").val();
			//验证ip地址
			var a=specialValue(outerIp,'messageBox');
			if(a=='0'){
				return false;
			}else{
				var ip=testIP(outerIp,'messageBox');
				if(ip=='0'){
					return false;
				}
			}
			
			
			var accessIp = $("#accessIp").val();
			//验证ip地址
			var b=specialValue(accessIp,'messageBox');
			if(b=='0'){
				return false;
			}else{
				var ip=testIP(accessIp,'messageBox');
				if(ip=='0'){
					return false;
				}
			}
			
			
			var folder = $("#folder").val();
			//验证ip地址
			var c=specialValue(folder,'messageBox');
			if(c=='0'){
				return false;
			}
			
			/* var bgTime = $("#beginCreateTime").val();
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
				}
			} */
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
	
	//判断是否输入了非法字符
	 function specialValue(name,nameId){
	 	var pattern = new RegExp("[`~!#$^@&*()=|{}':;',\\[\\]<>?~！#￥……&*（）——|{}【】‘；：”“'。，、？]") 
		if(pattern.test(name)){
			$('#'+nameId+'').text("不允许输入特殊字符，请重新输入");
			return "0";
		}else{
			$('#'+nameId+'').text("");
			return "1";
		}
	 } 
	
	//验证ip地址
 	function testIP(s,nameId){
		
	if(s==""){
		 return "1";
	}else{
		  var arr=s.match(/^(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})$/);
		  if(arr==null){
			  $('#'+nameId+'').text("ip地址输入错误,请重新输入");
			  return "0";
		  }
		  for(i=1;i<arr.length;i++){
			  if(String(Number(arr[i]))!=arr[i]||Number(arr[i])>255){
				  $('#'+nameId+'').text("ip地址输入错误,请重新输入");
				  return "0";
			  }else{
				  $('#'+nameId+'').text("");
				  return "1";
			  }
		  }
		}
	}
</script>
</head>
<body>
	
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/modules/monitorMachine/">主机信息管理列表</a></li>
		<c:if test="${not empty list}">
			<shiro:hasPermission name="modules:monitorMachine:edit"><li><a style="cursor:pointer;" class="checkPass"  href="${ctx}/modules/monitorMachine/update">主机信息管理添加</a></li></shiro:hasPermission>
		</c:if>
	</ul>
	<form action="${ctx}/modules/monitorMachine" method="post" id="formBtn"></form>
	<form:form id="searchForm" modelAttribute="monitorMachine" action="${ctx}/modules/monitorMachine" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>外网IP：</label>
				<form:input path="outerIp" id="outerIp"  htmlEscape="false" maxlength="20" class="input-medium" placeholder="请输入外网IP地址" style="width:210px;"/>
			</li> 
			<li><label>受访IP：</label>
				<form:input path="accessIp" id="accessIp" htmlEscape="false" maxlength="20" class="input-medium" placeholder="请输入受访IP地址" style="width:210px;"/>
			</li> 
			<li><label>日志全路径：</label>
				<form:input path="folder" id="folder" htmlEscape="false" maxlength="100" class="input-medium" placeholder="请输入日志全路径" style="width:210px;"/>
			</li> 
			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="clearfix"></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>主机编号</th>
			<th>主机名称</th>
			<th>是否DB</th>
			<th>组类型编号</th>
			<th>内网IP</th>
			<th>外网IP</th>
			<th>受访IP</th>
			<!-- <th>连接方式</th> -->
			<th>受访主机登录名</th>
			<!-- <th>受访主机密码</th> -->
			<th>端口号</th>
			<th>日志全路径名称</th>
			<!-- <th>日志后缀名</th>
			<th>所属系统名称</th> -->
			<th>创建人</th>
			<th>创建时间</th>
			<th>更新人</th>
			<th>更新时间</th>
			<th>主机访问状态</th>
			<shiro:hasPermission name="modules:monitorMachine:edit"><th>操作</th></shiro:hasPermission>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="monitorMachine">
			<tr>
				<td>${monitorMachine.machineId}</td>
				<td>${monitorMachine.machineName}</td>
				<td>${monitorMachine.view}</td>
				<td>${monitorMachine.name}</td>
				<td>${monitorMachine.innerIp}</td>
				<td>${monitorMachine.outerIp}</td>
				<td>${monitorMachine.accessIp}</td>
				<%-- <td>${monitorMachine.accessType}</td> --%>
				<td>${monitorMachine.userName}</td>
				<%-- <td>${monitorMachine.password}</td> --%>
				<td>${monitorMachine.port}</td>
				<td>${monitorMachine.folder}</td>
				<%-- <td>${monitorMachine.suffix}</td>
				<td>${monitorMachine.systemOf}</td> --%>
				
				<td>${monitorMachine.createAuthor}</td>
				<td><fmt:formatDate value="${monitorMachine.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${monitorMachine.updateAuthor}</td>
				<td><fmt:formatDate value="${monitorMachine.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td <c:choose>
			     		<c:when test="${monitorMachine.value=='正常'}">
			     		 style="background:#6cf683" 	
			     		</c:when>
			     		<c:when test="${monitorMachine.value=='访问异常'}">
			     		 style="background:#707c9b" 
			     		</c:when>
			     	</c:choose>>${monitorMachine.value}</td>
				<shiro:hasPermission name="modules:monitorMachine:edit"><td><a href="${ctx}/modules/monitorMachine/delete/${monitorMachine.machineId}">删除</a>
					| <a href="${ctx}/modules/monitorMachine/update?groupId=${monitorMachine.machineId}">修改</a>
				</td></shiro:hasPermission>
			</tr> 
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>