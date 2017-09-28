<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/modules/sys/validation.jsp"%>
<html>
<head>
	<title>单表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function() {
    });
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/route/certifyChannel/">产品列表</a></li>
		<li class="active"><a href="${ctx}/route/certifyChannel/details?id=${product.id}">产品信息</a></li>
	</ul><br/>
	<form:form  modelAttribute="product"  method="post" class="form-horizontal">
	<sys:message content="${message}"/>
        <div class="control-group">
			<label class="control-label">产品名称：</label>
			<div class="controls">
				${product.name}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				${product.status}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><b>支付通道</b></label>
		</div>
		<table id="contentTable" class="table table-striped table-bordered table-condensed" style="width:1200px">
			<thead>
				<tr>
				    <th>支付通道名称</th>
					<th>通道合作方</th>					
					<th>成本</th>
					<th>手续费结算类型</th>
					<th>手续费结算周期</th>
					<th>优先级</th>
					<th>修改人</th>
					<shiro:hasPermission name="route:certifyChannel:edit"><th>操作</th></shiro:hasPermission>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${page.list}" var="certifyChannel" >
				<tr>
					<td>${certifyChannel.channelName}</td>
					<td>${certifyChannel.channelPartnerName}</td>
					<td>${certifyChannel.cost}元</td>
				    <td>${certifyChannel.settleType}</td>
					<td>${certifyChannel.settlePeriod}</td>
					<td><c:if test="${empty certifyChannel.sort}">无</c:if><c:if test="${certifyChannel.sort=='1'}">默认</c:if></td>
					<td>${certifyChannel.updateName}</td>
					<shiro:hasPermission name="route:certifyChannel:edit"><td>
						<a style="cursor:pointer;" class="checkPass" value-url="${ctx}/route/certifyChannel/update?id=${certifyChannel.id}&productId=${product.id}">修改</a>
					</td></shiro:hasPermission>
				</tr>
			</c:forEach>
			</tbody>
	    </table>		
		<div class="form-actions">
			<input type="button" class="checkPass btn btn-primary" value="添加通道" value-url="${ctx}/route/certifyChannel/form?productId=${product.id}"/>
			<input type="button" class="btn" value="返回" onclick="javascript:window.location='${ctx}/route/certifyChannel/list';"/>
		</div>
	</form:form>
</body>
</html>