<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>限额管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
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
			$("#pageNo").val(1);
			validateFrom.validate($("#searchForm"));
		}

		//清空
		function onClear(){
			$("#searchForm").find("input[type='text']").val("");
			
			//产品名称
			$("#productCode").find("option").removeAttr("selected");
			$("#productCode").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#productCode option[selected]").text()); 
			
			//账户类型
			$("#accountType").find("option").removeAttr("selected");
			$("#accountType").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(1)").text($("#accountType option[selected]").text()); 
			
			//银行卡类型
			$("#bankcardType").find("option").removeAttr("selected");
			$("#bankcardType").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(2)").text($("#bankcardType option[selected]").text()); 
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a id="url_id" href="${ctx}/riskManage/riskProductQuotaQuery/">产品限额列表</a></li>
		<shiro:hasPermission name="riskManage:riskProductQuota:edit"><li><a style="cursor:pointer;" class="checkPass"  value-url="${ctx}/riskManage/riskProductQuotaQuery/edit">产品限额添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="riskProductQuota" action="${ctx}/riskManage/riskProductQuotaQuery/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<c:if test="${flg == 'true'}">
				<li class="btns">
					<input id="btnCancel" class="btn btn-primary" type="button" value="返 回" onclick="history.go(-1)"/>
				</li>
			</c:if>
			<li><label>产品名称：</label>
				<form:select id="productCode" path="productCode" class="input-xlarge">
               		<form:option value="" label="请选择"/>
					<c:forEach items="${prodList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>公私标记：</label>
				<form:select id="accountType" path="accountType" class="input-xlarge">
               		<form:option value="" label="请选择"/>
					<c:forEach items="${rATList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>银行卡类型：</label>
				<form:select id="bankcardType" path="bankcardType" class="input-xlarge">
               		<form:option value="" label="请选择"/>
					<c:forEach items="${rBTList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
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
			<shiro:hasPermission name="riskManage:riskProductQuota:edit"><th>操作</th></shiro:hasPermission>
		</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="riskProductQuota">
				<tr>
					<td>${riskProductQuota.productName}</td>
					<td>${riskProductQuota.accountType}</td>
					<td>
						<c:if test='${riskProductQuota.accountType == "对公"}'>
							--
						</c:if>
						<c:if test='${riskProductQuota.accountType != "对公"}'>
							${riskProductQuota.bankcardType}
						</c:if>
					</td>
					<td>
						<fmt:formatNumber value="${riskProductQuota.perItem}" pattern="￥0.0000" />
					</td>
					<td>
						<fmt:formatNumber value="${riskProductQuota.perDay}" pattern="￥0.0000" />
					</td>
					<td>
						<fmt:formatNumber value="${riskProductQuota.perMonth}" pattern="￥0.0000" />
					</td>
					
					<td>
						<fmt:formatDate value="${riskProductQuota.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>${riskProductQuota.createAuthor}</td>
					<td>
						<fmt:formatDate value="${riskProductQuota.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>${riskProductQuota.updateAuthor}</td>
					<td <c:choose>
			     		<c:when test="${riskProductQuota.status=='启用'}">
			     		 style="background:#6cf683" 	
			     		</c:when>
			     		<c:when test="${riskProductQuota.status=='禁用'}">
			     		 style="background:red" 
			     		</c:when>
			     	</c:choose>>${riskProductQuota.status}</td>
					<shiro:hasPermission name="riskManage:riskProductQuota:edit"><td>
						<c:if test='${riskProductQuota.status == "启用"}'>
							<a href="${ctx}/riskManage/riskProductQuotaQuery/status?status=DISABL&proId=${riskProductQuota.proId}">禁用</a> | 
						</c:if>
						<c:if test='${riskProductQuota.status == "禁用"}'>
							<a href="${ctx}/riskManage/riskProductQuotaQuery/status?status=ENABLE&proId=${riskProductQuota.proId}">启用</a> | 
						</c:if>
						<a style="cursor:pointer;" class="checkPass"  value-url="${ctx}/riskManage/riskProductQuotaQuery/edit?proId=${riskProductQuota.proId}">修改</a>
					</td></shiro:hasPermission>					
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>