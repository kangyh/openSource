<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商户打款认证管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/validateNum.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".pay").click(function(){
				var a = $(this).parent().parent().find("td").eq(3).text();
				var merchantId = $(this).parent().parent().find("td").eq(0).text();
				var id = $(this).parent().parent().find("input").val();
				$.post("${ctx}/merchant/merchantBankCardAuthentication/getMoney",{"merchantId":merchantId},function(data){
					parent.showDynamicMoney();
					parent.changeName("认证打款",a,data,id);
				},"text");
			});
			$(".checkPass").on("click",function(){
                var url = $(this).attr("value-url");
                parent.showDynamicPa();
                parent.enterDynamicPa(url);
            });
			$(".getBankNo").click(function(){
				var _this = $(this);
				var id = $(this).parent().parent().find("input").val();
				$.post("${ctx}/merchant/merchantBankCardAuthentication/getBankNo",{"id":id},function(data){
					_this.parent().parent().find("td").eq(3).text(data);
				},"text");
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function search(){
			var flag = validatePreventInject($("#merchantCompanyName").val(),"商户公司名称输入不合法!");
			return flag;
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/merchant/merchantBankCardAuthentication/">商户打款认证列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="merchantBankCardAuthentication" action="${ctx}/merchant/merchantBankCardAuthentication/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户编码：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="8" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}
					else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label style="width:100px">商户公司名称：</label>
				<form:input path="merchantCompanyName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>打款状态：</label>
				<form:select id="payStatus" path="payStatus" class="input-xlarge">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getEnumList('remitStatus')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>认证状态：</label>
				<form:select id="authenticationStatus" path="authenticationStatus" class="input-xlarge">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getEnumList('authenticationStatus')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>打款时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${merchantBankCardAuthentication.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,maxDate:'#F{$dp.$D(\'endCreateTime\')}'});"/> -
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${merchantBankCardAuthentication.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,minDate:'#F{$dp.$D(\'beginCreateTime\')}'});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" onclick="return search()" value="查询"/></li>
			<li class="clearfix"></li>
			<div id="messageBox" style="font-size: 15px; color: red;"></div>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户编码</th>
				<th>商户公司名称</th>
				<th>法人代表</th>
				<th>银行卡号</th>
				<th>打款金额</th>
				<th>打款状态</th>
				<th>认证金额</th>
				<th>认证状态</th>
				<th>打款时间</th>
				<shiro:hasPermission name="merchant:merchantBankCardAuthentication:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchantBankCardAuthentication">
			<tr>
				<input type="hidden" value="${merchantBankCardAuthentication.id}" />
				<td>${merchantBankCardAuthentication.merchantId}</td>
				<td>${merchantBankCardAuthentication.merchantCompanyName}</td>
				<td>${merchantBankCardAuthentication.legalRepresentative}</td>
				<td>${merchantBankCardAuthentication.bankNo}</td>
				<td>${merchantBankCardAuthentication.payAmount}</td>
				<td>${merchantBankCardAuthentication.payStatus}</td>
				<td>${merchantBankCardAuthentication.authenticationAmount}</td>
				<td>${merchantBankCardAuthentication.authenticationStatus}</td>
				<td><fmt:formatDate value="${merchantBankCardAuthentication.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<shiro:hasPermission name="merchant:merchantBankCardAuthentication:edit"><td>
    				<c:if test="${merchantBankCardAuthentication.payStatus == '待打款'}">
    					<a class="pay" href="javascript:void(0);">打款</a>
    				</c:if>
    				<c:if test="${merchantBankCardAuthentication.payStatus == '打款失败' && empty merchantBankCardAuthentication.authenticationStatus}">
    				    <a style="cursor:pointer;" class="checkPass" value-url="${ctx}/merchant/merchantBankCardAuthentication/form?id=${merchantBankCardAuthentication.id}">修改</a>
	    				<a class="pay" href="javascript:void(0);">再次打款</a>
    				</c:if>
    				<c:if test="${merchantBankCardAuthentication.payStatus == '打款成功' && merchantBankCardAuthentication.authenticationStatus == '认证失败'}">
                        <a style="cursor:pointer;" class="checkPass" value-url="${ctx}/merchant/merchantBankCardAuthentication/form?id=${merchantBankCardAuthentication.id}">修改</a>
                    </c:if>
					<shiro:hasPermission name="merchant:merchantBankCard:get">
						<a href="javascript:void(0);" class="getBankNo">查看卡号</a>
					</shiro:hasPermission>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>