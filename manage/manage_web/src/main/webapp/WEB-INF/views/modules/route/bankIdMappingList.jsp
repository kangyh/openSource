<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>通道BankId关联</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#btnClear").on("click",function(){
                //console.log("11");
                $("#bankNo").val("");
                $("#channelPartnerCode").val("");
                $("#channelTypeCode").val("");
                $("#searchForm").submit();
            });
            //$("#contentTable tr:eq(15)").hide();
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
    <li class="active"><a href="${ctx}/route/bankIdMapping/">通道列表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="payChannel" action="${ctx}/route/bankIdMapping/" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>银行名称：</label>
            <form:select id="bankNo" path="bankNo" name="bankNo" class="input-medium" onchange="Sel2change(this.options[this.options.selectedIndex].text);">
                <option selected value="">全部</option>
                <form:options items="${fns:getBankInfoList()}" itemLabel="bankName" itemValue="bankNo" htmlEscape="false" />
            </form:select>
            <input type="hidden" name="bankName" id="bankName" class="input-xlarge required">
        </li>
        <li><label>通道合作方：</label>
            <form:select  path="channelPartnerCode" name="channelPartnerCode" style="width:100px" >
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
            <form:select  path="cardTypeCode" name="cardTypeCode"  style="width:100px">
                <option selected value="">全部</option>
                <form:options items="${fns:getEnumList('rateBankcardType')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
            </form:select>
        </li>
        <li><label>状态：</label>
            <form:select id="status" path="status" class="input-medium">
                <form:option value="" label="全部"/>
                <form:options items="${fns:getEnumList('commonStatus')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
            </form:select>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="btns"><input id="btnClear" class="btn btn-primary" type="submit" value="清空" /></li>
     <%--   <input type="button" class="btn"  class="btn btn-primary" value="bankId同步" onclick="javascript:window.location='${ctx}/route/bankIdMapping/bankIdSync';"/>--%>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>序号</th>
        <th>通道名称</th>
        <th>通道代码</th>
        <th>有效开始时间</th>
        <th>有效结束时间</th>
        <th>状态</th>
        <shiro:hasPermission name="route:bankIdMapping:edit"><th>操作</th></shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="payChannel" varStatus="i">
        <tr>
            <td>${i.count}</td>
            <td><a href="${ctx}/route/bankIdMapping/details?id=${payChannel.id}">${payChannel.channelName}</a></td>
            <td>${payChannel.channelCode}</td>
            <td><fmt:formatDate value="${payChannel.effectStartDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td><fmt:formatDate value="${payChannel.effectEndDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td>${payChannel.status}</td>
            <shiro:hasPermission name="route:bankIdMapping:edit"><td>
                <a href="${ctx}/route/bankIdMapping/details?id=${payChannel.id}">关联bankId</a>
            </td></shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>