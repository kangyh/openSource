<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<link href="${ctxStatic}/common/img.css" type="text/css" rel="stylesheet">
	<title>商家风控审核管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					var time1 = $("#daily_time").val() ? $("#daily_time").val() : $("#daily_time").siblings("input:checkbox:checked").val();
					var time2 = $("#legal_time").val() ? $("#legal_time").val() : $("#legal_time").siblings("input:checkbox:checked").val();
					var time3 = $("#contact_time").val() ? $("#contact_time").val() : $("#contact_time").siblings("input:checkbox:checked").val();
					$("#legalCertificateEndTime").val(time1);
					$("#businessLicenseEndTime").val(time2);
					$("#contactorCertificateEndTime").val(time3);
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
			//选择分类
			function showLocation(province , city , town ,flag) {
				var title	= [province , city , town];
				$.each(title , function(k , v) {
					title[k]	= '<option value="">'+v+'</option>';
				})
				
				$("#provinceCode,#cityCode,#countyCode").select2();
				$('#provinceCode').change(function() {
					$("#provinceName").val($(this).find("option:selected").text());
					$('#cityCode').empty();
					$('#cityCode').append(title[1]);
					var _province_id = $('#provinceCode').val();
		            //console.log(_province_id);
					if(_province_id!=''){
						get_cate_list('cityCode' , $('#provinceCode').val(),"2");
					}
					$('#cityCode').change();
				})
				
				$('#cityCode').change(function() {
					$("#cityName").val($(this).find("option:selected").text());
					$('#countyCode').empty();
					$('#countyCode').append(title[2]);
					var _city_id = $('#cityCode').val();
					if(_city_id!='' && _city_id!='0'){
						get_cate_list('countyCode' , $('#cityCode').val(),"3");
					}
					$('#countyCode').change();
				})
				
				$('#countyCode').change(function() {
					$("#countyName").val($(this).find("option:selected").text());
					var _town_id = $(this).val();
					$('input[name=location_id]').val(_town_id);
					/*if(_town_id!='' && _town_id!='0'){
						change_url($(this).val());
					}*/
					
				})
				if(flag){
					$('#provinceCode').append(title[0]);
					$('#cityCode').append(title[1]);
					$('#countyCode').append(title[2]);
					get_cate_list('provinceCode' , '',"1");	
				}
			}
			showLocation('省' , '市' , '区（县）',true);
		    function get_cate_list(el_id , loc_id,role) {
				var el	= $('#'+el_id); 
				$.ajax({
					   async: false, 
			           type: "GET",
			           url: "${ctx}/merchant/three/select",
					   data: {'id':loc_id,'role':role},
					   dataType: 'jsonp',
		               jsonp:'callback',
			           success: function(data){
			               //console.log(data);
						 $.each(data , function(k , v) {
								var option	= '<option value="'+v.id+'">'+v.name+'</option>';
								el.append(option);
						})
					   },
		            error: function(){
		                console.log("请求失败!");
		            }
			    });	
			}
		    if($('#merchantProvince').val() != '' && $('#merchantProvince').val() != null){
			    $("#provinceCode").find("option[value='"+$('#merchantProvince').val()+"']").attr("selected",true);
		    	//showLocation();
				var province = $('#provinceCode').val();
				$('#cityCode').empty();
				if(province != ''){
		    		get_cate_list('cityCode' , province,"2");
				}
		    	$("#cityCode").find("option[value='"+$('#merchantCity').val()+"']").attr("selected",true);
	    		
		    	if($('#merchantCity').val() != '' && $('#merchantCity').val() != null){
			    	var city = $('#cityCode').val();
			    	$('#countyCode').empty();
			    	get_cate_list('countyCode' , city,"3");
			    	$("#countyCode").find("option[value='"+$('#merchantCounty').val()+"']").attr("selected",true);
	    		}
		    	showLocation('','','',false);
		    }
    		
    		$(".pop_img span").on("click",function(){
    			$(".pop_img").hide();
    		});
    		
    		$(".popA").on("click",function(){
    			$(this).siblings(".pop_img").show();
    		});
			
    		$("#long_time").on("click", function () {
    	        if ($(this).prop("checked")) {
    	            $("#daily_time").val("");
    	        }
    	    })

    	    $("#daily_time").on("click", function () {
    	        $("#long_time").attr("checked", false);
    	        console.log($("#daily_time").val());
    	    })


    	    $("#legal_long").on("click", function () {
    	        if ($(this).prop("checked")) {
    	            $("#legal_time").val("");
    	        }
    	    })

    	    $("#legal_time").on("click", function () {
    	        $("#legal_long").attr("checked", false);
    	    })

    	    $("#contact_long").on("click", function () {
    	        if ($(this).prop("checked")) {
    	            $("#contact_time").val("");
    	        }
    	    })

    	    $("#contact_time").on("click", function () {
    	        $("#contact_long").attr("checked", false);
    	    })
		});
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
		<li><a href="${ctx}/merchant/merchantRc?cache=1">风控审核列表</a></li>
		<li class="active"><a href="${ctx}/merchant/merchantRc/form?id=${merchant.userId}">风控审核修改</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="merchant" action="${ctx}/merchant/merchantRc/save" method="post" class="form-horizontal">
		<form:hidden path="userId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">商户编码：</label>
				<div class="controls">
					<input name="userId" value="${merchant.userId}" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="true"/>
				</div>
			</div>
		</div>
		
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">商户公司名称：</label>
				<div class="controls">
					<input name="companyName" value="${merchant.companyName}" style="width:284px;border:0px;background-color:#fff;padding-top: 3px;" readonly="true"/>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">商户标识：</label>
				<div class="controls">
					<form:select path="merchantFlag" htmlEscape="false" class="input-xlarge required">
						<form:option value="" label="请选择"/>
						<form:option value="inside" label="内部商户"/>
						<form:option value="outsid" label="外部商户"/>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">公司简称：</label>
				<div class="controls">
					<form:input path="shortName" id="shortName" htmlEscape="false" maxlength="16" class="input-xlarge "/>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">法人代表：</label>
				<div class="controls">
					<input name="legalRepresentative" value="${merchant.legalRepresentative}" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="true"/>
				</div>
			</div>
		</div>
		
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">法人代表身份证号：</label>
				<div class="controls">
					<input name="legalIdcard" value="${merchant.legalIdcard}" style="width:284px;border:0px;background-color:#fff;padding-top: 3px;" readonly="true"/>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">法人代表证件有效期：</label>
				<div class="controls">
					<input type="hidden" id="legalCertificateEndTime" name="legalCertificateEndTime" value="${merchant.legalCertificateEndTime}"/>
					<c:if test="${ merchant.legalCertificateEndTime=='Thu Nov 30 00:00:00 CST 2'}">
						<input id="daily_time" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
						<input id="long_time" type="checkbox" checked="checked" value="0000-00-00"></label><span>长期</span>
					</c:if>
					<c:if test="${ merchant.legalCertificateEndTime!='Thu Nov 30 00:00:00 CST 2'}">
						<input id="daily_time" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
						value="<fmt:formatDate value="${merchant.legalCertificateEndTime}" pattern="yyyy-MM-dd"/>"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
						<input id="long_time" type="checkbox"  value="0000-00-00"></label><span>长期</span>
					</c:if>
				</div>
			</div>
		</div>
		
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">法人代表证件照(正)：</label>
				<div class="controls">
					<a class="popA">图片</a>
					<p class="pop_img">
						<span>X</span>
						<img src="${merchant.legalCertificateFront}">
					</p>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">法人代表证件照(反)：</label>
				<div class="controls">
					<a class="popA">图片</a>
					<p class="pop_img">
						<span>X</span>
						<img src="${merchant.legalCertificateBack}">
					</p>
				</div>
			</div>
		</div>
		
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">单位类型：</label>
				<div class="controls">
				<input value="${merchant.type}" maxlength="100" readonly style="width:284px;border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">证件类型：</label>
				<div class="controls">
				<input value="${merchant.certificateType}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">公司所在地：</label>
				<div class="controls">
					<form:select id="provinceCode" path="provinceCode" class="input-medium">
				    </form:select>
				    <input type="hidden"  id="merchantProvince" value="${merchantProvince}"/>
				    <form:hidden id="provinceName" path="provinceName"/>
				    <form:select id="cityCode" path="cityCode" class="input-medium">
				    </form:select>
				    <input type="hidden" id="merchantCity" value="${merchantCity}"/>
				    <form:hidden id="cityName" path="cityName"/>
				    <form:select id="countyCode" path="countyCode" class="input-medium">
				    </form:select>
				    <input type="hidden" id="merchantCounty" value="${merchantCounty}"/>
				    <form:hidden id="countyName" path="countyName"/>
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">详细地址：</label>
				<div class="controls">
					<form:input path="businessAddress" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">公司网址：</label>
				<div class="controls">
					<form:input path="website" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">ICP备案号：</label>
				<div class="controls">
					<form:input path="ipcNo" htmlEscape="false" maxlength="100" class="input-xlarge "/>
				</div>
			</div>
			<div class="control-left">
				<label class="control-label">营业执照号码：</label>
				<div class="controls">
					<form:input path="businessLicenseNo" htmlEscape="false" maxlength="100" class="input-xlarge "/>
				</div>
			</div>
		</div>
		
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">组织机构代码：</label>
				<div class="controls">
					<form:input path="organizationCode" htmlEscape="false" maxlength="100" class="input-xlarge "/>
				</div>
			</div>
			<div class="control-left">
				<label class="control-label">营业执照有效期：</label>
				<div class="controls">
					<input type="hidden" id="businessLicenseEndTime" name="businessLicenseEndTime" value="${merchant.businessLicenseEndTime}"/>
					<c:if test="${ merchant.businessLicenseEndTime=='Thu Nov 30 00:00:00 CST 2'}">
						<input id="legal_time" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
						<input id="legal_long" type="checkbox" checked="checked" value="0000-00-00"></label><span>长期</span>
					</c:if>
					<c:if test="${ merchant.businessLicenseEndTime!='Thu Nov 30 00:00:00 CST 2'}">
						<input id="legal_time" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
						value="<fmt:formatDate value="${merchant.businessLicenseEndTime}" pattern="yyyy-MM-dd"/>"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
						<input id="legal_long" type="checkbox"  value="0000-00-00"></label><span>长期</span>
					</c:if>
				</div>
			</div>
		</div>
		
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">税务登记证号码：</label>
				<div class="controls">
					<form:input path="taxRegistrationCertificateNo" htmlEscape="false" maxlength="100" class="input-xlarge "/>
				</div>
			</div>
			<div class="control-left">
				<label class="control-label">经营范围：</label>
				<div class="controls">
					<form:input path="businessScope" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</div>
			</div>
		</div>
		
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">联系人身份证号：</label>
				<div class="controls">
					<form:input path="contactorIdcardNo" htmlEscape="false" maxlength="50" class="input-xlarge "/>
				</div>
			</div>
			<div class="control-left">
				<label class="control-label">联系人：</label>
				<div class="controls">
					<form:input path="contactor" htmlEscape="false" maxlength="255" class="input-xlarge "/>
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
			<div class="control-left">
				<label class="control-label">联系人证件有效期：</label>
				<div class="controls">
					<input type="hidden" id="contactorCertificateEndTime" name="contactorCertificateEndTime" value="${merchant.contactorCertificateEndTime}"/>
					<c:if test="${ merchant.contactorCertificateEndTime=='Thu Nov 30 00:00:00 CST 2'}">
						<input id="contact_time" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
						<input id="contact_long" type="checkbox" checked="checked" value="0000-00-00"></label><span>长期</span>
					</c:if>
					<c:if test="${ merchant.contactorCertificateEndTime!='Thu Nov 30 00:00:00 CST 2'}">
						<input id="contact_time" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
						value="<fmt:formatDate value="${merchant.contactorCertificateEndTime}" pattern="yyyy-MM-dd"/>"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
						<input id="contact_long" type="checkbox"  value="0000-00-00"></label><span>长期</span>
					</c:if>
				</div>
			</div>
		</div>
		
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">营业执照照片：</label>
				<div class="controls">
					<a class="popA">图片</a>
					<p class="pop_img">
						<span>X</span>
						<img src="${merchant.businessLicenseFile}">
					</p>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">组织机构代码证照片：</label>
				<div class="controls">
					<a class="popA"><c:if test="${!empty merchant.organizationCodeCertificate}">图片</c:if></a>
					<p class="pop_img">
						<span>X</span>
						<img src="${merchant.organizationCodeCertificate}">
					</p>
				</div>
			</div>
		</div>
		
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">税务登记证照片：</label>
				<div class="controls">
					<a class="popA"><c:if test="${!empty merchant.taxRegistrationCertificate}">图片</c:if></a>
					<p class="pop_img">
						<span>X</span>
						<img src="${merchant.taxRegistrationCertificate}">
					</p>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">开户许可证照片：</label>
				<div class="controls">
					<a class="popA">图片</a>
					<p class="pop_img">
						<span>X</span>
						<img src="${merchant.permitsAccounts}">
					</p>
				</div>
			</div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="merchant:merchantRc:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" onclick="return confirmation()" value="保存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>