<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>内部账户管理</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/jquery-ztree/3.5.12/css/demo.css" rel="stylesheet" type="text/css"/>
	<link href="${ctxStatic}/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
	<script src="${ctxStatic}/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
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
			
			$("#btnClearBankCode").on("click", function(){
				$('#internalAccountDetailsTypeId').val(null).trigger("change");
			});
			
			$("#viewRule").on("click", function(){
				var textVal = $(this).text();
				if(textVal == '查看'){
					$(this).text("收起");
					$("#ruleDiv").show();
				}else{
					$(this).text("查看");
					$("#ruleDiv").hide();
				}
				
			});
			
			//手动录入银行/第三方代码
			$("#btnHandInput").on("click", function(){
				var textVal = $(this).val();
				if(textVal == '手动录入'){
					$("#hideSelect").hide();
					$("#detailsTypeId").show();
					$(this).val("关闭手动录入")
				}else{
					$("#detailsTypeId").hide();
					$("#hideSelect").show();
					$(this).val("手动录入")
				}
				
			});
			
		});
		
		
		var setting = {
				view: {
					dblClickExpand: false
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					beforeClick: beforeClick,
					onClick: onClick
				}
			};
			
			function beforeClick(treeId, treeNode) {
				var check = (treeNode && !treeNode.isParent);
				if (!check) alert("只能选择最底层科目");
				return check;
			}
			
			function onClick(e, treeId, treeNode) {
				var subjectObj = $("#accountTitle");
				subjectObj.attr("value", treeNode.name);
				$("#accountCode").val(treeNode.id);
			}
			

			function showMenu() {
				cancleSelect();
				var subjectObj = $("#accountTitle");
				var subjectOffset = $("#accountTitle").offset();
				$("#menuContent").css({left:subjectOffset.left + "px", top:subjectOffset.top + subjectObj.outerHeight() + "px"}).slideDown("fast");

				$("body").bind("mousedown", onBodyDown);
			}
			function hideMenu() {
				$("#menuContent").fadeOut("fast");
				$("body").unbind("mousedown", onBodyDown);
			}
			function onBodyDown(event) {
				if (!(event.target.id == "menuBtn" || event.target.id == "accountTitle" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
					hideMenu();
				}
			}
			function cancleSelect(){
				$("#accountTitle").val("")
				$("#accountCode").val("");
				var treeObj = $.fn.zTree.getZTreeObj('subjectTree');
				treeObj.checkAllNodes(false);
			}

			$(document).ready(function(){
				$.ajax({
					type : "post",
					url : "${ctx}/account/accountQuery/getSubjectCodes",
					dataType : "json",
					async : false,
					success : function(data) {
						$.fn.zTree.init($("#subjectTree"), setting, data);
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert(errorThrown);
					}
				});
			});
		
	</script>
</head>
<body>
	<div id="menuContent" class="menuContent" style="display: none; position: absolute;background:#F5F5F5";>  
		<ul id="subjectTree" class="ztree" style="margin-top:0; width:280px; height: 300px;"></ul>
    </div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/inner/innerAccount/">内部账户管理列表</a></li>
		<li class="active"><a href="javascript:void(0)">新增内部账户</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="merchantAccount" action="${ctx}/inner/innerAccount/save" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">内部账户名称：</label>
			<div class="controls">
				<form:input path="accountName" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">内部账户类型：</label>
			<div class="controls">
				<form:select id="internalAccountTypeId" path="internalAccountTypeId" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getEnumList('internalAccountType')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行/第三方代码：</label>
			<div class="controls">
				<span id="hideSelect" style="display:true">
					<form:select id="internalAccountDetailsTypeId" path="internalAccountDetailsTypeId" class="input-xlarge">
						<form:option value="" label=""/>
						<form:options items="${fns:getInteralAccountDetailsType()}" itemLabel="bankName" itemValue="bankNo" htmlEscape="false"/>
					</form:select>
				</span>
				<input id="detailsTypeId" name="detailsTypeId" type="text" htmlEscape="false" class="input-xlarge" style="display: none;" />
				<span class="help-inline"><input id="btnHandInput" class="btn" type="button" value="手动录入" /></span>
				<span class="help-inline"><input id="btnClearBankCode" class="btn" type="button" value="清 空" /></span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">借贷方向：</label>
			<div class="controls">
				<form:select path="balanceDirection" class="input-xlarge required">
					<form:option value=""></form:option>
					<form:option value="0">借</form:option>
					<form:option value="1">贷</form:option>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">币种：</label>
			<div class="controls">
				<form:select path="currency" class="input-xlarge required">
					<form:option value=""></form:option>
					<form:option value="156">人民币</form:option>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">科目：</label>
			<div class="controls">
				<form:hidden path="accountCode" id="accountCode"/>
				<form:input id="accountTitle" path="accountTitle" htmlEscape="false" class="input-xlarge required" onclick="showMenu();"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">描述：</label>
			<div class="controls">
				<form:textarea path="description" htmlEscape="false" rows="2" maxlength="255" class="input-xxlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remark" htmlEscape="false" rows="2" maxlength="255" class="input-xxlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="inner:innerAccount:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>