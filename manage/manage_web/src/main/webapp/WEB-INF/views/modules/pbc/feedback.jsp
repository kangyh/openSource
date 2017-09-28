<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>电信诈骗风险管理</title>
<meta name="decorator" content="default"/>
<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
<style>
       #main {
           margin: 50px;
       }
       .trd{
       		border-bottom:solid 1px #ccc;
       		padding-left:70px;
       }
       .trd span{
       		width:100px;
       		height:35px;
       		background-color:#2b91e6;
       		display: inline-block;
       		margin-right:10px;
       		color:#fff;
       		text-align:center;
       		line-height:35px;
       		cursor:pointer;
       }
       #ok{
      		width:100px;
       		height:35px;
       		float:right;
       		margin-right: 8%;
       }
   </style>
<script type="text/javascript">

	$(document).ready(function() {
		$("#searchForm").validate({
			submitHandler: function(form){
			
				var uploadId = document.getElementById("uploadId");
				if(uploadId !=null){
					var	fileName=uploadId.value;
					
					if(fileName !=''){
						var	index=fileName.lastIndexOf('.');
						if(index !=-1){
							var name=fileName.substr(index);
							
							if(name==".xlsx" || name==".XLSX" || name==".xls"|| name==".XLS"){ 
								var fileSize=uploadId.files[0].size; 
								var maxSize=20*1024*1024;
								if(fileSize>maxSize){
									$("#type_val_info").text("上传文件过大,不能超过20M");
									$("#type_val_info").removeAttr("style");
									return false;
								}else{
									loading('正在提交，请稍等...');
									form.submit();
								}
							}else{
								$("#type_val_info").text("只允许上次Excel格式的文件");
								$("#type_val_info").removeAttr("style");
								return false;
							}
						}
					}else{
						
						loading('正在提交，请稍等...');
						form.submit();
					}
				}else{
					$("searchForm").removeAttr("enctype");
					loading('正在提交，请稍等...');
					form.submit();
				}
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
	function Sel2change(field){
		
		$("#channelName").val(field);
	}
</script>
<script type="text/javascript">
	$(document).ready(function() {
		
		var featureCode = $("#featureCode").val();
		
		
	});

	function checkValue(obj){
		
		var featureCode = obj.value;
		if(featureCode=='CASEREPORT'){//案件举报
			window.location="${ctx}/pbc/feedback/save?value="+featureCode;
		}else if(featureCode=='ABNORMALACCOUNTS'){//可疑名单上报-异常开户
			window.location="${ctx}/pbc/feedback/save?value="+featureCode;
		}else if(featureCode=='ACCOUNTSINVOLVED'){//可疑名单上报-涉案账户
			window.location="${ctx}/pbc/feedback/save?value="+featureCode;
		}else if(featureCode=='EXCEPTIONEVENTS'){//可疑名单上报-异常事件
			window.location="${ctx}/pbc/feedback/save?value="+featureCode;
		}else if(featureCode=='VERIFYACCOUNT'){//账户涉案/可疑名单验证
			window.location="${ctx}/pbc/feedback/save?value="+featureCode;
		}else if(featureCode=='IDENTITYACCOUNT'){//身份涉案/可疑名单验证
			window.location="${ctx}/pbc/feedback/save?value="+featureCode;
		}else{
			
		}
		
	}
	
	//添加事件验证
	function AddMsg(){
		
		window.location="${ctx}/pbc/feedback/save";
	}
	//添加交易验证
	function AddTrans(){
		var headValue=$("#headValue").val();
		window.location="${ctx}/pbc/feedback/save?value=bb&headValue="+headValue;
	}
	
	//确认上报
	function toSave(){
		
		/* var actionURL = $("#searchForm").attr("action");
        $("#searchForm").attr("action",$("#searchForm").attr("action")+"?interface=true"); */
		
        $("#urlValue").attr("value","true");//
        
		$("#searchForm").submit();
	}
	/*//上传文件
	function downLoad(){
		var actionURL = $("#searchForm").attr("action");
        $("#searchForm").attr("action",$("#searchForm").attr("action")+"/export");
		validateFrom.validate($("#searchForm"));
		$("#searchForm").attr("action",actionURL);
		 $("#downLoadId").attr("href","${ctxStatic}/pbc/loadModel.xls"); 
	}*/
</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li><a id="url_id" href="${ctx}/pbc/feedback/save">举报及可疑验证</a></li>
</ul><br/>
<form:form id="searchForm" modelAttribute="pbcFeedBack" action="${ctx}/pbc/feedback/save/insert" method="post" enctype="multipart/form-data" class="form-horizontal">
	<sys:message content="${message}"/>
	
	<div class="control-group">
		<label class="control-label">上报事件类型：</label>
		<div class="controls">
		    <form:select id="featureCode" path="changeType" class="input-xlarge required" onchange="checkValue(this)" >
				<c:forEach items="${pbcEventType}" var="pbcEventTypeStatus">
					<form:option value="${pbcEventTypeStatus.value}" label="${pbcEventTypeStatus.name}"/>
				</c:forEach>
			</form:select> 
		</div>
	</div>
	
	<div class="trd">
		<span onclick="AddMsg()" >${headMsg}</span> <!-- 添加事件信息 -->
		<c:if test="${pbcFeedBack.yes =='aa' || pbcFeedBack.yes =='dd' || pbcFeedBack.yes =='ee'}"><span onclick="AddTrans()" id="wang">添加交易信息</span></c:if>
		<span onclick="toSave()" id="ok" >确认上报</span>
	</div>
	<br>
<!-- ------------------------------案件举报-添加案件信息---------------------------- CASEREPORT -->
<c:if test="${pbcFeedBack.yes =='aa'}">

	<div class="control-group">
		<div class="control-left">
		<label class="control-label">交易编码：</label>
			<div class="controls">
			<form:input path="transTypeCode" id="transTypeCode" value="${pbcFeedBack.transTypeCode}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;" />
			<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">传输报文流水号：</label>
				<div class="controls">
				<form:input path="transSerialNumber" id="transSerialNumber" value="${pbcFeedBack.transSerialNumber}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;" />
				<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
	</div>
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">业务申请编号：</label>
			<div class="controls">
			<form:input path="applicationId" id="applicationId_aa" value="${pbcFeedBack.applicationId}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;" />
			</div>
		</div>
	</div>
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">受害者姓名：</label>
			<div class="controls">
			<form:input path="transTypeCode"  value="${pbcFeedBack.transTypeCode}" htmlEscape="false" maxlength="12" class="required" />
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">受害人联系电话：</label>
			<div class="controls">
				<form:input path="transTypeCode" value="${pbcFeedBack.transTypeCode}" htmlEscape="false" maxlength="12" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</div>
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">证件类型：</label>
			<div class="controls">
			<form:input path="transTypeCode"  value="${pbcFeedBack.transTypeCode}" htmlEscape="false" maxlength="12" class="required" />
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">证件号：</label>
			<div class="controls">
				<form:input path="operatorName" value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</div>
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">受害人银行卡号：</label>
			<div class="controls">
			<form:input path="transTypeCode"  value="${pbcFeedBack.transTypeCode}" htmlEscape="false" maxlength="12" class="required" />
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">事件描述：</label>
			<div class="controls">
				<form:input path="remark" value="${pbcFeedBack.remark}" htmlEscape="false" maxlength="12" class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</div>
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">上报机构名称：</label>
			<div class="controls">
			<form:input path="operatorName"  value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required" />
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">经办人姓名：</label>
			<div class="controls">
				<form:input path="operatorName" value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</div>
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">经办人电话：</label>
			<div class="controls">
			<form:input path="operatorPhoneNumber"  value="${pbcFeedBack.operatorPhoneNumber}" htmlEscape="false" maxlength="12" class="required" />
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</div>

</c:if>
<!-- ------------------------------案件举报-添加案件信息----------添加交易信息-------ABNORMALACCOUNTS--- -->
<c:if test="${pbcFeedBack.yes =='bb'}">

	<div class="control-group">
		<div class="control-left">
		<label class="control-label">请求单标识：</label>
			<div class="controls">
			<form:input path="applicationId" id="applicationId" value="${pbcFeedBack.applicationId}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
			<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		 <div class="control-left">
		  <label class="control-label">交易类型：</label>
			<div class="controls">
				<form:select id="applicationId" path="applicationId" class="input-xlarge" style="width:220px;">
					<c:forEach items="${pbcEventType}" var="pbcEventTypeStatus">
						<form:option value="${pbcEventTypeStatus.value}" label="${pbcEventTypeStatus.name}"/>
					</c:forEach>
				</form:select> 
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</div>
	
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">借贷标识：</label>
			<div class="controls">
			<form:input path="operatorName"  value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required" />
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">币种：</label>
			<div class="controls">
				<form:input path="operatorName" value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</div>
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">交易金额：</label>
			<div class="controls">
			<form:input path="operatorName"  value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required" />
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">交易余额：</label>
			<div class="controls">
				<form:input path="operatorName" value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</div>
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">交易时间：</label>
			<div class="controls">
			<%-- <form:input path="transaction_time"  value="${pbcFeedBack.transaction_time}" htmlEscape="false" maxlength="12" class="required" />  HH:mm:ss --%>
			<input id="beginOperEndTime" name="beginOperEndTime" type="text" readonly="readonly" maxlength="20"  style="width:208px;" class="input-medium Wdate"
					value="<fmt:formatDate value="${pbcFeedBack.beginOperEndTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">交易流水号：</label>
			<div class="controls">
				<form:input path="operatorName" value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</div>
	<div class="control-group">
		<div class="control-left">
		  <label class="control-label">交易对方账户类型：</label>
			<div class="controls">
				<form:select id="operatorName" path="operatorName" class="input-xlarge" style="width:220px;" >
					<c:forEach items="${pbcEventType}" var="pbcEventTypeStatus">
						<form:option value="${pbcEventTypeStatus.value}" label="${pbcEventTypeStatus.name}"/>
					</c:forEach>
				</form:select> 
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">交易设备类型：</label>
			<div class="controls">
				<form:select id="operatorName" path="operatorName" class="input-xlarge" style="width:220px;" >
					<c:forEach items="${pbcEventType}" var="pbcEventTypeStatus">
						<form:option value="${pbcEventTypeStatus.value}" label="${pbcEventTypeStatus.name}"/>
					</c:forEach>
				</form:select> 
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</div>
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">交易对方账号/银行卡号：</label>
			<div class="controls">
			<form:input path="operatorName"  value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required" />
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">交易对方户主姓名：</label>
			<div class="controls">
				<form:input path="operatorName" value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</div>
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">交易对方户主证件号码：</label>
			<div class="controls">
			<form:input path="operatorName"  value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required" />
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">交易设备IP：</label>
			<div class="controls">
				<form:input path="operatorName" value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</div>
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">MAC地址：</label>
			<div class="controls">
			<form:input path="operatorName"  value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required" />
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">交易设备号：</label>
			<div class="controls">
				<form:input path="feedBackId" value="${pbcFeedBack.feedBackId}" htmlEscape="false" maxlength="12" class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</div>
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">日志号：</label>
			<div class="controls">
			<form:input path="operatorName"  value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required" />
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">凭证种类：</label>
			<div class="controls">
				<form:input path="feedBackId" value="${pbcFeedBack.feedBackId}" htmlEscape="false" maxlength="12" class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</div>
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">交易是否成功：</label>
			<div class="controls">
			<form:input path="operatorName"  value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required" />
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">凭证号：</label>
			<div class="controls">
				<form:input path="operatorName" value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</div>
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">商户名称：</label>
			<div class="controls">
			<form:input path="operatorName"  value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required" />
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">商户注册号：</label>
			<div class="controls">
				<form:input path="operatorName" value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</div>
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">交易摘要：</label>
			<div class="controls">
			<form:input path="operatorName"  value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required" />
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:input path="remark" value="${pbcFeedBack.remark}" htmlEscape="false" maxlength="12" class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</div>
	
</c:if>
<!-- ----------------------可疑名单上报-异常开户----------------- -->	
<c:if test="${pbcFeedBack.yes =='cc'}">

	<div class="control-group">
		<div class="control-left">
		<label class="control-label">交易编码：</label>
			<div class="controls">
			<form:input path="transTypeCode" id="transTypeCode" value="${pbcFeedBack.transTypeCode}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;" />
			<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">传输报文流水号：</label>
				<div class="controls">
				<form:input path="transSerialNumber" id="transSerialNumber" value="${pbcFeedBack.transSerialNumber}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;" />
				<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
	</div>
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">业务申请编号：</label>
			<div class="controls">
			<form:input path="applicationId" id="applicationId" value="${pbcFeedBack.applicationId}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;" />
			<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">事件特征码：</label>
				<div class="controls">
				<form:select id="operatorName" path="operatorName" class="input-xlarge" style="width:220px;" >
					<c:forEach items="${pbcFeatureCode}" var="pbcFeatureCodeStatus">
						<form:option value="${pbcFeatureCodeStatus.value}" label="${pbcFeatureCodeStatus.name}"/>
					</c:forEach>
				</form:select> 
				<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
	</div>
	
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">证件类型：</label>
			<div class="controls">
				<form:input path="operatorName" value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required"/> 
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">证件号：</label>
			<div class="controls">
				<form:input path="operatorName" value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</div>
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">姓名：</label>
			<div class="controls">
			<form:input path="operatorName"  value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required" />
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">上报机构名称：</label>
			<div class="controls">
				<form:input path="operatorName" value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</div>
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">经办人姓名：</label>
			<div class="controls">
			<form:input path="operatorName"  value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required" />
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">经办人电话：</label>
			<div class="controls">
				<form:input path="operatorPhoneNumber" value="${pbcFeedBack.operatorPhoneNumber}" htmlEscape="false" maxlength="12" class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</div>
</c:if>
<!-- -------------------------可疑名单上报-涉案账户-----------------------------ACCOUNTSINVOLVED -->
<c:if test="${pbcFeedBack.yes =='dd'}">

	<div class="control-group" id="add_show_info">
		<div class="controls">
			<div style="font-size: 2em;font-family: serif;font: bold;padding-bottom: 10px;">上传文件时,请下载模添加上传</div>
		</div>
	</div>
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">交易编码：</label>
			<div class="controls">
			<form:input path="transTypeCode" id="transTypeCode" value="${pbcFeedBack.transTypeCode}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;" />
			<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">传输报文流水号：</label>
				<div class="controls">
				<form:input path="transSerialNumber" id="transSerialNumber" value="${pbcFeedBack.transSerialNumber}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;" />
				<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
	</div>
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">业务申请编号：</label>
			<div class="controls">
			<form:input path="applicationId" id="applicationId" value="${pbcFeedBack.applicationId}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;" />
			<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">事件特征码：</label>
				<div class="controls">
				<form:input path="operatorName" id="operatorName" value="${pbcFeedBack.requestEventType}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;" />
				<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
	</div>
	
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">查询支付机构名称：</label>
			<div class="controls">
				<form:input path="operatorName" value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">账户类型：</label>
			<div class="controls">
			<form:select id="operatorName" path="operatorName" class="input-xlarge" style="width:220px;" >
					<c:forEach items="${pbcAccountType}" var="pbcAccountTypeStatus">
						<form:option value="${pbcAccountTypeStatus.value}" label="${pbcAccountTypeStatus.name}"/>
					</c:forEach>
			</form:select> 
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</div>
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">查询账户名：</label>
			<div class="controls">
			<form:input path="operatorName"  value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required" />
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">户主姓名：</label>
			<div class="controls">
				<form:input path="operatorName" value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</div>
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">户主证件类型：</label>
			<div class="controls">
			<form:input path="requestEventType"  value="${pbcFeedBack.requestEventType}" htmlEscape="false" maxlength="12" class="required" />
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">户主证件号：</label>
			<div class="controls">
				<form:input path="applicationId" value="${pbcFeedBack.applicationId}" htmlEscape="false" maxlength="12" class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</div>
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">联系手机号：</label>
			<div class="controls">
			<form:input path="applicationId"  value="${pbcFeedBack.applicationId}" htmlEscape="false" maxlength="12" class="required" />
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">常用通讯地址：</label>
			<div class="controls">
				<form:input path="operatorName" value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</div>
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">邮政编码：</label>
			<div class="controls">
			<form:input path="operatorName"  value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required" />
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">上报机构名称：</label>
			<div class="controls">
				<form:input path="operatorName" value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</div>
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">经办人姓名：</label>
			<div class="controls">
			<form:input path="operatorName"  value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required" />
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">经办人电话：</label>
			<div class="controls">
				<form:input path="operatorPhoneNumber" value="${pbcFeedBack.operatorPhoneNumber}" htmlEscape="false" maxlength="12" class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</div>
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">黑名单账户资金清单：</label>
			<div class="controls">
				<input type="file" name="file" id="uploadId"/>
				<span class="info"><a href="${ctxStatic}/pbc/loadMdel.xls">点击下载模板</a></span>  <%-- onclick="downLoad()" id="downLoadId" --%>
				<span class="info">(近30天的交易，不超过1000笔)</span>
				<label id="type_val_info" for="typeId" class="error"></label>
			</div>
		</div>
	</div>

</c:if>
<!-- --------------------------------可疑名单上报-异常事件-------------------------EXCEPTIONEVENTS -->
<c:if test="${pbcFeedBack.yes =='ee'}">

	<div class="control-group">
		<div class="control-left">
		<label class="control-label">交易编码：</label>
			<div class="controls">
			<form:input path="transTypeCode" id="transTypeCode" value="${pbcFeedBack.transTypeCode}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;" />
			<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">传输报文流水号：</label>
				<div class="controls">
				<form:input path="transSerialNumber" id="transSerialNumber" value="${pbcFeedBack.transSerialNumber}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;" />
				<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
	</div>
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">业务申请编号：</label>
			<div class="controls">
			<form:input path="applicationId" id="applicationId" value="${pbcFeedBack.applicationId}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;" />
			<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">事件特征码：</label>
				<div class="controls">
				<form:input path="operatorName" id="operatorName" value="${pbcFeedBack.operatorName}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;" />
				<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
	</div>
	
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">上报机构名称：</label>
			<div class="controls">
				<form:input path="operatorName" value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">经办人姓名：</label>
			<div class="controls">
				<form:input path="operatorName" value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</div>
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">经办人电话：</label>
			<div class="controls">
				<form:input path="operatorName" value="${pbcFeedBack.operatorPhoneNumber}" htmlEscape="false" maxlength="12" class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</div>
</c:if>
<!-- -----------------------账户涉案/可疑名单验证-----------------------VERIFYACCOUNT -->	
<c:if test="${pbcFeedBack.yes =='ff'}">

	<div class="control-group">
		<div class="control-left">
		<label class="control-label">交易编码：</label>
			<div class="controls">
			<form:input path="transTypeCode" id="transTypeCode" value="${pbcFeedBack.transTypeCode}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;" />
			<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">传输报文流水号：</label>
				<div class="controls">
				<form:input path="operatorName" id="operatorName" value="${pbcFeedBack.transSerialNumber}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;" />
				<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
	</div>
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">业务申请编号：</label>
			<div class="controls">
			<form:input path="operatorName" id="operatorName" value="${pbcFeedBack.operatorName}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;" />
			<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">事件特征码：</label>
				<div class="controls">
				<form:input path="operatorName" id="operatorName" value="${pbcFeedBack.operatorName}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;" />
				<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
	</div>
	
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">支付机构名称：</label>
			<div class="controls">
				<form:input path="operatorName" value="${pbcFeedBack.operatorName}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;" />
				<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">账户名：</label>
			<div class="controls">
			<form:input path="operatorName" value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required" />
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</div>
</c:if>
<!-- -----------------------身份涉案/可疑名单验证----------------------IDENTITYACCOUNT -->
<c:if test="${pbcFeedBack.yes =='gg'}">	

	<div class="control-group">
		<div class="control-left">
		<label class="control-label">交易编码：</label>
			<div class="controls">
			<form:input path="transTypeCode" id="transTypeCode" value="${pbcFeedBack.transTypeCode}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;" />
			<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">传输报文流水号：</label>
				<div class="controls">
				<form:input path="transSerialNumber" id="transSerialNumber" value="${pbcFeedBack.transSerialNumber}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;" />
				<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
	</div>
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">业务申请编号：</label>
			<div class="controls">
			<form:input path="applicationId" id="applicationId" value="${pbcFeedBack.applicationId}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;" />
			<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">证件类型：</label>
				<div class="controls">
				<form:input path="requestEventType" id="requestEventType" value="${pbcFeedBack.requestEventType}" htmlEscape="false" maxlength="12" class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
	</div>
	
	<div class="control-group">
		<div class="control-left">
		<label class="control-label">证件号：</label>
			<div class="controls">
				<form:input path="applicationId" value="${pbcFeedBack.applicationId}" htmlEscape="false" maxlength="12" class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-right">
			<label class="control-label">证件所有人姓名：</label>
			<div class="controls">
			<form:input path="operatorName" value="${pbcFeedBack.operatorName}" htmlEscape="false" maxlength="12" class="required" />
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</div>
</c:if>
	<div style="display: none;">
		<input type="file" name="file"/>
	</div>
	
	<input type="hidden" id="urlValue" name="urlValue">
	<input type="hidden" id="headValue" value="${headValue}">
	<input type="hidden" id="addValue" name="addValue" value="${addValue}">
	<div class="form-actions">
	  <div class="control-left">
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="保存并提交"/>
	  </div>
	  <!-- <div class="control-right">
		<input id="btnCancel" class="btn btn-primary" type="button" value="确认上报" onclick="toSave()"/>
		 <div class="controls">
	  </div>
     </div> -->
  </div>
</form:form> 
</body>
</html>