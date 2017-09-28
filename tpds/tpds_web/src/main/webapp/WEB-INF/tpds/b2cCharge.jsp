<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <script type="text/javascript">
        function loadForm(){
            document.form.submit();
        }
    </script>
</head>
<body onload="loadForm();">
    <form name="form" action="<%=request.getAttribute("unionPayUrl")%>" method="post">
        <input type="hidden" name="reqHyTime" value="<%=request.getAttribute("reqHyTime")%>">
        <input type="hidden" name="callBackUrl" value="<%=request.getAttribute("callBackUrl")%>">
        <input type="hidden" name="bankCardType" value="<%=request.getAttribute("bankCardType")%>">
        <input type="hidden" name="description" value="<%=request.getAttribute("description")%>">
        <input type="hidden" name="bankName" value="<%=request.getAttribute("bankName")%>">
        <input type="hidden" name="merchantOrderNo" value="<%=request.getAttribute("merchantOrderNo")%>">
        <input type="hidden" name="version" value="<%=request.getAttribute("version")%>">
        <input type="hidden" name="requestTime" value="<%=request.getAttribute("requestTime")%>">
        <input type="hidden" name="bankId" value="<%=request.getAttribute("bankId")%>">
        <input type="hidden" name="productCode" value="<%=request.getAttribute("productCode")%>">
        <input type="hidden" name="payAmount" value="<%=request.getAttribute("payAmount")%>">
        <input type="hidden" name="onlineType" value="<%=request.getAttribute("onlineType")%>">
        <input type="hidden" name="merchantId" value="<%=request.getAttribute("merchantId")%>">
        <input type="hidden" name="clientIp" value="<%=request.getAttribute("clientIp")%>">
        <input type="hidden" name="notifyUrl" value="<%=request.getAttribute("notifyUrl")%>">
        <input type="hidden" name="merchantUserId" value="<%=request.getAttribute("merchantUserId")%>">
        <input type="hidden" name="signString" value="<%=request.getAttribute("signString")%>">
    </form>
</body>
</html>

