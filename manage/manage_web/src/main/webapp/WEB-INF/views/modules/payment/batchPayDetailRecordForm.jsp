<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>转账明细管理</title>
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
				var batchpayId = $("#batchpayId").val();
				var transType = $("#transType").val();
				var result = $("#result").val();
				var processDesc = $("#processDesc").val();
				var data = {
	                exceptionId:batchpayId,
	                transType:transType,
	                processDesc:processDesc,
	                processResult:result
			    }
				$.ajax({
	                type:"post",
	                url:"${ctx}/payment/exceptionRecord/processException",
	                data:data,
	                success: function(data) {
	                    var result = data.result;
	                    parent.showThreeEp();
	                    parent.changeThreeNameEp(result,transType,batchpayId);
	                },
	                error:function(data){
	                	parent.showThreeEp();
	                	parent.changeThreeNameEp("系统错误，请稍后再试");
	                }
	            });
	
			});
		});
		//查询
        function queryBankResult(batchpayId){
        	parent.show_one();
            var data = {
                exceptionId:batchpayId
            }
            $.ajax({
                type:"post",
                url:"${ctx}/payment/exceptionRecord/queryBatchPayBankResult",
                data:data,
                success: function(data) {
                    var result = data.result;
                    parent.showThree();
                    parent.changeThreeName(result);
                    $("#ngp_cover").hide();
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
        function processException(batchpayId,transType,result){
            var processDesc = $("#processDesc").val();
            if($.trim(processDesc).length == 0){
				parent.showThree();
            	parent.changeThreeName("请填写处理备注");
				return;
			}
            $("#batchpayId").val(batchpayId);
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
      	//处理
		function showThree_cl(){
			$('#ngp_cover').height($(document).height());
			$("#ngp_cover").show();
			$(".ngp_pop_three_cl").show();
			$("html,body").animate({scrollTop:0},500);
		}
		//修改处理
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
	.control-group{width:40%;float:left;}
	</style>
</head>
<body>
	<div id="ngp_cover"></div>
	<div class="ngp_pop_three_cl">
	    <p class="ngp_name_text" id="three_text_cl"></p>
	    <h3 class="ngp_btn_kt"><span id="ngp_ok_q">确定</span><span id="ngp_ok_c">取消</span></h3>
	</div>
	<ul class="nav nav-tabs">
		<c:if test="${empty exception}">
			<li><a href="${ctx}/payment/batchPayRecordDetail/">转账明细管理列表</a></li>
			<li class="active"><a href="javascript:void(0)">转账明细详细查看</a></li>
		</c:if>
		<c:if test="${not empty exception}">
			<li><a href="">转账详情</a></li>
		</c:if>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="batchPayRecordDetail" action="${ctx}/payment/batchPayRecordDetail/checkBatchPay?id=${batchPayRecordDetail.batchPayId}&merchantId=${batchPayRecordDetail.merchantId}" method="post" class="form-horizontal">
		<%-- <form:hidden path="id"/> --%>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">交易订单号：</label>
			<div class="controls">
					${batchPayRecordDetail.batchPayId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户订单号：</label>
			<div class="controls">
					${batchPayRecordDetail.merchantPayNo}
			</div>
		</div>
		<div class="control-group">
            <label class="control-label">付款批次号：</label>
            <div class="controls">
                    ${batchPayRecordDetail.batchId}
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">商户批次号：</label>
			<div class="controls">
					${batchPayRecordDetail.merchantBatchNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付订单号：</label>
			<div class="controls">
					${batchPayRecordDetail.paymentId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">账户ID：</label>
			<div class="controls">
					${batchPayRecordDetail.accountId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">账户名称：</label>
			<div class="controls">
					${batchPayRecordDetail.accountName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户登录账号：</label>
			<div class="controls">
					${batchPayRecordDetail.merchantLoginName}
			</div>
		</div>
		<div class="control-group">
            <label class="control-label">商户公司名称：</label>
            <div class="controls">
                    ${batchPayRecordDetail.merchantCompany}
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">转账银行卡号：</label>
			<div class="controls">
				<shiro:lacksPermission name="payment:batchPayRecordDetail:bankcardNo">
                    ${fns:hiddenBankcardNo(batchPayRecordDetail.bankcardNo)}
                </shiro:lacksPermission>
				<shiro:hasPermission name="payment:batchPayRecordDetail:bankcardNo">
				    ${batchPayRecordDetail.bankcardNo}
                </shiro:hasPermission>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">持卡人姓名：</label>
			<div class="controls">
					${ batchPayRecordDetail.bankcardOwnerName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行卡类型：</label>
			<div class="controls">
					${fns:getDictLabel(batchPayRecordDetail.bankcardType, 'BankcardType', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行卡持卡人类型：</label>
			<div class="controls">
				<c:choose>
					<c:when test="${ batchPayRecordDetail.bankcardOwnerType eq 0}">个人</c:when>
					<c:when test="${ batchPayRecordDetail.bankcardOwnerType eq 1}">企业</c:when>
					<c:otherwise>未知</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行ID：</label>
			<div class="controls">
					${batchPayRecordDetail.bankId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行名称：</label>
			<div class="controls">
					${batchPayRecordDetail.bankName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联行号：</label>
			<div class="controls">
					${ batchPayRecordDetail.associateLineNumber}
			</div>
		</div>
 		<div class="control-group">
			<label class="control-label">开户银行省份：</label>
			<div class="controls">
				${ batchPayRecordDetail.province}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开户银行城市：</label>
			<div class="controls">
				${ batchPayRecordDetail.city}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开户支行名称：</label>
			<div class="controls">
				${batchPayRecordDetail.openbankName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">转账金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${batchPayRecordDetail.payAmount}" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续费：</label>
			<div class="controls">
				<fmt:formatNumber value="${batchPayRecordDetail.feeAmount}" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
					${fns:getDictLabels(batchPayRecordDetail.status,'BatchPayRecordDetailStatus','')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">失败原因：</label>
			<div class="controls">
					${batchPayRecordDetail.failReason}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">异常状态：</label>
			<div class="controls">
					${batchPayRecordDetail.exceptionStatus}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成功金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${batchPayRecordDetail.successAmount}" pattern="￥0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成功时间：</label>
			<div class="controls">
				<fmt:formatDate value="${batchPayRecordDetail.successTime}" pattern="YYYY-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最早到账时间：</label>
			<div class="controls">
				<fmt:formatDate value="${batchPayRecordDetail.intoaccountTime}" pattern="YYYY-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<fmt:formatDate value="${batchPayRecordDetail.createTime}" pattern="YYYY-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">更新时间：</label>
			<div class="controls">
				<fmt:formatDate value="${batchPayRecordDetail.updateTime}" pattern="YYYY-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
            <label class="control-label">付款通道：</label>
            <div class="controls">
               ${batchPayRecordDetail.channelCode}
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">银行订单号：</label>
			<div class="controls">
					${batchPayRecordDetail.bankOrderId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">转账理由：</label>
			<div class="controls">
				${batchPayRecordDetail.payReason}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否需要审核：</label>
			<div class="controls">
				<c:if test="${batchPayRecordDetail.auditFlag eq '0'}">是</c:if>
				<c:if test="${batchPayRecordDetail.auditFlag eq '1'}">否</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作人：</label>
			<div class="controls">
					${batchPayRecordDetail.operator}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审核人：</label>
			<div class="controls">
					${batchPayRecordDetail.auditPerson}
			</div>
		</div>
		<c:if test="${not empty exception}">
        <div class="control-group">
            <label class="control-label">处理备注：</label>
            <div class="controls">
                <form:textarea path="processDesc" htmlEscape="false" rows="3" maxlength="200" class="input-xxlarge"/>
            </div>
        </div>
    </c:if>
		<p>
		<div class="form-actions">
            <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
            <c:if test="${not empty exception}">
               <input id="queryResult" class="btn" type="button" value="查询银行处理结果" onclick="queryBankResult('${batchPayRecordDetail.batchPayId}')"/>
	           <c:if test="${batchPayRecordDetail.status == 'PAYING' || batchPayRecordDetail.status == 'PREPAY'}">
		           <input id="processSuccess" class="btn" type="button" value="处理为成功" onclick="processException('${batchPayRecordDetail.batchPayId}','BATPAY','1000')"/>
		           <input id="processFail" class="btn" type="button" value="处理为失败" onclick="processException('${batchPayRecordDetail.batchPayId}','BATPAY','1002')"/>
               </c:if>
               <c:if test="${batchPayRecordDetail.status == 'FAILED' || batchPayRecordDetail.status == 'SUCCES'}">
               	   <input id="processFail" class="btn" type="button" value="处理为失败" onclick="processException('${batchPayRecordDetail.batchPayId}','BATPAY','1002')"/>
               </c:if>
            </c:if>
        </div>
		</p>
	</form:form>
	<input type="hidden" id="batchpayId" value=""/>
	<input type="hidden" id="transType" value=""/>
	<input type="hidden" id="result" value=""/>
</body>
</html>