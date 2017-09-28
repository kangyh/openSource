\<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>鉴权信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//控制输入框只能输入数字
			$("#merchantId,#transNo").keyup(function(){
				 if(this.value.indexOf(".")==0){
					 this.value="0"+this.value;
				 }
				 this.value = this.value.replace(/[^\d]/g,'');
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/authentication/">鉴权信息列表</a></li>
		<shiro:hasPermission name="payment:authentication:edit"><li><a href="${ctx}/payment/authentication/form">鉴权信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="authentication" action="${ctx}/payment/authentication/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			
			
			
			<li><label>汇付宝交易号：</label>
				<form:input path="authId" htmlEscape="false" maxlength="26" class="input-medium"/>
			</li>
			<li><label>商户ID：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>鉴权状态：</label>
				
				<form:select id="status" path="status" class="input-xlarge">
					<form:option value="" label="全部"/>
					<form:option value="SUCCES" label="成功"/>
					<form:option value="FAILED" label="失败"/>
				</form:select>
			</li>
			<li><label>创建时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${authentication.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${authentication.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				
				<th>汇付宝鉴权交易号</th>
				<th>商户ID</th>
				<th>商户登录账号</th>
				<th>商户公司</th>
				<th>鉴权类型</th>
				<th>银行名称</th>
				<th>银行卡号</th>
				<th>持卡人姓名</th>
				<th>持卡人身份证号</th>
				<th>卡类型</th>
				<th>鉴权状态</th>
				<th>创建时间</th>
				<th>更新时间</th>
			
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="authentication">
			<tr>
				<td>
					${authentication.authId}
				</td>
				<td>
					${authentication.merchantId}
				</td>
				<td>
					${authentication.merchantLoginName}
				</td>
				<td>
					${authentication.merchantCompany}
				</td>
				<td>
					${fns:getDictLabel(authentication.ext1, "AuthType", authentication.ext1)}
				</td>
				<td>
					${authentication.bankName}
				</td>
				<td>
					${authentication.bankcardNo}
				</td>
				<td>
					${authentication.bankcardOwnerName}
				</td>
				<td>
					${authentication.bankcardOwnerIdcard}
				</td>
				<td>
                    <c:if test="${authentication.bankcardType=='SAVING'}">储蓄卡</c:if>
                    <c:if test="${authentication.bankcardType=='CREDIT'}">信用卡</c:if>
				</td>
				<td <c:if test="${authentication.status=='SUCCES'}"> style="background:${success_background}" </c:if> >
					<c:if test="${authentication.status=='SUCCES'}">
					    完成
					</c:if>
					<c:if test="${authentication.status!='SUCCES'}">
					    失败
					</c:if>
				</td>
				<td>
					<fmt:formatDate value="${authentication.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${authentication.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>