<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<head>
    <title>prom_web monitor</title>
    <script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
	<script type="text/javascript">
	 $(function () {
		 $.ajax({
             type: "GET",
             url: "${ctxStatic}/monitor",
             dataType: 'json',
             success: function(data){
            	  $("body").html(data);
             },
             error: function(){
                  console.log("请求失败!");
             }
         }); 
	 });
	</script>
</head>
<body>
	
</body>