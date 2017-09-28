$(function () {
    /*三级城市联动*/
    function showLocation(flag) {
        var title = ['省/直辖市', '请选择市', '选择开户支行名称'];
        $.each(title, function (k, v) {
            title[k] = '<option value="">' + v + '</option>';
        })

        $('#loc_province').append(title[0]);
        $('#loc_city').append(title[1]);
        $('#open_bank_name').append(title[2]);

        $("#loc_province,#loc_city,#open_bank_name").select2();

        $('#loc_province').change(function () {
            $('#loc_city').empty();
            $('#loc_city').append(title[1]);
            var _province_id = $('#loc_province').val();
            if (_province_id != '') {
                get_cate_list('loc_city', $('#loc_province').val(), "2");
            }
            $('#loc_city').change();
        })

        $('#loc_city').change(function () {
            $('#open_bank_name').empty();
            $('#open_bank_name').append(title[2]);
            var _city_id = $('#loc_city').val();
            if (_city_id != '' && _city_id != '0') {
                openBankName('open_bank_name', $('#loc_city').val(), "3");
            }
            $('#open_bank_name').change();
        })

        $('#open_bank_name').change(function () {
            var _town_id = $(this).val();
            $('input[name=location_id]').val(_town_id);
            /*if(_town_id!='' && _town_id!='0'){
             change_url($(this).val());
             }*/

        })
        if (flag) {
            get_cate_list('loc_province', '', "1");
        }
    }

    showLocation(true);
    openBank();
    function get_cate_list(el_id, loc_id, role) {
        var el = $('#' + el_id);
        $.ajax({
            async: false,
            type: "GET",
            url: "/user/selectBank",
            data: {'id': loc_id, 'role': role, 'bankNo': $("#open_bank").val()},
            dataType: 'jsonp',
            jsonp: 'callback',
            success: function (data) {
                $.each(data, function (k, v) {
                    var option = '<option value="' + v.id + '">' + v.name + '</option>';
                    el.append(option);
                })
            },
            error: function () {
            }
        });
    }


    function openBank() {
        $("#open_bank").select2();
        $.ajax({
            async: false,
            type: "GET",
            url: "/user/bankList",
            data: {},
            dataType: 'json',
            success: function (data) {
                $.each(data, function (k, v) {
                    var option = '<option value="' + v.bankNo + '">' + v.bankName + '</option>';
                    /*						var option=null;
                     if($("#bankNameId").val() == v.bankNo){
                     option	= '<option value="'+v.bankNo+'" selected=\"selected\">'+v.bankName+'</option>';
                     }else{
                     option	= '<option value="'+v.bankNo+'">'+v.bankName+'</option>';
                     }*/
                    $("#open_bank").append(option);
                })
            },
            error: function () {
            }
        });

        $('#open_bank').change(function () {
            $('#open_bank_name').empty();
            var _city_id = $('#loc_city').val();
            if (_city_id != '' && _city_id != '0') {
                openBankName('open_bank_name', $('#loc_city').val(), "3");
            }
            $('#open_bank_name').change();
        });


        if ($('#bankNameId').val() != '' && $('#bankNameId').val() != null) {
            //$(".select2-chosen").text($('#bankNameId').val());
            $("#open_bank").find("option[value='" + $('#bankNameId').val() + "']").attr("selected", true);
            $("#open_bank_div").find(".select2-chosen").text($("#open_bank").find("option:selected").text());
            $("#bankId").val($("#open_bank").find("option:selected").val());
            $("#bankName").val($("#open_bank").find("option:selected").text());
        }

        if ($('#bnkProvinceCode').val() != '' && $('#bnkProvinceCode').val() != null) {
            //$(".select2-chosen").text($('#bankNameId').val());
            $("#loc_province").find("option[value='" + $('#bnkProvinceCode').val() + "']").attr("selected", true);
            $("#open_bank_pri").find(".select2-chosen").text($("#loc_province").find("option:selected").text());
            $("#bankProvinceCode").val($("#loc_province").find("option:selected").val());
            $("#bankProvinceName").val($("#loc_province").find("option:selected").text());
        }

        $('#loc_province').change();

        if ($('#bnkcityCode').val() != '' && $('#bnkcityCode').val() != null) {
            //$(".select2-chosen").text($('#bankNameId').val());
            $("#loc_city").find("option[value='" + $('#bnkcityCode').val() + "']").attr("selected", true);
            $("#open_bank_pri").find(".select2-chosen").text($("#loc_city").find("option:selected").text());
            $("#bankCityCode").val($("#loc_city").find("option:selected").val());
            $("#bankCityName").val($("#loc_city").find("option:selected").text());
        }
        $('#loc_city').change();

        if ($('#bnkBranchCode').val() != '' && $('#bnkBranchCode').val() != null) {
            //$(".select2-chosen").text($('#bankNameId').val());
            $("#open_bank_name").find("option[value='" + $('#bnkBranchCode').val() + "']").attr("selected", true);
            $("#open_bank_branch").find(".select2-chosen").text($("#open_bank_name").find("option:selected").text());
            $("#bankBranchCode").val($("#open_bank_name").find("option:selected").val());
            $("#bankBranchName").val($("#open_bank_name").find("option:selected").text());
        }
        $('#open_bank_name').change();
    }

    function openBankName(el_id, loc_id, role) {
        var el = $('#' + el_id);
        $.ajax({
            async: false,
            type: "GET",
            url: "/user/selectBank",
            data: {'id': loc_id, 'role': role, 'bankNo': $("#open_bank").val()},
            dataType: 'jsonp',
            jsonp: 'callback',
            success: function (data) {
                $.each(data, function (k, v) {
                    var option = '<option data-name="' + v.associateLineNumber + '" value="' + v.openBankCode + '">' + v.openBankName + '</option>';
                    el.append(option);
                })
            },
            error: function () {
            }
        });

    }


    function openNamebank(el_id, loc_id, role) {
        var el = $('#' + el_id);
        $.ajax({
            async: false,
            type: "GET",
            url: "/user/selectBank",
            data: {'id': loc_id, 'role': role, 'bankNo': $("#open_bank").val()},
            dataType: 'jsonp',
            jsonp: 'callback',
            success: function (data) {
                $.each(data, function (k, v) {
                    var li = '<li data-name="' + v.associateLineNumber + '" value="' + v.openBankCode + '">' + v.openBankName + '</li>';
                    el.append(li);
                })
            },
            error: function () {
            }
        });

    }

    /*$("#open_bank_name").change(function(){
     var id = $(this).find("option:selected").data("name");
     $("#associateLineNumber").val(id);
     var name = $("#bankSubbranchName").val($("#select2-chosen-3").text());
     console.log("141行的name"+name);
     console.log($("#select2-chosen-3").text());
     });*/

    $("#open_bank").on("change", function () {
        /*$("#bankName").val($("#select2-chosen-3").text());*/
        $("#bankId").val($("#open_bank").find("option:selected").val());
        $("#bankName").val($("#open_bank").find("option:selected").text());
    });

    $("#loc_province").on("change", function () {
        /*$("#bankName").val($("#select2-chosen-3").text());*/
        $("#bankProvinceCode").val($("#loc_province").find("option:selected").val());
        $("#bankProvinceName").val($("#loc_province").find("option:selected").text());
    });

    $("#loc_city").on("change", function () {
        /*$("#bankName").val($("#select2-chosen-3").text());*/
        $("#bankCityCode").val($("#loc_city").find("option:selected").val());
        $("#bankCityName").val($("#loc_city").find("option:selected").text());
    });

    $("#open_bank_name").on("change", function () {
        /*$("#bankName").val($("#select2-chosen-3").text());*/
        $("#bankBranchCode").val($("#open_bank_name").find("option:selected").val());
        $("#bankBranchName").val($("#open_bank_name").find("option:selected").text());
    });
    /*获取开户支行名称*/
    $('[name="open_bank_select"]').click(function (e) {
        $('[name="open_bank_select"]').find('ul').hide();
        $('#open_bank_list').empty();
        var _city_id = $('#loc_city').val();
        if (_city_id != '' && _city_id != '0') {
            openNamebank('open_bank_list', $('#loc_city').val(), "3");
        }
        //$('#open_bank_name').change();
        $(this).find('ul').show();
        e.stopPropagation();
    });
    $('[name="open_bank_select"] li').hover(function (e) {
        $(this).toggleClass('on');
        e.stopPropagation();
    });
    $("#open_bank_list").on("click", "li", function (e) {
        var val = $(this).text();
        $("#bankSubbranchName").val(val);
        var dataName = $(this).attr("data-name");
        $("#associateLineNumber").val(dataName);
        $(this).parents('[name="open_bank_select"]').find('input').val(val);
        $('[name="open_bank_select"] ul').hide();
        e.stopPropagation();
    });
    $(document).click(function () {
        $('[name="open_bank_select"] ul').hide();
    });

    /*所有表单获取焦点*/
    $(".company_box input").on("focus", function () {
        debugger;
        $(this).removeClass("b_color").addClass("b_success_color");
        $(".company_form_tip").hide();
    })

    $(".upload_next").on("click", function () {
        var open_bank = $("#open_bank").find("option:selected").text();
        var loc_province = $("#loc_province").find("option:selected").text();
        var loc_city = $("#loc_city").find("option:selected").text();
        var open_bank_name = $("#hand_input").val();
        var info4 = $("#info4").val();

        var errorOff = true;
        if (open_bank == "选择开户银行") {
            $(".company_sel_one").find(".company_form_tip").show();
            errorOff = false;
        }
        if (loc_province == "省/直辖市" || loc_city == "请选择市") {
            $(".company_sel_two").find(".company_form_tip").show();
            $(".company_sel_three").find(".company_form_tip").show();
            errorOff = false;
        }
        if (!open_bank_name) {
            $(".open_bank_box").find(".company_form_tip").show();
            errorOff = false;
        }
        if (!info4) {
            $("#info4").siblings(".company_form_tip").show();
            errorOff = false;
        } else {
            var reg = /^\d{10,20}$/;
            if (!reg.test(info4)) {
                $("#info4").siblings(".company_form_tip").html("<img src='../static/images/login_error.png'><em>银行账户格式错误！</em>").show();
                errorOff = false;
            }
        }
        if (!errorOff) {
            $("html,body").animate({scrollTop: 0}, 500);
            return false;
        }
    })

})