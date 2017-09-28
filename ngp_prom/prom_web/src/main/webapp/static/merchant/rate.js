/**
 * Created by Administrator on 2017/9/7.
 */

var myctx = sessionStorage["myctx"];
//根据业务类型获取产品方法
    function getProduct(businessType) {
        var el = $('#productCode');
        $.ajax({
            async: false,
            type: "GET",
            url: myctx+"/merchant/product/getProduct",
            data: {"businessType": businessType},
            dataType: 'json',
            success: function (data) {
                $.each(data, function (k, v) {
                    var option = '<option data-type="' + v.trxType + '" value="' + v.code + '">' + v.name + '</option>';
                    el.append(option);
                })
            },
            error: function () {
                console.log("请求失败!");
            }
        });
    }

//借记卡按比例
    function savingRatiod() {
        $("#chargeRatioSavingDiv").show();
        $("#chargeRatioSavingDiv2").show();
        $("#chargeRatioSavingDiv3").show();
        $("#maxFeeSavingDiv").show();
        $("#minFeeSavingDiv").show();
        $("#chargeFeeSavingDiv").hide();
        $("#chargeFeeSavingDiv2").hide();
        $("#chargeFeeSavingDiv3").hide();

    }

//借记卡按笔数
    function savingCountd() {
        $("#chargeRatioSavingDiv").hide();
        $("#chargeRatioSavingDiv2").hide();
        $("#chargeRatioSavingDiv3").hide();
        $("#maxFeeSavingDiv").hide();
        $("#minFeeSavingDiv").hide();
        $("#chargeFeeSavingDiv").show();
        $("#chargeFeeSavingDiv2").show();
        $("#chargeFeeSavingDiv3").show();
    }

//贷记卡按比例
    function creditRatiod() {
        $("#chargeRatioCreditDiv").show();
        $("#chargeRatioCreditDiv2").show();
        $("#chargeRatioCreditDiv3").show();
        $("#maxFeeCreditDiv").show();
        $("#minFeeCreditDiv").show();
        $("#chargeFeeCreditDiv").hide();
        $("#chargeFeeCreditDiv2").hide();
        $("#chargeFeeCreditDiv3").hide();
    }

//贷记卡按笔数
    function creditCountd() {
        $("#chargeRatioCreditDiv").hide();
        $("#chargeRatioCreditDiv2").hide();
        $("#chargeRatioCreditDiv3").hide();
        $("#maxFeeCreditDiv").hide();
        $("#minFeeCreditDiv").hide();
        $("#chargeFeeCreditDiv").show();
        $("#chargeFeeCreditDiv2").show();
        $("#chargeFeeCreditDiv3").show();
    }


//保存通过方法
    function pass() {
        if (!confirm("请确认提交此操作?")) {
            return false;
        } else {
            //阶梯判断
            var ladderSavingRatiod = ($("#chargeMinSavingRatiod").val() == "" || $("#chargeMaxSavingRatiod").val() == "") &&
                ($("#chargeMinSavingRatiod2").val() != "" || $("#chargeMinSavingRatiod3").val() != "" ||
                $("#chargeMaxSavingRatiod2").val() != "" || $("#chargeMaxSavingRatiod3").val() != "");

            var ladderSavingCountd = ($("#chargeMinSavingCountd").val() == "" || $("#chargeMaxSavingCountd").val() == "") &&
                ($("#chargeMinSavingCountd2").val() != "" || $("#chargeMinSavingCountd3").val() != "" ||
                $("#chargeMaxSavingCountd2").val() != "" || $("#chargeMaxSavingCountd3").val() != "");

            var ladderCreditRatiod = ($("#chargeMinCreditRatiod").val() == "" || $("#chargeMaxCreditRatiod").val() == "") &&
                ($("#chargeMinCreditRatiod2").val() != "" || $("#chargeMinCreditRatiod3").val() != "" ||
                $("#chargeMaxCreditRatiod2").val() != "" || $("#chargeMaxCreditRatiod3").val() != "");

            var ladderCreditCountd = ($("#chargeMinSavingCountd").val() == "" || $("#chargeMaxSavingCountd").val() == "") &&
                ($("#chargeMinCreditCountd2").val() != "" || $("#chargeMinCreditCountd3").val() != "" ||
                $("#chargeMaxCreditCountd2").val() != "" || $("#chargeMaxCreditCountd3").val() != "");

            if (ladderSavingRatiod || ladderSavingCountd || ladderCreditRatiod || ladderCreditCountd) {
                parent.showThree();
                parent.changeThreeName("阶梯配置有误");
                return false;
            }
            //最大最小值判断
            var saving = parseFloat($("#maxFeeSaving").val()) < parseFloat($("#minFeeSaving").val());
            var credit = parseFloat($("#maxFeeCredit").val()) < parseFloat($("#minFeeCredit").val());
            if (saving || credit) {
                parent.showThree();
                parent.changeThreeName("手续费最大值必须大于等于手续费最小值");
                return false;
            }
        }
    }

//计费类型切换(借记卡)
    function chargeTypeSavingChange(code) {
        switch (code) {
            case "RATIOD":
                ratiodSaving();
                break;
            case "COUNTD":
                countdSaving();
                break;
        }
    }

//按比例
    function ratiodSaving() {
        savingRatiod();
        $("#chargeFeeSaving").val("");
        $("#chargeFeeSaving2").val("");
        $("#chargeFeeSaving3").val("");
        $("#chargeMinSavingCountd").val("");
        $("#chargeMinSavingCountd2").val("");
        $("#chargeMinSavingCountd3").val("");
        $("#chargeMaxSavingCountd").val("");
        $("#chargeMaxSavingCountd2").val("");
        $("#chargeMaxSavingCountd3").val("");
    }

//按笔数
    function countdSaving() {
        savingCountd();
        $("#chargeRatioSaving").val("");
        $("#chargeRatioSaving2").val("");
        $("#chargeRatioSaving3").val("");
        $("#maxFeeSaving").val("");
        $("#minFeeSaving").val("");
        $("#chargeMinSavingRatiod").val("");
        $("#chargeMinSavingRatiod2").val("");
        $("#chargeMinSavingRatiod3").val("");
        $("#chargeMaxSavingRatiod").val("");
        $("#chargeMaxSavingRatiod2").val("");
        $("#chargeMaxSavingRatiod3").val("");
    }

//计费类型切换(贷记卡)
    function chargeTypeCreditChange(code) {
        switch (code) {
            case "RATIOD":
                ratiodCredit();
                break;
            case "COUNTD":
                countdCredit();
                break;
        }
    }

//按比例
    function ratiodCredit() {
        creditRatiod();
        $("#chargeFeeCredit").val("");
        $("#chargeFeeCredit2").val("");
        $("#chargeFeeCredit3").val("");
        $("#chargeMinCreditCountd").val("");
        $("#chargeMinCreditCountd2").val("");
        $("#chargeMinCreditCountd3").val("");
        $("#chargeMaxCreditCountd").val("");
        $("#chargeMaxCreditCountd2").val("");
        $("#chargeMaxCreditCountd3").val("");
    }

//按笔数
    function countdCredit() {
        creditCountd();
        $("#chargeRatioCredit").val("");
        $("#chargeRatioCredit2").val("");
        $("#chargeRatioCredit3").val("");
        $("#maxFeeCredit").val("");
        $("#minFeeCredit").val("");
        $("#chargeMinCreditRatiod").val("");
        $("#chargeMinCreditRatiod2").val("");
        $("#chargeMinCreditRatiod3").val("");
        $("#chargeMaxCreditRatiod").val("");
        $("#chargeMaxCreditRatiod2").val("");
        $("#chargeMaxCreditRatiod3").val("");
    }

//产品切换方法
    function productChange(option) {
        var code = option.value;
        var name = option.text;
        var type = $(option).data('type');
        $("#productName").val(name);
        all();
        choose(type, name);
        chooseDefault(type, code);
        productX(code);
    }

//是否T+X方法
    function productX(code) {
        var productX = $("#productX").val();
        var isTX = $("#isTX").val();
        var isSelected = "";
        if (isTX == "X") {
            isSelected = "selected='selected'";
        }
        var business = $("#businessType").val();
        $("#settleType option[value=X]").remove();
        if (productX.indexOf(code) >= 0 || business == "TRUSTE") {
            $("#settleType").append("<option value='X' " + isSelected + " class='input-xlarge required'>X</option>");
            $("#s2id_settleType").find(".select2-chosen").text($("#settleTypeDiv").find("option:selected").text());
        }
    }

//获取默认费率方法
    function getDefault(code, bankType) {
        $.ajax({
            type: "GET",
            async: false,
            url: myctx+"/merchant/merchantDefaultRateNew/getDefault",
            data: {'productCode': code, 'bankCardType': bankType},
            dataType: 'json',
            success: function (data) {
                if (null != data && "" != data) {
                    if ("CREDIT" == bankType) {
                        $("#chargeTypeCredit").find("option[value='" + data.chargeType + "']").attr("selected", true);
                        $("#chargeTypeCreditDiv").find(".select2-chosen").text($("#chargeTypeCredit").find("option[value='" + data.chargeType + "']").text());
                        chargeTypeCreditChange(data.chargeType);
                        $("#chargeFeeCredit").val(data.chargeFee);
                        $("#chargeRatioCredit").val(data.chargeRatio);
                        $("#maxFeeCredit").val(data.maxFee);
                        $("#minFeeCredit").val(data.minFee);
                    } else {
                        $("#chargeTypeSaving").find("option[value='" + data.chargeType + "']").attr("selected", true);
                        $("#chargeTypeSavingDiv").find(".select2-chosen").text($("#chargeTypeSaving").find("option[value='" + data.chargeType + "']").text());
                        chargeTypeSavingChange(data.chargeType);
                        $("#chargeFeeSaving").val(data.chargeFee);
                        $("#chargeRatioSaving").val(data.chargeRatio);
                        $("#maxFeeSaving").val(data.maxFee);
                        $("#minFeeSaving").val(data.minFee);
                    }
                }
            }
        });
    }

    function bankCardTypeChange(radio) {
        var code = $("#productCode").val();
        var type = $("#productCode option:selected").data('type');
        if (code != "") {
            chooseDefault(type, code);
        }
        var chk_value = [];
        $(".radio_inout1").find('input[name="bankCardType"]:checked').each(function () {
            chk_value.push($(this).val());
        });
        if (chk_value.length === 0) {
            hideSaving();
            hideCredit();
            clearSaving();
            clearCreadit();
        } else if (chk_value.length === 1) {
            var bct = chk_value[0];
            if ("SAVING" == bct) {
                hideCredit();
                clearCreadit();
                chargeTypeSavingChange($("#chargeTypeSavingDiv").find("option:selected").val());
            } else if ("CREDIT" == bct) {
                hideSaving();
                clearSaving();
                chargeTypeCreditChange($("#chargeTypeCreditDiv").find("option:selected").val());
            }
        }
    }

//隐藏储蓄卡费率并清空值
    function hideSaving() {
        $("#chargeFeeSavingDiv").hide();
        $("#chargeRatioSavingDiv").hide();
        $("#chargeFeeSavingDiv2").hide();
        $("#chargeRatioSavingDiv2").hide();
        $("#chargeFeeSavingDiv3").hide();
        $("#chargeRatioSavingDiv3").hide();
        $("#maxFeeSavingDiv").hide();
        $("#minFeeSavingDiv").hide();
    }

//清空储蓄卡费率值
    function clearSaving() {
        $("#chargeFeeSaving").val("");
        $("#chargeRatioSaving").val("");
        $("#chargeFeeSaving2").val("");
        $("#chargeRatioSaving2").val("");
        $("#chargeFeeSaving3").val("");
        $("#chargeRatioSaving3").val("");
        $("#maxFeeSaving").val("");
        $("#minFeeSaving").val("");
        $("#chargeMinSavingRatiod").val("");
        $("#chargeMinSavingRatiod2").val("");
        $("#chargeMinSavingRatiod3").val("");
        $("#chargeMaxSavingRatiod").val("");
        $("#chargeMaxSavingRatiod2").val("");
        $("#chargeMaxSavingRatiod3").val("");
        $("#chargeMinSavingCountd").val("");
        $("#chargeMinSavingCountd2").val("");
        $("#chargeMinSavingCountd3").val("");
        $("#chargeMaxSavingCountd").val("");
        $("#chargeMaxSavingCountd2").val("");
        $("#chargeMaxSavingCountd3").val("");
    }

//隐藏信用卡费率并清空值
    function hideCredit() {
        $("#chargeFeeCreditDiv").hide();
        $("#chargeRatioCreditDiv").hide();
        $("#chargeFeeCreditDiv2").hide();
        $("#chargeRatioCreditDiv2").hide();
        $("#chargeFeeCreditDiv3").hide();
        $("#chargeRatioCreditDiv3").hide();
        $("#maxFeeCreditDiv").hide();
        $("#minFeeCreditDiv").hide();
    }

//清空信用卡费率值
    function clearCreadit() {
        $("#chargeFeeCredit").val("");
        $("#chargeRatioCredit").val("");
        $("#chargeFeeCredit2").val("");
        $("#chargeRatioCredit2").val("");
        $("#chargeFeeCredit3").val("");
        $("#chargeRatioCredit3").val("");
        $("#maxFeeCredit").val("");
        $("#minFeeCredit").val("");
        $("#chargeMinCreditRatiod").val("");
        $("#chargeMinCreditRatiod2").val("");
        $("#chargeMinCreditRatiod3").val("");
        $("#chargeMaxCreditRatiod").val("");
        $("#chargeMaxCreditRatiod2").val("");
        $("#chargeMaxCreditRatiod3").val("");
        $("#chargeMinCreditCountd").val("");
        $("#chargeMinCreditCountd2").val("");
        $("#chargeMinCreditCountd3").val("");
        $("#chargeMaxCreditCountd").val("");
        $("#chargeMaxCreditCountd2").val("");
        $("#chargeMaxCreditCountd3").val("");
    }


    function chooseDefault(type, code) {
        var name = $("#productName").val();
        if (code != "") {
            switch (type) {
                case "BATCOL"://代收
                case "BATPAY"://转账
                case "CHARGE"://充值
                case "WZDRAW"://提现
                case "REFUND"://退款
                case "RENAME"://实名认证
                case "SHARES"://平级分润
                case "CACNCE"://通关申报
                case "TRAFER"://内转
                case "DPTWZD"://存管提现
                case "DPTPAY"://存管支付
                case "WALLET"://钱包服务
                    getDefault(code);
                    break;
                default:
                    if (name.indexOf("B2B") >= 0 || name.indexOf("微信") >= 0 || name.indexOf("快捷支付2") >= 0
                        || name.indexOf("支付宝") >= 0 || name.indexOf("银联") >= 0 || name.indexOf("量化派") >= 0) {
                        getDefault(code);
                        break;
                    }
                    $("input[name='bankCardType']:checked").each(function () {
                        getDefault(code, $(this).val());
                    })
                    break;
            }
        }
    }


    function choose(type, name) {
        switch (type) {
            case "BATCOL"://代收
                b();
                break;
            case "BATPAY"://转账
                c();
                break;
            case "CHARGE"://充值
                d();
                break;
            case "WZDRAW"://提现
                e();
                break;
            case "REFUND"://退款
                f();
                break;
            case "RENAME"://实名认证
            case "EPRISE"://企业认证
            case "AUTHEN"://鉴权认证
                g();
                break;
            case "SHARES"://平级分润
                i();
                break;
            case "CACNCE"://通关申报
            case "TRAFER"://内转
            case "WALLET"://钱包服务
                costom();
                break;
            case "DPTWZD"://存管提现
                dptwzd();
                break;
            case "DPTPAY"://存管支付
                dptpay();
                break;
            default://支付
                //B2B支付,微信
                if (name.indexOf("B2B") >= 0 || name.indexOf("微信") >= 0 || name.indexOf("快捷支付2") >= 0
                    || name.indexOf("支付宝") >= 0 || name.indexOf("银联") >= 0 || name.indexOf("量化派") >= 0) {
                    h();
                    break;
                }
                //其他支付
                a();
                break;
        }
    }

//还原页面初始状态
    function all() {
        $("#bankCardTypeDiv").show();
        $("#ruleBeginTimeDiv").show();
        $("#ruleEndTimeDiv").show();
        $("#chargeSourceDiv").show();
        $("#chargeDeductTypeDiv").show();
        $("#chargeCollectionTypeDiv").show();
        $("#isRefundableDiv").show();
        $("#statusDiv").show();
        $("#settleTypeDiv").show();
        $("#settlementToDiv").show();
        $("#notifyUrlDiv").show();
        $("#backUrlDiv").show();
        $("#ipDomainDiv").show();
        $("#autographTypeDiv").show();
        //借记卡
        $("#chargeTypeSavingDiv").show();
        $("#chargeRatioSavingDiv").show();
        $("#chargeFeeSavingDiv").show();
        $("#chargeRatioSavingDiv2").show();
        $("#chargeFeeSavingDiv2").show();
        $("#chargeRatioSavingDiv3").show();
        $("#chargeFeeSavingDiv3").show();
        $("#maxFeeSavingDiv").show();
        $("#minFeeSavingDiv").show();
        //贷记卡
        $("#chargeTypeCreditDiv").show();
        $("#chargeRatioCreditDiv").show();
        $("#chargeFeeCreditDiv").show();
        $("#chargeRatioCreditDiv2").show();
        $("#chargeFeeCreditDiv2").show();
        $("#chargeRatioCreditDiv3").show();
        $("#chargeFeeCreditDiv3").show();
        $("#maxFeeCreditDiv").show();
        $("#minFeeCreditDiv").show();
        //清空值
        $("#bankCardType").val("");
        $("#ruleBeginTime").val("");
        $("#ruleEndTime").val("");
        $("#chargeSource").val("");
        $("#chargeDeductType").val("");
        $("#chargeCollectionType").val("");
        $("#isRefundable").val("");
        $("#bankName").val("");
        $("#settleType").val("");
        $("#settlementTo").val("");
        $("#notifyUrl").val("");
        $("#backUrl").val("");
        $("#ipDomain").val("");
        $("#autographType").val("");
        //借记卡
        clearSaving();
        //贷记卡
        clearCreadit();

        $("input[type='radio']").removeAttr('checked');
        $("input[type='checkbox']").removeAttr('checked');
        $("#settleTypeDiv").find("option[value='1']").attr("selected", true);
        $("#settleTypeDiv").find("option[value='0']").attr("selected", false);
        $("#s2id_settleType").find(".select2-chosen").text($("#settleTypeDiv").find("option:selected").text());
        $("#settlementToDiv").find("option[value='']").attr("selected", true);
        $("#settlementToDiv").find("option[value='ACCBAL']").attr("selected", false);
        $("#s2id_settlementTo").find(".select2-chosen").text($("#settlementToDiv").find("option:selected").text());
        $("#s2id_chargeTypeSaving").find(".select2-chosen").text("");
        $("#s2id_chargeTypeCredit").find(".select2-chosen").text("");
        $("#statusDiv").find("input[value='ENABLE']").attr("checked", 'checked');
    }

//隐藏通用的部分
    function hideCommon() {
//			$("#chargeFeeSavingDiv").hide();
//			$("#chargeRatioSavingDiv").hide();
//			$("#maxFeeSavingDiv").hide();
//			$("#minFeeSavingDiv").hide();
//			$("#chargeFeeCreditDiv").hide();
//            $("#chargeRatioCreditDiv").hide();
//            $("#maxFeeCreditDiv").hide();
//            $("#minFeeCreditDiv").hide();
        hideSaving();
        hideCredit();
        $("#chargeDeductTypeDiv").hide();
        $("#chargeSourceDiv").hide();
        $("#chargeCollectionTypeDiv").hide();
        $("#autographTypeDiv").hide();
    }

//支付
    function a() {
        $("#chargeSourceDiv").hide();
        $("#isRefundableDiv").hide();
        $("#chargeDeductTypeDiv").find("input[value='INDEDU']").attr("checked", 'checked');
        $("#chargeSourceDiv").find("input[value='MERCHA']").attr("checked", 'checked');
        $("#autographTypeDiv").find("input[value='MD5']").attr("checked", 'checked');
        $("#settlementToDiv").find("option[value='ACCBAL']").attr("selected", true);
        $("#settlementToDiv").find("option[value='']").attr("selected", false);
        $("#s2id_settlementTo").find(".select2-chosen").text($("#settlementToDiv").find("option:selected").text());
        chargeDeductTypeChange("INDEDU");
        hideCommon();
    }

//代收
    function b() {
        $("#bankCardTypeDiv").hide();
        $("#chargeSourceDiv").hide();
        $("#isRefundableDiv").hide();
        $("#chargeTypeCreditDiv").hide();
        $("#chargeDeductTypeDiv").find("input[value='INDEDU']").attr("checked", 'checked');
        $("#autographTypeDiv").find("input[value='MD5']").attr("checked", 'checked');
        $("#settlementToDiv").find("option[value='ACCBAL']").attr("selected", true);
        $("#settlementToDiv").find("option[value='']").attr("selected", false);
        $("#s2id_settlementTo").find(".select2-chosen").text($("#settlementToDiv").find("option:selected").text());
        chargeDeductTypeChange("INDEDU");
        $("#chargeDeductTypeDiv").hide();
        $("#chargeCollectionTypeDiv").hide();
        hideCommon();
    }

//转账
    function c() {
        $("#bankCardTypeDiv").hide();
        $("#chargeSourceDiv").hide();
        $("#isRefundableDiv").hide();
        $("#chargeTypeCreditDiv").hide();
        $("#chargeDeductTypeDiv").find("input[value='EXDEDU']").attr("checked", 'checked');
        $("#autographTypeDiv").find("input[value='MD5']").attr("checked", 'checked');
        chargeDeductTypeChange("EXDEDU");
        $("#chargeDeductTypeDiv").hide();
        $("#chargeCollectionTypeDiv").hide();
        $("#settleTypeDiv").hide();
        $("#settleTypeDiv").find("option[value='0']").attr("selected", true);
        $("#settleTypeDiv").find("option[value='1']").attr("selected", false);
        $("#s2id_settleType").find(".select2-chosen").text($("#settleTypeDiv").find("option:selected").text());
        $("#settlementToDiv").hide();
        hideCommon();
    }

//充值
    function d() {
        $("#bankCardTypeDiv").hide();
        $("#ruleBeginTimeDiv").hide();
        $("#ruleEndTimeDiv").hide();
        $("#chargeSourceDiv").hide();
        $("#isRefundableDiv").hide();
        $("#chargeDeductTypeDiv").find("input[value='INDEDU']").attr("checked", 'checked');
        chargeDeductTypeChange("INDEDU");
        $("#chargeDeductTypeDiv").hide();
        $("#chargeCollectionTypeDiv").hide();
        $("#settleTypeDiv").hide();
        $("#settleTypeDiv").find("option[value='0']").attr("selected", true);
        $("#settleTypeDiv").find("option[value='1']").attr("selected", false);
        $("#s2id_settleType").find(".select2-chosen").text($("#settleTypeDiv").find("option:selected").text());
        $("#settlementToDiv").hide();
        $("#notifyUrlDiv").hide();
        $("#backUrlDiv").hide();
        $("#ipDomainDiv").hide();
        //$("#autographTypeDiv").hide();
        $("#chargeTypeCreditDiv").hide();
        hideCommon();
    }

//提现
    function e() {
        $("#bankCardTypeDiv").hide();
        $("#ruleBeginTimeDiv").hide();
        $("#ruleEndTimeDiv").hide();
        $("#chargeSourceDiv").hide();
        $("#isRefundableDiv").hide();
        $("#chargeDeductTypeDiv").find("input[value='EXDEDU']").attr("checked", 'checked');
        chargeDeductTypeChange("EXDEDU");
        $("#chargeDeductTypeDiv").hide();
        $("#chargeCollectionTypeDiv").hide();
        $("#settleTypeDiv").hide();
        $("#settleTypeDiv").find("option[value='0']").attr("selected", true);
        $("#settleTypeDiv").find("option[value='1']").attr("selected", false);
        $("#s2id_settleType").find(".select2-chosen").text($("#settleTypeDiv").find("option:selected").text());
        $("#settlementToDiv").hide();
        $("#notifyUrlDiv").hide();
        $("#backUrlDiv").hide();
        $("#ipDomainDiv").hide();
        //$("#autographTypeDiv").hide();
        $("#chargeTypeCreditDiv").hide();
        hideCommon();
    }

//退款
    function f() {
        $("#bankCardTypeDiv").hide();
        $("#ruleBeginTimeDiv").hide();
        $("#ruleEndTimeDiv").hide();
        $("#chargeTypeSavingDiv").hide();
        $("#chargeTypeCreditDiv").hide();
        $("#chargeSourceDiv").hide();
        $("#chargeDeductTypeDiv").hide();
        $("#chargeCollectionTypeDiv").hide();
        $("#settleTypeDiv").hide();
        $("#settleTypeDiv").find("option[value='0']").attr("selected", true);
        $("#settleTypeDiv").find("option[value='1']").attr("selected", false);
        $("#s2id_settleType").find(".select2-chosen").text($("#settleTypeDiv").find("option:selected").text());
        $("#settlementToDiv").hide();
        $("#notifyUrlDiv").hide();
        $("#backUrlDiv").hide();
        $("#ipDomainDiv").hide();
        //$("#autographTypeDiv").hide();
        hideCommon();
    }

//实名认证
    function g() {
        $("#bankCardTypeDiv").hide();
        $("#isRefundableDiv").hide();
        $("#chargeSourceDiv").hide();
        $("#chargeDeductTypeDiv").find("input[value='EXDEDU']").attr("checked", 'checked');
        $("#autographTypeDiv").find("input[value='MD5']").attr("checked", 'checked');
        chargeDeductTypeChange("EXDEDU");
        $("#chargeDeductTypeDiv").hide();
        $("#chargeCollectionTypeDiv").hide();
        $("#settleTypeDiv").hide();
        $("#settleTypeDiv").find("option[value='0']").attr("selected", true);
        $("#settleTypeDiv").find("option[value='1']").attr("selected", false);
        $("#s2id_settleType").find(".select2-chosen").text($("#settleTypeDiv").find("option:selected").text());
        $("#settlementToDiv").hide();
        $("#chargeTypeCreditDiv").hide();
        hideCommon();
    }

//B2B
    function h() {
        $("#bankCardTypeDiv").hide();
        $("#chargeSourceDiv").hide();
        $("#isRefundableDiv").hide();
        $("#chargeTypeCreditDiv").hide();
        $("#chargeDeductTypeDiv").find("input[value='INDEDU']").attr("checked", 'checked');
        $("#chargeSourceDiv").find("input[value='MERCHA']").attr("checked", 'checked');
        $("#autographTypeDiv").find("input[value='MD5']").attr("checked", 'checked');
        $("#settlementToDiv").find("option[value='ACCBAL']").attr("selected", true);
        $("#settlementToDiv").find("option[value='']").attr("selected", false);
        $("#s2id_settlementTo").find(".select2-chosen").text($("#settlementToDiv").find("option:selected").text());
        chargeDeductTypeChange("INDEDU");
        hideCommon();
    }

//平级分润
    function i() {
        $("#bankCardTypeDiv").hide();
        $("#chargeSourceDiv").hide();
        $("#isRefundableDiv").hide();
        $("#chargeDeductTypeDiv").find("input[value='EXDEDU']").attr("checked", 'checked');
        $("#autographTypeDiv").find("input[value='MD5']").attr("checked", 'checked');
        chargeDeductTypeChange("EXDEDU");
        $("#chargeDeductTypeDiv").hide();
        $("#chargeCollectionTypeDiv").hide();
        $("#settleTypeDiv").hide();
        $("#settleTypeDiv").find("option[value='1']").attr("selected", true);
        $("#settleTypeDiv").find("option[value='0']").attr("selected", false);
        $("#s2id_settleType").find(".select2-chosen").text($("#settleTypeDiv").find("option:selected").text());
        $("#settlementToDiv").hide();
        $("#chargeTypeCreditDiv").hide();
        hideCommon();
    }

//通关申报
    function costom() {
        $("#bankCardTypeDiv").hide();
        $("#chargeSourceDiv").hide();
        $("#isRefundableDiv").hide();
        $("#chargeTypeCreditDiv").hide();
        $("#chargeDeductTypeDiv").find("input[value='EXDEDU']").attr("checked", 'checked');
        $("#autographTypeDiv").find("input[value='MD5']").attr("checked", 'checked');
        chargeDeductTypeChange("EXDEDU");
        $("#chargeDeductTypeDiv").hide();
        $("#chargeCollectionTypeDiv").hide();
        $("#settleTypeDiv").hide();
        $("#settleTypeDiv").find("option[value='0']").attr("selected", true);
        $("#settleTypeDiv").find("option[value='1']").attr("selected", false);
        $("#s2id_settleType").find(".select2-chosen").text($("#settleTypeDiv").find("option:selected").text());
        $("#settlementToDiv").hide();
        $("#notifyUrlDiv").hide();
        $("#backUrlDiv").hide();
        $("#ipDomainDiv").hide();
        hideCommon();
    }

//存管提现
    function dptwzd() {
        $("#bankCardTypeDiv").hide();
        $("#ruleBeginTimeDiv").hide();
        $("#ruleEndTimeDiv").hide();
        $("#chargeSourceDiv").hide();
        $("#isRefundableDiv").hide();
        $("#autographTypeDiv").find("input[value='MD5']").attr("checked", 'checked');
        $("#chargeDeductTypeDiv").find("input[value='EXDEDU']").attr("checked", 'checked');
        chargeDeductTypeChange("EXDEDU");
        $("#chargeDeductTypeDiv").hide();
        $("#chargeCollectionTypeDiv").hide();
        $("#settleTypeDiv").hide();
        $("#settleTypeDiv").find("option[value='0']").attr("selected", true);
        $("#settleTypeDiv").find("option[value='1']").attr("selected", false);
        $("#s2id_settleType").find(".select2-chosen").text($("#settleTypeDiv").find("option:selected").text());
        $("#settlementToDiv").hide();
        $("#notifyUrlDiv").hide();
        $("#backUrlDiv").hide();
        $("#ipDomainDiv").hide();
        //$("#autographTypeDiv").hide();
        $("#chargeTypeCreditDiv").hide();
        hideCommon();
    }

//存管支付
    function dptpay() {
        $("#bankCardTypeDiv").hide();
        $("#chargeSourceDiv").hide();
        $("#isRefundableDiv").hide();
        $("#chargeTypeCreditDiv").hide();
        $("#chargeDeductTypeDiv").find("input[value='EXDEDU']").attr("checked", 'checked');
        $("#autographTypeDiv").find("input[value='MD5']").attr("checked", 'checked');
        $("#settlementToDiv").find("option[value='ACCBAL']").attr("selected", true);
        $("#settlementToDiv").find("option[value='']").attr("selected", false);
        $("#s2id_settlementTo").find(".select2-chosen").text($("#settlementToDiv").find("option:selected").text());
        chargeDeductTypeChange("EXDEDU");
        hideCommon();
    }

//手续费收取类型切换的方法
    function chargeDeductTypeChange(type) {
        var code = $("#productCode option:selected").data('type');
        switch (code) {
            case "BATCOL"://代收
            case "BATPAY"://转账
            case "CHARGE"://充值
            case "WZDRAW"://提现
            case "REFUND"://退款
            case "RENAME"://实名认证
            case "EPRISE"://企业认证
            case "AUTHEN"://鉴权认证
            case "SHARES"://平级分润
            case "CACNCE"://通关申报
            case "TRAFER"://内转
            case "DPTWZD"://存管提现
            case "WALLET"://钱包服务
                if (type == "INDEDU") {
                    $("#chargeCollectionTypeDiv").find("input[value='SETTLE']").attr("checked", 'checked');
                    $("#chargeCollectionTypeDiv").find("input[value='RETIME']").attr("disabled", 'disabled');
                } else if (type == "EXDEDU") {
                    $("#chargeCollectionTypeDiv").find("input[value='RETIME']").attr("checked", 'checked');
                    $("#chargeCollectionTypeDiv").find("input[value='SETTLE']").attr("disabled", 'disabled');
                }
                break;
            default:
                if (type == "INDEDU") {
                    //$("#chargeSourceDiv").show();
                    $("#chargeCollectionTypeDiv").find("input[value='SETTLE']").attr("checked", 'checked');
                    $("#chargeCollectionTypeDiv").find("input[value='RETIME']").attr("disabled", 'disabled');
                } else if (type == "EXDEDU") {
                    $("#chargeSourceDiv").hide();
                    $("#chargeCollectionTypeDiv").find("input[value='RETIME']").attr("checked", 'checked');
                    $("#chargeCollectionTypeDiv").find("input[value='SETTLE']").attr("disabled", 'disabled');
                }
                break;
        }
    }

//根据产品code获取交易类型(type)
    function getProductType(code) {
        $.ajax({
            async: false,
            type: "GET",
            url: myctx+"/merchant/product/getTypeByCode",
            data: {"code": code},
            dataType: 'json',
            success: function (data) {
                $("#productType").val(data.trxType);
            },
            error: function () {
                console.log("请求失败!");
            }
        });
    }
