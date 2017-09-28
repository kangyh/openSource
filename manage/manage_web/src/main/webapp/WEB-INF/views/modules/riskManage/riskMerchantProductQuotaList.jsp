<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>限额管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
		//用于验证
		var flg = true;	
		
		$(document).ready(function() {
			$(".checkPass").on("click",function(){
				var url = $(this).attr("value-url");
				parent.showDynamicPa();
				parent.enterDynamicPa(url);
			})
		});
		//验证搜索条件
		var validateFrom = {
			validate: function(form){
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
			if(flg){
				$("#pageNo").val(1);
				validateFrom.validate($("#searchForm"));
			}
		}

		//清空
		function onClear(){
			$("#messageBox").text("");
			flg = true;
			
			$("#searchForm").find("input[type='text']").val("");
			
			//产品名称
			$("#productCode").find("option").removeAttr("selected");
			$("#productCode").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#productCode option[selected]").text()); 
			
			//账户类型
			$("#updateAuthor").find("option").removeAttr("selected");
			$("#updateAuthor").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(1)").text($("#updateAuthor option[selected]").text()); 
		}
		
		//验证是否是数字
		function checkNum(obj){
			if(isNaN(obj)){
				$("#messageBox").text("请输入数字！");
				flg = false;
				return;
			}else{
				$("#messageBox").text("");
				flg = true;
				return;
			}
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/riskManage/riskMerchantProductQuotaQuery/">商户限额列表</a></li>
		<shiro:hasPermission name="riskManage:riskMerchantProductQuota:edit"><li><a style="cursor:pointer;" class="checkPass"  value-url="${ctx}/riskManage/riskMerchantProductQuotaQuery/edit">商户限额添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="riskMerchantProductQuota" action="${ctx}/riskManage/riskMerchantProductQuotaQuery/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<c:if test="${flg == 'true'}">
				<li class="btns">
					<input id="btnCancel" class="btn btn-primary" type="button" value="返 回" onclick="history.go(-1)"/>
				</li>
			</c:if>
			<li><label>商户编码：</label>
				<form:input path="merchantId" htmlEscape="false" onkeyup="checkNum(this.value)" maxlength="17" class="input-medium required"/>
			</li>
			<li><label>产品名称：</label>
				<form:select id="productCode" path="productCode" class="input-xlarge">
               		<form:option value="" label="请选择"/>
					<c:forEach items="${prodList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>操作人：</label>
				<form:select id="updateAuthor" path="updateAuthor" class="input-xlarge">
               		<form:option value="" label="请选择"/>
					<c:forEach items="${uAList}" var="wStatus">
						<form:option value="${wStatus.updateAuthor}" label="${wStatus.updateAuthor}"/>
					</c:forEach>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="clearfix"></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>商户编码</th>
			<th>商户账户</th>
			<th>商户公司名</th>
			<th>产品名称</th>
			<th>公私标记</th>
			<th>银行卡类型</th>
			<th>单笔限额</th>
			<th>单日限额</th>
			<th>单月限额</th>
			<th>创建时间</th>
			<th>创建者</th>
			<th>更新时间</th>
			<th>更新者</th>
			<th>状态</th>
			<shiro:hasPermission name="riskManage:riskMerchantProductQuota:edit"><th>操作</th></shiro:hasPermission>
		</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="riskMerchantProductQuota">
				<tr>
					<td>${riskMerchantProductQuota.merchantId}</td>
					<td>${riskMerchantProductQuota.merchantAccount}</td>
					<td>${riskMerchantProductQuota.merchantName}</td>
					<td>${riskMerchantProductQuota.productName}</td>
					<td>${riskMerchantProductQuota.accountType}</td>
					<td>
						<c:if test='${riskMerchantProductQuota.accountType == "对公"}'>
							--
						</c:if>
						<c:if test='${riskMerchantProductQuota.accountType != "对公"}'>
							${riskMerchantProductQuota.bankcardType}
						</c:if>
					</td>
					<td>
						<fmt:formatNumber value="${riskMerchantProductQuota.perItem}" pattern="￥0.0000" />
					</td>
					<td>
						<fmt:formatNumber value="${riskMerchantProductQuota.perDay}" pattern="￥0.0000" />
					</td>
					<td>
						<fmt:formatNumber value="${riskMerchantProductQuota.perMonth}" pattern="￥0.0000" />
					</td>
					
					<td>
						<fmt:formatDate value="${riskMerchantProductQuota.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>${riskMerchantProductQuota.createAuthor}</td>
					<td>
						<fmt:formatDate value="${riskMerchantProductQuota.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>${riskMerchantProductQuota.updateAuthor}</td>
					<td <c:choose>
			     		<c:when test="${riskMerchantProductQuota.status=='启用'}">
			     		 style="background:#6cf683" 	
			     		</c:when>
			     		<c:when test="${riskMerchantProductQuota.status=='禁用'}">
			     		 style="background:red" 
			     		</c:when>
			     	</c:choose>>${riskMerchantProductQuota.status}</td>
					<shiro:hasPermission name="riskManage:riskMerchantProductQuota:edit"><td>
						<c:if test='${riskMerchantProductQuota.status == "启用"}'>
							<a href="${ctx}/riskManage/riskMerchantProductQuotaQuery/status?status=DISABL&merProId=${riskMerchantProductQuota.merProId}">禁用</a> | 
						</c:if>
						<c:if test='${riskMerchantProductQuota.status == "禁用"}'>
							<a href="${ctx}/riskManage/riskMerchantProductQuotaQuery/status?status=ENABLE&merProId=${riskMerchantProductQuota.merProId}">启用</a> |
						</c:if>
						<a style="cursor:pointer;" class="checkPass"  value-url="${ctx}/riskManage/riskMerchantProductQuotaQuery/edit?merProId=${riskMerchantProductQuota.merProId}">修改</a>
					</td></shiro:hasPermission>					
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>