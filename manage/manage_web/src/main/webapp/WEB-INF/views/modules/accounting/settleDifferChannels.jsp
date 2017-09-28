<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商户侧清结算管理</title>
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

				var transNoid = $("#paymentIdid").val();
				if(isNaN(transNoid)){
					$("#messageBox").text("支付单号请输入数字类型");
					return false;
				}
				
				
				var bgTime = $("#beginOperEndTime").val();
				var endTime = $("#endOperEndTime").val();
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
		<li class="active"><a href="${ctx}/accounting/settleDifferChannels">通道差错汇总列表</a></li>
	</ul>
	<form action="${ctx}/accounting/settleDifferChannels" method="post" id="formBtn"></form>
	<form:form id="searchForm" modelAttribute="settleDifferChannel" action="${ctx}/accounting/settleDifferChannels" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			 
		<ul class="ul-form">

			<li><label>账单类型：</label>
				<form:select id="billType" path="billType" class="input-xlarge">
					<form:option value="" label="-选择账单类型-"/>
					<c:forEach items="${differencesBillType}" var="differencesBillTypeControl">
							<form:option value="${differencesBillTypeControl.value}" label="${differencesBillTypeControl.name}"/>
						</c:forEach>
				</form:select>
			</li> 
			<li><label>支付单号：</label>
					<form:input path="paymentId" id="paymentIdid" htmlEscape="false" maxlength="40" class="input-medium" placeholder="请输入7位数字" style="width:256px;"/>
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
					<input id="beginOperEndTime" name="beginOperEndTime" type="text" readonly="readonly" maxlength="20" style="width:150px;" class="input-medium Wdate"
					value="<fmt:formatDate value="${settleDifferChannel.beginOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endOperEndTime" name="endOperEndTime" type="text" readonly="readonly" maxlength="20" style="width:150px;" class="input-medium Wdate"
					value="<fmt:formatDate value="${settleDifferChannel.endOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
			<th>通道合作方</th>
			<th>支付通道类型</th>
			<th>差错批次号</th>
			
			<th>差错日期</th>
			
			<th>交易订单号</th>
			<th>支付单号</th>
			<th>币种</th>
			<th>实际支付金额</th>
			<th>交易成本</th>
			<th>账单类型</th>
			<th>记账状态</th>
			<th>操作</th>
			<!-- <th>审核状态</th>
			<th>审核备注</th>
			
			<th>操作人</th>
			<th>操作时间</th>
			<th>审核操作</th> -->
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="settleDifferChannel">
			<tr>
				<td>${settleDifferChannel.channelCode}</td>
				<td>${settleDifferChannel.channelType}</td>
				<td>${settleDifferChannel.errorBath}</td>
				
				<td><fmt:formatDate value="${settleDifferChannel.errorDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				
				<td>${settleDifferChannel.transNo}</td>
				<td>${settleDifferChannel.paymentId}</td>
				<td>${settleDifferChannel.currency}</td>
				<td>
					<fmt:formatNumber value="${settleDifferChannel.successAmount}" pattern="￥0.0000" />
				</td>
				<td>
					<fmt:formatNumber value="${settleDifferChannel.cost}" pattern="￥0.0000" />
				</td>
				<td>${settleDifferChannel.billType}</td>
				<td <c:choose>
			     		<c:when test="${settleDifferChannel.errorStatus=='已记账'}">
			     		 style="background:#6cf683" 	
			     		</c:when>
			     		<c:when test="${settleDifferChannel.errorStatus=='未记账'}">
			     		 style="background:#707c9b" 
			     		</c:when>
			     		<c:when test="${settleDifferChannel.errorStatus=='记账中'}">
			     		 style="background:#ffb300" 
			     		</c:when>
			     	</c:choose>>${settleDifferChannel.errorStatus}</td>
			     <td>
				   <c:if test="${settleDifferChannel.errorStatus=='未记账' && settleDifferChannel.flag !='Y' }">
						<a href="${ctx}/accounting/settleDifferChannels/fillAgain/${settleDifferChannel.differChanbillId}/${page.pageNo}" onclick="return confirmx('确认要补充记账？', this.href)">补充记账</a>
					</c:if>
			     </td>
				<%-- <td <c:choose>
			     		<c:when test="${settleDifferChannel.checkStatus=='审核成功'}">
			     		 style="background:#6cf683" 	
			     		</c:when>
			     		<c:when test="${settleDifferChannel.checkStatus=='审核失败'}">
			     		 style="background:red" 
			     		</c:when>
			     		<c:when test="${settleDifferChannel.checkStatus=='未审核'}">
			     		 style="background:#707c9b" 
			     		</c:when>
			     	</c:choose>>${settleDifferChannel.checkStatus}</td>
				<td>${settleDifferChannel.checkMessage}</td>
				
				<td>${settleDifferChannel.updateAuthor}</td>
				<td><fmt:formatDate value="${settleDifferChannel.dealTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>

				<td>
					<c:if test="${settleDifferChannel.status =='Y' }">
						<a style="cursor:pointer;" class="checkPass"  value-url="${ctx}/accounting/settleDifferChannels/toupdate/${settleDifferChannel.differChanbillId}/${page.pageNo}">${settleDifferChannel.billType == '补账'?'补账':'撤账'}</a>
					</c:if>
					<c:if test="${settleDifferChannel.checkStatus !='未审核' }">
						已处理
					</c:if>
					
					<c:if test="${settleDifferChannel.status !='Y' && settleDifferChannel.checkStatus =='未审核'}">
					 --
					</c:if>
				</td> --%>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>