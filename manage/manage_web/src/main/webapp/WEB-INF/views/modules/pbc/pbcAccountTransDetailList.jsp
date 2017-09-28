<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>电信诈骗风险管理</title>
	<meta name="decorator" content="default"/>
	<meta http-equiv="Content-Type" content="multipart/form-data; charset=UTF-8" />
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
		});
		//验证搜索条件
		var validateFrom = {
			validate: function(form){
				form.submit();
			}
		}
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
		
		//文件上传
		function upLoad(){
			var uploadId = document.getElementById("uploadId");
			var img1 = document.getElementById('tempimg');
			img1.dynsrc=uploadId.value;
			var flg = $("#flg").val();
			if(uploadId.value==null || uploadId.value==""){
				$("#upload_id").text("文件为空");
				$("#upload_id").show();
				return false;
			}
			if(uploadId !=null){
				var	fileName=uploadId.value;
				if(fileName !=''){
					//获取文件名称
					var	index=fileName.lastIndexOf('.');
					if(index !=-1){
						var name=fileName.substr(index);
						fileName = fileName.substr(0,index);
						//分割后缀名
						if(name==".xlsx" || name==".XLSX" || name==".xls"|| name==".XLS"){ 
							if(flg == 'bank' && fileName.indexOf('银行卡信息模板')==-1){
								$("#upload_id").text("请选择【银行卡信息模板】Excel文件！");
								$("#upload_id").show();
								return false;
							}else if(flg == 'trans' && fileName.indexOf('交易流水信息模板')==-1){
								$("#upload_id").text("请选择【交易流水信息模板】Excel文件！");
								$("#upload_id").show();
								return false;
							}else if(flg == 'measure' &&  fileName.indexOf('止付冻结措施信息模板')==-1){
								$("#upload_id").text("请选择【止付冻结措施信息模板】Excel文件！");
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
		
		//返回
		function goBack(){
			window.location.href = $("#url_id").attr("href")
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/pbc/pbcFeedBackQueryDealQuery/">电信诈骗交易处理列表</a></li>
		<li><a id="url_id" href="${ctx}/pbc/pbcFeedBackQueryDealQuery/deal?feedBackId=${pbcFeedBack.feedBackId}">处理</a></li>
		<c:if test="${flg == 'trans'}">
			<li class="active"><a>交易信息详情</a></li>
		</c:if>
		<c:if test="${flg == 'bank'}">
			<li class="active"><a>银行卡信息</a></li>
		</c:if>
		<c:if test="${flg == 'measure'}">
			<li class="active"><a>止付冻结措施信息</a></li>
		</c:if>
	</ul>
	<form:form id="loadForm" modelAttribute="pbcTransInfo" action="${ctx}/pbc/pbcFeedBackQueryDealQuery/upload" enctype="multipart/form-data" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="flg" type="hidden" name="flg" value="${flg}"/>
		<input id="applicationId" type="hidden" name="applicationId" value="${pbcFeedBack.applicationId}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" onclick="goBack()" value="返回"/></li>
			<li class="btns">
				<input type="file" name="file" id="uploadId"/>
				<c:if test="${flg == 'trans'}">
					<span class="info"><a title="按交易流水号查询银行卡支付帐号反馈" href="${ctxStatic}/pbc/交易流水信息模板.xlsx">点击下载模板</a></span>
					<span class="info" style="color:red;">(近30天的交易，不超过1000笔)</span>
				</c:if>
				<c:if test="${flg == 'bank'}">
					<span class="info"><a title="银行" href="${ctxStatic}/pbc/银行卡信息模板.xlsx">点击下载模板</a></span>
				</c:if>
				<c:if test="${flg == 'measure'}">
					<span class="info"><a title="措施" href="${ctxStatic}/pbc/止付冻结措施信息模板.xlsx">点击下载模板</a></span>
				</c:if>
				<label id="upload_id" class="error"> </label>
				<img id="tempimg" dynsrc=''/> 
				<input id="btnSubmit" class="btn btn-primary" type="button" onclick="upLoad()" value="上传"/>
			</li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<c:if test="${flg == 'trans'}">
					<th>支付订单号</th>
					<th>交易类型</th>
					<th>支付类型</th>
					<th>交易主体的出入账标识</th>
					<th>交易时间</th>
					<th>币种</th>
					<th>交易金额</th>
					<th>交易流水号</th>
					<th>交易余额</th>
					<th>交易对方所属银行机构编码</th>
					<th>交易对方银行卡所属银行名称</th>
					<th>交易对方银行卡卡号</th>
					<th>交易对方支付账号</th>
					<th>交易设备类型</th>
					<th>交易设备IP</th>
					<th>MAC地址</th>
					<th>交易设备号</th>
					<th>银行(渠道流水号)</th>
					<th>备注</th>
					<shiro:hasPermission name="pbc:pbcFeedBackQueryDeal:edit"><th>操作</th></shiro:hasPermission>
				</c:if>
				<c:if test="${flg == 'bank'}">
					<th>银行卡所属银行机构编码</th>
					<th>银行卡所属银行名称</th>
					<th>银行卡卡号</th>
					<th>银行卡类型</th>
					<th>银行卡认证状态</th>
					<th>银行卡有效期</th>
					<th>银行卡其他信息</th>
					<shiro:hasPermission name="pbc:pbcFeedBackQueryDeal:edit"><th>操作</th></shiro:hasPermission>
				</c:if>
				<c:if test="${flg == 'measure'}">
					<th>措施序号</th>
					<th>支付账号</th>
					<th>措施类型</th>
					<th>开始日</th>
					<th>截止日</th>
					<th>机关名称 </th> 
					<th>币种</th> 
					<th>执行金额 </th> 
					<th>备注 </th> 
					<shiro:hasPermission name="pbc:pbcFeedBackQueryDeal:edit"><th>操作</th></shiro:hasPermission>
				</c:if>
			</tr>
		</thead>
		<tbody>
			<c:if test="${flg == 'trans'}">
				<c:forEach items="${page.list}" var="pbcTransInfo">
					<tr>
						<td>${pbcTransInfo.orderId}</td>
						<td>${pbcTransInfo.transactionType}</td>
						<td>${pbcTransInfo.paymentType}</td>
						<td>${pbcTransInfo.borrowingSigns}</td>
						<td>
							<fmt:formatDate value="${pbcTransInfo.transactionTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>${pbcTransInfo.currency}</td>
						<td>
							<fmt:formatNumber value="${pbcTransInfo.transactionAmount}" pattern="￥0.0000" />
						</td>
						<td>${pbcTransInfo.transactionSerial}</td>
						<td>
							<fmt:formatNumber value="${pbcTransInfo.accountBalance}" pattern="￥0.0000" />
						</td>
						<td>${pbcTransInfo.transInBankId}</td>
						<td>${pbcTransInfo.transInBankName}</td>
						<td>${pbcTransInfo.transInCardNumber}</td>
						<td>${pbcTransInfo.transInAccountNumber}</td>
						<td>${pbcTransInfo.transactionDeviceType}</td>
						<td>${pbcTransInfo.transactionIp}</td>
						<td>${pbcTransInfo.transactionMac}</td>
						<td>${pbcTransInfo.transactionDeviceCode}</td>
						<td>${pbcTransInfo.bankTransactionSerial}</td>
						<td>${pbcTransInfo.remark}</td>
						<shiro:hasPermission name="pbc:pbcFeedBackQueryDeal:edit"><td>
							<a href="${ctx}/pbc/pbcFeedBackQueryDealQuery/del?flg=${flg}&pbcId=${pbcTransInfo.pbcId}&applicationId=${pbcFeedBack.applicationId}">删除</a>
						</td></shiro:hasPermission>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${flg == 'bank'}">
				<c:forEach items="${page.list}" var="pbcBankInfo">
					<tr>	
						<td>${pbcBankInfo.bankId}</td>
						<td>${pbcBankInfo.bankName}</td>
						<td>${pbcBankInfo.bankAccount}</td>
						<td>${pbcBankInfo.cardType}</td>
						<td>${pbcBankInfo.cardValidation}</td>
						<td>${pbcBankInfo.cardExpiryDate}</td>
						<td>${pbcBankInfo.cardInfo}</td>
						<shiro:hasPermission name="pbc:pbcFeedBackQueryDeal:edit"><td>
							<a href="${ctx}/pbc/pbcFeedBackQueryDealQuery/del?flg=${flg}&pbcId=${pbcBankInfo.pbcId}&applicationId=${pbcFeedBack.applicationId}">删除</a>
						</td></shiro:hasPermission>
					</tr>
				</c:forEach>	
			</c:if>
			<c:if test="${flg == 'measure'}">
				<c:forEach items="${page.list}" var="pbcMeasureInfo">
					<tr>
						<td>${pbcMeasureInfo.freezeSerial}</td>
						<td>${pbcMeasureInfo.paymentAccountNumber}</td>
						<td>${pbcMeasureInfo.measureType}</td>
						<td>
							<fmt:formatDate value="${pbcMeasureInfo.measureStartTime}" type="both" pattern="yyyy-MM-dd"/>
						</td>
						<td>
							<fmt:formatDate value="${pbcMeasureInfo.measureEndTime}" type="both" pattern="yyyy-MM-dd"/>
						</td>
						<td>${pbcMeasureInfo.measureDeptName}</td>
						<td>${pbcMeasureInfo.currency}</td>
						<td>
							<fmt:formatNumber value="${pbcMeasureInfo.measureBalance}" pattern="￥0.0000" />	
						</td>
						<td>${pbcMeasureInfo.remark}</td>
						<shiro:hasPermission name="pbc:pbcFeedBackQueryDeal:edit"><td>
							<a href="${ctx}/pbc/pbcFeedBackQueryDealQuery/del?flg=${flg}&pbcId=${pbcMeasureInfo.pbcId}&applicationId=${pbcFeedBack.applicationId}">删除</a>
						</td></shiro:hasPermission>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>