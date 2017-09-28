<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>对账管理</title>
    <meta name="decorator" content="default"/>
    <script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
    <style>
        #main {
            margin: 50px;
        }
    </style>
    <script type="text/javascript">
        //验证搜索条件
        var validateFrom = {
            validate: function (form) {

                var bgTime = $("#beginTime").val();
                var endTime = $("#endTime").val();
                if (bgTime == "" && endTime != "" || bgTime != "" && endTime == "") {
                    $("#messageBox").text("请正确设置查询时间范围!");
                    return;
                }
                if (bgTime != "" && endTime != "") {
                    if (compareDate(convertDateToJoinStr(bgTime),
                            convertDateToJoinStr(endTime)) > 0) {
                        $("#messageBox").text("起始日期不能大于结束日期!");
                        return;
                    }
                }
                form.submit();
            }
        }


        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }

        //搜索
        function onSubmit() {
            $("#pageNo").val(1);
            validateFrom.validate($("#searchForm"));
        }

        //清空
        function onClear() {
            $("form :input").not(":button, :submit, :reset, :hidden").val("").removeAttr("checked").remove("selected");
            $("#searchForm").submit();
        }


    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/reconciliation/clearException">异常明细列表</a></li>
</ul>
<form action="${ctx}/reconciliation/clearingException" method="post" id="formBtn"></form>
<form:form id="searchForm" modelAttribute="clearingException" action="${ctx}/reconciliation/clearException"
           method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>差错状态：</label>
            <form:select id="status" path="status" style="width:225px;" class="input-xlarge">
                <form:option value="" label="-差错状态-"/>
                <c:forEach items="${status}" var="statusFor">
                    <form:option value="${statusFor.value}" label="${statusFor.name}"/>
                </c:forEach>
            </form:select>
        </li>
        <li><label>手续费扣除：</label>
            <form:select id="costWay" path="costWay" style="width:225px;" class="input-xlarge">
                <form:option value="" label="-手续费扣除-"/>
                <c:forEach items="${costWay}" var="costWayFor">
                    <form:option value="${costWayFor.value}" label="${costWayFor.name}"/>
                </c:forEach>
            </form:select>
        </li>

        <li><label>银行卡类型：</label>
            <form:select id="bankcardType" path="bankcardType" style="width:225px;" class="input-xlarge">
                <form:option value="" label="-银行卡类型-"/>
                <c:forEach items="${bankcardType}" var="bankcardTypeFor">
                    <form:option value="${bankcardTypeFor.value}" label="${bankcardTypeFor.name}"/>
                </c:forEach>
            </form:select>
        </li>

    </ul>
    <ul class="ul-form">
        <li><label>支付订单号：</label>
            <form:input path="paymentId" id="paymentId" value="${clearingException.paymentId}" htmlEscape="false" maxlength="40" class="input-medium" placeholder="支付订单号" style="width:210px;"/>
        </li>

        <li><label>银行流水号：</label>
            <form:input path="bankSeq" id="bankSeq" value="${clearingException.bankSeq}" htmlEscape="false" maxlength="40" class="input-medium" placeholder="银行流水号" style="width:210px;"/>
        </li>
        <li><label>商户编码：</label>
            <form:input path="merchantId" id="merchantId" value="${clearingException.merchantId}" htmlEscape="false" maxlength="40" class="input-medium" placeholder="商户编码" style="width:210px;"/>
        </li>
    </ul>
    <ul class="ul-form">
        <li><label>交易订单号：</label>
            <form:input path="transNo" id="transNo" value="${clearingException.transNo}" htmlEscape="false" maxlength="40" class="input-medium" placeholder="交易订单号" style="width:210px;"/>
        </li>
        <li><label>paymentID：</label>
            <form:input path="paymentid" id="paymentid" value="${clearingException.paymentid}" htmlEscape="false" maxlength="40" class="input-medium" placeholder="用户订单号" style="width:210px;"/>
        </li>

        <li><label>入库时间：</label>
            <input id="beginTime" name="beginTime" type="text" readonly="readonly" maxlength="20" style="width:95px;cursor: pointer" class="input-medium Wdate"
                   value="<fmt:formatDate value="${clearingException.beginTime}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
            <input id="endTime" name="endTime" type="text" readonly="readonly" maxlength="20"style="width:95px;cursor: pointer" class="input-medium Wdate"
                   value="<fmt:formatDate value="${clearingException.endTime}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/>
        </li>
        <li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清除"/>
        </li>
    </ul>
    <br>
    <div id="messageBox" style="font-size: 15px; color: red;"></div>

</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>通道编码</th>
        <th>支付单号</th>
        <th>订单金额</th>

        <th>手续费扣除方式</th>
        <th>交易类型</th>
        <th>银行编码</th>
        <th>银行名称</th>
        <th>银行流水号</th>
        <th>商户编码</th>
        <th>交易订单号</th>
        <th>paymentID</th>

        <th>交易金额</th>
        <th>手续费</th>
        <th>手续费扣除方式</th>
        <th>银行卡类型</th>
        <th>支付类型</th>
        <th>差错状态</th>
        <th>入库时间</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="clearingException">
        <tr>
            <td>${clearingException.channelCode}</td>
            <td>${clearingException.paymentId}</td>
            <td>${clearingException.payAmount}</td>

            <td>${clearingException.costWay}</td>
            <td>${clearingException.transType}</td>
            <td>${clearingException.bankCode}</td>
            <td>${clearingException.bankName}</td>
            <td>${clearingException.bankSeq}</td>
            <td> <a href="${ctx}/reconciliation/clearException/more/${clearingException.clearId}">${clearingException.merchantId}</a></td>
            <td>${clearingException.transNo}</td>
            <td>${clearingException.paymentid}</td>

            <td>${clearingException.requestAmount}</td>
            <td>${clearingException.fee}</td>
            <td>${clearingException.feeWay}</td>
            <td>${clearingException.bankcardType}</td>
            <td>${clearingException.payType}</td>
            <td>${clearingException.status}</td>
            <td>${clearingException.field1}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>

</body>
</html>