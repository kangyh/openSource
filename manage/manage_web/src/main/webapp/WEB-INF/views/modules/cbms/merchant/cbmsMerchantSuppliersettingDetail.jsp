<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <link href="${ctxStatic}/common/img.css" type="text/css" rel="stylesheet">
	<title>商户详情</title>
	<meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {

            //默认选中
            var timeIntervalValue=$("#timeIntervalValue").val();
            if(timeIntervalValue!=null) {
                var timeInterval = document.getElementsByName('timeInterval');
                for (var i = 0; i < timeInterval.length; i++) {
                    if (timeIntervalValue == timeInterval[i].value) {
                        var id = timeInterval[i].id;
                        $("input[id='" + id + "']").attr("checked", "checked");

                    }
                }
                var radioval = $('input:radio[name="timeInterval"]:checked').val();
                if (radioval == null) {
                    var id = timeInterval[timeInterval.length - 1].id;
                    $("input[id='" + id + "']").attr("checked", "checked");
                    $("input[id='" + id + "']").val(timeIntervalValue);
                    $('#inputtext').val(timeIntervalValue)
                    $("#inputtext").attr('disabled', false);
                }
            }
            if(${merchant.certificateType == "多证合一"}){
                $("#taxRegistrationCertificateFile").hide();
                $("#organizationCodeCertificateFile").hide();
                $("#organizeAndtaxRegist").hide();
            }else if(${merchant.certificateType == "普通证件"} || ${merchant.certificateType == "个体商户"}) {
                $("#taxRegistrationCertificateFile").show();
                $("#organizationCodeCertificateFile").show();
                $("#organizeAndtaxRegist").show();
            }

            //点击
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
        function timeRadio(){
            var code=$('input:radio[name="timeInterval"]:checked').attr('id');
            var value=$("label[for='"+code+"']").text();
            if(value=='其他'){
                $("#inputtext").attr('disabled',false);
                $("#inputtext").attr('class',"input-xlarge required");
            }else {
                $("label[for='inputtext']").remove()
                $("#inputtext").attr('disabled',true);
                $("#inputtext").removeAttr('class');
            }
        };
        function fuzhi(option){
            var  code=option.value;
            if(parseFloat(code)>60){
                alert("自选区间不得大于60天")
                $("#inputtext").val("");
            }else{
                $('input:radio[name="timeInterval"]:checked').val(code);
            }
        };

        function submitMerchant(){
            var  timeInterval=$('input:radio[name="timeInterval"]:checked').val();
            if(timeInterval==null){
                alert("请选择一项查询区间")
                return;
            }else if(timeInterval=="其他"){
                timeInterval="60"
            }else{
            }
            var  merchantid=$("#id").val();
            $.ajax({
                type:"post",
                data:{"timeInterval":timeInterval,"merchantid":merchantid},
                url:"${ctx}/cbms/cbmsMerchantSuppliersetting/updateTimeInterval",
                success:function(msg){
                    alert(msg);
                }
            });
        }
    </script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/cbms/merchant/merchant/">跨境商户列表</a></li>
		<li class="active"><a href="${ctx}/cbms/cbmsMerchantSuppliersetting/detail?id=${merchantUser.id}">跨境商户<shiro:hasPermission name="cbms:cbmsMerchantSuppliersetting:edit">${not empty merchantUser.id?'详情':'添加'}</shiro:hasPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cbmsMerchantSuppliersetting" class="form-horizontal">
		<form:hidden path="id" value="${merchantUser.id}"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">商户账号：</label>
				<div class="controls">
					<input value="${merchantUser.loginName}" readonly style="border:0px;background-color:#fff;padding-top: 3px;"/>
				</div>
			</div>
            <div class="control-right">
                <label class="control-label">国家名称：</label>
                <div class="controls">
                    <input value ="${merchant.contryName}"  readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
                </div>
            </div>
        </div>

        <div class="control-group">
            <div class="control-left">
                <label class="control-label">单位类型：</label>
                <div class="controls">
                    <input value ="${merchant.type}"readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">证件类型：</label>
                <div class="controls">
                    <input value ="${merchant.certificateType}"  readonly style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
        </div>

		<div class="control-group">
            <div class="control-left">
                <label class="control-label">公司名称：</label>
                <div class="controls">
                    <input value ="${merchant.companyName}" readonly style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">公司联系电话：</label>
                <div class="controls">
                    <input value ="${merchant.companyPhone}"readonly style="border:0px;background-color:#fff;padding-top: 3px;"/>
                </div>
            </div>
        </div>

        <div class="control-group">
            <div class="control-left">
                <label class="control-label">证件地址：</label>
                    <input value ="${merchant.provinceName}" readonly style="border:0px;background-color:#fff;padding-top: 3px;"/>
                    <input value ="${merchant.cityName}" readonly style="border:0px;background-color:#fff;padding-top: 3px;"/>
                    <input value ="${merchant.countyName}" readonly style="border:0px;background-color:#fff;padding-top: 3px;"/>
            </div>
        </div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">公司注册地址：</label>
				<div class="controls">
					<input value="${merchant.businessAddress}"  readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">公司网址：</label>
				<div class="controls">
                    <input value="${merchant.website}" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">营业执照号码：</label>
				<div class="controls">
                    <input value="${merchant.businessLicenseNo}" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">营业执照结束时间：</label>
				<div class="controls">
                    <input value="<fmt:formatDate value="${merchant.businessLicenseEndTime}" pattern="yyyy-MM-dd"/>" readonly style="border:0px;background-color:#fff;padding-top: 3px;"  />
				</div>
			</div>
		</div>

		<div class="control-group" id="organizeAndtaxRegist">
			<div class="control-left">
				<label class="control-label">组织机构代码：</label>
				<div class="controls">
                    <input value="${merchant.organizationCode}" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">税务登记证号码：</label>
				<div class="controls">
                    <input value="${merchant.taxRegistrationCertificateNo}"  readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">IPC备案号：</label>
            <div class="controls">
                <input value="${merchant.ipcNo}" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
            </div>
        </div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">法人名称：</label>
				<div class="controls">
                    <input value="${cbmsSuppliersetting.llegalperson}" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
                <label class="control-label">法人手机号码：</label>
                <div class="controls">
                    <input value="${merchant.legalMobile}" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
                </div>
			</div>
		</div>

        <div class="control-group">
            <div class="control-left">
                <label class="control-label">法人代表身份证号：</label>
                <div class="controls">
                    <input value="${merchant.legalIdcard}" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">法人代表证件有效期结束：</label>
                <div class="controls">
                    <input value="<fmt:formatDate value="${merchant.legalCertificateEndTime}" pattern="yyyy-MM-dd"/>" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
                </div>
            </div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">联系人：</label>
				<div class="controls">
                    <input value="${merchant.contactor}" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
            <div class="control-right">
                <label class="control-label">联系人手机号：</label>
                <div class="controls">
                    <input value="${merchant.contactorPhone}" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
                </div>
            </div>
		</div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">联系人身份证号：</label>
				<div class="controls">
                    <input value="${merchant.contactorIdcardNo}" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">联系人证件有效期结束：</label>
				<div class="controls">
                    <input value="<fmt:formatDate value="${merchant.contactorCertificateEndTime}" pattern="yyyy-MM-dd"/>" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>

		<div class="control-group">
            <div class="control-left">
                <label class="control-label">海关注册代码：</label>
                <div class="controls">
                    <input value="${cbmsSuppliersetting.orgCustomsCode}" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">商户名称：</label>
                <div class="controls">
                    <input value="${cbmsSuppliersetting.supplierName}" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
                </div>
            </div>
		</div>

		<div class="control-group">
            <label class="control-label">电商平台名称（总局备案名称）[国检]（跟汇元合作的电商平台）：</label>
            <div class="controls">
                <input value="${cbmsSuppliersetting.enterpriseName}" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">电商平台编号（总局备案号）[国检]（跟汇元合作的电商平台）：</label>
            <div class="controls">
                <input value="${cbmsSuppliersetting.centralOfficeNumber}" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
            </div>
        </div>

		<div class="control-group">
            <div class="control-left">
                <label class="control-label">平台商户名称（海关报备）：</label>
                <div class="controls">
                    <input value="${cbmsSuppliersetting.orgCustomsName}" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
                </div>
            </div>
		</div>
		<div class="control-group">
            <div class="control-left">
                <label class="control-label">电商/店铺名称：</label>
                <div class="controls">
                    <input value="${cbmsSuppliersetting.electricityName}" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
                </div>
            </div>
            <div class="control-right">
                <label class="control-label">电商/店铺网址：</label>
                <div class="controls">
                    <input value="${cbmsSuppliersetting.electricityUrl}" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
                </div>
            </div>
		</div>
		<div class="control-group">
			<label class="control-label">商户企业类别名称：</label>
			<div class="controls">
                <input value="${cbmsSuppliersetting.cbmsEnterpriseTypeName}" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户企业类型名称：</label>
			<div class="controls">
                <input value="${cbmsSuppliersetting.cbmsSupplierCategoryName}" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户贸易类型名称：</label>
			<div class="controls">
                <input value="${cbmsSuppliersetting.cbmsTradeTypeName}" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
			</div>
		</div>

        <div class="control-group">
            <label class="control-label">经营范围：</label>
            <div class="controls">
                <input value="${merchant.businessScope}"  readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
            </div>
        </div>

		<div class="control-group">
			<label class="control-label">开户许可证：</label>
			<div class="controls">
                <a class="popA">图片</a>
                <p class="pop_img">
                    <span>X</span>
                    <img src="${merchant.permitsAccounts}">
                </p>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">法人代表证件照(正)：</label>
            <div class="controls">
                <a class="popA">图片</a>
                <p class="pop_img">
                    <span>X</span>
                    <img src="${merchant.legalCertificateFront}">
                </p>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">法人代表证件照(反)：</label>
			<div class="controls">
                <a class="popA">图片</a>
                <p class="pop_img">
                    <span>X</span>
                    <img src="${merchant.legalCertificateBack}">
                </p>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">营业执照证（三证合一）：</label>
            <div class="controls">
                <a class="popA">图片</a>
                <p class="pop_img">
                    <span>X</span>
                    <img src="${merchant.businessLicenseFile}">
                </p>
            </div>
        </div>

		<div class="control-group" id="taxRegistrationCertificateFile">
			<label class="control-label">税务登记证：</label>
			<div class="controls">
                <a class="popA">图片</a>
                <p class="pop_img">
                    <span>X</span>
                    <img src="${merchant.taxRegistrationCertificate}">
                </p>
			</div>
		</div>
		<div class="control-group" id="organizationCodeCertificateFile">
			<label class="control-label">组织机构代码证：</label>
			<div class="controls">
                <a class="popA">图片</a>
                <p class="pop_img">
                    <span>X</span>
                    <img src="${merchant.organizationCodeCertificate}">
                </p>
			</div>
		</div>
        <div class="control-group" >
            <label class="control-label">API接口查询区间：</label>
            <div class="controls">
                <form:radiobuttons path="timeInterval" items="${timeIntervalType}" itemLabel="value" itemValue="name" htmlEscape="false" class="input-xlarge required" onchange="timeRadio()"/>
                <input type="text" id="inputtext"  disabled="disabled" onchange="fuzhi(this)" style="width:50px" value="60"
                       onfocus='if(this.value=="60"){this.value="";}; '
                       onblur='if(this.value==""){this.value="60";};' onkeyup="this.value=(this.value.match(/^[1-9]{1}[0-9]*$/))" maxlength="2"/>天
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
            <font color="#a9a9a9" style="padding-left: 155px">提示:查询区间只能选择一项,自选区间不得大于60天</font>
        </div>
        <input id="timeIntervalValue"  type="hidden" value="${cbmsSuppliersetting.timeInterval}">
		<div class="form-actions">
            <input id="btnsubmit" class="btn" type="button" value="保存" onclick="submitMerchant()"/>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>