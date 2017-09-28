<%--suppress ALL --%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>单表管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#btnClear").on("click",function(){
                $("#bankNo").val("");
                $("#channelPartnerCode").val("");
                $("#channelTypeCode").val("");
                $("#cardTypeCode").val("");
                $("#channelCode").val("");
                $("#paymentId").val("");
                $("#result").val("");
                $("#reqTime").val("");
                $("#respTime").val("");
                $("#searchForm").submit();
            });
            $("#btnSubmit").on("click",function(){
                $("#pageNo").val("1");
            });
        });
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/riskChannel/channelLog/">支付通道列表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="riskChannelLogVo" action="${ctx}/riskChannel/channelLog/list" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>银行名称：</label>
            <form:select id="bankNo" path="bankNo" name="bankNo" class="input-medium" style="width:150px">
                <option selected value="">全部</option>
                <form:options items="${fns:getBankInfoList()}" itemLabel="bankName" itemValue="bankNo" htmlEscape="false" />
            </form:select>
            <input type="hidden" name="bankName" id="bankName" class="input-xlarge required">
        </li>
        <li><label>通道合作方：</label>
            <form:select  path="channelPartnerCode" name="channelPartnerCode" style="width:150px" >
                <option selected value="">全部</option>
                <form:options items="${fns:getEnumList('channelPartner')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
            </form:select>
        </li>
        <li><label style="width:100px" >支付通道类型：</label>
            <form:select  path="channelTypeCode" name="channelTypeCode"  style="width:150px">
                <option selected value="">全部</option>
                <form:options items="${fns:getEnumList('channelType')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
            </form:select>
        </li>
        <li><label>银行卡类型：</label>
            <form:select  path="cardTypeCode" name="cardTypeCode"  style="width:150px">
                <option selected value="">全部</option>
                <form:options items="${fns:getEnumList('rateBankcardType')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
            </form:select>
        </li>
        <li><label>请求结果：</label>
            <form:select  path="result" name="result"  style="width:150px">
                <option selected value="">全部</option>
                <form:options items="${fns:getEnumList('channelReqResult')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
            </form:select>
        </li>
        <li><label>通道代码：</label>
            <form:input path="channelCode" name="channelCode" onpaste="return true" htmlEscape="false" maxlength="30"  style="width:150px" class="input-xlarge required"/>
        </li>
        <li><label>交易号：</label>
            <form:input path="paymentId" name="paymentId" onpaste="return true" htmlEscape="false" maxlength="30"  style="width:150px" class="input-xlarge required"/>
        </li>
        <li><label>查询时间：</label>
            <input id="reqTime" name="reqTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="${riskChannelLogVo.reqTime}"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,maxDate:'#F{$dp.$D(\'respTime\')}'});"/> -
            <input id="respTime" name="respTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="${riskChannelLogVo.respTime}"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,minDate:'#F{$dp.$D(\'reqTime\')}'});"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="btns"><input id="btnClear" class="btn btn-primary" type="submit" value="清空" /></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>序号</th>
        <th>支付通道代码</th>
        <th>通道合作方</th>
        <th>银行名称</th>
        <th>银行卡类型</th>
        <th>支付通道类型</th>
        <th>对公对私标识</th>
        <th>付款类型</th>
        <th>交易号</th>
        <th>订单金额</th>
        <th>单日限额</th>
        <th>单月限额</th>
        <th>请求结果</th>
        <th>请求时间</th>
        <th>返回时间</th>
    </tr>
    </thead>
    <tbody>
    <jsp:useBean id="myDate" class="java.util.Date"/>
    <c:forEach items="${page.list}" var="channel" varStatus="i" >
        <tr>
            <td>${i.count}</td>
            <td>${channel.channelCode}</td>
            <td>${channel.channelPartnerName}</td>
            <td>${channel.bankName}</td>
            <td>${channel.cardTypeName}</td>
            <td>${channel.channelTypeName}</td>
            <td>
                <c:choose>
                    <c:when test="${channel.accountType eq 'PUBLIC'}">
                        对公
                    </c:when>
                    <c:when test="${channel.accountType eq 'PRIVAT'}">
                        对私
                    </c:when>
                    <c:when test="${channel.accountType eq 'COMMON'}">
                        通用
                    </c:when>
                    <c:otherwise>
                        ${channel.accountType}
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <c:choose>
                    <c:when test="${channel.businessType eq 'OWNBAK'}">
                        本行
                    </c:when>
                    <c:when test="${channel.businessType eq 'SPNBAK'}">
                        跨行
                    </c:when>
                    <c:otherwise>
                        ${channel.businessType}
                    </c:otherwise>
                </c:choose>
            </td>
            <td>${channel.paymentId}</td>
            <td><fmt:formatNumber value="${channel.amount}" pattern="￥#,##0.00" /></td>
            <td><fmt:formatNumber value="${channel.daylimitAmount}" pattern="￥#,##0.00" /></td>
            <td><fmt:formatNumber value="${channel.monlimitAmount}" pattern="￥#,##0.00" /></td>
            <td>
                <c:choose>
                    <c:when test="${channel.result eq 'SUCCESS'}">
                        成功
                    </c:when>
                    <c:when test="${channel.result eq 'FAILURE'}">
                        失败
                    </c:when>
                    <c:otherwise>
                        ${channel.result}
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <c:set target="${myDate}" property="time" value="${channel.reqTime}"/>
                <fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss.SSS" value="${myDate}" type="both"/>
            </td>
            <td>
                <c:set target="${myDate}" property="time" value="${channel.respTime}"/>
                <fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss.SSS" value="${myDate}" type="both"/>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
<div class="form-actions">
    <%--<input type="button" value="修改通道合作方" class="checkPass btn btn-primary" value-url="${ctx}/riskChannel/channelLog/batchList"/>--%>
</div>
</body>
</html>