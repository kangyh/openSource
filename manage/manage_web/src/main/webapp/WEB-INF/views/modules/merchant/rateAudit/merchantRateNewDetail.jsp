<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品配置管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">

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
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        };
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/merchant/merchantProductRate/rateAudit?cache=1">费率详情列表</a></li>
		<li class="active"><a href="">费率审核</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="merchantProductRate" action="${ctx}/merchant/merchantProductRate/rateAudit/save/${id}" method="post" class="form-horizontal">

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">商户编码：</label>
				<div class="controls">
					<input value="${merchantProductRate.merchantId}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">商户公司名称：</label>
				<div class="controls">
					<input value="${merchantProductRate.merchantCompanyName}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				</div>
			</div>
		</div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">业务类型：</label>
				<div class="controls">
					<input value="${merchantProductRate.businessType}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">产品名称：</label>
				<div class="controls">
					<input id="productName" value="${merchantProductRate.productName}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				</div>
			</div>
		</div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">结算至：</label>
				<div class="controls">
					<input value="${merchantProductRate.settlementTo}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				</div>
			</div>
			<!-- 结算周期  -->
			<div class="control-right">
				<label class="control-label">结算类型：</label>
				<div class="controls">
					T+${merchantProductRate.settleType}
				</div>
			</div>
		</div>


		<div class="control-group">
			<!-- 费率 -->
			<div class="control-left">
				<label class="control-label">手续费扣除方式：</label>
				<div class="controls radio_inout">
					<input value="${merchantProductRate.chargeDeductType}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">手续费来源：</label>
				<div class="controls radio_inout">
					<input value="${merchantProductRate.chargeSource}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				</div>
			</div>
		</div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label ">手续费收取方式：</label>
				<div class="controls radio_inout">
					<input value="${merchantProductRate.chargeCollectionType}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">规则结束时间：</label>
				<div class="controls">
					<input id="ruleEndTime" type="text" readonly="readonly" style="border:0px;background-color:#fff;padding-top: 3px;"
						   value="<fmt:formatDate value="${merchantProductRate.ruleEndTime}" pattern="yyyy-MM-dd"/>" />
				</div>
			</div>
		</div>


		<div class="control-group">
			<div class="control-left">
				<label class="control-label">规则状态：</label>
				<div class="controls radio_inout">
					<input value="${merchantProductRate.status}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">规则开始时间：</label>
				<div class="controls">
					<input id="ruleBeginTime" type="text" readonly="readonly" style="border:0px;background-color:#fff;padding-top: 3px;"
						value="<fmt:formatDate value="${merchantProductRate.ruleBeginTime}" pattern="yyyy-MM-dd"/>" />
				</div>
			</div>
		</div>

		<%--<div class="control-group">
			<div class="control-left">
				<label class="control-label">异步通知地址：</label>
				<div class="controls">
					<input  value="${merchantProductRate.notifyUrl}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;width: 500px"/>
				</div>
			</div>
		</div>
		<div class="control-group">
			<!-- 技术签约 -->
			<div class="control-left">
				<label class="control-label">同步返回地址：</label>
				<div class="controls">
					<input value="${merchantProductRate.backUrl}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;width: 500px"/>
				</div>
			</div>
		</div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">ip/域名：</label>
				<div class="controls">
					<input value="${merchantProductRate.ipDomain}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;width: 500px"/>
				</div>
			</div>
		</div>--%>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">签名方式：</label>
				<div class="controls">
					<input value="${merchantProductRate.autographType}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">退款时是否退还手续费：</label>
				<div class="controls radio_inout">
					<input value="${merchantProductRate.isRefundable}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
				</div>
			</div>
		</div>
		<c:if test="${empty rateAuditValue}">
			<div class="control-group">
				<label class="control-label">费率审核：</label>
				<div class="controls">
					<form:select id="rateAudit" path="rateAudit" style="width:225px;" class="required">
						<form:option value="" label="-选择状态-"/>
						<c:forEach items="${rateAudit}" var="rateAuditFor">
							<option value="${rateAuditFor.value}" />${rateAuditFor.name}
						</c:forEach>
					</form:select>
					<span class="help-inline"><font color="red">*</font></span>
				</div>
			</div>
		</c:if>

		<div class="form-actions">
			<c:if test="${empty rateAuditValue}">
				<input id="btnSubmit" class="btn btn-primary" type="submit" onclick="return confirmation()" value="保 存"/>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location='${ctx}/merchant/merchantProductRate/rateAudit?cache=1'"/>
		</div>
	</form:form>

	<form id="searchForm" action="${ctx}/merchant/merchantProductRate/rateAudit/jump/${id}?rateAuditValue=${rateAuditValue}" method="post">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div style="font-size: 2em;font-family: serif;padding-bottom: 15px;padding-top: 8px;">商户费率详情</div>
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>产品名称</th>
					<th>银行名称</th>
					<th>银行卡类型</th>
					<th>计费类型</th>
					<th>手续费费用</th>
					<th>手续费费用2</th>
					<th>手续费费用3</th>
					<th>手续费最大值</th>
					<th>手续费最小值</th>
					<th>配置人</th>
				</tr>
			</thead>
			<tbody class="record_list">
			<c:forEach items="${page.list}" var="merchantRateNew" varStatus="status">
				<tr>
					<td>${merchantRateNew.productName}</td>
					<td>${merchantRateNew.bankName}</td>
					<td>${merchantRateNew.bankCardType}</td>
					<td>${merchantRateNew.chargeType}</td>
					<td>
						<c:if test="${!empty merchantRateNew.chargeMin}">
							区间${merchantRateNew.chargeMin}~${merchantRateNew.chargeMax};手续费:
						</c:if>
						<c:if test="${merchantRateNew.chargeType == '按比例'}">
							${merchantRateNew.chargeRatio}%
						</c:if>
						<c:if test="${merchantRateNew.chargeType == '按笔数'}">
							${merchantRateNew.chargeFee}元
						</c:if>
					</td>
					<td>
						<c:if test="${!empty merchantRateNew.chargeMin2}">
							区间${merchantRateNew.chargeMin2}~${merchantRateNew.chargeMax2};手续费:
							<c:if test="${merchantRateNew.chargeType == '按比例'}">
								${merchantRateNew.chargeRatio2}%
							</c:if>
							<c:if test="${merchantRateNew.chargeType == '按笔数'}">
								${merchantRateNew.chargeFee2}元
							</c:if>
						</c:if>
					</td>
					<td>
						<c:if test="${!empty merchantRateNew.chargeMin3}">
							区间${merchantRateNew.chargeMin3}~${merchantRateNew.chargeMax3};手续费:
							<c:if test="${merchantRateNew.chargeType == '按比例'}">
								${merchantRateNew.chargeRatio3}%
							</c:if>
							<c:if test="${merchantRateNew.chargeType == '按笔数'}">
								${merchantRateNew.chargeFee3}元
							</c:if>
						</c:if>
					</td>
					<td>${merchantRateNew.maxFee}</td>
					<td>${merchantRateNew.minFee}</td>
					<td>${fns:getUserById(merchantRateNew.updateBy.id).name}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<div class="pagination">${page}</div>
	</form>
</body>
</html>