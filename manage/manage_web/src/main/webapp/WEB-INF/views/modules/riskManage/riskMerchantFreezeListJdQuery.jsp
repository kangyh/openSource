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
    </style>
	
	<script type="text/javascript">
		
		//验证搜索条件
		var validateFrom = {
			validate: function(form){
				
				var merchantCodeId = $("#merchantCodeId").val();
				
				var merchantLoginNameId = $("#merchantLoginNameId").val();
				
				var pattern = new RegExp("[`~!#$^&*()=|{}':;',\\[\\]<>/?~！#￥……&*（）——|{}【】‘；：”“'。，、？]") 
				if(pattern.test(merchantLoginNameId)){
					$("#messageBox").text("商户账号输入不合法，请重新输入");
					return false;
				}
				
				if(isNaN(merchantCodeId)){
					$("#messageBox").text("商户编码请输入数字类型");
					return false;
				}
				
				var freezeNoId = $("#freezeNoId").val();
				if(isNaN(freezeNoId)){
					$("#messageBox").text("冻结单号请输入数字类型");
					return false;
				}
				
				
				form.submit();
			}
		}
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//搜索
		function onSubmit(){
			$("#pageNo").val(1);
			validateFrom.validate($("#searchForm"));
		}
		//清空
		function onClear(){
			$("#formBtn").submit();
		}
		
		 
		function Sel2change(field){
			$("#channelName").val(field);
		}
		
	</script> 
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/riskManage/RiskMerchantFreezeQueryJd/query/">解冻申请列表</a></li>
	</ul>
	<form action="${ctx}/riskManage/RiskMerchantFreezeQueryJd/query/" method="post" id="formBtn"></form>
	<form:form id="searchForm" modelAttribute="riskMerchantFreeze" action="${ctx}/riskManage/RiskMerchantFreezeQueryJd/query/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
		<ul class="ul-form">
			<li><label>商户编码：</label>
					<form:input path="merchantCode" id="merchantCodeId" htmlEscape="false" maxlength="40" class="input-medium" placeholder="请输入6位数字"/>
			</li> 
			
			<li><label>商户账号：</label>
					<form:input path="merchantName" id="merchantLoginNameId" htmlEscape="false" maxlength="40" class="input-medium" placeholder="请输入商户账号"/>
			</li> 
			
			<li><label>冻结单号：</label>
					<form:input path="freezeNo" id="freezeNoId" htmlEscape="false" maxlength="40" class="input-medium" placeholder="请输入14位数字"/>
			</li> 
			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清除"/></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
		
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>商户编码</th>
			<th>商户账号</th>
			<th>冻结单号</th>
			<th>冻结申请时间</th>
			
			<th>冻结金额</th>
			
			
			<th>冻结理由</th>
			<th>冻结申请备注1</th>
			<th>冻结申请备注2</th>
			<th>冻结申请备注3</th>
			<th>冻结申请备注4</th>
			<th>冻结审核备注</th>
			<th>解冻审核备注</th>
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="riskMerchantFreeze">
		
			<c:if test="${riskMerchantFreeze.status == '冻结审核通过' ||  riskMerchantFreeze.status == '解冻审核拒绝'}">
			<tr>
				<td>${riskMerchantFreeze.merchantCode}</td>
				<td>${riskMerchantFreeze.merchantName}</td>
				<td>${riskMerchantFreeze.freezeNo}</td>
				<td>
					<fmt:formatDate value="${riskMerchantFreeze.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				
				<td>
					<fmt:formatNumber value="${riskMerchantFreeze.transAmount}" pattern="￥0.0000" />
				</td>
				
				
				<td>${riskMerchantFreeze.freezeRemark}</td>
				<td>${riskMerchantFreeze.remark1}</td>
				<td>${riskMerchantFreeze.remark2}</td>
				<td>${riskMerchantFreeze.remark3}</td>
				<td>${riskMerchantFreeze.remark4}</td>
				<td>${riskMerchantFreeze.freezeMessage}</td>
				<td>
					<c:choose>
						<c:when test="${riskMerchantFreeze.status == '冻结审核通过'}">
						--
						</c:when>
						<c:when test="${riskMerchantFreeze.status == '解冻审核拒绝'}">
						${riskMerchantFreeze.thawMessages}
						</c:when>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${riskMerchantFreeze.status == '冻结审核通过'}">  <!-- onclick="return confirm('确认提交解冻申请？');" -->
							<a   href="${ctx}/riskManage/RiskMerchantFreezeQueryJd/query/tojdquery/${riskMerchantFreeze.freezeId}/${page.pageNo}">解冻申请</a>
						</c:when>
						<c:when test="${riskMerchantFreeze.status == '解冻审核拒绝'}">
							<a  href="${ctx}/riskManage/RiskMerchantFreezeQueryJd/query/tojdquery/${riskMerchantFreeze.freezeId}/${page.pageNo}">解冻申请(原申请已拒绝)</a>
						</c:when>
					</c:choose>
				</td>		
			</c:if>	
			
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>