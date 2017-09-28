<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			if($("#msg").val() != ""){
                parent.showThree();
                parent.changeThreeName($("#msg").val());
            }
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
			//交易类型根据业务类型联动方法
			getProductTrxType($("#businessType").val());
			showLocation();
			function showLocation() {
				var title = '<option value="">请选择</option>';
				$("#trxType").select2();
				$('#businessType').change(function(){
					$('#trxType').empty();
					$('#trxType').parent().find(".select2-chosen").html("");
					$('#trxType').append(title);
					getProductTrxType($("#businessType").val());
				});
			}
			if($('#trxType1').val() != '' && $('#trxType1').val() != null){
				$("#trxType").find("option[value='"+$('#trxType1').val()+"']").attr("selected",true);
				$("#trxType").parent().find(".select2-chosen").text($("#trxType").find("option[value='"+$('#trxType1').val()+"']").text());
			}
		});
		//根据业务类型获取交易类型方法
		function getProductTrxType(businessType){
			var el  = $('#trxType');
			$.ajax({
				async: false,
				type: "GET",
				url: "${ctx}/merchant/product/getProductTrxType",
				data: {"businessType":businessType},
				dataType: 'json',
				success: function(data){
					$.each(data , function(k , v) {
						var option  = '<option value="'+v.code+'">'+v.name+'</option>';
						el.append(option);
					})
				},
				error: function(){
					console.log("请求失败!");
				}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/merchant/product?cache=1">产品列表</a></li>
		<li class="active"><a href="${ctx}/merchant/product/form?id=${product.id}">产品<shiro:hasPermission name="merchant:product:edit">${not empty product.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="merchant:product:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="product" action="${ctx}/merchant/product/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" id="msg" value = "${msg}" />
		<input type="hidden" id="trxType1" value = "${product.trxType}" />
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">业务类型：</label>
			<div class="controls">
				<form:select path="businessType" class="input-xlarge required">
					<form:options items="${fns:getEnumList('rateBusinessType')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易类型：</label>
			<div class="controls">
				<form:select id="trxType" path="trxType" class="input-xlarge required">
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:input path="remark" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="merchant:product:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>