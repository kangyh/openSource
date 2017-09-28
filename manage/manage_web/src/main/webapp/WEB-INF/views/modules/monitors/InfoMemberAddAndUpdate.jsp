<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>商户台账信息</title>
<meta name="decorator" content="default"/>
	
<script type="text/javascript">
	$(document).ready(function() {
		$("#inputFormFrom").validate({
			submitHandler: function(form){
				
				var memberName = $("#memberName").val();
				var pattern = new RegExp("[`~!#$^@&*()=|{}':;',\\[\\]<>/?~！#￥……&*（）——|{}【】‘；：”“'。，、？]") 
				if(pattern.test(memberName)){
					$("#memberNameId").text("名称输入不合法，请重新输入");
					return false;
				}else{
					$("#memberNameId").text("");
				}
				
				var remark = $("#remark").val();
				if(pattern.test(remark)){
					$("#remarkId").text("备注输入有特殊字符，请重新输入");
					return false;
				}else{
					$("#remarkId").text("");
				}
				
				var emailValue=/^\w+([-+.]\w+)*@\w+([-.]\\w+)*\.\w+([-.]\w+)*$/;
				var email = $("#email").val();
				if(email != ''){
					if(!emailValue.test(email)){
						$("#emailId").text("邮箱输入不合法，请重新输入");
						return false;
					}else{
						$("#emailId").text("");
					}
					
				}
				/*var mobile = $("#mobile").val();
				if(isNaN(mobile)){

						$("#mobileId").text("手机号码输入不合法，请重新输入");
						return false;
					}else{
						$("#mobileId").text("");
				}*/
				loading('正在提交，请稍等...');
				form.submit();
			},
		});
	});
	
  function goHome(){
	  window.location.href="${ctx}/monitors/infoMember";
  }
  
  function ChangeOption(value){
		
	 if(value !=''){
	  $.post("${ctx}/monitors/infoMember/groupId",{
		  groupId:value
		},function(data){
			if(data != 404){
         	   //短信必填
         	   if(data == 'sms'){
         		  $("#mobile").attr("class","required");
         		  
         		   removeDiv('email','wechat','mobile');
         		   
         		   removeClass('email','wechat');
         		   hiddenDiv('email','wechat');
         		   
         	   }
         	   //邮箱必填
         	   if(data == 'email'){
         		   $("#email").attr("class","required");

         		   removeDiv('email','wechat','mobile');
         		   
         		   removeClass('mobile','wechat');
         		   hiddenDiv('mobile','wechat');
         	   }
         	   //微信必填
         	   if(data == 'wechat'){
         		   $("#wechat").attr("class","required");

         		   removeDiv('email','wechat','mobile');
         		   
         		   removeClass('mobile','email');
         		   hiddenDiv('mobile','email');
         	   }
         	   //所有的都填
         	   if(data == 'all'){
         		   $("#mobile").attr("class","required");
         		   $("#email").attr("class","required");
         		   $("#wechat").attr("class","required");
         		   removeDiv('email','wechat','mobile');
         		  
         	   }
            }else{
         	$("#groupIdId").text("查询异常,返回后再试！");
            	$("#btnSubmit").attr("disabled","disabled");
            }
		},"text");
	  }
  }
  
  function removeClass(id,value){
	  $("#"+id).removeAttr("class");
	  $("#"+value).removeAttr("class");
  }

  function hiddenDiv(id,value){
	  $("#"+id+"Div").attr("style","display:none;");
	  $("#"+value+"Div").attr("style","display:none;");
  }
  
  function removeDiv(id,value,index){
	  $("#"+id+"Div").removeAttr("style");
	  $("#"+value+"Div").removeAttr("style");
	  $("#"+index+"Div").removeAttr("style");
  }
</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li class="active"><a>${empty infoMember.memberId?'组成员添加':'组成员修改'}</a></li>
</ul><br/>
<form:form id="inputFormFrom" modelAttribute="infoMember" action="${ctx}/monitors/infoMember/saveEntity" method="post" enctype="multipart/form-data" class="form-horizontal">
	<sys:message content="${message}"/>
	<div class="control-group">
		<label class="control-label">成员名称：</label>
		<div class="controls">
			<form:input path="memberName" id="memberName"  value="${infoMember.memberName}" htmlEscape="false" maxlength="20" placeholder="请输入组编码,不超过20个数" style="width:256px;" class="required"/>
			<span class="help-inline" id="memberNameId" style="color: red"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">组ID：</label>
		 <div class="controls">
				<form:select path="groupId" id="groupId"  class="input-xlarge required" value="${infoMember.groupId}" onchange="ChangeOption(this.options[this.options.selectedIndex].value);">
					<form:option value="" label="-选择组ID-"/>
					<c:forEach items="${infoGroupList}" var="infoGroup">
						<form:option value="${infoGroup.value}" label="${infoGroup.name}"/>
					</c:forEach>
			</form:select> 
			<span class="help-inline" id="groupIdId"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group" id="emailDiv" >
		<label class="control-label">邮箱：</label>
		<div class="controls">
			<form:input path="email" id="email"  value="${infoMember.email}" htmlEscape="false" maxlength="30" placeholder="请输入邮箱,不超过30个数" style="width:256px;"/>
			<span class="help-inline" id="emailId" style="color: red"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group" id="mobileDiv">
		<label class="control-label">手机号码：</label>
		<div class="controls">
			<form:input path="mobile" id="mobile"  value="${infoMember.mobile}" htmlEscape="false" maxlength="11" placeholder="请输入手机号码" style="width:256px;" />
			<span class="help-inline" id="mobileId" style="color: red"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group" id="wechatDiv">
		<label class="control-label">微信号：</label>
		<div class="controls">
			<form:input path="wechat" id="wechat"  value="${infoMember.wechat}" htmlEscape="false" maxlength="20" placeholder="请输入微信号,不超过20个数" style="width:256px;"/>
			<span class="help-inline" id="wechatId" style="color: red"><font color="red">*</font> </span>
		</div>
	</div>
	
	<div class="control-group">
		<label class="control-label">备注：</label>
		<div class="controls">
			<form:textarea path="remark" id="remark"  value="${infoMember.remark}" htmlEscape="false" maxlength="99" placeholder="请输入备注信息,不超过99个数" style="width:256px;"/>
			<span class="help-inline" id="remarkId" style="color: red"><font color="red"></font> </span>
		</div>
	</div>
		
	<input id="memberId" value="${infoMember.memberId}" type="hidden" name="memberId"/>
	<div class="form-actions">
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="提交申请"/>
		<input id="btnCancel" class="btn" type="button" value="返回" onclick="goHome()" style="margin-left:50px"/>
	</div>
</form:form>
</body>
</html>