<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>通道BankId关联</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
            $(".checkPass").on("click",function(){
                var url = $(this).attr("value-url");
                parent.showDynamicPa();
                parent.enterDynamicPa(url);
            });

            /*增加产品的支付通道*/
            $(".add_list").on("click",function(){
                var _this = $(this);
                var id = $(this).attr("data-id");
                var bankId = $("#BankIdByTypeIn").val();
                var subMerchantNo = $("#subMerchantNo").val();
                var channelCode = $(this).attr("data-channelCode");
                var channelName = $(this).attr("data-channelName");
                var pageNo = $(this).attr("data-pageNo");
                //避免重复提交
                if(_this.hasClass('submintIn')){ return false; }
                _this.text("增加中...").addClass('submintIn');
                $.ajax({
                    type: "GET",
                    url: "${ctx}/route/bankIdMapping/addBankIdByHands",
                    data: {'id':id,'bankId':bankId,'subMerchantNo':subMerchantNo,'channelCode':channelCode,'channelName':channelName,'pageNo':pageNo},
                    success: function(){
                        window.location.reload();
                    },
                    error: function(){
                        console.log("请求失败!");
                    }
                });

            });
        });

        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        };
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/route/bankIdMapping/">通道列表</a></li>
    <li class="active"><a href="${ctx}/route/bankIdMapping/details?id=${payChannel.id}">关联信息</a></li>
</ul>
<form:form id="searchForm" modelAttribute="payChannel" method="post" class="form-horizontal">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <div class="control-group">
        <label class="control-label">通道名称：</label>
        <div class="controls">
                ${payChannel.channelName}
        </div>
    </div>
    <div class="control-group">
        <label class="control-label"><b>BankId列表</b></label>
    </div>
    BankId: <input id="BankIdByTypeIn" type="text" class="input-xlarge required">
    子商户号：<input id="subMerchantNo" type="text" class="input-xlarge required">
    <a style ="cursor: pointer" class="add_list" data-id="${payChannel.id}" data-channelCode="${payChannel.channelCode}" data-channelName="${payChannel.channelName}" data-pageNo="${page.pageNo}">添加</a>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>通道代码</th>
        <th>通道名称</th>
        <%--<th>通道key</th>
        <th>通道商编码</th>
        <th>支付类型</th>
        <th>场景key</th>--%>
        <th>银行ID</th>
        <th>子商户号</th>
      <%--  <th>银行支付提供者</th>--%>
        <shiro:hasPermission name="route:bankIdMapping:edit"><th>操作</th></shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="channelBankid" >
        <tr>
            <td>${channelBankid.channelCode}</td>
            <td>${channelBankid.channelName}</td>
           <%-- <td>${channelBankid.channelKey}</td>
            <td>${channelBankid.providerCode}</td>
            <td>${channelBankid.payType}</td>
            <td>${channelBankid.sceneKey}</td>--%>
            <td>${channelBankid.bankId}</td>
            <td>${channelBankid.subMerchantNo}</td>
            <%--<td>${channelBankid.bankProvider}</td>--%>
            <shiro:hasPermission name="route:bankIdMapping:edit"><td>
                <a id="a" href="${ctx}/route/bankIdMapping/delete?id=${channelBankid.id}&channelId=${payChannel.id}" onclick="return confirmx('确认要删除该BankId吗？', this.href)">删除</a>
            </td></shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
<div class="form-actions">
    <%--<input type="button" value="添加BankId" class="checkPass btn btn-primary" value-url="${ctx}/route/bankIdMapping/add?channelCode=${payChannel.channelCode}&channelId=${payChannel.id}&channelName=${payChannel.channelName}"/>--%>
    <input type="button" class="btn" value="返回" onclick="javascript:window.location='${ctx}/route/bankIdMapping/list';"/>
</div>
</body>
</html>