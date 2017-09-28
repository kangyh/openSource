<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>调拨审核</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		//提交表单  flag=1 通过    flag=2审核失败
		function btnSubmit(flag){
			if(checkReviewUser()){
				alert("审核人不能与申请人为同一人");
			}else{
				var remark = $("#remark").val();
				if(remark == null || remark == ''){
					alert("请录入审核理由");
					return false;
				}
				if(confirmReview(flag)){
					if(flag == '1'){
						$("#flag").val('1');
					}else{
						$("#flag").val('2');
					}
					loading('正在提交，请稍等...');
					$("#reviewForm").submit();
				}
			}
		}
		
		//弹出审核提示框
		function confirmReview(flag){
			if(flag == '1'){
				if(confirm("您确认通过本次资金头寸调拨吗？")){
			    	return true;
				}
			}else{
				if(confirm("您确认拒绝本次资金头寸调拨吗？")){
			    	return true;
				}
			}
			return false;
			 
		}
		
		//审核人不能喝创建人为同一人
		function checkReviewUser(){
			var creator = $("#creator").val();
			var loginName = $("#loginName").val();
			if(creator == loginName){
				return true;
			}else{
				return false;
			}
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/allocate/allocateRecordReview/">调拨列表</a></li>
	</ul>
	<form:form id="reviewForm" modelAttribute="allocateRecord" action="${ctx}/allocate/allocateRecordReview/review" method="post" class="form-horizontal">
		<input type="hidden" id="allocateId" name="allocateId" value="${allocateRecord.allocateId }"/>
		<input type="hidden" id="flag" name="flag"/>
		<input type="hidden" id="loginName" name="loginName" value="${loginName }"/>
		<input type="hidden" id="creator" name="creator" value="${allocateRecord.creator }"/>
        <div class="control-group">
			<label class="control-label">出账的备付金账户：</label>
			<div class="controls">
				${allocateRecord.reserveInAccountName} [${allocateRecord.reserveOutAccountId}]
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">入账的备付金账户：</label>
			<div class="controls">
				${allocateRecord.reserveOutAccountName} [${allocateRecord.reserveInAccountId}]
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户Id：</label>
			<div class="controls">
				${allocateRecord.merchantName} [${allocateRecord.merchantId}]
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><b>调拨金额：</b></label>
			<div class="controls">
				<fmt:formatNumber type="number" value="${allocateRecord.amount}" pattern="￥0.00" maxFractionDigits="2"/> 
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><b>原因：</b></label>
			<div class="controls">
				${allocateRecord.reason}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><b>操作人：</b></label>
			<div class="controls">
				${allocateRecord.creator}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><b>审核人：</b></label>
			<div class="controls">
				${allocateRecord.auditor}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><b>创建时间：</b></label>
			<div class="controls">
				<fmt:formatDate value="${allocateRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><b>审核时间：</b></label>
			<div class="controls">
				<fmt:formatDate value="${allocateRecord.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><b>审核意见：</b></label>
			<div class="controls">
				<form:textarea id="remark" name="remark" path="remark" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	</form:form>
	<div class="form-actions">
	   <c:if test="${not empty auditStatus}">
		   <input type="button" class="btn btn-primary" value="通过" onclick="btnSubmit('1')"/>					
		   <input type="button" class="btn btn-warning" value="拒绝" onclick="btnSubmit('2')"/>	
	   </c:if>				
	   <input type="button" class="btn" value="返回" onclick="javascript:window.location='${ctx}/allocate/allocateRecordReview/list';"/>					
	</div>
</body>
</html>