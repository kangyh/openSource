<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>单表管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#btnClear").on("click",function(){
                //console.log("11");
                $("#channelKey").val("");
                $("#providerCode").val("");
                $("#payType").val("");
                $("#sceneKey").val("");
                $("#searchForm").submit();
            });
            /*增加产品的支付通道*/
            $(".add_list").on("click",function(){
                var _this = $(this);
                var channelId = $(this).attr("data-channelId");
                var integraChannelId = $(this).attr("data-integraChannelId");
                var channelCode = $(this).attr("data-channelCode");
                var channelName = $(this).attr("data-channelName");
                var channelKey = $(this).attr("data-channelKey");
                var providerCode = $(this).attr("data-providerCode");
                var sceneKey = $(this).attr("data-sceneKey");
                var payType = $(this).attr("data-payType");
                var pageNo = $(this).attr("data-pageNo");
                //避免重复提交
                if(_this.hasClass('submintIn')){ return false; }
                _this.text("增加中...").addClass('submintIn');
                $.ajax({
                    type: "GET",
                    url: "${ctx}/route/bankIdMapping/addBankId",
                    data: {'channelId':channelId,'id':integraChannelId,'channelCode':channelCode,'channelName':channelName,'channelKey':channelKey,'providerCode':providerCode,'sceneKey':sceneKey,'payType':payType,'pageNo':pageNo},
                    success: function(){
                        window.location.reload();
                    },
                    error: function(){
                        console.log("请求失败!");
                    }
                });

            });

            //全选，反选
            $(".comm_all").off().on("click",function(){
                var comm_all = $(".comm_all");
                var goods_delete = document.getElementsByName("app_box");
                for(var i=0;i<goods_delete.length;i++){
                    if(comm_all.is(':checked')){
                        goods_delete[i].checked = true;
                    }else{
                        goods_delete[i].checked = false;
                    }
                }
            });
            //单选取消全选
            $(".record_list").delegate("input[name=app_box]","click",function(){
                var comm_all = $(".comm_all");
                var goods_delete = document.getElementsByName("app_box");
                for(var i=0,n=0;i<goods_delete.length;i++){
                    if(goods_delete[i].checked){
                        n++;
                    }
                    if(n==goods_delete.length){
                        $(".comm_all").attr("checked",true);
                    }else{
                        $(".comm_all").attr("checked",false);
                    }
                }
            });
            //点击增加
            $("#add_channel").on("click",function(){
                //获取选中的通道id数组
                var checked = [];
                $('.record_list input:checkbox[name="app_box"]:checked').each(function() {
                    checked.push($(this).val());
                });
                var checkedstr = checked.join(",");
                $("#msg").val(checkedstr);
                if($("#msg").val() == ""){
                    $("#msg").val("未选中通道，请检查！");
                    parent.showThree();
                    parent.changeThreeName($("#msg").val());
                    return;
                }
                var channelCode = $("#channelCode").val();
                var channelId = $("#channelId").val();
                var channelName = $("#channelName").val();
                var channelKey = $("#channelKey").val();
                var providerCode = $("#providerCode").val();
                var sceneKey = $("#sceneKey").val();
                var pageNo = $("#pageNo").val();
                var payType = $("#payType").val();
                $.ajax({
                    type: "GET",
                    url: "${ctx}/route/bankIdMapping/addBatchBankId",
                    data: {'channelId':channelId,'checkedstr':checkedstr,'channelCode':channelCode,'channelName':channelName,'channelKey':channelKey,'providerCode':providerCode,'sceneKey':sceneKey,'payType':payType,'pageNo':pageNo},
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
        }
    </script>
</head>
<body>
<form:form id="searchForm" modelAttribute="integrationChannel" action="${ctx}/route/bankIdMapping/add" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <input type="hidden" id="msg" name="msg" value ="${msg}"/>
        <div class="control-group">
            <label class="control-label">通道名称：${channelName}</label>
        </div>
        <li><label>通道key：</label>
            <%--<form:select id="channelKey" path="channelKey" name="channelKey" class="input-medium" onchange="Sel2change(this.options[this.options.selectedIndex].text);">
                <option selected value="">全部</option>
                <form:options items="${fns:getBankInfoList()}" itemLabel="bankName" itemValue="bankNo" htmlEscape="false" />
            </form:select>--%>
            <form:input path="channelKey" htmlEscape="false" maxlength="100" class="input-medium" />
        </li>
        <li><label>通道商编码：</label>
          <%--  <form:select  path="channelPartnerCode" name="channelPartnerCode" style="width:100px" >
                <option selected value="">全部</option>
                <form:options items="${fns:getEnumList('channelPartner')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
            </form:select>--%>
            <form:input path="providerCode" htmlEscape="false" maxlength="100" class="input-medium" />
        </li>
        <li><label >支付类型：</label>
            <form:select  path="payType" name="payType"  style="width:200px">
                <option selected value="">全部</option>
                <form:options items="${fns:getEnumList('integraChannelPayType')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
            </form:select>
        </li>
        <li><label>使用场景：</label>
            <%--<form:select  path="cardTypeCode" name="cardTypeCode"  style="width:100px">
                <option selected value="">全部</option>
                <form:options items="${fns:getEnumList('rateBankcardType')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
            </form:select>--%>
            <form:input path="sceneKey" htmlEscape="false" maxlength="100" class="input-medium" />
        </li>
            <%--查询功能，为add方法传参数--%>
        <input type="hidden" name="channelCode" id="channelCode" class="input-xlarge required" value="${channelCode}">
        <input type="hidden" name="channelId" id="channelId" class="input-xlarge required" value="${channelId}">
        <input type="hidden" name="channelName" id="channelName" class="input-xlarge required" value="${channelName}">
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="btns"><input id="btnClear" class="btn btn-primary" type="submit" value="清空" /></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed" style="width:1000px">
    <thead>
    <tr>
        <th><input type="checkbox" class="checkbox comm_all" value="">全选</th>
        <th>通道key</th>
        <th>通道名称</th>
        <th>通道商编码</th>
        <th>支付类型</th>
        <th>场景key</th>
        <th>银行ID</th>
        <th>银行支付提供者</th>
        <th>银行卡类型</th>
        <th>是否按比成本</th>
        <th>成本费率</th>
        <th>最小成本金额</th>
        <th>最大成本金额</th>
        <th>是否按笔收费</th>
        <th>按笔费率金额</th>
        <th>按笔最小消费率金额</th>
        <th>按笔最大消费率金额</th>
        <th>按金额结算费率</th>
        <th>按金额最小结算费率</th>
        <th>按金额最大结算费率</th>
        <th>备注</th>
        <th>状态</th>
        <shiro:hasPermission name="route:bankIdMapping:edit"><th style="width:150px">操作</th></shiro:hasPermission>
    </tr>
    </thead>
    <tbody class="record_list">
    <c:forEach items="${page.list}" var="integrationChannel">
        <tr>
            <c:if test="${integrationChannel.mappingId!=null}"><td><input type="checkbox" checked="checked" disabled="disabled"></td></c:if>
            <c:if test="${empty integrationChannel.mappingId}"><td><input type="checkbox" class="checkbox" value="${integrationChannel.id}" name="app_box"></td></c:if>
            <td>${integrationChannel.channelKey}</td>
            <td>${integrationChannel.channelName}</td>
            <td>${integrationChannel.providerCode}</td>
            <td>${integrationChannel.payType}</td>
            <td>${integrationChannel.sceneKey}</td>
            <td>${integrationChannel.bankId}</td>
            <td>${integrationChannel.bankProvider}</td>
            <td>${integrationChannel.bankCardType}</td>
            <td>${integrationChannel.costIsByBill}</td>
            <td>${integrationChannel.costRate}</td>
            <td>${integrationChannel.minCostFee}</td>
            <td>${integrationChannel.maxCostFee}</td>
            <td>${integrationChannel.isByBill}</td>
            <td>${integrationChannel.oneBillFee}</td>
            <td>${integrationChannel.minBillFee}</td>
            <td>${integrationChannel.maxBillFee}</td>
            <td>${integrationChannel.feeRate}</td>
            <td>${integrationChannel.minRate}</td>
            <td>${integrationChannel.maxRate}</td>
            <td>${integrationChannel.note}</td>
            <td>${integrationChannel.status}</td>
            <shiro:hasPermission name="route:bankIdMapping:edit"><td>
                    <%--<c:if test="${payChannel.productDetailId!=null}"><span>已增加</span></c:if><c:if test="${empty payChannel.productDetailId}"><a class="add_list" href="${ctx}/route/productChannel/addChannel?productId=${productId}&id=${payChannel.id}&productCode=${productCode}&productName=${productName}&bankNo=${bankNo}&channelPartnerCode=${channelPartnerCode}&channelTypeCode=${channelTypeCode}&cardTypeCode=${cardTypeCode}&pageNo=${page.pageNo}">增加</a></c:if>--%>
                <c:if test="${integrationChannel.mappingId!=null}"><span>已增加</span></c:if>
                <c:if test="${empty integrationChannel.mappingId}">
                    <%--<span style ="color: #38B0DE;cursor: pointer"  class="add_list" data-productId="${productId}" data-payChannelId="${payChannel.id}" data-productCode="${productCode}" data-productName="${productName}" data-bankNo="${bankNo}" data-channelPartnerCode="${channelPartnerCode}" data-channelTypeCode="${channelTypeCode}" data-cardTypeCode="${cardTypeCode}" data-pageNo="${page.pageNo}">增加</span>--%>
                    <a style ="cursor: pointer"  class="add_list" data-channelId="${channelId}" data-integraChannelId="${integrationChannel.id}" data-channelCode="${channelCode}" data-channelName="${channelName}" data-channelKey="${channelKey}" data-providerCode="${providerCode}" data-sceneKey="${sceneKey}" data-payType="${payType}" data-pageNo="${page.pageNo}">增加</a>
                </c:if>
            </td></shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
<div class="form-actions">
    <input type="button" value="提交已选通道" id="add_channel" class="checkPass btn btn-primary"/>
    <input type="button" class="btn" value="返回" onclick="javascript:window.location='${ctx}/route/bankIdMapping/details?id=${channelId}';"/>
</div>
<input type="hidden" id="channelCode" value="${payChannel.channelCode}">
<input type="hidden" id="channelId" value="${payChannel.id}">
<input type="hidden" id="channelName" value="${payChannel.channelName}">
<input type="hidden" id="channelKey" value="${channelKey}">
<input type="hidden" id="providerCode" value="${providerCode}">
<input type="hidden" id="sceneKey" value="${sceneKey}">
<input type="hidden" id="pageNo" value="${page.pageNo}">
<input type="hidden" id="payType" value="${payType}">
</body>
</html>