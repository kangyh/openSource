<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>调账管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".checkPass").on("click",function(){
				var valueUrl =  $(this).attr("value-url");
				$.ajax({
					type : "POST",
					url : "${ctx}/adjust/adjustAccount/isAdminUser",
					dataType : "json",
					async : false,
					success : function(data){
						if(data){
							alert("超级管理员不允许操作此功能");
							return false;
						}else{
							parent.showDynamicPa();
							parent.enterDynamicPa(valueUrl);
							return true;
						}
					}
				});			
			});
		});
		//验证搜索条件
		var validateFrom = {
			validate: function(form){
				var maxYear = 1;	//默认查询一年
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
					}else if(compareYear(convertDateToJoinStr(bgTime),
									convertDateToJoinStr(endTime),maxYear) < 0){
						$("#messageBox").text("查询的时间范围不能超过" + maxYear + "年!");
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
			validateFrom.validate($("#searchForm"));
		}
		//清空
		function onClear(){
			$("#searchForm").find("input[type='text']").val("");
			//默认升降排序
			$("#status").find("option").removeAttr("selected");
			$("#status").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#status option[selected]").text());
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/adjust/adjustAccount/toReviewList">调账审核列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="adjustAccount" action="${ctx}/adjust/adjustAccount/toReviewList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>记账流水号：</label>
				<form:input path="serialNo" onchange="checkValue(this.value)" htmlEscape="false" maxlength="16" class="input-medium required"/>
			</li>
			<li><label>记账操作员：</label>
				<form:input path="creator" onchange="checkValue(this.value)" htmlEscape="false" class="input-medium required"/>
			</li>
			<li><label>审核员：</label>
				<form:input path="auditor" onchange="checkValue(this.value)" htmlEscape="false" class="input-medium required"/>
			</li>
			<li><label>状态：</label>
				<form:select id="status" path="status" class="input-xlarge ">
                   <form:option value="" label="全部"/>
                   <form:option value="AUDING" label="未复核"/>
                   <form:option value="REVOKE" label="已撤销"/>
                   <form:option value="ADOPT" label="复核通过"/>
               </form:select>
			</li>
			<li><label>创建时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${adjustAccount.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,maxDate:'#F{$dp.$D(\'endCreateTime\')}'});"/> —
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${adjustAccount.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,minDate:'#F{$dp.$D(\'beginCreateTime\')}'});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>Id</th>
				<th>流水号</th>
				<th>笔数</th>
				<th>状态</th>
				<th>记账操作人</th>
				<th>审核人</th>
				<th>调账原因</th>
				<th>记账时间</th>
				<shiro:hasPermission name="adjust:adjustAccount:review"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="adjustAccount">
			<tr>
				<td><a href="${ctx}/adjust/adjustAccount/reviewDetails?adjustId=${adjustAccount.adjustId}">
					${adjustAccount.adjustId}
				</a></td>
				<td>
					${adjustAccount.serialNo}
				</td>
				<td>
					${adjustAccount.serialNum}
				</td>
				<td>
					<c:if test="${adjustAccount.status == 'AUDING'}">
						未复核
					</c:if>
					<c:if test="${adjustAccount.status == 'REVOKE'}">
						已撤销
					</c:if>
					<c:if test="${adjustAccount.status == 'ADOPT'}">
						复核通过
					</c:if>
				</td>
				<td>
					${adjustAccount.creator}
				</td>
				<td>
					${adjustAccount.auditor}
				</td>
				<td>
					${adjustAccount.reason}
				</td>
				<td>
					<fmt:formatDate value="${adjustAccount.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="adjust:adjustAccount:review">
					<td>
						<c:if test="${adjustAccount.status == 'AUDING'}">
		    				<a href="${ctx}/adjust/adjustAccount/reviewDetails?adjustId=${adjustAccount.adjustId}">查看详情</a>&nbsp;&nbsp;
		    				<a style="cursor:pointer;" class="checkPass" value-url="${ctx}/adjust/adjustAccount/toReviewPage?adjustId=${adjustAccount.adjustId}&auditStatus=true">复核</a>
						</c:if>
						<c:if test="${adjustAccount.status == 'REVOKE'}">
		    				<a href="${ctx}/adjust/adjustAccount/reviewDetails?adjustId=${adjustAccount.adjustId}">查看详情</a>
						</c:if>
						<c:if test="${adjustAccount.status == 'ADOPT'}">
		    				<a href="${ctx}/adjust/adjustAccount/reviewDetails?adjustId=${adjustAccount.adjustId}">查看详情</a>
						</c:if>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>