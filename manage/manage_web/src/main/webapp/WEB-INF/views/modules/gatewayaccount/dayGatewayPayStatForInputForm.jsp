<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>网关支付核账管理</title>
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
		});
		
		//录入核对笔数
		function inputCheckCount(obj){
			var successCount = $("#successBillCount").text().trim();
			var diffCount = accSub(successCount, $(obj).val())
			$("#diffCount").text(diffCount);
			
		}
		
		//录入核对金额
		function inputCheckAmount(obj){
			var successAmount = $("#successTradeAmt").text().replace("￥","").replaceAll(",","");
			var checkAmount = $(obj).val();
			var diffAmount = accSub(successAmount, checkAmount);
			$("#diffAmount").text(diffAmount);
			
			//计算实际差异金额
			var settleAmount = $("input[name='inputSettleAmt']").val();
			var diffCheckAmount = accSub(checkAmount, settleAmount);
			$("#diffCheckAmount").text(diffCheckAmount);
			
		}
		
		
		function inputSettleAmount(obj){
			var checkAmount = $("input[name='inputCheckSuccessAmt']").val();
			var settleAmount = $(obj).val();
			var diffCheckAmount = accSub(checkAmount, settleAmount);
			$("#diffCheckAmount").text(diffCheckAmount);
		}
		
		
		//两个浮点数做减法
		function accSub(num1,num2){  
	       var r1,r2,m;  
	       try{  
	           r1 = num1.toString().split('.')[1].length;  
	       }catch(e){  
	           r1 = 0;  
	       }  
	       try{  
	           r2=num2.toString().split(".")[1].length;  
	       }catch(e){  
	           r2=0;  
	       }  
	       m=Math.pow(10,Math.max(r1,r2));  
	       return (Math.round(num1*m-num2*m)/m).toFixed(2);  
	    } 
		
		String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {    
		    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {    
		        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);    
		    } else {    
		        return this.replace(reallyDo, replaceWith);    
		    }    
		}    
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/gatewayAccount/dayGatewayPayStatForInput/">网关支付核账列表</a></li>
		<li class="active"><a href="${ctx}/gatewayAccount/dayGatewayPayStatForInput/form?id=${dayGatewayPayStatForInput.id}">网关支付核账<shiro:hasPermission name="gatewayAccount:dayGatewayPayStatForInput:edit">${not empty dayGatewayPayStatForInput.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="gatewayAccount:dayGatewayPayStatForInput:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="dayGatewayPayStatForInput" action="${ctx}/gatewayAccount/dayGatewayPayStatForInputOk/update" method="post" class="form-horizontal">
		<input type="hidden" name="recordId" value="${dayGatewayPayStatForInput.recordId }" />
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">银行名称：</label>
			<div class="controls">
				${dayGatewayPayStatForInput.bankName }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通道合作方：</label>
			<div class="controls">
				${dayGatewayPayStatForInput.channelPartnersName }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成功笔数：</label>
			<div class="controls" id="successBillCount">
				${dayGatewayPayStatForInput.successBillCount }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成功金额：</label>
			<div class="controls" id="successTradeAmt">
				<fmt:formatNumber value="${dayGatewayPayStatForInput.successTradeAmt }"  type="number" pattern="￥#,##0.00" />
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">核对笔数：</label>
			<div class="controls">
				<input name="inputCheckSuccessCount" value="${dayGatewayPayStatForInput.inputCheckSuccessCount }" maxlength="6" htmlEscape="false" style="width:60px" class="input-xlarge required" onblur="inputCheckCount(this)"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">核对金额：</label>
			<div class="controls">
				<input name="inputCheckSuccessAmt" value="<fmt:formatNumber value="${dayGatewayPayStatForInput.inputCheckSuccessAmt }"  type="number" pattern="0.00" />" class="input-xlarge required" htmlEscape="false" style="width:100px" onblur="inputCheckAmount(this)"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成功笔数—核对笔数：</label>
			<div class="controls">
				<label id="diffCount" name="diffCount">${dayGatewayPayStatForInput.failCount }</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成功金额—核对金额：</label>
			<div class="controls">
				<label id="diffAmount" name="diffAmount">
					<fmt:formatNumber value="${dayGatewayPayStatForInput.failAmount }"  type="number" pattern="#,##0.00" />
				</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">核对金额差异说明：</label>
			<div class="controls">
				<input name="inputNote" value="${dayGatewayPayStatForInput.inputNote }" maxlength="200" htmlEscape="false" class="input-xlarge required" style="width:200px" />
				<span class="help-inline"><font color="red">*</font> </span>			
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行实际到账资金：</label>
			<div class="controls">
				<input name="inputSettleAmt" value="<fmt:formatNumber value="${dayGatewayPayStatForInput.inputSettleAmt }"  type="number" pattern="0.00" />" htmlEscape="false" class="input-xlarge required" style="width:100px" onblur="inputSettleAmount(this)"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">核对金额—实际到账资金：</label>
			<div class="controls">
				<label id="diffCheckAmount" name="diffCheckAmount">
					<fmt:formatNumber value="${dayGatewayPayStatForInput.realFailAmount }"  type="number" pattern="#,##0.00" />
				</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实际资金差异说明：</label>
			<div class="controls">
				<input name="checkNote" value="${dayGatewayPayStatForInput.checkNote }" maxlength="200" htmlEscape="false" class="input-xlarge required" style="width:200px"/>
				<span class="help-inline"><font color="red">*</font> </span>			
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="gatewayAccount:dayGatewayPayStatForInput:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>