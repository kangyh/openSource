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
    <li><a href="#">清算异常列表详情页面</a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="clearingException" method="post" class="form-horizontal">

    <div style="font-size: 2em;font-family: serif;padding-bottom: 12px;padding-top: 12px;">通道信息:</div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">商户编码：</label>
            <div class="controls">
                <input name="channelName" value="${clearingException.merchantId}" readOnly="true"  style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
       <div class="control-right">
           <div class="control-left">
               <label class="control-label">商户名称：</label>
               <div class="controls">
                   <input value="${clearingException.merchantName}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
               </div>
           </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">通道编码：</label>
            <div class="controls">
                <input name="channelName" value="${clearingException.channelCode}" readOnly="true"  style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
       <div class="control-right">
           <div class="control-left">
               <label class="control-label">银行名称：</label>
               <div class="controls">
                   <input value="${clearingException.bankName}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
               </div>
           </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">银行流水：</label>
            <div class="controls">
                <input value="${clearingException.bankSeq}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">银行编码：</label>
            <div class="controls">
                <input value="${clearingException.bankCode}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">用户类型：</label>
            <div class="controls">
                <input value="${clearingException.merchantType}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">产品编码：</label>
            <div class="controls">
                <input value="${clearingException.productCode}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-right">
            <label class="control-label">商户手续费扣除方式：</label>
            <div class="controls">
                <input value="${clearingException.costWay}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>


    <div style="font-size: 2em;font-family: serif;padding-bottom: 12px;padding-top: 12px;">交易信息:</div>

    <div class="control-group">
        <div class="control-left">
            <label class="control-label">币种：</label>
            <div class="controls">
                <input value="${clearingException.currency}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">paymentID：</label>
            <div class="controls">
                <input value="${clearingException.paymentid}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>

    <div class="control-group">
        <div class="control-left">
            <label class="control-label">支付订单号：</label>
            <div class="controls">
                <input value="${clearingException.paymentId}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">原支付订单号：</label>
            <div class="controls">
                <input value="${clearingException.paymentIdOld}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>

    <div class="control-group">
        <div class="control-left">
            <label class="control-label">交易订单号：</label>
            <div class="controls">
                <input value="${clearingException.transNo}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">支付发起时间：</label>
            <div class="controls">
                <input value="${clearingException.payTime}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-group">
       <div class="control-left">
           <label class="control-label">原交易订单号：</label>
           <div class="controls">
               <input value="${clearingException.transNoOld}" readOnly="true"
                      style="border:0px;background-color:#fff;padding-top: 3px;"/>
           </div>
        </div>
        <div class="control-right">
            <label class="control-label">交易金额：</label>
            <div class="controls">
                <input value="${clearingException.requestAmount}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">交易类型：</label>
            <div class="controls">
                <input value="${clearingException.transType}" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="readonly"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">通道手续费扣除方式：</label>
            <div class="controls">
                <input value="${clearingException.feeWay}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">订单金额：</label>
            <div class="controls">
                <input value="${clearingException.payAmount}" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="readonly"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">订单结算周期：</label>
            <div class="controls">
                <input value="${clearingException.settleCyc}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">用户订单号：</label>
            <div class="controls">
                <input value="${clearingException.merchantOrderNo}" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="readonly"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">手续费：</label>
            <div class="controls">
                <input value="${clearingException.fee}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">支付类型：</label>
            <div class="controls">
                <input value="${clearingException.payType}" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="readonly"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">成功支付时间：</label>
            <div class="controls">
                <input value="${clearingException.successTime}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">银行卡类型：</label>
            <div class="controls">
                <input value="${clearingException.bankcardType}" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="readonly"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">订单创建时间：</label>
            <div class="controls">
                <input value="${clearingException.createTime}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>



    <div style="font-size: 2em;font-family: serif;padding-bottom: 12px;padding-top: 12px;">清结算信息:</div>

    <div class="control-group">
        <div class="control-left">
            <label class="control-label">差错状态：</label>
            <div class="controls">
                <input value="${clearingException.status}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">通道对账日期：</label>
            <div class="controls">
                <input value="${clearingException.field1}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>


    <div class="form-actions">
        <input id="btnCancel" class="btn" type="button" value="取 消" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>