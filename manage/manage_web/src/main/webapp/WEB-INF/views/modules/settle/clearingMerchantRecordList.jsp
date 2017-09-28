<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>清结算管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
		//用于验证
		var flg = true;

		$(document).ready(function() {
			var checkStatus = $("#checkStatus option[selected]").val();
			if(checkStatus=='Y'){
				$('#checkStatusY').css('display','block');
			}else{
				$('#checkStatusY').css('display','none');
			}
		});
		//验证搜索条件
		var validateFrom = {
			validate: function(form){
                var bgTime = $("#beginCheckTime").val();
                var endTime = $("#endCheckTime").val();
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

		//验证是否是数字
		function checkValue(obj){
			if(isNaN(obj)){
				$("#messageBox").text("请输入数字！");
				flg = false;
				return;
			}else{
				$("#messageBox").text("");
				flg = true;
				return;
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
            $("form :input").not(":button, :submit, :reset, :hidden").val("").removeAttr("checked").remove("selected");
            $("#searchForm").submit();
		}

		//对账状态选择已对账
		function checkedStatusY(obj){
			var checkStatus = obj.value;
			if(checkStatus=='Y'){
				$('#checkStatusY').css('display','block');
			}else{
				$('#checkStatusY').css('display','none');
			}
		}

		//文件导出
		function onExport(){
            var flag = true;
            $("form :input").not(":button, :submit, :reset, :hidden").each(function (element) {
                if(this.value != ""){
                    var actionURL = $("#searchForm").attr("action");
                    $("#searchForm").attr("action", $("#searchForm").attr("action") + "export");
                    validateFrom.validate($("#searchForm"));
                    $("#searchForm").attr("action", actionURL);
                    flag = false;
                    return
                }
            });
            if(flag){
                alertx("请选择导出条件!");
            }

			/*var actionURL = $("#searchForm").attr("action");
	        $("#searchForm").attr("action",$("#searchForm").attr("action")+"export");
			validateFrom.validate($("#searchForm"));
			$("#searchForm").attr("action",actionURL);*/
		}

		//修复历史数据
		function onEditHisDate(){
            loading('正在修复历史数据，请稍等...');
			$.post($("#searchForm").attr("action")+"editHisData",{
				},function(data){
			    	closeLoading();
					parent.showThree();
					parent.changeThreeName(data);
				}
			);
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/settle/clearingMerchantRecordQuery/">商户清算单据列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="clearingMerchantRecord" action="${ctx}/settle/clearingMerchantRecordQuery/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>交易类型：</label>
				<form:select id="transType" path="transType" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${productType}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>结算状态：</label>
				<form:select id="settleStatus" path="settleStatus" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${settleStatus}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>对账状态：</label>
				<form:select onchange="checkedStatusY(this)" id="checkStatus" path="checkStatus" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${checkStatus}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<li id="checkStatusY" style="display: none;"><label>状态描述：</label>
				<form:select id="checkFlg" path="checkFlg" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<form:option value="QSTS" label="平账"/>
					<form:option value="QFTS" label="差异账"/>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form">
			<li><label>商户编码：</label>
				<form:input path="merchantId" onchange="checkValue(this.value)" htmlEscape="false" maxlength="10" style="width:256px;" class="input-medium required"/>
			</li>
			<li><label>交易订单号：</label>
				<form:input path="transNo" onchange="checkValue(this.value)" htmlEscape="false" maxlength="26" style="width:256px;" class="input-medium required"/>
			</li>
			<li><label>结算单号：</label>
				<form:input path="settleBath" onchange="checkValue(this.value)" htmlEscape="false" maxlength="17" style="width:256px;" class="input-medium required"/>
			</li>
		</ul>
		<ul class="ul-form">
			<li><label>成功支付时间：</label>
				<input id="beginCheckTime" name="beginCheckTime" type="text" readonly="readonly" maxlength="20" style="width:115px;" class="input-medium Wdate"
					   value="<fmt:formatDate value="${clearingMerchantRecord.beginCheckTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input id="endCheckTime" name="endCheckTime" type="text" readonly="readonly" maxlength="20" style="width:115px;" class="input-medium Wdate"
					   value="<fmt:formatDate value="${clearingMerchantRecord.endCheckTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>商户标识：</label>
				<form:select path="merchantFlag" htmlEscape="false" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getEnumList('merchantFlag')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>

			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" onclick="onExport()" value="导出"/></li>
			<shiro:hasPermission name="settle:clearingMerchantRecord:edit"><li class="btns"><input class="btn btn-primary" type="button" onclick="onEditHisDate()" value="修复历史数据"/></li></shiro:hasPermission>
			<div id="messageBox" style="font-size: 15px; color: red;"></div>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>实际支付总金额</th>
			<th>订单应结算总金额</th>
			<th>订单手续费总金额</th>
		</tr>
		</thead>
		<tbody>
		<tr>
			<td><fmt:formatNumber value="${clearingMerSumNum.sumRequestAmount}" pattern="￥0.0000" /></td>
			<td><fmt:formatNumber value="${clearingMerSumNum.sumSettleAmountPlan}" pattern="￥0.0000" /></td>
			<td><fmt:formatNumber value="${clearingMerSumNum.sumFee}" pattern="￥0.0000" /></td>
		</tr>
		</tbody>
	</table>

	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>商户编码</th>
			<th>交易类型</th>
			<th>币种</th>
			<th>交易订单号</th>
			<th>实际支付金额</th>
			<th>清算流水号</th>
			<th>清算日期</th>
			<th>订单应结算日期</th>
			<th>成功支付时间</th>
			<th>订单应结算金额</th>
			<th>结算单号</th>
			<th>手续费扣除方式</th>
			<th>订单手续费</th>
			<th>是否分润</th>
			<th>对账状态</th>
			<th>状态描述</th>
			<th>结算状态</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="clearingMerchantRecord">
			<tr>
				<td>${clearingMerchantRecord.merchantId}</td>
				<td>${clearingMerchantRecord.transType}</td>
				<td>${clearingMerchantRecord.currency}</td>
				<td>${clearingMerchantRecord.transNo}</td>
				<%-- <td>${clearingMerchantRecord.transNoOld}</td> --%>
				<td>
					<fmt:formatNumber value="${clearingMerchantRecord.requestAmount}" pattern="￥0.0000" />
				</td>
				<td><a href="${ctx}/settle/clearingMerchantRecordQuery/more/${clearingMerchantRecord.clearingId}">${clearingMerchantRecord.settleNo}</a></td>
				<td><fmt:formatDate value="${clearingMerchantRecord.settleTime}" type="both" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${clearingMerchantRecord.settleTimePlan}" type="both" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${clearingMerchantRecord.successTime}" type="both" pattern="yyyy-MM-dd"/></td>
				<td>
					<fmt:formatNumber value="${clearingMerchantRecord.settleAmountPlan}" pattern="￥0.0000" />
				</td>
				<td>${clearingMerchantRecord.settleBath}</td>
				<td>
					<c:if test='${clearingMerchantRecord.feeWay == "" or clearingMerchantRecord.feeWay == null}'>
						--
					</c:if>
					<c:if test='${clearingMerchantRecord.feeWay != ""}'>
						${clearingMerchantRecord.feeWay}
					</c:if>
				</td>
				<td>
					<fmt:formatNumber value="${clearingMerchantRecord.fee}" pattern="￥0.0000" />
				</td>
				<td >${clearingMerchantRecord.isProfit}</td>
				<td <c:choose>
			     		<c:when test="${clearingMerchantRecord.checkStatus=='已对账'}">
			     		 style="background:#6cf683"
			     		</c:when>
			     		<c:when test="${clearingMerchantRecord.checkStatus=='未对账'}">
			     		 style="background:#707c9b"
			     		</c:when>
			     		<c:when test="${clearingMerchantRecord.checkStatus=='对账中'}">
			     		 style="background:#ffb300"
			     		</c:when>
			     	</c:choose>>${clearingMerchantRecord.checkStatus}</td>
				<td>${clearingMerchantRecord.checkFlg}</td>
				<td <c:choose>
			     		<c:when test="${clearingMerchantRecord.settleStatus=='已结算'}">
			     		 style="background:#6cf683"
			     		</c:when>
			     		<c:when test="${clearingMerchantRecord.settleStatus=='未结算'}">
			     		 style="background:#707c9b"
			     		</c:when>
			     		<c:when test="${clearingMerchantRecord.settleStatus=='结算中'}">
			     		 style="background:#ffb300"
			     		</c:when>
			     	</c:choose>>${clearingMerchantRecord.settleStatus}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>