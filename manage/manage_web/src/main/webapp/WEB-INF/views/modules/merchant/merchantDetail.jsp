<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>merchant管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		inputReadonly{
			border:none
		}
			
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/merchant/merchant?cache=1">商户列表</a></li>
		<li class="active"><a href="">商户查看</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="merchant"  class="form-horizontal">
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">商户编码：</label>
				<div class="controls">
				<input value="${merchant.userId}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">商户账号：</label>
				<div class="controls">
				<input value="${merchant.loginName}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">公司名称：</label>
				<div class="controls">
					<input value="${merchant.companyName}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-left">
				<label class="control-label">公司简称：</label>
				<div class="controls">
					<input value="${merchant.shortName}" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>

		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">联系人：</label>
				<div class="controls">
				<input value="${merchant.contactor}"  maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">商户标识：</label>
				<div class="controls">
					<c:if test="${merchant.merchantFlag == 'inside'}">
						<input value="内部商户" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
					</c:if>
					<c:if test="${merchant.merchantFlag == 'outsid'}">
						<input value="外部商户" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
					</c:if>
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">联系人手机号：</label>
				<div class="controls">
				<input value="${merchant.contactorPhone}" maxlength="100" readonly  style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">联系地址：</label>
				<div class="controls">
				<input value="${merchant.contactAddress}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
	<%-- 	<div class="control-group">
			<div class="control-left">
				<label class="control-label">合约签订日期：</label>
				<div class="controls">
				<input value="<fmt:formatDate value="${merchantAutographInfo.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">合同过期日期：</label>
				<div class="controls">
				<input value="<fmt:formatDate value="${merchantAutographInfo.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div> --%>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">商户MCC码：</label>
				<div class="controls">
				<input value="${merchant.industryCategory}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-left">
				<label class="control-label">平台会员行业分类：</label>
				<div class="controls">
				<div>${merchantIndustryBase.industryName}  ${merchantIndustryBase.industryChildName}  ${merchantIndustryBase.industryDescribe}</div>
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">留存金额：</label>
				<div class="controls">
				<input value="${merchant.retainedAmount}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				  
				</div>
			</div>
			<div class="control-left">
				<label class="control-label">维系员工：</label>
				<div class="controls">
				<input value="${merchant.inchargerId}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
	<%-- 	<div class="control-group">
			<div class="control-left">
				<label class="control-label">结算类型：</label>
				<div class="controls">
				<input value="T+${settleCycleManage.settleType}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-left">
				<label class="control-label">结算至：</label>
				<div class="controls">
				<input value="${settleCycleManage.settlementTo}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div> --%>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">创建时间：</label>
				<div class="controls">
				<input value="<fmt:formatDate value="${merchant.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-left">
				<label class="control-label">修改时间：</label>
				<div class="controls">
				<input value="<fmt:formatDate value="${merchant.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">中国银联商户号：</label>
				<div class="controls">
					<input id="merchantPosCode" name="merchantPosCode" value="${merchant.merchantPosCode}" readonly="true" style="border:0px;background-color:#fff;"/>
				</div>
			</div>
		</div>
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>产品名称</th>
				<th>计费类型</th>
				<th>手续费费用</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchantRate">
			<tr>
				<td>${merchantRate.productName}${merchantRate.bankCardType}</td>
				<td>${merchantRate.chargeType}</td>
				<td>
					<c:if test="${merchantRate.chargeType == '按比例'}">${merchantRate.chargeRatio}%</c:if>
					<c:if test="${merchantRate.chargeType == '按笔数'}">${merchantRate.chargeFee}元</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>