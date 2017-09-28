<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/modules/sys/validation.jsp"%>
<html>
<head>
	<title>清结算字典管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		
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
			
			//类型
			$("#typeId").find("option").removeAttr("selected");
			$("#typeId").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#typeId option[selected]").text());
			
			//明细
			$("#value").find("option").removeAttr("selected");
			$("#value").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(1)").text($("#value option[selected]").text());
			
			//状态
			$("#status").find("option").removeAttr("selected");
			$("#status").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(2)").text($("#status option[selected]").text());
		}
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/settleDic/settleDicItemQuery/">字典明细列表</a></li>
		<shiro:hasPermission name="settleDic:settleDicItem:edit"><li><a style="cursor:pointer;" class="checkPass"  value-url="${ctx}/settleDic/settleDicItemQuery/add">字典明细添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="settleDicItem" action="${ctx}/settleDic/settleDicItemQuery/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>字典类型：</label>
				<form:select id="typeId" path="typeId" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${typeList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select> 
			</li>
			<li><label>明细VALUE：</label>
				<form:select id="value" path="value" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${itemList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select> 
			</li>
			<li><label>状态：</label>
				<form:select id="status" path="status" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${statusList}" var="wStatus">
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
			<th>明细KEY</th>
			<th>明细VALUE</th>
			<th>字典类型</th>
			<th>创建者</th>
			<th>创建时间</th>
			<th>更新者</th>
			<th>更新时间</th>
			<th>状态</th>
			<shiro:hasPermission name="settleDic:settleDicItem:edit"><th>操作</th></shiro:hasPermission>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="settleDicItem">
			<tr>
				<td>${settleDicItem.text}</td>
				<td>${settleDicItem.value}</td>
				<td>${settleDicItem.typeText}</td>
				<td>${settleDicItem.createOperator}</td>
				<td>
					<fmt:formatDate value="${settleDicItem.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>${settleDicItem.updateOperator}</td>
				<td>
					<fmt:formatDate value="${settleDicItem.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td <c:choose>
			     		<c:when test="${settleDicItem.status=='生效'}">
			     		 style="background:#6cf683" 	
			     		</c:when>
			     		<c:when test="${settleDicItem.status=='无效'}">
			     		 style="background:red" 
			     		</c:when>
			     	</c:choose>>${settleDicItem.status}</td>
				<shiro:hasPermission name="settleDic:settleDicItem:edit"><td>
					<a style="cursor:pointer;" class="checkPass"  value-url="${ctx}/settleDic/settleDicItemQuery/add?itemId=${settleDicItem.itemId}">修改</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>