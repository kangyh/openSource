<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>规则表管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
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
        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/hgms/hgmsRegularRule/">规则表列表</a></li>
    <li class="active"><a href="${ctx}/hgms/hgmsRegularRule/detail?id=${hgmsRegularRule.ruleId}">规则表查看</a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="hgmsRegularRule" action="" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">规则ID：</label>
            <div class="controls">
                <input value="${hgmsRegularRule.ruleId}" maxlength="100" readonly
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">任务名称：</label>
            <div class="controls">
                <input value="${hgmsRegularRule.ruleName}" maxlength="100" readonly
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">创建商户编码：</label>
            <div class="controls">
                <input value="${hgmsRegularRule.merchantId}" maxlength="100" readonly
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">创建商户名称：</label>
            <div class="controls">
                <input value="${hgmsRegularRule.companyName}" maxlength="100" readonly
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">规则类型：</label>
            <div class="controls">
                <input value="${hgmsRegularRule.ruleType}" maxlength="100" readonly
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">规则模式：</label>
            <div class="controls">
                <input value="${hgmsRegularRule.ruleMode}" maxlength="100" readonly
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">规则形式：</label>
        <div class="controls">
            <input value="${hgmsRegularRule.ruleForms}" maxlength="100" readonly
                   style="border:0px;background-color:#fff;padding-top: 3px;"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">月：</label>
        <div class="controls">
            <input value="${hgmsRegularRule.ruleModeMonth}" maxlength="100" readonly
                   style="border:0px;background-color:#fff;padding-top: 3px;"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">日：</label>
        <div class="controls">
            <input value="${hgmsRegularRule.ruleModeDay}" maxlength="100" readonly
                   style="border:0px;background-color:#fff;padding-top: 3px;"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">周：</label>
        <div class="controls">
            <input value="${hgmsRegularRule.ruleModeWeek}" maxlength="100" readonly
                   style="border:0px;background-color:#fff;padding-top: 3px;"/>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">用户管理状态：</label>
            <div class="controls">
                <input value="${hgmsRegularRule.userStatus}" maxlength="100" readonly
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">管理员管理状态：</label>
            <div class="controls">
                <input value="${hgmsRegularRule.adminStatus}" maxlength="100" readonly
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">归集金额：</label>
        <div class="controls">
            <input value="${hgmsRegularRule.payAmount}" maxlength="100" readonly
                   style="border:0px;background-color:#fff;padding-top: 3px;"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">创建时间：</label>
        <div class="controls">
            <input value="<fmt:formatDate value="${hgmsRegularRule.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;"/>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">修改人：</label>
            <div class="controls">
                <input value="${hgmsRegularRule.updateUserId}" maxlength="100" readonly
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">修改时间：</label>
            <div class="controls">
                <input value="<fmt:formatDate value="${hgmsRegularRule.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                       maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">交易类型：</label>
            <div class="controls">
                <input value="${hgmsRegularRule.transWay}" maxlength="100" readonly
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">规则模式详情：</label>
            <div class="controls">
                <input value="${hgmsRegularRule.ruleDetail}" maxlength="100" readonly
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="form-actions">
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>