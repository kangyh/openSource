<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>路由通道缓存管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#updateMerchantChannel").attr("disabled",true);
            $("#updateBestChannel").attr("disabled",true);
            $("#updatePayChannel").attr("disabled",true);
            $("#updateChannelType").attr("disabled",true);
            $("#deleteMerchantChannel").attr("disabled",true);
            $("#deleteBestChannel").attr("disabled",true);
            $("#deletePayChannel").attr("disabled",true);
            $("#deleteChannelType").attr("disabled",true);
            $("#delBankInfoOfProduct").attr("disabled",true);
            $("#syncDataLineBankNumber").attr("disabled",true);
            $("#delRouteMap").attr("disabled",true);
            setInterval(setButtonGray,10000);
        });
        function setButtonGray(){
            $("#updateMerchantChannel").attr("disabled",false);
            $("#updateBestChannel").attr("disabled",false);
            $("#updatePayChannel").attr("disabled",false);
            $("#updateChannelType").attr("disabled",false);
            $("#deleteMerchantChannel").attr("disabled",false);
            $("#deleteBestChannel").attr("disabled",false);
            $("#deletePayChannel").attr("disabled",false);
            $("#deleteChannelType").attr("disabled",false);
            $("#delBankInfoOfProduct").attr("disabled",false);
            $("#syncDataLineBankNumber").attr("disabled",false);
            $("#delRouteMap").attr("disabled",false);
        }
        function updateMerchantChannel(){
            $("#updateMerchantChannel").attr("disabled",true);
            window.location.href="${ctx}/route/PayChannelRedis/merchantChannel"
        }
        function updateBestChannel(){
            $("#updateBestChannel").attr("disabled",true);
            window.location.href="${ctx}/route/PayChannelRedis/payChannel"
        }
        function updatePayChannel(){
            $("#updatePayChannel").attr("disabled",true);
            window.location.href="${ctx}/route/PayChannelRedis/payChannelAll"
        }
        function updateChannelType(){
            $("#updateChannelType").attr("disabled",true);
            window.location.href="${ctx}/route/PayChannelRedis/channelType"
        }
        function deleteMerchantChannel(){
            $("#deleteMerchantChannel").attr("disabled",true);
            window.location.href="${ctx}/route/PayChannelRedis/delMerchantChannel"
        }
        function deleteBestChannel(){
            $("#deleteBestChannel").attr("disabled",true);
            window.location.href="${ctx}/route/PayChannelRedis/delBestChannel"
        }
        function deletePayChannel(){
            $("#deletePayChannel").attr("disabled",true);
            window.location.href="${ctx}/route/PayChannelRedis/delAllPayChannel"
        }
        function deleteChannelType(){
            $("#deleteChannelType").attr("disabled",true);
            window.location.href="${ctx}/route/PayChannelRedis/delPayChannelType"
        }
        function delBankInfoOfProduct(){
            $("#delBankInfoOfProduct").attr("disabled",true);
            window.location.href="${ctx}/route/PayChannelRedis/delBankInfoOfProduct"
        }
        function syncDataLineBankNumber(){
        	$("#syncDataLineBankNumber").attr("disabled",true);
        	window.location.href="${ctx}/route/PayChannelRedis/syncDataLineBankNumber";
        }

        function delRouteMap(){
            $("#delRouteMap").attr("disabled",true);
            window.location.href="${ctx}/route/PayChannelRedis/delRouteMap"
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active">路由通道缓存管理</li>
</ul>
     <br><li class="btns"><input id="updateMerchantChannel" type="button" class="btn btn-primary"  value="更新商户通道缓存" onclick="updateMerchantChannel();"/></li><br>
     <br><li class="btns"><input id="updateBestChannel" type="button" class="btn btn-primary"  value="更新最优通道缓存" onclick="updateBestChannel();"/></li><br>
     <br><li class="btns"><input id="updatePayChannel" type="button" class="btn btn-primary"  value="更新支付通道所有信息缓存" onclick="updatePayChannel();"/></li><br>
     <br><li class="btns"><input id="updateChannelType" type="button" class="btn btn-primary"  value="更新通道类型对应的银行及卡类型" onclick="updateChannelType();"/></li><br>
     <br><li class="btns"><input id="deleteMerchantChannel" type="button" class="btn btn-primary"  value="删除商户通道缓存" onclick="deleteMerchantChannel();"/></li><br>
     <br><li class="btns"><input id="deleteBestChannel" type="button" class="btn btn-primary"  value="删除最优通道缓存" onclick="deleteBestChannel();"/></li><br>
     <br><li class="btns"><input id="deletePayChannel" type="button" class="btn btn-primary"  value="删除支付通道所有信息缓存" onclick="deletePayChannel();"/></li><br>
     <br><li class="btns"><input id="deleteChannelType" type="button" class="btn btn-primary"  value="删除通道类型对应的银行及卡类型" onclick="deleteChannelType();"/></li><br>
     <br><li class="btns"><input id="delBankInfoOfProduct" type="button" class="btn btn-primary"  value="删除产品对应的银行及卡类型" onclick="delBankInfoOfProduct();"/></li><br>
     <br><li class="btns"><input id="syncDataLineBankNumber" type="button" class="btn btn-primary"  value="同步solr数据(开户支行信息)" onclick="syncDataLineBankNumber();"/></li><br>
     <br><li class="btns"><input id="delRouteMap" type="button" class="btn btn-primary"  value="删除路由通道缓存" onclick="delRouteMap();"/><br>
     <br><li class="clearfix"></li>
</body>
</html>