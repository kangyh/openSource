<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>异步通知管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		
				var valueUrl =  "a/payment/notifyRecord/form";
				$.ajax({
					type : "POST",
					url : "${ctx}/adjust/adjustAccount/isAdminUser",
					dataType : "json",
					async : false,
					success : function(data){
						if(data){
							alert("超级管理员不允许操作此功能");
							return false;
						}else{
							parent.showDynamicPa();
							parent.enterDynamicPa(valueUrl);
							return true;
						}
					}
				});
		});
		
         
        
	</script>
</head>
<body>
	
</body>
</html>