<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>网关支付核账管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnSubmit").bind("click", function(){
				var statisticsTime = $("#statisticsTime").val();
				if(statisticsTime == null || statisticsTime == ''){
					alert("请选择统计时间");
					return false;
				}
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/gatewayAccount/dayGatewayPayStatForInput/list">网关支付核账列表</a></li>
	</ul>
	<form id="searchForm" modelAttribute="dayGatewayPayStatForInput" action="${ctx}/gatewayAccount/dayGatewayPayStatForInput/getListByStatisticTime" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>统计时间：</label>
				<input id="statisticsTime" name="statisticsTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${statisticsTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</li>
			<li><label>通道合作方：</label>
				<select name="channelCode" id="channelCode" style="width: 230px;">
					<option value="">-通道合作方-</option>
					<c:forEach var="item" items="${channelCodeList}" varStatus="status">
							<c:choose>
								<c:when test="${item.channelPartners == 'DIRCON'}">
									 <c:set var="selChannelCode" value="${item.channelPartners}_${item.bankCode}"></c:set> 
									 <c:if test="${channelCode == selChannelCode}">
										<option value="${item.channelPartners}_${item.bankCode}"  selected>${item.channelPartnersName }_${item.bankName }</option>
									 </c:if>  
									 <c:if test="${channelCode != selChannelCode}">
										<option value="${item.channelPartners}_${item.bankCode}">${item.channelPartnersName }_${item.bankName }</option>
									 </c:if>  
								</c:when>
								<c:otherwise>
									 <c:if test="${channelCode == item.channelPartners}">
										<option value="${item.channelPartners}"  selected>${item.channelPartnersName }</option>
									 </c:if>  
									 <c:if test="${channelCode != item.channelPartners}">
										<option value="${item.channelPartners}">${item.channelPartnersName }</option>
									 </c:if>  
								</c:otherwise>
							</c:choose>
					</c:forEach> 
				</select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnSave" class="btn btn-warning" type="button" value="保存"/></li>
			<li class="clearfix"></li>
		</ul>
	</form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th rowspan="2"><input type="checkbox" id="checkboxId"/>选择</th>
				<th rowspan="2">银行名称</th>
				<th rowspan="2">通道合作方</th>
				<th rowspan="2">成功笔数</th>
				<th rowspan="2">成功金额</th>
				<th rowspan="2">核对笔数</th>
				<th rowspan="2">核对金额</th>
				<th>差异笔数</th>
				<th>差异金额</th>
				<th rowspan="2">核对金额差异说明</th>
				<th rowspan="2">银行实际到账资金</th>
				<th>实际差异资金</th>
				<th rowspan="2">实际资金差异说明</th>
			</tr>
			<tr>
				<th style="font-size: 11px;">成功笔数—核对笔数</th>
				<th style="font-size: 11px;">成功金额—核对金额</th>
				<th style="font-size: 11px;">核对金额—实际到账资金</th>
			</tr>
		</thead>
		<tbody id="bodyId">
		<c:choose>
			<c:when test="${empty page.list}">
				<tr>
					<td colspan="13">没有查到相关的数据</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${page.list}" var="dayGatewayPayStatForInput" varStatus="status">
					<tr>
						<input type="hidden" name="recordId" value="${dayGatewayPayStatForInput.recordId }" />
						<td><input type="checkbox" name="checkName" value="${status.index}" /></td>
						<td style="color:#228B22;"><label name="bankName">${dayGatewayPayStatForInput.bankName }</label></td>
						<td style="color:#228B22;"><label name="channelPartnersName">${dayGatewayPayStatForInput.channelPartnersName }</label></td>
						<td style="color:#228B22;"><label name="sucessCount">${dayGatewayPayStatForInput.successBillCount }</label></td>
						<td style="color:#228B22;"><label name="sucessAmount"><fmt:formatNumber value="${dayGatewayPayStatForInput.successTradeAmt }" pattern="￥#,##0.00" /></label></td>
						<td><font color="red">*</font>&nbsp;<input name="checkSuccessCount" maxlength="7" htmlEscape="false" style="width:60px" onblur="inputCheckCount(this)"/></td>
						<td><font color="red">*</font>&nbsp;<input name="checkSuccessAmount" maxlength="20" htmlEscape="false" style="width:100px" onblur="inputCheckAmount(this)"/></td>
						<td><label name="diffCount">0</label></td>
						<td><label name="diffAmount">￥0.00</label></td>
						<td><font color="red">*</font>&nbsp;</a><input name="remark" maxlength="200" htmlEscape="false" class="input-xlarge required" style="width:200px" onblur="inputNote(this)"/></td>
						<td><font color="red">*</font>&nbsp;<input name="settleAmount" maxlength="20" htmlEscape="false" class="input-xlarge required" style="width:100px" onblur="inputSettleAmount(this)"/></td>
						<td><label name="diffCheckAmount">￥0.00</label></td> 
						<td><font color="red">*</font>&nbsp;</a><input name="remarkOther" maxlength="200" htmlEscape="false" class="input-xlarge required" style="width:200px" onblur="inputNoteOther(this)"/></td>
				</c:forEach>
			</c:otherwise>
		</c:choose>
		</tbody>
	</table>
	<script type="text/javascript">
		$(function(){
			//全选反选
			$("#checkboxId").bind("click", function(){
			    if ($(this).attr("checked")) {  
			        $(":checkbox").attr("checked", true);  
			    } else {  
			        $(":checkbox").attr("checked", false);  
			    }  
			});
			
			
			//保存
			$("#btnSave").bind("click", function(){
				var flag = false;
				var groupTime = $("#statisticsTime").val();
				if(groupTime == null || groupTime==''){
					alert("统计时间不能为空");
					flag = true;
					return false;
				}
				var selectedData = [];
				$(":checkbox[name='checkName']:checked").each(function(index, item){
					var tablerow = $(this).parent().parent();
					var recordId = tablerow.find("[name='recordId']").val();
					var failCount = tablerow.find("[name='failCount']").val();
					var failAmount = tablerow.find("[name='failAmount']").val();
					var bankName = tablerow.find("[name='bankName']").text();
					var checkSuccessCount = tablerow.find("[name='checkSuccessCount']").val().replace(/\s+/g,"");
					if(checkSuccessCount == null || checkSuccessCount==''){
						alert("选中的第"+(index+1)+"行，银行名称为【"+bankName+"】这条记录的核对笔数不能为空");
						flag = true;
						return false;
					}
					if (!isNumber(checkSuccessCount)) {
						alert("您录入的核对笔数不符合要求");
						flag = true;
						return false;
		            }
					if(checkSuccessCount < 0){
						alert("核对笔数要大于等于0");
						flag = true;
						return false;
					}
					var checkSuccessAmount = tablerow.find("[name='checkSuccessAmount']").val().replace(/\s+/g,"");
					if(checkSuccessAmount == null || checkSuccessAmount==''){
						alert("选中的第"+(index+1)+"行，银行名称为【"+bankName+"】这条记录的核对金额不能为空");
						flag = true;
						return false;
					}
					if (!isfloat(checkSuccessAmount)) {
						alert("您录入的核对金额不符合要求,金额精确到分");
						flag = true;
						return false;
		            }
					if(checkSuccessAmount < 0){
						alert("核对金额要大于等于0");
						flag = true;
						return false;
					}
					var remark = tablerow.find("[name='remark']").val().replace(/\s+/g,"");
					if(remark == null || remark==''){
						alert("选中的第"+(index+1)+"行，银行名称为【"+bankName+"】这条记录的核对金额差异说明不能为空");
						flag = true;
						return false;
					}
					var settleAmount = tablerow.find("[name='settleAmount']").val().replace(/\s+/g,"");
					if(settleAmount == null || settleAmount==''){
						alert("选中的第"+(index+1)+"行，银行名称为【"+bankName+"】这条记录的实际结算金额不能为空");
						flag = true;
						return false;
					}
					if (!isfloat(settleAmount)) {
						alert("您录入的实际结算金额不符合要求,金额精确到分");
						flag = true;
						return false;
		            }
					if(settleAmount < 0){
						alert("实际结算金额要大于等于0");
						flag = true;
						return false;
					}
					var remarkOther = tablerow.find("[name='remarkOther']").val().replace(/\s+/g,"");
					if(remarkOther == null || remarkOther==''){
						alert("选中的第"+(index+1)+"行，银行名称为【"+bankName+"】这条记录的实际资金差异说明不能为空");
						flag = true;
						return false;
					}
					selectedData.push({'recordId':recordId,
						               'checkSuccessCount':checkSuccessCount,
						               'checkSuccessAmount':checkSuccessAmount,
						               'remark':remark,
						               'settleAmount':settleAmount,
						               'remarkOther':remarkOther,
						               'groupTime':groupTime});
					
				});
				
				if(selectedData.length == 0 && !flag){
					alert("请勾选要保存的记录");
					return false;
				}
				
				if(!flag){
					$.ajax({
						type : "POST",
						url : "${ctx}/gatewayAccount/dayGatewayPayStatForInput/save",
						data : {"reqData" : JSON.stringify(selectedData)},
						dataType : "json",
						async: false,
						success : function(data){
							if(data.success){
								alert("保存成功");
								$("#searchForm").submit();
							}else{
								alert(data.message);
							}
							
						}
					});
				}
				
				
				
			});
			
			
		});
	
		//录入核对笔数
		function inputCheckCount(obj){
			//设置差异笔数初始值为0
			$(obj).parent().parent().find("label[name='diffCount']").text(0);
			var checkCount = stripscript($(obj).val().replace(/\s+/g,""));
			$(obj).val(checkCount);
			if(checkCount.length > 0){
				if (!isNumber(checkCount)) {
					alert("您录入的整数不符合要求");
					return false;
	            }
				if(checkCount < 0){
					alert("核对笔数要大于等于0");
					return false;
				}
				
				//获取当前记录的成功笔数
				var sucessCount = $(obj).parent().parent().find("label[name='sucessCount']").text();
				//设置差异笔数  成功笔数-录入核对笔数
				$(obj).parent().parent().find("label[name='diffCount']").text(sucessCount-checkCount);
				
			}
		}
		
		//录入核对金额
		function inputCheckAmount(obj){
			//设置差异笔数初始值为0
			$(obj).parent().parent().find("label[name='diffAmount']").text("￥0.00");
			var checkAmount = stripscript($(obj).val().replace(/\s+/g,""));
			$(obj).val(checkAmount);
			if(checkAmount.length > 0){
				if (!isfloat(checkAmount)) {
					alert("您录入的数字不符合要求,金额精确到分");
					return false;
	            }
				if(checkAmount < 0){
					alert("核对金额要大于等于0");
					return false;
				}
				
				//获取当前记录的成功笔数
				var sucessAmount = $(obj).parent().parent().find("label[name='sucessAmount']").text().replace("￥","").replaceAll(",","");
				//设置差异笔数  成功笔数-录入核对笔数
				var amountVal = "￥" + accSub(sucessAmount,checkAmount);
				$(obj).parent().parent().find("label[name='diffAmount']").text(amountVal);
				
				
				//设置核对金额与实际到账资金差异金额
				var settleAmount = $(obj).parent().parent().find("[name='settleAmount']").val().replace(/\s+/g,"");
				if(settleAmount == null || settleAmount == ''){
					settleAmount = 0;
				}
				var diffCheckAmountVal = "￥" + accSub(checkAmount,settleAmount);
				$(obj).parent().parent().find("label[name='diffCheckAmount']").text(diffCheckAmountVal);
				
			}
		}
		
		
		//校验实际金额
		function inputSettleAmount(obj){
			var settleAmount = stripscript($(obj).val().replace(/\s+/g,""));
			$(obj).val(settleAmount);
			if(settleAmount.length > 0){
				if (!isfloat(settleAmount)) {
					alert("您录入的数字不符合要求,金额精确到分");
					return false;
	            }
				if(settleAmount < 0){
					alert("核对金额要大于等于0");
					return false;
				}
				
				//设置核对金额与实际到账资金差异金额
				var checkSuccessAmount = $(obj).parent().parent().find("[name='checkSuccessAmount']").val().replace(/\s+/g,"");
				if(checkSuccessAmount == null || checkSuccessAmount == ''){
					checkSuccessAmount = 0;
				}
				var diffCheckAmountVal = "￥" + accSub(checkSuccessAmount,settleAmount);
				$(obj).parent().parent().find("label[name='diffCheckAmount']").text(diffCheckAmountVal);
				
			}
		}
		
		//过滤说明
		function inputNote(obj){
			var inputNote = stripscript($(obj).val().replace(/\s+/g,""));
			$(obj).val(inputNote);
		}
		
		//过滤说明
		function inputNoteOther(obj){
			var inputNote = stripscript($(obj).val().replace(/\s+/g,""));
			$(obj).val(inputNote);
		}
		
		
		//校验整数
		function isNumber(oNum) {
			if (!oNum){
				return false;
			}
			var strP = /^\d+$/; //正整数
			if (!strP.test(oNum)){
				return false;
			}
			return true;
		}
		
		
		//校验浮点数
		function isfloat(oNum) {
			if (!oNum){
				return false;
			}
			var strP = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
			if (!strP.test(oNum)){
				return false;
			}
			try {
				if (parseFloat(oNum) != oNum){
					return false;
				}
			} catch (ex) {
				return false;
			}
			return true;

		}
		
		
		//两个浮点数做减法
		function accSub(num1,num2){  
	       var r1,r2,m;  
	       try{  
	           r1 = num1.toString().split('.')[1].length;  
	       }catch(e){  
	           r1 = 0;  
	       }  
	       try{  
	           r2=num2.toString().split(".")[1].length;  
	       }catch(e){  
	           r2=0;  
	       }  
	       m=Math.pow(10,Math.max(r1,r2));  
	       return (Math.round(num1*m-num2*m)/m).toFixed(2);  
	    } 
		
		
		function stripscript(s) {
		    var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\]<>/?~！@#￥……&*（）&;—|{}【】‘；：”“'。，、？]")
		        var rs = "";
		    for (var i = 0; i < s.length; i++) {
		        rs = rs + s.substr(i, 1).replace(pattern, '');
		    }
		    return rs;
		}
		
		String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {    
		    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {    
		        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);    
		    } else {    
		        return this.replace(reallyDo, replaceWith);    
		    }    
		}    

	</script>
</body>
</html>