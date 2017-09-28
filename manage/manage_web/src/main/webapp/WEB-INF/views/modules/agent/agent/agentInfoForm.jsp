<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>代理商信息管理管理</title>
    <meta name="decorator" content="default"/>
    <link href="${ctxStatic}/agent/base.css" rel="stylesheet">
    <link href="${ctxStatic}/agent/style.css" rel="stylesheet">
    <script src="${ctxStatic}/agent/bank_card.js"></script>
    <script src="${ctxStatic}/agent/upload_normal.js"></script>
    <script src="${ctxStatic}/agent/webuploader.min.js"></script>


    <style>
        .upload_img, .upload_tip, .upload_one {
            position: relative;
        }

        .look_big_img {
            color: #00b7ee;
            cursor: pointer;
        }

        .big_img_close {
            float: right;
            margin-right: 10px;
            margin-top: 10px;
            cursor: pointer;

        }

        .big_img_box {
            position: absolute;
            top: 0px;
            left: 0px;
            display: none;
            z-index: 999;
        }

        .big_img_box img {
            width: 500px;
            max-width: none;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function () {

            $('#busiType').trigger("onchange");

            //点击显示图片
            $(".look_big_img").on("click", function () {
                $(this).parent().siblings(".big_img_box").show();
            });
            //点击隐藏图片
            $(".big_img_close").on("click", function () {
                $(".big_img_box").hide();
            });


            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function (form) {
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
            //选择分类
            function showLocation(province, city, town, flag) {
                var title = [province, city, town];
                $.each(title, function (k, v) {
                    title[k] = '<option value="">' + v + '</option>';
                })
                $("#provinceCode,#cityCode,#countyCode").select2();
                $('#provinceCode').change(function () {
                    $("#provinceName").val($(this).find("option:selected").text());
                    $('#cityCode').empty();
                    $('#cityCode').append(title[1]);
                    var _province_id = $('#provinceCode').val();
                    //console.log(_province_id);
                    if (_province_id != '') {
                        get_cate_list('cityCode', $('#provinceCode').val(), "2");
                    }
                    $('#cityCode').change();
                })

                $('#cityCode').change(function () {
                    $("#cityName").val($(this).find("option:selected").text());
                    $('#countyCode').empty();
                    $('#countyCode').append(title[2]);
                    var _city_id = $('#cityCode').val();
                    if (_city_id != '' && _city_id != '0') {
                        get_cate_list('countyCode', $('#cityCode').val(), "3");
                    }
                    $('#countyCode').change();
                })

                $('#countyCode').change(function () {
                    $("#countyName").val($(this).find("option:selected").text());
                    var _town_id = $(this).val();
                    $('input[name=location_id]').val(_town_id);
                    /*if(_town_id!='' && _town_id!='0'){
                     change_url($(this).val());
                     }*/

                })
                if (flag) {
                    $('#provinceCode').append(title[0]);
                    $('#cityCode').append(title[1]);
                    $('#countyCode').append(title[2]);
                    get_cate_list('provinceCode', '', "1");
                }
            }

            showLocation('省', '市', '区（县）', true);
            function get_cate_list(el_id, loc_id, role) {
                var el = $('#' + el_id);
                $.ajax({
                    async: false,
                    type: "GET",
                    url: "${ctx}/merchant/three/select",
                    data: {'id': loc_id, 'role': role},
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

            if ($('#merchantProvince').val() != '' && $('#merchantProvince').val() != null) {
                $("#provinceCode").find("option[value='" + $('#merchantProvince').val() + "']").attr("selected", true);
                //showLocation();
                var province = $('#provinceCode').val();
                $('#cityCode').empty();
                if (province != '') {
                    get_cate_list('cityCode', province, "2");
                }
                $("#cityCode").find("option[value='" + $('#merchantCity').val() + "']").attr("selected", true);

                if ($('#merchantCity').val() != '' && $('#merchantCity').val() != null) {
                    var city = $('#cityCode').val();
                    $('#countyCode').empty();
                    get_cate_list('countyCode', city, "3");
                    $("#countyCode").find("option[value='" + $('#merchantCounty').val() + "']").attr("selected", true);
                }
                showLocation('', '', '', false);
            }

            $("#headPhone").blur(function(){
                var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
                if(!myreg.test($("#headPhone").val()))
                {
                    /*alert('请输入有效的手机号码！');*/
                    $("#headPhone").append("<p>请输入正确的手机号</p>");
                    return false;
                }
            })

            if($('#id').val().length>0){   //说明是修改
                $('#img1sub').val(getRealPath($('#_img1sub').attr('src')));
                $('#img2sub').val(getRealPath($('#_img2sub').attr('src')));
                $('#img3sub').val(getRealPath($('#_img3sub').attr('src')));
                $('#img4sub').val(getRealPath($('#_img4sub').attr('src')));
                $('#img5sub').val(getRealPath($('#_img5sub').attr('src')));
                $('#img6sub').val(getRealPath($('#_img6sub').attr('src')));
            }

            jQuery.validator.addMethod("checkHeadPhone", function(value, element) {
                var id = $("#id").val();
                var flag = true;
                $.ajax({
                    url: "${ctx}/agent/agent/agentInfo/checkHeadPhone",
                    type: 'POST',
                    data: 'headPhone='+value+"&id="+id,
                    async: false,
                    dataType:'text',
                    success: function(data){
                        if(data!=null&&data=='success'){
                            flag= true;
                        }else{
                            flag= false;
                        }
                    }

                });
                return flag;
            }, "负责人手机号已存在");
        });

        //去掉图片的ip地址
        function getRealPath(source){
            var newStr='';
            if(source!=''){
                var strArr=source.split('/');
                $.each(strArr,function(index,obj){
                    if(index>2){
                        if(index==strArr.length-1){
                            newStr+=obj;
                        }
                        else {
                            newStr+=obj+'/';
                        }
                    }
                });
            }
            return newStr;
        }

        function changeBusiType(obj) {
            obj=$(obj);
            //动态显示，选择多证合一则隐藏   税务登记证和组织机构代码证  orgInstCode taxRegCode
            if(obj.val()=='GENERA'){   //普通
                $('#hideOrShow').show('fast');
                if(!($("#orgInstCode").hasClass('required'))){
                    $("#orgInstCode").addClass("required");
                }
                if(!($("#taxRegCode").hasClass('required'))){
                    $("#taxRegCode").addClass("required");
                }

            }
            else{                     //多证合一
                $('#hideOrShow').hide('fast');
                if(($("#orgInstCode").hasClass('required'))){
                    $("#orgInstCode").removeClass("required");
                }
                if(($("#taxRegCode").hasClass('required'))){
                    $("#taxRegCode").removeClass("required");
                }
            }

        }
    </script>
</head>
<body>
<!--弹窗-->
<div id="cover"></div>
<div id="cover_two"></div>
<div class="loading"><img src="${pageContext.request.contextPath}/static/images/ajax-loader.gif"></div>
<div class="pop_upload_box">
    <h3><em></em><span><img src="${pageContext.request.contextPath}/static/images/del.png"></span></h3>
    <p><img src=""></p>
</div>
<div class="pop_upload_message">
    <div class="pop_hd">
        <p>提示<img src="${pageContext.request.contextPath}/static/images/del.png"></p>
    </div>
    <p class="upload_text_tip"></p>
</div>
<input type="hidden" id="agent_project_name" value="${pageContext.request.contextPath}">
<%--<ul class="nav nav-tabs">--%>
    <%--<li><a href="${ctx}/agent/agent/agentInfo/">代理商列表</a></li>--%>
    <%--<li class="active"><a href="${ctx}/agent/agent/agentInfo/form?id=${agentInfo.id}">代理商<shiro:hasPermission--%>
            <%--name="agent:agent:agentInfo:edit">${not empty agentInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission--%>
            <%--name="agent:agent:agentInfo:view">查看</shiro:lacksPermission></a></li>--%>
<%--</ul>--%>
<br/>
<form:form id="inputForm" modelAttribute="agentInfo" action="${ctx}/agent/agent/agentInfo/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <h3>基本信息</h3>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">代理商名称：</label>
            <div class="controls">
                <form:input path="agentName" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">代理商名称简称：</label>
            <div class="controls">
                <form:input path="shortName" htmlEscape="false" maxlength="50" class="input-xlarge "/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">负责人姓名：</label>
            <div class="controls">
                <form:input path="headName" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">负责人手机号：</label>
            <div class="controls">
                <form:input id="headPhone" path="headPhone" htmlEscape="false" maxlength="11" class="input-xlarge required checkHeadPhone"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">负责人身份证号：</label>
            <div class="controls">
                <form:input path="headIdcard" htmlEscape="false" maxlength="18" class="input-xlarge required card"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">身份证有效期：</label>
            <div class="controls">
                <input name="headIdcardExpiry" path="headIdcardExpiry" type="text" readonly="readonly" maxlength="20"
                       class="input-medium Wdate required"
                       value="${agentInfo.headIdcardExpiry}"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">电子邮箱：</label>
            <div class="controls">
                <form:input path="email" htmlEscape="false" maxlength="50" type="email" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">代理商有效期：</label>
            <div class="controls">
                <form:input name="agentExpiry" path="agentExpiry" type="text" readonly="readonly" maxlength="20"
                       class="input-medium Wdate required"
                       value="${agentInfo.agentExpiry}"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
    </div>
    <h3>企业信息</h3>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">法人姓名：</label>
            <div class="controls">
                <form:input path="legalName" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">法人手机号：</label>
            <div class="controls">
                <form:input path="legalPhone" htmlEscape="false" maxlength="11" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">法人身份证号：</label>
            <div class="controls">
                <form:input path="legalIdcard" htmlEscape="false" maxlength="18" class="input-xlarge required card"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">法人身份证有效期：</label>
            <div class="controls">
                <input name="legalIdcardExpiry" path="legalIdcardExpiry" type="text" readonly="readonly" maxlength="20"
                       class="input-medium Wdate required"
                       value="${agentInfo.legalIdcardExpiry}"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">营业证照类型：</label>
            <div class="controls">
                <form:select path="busiType" class="input-xlarge required" onchange="changeBusiType(this)">
                    <form:options items="${busiTypeList}" itemLabel="value" itemValue="name" htmlEscape="false"/>
                </form:select>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">营业证照编号：</label>
            <div class="controls">
                <form:input path="busiCode" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">营业证照有效期：</label>
            <div class="controls">
                <form:input name="busiExpiry" path="busiExpiry" type="text" readonly="readonly" maxlength="20"
                       class="input-medium Wdate required"
                       value="${agentInfo.busiExpiry}"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">注册资金：</label>
            <div class="controls">
                <form:input path="regAmount" htmlEscape="false" class="input-xlarge required" maxlength="18" type="number"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
    </div>
    <div class="control-group" id="hideOrShow">
        <div class="control-left">
            <label class="control-label">组织机构代码证：</label>
            <div class="controls">
                <form:input path="orgInstCode" htmlEscape="false" maxlength="30" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">税务登记证：</label>
            <div class="controls">
                <form:input path="taxRegCode" htmlEscape="false" maxlength="30" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">所在地：</label>
        <div class="controls">
            <form:select id="provinceCode" path="addrProvinceCode" class="input-medium">
            </form:select>
            <input type="hidden" id="merchantProvince" value="${agentInfo.addrProvinceCode}"/>
            <form:hidden id="provinceName" path="addrProvinceName"/>
            <form:select id="cityCode" path="addrCityCode" class="input-medium">
            </form:select>
            <input type="hidden" id="merchantCity" value="${agentInfo.addrCityCode}"/>
            <form:hidden id="cityName" path="addrCityName"/>
            <form:select id="countyCode" path="addrCountryCode" class="input-medium">
            </form:select>
            <input type="hidden" id="merchantCounty" value="${agentInfo.addrCountryCode}"/>
            <form:hidden id="countyName" path="addrCountryName"/>
            <form:input path="addrDetail" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
                <%--<form:select path="addrProvinceName" class="input-xlarge required">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
                <form:select path="addrCityName" class="input-xlarge required">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
                <form:input path="addrDetail" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>--%>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">代理区域：</label>
        <div class="controls">
            <form:input path="agentAreaAddr" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>

    <%--		<div class="control-group">
                <label class="control-label">代理区域省份名称：</label>
                <div class="controls">
                    <form:select path="agentAreaProvinceName" class="input-xlarge required">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">代理区域城市名称：</label>
                <div class="controls">
                    <form:select path="agentAreaCityName" class="input-xlarge required">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>--%>
<%--    <h3>账户信息</h3>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">账户类型：</label>
            <div class="controls">
                &lt;%&ndash;<form:select path="bankAccountType" class="input-xlarge required">&ndash;%&gt;
                    &lt;%&ndash;<form:options items="${bankAccountTypeList}" itemLabel="value" itemValue="name" htmlEscape="false"/>&ndash;%&gt;
                &lt;%&ndash;</form:select>&ndash;%&gt;
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-right" id="open_bank_div">
            <label class="control-label">开户行：</label>
            <div class="controls">
                <div class="company_sel_one">
                    <select id="open_bank" path="bankName" style="width:367px;">
                        <option value=''>选择开户银行</option>
                    </select>
                    <input type="hidden" id="bankId" name="bankCode"/>
                    <input type="hidden" id="bankName" name="bankName"/>
                    &lt;%&ndash;<input type="hidden" id="bankNameId" value="${agentInfo.bankCode}"/>&ndash;%&gt;
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left" id="open_bank_pri">
            <label class="control-label">开户行所在地：</label>
            <div class="controls">
                <div class="company_sel_two">
                    <select id="loc_province" path="bankProvinceName" style="width:120px;"></select>
                    <input type="hidden" id="bankProvinceCode" name="bankProvinceCode"/>
                    <input type="hidden" id="bankProvinceName" name="bankProvinceName"/>
                    &lt;%&ndash;<input type="hidden" id="bnkProvinceCode" value="${agentInfo.bankProvinceCode}"/>&ndash;%&gt;
                    <select id="loc_city" path="bankCityName" style="width:120px;"></select>
                    <input type="hidden" id="bankCityCode" name="bankCityCode"/>
                    <input type="hidden" id="bankCityName" name="bankCityName"/>
                    &lt;%&ndash;<input type="hidden" id="bnkcityCode" value="${agentInfo.bankCityCode}"/>&ndash;%&gt;
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
        </div>
        <div class="control-right" id="open_bank_branch">
            <label class="control-label">开户支行：</label>
            <div class="controls">
                    &lt;%&ndash;<div class="open_bank_problem">&ndash;%&gt;
                <select id="open_bank_name" path="bankBranchName" style="width:367px;">
                    <option value=''>选择开户支行</option>
                </select>
                <input type="hidden" id="bankBranchCode" name="bankBranchCode"/>
                <input type="hidden" id="bankBranchName" name="bankBranchName"/>
                &lt;%&ndash;<input type="hidden" id="bnkBranchCode" value="${agentInfo.bankBranchCode}"/>&ndash;%&gt;
                <span class="help-inline"><font color="red">*</font> </span>
                    &lt;%&ndash;</div>&ndash;%&gt;
            </div>
        </div>
    </div>
    &lt;%&ndash;<input type="hidden" id="bankAccountName" name="bankAccountName" value="${openName}"/>&ndash;%&gt;

    <div class="control-group">
        <div class="control-left">
            <label class="control-label">银行账户名：</label>
            <div class="controls">
                &lt;%&ndash;<form:input path="bankAccountName" htmlEscape="false" maxlength="50" class="input-xlarge required"/>&ndash;%&gt;
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">银行账号：</label>
            <div class="controls">
                &lt;%&ndash;<form:input path="bankAccountCode" htmlEscape="false" maxlength="30" class="input-xlarge required"/>&ndash;%&gt;
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
    </div>--%>
    <h3>图片信息</h3>
    <div class="upload_box">
        <p class="message_hd">营业执照</p>
        <div class="upload_one">
            <c:if test="${agentInfo.id !='' && agentInfo.id != null}">
                <p>营业执照 <span class="look_big_img">查看原图</span></p>
                <div class="big_img_box">
                    <p class="big_img_close">X</p>
                    <p><img src="${agentInfo.busiImage}" id="_img1sub"></p>
                </div>
                <br>
            </c:if>
            <div class="upload_img">
                <!--上传-->
                <div id="uploader" class="uploader_style" data-name="uploader">
                    <div class="queueList">
                        <div id="dndArea" class="placeholder">
                            <input type="hidden" name="uploadImgHidden" id="uploadImgHidden" value=""/>
                            <div id="filePicker"><img src="${pageContext.request.contextPath}/static/images/upload.png">
                            </div>
                        </div>
                        <div class="number_img_edit upload_edit_one"></div>
                    </div>
                </div>
            </div>
            <c:if test="${agentInfo.id =='' || agentInfo.id==null}">
                <div class="upload_tip">
                    <div class="js_pop_upload" data-name="营业执照"
                         data-img="${pageContext.request.contextPath}/static/images/upload_img_1_1.png">
                        <img src="${pageContext.request.contextPath}/static/images/upload_img_1.png">
                    </div>
                 </div>
            </c:if>
            <div class="clear"></div>
            <p class="upload_tip_text">营业执照必须是清晰有效真实的彩色原件电子版，可以是扫描件或数码拍摄照片。上传图片格式仅支持.gif.jpg.jpeg.bmp.png。<em>上传文件大小不能超过2M。</em>
            </p>
        </div>
    </div>

    <div class="upload_box">
        <p class="message_hd">组织机构代码</p>
        <div class="upload_one">
            <c:if test="${agentInfo.id !='' &&  agentInfo.id != null}">
                <p>组织机构代码 <span class="look_big_img">查看原图</span></p>
                <div class="big_img_box">
                    <p class="big_img_close">X</p>
                    <p><img src="${agentInfo.orgInstImage}" id="_img2sub"></p>
                </div>
                <br>
            </c:if>
            <div class="upload_img">
                <!--上传-->
                <div id="uploader_two" class="uploader_style" data-name="uploader_two">
                    <div class="queueList">
                        <div id="dndArea_two" class="placeholder">
                            <div id="filePicker_two"><img
                                    src="${pageContext.request.contextPath}/static/images/upload.png"></div>
                        </div>
                        <div class="number_img_edit upload_edit_two"></div>
                    </div>
                </div>
            </div>
            <c:if test="${agentInfo.id =='' || agentInfo.id==null}">
                <div class="upload_tip">
                    <div class="js_pop_upload" data-name="组织机构代码"
                         data-img="${pageContext.request.contextPath}/static/images/upload_img_2_2.png">
                        <img src="${pageContext.request.contextPath}/static/images/upload_img_2.png">
                    </div>
                </div>
            </c:if>
            <div class="clear"></div>
            <p class="upload_tip_text">组织机构代码必须是清晰有效真实的彩色原件电子版，可以是扫描件或数码拍摄照片。上传图片格式仅支持.gif.jpg.jpeg.bmp.png。<em>上传文件大小不能超过2M。</em>
            </p>
        </div>
    </div>

    <div class="upload_box">
        <p class="message_hd">税务登记证</p>
        <div class="upload_one">
            <c:if test="${agentInfo.id !='' && agentInfo.id!=null}">
                <p>税务登记证 <span class="look_big_img">查看原图</span></p>
                <div class="big_img_box">
                    <p class="big_img_close">X</p>
                    <p><img src="${agentInfo.taxRegImage}"  id="_img3sub"></p>
                </div>
                <br>
            </c:if>
            <div class="upload_img">
                <!--上传-->
                <div id="uploader_three" class="uploader_style" data-name="uploader_three">
                    <div class="queueList">
                        <div id="dndArea_three" class="placeholder">
                            <div id="filePicker_three"><img
                                    src="${pageContext.request.contextPath}/static/images/upload.png"></div>
                        </div>
                        <div class="number_img_edit upload_edit_three"></div>
                    </div>
                </div>

            </div>
            <c:if test="${agentInfo.id =='' || agentInfo.id==null}">
                <div class="upload_tip">
                   <div class="js_pop_upload" data-name="税务登记证"
                        data-img="${pageContext.request.contextPath}/static/images/upload_img_3_3.png">
                       <img src="${pageContext.request.contextPath}/static/images/upload_img_3.png">
                   </div>
               </div>
            </c:if>
            <div class="clear"></div>
            <p class="upload_tip_text">税务登记证必须是清晰有效真实的彩色原件电子版，可以是扫描件或数码拍摄照片。上传图片格式仅支持.gif.jpg.jpeg.bmp.png。<em>上传文件大小不能超过2M。</em>
            </p>
        </div>
    </div>

    <div class="upload_box">
        <p class="message_hd">开户许可证</p>
        <div class="upload_one">
            <c:if test="${agentInfo.id !='' && agentInfo.id!=null}">
                <p>开户许可证 <span class="look_big_img">查看原图</span></p>
                <div class="big_img_box">
                    <p class="big_img_close">X</p>
                    <p><img src="${agentInfo.otherGenaImage}" id="_img4sub"></p>
                </div>
                <br>
            </c:if>
            <div class="upload_img">
                <p>正面</p>
                <!--上传-->
                <div id="uploader_four" class="uploader_style" data-name="uploader_four">
                    <div class="queueList">
                        <div id="dndArea_four" class="placeholder">
                            <div id="filePicker_four"><img
                                    src="${pageContext.request.contextPath}/static/images/upload.png"></div>
                        </div>
                        <div class="number_img_edit upload_edit_four"></div>
                    </div>
                </div>
            </div>
            <c:if test="${agentInfo.id =='' || agentInfo.id==null}">
                <div class="upload_tip">
                    <p>&nbsp;</p>
                    <div class="js_pop_upload" data-name="开户许可证(正面)"
                         data-img="${pageContext.request.contextPath}/static/images/upload_img_4_4.png">
                        <img src="${pageContext.request.contextPath}/static/images/upload_img_4.png">
                    </div>
                </div>
            </c:if>
            <div class="clear"></div>
            <p class="upload_tip_text">开户许可证必须是清晰有效真实的彩色原件电子版，可以是扫描件或数码拍摄照片。上传图片格式仅支持.gif.jpg.jpeg.bmp.png。<em>上传文件大小不能超过2M。</em>
            </p>
        </div>
    </div>
    <div class="upload_box">
        <p class="message_hd">法定代表人证件照片</p>
        <div class="upload_one">
            <p>法定代表人证件照片</p>
            <div class="upload_img">
                <c:if test="${agentInfo.id !='' && agentInfo.id!=null}">
                    <p>个人信息 <span class="look_big_img">查看原图</span></p>
                    <div class="big_img_box">
                        <p class="big_img_close">X</p>
                        <p><img src="${agentInfo.idcardFaceImage}" id="_img5sub"></p>
                    </div>
                    <br>
                </c:if>
                <!--上传-->
                <div id="uploader_five" class="uploader_style" data-name="uploader_five">
                    <div class="queueList">
                        <div id="dndArea_five" class="placeholder">
                            <div id="filePicker_five"><img
                                    src="${pageContext.request.contextPath}/static/images/upload.png"></div>
                        </div>
                        <div class="number_img_edit upload_edit_five"></div>
                    </div>
                </div>

                <c:if test="${agentInfo.id =='' || agentInfo.id==null}">
                    <div class="js_pop_upload" data-name="法定代表人证件照片（个人信息页）" data-img="${pageContext.request.contextPath}/static/images/upload_img_5_5.png">
                        <img src="${pageContext.request.contextPath}/static/images/upload_img_5.png">
                    </div>
                </c:if>
            </div>
            <div class="upload_tip">
                <c:if test="${agentInfo.id !='' && agentInfo.id!=null}">
                    <p>国徽页 <span class="look_big_img">查看原图</span></p>
                    <div class="big_img_box">
                        <p class="big_img_close">X</p>
                        <p><img src="${agentInfo.idcardBackImage}" id="_img6sub"></p>
                    </div>
                    <br>
                </c:if>
                <!--上传-->
                <div id="uploader_sex" class="uploader_style" data-name="uploader_sex">
                    <div class="queueList">
                        <div id="dndArea_sex" class="placeholder">
                            <div id="filePicker_sex"><img
                                    src="${pageContext.request.contextPath}/static/images/upload.png"></div>
                        </div>
                        <div class="number_img_edit upload_edit_sex"></div>
                    </div>

                    <c:if test="${agentInfo.id =='' || agentInfo.id==null}">
                        <div class="js_pop_upload" data-name="法定代表人证件照片（国徽页）"
                             data-img="${pageContext.request.contextPath}/static/images/upload_img_6_6.png">
                            <img src="${pageContext.request.contextPath}/static/images/upload_img_6.png">
                        </div>
                    </c:if>
                </div>
                    <%--              <c:choose>
                                      <c:when test="${agentInfo.idcardBackImage != '' || agentInfo.idcardBackImage != null}">
                                          <div class="js_pop_upload" data-name="法定代表人证件照片（国徽页）"
                                               data-img="${agentInfo.idcardFaceImage}">
                                              <img src="${pageContext.request.contextPath}/static/images/upload_img_6.png">
                                              &lt;%&ndash;<img src="${agentInfo.idcardBackImage}">&ndash;%&gt;
                                          </div>
                                      </c:when>
                                      <c:otherwise>
                                          <div class="js_pop_upload" data-name="法定代表人证件照片（国徽页）"
                                               data-img="${pageContext.request.contextPath}/static/images/upload_img_6_6.png">
                                              <img src="${pageContext.request.contextPath}/static/images/upload_img_6.png">
                                          </div>
                                      </c:otherwise>
                                  </c:choose>--%>

            </div>
            <div class="clear"></div>
            <p class="upload_tip_text">法定代表人证件照片必须是清晰有效真实的彩色原件电子版，可以是扫描件或数码拍摄照片。上传图片格式仅支持.gif.jpg.jpeg.bmp.png。<em>上传文件大小不能超过2M。</em>
            </p>
        </div>
    </div>

    <input type="hidden" id="img1"/>
    <input type="hidden" id="img2"/>
    <input type="hidden" id="img3"/>
    <input type="hidden" id="img4"/>
    <input type="hidden" id="img5"/>
    <input type="hidden" id="img6"/>

    <input type="hidden" id="img1sub" name="busiImage"/>
    <input type="hidden" id="img2sub" name="orgInstImage" />
    <input type="hidden" id="img3sub" name="taxRegImage"/>
    <input type="hidden" id="img4sub" name="otherGenaImage" />
    <input type="hidden" id="img5sub" name="idcardFaceImage"/>
    <input type="hidden" id="img6sub" name="idcardBackImage"/>

<%--    <input type="hidden" id="img1sub" name="busiImage" value="${agentInfo.busiImage}"/>
    <input type="hidden" id="img2sub" name="orgInstImage" value="${agentInfo.orgInstImage}"/>
    <input type="hidden" id="img3sub" name="taxRegImage" value="${agentInfo.taxRegImage}"/>
    <input type="hidden" id="img4sub" name="otherGenaImage" value="${agentInfo.otherGenaImage}"/>
    <input type="hidden" id="img5sub" name="idcardFaceImage" value="${agentInfo.idcardFaceImage}"/>
    <input type="hidden" id="img6sub" name="idcardBackImage" value="${agentInfo.idcardBackImage}"/>--%>

    <%--		<div class="control-group">
                <label class="control-label">其他辅助材料：</label>
                <div class="controls">
                    <form:input path="otherGenaImage" htmlEscape="false" maxlength="256" class="input-xlarge "/>
                </div>
            </div>--%>
    <div class="form-actions">
        <shiro:hasPermission name="agent:agent:agentInfo:edit"><input id="btnSubmit" class="btn btn-primary"
                                                                      type="submit"
                                                                      value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>