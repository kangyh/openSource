<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
	        $(".checkPass").on("click",function(){
	            var url = $(this).attr("value-url");
	            parent.showDynamicPa();
	            parent.enterDynamicPa(url);
	        });
			$("#btnClear").on("click",function(){
				$("#bankNo").val("");
				$("#channelPartnerCode").val("");
				$("#channelTypeCode").val("");
				$("#cardTypeCode").val("");
				$("#searchFormDetail").submit();
			});
			//全选，反选
			$(".comm_all").off().on("click",function(){
				var comm_all = $(".comm_all");
				var goods_delete = document.getElementsByName("app_box");
				for(var i=0;i<goods_delete.length;i++){
					if(comm_all.is(':checked')){
						goods_delete[i].checked = true;
					}else{
						goods_delete[i].checked = false;
					}
				}
			});
			//单选取消全选
			$(".record_list").delegate("input[name=app_box]","click",function(){
				var comm_all = $(".comm_all");
				var goods_delete = document.getElementsByName("app_box");
				for(var i=0,n=0;i<goods_delete.length;i++){
					if(goods_delete[i].checked){
						n++;
					}
					if(n==goods_delete.length){
						$(".comm_all").attr("checked",true);
					}else{
						$(".comm_all").attr("checked",false);
					}
				}
			});

			//点击批量删除
			$("#del_channel").on("click",function(){
				//获取选中的通道id数组
				var checked = [];
				$('.record_list input:checkbox[name="app_box"]:checked').each(function() {
					checked.push($(this).val());
				});
				var checkedstr = checked.join(",");
				$("#msg").val(checkedstr);
				if($("#msg").val() == ""){
					$("#msg").val("未选中通道，请检查！");
					parent.showThree();
					parent.changeThreeName($("#msg").val());
					return;
				}
				return confirmx('确认要删除该批支付通道吗？','${ctx}/route/productChannel/delBatchChannel?productId=${product.id}&pageNo=${page.pageNo}&checkedstr='+checkedstr);
			});

	    });
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
		    $("#searchFormDetail").submit();
	    	return false;
	    };
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/route/productChannel?cache=1">产品列表</a></li>
		<li class="active"><a href="${ctx}/route/productChannel/details?id=${product.id}">产品信息</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="product" method="post" class="form-horizontal">
		<input id="msg" name="msg" type="hidden"value ="${msg}"/>
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
	</form:form>
	<form:form id="searchFormDetail" modelAttribute="productDetail" action="${ctx}/route/productChannel/selectChannelDetail?productId=${product.id}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>银行名称：</label>
				<form:select id="bankNo" path="bankNo" name="bankNo" class="input-medium">
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
			<li><label style="display:none">产品代码：</label>
				<input type="hidden" path="productCode" name="productCode" value="${product.code}" class="input-xlarge required">
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="submit" value="清空" /></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
		<table id="contentTable" class="table table-striped table-bordered table-condensed" style="width:1200px">
			<thead>
				<tr>
					<th><input type="checkbox" class="checkbox comm_all" value="">全选</th>
				    <th>支付通道名称</th>
					<th>银行名称</th>
					<th>通道合作方</th>
					<th>支付通道类型</th>
					<th>银行卡类型</th>
					<th>创建人</th>
					<shiro:hasPermission name="route:productChannel:edit"><th>操作</th></shiro:hasPermission>
				</tr>
			</thead>
			<tbody class="record_list">
			<c:forEach items="${page.list}" var="productDetail" >
				<tr>
					<td><input type="checkbox" class="checkbox" value="${productDetail.id}" name="app_box"></td>
					<td>${productDetail.channelName}</td>
					<td>${productDetail.bankName}</td>
					<td>${productDetail.channelPartnerName}</td>
					<td>${productDetail.channelTypeName}</td>
					<td>${productDetail.cardTypeName}</td>
					<td>${productDetail.createName}</td>
					<shiro:hasPermission name="route:productChannel:edit"><td>
						<a id="a" href="${ctx}/route/productChannel/delete?id=${productDetail.id}&productId=${product.id}&pageNo=${page.pageNo}" onclick="return confirmx('确认要删除该支付通道吗？', this.href)">删除</a>
					</td></shiro:hasPermission>
				</tr>
			</c:forEach>
			</tbody>
	    </table>
	    <div class="pagination">${page}</div>		
		<div class="form-actions">
			<input type="button" value="添加通道" class="checkPass btn btn-primary" value-url="${ctx}/route/productChannel/add?productCode=${product.code}&productId=${product.id}&productName=${product.name}"/>
			<input type="button" value="删除已选通道" id="del_channel" class="btn btn-primary"/>
			<input type="button" class="btn" value="返回" onclick="javascript:window.location='${ctx}/route/productChannel/list?cache=1';"/>
		</div>
</body>
</html>