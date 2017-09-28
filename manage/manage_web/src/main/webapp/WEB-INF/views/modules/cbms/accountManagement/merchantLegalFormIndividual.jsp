<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<link href="${ctxStatic}/common/img.css" type="text/css" rel="stylesheet">
	<title>商家法务审核管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
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
		function pass(){
			if($("#objection").val() != ""){
				parent.showThree();
				parent.changeThreeName("通过时拒绝理由应为空");
				return false;
			}
			$("#legalAuditStatus").val("SUCCES");
			$("#rcAuditStatus").val("AUDING");
		}
		function reject(){
			if($("#objection").val() == ""){
				parent.showThree();
				parent.changeThreeName("拒绝理由不能为空");
				return false;
			}
			$("#legalAuditStatus").val("AUDREJ");
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/cbms/merchant/merchantLegal?cache=1">法务审核列表</a></li>
		<li class="active"><a href="${ctx}/cbms/merchant/merchantLegal/form?id=${merchant.userId}">法务审核</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="merchant" action="${ctx}/cbms/merchant/merchantLegal/status" method="post" class="form-horizontal">
		<form:hidden path="userId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">商户编码：</label>
				<div class="controls">
					<input name="userId" value="${merchant.userId}" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="true"/>
				</div>
			</div>
            <div class="control-right">
                <label class="control-label">商户账号：</label>
                <div class="controls">
                    <input name="email" value="${merchant.email}" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="true"/>
                </div>
            </div>
		</div>
		
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">商户公司名称：</label>
				<div class="controls">
					<input name="companyName" value="${merchant.companyName}" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="true"/>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">法人代表：</label>
				<div class="controls">
					<input name="legalRepresentative" value="${merchant.legalRepresentative}" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="true"/>
				</div>
			</div>
		</div>
		
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">法人代表身份证号：</label>
				<div class="controls">
					<input name="legalIdcard" value="${merchant.legalIdcard}" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="true"/>
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
				<label class="control-label">商户类型：</label>
				<div class="controls">
					<input name="type" value="${merchant.type}" htmlEscape="false"  style="border:0px;background-color:#fff;padding-top: 3px;" readonly="true"/>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">证件类型：</label>
				<div class="controls">
					<input name="certificateType" value="${merchant.certificateType}" htmlEscape="false" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="true" value="个体工商户"/>
				</div>
			</div>
		</div>
		
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">公司所在地：</label>
				<div class="controls">
					<input value="${merchant.provinceName}${merchant.cityName}${merchant.countyName}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">详细地址：</label>
				<div class="controls">
					<input name="businessAddress" value="${merchant.businessAddress}" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="true"/>
				</div>
			</div>
		</div>
		
		
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">公司网址：</label>
				<div class="controls">
					<input name="website" value="${merchant.website}" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="true"/>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">ICP备案号：</label>
				<div class="controls">
					<input name="ipcNo" value="${merchant.ipcNo}" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="true"/>
				</div>
			</div>
		</div>
		
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">营业执照号码：</label>
				<div class="controls">
					<input name="businessLicenseNo" value="${merchant.businessLicenseNo}" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="true"/>
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
					<input name="organizationCode" value="${merchant.organizationCode}" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="true"/>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">税务登记证号码：</label>
				<div class="controls">
					<input name="taxRegistrationCertificateNo" value="${merchant.taxRegistrationCertificateNo}" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="true"/>
				</div>
			</div>
		</div>
		
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">经营范围：</label>
				<div class="controls">
					<input name="businessScope" value="${merchant.businessScope}" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="true"/>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">联系人身份证号：</label>
				<div class="controls">
					<input name="contactorIdcardNo" value="${merchant.contactorIdcardNo}" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="true"/>
				</div>
			</div>
		</div>
		
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">联系人：</label>
				<div class="controls">
					<input name="contactor" value="${merchant.contactor}" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="true"/>
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
				<label class="control-label">联系人手机号：</label>
				<div class="controls">
					<input name="contactorPhone" value="${merchant.contactorPhone}" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="true"/>
				</div>
			</div>
		</div>
		
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">个体工商户营业执照照片：</label>
				<div class="controls">
					<a class="popA">图片</a>
					<p class="pop_img">
						<span>X</span>
						<img src="${merchant.businessLicenseFile}">
					</p>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">开户许可证：</label>
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
				<label class="control-label">商户国家：</label>
				<div class="controls">
					<input value="${merchant.contryName}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">海关注册代码：</label>
				<div class="controls">
					<input value="${cbmsSuppliersetting.orgCustomsCode}" maxlength="64" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">电商/店铺网址：</label>
				<div class="controls">
					<input value="${cbmsSuppliersetting.electricityUrl}" maxlength="64" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">电商/店铺名称：</label>
				<div class="controls">
					<input value="${cbmsSuppliersetting.electricityName}" maxlength="64" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">商户企业类别名称：</label>
				<div class="controls">
					<input value="${cbmsSuppliersetting.cbmsEnterpriseTypeName}" maxlength="64" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">商户企业类型名称：</label>
				<div class="controls">
					<input value="${cbmsSuppliersetting.cbmsSupplierCategoryName}" maxlength="64" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">商户贸易类型名称：</label>
				<div class="controls">
					<input value="${cbmsSuppliersetting.cbmsTradeTypeName}" maxlength="64" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">经营范围：</label>
				<div class="controls">
					<input value="${cbmsSuppliersetting.businessScope}" maxlength="64" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">拒绝理由：</label>
			<div class="controls">
				<form:input id="objection" path="objection" htmlEscape="false" maxlength="50" class="input-xlarge"/>
			</div>
		</div>
		
		<input type="hidden"  id="legalAuditStatus" name="legalAuditStatus" />
		<input type="hidden"  id="rcAuditStatus" name="rcAuditStatus" />
		
		<div class="form-actions">
			<shiro:hasPermission name="merchant:merchantLegal:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="通 过" onclick="return pass();"/>&nbsp;</shiro:hasPermission>
			<shiro:hasPermission name="merchant:merchantLegal:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="拒 绝" onclick="return reject();"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>