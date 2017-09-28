<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>同步数据任务记录管理</title>
	<meta name="decorator" content="default"/>
	<style>

		.white_content {
			border:1px ;    /*边框*/

			text-align: center;        /*文字水平居中对齐*/

			/* line-height: 100%;  */      /*设置文字行距等于div的高度*/
			padding-top: 200px;
			padding-bottom: 200px;
			overflow:hidden;
			display: none;
			position: fixed;
			/* padding-left:50%;
             padding-top:20%;
 */         background: rgba(3,0,17,0.28);
			top: 0%;
			left: 0%;
			width: 100%;
			height: 100%;
			z-index:1001;
			-moz-opacity: 0.8;

			filter: alpha(opacity=88);
		}
		textarea{ resize:none; width:350px; height:150px;}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/hgms/hgmsSynDataTaskRecord/">同步数据任务记录列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="hgmsSynDataTaskRecord" action="${ctx}/hgms/hgmsSynDataTaskRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>编号：</label>
				<form:input path="id" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
                <input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                       value="<fmt:formatDate value="${hgmsSynDataTaskRecord.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,maxDate:'#F{$dp.$D(\'endCreateTime\')}'});"/> -
                <input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                       value="<fmt:formatDate value="${hgmsSynDataTaskRecord.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,minDate:'#F{$dp.$D(\'beginCreateTime\')}'});"/>
			</li>
			<li><label>交易类型：</label>
				<form:select path="transType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="PAY" label="代付"/>
					<form:option value="COLLECTION" label="代收"/>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${payTransStatus}" itemLabel="name" itemValue="value" htmlEscape="false"/>
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
				<th>编号</th>
				<th>任务名称</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<th>成功时间</th>
				<th>对账开始时间</th>
				<th>对账结束时间</th>
				<th>交易类型</th>
				<th>状态</th>
				<th>操作人</th>
				<th>错误记录</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hgmsSynDataTaskRecord">
			<tr>
				<td>
					${hgmsSynDataTaskRecord.id}
				</td>
				<td>
					${hgmsSynDataTaskRecord.taskName}
				</td>
				<td>
					<fmt:formatDate value="${hgmsSynDataTaskRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${hgmsSynDataTaskRecord.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${hgmsSynDataTaskRecord.successTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${hgmsSynDataTaskRecord.checkBeginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${hgmsSynDataTaskRecord.checkEndDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${hgmsSynDataTaskRecord.transType}
				</td>
				<td>
					${hgmsSynDataTaskRecord.status}
				</td>
				<td>
					${hgmsSynDataTaskRecord.operator}
				</td>
				<td>
					${hgmsSynDataTaskRecord.errorRecord}
				</td>
                <td>
                    <c:if test="${hgmsSynDataTaskRecord.status != '成功'}">
                        <shiro:hasPermission name="hgms:hgmsSynDataTaskRecord:edit">
                        <a class="btn btn-primary" href="${ctx}/hgms/hgmsSynDataTaskRecord/cutData?id=${hgmsSynDataTaskRecord.id}" onclick = "document.getElementById('lightsucc').style.display='block'">同步信息</a>
                        </shiro:hasPermission>
                    </c:if>
                </td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<div id="lightsucc" class="white_content" >
		<font size="4px" color="white">后台正在同步信息，请耐心等待</font>
	</div>
</body>
</html>