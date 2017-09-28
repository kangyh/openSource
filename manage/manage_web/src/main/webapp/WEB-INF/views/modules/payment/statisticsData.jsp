<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>交易数据统计管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			Date.prototype.Format = function (fmt) { //author: meizz 
			    var o = {
			        "M+": this.getMonth() + 1, //月份 
			        "d+": this.getDate(), //日 
			        "h+": this.getHours(), //小时 
			        "m+": this.getMinutes(), //分 
			        "s+": this.getSeconds(), //秒 
			        "q+": Math.floor((this.getMonth()+3)/3),
			        "S": this.getMilliseconds() //毫秒 
			    };
			    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
			    for (var k in o)
			    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
			    return fmt;
			}
			
			
			
			$("#btnSubmit").on("click",function(){
				var merchantId = $("#merchantId").val();
				var merchantCompany = $("#merchantCompany").val();

				if(!isEmpty(merchantId)&&!/\d+/.test(merchantId)){
					alert("商户编码输入不合法，只能输入数字");
					return ;
				}
				if(!isEmpty(merchantCompany)&&!/^[a-zA-Z0-9\u4e00-\u9fa5()（）]+$/.test(merchantCompany)){
					alert("商户公司名称输入不合法，请重新输入");
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
				
				
				var host = "${ctx}/payment/statisticsData/exportExcel";
				var url = host+"?merchantId="+merchantId+"&merchantCompany="+merchantCompany+"&transType="+transType+"&productCode="+productCode+"&payType="+payType+"&beginStatisticsTime="+beginStatisticsTime+"&endStatisticsTime="+endStatisticsTime;
				$('<form method="post" action="' + url + '"></form>').appendTo('body').submit().remove();
				
			});
			
			$("#statistics").on("click",function(){
				var beginStatisticsTime = $("#beginStatisticsTime").val();
				var endStatisticsTime = $("#endStatisticsTime").val();
				var now = new Date().Format("yyyy-MM-dd");
				var url = "${ctx}/payment/statisticsData/statistics";
				
				if(isEmpty(beginStatisticsTime)|| isEmpty(endStatisticsTime)){
					alert("起始日期不能为空");
					return;
				}

				if(endStatisticsTime<beginStatisticsTime){
					alert("开始时间不能大于结束时间");
					return ;
				}
				if(now<endStatisticsTime){
					alert("结束时间不能大于当前时间");
					return ;
				}

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
			
			$("#financeStatistics").on("click",function(){
				var beginStatisticsTime = $("#beginStatisticsTime").val();
				var endStatisticsTime = $("#endStatisticsTime").val();
				var now = new Date().Format("yyyy-MM-dd");
				var url = "${ctx}/payment/statisticsData/financeStatistics";
				
				if(isEmpty(beginStatisticsTime)|| isEmpty(endStatisticsTime)){
					alert("起始日期不能为空");
					return;
				}

				if(endStatisticsTime<beginStatisticsTime){
					alert("开始时间不能大于结束时间");
					return ;
				}
				if(now<endStatisticsTime){
					alert("结束时间不能大于当前时间");
					return ;
				}

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
			
			$("#financeStatisticsPerHour").on("click",function(){
				var beginStatisticsTime = $("#beginStatisticsTime").val();
				var endStatisticsTime = $("#endStatisticsTime").val();
				var now = new Date().Format("yyyy-MM-dd");
				var url = "${ctx}/payment/statisticsData/financeStatisticsPerHour";
				
				if(isEmpty(beginStatisticsTime)|| isEmpty(endStatisticsTime)){
					alert("起始日期不能为空");
					return;
				}

				if(endStatisticsTime<beginStatisticsTime){
					alert("开始时间不能大于结束时间");
					return ;
				}
				if(now<endStatisticsTime){
					alert("结束时间不能大于当前时间");
					return ;
				}

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
		
		
		
	
		
		function isEmpty(value){
	 		if(value==undefined||value==""||value.trim()==""){
	 			return true;
	 		}
	 		return false;
	 	}
		
		function getNowFormatDate() {
		    var date = new Date();
		    var seperator1 = "-";
		    var month = date.getMonth() + 1;
		    var strDate = date.getDate();
		    if (month >= 1 && month <= 9) {
		        month = "0" + month;
		    }
		    if (strDate >= 0 && strDate <= 9) {
		        strDate = "0" + strDate;
		    }
		    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate ;
		    return currentdate;
		}
		
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
		<li class="active"><a href="${ctx}/payment/statisticsData/">交易数据统计列表</a></li>
		<shiro:hasPermission name="payment:statisticsTransRecord:edit"><li><a href="${ctx}/payment/statisticsData/form">交易数据统计添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="statisticsTransRecord" action="${ctx}/payment/statisticsData/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户编码：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="18" class="input-medium"/>
			</li>
			<li><label>商户公司名称：</label>
				<form:input path="merchantCompany" htmlEscape="false" maxlength="100" class="input-medium"/>
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
			<li><label>支付时间：</label>
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
			<input class="btn btn-primary" type="button" id="statistics" value="统计"/>
			<input class="btn btn-primary" type="button" id="financeStatistics" value="财务统计"/>
			<input class="btn btn-primary" type="button" id="financeStatisticsPerHour" value="财务统计(小时)"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				
				<th>总笔数</th>
				<th>总金额（元）</th>
				<th>成功总笔数</th>
				<th>成功总金额（元）</th>
				<th>成功率</th>
				<th>失败总笔数</th>
				<th>失败总金额（元）</th>
				<th>失败率</th>
				
			</tr>
		</thead>
		<tbody>
			<tr>
	
				
				<td>
					${pretotalCount }
				</td>
				<td>
					 <fmt:formatNumber value="${pretotalAmount}" pattern="￥#,##0.00" /> 
					
				</td>
				<td>
					${presucessCount }
				</td>
				<td>
					 <fmt:formatNumber value="${presucessAmount}" pattern="￥#,##0.00" /> 
				</td>
				<td>
					
					 <fmt:formatNumber value="${presucessCount/pretotalCount}" pattern="0.00%" /> 
				</td>
				<td>
					${prefailCount}
				</td>
				<td>
					<fmt:formatNumber value="${prefailAmount}" pattern="￥#,##0.00" /> 
					
				</td>
				<td>
					<fmt:formatNumber value="${prefailCount/pretotalCount}" pattern="0.00%" /> 
				</td>
				
			</tr>
		</tbody>
	</table>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			
			<tr>
				<th>商户编码</th>
				<th>商户公司名称</th>
				<th>交易类型</th>
				<th>产品名称</th>
				<th>支付类型</th>
				<th>总笔数</th>
				<th>总金额（元）</th>
				<th>成功总笔数</th>
				<th>成功总金额（元）</th>
				<th>成功率</th>
				<th>失败总笔数</th>
				<th>失败总金额（元）</th>
				<th>失败率</th>
				
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
				<td>
					<fmt:formatNumber value="${statisticsTransRecord.totalAmount}" pattern="￥#,##0.00" />
					
				</td>
				<td>
					${statisticsTransRecord.sucessCount}
				</td>
				<td>
					<fmt:formatNumber value="${statisticsTransRecord.sucessAmount}" pattern="￥#,##0.00" />
				</td>
				<td>
					<fmt:formatNumber value="${statisticsTransRecord.sucessCount/statisticsTransRecord.totalCount}" pattern="0.00%" />
				</td>
				<td>
					${statisticsTransRecord.failCount}
				</td>
				<td>
					<fmt:formatNumber value="${statisticsTransRecord.failAmount}" pattern="￥#,##0.00" />
					
				</td>
				<td>
					<fmt:formatNumber value="${statisticsTransRecord.failCount/statisticsTransRecord.totalCount}" pattern="0.00%" />
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
					${totalCount }
				</td>
				<td>
					 <fmt:formatNumber value="${totalAmount}" pattern="￥#,##0.00" /> 
					
				</td>
				<td>
					${sucessCount }
				</td>
				<td>
					 <fmt:formatNumber value="${sucessAmount}" pattern="￥#,##0.00" /> 
				</td>
				<td>
					
					 <fmt:formatNumber value="${sucessCount/totalCount}" pattern="0.00%" /> 
				</td>
				<td>
					${failCount}
				</td>
				<td>
					<fmt:formatNumber value="${failAmount}" pattern="￥#,##0.00" /> 
					
				</td>
				<td>
					<fmt:formatNumber value="${failCount/totalCount}" pattern="0.00%" /> 
				</td>
				
			</tr>
		
		
		
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>