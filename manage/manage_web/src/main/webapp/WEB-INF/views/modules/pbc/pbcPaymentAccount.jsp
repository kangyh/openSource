<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>风控管理</title>
<meta name="decorator" content="default"/>
<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
<script type="text/javascript">
	//上传
	function loadSave(){
		var feedBackId=$("#feedBackId").val();
        $("#inputForm").attr("action","${ctx}/pbc/pbcPaymentAccount/view/save/load?feedBackId="+feedBackId);
       
		$("#inputForm").submit();
	}
	//保存
	function save(){
		$("#inputForm").submit();
	}
	
	//保存
	function saveTwo(){
		var feedBackId=$("#feedBackId").val();
		var redioValue=$("#redioValue").val();
		if(redioValue=="Y"){
			//审核通过的保存操作 
			$("#inputForm").attr("action","${ctx}/pbc/pbcPaymentAccount/view/save?redioValue=Y&feedBackId="+feedBackId).submit();
		}else{
			$("#inputForm").attr("action","${ctx}/pbc/pbcPaymentAccount/view/save?feedBackId="+feedBackId).submit();
		}
		
	}
	
	//返回原来的页面
	function toHome(){
		window.location="${ctx}/pbc/verification";
	}
	
	function rad(){//审核拒绝，只能保存不能上报
		$("#loadSubmit").attr("disabled","disabled");
		$("#redioValue").attr("value","N");
	};
	function radCli(){//审核通过，才上报
		$("#loadSubmit").removeAttr("disabled");
		$("#redioValue").attr("value","Y");
	};
	
	$(document).ready(function() {
		$("#inputForm").validate({
			submitHandler: function(form){
			
			//单选按钮
			var v=document.getElementsByName('checkFlg');
			
			if(v.length !=0){
				var j=0
				for (var i=0;i<v.length;i++){
				 if(v.item(i).checked){
				   j++
				 }
				}
				if(j<1){
					$("#type_val").text("请选择审核状态");
					$("#type_val").removeAttr("style");
						
					return false;
				}else{
					loading('正在提交，请稍等...');
					form.submit();
				}
			}else{
				loading('正在提交，请稍等...');
				form.submit();
			}
			
			
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
	function page(n,s){
		var viewLook=$("#viewLook").val();
		
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		
		if(viewLook!=''){
			
			$("#searchForm").attr("action","${ctx}/pbc/pbcPaymentAccount/view?no=true&pageNo="+n);   
		}else{
			
			$("#searchForm").attr("action","${ctx}/pbc/pbcPaymentAccount/view?pageNo="+n);   
		} 
		$("#searchForm").submit();
    	return false;
    }
	
	function Sel2change(field){
		
		$("#channelName").val(field);
	}
	
	function load(){
		var uploadId = document.getElementById("uploadId");
		
		if(uploadId.value==null || uploadId.value==""){
			$("#type_val_info").text("文件为空");
			$("#type_val_info").removeAttr("style");
			return false;
		}
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
							$("#loadForm").submit();
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
		}
	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a>可疑名单上报-异常消费支付账户</a></li> 
	</ul>
	<form id="searchForm"  method="post" class="breadcrumb form-search">
		<input type="hidden" value="${feedBackId}" name="feedBackId" id="feedBackId">
		<input type="hidden" id="viewLook" value="${pbcPaymentAccount.no}">
	</form>
	<form:form id="inputForm" modelAttribute="pbcPaymentAccount" action="${ctx}/pbc/pbcPaymentAccount/view/save" method="post" class="form-horizontal">
	<sys:message content="${message}"/>
	
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
		
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">接收机构ID：</label>
				<div class="controls">
					<form:input path="toId" id="toId" value="${pbcPaymentAccount.toId}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;" />
					<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">交易类型编码：</label>
				<div class="controls">
					<form:input path="txCode" value="${pbcPaymentAccount.txCode}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
					<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">传输报文流水号：</label>
				<div class="controls">
					<form:input path="transSerialNumber" value="${pbcPaymentAccount.transSerialNumber}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
					<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</div>
		
		
		<div class="control-group">	
			<div class="control-left">
				<label class="control-label">业务申请编号：</label>
				<div class="controls">
					<form:input path="applicationId" value="${pbcPaymentAccount.applicationId}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
					<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			
			
			
			<div class="control-right">
				<label class="control-label">事件特征码：</label>
				<div class="controls">
					<form:select id="featureCodes" path="featureCodes" class="input-xlarge" style="width:220px;">
						<c:forEach items="${pbcPaymentAccountFeatureCodes}" var="pbcEventTypeStatus">
							<form:option value="${pbcEventTypeStatus.value}" label="${pbcEventTypeStatus.name}"/>
						</c:forEach>
					</form:select> 
					<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			
			<div class="control-right">
				<label class="control-label">异常事件描述(事件特征码为9999时必填)：</label>
				<div class="controls">
					<form:input path="abnormalDescribe" value="${pbcPaymentAccount.abnormalDescribe}" htmlEscape="false" maxlength="100" class="required" />
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</div> 
		
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">上报机构编码：</label>
				<div class="controls">
					<form:input path="reportCodes" id="reportCodes" value="${pbcPaymentAccount.reportCodes}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
					<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">我经办人姓名：</label>
				<div class="controls">
					<form:input path="operatorName"  value="${pbcPaymentAccount.operatorName}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
					<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">我方经办人电话：</label>
				<div class="controls">
					<form:input path="operatorPhoneNumber"  value="${pbcPaymentAccount.operatorPhoneNumber}" htmlEscape="false" maxlength="18" class="required" />
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</div>
		
		<%-- <c:if test="${not empty pbcPaymentAccount.yes}">
			
			<div class="control-group">
				<div class="control-left">
					<label class="control-label">审核人：</label>
					<div class="controls">
						<form:input path="riskOperator" id="riskOperator" value="${pbcPaymentAccount.riskOperator}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
						<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="control-right">
					<label class="control-label">审核时间：</label>
					<div class="controls">
						<form:input path="riskTime" id="riskTime" value="${pbcPaymentAccount.riskTime}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
						<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="control-right">
					<label class="control-label">上报时间：</label>
					<div class="controls">
						<form:input path="reportTime" id="reportTime" value="${pbcPaymentAccount.reportTime}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
						<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</div>
			<div class="control-group">
				<div class="control-left">
					<label class="control-label">上报状态：</label>
					<div class="controls">
						<form:input path="status" id="status" value="${pbcPaymentAccount.status}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
						<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="control-right">
					<label class="control-label">风控审核状态：</label>
					<div class="controls">
						<form:input path="riskStatus" id="riskStatus" value="${pbcPaymentAccount.riskStatus}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
						<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
					</div>
				</div> 
				<div class="control-right">
					<label class="control-label">风控专员处理时间：</label>
					<div class="controls">
						<form:input path="dealTime" id="dealTime" value="${pbcPaymentAccount.dealTime}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
						<span style="visibility: hidden;" class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</div>
	 </c:if> --%>
	
	<div style="font-size: 2em;font-family: serif;font: bold;padding-bottom: 20px;padding-top: 20px;">账户明细:</div>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>支付帐号</th>
			<th>支付帐号类别</th>
			<th>开户主体姓名或企业名称</th>
			<th>开户主体证件类型 </th>
			<th>开户主体证件号或证照号</th>
			<th>证照有效期</th>
			<th>开户主体绑定手机号</th>
			
			<th>登录号</th>
			<th>账户名</th>
			<th>绑定微信号</th>
			<th>绑定QQ号</th>
			<th>常用IP地址</th>
			<th>常用设备号</th>
			<th>商户名称</th>
			<th>商户号</th>
			<th>备注</th>
			
			<c:if test="${empty ngp}">
				<th>操作</th>
			</c:if>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="pbcPaymentAccount">
				<tr>
					<td>${pbcPaymentAccount.paymentAccountNumber}</td>
					<td>${pbcPaymentAccount.subjectType}</td>
					<td>${pbcPaymentAccount.accountOwnerName}</td>
					<td>${pbcPaymentAccount.accountOwnerIdType}</td>
					<td>${pbcPaymentAccount.accountOwnerId}</td>
					<td>${pbcPaymentAccount.credentialValidity}</td>
					<td>${pbcPaymentAccount.telNumber}</td>
					
					
					<td>${pbcPaymentAccount.loginId}</td>
					<td>${pbcPaymentAccount.accountName}</td>
					<td>${pbcPaymentAccount.wechatId}</td>
					<td>${pbcPaymentAccount.qq}</td>
					<td>${pbcPaymentAccount.regularIpAddress}</td>
					<td>${pbcPaymentAccount.regularDeviceNumber}</td>
					<td>${pbcPaymentAccount.onlineShopName}</td>
					<td>${pbcPaymentAccount.merchantNumber}</td>
					<td>${pbcPaymentAccount.remark}</td>
					
					<c:if test="${empty ngp}">
						<td>
							<a href="${ctx}/pbc/pbcPaymentAccount/view/add?paymentAccountId=${pbcPaymentAccount.accountNumber}&applicationId=${applicationIdLoad}&feedBackId=${feedBackId}">添加银行卡信息</a>
						</td>
					</c:if>
					
				</tr>
			
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
		<c:if test="${not empty pbcPaymentAccount.yes}">
			<div class="control-group" style="margin-top:30px">
				<label class="control-label">审核状态：</label>
				<div class="controls">
					<input type="radio" id="checkFlg1" name="checkFlg" value="Y" onclick="radCli()" /><label for="checkFlg1">审核通过</label>
					<input type="radio" id="checkFlg2" name="checkFlg" value="N" onclick="rad()"  /><label for="checkFlg2">审核拒绝</label>
					
					<label id="type_val" for="typeId3" class="error"></label>
					<input type="hidden" id="redioValue">
				</div>
			</div>
			<div class="control-group">
					<label class="control-label">审核备注：</label>
					<div class="controls">
						<form:input path="riskRemark" value="${pbcPaymentAccount.riskRemark}" htmlEscape="false" maxlength="20" class="required"  placeholder="输入冻结审核备注,不超过20个数" style="width:300px;"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
			</div> 
		</c:if> 
	</form:form>
	
	<c:if test="${empty pbcPaymentAccount.no}">
		<form id="loadForm" action="${ctx}/pbc/pbcPaymentAccount/view/loadModel?paymentAccountId=${pbcPaymentAccount.accountNumber}&applicationId=${pbcPaymentAccount.applicationId}&feedBackId=${feedBackId}" method="post" enctype="multipart/form-data" class="form-horizontal">
			<div class="control-group" style="margin-top:30px">
				<div class="control-left">
				<label class="control-label">黑名单账户资金清单：</label>
					<div class="controls">
						<input type="file" name="file" id="uploadId"/>
						<span class="info"><a href="${ctxStatic}/pbc/pbcPaymentAccount.xls">点击下载模板</a></span>  <%-- onclick="downLoad()" id="downLoadId" --%>
						<span class="info">(近30天的交易，不超过1000笔)</span>
						<label id="type_val_info" for="typeId" class="error"></label>
						<input type="button" class="btn btn-primary"   onclick="load()" value="上传">
						<%-- <input type="hidden" value="${feedBackId}" name="feedBackId" id="feedBackId"> --%>
					</div>
				</div>
			</div>
		</form>
	</c:if>
	
	<div class="form-actions">
		<%-- <c:if test="${empty pbcPaymentAccount.yes}">
			<input id="btnSubmit" class="btn btn-primary" type="submit" onclick="save()" value="保存"/> 
		</c:if> --%>
		<c:choose>
			<c:when test="${empty pbcPaymentAccount.no}">
				<c:if test="${empty pbcPaymentAccount.yes}">
					<input id="btnSubmit" class="btn btn-primary" type="submit" onclick="save()" value="保存"/> 
				</c:if>
			</c:when>
			<c:when test="${not empty pbcPaymentAccount.no}">
				<input id="btnCancel" class="btn" type="button" value="取 消" onclick="toHome()" style="margin-left:50px"/>
			</c:when>
		</c:choose>
		
		<c:if test="${not empty pbcPaymentAccount.yes}">
			<input id="btnSubmit" class="btn btn-primary" type="submit" onclick="saveTwo()" value="保存"/> 
			<input id="loadSubmit" class="btn btn-primary" type="submit" onclick="loadSave()" value="上报" style="margin-left:50px"/>
			<input id="btnCancel" class="btn" type="button" value="取 消" onclick="toHome()" style="margin-left:50px"/>  <!-- history.go(-1) -->
		</c:if>
	</div>
	
</body>
</html>