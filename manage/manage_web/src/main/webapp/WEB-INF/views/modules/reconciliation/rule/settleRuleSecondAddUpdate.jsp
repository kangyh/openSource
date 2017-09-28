<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>对账规则管理</title>
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

			//获取表单里面的值
			 var paymentId = $("#paymentId").val();
			 var bankSeq = $("#bankSeq").val();
			 var arr = [];

			 arr.push(paymentId,bankSeq);
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

                var beginRowNumid=$('#beginRowNumid').val();
                if(isNaN(beginRowNumid)){
                    parent.showThree();
                    parent.changeThreeName("开始行号位置请输入数字类型");
                    return false;
                }

               /* var endFlgId=$('#endFlgId').val();
                if(isNaN(endFlgId)){
                    parent.showThree();
                    parent.changeThreeName("结束标记位置请输入数字类型");
                    return false;
                }*/



                var flag = false;
                $(".add_number").each(function(){
                    var number = $(this).val();
                    var splitFlg = $("#splitFlg").val();

                    if(number != ""){
                        if(splitFlg != ""){//有分隔符
                            flag = te_nan(number,this);
                            if(flag){
                                return false;
                            }
                        }else{//没有分隔符
                            flag = no_nan(number,this);
                            if(flag){
                                return false;
                            }
                        }
                    }

                });

                if(!flag){
                    $(".get_value").each(function(){
                        var get_value = $(this).val();
                        var str=encodeBase64(get_value);
                        $(this).val(str);

                    });
                    var splitFlg = $("#splitFlg").val();
                    if(splitFlg != ""){
                        var pwd=encodeBase64(splitFlg);
                        $("#splitFlg").val(pwd);
					}
                    form.submit();
                }
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

	//有分隔符 检验是数字
	function  te_nan(str,id) {
        var re = /^[1-9]+[0-9]*]*$/;
        if(!re.test(str)){
            parent.showThree();
            parent.changeThreeName("有分隔符,请输入整数类型");
            $(id).val("");
            return true;
        }else{
            return false;
		}

    }

	//没有分隔符 检验不是数字
	function  no_nan(str,id) {
        var pattern = new RegExp("-")
        if(!pattern.test(str)){
            parent.showThree();
            parent.changeThreeName("没有分隔符,请输入'X-X'类型");
            $(id).val("");
            return true;
        }else{
            return false;
		}

    }

    //添加规则
    function apentClick(valueId) {
        var splitFlg = $("#splitFlg").val();
        //分割符必选存在才能添加正则
        if(splitFlg != ""){
            var div = valueId+'Div';
            var fatherDiv = $("#"+div);
            var idValue = $("#"+valueId).val();
            var re = /^[1-9]+[0-9]*]*$/;
            if(re.test(idValue)){
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
    }

    //删除规则
    function deleteDiv(id) {
        $(id).parent().parent().remove();
    }

    //删除正则表达式
    function deleteRegex(value) {
        var id =  $(value).attr("saveid");
        $.post("${ctx}/reconciliation/SettleRuleSecond/delete", { "deleteId":id},
            function(data){
                if(data){
                    deleteDiv(value);
                }else{
                    parent.showThree();
                    parent.changeThreeName("删除正则表达式错误!");
                }
            });
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

</script>
</head>
	<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/reconciliation/SettleRuleSecond?cache=1">对账规则控制</a></li>
		<li class="active"><a href="#">对账二次规则管理</a></li>
	</ul><br/>
		<form:form id="inputForm" modelAttribute="settleRuleSecond" action="${ctx}/reconciliation/SettleRuleSecond/save?ruleId=${settleRuleSecond.ruleId}" method="post" class="form-horizontal">
			<sys:message content="${message}"/>
			<div class="control-group">
				<label class="control-label">通道合作方：</label>
				<div class="controls">
						<form:select id="channelCode" path="channelCode" class="input-xlarge required">
						<c:forEach items="${channelCode}" var="channelCodeFor">
							<form:option value="${channelCodeFor.channelCode}" label="${channelCodeFor.channelName}"/>
						</c:forEach>
					</form:select>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">支付通道类型：</label>
				<div class="controls">
					<form:select id="channelType" path="channelType" class="input-xlarge required">
						<c:forEach items="${channelType}" var="channelTypeFor">
							<form:option value="${channelTypeFor.value}" label="${channelTypeFor.name}" htmlEscape="false" class="required"/>
							<span class="help-inline"><font color="red">*</font> </span>
						</c:forEach>
					</form:select>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">账单类型：</label>
				 <div class="controls">
					<form:select id="billType" path="billType" class="input-xlarge required">
						<c:forEach items="${billType}" var="billTypeFor">
							<form:option value="${billTypeFor.value}" label="${billTypeFor.name}"/>
						</c:forEach>
					</form:select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">对账方式：</label>
				 <div class="controls">
					<form:select id="settleWay" path="settleWay" class="input-xlarge required">
						<c:forEach items="${settleWay}" var="settleWayFor">
							<form:option value="${settleWayFor.value}" label="${settleWayFor.name}"/>
						</c:forEach>
					</form:select>
				</div>
			</div>

			 <div class="control-group">
				<label class="control-label">分隔符：</label>
				<div class="controls">
					<form:input path="splitFlg" id="splitFlg" value="${settleRuleSecond.splitFlg}" htmlEscape="false" maxlength="2" style="width:256px;"/>(允许输入"空格")
				</div>
			</div>
			<div class="control-group" id="paymentIdDiv">
				<label class="control-label">支付单号位置：</label>
				<div class="controls">
					<form:input path="paymentId" id="paymentId" value="${settleRuleSecond.paymentId}" htmlEscape="false" class="add_number" style="width:256px;"/>
					<span class="help-inline" onclick="apentClick('paymentId')" style="cursor: pointer;"><h3>+</h3></span>
					<span class="help-inline" id="paymentIdId" style="color: red"></span>
					<c:if test="${not empty settleRuleSecond.paymentId}">
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
				</div>

			</div>
			<div class="control-group" id="bankSeqDiv">
				<label class="control-label">银行流水号位置：</label>
				<div class="controls">
					<form:input path="bankSeq" id="bankSeq" value="${settleRuleSecond.bankSeq}" htmlEscape="false" class="add_number" style="width:256px;"/>
					<span class="help-inline" onclick="apentClick('bankSeq')" style="cursor: pointer;"><h3>+</h3></span>
					<span class="help-inline" id="bankSeqId" style="color: red"></span>
					<c:if test="${not empty settleRuleSecond.bankSeq}">
						<c:forEach items="${list_bankSeq}" var="bankSeq">
							<div style="left: 20px">
								<label class="control-label" >名称：</label>
								<div class="controls">
									<input type="text" name="bankSeqName" value="${bankSeq.regexName}"  style="width:80px;"/> - 规则:<input name="bankSeqRule"  value="${bankSeq.regexShow}" maxlength="200" class="get_value"  style="width:180px;border-radius:5px;border: solid 1px #ccc;height: 26px;" />
									<input class="btn btn-primary" type="button" saveid="${bankSeq.regexControlId}"  onclick="deleteRegex(this)" value="删除"/>
								</div>
							</div>
						</c:forEach>
					</c:if>
				</div>
			</div>
			<div class="control-group" id="feeServiceDiv">
				<label class="control-label">服务费位置：</label>
				<div class="controls">
					<form:input path="feeService" id="feeService" value="${settleRuleSecond.feeService}" htmlEscape="false"  class="add_number" style="width:170px;"/>
					<form:select id="feeServiceAdd" path="feeServiceAdd" cssStyle="width: 80px">
						<c:forEach items="${unitType}" var="unitTypeFor">
							<form:option value="${unitTypeFor.value}" label="${unitTypeFor.name}"/>
						</c:forEach>
					</form:select>
				</div>
			</div>
			<div class="control-group" id="promotionAmountDiv">
				<label class="control-label">优惠金额位置：</label>
				<div class="controls">
					<form:input path="feeFree" id="feeFree" value="${settleRuleSecond.feeFree}" htmlEscape="false"  style="width:170px;" class="add_number"/>
					<form:select id="feeFreeAdd" path="feeFreeAdd" cssStyle="width: 80px">
						<c:forEach items="${unitType}" var="unitTypeFor">
							<form:option value="${unitTypeFor.value}" label="${unitTypeFor.name}"/>
						</c:forEach>
					</form:select>
				</div>
			</div>
			<div class="control-group" id="feeBankDiv">
				<label class="control-label">发卡行手续费位置：</label>
				<div class="controls">
					<form:input path="feeBank" id="feeBank" value="${settleRuleSecond.feeBank}" htmlEscape="false"  class="add_number" style="width:170px;"/>
					<form:select id="feeBankAdd" path="feeBankAdd" cssStyle="width: 80px">
						<c:forEach items="${unitType}" var="unitTypeFor">
							<form:option value="${unitTypeFor.value}" label="${unitTypeFor.name}"/>
						</c:forEach>
					</form:select>
				</div>
			</div>
			<div class="control-group" id="successAmountDiv">
				<label class="control-label">结算金额位置：</label>
				<div class="controls">
					<form:input path="successAmount" id="successAmount" value="${settleRuleSecond.successAmount}" htmlEscape="false"  class="required add_number" style="width:170px;"/>
					<form:select id="successAmountAdd" path="successAmountAdd" cssStyle="width: 80px">
						<c:forEach items="${unitType}" var="unitTypeFor">
							<form:option value="${unitTypeFor.value}" label="${unitTypeFor.name}"/>
						</c:forEach>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>


			<div class="control-group" id="beginRowNumDiv">
				<label class="control-label">开始行号：</label>
				<div class="controls">
					<form:input path="startRow" id="beginRowNumid" value="${settleRuleSecond.startRow}" htmlEscape="false"  class="required" style="width:256px;"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>

			<div class="control-group" id="endFlgDiv">
				<label class="control-label">结束标记：</label>
				<div class="controls">
					<form:input path="endFlg" id="endFlgId" value="${settleRuleSecond.endFlg}" htmlEscape="false"  style="width:256px;"/>
				</div>
			</div>


			<div class="control-group" id="transStatusDiv">
				<label class="control-label">交易状态位置：</label>
				<div class="controls">
					<form:input path="transStatus" id="transStatus" value="${settleRuleSecond.transStatus}" htmlEscape="false"  style="width:118px;" class="add_number"/>
					<label id="lbltransStatus">-</label><form:input path="transStatusAdd" id="transStatusAdd" value="${settleRuleSecond.transStatusAdd}" htmlEscape="false"  style="width:118px;"/>(成功交易状态以#分割:"1#2")
				</div>
			</div>
			<div class="control-group" id="remarkDiv">
				<label class="control-label">摘要位置：</label>
				<div class="controls">
					<form:input path="remark" id="remarkid" value="${settleRuleSecond.remark}" htmlEscape="false"  style="width:256px;"/>
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
				<input type="hidden" id="hidRuleId" value="${settleRuleSecond.ruleId}">
			</div>
		</form:form>
	</body>
</html>