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

			$(".toAuthentication").on("click",function () {
				$(".loading_box").show();
				$.post("${ctx}/merchant/corporateIdentities/auth",{"merchantId":$("#userId").val()},function(data){
					$(".loading_box").hide();
					$("#authInfo").html(data);
					$("#authInfo").css("color","red");
				},"text");
			});
		});
		function pass(){
            if(!confirm("请确认提交此操作?")){
                return false;
            }else {
                if($("#objection").val() != ""){
                    parent.showThree();
                    parent.changeThreeName("通过时拒绝理由应为空");
                    return false;
                }
                $("#legalAuditStatus").val("SUCCES");
                $("#rcAuditStatus").val("AUDING");
            }

		}
		function reject(){
			if($.trim($("#objection").val()) == ""){
				parent.showThree();
				parent.changeThreeName("拒绝理由不能为空");
				return false;
			}else{
                if(!confirm("请确认提交此操作?")){
                    return false;
                }else {
                    $("#legalAuditStatus").val("AUDREJ");
                }
            }
		}
	</script>
	<style>
		.loading_box{
			position: absolute;
			width:32px;
			height:32px;
			top: 50%;
			left: 50%;
			margin-left: -16px;
			margin-top: -16px;
			display: none;
		}
		.toAuthentication{
			width:55px;
			height: 30px;
			line-height: 30px;
			display: inline-block;
			color: #fff;
			background-color: #2fa4e7;
			text-align: center;
			margin-left: 20px;
			border-radius: 5px;

		}
		#authInfo{
			float: left;
		}
		.go_renz{
			float: left;
			width:300px;
		}
	</style>
</head>
<body>
	<div class="loading_box">
		<p><img src="${ctxStatic}/images/ajax-loader.gif"></p>
	</div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/merchant/merchantLegal?cache=1">法务审核列表</a></li>
		<li class="active"><a href="${ctx}/merchant/merchantLegal/form?id=${merchant.userId}">法务审核</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="merchant" action="${ctx}/merchant/merchantLegal/status" method="post" class="form-horizontal">
		<form:hidden path="userId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">商户编码：</label>
				<div class="controls">
					<input name="userId" value="${merchant.userId}" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="true"/>
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
					<input name="type" value="${merchant.type}" htmlEscape="false" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="true"/>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">证件类型：</label>
				<div class="controls">
					<input name="certificateType" value="${merchant.certificateType}" htmlEscape="false" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="true" value="普通营业执照"/>
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
					<input name="contactorPhone" value="${merchant.contactorPhone}" style="border:0px;background-color:#fff;padding-top: 3px;" readonly="true"/>
				</div>
			</div>
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
					<a class="popA">图片</a>
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
					<a class="popA">图片</a>
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
			<label class="control-label">拒绝理由：</label>
			<div class="controls">
				<form:input id="objection" path="objection" htmlEscape="false" maxlength="50" class="input-xlarge"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">第三方企业信息认证：</label>
			<div class="controls">
				<p id="authInfo">${merchant.authenticationStatus}
				<c:if test="${empty merchant.authenticationStatus}">未认证</c:if>
				<c:if test="${merchant.authenticationStatus == '认证失败'}">,${merchant.authenticationReason}</c:if></p>
				<p class="go_renz">
				<c:if test="${merchant.authenticationStatus != '认证成功'}"><a style="cursor:pointer;" class="toAuthentication">去认证</a></c:if></p>
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