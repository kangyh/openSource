<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/modules/sys/validation.jsp"%>
<html>
<head>
	<title>产品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//交易类型根据业务类型联动方法
			getProductTrxType($("#businessType").val());
			showLocation();
			function showLocation() {
				var title = '<option value="">全部</option>';
				$("#trxType").select2();
				$('#businessType').change(function(){
					$('#trxType').empty();
					$('#trxType').parent().find(".select2-chosen").html("");
					$('#trxType').append(title);
					getProductTrxType($("#businessType").val());
				});
			}
			if($('#trxType1').val() != '' && $('#trxType1').val() != null){
				$("#trxType").find("option[value='"+$('#trxType1').val()+"']").attr("selected",true);
				$("#trxType").parent().find(".select2-chosen").text($("#trxType").find("option[value='"+$('#trxType1').val()+"']").text());
			}
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//根据业务类型获取交易类型方法
		function getProductTrxType(businessType){
			var el  = $('#trxType');
			$.ajax({
				async: false,
				type: "GET",
				url: "${ctx}/merchant/product/getProductTrxType",
				data: {"businessType":businessType},
				dataType: 'json',
				success: function(data){
					$.each(data , function(k , v) {
						var option  = '<option value="'+v.code+'">'+v.name+'</option>';
						el.append(option);
					})
				},
				error: function(){
					console.log("请求失败!");
				}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/merchant/product/">产品列表</a></li>
		<shiro:hasPermission name="merchant:product:edit"><li><a style="cursor:pointer;" class="checkPass" value-url="${ctx}/merchant/product/form">产品增加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="product" action="${ctx}/merchant/product/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="trxType1" type="hidden" value="${product.trxType}"/>
		<ul class="ul-form">
			<li><label>产品名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>业务类型：</label>
                <form:select path="businessType" class="input-medium ">
                    <form:option value="" label="全部"/>
                    <form:options items="${fns:getEnumList('rateBusinessType')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
            <li><label>交易类型：</label>
                <form:select path="trxType" class="input-medium">
                    <form:option value="" label="全部"/>
					<form:options items="${fns:getProductTrxType()}" itemLabel="name" itemValue="code" htmlEscape="false"/>
                </form:select>
            </li>
			<li><label>审核状态：</label>
				<form:select id="status" path="auditStatus" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getEnumList('routeStatus')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select id="status" path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getEnumList('commonStatus')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>产品编码</th>
				<th>产品名称</th>
				<th>业务类型</th>
				<th>交易类型</th>
				<th>审核状态</th>
				<th>状态</th>
				<th>备注</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="product">
			<tr>
				<td><a href="${ctx}/merchant/product/details?id=${product.id}">${product.code}</a></td>
				<%--<td>${product.code}</td>--%>
				<td>${product.name}</td>
				<td>${product.businessType}</td>
				<td>${product.trxType}</td>
				<td>${product.auditStatus}</td>
				<td>${product.status}</td>
				<td>${product.remark}</td>
				<td>
					<a href="${ctx}/merchant/product/details?id=${product.id}">查看</a>
					<shiro:hasPermission name="merchant:product:edit">
					</shiro:hasPermission>
    				<c:choose>
					    <c:when test="${product.auditStatus == '审核通过'}">
						    <shiro:hasPermission name="merchant:product:audit">
							    <c:if test="${product.status == '启用'}">
			    					<a href="${ctx}/merchant/product/status?id=${product.id}&status=DISABL" onclick="return confirmx('确认要禁用吗？', this.href)">禁用</a>
			    				</c:if>
			    				<c:if test="${product.status == '禁用'}">
			    					<a href="${ctx}/merchant/product/status?id=${product.id}&status=ENABLE" onclick="return confirmx('确认要启用吗？', this.href)">启用</a>
			    				</c:if>
		    				</shiro:hasPermission>
					    </c:when>
					    <c:otherwise>
					    <shiro:hasPermission name="merchant:product:edit">
							<a style="cursor:pointer;" class="checkPass" value-url="${ctx}/merchant/product/form?id=${product.id}">修改</a>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="merchant:product:audit">
							<a href="${ctx}/merchant/product/delete?id=${product.id}" onclick="return confirmx('确认要删除该产品吗？', this.href)">删除</a>
						</shiro:hasPermission>
					    </c:otherwise>
					</c:choose>
    				<c:if test="${product.auditStatus == '待审核'}">
	    				<shiro:hasPermission name="merchant:product:audit">
	    				   <a style="cursor:pointer;" class="checkPass" value-url="${ctx}/merchant/product/audit?id=${product.id}" >审核</a>
	    				</shiro:hasPermission>
    				</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>