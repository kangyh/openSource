<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>调账详情</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		//提交表单  flag=1 通过    flag=0撤销
		function btnSubmit(flag){
			if(checkReviewUser()){
				alert("审核人不能与申请人为同一人");
			}else{
				if(confirmReview(flag)){
					if(flag == '1'){
						$("#flag").val('1');
					}else{
						$("#flag").val('0');
					}
					loading('正在提交，请稍等...');
					$("#reviewForm").submit();
				}
			}
		}
		
		//弹出审核提示框
		function confirmReview(flag){
			if(flag == '1'){
				if(confirm("您确认复核通过本次调账申请吗？")){
			    	return true;
				}
			}else{
				if(confirm("您确认撤销本次调账申请吗？")){
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
		<li><a href="${ctx}/adjust/adjustAccount/toReviewList">调账审核列表</a></li>
	</ul><br/>
	<form:form id="reviewForm" modelAttribute="adjustAccount" action="${ctx}/adjust/adjustAccount/review" method="post" class="form-horizontal">
		<input type="hidden" id="adjustId" name="adjustId" value="${adjustAccount.adjustId }"/>
		<input type="hidden" id="flag" name="flag"/>
		<input type="hidden" id="loginName" name="loginName" value="${loginName }"/>
		<input type="hidden" id="creator" name="creator" value="${adjustAccount.creator }"/>
		<div class="control-group">
			<label class="control-label">记账流水号：</label>
			<div class="controls">
				${adjustAccount.serialNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">笔数：</label>
			<div class="controls">
				${adjustAccount.serialNum}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<c:if test="${adjustAccount.status == 'AUDING'}">
					未复核
				</c:if>
				<c:if test="${adjustAccount.status == 'REVOKE'}">
					已撤销
				</c:if>
				<c:if test="${adjustAccount.status == 'ADOPT'}">
					复核通过
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<c:forEach items="${adjustAccount.images}" var="image">
					<img src="${image }"><br/>
				</c:forEach>
			</div>
		</div>
	</form:form>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="5%">套号</th>
				<th width="8%">账户类型</th>
				<th width="15%">账号</th>
				<th width="20%">户名</th>
				<th width="10%">当前余额</th>
				<th width="8%">借贷</th>
				<th width="10%">金额</th>
				<th width="15%">备注</th>
			</tr>
		</thead>
		<tbody id="tbody">
			<c:forEach items="${list}" var="adjustAccountDetail">
				<tr>
					<td>${adjustAccountDetail.seNo }</td>
					<td>${adjustAccountDetail.accountType }</td>
					<td>${adjustAccountDetail.accountid }</td>
					<td>${adjustAccountDetail.accountName }</td>
					<td>${adjustAccountDetail.balanceAmount }</td>
					<td>${adjustAccountDetail.direction }</td>
					<td>￥${adjustAccountDetail.amount }</td>
					<td>${adjustAccountDetail.remark }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="form-actions">
	   <input type="button" class="btn" value="返回" onclick="javascript:window.location='${ctx}/adjust/adjustAccount/toReviewList';"/>&nbsp;&nbsp;&nbsp;					
	   <c:if test="${not empty auditStatus}">
		   <input type="button" class="btn btn-warning" value="撤销" onclick="btnSubmit('0')"/>&nbsp;&nbsp;&nbsp;					
		   <input type="button" class="btn btn-primary" value="复核通过" onclick="btnSubmit('1')";"/>		
	   </c:if>			
	</div>	
</body>
</html>