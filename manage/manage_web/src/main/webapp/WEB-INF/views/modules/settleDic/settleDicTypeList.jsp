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
			$("#code").find("option").removeAttr("selected");
			$("#code").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#code option[selected]").text());
			
			//状态
			$("#status").find("option").removeAttr("selected");
			$("#status").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(1)").text($("#status option[selected]").text());
		}
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/settleDic/settleDicTypeQuery/">字典类型列表</a></li>
		<shiro:hasPermission name="settleDic:settleDicType:edit"><li><a style="cursor:pointer;" class="checkPass"  value-url="${ctx}/settleDic/settleDicTypeQuery/add">字典类型添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="settleDicType" action="${ctx}/settleDic/settleDicTypeQuery/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>字典类型：</label>
				<form:select id="code" path="code" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${typeList}" var="wStatus">
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
			<th>字典类型编码</th>
			<th>字典类型名称</th>
			<th>创建者</th>
			<th>创建时间</th>
			<th>更新者</th>
			<th>更新时间</th>
			<th>状态</th>
			<shiro:hasPermission name="settleDic:settleDicType:edit"><th>操作</th></shiro:hasPermission>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="settleDicType">
			<tr>
				<td>${settleDicType.code}</td>
				<td>${settleDicType.text}</td>
				<td>${settleDicType.createOperator}</td>
				<td>
					<fmt:formatDate value="${settleDicType.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>${settleDicType.updateOperator}</td>
				<td>
					<fmt:formatDate value="${settleDicType.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td <c:choose>
			     		<c:when test="${settleDicType.status=='生效'}">
			     		 style="background:#6cf683" 	
			     		</c:when>
			     		<c:when test="${settleDicType.status=='无效'}">
			     		 style="background:red" 
			     		</c:when>
			     	</c:choose>>${settleDicType.status}</td>
				<shiro:hasPermission name="settleDic:settleDicType:edit"><td>
					<a style="cursor:pointer;" class="checkPass"  value-url="${ctx}/settleDic/settleDicTypeQuery/add?typeId=${settleDicType.typeId}">修改</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>