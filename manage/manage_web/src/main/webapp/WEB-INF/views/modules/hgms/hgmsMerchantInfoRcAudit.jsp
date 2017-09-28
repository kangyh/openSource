<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <link href="${ctxStatic}/common/img.css" type="text/css" rel="stylesheet">
    <title>资金归集商户管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
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

            if(${hgmsMerchantInfo.certificateType == "多证合一"}){
                $("#organizeAndtaxRegist").hide();
            }else if(${hgmsMerchantInfo.certificateType == "普通证件"} || ${hgmsMerchantInfo.certificateType == "个体商户"}) {
                $("#organizeAndtaxRegist").show();
            }
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
        });
        function pass(){
            if($("#objection").val() != ""){
                parent.showThree();
                parent.changeThreeName("通过时拒绝理由应为空");
                return false;
            }
            $("#rcAuditStatus").val("SUCCES");
        }
        function reject(){
            if($("#objection").val() == ""){
                parent.showThree();
                parent.changeThreeName("拒绝理由不能为空");
                return false;
            }
            $("#rcAuditStatus").val("AUDREJ");
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li ><a href="${ctx}/hgms/hgmsMerchantInfoRc">资金归集风控审核列表</a></li>
    <li class="active"><a href="${ctx}/hgms/hgmsMerchantInfoRc/audit?id=${hgmsMerchantInfo.merchantId}&goal=3">资金归集商户风控审核页面</a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="hgmsMerchantInfo" action="${ctx}/hgms/hgmsMerchantInfoRc/update" method="post" class="form-horizontal" enctype="multipart/form-data">
    <sys:message content="${message}"/>
    <form:hidden path="merchantId" />
    <div class="control-group">公司基本信息<hr width="80%" align="left" size="4" noshade=""></div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">商户账号：</label>
            <div class="controls">
                <form:input path="loginName" readonly="true"  class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">国家名称：</label>
            <div class="controls">
                <form:input path="contryName" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>

    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">集团总部ID：</label>
            <div class="controls">
                <form:input path="superiorId" class="input-xlarge" style="border:0px;background-color:#fff;padding-top: 3px;"/>
                <span class="id-inline"><font color="red"></font> </span>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">总部公司名称：</label>
            <div class="controls">
                <form:input id="superiorName" path="superiorName" class="input-xlarge"  readonly="true" style="border:0px;background-color:#fff;padding-top: 3px;" />
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">证件类型：</label>
        <div class="controls">
            <form:input path="certificateType" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
        </div>
    </div>
    <div class="control-group">
        <div class="control-group">
            <label class="control-label">证件地址：</label>
            <div class="controls">
                <form:input path="provinceName" readonly="true" class="input-medium " style="border:0px;background-color:#fff;padding-top: 3px;"/>
                <form:input path="cityName" readonly="true" class="input-medium " style="border:0px;background-color:#fff;padding-top: 3px;"/>
                <form:input path="countyName" readonly="true" class="input-medium " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">单位类型：</label>
        <div class="controls">
            <form:input path="type" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">公司名称：</label>
            <div class="controls">
                <form:input path="companyName" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">事业类型：</label>
            <div class="controls">
                <form:input path="industryTypes" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">公司网址：</label>
            <div class="controls">
                <form:input path="website" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">公司联系电话：</label>
            <div class="controls">
                <form:input path="companyPhone" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">营业执照号码：</label>
            <div class="controls">
                <form:input path="businessLicenseNo" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">营业执照有效期：</label>
            <div class="controls">
                <c:if test="${ hgmsMerchantInfo.businessLicenseEndTime!='Thu Nov 30 00:00:00 CST 2'}">
                    <input value="<fmt:formatDate value="${hgmsMerchantInfo.businessLicenseEndTime}" pattern="yyyy-MM-dd"/>" readonly style="border:0px;background-color:#fff;padding-top: 3px;"  />
                </c:if>
                <c:if test="${ hgmsMerchantInfo.businessLicenseEndTime=='Thu Nov 30 00:00:00 CST 2'}">
                    <input type="text" readonly="true" style="border:0px;background-color:#fff;padding-top: 3px;" value="长期" />
                </c:if>
            </div>
        </div>
    </div>
    <div class="control-group" id="organizeAndtaxRegist">
        <div class="control-left">
            <label class="control-label">组织机构代码：</label>
            <div class="controls">
                <form:input path="organizationCode" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">税务登记证号码：</label>
            <div class="controls">
                <form:input path="taxRegistrationCertificateNo" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-left">
        <label class="control-label">ICP备案号：</label>
        <div class="controls">
            <form:input path="ipcNo" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">员工人数：</label>
        <div class="controls">
            <form:input path="employeeAccount" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">经营范围：</label>
            <div class="controls">
                <form:input path="businessScope" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">公司注册地址：</label>
            <div class="controls">
                <form:input path="businessAddress" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-group">负责人基本信息<hr width="80%" align="left" size="4" noshade=""></div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">法人名称：</label>
            <div class="controls">
                <form:input path="legalRepresentative" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">法人手机号码：</label>
            <div class="controls">
                <form:input path="legalMobile" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>

    <div class="control-group">
        <div class="control-left">
            <label class="control-label">法人代表身份证号：</label>
            <div class="controls">
                <form:input path="legalIdcard" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">法人代表证件有效期：</label>
            <div class="controls">
                <c:if test="${ hgmsMerchantInfo.legalCertificateEndTime!='Thu Nov 30 00:00:00 CST 2'}">
                    <input value="<fmt:formatDate value="${hgmsMerchantInfo.legalCertificateEndTime}" pattern="yyyy-MM-dd"/>" readonly style="border:0px;background-color:#fff;padding-top: 3px;"  />
                </c:if>
                <c:if test="${ hgmsMerchantInfo.legalCertificateEndTime=='Thu Nov 30 00:00:00 CST 2'}">
                    <input type="text" readonly="true" style="border:0px;background-color:#fff;padding-top: 3px;" value="长期" />
                </c:if>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">法人邮箱：</label>
            <div class="controls">
                <form:input path="legalPersionEmail" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">法人联系地址：</label>
            <div class="controls">
                <form:input path="legalPersionAddress" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">联系人：</label>
            <div class="controls">
                <form:input path="contactor" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">联系人手机号：</label>
            <div class="controls">
                <form:input path="contactorPhone" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>

    <div class="control-group">
        <div class="control-left">
            <label class="control-label">联系人身份证号：</label>
            <div class="controls">
                <form:input path="contactorIdcardNo" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">联系人证件有效期：</label>
            <div class="controls">
                <c:if test="${ hgmsMerchantInfo.contactorCertificateEndTime!='Thu Nov 30 00:00:00 CST 2'}">
                    <input value="<fmt:formatDate value="${hgmsMerchantInfo.contactorCertificateEndTime}" pattern="yyyy-MM-dd"/>" readonly style="border:0px;background-color:#fff;padding-top: 3px;"  />
                </c:if>
                <c:if test="${ hgmsMerchantInfo.contactorCertificateEndTime=='Thu Nov 30 00:00:00 CST 2'}">
                    <input type="text" readonly="true" style="border:0px;background-color:#fff;padding-top: 3px;" value="长期" />
                </c:if>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">联系人邮箱：</label>
            <div class="controls">
                <form:input path="contactorPersionEmail" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">联系人联系地址：</label>
            <div class="controls">
                <form:input path="contactorPersionAddress" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-group">结算银行卡信息<hr width="80%" align="left" size="4" noshade=""></div>

    <div class="control-group">
        <div class="control-left">
            <label class="control-label">银行卡号：</label>
            <div class="controls">
                <form:input path="bankcardNo" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">银行卡持卡人名称：</label>
            <div class="controls">
                <form:input path="bankcardOwnerName" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">银行预留手机号：</label>
            <div class="controls">
                <form:input path="bankcardOwnerMobile" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">银行卡有效期：</label>
            <div class="controls">
                <form:input path="bankcardExpiredDate" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">银行卡持卡人类型：</label>
            <div class="controls">
                <form:input path="bankcardOwnerType" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">银行卡类型：</label>
            <div class="controls">
                <form:input path="bankcardType" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-group"  >
        <div class="controls" style="display: none" >
            <form:input path="bankName" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">银行名称：</label>
            <div class="controls" >
                <form:input path="bankName" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">省份名称：</label>
            <div class="controls">
                <form:input path="unionPayProvinceName" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">城市名称：</label>
            <div class="controls">
                <form:input path="unionPayCityName" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right" >
            <label class="control-label">开户银行名称：</label>
            <div class="controls">
                <form:input path="openBankName" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">联行号：</label>
            <div class="controls">
                <form:input path="associateLineNumber" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>

        <div class="control-right" id="bankcardOwnerIdcard">
            <c:if test="${hgmsMerchantInfo.bankcardOwnerType == '个人'}" >
                <label class="control-label">持卡人身份证号：</label>
                <div class="controls">
                    <form:input path="bankcardOwnerIdcard" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>

            </c:if>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">开户许可证：</label>
        <div class="controls">
            <a class="popA">图片</a>
            <p class="pop_img">
                <span>X</span>
                <img src="${hgmsMerchantInfo.permitsAccounts}">
            </p>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">法人代表证件照(正)：</label>
        <div class="controls">
            <a class="popA">图片</a>
            <p class="pop_img">
                <span>X</span>
                <img src="${hgmsMerchantInfo.legalCertificateFront}">
            </p>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">法人代表证件照(反)：</label>
        <div class="controls">
            <a class="popA">图片</a>
            <p class="pop_img">
                <span>X</span>
                <img src="${hgmsMerchantInfo.legalCertificateBack}">
            </p>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">营业执照证（三证合一）：</label>
        <div class="controls">
            <a class="popA">图片</a>
            <p class="pop_img">
                <span>X</span>
                <img src="${hgmsMerchantInfo.businessLicenseFile}">
            </p>
        </div>
    </div>
    <c:if test="${hgmsMerchantInfo.certificateType != '多证合一'}" >
        <div class="control-group">
            <label class="control-label">税务登记证：</label>
            <div class="controls">
                <a class="popA">图片</a>
                <p class="pop_img">
                    <span>X</span>
                    <img src="${hgmsMerchantInfo.taxRegistrationCertificate}">
                </p>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">组织机构代码证：</label>
            <div class="controls">
                <a class="popA">图片</a>
                <p class="pop_img">
                    <span>X</span>
                    <img src="${hgmsMerchantInfo.organizationCodeCertificate}">
                </p>
            </div>
        </div>
    </c:if>

    <div class="control-group">
        <div class="control-left">
            <label class="control-label">拒绝理由：</label>
            <div class="controls">
                <form:input id="objection" path="objection" htmlEscape="false" maxlength="50" class="input-xlarge"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">风险类型：</label>
            <div class="controls">
                <form:select path="creditLevel" class="input-xlarge required">
                    <form:option value="" label=""/>
                    <form:options items="${riskLevel}" itemLabel="name" itemValue="value" htmlEscape="false" class="input-xlarge required"/>
                </form:select>
            </div>
        </div>
    </div>
    <input type="hidden"  id="legalAuditStatus" name="legalAuditStatus" />
    <input type="hidden"  id="rcAuditStatus" name="rcAuditStatus" />
    <div class="form-actions">
        <shiro:hasPermission name="hgms:hgmsMerchantInfoRc:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="通 过" onclick="return pass();"/>&nbsp;</shiro:hasPermission>
        <shiro:hasPermission name="hgms:hgmsMerchantInfoRc:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="拒 绝" onclick="return reject();"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>