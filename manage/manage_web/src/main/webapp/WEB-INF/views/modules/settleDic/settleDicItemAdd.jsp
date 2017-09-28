<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>字典明细添加</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var channelValue = "";//通道侧名称
		var merchantValue = "";//商户侧名称
		var flg = false; //用于判断修改时是否是结算区间
		$(document).ready(function() {
			
			if(null != $("#itemId").val() && undefined != $("#itemId").val() && "" != $("#itemId").val()){
				$("#text").attr("readOnly","true");
			}
			var checkStatus = $("#typeId").find("option:selected").text();
			if(checkStatus == "结算区间"){
				$("#type_val_info").text("区间格式：XX:XX:XX(24小时制 整点数)");
				flg = true;
			}else{
				$("#type_val_info").text("");
				flg = false;
			}
			
			$("#inputForm").validate({
				submitHandler: function(form){
					var typeId = $("#typeId").find("option:selected").val();
					if(typeId == "" || typeId == undefined || typeId == null){
						$("#type_id_info").text("请选择类型!");
						$("#type_id_info").show();
						return;
					}else{
						loading('正在提交，请稍等...');
						form.submit();
					}
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
		
		//判断是不是结算区间类型
		function checkType(){
			var typeId = $("#typeId").find("option:selected").val();
			if(typeId == "" || typeId == undefined || typeId == null){
				$("#type_id_info").text("请选择类型!");
				$("#type_id_info").show();
				return;
			}
			
			
			var checkStatus = $("#typeId").find("option:selected").text();
			if(checkStatus == "结算区间"){
				//显示结算区间明细下拉选
				$("#text_id").css("display","none");
				$("#text_select_id").css("display","block");
				
				$.post($("#url_id").attr("href")+"checkType",{
					typeId:$("#typeId").find("option:selected").val()
					},function(data){
						if(null != data){
							var msArr = data.split(",");
							if(msArr[0] == "false"){
								if(msArr[1] == "all"){
									//结算区间  存在两个明细
									if(null != $("#itemId").val() && undefined != $("#itemId").val() && "" != $("#itemId").val()){
										$("#text_select_id").css("display","none");
										$("#text_id").css("display","block");
										if(!flg){
											$("#type_id_info").text("请选择结算区间修改！");
											$("#btnSubmit").attr("disabled","disabled");
										}
									}else{
										$("#type_id_info").text("结算区间类型下存在【通道侧】,【商户侧】明细，不允许新增！");
										$("#btnSubmit").attr("disabled","disabled");
									}
									channelValue = "channel";
									merchantValue = "merchant";
								}else if(msArr[1] == "channel"){
									//结算区间   存在通道侧明细
									$("#type_id_info").text("结算区间类型下存在【通道侧】明细，不允许新增【通道侧】明细！");
									channelValue = "channel";
								}else if(msArr[1] == "merchant"){
									//结算区间 存在商户侧明细
									$("#type_id_info").text("结算区间类型下存在【商户侧】明细，不允许新增【商户侧】明细！");
									merchantValue = "merchant";
								}
							}
						}else{
							$("#btnSubmit").attr("disabled",false);
						}
					},"text"
				);
				$("#type_val_info").text("区间格式：XX:XX:XX(24小时制)");
			}else{
				$("#value_id").css("display","block");
				$("#value_select_id").css("display","none");
				$("#text_id").css("display","block");
				$("#text_select_id").css("display","none");
				
				$("#btnSubmit").attr("disabled",false);
				$("#type_id_info").text("");
				$("#type_val_info").text("");
			}
		}
		
		//控制结算区间 text
		function checkTextSelect(){
			var text = $("#text_select").find("option:selected").val();
			if(text == channelValue || text == merchantValue){
				$("#btnSubmit").attr("disabled","disabled");
			}else{
				$("#btnSubmit").attr("disabled",false);
			}
		}
		
		//验证格式  结算区间类型
		function checkStyle(obj){
			var checkStatus = $("#typeId").find("option:selected").text();
			if(checkStatus == "结算区间"){
				var reg = /\d{4}-\[01]\d-[0123]\d\s{1,2}[012]\d:[0-6]\d/;
				if(!reg.test(obj)){
					$("#type_val_info").text("区间格式：XX:XX:XX(24小时制 整点数)");
					$("#btnSubmit").attr("disabled","disabled");
				}else{
					$("#type_val_info").text("");
					$("#btnSubmit").attr("disabled",false);
				}				
			}
			
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a id="url_id" href="${ctx}/settleDic/settleDicItemQuery?cache=1">字典明细列表</a></li>
		<li class="active"><a>字典明细<shiro:hasPermission name="settleDic:settleDicItem:edit">${not empty settleDicItem.itemId?'修改':'添加'}</shiro:hasPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="settleDicItem" action="${ctx}/settleDic/settleDicItemQuery/save" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<form:hidden path="itemId"/>
		<div class="control-group">
			<label class="control-label">字典类型:</label>
			<div class="controls">
               <form:select id="typeId" onchange="checkType()" path="typeId" class="input-xlarge">
               		<form:option value="" label="--选择类型--"/>
					<c:forEach items="${typeList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
				<label id="type_id_info" class="error"></label>
			</div>
		</div>
		<div class="control-group" id="text_id" style="display:block;">
			<label class="control-label">明细KEY:</label>
			<div class="controls">
				<form:input path="text" htmlEscape="false" maxlength="20" class="required input-xlarge"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="value_id">
			<label class="control-label">明细VALUE:</label>
			<div class="controls">
				<form:input path="value" htmlEscape="false" maxlength="120" onchange="checkStyle(this.value)" class="required input-xlarge"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<label id="type_val_info" class="info"></label>
			</div>
		</div>
		<div class="control-group" id="text_select_id" style="display:none;">
			<label class="control-label">明细名称:</label>
			<div class="controls">
				<form:select id="text_select" onchange="checkTextSelect()" path="text" class="input-xlarge">
               		<form:option value="" label="--选择类型--"/>
					<c:forEach items="${textList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">状态:</label>
			<div class="controls">
               <form:select id="status" path="status" class="input-xlarge">
					<c:forEach items="${statusList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select> 
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="settleDic:settleDicItem:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>