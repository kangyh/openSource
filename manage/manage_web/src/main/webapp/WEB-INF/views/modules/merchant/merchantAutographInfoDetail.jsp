<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>技术签约管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/merchant/merchantAutographInfo/">技术签约列表</a></li>
		<li class="active"><a href="">技术签约<shiro:hasPermission name="merchant:merchantAutographInfo:edit">${not empty merchantAutographInfo.id?'详情':'添加'}</shiro:hasPermission><shiro:lacksPermission name="merchant:merchantAutographInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="merchantAutographInfo"  class="form-horizontal">
		<div class="control-group">
			<label class="control-label">商户编码：</label>
			<div class="controls">
			<div>${merchantAutographInfo.merchantId}</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户公司名称：</label>
			<div class="controls">
			<div>${merchantAutographInfo.merchantCompanyName}</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品名称：</label>
			<div class="controls">
			<div>${merchantAutographInfo.productName}</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">异步通知地址：</label>
			<div class="controls">
			<div>${merchantAutographInfo.notifyUrl}</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同步返回地址：</label>
			<div class="controls">
			<div>${merchantAutographInfo.backUrl}</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">签名方式：</label>
			<div class="controls">
			<div>${merchantAutographInfo.autographType}</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">签名key：</label>
			<div class="controls">
			<div>${merchantAutographInfo.autographKey}</div>
			</div>
		</div>
		<div class="control-group" style="display:none;">
			<label class="control-label">允许的接口类型：</label>
			<div class="controls">
			<div>${merchantAutographInfo.allowInterfaceTypes}</div>
			</div>
		</div>
		<div class="control-group" style="display:none;">
			<label class="control-label">合同开始时间：</label>
			<div class="controls">
			<div><fmt:formatDate value="${merchantAutographInfo.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
			</div>
		</div>
		<div class="control-group" style="display:none;">
			<label class="control-label">合同结束时间：</label>
			<div class="controls">
			<div><fmt:formatDate value="${merchantAutographInfo.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<div>${merchantAutographInfo.status}</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
			<div>${merchantAutographInfo.remark}</div>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>