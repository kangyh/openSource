<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>电信诈骗风险管理</title>
<meta name="decorator" content="default" />
<style>
#pop_img{
	position:absolute;
	left:300px;
	text-align:center;
	margin:0;
	z-index:100;
	border: solid 1px #ccc;
	padding:15px;
	background:#fff;
}
#pop_img img{
	max-width: 100%;
	max-height: 100%;
}
#pop_img span{
	cursor: pointer;
	position: absolute;
	top: 0px;
	right: 4px;
}
</style>
<script type="text/javascript">
$(document).ready(function() {
	$("#pop_img").click(function(){
        $("#pop_img").hide();
    });
    $("#see_img").click(function(){
        $("#pop_img").show();
    });

});
</script>
<script type="text/javascript">
	Date.prototype.format = function(format) { 
	    var date = { 
	           "M+": this.getMonth() + 1, 
	           "d+": this.getDate(), 
	           "h+": this.getHours(), 
	           "m+": this.getMinutes(), 
	           "s+": this.getSeconds(), 
	           "q+": Math.floor((this.getMonth() + 3) / 3), 
	           "S+": this.getMilliseconds() 
	    }; 
	    if (/(y+)/i.test(format)) { 
	           format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length)); 
	    } 
	    for (var k in date) { 
	           if (new RegExp("(" + k + ")").test(format)) { 
	                  format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? date[k] : ("00" + date[k]).substr(("" + date[k]).length)); 
	           } 
	    } 
	    return format; 
	}
	
	$(document).ready(function() {
		$.post($("#url_id").attr("href")+"getBodyInfo",{
			applicationId:$("#show_applicationId").val(),
			requestEventType:$("#show_requestEventType").val()
		},function(data){
			if(data != null && data != "" && data != undefined && data.length > 0){
				data = eval(data);
				var thead = "";
				var thbody = "";
				if($("#show_requestEventType").val() == 'ACCOUNTRANSDETAIL'){
					//账户交易明细查询
					thead = "<table id=\"contentTable\" class=\"table table-striped table-bordered table-condensed\">"+
						"<thead><tr>"+
						"<th>账户类型</th>"+
						"<th>账户状态</th>"+
						"<th>币种</th>"+
						"<th>账户余额</th>"+
						"<th>账户描述</th>"+
						"<th>开户日期</th>"+
						"<th>开户IP</th>"+
						"<th>开户设备号</th>"+
						"<th>最后交易时间</th>"+
						"<th>操作</th>"+
						"</tr></thead><tbody>";
					for(var i = 0; i < data.length; i++){
						thbody += "<tr>"+
							"<td>"+data[i].accountType+"</td>"+
							"<td>"+data[i].accountStatus+"</td>"+
							"<td>"+data[i].currency+"</td>"+
							"<td>"+data[i].accountBalance+"</td>"+
							"<td>"+data[i].accountInfo+"</td>"+
							"<td>"+data[i].accountOpenTime+"</td>"+
							"<td>"+data[i].accountOpenIpAddress+"</td>"+
							"<td>"+data[i].accountOpenMachineNumber+"</td>"+
							"<td>"+new Date(data[i].lastTransactionTime).format('yyyy-MM-dd hh:mm:ss')+"</td>"+
							"<td>"+
							"<a href=\"${ctx}/pbc/pbcFeedBackQueryDealQuery/show?flg=trans&applicationId="+data[i].applicationCode+"\">查看交易详情</a>"+
							" | <a class='delete_id' href=\"${ctx}/pbc/pbcFeedBackQueryDealQuery/del?feedBackId="+$("#feedBackId_id").val()+"&param="+$("#hidden").val()+"&type=ACCOUNTRANSDETAIL&pbcId="+data[i].pbcId+"&applicationId="+data[i].applicationCode+"\">删除</a>"+
							"</td>"+
							"</tr>";
					}
					$("#showInfoBody").append(thead+thbody+"</tbody>");
				}else if($("#show_requestEventType").val() == 'ACCOUNTSUBJECTDETAIL'){
					//账户主体详情查询
					thead = "<table id=\"contentTable\" class=\"table table-striped table-bordered table-condensed\">"+
						"<thead><tr>"+
						"<th>支付帐号类别</th>"+
						"<th>开户主体姓名或企业名称</th>"+
						"<th>开户主体证件类型</th>"+
						"<th>开户主体证件号或证照号</th>"+
						"<th>证照有效期</th>"+
						"<th>开户主体绑定手机号</th>"+
						"<th>数据类型</th>"+
						"<th>绑定的数据值</th>"+
						"<th>操作</th>"+
						"</tr></thead><tbody>";
					for(var i = 0; i < data.length; i++){
						thbody += "<tr>"+
							"<td>"+data[i].subjectType+"</td>"+
							"<td>"+data[i].accountOwnerName+"</td>"+
							"<td>"+data[i].accountOwnerIdType+"</td>"+
							"<td>"+data[i].accountOwnerId+"</td>"+
							"<td>"+data[i].credentialValidity+"</td>"+
							"<td>"+data[i].telNumber+"</td>"+
							"<td>"+data[i].bindingDataType+"</td>"+
							"<td>"+data[i].bindingData+"</td>"+
							"<td>"+
							"<a href=\"${ctx}/pbc/pbcFeedBackQueryDealQuery/show?flg=bank&applicationId="+data[i].applicationCode+"\">查看银行卡信息</a>"+
							" | <a class='delete_id' href=\"${ctx}/pbc/pbcFeedBackQueryDealQuery/del?feedBackId="+$("#feedBackId_id").val()+"&param="+$("#hidden").val()+"&type=ACCOUNTSUBJECTDETAIL&pbcId="+data[i].pbcId+"&applicationId="+data[i].applicationCode+"\">删除</a>"+
							"</td>"+
							"</tr>";
					}
					$("#showInfoBody").append(thead+thbody+"</tbody>");
				}else if($("#show_requestEventType").val() == 'ACCOUNTDYNAMIC'){
					//账户动态查询
					thead = "<table id=\"contentTable\" class=\"table table-striped table-bordered table-condensed\">"+
						"<thead><tr>"+
						"<th>账户类型</th>"+
						"<th>账户状态</th>"+
						"<th>币种</th>"+
						"<th>账户余额</th>"+
						"<th>账户描述</th>"+
						"<th>开户日期</th>"+
						"<th>开户IP</th>"+
						"<th>开户设备号</th>"+
						"<th>最后交易时间</th>"+
						"<th>操作</th>"+
						"</tr></thead><tbody>";
					for(var i = 0; i < data.length; i++){
						thbody += "<tr>"+
							"<td>"+data[i].accountType+"</td>"+
							"<td>"+data[i].accountStatus+"</td>"+
							"<td>"+data[i].currency+"</td>"+
							"<td>"+data[i].accountBalance+"</td>"+
							"<td>"+data[i].accountInfo+"</td>"+
							"<td>"+data[i].accountOpenTime+"</td>"+
							"<td>"+data[i].accountOpenIpAddress+"</td>"+
							"<td>"+data[i].accountOpenMachineNumber+"</td>"+
							"<td>"+new Date(data[i].lastTransactionTime).format('yyyy-MM-dd hh:mm:ss')+"</td>"+
							"<td>"+
							"<a href=\"${ctx}/pbc/pbcFeedBackQueryDealQuery/show?flg=trans&applicationId="+data[i].applicationCode+"\">查看账户交易明细</a>"+
							" | <a class='delete_id' href=\"${ctx}/pbc/pbcFeedBackQueryDealQuery/del?feedBackId="+$("#feedBackId_id").val()+"&param="+$("#hidden").val()+"&type=ACCOUNTDYNAMIC&pbcId="+data[i].pbcId+"&applicationId="+data[i].applicationCode+"\">删除</a>"+
							"</td>"+
							"</tr>";
					}
					$("#showInfoBody").append(thead+thbody+"</tbody>");
				}else if($("#show_requestEventType").val() == 'ACCOUNTDYNAMICREMOVE'){
					//账户动态查询解除
					var body = "<form class=\"form-horizontal\"><div class=\"control-group\">"+
						"<div class=\"control-right\">"+
							"<label class=\"control-label\">动态查询解除结果：</label>"+
							"<div class=\"controls\" style=\"width: 205px;\">"+
								"<input value="+data[0].result+" maxlength=\"100\" readonly style=\"border:0px;background-color:#fff;padding-top: 3px;\" />"+
							"</div>"+
						"</div>"+
						"<div class=\"control-right\">"+
							"<label class=\"control-label\">案件编号：</label>"+
							"<div class=\"controls\" style=\"width: 205px;\">"+
								"<input value="+data[0].caseNumber+" maxlength=\"100\" readonly style=\"border:0px;background-color:#fff;padding-top: 3px;\" />"+
							"</div>"+
						"</div>"+
						"<div class=\"control-left\">"+
							"<label class=\"control-label\">动态查询解除时间：</label>"+
							"<div class=\"controls\" style=\"width: 205px;\">"+
								"<div>"+data[0].applicationTime+" </div>"+
							"</div>"+
						"</div>"+
						"<div class=\"control-left\">"+
							"<label class=\"control-label\">支付帐号 ：</label>"+
							"<div class=\"controls\" style=\"width: 205px;\">"+
								"<input value="+data[0].accountNumber+" maxlength=\"100\" readonly style=\"border:0px;background-color:#fff;padding-top: 3px;\" />"+
							"</div>"+
						"</div>"+
					"</div>"+
					
					"<div class=\"control-group\">"+
						"<div class=\"control-right\">"+
							"<label class=\"control-label\">动态查询解除结果：</label>"+
							"<div class=\"controls\" style=\"width: 205px;\">"+
								"<input value="+data[0].feedbackRemark+" maxlength=\"100\" readonly style=\"border:0px;background-color:#fff;padding-top: 3px;\" />"+
							"</div>"+
						"</div>"+
					"</div></form>";
					
					$("#showInfoBody").append(body);
				}else if($("#show_requestEventType").val() == 'PAYMENYACCOUNT'){
					//关联全支付账号查询
					thead = "<table id=\"contentTable\" class=\"table table-striped table-bordered table-condensed\">"+
						"<thead><tr>"+
						"<th>支付账号</th>"+
						"<th>支付帐号类别</th>"+
						"<th>开户主体姓名或企业名称</th>"+
						"<th>开户主体证件类型</th>"+
						"<th>开户主体证件号或证照号</th>"+
						"<th>证照有效期</th>"+
						"<th>开户主体绑定手机号</th>"+
						"<th>登录号</th>"+
						"<th>绑定微信号</th>"+
						"<th>绑定QQ号</th>"+
						"<th>常用IP地址</th>"+
						"<th>常用设备号</th>"+
						"<th>商户名称</th>"+
						"<th>商户号</th>"+
						"<th>操作</th>"+
						"</tr></thead><tbody>";
					for(var i = 0; i < data.length; i++){
						thbody += "<tr>"+
							"<td>"+data[i].accountNumber+"</td>"+
							"<td>"+data[i].subjectType+"</td>"+
							"<td>"+data[i].accountOwnerName+"</td>"+
							"<td>"+data[i].accountOwnerIdType+"</td>"+
							"<td>"+data[i].accountOwnerId+"</td>"+
							"<td>"+data[i].credentialValidity+"</td>"+
							"<td>"+data[i].telNumber+"</td>"+
							"<td>"+data[i].loginId+"</td>"+
							"<td>"+data[i].wechatId+"</td>"+
							"<td>"+data[i].qq+"</td>"+
							"<td>"+data[i].regularIpAddress+"</td>"+
							"<td>"+data[i].regularDeviceNumber+"</td>"+
							"<td>"+data[i].onlineShopName+"</td>"+
							"<td>"+data[i].merchantNumber+"</td>"+
							"<td><a href=\"${ctx}/pbc/pbcFeedBackQueryDealQuery/show?flg=bank&applicationId="+data[i].applicationCode+"\">查看银行卡信息</a>"+
							" | <a href=\"${ctx}/pbc/pbcFeedBackQueryDealQuery/show?flg=measure&applicationId="+data[i].applicationCode+"\">查看止付冻结措施信息</a>"+
							" | <a class='delete_id' href=\"${ctx}/pbc/pbcFeedBackQueryDealQuery/del?feedBackId="+$("#feedBackId_id").val()+"&param="+$("#hidden").val()+"&type=PAYMENYACCOUNT&pbcId="+data[i].paymentAccountId+"&applicationId="+data[i].applicationCode+"\">删除</a>"+
							"</td>"+
							"</tr>";
					}
					$("#showInfoBody").append(thead+thbody+"</tbody>");
				}else if($("#show_requestEventType").val() == 'TRANSNOACCOUNT'){
					//按交易流水号查询银行卡/支付帐号
					thead = "<table id=\"contentTable\" class=\"table table-striped table-bordered table-condensed\">"+
						"<thead><tr>"+
						"<th>支付机构类型</th>"+
						"<th>交易类型</th>"+
						"<th>支付类型</th>"+
						"<th>币种</th>"+
						"<th>止付金额</th>"+
						"<th>收款方银行卡所属银行机构编码</th>"+
						"<th>收款方银行卡所属银行名称</th>"+
						"<th>收款方银行卡所属银行卡号</th>"+
						"<th>收款方的支付帐号</th>"+
						"<th>消费POS机编号</th>"+
						"<th>收款方的商户号</th>"+
						"<th>付款方银行卡所属银行机构编码</th>"+
						"<th>付款方银行卡所属银行名称</th>"+
						"<th>付款方银行卡所属银行卡号</th>"+
						"<th>付款方的支付帐号</th>"+
						"<th>交易设备类型</th>"+
						"<th>交易支付设备IP</th>"+
						"<th>MAC地址</th>"+
						"<th>交易设备号</th>"+
						"<th>银行外部渠道交易流水号</th>"+
						"</tr></thead><tbody>";
					for(var i = 0; i < data.length; i++){
						thbody += "<tr>"+
							"<td>"+data[i].onlinePayCompanyType+"</td>"+
							"<td>"+data[i].transactionType+"</td>"+
							"<td>"+data[i].paymentType+"</td>"+
							"<td>"+data[i].currency+"</td>"+
							"<td>"+data[i].transferAmount+"</td>"+
							"<td>"+data[i].transInBankId+"</td>"+
							"<td>"+data[i].transInBankName+"</td>"+
							"<td>"+data[i].transInCardNumber+"</td>"+
							"<td>"+data[i].transInAccountNumber+"</td>"+
							"<td>"+data[i].posIdentity+"</td>"+
							"<td>"+data[i].merchantNumber+"</td>"+
							"<td>"+data[i].transOutBankId+"</td>"+
							"<td>"+data[i].transOutBankName+"</td>"+
							"<td>"+data[i].transOutCardNumber+"</td>"+
							"<td>"+data[i].transOutAccountNumber+"</td>"+
							"<td>"+data[i].transactionDeviceType+"</td>"+
							"<td>"+data[i].transactionIp+"</td>"+
							"<td>"+data[i].transactionMac+"</td>"+
							"<td>"+data[i].transactionDeviceCode+"</td>"+
							"<td>"+data[i].bankTransactionSerial+"</td>"+
							"<td>"+
							"<a class='delete_id' href=\"${ctx}/pbc/pbcFeedBackQueryDealQuery/del?feedBackId="+$("#feedBackId_id").val()+"&param="+$("#hidden").val()+"&type=TRANSNOACCOUNT&pbcId="+data[i].transCardPaymentId+"&applicationId="+data[i].applicationCode+"\">删除</a>"+
							"</td>"+
							"</tr>";
					}
					$("#showInfoBody").append(thead+thbody+"</tbody>");
				}
			}
		},"text");
		
		if($("#hidden").val() == "hidden"){
			$("#options").hide();
			$("#bbb").hide();
			$("#btnCancel1").hide();
			$("#btnCancel2").hide();
			$("#contentTable").find(".delete_id").hide();
		}
	});
	
	//上报
	function toReport(feedBackId){
		var ok = $('input:radio:checked').val();
		var pbcRiskStatus = $("#pbcRiskStatus").val();
		var pbcFailRemark = $("#pbcFailRemark").val();
		var pbcStatus = $("#pbcStatus").val();
		var aaa = $("#btnCancel3").val();
		var y='Y';
		var s='S';
		if(y == ok && '审核通过'== pbcRiskStatus && '上报成功' != pbcStatus){
			$.ajax({
				url:"${ctx}/pbc/pbcFeedBackQueryDealQuery/report",
				type:"get",
				cache:false,
				data:{
					"feedBackId":feedBackId
				},
				success:function(msg1){
					if(msg1 == 1000){
						$("#pbcRiskStatus").val(pbcRiskStatus);
						$("#pbcFailRemark").val(pbcFailRemark);
						$("#pbcStatus").val(pbcStatus);
						parent.showThrees();
						parent.changeThreeNames("上报成功",aaa);
					}else{
						parent.showThrees();
						parent.changeThreeNames("上报失败,"+pbcFailRemark,aaa);
					}
				}
			});
		}else if((s == pbcStatus) || ( '上报成功' == pbcStatus)){
			parent.showThree();
			parent.changeThreeName("已经上报成功，不能重复上报");
		}else{
			parent.showThree();
			parent.changeThreeName("审核未通过，不能上报");
		}
	}
	
	
//baocun
	function toSubmit(feedBackId){
		var remark=$("#remark").val();
		var riskStatus = $('input:radio:checked').val();
		var status = $("#pbcStatus").val();
		var aaa = $("#btnCancel3").val();
		var y ='y';
		
		var total = $("#total").val();
		if(remark == null || remark ==""){
			parent.showThree();
			parent.changeThreeName("审核备注不能为空");
		}else {
		$.ajax({
			url:"${ctx}/pbc/pbcFeedBackQueryDealQuery/saveRemark",
			type:"get",
			cache:false,
			data:{
				"feedBackId":feedBackId,
				"remark":remark,
				"riskStatus":riskStatus,
				"status":status
			},
			success:function(total){
				if(total==0){
					$("#pbcRiskStatus").val(status);
					parent.showThrees();
					parent.changeThreeNames("保存失败",aaa);
				}else{
					$("#pbcRemark").val(remark);
					$("#pbcRiskStatus").val(status); 
					parent.showThrees();
					parent.changeThreeNames("已保存",aaa);
				}
			}
		});
}
	}
	
	//查看附件
	 function onExport(queryInfoId){
		
		$.post($("#url_id").attr("href")+"onExport",{
		queryInfoId:$("#queryInfoId").val()
	},function(data){
		data = eval(data);
		var html='<span>X</span> <img src="data:image/jpg;base64,'  + data.fileContent + '">';
		$("#pop_img").html(html);
	});
}
	
	 
	//文件上传
	function upLoad(){
		var uploadId = document.getElementById("uploadId");
		var img1 = document.getElementById('tempimg');
		img1.dynsrc=uploadId.value;
		var requstEventType = $("#request_event_type_id").val();
		if(uploadId.value==null || uploadId.value==""){
			$("#upload_id").text("文件为空");
			$("#upload_id").show();
			return false;
		}
		if(uploadId !=null){
			var	fileName=uploadId.value;
			if(fileName !=''){
				var	index=fileName.lastIndexOf('.');
				if(index !=-1){
					var name=fileName.substr(index);
					fileName = fileName.substr(0,index);
					if(name==".xlsx" || name==".XLSX" || name==".xls"|| name==".XLS"){
						if(requstEventType == 'ACCOUNTRANSDETAIL' && fileName.indexOf('账户交易明细查询反馈')==-1){
							//账户交易明细查询
							$("#upload_id").text("请选择【账户交易明细查询反馈】Excel文件！");
							$("#upload_id").show();
							return false;
						}else if(requstEventType == 'ACCOUNTSUBJECTDETAIL' && fileName.indexOf('账户主体详情查询反馈')==-1){
							//账户主体详情查询
							$("#upload_id").text("请选择【账户主体详情查询反馈】Excel文件！");
							$("#upload_id").show();
							return false;
						}else if(requstEventType == 'ACCOUNTDYNAMIC' && fileName.indexOf('账户动态查询反馈')==-1){
							//账户动态查询
							$("#upload_id").text("请选择【账户动态查询反馈】Excel文件！");
							$("#upload_id").show();
							return false;
						}else if(requstEventType == 'ACCOUNTDYNAMICREMOVE' && fileName.indexOf('账户动态查询解除反馈')==-1){
							//账户动态查询解除
							$("#upload_id").text("请选择【账户动态查询解除反馈】Excel文件！");
							$("#upload_id").show();
							return false;
						}else if(requstEventType == 'PAYMENYACCOUNT' && fileName.indexOf('关联全支付账号查询反馈')==-1){
							//关联全支付账号查询
							$("#upload_id").text("请选择【关联全支付账号查询反馈】Excel文件！");
							$("#upload_id").show();
							return false;
						}else if(requstEventType == 'TRANSNOACCOUNT' && fileName.indexOf('按交易流水号查询银行卡支付帐号反馈')==-1){
							//按交易流水号查询银行卡/支付帐号
							$("#upload_id").text("请选择【按交易流水号查询银行卡支付帐号反馈】Excel文件！");
							$("#upload_id").show();
							return false;
						}
						var fileSize = img1.fileSize || uploadId.files[0].fileSize || uploadId.files[0].size;  
						var maxSize=20*1024*1024;
						if(fileSize>maxSize){
							$("#upload_id").text("上传文件过大,不能超过20M");
							$("#upload_id").show();
							return false;
						}else{
							$("#loadForm").submit();
						}
					}else{
						$("#upload_id").text("只允许上次Excel格式的文件");
						$("#upload_id").show();
						return false;
					}
				}
			}else{
				$("#loadForm").submit();
			}
		}
	}
	
	//保存查询结果
	function saveReault(){
	    var resultId = $("#result").find("option:selected").val();
		$.post($("#url_id").attr("href")+"saveReault",{
			feedBackId:$("#show_feedBackId").val(),
			result:resultId,
			param:$("#hidden").val()
		},function(data){
            parent.showThrees();
            parent.changeThreeNames("已保存",$("#url_id").attr("href"));
		});
	}

</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a id="url_id" href="${ctx}/pbc/pbcFeedBackQueryDealQuery/">电信诈骗交易处理列表</a></li>
		<li class="active"><a>处理</a></li>
	</ul>
	<br />
	<form:form class="form-horizontal">
		<sys:message content="${message}" />
		<input id="total" name="total" type="hidden" value="${total}" />
		<!-- 头部开始 -->
		<div>
			<div
				style="font-size: 2em; font-family: serif; font: bold; padding-bottom: 20px;"
				align="left"><%=request.getAttribute("title")%></div>
			<div class="control-group">
				<div class="control-left">
					<label class="control-label">交易类型编号：</label>
					<div class="controls">
						<input value="${pbcQueryInfo.txCode}" maxlength="100" readonly
							style="border: 0px; background-color: #fff; padding-top: 3px;" />
					</div>
				</div>
				<div class="control-right">
					<label class="control-label">发送机构编号：</label>
					<div class="controls">
						<input value="${pbcQueryInfo.messageFrom}" maxlength="100"
							readonly
							style="border: 0px; background-color: #fff; padding-top: 3px;" />
					</div>
				</div>
			</div>
			<div class="control-group">
				<div class="control-right">
					<label class="control-label">传输报文流水号：</label>
					<div class="controls" style='width:110%;'>
						<input value="${pbcQueryInfo.transSerialNumber}" maxlength="100"
							readonly
							style='border: 0px; background-color: #fff; padding-top: 3px;width: 100%;' />
					</div>
				</div>
				<div class="control-right">
					<label class="control-label" style='padding-left:50%;'>业务申请编号：</label>
					<div class="controls" style='width:150%;'>
						<input value="${pbcQueryInfo.applicationId}" readonly style='border: 0px; background-color: #fff;width:50%;padding-left:30px;' />
					</div>
				</div>
			</div>
			
			<div class="control-group">
				<div class="control-right">
					<label class="control-label">案件编号：</label>
					<div class="controls">
						<input value="${pbcQueryInfo.caseNumber}" maxlength="100" readonly
							style="border: 0px; background-color: #fff; padding-top: 3px;" />
					</div>
				</div>
				<div class="control-right">
					<label class="control-label">案件类型：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcQueryInfo.caseType}" maxlength="100" readonly
							style="border: 0px; background-color: #fff; padding-top: 3px;" />
					</div>
				</div>
				<div class="control-left">
					<label class="control-label">查询支付机构编号：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcQueryInfo.onlinePayCompanyId}" maxlength="100"
							readonly
							style="border: 0px; background-color: #fff; padding-top: 3px;" />
					</div>
				</div>
				<div class="control-right">
					<label class="control-label">查询支付机构名称：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcQueryInfo.onlinePayCompanyName}"
							maxlength="100" readonly
							style="border: 0px; background-color: #fff; padding-top: 3px;" />
					</div>
				</div>
			</div>
			<!-- 头部结束 -->

			<!-- 信息内容开始 -->
			<%-- 账户交易明细查询  --%>
			<c:if test="${pbcFeedBack.requestEventType == 'ACCOUNTRANSDETAIL'}">
				<div class="control-group">
					<div class="control-left">
						<label class="control-label">明细查询传入参数的类型：</label>
						<div class="controls" style="width: 205px;">
							<input value="${pbcTransDetailQuery.dataType}" maxlength="100"
								readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;" />
						</div>
					</div>
					<div class="control-right">
						<label class="control-label">明细查询操作的传入参数：</label>
						<div class="controls" style="width: 205px;">
							<input value="${pbcTransDetailQuery.data}" maxlength="100"
								readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;" />
						</div>
					</div>
					<div class="control-left">
						<label class="control-label">查询类型：</label>
						<div class="controls" style="width: 205px;">
							<input value="${pbcTransDetailQuery.inquiryMode}" maxlength="100"
								readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;" />
						</div>
					</div>
					<div class="control-right">
						<label class="control-label">支付帐号明细查询类别：</label>
						<div class="controls" style="width: 205px;">
							<input value="${pbcTransDetailQuery.subjectType}" maxlength="100"
								readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;" />
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="control-left">
						<label class="control-label">交易流水查询起始时间：</label>
						<div class="controls" style="width: 205px;">
							<fmt:formatDate value="${pbcTransDetailQuery.startTime}"
								type="both" pattern="yyyy-MM-dd HH:mm:ss" />
						</div>
					</div>
					<div class="control-right">
						<label class="control-label">交易流水查询截止日期：</label>
						<div class="controls" style="width: 205px;">
							<fmt:formatDate value="${pbcTransDetailQuery.expireTime}"
								type="both" pattern="yyyy-MM-dd HH:mm:ss" />
						</div>
					</div>
				</div>
			</c:if>

			<%-- 账户主体详情查询   --%>
			<c:if
				test="${pbcFeedBack.requestEventType == 'ACCOUNTSUBJECTDETAIL'}">
				<div class="control-group">
					<div class="control-left">
						<label class="control-label">明细查询传入参数的类型：</label>
						<div class="controls" style="width: 205px;">
							<input value="${pbcQueryInfo.dataType}" maxlength="100" readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;" />
						</div>
					</div>
					<div class="control-right">
						<label class="control-label">明细查询操作的传入参数：</label>
						<div class="controls" style="width: 205px;">
							<input value="${pbcQueryInfo.data}" maxlength="100" readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;" />
						</div>
					</div>
				</div>
			</c:if>

			<%-- 账户动态查询  --%>
			<c:if test="${pbcFeedBack.requestEventType == 'ACCOUNTDYNAMIC'}">
				<div class="control-group">
					<div class="control-left">
						<label class="control-label">动态查询传入参数的类型：</label>
						<div class="controls" style="width: 205px;">
							<input value="${pbcAccountDynamicQuery.dataType}" maxlength="100"
								readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;" />
						</div>
					</div>
					<div class="control-left">
						<label class="control-label">支付账户类型：</label>
						<div class="controls" style="width: 205px;">
							<input value="${pbcAccountDynamicQuery.subjectType}"
								maxlength="100" readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;" />
						</div>
					</div>
					<div class="control-right">
						<label class="control-label">支付账号：</label>
						<div class="controls" style="width: 205px;">
							<input value="${pbcAccountDynamicQuery.accountNumber}"
								maxlength="100" readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;" />
						</div>
					</div>
					<div class="control-left">
						<label class="control-label">支付账户主体的证件类型：</label>
						<div class="controls" style="width: 205px;">
							<input value="${pbcAccountDynamicQuery.idType}" maxlength="100"
								readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;" />
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="control-left">
						<label class="control-label">支付账户主体的证件号码：</label>
						<div class="controls" style="width: 205px;">
							<input value="${pbcAccountDynamicQuery.idNumber}" maxlength="100"
								readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;" />
						</div>
					</div>
					<div class="control-left">
						<label class="control-label">执行起始日期：</label>
						<div class="controls" style="width: 205px;">
							<fmt:formatDate value="${pbcAccountDynamicQuery.startTime}"
								type="both" pattern="yyyy-MM-dd HH:mm:ss" />
						</div>
					</div>
					<div class="control-left">
						<label class="control-label">执行截止日期：</label>
						<div class="controls" style="width: 205px;">
							<fmt:formatDate value="${pbcAccountDynamicQuery.expireTime}"
								type="both" pattern="yyyy-MM-dd HH:mm:ss" />
						</div>
					</div>
				</div>
			</c:if>

			<%-- 账户动态查询解除   --%>
			<c:if
				test="${pbcFeedBack.requestEventType == 'ACCOUNTDYNAMICREMOVE'}">
				<div class="control-group">
					<div class="control-left">
						<label class="control-label">原动态查询申请编号：</label>
						<div class="controls" style="width: 100%;">
							<input value="${pbcQueryInfo.originalApplicationId}"
								maxlength="100" readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;width: 100%;" />
						</div>
					</div>
				</div>
			</c:if>

			<%-- 关联全支付账号查询  --%>
			<c:if test="${pbcFeedBack.requestEventType == 'PAYMENYACCOUNT'}">
				<div class="control-group">
					<div class="control-left">
						<label class="control-label">明细查询传入参数的类型：</label>
						<div class="controls" style="width: 205px;">
							<input value="${pbcPaymentAccountQuery.dataType}" maxlength="100"
								readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;" />
						</div>
					</div>
					<div class="control-right">
						<label class="control-label">明细查询操作的传入参数：</label>
						<div class="controls" style="width: 205px;">
							<input value="${pbcPaymentAccountQuery.data}" maxlength="100"
								readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;" />
						</div>
					</div>
					<div class="control-left">
						<label class="control-label">支付帐号明细查询类别：</label>
						<div class="controls" style="width: 205px;">
							<input value="${pbcPaymentAccountQuery.subjectType}"
								maxlength="100" readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;" />
						</div>
					</div>
					<div class="control-right">
						<label class="control-label">开户主体姓名或商户名：</label>
						<div class="controls" style="width: 205px;">
							<input value="${pbcPaymentAccountQuery.accountOwnerName}"
								maxlength="100" readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;" />
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="control-left">
						<label class="control-label">开户主体证件类型：</label>
						<div class="controls" style="width: 205px;">
							<input value="${pbcPaymentAccountQuery.accountOwnerIdType}"
								maxlength="100" readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;" />
						</div>
					</div>
				</div>
			</c:if>

			<%-- 按交易流水号查询银行卡/支付帐号  --%>
			<c:if test="${pbcFeedBack.requestEventType == 'TRANSNOACCOUNT'}">
				<div class="control-group">
					<div class="control-left">
						<label class="control-label">查询传入参数的类型：</label>
						<div class="controls" style="width: 205px;">
							<input value="${pbcTransSerialCardPaymentAccountQuery.dataType}"
								maxlength="100" readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;" />
						</div>
					</div>
					<div class="control-right">
						<label class="control-label">查询操作的传入参数：</label>
						<div class="controls" style="width: 205px;">
							<input value="${pbcTransSerialCardPaymentAccountQuery.data}"
								maxlength="100" readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;" />
						</div>
					</div>
					<div class="control-left">
						<label class="control-label">银行机构编码：</label>
						<div class="controls" style="width: 205px;">
							<input value="${pbcTransSerialCardPaymentAccountQuery.bankId}"
								maxlength="100" readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;" />
						</div>
					</div>
					<div class="control-right">
						<label class="control-label">交易时间：</label>
						<div class="controls" style="width: 205px;">
							<fmt:formatDate
								value="${pbcTransSerialCardPaymentAccountQuery.transactionDate}"
								type="both" pattern="yyyy-MM-dd HH:mm:ss" />
						</div>
					</div>
				</div>
			</c:if>
			<!-- 信息内容结束 -->


			<!-- 尾部开始 -->
			<div class="control-group">
				<div class="control-right">
					<label class="control-label">查询事由：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcQueryInfo.reason}" maxlength="100" readonly
							style="border: 0px; background-color: #fff; padding-top: 3px;" />
					</div>
				</div>
				<div class="control-right">
					<label class="control-label">查询说明：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcQueryInfo.remark}" maxlength="100" readonly
							style="border: 0px; background-color: #fff; padding-top: 3px;" />
					</div>
				</div>
				<div class="control-left">
					<label class="control-label">申请时间：</label>
					<div class="controls" style="width: 205px;">
						<fmt:formatDate
								value="${pbcQueryInfo.applicationTime}"
								type="both" pattern="yyyy-MM-dd HH:mm:ss" />
					</div>
				</div>
				<div class="control-left">
					<label class="control-label">申请机构编码：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcQueryInfo.applicationOrgId}" maxlength="100"
							readonly
							style="border: 0px; background-color: #fff; padding-top: 3px;" />
					</div>
				</div>
			</div>
			<div class="control-group">
				<div class="control-right">
					<label class="control-label">申请机构名称：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcQueryInfo.applicationOrgName}" maxlength="100"
							readonly
							style="border: 0px; background-color: #fff; padding-top: 3px;" />
					</div>
				</div>
				<div class="control-left">
					<label class="control-label">经办人证件类型：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcQueryInfo.operatorIdType}" maxlength="100"
							readonly
							style="border: 0px; background-color: #fff; padding-top: 3px;" />
					</div>
				</div>
				<div class="control-right">
					<label class="control-label">经办人证件号：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcQueryInfo.operatorIdNumber}" maxlength="100"
							readonly
							style="border: 0px; background-color: #fff; padding-top: 3px;" />
					</div>
				</div>
				<div class="control-left">
					<label class="control-label">经办人姓名：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcQueryInfo.operatorName}" maxlength="100"
							readonly
							style="border: 0px; background-color: #fff; padding-top: 3px;" />
					</div>
				</div>
			</div>
			<div class="control-group">
				<div class="control-right">
					<label class="control-label">经办人电话：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcQueryInfo.operatorPhoneNumber}" maxlength="100"
							readonly
							style="border: 0px; background-color: #fff; padding-top: 3px;" />
					</div>
				</div>
				<div class="control-left">
					<label class="control-label">协查人证件类型：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcQueryInfo.investigatorIdType}" maxlength="100"
							readonly
							style="border: 0px; background-color: #fff; padding-top: 3px;" />
					</div>
				</div>
				<div class="control-right">
					<label class="control-label">协查人证件号：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcQueryInfo.investigatorIdNumber}"
							maxlength="100" readonly
							style="border: 0px; background-color: #fff; padding-top: 3px;" />
					</div>
				</div>
				<div class="control-left">
					<label class="control-label">协查人姓名：</label>
					<div class="controls" style="width: 205px;">
						<input value="${pbcQueryInfo.investigatorName}" maxlength="100"
							readonly
							style="border: 0px; background-color: #fff; padding-top: 3px;" />
					</div>
				</div>
			</div>
		</div>

		<div class="control-group" id="options">
			<div class="control-right">
				<a id= "see_img" onclick="onExport('${pbcFeedBack.applicationId}')">查看附件</a>
				<div id="pop_img">
				</div>
			</div>
		</div>

		<div>
			<div
				style="font-size: 2em; font-family: serif; font: bold; padding-bottom: 20px;"
				align="left">反馈信息-通用信息</div>
			<form:form id="inputForm" modelAttribute="pbcFeedBack"
				action="${ctx}/pbc/pbcFeedBackQueryDealQuery/updatePbcFeedBack"
				method="post" class="form-horizontal">
				<form:hidden id="show_feedBackId" path="feedBackId" />
				<form:hidden id="show_applicationId" path="applicationId" />
				<form:hidden id="show_requestEventType" path="requestEventType" />
				<div class="control-group">
					<div class="control-right">
						<label class="control-label">接收机构ID：</label>
						<div class="controls" style="width: 205px;">
							<input value="${pbcFeedBack.toId}" maxlength="100" readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;" />
						</div>
					</div>
					<div class="control-left">
						<label class="control-label">交易类型编码：</label>
						<div class="controls" style="width: 205px;">
							<input value="${pbcFeedBack.txCode}" maxlength="100" readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;" />
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="control-right">
						<label class="control-label">传输报文流水号：</label>
						<div class="controls" style='width:110%;'>
							<input value="${pbcFeedBack.transSerialNumber}" maxlength="100"
								readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;width: 100%;" />
						</div>
					</div>
					<div class="control-left">
						<label class="control-label" style='padding-left:50%;'>业务申请编号：</label>
						<div class="controls" style='width:150%;'>
							<input value="${pbcFeedBack.applicationId}" maxlength="100"
								readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;width:50%;padding-left:30px;" />
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="control-right">
						<label class="control-label">查询结果：</label>
						<div class="controls" style="width: 205px;">
							<select id="result" path="result" onchange="saveReault(this.value)" class="input-xlarge">
								<option value="">请选择</option>
								<c:forEach items="${yList}" var="wStatus">
									<option <c:if test="${pbcFeedBack.result == wStatus.value}">selected="selected"</c:if> style="width: 100px;" value="${wStatus.value}" >${wStatus.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="control-left">
						<label class="control-label">我经办人姓名：</label>
						<div class="controls" style="width: 205px;">
							<input value="${pbcFeedBack.operatorName}" maxlength="100"
								readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;" />
						</div>
					</div>
					<div class="control-right">
						<label class="control-label">我方经办人电话：</label>
						<div class="controls" style="width: 205px;">
							<input value="${pbcFeedBack.operatorPhoneNumber}" maxlength="100"
								readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;" />
						</div>
					</div>
					<div class="control-left">
						<label class="control-label">反馈机构名称：</label>
						<div class="controls" style="width: 205px;">
							<input value="${pbcFeedBack.feedBackOrgName}" maxlength="100"
								readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;" />
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="control-right">
						<label class="control-label">反馈查询说明：</label>
						<div class="controls" style="width: 205px;">
							<input value="${pbcFeedBack.feedBackRemark}" maxlength="100"
								readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;" />
						</div>
					</div>
					<div class="control-left">
						<label class="control-label">备注：</label>
						<div class="controls" style="width: 205px;">
							<input id="pbcRemark" value="${pbcFeedBack.remark}"
								maxlength="100" readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;" />
						</div>
					</div>
					<div class="control-right">
						<label class="control-label">上报状态：</label>
						<div class="controls" style="width: 205px;">
							<input id="pbcStatus" value="${pbcFeedBack.status}" maxlength="100" readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;" />
						</div>
					</div>
					<div class="control-left">
						<label class="control-label">风控审核状态：</label>
						<div class="controls" style="width: 205px;">
							<input id="pbcRiskStatus" value="${pbcFeedBack.riskStatus}"
								maxlength="100" readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;" />
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="control-right">
						<label class="control-label">风控专员处理时间：</label>
						<div class="controls" style="width: 205px;">
							<fmt:formatDate value="${pbcFeedBack.dealTime}" type="both"
								pattern="yyyy-MM-dd HH:mm:ss" />
						</div>
					</div>
					<div class="control-left">
						<label class="control-label">审核人：</label>
						<div class="controls" style="width: 205px;">
							<input value="${pbcFeedBack.riskOperator}" maxlength="100"
								readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;" />
						</div>
					</div>
					<div class="control-right">
						<label class="control-label">风控专员处理时间：</label>
						<div class="controls" style="width: 205px;">
							<fmt:formatDate value="${pbcFeedBack.dealTime}" type="both"
								pattern="yyyy-MM-dd HH:mm:ss" />
						</div>
					</div>
					<div class="control-left">
						<label class="control-label">审核人：</label>
						<div class="controls" style="width: 205px;">
							<input id="pbcRiskOperator" value="${pbcFeedBack.riskOperator}"
								maxlength="100" readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;" />
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="control-right">
						<label class="control-label">审核时间：</label>
						<div class="controls" style="width: 205px;">
							<fmt:formatDate value="${pbcFeedBack.riskTime}" type="both"
								pattern="yyyy-MM-dd HH:mm:ss" />
						</div>
					</div>
					<div class="control-left">
						<label class="control-label">上报时间：</label>
						<div class="controls" style="width: 205px;">
							<fmt:formatDate value="${pbcFeedBack.reportTime}" type="both"
								pattern="yyyy-MM-dd HH:mm:ss" />
						</div>
					</div>
					<div class="control-right">
						<label class="control-label">上报失败原因：</label>
						<div class="controls" style="width: 205px;">
							<input id="pbcFailRemark" value="${pbcFeedBack.failRemark}" maxlength="100" readonly
								style="border: 0px; background-color: #fff; padding-top: 3px;" />
						</div>
					</div>
				</div>
			</form:form>
		</div>

		<div class="controls">
			<div
				style="font-size: 2em; font-family: serif; font: bold; padding-bottom: 20px;"><%=request.getAttribute("feedback")%>
			</div>
			<form id="loadForm" style='float: inherit;' action="${ctx}/pbc/pbcFeedBackQueryDealQuery/upload" method="post" enctype="multipart/form-data" class="form-horizontal">
				<input type="hidden" name='requestEventType' id='request_event_type_id' value='${pbcFeedBack.requestEventType}' /> 
				<input type="hidden" name='applicationId' value='${pbcFeedBack.applicationId}' /> 
				<input type="hidden" name='deal' value='deal' /> 
				<input type="hidden" name='feedBackId' id="feedBackId_id" value='${pbcFeedBack.feedBackId}' /> 
				<input type="hidden" name='flg' value='${flg}' /> 
				<input type="file" name="file" id="uploadId"/>
				<input id="hidden" name="param" type="hidden" value="${hidden}" />
				<c:if test="${pbcFeedBack.requestEventType == 'TRANSNOACCOUNT'}">
					<span class="info"><a title="按交易流水号查询银行卡支付帐号反馈"
						href="${ctxStatic}/pbc/按交易流水号查询银行卡支付帐号反馈.xlsx">点击下载模板</a></span>
				</c:if>
				<c:if test="${pbcFeedBack.requestEventType == 'PAYMENYACCOUNT'}">
					<span class="info"><a title="关联全支付账号查询反馈"
						href="${ctxStatic}/pbc/关联全支付账号查询反馈.xlsx">点击下载模板</a></span>
				</c:if>
				<c:if test="${pbcFeedBack.requestEventType == 'ACCOUNTDYNAMIC'}">
					<span class="info"><a title="账户动态查询反馈"
						href="${ctxStatic}/pbc/账户动态查询反馈.xlsx">点击下载模板</a></span>
				</c:if>
				<c:if
					test="${pbcFeedBack.requestEventType == 'ACCOUNTDYNAMICREMOVE'}">
					<span class="info"><a title="账户动态查询解除反馈"
						href="${ctxStatic}/pbc/账户动态查询解除反馈.xlsx">点击下载模板</a></span>
				</c:if>
				<c:if test="${pbcFeedBack.requestEventType == 'ACCOUNTRANSDETAIL'}">
					<span class="info"><a title="账户交易明细查询反馈"
						href="${ctxStatic}/pbc/账户交易明细查询反馈.xlsx">点击下载模板</a></span>
				</c:if>
				<c:if
					test="${pbcFeedBack.requestEventType == 'ACCOUNTSUBJECTDETAIL'}">
					<span class="info"><a title="账户主体详情查询反馈"
						href="${ctxStatic}/pbc/账户主体详情查询反馈.xlsx">点击下载模板</a></span>
				</c:if>
				<span class="info" style="color: red;"></span> 
				<label id="upload_id" class="error"> </label>
				<img id="tempimg" dynsrc=''/> 
				<input id="btnSubmit" class="btn btn-primary" type="button" onclick="upLoad()" value="上传" />
				<span style="padding-left: 20px;color: #e9322d;font-size: 2em;" >如果没有数据上传请填写反馈信息中【查询结果】！！！</span>
			</form>
			<form action=''>
				<div id="showInfoBody"></div>
			</form>
		</div>
		<!-- 尾部结束 -->
	</form:form>
	<div class="control-group" id="bbb">
		<div class="control-left">
			<label class="control-label">审核备注：</label> <input id="remark"
				value="" htmlEscape="false" maxlength="12" class="required"
				placeholder="输入冻结审核备注,不超过40个数" /> <span class="help-inline"><font
				color="red">*</font> </span>
		</div>
		<div class="control-right" style='margin-left: 30px;'>
			<label class="control-label">审核状态：</label> <input id="pass"
				type="radio" calss="type_class" name="reviewed" value="Y" checked /><label
				for="pass">审核通过</label> <input id="refuse" type="radio"
				calss="type_class" name="reviewed" value="N" /><label for="refuse">审核拒绝</label>
		</div>
	</div>
	<span id="message" style="color: red; font-size: x-large;"></span>
	<!-- 上报按钮 -->
	<div class="form-actions">
		<input id="btnCancel1" class="btn btn-primary" type="button" value="保存" onclick="toSubmit(${feedBackId})" /> 
		<input id="btnCancel2" class="btn btn-primary" type="button" value="上报" onclick="toReport(${feedBackId})" style="margin-left: 30px" /> 
		<input id="btnCancel" class="btn btn-primary" type="button" value="返 回" onclick="history.go(-1)" style="margin-left: 30px" />
		<input id="btnCancel3" class="btn btn-primary" type="hidden" value="${ctx}/pbc/pbcFeedBackQueryDealQuery/deal?feedBackId=${feedBackId}"/>
	</div>

</body>
</html>