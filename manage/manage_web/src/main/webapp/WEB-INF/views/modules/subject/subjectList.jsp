<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>科目管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#addSubject").on("click", function(){
				var url = $("#addSubject").attr("value-url"); 
				$.ajax({
					type : "POST",
					url : "${ctx}/allocate/allocateRecord/isAdminUser",
					dataType : "json",
					async : false,
					success : function(data){
						if(data){
							alert("超级管理员不允许操作此功能");
							return false;
						}else{
							parent.showDynamicPa();
							parent.enterDynamicPa(url);
							return true;
						}
					}
				});		
				
			});
			
		});
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		//清空
		function onClear(){
			$("#searchForm").find("input[type='text']").val("");
			
			$("#subjectLevel").find("option").removeAttr("selected");
			$("#subjectLevel").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#subjectLevel option[selected]").text());
			
			$("#type").find("option").removeAttr("selected");
			$("#type").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(1)").text($("#type option[selected]").text());
			
			$("#isLast").find("option").removeAttr("selected");
			$("#isLast").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(2)").text($("#isLast option[selected]").text());
			
			$("#status").find("option").removeAttr("selected");
			$("#status").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(3)").text($("#status option[selected]").text());
			
			
			
		}
		
		
		//打开或者关闭科目
		function closeOrOpenSubject(obj){
			$.ajax({
				type : "POST",
				url : "${ctx}/subject/subject/isAdminUser",
				dataType : "json",
				async : false,
				success : function(data){
					if(data){
						alert("超级管理员不允许操作此功能");
						return false;
					}else{
						var btnVal = $(obj).text().trim();
						var subjectId = $(obj).parent().parent().find("td").eq(0).text().trim();
						var levelName = $(obj).parent().parent().find("td").eq(4).text().trim();
						if(btnVal == '正常'){
							if(levelName == '一级科目'){
								if(confirm("此科目为一级科目，会将二级和三级科目一并关闭，您确认关闭此科目？")){
									showDynamicPa(obj);
								}
							}else if(levelName == '二级科目'){
								if(confirm("此科目为二级科目，会将三级科目一并关闭，您确认关闭此科目？")){
									showDynamicPa(obj);
								}
							}else{
								if(confirm("此科目为三级科目，您确认关闭此科目？")){
									showDynamicPa(obj);
								}
							}
						}else{
							if(levelName == '一级科目'){
								if(confirm("此科目为一级科目，会将二级和三级科目一并启用，您确认启用此科目？")){
									showDynamicPa(obj);
								}
							}else if(levelName == '二级科目'){
								if(confirm("此科目为二级科目，会将三级科目一并启用，您确认启用此科目？")){
									showDynamicPa(obj);
								}
							}else{
								if(confirm("此科目为三级科目，您确认启用此科目？")){
									showDynamicPa(obj);
								}
							}
						}
					}
				}
			});
		}
		
		
		//显示汇元宝令界面
		function showDynamicPa(obj){
			var url = $(obj).attr("value-url");
			parent.showDynamicPa();
			parent.enterDynamicPa(url);
			return true;
		}
		
		
		//更新科目
		function updateSub(tar){
			var url = $(tar).attr("value-url");
			$.ajax({
				type : "POST",
				url : "${ctx}/subject/subject/isAdminUser",
				dataType : "json",
				async : false,
				success : function(data){
					if(data){
						alert("超级管理员不允许操作此功能");
						return false;
					}else{
						//var url = $(this).attr("value-url");
						parent.showDynamicPa();
						parent.enterDynamicPa(url);
						return true;
					}
				}
			});
		}
		
		
		//删除科目
		function deleteSub(tar){
			var url = $(tar).attr("value-url");
			$.ajax({
				type : "POST",
				url : "${ctx}/subject/subject/isAdminUser",
				dataType : "json",
				async : false,
				success : function(data){
					if(data){
						alert("超级管理员不允许操作此功能");
						return false;
					}else{
						if(confirm("请谨慎操作，删除后不能恢复")){
						}
						parent.showDynamicPa();
						parent.enterDynamicPa(url);
						return true;
					}
				}
			});
		}
		
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/subject/subject/">科目列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="subject" action="${ctx}/subject/subject/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>科目号：</label>
				<form:input path="subjectCode" htmlEscape="false" maxlength="8" class="input-medium"/>
			</li>
			<li><label>科目级别：</label>
				<form:select id="subjectLevel" path="subjectLevel" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getEnumList('subjectLevel')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>类别：</label>
				<form:select id="type" path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getEnumList('subjectType')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>最底层科目：</label>
				<form:select id="isLast" path="isLast" class="input-medium">
					<form:option value="" label=""/>
                    <form:option value="Y" label="是"/>
                    <form:option value="N" label="否"/>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select id="status" path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getEnumList('subjectStatus')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="btns"><a id="addSubject" class="btn btn-warning" value-url="${ctx}/subject/subject/form">新增科目</a></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>科目Id</th>
				<th>科目号</th>
				<th>科目名称</th>
				<th>上级科目</th>
				<th>科目级别</th>
				<th>是否为最底层</th>
				<th>类别</th>
				<th>余额方向</th>
				<th>状态</th>
				<th>创建时间</th>
				<shiro:hasPermission name="subject:subject:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="subject">
			<tr>
				<td><a href="${ctx}/subject/subject/form?id=${subject.id}">
					${subject.subjectId}
				</a></td>
				<td>
					${subject.subjectCode}
				</td>
				<td>
					${subject.subjectName}
				</td>
				<td>
					${subject.parentSubjectName}
				</td>
				<td>
					${subject.levelName}
				</td>
				<td>
					${subject.isLastName}
				</td>
				<td>
					${subject.typeName}
				</td>
				<td>
					${subject.directionName}
				</td>
				<td>
					<c:if test="${subject.status == 'NORMAL' }">
	    				<a style="cursor:pointer;" onclick="closeOrOpenSubject(this)" class="checkPass" value-url="${ctx}/subject/subject/updateStatus?subjectId=${subject.subjectId}">正常</a>&nbsp;&nbsp;
					</c:if>
					<c:if test="${subject.status == 'CLOSED' }">
	    				<a style="cursor:pointer;" onclick="closeOrOpenSubject(this)" class="checkPass" value-url="${ctx}/subject/subject/updateStatus?subjectId=${subject.subjectId}">已关闭</a>&nbsp;&nbsp;
					</c:if>
				</td>
				<td>
					<fmt:formatDate value="${subject.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="subject:subject:edit"><td>
    				<a style="cursor:pointer;" onclick="updateSub(this)" class="checkPass" value-url="${ctx}/subject/subject/toUpdatePage?subjectId=${subject.subjectId}">修改</a>&nbsp;&nbsp;
					<a style="cursor:pointer;" onclick="deleteSub(this)" class="checkPass" value-url="${ctx}/subject/subject/delete?subjectId=${subject.subjectId}">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>