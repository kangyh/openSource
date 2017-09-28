<%--
  Created by IntelliJ IDEA.
  User: Tangwei
  Date: 2017/2/28
  Time: 15:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>代理商商户管理</title>
    <meta name="decorator" content="default"/>
    <style type="text/css">
        inputReadonly{
            border:none
        }
    </style>
    <link href="${ctxStatic}/common/img.css" type="text/css" rel="stylesheet">
    <script type="text/javascript">
        $(document).ready(function() {
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
<form:form id="inputForm" modelAttribute="agentMerchant" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <h3>基本信息</h3>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">当前状态：</label>
            <div class="controls">
                <input value="${agentMerchant.agentMerchantStatus}" maxlength="50" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">商户类型：</label>
            <div class="controls">
                <input value="${agentMerchant.merchantType}" maxlength="50" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
                    <%--					<form:input path="headName" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
                                        <span class="help-inline"><font color="red">*</font> </span>--%>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">商户名称：</label>
            <div class="controls">
                <input value="${agentMerchant.merchantName}" maxlength="11" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">代理商名称：</label>
            <div class="controls">
                <input value="${agentMerchant.agentName}" maxlength="18" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">代理商简称：</label>
            <div class="controls">
                <input value="${agentMerchant.agentShortName}" maxlength="10" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">负责人姓名：</label>
            <div class="controls">
                <input value="${agentMerchant.headName}" maxlength="50" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">负责人手机号：</label>
            <div class="controls">
                <input value="${agentMerchant.headPhone}" maxlength="10" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">负责人身份证号：</label>
            <div class="controls">
                <input value="${agentMerchant.headIdcard}" maxlength="12" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">身份证有效期：</label>
            <div class="controls">
                <input value="${agentMerchant.headIdcardExpiry}" maxlength="10" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
                <%--<fmt:parseDate value="${agentMerchant.headIdcardExpiry}" var="date1"></fmt:parseDate>
                <fmt:formatDate value="${date1 }" pattern="yyyy 年 MM 月 dd 日"/>--%>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">电子邮箱：</label>
            <div class="controls">
                <input value="${agentMerchant.email}" maxlength="10" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">有效期：</label>
            <div class="controls">
                <input value="${agentMerchant.merchantExpiry}" maxlength="10" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
                <%--<fmt:parseDate value="${agentMerchant.merchantExpiry}" var="date1"></fmt:parseDate>
                <fmt:formatDate value="${date1 }" pattern="yyyy 年 MM 月 dd 日"/>--%>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">加入日期：</label>
            <div class="controls">
                <input value="<fmt:formatDate value="${agentMerchant.createTime}" pattern="yyyy年MM月dd日"/>" maxlength="30" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">最新修改日期：</label>
            <div class="controls">
                <input value="<fmt:formatDate value="${agentMerchant.updateTime}" pattern="yyyy年MM月dd日"/>" maxlength="30" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
            </div>
        </div>
    </div>
    <h3>企业信息</h3>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">法人姓名：</label>
            <div class="controls">
                <input value="${agentMerchant.legalName}" maxlength="50" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">法人手机号：</label>
            <div class="controls">
                <input value="${agentMerchant.legalPhone}" maxlength="11" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">法人身份证号：</label>
            <div class="controls">
                <input value="${agentMerchant.legalIdcard}" maxlength="18" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">法人身份证有效期：</label>
            <div class="controls">
                <input value="${agentMerchant.legalIdcardExpiry}" maxlength="50" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
                <%--<fmt:parseDate value="${agentMerchant.legalIdcardExpiry}" var="date1"></fmt:parseDate>
                <fmt:formatDate value="${date1 }" pattern="yyyy 年 MM 月 dd 日"/>--%>

                    <%--					<input name="legalIdcardExpiry" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
                                            value="<fmt:formatDate value="${agentInfo.legalIdcardExpiry}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
                                        <span class="help-inline"><font color="red">*</font> </span>--%>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">营业证照类型：</label>
            <div class="controls">
                <input value="${agentMerchant.busiType}" maxlength="50" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">营业证照编号：</label>
            <div class="controls">
                <input value="${agentMerchant.busiCode}" maxlength="50" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">营业证照有效期：</label>
            <div class="controls">
                <input value="${agentMerchant.busiExpiry}" maxlength="50" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
                    <%--<fmt:parseDate value="${agentMerchant.busiExpiry}" var="date1"></fmt:parseDate>
                    <fmt:formatDate value="${date1}" pattern="yyyy 年 MM 月 dd 日"/>--%>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">注册资金：</label>
            <div class="controls">
                <input value="${agentMerchant.regAmount}" maxlength="50" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
            </div>
        </div>
    </div>
    <c:if test="${agentMerchant.busiExpiry=='普通'}">
        <div class="control-group">
            <div class="control-left">
                <label class="control-label">组织机构代码证：</label>
                <div class="controls">
                    <input value="${agentMerchant.orgInstCode}" maxlength="30" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">税务登记证：</label>
                <div class="controls">
                    <input value="${agentMerchant.taxRegCode}" maxlength="30" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
                </div>
            </div>
        </div>
    </c:if>
    <div class="control-group">
        <label class="control-label">所在地：</label>
        <div class="controls">
            <input value="${agentMerchant.addrProvinceName} ${agentMerchant.addrCityName} ${agentMerchant.addrCountryName} ${agentMerchant.addrDetail}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
        </div>
    </div>

    <%--<div class="control-group">--%>
        <%--<label class="control-label">最低结算金额：</label>--%>
        <%--<div class="controls">--%>
            <%--323元（目前没有这个字段）--%>
        <%--</div>--%>
    <%--</div>--%>
    <h3>账户信息</h3>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">账户类型：</label>
            <div class="controls">
                    <input value="${agentMerchant.bankAccountType}" maxlength="30" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">开户行：</label>
            <div class="controls">
                    <input value="${agentMerchant.bankName}" maxlength="70" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">开户行所在地：</label>
            <div class="controls">
                    <input value="${agentMerchant.bankProvinceName} ${agentMerchant.bankCityName}" maxlength="30" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">开户支行名称：</label>
            <div class="controls">
                    <input value="${agentMerchant.bankBranchName}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">银行账户名：</label>
            <div class="controls">
                    <input value="${agentMerchant.bankAccountName}" maxlength="50" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">银行账号：</label>
            <div class="controls">
                    <input value="${agentMerchant.bankAccountCode}" maxlength="30" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
            </div>
        </div>
    </div>
    <h3>图片信息</h3>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">身份证（个人信息页）：</label>
            <div class="controls">
                <a class="popA">图片</a>
                <p class="pop_img">
                    <span>X</span>
                    <img src="${agentMerchant.idcardFaceImage}">
                </p>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">身份证（国徽页）：</label>
            <div class="controls">
                <a class="popA">图片</a>
                <p class="pop_img">
                    <span>X</span>
                    <img src="${agentMerchant.idcardBackImage}">
                </p>
            </div>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">合作协议：</label>
        <div class="controls">
            <a class="popA">图片</a>
            <p class="pop_img">
                <span>X</span>
                <img src="${agentMerchant.agreementFile}">
            </p>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">营业执照：</label>
        <div class="controls">
            <a class="popA">图片</a>
            <p class="pop_img">
                <span>X</span>
                <img src="${agentMerchant.busiImage}">
            </p>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">税务登记证：</label>
        <div class="controls">
            <a class="popA">图片</a>
            <p class="pop_img">
                <span>X</span>
                <img src="${agentMerchant.taxRegImage}">
            </p>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">组织机构代码：</label>
        <div class="controls">
            <a class="popA">图片</a>
            <p class="pop_img">
                <span>X</span>
                <img src="${agentMerchant.orgInstImage}">
            </p>
        </div>
    </div>
    <%--<div class="control-group">
        <label class="control-label">其他辅助材料：</label>
        <div class="controls">
            <a class="popA">图片</a>
            <p class="pop_img">
                <span>X</span>
                <img src="${agentMerchant.otherGenaImage}">
            </p>
        </div>
    </div>--%>
    <div class="form-actions">
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>
