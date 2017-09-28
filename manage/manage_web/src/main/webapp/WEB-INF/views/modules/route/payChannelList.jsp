<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/modules/sys/validation.jsp"%>
<html>
<head>
	<title>单表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnClear").on("click",function(){
				//console.log("11");
				$("#bankNo").val("");
				$("#channelPartnerCode").val("");
				$("#channelTypeCode").val("");
				$("#cardTypeCode").val("");
				$("#status").val("");
				$("#searchForm").submit();
			});
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
			/*$(".checkPass").on("click",function(){
				var url = $(this).attr("value-url");
				parent.showDynamicPa();
				parent.enterDynamicPa(url);
			});*/
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
<div id="importBox" class="hide">
	<form id="importForm" action="${ctx}/route/payChannel/import" method="post" enctype="multipart/form-data"
		  class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
		<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
		<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
		<a href="${ctxStatic}/route/payChannelModel.xlsx">下载模板</a>
	</form>
</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/route/payChannel/">支付通道列表</a></li>
		<shiro:hasPermission name="route:payChannel:edit"><li><a href="${ctx}/route/payChannel/form">支付通道添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="payChannel" action="${ctx}/route/payChannel/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
	        <li><label>银行名称：</label>
		    	<form:select id="bankNo" path="bankNo" name="bankNo" class="input-medium" onchange="Sel2change(this.options[this.options.selectedIndex].text);">
		    	    <option selected value="">全部</option>
				    <form:options items="${fns:getBankInfoList()}" itemLabel="bankName" itemValue="bankNo" htmlEscape="false" />
			    </form:select>
			    <input type="hidden" name="bankName" id="bankName" class="input-xlarge required">
	        </li>
	        <li><label>通道合作方：</label>
		    	<form:select  path="channelPartnerCode" name="channelPartnerCode" style="width:100px" >
	    	        <option selected value="">全部</option>
	    	        <form:options items="${fns:getEnumList('channelPartner')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
			   </form:select>
	        </li>
	        <li><label style="width:100px" >支付通道类型：</label>
	            <form:select  path="channelTypeCode" name="channelTypeCode"  style="width:150px">
                    <option selected value="">全部</option>
					<form:options items="${fns:getEnumList('channelType')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
				</form:select>
	        </li> 
	        <li><label>银行卡类型：</label>
	            <form:select  path="cardTypeCode" name="cardTypeCode"  style="width:100px">
                    <option selected value="">全部</option>
					<form:options items="${fns:getEnumList('rateBankcardType')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
			    </form:select>
	        </li>
			<li><label>状态：</label>
				<form:select id="status" path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getEnumList('commonStatus')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="submit" value="清空" /></li>
			<%--<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="通道批量导入"/></li>--%>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
		        <th>序号</th>
				<th style="display:none">支付通道代码</th>
				<th>支付通道名称</th>
				<th>银行名称</th>
				<th>通道合作方</th>
				<th>支付通道类型</th>
				<th>银行卡类型</th>
				<%--<th>有效开始时间</th>
				<th>有效结束时间</th>--%>
				<th>渠道标识</th>
				<th>成本类型</th>
				<th>成本</th>
				<th>优先级别</th>
				<th>状态</th>
				<%--<th>创建时间</th>--%>
				<th>修改人</th>
				<th>修改时间</th>
				<shiro:hasPermission name="route:payChannel:edit"><th style="width:150px">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="payChannel" varStatus="i" >
			<tr>
			    <td>${i.count}</td>
				<td  style="display:none">${payChannel.channelCode}</td>
				<td><a href="${ctx}/route/payChannel/details?id=${payChannel.id}">${payChannel.channelName}</a></td>
				<td>${payChannel.bankName}</td>
				<td>${payChannel.channelPartnerName}</td>
				<td>${payChannel.channelTypeName}</td>
				<td>${payChannel.cardTypeName}</td>
				<td>${payChannel.channelTag}</td>
				<%--<td><fmt:formatDate value="${payChannel.effectStartDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><fmt:formatDate value="${payChannel.effectEndDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>--%>
				<td>${payChannel.costType}</td>
				<td><c:if test="${payChannel.costType=='按笔数'}">${payChannel.costCount}元</c:if><c:if test="${payChannel.costType=='按比例'}">${payChannel.costRate}‰</c:if></td>
				<td>${payChannel.sort}</td>
				<td>${payChannel.status}</td>
				<%--<td><fmt:formatDate value="${payChannel.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>--%>
				<td>${payChannel.updateName}</td>
				<td><fmt:formatDate value="${payChannel.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<shiro:hasPermission name="route:payChannel:edit"><td>
				    <c:if test="${payChannel.status == '启用'}">
    					<a href="${ctx}/route/payChannel/status?id=${payChannel.id}&status=DISABL&bankNo=${payChannelFind.bankNo}&channelPartnerCode=${payChannelFind.channelPartnerCode}&channelTypeCode=${payChannelFind.channelTypeCode}&cardTypeCode=${payChannelFind.cardTypeCode}&selectStatus=${payChannelFind.status}&pageNo=${page.pageNo}" onclick="return confirmx('确认要禁用吗？', this.href)">禁用</a>
    				</c:if>
    				<c:if test="${payChannel.status == '禁用'}">
    					<a href="${ctx}/route/payChannel/status?id=${payChannel.id}&status=ENABLE&bankNo=${payChannelFind.bankNo}&channelPartnerCode=${payChannelFind.channelPartnerCode}&channelTypeCode=${payChannelFind.channelTypeCode}&cardTypeCode=${payChannelFind.cardTypeCode}&selectStatus=${payChannelFind.status}&pageNo=${page.pageNo}" onclick="return confirmx('确认要启用吗？', this.href)">启用</a>
    				</c:if>
				    <a href="${ctx}/route/payChannel/details?id=${payChannel.id}">查看</a>
					<a style="cursor:pointer;" class="checkPass" value-url="${ctx}/route/payChannel/update?id=${payChannel.id}&bankNoFind=${payChannelFind.bankNo}&channelPartnerCodeFind=${payChannelFind.channelPartnerCode}&channelTypeCodeFind=${payChannelFind.channelTypeCode}&cardTypeCodeFind=${payChannelFind.cardTypeCode}&selectStatusFind=${payChannelFind.status}&pageNo=${page.pageNo}">修改</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<div class="form-actions">
		<%--<input type="button" value="修改通道合作方" class="checkPass btn btn-primary" value-url="${ctx}/route/payChannel/batchList"/>--%>
		<input type="button" class="btn btn-primary" value="修改通道合作方" onclick="javascript:window.location='${ctx}/route/payChannel/batchList';"/>
	</div>
</body>
</html>