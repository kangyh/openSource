<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>余额统计管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.tbl_content{
		    width:100%;
		}
		.tbl_content thead td{
		    border-right:1px solid #d8d8d8;
		    border-top:1px solid #d8d8d8;
		    border-left:1px solid #d8d8d8;
		    background:#f7f7f7;
		    color:#333;
		    text-align:center;
		    padding:10px 5px;
		    font-size: 14px;
		    font-weight:600;
		}
		.tbl_content tr:nth-child(2n){ 
		    background:#f6f6f6; 
		}
		.tbl_content tbody tr:hover{ 
		    background:#f9f1f1;
		}
		.tbl_content tbody td{ 
		    text-align:center;
		    border:1px solid #d8d8d8;
		    color:#333; 
		    height:30px;
		    padding:5px 5px;
		    word-break: break-all;
		    font-size: 12px;
		}	
	</style>
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
				
				var beginStatisticsTime = $("#beginTransDate").val();
				var endStatisticsTime = $("#endTransDate").val();
				var now = new Date().Format("yyyy-MM-dd");

				if(endStatisticsTime<beginStatisticsTime){
					alert("开始时间不能大于结束时间");
					return ;
				}
				if(now<endStatisticsTime){
					alert("结束时间不能大于当前时间");
					return ;
				}
				
				$("#searchForm").submit();
			});
			
			
			$("#statistics").on("click",function(){
				var beginTransDate = $("#beginTransDate").val();
				var endTransDate = $("#endTransDate").val();
				var url = "${ctx}/payment/statisticsBalanceRecord/statistics";
				//var url = host+"?beginStatisticsTime="+beginStatisticsTime+"&endStatisticsTime="+endStatisticsTime;
				var data={
						beginTransDate:beginTransDate,
						endTransDate:endTransDate
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
		
		//清空
		function onClear(){
			$("#searchForm").find("input[type='text']").val("");
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/statisticsBalanceRecord/">余额统计列表</a></li>
		<shiro:hasPermission name="payment:statisticsBalanceRecord:edit"><li><a href="${ctx}/payment/statisticsBalanceRecord/form">余额统计添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="statisticsBalanceRecord" action="${ctx}/payment/statisticsBalanceRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
			<input name="beginTransDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${statisticsBalanceRecord.beginTransDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input name="endTransDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${statisticsBalanceRecord.endTransDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="查询"/>
			<input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/>
			<!-- <input id="statistics" class="btn btn-primary" type="button" onclick="statistics()" value="统计"/> -->
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table class="tbl_content">
            <thead>
                <tr class="thead">
                    <td width="10%" rowspan="2">交易日期</td>
                    <td width="10%" colspan="3">期初</td>
                    <td width="10%" colspan="2">交易额</td>
                    <td width="10%" colspan="3">期末</td>
                    <td width="10%" rowspan="2">备注</td>
                </tr>
                 <tr class="thead">
                    <td width="10%">入金</td>
                    <td width="10%">出金</td>
                    <td width="10%">余额</td>
                    <td width="10%">入金</td>
                    <td width="10%">出金</td>
                    <td width="10%">入金</td>
                    <td width="10%">出金</td>
                    <td width="10%">余额</td>
                </tr>
            </thead>
            <tbody class="record_list">
	            <c:forEach items="${page.list}" var="StatisticsBalanceRecord">
					<tr>
						<td>
						<fmt:formatDate value="${StatisticsBalanceRecord.transDate}" pattern="yyyy-MM-dd"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${StatisticsBalanceRecord.beginInAmount}" pattern="￥#,##0.00" />
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${StatisticsBalanceRecord.beginOutAmount}" pattern="￥#,##0.00" />
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${StatisticsBalanceRecord.beginBalanceAmount}" pattern="￥#,##0.00" />
							
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${StatisticsBalanceRecord.transInAmount}" pattern="￥#,##0.00" />
							
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${StatisticsBalanceRecord.transOutAmount}" pattern="￥#,##0.00" />
							
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${StatisticsBalanceRecord.endInAmount}" pattern="￥#,##0.00" />
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${StatisticsBalanceRecord.endOutAmount}" pattern="￥#,##0.00" />
							
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${StatisticsBalanceRecord.endBalanceAmount}" pattern="￥#,##0.00" />
						</td>
					
						<td>
							
						</td>
					</tr>
				</c:forEach>
  <!--               <tr>
                    <td>总计</td>
                    <td>2</td>
                    <td>3</td>
                    <td>4</td>
                    <td>5</td>
                    <td>6</td>
                    <td>7</td>
                    <td>8</td>
                    <td>9</td>
                    <td></td>
                </tr> -->
            </tbody>
        </table>
	<div class="pagination">${page}</div>
</body>
</html>