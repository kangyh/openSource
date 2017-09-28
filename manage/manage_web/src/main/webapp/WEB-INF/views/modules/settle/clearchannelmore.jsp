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
    <li><a href="#">通道清算单据列表详情页面</a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="clearingChannelRecord" method="post" class="form-horizontal">

    <div style="font-size: 2em;font-family: serif;padding-bottom: 12px;padding-top: 12px;">通道信息:</div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">通道名称：</label>
            <div class="controls">
                <input name="channelName" value="${clearingChannelRecord.channelName}" readOnly="true"  style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
       <div class="control-right">
           <div class="control-left">
               <label class="control-label">银行名称：</label>
               <div class="controls">
                   <input value="${clearingChannelRecord.bankName}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
               </div>
           </div>
        </div>

    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">银行流水：</label>
            <div class="controls">
                <input value="${clearingChannelRecord.bankSeq}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">银行编码：</label>
            <div class="controls">
                <input value="${clearingChannelRecord.bankCode}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>


    <div style="font-size: 2em;font-family: serif;padding-bottom: 12px;padding-top: 12px;">交易信息:</div>

    <div class="control-group">
        <div class="control-left">
            <label class="control-label">币种：</label>
            <div class="controls">
                <input value="${clearingChannelRecord.currency}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">通道类型：</label>
            <div class="controls">
                <input value="${clearingChannelRecord.channelType}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>

    <div class="control-group">
        <div class="control-left">
            <label class="control-label">支付订单号：</label>
            <div class="controls">
                <input value="${clearingChannelRecord.paymentId}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">原支付订单号：</label>
            <div class="controls">
                <input value="${clearingChannelRecord.paymentIdOld}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>

    <div class="control-group">
        <div class="control-left">
            <label class="control-label">交易订单号：</label>
            <div class="controls">
                <input value="${clearingChannelRecord.transNo}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">交易日期：</label>
            <div class="controls">
                <fmt:formatDate value="${clearingChannelRecord.payTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
            </div>
        </div>
    </div>
    <div class="control-group">
       <div class="control-left">
           <label class="control-label">原交易订单号：</label>
           <div class="controls">
               <input value="${clearingChannelRecord.transNoOld}" readOnly="true"
                      style="border:0px;background-color:#fff;padding-top: 3px;"/>
           </div>
        </div>
        <div class="control-right">
            <label class="control-label">交易金额：</label>
            <div class="controls">
                <fmt:formatNumber value="${clearingChannelRecord.successAmount}" pattern="￥0.0000"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">交易类型：</label>
            <div class="controls">
                <input value="${clearingChannelRecord.transType}"
                       style="border:0px;background-color:#fff;padding-top: 3px;" readonly="readonly"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">手续费扣除方式：</label>
            <div class="controls">
                <input value="${clearingChannelRecord.costWay}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>


    <div style="font-size: 2em;font-family: serif;padding-bottom: 12px;padding-top: 12px;">清结算信息:</div>

    <div class="control-group">
        <div class="control-left">
            <label class="control-label">清算流水号：</label>
            <div class="controls">
                <input value="${clearingChannelRecord.settleNo}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">通道对账日期：</label>
            <div class="controls">
                <fmt:formatDate value="${clearingChannelRecord.channelTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">对账批次号：</label>
            <div class="controls">
                <input value="${clearingChannelRecord.checkBath}" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="readonly"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">会计时间：</label>
            <div class="controls">
                <fmt:formatDate value="${clearingChannelRecord.checkTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>

            </div>
        </div>
    </div>


  <div class="control-group">
        <div class="control-left">
            <label class="control-label">结算批次号：</label>
            <div class="controls">
                <input value="${clearingChannelRecord.settleBath}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">订单应结算日期：</label>
            <div class="controls">
                <fmt:formatDate value="${clearingChannelRecord.settleTimePlan}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
            </div>
        </div>
    </div>

    <div class="control-group">
        <div class="control-left">
            <label class="control-label">订单结算周期：</label>
            <div class="controls">
                <input value="${clearingChannelRecord.settleCyc}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">清算日期：</label>
            <div class="controls">
                <fmt:formatDate value="${clearingChannelRecord.settleTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
            </div>
        </div>
    </div>

    <div class="control-group">
        <div class="control-left">
            <label class="control-label">成本结算周期：</label>
            <div class="controls">
                <input value="${clearingChannelRecord.costSettleCyc}" style="border:0px;background-color:#fff;padding-top: 3px;"  readonly="readonly"/>
            </div>

        </div>
        <div class="control-left">
            <label class="control-label">成本结算日期：</label>
            <div class="controls">
                <fmt:formatDate value="${clearingChannelRecord.costTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
            </div>
        </div>
    </div>

    <div class="control-group">
        <div class="control-left">
            <label class="control-label">备注：</label>
            <div class="controls">
                <input value="${clearingChannelRecord.remark}" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="readonly"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">成本：</label>
            <div class="controls">
                <fmt:formatNumber value="${clearingChannelRecord.costAmount}" pattern="￥0.0000"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">对账状态：</label>
            <div class="controls">
                <input value="${clearingChannelRecord.checkStatus}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">对账标记：</label>
            <div class="controls">
                <input value="${clearingChannelRecord.checkFlg}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
    </div>
    <div class="control-group">
        <div class="control-left">
            <label class="control-label">结算状态：</label>
            <div class="controls">
                <input value="${clearingChannelRecord.settleStatus}" readOnly="true"
                       style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>
        <div class="control-right">
            <label class="control-label">处理结束时间：</label>
            <div class="controls">
                <fmt:formatDate value="${clearingChannelRecord.finishTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
            </div>
        </div>
    </div>
    <div class="form-actions">
        <input id="btnCancel" class="btn" type="button" value="取 消" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>