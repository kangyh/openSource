<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分润单管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
		//用于验证
		var flg = true;	
		
		$(document).ready(function() {
			$("#allRecord").click(function(){
				if($(this).attr("checked")){
					$(".exportParam").attr("checked",true);
				}else{
					$(".exportParam").attr("checked",false);
				}
				
			});
		});
		//验证搜索条件
		var validateFrom = {
			validate: function(form){
				var orderId = $("#orderId_q").val().trim();
				var warrantyId = $("#warrantyId_q").val().trim();
				var orderBatch = $("#orderBatch_q").val().trim();
				var merchantId = $("#merchantId_q").val().trim();
				if(!checkSafe(orderId)){
					
					alertx("订单号包含非法字符!");
					return ;
				}
				if(!checkSafe(warrantyId)){
					
					alertx("保单号包含非法字符!");
					return ;
				}
				if(!checkSafe(orderBatch)){
					
					alertx("订单批次号包含非法字符!");
					return ;
				}
				if(!checkSafe(merchantId)){
					
					alertx("商户编号包含非法字符!");
					return ;
				}
				
				var beginDate = $("#beginDate").val();
				var endDate = $("#endDate").val();
				if( beginDate=="" && endDate!="" || beginDate!="" && endDate=="" ){
					alertx("请正确设置查询时间范围!");
					return ;
				}
				if( beginDate!="" && endDate!=""){
					if(compareDate(convertDateToJoinStr(beginDate),
									convertDateToJoinStr(endDate)) > 0){
						alertx("起始日期不能大于结束日期!");
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
			if(flg){
				$("#pageNo").val(1);
				validateFrom.validate($("#searchForm"));
			}
		}

		//清空
		function onClear(){
			
			flg = true;
			
			$("#searchForm").find("input[type='text']").val("");
			
			//查询类型
			//$("#pcacReportOpStatus").find("option").removeAttr("selected");
			//$("#pcacReportOpStatus").find("option:eq(0)").attr("selected","selected");
		}
		
		//验证是否是数字
		function checkNum(obj){
			if(isNaN(obj)){
				alertx("请输入数字！");
				flg = false;
				return;
			}else{
				
				flg = true;
				return;
			}
		}
		
		//文件导出
		function onExport(){
			var exportParam = "";
			$(".exportParam").each(function(){
				if ( $(this).attr("checked") ) {
					exportParam = exportParam + $(this).val() + "," ;
				}
			})
			$("#exportParam").val(exportParam);
	        var actionURL = $("#searchForm").attr("action");
	        $("#searchForm").attr("action","${ctx}/prom/promProfitOrder/view/export");
			validateFrom.validate($("#searchForm"));
			$("#searchForm").attr("action",actionURL);
	        
		}
		
		function checkDelete() {
			if( !confirm("确认要删除这些记录？") ){
		    	return false;
		    }
			var x = 0 ;
			var profitIds = "";
			$(".exportParam").each(function(){
				if ( $(this).attr("checked") ) {
					x +=1;
					profitIds = profitIds + $(this).val() + "," ;
				}
			})
			if(x==0) {
				alertx("请选择要删除的记录");
				return false;
			}else{
				loading('正在提交，请稍等...');
				$.ajax({
					url:"${ctx}/prom/promProfitOrder/view/deleteManyData",
					type:"post",
					cache:false,
					timeout:10000,
					data:{
						"profitIds":profitIds
					},
					success:function(msg){
						closeLoading();
						alertx(msg, closed);
						//$("#messageBox").text(msg);
						/* $(".exportParam").each(function(){
							if ( $(this).attr("checked") ) {
								 $("#tr_"+$(this).val() ).remove();
							}
						}) */
						$("#searchForm").submit();
						return ;
					},
					error:function(){
						closeLoading();
						alertx("删除失败", closed);
						return ;
					}
				});
				
				
				//$("#reportIds_delete").val(reportIds);
				//$("#deleteForm").submit();
			}
			
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
		<li class="active"><a href="${ctx}/prom/promProfitOrder/view">分润单查询</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="promProfitOrder" action="${ctx}/prom/promProfitOrder/view" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="exportParam" name="exportParam" type="hidden" value=""/>
		<ul class="ul-form">
			<li><label>订单号：</label>
				<form:input id="orderId_q" path="orderId"  htmlEscape="false"  style="width:256px;" class="input-medium required" maxlength="32"/>
			</li>
			<li><label>保单号：</label>
				<form:input id="warrantyId_q" path="warrantyId"  htmlEscape="false"  style="width:256px;" class="input-medium required" maxlength="32"/>
			</li>
			<li><label>分润时间：</label>
				<input id="beginDate" name="beginDate" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${promProfitOrder.beginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endDate" name="endDate" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${promProfitOrder.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			
		</ul>
		<ul class="ul-form">
			
			<li><label>订单批次号：</label>
				<form:input id="orderBatch_q" path="orderBatch"  htmlEscape="false"  style="width:256px;" class="input-medium required" maxlength="32"/>
			</li>
			<li><label>商户编号：</label>
				<form:input id="merchantId_q" path="merchantId"  htmlEscape="false"  style="width:256px;" class="input-medium required" maxlength="32"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<!-- <li class="btns"><input id="btnExport" class="btn btn-primary" type="button" onclick="onExport()" value="导出"/></li> -->
			<li class="clearfix"></li>
		</ul>
		
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
				<th>序号</th>
				<th>选定<input type="checkbox" name="allRecord" id="allRecord" /></th>
				<th>订单号</th>
				<th>保单号</th>
				<th>订单批次号</th>
				<th>商户编码</th>
				<th>商户名称</th>
				<th>分润金额</th>
				<th>结算时间</th>
				<th>结算批次</th>
				<th>分润时间</th>
				<th>操作人</th>
				<th>备注</th>
		</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="psOrder" varStatus="seq">
				<tr id="tr_${psOrder.profitId}">
					<td>${seq.index+1}</td>
					<td><input type="checkbox" name="profitId" value="${psOrder.profitId}" class="exportParam"/></td>
					<td>${psOrder.orderId}</td>
					<td>${psOrder.warrantyId}</td>
					<td>${psOrder.orderBatch}</td>
					<td>${psOrder.merchantId}</td>
					<td>${psOrder.merchantName}</td>
					<td>${psOrder.profitMoney}</td>
					<td>${psOrder.settleTime}</td>
					<td>${psOrder.settleBatch}</td>
					<td>
						<fmt:formatDate value="${psOrder.profitTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>${psOrder.operator}</td>
					<td>${psOrder.remark}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>

	<div class="form-actions">
			
			<div class="control-left" style="margin-right: 10px;">
			<form id="deleteForm" action="${ctx}/prom/promProfitOrder/view/deleteManyData" method="post"  >
				<input type="hidden" name="profitIds" id="profitIds_delete" value=""/>
				<input class="btn btn-primary" type="button" value="批量删除" onclick="checkDelete()" />
			</form>
			</div>
	</div>
	
</body>
</html>