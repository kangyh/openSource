<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>报表规则配置管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
		
		//格式化js日期
        Date.prototype.Format = function (fmt) { //author: meizz 
	        var o = {
	            "M+": this.getMonth() + 1, //月份 
	            "d+": this.getDate(), //日 
	            "H+": this.getHours(), //小时 
	            "m+": this.getMinutes(), //分 
	            "s+": this.getSeconds(), //秒 
	            "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	            "S": this.getMilliseconds() //毫秒 
	        };
	        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	        for (var k in o)
	        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	        return fmt;
	    }
		
        $("#inputForm").validate({
			submitHandler: function(form){
				var startDate=$("#startDate").val();
				var endDate=$("#endDate").val();
				
				if(startDate=="" || endDate=="" ){
					$("#messageBox").text("日期不能为空!");
					return ;
				}
				if( startDate!="" && endDate!=""){
					if(compareDate(convertDateToJoinStr(startDate),
									convertDateToJoinStr(endDate)) > 0){
						$("#messageBox").text("开始日期不能大于结束日期!");
						return ;
					}
					if(compareDate(convertDateToJoinStr(startDate),
							convertDateToJoinStr(endDate)) == 0){
				          $("#messageBox").text("开始日期不能等于结束日期!");
				         return ;
			          }
				} 
				
				loading('正在提交，请稍等...');
				form.submit();
			},
		});
	});
	
	 function checkstartDate(){
		var startDate = $("#startDate").val();
		var nowTime = new Date().Format("yyyy-MM-dd");  
		if(startDate!="" && nowTime!=""){
			if(compareDate(convertDateToJoinStr(startDate),
							convertDateToJoinStr(nowTime)) > 0){
				$("#startDateId").text("不能大于当前日期");
				$("#startDate").val("");
			}
		}
	}
	 
	 function checkendDate(){
			var endDate = $("#endDate").val();
			var nowTime = new Date().Format("yyyy-MM-dd");  
			if( endDate!="" && nowTime!=""){
				if(compareDate(convertDateToJoinStr(endDate),
								convertDateToJoinStr(nowTime)) > 0){
					$("#endDateId").text("不能大于当前日期");
					$("#endDate").val("");
				}
			}
		}
	 
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/risk/bossRule/">报表规则配置列表</a></li>
		<li class="active"><a href="${ctx}/risk/bossRule/form?id=${bossRule.id}">报表规则配置<shiro:hasPermission name="risk:bossRule:edit">${not empty bossRule.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="risk:bossRule:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="bossRule" action="${ctx}/risk/bossRule/save" method="post" class="form-horizontal">
		<form:hidden path="ruleId"/>
		<sys:message content="${message}"/>		
		
		<div class="control-group">
			<label class="control-label">开始日期：</label>
			<div class="controls">
				<input name="startDate" id="startDate" type="text"   maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${bossRule.startDate}" pattern="yyyy-MM-dd"/>"
					onchange="checkstartDate()"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					<span id="startDateId" style="color:red;" class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束日期：</label>
			<div class="controls">
				<input name="endDate" id="endDate" type="text"  maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${bossRule.endDate}" pattern="yyyy-MM-dd"/>"
					onchange="checkendDate()"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					<span id="endDateId" style="color:red;" class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:input path="remark" htmlEscape="false" maxlength="30" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="risk:bossRule:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
</body>
</html>