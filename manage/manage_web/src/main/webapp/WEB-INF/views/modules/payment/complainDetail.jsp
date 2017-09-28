<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>投诉处理详情</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		//查询
		function queryBankResult(paymentId){
			var data = {
				paymentId:paymentId
			};
			$.ajax({
				type:"post",
				url:"${ctx}/payment/exceptionRecord/queryBankResult",
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
		function processException(paymentId,result){
			var processDesc = $("#processDesc").val();
			if($.trim(processDesc).length == 0){
				parent.showThree();
				parent.changeThreeName("请填写处理备注");
				return;
			}
			$("#paymentId").val(paymentId);
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
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
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

			//处理取消
			$("#ngp_cancel").on("click",function(){
				$("#ngp_cover").hide();
				$(".ngp_pop_three_cl").hide();
			});
			//处理确定
			$("#ngp_ok").on("click",function(){
				$("#ngp_cover").hide();
				$(".ngp_pop_three_cl").hide();
				var paymentId = '${paymentRecord.paymentId}';
				var transType = '${paymentRecord.transType}';
				var result = $("#result").val();
				var processDesc = $("#processDesc").val();
				var data = {
					paymentId:paymentId,
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
						parent.changeThreeNameEp(result, transType, paymentId);
					},
					error:function(data){
						parent.showThreeEp();
						parent.changeThreeNameEp("系统错误，请稍后再试", transType, paymentId);
					}
				});
			});

		});
	</script>
	<style type="text/css">
		.control-group{width:40%;float:left;}
		#ngp_cover{position: absolute;width:100%;height:100%;background-color:#000;opacity:0.28;z-index:1;display: none;}
		.ngp_pop_three_cl{position: absolute; width:400px;height:220px;top:50%;left:50%;background-color:#fff;z-index:8;margin-left:-200px;
			margin-top:-110px;border-radius: 5px;display: none;border: solid 2px rgba(0,0,0,0.5);}
		.ngp_name_text{font-size: 16px;color: #333;text-align: center;margin-top: 30px;}
		.ngp_btn_kt{margin-top: 30px;text-align: center;}
		.ngp_btn_kt span{background:#4287f5;width:120px; height:35px;line-height: 35px;text-align: center;color: #fff;
			margin: auto;font-size: 14px;font-weight: 400; cursor: pointer;display: inline-block;margin-left: 10px;}
	</style>
</head>
<body>
	<div id="ngp_cover"></div>
	<div class="ngp_pop_three_cl">
		<p class="ngp_name_text" id="three_text_cl"></p>
		<h3 class="ngp_btn_kt"><span id="ngp_ok">确定</span><span id="ngp_cancel">取消</span></h3>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="javascript:void(0)">投诉处理详情</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="paymentRecord" action="${ctx}/payment/paymentFundRecord/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">支付ID：</label>
			<div class="controls">
				${paymentRecord.paymentId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易订单号：</label>
			<div class="controls">
				${paymentRecord.transNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户ID：</label>
			<div class="controls">
				${paymentRecord.merchantId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户登录账号：</label>
			<div class="controls">
				${paymentRecord.merchantLoginName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户公司：</label>
			<div class="controls">
				${paymentRecord.merchantCompany}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品编码：</label>
			<div class="controls">
				${paymentRecord.productCode}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付类型：</label>
			<div class="controls">
				${fns:getDictLabel(paymentRecord.payType, 'payType', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易类型：</label>
			<div class="controls">
				${fns:getDictLabel(paymentRecord.transType, 'TransType', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付状态：</label>
			<div class="controls">
				${fns:getDictLabel(paymentRecord.status, 'PaymentRecordStatus', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付说明：</label>
			<div class="controls">
				${paymentRecord.description}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<fmt:formatDate value="${paymentRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">更新时间：</label>
			<div class="controls">
				<fmt:formatDate value="${paymentRecord.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${paymentRecord.payAmount}" pattern="￥0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付时间：</label>
			<div class="controls">
				<fmt:formatDate value="${paymentRecord.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成功金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${paymentRecord.successAmount}" pattern="￥0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成功时间：</label>
			<div class="controls">
				<fmt:formatDate value="${paymentRecord.successTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通知地址：</label>
			<div class="controls">
				${paymentRecord.notifyUrl}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户订单号：</label>
			<div class="controls">
				${paymentRecord.merchantOrderNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行ID：</label>
			<div class="controls">
				${paymentRecord.bankId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行名称：</label>
			<div class="controls">
				${paymentRecord.bankName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行返回地址：</label>
			<div class="controls">
				${paymentRecord.bankReturnUrl}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行卡号：</label>
			<div class="controls">
				<c:if test="${not empty paymentRecord.bankcardNo}">
					${fns:hiddenBankcardNo(paymentRecord.bankcardNo)}
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行卡类型：</label>
			<div class="controls">
				${fns:getDictLabel(paymentRecord.bankcardType, 'BankcardType', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行卡持卡人类型：</label>
			<div class="controls">
				${paymentRecord.bankcardOwnerType}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行流水号：</label>
			<div class="controls">
				${paymentRecord.bankSerialNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">授权码：</label>
			<div class="controls">
				${paymentRecord.authorizationCode}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通道代码：</label>
			<div class="controls">
				${paymentRecord.channelCode}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通道名称：</label>
			<div class="controls">
				${paymentRecord.channelName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通道参考号：</label>
			<div class="controls">
				${paymentRecord.channelRefNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">币种：</label>
			<div class="controls">
				${paymentRecord.currency}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续费收取方式：</label>
			<div class="controls">
				${fns:getDictLabel(paymentRecord.feeType, 'FeeDeductType', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续费金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${paymentRecord.feeAmount}" pattern="￥0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作人：</label>
			<div class="controls">
				${paymentRecord.operator}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">优惠金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${paymentRecord.couponAmount}" pattern="￥0.00" />
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">用户ID：</label>
			<div class="controls">
				${paymentRecord.userId}
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">结算周期：</label>
			<div class="controls">
				${paymentRecord.settleCyc}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">核对状态：</label>
			<div class="controls">
				${fns:getDictLabel(paymentRecord.checkStatus, 'CheckStatus', '')}
			</div>
		</div>

		<c:if test="${not empty exception}">
			<div class="control-group">
				<label class="control-label">处理备注：</label>
				<div class="controls">
                    <input id="processDesc" type="text">
				</div>
			</div>
		</c:if>

		<div style="clear:both"></div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
			<c:if test="${not empty exception}">
				<input id="queryResult" class="btn" type="button" value="查询银行处理结果" onclick="queryBankResult('${paymentRecord.paymentId}')"/>
				<input id="processSucc" class="btn" type="button" value="处理为成功" onclick="processException('${paymentRecord.paymentId}','1000')"/>
				<input id="processFail" class="btn" type="button" value="处理为失败" onclick="processException('${paymentRecord.paymentId}','1002')"/>
			</c:if>
		</div>
	</form:form>
    <input type="hidden" id="result" value=""/>
    <input type="hidden" id="paymentId" value=""/>
</body>
</html>