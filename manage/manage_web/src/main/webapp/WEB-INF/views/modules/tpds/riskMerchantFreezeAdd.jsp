<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>资金存管</title>
    <meta name="decorator" content="default"/>
    <script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
    <style>
        #main {
            margin: 50px;
        }

        .modal-body {
            width: 285px;
            margin: auto;
            position: relative;
            max-height: 400px;
            padding: 10px;
            overflow-y: inherit;
        }

        .modal-body label {
            width: 90px;
            text-align: right;
        }

        .pop {
            display: none;
            margin-left: 17px;
        }

        .pop label {
            width: 90px;
            text-align: right;
        }
    </style>
    <script type="text/javascript">

        $(document).ready(function () {

            $(".checkValue").on("blur", function () {
                var pattern = new RegExp("[`~!#$^@&*()=|{}':;',\\[\\]<>/?~！#￥……&*（）——|{}【】‘；：”“'。，、？]");
                var this_value = $(this).val();
                var this_id = this.id;

                if (pattern.test(this_value)) {
                    $("#" + this_id + "Id").text("输入不合法，不能输入特殊字符，请重新输入");
                    $(this).val("");
                } else {
                    $("#" + this_id + "Id").text("");
                }
            });

            $("#inputForm").validate({
                submitHandler: function (form) {

                    var associateLineNumber = $('#associateLineNumber').val();
                    var bankNo = $('#bankNo').val();

                    var bankName = $("#bankName").val();

                    if (bankName == '') {
                        $("#bankIdId").text("请选择银行名称");
                        return false;
                    } else {
                        $("#bankIdId").text("");
                    }


                    if (isNaN(associateLineNumber)) {

                        $("#associateLineNumberId").text("联行号请输入数字");
                        return false;
                    } else {
                        $("#associateLineNumberId").text("");
                    }

                    if (isNaN(bankNo)) {
                        $("#bankNoId").text("银行账号输入数字");
                        return false;
                    } else {
                        $("#bankNoId").text("");
                    }

                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });

            //联动
            $(function () {
                //选择分类
                function showLocation(flag) {
                    var title = ['选择省份', '选择城市'];
                    $.each(title, function (k, v) {
                        title[k] = '<option value="">' + v + '</option>';
                    })
                    $("#provinceCode,#cityCode,#openBankCode").select2();
                    $('#provinceCode').change(function () {
                        $('#cityCode').empty();
                        $('#cityCode').append(title[1]);
                        var _province_id = $('#provinceCode').val();
                        if (_province_id != '') {
                            get_cate_list('cityCode', $('#provinceCode').val(), "2");
                        }
                        $('#cityCode').change()
                    })
                    $('#cityCode').change(function () {
                        var _city_id = $('#cityCode').val();
                        $('input[name=location_id]').val(_city_id);
                    })

                    if (flag) {
                        get_cate_list('provinceCode', '', "1");
                    }
                }

                showLocation(true);
                function get_cate_list(el_id, loc_id, role, bankNo) {
                    var el = $('#' + el_id);
                    var bankNo = $("#bankNo").val();
                    console.log(bankNo);
                    $.ajax({
                        async: false,
                        type: "GET",
                        url: "${ctx}/route/city/selectLineNumber",
                        data: {'id': loc_id, 'role': role, 'bankNo': bankNo},
                        dataType: 'jsonp',
                        jsonp: 'callback',
                        success: function (data) {
                            //console.log(data);
                            $.each(data, function (k, v) {
                                var option = '<option value="' + v.id + '">' + v.name + '</option>';
                                el.append(option);
                            })
                        },
                        error: function () {
                            console.log("请求失败!");
                        }
                    });
                }

            });
        });

        function SelchangeBank(a) {
            $("#bankName").val(a);
            SelchangeOpenBank();
        }

        function SelchangeProvince(a) {
            $("#provinceName").val(a);
        }

        function SelchangeCity(a) {
            $("#cityName").val(a);
            SelchangeOpenBank();
        }

        function Sel2change(field) {
            $("#bankName").val(field);
        }

        function SelOpenBank(a){
            $("#openBankName").val(a.text);
            var bankNo = $("#bankNo").val();
            var city = $("#cityCode").val();
            var line = $("#openBankCode option:selected").data('line');
            $("#associateLineNumber").val(line);
        }

        function SelchangeOpenBank(){
            var bank= $("#bankNo").val();
            var city= $("#cityCode").val();
            var role = '3';
            if(bank != '' && city != ''){
                $.ajax({
                    async: false,
                    type: "GET",
                    url: "${ctx}/route/city/selectLineNumber",
                    data: {'id':city,'role': role,'bankNo':bank},
                    dataType: 'jsonp',
                    jsonp: 'callback',
                    success: function(data){
                        //console.log(data);
                        $("#openBankCode").empty();
                        var optionDefault  = '<option value="">选择开户行</option>';
                        $('#openBankCode').append(optionDefault);
                        $.each(data , function(k , v) {
                            var option  = '<option data-line="'+v.associateLineNumber+'" value="'+v.openBankCode+'">'+v.openBankName+'</option>';
                            $("#openBankCode").append(option);
                        })
                    },
                    error: function(){
                        console.log("请求失败!");
                    }
                });
            }
        }

    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a>添加银行卡</a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="merchantBankCard"
           action="${ctx}/tpds/tpdsMerchant/saveEntity/merchantBankCard" method="post" class="form-horizontal">

    <sys:message content="${message}"/>
    <input type="hidden" id="provinceCode1" value="${merchantBankCard.provinceCode}"/>
    <input type="hidden" id="cityCode1" value="${merchantBankCard.cityCode}"/>
    <input type="hidden" id="openBankCode1" value="${merchantBankCard.openBankCode}"/>


    <div class="control-group">
        <label class="control-label">银行卡号：</label>
        <div class="controls">
            <form:input path="bankId" id="bankId" value="${merchantBankCard.bankId}" htmlEscape="false" maxlength="20"
                        placeholder="请输入银行卡号" style="width:300px;" class="required"/>
            <span class="help-inline"><font color="red">*</font> </span>
            <label id="bankIdId" for="bankId" class="info" style="color: red"></label>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">银行卡持有人：</label>
        <div class="controls">
            <form:input path="recAccName" id="recAccName" value="${merchantBankCard.recAccName}" htmlEscape="false"
                        maxlength="20" placeholder="请输入银行卡持有人姓名" style="width:300px;" class="required"/>
            <span class="help-inline"><font color="red">*</font> </span>
            <label id="recAccNameId" for="recAccName" class="info" style="color: red"></label>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">账户类型：</label>
        <div class="controls">
            <form:select id="accountType" path="accountType" class="input-xlarge required" style="width:315px;">
                <form:option value="" label="-选择账户类型-"/>
                <c:forEach items="${accountTypeList}" var="tpdsAccountTypeStatus">
                    <form:option value="${tpdsAccountTypeStatus.value}" label="${tpdsAccountTypeStatus.name}"/>
                </c:forEach>
            </form:select>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">银行卡类型：</label>
        <div class="controls">
            <form:select id="cardTypeCode" path="cardTypeCode" class="input-xlarge required" style="width:315px;">
                <form:option value="" label="-选择银行卡类型-"/>
                <c:forEach items="${cardTypeList}" var="tpdsCardTypeCodeStatus">
                    <form:option value="${tpdsCardTypeCodeStatus.value}" label="${tpdsCardTypeCodeStatus.name}"/>
                </c:forEach>
            </form:select>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">签约类型：</label>
        <div class="controls">
            <form:select id="bankCardAuthType" path="bankCardAuthType" class="input-xlarge required"
                         style="width:315px;">
                <form:option value="" label="-选择签约类型-"/>
                <c:forEach items="${bankcardAuthTypeList}" var="tpdsBankCardAuthTypeStatus">
                    <form:option value="${tpdsBankCardAuthTypeStatus.value}"
                                 label="${tpdsBankCardAuthTypeStatus.name}"/>
                </c:forEach>
            </form:select>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">银行名称：</label>
        <div class="controls">
            <form:select id="bankNo" path="bankNo" class="input-xlarge required"
                         onchange="SelchangeBank(this.options[this.options.selectedIndex].text);" style="width:315px;">
                <form:option value="" label="请选择"/>
                <form:options items="${fns:getBankInfoList()}" itemLabel="bankName" itemValue="bankNo"
                              htmlEscape="false"/>
            </form:select>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
     <form:hidden path="bankName" id="bankName" htmlEscape="false"/>
    <div class="control-group" style="display: none">
        <div class="controls">
            <form:input path="provinceName" id="provinceName" htmlEscape="false" maxlength="50" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">省份名称：</label>
        <div class="controls">
            <form:select id="provinceCode" path="provinceCode" class="input-xlarge required"
                         onchange="SelchangeProvince(this.options[this.options.selectedIndex].text);"
                         style="width:315px;">
                <form:option value="" label="选择省份"/>
            </form:select>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group" style="display: none">
        <div class="controls">
            <form:input path="cityName" id="cityName" htmlEscape="false" maxlength="50" class="input-xlarge "/>
        </div>
    </div>
    <form:hidden path="provinceName" id="provinceName" htmlEscape="false"/>
    <div class="control-group">
        <label class="control-label">城市名称：</label>
        <div class="controls">
            <form:select path="cityCode" id="cityCode" class="input-xlarge required"
                         onchange="SelchangeCity(this.options[this.options.selectedIndex].text);" style="width:315px;">
                <form:option value="" label="选择城市"/>
            </form:select>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
     <form:hidden path="cityName" id="cityName" htmlEscape="false"/>
    <div class="control-group">
        <label class="control-label">开户银行名称：</label>
        <div class="controls">
            <form:select path="openBankCode" id="openBankCode" onchange="SelOpenBank(this.options[this.options.selectedIndex]);" style="width:315px;">
                <form:option value="" label="选择开户行"/>
            </form:select>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <form:hidden path="openBankName" id="openBankName" htmlEscape="false"/>
    <div class="control-group">
        <label class="control-label">联行号：</label>
        <div class="controls">
            <form:input id="associateLineNumber" path="associateLineNumber" htmlEscape="false" maxlength="12"
                        readonly="true" style="width:300px;"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <input type="hidden" name="merchantId" value="${merchantId}">

    <div class="form-actions">
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>

</form:form>

</body>
</html>