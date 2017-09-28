<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>风控管理</title>
<meta name="decorator" content="default"/>
<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
<script type="text/javascript">
	
	/* //上传
	function load(){
		$("#loadForm").submit();
	} */
	/* //保存
	function save(){
		$("#inputForm").submit();
	} */
	
	var validateFrom = {
			submitHandler: function(form){
				form.submit();
			}
		}
		
	
	$(document).ready(function() {
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
			}
		}
	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a>可疑名单上报-涉案账户</a></li>
	</ul>
	<form:form id="inputForm" modelAttribute="pbcAccountDynamic" action="${ctx}/pbc/pbcPaymentAccount/view/two/save" method="post" class="form-horizontal">
	<sys:message content="${message}"/>
	
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
		
	</form:form>
	<div style="font-size: 2em;font-family: serif;font: bold;padding-bottom: 20px;padding-top: 20px;">账户交易明细:</div>
	
	<table id="contentTable" class="table  table-striped   table-bordered table-condensed">
		<thead>
		<thead>
		<tr>
			<th>支付订单号</th>
			<th>交易类型</th>
			<th>支付类型</th>
			<th>交易主体的出入账标识</th>
			<th>交易时间 </th>
			<th>币种</th>
			<th>交易金额</th>
			<th>交易流水号</th>
			
			<th>交易对方所属银行机构编码</th>
			<th>交易对方银行卡所属银行名称</th>
			<th>交易对方银行卡卡号</th>
			<th>交易对方支付账号</th>
			<th>交易设备类型</th>
			<th>交易设备IP</th>
			<th>MAC地址</th>
			<th>交易设备号</th>
			<th>银行(渠道流水号)</th>
			
			<!-- <th>操作</th>
			 -->
			
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="pbcAccountDynamic">
			
				<tr>
					<td>${pbcAccountDynamic.orderId}</td>
					<td>${pbcAccountDynamic.transactionType}</td>
					<td>${pbcAccountDynamic.paymentType}</td>
					<td>${pbcAccountDynamic.borrowingSigns}</td>
					<td>
						<fmt:formatDate value="${pbcAccountDynamic.transactionTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>${pbcAccountDynamic.transCurrency}</td>
					<td>
						<fmt:formatNumber value="${pbcAccountDynamic.transactionAmount}" pattern="￥0.0000" />
					</td>
					<td>${pbcAccountDynamic.transactionSerial}</td>
					
					
					
					<td>${pbcAccountDynamic.transInBankId}</td>
					<td>${pbcAccountDynamic.transInBankName}</td>
					<td>${pbcAccountDynamic.transOutCardNumber}</td>
					<td>${pbcAccountDynamic.transOutAccountNumber}</td>
					<td>${pbcAccountDynamic.transactionDeviceType}</td>
					<td>${pbcAccountDynamic.transactionIp}</td>
					<td>${pbcAccountDynamic.transactionMac}</td>
					<td>${pbcAccountDynamic.transactionDeviceCode}</td>
					<td>${pbcAccountDynamic.bankTransactionSerial}</td>
					
					<%-- <td>
						<a href="${ctx}/riskManage/RiskMerchantFreezeOrderQuery/">冻结</a>
					</td> --%>
				</tr>
			
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	
	
	
	<form id="loadForm" action="${ctx}/pbc/pbcPaymentAccountTwo/view/two/loadModel/two?paymentAccountId=${paymentAccountId}&applicationId=${applicationIdLoad}&feedBackId=${feedBackId}" method="post" enctype="multipart/form-data" class="form-horizontal">
	
		
		<div class="control-group" style="margin-top:30px">
			<div class="control-left">
			<label class="control-label">添加交易信息：</label>
				<div class="controls">
					<input type="file" name="file" id="uploadId"/>
					<span class="info"><a href="${ctxStatic}/pbc/pbcAccountDynamict.xls">点击下载模板</a></span>  <%-- onclick="downLoad()" id="downLoadId" --%>
					<!-- <span class="info">(近30天的交易，不超过1000笔)</span> -->
					<label id="type_val_info" for="typeId" class="error"></label>
					<input type="hidden" value="${local}" name="local" id="local">
				</div>
			</div>
		</div>
		<input type="hidden" value="${pbcAccountDynamic.local}" name="local">
	<div class="form-actions">
		
		<input type="button" class="btn btn-primary"   onclick="load()" value="上传">
		<input id="btnCancel" class="btn" type="button" value="取 消" onclick="history.go(-1)" style="margin-left:50px"/>
	</div>
	</form>
	
	
</body>
</html>