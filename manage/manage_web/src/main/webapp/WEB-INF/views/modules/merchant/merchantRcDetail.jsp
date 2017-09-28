<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<link href="${ctxStatic}/common/img.css" type="text/css" rel="stylesheet">
	<title>商家风控审核管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".pop_img span").on("click",function(e){
				e.stopPropagation();
				$(this).parent().hide();
    		});
    		
    		$(".popA").on("click",function(e){
    			e.stopPropagation();
    			$(".pop_img").hide();
    			$(this).siblings(".pop_img").show();
    		});
    		$(document).click(function(){
    		    $(".pop_img").hide();
    		});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/merchant/merchantRc/">风控审核列表</a></li>
		<li class="active"><a href="">风控审核详情</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="merchant"  class="form-horizontal">
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">商户编码：</label>
				<div class="controls">
				<input value="${merchant.userId}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">商户公司名称：</label>
				<div class="controls">
				<input value="${merchant.companyName}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
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
				<label class="control-label">公司简称：</label>
				<div class="controls">
					<input value="${merchant.shortName}" name="shortName" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">法人代表：</label>
				<div class="controls">
				<input value="${merchant.legalRepresentative}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">法人代表身份证号：</label>
				<div class="controls">
				<input value="${merchant.legalIdcard}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">法人代表证件有效期：</label>
				<div class="controls">
					<c:if test="${ merchant.legalCertificateEndTime=='Thu Nov 30 00:00:00 CST 2'}">长期</c:if><c:if test="${merchant.legalCertificateEndTime!='Thu Nov 30 00:00:00 CST 2'}"><fmt:formatDate value="${merchant.legalCertificateEndTime}" pattern="yyyy-MM-dd"/></c:if>
				</div>
			</div>
		</div>
		
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">法人代表证件照(正)：</label>
				<div class="controls">
					<a class="popA">图片</a>
					<p class="pop_img">
						<span>X</span>
						<img src="${merchant.legalCertificateFront}">
					</p>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">法人代表证件照(反)：</label>
				<div class="controls">
					<a class="popA">图片</a>
					<p class="pop_img">
						<span>X</span>
						<img src="${merchant.legalCertificateBack}">
					</p>
				</div>
			</div>
		</div>
		
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">单位类型：</label>
				<div class="controls">
				<input value="${merchant.type}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">证件类型：</label>
				<div class="controls">
				<input value="${merchant.certificateType}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">公司所在地：</label>
				<div class="controls">
				<input value="${merchant.provinceName}${merchant.cityName}${merchant.countyName}" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">详细地址：</label>
				<div class="controls">
				<input value="${merchant.businessAddress}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">公司网址：</label>
				<div class="controls">
				<input value="${merchant.website}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">ICP备案号：</label>
				<div class="controls">
				<input value="${merchant.ipcNo}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">营业执照号码：</label>
				<div class="controls">
				<input value="${merchant.businessLicenseNo}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">营业执照有效期：</label>
				<div class="controls">
					<c:if test="${ merchant.businessLicenseEndTime=='Thu Nov 30 00:00:00 CST 2'}">长期</c:if><c:if test="${merchant.businessLicenseEndTime!='Thu Nov 30 00:00:00 CST 2'}"><fmt:formatDate value="${merchant.businessLicenseEndTime}" pattern="yyyy-MM-dd"/></c:if>
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">组织机构代码：</label>
				<div class="controls">
				<input value="${merchant.organizationCode}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">税务登记证号码：</label>
				<div class="controls">
				<input value="${merchant.taxRegistrationCertificateNo}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">经营范围：</label>
				<div class="controls">
				<input value="${merchant.businessScope}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">联系人身份证号：</label>
				<div class="controls">
				<input value="${merchant.contactorIdcardNo}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">联系人：</label>
				<div class="controls">
				<input value="${merchant.contactor}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">联系人证件有效期：</label>
				<div class="controls">
					<c:if test="${ merchant.contactorCertificateEndTime=='Thu Nov 30 00:00:00 CST 2'}">长期</c:if><c:if test="${merchant.contactorCertificateEndTime!='Thu Nov 30 00:00:00 CST 2'}"><fmt:formatDate value="${merchant.contactorCertificateEndTime}" pattern="yyyy-MM-dd"/></c:if>
				</div>
			</div>
		</div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">联系人证件照(正)：</label>
				<div class="controls">
					<a class="popA">图片</a>
					<p class="pop_img">
						<span>X</span>
						<img src="${merchant.contactorCertificateFront}">
					</p>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">联系人证件照(反)：</label>
				<div class="controls">
					<a class="popA">图片</a>
					<p class="pop_img">
						<span>X</span>
						<img src="${merchant.contactorCertificateBack}">
					</p>
				</div>
			</div>
		</div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">联系人手机号：</label>
				<div class="controls">
				<input value="${merchant.contactorPhone}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<%-- <div class="control-right">
				<label class="control-label">商户等级：</label>
				<div class="controls">
				<input value="${merchant.level}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div> --%>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">营业执照照片：</label>
				<div class="controls">
					<a class="popA">图片</a>
					<p class="pop_img">
						<span>X</span>
						<img src="${merchant.businessLicenseFile}">
					</p>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">组织机构代码证照片：</label>
				<div class="controls">
					<a class="popA"><c:if test="${!empty merchant.organizationCodeCertificate}">图片</c:if></a>
					<p class="pop_img">
						<span>X</span>
						<img src="${merchant.organizationCodeCertificate}">
					</p>
				</div>
			</div>
		</div>
		
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">税务登记证照片：</label>
				<div class="controls">
					<a class="popA"><c:if test="${!empty merchant.taxRegistrationCertificate}">图片</c:if></a>
					<p class="pop_img">
						<span>X</span>
						<img src="${merchant.taxRegistrationCertificate}">
					</p>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">开户许可证照片：</label>
				<div class="controls">
					<a class="popA">图片</a>
					<p class="pop_img">
						<span>X</span>
						<img src="${merchant.permitsAccounts}">
					</p>
				</div>
			</div>
		</div>
		<div class="control-group">
            <div class="control-left">
                <label class="control-label">审核人：</label>
                <div class="controls">
                <input value="${merchant.rcAuditor}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">审核时间：</label>
                <div class="controls">
                    <fmt:formatDate value="${merchant.rcAuditTime}" pattern="yyyy-MM-dd"/>
                </div>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">审核状态：</label>
			<div class="controls">
			<input value="${merchant.rcAuditStatus}" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
			</div>
		</div>
		<c:if test="${merchant.rcAuditStatus=='审核不通过'}">
			<div class="control-group">
				<label class="control-label">拒绝理由：</label>
				<div class="controls">
					<input value="${merchant.objection}" readonly style="width:700px;border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</c:if>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>