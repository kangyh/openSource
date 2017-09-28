<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>商户台账信息</title>
<meta name="decorator" content="default"/>
<script language="JavaScript" src="${ctxStatic}/jquery.base64.js"></script>
<script type="text/javascript">
	
	$(document).ready(function() {
		
		$("#inputFormFrom").validate({
			submitHandler: function(form){
				//加密组编码
				baseValue('groupCode');
				
				var groupName = $("#groupName").val();
				var pattern = new RegExp("[`~!#$^@&*()=|{}':;',\\[\\]<>/?~！#￥……&*（）——|{}【】‘；：”“'。，、？]") 
				if(pattern.test(groupName)){
					$("#noteId").text("名称输入不合法，请重新输入");
					return false;
				}
				loading('正在提交，请稍等...');
				form.submit();
			},
		});
	});
	
	//验重
	function changValue(){
		
		var groupCode=$('#groupCode').val();
		if(groupCode != ''){
			var str=encodeBase64(groupCode);
			
			$.post("${ctx}/monitors/infoGroup/changValue",{
				groupCode:str
			},function(data){
				 if(404 != data){
	                	$("#groupCodeId").text("组编码已存在请重新输入");
	                	$("#btnSubmit").attr("disabled","disabled");
	                }else{
	                	$("#groupCodeId").text("");
	                	$("#btnSubmit").removeAttr("disabled");
	                }
			},"text");
			
		}
	}
	
	//加密操作
	function baseValue(id){
		var value=$('#'+id).val();
		
		if(value != ''){
			var str=encodeBase64(value);
			//var str = $.base64.encode(value);
			$('#'+id).val(str);
		}
	}
	
	//对中文进行转码
	function encodeBase64(mingwen,times){    
	    var code="";    
	    var num=1;    
	    if(typeof times=='undefined'||times==null||times==""){    
	       num=1;    
	    }else{    
	       var vt=times+"";    
	       num=parseInt(vt);    
	    }    
	    if(typeof mingwen=='undefined'||mingwen==null||mingwen==""){    
	    }else{    
	        $.base64.utf8encode = true;    
	        code=mingwen;    
	        for(var i=0;i<num;i++){    
	           code=$.base64.btoa(code);    
	        }    
	    }    
	    return code;    
	};
	
	function goHome(){
		  window.location.href="${ctx}/monitors/infoGroup";
	  }
	
	
</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li class="active"><a>${empty infoGroup.groupId?'组信息添加':'组信息修改'}</a></li>
</ul><br/>
<form:form id="inputFormFrom" modelAttribute="infoGroup" action="${ctx}/monitors/infoGroup/saveEntity" method="post" enctype="multipart/form-data" class="form-horizontal">
	<sys:message content="${message}"/>
	<div class="control-group">
		<label class="control-label">组编码：</label>
		<div class="controls">
			<form:input path="groupCode" id="groupCode"  value="${infoGroup.groupCode}" htmlEscape="false" onchange="changValue()" maxlength="20" placeholder="请输入组编码,不超过20个数" style="width:256px;" class="required"/>
			<span class="help-inline" id="groupCodeId" style="color: red"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">组名称：</label>
		<div class="controls">
			<form:input path="groupName" id="groupName"  value="${infoGroup.groupName}" htmlEscape="false" maxlength="10" placeholder="请输入组名称,不超过10个数" style="width:256px;" class="required"/>
			<span class="help-inline" id="noteId" style="color: red"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">状态：</label>
		 <div class="controls">
				<form:select path="status" id="status"  class="input-xlarge required" value="${infoGroup.status}">
					<c:forEach items="${status}" var="statusValue">
						<form:option value="${statusValue.value}" label="${statusValue.name}"/>
					</c:forEach>
			</form:select> 
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div> 
	<div class="control-group">
		<label class="control-label">发送类型：</label>
		<div class="controls">
			<form:select path="remark" id="remark"  class="input-xlarge required" value="${infoGroup.remark}">
					<c:forEach items="${sendType}" var="type">
						<form:option value="${type.value}" label="${type.name}"/>
					</c:forEach>
			</form:select> 
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
		
	<input id="groupId" value="${infoGroup.groupId}" type="hidden" name="groupId"/>
	<div class="form-actions">
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="提交申请"/>
		<input id="btnCancel" class="btn" type="button" value="返回" onclick="goHome()" style="margin-left:50px"/>
	</div>
</form:form>
</body>
</html>