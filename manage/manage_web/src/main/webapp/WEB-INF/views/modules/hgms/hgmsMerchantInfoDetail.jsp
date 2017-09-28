<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <link href="${ctxStatic}/common/img.css" type="text/css" rel="stylesheet">
    <title>资金归集商户管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
            if(${hgmsMerchantInfo.certificateType == "多证合一"}){
                $("#organizeAndtaxRegist").hide();
            }else if(${hgmsMerchantInfo.certificateType == "普通证件"} || ${hgmsMerchantInfo.certificateType == "个体商户"}) {
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


        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <c:if test="${goal == 1}">
        <li><a href="${ctx}/hgms/hgmsMerchantInfo/">资金归集商户列表</a></li>
        <li class="active"><a href="${ctx}/hgms/hgmsMerchantInfo/detail?id=${hgmsMerchantInfo.merchantId}&goal=1">资金归集商户详情</a></li>
    </c:if>
    <c:if test="${goal == 6}">
        <li ><a href="${ctx}/hgms/hgmsMerchantInfoLegal">资金归集法务审核列表</a></li>
        <li class="active"><a href="${ctx}/hgms/hgmsMerchantInfo/detail?id=${hgmsMerchantInfo.merchantId}&goal=6">资金归集商户详情</a></li>
    </c:if>
    <c:if test="${goal == 7}">
        <li ><a href="${ctx}/hgms/hgmsMerchantInfoRc">资金归集风控审核列表</a></li>
        <li class="active"><a href="${ctx}/hgms/hgmsMerchantInfo/detail?id=${hgmsMerchantInfo.merchantId}&goal=7">资金归集商户详情</a></li>
    </c:if>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="hgmsMerchantInfo" class="form-horizontal">
        <form:hidden path="merchantId" />
		<sys:message content="${message}"/>
        <div class="control-group">公司基本信息<hr width="80%" align="left" size="4" noshade=""></div>
        <div class="control-group">
            <div class="control-left">
                <label class="control-label">商户账号：</label>
                <div class="controls">
                    <input value="${hgmsMerchantInfo.loginName}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">国家名称：</label>
                <div class="controls">
                    <input value="${hgmsMerchantInfo.contryName}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>

        </div>
        <div class="control-group">
            <div class="control-left">
                <label class="control-label">集团总部ID：</label>
                <div class="controls">
                    <input value="${hgmsMerchantInfo.superiorId}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">总部公司名称：</label>
                <div class="controls">
                    <input value="${hgmsMerchantInfo.superiorName}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">证件类型：</label>
            <div class="controls">
                <input value="${hgmsMerchantInfo.certificateType}" readonly
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-group">
            <div class="control-group">
                <label class="control-label">证件地址：</label>
                <div class="controls">
                    <input value="${hgmsMerchantInfo.provinceName}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                    <input value="${hgmsMerchantInfo.cityName}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                    <input value="${hgmsMerchantInfo.countyName}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">单位类型：</label>
            <div class="controls">
                <input value="${hgmsMerchantInfo.type}" readonly
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-group">
            <div class="control-left">
                <label class="control-label">公司名称：</label>
                <div class="controls">
                    <input value="${hgmsMerchantInfo.companyName}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">事业类型：</label>
                <div class="controls">
                    <input value="${hgmsMerchantInfo.industryTypes}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
        </div>
        <div class="control-group">
            <div class="control-left">
                <label class="control-label">公司网址：</label>
                <div class="controls">
                    <input value="${hgmsMerchantInfo.website}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">公司联系电话：</label>
                <div class="controls">
                    <input value="${hgmsMerchantInfo.companyPhone}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
        </div>
        <div class="control-group">
            <div class="control-left">
                <label class="control-label">营业执照号码：</label>
                <div class="controls">
                    <input value="${hgmsMerchantInfo.businessLicenseNo}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">营业执照有效期：</label>
                <div class="controls">
                    <c:if test="${ hgmsMerchantInfo.businessLicenseEndTime!='Thu Nov 30 00:00:00 CST 2'}">
                        <input value="<fmt:formatDate value="${hgmsMerchantInfo.businessLicenseEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                               readonly style="border:0px;background-color:#fff;padding-top: 3px;"/>
                    </c:if>
                    <c:if test="${ hgmsMerchantInfo.businessLicenseEndTime=='Thu Nov 30 00:00:00 CST 2'}">
                        <input value="长期" readonly
                               style="border:0px;background-color:#fff;padding-top: 3px;"/>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="control-group" id="organizeAndtaxRegist">
            <div class="control-left">
                <label class="control-label">组织机构代码：</label>
                <div class="controls">
                    <input value="${hgmsMerchantInfo.organizationCode}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">税务登记证号码：</label>
                <div class="controls">
                    <input value="${hgmsMerchantInfo.taxRegistrationCertificateNo}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
        </div>
        <div class="control-group">
            <div class="control-left">
                <label class="control-label">ICP备案号：</label>
                <div class="controls">
                    <input value="${hgmsMerchantInfo.ipcNo}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">员工人数：</label>
                <div class="controls">
                    <input value="${hgmsMerchantInfo.employeeAccount}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
        </div>
        <div class="control-group">
            <div class="control-left">
                <label class="control-label">经营范围：</label>
                <div class="controls">
                    <input value="${hgmsMerchantInfo.businessScope}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">公司注册地址：</label>
                <div class="controls">
                            ${hgmsMerchantInfo.businessAddress}
                </div>
            </div>
        </div>
        <div class="control-group">负责人基本信息<hr width="80%" align="left" size="4" noshade=""></div>
        <div class="control-group">
            <div class="control-left">
                <label class="control-label">法人名称：</label>
                <div class="controls">
                    <input value="${hgmsMerchantInfo.legalRepresentative}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">法人手机号码：</label>
                <div class="controls">
                    <input value="${hgmsMerchantInfo.legalMobile}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
        </div>

        <div class="control-group">
            <div class="control-left">
                <label class="control-label">法人代表身份证号：</label>
                <div class="controls">
                    <input value="${hgmsMerchantInfo.legalIdcard}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">法人代表证件有效期：</label>
                <div class="controls">
                    <c:if test="${ hgmsMerchantInfo.legalCertificateEndTime!='Thu Nov 30 00:00:00 CST 2'}">
                        <input value="<fmt:formatDate value="${hgmsMerchantInfo.legalCertificateEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                               readonly style="border:0px;background-color:#fff;padding-top: 3px;"/>
                    </c:if>
                    <c:if test="${ hgmsMerchantInfo.legalCertificateEndTime=='Thu Nov 30 00:00:00 CST 2'}">
                        <input value="长期" readonly
                               style="border:0px;background-color:#fff;padding-top: 3px;"/>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="control-group">
            <div class="control-left">
                <label class="control-label">法人邮箱：</label>
                <div class="controls">
                    <input value="${hgmsMerchantInfo.legalPersionEmail}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">法人联系地址：</label>
                <div class="controls">
                        ${hgmsMerchantInfo.legalPersionAddress}
                </div>
            </div>
        </div>
        <div class="control-group">
            <div class="control-left">
                <label class="control-label">联系人：</label>
                <div class="controls">
                    <input value="${hgmsMerchantInfo.contactor}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">联系人手机号：</label>
                <div class="controls">
                    <input value="${hgmsMerchantInfo.contactorPhone}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
        </div>

        <div class="control-group">
            <div class="control-left">
                <label class="control-label">联系人身份证号：</label>
                <div class="controls">
                    <input value="${hgmsMerchantInfo.contactorIdcardNo}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">联系人证件有效期：</label>
                <div class="controls">
                    <c:if test="${ hgmsMerchantInfo.contactorCertificateEndTime!='Thu Nov 30 00:00:00 CST 2'}">
                        <input value="<fmt:formatDate value="${hgmsMerchantInfo.contactorCertificateEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                               readonly style="border:0px;background-color:#fff;padding-top: 3px;"/>
                    </c:if>
                    <c:if test="${ hgmsMerchantInfo.contactorCertificateEndTime=='Thu Nov 30 00:00:00 CST 2'}">
                        <input value="长期" readonly
                               style="border:0px;background-color:#fff;padding-top: 3px;"/>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="control-group">
            <div class="control-left">
                <label class="control-label">联系人邮箱：</label>
                <div class="controls">
                    <input value="${hgmsMerchantInfo.contactorPersionEmail}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">联系人联系地址：</label>
                <div class="controls">
                    ${hgmsMerchantInfo.contactorPersionAddress}
                </div>
            </div>
        </div>
        <div class="control-group">结算银行卡信息<hr width="80%" align="left" size="4" noshade=""></div>

        <div class="control-group">
            <div class="control-left">
                <label class="control-label">银行卡号：</label>
                <div class="controls">
                    <input value="${hgmsMerchantInfo.bankcardNo}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">银行卡持卡人名称：</label>
                <div class="controls">
                    <input value="${hgmsMerchantInfo.bankcardOwnerName}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
        </div>
        <div class="control-group">
            <div class="control-left">
                <label class="control-label">银行预留手机号：</label>
                <div class="controls">
                    <input value="${hgmsMerchantInfo.bankcardOwnerMobile}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">银行卡有效期：</label>
                <div class="controls">
                    <input value="${hgmsMerchantInfo.bankcardExpiredDate}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
        </div>
        <div class="control-group">
            <div class="control-left">
                <label class="control-label">银行卡持卡人类型：</label>
                <div class="controls">
                    <input value="${hgmsMerchantInfo.bankcardOwnerType}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">银行卡类型：</label>
                <div class="controls">
                    <input value="${hgmsMerchantInfo.bankcardType}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
        </div>
        <div class="control-group"  >
            <div class="controls" style="display: none" >
                <input value="${hgmsMerchantInfo.bankName}" readonly
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-group">
            <div class="control-left">
                <label class="control-label">银行名称：</label>
                <div class="controls" >
                    <input value="${hgmsMerchantInfo.bankName}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">省份名称：</label>
                <div class="controls">
                    <input value="${hgmsMerchantInfo.unionPayProvinceName}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
        </div>
        <div class="control-group">
            <div class="control-left">
                <label class="control-label">城市名称：</label>
                <div class="controls">
                    <input value="${hgmsMerchantInfo.unionPayCityName}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
            <div class="control-right" >
                <label class="control-label">开户银行名称：</label>
                <div class="controls">
                    ${hgmsMerchantInfo.openBankName}
                </div>
            </div>
        </div>
        <div class="control-group">
            <div class="control-left">
                <label class="control-label">联行号：</label>
                <div class="controls">
                    <input value="${hgmsMerchantInfo.associateLineNumber}" readonly
                           style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>

            <div class="control-right" id="bankcardOwnerIdcard">
                <c:if test="${hgmsMerchantInfo.bankcardOwnerType == '个人'}" >
                    <label class="control-label">持卡人身份证号：</label>
                    <div class="controls">
                        <input value="${hgmsMerchantInfo.bankcardOwnerIdcard}" readonly
                               style="border:0px;background-color:#fff;padding-top: 3px;"/>
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
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>