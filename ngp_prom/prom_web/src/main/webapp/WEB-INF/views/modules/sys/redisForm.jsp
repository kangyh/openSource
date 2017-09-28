<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>缓存管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		//缓存设置
		function resetRedis(){
			if(confirm("确定要重新设置缓存吗？")){
				$.ajax({
	                type:"post",
	                url:"${ctx}/sys/redis/resetRedis",
	                data:"",
	                success: function(data) {
	                    var result = data.result;
	                    parent.showThree();
	                    parent.changeThreeName(result);
	                    return;
	                },
	                error:function(data){
	                	parent.showThreeEp();
	                    parent.changeThreeNameEp("系统错误，请稍后再试");
	                }
	            });
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/redis/">缓存设置</a></li>
	</ul><br/>
	<form:form id="inputForm" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="重新设置缓存" onclick="resetRedis()"/>&nbsp;
		</div>
	</form:form>
</body>
</html>