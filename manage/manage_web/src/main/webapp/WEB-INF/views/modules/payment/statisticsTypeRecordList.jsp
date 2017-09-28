<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分类交易数据统计管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnSubmit").on("click",function(){
				var merchantId = $("#merchantId").val();
				var merchantCompany = $("#merchantCompany").val();

				var beginStatisticsTime = $("#beginStatisticsTime").val();
				var endStatisticsTime = $("#endStatisticsTime").val();
				
				if(!isEmpty(merchantId)&&!/\d+/.test(merchantId)){
					alert("商户编码输入不合法，只能输入数字");
					return ;
				}
				if(!isEmpty(merchantCompany)&&!/^[a-zA-Z0-9\u4e00-\u9fa5()（）]+$/.test(merchantCompany)){
					alert("商户公司名称输入不合法，请重新输入");
					return ;
				}
				
				if(endStatisticsTime<beginStatisticsTime){
					alert("结束时间不能小于开始时间");
					return ;
				}
				
				$("#searchForm").submit();
			});
			
			//excel导出功能
		 	$("#exportExcel").on("click",function(){
				var merchantId = $("#merchantId").val();
				var merchantCompany = $("#merchantCompany").val();
				var transType = $("#transType").val();
				var productCode =$("#productCode").val();
				var payType = $("#payType").val();
				var beginStatisticsTime = $("#beginStatisticsTime").val();
				var endStatisticsTime = $("#endStatisticsTime").val();
				
				
				var host = "${ctx}/payment/statisticsTransRecord/exportExcel";
				var url = host+"?merchantId="+merchantId+"&merchantCompany="+merchantCompany+"&transType="+transType+"&productCode="+productCode+"&payType="+payType+"&beginStatisticsTime="+beginStatisticsTime+"&endStatisticsTime="+endStatisticsTime;
				$('<form method="post" action="' + url + '"></form>').appendTo('body').submit().remove();
				
			});
			
			$("#statistics").on("click",function(){
				var beginStatisticsTime = $("#beginStatisticsTime").val();
				var endStatisticsTime = $("#endStatisticsTime").val();
				var url = "${ctx}/payment/statisticsTransRecord/statistics";
				//var url = host+"?beginStatisticsTime="+beginStatisticsTime+"&endStatisticsTime="+endStatisticsTime;
				var data={
						beginStatisticsTime:beginStatisticsTime,
						endStatisticsTime:endStatisticsTime
				}
				
				$.ajax({
					type:"post",
		 			url:url,
		 			data:data,
		 			success:function(data){
		 				alert(data.msg);
		 			},
		 			 error:function(jqXHR){
		 				var data = eval("("+jqXHR.responseText+")");
		 				alert(data.msg);
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
		
		function isEmpty(value){
	 		if(value==undefined||value==""||value.trim()==""){
	 			return true;
	 		}
	 		return false;
	 	}
		//清空
		function onClear(){
			$("#searchForm").find("input[type='text']").val("");
 			//默认交易类型
			$("#transType").find("option").removeAttr("selected");
			$("#transType").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#transType option[selected]").text());
			//默认产品类型
			$("#productCode").find("option").removeAttr("selected");
			$("#productCode").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(1)").text($("#productCode option[selected]").text());
			//默认支付类型
			$("#payType").find("option").removeAttr("selected");
			$("#payType").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(2)").text($("#payType option[selected]").text());
			
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/statisticsTypeRecord/">分类交易数据统计列表</a></li>
		<shiro:hasPermission name="payment:statisticsTransRecord:edit"><li><a href="${ctx}/payment/statisticsTypeRecord/form">交易数据统计添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="statisticsTransRecord" action="${ctx}/payment/statisticsTypeRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>统计时间：</label>
				<input id="beginStatisticsTime" name="beginStatisticsTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${statisticsTransRecord.beginStatisticsTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input id="endStatisticsTime" name="endStatisticsTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${statisticsTransRecord.endStatisticsTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="查询"/>
			<input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/>
			<!-- <input class="btn btn-primary" type="button" id="statistics" value="统计"/> --></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	交易类型：
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>交易类型代码</th>
				<th>交易类型</th>
				<th>总笔数</th>
				<th>总金额（元）</th>
				<th>转化率</th>
				<th>成功总笔数</th>
				<th>成功总金额（元）</th>
				<th>成功率</th>
				<th>失败总笔数</th>
				<th>失败总金额（元）</th>
				<th>失败率</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${transTypeList}" var="statisticsTransRecord">
			<tr>
				<td>
					${statisticsTransRecord.transType }
				</td>
				<td>
					${fns:getDictLabel(statisticsTransRecord.transType, 'TransType', '')}
					
				</td>
		
				<td>
					${statisticsTransRecord.totalCount}
				</td>
				<td>
					<fmt:formatNumber value="${statisticsTransRecord.totalAmount}" pattern="￥#,##0.00" />
					
				</td>
				<td>
					<fmt:formatNumber value="${statisticsTransRecord.sucessCount/statisticsTransRecord.totalCount}" pattern="0.00%" />
				</td>
				<td>
					${statisticsTransRecord.sucessCount}
				</td>
				<td>
					<fmt:formatNumber value="${statisticsTransRecord.sucessAmount}" pattern="￥#,##0.00" />
				</td>
				<td>
					<fmt:formatNumber value="${statisticsTransRecord.sucessCount/((statisticsTransRecord.sucessCount+statisticsTransRecord.failCount) eq 0 ?1:(statisticsTransRecord.sucessCount+statisticsTransRecord.failCount))}" pattern="0.00%" />
				</td>
				<td>
					${statisticsTransRecord.failCount}
				</td>
				<td>
					<fmt:formatNumber value="${statisticsTransRecord.failAmount}" pattern="￥#,##0.00" />
					
				</td>
				<td>
					<fmt:formatNumber value="${statisticsTransRecord.failCount/((statisticsTransRecord.sucessCount+statisticsTransRecord.failCount) eq 0 ?1:(statisticsTransRecord.sucessCount+statisticsTransRecord.failCount))}" pattern="0.00%" />
				</td>
				
			</tr>
		</c:forEach>
		</tbody>
	</table>

	<br>
	<br>
	按产品统计：
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>产品代码</th>
				<th>产品名称</th>
				<th>总笔数</th>
				<th>总金额（元）</th>
				<th>转化率</th>
				<th>成功总笔数</th>
				<th>成功总金额（元）</th>
				<th>成功率</th>
				<th>失败总笔数</th>
				<th>失败总金额（元）</th>
				<th>失败率</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${productList}" var="statisticsTransRecord">
			<tr>
				<td>
					${statisticsTransRecord.productCode }
				</td>
				<td>
					${fns:getDictLabel(statisticsTransRecord.productCode, 'ProductCodeType', '')}
					
				</td>
		
				<td>
					${statisticsTransRecord.totalCount}
				</td>
				<td>
					<fmt:formatNumber value="${statisticsTransRecord.totalAmount}" pattern="￥#,##0.00" />
					
				</td>
				<td>
					<fmt:formatNumber value="${statisticsTransRecord.sucessCount/statisticsTransRecord.totalCount}" pattern="0.00%" />
				</td>
				<td>
					${statisticsTransRecord.sucessCount}
				</td>
				<td>
					<fmt:formatNumber value="${statisticsTransRecord.sucessAmount}" pattern="￥#,##0.00" />
				</td>
				<td>
					<fmt:formatNumber value="${statisticsTransRecord.sucessCount/((statisticsTransRecord.sucessCount+statisticsTransRecord.failCount) eq 0 ?1:(statisticsTransRecord.sucessCount+statisticsTransRecord.failCount))}" pattern="0.00%" />
				</td>
				<td>
					${statisticsTransRecord.failCount}
				</td>
				<td>
					<fmt:formatNumber value="${statisticsTransRecord.failAmount}" pattern="￥#,##0.00" />
					
				</td>
				<td>
					<fmt:formatNumber value="${statisticsTransRecord.failCount/((statisticsTransRecord.sucessCount+statisticsTransRecord.failCount) eq 0 ?1:(statisticsTransRecord.sucessCount+statisticsTransRecord.failCount))}" pattern="0.00%" />
				</td>
				
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<br>
	<br>
	支付类型：
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>支付类型代码</th>
				<th>支付类型</th>
				<th>总笔数</th>
				<th>总金额（元）</th>
				<th>转化率</th>
				<th>成功总笔数</th>
				<th>成功总金额（元）</th>
				<th>成功率</th>
				<th>失败总笔数</th>
				<th>失败总金额（元）</th>
				<th>失败率</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${payTypeList}" var="statisticsTransRecord">
			<tr>
				<td>
					${statisticsTransRecord.payType }
				</td>
				<td>
					${fns:getDictLabel(statisticsTransRecord.payType, 'payType', '')}
					
				</td>
				<td>
					${statisticsTransRecord.totalCount}
				</td>
				<td>
					<fmt:formatNumber value="${statisticsTransRecord.totalAmount}" pattern="￥#,##0.00" />
					
				</td>
				<td>
					<fmt:formatNumber value="${statisticsTransRecord.sucessCount/statisticsTransRecord.totalCount}" pattern="0.00%" />
				</td>
				<td>
					${statisticsTransRecord.sucessCount}
				</td>
				<td>
					<fmt:formatNumber value="${statisticsTransRecord.sucessAmount}" pattern="￥#,##0.00" />
				</td>
				<td>
					<fmt:formatNumber value="${statisticsTransRecord.sucessCount/((statisticsTransRecord.sucessCount+statisticsTransRecord.failCount) eq 0 ?1:(statisticsTransRecord.sucessCount+statisticsTransRecord.failCount))}" pattern="0.00%" />
				</td>
				<td>
					${statisticsTransRecord.failCount}
				</td>
				<td>
					<fmt:formatNumber value="${statisticsTransRecord.failAmount}" pattern="￥#,##0.00" />
					
				</td>
				<td>
					<fmt:formatNumber value="${statisticsTransRecord.failCount/((statisticsTransRecord.sucessCount+statisticsTransRecord.failCount) eq 0 ?1:(statisticsTransRecord.sucessCount+statisticsTransRecord.failCount))}" pattern="0.00%" />
				</td>
				
			</tr>
		</c:forEach>
		</tbody>
	</table>
<%-- 	<div class="pagination">${page}</div> --%>
</body>
</html>