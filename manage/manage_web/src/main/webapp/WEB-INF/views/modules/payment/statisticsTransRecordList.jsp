<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>交易数据统计管理</title>
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
			//默认商户来源
			$("#allowSystem").find("option").removeAttr("selected");
			$("#allowSystem").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#allowSystem option[selected]").text());
 			//默认交易类型
			$("#transType").find("option").removeAttr("selected");
			$("#transType").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(1)").text($("#transType option[selected]").text());
			//默认产品类型
			$("#productCode").find("option").removeAttr("selected");
			$("#productCode").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(2)").text($("#productCode option[selected]").text());
			//默认支付类型
			$("#payType").find("option").removeAttr("selected");
			$("#payType").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(3)").text($("#payType option[selected]").text());
			
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/statisticsTransRecord/">交易数据统计列表</a></li>
		<shiro:hasPermission name="payment:statisticsTransRecord:edit"><li><a href="${ctx}/payment/statisticsTransRecord/form">交易数据统计添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="statisticsTransRecord" action="${ctx}/payment/statisticsTransRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户编码：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="18" class="input-medium"/>
			</li>
			<li><label>商户公司名称：</label>
				<form:input path="merchantCompany" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>商户来源：</label>
				<form:select path="allowSystem" class="input-medium">
					<form:option value="R1" label="全部"/>
					<form:options items="${fns:getDictList('AllowSystemSource')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>交易类型：</label>
				<form:select path="transType" class="input-medium">
					<form:option value="R1" label="全部"/>
					<form:options items="${fns:getDictList('TransType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>产品名称：</label>
				<form:select path="productCode" class="input-medium">
					<form:option value="R1" label="全部"/>
					<form:options items="${fns:getDictList('ProductCodeType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>支付类型：</label>
				<form:select path="payType" class="input-medium">
					<form:option value="R1" label="全部"/>
					<form:options items="${fns:getDictList('payType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
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
			<input class="btn btn-primary" type="button" id="exportExcel" value="导出Excel"/>
			<!-- <input class="btn btn-primary" type="button" id="statistics" value="统计"/> --></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				
				<th>总笔数</th>
				<th>总金额（元）</th>
				<th>转化率</th>
				<th>成功总笔数</th>
				<th>成功总金额（元）</th>
				<th>成功率</th>
				<th>失败总笔数</th>
				<th>失败总金额（元）</th>
				<th>失败率</th>
				<th>手续费金额（元）</th>
				
			</tr>
		</thead>
		<tbody>
			<tr>
	
				
				<td>
					${pretotalCount }
				</td>
				<td style="text-align: right">
					 <fmt:formatNumber value="${pretotalAmount}" pattern="￥#,##0.00" /> 
					
				</td>
				<td>
					
					 <fmt:formatNumber value="${presucessCount/pretotalCount}" pattern="0.00%" /> 
				</td>
				<td>
					${presucessCount }
				</td>
				<td style="text-align: right">
					 <fmt:formatNumber value="${presucessAmount}" pattern="￥#,##0.00" /> 
				</td>
				<td>
					
					 <fmt:formatNumber value="${presucessCount/((presucessCount+prefailCount) eq 0?1:(presucessCount+prefailCount))}" pattern="0.00%" /> 
				</td>
				<td>
					${prefailCount}
				</td>
				<td style="text-align: right">
					<fmt:formatNumber value="${prefailAmount}" pattern="￥#,##0.00" /> 
					
				</td>
				<td>
					<fmt:formatNumber value="${prefailCount/((presucessCount+prefailCount) eq 0?1:(presucessCount+prefailCount))}" pattern="0.00%" /> 
				</td>
				<td style="text-align: right">
					<fmt:formatNumber value="${prefeeAmount}" pattern="￥#,##0.00" /> 
					
				</td>
			</tr>
		</tbody>
	</table>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			
			<tr>
				<th>商户编码</th>
				<th>商户公司名称</th>
				<th>商户来源</th>
				<th>交易类型</th>
				<th>产品名称</th>
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
				<th>手续费金额（元）</th>
			
				
				
			</tr>
		</thead>
		<tbody>
		
		
		
		<c:forEach items="${page.list}" var="statisticsTransRecord">
			<tr>
				<td>
					${statisticsTransRecord.merchantId}
				</td>
				<td>
					${statisticsTransRecord.merchantCompany}
				</td>
				<td>
					${fns:getDictLabel(statisticsTransRecord.allowSystem, 'AllowSystemSource', '')}
				</td>
				<td>
					${fns:getDictLabel(statisticsTransRecord.transType, 'TransType', '')}
					
				</td>
				<td>
					${fns:getDictLabel(statisticsTransRecord.productCode, 'ProductCodeType', '')}
					
				</td>
				<td>
					${fns:getDictLabel(statisticsTransRecord.payType, 'payType', '')}
					
				</td>
				<td>
					${statisticsTransRecord.totalCount}
				</td>
				<td style="text-align: right">
					<fmt:formatNumber value="${statisticsTransRecord.totalAmount}" pattern="￥#,##0.00" />
					
				</td>
				<td>
					<fmt:formatNumber value="${statisticsTransRecord.sucessCount/statisticsTransRecord.totalCount}" pattern="0.00%" />
				</td>
				<td>
					${statisticsTransRecord.sucessCount}
				</td>
				<td style="text-align: right">
					<fmt:formatNumber value="${statisticsTransRecord.sucessAmount}" pattern="￥#,##0.00" />
				</td>
				<td>
					<fmt:formatNumber value="${statisticsTransRecord.sucessCount/((statisticsTransRecord.sucessCount+statisticsTransRecord.failCount) eq 0 ?1:(statisticsTransRecord.sucessCount+statisticsTransRecord.failCount))}" pattern="0.00%" />
				</td>
				<td>
					${statisticsTransRecord.failCount}
				</td>
				<td style="text-align: right">
					<fmt:formatNumber value="${statisticsTransRecord.failAmount}" pattern="￥#,##0.00" />
					
				</td>
				<td>
					<fmt:formatNumber value="${statisticsTransRecord.failCount/((statisticsTransRecord.sucessCount+statisticsTransRecord.failCount) eq 0 ?1:(statisticsTransRecord.sucessCount+statisticsTransRecord.failCount))}" pattern="0.00%" />
				</td>
				<td style="text-align: right">
					<fmt:formatNumber value="${statisticsTransRecord.feeAmount}" pattern="￥#,##0.00" />
				</td>
	
			</tr>
		</c:forEach>
		<tr>
	
				<td>
					<b>合计</b>
				</td>
				<td>
				</td>
				<td>
				</td>
				<td>
				</td>
				<td>
				</td>
				<td>
				</td>
				<td>
					${totalCount }
				</td>
				<td style="text-align: right">
					 <fmt:formatNumber value="${totalAmount}" pattern="￥#,##0.00" /> 
					
				</td>
				<td>
					<fmt:formatNumber value="${sucessCount/totalCount}" pattern="0.00%" /> 
				</td>
				<td>
					${sucessCount }
				</td>
				<td style="text-align: right">
					 <fmt:formatNumber value="${sucessAmount}" pattern="￥#,##0.00" /> 
				</td>
				<td>
					
					 <fmt:formatNumber value="${sucessCount/((failCount+sucessCount) eq 0?1:(failCount+sucessCount))}" pattern="0.00%" /> 
				</td>
				<td>
					${failCount}
				</td>
				<td style="text-align: right">
					<fmt:formatNumber value="${failAmount}" pattern="￥#,##0.00" /> 
					
				</td>
				<td>
					<fmt:formatNumber value="${failCount/((failCount+sucessCount) eq 0?1:(failCount+sucessCount))}" pattern="0.00%" /> 
				</td>
				<td style="text-align: right"><fmt:formatNumber value="${feeAmount}" pattern="￥#,##0.00" /> </td>
				
			</tr>
		
		
		
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>