<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>交易管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function() {
		//处理取消
		$("#ngp_ok_c").on("click",function(){
			$("#ngp_cover").hide();
			$(".ngp_pop_three_cl").hide();
		});
		//处理确定
		$("#ngp_ok_q").on("click",function(){
			$("#ngp_cover").hide();
			$(".ngp_pop_three_cl").hide();
			var gatewayId = $("#gatewayId").val();
			var transType = $("#transType").val();
			var result = $("#result").val();
			var processDesc = $("#processDesc").val();
			var bankStatus = $("#bankStatus").val();
			var data = {
                exceptionId:gatewayId,
                transType:transType,
                processDesc:processDesc,
                processResult:result,
                bankStatus:bankStatus
		    }
			$.ajax({
                type:"post",
                url:"${ctx}/payment/exceptionRecord/processException",
                data:data,
                success: function(data) {
                    var result = data.result;
                    parent.showThreeEp();
                    parent.changeThreeNameEp(result,transType,gatewayId);
                },
                error:function(data){
                	parent.showThreeEp();
                	parent.changeThreeNameEp("系统错误，请稍后再试");
                }
            });

		});
	});
	//查询
    function queryBankResult(gatewayId){
    	parent.show_one();
        var data = {
            exceptionId:gatewayId,
            transType:"PAYMNT"
        }
        $.ajax({
            type:"post",
            url:"${ctx}/payment/exceptionRecord/queryChargeResult",
            data:data,
            success: function(data) {
                var result = data.result;
                parent.showThree();
                parent.changeThreeName(result);
                $("#ngp_cover").hide();
                if(result.indexOf("成功") > 0){
                	$("#bankStatus").val("1000");
                }else{
                	$("#bankStatus").val("1002");
                }
                return;
            },
            error:function(data){
            	parent.showThreeEp();
                parent.changeThreeNameEp("系统错误，请稍后再试");
                $("#ngp_cover").hide();
            }
        });
    }
	//处理
    function processException(gatewayId,transType,result){
       	var processDesc = $("#processDesc").val();
        if($.trim(processDesc).length == 0){
			parent.showThree();
	        parent.changeThreeName("请填写处理备注");
			return;
	    }
        $("#gatewayId").val(gatewayId);
        $("#transType").val(transType);
        $("#result").val(result);
        var msg = "";
        if("1000"==result){
        	msg = "成功";
        }else{
        	msg = "失败";
        }
        showThree_cl();
        changeThreeName_cl('确认处理为'+msg+'?');
    }
	function showThree_cl(){
		$('#ngp_cover').height($(document).height());
		$("#ngp_cover").show();
		$(".ngp_pop_three_cl").show();
		$("html,body").animate({scrollTop:0},500);
	}
	function changeThreeName_cl(a){
		$("#three_text_cl").text(a);
	}
    </script>
	<style>
	#ngp_cover{position: absolute;width:100%;height:100%;background-color:#000;opacity:0.28;z-index:1;display: none;}
	.ngp_pop_three_cl{position: absolute; width:400px;height:220px;top:50%;left:50%;background-color:#fff;z-index:8;margin-left:-200px;
		    margin-top:-110px;border-radius: 5px;display: none;border: solid 2px rgba(0,0,0,0.5);}
	.ngp_name_text{font-size: 16px;color: #333;text-align: center;margin-top: 30px;}
	.ngp_btn_kt{margin-top: 30px;text-align: center;}
	.ngp_btn_kt span{background:#4287f5;width:120px; height:35px;line-height: 35px;text-align: center;color: #fff;
	        margin: auto;font-size: 14px;font-weight: 400; cursor: pointer;display: inline-block;margin-left: 10px;}
	        
	 .control-group{
	 	width:40%;
	 	float:left;
	 }
	</style>
</head>
<body>
	<div id="ngp_cover"></div>
	<div class="ngp_pop_three_cl">
	    <p class="ngp_name_text" id="three_text_cl"></p>
	    <h3 class="ngp_btn_kt"><span id="ngp_ok_q">确定</span><span id="ngp_ok_c">取消</span></h3>
	</div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/payment/gatewayRecord/">支付查询</a></li>
		<li class="active"><a href="javascript:void(0)">支付详情查看</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="gatewayRecord" action="${ctx}/payment/paymentRecord/save" method="post" class="form-horizontal">
		<%--<form:hidden path="id"/>--%>
		<input type="hidden" id="bankStatus" value="">
		<sys:message content="${message}"/>		
<%-- 		<div class="control-group">
			<label class="control-label">交易类型：</label>
			<div class="controls">
				${fns:getDictLabel(paymentRecord.productCode, 'ProductCodeType', '')}
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">交易订单号：</label>
			<div class="controls">
					${gatewayRecord.gatewayId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户编码：</label>
			<div class="controls">
					${gatewayRecord.merchantId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户公司名称：</label>
			<div class="controls">
					${gatewayRecord.merchantCompany}
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">商户订单号：</label>
			<div class="controls">
					${gatewayRecord.merchantOrderNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付类型：</label>
			<div class="controls">
					${fns:getDictLabel(paymentRecord.payType, 'payType', '')}
			</div>
		</div>
<%-- 		<div class="control-group">
			<label class="control-label">通道提供方：</label>
			<div class="controls">
				${paymentRecord.channelName}
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">银行名称：</label>
			<div class="controls">
				${paymentRecord.bankName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行流水号：</label>
			<div class="controls">
				${paymentRecord.bankSerialNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${paymentRecord.payAmount}" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实际支付金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${paymentRecord.successAmount}" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续费：</label>
			<div class="controls">
				<fmt:formatNumber value="${gatewayRecord.feeAmount}" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续费来源：</label>
			<div class="controls">
				-
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续费扣除方式：</label>
			<div class="controls">
				${fns:getDictLabel(gatewayRecord.feeType, 'FeeDeductType', '')}
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">服务器请求IP：</label>
			<div class="controls">
				${gatewayRecord.requestIp}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户请求IP：</label>
			<div class="controls">
				${gatewayRecord.requestUserIp}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易状态：</label>
			<div class="controls">
						${fns:getDictLabel(gatewayRecord.status, 'PaymentRecordStatus', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通知商户状态：</label>
			<div class="controls">
				${fns:getDictLabel(notifyRecord.status, 'NotifyRecordStatus', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付完通知商户的URL：</label>
			<div class="controls">
					${gatewayRecord.notifyUrl}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付完返回到商户的URL：</label>
			<div class="controls">
					${gatewayRecord.callbackUrl}
			</div>
		</div>
<!-- 		<div class="control-group">
			<label class="control-label">维系员工：</label>
			<div class="controls">
-
			</div>
		</div> -->
		<div class="control-group">
			<label class="control-label">创建人：</label>
			<div class="controls">
				-
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建日期：</label>
			<div class="controls">
					<fmt:formatDate value="${paymentRecord.payTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
<!-- 		<div class="control-group">
			<label class="control-label">会计日期：</label>
			<div class="controls">
				-
			</div>
		</div> -->
		<div class="control-group">
			<label class="control-label">修改人：</label>
			<div class="controls">
				-
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">修改日期：</label>
			<div class="controls">
				-
			</div>
		</div>
		
		<div>
			<label class="control-label">退款记录：</label>
			</div>
		
		</div>
		
		<div class="controls">
		<table id="contentTable" class="table   table-bordered table-condensed">
					<thead>
						<tr>
	
							<th>交易订单号</th>
							<th>退款金额</th>
							<th>退款来源</th>
							<th>创建时间</th>
							<th>状态 </th>

						</tr>
					</thead>
					<tbody>
					<c:forEach items="${refundList}" var="refundRecord">
						<tr>
							<td>
								${refundRecord.refundId}
							</td>
							<td>
								<fmt:formatNumber value="${refundRecord.refundAmount}" pattern="￥#,##0.00" />
							</td>
							<td>
								${fns:getDictLabel(refundRecord.refundFrom, 'RefundFrom', '')}
							</td>
							<td>
								<fmt:formatDate value="${refundRecord.refundTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								${fns:getDictLabel(refundRecord.status, 'RefundStatus', '')}
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
		</div>
		
		
		<div class="control-group">
			<label class="control-label">分润记录：</label>
		</div>
		<div class="controls">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>分润号</th>
							<th>入款商户编码</th>
							<th>入款商户公司名称</th>
							<th>分润金额</th>
							<th>分润发起时间</th>
							<th>分润状态</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${shareAccountDetails}" var="shareAccountRecordDetail">
						<tr>

							<td>
								${shareAccountRecordDetail.shareId}
							</td>
							<td>
								${shareAccountRecordDetail.slaverMerchantId}
							</td>
							<td>
								${shareAccountRecordDetail.slaverMerchantCompany}
							</td>
							<td>
								${shareAccountRecordDetail.shareAmount}
							</td>
							<td>
								<fmt:formatDate value="${shareAccountRecordDetail.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								${fns:getDictLabel(shareAccountRecordDetail.status, 'ShareStatus', '')}
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
		
		</div>
		<c:if test="${not empty exception}">
	        <div class="control-group">
	            <label class="control-label">处理备注：</label>
	            <div class="controls">
	                <form:textarea path="processDesc" htmlEscape="false" rows="3" maxlength="200" class="input-xxlarge"/>
	            </div>
	        </div>
	    </c:if>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
			<c:if test="${not empty exception}">
	           <input id="queryResult" class="btn" type="button" value="查询银行处理结果" onclick="queryBankResult('${gatewayRecord.gatewayId}')"/>
	           <c:if test="${gatewayRecord.status == 'PAYING'}">
	           		<input id="processSuccess" class="btn" type="button" value="处理为成功" onclick="processException('${gatewayRecord.gatewayId}','PAYMNT','1000')"/>
	           		<input id="processFail" class="btn" type="button" value="处理为失败" onclick="processException('${gatewayRecord.gatewayId}','PAYMNT','1002')"/>
	           </c:if>
        	</c:if>
		</div>
	</form:form>
	<input type="hidden" id="gatewayId" value=""/>
	<input type="hidden" id="transType" value=""/>
	<input type="hidden" id="result" value=""/>
</body>
</html>