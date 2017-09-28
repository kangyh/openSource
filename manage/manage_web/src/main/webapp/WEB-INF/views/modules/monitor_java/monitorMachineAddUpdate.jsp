<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>服务器日志管理</title>
<meta name="decorator" content="default"/>
<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
<style>
    #main {
        margin: 50px;
    }
</style>
<script type="text/javascript">
$(document).ready(function() {
	$("#inputForm").validate({
	submitHandler: function(form){
			
			var isDbv=document.getElementsByName('isDb');
			var status=document.getElementsByName('status');
			var aa = checkValue(isDbv,'isDbId');
			if(aa=='0'){
				return false;
			}
			var bb = checkValue(status,'statusId');
			if(bb=='0'){
				return false;
			}
			
			//检查主机编号是否符合规定
			var machineCode=$('#machineCode').val();
			var cc=specialValue(machineCode,'machineCodeId');
			if(cc=='0'){
				return false;
			}
			
			var machineNameID=$('#machineNameID').val();
			var dd=specialValue(machineNameID,'machineNameIdID');
			if(dd=='0'){
				return false;
			}
			
			var userName=$('#userName').val();
			var i=specialValue(userName,'userNameId');
			if(i=='0'){
				return false;
			}
			
			var password=$('#password').val();
			var j=specialValue(password,'passwordId');
			if(j=='0'){
				return false;
			}
			
			var folder=$('#folder').val();
			var k=specialValue(folder,'folderId');
			if(k=='0'){
				return false;
			}
			
			var suffix=$('#suffix').val();
			var l=specialValue(suffix,'suffixId');
			if(l=='0'){
				return false;
			}
			
			var te= /^[1-9]+[0-9]*]*$/;
			var port=$('#port').val();
			if (!te.test(port)){
				$('#portId').text("端口号请输入数字，请重新输入");
				return false;
			}else{
				$('#portId').text("");
			}
			
			//验证ip地址
			var innerIp=$('#innerIp').val();
			var ee=specialValue(innerIp,'innerIpId');
			if(ee=='0'){
				return false;
			}else{
				var ip=testIP(innerIp,'innerIpId');
				if(ip=='0'){
					return false;
				}
			}
			
			var outerIp=$('#outerIp').val();
			var gg=specialValue(outerIp,'outerIpId');
			if(gg=='0'){
				return false;
			}else{
				var ip=testIP(outerIp,'outerIpId');
				if(ip=='0'){
					return false;
				}
			}
			
			var accessIp=$('#accessIp').val();
			var hh=specialValue(accessIp,'accessIpId');
			if(hh=='0'){
				return false;
			}else{
				var ip=testIP(accessIp,'accessIpId');
				if(ip=='0'){
					return false;
				}
			}
			
			loading('正在提交，请稍等...');
			form.submit();
			
		},
		errorContainer: "#messageBox",
		errorPlacement: function(error, element) {
			$("#messageBox").text("输入有误，请先更正。");
			if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
				error.appendTo(element.parent().parent());
			} else {
				error.insertAfter(element);
			}
		}
	});
});



 //验证是否输入正确
 function checkUpload(){
	//检查主机编号是否符合规定
		var machineCode=$('#machineCode').val();
		var cc=specialValue(machineCode,'machineCodeId');
		if(cc=='0'){
			return false;
		}
		
		var machineNameID=$('#machineNameID').val();
		var dd=specialValue(machineNameID,'machineNameIdID');
		if(dd=='0'){
			return false;
		}
		
		var userName=$('#userName').val();
		var i=specialValue(userName,'userNameId');
		if(i=='0'){
			return false;
		}
		
		var password=$('#password').val();
		var j=specialValue(password,'passwordId');
		if(j=='0'){
			return false;
		}
		
		var folder=$('#folder').val();
		var k=specialValue(folder,'folderId');
		if(k=='0'){
			return false;
		}
		
		var suffix=$('#suffix').val();
		var l=specialValue(suffix,'suffixId');
		if(l=='0'){
			return false;
		}
		
		var te= /^[1-9]+[0-9]*]*$/;
		var port=$('#port').val();
		if (!te.test(port)){
			$('#portId').text("端口号请输入数字，请重新输入");
			return false;
		}else{
			$('#portId').text("");
		}
		
		//验证ip地址
		var innerIp=$('#innerIp').val();
		var ee=specialValue(innerIp,'innerIpId');
		if(ee=='0'){
			return false;
		}else{
			var ip=testIP(innerIp,'innerIpId');
			if(ip=='0'){
				return false;
			}
		}
		
		
		var outerIp=$('#outerIp').val();
		var gg=specialValue(outerIp,'outerIpId');
		if(gg=='0'){
			return false;
		}else{
			var ip=testIP(outerIp,'outerIpId');
			if(ip=='0'){
				return false;
			}
		}
		
		var accessIp=$('#accessIp').val();
		var hh=specialValue(accessIp,'accessIpId');
		if(hh=='0'){
			return false;
		}else{
			var ip=testIP(accessIp,'accessIpId');
			if(ip=='0'){
				return false;
			}
		}
		
		$.post("${ctx}/modules/monitorMachine/load",
				{machineCode:machineCode,machineName:machineNameID,userName:userName,password:password,folder:folder,
				suffix:suffix,port:port,innerIp:innerIp,outerIp:outerIp,accessIp:accessIp},
			function(msg){
				if(msg=='ok'){
					parent.showThree();
					parent.changeThreeName("验证通过");
					//$("#loadId").text("验证通过");
				}else{
					parent.showThree();
					parent.changeThreeName(msg);
				}
			},"text");
		
		/* $.ajax({
			url:"${ctx}/modules/monitorMachine/load",
			type:"get",
			cache:false,
			data:{
				machineCode:machineCode,machineName:machineNameID,userName:userName,password:password,folder:folder,
				suffix:suffix,port:port,innerIp:innerIp,outerIp:outerIp,accessIp:accessIp
			},
			success:function(msg){
				if(msg == 'ok'){
					parent.showThree();
					parent.changeThreeName("验证通过");
				}else{
					parent.showThree();
					parent.changeThreeName(msg);
				}
			}
		}); */
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
 
 //判断单选按钮是否被选中
  function checkValue(v,name){
	  if(v.length !=0){
		var j=0
		for (var i=0;i<v.length;i++){
		 if(v.item(i).checked){
		   j++
		 }
		}
		if(j<1){
			$("#"+name+"").text("请选择审核状态");
			return "0";
		}else{
			$("#"+name+"").text("");
			return "1";
		}
	}
  }
</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li class="active"><a>主机信息管理${not empty monitorMachine.machineId?'修改':'添加' }</a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="monitorMachine" action="${ctx}/modules/monitorMachine/saveEntity" method="post" class="form-horizontal">

	<c:if test="${not empty monitorMachine.machineId}">
		<div class="control-group">
			<label class="control-label">主机编号：</label>
			<div class="controls">
				<input value="${monitorMachine.machineId}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
			</div>
		</div>
	</c:if>

	<div class="control-group">
		<label class="control-label">主机名称：</label>
		<div class="controls">
			<form:input path="machineName" id="machineNameID"  value="${monitorMachine.machineName}" htmlEscape="false"  maxlength="30" class="required" placeholder="请输入主机名称" style="width:200px;"/>
			<span class="help-inline"><font color="red">*</font> </span>
			<label id="machineNameIdID" for="typeId" class="info"></label>
		</div>
	</div>
	
	<%-- <c:if test="${not empty monitorMachine.machineId}">
		<div class="control-group">
			<label class="control-label">是否DB：</label>
			<div class="controls">
				<form:radiobutton path="isDb" id="isDb2" value="0"/><label for="isDb2">否</label>
				<form:radiobutton path="isDb" id="isDb1" value="1" /><label for="isDb1">是</label>
				<span class="help-inline"><font color="red">*</font> </span>
				<label id="isDbId" for="typeId" class="info"></label>
			</div>
		</div>
	</c:if> --%>
	
	<div class="control-group">
		<label class="control-label">主机访问状态：</label>
		<div class="controls">
			<form:radiobutton path="status" id="status2" value="0"/><label for="status2">否</label>
			<form:radiobutton path="status" id="status1" value="1" /><label for="status1">是</label>
			<span class="help-inline"><font color="red">*</font> </span>
			<label id="statusId" for="typeId" class="info"></label>
		</div>
	</div>
	
	<div class="control-group">
		<label class="control-label">组类型编号：</label>
		 <div class="controls">
				<form:select path="machineGroupId" id="machineGroupId"  class="input-xlarge required" style="width:212px;">
					<c:forEach items="${machineName}" var="listValue">
						<form:option value="${listValue.value}" label="${listValue.name}"/>
					</c:forEach>
			</form:select> 
		</div>
	</div> 
	<div class="control-group">
		<label class="control-label">内网IP：</label>
		<div class="controls">
			<form:input path="innerIp" id="innerIp"  value="${monitorMachine.innerIp}" htmlEscape="false"  maxlength="20" class="required" placeholder="请输入内网IP" style="width:200px;"/>
			<span class="help-inline"><font color="red">*</font> </span>
			<label id="innerIpId" for="typeId" class="info"></label>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">外网IP：</label>
		<div class="controls">
			<form:input path="outerIp" id="outerIp"  value="${monitorMachine.outerIp}" htmlEscape="false"  maxlength="20" class="required" placeholder="请输入外网IP" style="width:200px;"/>
			<span class="help-inline"><font color="red">*</font> </span>
			<label id="outerIpId" for="typeId" class="info"></label>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">受访IP：</label>
		<div class="controls">
			<form:input path="accessIp" id="accessIp"  value="${monitorMachine.accessIp}" htmlEscape="false"  maxlength="20" class="required" placeholder="请输入受访IP" style="width:200px;"/>
			<span class="help-inline"><font color="red">*</font> </span>
			<label id="accessIpId" for="typeId" class="info"></label>
		</div>
	</div>
	
	<div class="control-group">
		<label class="control-label">受访主机登录名：</label>
		<div class="controls">
			<form:input path="userName" id="userName"  value="${monitorMachine.userName}" htmlEscape="false"  maxlength="30" class="required" placeholder="请输入受访主机登录名" style="width:200px;"/>
			<span class="help-inline"><font color="red">*</font> </span>
			<label id="userNameId" for="typeId" class="info"></label>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">受访主机密码：</label>
		<div class="controls">
			<form:input path="password" type="password" id="password"  value="${monitorMachine.password}" maxlength="50" class="required" placeholder="请输入受访主机密码" style="width:200px;"/>
			<span class="help-inline"><font color="red">*</font> </span>
			<label id="passwordId" for="typeId" class="info"></label>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">端口号：</label>
		<div class="controls">
			<form:input path="port" id="port"  value="${monitorMachine.port}" maxlength="10" class="required" placeholder="请输入端口号" style="width:200px;"/>
			<span class="help-inline"><font color="red">*</font> </span>
			<label id="portId" for="typeId" class="info"></label>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">日志全路径名称：</label>
		<div class="controls">
			<form:input path="folder" id="folder"  value="${monitorMachine.folder}" maxlength="100" class="required" placeholder="请输入日志全路径名称" style="width:200px;"/>
			<span class="help-inline"><font color="red">* 例如: /home/risk/app/risk_server/logs</font> </span>
			<label id="folderId" for="typeId" class="info"></label>
		</div>
	</div>
	<%-- <div class="control-group">
		<label class="control-label">日志后缀名：</label>
		<div class="controls">
			<form:input path="suffix" id="suffix"  value="${monitorMachine.suffix}" maxlength="10" class="required" placeholder="请输入日志后缀名" style="width:200px;"/>
			<span class="help-inline"><font color="red">*</font> </span>
			<label id="suffixId" for="typeId" class="info"></label>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">所属系统名称：</label>
		<div class="controls">
			<form:input path="systemOf" id="systemOf"  value="${monitorMachine.systemOf}" maxlength="10" class="required" placeholder="请输入所属系统名称" style="width:200px;"/>
			<span class="help-inline"><font color="red">*</font> </span>
			<label id="systemOfId" for="typeId" class="info"></label>
		</div>
	</div> --%>
	
	
	<div class="form-actions">
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="提交申请"/>
		<input id="btnCancel" class="btn btn-primary" type="button" value="返回" onclick="history.go(-1)" style="margin-left:50px"/>
		<input class="btn btn-primary" type="button" value="验证" onclick="checkUpload()" style="margin-left: 50px"/>
		<label id="loadId" for="typeId" class="info"></label>
		
		<input type="hidden" name="machineId" value="${monitorMachine.machineId}">
	</div>
</form:form>
</body>
</html>