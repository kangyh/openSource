<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>费率配置管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            $("#productCode1").find("option[value='" + $('#productCode').val() + "']").attr("selected",true);
            $("#s2id_productCode1").find(".select2-chosen").text($("#productCode1").find("option:selected").text());
            //$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
                    var productName = $("#productName").val();
                    var productCode = $("#productCode").val();
                    var profitPercent = $("#profitPercent").val();
                    var defaultFeePercent = $("#defaultFeePercent").val();
                    var minAmount = $("#transAmountBegin").val();
                    var maxAmount = $("#transAmountEnd").val();
                    var feeMinAmount = $("#defaultFeeMin").val();
                    var feeMaxAmount = $("#defaultFeeMax").val();

                    if(productName == undefined || productName.trim() == ""){
                        alert("请选择产品名称!");
                        return;
                    }

                    if(productCode == undefined || productCode.trim() == ""){
                        alert("请选择产品名称!");
                        return;
                    }

                    if(profitPercent == undefined || profitPercent.trim() == ""){
                        alert("请输入分润百分比!");
                        return;
                    }

                    if(minAmount == undefined || minAmount.trim() == ""){
                        alert("请输入交易总额最小金额!");
                        return;
                    }

                    if(maxAmount == undefined || maxAmount.trim() == ""){
                        alert("请输入交易总额最大金额!");
                        return;
                    }

                    if(defaultFeePercent == undefined || defaultFeePercent.trim() == ""){
                        alert("请输入借记卡基本费率!");
                        return;
                    }

                    if(feeMinAmount == undefined || feeMinAmount.trim() == ""){
                        alert("请输入借记卡费率最小值!");
                        return;
                    }

                    if(feeMaxAmount == undefined || feeMaxAmount.trim() == ""){
                        alert("请输入借记卡费率最大值!");
                        return;
                    }

                    var  mumRegex=/^(([1-9]\d{0,9})|0)(\.\d{1,4})?$/;
                    var  mumRegex1=/^[1-9]\d*$/;

                    if(!mumRegex1.test(profitPercent)){
                        alert("分润百分比格式不正确，请输入正整数!");
                        return;
                    }

                    if(!mumRegex.test(minAmount)){
                        alert("分润总额最小金额格式不正确!");
                        return;
                    }

                    if(!mumRegex.test(maxAmount)){
                        alert("分润总额最大金额格式不正确!");
                        return;
                    }

                    if(!mumRegex.test(defaultFeePercent)){
                        alert("借记卡基本费率格式不正确！");
                        return;
                    }

                    if(!mumRegex.test(feeMinAmount)){
                        alert("借记卡费率最小值格式不正确!");
                        return;
                    }

                    if(!mumRegex.test(feeMaxAmount)){
                        alert("借记卡费率最大值格式不正确!");
                        return;
                    }

                    if(parseFloat(minAmount)>parseFloat(maxAmount)){
                        alert("交易总额的最小值不能大于最大金额");
                        return;
					}

                    if(parseInt(feeMinAmount)>parseInt(feeMaxAmount)){
                        alert("借记卡费率的最小值不能大于最大金额");
                        return;
                    }

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

        function getProductName(option){
            var name = option.text;
            var code = option.value;
            $("#productName").val(name);
            $("#productCode").val(code);
            return;
        }
        
        function limitMinAmount(o) {
			var minAmount = $("#transAmountBegin").val();
			var maxAmount = $("#transAmountEnd").val();

			if(!(maxAmount == undefined || maxAmount.trim() == "") && !(minAmount == undefined || minAmount.trim() == "")){
				if(parseFloat(minAmount) > parseFloat(maxAmount)){
				    alert("最小金额不能大于最大金额!");
				    return;
				}
			}
        }

        function limitMaxAmount(o) {
            var minAmount = $("#transAmountBegin").val();
            var maxAmount = $("#transAmountEnd").val();

            if(!(maxAmount == undefined || maxAmount.trim() == "") && !(minAmount == undefined || minAmount.trim() == "")){
                if(parseFloat(maxAmount) < parseFloat(minAmount)){
                    alert("最大金额不能小于最小金额!");
                    return;
                }
            }
        }

        function limitFeeMinAmount(o) {
            var minAmount = $("#defaultFeeMin").val();
            var maxAmount = $("#defaultFeeMax").val();

            if(!(maxAmount == undefined || maxAmount.trim() == "") && !(minAmount == undefined || minAmount.trim() == "")){
                if(parseFloat(minAmount) > parseFloat(maxAmount)){
                    alert("最小金额不能大于最大金额!");
                    return;
                }
            }
        }

        function limitFeeMaxAmount(o) {
            var minAmount = $("#defaultFeeMin").val();
            var maxAmount = $("#defaultFeeMax").val();

            if(!(maxAmount == undefined || maxAmount.trim() == "") && !(minAmount == undefined || minAmount.trim() == "")){
                if(parseFloat(maxAmount) < parseFloat(minAmount)){
                    alert("最大金额不能小于最小金额!");
                    return;
                }
            }
        }
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">--%>
		<%--<li><a href="${ctx}/agent/rate/agentRate/list?agentId=${agentRate.agentId}">费率配置列表</a></li>--%>
		<%--<li class="active"><a href="${ctx}/agent/rate/agentRate/form?id=${agentRate.id}">费率配置<shiro:hasPermission name="agent:rate:agentRate:edit">${not empty agentRate.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="agent:rate:agentRate:edit">查看</shiro:lacksPermission></a></li>--%>
	<%--</ul>--%>
	<br/>
	<form:form id="inputForm" modelAttribute="agentRateExt" action="${ctx}/agent/rate/agentRate/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">代理商编号：</label>
			<div class="controls">
				${agentRateExt.agentCode}
				<form:input path="agentId" type="hidden" value="${agentRate.agentId}" htmlEscape="false" class="input-xlarge "/>
				<form:input path="agentCode" type="hidden" value="${agentRate.agentCode}" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">代理商名称：</label>
			<div class="controls">
					${agentRateExt.agentName}
				<form:input path="agentName" type="hidden" value="${agentRate.agentName}" htmlEscape="false" maxlength="11" class="input-xlarge "/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">产品名称：</label>
			<div class="controls">
				<form:select path="" id="productCode1" class="input-xlarge" onchange="getProductName(this.options[this.options.selectedIndex]);">
					<c:if test="${agentRate.id == null}">
						<form:option value="" label="请选择"/>
					</c:if>
					<form:options items="${productList}" itemLabel="name" itemValue="code" htmlEscape="false"/>
					<%--<form:input path="productCode" htmlEscape="false" maxlength="6" class="input-xlarge "/>--%>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
				<form:input path="productCode" id="productCode" type="hidden"/>
				<form:input path="productName" id="productName" type="hidden"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分润百分比：</label>
			<div class="controls">
				<form:input path="profitPercent" htmlEscape="false" maxlength="11" class="input-xlarge "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易总额区间：</label>
			<div class="controls">
				<form:input id="transAmountBegin" path="transAmountBegin" htmlEscape="false" maxlength="11" class="input-xlarge required" onblur="limitMinAmount(this)"/>
				万-
				<form:input id="transAmountEnd" path="transAmountEnd" htmlEscape="false" maxlength="11" class="input-xlarge required" onblur="limitMaxAmount(this)"/>
				万
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">借记卡计费类型：</label>
			<div class="controls">
				${feeType.value}
				<%--<form:select path="defaultFeeType" class="input-xlarge required" disabled="true">--%>
					<%--<form:options items="${feeTypeList}" itemLabel="value" itemValue="name" htmlEscape="false" />--%>
				<%--</form:select>--%>
				<input name="defaultFeeType" value = ${feeType.name} type="hidden" />

				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">借记卡基本费率：</label>
			<div class="controls">
				<form:input id="defaultFeePercent" path="defaultFeePercent" htmlEscape="false" class="input-xlarge "/> %
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">借记卡费率最小值：</label>
			<div class="controls">
				<form:input id="defaultFeeMin" path="defaultFeeMin" htmlEscape="false" maxlength="11" class="input-xlarge "/> 元
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">借记卡费率最大值：</label>
			<div class="controls">
				<form:input id="defaultFeeMax" path="defaultFeeMax" htmlEscape="false" maxlength="11" class="input-xlarge "/> 元
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="agent:rate:agentRate:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>