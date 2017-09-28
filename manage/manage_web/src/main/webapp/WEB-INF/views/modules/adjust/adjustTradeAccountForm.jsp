<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>调账-交易维度</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function() {
		//$("#name").focus();
		$("#inputForm").validate({
			submitHandler: function(form){
				if(checkForm()){
					loading('正在提交，请稍等...');
					$("select[name='balanceDirection']").attr("disabled",false); 
					form.submit();
				}
			},
			errorContainer: "#messageBox",
			errorPlacement: function(error, element) {
				$("#messageBox").text("输入有误，请先更正。");
				if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
					error.appendTo(element.parent().parent());
				} else {
					error.insertAfter(element);
				}
			}
		});
		
	});
	
	
	//点击增加按钮，在最后一行后添加新的数据
	function addAdjustAccount(obj){
		var $tbody = $("#tbody");
		//获取最后一行数据的套号
		var no = $tbody.find("tr:last").find("td:first").find("input").val();
		var newIndex = parseInt(no)+1;
		var htmlDiv = [];
		htmlDiv.push('');
		htmlDiv.push('<tr>');
		htmlDiv.push('<td><input name="seno" value="'+newIndex+'" style="width:20px" readonly="true"/></td>');
		htmlDiv.push('<td><select name="type" style="width:90px;"><option value="1">个人账户</option><option value="2">商户账户</option><option value="3">内部账户</option></select></td>');
		htmlDiv.push('<td><input name="accountId" htmlEscape="false" class="input-xlarge required" style="width:160px"/>&nbsp;<input class="btn btn-primary" type="button" onclick="queryMerchantAccount(this)" value="查询"/></td>');
		htmlDiv.push('<td><label name="accountName">--</label></td>');
		htmlDiv.push('<td><label name="balanceAmount">--</label></td>');
		htmlDiv.push('<td name="balanceDirectionTd">--</td>');
		htmlDiv.push('<td name="adjustAmountTd">--</td>');
		htmlDiv.push('<td><input class="btn btn-danger" type="button" onclick="deleteAdjustAccount(this)" value="删除"/></td>');
		htmlDiv.push('</tr>');
		htmlDiv.push('');
		$tbody.append(htmlDiv.join(''));
	}
	
	
	//删除一行数据
	function deleteAdjustAccount(obj){
		var totalCount = ${totalCount};
		var count = totalCount - 1;
		var currentIndex = getNum(obj);
		var index = parseInt(currentIndex) - 1;
		$("#tbody > tr").eq(index).remove();
		//重新计算套号
		var trs = $("#tbody > tr");
		console.log(trs);
		$.each(trs, function(i, item){
			trs.find("input[name='seno']").eq(i).val(i+1+count);
		});
		
		//重新计算借贷总金额
		calculateBalance(obj);
		
	}
	
	//根据账户id获取商户信息
	function queryMerchantAccount(obj){
		var totalCount = ${totalCount};
		var count = totalCount - 1;
		var currentIndex = getNum(obj);
		var index = parseInt(currentIndex) - 1;
		var type = $("select[name='type']").eq(index).val();
		var accountId = $("input[name='accountId']").eq(index).val();
		$.ajax({
			type : "POST",
			url : "${ctx}/adjust/adjustAccount/getMerchantAccountByAccountIdAndType",
			data : {
				"accountId" : accountId,
				"type" : type
			},
			dataType : "json",
			success : function(data){
				if(data == null){
					alert("没有对应的商户信息，请检查账号和账户类型！");
				}else{
					$("label[name='accountName']").eq(index).text(data.accountName);
					$("label[name='balanceAmount']").eq(index).text(data.balanceAmount);
					var balanceDirection = '<select onchange="changeDirection(this)" name="balanceDirection" style="width:70px;"><option value="D">借方</option><option value="C">贷方</option></select>';
					var adjustAmount = '<input name="amount" htmlEscape="false" class="input-xlarge required number" onkeyup="parent.amountCheck(this);" onblur="calculateBalance(this)" style="width:150px"/>';
					var remark = '<input name="remark" htmlEscape="false" class="input-xlarge required" style="width:220px"/>';
					$("#tbody > tr").eq(index).find("td[name='balanceDirectionTd']").html(balanceDirection);
					$("#tbody > tr").eq(index).find("td[name='adjustAmountTd']").html(adjustAmount);
				}
			}
		});
	}
	
	
	//获取当前tr的行号
	function getNum(obj){
		return $(obj).parent().parent().find("input").val();
	}
	
	//校验金额是不是都有值
	function checkInputNull(){
		var trs = $("#tbody > tr");
		var mes = "";
		var nums = new Array();
		$.each(trs, function(i, item){
			var adjustAmount = $(this).find("input[name='amount']").val();
			if(adjustAmount ==undefined || adjustAmount==null || adjustAmount==''){
				nums.push(i+1);
			}
		});
		if(nums.length > 0){
			mes = "第"+nums.join(",")+"行的金额为空，请检查！";
		}
		return mes;
	}
	
	//校验金额是不是都有值
	function checkInputAmount(){
		var trs = $("#tbody > tr");
		var mes = "";
		var nums = new Array();
		$.each(trs, function(i, item){
			var adjustAmount = $(this).find("input[name='amount']").val();
			var balanceAmount = $(this).find("label[name='balanceAmount']").text();
			var balanceDirection = $(this).find("select[name='balanceDirection']").val();
			if(parseFloat(balanceAmount) < parseFloat(adjustAmount) && balanceDirection=='D'){
				nums.push(i+1);
			}
		});
		if(nums.length > 0 ){
			mes = "第"+nums.join(",")+"行的金额大于账户的余额";
		}
		return mes;
	}
	
	//资金试算平衡
	function checkBalance(){
		var mes = checkInputNull();
		if(mes != ""){
			alert(mes);
			return;
		}
		
		var totalDebitAmount = $("#totalDebitAmount").text();
		if(totalDebitAmount == undefined){
			totalDebitAmount = 0;
		}
		var totalCreditAmount = $("#totalCreditAmount").text();
		if(totalCreditAmount == undefined){
			totalCreditAmount = 0;
		}
		var title;
		if(parseFloat(totalDebitAmount) == parseFloat(totalCreditAmount)){
			title = "资金平衡检查成功！";
		}else{
			title = "资金平衡检查失败！";
		}
		parent.setCheckBalanceVal(title, totalDebitAmount, totalCreditAmount);
		parent.showCheckBalanceWindow();
	}
	
	//计算借贷总金额
	function calculateBalance(obj){
		var totalCount = 0;
		var totalDebitAmount = 0;
		var totalCreditAmount = 0;
		var trs = $("#tbody > tr");
		$.each(trs, function(i, item){
			var adjustAmount = $(this).find("input[name='amount']").val();
			var balanceDirection = $(this).find("select[name='balanceDirection']").val();
			if(adjustAmount != undefined && adjustAmount != ''){
				//计算借贷总金额
				totalCount++;
				if(balanceDirection == 'D'){
					totalDebitAmount += parseFloat(adjustAmount);
				}else{
					totalCreditAmount += parseFloat(adjustAmount);
				}
				
			}
		});
		$("#totalCount").text(totalCount);
		$("#totalDebitAmount").text(totalDebitAmount);
		$("#totalCreditAmount").text(totalCreditAmount);
		
	}
	
	//选择借贷方向时触发的事件
	function changeDirection(obj){
		calculateBalance(obj)
	}
	
	//提交表单时，校验下表单元素正确性
	function checkForm(){
		var mes = checkInputNull();
		if(mes != ""){
			alert(mes);
			return false;
		}
		
		var totalDebitAmount = $("#totalDebitAmount").text();
		if(totalDebitAmount == undefined){
			totalDebitAmount = 0;
		}
		var totalCreditAmount = $("#totalCreditAmount").text();
		if(totalCreditAmount == undefined){
			totalCreditAmount = 0;
		}
		if(parseFloat(totalDebitAmount) != parseFloat(totalCreditAmount)){
			alert("借贷不相等，请检查！");
			return false
		}
		
		return true;
	}
	
	
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/adjust/adjustAccount/form">调账-交易维度</a></li>
	</ul><br/>
	<form id="inputForm" modelAttribute="adjustAccount" action="${ctx}/adjust/adjustTradeAccount/save" method="post" class="form-horizontal">
		<sys:message content="${message}"/>	
		<input type="hidden" name="totalCount" value="${totalCount-1}" />
		<input type="hidden" name="logIds" value="${logIds}" />
		<input type="hidden" name="trialBalanceTransId" value="${trialBalanceTransId}" />
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th width="5%">套号</th>
					<th width="10%">账户类型</th>
					<th width="15%">账号</th>
					<th width="20%">户名</th>
					<th width="10%">当前余额</th>
					<th width="10%">借贷</th>
					<th width="10%">金额</th>
					<shiro:hasPermission name="adjust:adjustAccount:edit"><th width="18%">操作</th></shiro:hasPermission>
				</tr>
			</thead>
			<tbody id="tbody">
				<c:forEach items="${merchantLogList}" var="merchantLog" varStatus="i">
					<tr>
						<td><input name="seno" value="${i.index + 1 }" style="width:20px" readonly="true"/></td>
						<td>
							<select name="type" style="width:90px;" disabled="disabled">
								<option value="1" <c:if test="${merchantLog.type=='1' }"> selected="selected"</c:if>>个人账户</option>
								<option value="2" <c:if test="${merchantLog.type=='2' }"> selected="selected"</c:if>>商户账户</option>
								<option value="3" <c:if test="${merchantLog.type=='3' }"> selected="selected"</c:if>>内部账户</option>
							</select>
						</td>
						<td><input name="accountId" htmlEscape="false" class="input-xlarge" style="width:160px" value="${merchantLog.accountId }" readonly="readonly"/></td>
						<td><label name="accountName">${merchantLog.accountName }</label></td>
						<td><label name="balanceAmount">${merchantLog.balanceAmount }</label></td>
						<td name="balanceDirectionTd">
							<select name="balanceDirection" style="width:70px;" disabled="disabled">
								<option value="D" <c:if test="${merchantLog.balanceDirection == 'D' }"> selected="selected"</c:if>>借方</option>
								<option value="C" <c:if test="${merchantLog.balanceDirection == 'C' }"> selected="selected"</c:if>>贷方</option>
							</select>
						</td>
						<td name="adjustAmountTd"><input name="amount" htmlEscape="false" class="input-xlarge required number" value="${merchantLog.balanceAmountChanges }" style="width:150px" readonly="readonly"/></td>
						<td>--</td>
					</tr>
				</c:forEach>
				<tr>
					<td><input name="seno" value="${totalCount }" style="width:20px" readonly="true"/></td>
					<td>
						<select name="type" style="100px;">
		                    <option value="1">个人账户</option>
		                    <option value="2">商户账户</option>
		                    <option value="3">内部账户</option>
						</select>
					</td>
					<td>
						<input name="accountId" htmlEscape="false" class="input-xlarge required" style="width:160px"/>&nbsp;<input class="btn btn-primary" type="button" onclick="queryMerchantAccount(this)"  value="查询"/>
					</td>
					<td><label name="accountName">--</label></td>
					<td><label name="balanceAmount">--</label></td>
					<td name="balanceDirectionTd">--</td>
					<td name="adjustAmountTd">--</td>
					<td><input class="btn btn-primary" type="button" onclick="addAdjustAccount(this)" value="增加"/></td>
				</tr>
			</tbody>
			<tfoot>
				<tr height="50px;">
					<th colspan="9">
						总笔数：<label id="totalCount">${totalCount-1 }</label>&nbsp;&nbsp;&nbsp;
						借方总金额：<label id="totalDebitAmount">${dAmount }</label>&nbsp;&nbsp;&nbsp;
						贷方总金额：<label id="totalCreditAmount">${cAmount }</label>
					</th>
				</tr>
				<tr height="60px;" >
					<th colspan="9">
						调账原因：<input name="reason" htmlEscape="false" class="input-xlarge required" style="width:300px"/>
						<input id="checkAmount" class="btn btn-warning" type="button" onclick="checkBalance()" value="检查资金平衡"/>&nbsp;&nbsp;&nbsp;
						<input id="submitBtn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;&nbsp;&nbsp;
						<input id="backBtn" class="btn" type="button" value="返回" onclick="history.go(-1);"/>
					</th>
				</tr>
			</tfoot>
		</table>	
	</form>
</body>
</html>