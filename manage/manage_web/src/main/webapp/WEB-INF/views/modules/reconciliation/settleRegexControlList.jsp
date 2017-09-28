<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>清结算参数维护</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
		
		$(document).ready(function() {
		});
		//验证搜索条件
		var validateFrom = {
			validate: function(form){
				var bgTime = $("#beginCreateTime").val();
				var endTime = $("#endCreateTime").val();
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
			$("#searchForm").find("input[type='text']").val("");
			$("#messageBox").text("");
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/reconciliation/settleRegexControl/">规则验证列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="settleRegexControl" action="${ctx}/reconciliation/settleRegexControl/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>创建时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${settleRegexControl.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${settleRegexControl.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
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
			<th>规则key</th>
			<th>规则名称</th>
			<th>正则表达式</th>
			<th>规则类型</th>
			<th>创建时间</th>
			<th>创建人</th>
			<%--<th>更新时间 </th>
			<th>更新人</th>--%>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="settleRegexControl">
			<tr>
				<td>${settleRegexControl.ruleKey}</td>
				<td>${settleRegexControl.regexName}</td>
				<td>${settleRegexControl.regexShow}</td>
				<td <c:choose>
					<c:when test="${settleRegexControl.ruleType=='一代规则'}">
						style="background:#6cf683"
					</c:when>
					<c:when test="${settleRegexControl.ruleType=='二代规则'}">
						style="background:#707c9b"
					</c:when>
				</c:choose>>${settleRegexControl.ruleType}</td>
				<td><fmt:formatDate value="${settleRegexControl.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${settleRegexControl.createAuthor}</td>
				<%--<td><fmt:formatDate value="${settleRegexControl.updateTime}" type="both" pattern="yyyy-MM-dd"/></td>
				<td>${settleRegexControl.updateAuthor}</td>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>