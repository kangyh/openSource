<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>对账管理</title>
    <meta name="decorator" content="default"/>
    <script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="#">对账差错管理列表详情页面</a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="settleDifferRecord" method="post" class="form-horizontal">

    <div style="font-size: 2em;font-family: serif;font: bold;padding-bottom: 12px;padding-top: 12px;">商户信息:</div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">商户编码：</label>
            <div class="controls">
                <input name="channelName" value="${settleDifferRecord.merchantId}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">商户名称：</label>
            <div class="controls">
                <input name="channelName" value="${settleDifferRecord.merchantName}" readOnly="true"  style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">账户编码：</label>
            <div class="controls">
                <input name="channelName" value="${settleDifferRecord.accountNo}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-left">
            <label class="control-label">商户交易订单号：</label>
            <div class="controls">
                <input name="channelName" value="${settleDifferRecord.merchantOrderNo}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>

    <div style="font-size: 2em;font-family: serif;font: bold;padding-bottom: 12px;padding-top: 12px;">通道信息:</div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">通道编码：</label>
            <div class="controls">
                <input name="channelName" value="${settleDifferRecord.channelCode}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
       <div class="control-right">
            <label class="control-label">通道名称：</label>
            <div class="controls">
                <input name="channelName" value="${settleDifferRecord.channelName}" readOnly="true"  style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">银行编码：</label>
            <div class="controls">
                <input value="${settleDifferRecord.bankCode}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">通道提供者：</label>
            <div class="controls">
                <input value="${settleDifferRecord.channelProvider}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">银行流水：</label>
            <div class="controls">
                <input value="${settleDifferRecord.bankSeq}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">银行名称：</label>
            <div class="controls">
                <input value="${settleDifferRecord.bankName}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>

    <div class="control-group">
        <div class="control-left">
            <label class="control-label">通道类型：</label>
            <div class="controls">
                <input value="${settleDifferRecord.channelType}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">通道流水：</label>
            <div class="controls">
                <input value="${settleDifferRecord.channleNo}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>


    <div style="font-size: 2em;font-family: serif;font: bold;padding-bottom: 12px;padding-top: 12px;">交易信息:</div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">产品编码：</label>
            <div class="controls">
                <input value="${settleDifferRecord.productCode}"  style="border:0px;background-color:#fff;padding-top: 3px;" readonly="readonly"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">产品名称：</label>
            <div class="controls">
                <input value="${settleDifferRecord.productName}"  style="border:0px;background-color:#fff;padding-top: 3px;" readonly="readonly"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">支付单号：</label>
            <div class="controls">
                <input value="${settleDifferRecord.paymentId}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">交易单号：</label>
            <div class="controls">
                <input value="${settleDifferRecord.transNo}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>

    <div class="control-group">
        <div class="control-left">
            <label class="control-label">支付单金额：</label>
            <div class="controls">
                <input value="${settleDifferRecord.successAmount}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">成功金额：</label>
            <div class="controls">
                <input value="${settleDifferRecord.requestAmount}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>

    <div class="control-group">
        <div class="control-left">
            <label class="control-label">交易订单号：</label>
            <div class="controls">
                <input value="${settleDifferRecord.transNo}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">交易日期：</label>
            <div class="controls">
                <fmt:formatDate value="${settleDifferRecord.payTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-right">
            <label class="control-label">交易金额：</label>
            <div class="controls">
                <fmt:formatNumber value="${settleDifferRecord.successAmount}" pattern="￥0.0000"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">交易类型：</label>
            <div class="controls">
                <input value="${settleDifferRecord.transType}" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="readonly"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">成功支付时间：</label>
            <div class="controls">
                <fmt:formatDate value="${settleDifferRecord.successTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">支付状态：</label>
            <div class="controls">
                <input value="${settleDifferRecord.transStatus}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
       <div class="control-right">
           <label class="control-label">手续费：</label>
           <div class="controls">
               <fmt:formatNumber value="${settleDifferRecord.feeAmount}" pattern="￥0.0000"/>
           </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">手续费扣除方式：</label>
            <div class="controls">
                <input value="${settleDifferRecord.feeWay}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">支付发起时间：</label>
            <div class="controls">
                <fmt:formatDate value="${settleDifferRecord.payTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">银行卡类型：</label>
            <div class="controls">
                <input value="${settleDifferRecord.bankcardType}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">交易发起时间：</label>
            <div class="controls">
                <fmt:formatDate value="${settleDifferRecord.busiTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">支付类型：</label>
            <div class="controls">
                <input value="${settleDifferRecord.payType}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>




    <div style="font-size: 2em;font-family: serif;font: bold;padding-bottom: 12px;padding-top: 12px;">清结算信息:</div>

    <div class="control-group">
        <div class="control-left">
            <label class="control-label">对账批次：</label>
            <div class="controls">
                <input value="${settleDifferRecord.checkBathno}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">结算批次：</label>
            <div class="controls">
                <input value="${settleDifferRecord.settleBath}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>

  <div class="control-group">
        <div class="control-left">
            <label class="control-label">差异类型：</label>
            <div class="controls">
                <input value="${settleDifferRecord.differType}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">处理结果：</label>
            <div class="controls">
                <input value="${settleDifferRecord.handleResult}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>

    <div class="control-group">
        <div class="control-left">
            <label class="control-label">处理意见：</label>
            <div class="controls">
                <input value="${settleDifferRecord.handleMessage}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">差错日期：</label>
            <div class="controls">
                <fmt:formatDate value="${settleDifferRecord.errorDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
            </div>
        </div>
    </div>

    <div class="control-group">
        <div class="control-left">
            <label class="control-label">操作人：</label>
            <div class="controls">
                <input value="${settleDifferRecord.updateAuthor}" style="border:0px;background-color:#fff;padding-top: 3px;"  readonly="readonly"/>
            </div>

        </div>
        <div class="control-left">
            <label class="control-label">操作日期：</label>
            <div class="controls">
                <fmt:formatDate value="${settleDifferRecord.operationDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
            </div>
        </div>
    </div>

    <div class="control-group">
        <div class="control-left">
            <label class="control-label">成本：</label>
            <div class="controls">
                <input value="${settleDifferRecord.costAmount}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">订单应结算金额：</label>
            <div class="controls">
                <fmt:formatNumber value="${settleDifferRecord.settleAmountPlan}" pattern="￥0.0000"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">记账标记：</label>
            <div class="controls">
                <input value="${settleDifferRecord.isBill}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">是否分润：</label>
            <div class="controls">
                <input value="${settleDifferRecord.isProfit}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">备注：</label>
            <div class="controls">
                <input value="${settleDifferRecord.remark}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>

    <div class="form-actions">
        <input id="btnCancel" class="btn" type="button" value="取 消" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>