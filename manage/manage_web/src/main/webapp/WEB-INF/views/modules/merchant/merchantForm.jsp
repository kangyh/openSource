<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>merchant管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					if (checkShotName()){
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
			industry();
			industryChild($('#mccType').val());
			UnionpayP();
			if($('#mccType1').val() != '' && $('#mccType1').val() != null){
				$("#mccType").find("option[value='"+$('#mccType1').val()+"']").attr("selected",true);
				$("#mccType").find("option:selected").text();
				industryChild($('#mccType').val());
				if($('#mccServer1').val() != '' && $('#mccServer1').val() != null){
					$("#mccServer").find("option[value='"+$('#mccServer1').val()+"']").attr("selected",true);
					industryMcc($('#mccServer').val());
					if($('#mccDetail1').val() != '' && $('#mccDetail1').val() != null){
						$("#mccDetail").find("option[value='"+$('#mccDetail1').val()+"']").attr("selected",true);
					}
				}
			}
			if($('#unionPayProvinceCode1').val() != '' && $('#unionPayProvinceCode1').val() != null){
				$("#unionPayProvinceCode").find("option[value='"+$('#unionPayProvinceCode1').val()+"']").attr("selected",true);
				UnionpayCity($('#unionPayProvinceCode').val());
				if($('#unionPayCityCode1').val != '' && $('#unionPayCityCode1').val() != null){
					$("#unionPayCityCode").find("option[value='"+$('#unionPayCityCode1').val()+"']").attr("selected",true);
				}
			}
			showLocation();
			
			function showLocation() {
				var title = '<option value=""></option>';
				$("#mccType,#mccServer,#mccDetail").select2();
				$('#mccType').change(function(){
					$('#mccServer').empty();
					$('#mccServer').append(title);
					industryChild($('#mccType').val());
					$('#mccServer').change();
				});
				
				$('#mccServer').change(function(){
					$('#mccDetail').empty();
					$('#mccDetail').append(title);
					industryMcc($('#mccServer').val());
					$('#mccDetail').change();
				});
				
				$('#mccDetail').change(function(){
					$("#industryCategory").val($('#mccDetail').val());
					//获取商户pos码
					var userId = $("#userId").val();
					var mcc = $("#industryCategory").val();
					if(mcc != ""){
					console.log(userId + "mcc :" +mcc);
					$.ajax({
						   async: false, 
				           type: "GET",
				           url: "${ctx}/merchant/merchant/getMerchantPosCode",
						   data: {"userId":userId,"mcc":mcc},
						   dataType: 'json',
				           success: function(data){
				               $("#merchantPosCode").val(data);
						   },
						   error: function(){
				                console.log("请求失败!");
				           }
				    });	
					}
				});
				
				$("#unionPayProvinceCode,#unionPayCityCode").select2();
				$('#unionPayProvinceCode').change(function(){
					$('#unionPayCityCode').empty();
					$('#unionPayCityCode').append(title);
					$("#unionPayProvinceName").val($(this).find("option:selected").text());
					UnionpayCity($('#unionPayProvinceCode').val());
					$('#unionPayCityCode').change();
				});
				$('#unionPayCityCode').change(function(){
					$("#unionPayCityName").val($(this).find("option:selected").text());
				});
				
			}

		});
		//获取行业三级联动(1)
		function industry(){
			var el	= $('#mccType'); 
			$.ajax({
				   async: false, 
		           type: "GET",
		           url: "${ctx}/merchant/linkage/industry",
				   data: {},
				   dataType: 'json',
		           success: function(data){
		               //console.log(data);
					 $.each(data , function(k , v) {
							var option	= '<option value="'+v.value+'" selected="selected">'+v.name+'</option>';
							el.append(option);
					})
				   },
				   error: function(){
		                console.log("请求失败!");
		           }
		    });	
		}
		//获取行业三级联动(2)
		function industryChild(id){
			var el	= $('#mccServer'); 
			$.ajax({
				   async: false, 
		           type: "GET",
		           url: "${ctx}/merchant/linkage/industryChild",
				   data: {"id":id},
				   dataType: 'json',
		           success: function(data){
		               //console.log(data);
					 $.each(data , function(k , v) {
							var option	= '<option value="'+v.value+'">'+v.name+'</option>';
							el.append(option);
					})
				   },
				   error: function(){
		                console.log("请求失败!");
		           }
		    });	
		}
		//获取行业三级联动(3)
		function industryMcc(id){
			var el	= $('#mccDetail'); 
			$.ajax({
				   async: false, 
		           type: "GET",
		           url: "${ctx}/merchant/linkage/industryMcc",
				   data: {"id":id},
				   dataType: 'json',
		           success: function(data){
		               //console.log(data);
					 $.each(data , function(k , v) {
							var option	= '<option value="'+v.value+'">'+v.name+'</option>';
							el.append(option);
					})
				   },
				   error: function(){
		                console.log("请求失败!");
		           }
		    });	
		}
		//获取银联二级联动(1)
		function UnionpayP(){
			var el	= $('#unionPayProvinceCode'); 
			$.ajax({
				   async: false, 
		           type: "GET",
		           url: "${ctx}/merchant/linkage/UnionpayP",
				   data: {},
				   dataType: 'json',
		           success: function(data){
		               //console.log(data);
					 $.each(data , function(k , v) {
							var option	= '<option value="'+v.value+'">'+v.name+'</option>';
							el.append(option);
					})
				   },
				   error: function(){
		                console.log("请求失败!");
		           }
		    });	
		}
		//获取银联二级联动(2)
		function UnionpayCity(id){
			var el	= $('#unionPayCityCode'); 
			$.ajax({
				   async: false, 
		           type: "GET",
		           url: "${ctx}/merchant/linkage/UnionpayCity",
				   data: {"id":id},
				   dataType: 'json',
		           success: function(data){
		               //console.log(data);
					 $.each(data , function(k , v) {
							var option	= '<option value="'+v.value+'">'+v.name+'</option>';
							el.append(option);
					})
				   },
				   error: function(){
		                console.log("请求失败!");
		           }
		    });
		}
		//判断商户简称长度
		function checkShotName(){
			var shotName = $("#shortName").val();
			var len = 0;
			for (var i = 0; i < shotName.length; i++) {
				var c = shotName.charCodeAt(i);
				//单字节加1
				if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
					len++;
				}
				else {
					len += 2;
				}
			}
			if (len>16){
				parent.showThree();
				parent.changeThreeName("商户简称不可超过16字符");
				return false;
			}
			return true;
		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/merchant/merchant?cache=1">商户列表</a></li>
		<li class="active"><a href="${ctx}/merchant/merchant/form?id=${merchant.userId}">商户<shiro:hasPermission name="merchant:merchant:edit">${not empty merchant.userId?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="merchant:merchant:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="merchant" action="${ctx}/merchant/merchant/save" method="post" class="form-horizontal" enctype="multipart/form-data">
		<form:hidden path="userId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">商户编码：</label>
				<div class="controls">
					<input id="userId" value="${merchant.userId}" name="userId" htmlEscape="false" maxlength="10" readonly="true" readOnly="true" style="width:284px; border:0px;background-color:#fff;"/>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">商户账号：</label>
				<div class="controls">
					<input value="${merchant.loginName}" name="loginName" htmlEscape="false" maxlength="50" readonly="true" readOnly="true" style="border:0px;background-color:#fff;"/>
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">公司名称：</label>
				<div class="controls">
					<input value="${merchant.companyName}" name="companyName" htmlEscape="false" maxlength="100" readonly="true" readOnly="true" style="width:284px;border:0px;background-color:#fff;"/>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">公司简称：</label>
				<div class="controls">
					<form:input path="shortName" id="shortName" maxlength="16" htmlEscape="false" class="input-xlarge "/>
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">联系人：</label>
				<div class="controls">
					<form:input path="contactor" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">商户标识：</label>
				<div class="controls">
					<form:select path="merchantFlag" htmlEscape="false" class="input-xlarge required">
						<form:option value="" label="全部"/>
						<form:options items="${fns:getEnumList('merchantFlag')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</div>
		
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">联系人手机号：</label>
				<div class="controls">
					<form:input path="contactorPhone" htmlEscape="false" maxlength="100" class="input-xlarge "/>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">联系地址：</label>
				<div class="controls">
					<form:input path="contactAddress" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</div>
			</div>
		</div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">联系人身份证号：</label>
				<div class="controls">
					<form:input path="contactorIdcardNo" htmlEscape="false" maxlength="100" class="input-xlarge "/>
				</div>
			</div>
		</div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">留存金额：</label>
				<div class="controls">
					<form:input path="retainedAmount" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</div>
			</div>
			<div class="control-left">
				<label class="control-label">维系员工：</label>
				<div class="controls">
					<form:select path="inchargerId" class="input-xlarge ">
						<form:options items="${fns:getInchargerUser()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
					</form:select>
				</div>
			</div>
		</div>
		
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">平台会员行业分类：</label>
				<div class="controls">
					<form:select id="mccType" path="mccType" class="input-xlarge required">
						<form:option value="" label=""/>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
					<input type="hidden" id="mccType1" value="${merchant.mccType}" />
					<form:select id="mccServer" path="mccServer" class="input-xlarge required">
						<form:option value="" label=""/>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
					<input type="hidden" id="mccServer1" value="${merchant.mccServer}" />
					<form:select id="mccDetail" path="mccDetail" class="input-xlarge required" >
						<form:option value="" label=""/>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
					<input type="hidden" id="mccDetail1" value="${merchant.mccDetail}" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">商户MCC码：</label>
				<div class="controls">
					<input id="industryCategory" name="industryCategory" value="${merchant.industryCategory}" readonly="true" style="border:0px;background-color:#fff;width: 285px;"/>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">中国银联商户号：</label>
				<div class="controls">
					<input id="merchantPosCode" name="merchantPosCode" value="${merchant.merchantPosCode}" readonly="true" style="border:0px;background-color:#fff;"/>
				</div>
			</div>
		</div>


		<div class="control-group">
			<label class="control-label">银联区域编码：</label>
				<div class="controls">
					<form:select id="unionPayProvinceCode" path="unionPayProvinceCode" class="input-xlarge required">
						<form:option value="" label=""/>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
					<input type="hidden" id="unionPayProvinceCode1" value="${merchant.unionPayProvinceCode}" />
					<form:hidden id="unionPayProvinceName" path="unionPayProvinceName"/>
					<form:select id="unionPayCityCode" path="unionPayCityCode" class="input-xlarge required">
						<form:option value="" label=""/>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
					<input type="hidden" id="unionPayCityCode1" value="${merchant.unionPayCityCode}" />
					<form:hidden id="unionPayCityName" path="unionPayCityName"/>
				</div>
		</div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">联系人身份证正：</label>
				<div class="controls">
					<input type="file" name="contactorCertificateFrontFile"  htmlEscape="false" maxlength="256" class="input-xlarge"/>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">联系人身份证反：</label>
				<div class="controls">
					<input type="file" name="contactorCertificateBackFile"  htmlEscape="false" maxlength="256" class="input-xlarge"/>
				</div>
			</div>
		</div>


		<div class="control-group">
			<div class="control-left">
				<label class="control-label">协议号：</label>
				<div class="controls">
					<form:input path="protocolNumber" htmlEscape="false" maxlength="40" class="input-xlarge "/>
				</div>
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="merchant:merchant:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" onclick="return confirmation()" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>