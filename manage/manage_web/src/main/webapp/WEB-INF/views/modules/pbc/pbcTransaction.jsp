<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>电信诈骗风险管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	function toHome(){
		window.location="${ctx}/pbc/reportInformation/";
	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a>账户主体详情</a></li>
	</ul>
	<br />
	<form:form id="inputForm" action="" method="post" class="form-horizontal">
		<div style="font-size: 2em;font-family: serif;font: bold;padding-bottom: 20px;padding-top: 20px;">账户信息:</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">账户类型：</label>
				<div class="controls">
					<input value="${pbcTransQuery.txCode}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">账户名：</label>
				<div class="controls">
					<input value="${pbcTransQuery.transSerialNumber}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-right">
				<label class="control-label">户主姓名：</label>
				<div class="controls">
					<input value="${pbcTransQuery.applicationId}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">户主证件类型：</label>
				<div class="controls">
					<input value="${pbcTransQuery.caseNumber}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-right">
				<label class="control-label">户主证件号：</label>
				<div class="controls" style="width: 205px;">
					<input value="${pbcTransQuery.caseTpye}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">商户证照类型代码：</label>
				<div class="controls">
					<input value="${pbcTransQuery.emergencyLevel}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-right">
				<label class="control-label">商户证照号码：</label>
				<div class="controls" style="width: 205px;">
					<input value="${pbcTransQuery.caseTpye}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">商户主体名称：</label>
				<div class="controls">
					<input value="${pbcTransQuery.emergencyLevel}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-right">
				<label class="control-label">法人代表：</label>
				<div class="controls" style="width: 205px;">
					<input value="${pbcTransQuery.caseTpye}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">法人代表证件类型：</label>
				<div class="controls">
					<input value="${pbcTransQuery.emergencyLevel}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-right">
				<label class="control-label">法人代表证件号码：</label>
				<div class="controls" style="width: 205px;">
					<input value="${pbcTransQuery.caseTpye}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">法人联系手机：</label>
				<div class="controls">
					<input value="${pbcTransQuery.emergencyLevel}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
	</form:form>
	<div style="font-size: 2em;font-family: serif;font: bold;padding-bottom: 20px;padding-top: 20px;">银行卡信息:</div>
	<br />
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>序号</th>
			<th>银行卡所属银行机构编码</th>
			<th>银行卡所属银行名称</th>
			<th>银行卡卡号</th>
		</tr>
		</thead>
		<tbody>
				<tr>
					<td>${1}</td>
					<td>${2}</td>
					<td>${3}</td>
					<td>${4}</td>
				</tr>
		</tbody>
	</table>
	<div class="form-actions">
			<input id="btnCancel" class="btn btn-primary" type="button" value="返 回" onclick="toHome()" />
	</div>
</body>
</html>