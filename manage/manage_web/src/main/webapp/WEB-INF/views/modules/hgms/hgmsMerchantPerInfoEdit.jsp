<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>资金归集商户管理</title>
	<meta name="decorator" content="default"/>
    <link href="${ctxStatic}/common/img.css" type="text/css" rel="stylesheet">
    <script type="text/javascript">
        $(document).ready(function() {
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

            if (${hgmsMerchantInfo.bankcardOwnerType == '0'}) {
                $("#bankcardOwnerIdcard").show();
            } else if(${hgmsMerchantInfo.bankcardOwnerType == '1' || hgmsMerchantInfo.bankcardOwnerType == '-1'}) {
                $("#bankcardOwnerIdcard").hide();
            }

            if(${hgmsMerchantInfo.certificateType == "MULTIP"}){
                $("#taxRegistrationCertificateFile").hide();
                $("#organizationCodeCertificateFile").hide();
            }else if(${hgmsMerchantInfo.certificateType == "ORDINA"} || ${hgmsMerchantInfo.certificateType == "INDIVI"}) {
                $("#taxRegistrationCertificateFile").show();
                $("#organizationCodeCertificateFile").show();
            }

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
            //团片点击显示隐藏效果
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

        //联动
        $(function(){
            //选择分类
            function showLocation(flag) {
                var title	= ['选择省份' , '选择城市'];
                $.each(title , function(k , v) {
                    title[k]	= '<option value="">'+v+'</option>';
                })
                $("#unionPayProvinceCode,#unionPayCityCode,#openBankCode").select2();
                $('#unionPayProvinceCode').change(function() {
                    $('#unionPayCityCode').empty();
                    $('#unionPayCityCode').append(title[1]);
                    var _province_id = $('#unionPayProvinceCode').val();
                    if(_province_id!=''){
                        get_cate_list('unionPayCityCode' , $('#unionPayProvinceCode').val(),"2");
                    }
                    $('#unionPayCityCode').change()
                })
                $('#unionPayCityCode').change(function() {
                    var _city_id = $('#unionPayCityCode').val();
                    $('input[name=location_id]').val(_city_id);
                })

                if(flag){
                    get_cate_list('unionPayProvinceCode' , '',"1");
                }
            }
            showLocation(true);
            function get_cate_list(el_id , loc_id,role,bankNo) {
                var el	= $('#'+el_id);
                var bankNo = $("#bankNo").val();
                $.ajax({
                    async: false,
                    type: "GET",
                    url: "${ctx}/route/city/select",
                    data: {'id':loc_id,'role':role,'bankNo':bankNo},
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
            if($('#unionPayProvinceCode1').val() != '' && $('#unionPayProvinceCode1').val() != null){
                $("#unionPayProvinceCode").find("option[value='"+$('#unionPayProvinceCode1').val()+"']").attr("selected",true);
                //showLocation();
                var province = $('#unionPayProvinceCode').val();
                $('#unionPayCityCode').empty();
                if(province != ''){
                    get_cate_list('unionPayCityCode' , province,"2");
                }
                if($('#unionPayCityCode1').val() != '' && $('#unionPayCityCode1').val() != null){
                    $("#unionPayCityCode").find("option[value='"+$('#unionPayCityCode1').val()+"']").attr("selected",true);
                    SelchangeOpenBank();
                    if($("#openBankCode1").val()!= '' && $("#openBankCode1").val()!= null){
                        $("#openBankCode").find("option[value='"+$("#openBankCode1").val()+"']").attr("selected",true);
                    }
                }
                showLocation(false);
            }
        });

        function SelchangeBank(a){
            $("#bankName").val(a);
            SelchangeOpenBank();
        }

        function SelchangeProvince(a){
            $("#unionPayProvinceName").val(a);
        }

        function SelchangeCity(a){
            $("#unionPayCityName").val(a);
            SelchangeOpenBank();
        }

        function SelOpenBank(a){
            $("#openBankName").val(a.text);
            var bankNo = $("#bankNo").val();
            var city = $("#unionPayCityCode").val();
            $("#associateLineNumber").val(bankNo+city+a.value);
        }



        function SelchangeOpenBank(){
            var bank= $("#bankNo").val();
            var city= $("#unionPayCityCode").val();
            if(bank != '' && city != ''){
                console.log(bank+"  city:"+city);
                $.ajax({
                    async: false,
                    type: "GET",
                    url: "${ctx}/route/city/getOpenBank",
                    data: {'city':city,'bankNo':bank},
                    dataType: 'json',
                    success: function(data){
                        //console.log(data);
                        $("#openBankCode").empty();
                        var optionDefault  = '<option value="">选择开户行</option>';
                        $('#openBankCode').append(optionDefault);
                        $.each(data , function(k , v) {
                            var option  = '<option value="'+v.id+'">'+v.name+'</option>';
                            $("#openBankCode").append(option);
                        })
                    },
                    error: function(){
                        console.log("请求失败!");
                    }
                });
            }
            /*  var openBank= $("#openBankCode").val();
             if(!reg.test(openBank)){
             $("#openBankCode").val("");
             $("#associateLineNumber").val(bank+city);
             }else{
             $("#associateLineNumber").val(bank+city+openBank);
             } */
        }
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
        //账户类型
        function companyTypeChange(option){
            var code = option.value;
            if(code == "HEADQUAR"){
                $("#superiorId").hide();
            }else if(code == "DIVISION") {
                $("#superiorId").show();
            }
        }
        function certificateTypeChange(option){
            var code = option.value;
            if(code == "MULTIP"){
                $("#taxRegistrationCertificateFile").hide();
                $("#organizationCodeCertificateFile").hide();
            }else if(code == "ORDINA" || code == "INDIVI") {
                $("#taxRegistrationCertificateFile").show();
                $("#organizationCodeCertificateFile").show();
            }
        }
        function bankcardOwnerTypeChange(option){
            var code = option.value;
            if(code == "0"){
                $("#bankcardOwnerIdcard").show();
            }else if(code == "1" || code == "-1") {
                $("#bankcardOwnerIdcard").hide();
            }
        }

        //输入框校验
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
                if(chineseName == "legalRepresentative"){
                    document.getElementById("legalRepresentative").value = "";
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
            }else{
                if(chineseName == "organizationCode"){
                    alert(prompt);
                    document.getElementById("organizationCode").value = "";
                }
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
        //用途：检查输入对象的值是否符合E-Mail格式
        function isEmail( str ){
            var emailsValue = str.value;
            var emailsName = str.name;
            var myReg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})$/;
            var prompt = "请输入正确邮箱！";
            if(myReg.test(emailsValue)) {
                return true;
            }else {
                alert(prompt);
                if(emailsName == "legalPersionEmail"){
                    document.getElementById("legalPersionEmail").value = "";
                }else if(emailsName == "contactorPersionEmail"){
                    document.getElementById("contactorPersionEmail").value = "";
                }
            }
        }

    </script>
</head>
<body>
	<ul class="nav nav-tabs">
        <c:if test="${goal == 2}" >
            <li><a href="${ctx}/hgms/hgmsMerchantInfo/">资金归集商户列表</a></li>
            <li class="active"><a href="${ctx}/hgms/hgmsMerchantInfo/edit?id=${hgmsMerchantInfo.merchantId}&goal=2">资金归集商户<shiro:hasPermission name="hgms:hgmsMerchantInfo:edit">${not empty hgmsMerchantInfo.merchantId?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="hgms:hgmsMerchantInfo:edit">查看</shiro:lacksPermission></a></li>
        </c:if>
        <c:if test="${goal == 4}" >
            <li ><a href="${ctx}/hgms/hgmsMerchantInfoLegal">资金归集法务审核列表</a></li>
            <li class="active"><a href="${ctx}/hgms/hgmsMerchantInfo/edit?id=${hgmsMerchantInfo.merchantId}&goal=4">法务审核修改</a></li>
        </c:if>
        <c:if test="${goal == 5}" >
            <li ><a href="${ctx}/hgms/hgmsMerchantInfoRc">资金归集风控审核列表</a></li>
            <li class="active"><a href="${ctx}/hgms/hgmsMerchantInfo/edit?id=${hgmsMerchantInfo.merchantId}&goal=5">风控审核修改</a></li>
        </c:if>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="hgmsMerchantInfo" action="${ctx}/hgms/hgmsMerchantInfo/updatePer?goal=${goal}" method="post" class="form-horizontal" enctype="multipart/form-data">
        <form:hidden path="merchantId" />
        <form:hidden path="email" />
        <form:hidden path="busiaddr" />
		<sys:message content="${message}"/>
        <div class="control-group">公司基本信息<hr width="80%" align="left" size="4" noshade=""></div>
        <div class="control-group">
            <div class="control-left">
                <label class="control-label">商户账号：</label>
                <div class="controls">
                    <form:input path="loginName" maxlength="64" readonly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">账户类型：</label>
                <div class="controls">
                    <form:radiobuttons path="companyType" items="${hgmsCompanyType}" itemLabel="name" itemValue="value" htmlEscape="false" class="required" onchange="companyTypeChange(this)"/>
                    <span class="help-inline"><font color="red">*</font></span>
                </div>
            </div>
        </div>
        <div class="control-group">
            <div class="control-left">
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
            <div class="control-right" id="superiorId">
                <label class="control-label">集团总部的ID：</label>
                <div class="controls">
                    <form:input path="superiorId" htmlEscape="false" maxlength="64" class="input-medium required" />
                </div>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">单位类型：</label>
            <div class="controls">
                <form:radiobuttons path="type" items="${fns:getEnumList('merchantType')}" itemLabel="name" itemValue="value" htmlEscape="false" class="required" />
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group">负责人基本信息<hr width="80%" align="left" size="4" noshade=""></div>
        <div class="control-group">
            <div class="control-left">
                <label class="control-label">联系人：</label>
                <div class="controls">
                    <form:input id="contactor" path="contactor" htmlEscape="false" maxlength="256" class="input-xlarge required" onchange="isChinese(this)" placeholder="中文"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">联系人手机号：</label>
                <div class="controls">
                    <form:input id="contactorPhone" path="contactorPhone" htmlEscape="false" class="input-xlarge required" maxlength="11" onchange="checkMobile(this)" placeholder="手机号"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
        </div>

        <div class="control-group">
            <div class="control-left">
                <label class="control-label">联系人身份证号：</label>
                <div class="controls">
                    <form:input id="contactorIdcardNo" path="contactorIdcardNo" htmlEscape="false" maxlength="18" class="input-xlarge required" onchange="isIdCard(this)" placeholder="身份证号"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">联系人证件有效期：</label>
                <div class="controls">
                    <input type="hidden" id="contactorCertificateEndTime" name="contactorCertificateEndTime" value="${hgmsMerchantInfo.contactorCertificateEndTime}"/>
                    <c:if test="${ hgmsMerchantInfo.contactorCertificateEndTime=='Thu Nov 30 00:00:00 CST 2'}">
                        <input id="contact_time" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
                        <input id="contact_long" type="checkbox" checked="checked" value="0000-00-00"></label><span>长期</span>
                    </c:if>
                    <c:if test="${ hgmsMerchantInfo.contactorCertificateEndTime!='Thu Nov 30 00:00:00 CST 2'}">
                        <input id="contact_time" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                               value="<fmt:formatDate value="${hgmsMerchantInfo.contactorCertificateEndTime}" pattern="yyyy-MM-dd"/>"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
                        <input id="contact_long" type="checkbox" value="0000-00-00"></label><span>长期</span>
                    </c:if>
                    <span class="help-inline"><font color="red">*</font></span>
                </div>
            </div>
        </div>
        <div class="control-group">
            <div class="control-left">
                <label class="control-label">联系人邮箱：</label>
                <div class="controls">
                    <form:input id="contactorPersionEmail" path="contactorPersionEmail" htmlEscape="false" maxlength="100" class="input-xlarge " onchange="isEmail(this)" placeholder="邮箱"/>
                    <span class="help-inline"><font color="red">*</font></span>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">联系人联系地址：</label>
                <div class="controls">
                    <form:input path="contactorPersionAddress" htmlEscape="false" maxlength="256" class="input-xlarge required"/>
                    <span class="help-inline"><font color="red">*</font></span>
                </div>
            </div>
        </div>
        <div class="control-group">结算银行卡信息<hr width="80%" align="left" size="4" noshade=""></div>

        <div class="control-group">
            <div class="control-left">
                <label class="control-label">银行卡号：</label>
                <div class="controls">
                    <form:input id="bankcardNo" path="bankcardNo" htmlEscape="false" maxlength="19" class="input-xlarge required" onchange="isNumber(this)" placeholder="14 - 19 的数字"/>
                    <span class="help-inline"><font color="red">*</font></span>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">银行卡持卡人名称：</label>
                <div class="controls">
                    <form:input id="bankcardOwnerName" path="bankcardOwnerName" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkCompanyName(this)"  placeholder="中文"/>
                    <span class="help-inline"><font color="red">*</font></span>
                </div>
            </div>
        </div>
        <div class="control-group">
            <div class="control-left">
                <label class="control-label">银行预留手机号：</label>
                <div class="controls">
                    <form:input id="bankcardOwnerMobile" path="bankcardOwnerMobile" htmlEscape="false" maxlength="256" class="input-xlarge required" onchange="checkMobile(this)" placeholder="手机号"/>
                    <span class="help-inline"><font color="red">*</font></span>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">银行卡有效期：</label>
                <div class="controls">
                    <form:input path="bankcardExpiredDate" htmlEscape="false" maxlength="16" class="input-xlarge required" placeholder="209909"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
        </div>
        <div class="control-group">
            <div class="control-left">
                <label class="control-label">银行卡持卡人类型：</label>
                <div class="controls">
                    <form:select id="bankcardOwnerType" path="bankcardOwnerType" class="input-xlarge required" onchange="bankcardOwnerTypeChange(this)">
                        <form:option value="" label="-选择账户类型-"/>
                        <c:forEach items="${bankcardOwnerType}" var="tpdsAccountTypeStatus">
                            <form:option value="${tpdsAccountTypeStatus.value}" label="${tpdsAccountTypeStatus.name}"/>
                        </c:forEach>
                    </form:select>
                    <span class="help-inline"><font color="red">*</font></span>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">银行卡类型：</label>
                <div class="controls">
                    <form:select path="bankcardType" class="input-xlarge required" >
                        <form:option value="" label=""/>
                        <form:options items="${hgmsBankcardType}" itemLabel="name" itemValue="value" htmlEscape="false" />
                    </form:select>
                    <span class="help-inline"><font color="red">*</font></span>
                </div>
            </div>
        </div>
        <div class="control-group"  >
            <div class="controls" style="display: none" >
                <form:input path="bankName" id="bankName"  htmlEscape="false" maxlength="50" class="input-xlarge "/>
            </div>
        </div>
        <input type="hidden" id="unionPayProvinceCode1" value="${hgmsMerchantInfo.unionPayProvinceCode}"/>
        <input type="hidden" id="unionPayCityCode1" value="${hgmsMerchantInfo.unionPayCityCode}"/>
        <input type="hidden" id="openBankCode1" value="${hgmsMerchantInfo.openBankCode}"/>
        <div class="control-group">
            <div class="control-left">
                <label class="control-label">银行名称：</label>
                <div class="controls" >
                    <form:select id="bankNo" path="bankNo"  class="input-xlarge required" onchange="SelchangeBank(this.options[this.options.selectedIndex].text);" >
                        <form:option value="" label="请选择"/>
                        <form:options items="${fns:getBankInfoList()}" itemLabel="bankName" itemValue="bankNo" htmlEscape="false"/>
                    </form:select>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
            <div class="control-group" style="display: none"  >
                <div class="controls">
                    <form:input path="unionPayProvinceName" id="unionPayProvinceName" htmlEscape="false" maxlength="50" class="input-xlarge "/>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">省份名称：</label>
                <div class="controls">
                    <form:select id="unionPayProvinceCode" path="unionPayProvinceCode" class="input-xlarge required" onchange="SelchangeProvince(this.options[this.options.selectedIndex].text);">
                        <form:option value="" label="选择省份"/>
                    </form:select>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
        </div>
        <div class="control-group" style="display: none">
            <div class="controls">
                <form:input path="unionPayCityName" id="unionPayCityName" htmlEscape="false" maxlength="50" class="input-xlarge "/>
            </div>
        </div>
        <div class="control-group">
            <div class="control-left">
                <label class="control-label">城市名称：</label>
                <div class="controls">
                    <form:select path="unionPayCityCode" id="unionPayCityCode" class="input-xlarge required"  onchange="SelchangeCity(this.options[this.options.selectedIndex].text);">
                        <form:option value="" label="选择城市"/>
                    </form:select>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
            <div class="control-right" >
                <label class="control-label">开户银行名称：</label>
                <div class="controls">
                    <form:select path="openBankCode" id="openBankCode" class="input-xlarge required"  onchange="SelOpenBank(this.options[this.options.selectedIndex]);">
                        <form:option value="" label="选择开户行"/>
                    </form:select>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
        </div>
        <form:hidden path="openBankName" id="openBankName"  htmlEscape="false"/>
        <div class="control-group">
            <div class="control-left">
                <label class="control-label">联行号：</label>
                <div class="controls">
                    <form:input id="associateLineNumber" path="associateLineNumber" htmlEscape="false" maxlength="12" class="input-xlarge required"  readonly="true" />
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
            <div class="control-right" id="bankcardOwnerIdcard">
                <label class="control-label">持卡人身份证号：</label>
                <div class="controls">
                    <form:input id="cardOwnerIdcard" path="bankcardOwnerIdcard" htmlEscape="false" maxlength="18" class="input-xlarge " onchange="isIdCard(this)"/>
                </div>
            </div>
        </div>
        <form:hidden id="legalAuditStatus" path="legalAuditStatus" />
        <form:hidden id="rcAuditStatus" path="rcAuditStatus" />
		<div class="form-actions">
			<shiro:hasPermission name="hgms:hgmsMerchantInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>