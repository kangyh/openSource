<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>对账管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<style>
        #main {
            margin: 50px;
        }
    </style>
	<script type="text/javascript">
		$(document).ready(function() {
			var checkStatus = $("#checkStatus option[selected]").val();
			if(checkStatus=='Y'){
				$('#checkStatusY').css('display','block');
			}else{
				$('#checkStatusY').css('display','none');
			}
		});
		
		//用于动态口令验证
		$(document).ready(function() {
			$(".checkPass").on("click",function(){
				var url = $(this).attr("value-url");
				parent.showDynamicPa();
				parent.enterDynamicPa(url);
			})
		});
		
		//验证搜索条件
		var validateFrom = {
			validate: function(form){

                var errorBathid = $("#errorBathid").val();
                var pattern = new RegExp("[`~!#$^@&*()=|{}':;',\\[\\]<>/?~！#￥……&*（）——|{}【】‘；：”“'。，、？]")
                if(pattern.test(errorBathid)){
                    $("#messageBox").text("差错批次号输入不合法，请重新输入");
                    return false;
                }
				
				var transNoid = $("#transNoid").val();
				if(isNaN(transNoid)){
					$("#messageBox").text("交易单号请输入数字类型");
					return false;
				}
				
				
				var bgTime = $("#beginErrorTime").val();
				var endTime = $("#endErrorTime").val();
				if(bgTime=="" && endTime!="" || bgTime!="" && endTime=="" ){
					$("#messageBox").text("请正确设置查询时间范围!");
					return ;
				}
				if( bgTime!="" && endTime!=""){
					if(compareDate(convertDateToJoinStr(bgTime),
									convertDateToJoinStr(endTime)) > 0){
						$("#messageBox").text("起始日期不能大于结束日期!");
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
			$("#pageNo").val(1);
			validateFrom.validate($("#searchForm"));
		}
		//清空
		function onClear(){
			$("#formBtn").submit();
		}
		
		//文件导出
		function onExport(){
	        var actionURL = $("#searchForm").attr("action");
	        $("#searchForm").attr("action",$("#searchForm").attr("action")+"/export");
			validateFrom.validate($("#searchForm"));
			$("#searchForm").attr("action",actionURL);
		}
		
		function Sel2change(field){
			$("#channelName").val(field);
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/accounting/settleDifferMerchantHis">商户侧差错汇总列表</a></li>
	</ul>
	<form action="${ctx}/accounting/settleDifferMerchantHis" method="post" id="formBtn"></form>
	<form:form id="searchForm" modelAttribute="settleDifferMerchantHis" action="${ctx}/accounting/settleDifferMerchantHis" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			 
			<%-- <li><label>记账状态：</label>
				<form:select id="errorStatus" path="errorStatus" class="input-xlarge">
					<form:option value="" label="-选择记账状态-"/>
					<c:forEach items="${billingBillStatus}" var="billingBillStatusControl">
							<form:option value="${billingBillStatusControl.value}" label="${billingBillStatusControl.name}"/>
						</c:forEach>
				</form:select>
			</li>  --%>
			
			
			<li><label>账单类型：</label>
				<form:select id="billType" path="billType" class="input-xlarge">
					<form:option value="" label="-选择账单类型-"/>
					<c:forEach items="${differencesBillType}" var="differencesBillTypeControl">
							<form:option value="${differencesBillTypeControl.value}" label="${differencesBillTypeControl.name}"/>
						</c:forEach>
				</form:select>
			</li> 
			
			
			<%-- <li><label>审核状态：</label>
				<form:select id="checkStatus" path="checkStatus" class="input-xlarge">
					<form:option value="" label="-选择审核状态-"/>
					<c:forEach items="${checkStatus}" var="checkStatusControl">
							<form:option value="${checkStatusControl.value}" label="${checkStatusControl.name}"/>
						</c:forEach>
				</form:select>
			</li>  --%>
			
			<li><label>交易类型：</label>
				<form:select id="transType" path="transType" class="input-xlarge">
					<form:option value="" label="-选择交易类型-"/>
					<c:forEach items="${transType}" var="transTypeControl">
							<form:option value="${transTypeControl.value}" label="${transTypeControl.name}"/>
						</c:forEach>
				</form:select>
			</li> 
			</ul>
			<ul class="ul-form">
			<li><label>差错批次号：</label>
				<form:input path="errorBath" id="errorBathid" htmlEscape="false" maxlength="40" class="input-medium" placeholder="请输入差错批次号" style="width:256px;"/>
			</li> 
			
			<li><label>交易订单号：</label>
					<form:input path="transNo" id="transNoid" htmlEscape="false" maxlength="40" class="input-medium" placeholder="请输入20位数字" style="width:256px;"/>
			</li> 
			</ul>
			<ul class="ul-form">
			<li><label>差错日期：</label>
					<input id="beginErrorTime" name="beginErrorTime" type="text" readonly="readonly" maxlength="20" style="width:150px;" class="input-medium Wdate"
					value="<fmt:formatDate value="${settleDifferMerchantHis.beginErrorTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endErrorTime" name="endErrorTime" type="text" readonly="readonly" maxlength="20" style="width:150px;" class="input-medium Wdate"
					value="<fmt:formatDate value="${settleDifferMerchantHis.endErrorTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清除"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" onclick="onExport()" value="导出"/></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
		
	</form:form>
	
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>商户编码</th>
			<th>交易类型</th>
			<th>币种</th>
			<th>差错批次号</th>
			
			<th>差错日期</th>
			
			<th>交易订单号</th>
			<th>实际支付金额</th>
			<th>订单应结算金额</th>
			<th>订单手续费</th>
			<th>账单类型</th>
			<!-- <th>记账状态</th>
			<th>审核状态</th>
			<th>审核备注</th>
			
			<th>操作人</th>
			<th>操作时间</th>
			<th>审核操作</th> -->
			
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="settleDifferMerchantHis">
			<tr>
				<td>${settleDifferMerchantHis.merchantId}</td>
				<td>${settleDifferMerchantHis.transType}</td>
				<td>${settleDifferMerchantHis.currency}</td>
				<td>${settleDifferMerchantHis.errorBath}</td>
				
				<td><fmt:formatDate value="${settleDifferMerchantHis.errorDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				
				<td>${settleDifferMerchantHis.transNo}</td>
				<td>
					<fmt:formatNumber value="${settleDifferMerchantHis.requestAmount}" pattern="￥0.0000" />
				</td>
				<td>
					<fmt:formatNumber value="${settleDifferMerchantHis.settleAmountPlan}" pattern="￥0.0000" />
				</td>
				<td>
					<fmt:formatNumber value="${settleDifferMerchantHis.fee}" pattern="￥0.0000" />
				</td>
				<td>${settleDifferMerchantHis.billType}</td>
				<%-- <td <c:choose>
			     		<c:when test="${settleDifferMerchantHis.errorStatus=='已记账'}">
			     		 style="background:#6cf683" 	
			     		</c:when>
			     		<c:when test="${settleDifferMerchantHis.errorStatus=='未记账'}">
			     		 style="background:#707c9b" 
			     		</c:when>
			     		<c:when test="${settleDifferMerchantHis.errorStatus=='记账中'}">
			     		 style="background:#ffb300" 
			     		</c:when>
			     	</c:choose>>${settleDifferMerchantHis.errorStatus}</td>
				
				<td <c:choose>
			     		<c:when test="${settleDifferMerchantHis.checkStatus=='审核成功'}">
			     		 style="background:#6cf683" 	
			     		</c:when>
			     		<c:when test="${settleDifferMerchantHis.checkStatus=='审核失败'}">
			     		 style="background:red" 
			     		</c:when>
			     		<c:when test="${settleDifferMerchantHis.checkStatus=='未审核'}">
			     		 style="background:#707c9b" 
			     		</c:when>
			     	</c:choose>>${settleDifferMerchantHis.checkStatus}</td>
				<td>${settleDifferMerchantHis.checkMessage}</td>
				
				<td>${settleDifferMerchantHis.updateAuthor}</td>
				<td><fmt:formatDate value="${settleDifferMerchantHis.dealTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<c:if test="${settleDifferMerchantHis.status =='Y' }">
						<a style="cursor:pointer;" class="checkPass"  value-url="${ctx}/accounting/settleDifferMerchantHis/checkstatus/${settleDifferMerchantHis.differMerbillId}/${page.pageNo}">${settleDifferMerchantHis.billType == '补账'?'补账':'撤账'}</a>
					</c:if>
					<c:if test="${settleDifferMerchantHis.checkStatus !='未审核' }">
						已处理
					</c:if>
					
					<c:if test="${settleDifferMerchantHis.status !='Y' && settleDifferMerchantHis.checkStatus =='未审核'}">
					 --
					</c:if>
				</td> --%>
				
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>