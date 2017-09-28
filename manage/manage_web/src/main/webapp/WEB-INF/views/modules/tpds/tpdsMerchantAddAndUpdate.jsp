<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>商户添加管理</title>
<meta name="decorator" content="default"/>
<link href="${ctxStatic}/common/img.css" type="text/css" rel="stylesheet">
<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		
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
		//添加商户的时候用户名验证
           $(function(){
			//给用户名输入框绑定change事件
               $("input[name='loginName']").blur(function(){
                   var loginNameCheck = $(this).val();
                   /*$.ajax({
                       type: "GET",
                       url: "/tpds/tpdsMerchant/regist?loginName="+loginNameCheck,
                       type:"html",
                       success: function(data){
                           if(1==data){
                               $(".errorMsg").html("<b style='color:green'>商户名可用</b>");
                           }else if(0==data) {
                               $(".errorMsg").html("<b style='color:red'>商户已存在 或 商户名不可用</b>");
                               //模拟聚焦事件发生
                               $("input[name='loginName']").focus();
                           }else{
                               $(".errorMsg").html("<b style='color:red'>商户名格式不正确</b>");
                               //模拟聚焦事件发生
                               $("input[name='loginName']").focus();
                           }
                       }
                   });*/
                   $.post("${ctx}/tpds/tpdsMerchant/regist",{
                       loginName:loginNameCheck
                   },function(data){
                       if(1==data){
                           $(".errorMsg").html("<b style='color:green'>商户名可用</b>");
                       }else if(0==data) {
                           $(".errorMsg").html("<b style='color:red'>商户已存在 或 商户名不可用</b>");
                           //模拟聚焦事件发生
                           $("input[name='loginName']").focus();
                       }else{
                           $(".errorMsg").html("<b style='color:red'>商户名格式不正确</b>");
                           //模拟聚焦事件发生
                           $("input[name='loginName']").focus();
                       }

                   },"json");
               });


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
           
           //格式化js日期
           Date.prototype.Format = function (fmt) { //author: meizz 
	        var o = {
	            "M+": this.getMonth() + 1, //月份 
	            "d+": this.getDate(), //日 
	            "H+": this.getHours(), //小时 
	            "m+": this.getMinutes(), //分 
	            "s+": this.getSeconds(), //秒 
	            "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	            "S": this.getMilliseconds() //毫秒 
	        };
	        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	        for (var k in o)
	        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	        return fmt;
	    }
	});

       function Sel1change(a){
           $("#bankcardName").val(a);
       }
       
       //验证网址
      function CheckWebsite(){
    	   var webSite=$("#website").val();
			$.ajax({
	            type: "GET",
	            url: "${ctx}/tpds/tpdsMerchant/checkWebsite",
	            cache:false,
	            data:{
	            	"webSite":webSite
				},
	            success: function(data){
	                if(1!=data){
	                	$("#websiteId").text("请输入合法的网址");
	                	$("#website").val("");
	                }else{
	                	$("#websiteId").text("");
	                }
	            }
	        });
       }
		//验证手机号
		function CheckTelNumber(){
		 var contactorPhone=$("#contactorPhone").val();
			$.ajax({
	            type: "GET",
	            url: "${ctx}/tpds/tpdsMerchant/checkTelNumber",
	            cache:false,
	            data:{
	            	"contactorPhone":contactorPhone
				},
	            success: function(data){
	                if(1!=data){
	                	$("#contactorPhoneId").text("请输入合法的手机号");
	                	$("#contactorPhone").val("");
	                }else{
	                	$("#contactorPhoneId").text("");
	                }
	            }
	        });
		}
		
		function CheckTel1Number(){
			var legalMobile = $("#legalMobile").val();
			$.ajax({
	            type: "GET",
	            url: "${ctx}/tpds/tpdsMerchant/checkTel1Number",
	            cache:false,
	            data:{
	            	"legalMobile":legalMobile
				},
	            success: function(data){
	                if(1!=data){
	                	$("#legalMobileId").text("请输入合法的手机号");
	                	$("#legalMobile").val("");
	                }else{
	                	$("#legalMobileId").text("");
	                }
	            }
	        });
		}
		
		
		function checkBusinessEndTime(){
			var businessLicenseEndTime = $("#businessLicenseEndTime").val();
			var nowTime = new Date().Format("yyyy-MM-dd HH:mm:ss");  
			if( businessLicenseEndTime!="" && nowTime!=""){
				if(compareDate(convertDateToJoinStr(nowTime),
								convertDateToJoinStr(businessLicenseEndTime)) > 0){
					$("#businessLicenseEndTimeId").text("不能小于当前日期");
					$("#businessLicenseEndTime").val("");
				}
			}
		}
		
		
		function CheckContactorEndTime(){
			var contactorCertificateEndTime = $("#contactorCertificateEndTime").val();
			var nowTime = new Date().Format("yyyy-MM-dd HH:mm:ss");  
			if( contactorCertificateEndTime!="" && nowTime!=""){
				if(compareDate(convertDateToJoinStr(nowTime),
								convertDateToJoinStr(contactorCertificateEndTime)) > 0){
					$("#contactorCertificateEndTimeId").text("不能小于当前日期");
					$("#contactorCertificateEndTime").val("");
				}
			}
		}
		
		function CheckLegalEndTime(){
			var legalCertificateEndTime = $("#legalCertificateEndTime").val();
			var nowTime = new Date().Format("yyyy-MM-dd HH:mm:ss");  
			if( legalCertificateEndTime!="" && nowTime!=""){
				if(compareDate(convertDateToJoinStr(nowTime),
								convertDateToJoinStr(legalCertificateEndTime)) > 0){
					$("#legalCertificateEndTimeId").text("不能小于当前日期");
					$("#legalCertificateEndTime").val("");
				}
			}
		}
		
		 //用途：检查输入字符串是否只由英文字母和数字和下划线组成
        function isNumberOrLetter(s){
            var numberOrLetterValue = s.value;
            var numberOrLetterName = s.name;
            var regu = "^[0-9a-zA-Z\_]+$";
            var re = new RegExp(regu);
            var prompt = "请输入英文字母和数字！";
            if (re.test(numberOrLetterValue)) {
                return true;
            }else{
                alert(prompt);
                if(numberOrLetterName == "businessLicenseNo"){
                    document.getElementById("businessLicenseNo").value = "";
                }
            }
        }
		
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/tpds/tpdsMerchant">${empty tpdsMerchant.merchantId?'商户信息添加':'商户信息修改'}</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tpdsMerchant" action="${ctx}/tpds/tpdsMerchant/save?merchantId=${tpdsMerchant.merchantId}" method="post" class="form-horizontal" enctype="multipart/form-data">
	
		<sys:message content="${message}"/>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">商户账号：</label>
				<div class="controls">
				<c:if test="${empty tpdsMerchant.merchantId}">
					<form:input id="loginName2" path="loginName" value="${tpdsMerchant.loginName}" htmlEscape="false" maxlength="30" class="input-xlarge required" name="merchantNo" />
                    <span class="errorMsg">*</span>
                    <span class="help-inline"><font color="red">邮箱格式</font> </span>
				</c:if>
				<c:if test="${not empty tpdsMerchant.merchantId}">
					<input type="text" id="merchantProvince" value="${tpdsMerchant.loginName}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				</c:if>
				</div>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">国家名称：</label>
            <div class="controls">
                <form:select path="contryName" value="${tpdsMerchant.contryName}" class="input-xlarge required"  onchange="Sel1change(this.options[this.options.selectedIndex].text);">
                    <form:options items="${cbmsCountrysettingList}" itemLabel="countryName" itemValue="countryName" htmlEscape="false" />
                </form:select>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">商户类型</label>
            <div class="controls">
                <form:radiobuttons path="type"  items="${fns:getEnumList('merchantType')}" itemLabel="name" itemValue="value" htmlEscape="false" class="required" />
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">证件类型</label>
            <div class="controls">
                <form:radiobuttons path="certificateType" items="${fns:getEnumList('certificateType')}" itemLabel="name" itemValue="value" htmlEscape="false" class="required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
		</div>

		<div class="control-group">
            <div class="control-left">
                <label class="control-label">公司名称：</label>
                <div class="controls">
                    <form:input path="companyName" value="${tpdsMerchant.companyName}" htmlEscape="false" maxlength="50" class="input-xlarge required"
                                onkeyup="value=value.replace(/[^\u4E00-\u9FA5|\uFF08|\uFF09]/g,'')"
                                onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\u4E00-\u9FA5|\uFF08|\uFF09]/g,''))"
                                onFocus="if(value==defaultValue){value='';this.style.color='#000'}"
                                onBlur="if(!value){value=defaultValue;}"
                                />
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">公司联系电话：</label>
                <div class="controls">
                    <form:input path="companyPhone" value="${tpdsMerchant.companyPhone}" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
                    <span id="companyPhoneId" class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
        </div>

        <div class="control-group">
            <div class="control-left">
                <label class="control-label">证件地址：</label>
                
                <c:if test="${empty tpdsMerchant.merchantId}">
	                <div class="controls">
	                   <form:select id="provinceCode" path="provinceCode"  class="input-medium "></form:select>
	                    <input type="hidden" id="merchantProvince" value="${merchantProvince}"/>
	                   	<form:hidden id="provinceName" path="provinceName" />
	                   <form:select id="cityCode" path="cityCode" class="input-medium "></form:select>
	                    <input type="hidden" id="merchantCity" value="${merchantCity}"/>
	                    <form:hidden id="cityName" path="cityName" />
	                   <form:select id="countyCode" path="countyCode" class="input-medium "></form:select>
	                    <input type="hidden" id="merchantCounty" value="${merchantCounty}" />
	                    <form:hidden id="countyName" path="countyName"/>
	                    <span class="help-inline"><font color="red">*</font> </span>
	                </div>
                </c:if>
                <c:if test="${not empty tpdsMerchant.merchantId}">
	                <div class="controls">
	                    <input type="text" id="merchantProvince" value="${tpdsMerchant.provinceName}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;width:80px;"/>
	                    <input type="text" id="merchantCity" value="${tpdsMerchant.cityName}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;width:80px;"/>
	                    <input type="text" id="merchantCounty" value="${tpdsMerchant.countyName}"  readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;width:80px;"/>
	                </div>
                </c:if>
            </div>
        </div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">公司注册地址：</label>
				<div class="controls">
					<form:input path="businessAddress" value="${tpdsMerchant.businessAddress}" htmlEscape="false" maxlength="50" class="input-xlarge required"
                                onkeyup="value=value.replace(/[^\u4E00-\u9FA5|\uFF08|\uFF09|0-9|\u2014]/g,'')"
                                onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\u4E00-\u9FA5|\uFF08|\uFF09|0-9|\u2014]/g,''))"
                                onFocus="if(value==defaultValue){value='';this.style.color='#000'}"
                                onBlur="if(!value){value=defaultValue;}"
                    />
                    <span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">公司网址：</label>
				<div class="controls">
					<form:input path="website" id="website" value="${tpdsMerchant.website}" htmlEscape="false"  class="input-xlarge required" 
                                onchange="CheckWebsite()"
                                onkeyup="value=value.replace(/[(\u4e00-\u9fa5)]/g,'') "
                                onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[(\u4e00-\u9fa5)]/g,''))"
                                onFocus="if(value==defaultValue){value='';this.style.color='#000'}"
                                onBlur="if(!value){value=defaultValue;}"
                                />
                    <span id="websiteId" style="color:red;" class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">营业执照号码：</label>
				<div class="controls">
					<form:input path="businessLicenseNo" value="${tpdsMerchant.businessLicenseNo}" htmlEscape="false" maxlength="30" class="input-xlarge required"
                               onchange="isNumberOrLetter(this)"/>
                                

				</div>
			</div>
			<div class="control-right">
				<label class="control-label">营业执照结束时间：</label>
				<div class="controls">
					<input name="businessLicenseEndTime" id="businessLicenseEndTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
						value="<fmt:formatDate value="${tpdsMerchant.businessLicenseEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						onchange="checkBusinessEndTime()"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					 <span id="businessLicenseEndTimeId" style="color:red;" class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">组织机构代码：</label>
				<div class="controls">
					<form:input path="organizationCode" value="${tpdsMerchant.organizationCode}" htmlEscape="false" maxlength="20" class="input-xlarge required"
                                onkeyup="value=value.replace(/[\u4e00-\u9fa5]/g,'') "
                                onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[\u4e00-\u9fa5]/g,''))"
                                onFocus="if(value==defaultValue){value='';this.style.color='#000'}"
                                onBlur="if(!value){value=defaultValue;}"
                                />
                    <span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">税务登记证号码：</label>
				<div class="controls">
					<form:input path="taxRegistrationCertificateNo" value="${tpdsMerchant.taxRegistrationCertificateNo}" htmlEscape="false" maxlength="20" class="input-xlarge required"
                                onkeyup="value=value.replace(/[^\d]/g,'') "
                                onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"
                                onFocus="if(value==defaultValue){value='';this.style.color='#000'}"
                                onBlur="if(!value){value=defaultValue;}"
                                />
                    <span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">ICP备案号：</label>
            <div class="controls">
                <form:input path="ipcNo" value="${tpdsMerchant.ipcNo}" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">法人名称：</label>
				<div class="controls">
					<form:input path="llegalperson" value="${tpdsMerchant.llegalperson}" htmlEscape="false" maxlength="10" class="input-xlarge required"
                                onkeyup="value=value.replace(/[^\u4E00-\u9FA5]/g,'')"
                                onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\u4E00-\u9FA5]/g,''))"
                                onFocus="if(value==defaultValue){value='';this.style.color='#000'}"
                                onBlur="if(!value){value=defaultValue;}"
                                />
                    <span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-right">
                <label class="control-label">法人手机号码：</label>
                <div class="controls">
                    <form:input path="legalMobile" id="legalMobile" value="${tpdsMerchant.legalMobile}" htmlEscape="false" class="input-xlarge required" maxlength="11" 
                              	onchange="CheckTel1Number()"
                                onkeyup="value=value.replace(/[^\d]/g,'') "
                                onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"
                                onFocus="if(value==defaultValue){value='';this.style.color='#000'}"
                                onBlur="if(!value){value=defaultValue;}"
                    />
                   <span id="legalMobileId" style="color:red;" class="help-inline"><font color="red">*</font> </span>
                </div>
			</div>
		</div>

        <div class="control-group">
            <div class="control-left">
                <label class="control-label">法人代表身份证号：</label>
                <div class="controls">
                    <form:input path="legalIdcard" value="${tpdsMerchant.legalIdcard}" htmlEscape="false" maxlength="18" class="input-xlarge required"
                                onkeyup="value=value.replace(/[^\d{17}[\d|X]|\d{15}]/g,'')"
                                onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d{18}|\d{15}$]/g,''))"
                                onFocus="if(value==defaultValue){value='';this.style.color='#000'}"
                                onBlur="if(!value){value=defaultValue;}"
                                />
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">法人代表证件有效期结束：</label>
                <div class="controls">
                    <input name="legalCertificateEndTime" id="legalCertificateEndTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
                        value="<fmt:formatDate value="${tpdsMerchant.legalCertificateEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                        onchange="CheckLegalEndTime()"
                        onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
                     <span id="legalCertificateEndTimeId" style="color:red;" class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">联系人：</label>
				<div class="controls">
					<form:input path="contactor" value="${tpdsMerchant.contactor}" htmlEscape="false" maxlength="10" class="input-xlarge required"
                                onkeyup="value=value.replace(/[^\u4E00-\u9FA5]/g,'')"
                                onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\u4E00-\u9FA5]/g,''))"
                                onFocus="if(value==defaultValue){value='';this.style.color='#000'}"
                                onBlur="if(!value){value=defaultValue;}"
                                />
                    <span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
            <div class="control-right">
                <label class="control-label">联系人手机号：</label>
                <div class="controls">
                    <form:input path="contactorPhone" id="contactorPhone" value="${tpdsMerchant.contactorPhone}" htmlEscape="false" class="input-xlarge required" maxlength="11" 
                               	onchange="CheckTelNumber()"
                                onkeyup="value=value.replace(/[^\d]/g,'') "
                                onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"
                                onFocus="if(value==defaultValue){value='';this.style.color='#000'}"
                                onBlur="if(!value){value=defaultValue;}"
                                />
                     <span id="contactorPhoneId" style="color:red;" class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
		</div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">联系人身份证号：</label>
				<div class="controls">
					<form:input path="contactorIdcardNo" value="${tpdsMerchant.contactorIdcardNo}" htmlEscape="false" maxlength="18" class="input-xlarge required"
                                onkeyup="value=value.replace(/[^\d{17}[\d|X]|\d{15}]/g,'')"
                                onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d{18}$|^\d{15}$]/g,''))"
                                onFocus="if(value==defaultValue){value='';this.style.color='#000'}"
                                onBlur="if(!value){value=defaultValue;}"
                                />
                    <span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">联系人证件有效期结束：</label>
				<div class="controls">
					<input name="contactorCertificateEndTime" id="contactorCertificateEndTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
						value="<fmt:formatDate value="${tpdsMerchant.contactorCertificateEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						onchange="CheckContactorEndTime()"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
						<span id="contactorCertificateEndTimeId" style="color:red;" class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</div>

		<div class="control-group">
           
            <div class="control-right">
                <label class="control-label">商户名称：</label>
                <div class="controls">
                    <form:input path="supplierName" value="${tpdsMerchant.supplierName}" htmlEscape="false" maxlength="20" class="input-xlarge required"
                                onkeyup="value=value.replace(/[^\u4E00-\u9FA5|\uFF08|\uFF09]/g,'')"
                                onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\u4E00-\u9FA5|\uFF08|\uFF09]/g,''))"
                                onFocus="if(value==defaultValue){value='';this.style.color='#000'}"
                                onBlur="if(!value){value=defaultValue;}"
                                />
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
		</div>



        <div class="control-group">
            <label class="control-label">经营范围：</label>
            <div class="controls">
                <form:textarea path="businessScope" value="${tpdsMerchant.businessScope}" htmlEscape="false" maxlength="150" class="input-xlarge required" />
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>

	<c:if test="${empty tpdsMerchant.merchantId}">
		<div class="control-group">
			<label class="control-label">开户许可证：</label>
			<div class="controls">
                <input type="file" name="permitsAccountsFile"  htmlEscape="false" maxlength="256" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">法人代表证件照(正)：</label>
            <div class="controls">
                <input  type="file" name="legalCertificateFrontFile"  htmlEscape="false" maxlength="256" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">法人代表证件照(反)：</label>
			<div class="controls">
                <input  type="file" name="legalCertificateBackFile"  htmlEscape="false" maxlength="256" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">营业执照证（三证合一）：</label>
            <div class="controls">
                <input  type="file" name="businessLicenseFileFrontFile"  htmlEscape="false" maxlength="256" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>

		<div class="control-group">
			<label class="control-label">税务登记证：</label>
			<div class="controls">
                <input  type="file" name="taxRegistrationCertificateFile"  htmlEscape="false" maxlength="256" class="input-xlarge "/>
                <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">组织机构代码证：</label>
			<div class="controls">
                <input  type="file" name="organizationCodeCertificateFile"  htmlEscape="false" maxlength="256" class="input-xlarge "/>
                <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</c:if>
	<c:if test="${not empty tpdsMerchant.merchantId}">
		<div class="control-group">
			<label class="control-label">开户许可证：</label>
			<div class="controls">
                <c:if test="${empty tpdsMerchant.permitsAccounts}">
                    <input type="file" name="permitsAccountsFile" htmlEscape="false" maxlength="256" class="input-xlarge required" />
                </c:if>
                <c:if test="${not empty tpdsMerchant.permitsAccounts}">
                    <a class="popA">图片</a>
                    <p class="pop_img">
                        <span>X</span>
                        <img src="${tpdsMerchant.permitsAccounts}">
                    </p>
                </c:if>
                <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">法人代表证件照(正)：</label>
            <div class="controls">
                <c:if test="${empty tpdsMerchant.legalCertificateFront}">
                    <input  type="file" name="legalCertificateFrontFile" htmlEscape="false" maxlength="256" class="input-xlarge required" />
                </c:if>
                <c:if test="${not empty tpdsMerchant.legalCertificateFront}">
                    <a class="popA">图片</a>
                    <p class="pop_img">
                        <span>X</span>
                        <img src="${tpdsMerchant.legalCertificateFront}">
                    </p>
                </c:if>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        
		<div class="control-group">
			<label class="control-label">法人代表证件照(反)：</label>
			<div class="controls">
                <c:if test="${empty tpdsMerchant.legalCertificateBack}">
                    <input  type="file" name="legalCertificateBackFile"  htmlEscape="false" maxlength="256" class="input-xlarge required" />
                </c:if>
                <c:if test="${not empty tpdsMerchant.legalCertificateBack}">
                    <a class="popA">图片</a>
                    <p class="pop_img">
                        <span>X</span>
                        <img src="${tpdsMerchant.legalCertificateBack}">
                    </p>
                </c:if>
                <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">营业执照证（三证合一）：</label>
            <div class="controls">
               <c:if test="${empty tpdsMerchant.businessLicenseFile}">
                    <input type="file" name="businessLicenseFileFrontFile"  htmlEscape="false" maxlength="256" class="input-xlarge required" />
                </c:if>
                <c:if test="${not empty tpdsMerchant.businessLicenseFile}">
                    <a class="popA">图片</a>
                    <p class="pop_img">
                        <span>X</span>
                        <img src="${tpdsMerchant.businessLicenseFile}">
                    </p>
                </c:if>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>

		<div class="control-group">
			<label class="control-label">税务登记证：</label>
			<div class="controls">
				<c:if test="${empty tpdsMerchant.taxRegistrationCertificate}">
                	<input type="file" name="taxRegistrationCertificateFile"  htmlEscape="false" maxlength="256" class="input-xlarge " />
				</c:if>
                <c:if test="${not empty tpdsMerchant.taxRegistrationCertificate}">
                    <a class="popA">图片</a>
                    <p class="pop_img">
                        <span>X</span>
                        <img src="${tpdsMerchant.taxRegistrationCertificate}">
                    </p>
                </c:if>
                <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">组织机构代码证：</label>
			<div class="controls">
				<c:if test="${empty tpdsMerchant.organizationCodeCertificate}">
                	<input  type="file" name="organizationCodeCertificateFile" htmlEscape="false" maxlength="256" class="input-xlarge " />
				</c:if>
                <c:if test="${not empty tpdsMerchant.organizationCodeCertificate}">
                    <a class="popA">图片</a>
                    <p class="pop_img">
                        <span>X</span>
                        <img src="${tpdsMerchant.organizationCodeCertificate}">
                    </p>
                </c:if>
                <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</c:if>

		
		<div class="form-actions">
			<c:if test="${empty read}">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>