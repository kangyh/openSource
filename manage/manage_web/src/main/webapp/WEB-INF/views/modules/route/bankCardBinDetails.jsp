<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>单表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/route/bankCardBin/">银行卡bin列表</a></li>
		<li class="active"><a href="${ctx}/route/bankCardBin/form?id=${bankCardBin.id}">银行卡bin信息</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="bankCardBin" action="${ctx}/route/bankCardBin/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">发卡行名称：</label>
			<div class="controls">
					${bankCardBin.bankcardName }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发卡行标识：</label>
			<div class="controls">
				${bankCardBin.bankcardNote }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发卡行标识长度：</label>
			<div class="controls">
				${bankCardBin.bankcardNoteLength }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">卡号长度：</label>
			<div class="controls">
				${bankCardBin.bankcardLength }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">卡名称：</label>
			<div class="controls">
				${bankCardBin.cardName }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">卡类型：</label>
			<div class="controls">
					${bankCardBin.cardType }
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>