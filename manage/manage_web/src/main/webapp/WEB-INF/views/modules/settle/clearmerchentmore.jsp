<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>清结算管理</title>
    <meta name="decorator" content="default"/>
    <script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="#">商户清算单据列表详情页面</a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="clearingMerchantRecord" method="post" class="form-horizontal">

    <div style="font-size: 2em;font-family: serif;font: bold;padding-bottom: 12px;padding-top: 20px;">商户信息:</div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">商户编码：</label>
            <div class="controls">
                <input name="channelName" value="${clearingMerchantRecord.merchantId}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">商户名称：</label>
            <div class="controls">
                <input name="channelName" value="${clearingMerchantRecord.merchantName}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>

    </div>


    <div style="font-size: 2em;font-family: serif;font: bold;padding-bottom: 12px;padding-top: 20px;">交易信息:</div>

    <div class="control-group">
        <div class="control-left">
            <label class="control-label">币种：</label>
            <div class="controls">
                <input value="${clearingMerchantRecord.currency}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">业务类型：</label>
            <div class="controls">
                <input value="${clearingMerchantRecord.productCode}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>


    <div class="control-group">
        <div class="control-left">
            <label class="control-label">交易订单号：</label>
            <div class="controls">
                <input value="${clearingMerchantRecord.transNo}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">原交易订单号：</label>
            <div class="controls">
                <input value="${clearingMerchantRecord.transNoOld}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">交易金额：</label>
            <div class="controls">
                <input value="${clearingMerchantRecord.requestAmount}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">成功支付时间：</label>
            <div class="controls">
                <fmt:formatDate value="${clearingMerchantRecord.successTime}" type="both"
                                pattern="yyyy-MM-dd HH:mm:ss"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">交易类型：</label>
            <div class="controls">
                <input value="${clearingMerchantRecord.transType}"
                       style="border:0px;background-color:#fff;padding-top: 3px;" readonly="readonly"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">手续费：</label>
            <div class="controls">
                <input value="${clearingMerchantRecord.fee}" style="border:0px;background-color:#fff;padding-top: 3px;"
                       readonly="readonly"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">手续费扣除方式：</label>
            <div class="controls">
                <input value="${clearingMerchantRecord.feeWay}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
                <label class="control-label">用户订单号：</label>
                <div class="controls">
                    <input value="${clearingMerchantRecord.merchantOrderNo}"
                           style="border:0px;background-color:#fff;padding-top: 3px;" readonly="readonly"/>
                </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">产品编码名称：</label>
            <div class="controls">
                <input value="${clearingMerchantRecord.productName}"
                       style="border:0px;background-color:#fff;padding-top: 3px;" readonly="readonly"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">交易发起时间：</label>
            <div class="controls">
                <fmt:formatDate value="${clearingMerchantRecord.payTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">银行卡类型：</label>
            <div class="controls">
                <input value="${clearingMerchantRecord.bankcardType}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">支付类型：</label>
            <div class="controls">
                <input value="${clearingMerchantRecord.payType}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">订单创建时间：</label>
            <div class="controls">
                <fmt:formatDate value="${clearingMerchantRecord.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
            </div>
        </div>
    </div>


    <div style="font-size: 2em;font-family: serif;font: bold;padding-bottom: 15px;padding-top: 12px;">清结算信息:</div>

    <div class="control-group">
        <div class="control-left">
            <label class="control-label">清算流水号：</label>
            <div class="controls">
                <input value="${clearingMerchantRecord.settleNo}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">订单应结算金额：</label>
            <div class="controls">
                <fmt:formatNumber value="${clearingMerchantRecord.settleAmountPlan}" pattern="￥0.0000"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">对账批次号：</label>
            <div class="controls">
                <input value="${clearingMerchantRecord.checkBath}"
                       style="border:0px;background-color:#fff;padding-top: 3px;" readonly="readonly"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">订单应结算日期：</label>
            <div class="controls">
                <fmt:formatDate value="${clearingMerchantRecord.settleTimePlan}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
            </div>

        </div>
    </div>

    <div class="control-group">
        <div class="control-left">
            <label class="control-label">订单结算周期：</label>
            <div class="controls">
                <input value="${clearingMerchantRecord.settleCyc}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">结算批次号：</label>
            <div class="controls">
                <input value="${clearingMerchantRecord.settleBath}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>

    <div class="control-group">
        <div class="control-left">
            <label class="control-label">对账状态：</label>
            <div class="controls">
                <input value="${clearingMerchantRecord.checkStatus}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">对账标记：</label>
            <div class="controls">
                <input value="${clearingMerchantRecord.checkFlg}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">结算状态：</label>
            <div class="controls">
                <input value="${clearingMerchantRecord.settleStatus}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">处理结束时间：</label>
            <div class="controls">
                <fmt:formatDate value="${clearingMerchantRecord.finishTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
            </div>
        </div>
    </div>

    <div class="control-group">
        <div class="control-left">
            <label class="control-label">是否分润：</label>
            <div class="controls">
                <input value="${clearingMerchantRecord.isProfit}"
                       style="border:0px;background-color:#fff;padding-top: 3px;"
                       readonly="readonly"/>
            </div>

        </div>
        <div class="control-right">
            <label class="control-label">清算日期：</label>
            <div class="controls">
                <fmt:formatDate value="${clearingMerchantRecord.settleTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
            </div>
        </div>
    </div>
    <div class="control-group">

        <div class="control-left">
            <label class="control-label">备注：</label>
            <div class="controls">
                <input value="${clearingMerchantRecord.remark}"
                       style="border:0px;background-color:#fff;padding-top: 3px;"
                       readonly="readonly"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">手续费结算日期：</label>
            <div class="controls">
                <fmt:formatDate value="${clearingMerchantRecord.feeTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
            </div>
        </div>
    </div>


    <div class="control-group">
        <div class="control-right">
            <label class="control-label">手续费结算周期：</label>
            <div class="controls">
                <input value="${clearingMerchantRecord.feeSettleCyc}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>

    <div class="form-actions">
        <input id="btnCancel" class="btn" type="button" value="取 消" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>