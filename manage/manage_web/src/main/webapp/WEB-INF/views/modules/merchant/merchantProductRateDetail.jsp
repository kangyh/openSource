<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>费率管理</title>
<meta name="decorator" content="default" />
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/merchant/merchantProductRate/">产品配置列表</a></li>
		<li class="active"><a href="">费率详情查看</a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="merchantProductRate"
		class="form-horizontal">
		<div class="control-group">
			<label class="control-label">商户编码：</label>
			<div class="controls">
				<div>${merchantProductRate.merchantId}</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户公司名称：</label>
			<div class="controls">
				<div>${merchantProductRate.merchantCompanyName}</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户账号：</label>
			<div class="controls">
				<div>${merchantProductRate.merchantLoginName}</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品名称：</label>
			<div class="controls">
				<div>${merchantProductRate.productName}</div>
			</div>
		</div>
		<c:if test="${!empty merchantProductRate.isRefundable}">
			<div class="control-group">
				<label class="control-label">是否退还手续费：</label>
				<div class="controls">
					<div>${merchantProductRate.merchantProductRate}</div>
				</div>
			</div>
		</c:if>
		<c:if test="${empty merchantProductRate.isRefundable}">
		    <c:if test="${empty merchantRateBanks}">
		    <c:forEach items="${merchantRateBanks}" var="merchantRateBank">
		      <c:if test="${!empty merchantRateBanks.bankCardType}">
		          <div class="control-group">
                <label class="control-label">银行卡类型：</label>
                <div class="controls">
                    <div>${merchantRateBank.bankCardType}</div>
                </div>
            </div>
		      </c:if>
			<div class="control-group">
				<label class="control-label">计费类型：</label>
				<div class="controls">
					<div>${merchantRateBank.chargeType}</div>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">手续费费用：</label>
				<div class="controls">
					<div>${merchantRateBank.backUrl}</div>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">最大值(元)：</label>
				<div class="controls">
					<div>${merchantRateBank.autographType}</div>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">最小值(元)：</label>
				<div class="controls">
					<div>${merchantRateBank.autographKey}</div>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">操作人：</label>
				<div class="controls">
					<div>${merchantRateBank.endTime}</div>
				</div>
			</div>
			</c:forEach>
			</c:if>
		</c:if>
            <div class="control-group">
                <label class="control-label">审核状态：</label>
                <div class="controls">
                    <div>${merchantProductRate.status}</div>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">审核人：</label>
                <div class="controls">
                    <div>${merchantProductRate.status}</div>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">拒绝理由：</label>
                <div class="controls">
                    <div>${merchantProductRate.remark}</div>
                </div>
            </div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回"
				onclick="history.go(-1)" />
		</div>
	</form:form>
</body>
</html>