<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>风控管理</title>
<meta name="decorator" content="default"/>
<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
<style>
       #main {
           margin: 50px;
       }
       .modal-body{
       		width:285px;
       		margin:auto;
       		position: relative;
    		max-height: 400px;
    		padding: 10px;
    		overflow-y: inherit;
       }
        .modal-body label{
        	width:90px;
        	text-align: right;
        }
        .pop{
        	display:none;
        	margin-left:17px;
        }
        .pop label{
        	width:90px;
        	text-align: right;
        }
   </style>
<script type="text/javascript">
	$(document).ready(function() {
		$("#inputForm").validate({
			submitHandler: function(form){
				var merchantCodeId=$('#merchantCodeId').val();
				var transAmountId=$('#transAmountId').val();
				if(isNaN(merchantCodeId)){
					$("#type_val_info").text("商户编码请输入数字");
					$("#type_val_info").removeAttr("style");
					return false;
				}else if(isNaN(transAmountId)){
					$("#type_val_info2").text("金额请输入数字");
					$("#type_val_info2").removeAttr("style");
					return false;
				}else{
					/* 弹出框显示查询 */
					$('#fenpeiModal').modal('show');
					$(".pop").show();
						 /* var merchantCodeId2 = $("#merchantCodeId2").val(); */
						 var merchantCodeId = $("#merchantCodeId").val();
						 var transAmountId = $("#transAmountId").val();
						 var remark1 = $("#remark1").val();
						 var remark2 = $("#remark2").val();
						 var remark3 = $("#remark3").val();
						 var remark4 = $("#remark4").val();
						 var freezeRemark = $("#freezeRemark").val();
						 
						 $("#merchantCodeId2").text(merchantCodeId);
						 $("#transAmountId2").text('￥ '+transAmountId);
						 
						 $("#remark11").text(remark1);
						 $("#remark22").text(remark2);
						 $("#remark33").text(remark3);
						 $("#remark44").text(remark4);
						 if(freezeRemark=='CC'){
							 $("#freezeRemark2").text('用户投诉');
						 }else{
							 $("#freezeRemark2").text('公安协查');
						 }
						 
					
					$("#modalID").click(function(){
					
					loading('正在提交，请稍等...');
					form.submit();
		   	       
				});
					
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
	function Sel2change(field){
		
		$("#channelName").val(field);
	}
	
	function closeModal(modalID,bodyId){
    	$('#'+modalID).modal('hide');
    }
	
	var flag=true;
	
	function changeValue(value){
		
		 if(!isNaN(value) && value !="" ){
			$.post("${ctx}/riskManage/RiskMerchantFreezeQuery/add/ajax",{
				merchantCode:value
			},function(msg){
				
				var	index=msg.lastIndexOf('+');
				if(index !=-1){
					
					var name = msg.substring(0, index);
					var val = msg.substring(msg.length-1, msg.length);
					
					if(val =='1'){
						$("#btnSubmit").removeAttr("disabled");
						$("#type_val_infoName").text("商户账号:"+name);
						$("#type_val_info").text("");
						$("#type_val_info").removeAttr("style");
						flag=true;
						
					}else{
						$("#btnSubmit").attr("disabled","disabled");
						flag=false;
					}
				}else{
					$("#btnSubmit").attr("disabled","disabled");
					$("#type_val_info").text(msg);
					$("#type_val_infoName").text("");
					$("#type_val_info").removeAttr("style");
					flag=false;
				}
				
				
			},"text");
		}else{
			$("#merchantCodeId").val("");
			$("#type_val_info").text("商户编码请输入整数");
			$("#type_val_info").removeAttr("style");
			return false;
		}
	
		/* var url= "${ctx}/riskManage/RiskMerchantFreezeQuery/add/ajax";
		if(!isNaN(value) && value !=""){
			var dataParam={"merchantCode":value};
			var collBack=function(msg){
			if(msg !=''){
				$("#btnSubmit").attr("disabled","disabled");
			}else{
				$("#btnSubmit").removeAttr("disabled");
			}
			$("#type_val_info").text(msg);
			$("#type_val_info").removeAttr("style");
			};
			$.post(url,dataParam,collBack);
			
		}else{
			$("#type_val_info").text("商户编码请输入数字");
			$("#type_val_info").removeAttr("style");
			return false;
		} */
	}

	
	function changeAmount(value){
		
		var num = /^([1-9][\d]{0,14}|0)(\.[\d]{1,12})?$/;
		var num2 = /^([0-9][\d]{1,14}|0)(\.[\d]{1,4})?$/;
		var num3 = /^([0][\d]{0,4}|0)?$/;
		
		if(!num.test(value)){ /* isNaN(value) */
			$("#transAmountId").val("");
			$("#type_val_info2").text("金额类型错误");
			$("#type_val_info2").removeAttr("style");
		}else{
			
			var	idnum=value.lastIndexOf('.');
			if(idnum !=-1){
				var valu = value.substring(0, idnum);
				
				if(valu=='0'){
					var va = value.substring(idnum+1, value.length);
						if(va=='0' || va =='00' || va=="000" ||va=="0000"){
							checkValue(valu);
						}else{
							$("#type_val_info2").text("");
						}
					
				}else{
					if(num2.test(value)){
						//如果正确则什么都不做
					}else{
						var	index=value.lastIndexOf('.');
						var value = value.substring(0, index+5);
						$("#transAmountId").val(value);
					}
				}
			}else{
				checkValue(value);
			}
			if(num2.test(value)){
				//如果正确则什么都不做
			}else{
				var	index=value.lastIndexOf('.');
				var value = value.substring(0, index+5);
				
				$("#transAmountId").val(value);
				/* $("#type_val_info2").text("小数后面最多4位小数");
				   $("#type_val_info2").removeAttr("style");
				*/
			}
		}
		
	}
		function checkValue(value){
			if(value=='0'){
				$("#type_val_info2").text("冻结金额必须大于 ￥0");
				$("#transAmountId").val("");
				$("#btnSubmit").attr("disabled","disabled");
			}else{
				$("#type_val_info2").text("");
				if(flag){
					
					$("#btnSubmit").removeAttr("disabled");
					$("#type_val_info2").removeAttr("style");
				}
			}
		}
		function toHome(){
			var refer=$('[name="referer"]').val();
			var pageNo=$('[name="pageNo"]').val();
			
			var lastIndex=refer.substr(refer.length-1,refer.length);
			if(!isNaN(lastIndex)) {
				window.location = refer;
			}else if(lastIndex != "/" ){
				window.location = refer + "?pageNo=" + pageNo;
			} else {
				window.location = refer + "?pageNo=" + pageNo;
			}s
		}
		
</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li class="active"><a>冻结申请</a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="riskMerchantFreeze" action="${ctx}/riskManage/RiskMerchantFreezeQuery/saveEntity" method="post" class="form-horizontal">
	<sys:message content="${message}"/>
	
	<c:choose>
		<c:when test="${not empty riskMerchantFreeze.merchantCode}">
			<div class="control-group">
				<label class="control-label">商户编码：</label>
				<div class="controls">
					<form:input path="merchantCode" id="merchantCodeId" value="${riskMerchantFreeze.merchantCode}" readonly="true"   htmlEscape="false" maxlength="6" onchange="changeValue(this.value)" class="required" placeholder="请输入6位数字" style="width:300px;"/>
					<label  class="info">商户名称:${msg}</label>
					<label class="error">${error}</label>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="control-group">
				<label class="control-label">商户编码：</label>
				<div class="controls">
					<form:input path="merchantCode" id="merchantCodeId" value="${riskMerchantFreeze.merchantCode}" htmlEscape="false" maxlength="6" onchange="changeValue(this.value)" class="required" placeholder="请输入6位数字" style="width:300px;"/>
					<span class="help-inline"><font color="red">*</font> </span>
					<label id="type_val_infoName" for="typeId" class="info"></label>
					<label id="type_val_info" for="typeId" class="error"></label>
				</div>
		</div>
		</c:otherwise>
	</c:choose>
	
	
	<c:choose>
		<c:when test="${not empty  riskMerchantFreeze.transAmount}">
			<div class="control-group">
				<label class="control-label">冻结金额：</label>
				<div class="controls">
					<form:input path="transAmount" id="transAmountId" value="${riskMerchantFreeze.transAmount}" readonly="true" onchange="changeAmount(this.value)" pattern="￥0.0000" htmlEscape="false" maxlength="14" class="required" placeholder="小数点后面最多4位小数" style="width:300px;"/>
					<label  class="info">${amountMsg}</label>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="control-group">
				<label class="control-label">冻结金额：</label> 
				<div class="controls">
					<form:input path="transAmount"  id="transAmountId" value="${riskMerchantFreeze.transAmount}"  onchange="changeAmount(this.value)" pattern="￥0.0000" htmlEscape="false" maxlength="14" class="required" placeholder="小数点后面最多4位小数" style="width:300px;"/>
					<span class="help-inline"><font color="red">*</font> </span>
					<label id="type_val_info2" for="typeId55" class="error"></label>
				</div>
		</div>
		</c:otherwise>
	</c:choose>
	
	 <c:choose>
		<c:when test="${not empty riskMerchantFreeze.remark1}">
			<div class="control-group">
				<label class="control-label">申请备注1：</label>
				<div class="controls">
				<form:input path="remark1" id="remark1" readonly="true"  value="${riskMerchantFreeze.remark1}" htmlEscape="false" maxlength="20" placeholder="请输入备注信息,不超过20个数" style="width:300px;"/>
			</div>
		</div>
		</c:when>
		<c:otherwise>
			<div class="control-group">
				<label class="control-label">申请备注1：</label>
				<div class="controls">
					<form:input path="remark1" id="remark1"  value="${riskMerchantFreeze.remark1}" htmlEscape="false" maxlength="20" placeholder="请输入备注信息,不超过20个数" style="width:300px;"/>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
	
	
	<div class="control-group">
		<label class="control-label">申请备注2：</label>
		<div class="controls">
			<form:input path="remark2" id="remark2"  value="${riskMerchantFreeze.remark2}" htmlEscape="false" maxlength="20" placeholder="请输入备注信息,不超过20个数" style="width:300px;"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">申请备注3：</label>
		<div class="controls">
			<form:input path="remark3" id="remark3"  value="${riskMerchantFreeze.remark3}" htmlEscape="false" maxlength="20" placeholder="请输入备注信息,不超过20个数" style="width:300px;"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">申请备注4：</label>
		<div class="controls">
			<form:input path="remark4" id="remark4"  value="${riskMerchantFreeze.remark4}" htmlEscape="false" maxlength="20" placeholder="请输入备注信息,不超过20个数" style="width:300px;"/>
		</div>
	</div>
	
	<div class="control-group">
		<label class="control-label">冻结理由：</label>
		<div class="controls">
		    <form:select id="freezeRemark" path="freezeRemark" class="input-xlarge required" style="width:314px;">
				<c:forEach items="${riskFreezeRemark}" var="riskFreezeRemarkStatus">
					<form:option value="${riskFreezeRemarkStatus.value}" label="${riskFreezeRemarkStatus.name}" />
				</c:forEach>
			</form:select> 
		</div>
	</div>
	
	
	<input id="refererid" value="${referer}" type="hidden" name="referer"/>
	<input value="${pageNo}" type="hidden" name="pageNo"/>
		
	<div class="form-actions">
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="提交申请"/>
		<c:if test="${not empty referer}">
			<input id="btnCancel" class="btn" type="button" value="返回" onclick="history.go(-1)" style="margin-left:50px"/>
			<%--<a id="btnCancel" class="btn" onclick="toHome()">返回</a>  href="${ctx}/riskManage/RiskMerchantFreezeQuery/add/referer/${pageNo}/${referer}" --%>
		</c:if>
	</div>
</form:form>

<!-- 模态框（Modal）  -->
<div class="modal fade" id="fenpeiModal" tabindex="-1" role="dialog" 
  style="width:400px;" aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true" onclick="closeModal('fenpeiModal','selectUser')">
                  &times;
            </button>
            <h4 class="modal-title"  id="titleName">请确认后提交</h4>
         </div>
         <div class="pop">
        	
        	<div class="control-group">
				<label class="control-label">商户编码：</label>
				<span id="merchantCodeId2"></span>
			</div>
        
			
			<div class="control-group">
				<label class="control-label">冻结金额：</label>
				<span  id="transAmountId2"></span>
			</div>
		
        
			
			<div class="control-group">
				<label class="control-label">申请备注1：</label>
				<span  id="remark11"></span>
			</div>
        
			
			<div class="control-group">
				<label class="control-label">申请备注2：</label>
				<span  id="remark22"></span>
			</div>
        
			<div class="control-group">
				<label class="control-label">申请备注3：</label>
				<span  id="remark33"></span>
			</div>
			<div class="control-group">
				<label class="control-label">申请备注4：</label>
				<span  id="remark44"></span>
			</div>
			<div class="control-group">
				<label class="control-label">冻结理由：</label>
				<span id="freezeRemark2"></span>
			</div>
		</div>
         <div class="modal-footer">
            <button type="button" class="btn btn-default" 
               data-dismiss="modal" onclick="closeModal('fenpeiModal','selectUser')">取消
            </button>
            <button type="button" id='modalID' class="btn btn-primary">
               确认
            </button>
         </div>
      </div>
	</div>
</div>
 
</body>
</html>