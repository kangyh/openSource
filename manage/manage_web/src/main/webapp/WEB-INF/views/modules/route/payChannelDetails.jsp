<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			if ($("#settleT").val() == "实时"){
				  $("#settlePeriod").hide();
			}
			if ($("#costT").val() == "按比例"){
				$("#costCount").hide();
		    }else{
		    	$("#costRate").hide();
		    }
			if ($("#chargeDeductT").val() == "外扣"){
				$("#chargeReturnTag").hide();
			}
//			$("#merchantNoDiv").hide();
			if ($("#channelT").val() == "批付"){
				$("#accountDiv").show();
				$("#businessDiv").show();
			}else{
				/*if($("#channelT").val() == "B2C网银"||$("#channelT").val() == "微信"||$("#channelT").val() == "支付宝"||$("#channelT").val() == "B2B网银"||$("#channelT").val() == "二维码"||$("#channelT").val() == "公众号"||$("#channelT").val() == "H5"){
					$("#merchantNoDiv").show();
				}*/
				$("#accountDiv").hide();
				$("#businessDiv").hide();
			}
			if ($("#monlimitA").val() ==""){
				$("#monlimitTag").hide();
			}
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/route/payChannel/">支付通道列表</a></li>
		<li class="active"><a href="${ctx}/route/payChannel/details?id=${payChannel.id}">支付通道信息</a></li>
	</ul><br/>
	<form:form  modelAttribute="payChannel"  method="post" class="form-horizontal">
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">支付通道名称：</label>
			<div class="controls">
				${payChannel.channelName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付通道代码：</label>
			<div class="controls">
				${payChannel.channelCode}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行名称：</label>
			<div class="controls">
				${payChannel.bankName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通道合作方：</label>
			<div class="controls">
				${payChannel.channelPartnerName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属主体：</label>
			<div class="controls">
				${payChannel.merchantSubject}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付通道类型：</label>
			<div class="controls">
				${payChannel.channelTypeName}
			</div>
			<input type="hidden" name="channelT" id="channelT" class="input-xlarge required" value="${payChannel.channelTypeName}">
		</div>
		<div id="merchantNoDiv" class="control-group">
			<label class="control-label">通道侧商户号：</label>
			<div class="controls">
				${payChannel.merchantNumber}
			</div>
		</div>
		<div id="cardTypeDiv" class="control-group">
			<label class="control-label">银行卡类型：</label>
			<div class="controls">
				${payChannel.cardTypeName}
			</div>
			<input type="hidden" name="cardT" class="input-xlarge required" value="${payChannel.cardTypeName}">
		</div>
		<div class="control-group">
			<label class="control-label">渠道标识：</label>
			<div class="controls">
				${payChannel.channelTag}
			</div>
		</div>
		<div id="accountDiv" class="control-group" >
			<label class="control-label">对公对私标识：</label>
			<div class="controls">
				${payChannel.accountType}
			</div>
		</div>
		<div id="businessDiv" class="control-group" >
			<label class="control-label">付款类型：</label>
			<div class="controls">
				${payChannel.businessType}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续费扣除方式：</label>
			<div class="controls">
				${payChannel.chargeDeductType}
			</div>
			<input type="hidden" name="chargeDeductT" id="chargeDeductT" class="input-xlarge required" value="${payChannel.chargeDeductType}">
		</div>
		<div  id="chargeReturnTag" class="control-group">
			<label class="control-label">是否退还手续费：</label>
			<div class="controls">
				<c:if test="${ payChannel.chargeReturnTag=='0'}">否</c:if><c:if test="${payChannel.chargeReturnTag=='1'}">是</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通道结算周期：</label>
			<div class="controls">
				<c:if test="${payChannel.orderSettlePeriod=='0'}">T+0</c:if><c:if test="${payChannel.orderSettlePeriod=='1'}">T+1</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">有效开始时间：</label>
			<div class="controls">
				<fmt:formatDate value="${payChannel.effectStartDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">有效结束时间：</label>
			<div class="controls">
				<fmt:formatDate value="${payChannel.effectEndDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成本类型：</label>
			<div class="controls">
				${payChannel.costType}
			</div>
			<input type="hidden" name="costT" id="costT" class="input-xlarge required" value="${payChannel.costType}">
		</div>
		<div class="control-group">
			<label class="control-label">成本：</label>
		    <div id="costCount" class="controls">
		    	${payChannel.costCount}元
			</div>
		    <div id="costRate" class="controls">
		    	${payChannel.costRate}‰
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续费结算类型：</label>
			<div class="controls">
				${payChannel.settleType}
			</div>
			<input type="hidden" name="settleT" id="settleT" class="input-xlarge required" value="${payChannel.settleType}">
		</div>
		<div id="settlePeriod" class="control-group" >
			<label class="control-label">手续费结算周期：</label>
			<div class="controls">
				${payChannel.settlePeriod}
			</div>
		</div>
		<div class="control-group" style="display:none">
			<label class="control-label">合约时间：</label>
			<div class="controls">
				<fmt:formatDate value="${payChannel.contractDate}" pattern="yyyy-MM-dd "/>
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">优先级别：</label>
			<div class="controls">
				<c:if test="${empty payChannel.sort}">无</c:if><c:if test="${payChannel.sort=='1'}">默认</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单笔限额：</label>
			<div class="controls">
				${payChannel.perlimitAmount}元
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单日限额：</label>
			<div class="controls">
				${payChannel.daylimitAmount}元
			</div>
		</div>
		<div id="monlimitTag" class="control-group">
			<label class="control-label">单月限额：</label>
			<div class="controls">
				${payChannel.monlimitAmount}元
			</div>
			<input type="hidden" name="monlimitA" id="monlimitA" class="input-xlarge " value="${payChannel.monlimitAmount}">
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				${payChannel.status}
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>