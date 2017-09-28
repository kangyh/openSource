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
		//动态口令验证
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
				
				var checkBathno = $("#checkBathNo").val();
				var pattern = new RegExp("[`~!#$^@&*()=|{}':;',\\[\\]<>/?~！#￥……&*（）——|{}【】‘；：”“'。，、？]") 
				if(pattern.test(checkBathno)){
					$("#messageBox").text("对账批次号输入不合法，请重新输入");
					return false;
				}
				
				var paymentIdid = $("#paymentIdid").val();
				if(isNaN(paymentIdid)){
					$("#messageBox").text("支付单号请输入数字类型");
					return false;
				}
				
				var transNoid = $("#transNoid").val();
				if(isNaN(transNoid)){
					$("#messageBox").text("交易订单号请输入数字类型");
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
	        $("#searchForm").attr("action",$("#searchForm").attr("action")+"export");
			validateFrom.validate($("#searchForm"));
			$("#searchForm").attr("action",actionURL);
		}
		
		function Sel2change(field){
			$("#channelName").val(field);
		}
		
		
	 	function closeModal(modalID,bodyId){
        	$('#'+modalID).modal('hide');
        }
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/reconciliation/SettleDifferRrecordHis/">对账差错记录列表</a></li>
	</ul>
	<form action="${ctx}/reconciliation/SettleDifferRrecordHis/" method="post" id="formBtn"></form>
	<form:form id="searchForm" modelAttribute="settleDifferRecordHis" action="${ctx}/reconciliation/SettleDifferRrecordHis/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			 
			<li><label class="control-label">通道合作方：</label>
			    	<form:select id="channelCode" path="channelCode" class="input-xlarge" style="width:225px;" onchange="Sel2change(this.options[this.options.selectedIndex].text);">
						<form:option value="" label="-通道合作方-"/>
						<c:forEach items="${checklist}" var="SettleRuleControl">
							<form:option value="${SettleRuleControl.channelCode}" label="${SettleRuleControl.channelName}"/>
						</c:forEach>
					</form:select>
					<input type="hidden" name="channelName" id="channelName" class="input-xlarge required"> 
			</li>
			
			<li><label>支付通道类型：</label>
				<form:select id="channelType" path="channelType" style="width:225px;" class="input-xlarge">
					<form:option value="" label="-支付通道类型-"/>
						<c:forEach items="${checkTypeList}" var="SettleRuleControl">
							<form:option value="${SettleRuleControl.value}" label="${SettleRuleControl.name}"/>
						</c:forEach>
				</form:select> 
			
			<li><label>交易类型：</label>
				<form:select id="transType" path="transType" style="width:225px;" class="input-xlarge">
					<form:option value="" label="-选择交易类型-"/>
					<c:forEach items="${transType}" var="transTypeControl">
							<form:option value="${transTypeControl.value}" label="${transTypeControl.name}"/>
						</c:forEach>
				</form:select>
			</li> 
			</ul>
			<ul class="ul-form">
			
			<li><label>差错类型：</label>
				<form:select id="differType" path="differType" style="width:225px;" class="input-xlarge">
					<form:option value="" label="-选择差错类型-"/>
					<c:forEach items="${errorList}" var="errorListControl">
							<form:option value="${errorListControl.value}" label="${errorListControl.name}"/>
						</c:forEach>
				</form:select>
			</li>
			
			
			
			<li><label>差错状态：</label>
				<form:select id="handleMessage" path="handleMessage" style="width:225px;" class="input-xlarge">
					<form:option value="" label="-选择差错状态-"/>
					<c:forEach items="${errorStatus}" var="errorStatusControl">
							<form:option value="${errorStatusControl.value}" label="${errorStatusControl.name}"/>
						</c:forEach>
				</form:select>
			</li> 
			
			<li><label>支付单号：</label>
				<form:input path="paymentId" id="paymentIdid" htmlEscape="false" maxlength="40" class="input-medium" placeholder="请输入7位数字" style="width:210px;"/>
			</li> 
			
			</ul>
			<ul class="ul-form">
			<li><label>交易订单号：</label>
					<form:input path="transNo" id="transNoid" htmlEscape="false" maxlength="40" class="input-medium" placeholder="请输入20位数字" style="width:210px;"/>
			</li> 
			<li><label>对账批次号：</label>
					<form:input path="checkBathNo" id="checkBathNo" htmlEscape="false" maxlength="20" class="input-medium" placeholder="请输入查询条件" style="width:210px;"/>
			</li> 
			
			
			<li><label>差错日期：</label>
					<input id="beginErrorTime" name="beginErrorTime" type="text" readonly="readonly" maxlength="20"  style="width:115px;" class="input-medium Wdate"
					value="<fmt:formatDate value="${settleDifferRecordHis.beginErrorTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input id="endErrorTime" name="endErrorTime" type="text" readonly="readonly" maxlength="20" style="width:115px;" class="input-medium Wdate"
					value="<fmt:formatDate value="${settleDifferRecordHis.endErrorTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
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
			<th>交易类型</th>
			<th>对账批次号</th>
			
			<th>差错日期</th>
			<th>差错类型</th>
			<th>支付单号</th>
			<th>交易订单号</th>
			
			<th>我方金额</th>
			<th>对方金额</th>
			
			<th>操作人</th>
			<th>操作日期</th>
			<!-- <th>支付状态</th> -->
			<!-- <th>记账标记</th> -->
			<th>分润标识</th>
			
			<th>差错状态</th>
			<th>操作</th>
			
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="SettleDifferRrecordHis">
			<tr>
				<td>${SettleDifferRrecordHis.channelName}</td>
				<td>${SettleDifferRrecordHis.channelType}</td>
				<td>${SettleDifferRrecordHis.transType}</td>
				<td>${SettleDifferRrecordHis.checkBathNo}</td>
				
				<td><fmt:formatDate value="${SettleDifferRrecordHis.errorDate}" type="both" pattern="yyyy-MM-dd"/></td>
				
				<td>${SettleDifferRrecordHis.differType}</td>
				<td>${SettleDifferRrecordHis.paymentId}</td>
				<td>${SettleDifferRrecordHis.transNo}</td>
				
				<td>
					<fmt:formatNumber value="${SettleDifferRrecordHis.requestAmount}" pattern="￥0.0000" />
				</td>
				
				<td>
					<fmt:formatNumber value="${SettleDifferRrecordHis.successAmount}" pattern="￥0.0000" />
					<c:choose>
						<c:when test="${SettleDifferRrecordHis.QT=='YQT'}">
							--
						</c:when>
					</c:choose>
				</td>
				<%-- <td>
					<fmt:formatNumber value="${SettleDifferRrecordHis.successAmount}" pattern="￥0.0000" />
				</td> --%>
				<td>${SettleDifferRrecordHis.updateAuthor}</td>
				<td><fmt:formatDate value="${SettleDifferRrecordHis.operationDate}" type="both" pattern="yyyy-MM-dd"/></td>
				<%-- <td>${SettleDifferRrecordHis.transStatus}</td> --%>
				<%-- <td>${SettleDifferRrecordHis.isBill}</td> --%>
				<td>${SettleDifferRrecordHis.isProfit}</td>
				
				<td>${SettleDifferRrecordHis.handleMessage}</td>
				
				<shiro:hasPermission name="reconciliation:SettleDifferRrecordHis:view"><td <c:choose>
			     		<c:when test="${SettleDifferRrecordHis.BD=='YBD'}">
			     		style="background:#707c9b"	
			     		</c:when>
			     		<c:when test="${SettleDifferRrecordHis.CD=='YCD'}">
			     		 style="background:#707c9b"
			     		</c:when>
			     		<c:when test="${SettleDifferRrecordHis.QXCY=='YQXCY'}">
			     		 style="background:#707c9b"
			     		</c:when>
			     		<c:when test="${SettleDifferRrecordHis.QT=='YQT'}">
			     		 style="background:#707c9b"
			     		</c:when>
			     		
			     		<c:when test="${SettleDifferRrecordHis.BD=='BD'}">
			     		style="background:red"
			     		</c:when>
			     		<c:when test="${SettleDifferRrecordHis.CD=='CD'}">
			     		 style="background:red"
			     		</c:when>
			     		<c:when test="${SettleDifferRrecordHis.QXCY=='QXCY'}">
			     		 style="background:red"
			     		</c:when>
			     		<c:when test="${SettleDifferRrecordHis.QT=='QT'}">
			     		 style="background:red"
			     		</c:when>
			     	</c:choose>>
				<c:choose>
					<%-- <c:when test="${SettleDifferRrecordHis.BD=='BD'}">
						<a style="cursor:pointer;" class="checkPass"  value-url="${ctx}/reconciliation/SettleDifferRrecordHis/differences/addbd/${SettleDifferRrecordHis.differId}/${page.pageNo}">补单</a>
					</c:when> --%>
					<c:when test="${SettleDifferRrecordHis.BD=='YBD'}">
						已补单
					</c:when>
				</c:choose>
				
				<c:choose>
					<%-- <c:when test="${SettleDifferRrecordHis.CD=='CD'}">
					<a style="cursor:pointer;" class="checkPass"  value-url="${ctx}/reconciliation/SettleDifferRrecordHis/differences/addcd/${SettleDifferRrecordHis.differId}/${page.pageNo}">撤单</a>
					</c:when> --%>
					<c:when test="${SettleDifferRrecordHis.CD=='YCD'}">
						已撤单
					</c:when>
				</c:choose>
				
				<c:choose>
					<%-- <c:when test="${SettleDifferRrecordHis.QXCY=='QXCY'}">
					<a style="cursor:pointer;" class="checkPass"  value-url="${ctx}/reconciliation/SettleDifferRrecordHis/differences/agree/${SettleDifferRrecordHis.differId}/${page.pageNo}">取消差异</a>
					</c:when> --%>
					<c:when test="${SettleDifferRrecordHis.QXCY=='YQXCY'}">
						人工取消
					</c:when>
				</c:choose>
				
				<c:choose>
					<%-- <c:when test="${SettleDifferRrecordHis.QT=='QT'}">
					<a id="qtAddId"  style="cursor:pointer;" class="checkPass"  value-url="${ctx}/reconciliation/SettleDifferRrecordHis/differences/qtAdd/${SettleDifferRrecordHis.differId}/${page.pageNo}">取消差异</a><!--    id="qtAddId" onclick='checkClick("${SettleDifferRrecordHis.differId}")' class="checkCss" href="#" -->
					</c:when> --%>
					<c:when test="${SettleDifferRrecordHis.QT=='YQT'}">
						人工取消
					</c:when>
				</c:choose>
				</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
</body>
</html>