<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品管理管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		//验证搜索条件
		var validateFrom = {
			validate: function(form){
				
				var productId = $("#productId").val().trim();
				var importBath = $("#importBath").val().trim();
				if(!checkSafe(productId)){
					
					$("#messageBox").text("产品编码包含非法字符!");
					return ;
				}
				if(!checkSafe(importBath)){
					
					$("#messageBox").text("导入批次号包含非法字符!");
					return ;
				}
				
				var bgTime = $("#beginOperEndTime").val();
				var endTime = $("#endOperEndTime").val();
				if(bgTime=="" && endTime!="" || bgTime!="" && endTime=="" ){
					$("#messageBox").text("请正确设置查询时间范围!");
					return ;
				}
				if( bgTime!="" && endTime!=""){
					if(compareDate(convertDateToJoinStr(bgTime),
									convertDateToJoinStr(endTime)) > 0){
						$("#messageBox").text("起始日期不能大于结束日期!");
						return ;
					}
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
		
		//导入数据
		function onImport(){
			  var actionURL = $("#searchForm").attr("action");
		        $("#searchForm").attr("action",$("#searchForm").attr("action")+"onImport");
		        validateFrom.validate($("#searchForm"));
				$("#searchForm").attr("action",actionURL);
		}
		
		function checkSafe(a){
			if ( a.indexOf("script")>-1 || a.indexOf("\\")>-1 ){
				return false;
			}
			fibdn = new Array ("'",">","<","*","%","#","$","}","{","~","`","!","￥","/","?","&","^","(",")","=",";");
			i=fibdn.length;
			j=a.length;
			for (ii=0; ii<i; ii++) {
				for (jj=0; jj<j; jj++) {
					temp1=a.charAt(jj);
					temp2=fibdn[ii];
					if (temp1==temp2){
					return false;
					}
				}
			}
			
			return true;
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/prom/promProduct/">产品管理列表</a></li>
		<%-- <shiro:hasPermission name="prom:promProduct:edit"><li><a href="${ctx}/prom/promProduct/form">产品管理添加</a></li></shiro:hasPermission> --%>
	</ul>
	
	<form action="${ctx}/prom/promProduct/" method="post" id="formBtn"></form> 
	
	<form:form id="searchForm" modelAttribute="promProduct" action="${ctx}/prom/promProduct/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			
			<li><label>产品名称：</label>
				<form:input path="productName"  htmlEscape="false" maxlength="64" style="width:256px;" class="input-medium required"/>
			</li>
			<li><label>产品编码：</label>
				<form:input path="productId"  id="productId" htmlEscape="false" maxlength="64" style="width:256px;" class="input-medium required"/>
			</li>
		</ul> 
		<ul class="ul-form">
		    <li><label>修改时间：</label>
				<input id="beginOperEndTime" name="beginOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${promProduct.beginOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endOperEndTime" name="endOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${promProduct.endOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>导入批次号：</label>
				<form:input path="importBath"  id="importBath" htmlEscape="false" maxlength="64" style="width:256px;" class="input-medium required"/>
			</li>
			 <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
	         <li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
	         <li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onImport()" value="导入数据"/></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>产品编码</th>
				<th>产品名称</th>
				<!-- <th>档位编码</th>
				<th>档位名称</th> -->
				<th>推广金额</th>
				<th>推广比例</th>
				<th>导入批次号</th>
				<th>导入时间</th>
				<th>导入人</th>
				<%-- <shiro:hasPermission name="prom:promProduct:edit"><th>操作</th></shiro:hasPermission> --%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="promProduct">
			<tr>
				<td>${promProduct.productId}</td>
				<td>${promProduct.productName}</td>
				<%-- <td>${promProduct.subjectType}</td>
				<td>${promProduct.gearName}</td> --%>
				<td>${promProduct.spreadMoney}</td>
				<td>${promProduct.spreadScale}</td>
				<td>${promProduct.importBath}</td>
				<td><fmt:formatDate value="${promProduct.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${promProduct.creator}</td>
				<%-- <shiro:hasPermission name="prom:promProduct:edit"><td>
    				<a href="${ctx}/prom/promProduct/form?id=${promProduct.id}">修改</a>
					<a href="${ctx}/prom/promProduct/delete?id=${promProduct.id}" onclick="return confirmx('确认要删除该产品管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>