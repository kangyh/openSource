<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
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
					$("#searchForm").submit();
				});
				/*增加产品的支付通道*/
				$(".add_list").on("click",function(){
					var _this = $(this);
					var merchantId = $(this).attr("data-merchantId");
                    var productCode = $(this).attr("data-productCode");
                    var productName = $(this).attr("data-productName");
					var payChannelId = $(this).attr("data-payChannelId");
					var companyName = $(this).attr("data-companyName");
					var bankNo = $(this).attr("data-bankNo");
					var channelPartnerCode = $(this).attr("data-channelPartnerCode");
					var channelTypeCode = $(this).attr("data-channelTypeCode");
					var cardTypeCode = $(this).attr("data-cardTypeCode");
					var pageNo = $(this).attr("data-pageNo");
					//避免重复提交
					if(_this.hasClass('submintIn')){ return false; }
					_this.text("增加中...").addClass('submintIn');
					$.ajax({
						type: "GET",
						url: "${ctx}/route/merchantPayChannel/addChannel",
						data: {'merchantId':merchantId,'productCode':productCode,'productName':productName,'id':payChannelId,'companyName':companyName,'bankNo':bankNo,'channelPartnerCode':channelPartnerCode,'channelTypeCode':channelTypeCode,'cardTypeCode':cardTypeCode,'pageNo':pageNo},
								success: function(){
							window.location.reload();
						},
						error: function(){
							console.log("请求失败!");
						}
					});

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
				//点击增加
				$("#add_channel").on("click",function(){
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
					var merchantId = $("#merchantId").val();
					var companyName = $("#companyName").val();
                    var productCode = $("#productCode").val();
                    var productName = $("#productName").val();
					var bankNo = $("#bankNo").val();
					var channelPartnerCode = $("#channelPartnerCode").val();
					var channelTypeCode = $("#channelTypeCode").val();
					var pageNo = $("#pageNo").val();
					var cardTypeCode = $("#cardTypeCode").val();
					$.ajax({
						type: "GET",
						url: "${ctx}/route/merchantPayChannel/addBatchChannel",
						data: {'merchantId':merchantId,'productCode':productCode,'productName':productName,'checkedstr':checkedstr,'companyName':companyName,'bankNo':bankNo,'channelPartnerCode':channelPartnerCode,'channelTypeCode':channelTypeCode,'cardTypeCode':cardTypeCode,'pageNo':pageNo},
						success: function(){
							window.location.reload();
						},
						error: function(){
							console.log("请求失败!");
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
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="payChannel" action="${ctx}/route/merchantPayChannel/add" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<input type="hidden" id="msg" name="msg" value ="${msg}"/>
		    <div class="control-group">
			<label class="control-label">商户名称：${companyName}</label>
				<label class="control-label">产品名称：${productName}</label>
		    </div>
	        <li><label>银行名称：</label>
		    	<form:select id="bankNo" path="bankNo" name="bankNo" class="input-medium" onchange="Sel2change(this.options[this.options.selectedIndex].text);">
		    	    <option selected value="">全部</option>
				    <form:options items="${fns:getBankInfoList()}" itemLabel="bankName" itemValue="bankNo" htmlEscape="false" />
			    </form:select>
	        </li>
	        <li><label>通道合作方：</label>
		    	<form:select  path="channelPartnerCode" name="channelPartnerCode" style="width:100px" >
	    	        <option selected value="">全部</option>
	    	        <form:options items="${fns:getEnumList('channelPartner')}" itemLabel="name" itemValue="value" htmlEscape="false" class=""/>
			   </form:select>
			</li>
	        <li><label  style="width:150px">支付通道类型：</label>
	            <form:select  path="channelTypeCode" name="channelTypeCode"  style="width:100px">
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
			<%--查询功能，为add方法传参数--%>
	        <input type="hidden" name="merchantId" id="merchantId" class="input-xlarge required" value="${merchantId}">
	        <input type="hidden" name="companyName" id="companyName" class="input-xlarge required" value="${companyName}">
			<input type="hidden" name="productCode" id="productCode" class="input-xlarge required" value="${productCode}">
			<input type="hidden" name="productName" id="productName" class="input-xlarge required" value="${productName}">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="submit" value="清空" /></li>
			<li class="clearfix"></li>
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
				<shiro:hasPermission name="route:merchantPayChannel:edit"><th style="width:150px">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody class="record_list">
		<c:forEach items="${page.list}" var="payChannel">
			<tr>
				<c:if test="${payChannel.merchantPayChannelId!=null}"><td><input type="checkbox" checked="checked" disabled="disabled"></td></c:if>
				<c:if test="${empty payChannel.merchantPayChannelId}"><td><input type="checkbox" class="checkbox" value="${payChannel.id}" name="app_box"></td></c:if>
				<td>${payChannel.channelName}</td>
				<td>${payChannel.bankName}</td>
				<td>${payChannel.channelPartnerName}</td>
				<td>${payChannel.channelTypeName}</td>
				<td>${payChannel.cardTypeName}</td>
				<shiro:hasPermission name="route:merchantPayChannel:edit"><td>
				<c:if test="${payChannel.merchantPayChannelId!=null}"><span>已增加</span></c:if>
				<c:if test="${empty payChannel.merchantPayChannelId}">
					<a style ="cursor: pointer"  class="add_list" data-merchant="${merchant}" data-payChannelId="${payChannel.id}" data-merchantId="${merchantId}" data-companyName="${companyName}" data-productCode="${productCode}" data-productName="${productName}" data-bankNo="${bankNo}" data-channelPartnerCode="${channelPartnerCode}" data-channelTypeCode="${channelTypeCode}" data-cardTypeCode="${cardTypeCode}" data-pageNo="${page.pageNo}">增加</a>
				</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<div class="form-actions">
		<input type="button" value="提交已选通道" id="add_channel" class="checkPass btn btn-primary"/>
		<input type="button" class="btn" value="返回" onclick="javascript:window.location='${ctx}/route/merchantPayChannel/details?id=${merchantId}';"/>
	</div>
	<input type="hidden" id="merchantId" value="${merchantId}">
	<input type="hidden" id="companyName" value="${merchant.companyName}">
	<input type="hidden" id="productCode" value="${productCode}">
	<input type="hidden" id="productName" value="${productName}">
	<input type="hidden" id="bankNo" value="${bankNo}">
	<input type="hidden" id="channelPartnerCode" value="${channelPartnerCode}">
	<input type="hidden" id="channelTypeCode" value="${channelTypeCode}">
	<input type="hidden" id="pageNo" value="${page.pageNo}">
	<input type="hidden" id="cardTypeCode" value="${cardTypeCode}">
</body>
</html>