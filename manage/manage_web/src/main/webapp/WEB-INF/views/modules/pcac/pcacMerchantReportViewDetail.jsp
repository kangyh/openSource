<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>企业商户信息列表</title>
<meta name="decorator" content="default" />
<style type="text/css">
.commonWidth{
	width:100px;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		
	});
function check() {
	var opStatusNote = $("#opStatusNote").val();
	var opStatus = $("input:radio[name='opStatus']:checked").val();
	if(opStatusNote==null || opStatusNote==""){
		alertx("请填写备注");
		return false;
	}else if(opStatus==undefined || opStatus == null || opStatus == "" ){
		alertx("请选择审核状态");
		return false;
	}else{
		var msg = null;
		if(opStatus=='02'){
			msg="确定审核通过？";
		}else{
			msg="确定审核拒绝？";
		}
		if(confirm(msg)){
			$("#inputForm").submit();
			return ;
		}
	}
}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/pcac/pcacMerchantReport/view">企业商户信息上报</a></li>
		<li class="active"><a>详情</a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="pcacMerchantReport" action="${ctx}/pcac/pcacMerchantReport/view/syndicOne/" method="post" class="form-horizontal">
		
		
		<div style="font-size: 2em;font-family: serif;font: bold;padding-bottom: 20px;padding-top: 20px;">企业商户信息详情:</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label" >商户类型：</label>
				<div class="controls  commonWidth">
				<c:forEach items="${enumMap.cusType}" var="wStatus">
					<c:if test="${pcacMerchantReport.cusType eq wStatus.value}">
						${wStatus.name}
					</c:if>
				</c:forEach>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">商户属性：</label>
				<div class="controls">
				<c:forEach items="${enumMap.cusNature}" var="wStatus">
					<c:if test="${pcacMerchantReport.cusNature eq wStatus.value}">
						${wStatus.name}
					</c:if>
				</c:forEach>
				</div>
			</div>

		</div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">商户名称：</label>
				<div class="controls  commonWidth">${pcacMerchantReport.regName}</div>
			</div>
			<div class="control-right">
				<label class="control-label">商户简称：</label>
				<div class="controls">${pcacMerchantReport.cusName}</div>
			</div>
		</div>


		<div class="control-group">

			<div class="control-left">
				<label class="control-label">商户英文名称：</label>
				<div class="controls  commonWidth">${pcacMerchantReport.cusNameEn}</div>
			</div>
			<div class="control-right">
				<label class="control-label">法人证件类型：</label>
				<c:forEach items="${enumMap.docType}" var="wStatus">
					<c:if test="${pcacMerchantReport.docType eq wStatus.value}">
						${wStatus.name}
					</c:if>
				</c:forEach>
				</div>
			</div>
		</div>
		<div class="control-group">

			<div class="control-left">
				<label class="control-label">法人证件号码：</label>
				<div class="controls  commonWidth">${pcacMerchantReport.docCode}</div>
			</div>
			<div class="control-right">
				<label class="control-label">法定代表人姓名：</label>
				<div class="controls">${pcacMerchantReport.legDocName}</div>
			</div>
		</div>
		<div class="control-group">

			<div class="control-left">
				<label class="control-label">法定代表人证件类型：</label>
				<div class="controls  commonWidth">
				<c:forEach items="${enumMap.legDocType}" var="wStatus">
					<c:if test="${pcacMerchantReport.legDocType eq wStatus.value}">
						${wStatus.name}
					</c:if>
				</c:forEach>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">法定代表人（负责人）证件号码：</label>
				<div class="controls">${pcacMerchantReport.legDocCode}</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">证件号码：</label>
				<div class="controls  commonWidth">${pcacMerchantReport.docCode}</div>
			</div>
			<div class="control-right">
				<label class="control-label">商户代码：</label>
				<div class="controls">${pcacMerchantReport.cusCode}</div>
			</div>

		</div>


		<div class="control-group">
			<div class="control-left">
				<label class="control-label">商户行业类别：</label>
				<div class="controls  commonWidth">${pcacMerchantReport.induType}</div>
			</div>
			<div class="control-right">
				<label class="control-label">收款账\卡号：</label>
				<div class="controls">${pcacMerchantReport.bankNo}</div>
			</div>
		</div>



		<div class="control-group">


			<div class="control-left">
				<label class="control-label">收款账\卡号开户行：</label>
				<div class="controls  commonWidth">${pcacMerchantReport.openBank}</div>
			</div>
			<div class="control-right">
				<label class="control-label">商户注册地址(省)：</label>
				<div class="controls">
				<c:forEach items="${enumMap.pcacProvince}" var="wStatus">
					<c:if test="${pcacMerchantReport.regAddrProv eq wStatus.value}">
						${wStatus.name}
					</c:if>
				</c:forEach>
				</div>
			</div>
		</div>
		<div class="control-group">

			<div class="control-left">
				<label class="control-label">商户注册地址：</label>
				<div class="controls  commonWidth">${pcacMerchantReport.regAddrDetail}</div>
			</div>
			<div class="control-right">
				<label class="control-label">商户经营地址(省)：</label>
				<div class="controls">
				<c:forEach items="${enumMap.pcacProvince}" var="wStatus">
					<c:if test="${pcacMerchantReport.addrProv eq wStatus.value}">
						${wStatus.name}
					</c:if>
				</c:forEach>
				</div>
			</div>

		</div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">商户经营地址：</label>
				<div class="controls  commonWidth">${pcacMerchantReport.addrDetail}</div>
			</div>
			<div class="control-right">
				<label class="control-label">网址：</label>
				<div class="controls">${pcacMerchantReport.url}</div>
			</div>
		</div>


		<div class="control-group">
			<div class="control-left">
				<label class="control-label">服务器IP：</label>
				<div class="controls  commonWidth">${pcacMerchantReport.serverIp}</div>
			</div>
			<div class="control-right">
				<label class="control-label">ICP备案编号：</label>
				<div class="controls">${pcacMerchantReport.icp}</div>
			</div>
		</div>

		<div class="control-group">

			<div class="control-left">
				<label class="control-label">商户联系人：</label>
				<div class="controls  commonWidth">${pcacMerchantReport.contName}</div>
			</div>
			<div class="control-right">
				<label class="control-label">商户联系电话：</label>
				<div class="controls">${pcacMerchantReport.contPhone}</div>
			</div>

		</div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">商户E-mail：</label>
				<div class="controls  commonWidth">${pcacMerchantReport.cusEmail}</div>
			</div>
			<div class="control-right">
				<label class="control-label">商户经营地区范围：</label>
				<div class="controls">${pcacMerchantReport.occurarea}</div>
			</div>
		</div>

		<div class="control-group">

			<div class="control-left">
				<label class="control-label">清算网络：</label>
				<div class="controls  commonWidth">
				<c:forEach items="${enumMap.networkType}" var="wStatus">
					<c:if test="${pcacMerchantReport.networkType eq wStatus.value}">
						${wStatus.name}
					</c:if>
				</c:forEach>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">商户状态：</label>
				<div class="controls">
				<c:forEach items="${enumMap.pcacCusStatus}" var="wStatus">
					<c:if test="${pcacMerchantReport.status eq wStatus.value}">
						${wStatus.name}
					</c:if>
				</c:forEach>
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">服务起始时间：</label>
				<div class="controls  commonWidth">${pcacMerchantReport.startTime}</div>
			</div>
			<div class="control-right">
				<label class="control-label">服务终止时间：</label>
				<div class="controls">${pcacMerchantReport.endTime}</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">合规风险状况：</label>
				<div class="controls  commonWidth">
				<c:forEach items="${enumMap.riskStatus}" var="wStatus">
					<c:if test="${pcacMerchantReport.riskStatus eq wStatus.value}">
						${wStatus.name}
					</c:if>
				</c:forEach>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">开通业务种类：</label>
				<div class="controls">
				<c:forEach items="${enumMap.openType}" var="wStatus">
					<c:if test="${pcacMerchantReport.openType eq wStatus.value}">
						${wStatus.name}
					</c:if>
				</c:forEach>
				</div>
			</div>
		</div>

		<div class="control-group">

			<div class="control-left">
				<label class="control-label">计费类型：</label>
				<div class="controls  commonWidth">
				<c:forEach items="${enumMap.chageType}" var="wStatus">
					<c:if test="${pcacMerchantReport.chageType eq wStatus.value}">
						${wStatus.name}
					</c:if>
				</c:forEach>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">支持账户类型：</label>
				<div class="controls">
				<c:forEach items="${enumMap.accountType}" var="wStatus">
					<c:if test="${pcacMerchantReport.accountType eq wStatus.value}">
						${wStatus.name}
					</c:if>
				</c:forEach>
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">拓展方式：</label>
				<div class="controls  commonWidth">
				<c:forEach items="${enumMap.expandType}" var="wStatus">
					<c:if test="${pcacMerchantReport.expandType eq wStatus.value}">
						${wStatus.name}
					</c:if>
				</c:forEach>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">外包服务机构名称：</label>
				<div class="controls">${pcacMerchantReport.outServiceName}</div>
			</div>

		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">外包服务机构法人证件类型：</label>
				<div class="controls  commonWidth">
				<c:forEach items="${enumMap.docType}" var="wStatus">
					<c:if test="${pcacMerchantReport.outServiceCardType eq wStatus.value}">
						${wStatus.name}
					</c:if>
				</c:forEach>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">外包服务机构法人证件号码：</label>
				<div class="controls">${pcacMerchantReport.outServiceCardCode}</div>
			</div>
		</div>
		<div class="control-group">

			<div class="control-left">
				<label class="control-label">外包服务机构法定代表人证件类型：</label>
				<div class="controls  commonWidth">
				<c:forEach items="${enumMap.legDocType}" var="wStatus">
						<c:if test="${pcacMerchantReport.outServicelegCardType eq wStatus.value}">
							${wStatus.name}
						</c:if>
				</c:forEach>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">外包服务机构法定代表人证件号码：</label>
				<div class="controls">
					${pcacMerchantReport.outServicelegCardCode}</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">申请人：</label>
				<div class="controls  commonWidth">${pcacMerchantReport.filler}</div>
			</div>
			<div class="control-right">
				<label class="control-label">申请时间：</label>
				<div class="controls">
				<fmt:formatDate value="${pcacMerchantReport.fillerTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				</div>
			</div>

		</div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">录入人：</label>
				<div class="controls  commonWidth">${pcacMerchantReport.uploader}</div>
			</div>
			<div class="control-right">
				<label class="control-label">录入时间：</label>
				<div class="controls">
				<fmt:formatDate value="${pcacMerchantReport.uploadTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				</div>
			</div>
		</div>



		<c:if test="${pcacMerchantReport.opStatus eq '02' or pcacPersonReport.opStatus eq '03'  }">
			<div class="control-group">
				<div class="control-left">
					<label class="control-label">审核时间：</label>
					<div class="controls  commonWidth">
					<fmt:formatDate value="${pcacMerchantReport.reviewTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
					</div>
				</div>
				<div class="control-right">
					<label class="control-label">审核人：</label>
					<div class="controls">${pcacMerchantReport.reviewer}</div>
				</div>

			</div>
			<div class="control-group">
				<div class="control-left">
					<label class="control-label">审核状态：</label>
					<div class="controls  commonWidth">
					<c:forEach items="${enumMap.opStatus}" var="wStatus">
						<c:if test="${pcacMerchantReport.opStatus eq wStatus.value}">
							${wStatus.name}
						</c:if>
					</c:forEach>
					</div>
				</div>
				<div class="control-right">
					<label class="control-label">审核备注：</label>
					<div class="controls">${pcacMerchantReport.opStatusNote}</div>
				</div>
			</div>
		</c:if>
		<c:if
			test="${pcacMerchantReport.opStatus eq '04' or  pcacMerchantReport.opStatus eq '05' or pcacMerchantReport.opStatus eq '06'}">
			<div class="control-group">
				<div class="control-left">
					<label class="control-label">审核时间：</label>
					<div class="controls  commonWidth">
					<fmt:formatDate value="${pcacMerchantReport.reviewTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
					</div>
				</div>
				<div class="control-right">
					<label class="control-label">审核人：</label>
					<div class="controls">${pcacMerchantReport.reviewer}</div>
				</div>

			</div>
			<div class="control-group">
				<div class="control-left">
					<label class="control-label">审核状态：</label>
					<div class="controls  commonWidth">
					<c:forEach items="${enumMap.opStatus}" var="wStatus">
						<c:if test="${pcacMerchantReport.opStatus eq wStatus.value}">
							${wStatus.name}
						</c:if>
					</c:forEach>
					</div>
				</div>
				<div class="control-right">
					<label class="control-label">审核备注：</label>
					<div class="controls">${pcacMerchantReport.opStatusNote}</div>
				</div>
			</div>
			<c:if test="${ pcacMerchantReport.opStatus eq '04' or pcacMerchantReport.opStatus eq '05'}">
			<div class="control-group">
				<div class="control-left">
					<label class="control-label">所属批次上报时间：</label>
					<div class="controls  commonWidth">
					<fmt:formatDate value="${pcacMerchantReport.repDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
					</div>
				</div>
				<div class="control-right">
					<label class="control-label">所属批次上报人：</label>
					<div class="controls">${pcacMerchantReport.repPerson}</div>
				</div>
			</div>
			<div class="control-group">
				<div class="control-left">
					<label class="control-label">所属批次上报状态：</label>
					<div class="controls  commonWidth">
					<c:forEach items="${enumMap.opStatus}" var="wStatus">
						<c:if test="${pcacMerchantReport.opStatus eq wStatus.value}">
							${wStatus.name}
						</c:if>
					</c:forEach>
					</div>
				</div>
				<c:if test="${ pcacMerchantReport.opStatus eq '05' }">
				<div class="control-right">
					<label class="control-label">所属批次上报失败原因：</label>
					<div class="controls"></div>
				</div>
				</c:if>
			</div>
			</c:if>
		</c:if>
		<input id="merchantReportId" name="merchantReportId" value="${pcacMerchantReport.merchantReportId }" type="hidden"/>
		<%-- <c:if test="${pcacMerchantReport.opStatus eq '01'  and type eq 'handle'}">
		    审核备注<input name="opStatusNote" id="opStatusNote" />
		    <input type="radio" name="opStatus" value="02"/>审核通过
		    <input type="radio" name="opStatus" value="03"/>审核拒绝
		    </c:if> --%>
			<div class="form-actions">
			<%-- <c:if test="${pcacMerchantReport.opStatus eq '01'  and type eq 'handle'}">
			<input id="btnSubmit" class="btn btn-primary" type="button" onclick="check()"  value="保存"/> 
			</c:if> --%>
			<c:if test="${fromWhere eq 'batchDetail' }">
					<input id="btnCancel" class="btn btn-primary" type="button" value="返 回" onclick="history.go(-2)" />
				</c:if>
				<c:if test="${fromWhere ne 'batchDetail' }">
					<input id="btnCancel" class="btn btn-primary" type="button" value="返 回" onclick="history.go(-1)" />
				</c:if>
			</div>
		
		<%-- <c:if test="${ type eq 'view'  }">
			<div class="form-actions">
			<c:if test="${fromWhere eq 'batchDetail' }">
					<input id="btnCancel" class="btn btn-primary" type="button" value="返 回" onclick="history.go(-2)" />
				</c:if>
				<c:if test="${fromWhere ne 'batchDetail' }">
					<input id="btnCancel" class="btn btn-primary" type="button" value="返 回" onclick="history.go(-1)" />
				</c:if>
			</div>
		</c:if> --%>
		
	</form:form>
	<div id="messageBox" style="font-size: 15px; color: red;"></div>
</body>
</html>