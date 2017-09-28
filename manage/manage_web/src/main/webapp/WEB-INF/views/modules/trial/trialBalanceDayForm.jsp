<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>试算平衡-汇总管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(
			function() {
				//$("#name").focus();
				$("#inputForm")
						.validate(
								{
									submitHandler : function(form) {
										loading('正在提交，请稍等...');
										form.submit();
									},
									errorContainer : "#messageBox",
									errorPlacement : function(error, element) {
										$("#messageBox").text("输入有误，请先更正。");
										if (element.is(":checkbox")
												|| element.is(":radio")
												|| element.parent().is(
														".input-append")) {
											error.appendTo(element.parent()
													.parent());
										} else {
											error.insertAfter(element);
										}
									}
								});
			});
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/trial/trialBalanceDay/">余额平衡</a></li>
		<li class="active"><a
			href="${ctx}/trial/trialBalanceDay/form?id=${trialBalanceDay.id}">明细(${accountDate})
				</a></li>
	</ul>
	<br />
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th colspan="10" style="text-align:left; color: #6F00D2;">
				说明：<br/>
				余额方向在贷方，余额=贷方期末余额-借方期末余额   &nbsp;&nbsp;,&nbsp;&nbsp; 余额方向在借方，余额=借方期末余额-贷方期末余额<br/>
				资产类(借)=负债类(贷)+共同类(贷-借)<br/>
				<fmt:formatNumber value="${assetClass}"
								pattern="￥#,##0.00" />
				=
				<fmt:formatNumber value="${debtClass}"
								pattern="￥#,##0.00" />				
				<%="+(" %>
				<fmt:formatNumber value="${commonClassC}"
								pattern="￥#,##0.00" />		
				<%="-" %>
				<fmt:formatNumber value="${commonClassD}"
								pattern="￥#,##0.00" />		
				<%=")" %>
				</th>

			</tr>	
		</thead>
		<tbody>
			<tr>
				<th colspan="10" height="60px"
					style="text-align: center; color: #6F00D2;">资产类</th>
			</tr>
			<tr>
				<th rowspan="2" style="text-align: center; color: #46A3FF;">科目</th>
				<th rowspan="2" style="text-align: center; color: #46A3FF;">项目</th>
				<th rowspan="2" style="text-align: center; color: #46A3FF;">余额方向</th>
				<th colspan="2" style="text-align: center; color: #46A3FF;">期初余额</th>
				<th colspan="2" style="text-align: center; color: #46A3FF;">发生额</th>
				<th colspan="2" style="text-align: center; color: #46A3FF;">期末余额</th>
			</tr>
			<tr>
				<th
					style="text-align: center; color: #46A3FF; vertical-align: middle;">借方</th>
				<th
					style="text-align: center; color: #46A3FF; vertical-align: middle;">贷方</th>
				<th
					style="text-align: center; color: #46A3FF; vertical-align: middle;">借方</th>
				<th
					style="text-align: center; color: #46A3FF; vertical-align: middle;">贷方</th>
				<th
					style="text-align: center; color: #46A3FF; vertical-align: middle;">借方</th>
				<th
					style="text-align: center; color: #46A3FF; vertical-align: middle;">贷方</th>
			</tr>
			<c:forEach items="${result}" var="sum">
				<tr>
					<c:if test="${ sum.subjectType=='assetClass'}">
						<td>${sum.type}</td>
						<td>${fns:getDictLabel(sum.type, 'AccountSubject', '')}</td>
						<td>
						    <c:if test="${sum.lendingDirection =='0'}">
						            借 
						    </c:if>
						    <c:if test="${sum.lendingDirection =='1'}">
						            贷
						    </c:if>						
						</td>
						<td><fmt:formatNumber value="${sum.debitBeginBalances}"
								pattern="￥#,##0.00" /></td>
						<td><fmt:formatNumber value="${sum.creditBeginBalances}"
								pattern="￥#,##0.00" /></td>
						<td><fmt:formatNumber value="${sum.debitCurrentAmount}"
								pattern="￥#,##0.00" /></td>
						<td><fmt:formatNumber value="${sum.creditCurrentAmount}"
								pattern="￥#,##0.00" /></td>
						<td><fmt:formatNumber value="${sum.debitEndingBalances}"
								pattern="￥#,##0.00" /></td>
						<td><fmt:formatNumber value="${sum.creditEndingBalances}"
								pattern="￥#,##0.00" /></td>
					</c:if>
				</tr>
			</c:forEach>
			<tr>
				<th colspan="10" height="60px"
					style="text-align: center; color: #6F00D2;">负债类</th>
			</tr>
			<tr>
				<th rowspan="2" style="text-align: center; color: #46A3FF;">科目</th>
				<th rowspan="2" style="text-align: center; color: #46A3FF;">项目</th>
				<th rowspan="2" style="text-align: center; color: #46A3FF;">余额方向</th>
				<th colspan="2" style="text-align: center; color: #46A3FF;">期初余额</th>
				<th colspan="2" style="text-align: center; color: #46A3FF;">发生额</th>
				<th colspan="2" style="text-align: center; color: #46A3FF;">期末余额</th>
			</tr>
			<tr>
				<th
					style="text-align: center; color: #46A3FF; vertical-align: middle;">借方</th>
				<th
					style="text-align: center; color: #46A3FF; vertical-align: middle;">贷方</th>
				<th
					style="text-align: center; color: #46A3FF; vertical-align: middle;">借方</th>
				<th
					style="text-align: center; color: #46A3FF; vertical-align: middle;">贷方</th>
				<th
					style="text-align: center; color: #46A3FF; vertical-align: middle;">借方</th>
				<th
					style="text-align: center; color: #46A3FF; vertical-align: middle;">贷方</th>
			</tr>
			<c:forEach items="${result}" var="sum">
				<tr>
					<c:if test="${ sum.subjectType=='debtClass'}">
						<td>${sum.type}</td>
						<td>${fns:getDictLabel(sum.type, 'AccountSubject', '')}</td>
						<td>
						    <c:if test="${sum.lendingDirection =='0'}">
						            借 
						    </c:if>
						    <c:if test="${sum.lendingDirection =='1'}">
						            贷
						    </c:if>
						</td>						
						<td><fmt:formatNumber value="${sum.debitBeginBalances}"
								pattern="￥#,##0.00" /></td>
						<td><fmt:formatNumber value="${sum.creditBeginBalances}"
								pattern="￥#,##0.00" /></td>
						<td><fmt:formatNumber value="${sum.debitCurrentAmount}"
								pattern="￥#,##0.00" /></td>
						<td><fmt:formatNumber value="${sum.creditCurrentAmount}"
								pattern="￥#,##0.00" /></td>
						<td><fmt:formatNumber value="${sum.debitEndingBalances}"
								pattern="￥#,##0.00" /></td>
						<td><fmt:formatNumber value="${sum.creditEndingBalances}"
								pattern="￥#,##0.00" /></td>
					</c:if>
				</tr>
			</c:forEach>
			<tr>
				<th colspan="10" height="60px"
					style="text-align: center; color: #6F00D2;">共同类</th>
			</tr>
			<tr>
				<th rowspan="2" style="text-align: center; color: #46A3FF;">科目</th>
				<th rowspan="2" style="text-align: center; color: #46A3FF;">项目</th>
				<th rowspan="2" style="text-align: center; color: #46A3FF;">余额方向</th>
				<th colspan="2" style="text-align: center; color: #46A3FF;">期初余额</th>
				<th colspan="2" style="text-align: center; color: #46A3FF;">发生额</th>
				<th colspan="2" style="text-align: center; color: #46A3FF;">期末余额</th>
			</tr>
			<tr>
				<th
					style="text-align: center; color: #46A3FF; vertical-align: middle;">借方</th>
				<th
					style="text-align: center; color: #46A3FF; vertical-align: middle;">贷方</th>
				<th
					style="text-align: center; color: #46A3FF; vertical-align: middle;">借方</th>
				<th
					style="text-align: center; color: #46A3FF; vertical-align: middle;">贷方</th>
				<th
					style="text-align: center; color: #46A3FF; vertical-align: middle;">借方</th>
				<th
					style="text-align: center; color: #46A3FF; vertical-align: middle;">贷方</th>
			</tr>
			<c:forEach items="${result}" var="sum">
				<tr>
					<c:if test="${ sum.subjectType=='commonClass'}">
						<td>${sum.type}</td>
						<td>${fns:getDictLabel(sum.type, 'AccountSubject', '')}</td>
						<td>
						    <c:if test="${sum.lendingDirection =='0'}">
						            借 
						    </c:if>
						    <c:if test="${sum.lendingDirection =='1'}">
						            贷
						    </c:if>						
						</td>						
						<td><fmt:formatNumber value="${sum.debitBeginBalances}"
								pattern="￥#,##0.00" /></td>
						<td><fmt:formatNumber value="${sum.creditBeginBalances}"
								pattern="￥#,##0.00" /></td>
						<td><fmt:formatNumber value="${sum.debitCurrentAmount}"
								pattern="￥#,##0.00" /></td>
						<td><fmt:formatNumber value="${sum.creditCurrentAmount}"
								pattern="￥#,##0.00" /></td>
						<td><fmt:formatNumber value="${sum.debitEndingBalances}"
								pattern="￥#,##0.00" /></td>
						<td><fmt:formatNumber value="${sum.creditEndingBalances}"
								pattern="￥#,##0.00" /></td>
					</c:if>
				</tr>
			</c:forEach>		
		</tbody>
	</table>
	<div class="form-actions">
		<input id="btnCancel" class="btn" type="button" value="返 回"
			onclick="history.go(-1)" />
	</div>

</body>
</html>