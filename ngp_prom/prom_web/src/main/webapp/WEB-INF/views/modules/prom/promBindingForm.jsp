<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>推广位绑定管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		
		function checkMerchantName(){
			var merchantId=$("#merchantId").val();
			if(isNaN(merchantId)){
				$("#merchantId").val("");
				$("#merchantIdId").text("请输入数字类型");
			}else{
				$.ajax({
		            type: "GET",
		            url: "${ctx}/prom/promBinding/queryMerchantName",
		            cache:false,
		            data:{
		            	"merchantId":merchantId
					},
		            success: function(data){
		                if(data != null){
		                	$("#merchantName").val(data);
		                }
		            }
		        });
			}
		}
		
		function check() {
			
			var promotionId = $("#promotionId").val().trim();
             if(!checkSafe(promotionId)){
				//$("#promotionIdId").text("推广位ID包含非法字符!");
				alertx("推广位ID包含非法字符!");
				$("#promotionId").val("");
			}
		}
		
		
		function checkSafe(a){
			if ( a.indexOf("script")>-1 || a.indexOf("\\")>-1 ){
				return false;
			}
			fibdn = new Array ("'",">","<","*","%","#","$","}","{","~","`","!","￥","/","?","&","^","(",")","=",";");
			i=fibdn.length;
			j=a.length;
			for (ii=0; ii<i; ii++) {
				for (jj=0; jj<j; jj++) {
					temp1=a.charAt(jj);
					temp2=fibdn[ii];
					if (temp1==temp2){
					return false;
					}
				}
			}
			
			return true;
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/prom/promBinding/">推广位绑定列表</a></li>
		<li class="active"><a href="${ctx}/prom/promBinding/form?promId=${promBinding.promId}">推广位绑定<shiro:hasPermission name="prom:promBinding:edit">${not empty promBinding.promId?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="prom:promBinding:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="promBinding" action="${ctx}/prom/promBinding/save" method="post" class="form-horizontal">
		<form:hidden path="promId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">商户编码：</label>
			<div class="controls">
				 <form:select id="merchantId" path="merchantId" class="input-xlarge required" onchange="checkMerchantName()">
                    <form:option value="" label="请选择商户"/>
                    <c:forEach items="${merchantIdList}" var="merchantId">
                        <form:option value="${merchantId.merchantId}" label="${merchantId.merchantId}"/>
                    </c:forEach>
                </form:select>
                <span id="merchantIdId" style="color:red;" class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户名称：</label>
			<div class="controls">
				<form:input path="merchantName" id="merchantName" htmlEscape="false" readonly="true"  maxlength="30" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">推广位ID：</label>
			<div class="controls">
				<form:input path="promotionId" id="promotionId" htmlEscape="false" maxlength="32" class="input-xlarge required" onmouseout="check()"/>
				<span id="promotionIdId" style="color:red;" class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">生效时间：</label>
			<div class="controls">
				<input name="effectiveTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${promBinding.effectiveTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">失效时间：</label>
			<div class="controls">
				<input name="loseTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${promBinding.loseTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		 <div class="control-group">
            <label class="control-label">状态：</label>
            <div class="controls">
                <form:select id="status" path="status" class="input-xlarge required">
                    <c:forEach items="${status}" var="wStatus">
                        <form:option value="${wStatus.value}" label="${wStatus.name}"/>
                    </c:forEach>
                </form:select>
            </div>
        </div>
		
		<div class="form-actions">
			<shiro:hasPermission name="prom:promBinding:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>