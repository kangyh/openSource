<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>资金归集个人商户管理</title>
	<meta name="decorator" content="default"/>
    <link href="${ctxStatic}/common/img.css" type="text/css" rel="stylesheet">
    <script type="text/javascript">
        $(document).ready(function() {
            $(".pop_img span").on("click",function(){
                $(".pop_img").hide();
            });

            $(".popA").on("click",function(){
                $(this).siblings(".pop_img").show();
            });
        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <c:if test="${goal == 1}">
        <li><a href="${ctx}/hgms/hgmsMerchantInfo/">资金归集商户列表</a></li>
        <li class="active"><a href="${ctx}/hgms/hgmsMerchantInfo/edit?id=${hgmsMerchantInfo.merchantId}&goal=1">资金归集商户详情</a></li>
    </c:if>
    <c:if test="${goal == 6}">
        <li ><a href="${ctx}/hgms/hgmsMerchantInfoLegal">资金归集法务审核列表</a></li>
        <li class="active"><a href="${ctx}/hgms/hgmsMerchantInfo/edit?id=${hgmsMerchantInfo.merchantId}&goal=6">资金归集商户详情</a></li>
    </c:if>
    <c:if test="${goal == 7}">
        <li ><a href="${ctx}/hgms/hgmsMerchantInfoRc">资金归集风控审核列表</a></li>
        <li class="active"><a href="${ctx}/hgms/hgmsMerchantInfo/edit?id=${hgmsMerchantInfo.merchantId}&goal=7">资金归集商户详情</a></li>
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
                    <form:input path="loginName" readonly="true"  class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">账户类型：</label>
                <div class="controls">
                    <form:input path="companyType" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
        </div>
        <div class="control-group">
            <div class="control-left">
                <label class="control-label">国家名称：</label>
                <div class="controls">
                    <form:input path="contryName" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
            <div class="control-right" id="superiorId">
                <c:if test="${hgmsMerchantInfo.companyType == '分公司'}">
                    <label class="control-label">集团总部的ID：</label>
                    <div class="controls">
                        <form:input path="superiorId" readonly="true" class="input-medium " style="border:0px;background-color:#fff;padding-top: 3px;" />
                    </div>
                </c:if>
            </div>
        </div>
        <%--<div class="control-group">
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
        </div>--%>
        <div class="control-group">
            <label class="control-label">单位类型：</label>
            <div class="controls">
                <form:input path="type" readonly="true" class="input-xlarge " style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <%--<div class="control-group">
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
        </div>--%>

        <div class="control-group">负责人基本信息<hr width="80%" align="left" size="4" noshade=""></div>

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

		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>