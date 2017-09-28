<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>对账管理</title>
<meta name="decorator" content="default"/>
<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="#">对账批次查询详情页面</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="settleChannelLog"  method="post" class="form-horizontal">
	<div class="control-group">
		<label class="control-label">通道合作方：</label>
		<div class="controls">
			<input name="channelName" value="${settleChannelLog.channelName}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">支付通道类型：</label>
		<div class="controls">
			<input value="${settleChannelLog.channelTypeName}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">通道对账日期：</label>
		 <div class="controls">
			<fmt:formatDate value="${settleChannelLog.operEndTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
	</div> 
	<div class="control-group">
		<label class="control-label">对账批次号：</label>
		<div class="controls">
			<input path="differType"  value="${settleChannelLog.checkBathNo}" readOnly="true"style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">入款总笔数：</label>
		<div class="controls">
			<input path="transNo" id="transNo" value="${settleChannelLog.inRecordNum}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">入款总金额：</label>
		<div class="controls">
			<fmt:formatNumber value="${settleChannelLog.inTotalAmount}" pattern="￥0.0000" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">出款总笔数：</label>
		<div class="controls">
			<input id="outRecordNum" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="readonly" value="${settleChannelLog.outRecordNum}" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">出款总金额：</label>
		<div class="controls">
			<fmt:formatNumber value="${settleChannelLog.outTotalAmount}" pattern="￥0.0000" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">差异总笔数：</label>
		<div class="controls">
			<input id="errorRecordNum" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="readonly" value="${settleChannelLog.errorRecordNum}"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">差异总金额：</label>
		<div class="controls">
		<fmt:formatNumber value="${settleChannelLog.errorTotalAmount}" pattern="￥0.0000" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">总笔数：</label>
		<div class="controls">
			<input id="recordNum" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="readonly" value="${settleChannelLog.recordNum}"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">总金额：</label>
		<div class="controls">
		<fmt:formatNumber value="${settleChannelLog.totalAmount}" pattern="￥0.0000" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">对账状态：</label>
		<div class="controls">
			<input id="checkStatus" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="readonly" value="${settleChannelLog.checkStatus}"/>
		</div>
	</div>
	
	<div class="control-group">
		<label class="control-label">文件来源：</label>
		<div class="controls">
			<input id="checkFileFrom" style="border:0px;background-color:#fff;padding-top: 3px;width:100%;" readonly="readonly" value="${settleChannelLog.checkFileFrom}"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">存储位置：</label>
		<div class="controls">
		<input id="checkFileWhere" style="border:0px;background-color:#fff;padding-top: 3px;width:100%;" readonly="readonly" value="${settleChannelLog.checkFileWhere}"/>
		</div>
	</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="取 消" onclick="history.go(-1)"/>
		</div>
</form:form>
</body>
</html>