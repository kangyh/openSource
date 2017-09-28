<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>交易管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		//查询
		function queryBankResult(paymentId, payType){
			var data = {
				paymentId:paymentId,
				payType:payType
			};
			$.ajax({
				type:"post",
				url:"${ctx}/payment/exceptionRecord/queryRefundBankResult",
				data:data,
				success: function(data) {
					var result = data.result;
					parent.showThree();
					parent.changeThreeName(result);
				},
				error:function(){
					parent.showThreeEp();
					parent.changeThreeNameEp("系统错误，请稍后再试");
				}
			});
		}
		//处理
		function processException(refundId,result){
			var processDesc = $("#processDesc").val();
			if($.trim(processDesc).length == 0){
				parent.showThree();
				parent.changeThreeName("请填写处理备注");
				return;
			}
			$("#refundId").val(refundId);
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
	$(document).ready(function() {
		//处理取消
		$("#ngp_cancel").on("click",function(){
			$("#ngp_cover").hide();
			$(".ngp_pop_three_cl").hide();
		});
		//处理确定
		$("#ngp_ok").on("click",function(){
			$("#ngp_cover").hide();
			$(".ngp_pop_three_cl").hide();
			var refundId = $("#refundId").val();
			var transType = "REFUND";
			var result = $("#result").val();
			var processDesc = $("#processDesc").val();
			var data = {
				exceptionId:refundId,
				transType:transType,
				processDesc:processDesc,
				processResult:result
			};
			$.ajax({
				type:"post",
				url:"${ctx}/payment/exceptionRecord/processException",
				data:data,
				success: function(data) {
					var result = data.result;
					parent.showThreeEp();
					parent.changeThreeNameEp(result, transType, refundId);
				},
				error:function(data){
					parent.showThreeEp();
					parent.changeThreeNameEp("系统错误，请稍后再试", transType, refundId);
				}
			});
		});
	});
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
		<h3 class="ngp_btn_kt"><span id="ngp_ok">确定</span><span id="ngp_cancel">取消</span></h3>
	</div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/payment/refundRecord/">退款管理</a></li>
		<li class="active"><a href="javascript:void(0)">退款详情查看</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="refundRecord" action="${ctx}/payment/paymentRecord/save" method="post" class="form-horizontal">
		<%--<form:hidden path="id"/>--%>
		<sys:message content="${message}"/>		
<%-- 		<div class="control-group">
			<label class="control-label">来源方式：</label>
			<div class="controls">
					${fns:getDictLabel(refundRecord.refundFrom, 'RefundType', '')}
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">商户编号：</label>
			<div class="controls">
				${paymentRecord.merchantId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户公司名称：</label>
			<div class="controls">
					${refundRecord.merchantCompany}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付类型：</label>
			<div class="controls">
				${fns:getDictLabel(paymentRecord.payType, 'payType', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易订单号：</label>
			<div class="controls">
				${refundRecord.refundId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付单时间：</label>
			<div class="controls">
					<fmt:formatDate value="${paymentRecord.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">退款来源：</label>
			<div class="controls">
							${fns:getDictLabel(refundRecord.refundFrom, 'RefundFrom', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">原商户订单号：</label>
			<div class="controls">
				${originPayment.merchantOrderNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单金额：</label>
			<div class="controls">
					<fmt:formatNumber value="${originPayment.payAmount}" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实际支付金额：</label>
			<div class="controls">
					<fmt:formatNumber value="${originPayment.successAmount}" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">原支付订单号：</label>
			<div class="controls">
					${originPayment.paymentId}
			</div>
		</div>
<%-- 		<div class="control-group">
			<label class="control-label">通道合作方：</label>
			<div class="controls">
				${paymentRecord.channelName }
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">银行名称：</label>
			<div class="controls">
				${paymentRecord.bankName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行卡号：</label>
			<div class="controls">
				${paymentRecord.bankcardNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行卡类型：</label>
			<div class="controls">
				${fns:getDictLabel(paymentRecord.bankcardType, 'BankcardType', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">退款金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${refundRecord.refundAmount}" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">退款状态：</label>
			<div class="controls">
				${fns:getDictLabel(refundRecord.status, 'RefundStatus', '')}
			</div>
		</div>
		<c:if test="${refundRecord.status eq 'FAILED'}">
			<div class="control-group">
				<label class="control-label">失败原因：</label>
				<div class="controls">
						${refundRecord.refundDetail}
				</div>
			</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">通知商户状态：</label>
			<div class="controls">
				${fns:getDictLabel(notifyRecord.status, 'NotifyRecordStatus', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">退款申请时间：</label>
			<div class="controls">
				<fmt:formatDate value="${refundRecord.refundTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">退款处理时间：</label>
			<div class="controls">
					<fmt:formatDate value="${refundRecord.refundSuccessTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				${paymentRecord.description}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户端记账情况：</label>
			<div class="controls">
				${merchantAccountStatus}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通道端记账情况：</label>
			<div class="controls">
				${channelAccountStatus}
			</div>
		</div>
<!-- 		<div class="control-group">
			<label class="control-label">会计日期：</label>
			<div class="controls">
				-
			</div>
		</div> -->

		<c:if test="${not empty exception}">
			<div class="control-group">
				<label class="control-label">处理备注：</label>
				<div class="controls">
					<form:textarea path="processDesc" htmlEscape="false" rows="3" maxlength="200" class="input-xxlarge"/>
				</div>
			</div>
		</c:if>
		<div style="clear:both"></div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
			<c:if test="${not empty exception}">
				<input id="queryResult" class="btn" type="button" value="查询银行处理结果" onclick="queryBankResult('${paymentRecord.paymentId}','${originPayment.payType}')"/>
				<c:if test="${refundRecord.status == 'RFDING' || refundRecord.status == 'FAILED'}">
					<input id="processFail" class="btn" type="button" value="处理为失败" onclick="processException('${refundRecord.refundId}','1002')"/>
				</c:if>
			</c:if>
		</div>
	</form:form>
	<input type="hidden" id="refundId" value=""/>
	<input type="hidden" id="result" value=""/>
</body>
</html>