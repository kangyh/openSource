<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商户修改管理</title>
    <meta name="decorator" content="default"/>
    <link href="${ctxStatic}/common/img.css" type="text/css" rel="stylesheet">
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


            if(${merchant.certificateType == "MULTIP"}){
                $("input[name='taxRegistrationCertificateFile']").val("");
                $("input[name='organizationCodeCertificateFile']").val("");
                $("#organizationCode").val("");
                $("#taxRegistrationCertificateNo").val("");
                $("#taxRegistrationCertificateFile").hide();
                $("#organizationCodeCertificateFile").hide();
                $("#organizeAndtaxRegist").hide();
            }else if(${merchant.certificateType == "ORDINA"} || ${merchant.certificateType == "INDIVI"}) {
                $("#taxRegistrationCertificateFile").show();
                $("#organizationCodeCertificateFile").show();
                $("#organizeAndtaxRegist").show();
            }

            //点击
            $(".pop_img span").on("click",function(e){
                e.stopPropagation();
                $(this).parent().hide();
            });

            $(".popA").on("click",function(e){
                e.stopPropagation();
                $(".pop_img").hide();
                $(this).siblings(".pop_img").show();
            });
            $(document).click(function(){
                $(".pop_img").hide();
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

        //图片上传验证
        function loadImage(img) {
            var filePath = img.value;
            var fileExt = filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
            if (!checkFileExt(fileExt)) {
                alert("请上传的文件不是图片,请重新上传！");
                img.value = "";
                return false;
            }
            if (img.files && img.files[0]) {
                if((img.files[0].size / 1024).toFixed(0) > 2 * 1024) {
                    alert("图片不大于2MB。");
                    img.value = "";
                    return false;
                }
//                alert('你选择的文件大小' + (img.files[0].size / 1024).toFixed(0) + "kb");
            }

        }
        function checkFileExt(ext) {
            if (!ext.match(/.jpg|.gif|.png|.bmp|.jpeg/i)) {
                return false;
            }
            return true;
        }
        function certificateTypeChange(option){
            var code = option.value;
            if(code == "MULTIP"){
                $("#taxRegistrationCertificateFile").hide();
                $("#organizationCodeCertificateFile").hide();
                $("#organizeAndtaxRegist").hide();
            }else if(code == "ORDINA" || code == "INDIVI") {
                $("#taxRegistrationCertificateFile").show();
                $("#organizationCodeCertificateFile").show();
                $("#organizeAndtaxRegist").show();
            }
        }
        //输入框校验
        //校验公司名称
        function isCompanyName( str ){
            var strValue = str.value;
            var strName = str.name;
            var regu = /^[\uF900-\uFA2D\u4E00-\u9FA5_a-zA-Z|\uFF08|\uFF09]+$/;
            var re = new RegExp(regu);
            var prompt = "请输入字母、中文、中文字符 “（）”";
            if(re.test(strValue)){
                return true;
            }else {
                alert(prompt);
                if(strName == "companyName") {
                    document.getElementById("companyName").value = "";
                }
            }
        }
        //校验商户名称
        function checkCompanyName( str ){
            var strValue = str.value;
            var strName = str.name;
            var regu = /^[\u4E00-\u9FA5\uF900-\uFA2D|\uFF08|\uFF09]+$/;
            var re = new RegExp(regu);
            var prompt = "请输入中文和字符 “（）”";
            if(re.test(strValue)){
                return true;
            }else {
                alert(prompt);
                if(strName == "companyName") {
                    document.getElementById("companyName").value = "";
                }else if(strName == "bankcardOwnerName") {
                    document.getElementById("bankcardOwnerName").value = "";
                }else if(strName == "supplierName") {
                    document.getElementById("supplierName").value = "";
                }else if(strName == "electricityName") {
                    document.getElementById("electricityName").value = "";
                }else if(strName == "businessScope") {
                    document.getElementById("businessScope").value = "";
                }else if(strName == "enterpriseName") {
                    document.getElementById("enterpriseName").value = "";
                }else if(strName == "orgCustomsName") {
                    document.getElementById("orgCustomsName").value = "";
                }
            }
        }
        //校验网址
        function isWebsite( strWebsite ){
            var strValue = strWebsite.value;
            var strName = strWebsite.name;
            var regu = /^(http[s]:\/\/)?(www.)?(\w+\.)+\w{2,4}(\/)?$/;
            var prompt = "请输入正确网址，例如：https://www.heepay.com";
            var re = new RegExp(regu);
            if(re.test(strValue)){
                return true;
            }else {
                alert(prompt);
                if(strName == "website"){
                    document.getElementById("website").value = "";
                }else if(strName == "electricityUrl"){
                    document.getElementById("electricityUrl").value = "";
                }
            }
        }
        //校验固定电话
        function checkPhone( strPhone ) {
            var phoneValue = strPhone.value;
            var phoneName = strPhone.name;
            var phoneRegWithArea = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
            var prompt = "请输入的电话号码不正确,例如：010-58103559"
            if( phoneRegWithArea.test(phoneValue) ){
                return true;
            }else{
                alert( prompt );
                if(phoneName == "companyPhone"){
                    document.getElementById("companyPhone").value = "";
                }
                return false;
            }
        }

        //校验手机号     legalMobile contactorPhone  bankcardOwnerMobile
        function checkMobile( strMobile ) {
            var mobileValue = strMobile.value;
            var mobileName = strMobile.name;
            var regu = /(^13\d{9}$)|(^14\d{9}$)|(^15\d{9}$)|(^16\d{9}$)|(^17\d{9}$)|(^18\d{9}$)|(^19\d{9}$)/g;
            var prompt = "请输入正确手机号码！"
            if( mobileValue.length == 11 ) {
                if( regu.test(mobileValue) ){
                    return true;
                }else{
                    alert(prompt);
                    if(mobileName == "legalMobile"){
                        document.getElementById("legalMobile").value = "";
                    }else if(mobileName == "contactorPhone"){
                        document.getElementById("contactorPhone").value = "";
                    }else if(mobileName == "bankcardOwnerMobile"){
                        document.getElementById("bankcardOwnerMobile").value = "";
                    }
                }
            }else{
                alert(prompt);
                if(mobileName == "legalMobile"){
                    document.getElementById("legalMobile").value = "";
                }else if(mobileName == "contactorPhone"){
                    document.getElementById("contactorPhone").value = "";
                }else if(mobileName == "bankcardOwnerMobile"){
                    document.getElementById("bankcardOwnerMobile").value = "";
                }
            }
        }

        //用途：检查输入字符串是否只由英文字母和数字和下划线组成
        function isNumberOrLetter( s ){
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

        //用途：检查输入字符串是汉字
        function isChinese( s ){
            var chineseValue = s.value;
            var chineseName = s.name;
            var regu = /^[\u4E00-\u9FA5\uF900-\uFA2D]+$/;
            var re = new RegExp(regu);
            var prompt = "请输入中文！";
            if (re.test(chineseValue)) {
                return true;
            }else{
                alert(prompt);
                if(chineseName == "llegalperson"){
                    document.getElementById("llegalperson").value = "";
                }else if(chineseName == "contactor") {
                    document.getElementById("contactor").value = "";
                }
            }
        }

        //用途：检查输入字符串是非汉字
        function isNotChinese( s ){
            var chineseValue = s.value;
            var chineseName = s.name;
            var regu = /^[\u2E80-\u9FFF]+$/;
            var re = new RegExp(regu);
            var prompt = "输入的字符串中包含中文！";
            if (!re.test(chineseValue)) {
                return true;
            }else if(chineseName == "organizationCode"){
                alert(prompt);
                document.getElementById("organizationCode").value = "";
            }else if(chineseName == "orgCustomsCode"){
                alert(prompt);
                document.getElementById("orgCustomsCode").value = "";
            }else if(chineseName == "centralOfficeNumber"){
                alert(prompt);
                document.getElementById("centralOfficeNumber").value = "";
            }else{
                alert(prompt);
            }
        }

        //用途：检查输入字符串是否符合正整数格式   taxRegistrationCertificateNo        /^\d+$/
        function isNumber( s ){
            var numberValue = s.value;
            var numberName = s.name;
            var regu = "^[0-9]+$";
            var re = new RegExp(regu);
            var prompt = "请输入数字！";
            if (re.test(numberValue)) {
                return true;
            } else {
                alert(prompt);
                if(numberName == "taxRegistrationCertificateNo"){
                    document.getElementById("taxRegistrationCertificateNo").value = "";
                }else if(numberName =="employeeAccount"){
                    document.getElementById("employeeAccount").value = "";
                }else if(numberName =="bankcardNo"){
                    document.getElementById("bankcardNo").value = "";
                }
            }
        }
        //验证身份证方法
        var aCity = {11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州"
            ,53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"};
        function isIdCard(sId){
            var iSum = 0;
            var numberValue = sId.value;
            var numberName = sId.name;
            var prompt = "请输入正确的身份证号码！";
            if(!/^\d{17}(\d|x)$/i.test(numberValue)){
                alert(prompt);
                if(numberName == "legalIdcard"){
                    document.getElementById("legalIdcard").value = "";
                }else if(numberName =="contactorIdcardNo"){
                    document.getElementById("contactorIdcardNo").value = "";
                }else if(numberName =="bankcardOwnerIdcard"){
                    document.getElementById("cardOwnerIdcard").value = "";
                }
                return false;
            }
            numberValue=numberValue.replace(/x$/i,"a");
            if(aCity[parseInt(numberValue.substr(0,2))]==null){
                alert(prompt);
                if(numberName == "legalIdcard"){
                    document.getElementById("legalIdcard").value = "";
                }else if(numberName =="contactorIdcardNo"){
                    document.getElementById("contactorIdcardNo").value = "";
                }else if(numberName =="bankcardOwnerIdcard"){
                    document.getElementById("cardOwnerIdcard").value = "";
                }
                return false;
            }
            var sBirthday = numberValue.substr(6,4)+"-"+Number(numberValue.substr(10,2))+"-"+Number(numberValue.substr(12,2));
            var d=new Date(sBirthday.replace(/-/g,"/"))
            if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate())){
                alert(prompt);
                if(numberName == "legalIdcard"){
                    document.getElementById("legalIdcard").value = "";
                }else if(numberName =="contactorIdcardNo"){
                    document.getElementById("contactorIdcardNo").value = "";
                }else if(numberName =="bankcardOwnerIdcard"){
                    document.getElementById("cardOwnerIdcard").value = "";
                }
                return false;
            }
            for(var i = 17;i>=0;i --) iSum += (Math.pow(2,i) % 11) * parseInt(numberValue.charAt(17 - i),11)
            if(iSum%11!=1){
                alert(prompt);
                if(numberName == "legalIdcard"){
                    document.getElementById("legalIdcard").value = "";
                }else if(numberName =="contactorIdcardNo"){
                    document.getElementById("contactorIdcardNo").value = "";
                }else if(numberName =="bankcardOwnerIdcard"){
                    document.getElementById("cardOwnerIdcard").value = "";
                }
                return false;
            }
            return true;
        };
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
        <c:if test="${goal == 1}">
            <li><a href="${ctx}/cbms/merchant/merchantLegal/">法务审核列表</a></li>
            <li class="active"><a href="${ctx}/cbms/cbmsMerchantSuppliersetting/edit?id=${merchantUser.id}&goal=1">跨境商户<shiro:hasPermission name="cbms:cbmsMerchantSuppliersetting:edit">${not empty merchantUser.id?'修改':'添加'}</shiro:hasPermission></a></li>
        </c:if>
        <c:if test="${goal == 2}">
            <li><a href="${ctx}/cbms/merchant/merchantRc/">商户风控列表</a></li>
            <li class="active"><a href="${ctx}/cbms/cbmsMerchantSuppliersetting/edit?id=${merchantUser.id}&goal=2">跨境商户<shiro:hasPermission name="cbms:cbmsMerchantSuppliersetting:edit">${not empty merchantUser.id?'修改':'添加'}</shiro:hasPermission></a></li>
        </c:if>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cbmsMerchantSuppliersetting" action="${ctx}/cbms/cbmsMerchantSuppliersetting/update?goal=${goal}" method="post" class="form-horizontal" enctype="multipart/form-data">
        <form:hidden path="merchantNo" value="${merchantUser.id}"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">商户账号：</label>
				<div class="controls">
                    <input value="${merchantUser.loginName}" maxlength="64" readonly style="border:0px;background-color:#fff;padding-top: 3px;"/>
				</div>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">国家名称：</label>
            <div class="controls">
                <form:select id="contryName" path="contryCode" class="input-xlarge required" >
                    <form:option value="" label="-国家名称-"/>
                    <c:forEach items="${cbmsCountrysettingList}" var="cbmsCountrysetting">
                        <form:option value="${cbmsCountrysetting.countryNo}" label="${cbmsCountrysetting.countryName}"/>
                        <input type="hidden" name="contryName" value="${cbmsCountrysetting.countryName}"/>
                    </c:forEach>
                </form:select>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">单位类型</label>
            <div class="controls">
                <form:radiobuttons path="type" items="${fns:getEnumList('merchantType')}" itemLabel="name" itemValue="value" htmlEscape="false" />
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">证件类型</label>
            <div class="controls">
                <form:radiobuttons path="certificateType" items="${fns:getEnumList('certificateType')}" itemLabel="name" itemValue="value" htmlEscape="false" class="required" onchange="certificateTypeChange(this)"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
		</div>

		<div class="control-group">
            <div class="control-left">
                <label class="control-label">公司名称：</label>
                <div class="controls">
                    <form:input id="companyName" path="companyName" htmlEscape="false" maxlength="100" value="${merchant.companyName}" class="input-xlarge required" onchange="isCompanyName(this)" placeholder="请输入字母、中文、中文字符（）"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">公司联系电话：</label>
                <div class="controls">
                    <form:input id="companyPhone" path="companyPhone" value ="${merchant.companyPhone}" htmlEscape="false" maxlength="50" class="input-xlarge required" onchange="checkPhone(this)" placeholder="010-58103559"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
        </div>

        <div class="control-group">
            <div class="control-left">
                <label class="control-label">证件地址：</label>
                <div class="controls">
                    <form:select id="provinceCode"  path="provinceCode" class="input-medium ">
                    </form:select>
                    <input type="hidden" id="merchantProvince" value="${merchant.provinceCode}"/>
                    <form:hidden id="provinceName" path="provinceName" value="${merchant.provinceName}"/>
                    <form:select id="cityCode" path="cityCode" class="input-medium ">
                    </form:select>
                    <input type="hidden" id="merchantCity" value="${merchant.cityCode}"/>
                    <form:hidden id="cityName" path="cityName" value="${merchant.cityName}"/>
                    <form:select id="countyCode" path="countyCode" class="input-medium ">
                    </form:select>
                    <input type="hidden" id="merchantCounty" value="${merchant.countyCode}" />
                    <form:hidden id="countyName" path="countyName" value="${merchant.countyName}"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
        </div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">公司注册地址：</label>
				<div class="controls">
					<form:input path="businessAddress" htmlEscape="false" maxlength="256" class="input-xlarge required" value="${merchant.businessAddress}" />
                    <span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">公司网址：</label>
				<div class="controls">
					<form:input id="website" path="website" htmlEscape="false" maxlength="256" class="input-xlarge required"  onchange="isWebsite(this)" placeholder="https://www.heepay.com或者www.heepay.com" value="${merchant.website}"/>
                    <span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">营业执照号码：</label>
				<div class="controls">
                    <form:input id="businessLicenseNo" path="businessLicenseNo" htmlEscape="false" maxlength="100" class="input-xlarge required" onchange="isNumberOrLetter(this)" placeholder="英文字母和数字和下划线" value="${merchant.businessLicenseNo}"/>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">营业执照结束时间：</label>
				<div class="controls">
                    <input type="hidden" id="businessLicenseEndTime" name="businessLicenseEndTime" value="${merchant.businessLicenseEndTime}"/>
                    <c:if test="${ merchant.businessLicenseEndTime=='Thu Nov 30 00:00:00 CST 2'}">
                        <input id="legal_time" type="text" readonly maxlength="20" class="input-medium Wdate"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
                        <input id="legal_long" type="checkbox" checked="checked" value="0000-00-00"></label><span>长期</span>
                    </c:if>
                    <c:if test="${ merchant.businessLicenseEndTime!='Thu Nov 30 00:00:00 CST 2'}">
                        <input id="legal_time" type="text" readonly maxlength="20" class="input-medium Wdate "
                               value="<fmt:formatDate value="${merchant.businessLicenseEndTime}" pattern="yyyy-MM-dd"/>"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
                        <input id="legal_long" type="checkbox"  value="0000-00-00"></label><span>长期</span>
                    </c:if>
                    <span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</div>

		<div class="control-group" id="organizeAndtaxRegist">
			<div class="control-left">
				<label class="control-label">组织机构代码：</label>
				<div class="controls">
                    <form:input id="organizationCode" path="organizationCode" htmlEscape="false" maxlength="100" class="input-xlarge required" onchange="isNotChinese(this)" placeholder="非汉字" value="${merchant.organizationCode}"/>
                    <span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">税务登记证号码：</label>
				<div class="controls">
                    <form:input id="taxRegistrationCertificateNo" path="taxRegistrationCertificateNo" value="${merchant.taxRegistrationCertificateNo}" htmlEscape="false" maxlength="100" class="input-xlarge required" onchange="isNumber(this)" placeholder="数字"/>
                    <span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">IPC备案号：</label>
            <div class="controls">
                <form:input path="ipcNo" value="${merchant.ipcNo}" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">法人名称：</label>
				<div class="controls">
                    <form:input id="llegalperson" path="llegalperson" htmlEscape="false" maxlength="200" value="${cbmsSuppliersetting.llegalperson}" class="input-xlarge required" onchange="isChinese(this)" placeholder="中文"/>
                    <span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-right">
                <label class="control-label">法人手机号码：</label>
                <div class="controls">
                    <form:input id="legalMobile" path="legalMobile" htmlEscape="false" class="input-xlarge required" maxlength="11" value="${merchant.legalMobile}" onchange="checkMobile(this)" placeholder="手机号"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
			</div>
		</div>

        <div class="control-group">
            <div class="control-left">
                <label class="control-label">法人代表身份证号：</label>
                <div class="controls">
                    <form:input id="legalIdcard" path="legalIdcard" value="${merchant.legalIdcard}" htmlEscape="false" maxlength="18" class="input-xlarge required" onchange="isIdCard(this)" placeholder="身份证号"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">法人代表证件有效期结束：</label>
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
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">联系人：</label>
				<div class="controls">
                    <form:input id="contactor" path="contactor" value="${merchant.contactor}" htmlEscape="false" maxlength="256" class="input-xlarge required" onchange="isChinese(this)" placeholder="中文"/>
                    <span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
            <div class="control-right">
                <label class="control-label">联系人手机号：</label>
                <div class="controls">
                    <form:input id="contactorPhone" path="contactorPhone" value="${merchant.contactorPhone}" htmlEscape="false" class="input-xlarge required" maxlength="11" onchange="checkMobile(this)" placeholder="手机号"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
		</div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">联系人身份证号：</label>
				<div class="controls">
                    <form:input id="contactorIdcardNo" path="contactorIdcardNo" value="${merchant.contactorIdcardNo}" htmlEscape="false" maxlength="18" class="input-xlarge required" onchange="isIdCard(this)" placeholder="身份证号"/>
                    <span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">联系人证件有效期结束：</label>
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
                        <input id="contact_long" type="checkbox" value="0000-00-00"></label><span>长期</span>
                    </c:if>
				</div>
			</div>
		</div>

		<div class="control-group">
            <div class="control-left">
                <label class="control-label">海关注册代码：</label>
                <div class="controls">
                    <form:input id="orgCustomsCode" path="orgCustomsCode" value="${cbmsSuppliersetting.orgCustomsCode}" htmlEscape="false" maxlength="64" class="input-xlarge required"  onchange="isNotChinese(this)" placeholder="非汉字" />
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">商户名称：</label>
                <div class="controls">
                    <form:input id="supplierName" path="supplierName" value="${cbmsSuppliersetting.supplierName}" htmlEscape="false" maxlength="200" class="input-xlarge required" onchange="checkCompanyName(this)" placeholder="请输入中文和字符（）" />
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
		</div>

		<div class="control-group">
            <label class="control-label">电商平台名称（总局备案名称）[国检]（跟汇元合作的电商平台）：</label>
            <div class="controls">
                <form:input id="enterpriseName" path="enterpriseName" value="${cbmsSuppliersetting.enterpriseName}" htmlEscape="false" maxlength="500" class="input-xlarge required" onchange="checkCompanyName(this)" placeholder="中文" />
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">电商平台编号（总局备案号）[国检]（跟汇元合作的电商平台）：</label>
            <div class="controls">
                <form:input id="centralOfficeNumber" path="centralOfficeNumber" value="${cbmsSuppliersetting.centralOfficeNumber}" htmlEscape="false" maxlength="500" class="input-xlarge required" onchange="isNotChinese(this)" placeholder="中文"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>

		<div class="control-group">
            <div class="control-left">
                <label class="control-label">平台商户名称（海关报备）：</label>
                <div class="controls">
                    <form:input id="orgCustomsName" path="orgCustomsName" value="${cbmsSuppliersetting.orgCustomsName}" htmlEscape="false"  maxlength="500" class="input-xlarge required"  onchange="checkCompanyName(this)" placeholder="请输入中文和字符（）"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
        </div>
        <div class="control-group">
            <div class="control-left">
                <label class="control-label">电商/店铺名称：</label>
                <div class="controls">
                    <form:input id="electricityName" path="electricityName" value="${cbmsSuppliersetting.electricityName}" htmlEscape="false"  maxlength="500" class="input-xlarge required" onchange="checkCompanyName(this)" placeholder="请输入中文和字符（）"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">电商/店铺网址：</label>
                <div class="controls">
                    <form:input id="electricityUrl" path="electricityUrl" value="${cbmsSuppliersetting.electricityUrl}" htmlEscape="false" maxlength="256" class="input-xlarge required"  onchange="isWebsite(this)" placeholder="https://www.heepay.com或者www.heepay.com" />
                </div>
            </div>
		</div>

		<div class="control-group">
			<label class="control-label">商户企业类别名称：</label>
			<div class="controls">
                <form:radiobuttons path="cbmsEnterpriseTypeName" items="${fns:getEnumList('cbmsEnterpriseType')}" itemLabel="name" itemValue="value" htmlEscape="false" class="required"/>
                <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户企业类型名称：</label>
			<div class="controls">
                <form:radiobuttons path="cbmsSupplierCategoryName" items="${fns:getEnumList('cbmsSupplierCategory')}" itemLabel="name" itemValue="value" htmlEscape="false" class="required"/>
                <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户贸易类型名称：</label>
			<div class="controls">
                <form:checkboxes path="cbmsTradeTypeNameList" items="${fns:getEnumList('cbmsTradeType')}" itemLabel="name" itemValue="value" htmlEscape="false" class="required"/>
                <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

        <div class="control-group">
            <label class="control-label">经营范围：</label>
            <div class="controls">
                <form:input id="businessScope" path="businessScope" value="${merchant.businessScope}" htmlEscape="false" maxlength="256" class="input-xlarge required"  onchange="checkCompanyName(this)" placeholder="请输入中文"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>

		<div class="control-group">
			<label class="control-label">开户许可证：</label>
			<div class="controls">
                <c:if test="${empty merchant.permitsAccounts}">
                    <input type="file" name="permitsAccountsFile" htmlEscape="false" maxlength="256" class="input-xlarge required" onchange="loadImage(this)"/>
                </c:if>
                <c:if test="${not empty merchant.permitsAccounts}">
                    <input type="file" name="permitsAccountsFile" htmlEscape="false" maxlength="256" class="input-xlarge " />
                    <a class="popA">图片</a>
                    <p class="pop_img">
                        <span>X</span>
                        <img src="${merchant.permitsAccounts}">
                    </p>
                    <form:hidden path="permitsAccounts" value="${merchant.permitsAccounts}" maxlength="256"/>
                </c:if>
                <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">法人代表证件照(正)：</label>
            <div class="controls">
                <c:if test="${empty merchant.legalCertificateFront}">
                    <input  type="file" name="legalCertificateFrontFile" htmlEscape="false" maxlength="256" class="input-xlarge required" onchange="loadImage(this)"/>
                </c:if>
                <c:if test="${not empty merchant.legalCertificateFront}">
                    <input  type="file" name="legalCertificateFrontFile" htmlEscape="false" maxlength="256" class="input-xlarge " />
                    <a class="popA">图片</a>
                    <p class="pop_img">
                        <span>X</span>
                        <img src="${merchant.legalCertificateFront}">
                    </p>
                    <form:hidden path="legalCertificateFront" value="${merchant.legalCertificateFront}" maxlength="256"/>
                </c:if>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">法人代表证件照(反)：</label>
			<div class="controls">
                <c:if test="${empty merchant.legalCertificateBack}">
                    <input  type="file" name="legalCertificateBackFile"  htmlEscape="false" maxlength="256" class="input-xlarge required" onchange="loadImage(this)"/>
                </c:if>
                <c:if test="${not empty merchant.legalCertificateBack}">
                    <input  type="file" name="legalCertificateBackFile"  htmlEscape="false" maxlength="256" class="input-xlarge " />
                    <a class="popA">图片</a>
                    <p class="pop_img">
                        <span>X</span>
                        <img src="${merchant.legalCertificateBack}">
                    </p>
                    <form:hidden path="legalCertificateBack" value="${merchant.legalCertificateBack}" maxlength="256"/>
                </c:if>
                <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">营业执照证（三证合一）：</label>
            <div class="controls">
                <c:if test="${empty merchant.businessLicenseFile}">
                    <input type="file" name="businessLicenseFileFrontFile"  htmlEscape="false" maxlength="256" class="input-xlarge required" onchange="loadImage(this)"/>
                </c:if>
                <c:if test="${not empty merchant.businessLicenseFile}">
                    <input type="file" name="businessLicenseFileFrontFile"  htmlEscape="false" maxlength="256" class="input-xlarge " />
                    <a class="popA">图片</a>
                    <p class="pop_img">
                        <span>X</span>
                        <img src="${merchant.businessLicenseFile}">
                    </p>
                    <form:hidden path="businessLicenseFile" value="${merchant.businessLicenseFile}" maxlength="256"/>
                </c:if>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>

		<div class="control-group" id="taxRegistrationCertificateFile">
			<label class="control-label">税务登记证：</label>
			<div class="controls">
                <c:if test="${empty merchant.taxRegistrationCertificate}">
                    <input type="file" name="taxRegistrationCertificateFile"  htmlEscape="false" maxlength="256" class="input-xlarge " onchange="loadImage(this)"/>
                </c:if>
                <c:if test="${not empty merchant.taxRegistrationCertificate}">
                    <input type="file" name="taxRegistrationCertificateFile"  htmlEscape="false" maxlength="256" class="input-xlarge " />
                    <a class="popA">图片</a>
                    <p class="pop_img">
                        <span>X</span>
                        <img src="${merchant.taxRegistrationCertificate}">
                    </p>
                    <form:hidden path="taxRegistrationCertificate" value="${merchant.taxRegistrationCertificate}" maxlength="256"/>
                </c:if>
                <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="organizationCodeCertificateFile">
			<label class="control-label">组织机构代码证：</label>
			<div class="controls">
                <c:if test="${empty merchant.organizationCodeCertificate}">
                    <input type="file" name="organizationCodeCertificateFile" htmlEscape="false" maxlength="256" class="input-xlarge " onchange="loadImage(this)"/>
                </c:if>
                <c:if test="${not empty merchant.organizationCodeCertificate}">
                    <input type="file" name="organizationCodeCertificateFile"  htmlEscape="false" maxlength="256" class="input-xlarge " />
                    <a class="popA">图片</a>
                    <p class="pop_img">
                        <span>X</span>
                        <img src="${merchant.organizationCodeCertificate}">
                    </p>
                    <form:hidden path="organizationCodeCertificate" value="${merchant.organizationCodeCertificate}" maxlength="256"/>
                </c:if>
                <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="cbms:cbmsMerchantSuppliersetting:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>