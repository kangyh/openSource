<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>日切信息配置</title>
<meta name="decorator" content="default"/>
<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#inputFormFrom").validate({
			submitHandler: function(form){
				var time=$("#time").val();
				if(time==''){
					$("#timeId").text("请输入数字类型");
					return false;
				}else{
					$("#timeId").text("");
				} 
				var busiNo=$("#busiNo").val();
				if(isNaN(busiNo)){
					$("#busiNoId").text("请输入数字类型");
					return false;
				}else{
					$("#busiNoId").text("");
				} 
				loading('正在提交，请稍等...');
				form.submit();
			},
		});
	});
	
	
</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li class="active"><a>添加银行绑定接口信息</a></li>
</ul><br/>
	<form:form id="inputFormFrom" modelAttribute="tpdsCutDay" action="${ctx}/tpds/tpdsCutDay/save?tpdsCutDayId=${tpdsCutDay.tpdsCutDayId}" method="post" class="form-horizontal">
		<sys:message content="${message}"/>	
		
		 <div class="control-group">
			<label class="control-label">标识：</label>
			<div class="controls">
				  <input name="busiNo" id="busiNo" value="${tpdsCutDay.busiNo}" htmlEscape="false"  maxlength="32" placeholder="请输入 商户号/银行号/客户编码" type="text" class="required"  style="width:256px;"/>
				<span class="help-inline" id="busiNoId" style="color: red"><font color="red">*</font> </span>
			</div>
		</div> 
		<div class="control-group">
			<label class="control-label">日切类型：</label>
			<div class="controls">
				<form:select id="cutType" path="cutType" class="input-xlarge required" >
					<c:forEach items="${cutDay}" var="cutDayss">
						<form:option value="${cutDayss.value}" label="${cutDayss.name}"/>
					</c:forEach>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
				
			</div>
		</div>
		
		 <div class="control-group">
			<label class="control-label">日切时间点：</label>
			<div class="controls">
                    <input name="cutTime" id="time" placeholder="请选择时间" style="width:256px;" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
                        value="<fmt:formatDate value="${tpdsCutDay.cutTime}" pattern="HH:mm:ss"/>"
                        onclick="WdatePicker({dateFmt:'HH:mm:ss',isShowClear:false});"/>
                    <span class="help-inline" id="timeId" style="color: red"><font color="red">*</font> </span>
                </div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保存并提交"/>
			<input id="btnCancel" class="btn" type="button" value="取 消" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>