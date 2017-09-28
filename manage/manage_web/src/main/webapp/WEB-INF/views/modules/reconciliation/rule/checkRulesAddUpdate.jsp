<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>对账参数管理</title>
<meta name="decorator" content="default"/>
<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
<script language="JavaScript" src="${ctxStatic}/jquery.base64.js"></script>
<style>
    #main {
        margin: 50px;
    }
	.form-horizontal .controls{
		margin-left: 3px;
	}
</style>
<script type="text/javascript">

	$(document).ready(function() {
		$(".add_number").on("blur",function(){
			var this_value = $(this).val();
			var this_id=this.id;

			//获取表单里面的值
			 var num1 = $("#paymentId").val();
			 var num2 = $("#channelNo").val();
			 var num3 = $("#costAmount").val();
			 var num4 = $("#successAmount").val();
			 var num5 = $("#promotionAmount").val();
			 var num6 = $("#transStatus").val();
			 var arr = [];
			 arr.push(num1,num2,num3,num4,num5,num6);

			for(var i=0;i<arr.length;i++){

				var value=arr[i];
				for (var j = i + 1; j <arr.length; j++){
					if(value==arr[j]){
						if(value !==''){
							parent.showThree();
							parent.changeThreeName("第" + (i + 1) + "个跟第" + (j + 1) + "个重复，值是：" + value+"  ，带  +  号的值不允许重复");
							$(this).val("");
						}
					}
				}
			}
		});

		$("#inputForm").validate({

            submitHandler: function(form){
				var transStatus=$('#transStatus').val();
				if(isNaN(transStatus)){
					$("#msgId").text("交易状态位置请输入数字类型");
					return false;
				}else{
					$("#msgId").text("");
				}
				var promotionAmount=$('#promotionAmount').val();
				if(isNaN(promotionAmount)){
					$("#msgId").text("优惠金额位置请输入数字类型");
					return false;
				}else{
					$("#msgId").text("");
				}

				var paymentId=$('#paymentId').val();
				if(isNaN(paymentId)){

					$("#msgId").text("支付单号位置请输入数字类型");
					return false;
				}else{
					$("#msgId").text("");
				}

				var channelNo=$('#channelNo').val();
				if(isNaN(channelNo)){

					$("#msgId").text("银行流水号位置请输入数字类型");
					return false;
				}else{
					$("#msgId").text("");
				}

				var costAmount=$('#costAmount').val();
				if(isNaN(costAmount)){
					$("#msgId").text("交易成本位置请输入数字类型");
					return false;
				}else{
					$("#msgId").text("");
				}

				//判断金额是不是数字
				var successAmount=$('#successAmount').val();
				if(isNaN(successAmount)){

					$("#msgId").text("通道金额位置请输入数字类型");
					return false;
				}else{
					$("#msgId").text("");
				}

				var beginRowNum=$('#beginRowNum').val();
				if(isNaN(beginRowNum)){

					$("#msgId").text("开始行号位置请输入数字类型");
					return false;
				}else{
					$("#msgId").text("");
				}

                $(".get_value").each(function(){
                    var get_value = $(this).val();
                    var str=encodeBase64(get_value);
                    $(this).val(str);

                });

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

	//添加规则
	function apentClick(valueId) {
	    var div = valueId+'Div';
        var fatherDiv = $("#"+div);

        var idValue = $("#"+valueId).val();
        if(idValue != ''){
            //获取最后一行数据的套号
            var htmlDiv = [];
            htmlDiv.push('');
            htmlDiv.push('<div>');
            htmlDiv.push('<label class="control-label" >名称：</label>');
            htmlDiv.push('<div class="controls">');
            htmlDiv.push('<input type="text" name='+valueId+'Name style="width:80px;"/> - 规则:<input  name='+valueId+'Rule maxlength="180" class="get_value" style="width:180px;border-radius:5px;border: solid 1px #ccc;height: 26px;" />');
            htmlDiv.push('&nbsp; <input class="btn btn-primary" type="button" onclick="deleteDiv(this)" value="删除"/>');
            htmlDiv.push('</div>');
            htmlDiv.push('</div>');
            htmlDiv.push('');

            fatherDiv.append(htmlDiv.join(''));
		}
    }

    function deleteDiv(id) {
         $(id).parent().parent().remove();

    }

    //对中文进行转码
    function encodeBase64(mingwen,times){
        var code="";
        var num=1;
        if(typeof times=='undefined'||times==null||times==""){
            num=1;
        }else{
            var vt=times+"";
            num=parseInt(vt);
        }
        if(typeof mingwen=='undefined'||mingwen==null||mingwen==""){
        }else{
            $.base64.utf8encode = true;
            code=mingwen;
            for(var i=0;i<num;i++){
                code=$.base64.btoa(code);
            }
        }
        return code;
    };


    //删除正则表达式
    function deleteRegex(value) {
     var id =  $(value).attr("saveid");
        $.post("${ctx}/reconciliation/settleRuleControl/delete", { "deleteId":id},
            function(data){
                if(data){
                   deleteDiv(value);
                }else{
                    parent.showThree();
                    parent.changeThreeName("删除正则表达式错误!");
                }
            });
    }
</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li><a href="${ctx}/reconciliation/settleRuleControl?cache=1">对账规则控制</a></li>
	<li class="active"><a>${not empty settleRuleControl.ruleControlId?'对账规则修改列表':'对账规则添加列表'}</a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="settleRuleControl" action="${ctx}/reconciliation/settleRuleControl/save?ruleControlId=${settleRuleControl.ruleControlId}" method="post" class="form-horizontal">
	<sys:message content="${message}"/>

<c:if test="${empty settleRuleControl.ruleControlId}">
	<div class="control-group">
		<label class="control-label">通道合作方：</label>
		<div class="controls">
			<form:select id="channelCode" path="channelCode" class="input-xlarge required">
				<c:forEach items="${List_channelName}" var="channelName">
					<form:option value="${channelName.channelCode}" label="${channelName.channelName}"/>
				</c:forEach>
			</form:select>
		</div>
	</div>

	<div class="control-group">
		<label class="control-label">支付通道类型：</label>
		<div class="controls">
		<!-- 隐藏域用来显示提示警告 -->
			<form:select id="channelType" path="channelType" class="input-xlarge required">
				<c:forEach items="${channelType}" var="wStatus">
					<form:option value="${wStatus.value}" label="${wStatus.name}" htmlEscape="false" class="required"/>
				</c:forEach>
			</form:select>
		</div>
	</div>
</c:if>
<c:if test="${not empty settleRuleControl.ruleControlId}">
    <div class="control-group">
        <label class="control-label">通道合作方：</label>
        <div class="controls">
            <input value="${settleRuleControl.channelName}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">支付通道类型：</label>
        <div class="controls">
            <input value="${settleRuleControl.channelTypeRule}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
        </div>
    </div>
	<input type="hidden" name="channelCode" value="${settleRuleControl.channelCode}">
	<input type="hidden" name="channelType" value="${settleRuleControl.channelType}">
</c:if>

	<div class="control-group">
		<label class="control-label">账单类型：</label>
		 <div class="controls">
			<form:select id="billType" path="billType" class="input-xlarge required">
				<c:forEach items="${billType}" var="SettleRuleControl">
					<form:option value="${SettleRuleControl.value}" label="${SettleRuleControl.name}"/>
				</c:forEach>
			</form:select>
		</div>
	</div>

	<div class="control-group">
		<label class="control-label">对账方式：</label>
		 <div class="controls">
			<form:select id="settleWay" path="settleWay" class="input-xlarge required">
				<c:forEach items="${settleWay}" var="SettleRuleControl">
					<form:option value="${SettleRuleControl.value}" label="${SettleRuleControl.name}"/>
				</c:forEach>
			</form:select>
		</div>
	</div>

	<div class="control-group" id="paymentIdDiv">
		<label class="control-label">支付单号：</label>
		<div class="controls">
			<form:input path="paymentId" id="paymentId" value="${settleRuleControl.paymentId}" htmlEscape="false" maxlength="2" class="required add_number" style="width:256px;"/>
			<span class="help-inline" onclick="apentClick('paymentId')" name="paymentId" style="cursor: pointer;"><h3>+</h3></span>
			<span class="help-inline"><font color="red">*</font> </span>
            <c:if test="${not empty settleRuleControl.ruleControlId}">
                <c:forEach items="${list_paymentId}" var="paymentId">
					<div style="left: 20px">
						<label class="control-label" >名称：</label>
						<div class="controls">
                            <input type="text" name="paymentIdName" value="${paymentId.regexName}"  style="width:80px;"/> - 规则:<input name="paymentIdRule"  value="${paymentId.regexShow}" maxlength="200" class="get_value"  style="width:180px;border-radius:5px;border: solid 1px #ccc;height: 26px;" />
                            <input class="btn btn-primary" type="button" saveid="${paymentId.regexControlId}"  onclick="deleteRegex(this)" value="删除"/>
						</div>
					</div>
                </c:forEach>
            </c:if>
			<span id="msgId" for="paymentId" style="color: red"></span>
		</div>
	</div>
	<div class="control-group" id="channelNoDiv">
		<label class="control-label">银行流水号：</label>
		<div class="controls">
			<form:input path="channelNo" id="channelNo" value="${settleRuleControl.channelNo}" htmlEscape="false" maxlength="2"  style="width:256px;" class="add_number"/>
			<span class="help-inline" onclick="apentClick('channelNo')" style="cursor: pointer;"><h3>+</h3></span>
            <c:if test="${not empty settleRuleControl.ruleControlId}">
                <c:forEach items="${list_channelNo}" var="channelNo">
                    <div>
                        <label class="control-label" >名称：</label>
                        <div class="controls">
                            <input type="text" name="channelNoName" value="${channelNo.regexName}"  style="width:80px;"/> - 规则:<input name="channelNoRule"  value="${channelNo.regexShow}" maxlength="200" class="get_value" style="width:180px;border-radius:5px;border: solid 1px #ccc;height: 26px;" />
                            <input class="btn btn-primary" type="button" saveid="${channelNo.regexControlId}" onclick="deleteRegex(this)" value="删除"/>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
		</div>
	</div>

	<div class="control-group" id="costAmountDiv">
		<label class="control-label">交易成本：</label>
		<div class="controls">
			<form:input path="costAmount" id="costAmount" value="${settleRuleControl.costAmount}" htmlEscape="false" maxlength="2" style="width:256px;" class="add_number"/>
			<span class="help-inline" onclick="apentClick('costAmount')" style="cursor: pointer;"><h3>+</h3></span>
            <c:if test="${not empty settleRuleControl.ruleControlId}">
                <c:forEach items="${list_costAmount}" var="costAmount">
                    <div>
                        <label class="control-label" >名称：</label>
                        <div class="controls">
                            <input type="text" name="costAmountName" value="${costAmount.regexName}"  style="width:80px;"/> - 规则:<input name="costAmountRule"  value="${costAmount.regexShow}" maxlength="200" class="get_value"  style="width:180px;border-radius:5px;border: solid 1px #ccc;height: 26px;" />
                            <input class="btn btn-primary" type="button" saveid="${costAmount.regexControlId}"  onclick="deleteRegex(this)" value="删除"/>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
		</div>
	</div>
	<div class="control-group" id="successAmountDiv">
		<label class="control-label">通道金额：</label>
		<div class="controls">
			<form:input path="successAmount" id="successAmount" value="${settleRuleControl.successAmount}" htmlEscape="false" maxlength="2" class="required add_number" style="width:256px;"/>
			<span class="help-inline" onclick="apentClick('successAmount')" style="cursor: pointer;"><h3>+</h3></span>
			<span class="help-inline"><font color="red">*</font> </span>
            <c:if test="${not empty settleRuleControl.ruleControlId}">
                <c:forEach items="${list_successAmount}" var="successAmount">
                    <div>
                        <label class="control-label" >名称：</label>
                        <div class="controls">
                            <input type="text" name="successAmountName" value="${successAmount.regexName}"  style="width:80px;"/> - 规则:<input name="successAmountRule"  value="${successAmount.regexShow}" maxlength="200" class="get_value" style="width:180px;border-radius:5px;border: solid 1px #ccc;height: 26px;" />
                            <input class="btn btn-primary" type="button" saveid="${successAmount.regexControlId}"  onclick="deleteRegex(this)" value="删除"/>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
		</div>
	</div>
	<div class="control-group" id="splitFlgDiv">
		<label class="control-label">分隔符：</label>
		<div class="controls">
			<form:input path="splitFlg" id="splitFlgId" value="${settleRuleControl.splitFlg}" htmlEscape="false" maxlength="2"  style="width:256px;"/>
		</div>
	</div>

	<div class="control-group" id="beginRowNumDiv">
		<label class="control-label">开始行号：</label>
		<div class="controls">
			<form:input path="beginRowNum" id="beginRowNum" value="${settleRuleControl.beginRowNum}" htmlEscape="false" maxlength="2" class="required" style="width:256px;"/>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>

	<div class="control-group" id="endFlgDiv">
		<label class="control-label">结束标记：</label>
		<div class="controls">
			<form:input path="endFlg" id="endFlgId" value="${settleRuleControl.endFlg}" htmlEscape="false" maxlength="12" style="width:256px;"/>
		</div>
	</div>
	<div class="control-group" id="promotionAmountDiv">
		<label class="control-label">优惠金额：</label>
		<div class="controls">
			<form:input path="promotionAmount" id="promotionAmount" value="${settleRuleControl.promotionAmount}" htmlEscape="false" maxlength="2" style="width:256px;" class="add_number"/>
			<span class="help-inline" onclick="apentClick('promotionAmount')" style="cursor: pointer;"><h3>+</h3></span>
            <c:if test="${not empty settleRuleControl.ruleControlId}">
                <c:forEach items="${list_promotionAmount}" var="promotionAmount">
                    <div>
                        <label class="control-label" >名称：</label>
                        <div class="controls">
                            <input type="text" name="promotionAmountName" value="${promotionAmount.regexName}"  style="width:80px;"/> - 规则:<input name="promotionAmountRule"  value="${promotionAmount.regexShow}" maxlength="200" class="get_value" style="width:180px;border-radius:5px;border: solid 1px #ccc;height: 26px;" />
                            <input class="btn btn-primary" type="button" saveid="${promotionAmount.regexControlId}"  onclick="deleteRegex(this)" value="删除"/>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
		</div>
	</div>
	<div class="control-group" id="transStatusDiv">
		<label class="control-label">交易状态：</label>
		<div class="controls">
			<form:input path="transStatus" id="transStatus" value="${settleRuleControl.transStatus}" htmlEscape="false" maxlength="4" style="width:256px;" class="add_number"/>
			<span class="help-inline" onclick="apentClick('transStatus')" style="cursor: pointer;"><h3>+</h3></span>
            <c:if test="${not empty settleRuleControl.ruleControlId}">
                <c:forEach items="${list_transStatus}" var="transStatus">
                    <div>
                        <label class="control-label" >名称：</label>
                        <div class="controls">
                            <input type="text" name="transStatusName" value="${transStatus.regexName}"  style="width:80px;"/> - 规则:<input name="transStatusRule"  value="${transStatus.regexShow}" maxlength="200" class="get_value" style="width:180px;border-radius:5px;border: solid 1px #ccc;height: 26px;" />
                            <input class="btn btn-primary" type="button" saveid="${transStatus.regexControlId}"  onclick="deleteRegex(this)" value="删除"/>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
		</div>
	</div>

	<div class="control-group">
		<label class="control-label">状态：</label>
		<div class="controls">
			<form:select id="status" path="status" class="input-xlarge required">
				<c:forEach items="${statusRegx}" var="SettleRuleControl">
					<form:option value="${SettleRuleControl.value}" label="${SettleRuleControl.name}"/>
				</c:forEach>
			</form:select>
		</div>
	</div>

	<div class="form-actions">
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="保存并提交"/>
		<input id="btnCancel" class="btn" type="button" value="取 消" onclick="history.go(-1)"/>
	</div>
</form:form>
</body>
</html>