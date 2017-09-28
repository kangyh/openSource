<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/modules/sys/validation.jsp"%>
<html>
<head>
	<title>产品配置管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
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
			$("#edit_rate").on("click",function(){
				//获取选中的通道id数组
				var checked = new Array();
				$('.record_list input:checkbox[name="app_box"]:checked').each(function() {
					var bank = $(this).parent().parent().find("td").eq(2).text();
					var bankType = $(this).parent().parent().find("td").eq(3).text();
					checked.push({'bankName':bank,'bankType':bankType});
				});
				$("#msg").val(checked);
				if($("#msg").val() == ""){
					$("#msg").val("未选中任何选项，请检查！");
					parent.showThree();
					parent.changeThreeName($("#msg").val());
					return;
				}
				var rateId = $("#rateId").val();
				parent.showDynamicRate(checked,rateId);
			});

			$("#edit_some").on("click",function(){
				parent.showDynamicRate($("#bankCardType").val(),$("#rateId").val(),"card");
			});

			$("#edit_some").on("mouseover",function(){
				$(".pop_div").show();
			});

			$("#edit_some").on("mouseout",function(){
				$(".pop_div").hide();
			});

			$("#edit_rate").on("mouseover",function(){
				$(".pop_div2").show();
			});

			$("#edit_rate").on("mouseout",function(){
				$(".pop_div2").hide();
			});


		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

	</script>
	<style>
		.form-actions{
			position: relative;
		}
		.pop_div{
			width:200px;
			height: 40px;
			border:solid 1px #d8d8d8;
			position: absolute;
			top:-25px;
			left:108px;
			display: none;
		}
		.pop_div2{
			width:200px;
			height: 40px;
			border:solid 1px #d8d8d8;
			position: absolute;
			top:-25px;
			left:22px;
			display: none;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="">费率详情</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="merchantRateNewSearch" action="${ctx}/merchant/merchantRateNew/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="rateId" name="rateId" type="hidden" value="${rateId}"/>
		<ul class="ul-form">
			<input type="hidden" id="msg" name="msg" value ="${msg}"/>
			<li><label>银行名称：</label>
				<form:select path="bankNo" class="input-medium ">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getBankInfoList()}" itemLabel="bankName" itemValue="bankNo" htmlEscape="false" />
				</form:select>
			</li>
			<li><label>银行卡类型：</label>
				<form:select path="bankCardType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getEnumList('rateBankcardType')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
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
				<th><input type="checkbox" class="checkbox comm_all" value="">全选</th>
				<th>产品名称</th>
				<th>银行名称</th>
				<th>银行卡类型</th>
				<th>计费类型</th>
				<th>手续费费用</th>
				<th>手续费费用2</th>
				<th>手续费费用3</th>
				<th>手续费最大值</th>
				<th>手续费最小值</th>				
				<shiro:hasPermission name="merchant:merchantRateNew:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody class="record_list">
		<c:forEach items="${page.list}" var="merchantRateNew" varStatus="status">
			<tr>
				<td><input type="checkbox" class="checkbox" value="${status.index}" name="app_box"></td>
				<td>${merchantRateNew.productName}</td>
				<td>${merchantRateNew.bankName}</td>
				<td>${merchantRateNew.bankCardType}</td>
				<td>${merchantRateNew.chargeType}</td>
				<td>
					<c:if test="${!empty merchantRateNew.chargeMin}">
						区间${merchantRateNew.chargeMin}~${merchantRateNew.chargeMax};手续费:
					</c:if>
					<c:if test="${merchantRateNew.chargeType == '按比例'}">
						${merchantRateNew.chargeRatio}%
					</c:if>
					<c:if test="${merchantRateNew.chargeType == '按笔数'}">
						${merchantRateNew.chargeFee}元
					</c:if>
				</td>
				<td>
					<c:if test="${!empty merchantRateNew.chargeMin2}">
						区间${merchantRateNew.chargeMin2}~${merchantRateNew.chargeMax2};手续费:
						<c:if test="${merchantRateNew.chargeType == '按比例'}">
							${merchantRateNew.chargeRatio2}%
						</c:if>
						<c:if test="${merchantRateNew.chargeType == '按笔数'}">
							${merchantRateNew.chargeFee2}元
						</c:if>
					</c:if>
				</td>
				<td>
					<c:if test="${!empty merchantRateNew.chargeMin3}">
						区间${merchantRateNew.chargeMin3}~${merchantRateNew.chargeMax3};手续费:
						<c:if test="${merchantRateNew.chargeType == '按比例'}">
							${merchantRateNew.chargeRatio3}%
						</c:if>
						<c:if test="${merchantRateNew.chargeType == '按笔数'}">
							${merchantRateNew.chargeFee3}元
						</c:if>
					</c:if>
				</td>
				<td>${merchantRateNew.maxFee}</td>
				<td>${merchantRateNew.minFee}</td>
				<shiro:hasPermission name="merchant:merchantRateNew:edit"><td>
    				<a style="cursor:pointer;" class="checkPass" value-url="${ctx}/merchant/merchantRateNew/edit?id=${merchantRateNew.id}
    				&productCode=${merchantRateNew.productCode}&bankNo=${merchantRateNew.bankNo}
    				&bankCardType=${merchantRateNew.bankCardType}&bankName=${merchantRateNew.bankName}
    				&merchantId=${merchantRateNew.merchantId}">修改</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	<div class="form-actions">
		<input type="button" value="批量修改" id="edit_rate" class="btn btn-primary"/>
		<input type="button" value="按卡类型修改" id="edit_some" class="btn btn-primary"/>
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		<div class="pop_div">
			该操作是针对查询条件中的“银行卡类型”进行的批量修改
		</div>
		<div class="pop_div2">
			该操作是针对当前页面复选的银行费率信息进行的批量修改
		</div>
	</div>

</body>
</html>